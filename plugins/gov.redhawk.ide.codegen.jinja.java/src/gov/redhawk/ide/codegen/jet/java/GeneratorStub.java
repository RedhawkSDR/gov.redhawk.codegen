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

import java.io.PrintStream;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.java.AbstractJavaCodeGenerator;
import gov.redhawk.ide.codegen.jinja.java.JavaGeneratorPlugin;
import gov.redhawk.ide.idl.IdlJavaUtil;
import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

/**
 * This is a placeholder code generator for legacy 1.8 extension points to point to. It should never be invoked...
 * @since 8.0
 */
public class GeneratorStub extends AbstractJavaCodeGenerator {

	public GeneratorStub() {
	}

	@Override
	protected void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName, PrintStream out, // SUPPRESS CHECKSTYLE Arguments
		PrintStream err, IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unsupported Code Generator"));
	}

	@Override
	public Code getInitialCodeSettings(SoftPkg softPkg, ImplementationSettings settings, Implementation impl) {
		final Code retVal = SpdFactory.eINSTANCE.createCode();
		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		retVal.setLocalFile(file);
		retVal.setType(CodeFileType.EXECUTABLE);
		return retVal;
	}

	@Override
	public boolean shouldGenerate() {
		return false;
	}

	@Override
	public IStatus validate() {
		return IdlJavaUtil.validate();
	}

	@Override
	public Version getCodegenVersion() {
		return new Version("1.8");
	}
}
