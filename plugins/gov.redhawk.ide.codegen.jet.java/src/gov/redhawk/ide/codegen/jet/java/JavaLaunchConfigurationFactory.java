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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.debug.AbstractWorkspaceLaunchConfigurationFactory;
import gov.redhawk.ide.debug.ILaunchConfigurationFactory;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * @since 7.0
 * 
 */
public class JavaLaunchConfigurationFactory extends AbstractWorkspaceLaunchConfigurationFactory implements ILaunchConfigurationFactory {

	@Override
	public ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String usageName, final String implId, final SoftPkg spd) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(usageName, implId, spd);
		final Implementation impl = spd.getImplementation(implId);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, resource.getProject().getName());
		final ImplementationSettings settings = CodegenUtil.getImplementationSettings(impl);
		final String mainClass = JavaGeneratorProperties.getMainClass(impl, settings);
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainClass);
		return retVal;
	}

	public void setProgramArguments(final String progArgs, final ILaunchConfigurationWorkingCopy config) throws CoreException {
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, progArgs);
	}

	public String getProgramArguments(final ILaunchConfiguration config) throws CoreException {
		return config.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
	}

}
