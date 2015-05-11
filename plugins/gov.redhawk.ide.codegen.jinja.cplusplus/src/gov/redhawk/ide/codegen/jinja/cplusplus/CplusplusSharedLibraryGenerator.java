/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.codegen.jinja.cplusplus;

import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.IScaComponentCodegenSetup;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.AbstractCplusplusCodeGenerator;
import gov.redhawk.ide.codegen.jinja.SharedLibraryJinjaGenerator;

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
import org.osgi.framework.Version;

/**
 * @since 1.2
 */
public class CplusplusSharedLibraryGenerator extends AbstractCplusplusCodeGenerator implements IScaComponentCodegenSetup {

	public static final String ID = "gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusSharedLibraryGenerator";
	public static final String TEMPLATE = "redhawk.codegen.jinja.cpp.library";

	private final SharedLibraryJinjaGenerator generator = new SharedLibraryJinjaGenerator();

	@Override
	public Code getInitialCodeSettings(SoftPkg softPkg, ImplementationSettings settings, Implementation impl) {
		String outputDir = settings.getOutputDir();
		if (outputDir == null) {
			outputDir = "";
		}
		// If outputDir has an absolute path, assume it's a project relative path
		if (outputDir.startsWith("/")) {
			outputDir = outputDir.substring(1);
		}

		String name = softPkg.getName();
		if (!outputDir.isEmpty()) {
			name = outputDir + File.separator + "lib";
		}
		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName(name);

		final Code code = SpdFactory.eINSTANCE.createCode();
		code.setLocalFile(file);
		code.setType(CodeFileType.SHARED_LIBRARY);

		return code;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	public IStatus validate() {
		return this.generator.validate();
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName, // SUPPRESS CHECKSTYLE Arguments
		final PrintStream out, final PrintStream err, final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap)
		throws CoreException {

		this.generator.generateFiles(implSettings, impl, monitor, generateFiles, out, err);
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Override
	public Set<FileStatus> getGeneratedFilesStatus(ImplementationSettings implSettings, SoftPkg softpkg) throws CoreException {
		return this.generator.list(implSettings, softpkg);
	}

	@Override
	public void checkSystem(IProgressMonitor monitor, String templateId) throws CoreException {
		this.generator.checkSystem(monitor, ID, templateId);
	}

	@Override
	public Version getCodegenVersion() {
		return generator.getCodegenVersion();
	}
}
