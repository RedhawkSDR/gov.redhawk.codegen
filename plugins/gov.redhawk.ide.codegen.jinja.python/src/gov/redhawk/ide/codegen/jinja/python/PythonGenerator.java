package gov.redhawk.ide.codegen.jinja.python;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.eclipse.core.runtime.Status;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.plugin.PydevPlugin;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.IScaComponentCodegen;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.util.ResourceUtils;
import gov.redhawk.model.sca.util.ModelUtil;

public class PythonGenerator implements IScaComponentCodegen {

	public PythonGenerator() {
		// TODO Auto-generated constructor stub
	}

	public IStatus generate(ImplementationSettings implSettings,
			Implementation impl, PrintStream out, PrintStream err,
			IProgressMonitor monitor, String[] generateFiles,
			boolean shouldGenerate, List<FileToCRCMap> crcMap) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final SoftPkg softpkg = impl.getSoftPkg();
		final IProject project = resource.getProject();
		
		final IPath workspaceRoot = project.getWorkspace().getRoot().getLocation();
		String spdFile = workspaceRoot.toOSString() + softpkg.eResource().getURI().toPlatformString(true);
		
		ArrayList<String> arguments = new ArrayList<String>();
		arguments.add("redhawk-codegen");
		arguments.add("-C");
		arguments.add(project.getLocation().toOSString());
		arguments.add("--impl");
		arguments.add(impl.getId());
		arguments.add("--impldir");
		arguments.add(implSettings.getOutputDir());
		arguments.add("--template");
		arguments.add(implSettings.getTemplate());
		for (Property property : implSettings.getProperties()) {
			arguments.add("-B"+property.getId());
			arguments.add(property.getValue());
		}
		arguments.add(spdFile);
		String[] command = arguments.toArray(new String[arguments.size()]);
		
		try {
			java.lang.Process process = java.lang.Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (final Exception e) {
			return new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID, "Generation failed");
		}
		return new Status(IStatus.OK, PythonGeneratorPlugin.PLUGIN_ID, "Generation complete");
	}

	public Code getInitialCodeSettings(SoftPkg softPkg,	ImplementationSettings settings, Implementation impl) {
		String outputDir = settings.getOutputDir();
		if (outputDir != null && !"".equals(outputDir) && outputDir.charAt(0) == '/') {
			outputDir = outputDir.substring(1);
		}
		if (outputDir != null && "".equals(outputDir)) {
			outputDir = ".";
		}

		final Code retVal = SpdFactory.eINSTANCE.createCode();
		final String prefix = softPkg.getName();
		retVal.setEntryPoint(outputDir + "/" + prefix + ".py");

		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName(outputDir);
		retVal.setLocalFile(file);
		retVal.setType(CodeFileType.EXECUTABLE);

		return retVal;
	}

	public IStatus cleanupSourceFolders(IProject project,
			IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, Boolean> getGeneratedFiles(
			ImplementationSettings implSettings, SoftPkg softpkg)
			throws CoreException {
		HashMap<String, Boolean> fileList = new HashMap<String, Boolean>();
		
		return fileList;
	}

	public boolean shouldGenerate() {
		// TODO Auto-generated method stub
		return false;
	}

	public IFile getDefaultFile(Implementation impl,
			ImplementationSettings implSettings) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStatus validate() {
		final IInterpreterManager interpreterManager = PydevPlugin.getPythonInterpreterManager();
		if (!interpreterManager.isConfigured()) {
			return new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID, "Configure the Python Interpreter before attempting code generation.");
		} else {
			return new Status(IStatus.OK, PythonGeneratorPlugin.PLUGIN_ID, "Validation ok");
		}		
	}

}
