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
package gov.redhawk.ide.codegen.cplusplus;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;

import java.io.PrintStream;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * An abstract class for C/C++ implementation generation. It handles most aspects of configuring CDT, but leaves
 * generating code files up to the sub-class.
 * @deprecated Use Jinja
 */
@Deprecated
public abstract class AbstractCplusplusGenerator extends AbstractCplusplusCodeGenerator {

	public AbstractCplusplusGenerator() {
	}

	@Override
	protected void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName, // SUPPRESS CHECKSTYLE Arguments
	        PrintStream out, PrintStream err, IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException {
		generateCode(impl, implSettings, project, componentName, monitor, generateFiles, crcMap);
	}

	/**
	 * Generates code files for the implementation
	 * 
	 * @param monitor The progress monitor to use for reporting progress to the
	 *            user. It is the caller's responsibility to call done() on the
	 *            given monitor. Accepts <code>null</code>, indicating that no
	 *            progress should be reported and that the operation cannot be
	 *            canceled.
	 * @throws CoreException A problem occurs while generating code files for the implementation
	 * @since 5.0
	 */
	protected abstract void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName,
	        IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException;

}
