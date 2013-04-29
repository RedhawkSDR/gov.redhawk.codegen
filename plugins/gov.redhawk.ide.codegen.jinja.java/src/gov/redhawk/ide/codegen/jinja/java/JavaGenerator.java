package gov.redhawk.ide.codegen.jinja.java;

import java.io.File;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.java.JavaGeneratorPlugin;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;

public class JavaGenerator extends JinjaGenerator {

	public JavaGenerator() {
		// TODO Auto-generated constructor stub
	}

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

	@Override
	protected IStatus configureProject(IProject project, ImplementationSettings implSettings, IProgressMonitor monitor) {
		final int CONFIGURE_STEPS = 3;
		final int DEFAULT_WORK = 1;
		final MultiStatus status = new MultiStatus(JavaGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Java code generation problems", null);
		SubMonitor progress = SubMonitor.convert(monitor, CONFIGURE_STEPS);
		IJavaProject javaProject = null;
		try {
			javaProject = JavaGeneratorUtils.addJavaProjectNature(project, progress.newChild(DEFAULT_WORK));
		} catch (final CoreException e) {
			status.add(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add Java project nature", e));
			return status;
		}

		final IPath destinationBinDirectory = new Path(implSettings.getOutputDir()).append("bin");
		final IPath destinationSrcDirectory = new Path(implSettings.getOutputDir()).append("src");
		try {
			final IPath srcPath = new Path(javaProject.getPath().toString() + "/" + destinationSrcDirectory);
			final IPath binPath = new Path(javaProject.getPath().toString() + "/" + destinationBinDirectory);
			JavaGeneratorUtils.addSourceClassPaths(javaProject, srcPath, binPath, progress.newChild(DEFAULT_WORK));
		} catch (final CoreException e) {
			status.add(new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add Java source path", e));
		}

		try {
			JavaGeneratorUtils.addRedhawkJavaClassPaths(javaProject, progress.newChild(DEFAULT_WORK));
		} catch (final CoreException e) {
			status.add(new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add setup Java class paths", e));
		}

		return status;
	}
}
