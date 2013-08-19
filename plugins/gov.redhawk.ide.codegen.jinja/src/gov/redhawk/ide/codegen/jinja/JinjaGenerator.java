/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.codegen.jinja;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.utils.InputRedirector;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.NamedThreadFactory;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;

public class JinjaGenerator {

	private static final ExecutorService EXECUTOR_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory(JinjaGenerator.class.getName()));

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
		URI uri = softpkg.eResource().getURI();
		if (uri.isPlatform()) {
			final IResource resource = ModelUtil.getResource(softpkg);
			return resource.getLocation().toOSString();
		} else if (uri.isFile()) {
			return uri.toFileString();
		} else {
			try {
				IFileStore store = EFS.getStore(java.net.URI.create(uri.toString()));
				File localFile = store.toLocalFile(0, null);
				return localFile.getAbsolutePath();
			} catch (CoreException e) {
				throw new IllegalArgumentException("Unknown uri " + uri, e);
			}

		}
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

	public void generate(final ImplementationSettings implSettings, final Implementation impl, final PrintStream out, final PrintStream err,
		final IProgressMonitor monitor, final String[] generateFiles) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Generating...", IProgressMonitor.UNKNOWN);
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final ArrayList<String> arguments = new ArrayList<String>();
		final String redhawkCodegen = getCodegenFile().getPath();
		arguments.add(redhawkCodegen);

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
			if (generateFiles.length == 0) {
				// Don't inadvertently regenerate everything!
				return;
			}
			for (final String fileName : generateFiles) {
				arguments.add(prependPath(implSettings.getOutputDir(), fileName));
			}
		}
		final String[] command = arguments.toArray(new String[arguments.size()]);

		// Print the command to the console.
		for (final String arg : command) {
			out.print(arg + " ");
		}
		out.println();

		// Launch the code generator.
		// NB: The process has implicitly exited (and been cleaned up by the JVM) when
		//     standard out/error are closed, so there is no need to explicitly wait for it.
		final Process process;
		try {
			process = java.lang.Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'", e));
		}

		// In order to poll both output (with the confusing name "getInputStream") and error,
		// create a thread for each, redirecting to the respective PrintStream objects.
		final Thread outThread = new Thread(new InputRedirector(process.getInputStream(), out));
		final Thread errThread = new Thread(new InputRedirector(process.getErrorStream(), err));
		outThread.start();
		errThread.start();

		Future< ? > future = EXECUTOR_POOL.submit(new Runnable() {

			@Override
			public void run() {
				try {
					outThread.join();
				} catch (final InterruptedException e) {
					// This is highly unlikely to occur, but log it just in case.
					JinjaGeneratorPlugin.logError("Interrupted waiting for standard out", e);
				}
				try {
					errThread.join();
				} catch (final InterruptedException e) {
					// This is highly unlikely to occur, but log it just in case.
					JinjaGeneratorPlugin.logError("Interrupted waiting for standard error", e);
				}
			}
		});
		try {
			while (true) {
				try {
					future.get(2, TimeUnit.SECONDS);
					break;
				} catch (InterruptedException e) {
					// PASS
				} catch (ExecutionException e) {
					throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'",
						e.getCause()));
				} catch (TimeoutException e) {
					if (subMonitor.isCanceled()) {
						process.destroy();
						throw new OperationCanceledException();
					}
				}
			}
			while (true) {
				try {
					int retValue = process.exitValue();
					if (retValue != 0) {
						throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, command[0] + " returned with error code " + retValue,
							null));
					}
					break;
				} catch (IllegalThreadStateException e) {
					// PASS
				}
			}
		} finally {
			subMonitor.done();
		}
	}

	public HashMap<String, Boolean> list(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		final HashMap<String, Boolean> fileList = new HashMap<String, Boolean>();

		final ArrayList<String> arguments = new ArrayList<String>();
		final String redhawkCodegen = getCodegenFile().getPath();
		arguments.add(redhawkCodegen);

		// List the files that would be generated.
		arguments.add("-l");

		// Turn the settings into command-line flags.
		arguments.addAll(settingsToOptions(implSettings));

		// The SPD file is the first positional argument.
		arguments.add(getSpdFile(softpkg));

		final String[] command = arguments.toArray(new String[arguments.size()]);
		StringBuilder fullCommandList = new StringBuilder();
		// Print the command to the console.
		for (final String arg : command) {
			fullCommandList.append(arg + " ");
		}

		// Launch the code generator.
		// NB: The process has implicitly exited (and been cleaned up by the JVM) when
		//     standard out/error are closed, so there is no need to explicitly wait for it.
		Process process = null;
		try {
			process = java.lang.Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'", e));
		}

		final InputStreamReader instream = new InputStreamReader(process.getInputStream());
		final BufferedReader reader = new BufferedReader(instream);
		try {
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
			Integer exitValue = null;
			while (true) {
				try {
					exitValue = process.exitValue();
					break;
				} catch (IllegalThreadStateException e) {
					// PASS							
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					process.destroy();
					throw new OperationCanceledException();
				}
			}
			if (exitValue != null && exitValue != 0) {
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, command[0] + " returned with error code " + exitValue, null));
			}

		} catch (final IOException e) {
			throw new CoreException(
				new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception reading standard out from '" + redhawkCodegen + "'", e));
		} finally {
			try {
				reader.close();
			} catch (final IOException e) {
				// This is highly unlikely to occur, but log it just in case.
				JinjaGeneratorPlugin.logError("Exception closing standard out", e);
			}
		}
		return fileList;
	}

	public IStatus validate() {
		final File redhawkCodegen = getCodegenFile();
		if (!redhawkCodegen.exists()) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + redhawkCodegen.getPath() + "' not found");
		} else if (!redhawkCodegen.canExecute()) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + redhawkCodegen.getPath() + "' not executable");
		}
		return new Status(IStatus.OK, JinjaGeneratorPlugin.PLUGIN_ID, "Code generator '" + redhawkCodegen.getPath() + "' is installed");
	}

	private File getCodegenFile() {
		return JinjaGeneratorPlugin.getDefault().getCodegenPath().toFile();
	}
}
