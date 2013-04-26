package gov.redhawk.ide.codegen.jinja.python;

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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.IScaComponentCodegen;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;

public class PythonGenerator implements IScaComponentCodegen {

	public PythonGenerator() {
		// TODO Auto-generated constructor stub
	}

	public IStatus generate(ImplementationSettings implSettings,
			Implementation impl, PrintStream out, PrintStream err,
			IProgressMonitor monitor, String[] generateFiles,
			boolean shouldGenerate, List<FileToCRCMap> crcMap) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
