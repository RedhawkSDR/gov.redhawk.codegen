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
package gov.redhawk.ide.codegen.jinja.java;

import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.java.AbstractJavaCodeGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public class JavaGenerator extends AbstractJavaCodeGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
		String outputDir = settings.getOutputDir();
		if (outputDir == null) {
			outputDir = "";
		} else if (outputDir.startsWith("/")) {
			outputDir = outputDir.substring(1);
		}
		if (outputDir.isEmpty()) {
			outputDir = ".";
		}

		final Code code = SpdFactory.eINSTANCE.createCode();
		code.setEntryPoint(outputDir + File.separator + "startJava.sh");

		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName(outputDir);
		code.setLocalFile(file);
		code.setType(CodeFileType.EXECUTABLE);

		return code;
	}

	public IStatus validate() {
		return this.generator.validate();
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName, final PrintStream out, final PrintStream err,
	        final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap) throws CoreException {
		this.generator.generate(implSettings, impl, out, err, monitor, generateFiles);
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}
	
	@Override
	public Set<FileStatus> getGeneratedFilesStatus(ImplementationSettings implSettings, SoftPkg softpkg) throws CoreException {
		return this.generator.list(implSettings, softpkg);
	}

}
