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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.java.AbstractJavaGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;
import gov.redhawk.model.sca.util.ModelUtil;

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
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final SoftPkg softpkg = impl.getSoftPkg();
		final String prefix = softpkg.getName();
		final String outputDir = implSettings.getOutputDir() + File.separator + "src";
		String packagePath = "";
		for (Property property : implSettings.getProperties()) {
			if ("java_package".equals(property.getId())) {
				packagePath = property.getValue().replace('.', File.separatorChar) + File.separator;
				break;
			}
		}
		final String defaultFilename = outputDir + File.separator + packagePath + prefix + ".java";
		return project.getFile(new Path(defaultFilename));
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
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(
			ImplementationSettings implSettings, SoftPkg softpkg)
			throws CoreException {
		return generator.getGeneratedFiles(implSettings, softpkg);
	}
}
