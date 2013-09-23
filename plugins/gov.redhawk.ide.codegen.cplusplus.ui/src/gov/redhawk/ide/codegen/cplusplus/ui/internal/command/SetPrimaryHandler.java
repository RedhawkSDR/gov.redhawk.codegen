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
package gov.redhawk.ide.codegen.cplusplus.ui.internal.command;

import gov.redhawk.ide.codegen.CodegenPackage;
import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.WaveDevSettings;
import gov.redhawk.ide.codegen.cplusplus.ui.CplusplusUiActivator;
import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IManagedProject;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.WorkbenchJob;

public class SetPrimaryHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// If the user used a context menu, generate code on the selection(s)
		final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection != null && selection instanceof IStructuredSelection) {
			final IStructuredSelection ss = (IStructuredSelection) selection;
			final WorkbenchJob job = new WorkbenchJob("Generate Component(s)") {
				@Override
				public IStatus runInUIThread(final IProgressMonitor monitor) {
					try {
						final Implementation impl = (Implementation) ss.getFirstElement();
						final SoftPkg softPkg = (SoftPkg) impl.eContainer();
						final IProject project = ModelUtil.getProject(softPkg);
						final WaveDevSettings waveDev = CodegenUtil.loadWaveDevSettings(softPkg);
						final String language = impl.getProgrammingLanguage().getName();

						// Initial validation
						final MultiStatus retStatus = new MultiStatus(CplusplusUiActivator.PLUGIN_ID, IStatus.OK, "Problems while setting primary implementation", null);
						// Wavedev checks
						if (waveDev == null) {
							retStatus.add(new Status(IStatus.ERROR, CplusplusUiActivator.PLUGIN_ID,
							        "Unable to find project settings (wavedev) file. Cannot generate code."));
							return retStatus;
						}
						final ImplementationSettings implSetting = waveDev.getImplSettings().get(impl.getId());
						if (implSetting == null) {
							retStatus.add(new Status(IStatus.ERROR, CplusplusUiActivator.PLUGIN_ID, "Unable to find settings in wavedev file for implementation "
							        + impl.getId()));
							return retStatus;
						}

						if (!CodegenUtil.CPP.equals(impl.getProgrammingLanguage().getName())) {
							retStatus.add(new Status(IStatus.ERROR, CplusplusUiActivator.PLUGIN_ID, "Primary can only be set for C++ implementations."));
							return retStatus;
						}

						TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(waveDev);

						if (domain == null) {
							domain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
							domain.setID("gov.redhawk.spd.editingDomainId");
						}

						final CompoundCommand updateCommand = new CompoundCommand();
						final List<String> oldSource = new ArrayList<String>();

						final EMap<String, ImplementationSettings> settingsMap = waveDev.getImplSettings();
						// Loop through all the implementations
						for (final Implementation tmpImpl : softPkg.getImplementation()) {
							// Get the settings for the implementation
							final ImplementationSettings set = settingsMap.get(tmpImpl.getId());
							// If the implementation is set to primary and the programming language is the same, unset primary for the implementation
							if ((set != null) && (set != implSetting) && set.isPrimary() && tmpImpl.getProgrammingLanguage().getName().equals(language)) {
								final SetCommand unSet = new SetCommand(domain, set, CodegenPackage.Literals.IMPLEMENTATION_SETTINGS__PRIMARY, false);
								updateCommand.append(unSet);
								oldSource.add(set.getOutputDir());
							}
						}

						final SetCommand cmd = new SetCommand(domain, implSetting, CodegenPackage.Literals.IMPLEMENTATION_SETTINGS__PRIMARY, true);
						updateCommand.append(cmd);

						domain.getCommandStack().execute(updateCommand);

						boolean hasNature = false;
						try {
							hasNature = (project != null) && project.hasNature(CProjectNature.C_NATURE_ID);
							hasNature |= (project != null) && project.hasNature(CCProjectNature.CC_NATURE_ID);
						} catch (final CoreException e) {
							retStatus.add(new Status(e.getStatus().getSeverity(), CplusplusUiActivator.PLUGIN_ID, "Problems checking C/C++ natures for project", e));
							return retStatus;
						}

						// Only change the build.sh if the project has the C++ nature
						if (hasNature) {
							final IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
							final IManagedProject managedProject = info.getManagedProject();
							for (final IConfiguration config : managedProject.getConfigurations()) {
								CppGeneratorUtils.configureBuilder(implSetting.getOutputDir(), config);
								CppGeneratorUtils.configureSourceFolders(oldSource, implSetting.getOutputDir(), config);
							}
						}

						return retStatus;
					} finally {
						monitor.done();
					}
				}
			};

			job.setPriority(Job.LONG);
			job.setUser(true);
			job.setSystem(false);
			job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
			job.schedule();
		}

		return null;
	}

}
