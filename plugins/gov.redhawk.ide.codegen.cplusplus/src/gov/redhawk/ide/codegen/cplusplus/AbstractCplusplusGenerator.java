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
package gov.redhawk.ide.codegen.cplusplus;

import gov.redhawk.ide.codegen.AbstractCodeGenerator;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.cdt.build.core.scannerconfig.ScannerConfigNature;
import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.core.ManagedCProjectNature;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * An abstract class for C/C++ implementation generation. It handles most aspects of configuring CDT, but leaves
 * generating code files up to the sub-class.
 */
public abstract class AbstractCplusplusGenerator extends AbstractCodeGenerator {

	public AbstractCplusplusGenerator() {
	}

	/**
	 * @since 5.0
	 */
	@Override
	public IStatus generate(final ImplementationSettings implSettings, final Implementation impl, final PrintStream out, final PrintStream err, // SUPPRESS CHECKSTYLE NumParameters
	        final IProgressMonitor monitor, final String[] generateFiles, final boolean shouldGenerate, final List<FileToCRCMap> crcMap) {
		final int CLEANUP_WORK = 1, ADD_NATURE_WORK = 1;
		final int ADD_MANAGED_NATURE_WORK = 90;
		final int GENERATE_CODE_WORK = 8;
		final SubMonitor progress = SubMonitor.convert(monitor, "Configuring project", CLEANUP_WORK + ADD_NATURE_WORK + ADD_MANAGED_NATURE_WORK
		        + GENERATE_CODE_WORK);

		final IProject project = ModelUtil.getProject(implSettings);
		if (project == null) {
			return new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, "Unable to determine project; cannot proceed with code generation", null);
		}
		final String componentName = implSettings.getName();
		final String destinationDirectory = implSettings.getOutputDir();
		final MultiStatus retStatus = new MultiStatus(GccGeneratorPlugin.PLUGIN_ID, IStatus.OK, "C++ code generation problems", null);

		// We clean source folders first. This ensures that if the user deletes an implementation directory and
		// then tries to regenerate that we first cleanup the mess CDT left when they deleted the directory.
		final IStatus status = cleanupSourceFolders(project, progress.newChild(CLEANUP_WORK));
		if (!status.isOK()) {
			retStatus.add(status);
			if (status.getSeverity() == IStatus.ERROR) {
				return retStatus;
			}
		}

		CppGeneratorUtils.addCandCPPNatures(project, progress.newChild(ADD_NATURE_WORK), retStatus);
		CppGeneratorUtils.addManagedNature(project, progress.newChild(ADD_MANAGED_NATURE_WORK), retStatus, destinationDirectory, out, shouldGenerate, impl);

