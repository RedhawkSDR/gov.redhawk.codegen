package gov.redhawk.ide.codegen.jinja.python;

import java.io.File;
import java.io.PrintStream;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;
import gov.redhawk.ide.codegen.python.AbstractPythonGenerator;
import gov.redhawk.ide.codegen.python.PythonGeneratorPlugin;

public class PythonGenerator extends AbstractPythonGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

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

	@Override
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
		final MultiStatus status = new MultiStatus(PythonGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Validation failed", null);
		status.add(super.validate());
		status.add(generator.validate());
		return status;
	}

	@Override
	protected void generateCode(Implementation impl,
			ImplementationSettings implSettings, IProject project,
			String componentName, PrintStream out, PrintStream err,
			IProgressMonitor monitor, String[] generateFiles,
			List<FileToCRCMap> crcMap) throws CoreException {
		generator.generate(implSettings, impl, out, err, monitor, generateFiles);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(
			ImplementationSettings implSettings, SoftPkg softpkg)
			throws CoreException {
		return generator.getGeneratedFiles(implSettings, softpkg);
	}
	
}
