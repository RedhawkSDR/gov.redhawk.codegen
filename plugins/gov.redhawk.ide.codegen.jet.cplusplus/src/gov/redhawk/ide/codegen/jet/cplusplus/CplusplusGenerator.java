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
package gov.redhawk.ide.codegen.jet.cplusplus;

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.AbstractCplusplusCodeGenerator;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.util.ResourceUtils;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

public class CplusplusGenerator extends AbstractCplusplusCodeGenerator {

	/**
	 * @since 9.0
	 */
	public static final String ID = "gov.redhawk.ide.codegen.jet.cplusplus.CplusplusGenerator";

	public CplusplusGenerator() {
		super();
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
		final PrintStream out, final PrintStream err, final IProgressMonitor monitor, String[] generateFiles, final List<FileToCRCMap> crcMap)
		throws CoreException {
		final int CREATE_DIR_WORK = 1;
		final int FILE_GEN_WORK = 98;
		final int ADD_BUILDER_WORK = 1;
		final SubMonitor progress = SubMonitor.convert(monitor, "Generating component code", CREATE_DIR_WORK + FILE_GEN_WORK + ADD_BUILDER_WORK);

		final IPath outputPath = new Path(implSettings.getOutputDir());
		final IResource outputFolder = project.findMember(outputPath);
		final SoftPkg softPkg = (SoftPkg) impl.eContainer();

		// Get the list of modified / non-existent files to be generated
		if (generateFiles == null) {
			generateFiles = this.getGeneratedFiles(implSettings, softPkg).keySet().toArray(new String[0]);
		}

		// Ensure the implementation directory exists if we need to generate files
		if ((generateFiles.length > 0) && (generateFiles[0].length() > 1)) {
			if (outputFolder == null) {
				ResourceUtils.create(outputFolder, progress.newChild(CREATE_DIR_WORK));
			}
		}
		progress.setWorkRemaining(FILE_GEN_WORK + ADD_BUILDER_WORK);

		// Prepare the template parameter
		final TemplateParameter templ = new TemplateParameter(impl, implSettings);
		if (softPkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase())) {
			templ.setDevice(true);
		}

		// Create a complete list of all files to be generated
		final List<String> filesList = new ArrayList<String>(Arrays.asList(generateFiles));
		filesList.addAll(getUnchangedFiles(implSettings, softPkg));

		// Get the template and list of executable files
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		final List<String> executableList = template.getTemplate().getExecutableFileNames(implSettings, softPkg);

		// For each file to be generated
		final int LOOP_WORK_ITEMS = 4;
		int loopWorkRemaining = filesList.size() * LOOP_WORK_ITEMS;
		final SubMonitor loopProgress = progress.newChild(FILE_GEN_WORK).setWorkRemaining(loopWorkRemaining);
		for (final String fileName : filesList) {
			loopProgress.subTask("Generating file: " + fileName);

			// Generate file contents
			final byte[] fileBytes;
			try {
				final String fileContents = template.getTemplate().generateFile(fileName, softPkg, implSettings, templ);
				fileBytes = fileContents.getBytes("UTF-8");
			} catch (final UnsupportedEncodingException e) {
				throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Internal Error", e));
			}
			loopProgress.worked(1);

			// Create/replace file in project
			final IFile newFile = project.getFile(implSettings.getOutputDir() + "/" + fileName);
			ResourceUtils.createFile(newFile, fileBytes, loopProgress.newChild(1));

			// Set file as executable if necessary
			if (executableList.contains(fileName)) {
				ResourceUtils.runSystemCommand("chmod +x " + project.getLocation().append(implSettings.getOutputDir() + File.separator + fileName));
				loopProgress.worked(1);
			}

			// Generate a new CRC
			updateCRC(fileName, fileBytes, crcMap);
			loopProgress.worked(1);

			loopWorkRemaining -= LOOP_WORK_ITEMS;
			loopProgress.setWorkRemaining(loopWorkRemaining);
		}
	}

	/**
	 * @since 9.0
	 */
	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
		final Code retVal = SpdFactory.eINSTANCE.createCode();
		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();

		String outputDir = settings.getOutputDir();
		// If outputDir has an absolute path, assume it's a project relative path
		if (outputDir != null && !"".equals(outputDir) && outputDir.charAt(0) == '/') {
			outputDir = outputDir.substring(1);
		}

		if ("".equals(outputDir)) {
			retVal.setEntryPoint(CodegenFileHelper.getPreferredFilePrefix(softPkg, settings));
			file.setName(CodegenFileHelper.getPreferredFilePrefix(softPkg, settings));
		} else {
			retVal.setEntryPoint(outputDir + "/" + CodegenFileHelper.getPreferredFilePrefix(softPkg, settings));

			file.setName(outputDir + "/" + CodegenFileHelper.getPreferredFilePrefix(softPkg, settings));
		}

		retVal.setLocalFile(file);
		retVal.setType(CodeFileType.EXECUTABLE);

		return retVal;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	/**
	 * @since 7.0
	 */
	public IStatus validate() {
		return IdlUtil.validate();
	}

}
