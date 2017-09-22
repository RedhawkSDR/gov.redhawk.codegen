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

package gov.redhawk.ide.codegen.jet.cplusplus;

import gov.redhawk.ide.debug.AbstractWorkspaceLaunchConfigurationFactory;
import gov.redhawk.ide.debug.ILaunchConfigurationFactory;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

/**
 * @since 10.0
 * 
 */
public class CppLaunchConfigurationFactory extends AbstractWorkspaceLaunchConfigurationFactory implements ILaunchConfigurationFactory {

	@Override
	public ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String usageName, final String implId, final SoftPkg spd) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(usageName, implId, spd);
		final Implementation impl = spd.getImplementation(implId);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH,
		        ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_USE_WORKSPACE_SETTING);
		retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, impl.getCode().getEntryPoint());
		retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, resource.getProject().getName());
		retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false);
		final ICProjectDescription projDes = CCorePlugin.getDefault().getProjectDescription(resource.getProject());
		if (projDes != null) {
			final String buildConfigID = projDes.getActiveConfiguration().getId();
			retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, buildConfigID);
		} else {
			retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, "");
		}
		retVal.setAttribute(ICDTLaunchConfigurationConstants.ATTR_USE_TERMINAL, true);
		return retVal;
	}

	@Override
	public void setProgramArguments(final String progArgs, final ILaunchConfigurationWorkingCopy config) throws CoreException {
		config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, progArgs);
	}

	public String getProgramArguments(final ILaunchConfiguration config) throws CoreException {
		return config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "");
	}
}
