package gov.redhawk.ide.codegen.jinja.java;

import java.io.File;
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.java.AbstractJavaGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

public class JavaGenerator extends AbstractJavaGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	public Code getInitialCodeSettings(SoftPkg softPkg, ImplementationSettings settings, Implementation impl) {
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

	public IFile getDefaultFile(Implementation impl,
			ImplementationSettings implSettings) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStatus validate() {
		return generator.validate();
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	protected void generateCode(Implementation impl,
			ImplementationSettings implSettings, IProject project,
			String componentName, IProgressMonitor monitor,
			String[] generateFiles, List<FileToCRCMap> crcMap)
			throws CoreException {
		generator.generate(implSettings, impl, null, null, monitor, generateFiles);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(
			ImplementationSettings implSettings, SoftPkg softpkg)
			throws CoreException {
		return generator.getGeneratedFiles(implSettings, softpkg);
	}
}
