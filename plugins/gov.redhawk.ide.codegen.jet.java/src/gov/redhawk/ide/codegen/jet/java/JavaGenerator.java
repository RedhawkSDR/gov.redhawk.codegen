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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.java.AbstractJavaCodeGenerator;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.idl.IdlJavaUtil;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.util.ResourceUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.EList;

public class JavaGenerator extends AbstractJavaCodeGenerator {
	/**
	 * @since 6.0
	 */
	public static final String ID = "gov.redhawk.ide.codegen.jet.java.JavaGenerator";

	/** @since 6.0 */
	public static final String EVENTCHANNEL_REPID = "IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0";
	/** @since 6.0 */
	public static final String EVENTCHANNEL_NAME = "propEvent";

	/**
	 * @since 6.0
	 */
	public static final String MESSAGING_REPID = "IDL:ExtendedEvent/MessageEvent:1.0";

	private final JMerger merger;

	public JavaGenerator() {
		this(null, null, null);
	}

	public JavaGenerator(final ImplementationSettings implSettings, final Implementation impl, final IProgressMonitor monitor) {
		super();

		// Initialize the JMerge functionality
		final JControlModel controlModel = new JControlModel();
		final IPath path = new Path("templates/common/merge.xml");
		final URL mergeFile = FileLocator.find(JavaJetGeneratorPlugin.getDefault().getBundle(), path, null);
		String uri = "";
		try {
			uri = mergeFile.toURI().toString();
			controlModel.initialize(new ASTFacadeHelper(), uri);
		} catch (final URISyntaxException e) {
			JavaJetGeneratorPlugin.logError("Unable to initialize merging capability", e);
		}
		this.merger = new JMerger(controlModel);
	}

