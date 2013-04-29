package gov.redhawk.ide.codegen.jinja.python;

import java.io.File;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.plugin.PydevPlugin;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

public class PythonGenerator extends JinjaGenerator {

	public PythonGenerator() {
		// TODO Auto-generated constructor stub
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

	public boolean shouldGenerate() {
		return true;
	}

	public IFile getDefaultFile(Implementation impl, ImplementationSettings implSettings) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final SoftPkg softpkg = impl.getSoftPkg();
		final String prefix = softpkg.getName();
		final String defaultFilename = implSettings.getOutputDir() + File.separator + prefix + ".py";
	    return project.getFile(new Path(defaultFilename));
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
