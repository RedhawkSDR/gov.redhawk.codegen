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
import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.AbstractCplusplusGenerator;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.util.ResourceUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

public class CplusplusGenerator extends AbstractCplusplusGenerator {

	/**
	 * @since 9.0
	 */
	public static final String ID = "gov.redhawk.ide.codegen.jet.cplusplus.CplusplusGenerator";

	public CplusplusGenerator() {
		super();
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
	        final IProgressMonitor monitor, String[] generateFiles, final List<FileToCRCMap> crcMap) throws CoreException {
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
			updateCRC(fileName, stripNewlines(fileBytes), crcMap);
			loopProgress.worked(1);

			loopWorkRemaining -= LOOP_WORK_ITEMS;
			loopProgress.setWorkRemaining(loopWorkRemaining);
		}

		// Add our auto-inclusion builder
		addAutoInclusionBuilder(project, progress.newChild(ADD_BUILDER_WORK));
	}

	/**
	 * Ensures the the auto-inclusion builder is added to a project.
	 * 
	 * @param project The project to add the auto-inclusion builder to
	 * @param progress The progress monitor to use for reporting progress to the
	 *            user. It is the caller's responsibility to call done() on the
	 *            given monitor. Accepts <code>null</code>, indicating that no
	 *            progress should be reported and that the operation cannot be
	 *            canceled.
	 * @throws CoreException There is a problem adding the builder to the project
	 */
	private void addAutoInclusionBuilder(final IProject project, final IProgressMonitor progress) throws CoreException {
		final ICommand[] oldBuildCommands = project.getDescription().getBuildSpec();
		for (final ICommand buildCommand : oldBuildCommands) {
			if (buildCommand.getBuilderName().equals(CplusplusBuilder.BUILDER_NAME)) {
				return;
			}
		}
		final IProjectDescription description = project.getDescription();
		final ICommand newBuildCommand = description.newCommand();
		newBuildCommand.setBuilderName(CplusplusBuilder.BUILDER_NAME);
		final ICommand[] newBuildCommands = new ICommand[oldBuildCommands.length + 1];
		System.arraycopy(oldBuildCommands, 0, newBuildCommands, 1, oldBuildCommands.length);
		newBuildCommands[0] = newBuildCommand;
		description.setBuildSpec(newBuildCommands);
		project.setDescription(description, progress);
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
	public HashMap<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softPkg) throws CoreException {
		final IProject project = ModelUtil.getProject(softPkg);
		final HashMap<String, Boolean> fileMap = new HashMap<String, Boolean>();
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		if (template == null) {
			throw new CoreException(new Status(IStatus.ERROR,
			        CplusplusJetGeneratorPlugin.PLUGIN_ID,
			        "Unable to find code generation template. Please check your template selection under the 'Code"
			                + " Generation Details' section of the Implementation tab of your component."));
		}

		final List<String> templateFileList = template.getTemplate().getAllGeneratedFileNames(implSettings, softPkg);
		if (templateFileList != null) {
			for (final String fileName : templateFileList) {
				if (project != null) {
					checkFile(implSettings, project, fileMap, null, fileName);
				} else {
					fileMap.put(fileName, true);
				}
			}
		}

		return fileMap;
	}

	/**
	 * Gets the list of files generated for the {@link Implementation} which are
	 * unmodified.
	 * 
	 * @param implSettings The {@link ImplementationSettings} for the
	 *            {@link Implementation}
	 * @param softPkg The {@link SoftPkg} for the {@link Implementation}
	 * @throws CoreException A problem occurs while getting the generated file
	 *             list
	 * @since 5.0
	 */
	public List<String> getUnchangedFiles(final ImplementationSettings implSettings, final SoftPkg softPkg) throws CoreException {
		final IProject project = ModelUtil.getProject(softPkg);
		final List<String> fileList = new ArrayList<String>();
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		if (template == null) {
			throw new CoreException(new Status(IStatus.ERROR,
			        CplusplusJetGeneratorPlugin.PLUGIN_ID,
			        "Unable to find code generation template. Please check your template selection under the 'Code"
			                + " Generation Details' section of the Implementation tab of your component."));
		}

		final List<String> templateFileList = template.getTemplate().getAllGeneratedFileNames(implSettings, softPkg);
		if (templateFileList != null) {
			for (final String fileName : templateFileList) {
				checkFile(implSettings, project, null, fileList, fileName);
			}
		}

		return fileList;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	public IFile getDefaultFile(final Implementation impl, final ImplementationSettings implSettings) {
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		IFile file = null;

		try {
			final IScaComponentCodegenTemplate temp = template.getTemplate();
			final String srcDir = implSettings.getOutputDir() + "/";
			file = super.getDefaultFile(impl, implSettings, temp.getDefaultFilename((SoftPkg) impl.eContainer(), implSettings, srcDir));
		} catch (final CoreException c) {
			// PASS
		}

		return file;
	}

	/**
	 * @since 7.0
	 */
	public IStatus validate() {
		return IdlUtil.validate();
	}

}