	/**
	 * @since 4.0
	 */
	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName, PrintStream out, PrintStream err,
	        final IProgressMonitor monitor, String[] generateFiles, final List<FileToCRCMap> crcMap) throws CoreException {
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		final JavaTemplateParameter templ = new JavaTemplateParameter(impl, implSettings, getPackage(impl, implSettings));
		final SoftPkg softPkg = (SoftPkg) impl.eContainer();
		final List<String> unchangedList = this.getUnchangedFiles(implSettings, softPkg);
		final String srcDir = implSettings.getOutputDir() + "/src/" + templ.getPackage().replace('.', '/') + "/";

		// If the generateFiles list is null, find some files. If it's an empty list
		// then we just need to regenerate the java files.
		if (generateFiles == null) {
			generateFiles = this.getGeneratedFiles(implSettings, softPkg).keySet().toArray(new String[0]);
		}

		final List<String> filesList = new ArrayList<String>(Arrays.asList(generateFiles));
		filesList.addAll(unchangedList);

		// Check if this is a device (not supported yet)
		if (softPkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase())) {
			templ.setDevice(true);
		}

		// Get all the ports
		final Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
		final EList<Provides> provides = ports.getProvides();
		final EList<Uses> uses = ports.getUses();

		// We have to do the provides ports, uses ports, resource file, and then the filesList
		final int WORK_PER_PORT_FILE = 3;
		final int WORK_PER_TEMPLATE_FILE = 4;
		int workRemaining = WORK_PER_PORT_FILE * (provides.size() + uses.size() + 1) + WORK_PER_TEMPLATE_FILE * filesList.size();
		final SubMonitor progress = SubMonitor.convert(monitor, "Generating code", workRemaining);

		// Regenerate the ProvidesPort files
		final HashSet<String> genReps = new HashSet<String>();
		for (final Provides p : provides) {
			final String rep = p.getRepID();
			if (!genReps.contains(rep) && !JavaGenerator.MESSAGING_REPID.equals(rep)) {
				templ.setPortRepId(rep);
				final String file = JavaGeneratorUtils.getPortName(rep) + "InPort.java";
				final String providesPort = template.getTemplate().generateFile(file, softPkg, implSettings, templ);
				progress.worked(1);

				// Make sure we found the IDL for that port and generated some code
				if (providesPort.trim().length() == 0) {
					JavaJetGeneratorPlugin.logWarning("Unable to create source for port: " + rep, null);
					continue;
				}

				final IFile providesPortFile = project.getFile(srcDir + "/ports/" + file);
				if (!providesPortFile.exists()) {
					ResourceUtils.create(providesPortFile, progress.newChild(1));
				}

				merge(providesPort, providesPortFile, progress.newChild(1));
				genReps.add(rep);
			}

			workRemaining -= WORK_PER_PORT_FILE;
			progress.setWorkRemaining(workRemaining);
		}

		// Regenerate the UsesPort files
		genReps.clear();
		for (final Uses u : uses) {
			final String rep = u.getRepID();
			//if (!(JavaGenerator.EVENTCHANNEL_NAME.equals(u.getUsesName()) && JavaGenerator.EVENTCHANNEL_REPID.equals(rep)) && !genReps.contains(rep) && !JavaGenerator.MESSAGING_REPID.equals(rep)) {
			if (!(JavaGenerator.EVENTCHANNEL_NAME.equals(u.getUsesName()) && JavaGenerator.EVENTCHANNEL_REPID.equals(rep)) && !genReps.contains(rep)) {
				templ.setPortRepId(rep);
				final String file = JavaGeneratorUtils.getPortName(rep) + "OutPort.java";
				final String usesPort = template.getTemplate().generateFile(file, softPkg, implSettings, templ);
				progress.worked(1);

				// Make sure we found the IDL for that port and generated some code
				if (usesPort.trim().length() == 0) {
					JavaJetGeneratorPlugin.logWarning("Unable to create source for port: " + rep, null);
					continue;
				}

				final IFile usesPortFile = project.getFile(srcDir + "/ports/" + file);
				if (!usesPortFile.exists()) {
					ResourceUtils.create(usesPortFile, progress.newChild(1));
				}

				merge(usesPort, usesPortFile, progress.newChild(1));
				genReps.add(rep);
			}

			workRemaining -= WORK_PER_PORT_FILE;
			progress.setWorkRemaining(workRemaining);
		}

		// Regenerate the Resource.java file 
		String resourceJava = "";
		final String file = componentName + ".java";
		resourceJava = template.getTemplate().generateFile(file, softPkg, implSettings, templ);
		progress.worked(1);

		// Make sure something was generated for the component implementation
		if (resourceJava.trim().length() > 0) {
			final IFile compResourceJavaFile = project.getFile(srcDir + componentName + ".java");
			if (!compResourceJavaFile.exists()) {
				ResourceUtils.create(compResourceJavaFile, progress.newChild(1));
			}

			merge(resourceJava, compResourceJavaFile, progress.newChild(1));
		} else {
			JavaJetGeneratorPlugin.logWarning("Unable to create source for " + file, null);
		}
		workRemaining -= WORK_PER_PORT_FILE;
		progress.setWorkRemaining(workRemaining);

		// Generate each file in the file list
		final List<String> executableList = template.getTemplate().getExecutableFileNames(implSettings, softPkg);
		for (final String fileName : filesList) {
			progress.subTask("Generating file: " + fileName);

			// Generate file contents
			final byte[] fileBytes;
			try {
				final String fileContents = template.getTemplate().generateFile(fileName, softPkg, implSettings, templ);
				if (fileContents.trim().length() > 0) {
					fileBytes = fileContents.getBytes("UTF-8");
				} else {
					fileBytes = null;
				}
			} catch (final UnsupportedEncodingException e) {
				throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Internal Error", e));
			}
			progress.worked(1);

			if (fileBytes != null) {
				// Create/replace file in project
				final IFile newFile = project.getFile(implSettings.getOutputDir() + "/" + fileName);
				ResourceUtils.createFile(newFile, fileBytes, progress.newChild(1));

				// Set file as executable if necessary
				if (executableList.contains(fileName)) {
					ResourceUtils.runSystemCommand("chmod +x " + project.getLocation().append(implSettings.getOutputDir() + File.separator + fileName));
					progress.worked(1);
				}

				// Generate a new CRC
				updateCRC(fileName, stripNewlines(fileBytes), crcMap);
				progress.worked(1);
			} else {
				JavaJetGeneratorPlugin.logWarning("Unable to create source for " + fileName, null);
			}

			workRemaining -= WORK_PER_TEMPLATE_FILE;
			progress.setWorkRemaining(workRemaining);
		}
	}

	/**
	 * @param monitor The progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *            to call done() on the given monitor. Accepts null, indicating that no progress should be reported and
	 *            that the operation cannot be canceled.
	 */
	private void merge(final String resource, final IFile compFile, final IProgressMonitor monitor) throws CoreException {
		final int TOTAL_WORK = 4;
		final SubMonitor progress = SubMonitor.convert(monitor, TOTAL_WORK);

		this.merger.reset();

		final IFile desiredOutput = compFile.getProject().getFile(compFile.getProjectRelativePath().addFileExtension("merge"));
		try {
			this.merger.setSourceCompilationUnit(this.merger.createCompilationUnitForContents(resource));
			this.merger.setTargetCompilationUnit(this.merger.createCompilationUnitForURI(compFile.getLocationURI().toString()));
			this.merger.merge();
			progress.worked(1);

			// If we made it this far without exception, we can delete the .merge files
			if (desiredOutput.exists()) {
				desiredOutput.delete(true, progress.newChild(1));
			}
		} catch (final Exception e) {
			if (desiredOutput.exists()) {
				desiredOutput.setContents(new ByteArrayInputStream(resource.getBytes()), true, false, progress.newChild(1));
			} else {
				desiredOutput.create(new ByteArrayInputStream(resource.getBytes()), true, progress.newChild(1));
			}
			throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Failed to merge generated output. See "
			        + desiredOutput.getName() + " for generated output.", e));
		}
		progress.setWorkRemaining(1);

		final InputStream mergedContents = new ByteArrayInputStream(this.merger.getTargetCompilationUnit().getContents().getBytes());
		compFile.setContents(mergedContents, true, false, progress.newChild(1));
	}

	private String getPackage(final Implementation impl, final ImplementationSettings implSettings) {
		return JavaGeneratorProperties.getPackage((SoftPkg) impl.eContainer(), impl, implSettings);
	}

	/**
	 * @since 6.0
	 */
	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
		final Code retVal = SpdFactory.eINSTANCE.createCode();
		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		final ICodeGeneratorDescriptor codeGenDesc = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(settings.getGeneratorId());

		String outputDir = settings.getOutputDir();
		if (outputDir.length() > 0 && outputDir.charAt(0) == '/') {
			outputDir = outputDir.substring(1);
		}
		if (outputDir != null && "".equals(outputDir)) {
			outputDir = ".";
		}
		retVal.setEntryPoint(outputDir + "/startJava.sh");

		file.setName(outputDir);
		retVal.setLocalFile(file);
		retVal.setType(CodeFileType.EXECUTABLE);

		return retVal;
	}

	/**
	 * @since 7.0
	 */
	@Override
	public Set<FileStatus> getGeneratedFilesStatus(ImplementationSettings implSettings, SoftPkg softpkg) throws CoreException {
		Map<String, Boolean> result = getGeneratedFiles(implSettings, softpkg);
		Set<FileStatus> retVal = new HashSet<FileStatus>();
		for (Map.Entry<String, Boolean> entry : result.entrySet()) {
			String filename = entry.getKey();
			Boolean modified = entry.getValue();
			if (modified != null) {
				if (modified) {
					retVal.add(new FileStatus(filename, FileStatus.Action.REGEN, FileStatus.State.MODIFIED, FileStatus.Type.SYSTEM));
				} else {
					retVal.add(new FileStatus(filename, FileStatus.Action.REGEN, FileStatus.State.MATCHES, FileStatus.Type.SYSTEM));
				}
			} else {
				retVal.add(new FileStatus(filename, FileStatus.Action.REGEN, FileStatus.State.MATCHES, FileStatus.Type.SYSTEM));
			}
		}
		return retVal;
	}
	
	@Deprecated
	public HashMap<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softPkg) throws CoreException {
		final IProject project = ModelUtil.getProject(softPkg);
		final HashMap<String, Boolean> fileMap = new HashMap<String, Boolean>();
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		if (template == null) {
			throw new CoreException(new Status(IStatus.ERROR,
			        JavaJetGeneratorPlugin.PLUGIN_ID,
			        "Unable to find a code generation template. Please check your template selection under the 'Code"
			                + " Generation Details' section of the Implementation tab of your component."));
		}

		final List<String> templateFileList = template.getTemplate().getAllGeneratedFileNames(implSettings, softPkg);
		if (templateFileList != null) {
			for (final String fileName : templateFileList) {
				checkFile(implSettings, project, fileMap, null, fileName);
			}
		}

		return fileMap;
	}

	/**
	 * @since 3.0
	 */
	public List<String> getUnchangedFiles(final ImplementationSettings implSettings, final SoftPkg softPkg) throws CoreException {
		final IProject project = ModelUtil.getProject(softPkg);
		final List<String> fileList = new ArrayList<String>();
		final ITemplateDesc template = CodegenUtil.getTemplate(implSettings.getTemplate(), implSettings.getGeneratorId());
		if (template == null) {
			throw new CoreException(new Status(IStatus.ERROR,
			        JavaJetGeneratorPlugin.PLUGIN_ID,
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

	/**
	 * @since 4.0
	 */
	public IStatus validate() {
		return IdlJavaUtil.validate();
	}

	@Override
	protected String getSourceDir(Implementation impl, ImplementationSettings implSettings) {
		final String packagePath = getPackage(impl, implSettings).replace('.', File.separatorChar);
		return super.getSourceDir(impl, implSettings) + "src" + File.separator + packagePath + File.separator;
	}

}
