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
package gov.redhawk.ide.codegen.jet.python;

import gov.redhawk.ide.debug.ScaLauncherUtil;
import gov.redhawk.sca.launch.ScaLaunchConfigurationConstants;

import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.python.pydev.debug.ui.launching.RegularLaunchConfigurationDelegate;

/**
 * @since 8.0
 * 
 */
public class LocalPythonComponentDelegate extends RegularLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {

	public static final String ID = "gov.redhawk.ide.codegen.jet.python.launchComponent";

	@Override
	public void launch(final ILaunchConfiguration conf, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		final ILaunchConfigurationWorkingCopy workingCopy = conf.getWorkingCopy();
		insertProgramArguments(launch, workingCopy);
		super.launch(workingCopy, mode, launch, monitor);
		ScaLauncherUtil.postLaunch(launch);
	}

	protected void insertProgramArguments(final ILaunch launch, final ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		final String args = configuration.getAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, "");
		final URI spdURI = URI.createPlatformResourceURI(configuration.getAttribute(ScaLaunchConfigurationConstants.ATT_PROFILE, ""), true);
		final String scaArgs = ScaLauncherUtil.getSpdProgramArguments(spdURI, launch, configuration);
		configuration.setAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, args + " " + scaArgs);
	}

}
