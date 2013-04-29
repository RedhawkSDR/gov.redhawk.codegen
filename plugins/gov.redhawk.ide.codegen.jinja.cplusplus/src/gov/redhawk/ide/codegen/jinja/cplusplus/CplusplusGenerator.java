package gov.redhawk.ide.codegen.jinja.cplusplus;

import java.io.File;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

public class CplusplusGenerator extends JinjaGenerator {

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

	public IStatus cleanupSourceFolders(IProject project,
			IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
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
		return new Status(IStatus.OK, CplusplusGeneratorPlugin.PLUGIN_ID, "Validation ok");
	}

}
