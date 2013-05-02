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

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;

import java.io.PrintStream;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * An abstract class for Java implementation generation. It handles most aspects of configuring JDT, but leaves
 * generating code files up to the sub-class.
 * @deprecated
 */
@Deprecated
public abstract class AbstractJavaGenerator extends AbstractJavaCodeGenerator {

	public AbstractJavaGenerator() {
		this(null, null, null);
	}

	public AbstractJavaGenerator(final ImplementationSettings implSettings, final Implementation impl, final IProgressMonitor monitor) {
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
	        final PrintStream out, final PrintStream err, final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap)
	        throws CoreException {
		generateCode(impl, implSettings, project, componentName, monitor, generateFiles, crcMap);
	}

	/**
	 * @param monitor The progress monitor to use for reporting progress to the
	 *            user. It is the caller's responsibility to call done() on the
	 *            given monitor. Accepts null, indicating that no progress
	 *            should be reported and that the operation cannot be canceled.
	 */
	protected abstract void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName,
	        IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException;

}
