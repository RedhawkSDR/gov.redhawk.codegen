package gov.redhawk.ide.codegen.jinja.java;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.java.AbstractJavaGenerator;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;
import gov.redhawk.model.sca.util.ModelUtil;

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

public class JavaGenerator extends AbstractJavaGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
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

	@Override
	public IFile getDefaultFile(final Implementation impl, final ImplementationSettings implSettings) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final SoftPkg softpkg = impl.getSoftPkg();
		final String prefix = softpkg.getName();
		final String outputDir = implSettings.getOutputDir() + File.separator + "src";
		String packagePath = "";
		for (final Property property : implSettings.getProperties()) {
			if ("java_package".equals(property.getId())) {
				packagePath = property.getValue().replace('.', File.separatorChar) + File.separator;
				break;
			}
		}
		final String defaultFilename = outputDir + File.separator + packagePath + prefix + ".java";
		return project.getFile(new Path(defaultFilename));
	}

	public IStatus validate() {
		return this.generator.validate();
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
	        final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap) throws CoreException {
		this.generator.generate(implSettings, impl, null, null, monitor, generateFiles);
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		return this.generator.getGeneratedFiles(implSettings, softpkg);
	}
}
