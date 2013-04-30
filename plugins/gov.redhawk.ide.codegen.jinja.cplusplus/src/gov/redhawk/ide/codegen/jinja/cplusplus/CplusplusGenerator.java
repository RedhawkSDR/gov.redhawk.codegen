package gov.redhawk.ide.codegen.jinja.cplusplus;

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
import gov.redhawk.ide.codegen.cplusplus.AbstractCplusplusGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

public class CplusplusGenerator extends AbstractCplusplusGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	public Code getInitialCodeSettings(SoftPkg softPkg, ImplementationSettings settings, Implementation impl) {
		String outputDir = settings.getOutputDir();
		if (outputDir == null) {
			outputDir = "";
		}
		// If outputDir has an absolute path, assume it's a project relative path
		if (outputDir.startsWith("/")) {
			outputDir = outputDir.substring(1);
		}

		String entryPoint = softPkg.getName();
		if (!outputDir.isEmpty()) {
			entryPoint = outputDir + File.separator + entryPoint;
		}

		final Code code = SpdFactory.eINSTANCE.createCode();
		code.setEntryPoint(entryPoint);

		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName(entryPoint);
		code.setLocalFile(file);
		code.setType(CodeFileType.EXECUTABLE);

		return code;
	}

	public boolean shouldGenerate() {
		return true;
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
	protected void generateCode(Implementation impl,
			ImplementationSettings implSettings, IProject project,
			String componentName, IProgressMonitor monitor,
			String[] generateFiles, List<FileToCRCMap> crcMap)
			throws CoreException {
		generator.generate(implSettings, impl, null, null, monitor, generateFiles, true, crcMap);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(ImplementationSettings implSettings,
			SoftPkg softpkg)
			throws CoreException {
		return generator.getGeneratedFiles(implSettings, softpkg);
	}
}
