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
package gov.redhawk.ide.codegen.jet.java;

import gov.redhawk.ide.debug.SpdLauncherUtil;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

/**
 * Used when a Java component, device, etc. in the workspace is launched in the sandbox.
 * @since 6.0
 */
public class JavaComponentLaunchDelegate extends JavaLaunchDelegate {

	public static final String ID_JAVA_COMPONENT = "gov.redhawk.ide.codegen.jet.java.launching.localJavaComponent";

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		final int WORK_LAUNCH = 10;
		final int WORK_POST_LAUNCH = 100;
		SubMonitor subMonitor = SubMonitor.convert(monitor, WORK_LAUNCH + WORK_POST_LAUNCH);

		final String arguments = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
		final ILaunchConfigurationWorkingCopy copy = configuration.getWorkingCopy();
		final SoftPkg spd = SpdLauncherUtil.getSpd(configuration);
		final String args = SpdLauncherUtil.insertProgramArguments(spd, arguments, launch, configuration);
		copy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, args);

		try {
			super.launch(copy, mode, launch, subMonitor.newChild(WORK_LAUNCH));
			SpdLauncherUtil.postLaunch(spd, copy, mode, launch, subMonitor.newChild(WORK_POST_LAUNCH));
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
	}

}
