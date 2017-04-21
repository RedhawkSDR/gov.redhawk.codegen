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

package gov.redhawk.ide.idl.ui.wizard;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.ide.idl.IdlLibraryProjectNature;
import gov.redhawk.ide.idl.IdlProjectBuilder;
import gov.redhawk.ide.idl.generator.newidl.IDLProjectCreator;
import gov.redhawk.ide.idl.ui.IdeIdlUiPlugin;
import gov.redhawk.ide.util.ResourceUtils;
import net.sf.eclipsecorba.compiler.CompileOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * @since 1.1
 */
public class NewScaIDLProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	/** The configuration used for this Wizard */
	private IConfigurationElement fConfig;

	/** Instance of the properties page associated with the Wizard */
	private ScaIDLProjectPropertiesWizardPage idlPropertiesPage;

	public NewScaIDLProjectWizard() {
		setWindowTitle("IDL Project");
		setNeedsProgressMonitor(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		try {
			// Find the working sets and where the new project should be located on disk
			final IWorkingSet[] workingSets = this.idlPropertiesPage.getSelectedWorkingSets();
			final java.net.URI locationURI;
			if (this.idlPropertiesPage.useDefaults()) {
				locationURI = null;
			} else {
				locationURI = this.idlPropertiesPage.getLocationURI();
			}
			final String projectName = this.idlPropertiesPage.getProjectName();
			final String moduleName = this.idlPropertiesPage.getModuleName();
			final String interfaceVersion = this.idlPropertiesPage.getInterfaceVersion();
			final List<String> idlFiles = this.idlPropertiesPage.getIdlFiles();

			final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

				@Override
				protected void execute(final IProgressMonitor monitor) throws CoreException {
					try {
						final SubMonitor progress = SubMonitor.convert(monitor, "Creating IDL Project...", 8);
						final MultiStatus multiStatus = new MultiStatus(IdeIdlUiPlugin.PLUGIN_ID, IStatus.OK, "Adding Nature to Project", null);

						// Create an empty project
						final IProject project = IDLProjectCreator.createEmptyProject(projectName, locationURI, progress.newChild(1));

						// Add the necessary natures to our project
						CppGeneratorUtils.addCandCPPNatures(project, progress.newChild(1), multiStatus);
						CppGeneratorUtils.addManagedNature(project, progress.newChild(1), multiStatus, "", null, null);
						if (!project.hasNature(IdlLibraryProjectNature.ID)) {
							Map<String, String> args = new HashMap<String, String>();
							args.put(IdlProjectBuilder.MODULE_NAME_ARG, moduleName);
							IdlLibraryProjectNature.addNature(project, args, progress.newChild(1));
						}

						if (!multiStatus.isOK()) {
							throw new CoreException(multiStatus);
						}

						if (workingSets.length > 0) {
							PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
						}

						// Create the support files
						IDLProjectCreator.createIDLFiles(project, moduleName, interfaceVersion, idlFiles, progress.newChild(1));

						final SubMonitor importChildProgress = progress.newChild(1);
						importChildProgress.beginTask("Adding idl files...", idlFiles.size());
						// Import any requested IDL files
						for (final String idl : idlFiles) {
							final String fileName = new Path(idl).lastSegment();
							final IFile file = project.getFile(fileName);
							FileInputStream fileStream = null;

							try {
								fileStream = new FileInputStream(idl);
								file.create(fileStream, false, importChildProgress.newChild(1));
							} catch (final FileNotFoundException e) {
								// PASS
							} finally {
								if (fileStream != null) {
									try {
										fileStream.close();
									} catch (final IOException e) {
										// PASS
									}
								}
							}
						}
						
						addDefaultIncludePaths(project);

						// Setup the IDL Path
						ResourceUtils.createIdlLibraryResource(project, progress.newChild(1));

						// Schedule a new job which will run a clean build; this should ensure all resource change
						// notifications are dispatched before beginning the build
						//
						// This will also make sure that the jar files are built as well
						project.build(IncrementalProjectBuilder.CLEAN_BUILD, progress.newChild(1));
					} finally {
						monitor.done();
					}
				}

				@SuppressWarnings("unchecked")
				private void addDefaultIncludePaths(IProject project) throws CoreException {
					CompileOptions options = CompileOptions.load(project);
					options.outputDirectory = "";
					for (IPath path : RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath(false)) {
						options.getIncludes().add(path.toOSString());
					}
					options.save(project);
				}
			};
			getContainer().run(false, false, op);
			BasicNewProjectResourceWizard.updatePerspective(this.fConfig);
		} catch (final InvocationTargetException x) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, IdeIdlUiPlugin.PLUGIN_ID, x.getCause().getMessage(), x.getCause()),
			        StatusManager.SHOW | StatusManager.LOG);
			return false;
		} catch (final InterruptedException x) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canFinish() {
		return this.idlPropertiesPage.isPageComplete();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		this.idlPropertiesPage = new ScaIDLProjectPropertiesWizardPage("");
		addPage(this.idlPropertiesPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInitializationData(final IConfigurationElement config, final String propertyName, final Object data) throws CoreException {
		this.fConfig = config;
	}
}
