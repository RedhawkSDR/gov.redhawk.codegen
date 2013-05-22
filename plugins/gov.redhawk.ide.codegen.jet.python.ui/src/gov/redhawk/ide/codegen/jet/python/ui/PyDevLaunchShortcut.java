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
package gov.redhawk.ide.codegen.jet.python.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import gov.redhawk.ide.codegen.jet.python.LocalPythonComponentDelegate;
import gov.redhawk.ide.codegen.jet.python.PythonGenerator;
import gov.redhawk.ide.codegen.ui.AbstractLaunchCodegenShortcut;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.python.pydev.debug.core.Constants;

/**
 * @since 8.0
 */
public class PyDevLaunchShortcut extends AbstractLaunchCodegenShortcut {

	@Override
	protected ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String name, final SoftPkg spd, final Implementation impl) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(name, spd, impl);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(Constants.ATTR_PROJECT, resource.getProject().getName());
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILDER_ENABLED, false);
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILD_SCOPE, "${none}");
		retVal.setAttribute(IExternalToolConstants.ATTR_BUILDER_SCOPE, "${none}");
		retVal.setAttribute(IExternalToolConstants.ATTR_LOCATION, getLocation(impl, resource));
		retVal.setAttribute(IExternalToolConstants.ATTR_WORKING_DIRECTORY, getWorkingDirectory(impl, resource));
		
		Map map = retVal.getAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, Collections.emptyMap());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILaunchConfigurationType getLaunchConfigType() {
		final ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(LocalPythonComponentDelegate.ID);
	}

	@Override
	protected String getGeneratorID() {
		return PythonGenerator.ID;
	}

}
