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
package gov.redhawk.ide.sharedlibrary.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.sharedlibrary.codegen.SharedLibraryProjectCreator;
import gov.redhawk.ide.sharedlibrary.ui.SharedLibraryUi;
import gov.redhawk.ide.spd.ui.ComponentUiPlugin;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;
import gov.redhawk.sca.util.SubMonitor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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

public class NewSharedLibraryScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {

	private final SharedLibraryProjectPropertiesWizardPage p1 = new SharedLibraryProjectPropertiesWizardPage("projectPage", "Shared Library");
	private SharedLibraryWizardPage p2 = new SharedLibraryWizardPage("tablePageNew", p1.getModel(), ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);

	public NewSharedLibraryScaResourceProjectWizard() {
		super();
		setWindowTitle("New Shared Library Project");
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

						// Populate the shared library spd.xml with base information and implementation
						setOpenEditorOn(SharedLibraryProjectCreator.createComponentFiles(project, projectName, getSoftPkg().getId(), null, progress.newChild(1)));
						SharedLibraryProjectCreator.addImplementation(project, projectName, pageImpl, settings, progress.newChild(1));

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
			StatusManager.getManager().handle(new Status(Status.ERROR, SharedLibraryUi.PLUGIN_ID, "Failed to create Shared Library Project.", e.getCause()),
				StatusManager.SHOW | StatusManager.LOG);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

	
}
