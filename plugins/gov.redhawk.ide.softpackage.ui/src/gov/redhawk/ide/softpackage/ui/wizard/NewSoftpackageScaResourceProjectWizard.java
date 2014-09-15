/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.softpackage.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.ide.spd.ui.ComponentUiPlugin;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;
import gov.redhawk.sca.util.Debug;
import gov.redhawk.sca.util.SubMonitor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.CorbaUtils;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewSoftpackageScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {
	private static final Debug DEBUG = new Debug(SoftPackageUi.PLUGIN_ID, "wizard");

	private final SoftpackageProjectPropertiesWizardPage p1 = new SoftpackageProjectPropertiesWizardPage("", "Softpackage");
	private final SoftpackageImplementationWizardPage p2 = new SoftpackageImplementationWizardPage("", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
	private SoftpackageTableWizardPage p3;

	private final SoftpackageTableWizardPage createNewLibraryPage = new SoftpackageTableWizardPage("tablePageNew", true);
	private final SoftpackageTableWizardPage useExistingLibraryPage = new SoftpackageTableWizardPage("tablePageExisting", false);

	public NewSoftpackageScaResourceProjectWizard() {
		super();
		setWindowTitle("New Softpackage Project");
		p3 = createNewLibraryPage;

		// Updates wizard based on selection of "Create new library" or "Use existing library"
		p2.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (p2.getModel().isCreateNewLibrary()) {
					p3 = createNewLibraryPage;
				} else {
					p3 = useExistingLibraryPage;
				}
			}
		});
	}

	@Override
	public void addPages() {
		setResourcePropertiesPage((ScaProjectPropertiesWizardPage) p1);
		addPage(getResourcePropertiesPage());

		setImplPage((ImplementationWizardPage) p2);
		getImplPage().setImpl(this.getImplementation());
		getImplList().add(new ImplementationAndSettings(getImplPage().getImplementation(), getImplPage().getImplSettings()));
		addPage(p2);

		addPage(createNewLibraryPage);
		addPage(useExistingLibraryPage);

		try {
			final Field field = Wizard.class.getDeclaredField("pages");
			field.getModifiers();
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			setWizPages((List<IWizardPage>) field.get(this));
		} catch (final SecurityException e1) {
			// PASS
		} catch (final NoSuchFieldException e1) {
			// PASS
		} catch (final IllegalArgumentException e) {
			// PASS
		} catch (final IllegalAccessException e) {
			// PASS
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage currentPage) {
		if (currentPage == p1) {
			return p2;
		}
		if (currentPage == p2) {
			return p3;
		}
		return null;
	}

	@Override
	public boolean canFinish() {
		return p1.canFlipToNextPage() && p2.canFlipToNextPage() && p3.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		try {
			final IWorkingSet[] workingSets = this.p1.getSelectedWorkingSets();
			final java.net.URI locationURI;
			if (this.p1.useDefaults()) {
				locationURI = null;
			} else {
				locationURI = this.p1.getLocationURI();
			}
			final String projectName = this.p1.getProjectName();
			getContainer().run(true, true, new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException {
					final SubMonitor progress = SubMonitor.convert(monitor, "Creating project...", 3);
					// Create an empty project
					final IProject project = createEmptyProject(projectName, locationURI, progress.newChild(1));

					try {
						try {
							if (workingSets.length > 0) {
								PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
							}
							BasicNewProjectResourceWizard.updatePerspective(getfConfig());

							URL url = FileLocator.find(SoftPackageUi.getDefault().getBundle(), new Path("resources/packageProjectGenerator.py"), null);
							URL fileUrl;
							SubMonitor codegenProgress = progress.newChild(1);
							codegenProgress.beginTask("Calling Codegen...", IProgressMonitor.UNKNOWN);
							String json = getJsonString(project.getLocation().toFile().getParentFile().getAbsolutePath(), project.getName());
							if (DEBUG.enabled) {
								DEBUG.message("JSON to send to script: {0}", json);
							}

							fileUrl = FileLocator.toFileURL(url);
							File file = new File(fileUrl.toURI());
							ProcessBuilder builder = new ProcessBuilder(file.getAbsolutePath());
							if (DEBUG.enabled) {
								DEBUG.message("Invoking script...");
							}

							final Process process = builder.start();
							OutputStream inputStream = process.getOutputStream();
							inputStream.write(json.getBytes());
							inputStream.close();

							Integer returnCode = CorbaUtils.invoke(new Callable<Integer>() {

								@Override
								public Integer call() throws Exception {
									return process.waitFor();
								}

							}, codegenProgress);
							if (returnCode != 0) {
								InputStream errStream = process.getErrorStream();
								InputStream outStream = process.getInputStream();
								String errorStr = IOUtils.toString(errStream);
								String outStr = IOUtils.toString(outStream);
								throw new InvocationTargetException(new CoreException(new Status(Status.ERROR, SoftPackageUi.PLUGIN_ID,
									"Subprocess python script exited with " + returnCode + "\nOutput: " + outStr + "\nError:" + errorStr, null)));
							}
							codegenProgress.worked(1);

							String spdFileName = project.getName() + SpdPackage.FILE_EXTENSION; // SUPPRESS CHECKSTYLE
							final IFile spdFile = project.getFile(spdFileName);
							setOpenEditorOn(spdFile);

							// Allows for subclasses to modify the project
							modifyResult(project, spdFile, progress.newChild(1));

							project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(1));

						} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
							if (project != null) {
								project.delete(true, progress.newChild(1));
							}
							throw e;
						}
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					} catch (URISyntaxException e) {
						throw new InvocationTargetException(e);
					} catch (CoreException e) {
						throw e;
					} catch (InvocationTargetException e) {
						throw e;
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, ComponentUiPlugin.PLUGIN_ID, "Error creating project", e));
					} finally {
						if (monitor != null) {
							monitor.done();
						}
					}

				}
			});
		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(Status.ERROR, SoftPackageUi.PLUGIN_ID, "Failed to create SoftPackage Project.", e.getCause()),
				StatusManager.SHOW | StatusManager.LOG);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private String getJsonString(String outputDir, String name) {
		Map<String, Object> metaData = new HashMap<String, Object>();
		metaData.put("outputDir", outputDir);
		metaData.put("name", name);
		metaData.put("impl", p2.getModel());
		metaData.put("library", p3.getModel());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(metaData);
	}
}
