package gov.redhawk.ide.codegen.java;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.AbstractCodeGenerator;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.util.ResourceUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public abstract class AbstractJavaCodeGenerator extends AbstractCodeGenerator {

	public AbstractJavaCodeGenerator() {
		super();
	}

	public String getRuntimePathLocation() {
		return RedhawkIdeActivator.getDefault().getRuntimePath().toOSString();
	}

	/**
	 * @since 5.0
	 */
	@Override
	public IStatus generate(final ImplementationSettings implSettings, final Implementation impl, final PrintStream out, final PrintStream err,
	        final IProgressMonitor monitor, final String[] generateFiles, final boolean shouldGenerate, final List<FileToCRCMap> crcMap) {
		final int STANDARD_WORK = 1;
		final int GENERATE_CODE_WORK = 30;
		final int CLEANUP_PROJECT_WORK = 2;
		final SubMonitor progress = SubMonitor.convert(monitor, "Configuring project", STANDARD_WORK + STANDARD_WORK + STANDARD_WORK + STANDARD_WORK
		        + GENERATE_CODE_WORK + CLEANUP_PROJECT_WORK);
		final MultiStatus retStatus = new MultiStatus(JavaGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Java code generation problems", null);

		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();
		final IPath destinationBinDirectory = new Path(implSettings.getOutputDir()).append("bin");
		final IPath destinationSrcDirectory = new Path(implSettings.getOutputDir()).append("src");

		final SoftPkg softPkg = (SoftPkg) impl.eContainer();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);

		softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();

		final IPath outputPath = new Path(implSettings.getOutputDir());
		final IResource outputDir = project.findMember(outputPath);
		final boolean outputExists = (outputDir != null);
		final IFolder srcDir = project.getFolder(destinationSrcDirectory);
		final boolean srcExists = srcDir.exists();
		IJavaProject javaProject = null;

		if (!outputExists) {
			try {
				ResourceUtils.create(outputDir, progress.newChild(STANDARD_WORK));
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to create implementation directory", e));
				return retStatus;
			}
		}

		progress.setWorkRemaining(STANDARD_WORK + STANDARD_WORK + GENERATE_CODE_WORK + CLEANUP_PROJECT_WORK);

		try {
			javaProject = JavaGeneratorUtils.addJavaProjectNature(project, progress.newChild(STANDARD_WORK));
		} catch (final CoreException e) {
			retStatus.add(new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add Java project nature", e));
		}

		try {
			final IPath srcPath = new Path(javaProject.getPath().toString() + "/" + destinationSrcDirectory);
			final IPath binPath = new Path(javaProject.getPath().toString() + "/" + destinationBinDirectory);
			JavaGeneratorUtils.addSourceClassPaths(javaProject, srcPath, binPath, progress.newChild(STANDARD_WORK));
		} catch (final CoreException e) {
			retStatus.add(new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add Java source path", e));
		}

		try {
			JavaGeneratorUtils.addRedhawkJavaClassPaths(javaProject, progress.newChild(STANDARD_WORK));
		} catch (final CoreException e) {
			retStatus.add(new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to add setup Java class paths", e));
		}

		if (!srcExists) {
			try {
				ResourceUtils.create(srcDir, progress.newChild(STANDARD_WORK));
			} catch (final CoreException ex) {
				retStatus.add(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to create 'src' directory", ex));
				return retStatus;
			}
		}

		if (shouldGenerate) {
			out.println("Targeting location " + project.getLocation() + "/" + implSettings.getOutputDir() + " for code generation...");

			try {
				generateCode(impl, implSettings, project, prefix, out, err, progress.newChild(GENERATE_CODE_WORK), generateFiles, crcMap);
			} catch (final CoreException ex) {
				retStatus.add(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to generate code", ex));
				return retStatus;
			}
			final IStatus status = cleanupSourceFolders(project, progress.newChild(CLEANUP_PROJECT_WORK));
			if (!status.isOK()) {
				retStatus.add(status);
				if (status.getSeverity() == IStatus.ERROR) {
					return retStatus;
				}
			}
		}

		return retStatus;
	}

	@Override
	public IStatus cleanupSourceFolders(final IProject project, final IProgressMonitor monitor) {
		final IJavaProject jp = JavaCore.create(project);
		final HashSet<IClasspathEntry> paths = new HashSet<IClasspathEntry>();
		try {
			for (final IClasspathEntry path : jp.getRawClasspath()) {
				IPath p = path.getPath();
				if (path.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					if (p.segment(0).equals(project.getFullPath().segment(0))) {
						p = p.removeFirstSegments(1);
						if (project.getFolder(p).exists()) {
							paths.add(path);
						}
					}
				} else {
					paths.add(path);
				}
			}
			jp.setRawClasspath(paths.toArray(new IClasspathEntry[paths.size()]), monitor);
		} catch (final JavaModelException e) {
			return new Status(IStatus.WARNING, JavaGeneratorPlugin.PLUGIN_ID, "Unable to adjust the list of source code folders for the project");
		}
		return new Status(IStatus.OK, JavaGeneratorPlugin.PLUGIN_ID, "Cleaned up source folders");
	}

	/**
	 * @param monitor The progress monitor to use for reporting progress to the
	 *            user. It is the caller's responsibility to call done() on the
	 *            given monitor. Accepts null, indicating that no progress
	 *            should be reported and that the operation cannot be canceled.
	 */
	protected abstract void generateCode(Implementation impl, ImplementationSettings implSettings, IProject project, String componentName, PrintStream out,
	        PrintStream err, IProgressMonitor monitor, String[] generateFiles, List<FileToCRCMap> crcMap) throws CoreException;

}
