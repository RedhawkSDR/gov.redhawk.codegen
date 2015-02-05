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
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.softpackage.codegen.SoftPackageProjectCreator;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.ide.spd.ui.ComponentUiPlugin;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.sca.util.SubMonitor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewSoftpackageScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {

	private final SoftpackageProjectPropertiesWizardPage p1 = new SoftpackageProjectPropertiesWizardPage("projectPage", "Softpackage");
	private SoftpackageWizardPage p2 = new SoftpackageWizardPage("tablePageNew", p1.getModel(), ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);

	public NewSoftpackageScaResourceProjectWizard() {
		super();
		setWindowTitle("New Softpackage Project");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPages() {
		// NOTE: Since Java doesn't allow multiple-inheritance, a second 'hidden' page is included
		// to handle implementation page actions. This page is not meant to be exposed to users.
		setResourcePropertiesPage((ScaProjectPropertiesWizardPage) p1);
		addPage(getResourcePropertiesPage());

		setImplPage((ImplementationWizardPage) p2);
		addPage(getImplPage());
		getImplPage().setImpl(this.getImplementation());
		getImplList().add(new ImplementationAndSettings(getImplPage().getImplementation(), getImplPage().getImplSettings()));

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
		return null;
	}

	@Override
	public boolean canFinish() {
		return p1.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		// Create a softpkg for this project
		final SoftPkg newSoftPkg = SpdFactory.eINSTANCE.createSoftPkg();
		newSoftPkg.setName(getResourcePropertiesPage().getProjectName());
		newSoftPkg.setId(getID());
		setSoftPkg(newSoftPkg);

		final IWorkingSet[] workingSets = this.p1.getSelectedWorkingSets();
		final java.net.URI locationURI;
		if (this.p1.useDefaults()) {
			locationURI = null;
		} else {
			locationURI = this.p1.getLocationURI();
		}
		final String projectName = this.p1.getProjectName();

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
				try {
					final SubMonitor progress = SubMonitor.convert(monitor, "Creating project...", 6);

					// Create the implementation
					final ImplementationWizardPage page = (ImplementationWizardPage) getWizPages().get(1);
					Implementation pageImpl = page.getImplementation();
					ImplementationSettings settings = page.getImplSettings();

					// Create an empty project
					final IProject project = createEmptyProject(projectName, locationURI, progress.newChild(1));

					try {
						if (workingSets.length > 0) {
							PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
						}
						BasicNewProjectResourceWizard.updatePerspective(getfConfig());

						// Populate the softpackage spd.xml with base information and implementation
						setOpenEditorOn(SoftPackageProjectCreator.createComponentFiles(project, projectName, getSoftPkg().getId(), null, progress.newChild(1)));
						SoftPackageProjectCreator.addImplementation(project, projectName, pageImpl, settings, progress.newChild(1));
						generateFiles(settings, pageImpl, progress.newChild(1));

						project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(1));

					} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
						if (project != null) {
							project.delete(true, progress.newChild(1));
						}
						throw e;
					}

				} catch (final CoreException e) {
					throw e;
				} catch (InterruptedException e) {
					throw e;
				} catch (OperationCanceledException e) {
					throw e;
				} catch (InvocationTargetException e) {
					throw e;
				} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
					throw new CoreException(new Status(IStatus.ERROR, ComponentUiPlugin.PLUGIN_ID, "Error creating project", e));
				} finally {
					if (monitor != null) {
						monitor.done();
					}
				}
			}
		};

		try {
			this.getContainer().run(true, true, operation);

			// Open the default editor for the new SCA component; also invoke code generator for manual templates
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			final IFile spdFile = this.getOpenEditorOn();
			if ((spdFile != null) && spdFile.exists()) {
				try {
					IDE.openEditor(activePage, spdFile, true);
				} catch (final PartInitException e) {
					// PASS
				}
			}

			return true;
		} catch (final InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(Status.ERROR, SoftPackageUi.PLUGIN_ID, "Failed to create SoftPackage Project.", e.getCause()),
				StatusManager.SHOW | StatusManager.LOG);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * @param subMonitor
	 * @param pageImpl
	 * @param settings
	 * @throws CoreException
	 * 
	 */
	private void generateFiles(ImplementationSettings implSettings, Implementation impl, SubMonitor monitor) throws CoreException {
		// TODO: CHECKSTYLE:OFF
		// TODO: Pull this method up, possibly as an overloaded method in the JinjaGenerator class?
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Generating Softpkg Files...", 3);
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final ArrayList<String> args = new ArrayList<String>();
		try {
			final String redhawkCodegen = JinjaGeneratorPlugin.getDefault().getCodegenPath().toFile().getPath();
			args.add(redhawkCodegen);
		} catch (Exception e) {
			System.out.println(e);
		}

		// Force overwrite of existing files; we assume that the user has already signed off on this.
		args.add("-f");

		// Set base output directory to the project location
		args.add("-C");
		args.add(project.getLocation().toOSString());

		// Turn the settings into command-line flags
		// TODO: See seetingsToOptions in JinjaGenerator, maybe pull this out into it's own method?
		args.add("--impl");
		args.add(implSettings.getId());
		args.add("--impldir");
		args.add(implSettings.getOutputDir());
		args.add("--template");
		args.add(implSettings.getTemplate());
		for (final Property property : implSettings.getProperties()) {
			args.add("-B " + property.getId() + "=" + property.getValue());
		}

		// TODO: This adds the spdFile, need to pass the actual spdFile when we pull this method up
		args.add(this.getOpenEditorOn().getLocation().toOSString());

		final String[] command = args.toArray(new String[args.size()]);
		for (final String arg : args) {
			if (arg == null) {
				throw new CoreException(new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "Error found in code-generation command: \n" + args));
			}
			System.out.print(arg + " ");
		}
		System.out.println();
		subMonitor.worked(1);

		try {
			Process process = java.lang.Runtime.getRuntime().exec(command);
			process.waitFor();
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor.newChild(1));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			subMonitor.done();
		}
	}
}
