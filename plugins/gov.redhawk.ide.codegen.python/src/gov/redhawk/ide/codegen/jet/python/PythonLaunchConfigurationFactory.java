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

import gov.redhawk.ide.debug.AbstractWorkspaceLaunchConfigurationFactory;
import gov.redhawk.ide.debug.ILaunchConfigurationFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.python.pydev.debug.core.Constants;

/**
 * @since 9.0
 * 
 */
@SuppressWarnings("restriction")
public class PythonLaunchConfigurationFactory extends AbstractWorkspaceLaunchConfigurationFactory implements ILaunchConfigurationFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String usageName, final String implId, final SoftPkg spd) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(usageName, implId, spd);
		final Implementation impl = spd.getImplementation(implId);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(Constants.ATTR_PROJECT, resource.getProject().getName());
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILDER_ENABLED, false);
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILD_SCOPE, "${none}");
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILDER_SCOPE, "${none}");
		retVal.setAttribute(IExternalToolConstants.ATTR_LOCATION, getLocation(impl, resource));
		retVal.setAttribute(IExternalToolConstants.ATTR_WORKING_DIRECTORY, getWorkingDirectory(impl, resource));

		Map map = retVal.getAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, (Map<String, String>) Collections.EMPTY_MAP);
		Map<String, String> newEnv = new HashMap<String, String>(map);
		newEnv.remove("PYTHONPATH");
		retVal.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, newEnv);

		return retVal;
	}

	private String getWorkingDirectory(final Implementation impl, final IFile spd) {
		return "${workspace_loc:" + spd.getParent().getFullPath().toPortableString() + "}";
	}

	private String getLocation(final Implementation impl, final IFile spd) {
		return "${workspace_loc:" + spd.getParent().getFullPath().append(new Path(impl.getCode().getEntryPoint())).toPortableString() + "}";
	}

	@Override
	public void setProgramArguments(final String progArgs, final ILaunchConfigurationWorkingCopy config) throws CoreException {
		config.setAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, progArgs);
	}

	public String getProgramArguments(final ILaunchConfiguration config) throws CoreException {
		return config.getAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, "");
	}

}
