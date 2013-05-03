package gov.redhawk.ide.codegen.jinja.cplusplus;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.AbstractCplusplusCodeGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public class CplusplusGenerator extends AbstractCplusplusCodeGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
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

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	public IStatus validate() {
		return this.generator.validate();
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
	        final PrintStream out, final PrintStream err, final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap)
	        throws CoreException {
		this.generator.generate(implSettings, impl, out, err, monitor, generateFiles);
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		return this.generator.list(implSettings, softpkg);
	}
}
