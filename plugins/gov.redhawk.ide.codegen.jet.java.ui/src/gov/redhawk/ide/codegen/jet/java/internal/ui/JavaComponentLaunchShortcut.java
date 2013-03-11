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
package gov.redhawk.ide.codegen.jet.java.internal.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.java.JavaComponentLaunchDelegate;
import gov.redhawk.ide.codegen.jet.java.JavaGenerator;
import gov.redhawk.ide.codegen.jet.java.JavaGeneratorProperties;
import gov.redhawk.ide.codegen.ui.AbstractLaunchCodegenShortcut;
import gov.redhawk.ide.sdr.util.ScaEnvironmentUtil;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * @since 8.1
 * 
 */
public class JavaComponentLaunchShortcut extends AbstractLaunchCodegenShortcut {

	@Override
	protected ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String name, final SoftPkg spd, final Implementation impl) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(name, spd, impl);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, resource.getProject().getName());
		final ImplementationSettings settings = CodegenUtil.getImplementationSettings(impl);
		final String mainClass = JavaGeneratorProperties.getMainClass(impl, settings);
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainClass);

		// Remove classpath since this is computed elsewhere
		Map envMap = retVal.getAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, Collections.emptyMap());
		Map<String,String> newEnvMap = new HashMap<String,String>(envMap);
		newEnvMap.remove("CLASSPATH");
		retVal.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, newEnvMap);
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILaunchConfigurationType getLaunchConfigType() {
		final ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(JavaComponentLaunchDelegate.ID_JAVA_COMPONENT);
	}

	@Override
	protected String getGeneratorID() {
		return JavaGenerator.ID;
	}

}
