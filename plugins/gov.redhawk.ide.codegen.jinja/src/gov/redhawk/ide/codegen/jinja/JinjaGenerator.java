package gov.redhawk.ide.codegen.jinja;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.utils.InputRedirector;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class JinjaGenerator {

	private static final String EXECUTABLE_NAME = "redhawk-codegen";

	private List<String> settingsToOptions(final ImplementationSettings implSettings) {
		final List<String> arguments = new ArrayList<String>();
		arguments.add("--impl");
		arguments.add(implSettings.getId());
		arguments.add("--impldir");
		arguments.add(implSettings.getOutputDir());
		arguments.add("--template");
		arguments.add(implSettings.getTemplate());
		for (final Property property : implSettings.getProperties()) {
			arguments.add("-B" + property.getId() + "=" + property.getValue());
		}

		return arguments;
	}

	private String getSpdFile(final SoftPkg softpkg) {
		final IResource resource = ModelUtil.getResource(softpkg);
		final IProject project = resource.getProject();
		final IPath workspaceRoot = project.getWorkspace().getRoot().getLocation();
		return workspaceRoot.toOSString() + softpkg.eResource().getURI().toPlatformString(true);
	}

	private String relativePath(final String dir, final String path) {
		final String prefix = dir + File.separator;
		if (path.startsWith(prefix)) {
			return path.substring(prefix.length());
		} else {
			return ".." + File.separator + path;
		}
	}

	private String prependPath(final String path, final String filename) {
		if (filename.startsWith(".." + File.separator)) {
			return filename.substring(3);
		} else {
			return path + File.separator + filename;
		}
	}

	public IStatus generate(final ImplementationSettings implSettings, final Implementation impl, final PrintStream out, final PrintStream err,
	        final IProgressMonitor monitor, final String[] generateFiles) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final ArrayList<String> arguments = new ArrayList<String>();
		arguments.add(JinjaGenerator.EXECUTABLE_NAME);

		// Force overwrite of existing files; we assume that the user has already signed off on this. 
		arguments.add("-f");

		// Set base output directory to the project location.
		arguments.add("-C");
		arguments.add(project.getLocation().toOSString());

		// Turn the settings into command-line flags.
		arguments.addAll(settingsToOptions(implSettings));

		// The SPD file is the first positional argument.
		arguments.add(getSpdFile(impl.getSoftPkg()));

		// If a file list was specified, add it to the command arguments.
		if (generateFiles != null) {
			for (final String fileName : generateFiles) {
				arguments.add(prependPath(implSettings.getOutputDir(), fileName));
			}
		}
		final String[] command = arguments.toArray(new String[arguments.size()]);

		try {
			final java.lang.Process process = java.lang.Runtime.getRuntime().exec(command);
			Thread outThread = null;
			Thread errThread = null;
			if (out != null) {
				// Print the command to the console.
				for (final String arg : command) {
					out.print(arg + " ");
				}
				out.println();

				outThread = new Thread(new InputRedirector(process.getInputStream(), out));
				outThread.start();
			}
			if (err != null) {
				errThread = new Thread(new InputRedirector(process.getErrorStream(), err));
				errThread.start();
			}
			process.waitFor();
			if (outThread != null) {
				outThread.join();
			}
			if (errThread != null) {
				errThread.join();
			}
		} catch (final Exception e) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Generation failed");
		}
		return new Status(IStatus.OK, JinjaGeneratorPlugin.PLUGIN_ID, "Generation complete");
	}

	public HashMap<String, Boolean> list(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		final HashMap<String, Boolean> fileList = new HashMap<String, Boolean>();

		final ArrayList<String> arguments = new ArrayList<String>();
		arguments.add(JinjaGenerator.EXECUTABLE_NAME);

		// List the files that would be generated.
		arguments.add("-l");

		// Turn the settings into command-line flags.
		arguments.addAll(settingsToOptions(implSettings));

		// The SPD file is the first positional argument.
		arguments.add(getSpdFile(softpkg));

		final String[] command = arguments.toArray(new String[arguments.size()]);

		try {
			final java.lang.Process process = java.lang.Runtime.getRuntime().exec(command);
			final InputStreamReader instream = new InputStreamReader(process.getInputStream());
			final BufferedReader reader = new BufferedReader(instream);
			String fileName;
			while ((fileName = reader.readLine()) != null) {
				// Adjust the path of the output to be relative to the output directory.
				fileName = relativePath(implSettings.getOutputDir(), fileName);

				// Check for a trailing asterisk denoting changes.
				final boolean changed = fileName.endsWith("*");
				if (changed) {
					fileName = fileName.substring(0, fileName.length() - 1);
				}
				fileList.put(fileName, !changed);
			}
		} catch (final Exception e) {
			return null;
		}
		return fileList;
	}

	public IStatus validate() {
		final IPath ossiehome = RedhawkIdeActivator.getDefault().getRuntimePath();
		final File codegen = ossiehome.append("bin").append(JinjaGenerator.EXECUTABLE_NAME).toFile();
		if (!codegen.exists()) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + JinjaGenerator.EXECUTABLE_NAME + "' not found");
		} else if (!codegen.canExecute()) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + JinjaGenerator.EXECUTABLE_NAME + "' not executable");
		}
		return new Status(IStatus.OK, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + JinjaGenerator.EXECUTABLE_NAME + "' is installed");
	}
}
