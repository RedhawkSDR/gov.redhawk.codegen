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

import gov.redhawk.ide.debug.ScaLauncherUtil;
import gov.redhawk.sca.launch.ScaLaunchConfigurationConstants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

/**
 * @since 6.0
 * 
 */
public class JavaComponentLaunchDelegate extends JavaLaunchDelegate {

	public static final String ID_JAVA_COMPONENT = "gov.redhawk.ide.codegen.jet.java.launching.localJavaComponent";

	//	@Override
	//	public String getProgramArguments(final ILaunchConfiguration configuration) throws CoreException {
	//		String retVal = super.getProgramArguments(configuration);
	//		if (retVal == null) {
	//			retVal = "";
	//		}
	//		final String args = ScaLauncherUtil.getProgramArguments(null, configuration);
	//		return retVal + " " + args;
	//	}

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		final String arguments = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
		final ILaunchConfigurationWorkingCopy copy = configuration.getWorkingCopy();
		final URI spdURI = URI.createPlatformResourceURI(configuration.getAttribute(ScaLaunchConfigurationConstants.ATT_PROFILE, ""), true);
		final String args = ScaLauncherUtil.getSpdProgramArguments(spdURI, launch, configuration);
		copy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, arguments + " " + args);
		super.launch(copy, mode, launch, monitor);
		ScaLauncherUtil.postLaunch(launch);
	}

}
