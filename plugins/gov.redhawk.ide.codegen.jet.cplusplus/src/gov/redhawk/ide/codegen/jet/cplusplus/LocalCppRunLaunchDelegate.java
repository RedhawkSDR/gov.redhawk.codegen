/**
 * This file is protected by Copyright.
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 */
package gov.redhawk.ide.codegen.jet.cplusplus;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.internal.LocalRunLaunchDelegate;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import gov.redhawk.ide.debug.LocalScaWaveform;
import gov.redhawk.ide.debug.ScaDebugLaunchConstants;
import gov.redhawk.ide.debug.ScaDebugPlugin;
import gov.redhawk.ide.debug.SpdLauncherUtil;
import gov.redhawk.ide.debug.internal.ComponentLaunch;
import gov.redhawk.ide.debug.internal.ComponentProgramLaunchUtils;
import gov.redhawk.ide.debug.variables.LaunchVariables;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

/**
 * Used when a C++ component, device, etc. in the <b>workspace</b> is launched in the sandbox in <b>run</b> mode.
 * @since 11.0
 */
@SuppressWarnings("restriction")
public class LocalCppRunLaunchDelegate extends LocalRunLaunchDelegate {

	public static final String ID = "gov.redhawk.ide.codegen.jet.cplusplus.launchComponentApplication";

	public LocalCppRunLaunchDelegate() {
	}

	@Override
	public void launch(final ILaunchConfiguration config, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		// Validate all XML before doing anything else
		final SoftPkg spd = SpdLauncherUtil.getSpd(config);
		IStatus status = SpdLauncherUtil.validateAllXML(spd);
		if (!status.isOK()) {
			throw new CoreException(status);
		}

		final ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
		insertProgramArguments(spd, launch, workingCopy);

		String implID = config.getAttribute(ScaDebugLaunchConstants.ATT_IMPL_ID, (String) null);
		final Implementation impl = spd.getImplementation(implID);

		try {
			// Shared address space components must be launched within a component host
			if (SoftPkg.Util.isContainedComponent(impl)) {

				// Default to sandbox waveform
				LocalScaWaveform waveform = ScaDebugPlugin.getInstance().getLocalSca().getSandboxWaveform();

				String waveformName = config.getAttribute(LaunchVariables.WAVEFORM_NAME, (String) null);
				for (LocalScaWaveform wf : ScaDebugPlugin.getInstance().getLocalSca().getWaveforms()) {
					if (wf.getName().equals(waveformName)) {
						waveform = wf;
					}
				}

				ComponentProgramLaunchUtils.launch(waveform, workingCopy, launch, spd, impl, mode, monitor);
			} else {
				// Legacy launch behavior for non-shared address space components and all other resource types
				final int WORK_LAUNCH = 10, WORK_POST_LAUNCH = 100;
				SubMonitor subMonitor = SubMonitor.convert(monitor, WORK_LAUNCH + WORK_POST_LAUNCH);
				super.launch(workingCopy, mode, launch, subMonitor.newChild(WORK_LAUNCH));
				SpdLauncherUtil.postLaunch(spd, workingCopy, mode, launch, subMonitor.newChild(WORK_POST_LAUNCH));
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
	}

	/**
	 * @since 10.0
	 */
	protected void insertProgramArguments(final SoftPkg spd, final ILaunch launch, final ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		final String args = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "");

		final String newArgs = SpdLauncherUtil.insertProgramArguments(spd, args, launch, configuration);
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, newArgs);
	}

	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		super.getLaunch(configuration, mode);
		return new ComponentLaunch(configuration, mode, null);
	}
}
