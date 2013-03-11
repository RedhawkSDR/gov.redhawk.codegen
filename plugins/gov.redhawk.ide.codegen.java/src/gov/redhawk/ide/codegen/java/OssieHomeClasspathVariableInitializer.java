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
package gov.redhawk.ide.codegen.java;

import gov.redhawk.ide.RedhawkIdeActivator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathVariableInitializer;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * @since 4.0
 */
public class OssieHomeClasspathVariableInitializer extends ClasspathVariableInitializer {

	public OssieHomeClasspathVariableInitializer() {

	}

	@Override
	public void initialize(final String variable) {
		String runtimeLocation = null;
		try {
			runtimeLocation = RedhawkIdeActivator.getDefault().getRuntimePath().toOSString();
		} catch (final NullPointerException e) {
			JavaGeneratorPlugin.logError(
			        "Unable to get the path for OSSIEHOME.  Failure to get the OSSIEHOME path prevents the Java Package Explorer from working.", e);
			return;
		}

		final Path runtimePath = new Path(runtimeLocation);
		try {
			JavaCore.setClasspathVariable("OSSIEHOME", runtimePath, new NullProgressMonitor());
		} catch (final JavaModelException e) {
			JavaGeneratorPlugin
			        .logError(
			                "Unable to set the runtime path for Java class variable OSSIEHOME.  Failure to define OSSIEHOME will result in the Java Package Explorer not working.",
			                e);
			return;
		}
	}
}
