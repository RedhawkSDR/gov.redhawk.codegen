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

import gov.redhawk.ide.codegen.AbstractCodeGenerator;
import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.IScaComponentCodegenSetup;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.GccGeneratorPlugin;
import gov.redhawk.ide.codegen.jinja.SharedLibraryJinjaGenerator;
import gov.redhawk.model.sca.util.ModelUtil;

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
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.framework.Version;

/**
 * @since 1.2
 */
public class OctaveSharedLibraryGenerator extends AbstractCodeGenerator implements IScaComponentCodegenSetup {

	public static final String ID = "gov.redhawk.ide.codegen.jinja.cplusplus.OctaveSharedLibraryGenerator";
	public static final String TEMPLATE = "redhawk.codegen.jinja.project.softPackageDependency.directory";

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
			name = outputDir;
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
	public IStatus generate(ImplementationSettings implSettings, Implementation impl, PrintStream out, PrintStream err, IProgressMonitor monitor, // SUPPRESS CHECKSTYLE Arguments
		String[] generateFiles, boolean shouldGenerate, List<FileToCRCMap> crcMap) { // SUPPRESS CHECKSTYLE Arguments
		final int CLEANUP_WORK = 1, ADD_NATURE_WORK = 1, ADD_BUILDER_WORK = 1;
		final int ADJUST_CONFIG_WORK = 90;
		final int GENERATE_CODE_WORK = 7;
		final SubMonitor progress = SubMonitor.convert(monitor, "Configuring project", CLEANUP_WORK + ADD_NATURE_WORK + ADD_NATURE_WORK + ADD_BUILDER_WORK
				+ ADJUST_CONFIG_WORK + GENERATE_CODE_WORK);
		
		final IProject project = ModelUtil.getProject(implSettings);
		if (project == null) {
			return new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, "Unable to determine project; cannot proceed with code generation", null);
		}

		final String componentName = implSettings.getName();
		final String destinationDirectory = implSettings.getOutputDir();
		final MultiStatus retStatus = new MultiStatus(GccGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Octave Shared Library code generation problems", null);

		if (shouldGenerate) {
			out.println("Targeting location " + project.getLocation() + "/" + destinationDirectory + " for code generation...");
			
			try {
				generateCode(impl, implSettings, project, componentName, out, err, progress.newChild(GENERATE_CODE_WORK), generateFiles, crcMap);
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, "Unable to generate code", e));
				return retStatus;
			}
		}

		return retStatus;
	}

	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName, // SUPPRESS
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

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.codegen.AbstractCodeGenerator#cleanupSourceFolders(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus cleanupSourceFolders(IProject project, IProgressMonitor monitor) {
		// PASS
		return Status.OK_STATUS;
	}

	@Override
	public Version getCodegenVersion() {
		return generator.getCodegenVersion();
	}
}
