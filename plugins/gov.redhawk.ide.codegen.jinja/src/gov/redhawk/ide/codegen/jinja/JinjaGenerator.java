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

import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileStatus.Action;
import gov.redhawk.ide.codegen.FileStatus.State;
import gov.redhawk.ide.codegen.FileStatus.Type;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.utils.InputRedirector;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.sca.util.Debug;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Version;

/** REDHAWK Core Framework (1.9+ Jinja based) Code Generator (i.e. redhawk-codegen). */
public class JinjaGenerator {
	static final Pattern VERSION_REGEX = Pattern.compile("\\d+(\\.\\d+(\\.\\d+(\\.\\S+)?)?)?$");
	static final String[] EMPTY_STRING_ARRAY = new String[0];
	static final Version VERSION_THAT_GENERATES_GOOD_BUILD_SH = new Version(1, 10, 1);
	static final Version VERSION_WITH_CHECK_TEMPLATE = new Version(1, 11, 0);

	private static final Debug DEBUG = new Debug(JinjaGeneratorPlugin.PLUGIN_ID, "command");

	private static final ExecutorService EXECUTOR_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory(JinjaGenerator.class.getName()));

	/** current version of the REDHAWK (core framework) code generator */
	private Version codegenVersion = null;

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
	
	private String commandToString(String [] command) {
		StringBuilder builder = new StringBuilder();
		for (String s : command) {
			builder.append(s).append(' ');
		}
		return builder.toString();
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
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja Generate Command:\n  {0}", commandToString(command));
			}
			process = java.lang.Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'", e));
		}

		// In order to poll both output (with the confusing name "getInputStream") and error,
		// create a thread for each, redirecting to the respective PrintStream objects.
		final Thread outThread = new Thread(new InputRedirector(process.getInputStream(), out));
		final Thread errThread = new Thread(new InputRedirector(process.getErrorStream(), err));
		outThread.setDaemon(true);
		errThread.setDaemon(true);
		outThread.start();
		errThread.start();

		Future< Integer > future = EXECUTOR_POOL.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
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
				return process.waitFor();
			}
		});
		try {
			while (true) {
				try {
					int retValue = future.get(2, TimeUnit.SECONDS);
					if (retValue != 0) {
						throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, command[0] + " returned with error code " + retValue
							+ "\n\nSee console output for details.", null));
					}
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
		} finally {
			subMonitor.done();
		}
	}

	public Set<FileStatus> list(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		final Set<FileStatus> fileList = new HashSet<FileStatus>();

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
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja List Command:\n  {0}", commandToString(command));
			}
			process = java.lang.Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'", e));
		}

		final InputStreamReader instream = new InputStreamReader(process.getInputStream());
		final BufferedReader reader = new BufferedReader(instream);
		try {
			String fileName;
			while ((fileName = reader.readLine()) != null) {
				// Strip attributes from file name.
				final String attrs = fileName.substring(0, 4);
				fileName = fileName.substring(5);

				// Adjust the path of the output to be relative to the output directory.
				fileName = relativePath(implSettings.getOutputDir(), fileName);

				// Parse attributes.
				final boolean changed = attrs.contains("M");
				final boolean user = attrs.contains("U");
				final boolean added = attrs.contains("A");
				final boolean deleted = attrs.contains("D");
				State state;
				if (changed) {
					state = State.MODIFIED;
				} else {
					state = State.MATCHES;
				}
				
				Action desiredAction;
				if (added) {
					desiredAction = FileStatus.Action.ADDING;
				} else if (deleted) {
					desiredAction = FileStatus.Action.REMOVING;
				} else {
					desiredAction = FileStatus.Action.REGEN;
				}
				
				Type type;
				if (user) {
					type = FileStatus.Type.USER;
				} else {
					type = FileStatus.Type.SYSTEM;
				}
				
				FileStatus status = new FileStatus(fileName, desiredAction, state, type);
				fileList.add(status);
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
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					process.destroy();
					throw new OperationCanceledException();
				}
			}
			if (exitValue != null && exitValue != 0) {
				StringBuilder log = new StringBuilder();
				final InputStreamReader errStream = new InputStreamReader(process.getErrorStream());
				final BufferedReader errBuffer = new BufferedReader(errStream);
				try {
					for (String errLine = errBuffer.readLine(); errLine != null; errLine = errBuffer.readLine()) {
						log.append(errLine);
						log.append("\n");
					}
				} finally {
					errBuffer.close();
				}
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, command[0] + " returned with error code " + exitValue + "\n\n"
					+ log, null));
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

	/**
	 * @since 1.1
	 */
	public void checkSystem(IProgressMonitor monitor, String id, String templateId) throws CoreException {
			getCodegenVersion();
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Checking system development support for " + id + ":" + templateId, IProgressMonitor.UNKNOWN);
			final ArrayList<String> arguments = new ArrayList<String>();
			final String redhawkCodegen = getCodegenFile().getPath();
			arguments.add(redhawkCodegen);

			// Check if template is supported
			if (VERSION_WITH_CHECK_TEMPLATE.compareTo(codegenVersion) <= 0) {
				arguments.add("--check-template=" + templateId); // 1.11+ CF codegen option
			} else {
				arguments.add("--template=" + templateId); // provide template 1.9+ CF codegen option
				arguments.add("--checkSupport"); // 1.10+ CF codegen option (fails against 1.9 CF codegen)
			}

			final String[] command = arguments.toArray(new String[arguments.size()]);
			
			// Launch the code generator.
			// NB: The process has implicitly exited (and been cleaned up by the JVM) when
			//     standard out/error are closed, so there is no need to explicitly wait for it.
			final Process process;
			String commandStr = commandToString(command);
			try {
				if (DEBUG.enabled) {
					DEBUG.trace("Jinja Check Command:\n  {0}", commandStr);
				}
				process = java.lang.Runtime.getRuntime().exec(command);
			} catch (final IOException e) {
				throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'\n" + commandStr, e));
			}

			// In order to poll both output (with the confusing name "getInputStream") and error,
			// create a thread for each, redirecting to the respective PrintStream objects.
			ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
			ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
			final Thread outThread = new Thread(new InputRedirector(process.getInputStream(), new PrintStream(outBuffer, true)));
			final Thread errThread = new Thread(new InputRedirector(process.getErrorStream(), new PrintStream(errBuffer, true)));
			outThread.setDaemon(true);
			errThread.setDaemon(true);
			outThread.start();
			errThread.start();

			Future< Integer > future = EXECUTOR_POOL.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
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
					return process.waitFor();
				}
			});
			try {
				while (true) {
					try {
						Integer retVal = future.get(500, TimeUnit.MILLISECONDS);
						if (retVal != 0) {
							String stdout = new String(outBuffer.toByteArray());
							String stderr = new String(errBuffer.toByteArray());
							MultiStatus status = new MultiStatus(JinjaGeneratorPlugin.PLUGIN_ID, Status.WARNING, stdout, null);
							status.add(new Status(Status.WARNING, JinjaGeneratorPlugin.PLUGIN_ID, commandStr + " returned with error code (" + retVal
								+ ")\n\nstdout: " + stdout + "\n\nstderr: " + stderr, null));
							throw new CoreException(status);
						}
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
			} finally {
				subMonitor.done();
			}
	}
	
	/**
	 * Uses redhawk-codegen --version option from 1.11+ (prior versions [1.9 - 1.10] fails with non-zero exit code).
	 * @return code generator's version, falls back to the empty version (0.0.0) if not able to determine it's version (i.e. unknown).
	 * @since 1.2
	 */
	public Version getCodegenVersion() throws CoreException {
		if (codegenVersion != null) { // if already got codegen's version
			return codegenVersion;    // use cached version for this object's session
		}

		final String redhawkCodegen = getCodegenFile().getPath();
		ProcessBuilder processBuilder = new ProcessBuilder(redhawkCodegen, "--version");
		processBuilder.redirectErrorStream(true); // merge stderr with stdout to simply logic
		
		// Launch the code generator to get it's version
		// NOTE: The process has implicitly exited (and been cleaned up by the JVM) when
		//       standard out/error are closed, so there is no need to explicitly wait for it.
		Process process;
		try {
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja Version Command:\n  {0}", commandToString(processBuilder.command().toArray(EMPTY_STRING_ARRAY)));
			}
			process = processBuilder.start();
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running '" + redhawkCodegen + "'", e));
		}

		Version version = null;
		final InputStreamReader inStream = new InputStreamReader(process.getInputStream());
		final BufferedReader reader = new BufferedReader(inStream);
		try {
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				Matcher matcher = VERSION_REGEX.matcher(line);
				if (matcher.find()) { // found version string
					try {
						version = new Version(line.substring(matcher.start(), matcher.end()));
						break; // found a valid version string and successfully converted to Version object
					} catch (IllegalArgumentException ex) {
						// PASS - continue to next line
					}
				}
			} // end for loop for reading through stdout/stderr

			int exitCode = process.waitFor();
			if (DEBUG.enabled) {
				DEBUG.trace("parsed version: {0}  exitCode={1}", version, exitCode);
			}
			if (exitCode != 0) { // failed to get codegen's version
				version = Version.emptyVersion; // reset to empty version 0.0.0 for unknown version
			}

		} catch (final IOException e) {
			throw new CoreException(
				new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception reading standard out from '" + redhawkCodegen + "' to get it's version", e));
		} catch (InterruptedException e) {
			// PASS - continue
			throw new CoreException(
				new Status(IStatus.WARNING, JinjaGeneratorPlugin.PLUGIN_ID, "Interrupted while reading standard out from '" + redhawkCodegen + "' to get it's version", e));
		} finally {

			try {
				reader.close(); // this also closes the wrapped inStream
			} catch (final IOException e) {
				// This is highly unlikely to occur, but log it just in case.
				JinjaGeneratorPlugin.logError("Exception closing standard out", e);
			}
		}
		if (version == null) {
			version = Version.emptyVersion;
		}
		codegenVersion = version;
		DEBUG.exitingMethod(version);
		return version;
	}

}