		if (shouldGenerate) {
			out.println("Targeting location " + project.getLocation() + "/" + destinationDirectory + " for code generation...");

			try {
				generateCode(impl, implSettings, project, componentName, progress.newChild(GENERATE_CODE_WORK), generateFiles, crcMap);
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, "Unable to generate code", e));
				return retStatus;
			}
		}

		return retStatus;
	}

	/**
	 * @since 7.0
	 */
	@Override
	public IStatus cleanupSourceFolders(final IProject project, final IProgressMonitor monitor) {
		final int REFRESH_WORK = 10;
		final int ADJUST_CONFIG_WORK = 30;
		final int PERFORM_SAVE_WORK = 90;
		final SubMonitor progress = SubMonitor.convert(monitor, "Correcting source folders", REFRESH_WORK + ADJUST_CONFIG_WORK + PERFORM_SAVE_WORK);
		final MultiStatus retStatus = new MultiStatus(GccGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Problems while cleaning up source code folders", null);

		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(REFRESH_WORK));
		} catch (final CoreException e) {
			retStatus.add(new Status(IStatus.WARNING, GccGeneratorPlugin.PLUGIN_ID, "Unable to refresh workspace", e));
		}
		progress.setWorkRemaining(ADJUST_CONFIG_WORK + PERFORM_SAVE_WORK);

		// Get the configurations for the project
		final CoreModel coreModel = CoreModel.getDefault();
		final ICProjectDescription projectDescription = coreModel.getProjectDescription(project, true);
		if (projectDescription == null) {
			return retStatus;
		}
		final ICConfigurationDescription[] configArray = projectDescription.getConfigurations();

		// Iterate over the configurations
		final int WORK_PER_LOOP = 2;
		int loopWork = configArray.length * WORK_PER_LOOP;
		final SubMonitor loopProgress = progress.newChild(ADJUST_CONFIG_WORK).setWorkRemaining(loopWork);
		final ICSourceEntry rootSourceEntry = (ICSourceEntry) CDataUtil.createEntry(ICSettingEntry.SOURCE_PATH, IPath.SEPARATOR + project.getName(), null,
		        null, ICSettingEntry.VALUE_WORKSPACE_PATH | ICSettingEntry.RESOLVED);
		boolean changes = false;
		for (final ICConfigurationDescription config : configArray) {
			// Find source folders that still exist (note that source entries from an ICConfigurationDescription
			// are relative to the workspace root, not the project)
			final ICSourceEntry[] originalEntries = config.getSourceEntries();
			final ArrayList<ICSourceEntry> tempEntries = new ArrayList<ICSourceEntry>();
			for (final ICSourceEntry entry : originalEntries) {
				if (project.getWorkspace().getRoot().findMember(entry.getFullPath()) != null && !entry.equalsByContents(rootSourceEntry)) {
					tempEntries.add(entry);
				}
			}
			loopProgress.worked(1);

			// Update the list of source folders to just those that exist, or remove the configuration if source folders are gone
			if (tempEntries.size() > 0) {
				if (tempEntries.size() != originalEntries.length) {
					changes = true;
					final ICSourceEntry[] entries = new ICSourceEntry[tempEntries.size()];
					tempEntries.toArray(entries);
					try {
						config.setSourceEntries(entries);
					} catch (final CoreException e) {
						retStatus.add(new Status(IStatus.WARNING, GccGeneratorPlugin.PLUGIN_ID,
						        "Unable to adjust the list of source code folders for the project", e));
					}
					loopProgress.worked(1);
				}
			} else {
				changes = true;
				projectDescription.removeConfiguration(config);
				loopProgress.worked(1);
			}
			loopWork -= WORK_PER_LOOP;
			loopProgress.setWorkRemaining(loopWork);
		}
		progress.setWorkRemaining(PERFORM_SAVE_WORK);

		// If there are no changes, we're done
		if (!changes) {
			return retStatus;
		}

		// Ensure the new project description is valid
		if (projectDescription.isValid()) {
			// Set project description - this makes it go into effect
			try {
				coreModel.setProjectDescription(project, projectDescription);
				progress.worked(PERFORM_SAVE_WORK);
				//CCorePlugin.getIndexManager().update(new ICElement[] { (ICElement) project.getAdapter(ICElement.class) }, IIndexManager.UPDATE_CHECK_CONFIGURATION);
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.WARNING, GccGeneratorPlugin.PLUGIN_ID,
				        "Unable to update the project's description while cleaning up source folders", e));
			}
		} else {
			// Our project description isn't valid because we're removing all the configurations
			// Just blow away all the CDT config and natures we can
			final int PERFORM_SAVE_WORK_QUARTER = PERFORM_SAVE_WORK / 4; // SUPPRESS CHECKSTYLE MAGIC
			try {
				ManagedBuildManager.removeBuildInfo(project);
				ScannerConfigNature.removeScannerConfigNature(project);
				ManagedCProjectNature.removeManagedNature(project, loopProgress.newChild(PERFORM_SAVE_WORK_QUARTER));
				CProjectNature.removeCNature(project, loopProgress.newChild(PERFORM_SAVE_WORK_QUARTER));
				CCProjectNature.removeCCNature(project, loopProgress.newChild(PERFORM_SAVE_WORK_QUARTER));
				project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(PERFORM_SAVE_WORK_QUARTER));
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.WARNING, GccGeneratorPlugin.PLUGIN_ID, "Error removing project nature", e));
			}
		}

		return retStatus;
	}

	/**
	 * Generates code files for the implementation
	 * 
	 * @param monitor The progress monitor to use for reporting progress to the
	 *            user. It is the caller's responsibility to call done() on the
	 *            given monitor. Accepts <code>null</code>, indicating that no
	 *            progress should be reported and that the operation cannot be
	 *            canceled.
	 * @throws CoreException A problem occurs while generating code files for the implementation
	 * @since 5.0
	 */
	protected abstract void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName,
	        IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException;

}
