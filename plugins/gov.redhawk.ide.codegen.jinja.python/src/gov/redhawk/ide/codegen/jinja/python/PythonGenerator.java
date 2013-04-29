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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.plugin.PydevPlugin;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;
import gov.redhawk.ide.codegen.python.PythonGeneratorPlugin;
import gov.redhawk.ide.codegen.python.utils.PythonGeneratorUtils;

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

	@Override
	public IStatus validate() {
		final MultiStatus status = new MultiStatus(PythonGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Validation status", null);
		status.add(super.validate());
		final IInterpreterManager interpreterManager = PydevPlugin.getPythonInterpreterManager();
		if (!interpreterManager.isConfigured()) {
			status.add(new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID, "Configure the Python Interpreter before attempting code generation."));
		} else {
			status.add(new Status(IStatus.OK, PythonGeneratorPlugin.PLUGIN_ID, "Validation ok"));
		}
		return status;
	}

	@Override
	protected IStatus configureProject(IProject project, ImplementationSettings implSettings, IProgressMonitor monitor) {
		final SubMonitor progress = SubMonitor.convert(monitor, "Configuring Python project", 2);

		// Check to see if interpreter manager is configured
		final IInterpreterManager interpreterManager = PydevPlugin.getPythonInterpreterManager();
		if (!interpreterManager.isConfigured()) {
			return new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID, "You must configure a python interpreter to generate code.");
		}

		// Add (if necessary) a Python nature to the project. 
		try {
			PythonGeneratorUtils.addPythonProjectNature(project, progress.newChild(1));
		} catch (final CoreException e) {
			return new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID,
					"Unable to determine if the project has been configured with the python nature; cannot proceed with code generation", e);
		}

		// Add the output directory to the Python source path.
		final String destinationDirectory = project.getFolder(implSettings.getOutputDir()).getFullPath().toString();
		try {
			PythonGeneratorUtils.addPythonSourcePath(project, destinationDirectory, progress.newChild(1));
		} catch (final CoreException e) {
			return new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID,
			        "Unable to set the python source path; cannot proceed with code generation", e);
		}

		return new Status(IStatus.OK, PythonGeneratorPlugin.PLUGIN_ID, "Python project configured");
	}
	
}
