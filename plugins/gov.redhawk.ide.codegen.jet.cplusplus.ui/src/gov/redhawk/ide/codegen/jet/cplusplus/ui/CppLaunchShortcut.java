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
package gov.redhawk.ide.codegen.jet.cplusplus.ui;

import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusGenerator;
import gov.redhawk.ide.codegen.jet.cplusplus.LocalCppCDILaunchDelegate;
import gov.redhawk.ide.codegen.ui.AbstractLaunchCodegenShortcut;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.internal.ui.launch.CApplicationLaunchShortcut;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut;

/**
 * 
 */
public class CppLaunchShortcut extends AbstractLaunchCodegenShortcut implements ILaunchShortcut {

	private static class InternalShortcut extends CApplicationLaunchShortcut {
		@Override
		public ILaunchConfiguration findLaunchConfiguration(final IBinary bin, final String mode) {
			return super.findLaunchConfiguration(bin, mode);
		}
	}

	@Override
	protected ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String name, final SoftPkg spd, final Implementation impl) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(name, spd, impl);
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
	protected String getGeneratorID() {
		return CplusplusGenerator.ID;
	}

	@Override
	protected ILaunchConfigurationType getLaunchConfigType() {
		final ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(LocalCppCDILaunchDelegate.ID);
	}

}
