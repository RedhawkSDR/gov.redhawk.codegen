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
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.softpackage.codegen.SoftPackageProjectCreator;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.ide.spd.ui.ComponentUiPlugin;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;
import gov.redhawk.sca.util.SubMonitor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewSoftpackageScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {

	private final SoftpackageProjectPropertiesWizardPage p1 = new SoftpackageProjectPropertiesWizardPage("projectPage", "Softpackage");
	private SoftpackageWizardPage p2;

	private final SoftpackageCreateNewLibraryWizardPage createNewLibraryPage = new SoftpackageCreateNewLibraryWizardPage("tablePageNew",
		ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
	private final SoftpackageUseExistingLibraryWizardPage useExistingLibraryPage = new SoftpackageUseExistingLibraryWizardPage("tablePageExisting",
		ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);

	public NewSoftpackageScaResourceProjectWizard() {
		super();
		setWindowTitle("New Softpackage Project");
		p2 = createNewLibraryPage;

		// Updates wizard based on selection of "Create new library" or "Use existing library"
		p1.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (p1.getModel().isCreateNewLibrary()) {
					p2 = createNewLibraryPage;
				} else {
					p2 = useExistingLibraryPage;
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPages() {
		setResourcePropertiesPage((ScaProjectPropertiesWizardPage) p1);
		addPage(getResourcePropertiesPage());
		addPage(createNewLibraryPage);
		addPage(useExistingLibraryPage);

		setImplPage((ImplementationWizardPage) p2);
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
		return p1.canFlipToNextPage() && p2.isPageComplete();
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

						// TODO: Needs to be a new project vs. import project check here, see NewScaResourceWizard - 651
						// Populate the softpackage spd.xml with base information and implementation
						SoftPackageProjectCreator.createComponentFiles(project, projectName, getSoftPkg().getId(), null, progress.newChild(1));
						SoftPackageProjectCreator.addImplementation(project, projectName, pageImpl, settings, progress.newChild(1));

						project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(1));

					} catch (CoreException e) {
						throw e;
					} catch (Exception e) { // SUPPRESS CHECKSTYLE INLINE
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
}
