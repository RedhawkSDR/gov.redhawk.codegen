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
import gov.redhawk.ide.sdr.ui.export.ExportUtils;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.sca.util.Debug;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	static final String[] EMPTY_STRING_ARRAY = new String[0];
	static final Pattern VERSION_REGEX = Pattern.compile("\\d+(\\.\\d+(\\.\\d+(\\.\\S+)?)?)?$");

	private static final Debug DEBUG = new Debug(JinjaGeneratorPlugin.PLUGIN_ID, "command");

	private static final ExecutorService EXECUTOR_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory(JinjaGenerator.class.getName()));

	/** current version of the REDHAWK (core framework) code generator */
	private Version codegenVersion = null;

	/**
	 * @since 1.2
	 */
	protected List<String> settingsToOptions(final ImplementationSettings implSettings) {
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

	/**
	 * @since 1.2
	 */
	protected String getSpdFile(final SoftPkg softpkg) {
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

	/**
	 * @since 1.2
	 */
	protected String prependPath(final String path, final String filename) {
		if (filename.startsWith(".." + File.separator)) {
			return filename.substring(3);
		} else {
			return path + File.separator + filename;
		}
	}

	private String listToString(List<String> list) {
		StringBuilder builder = new StringBuilder(256);
		for (String s : list) {
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

		// Header settings
		arguments.addAll(headerSettings(project));

		// Implementation settings
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

		final String commandline = listToString(arguments);
		// Print the command to the console.
		out.println(commandline + " ");

		// Launch the code generator.
		// NB: The process has implicitly exited (and been cleaned up by the JVM) when
		// standard out/error are closed, so there is no need to explicitly wait for it.
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		final Process process;
		try {
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja Generate Command:\n  {0}", commandline);
			}
			process = processBuilder.start();
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

		Future<Integer> future = EXECUTOR_POOL.submit(new Callable<Integer>() {

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
						out.println("exit code = " + retValue); // display non-zero exit to console
						throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, redhawkCodegen + " returned with error code "
							+ retValue + "\n\nSee console output for details.", null));
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
			Version cfCodegenVersion = getCodegenVersion();
			if (new Version(1, 10, 1).compareTo(cfCodegenVersion) <= 0) { // 1.10.1+ CF codegen is good for all implementation
																	// languages
				// Look for build.sh top-level and in the impl directory
				boolean foundTopLevelBuildSh = false;
				boolean foundBuildSh = false;
				for (String file : generateFiles) {
					if ("../build.sh".equals(file)) {
						foundTopLevelBuildSh = true;
					} else if ("build.sh".equals(file)) {
						foundBuildSh = true;
					}
				}

				// For C++, both files are required. For other languages, just the top-level is required.
				if (foundTopLevelBuildSh) {
					String progLang = (impl.getProgrammingLanguage() != null) ? impl.getProgrammingLanguage().getName() : null;
					if ("C++".equals(progLang)) {
						if (foundBuildSh) {
							ExportUtils.setUseBuildSH(project);
						}
					} else {
						ExportUtils.setUseBuildSH(project);
					}
				}
			}
		} finally {
			out.println(""); // add newline to separate current output from next run's output in console
			subMonitor.done();
		}
	}

	/**
	 * If the code generator is version 2.1.1+ and the header file is present, returns command line arguments to
	 * instruct the code generator to use the header.
	 * @param project The project for which code generation is occurring.
	 * @return A list of command line arguments to be added.
	 */
	private List<String> headerSettings(IProject project) {
		if (new Version(2, 1, 1).compareTo(getCodegenVersion()) <= 0 || !project.getFile("HEADER").exists()) {
			return Collections.emptyList();
		}
		return Arrays.asList("--header", "HEADER");
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

		// Launch the code generator.
		// NB: The process has implicitly exited (and been cleaned up by the JVM) when
		// standard out/error are closed, so there is no need to explicitly wait for it.
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Process process = null;
		final String commandline = listToString(arguments);
		try {
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja List Command:\n  {0}", commandline);
			}
			process = processBuilder.start();
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID,
				"Exception running code generator list command\n  " + commandline, e));
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
						log.append(errLine).append('\n');
					}
				} finally {
					errBuffer.close();
				}
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, redhawkCodegen + " returned with error code " + exitValue
					+ "\n\nCommand:\n " + commandline + "\n\n" + log, null));
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
		Version cfCodegenVersion = getCodegenVersion();
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Checking system development support for " + id + ":" + templateId, IProgressMonitor.UNKNOWN);
		final ArrayList<String> arguments = new ArrayList<String>();
		final String redhawkCodegen = getCodegenFile().getPath();
		arguments.add(redhawkCodegen);

		// Check if template is supported
		if (new Version(1, 11, 0).compareTo(cfCodegenVersion) <= 0) {
			arguments.add("--check-template=" + templateId); // 1.11+ CF codegen option
		} else {
			arguments.add("--template=" + templateId); // provide template 1.9+ CF codegen option
			arguments.add("--checkSupport"); // 1.10+ CF codegen option (fails against 1.9 CF codegen) (deprecated in
												// 1.11)
		}

		// Launch the code generator.
		// NB: The process has implicitly exited (and been cleaned up by the JVM) when
		// standard out/error are closed, so there is no need to explicitly wait for it.
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		processBuilder.redirectErrorStream(true); // merge stderr with stdout to simplify logic
		final Process process;
		try {
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja Check Command:\n  {0}", listToString(arguments));
			}
			process = processBuilder.start();
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Exception running code generator template check\n"
				+ listToString(arguments), e));
		}

		// poll output (with the confusing name "getInputStream") using a thread redirecting to a PrintStream
		ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
		final Thread outThread = new Thread(new InputRedirector(process.getInputStream(), new PrintStream(outBuffer, true)));
		outThread.setDaemon(true);
		outThread.start();

		Future<Integer> future = EXECUTOR_POOL.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				try {
					outThread.join();
				} catch (final InterruptedException e) {
					// This is highly unlikely to occur, but log it just in case.
					JinjaGeneratorPlugin.logError("Interrupted waiting for standard out during codegen template check", e);
				}
				return process.waitFor();
			}
		});
		try {
			while (true) {
				try {
					Integer retVal = future.get(500, TimeUnit.MILLISECONDS);
					if (retVal != 0) {
						String cmdOutput = new String(outBuffer.toByteArray());
						MultiStatus status = new MultiStatus(JinjaGeneratorPlugin.PLUGIN_ID, Status.WARNING, cmdOutput, null);
						status.add(new Status(Status.WARNING, JinjaGeneratorPlugin.PLUGIN_ID, redhawkCodegen + " returned with error code (" + retVal
							+ ")\nstdout/stderr:\n\n" + cmdOutput, null));
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
	 * fall back mechanism to detect REDHAWK CF code generator version (e.g. for 1.9 and 1.10).
	 * This method can be remove when IDE no longer supports running against a REDHAWK 1.9 or 1.10 framework.
	 * @return null if unable to determine version
	 */
	private Version getCodegenVersionFromPythonEggInfoFile() {
		Version version = null;
		File parent = getCodegenFile().getParentFile(); // e.g. bin directory
		if (parent != null) {
			File ossiehome = parent.getParentFile();
			if (ossiehome != null) {
				final String FN_PREFIX = "redhawk_codegen-";
				final String FN_SUFFIX = "-py2.6.egg-info";
				File libPythonDir = new File(new File(ossiehome, "lib"), "python");
				String[] foundFiles = libPythonDir.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (name != null && name.startsWith(FN_PREFIX) && name.endsWith(FN_SUFFIX)) {
							return true;
						}
						return false;
					}

				});
				if (foundFiles != null && foundFiles.length > 0) {
					final int beginIndex = FN_PREFIX.length();
					for (String filename : foundFiles) {
						int endIndex = filename.lastIndexOf(FN_SUFFIX);
						if (endIndex <= beginIndex) {
							continue; // ignore since name it not what we expect
						}
						filename = filename.substring(beginIndex, endIndex); // strip off fn prefix and suffix (only
																				// version string should remain)
						Matcher matcher = VERSION_REGEX.matcher(filename);
						if (matcher.find()) { // found version string
							try {
								version = new Version(filename.substring(matcher.start(), matcher.end()));
								break; // found a valid version string and successfully converted to Version object
							} catch (IllegalArgumentException ex) {
								// PASS - continue to next filename
							}
						}
					} // end for loop
				}
			}
		}
		DEBUG.exitingMethod(version);
		return version;
	}

	/**
	 * Uses redhawk-codegen --version option from 1.11+.
	 * Prior REDHAWK framework versions [1.9 - 1.10] fails with non-zero exit code so try to detect it's version using
	 * {@link #getCodegenVersionFromPythonEggInfoFile()}.
	 * This will never error out, even if the path to the code-generator is not found.
	 * @return code generator's version, falls back to the empty version (0.0.0) if not able to determine it's version
	 * (i.e. unknown).
	 * @since 1.2
	 */
	public Version getCodegenVersion() {
		if (codegenVersion != null) { // if already got codegen's version
			return codegenVersion; // use cached version for this object's session
		}

		final String redhawkCodegen = getCodegenFile().getPath();
		ProcessBuilder processBuilder = new ProcessBuilder(redhawkCodegen, "--version");
		processBuilder.redirectErrorStream(true); // merge stderr with stdout to simplify logic

		// Launch the code generator to get it's version
		// NOTE: The process has implicitly exited (and been cleaned up by the JVM) when
		// standard out/error are closed, so there is no need to explicitly wait for it.
		Version version = null;
		try {
			if (DEBUG.enabled) {
				DEBUG.trace("Jinja Version Command:\n  {0}", listToString(processBuilder.command()));
			}
			Process process = processBuilder.start();

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
				if (exitCode != 0) { // failed to get codegen's version, try to use some smarts to detect it
					version = getCodegenVersionFromPythonEggInfoFile();
				}

			} catch (final IOException e) {
				JinjaGeneratorPlugin.logWarn("Unable to get code generator version from " + redhawkCodegen, e);
			} catch (InterruptedException e) {
				JinjaGeneratorPlugin.logWarn("Interrupted while getting code generator version from " + redhawkCodegen, e);
			} finally {

				try {
					reader.close(); // this also closes the wrapped inStream
				} catch (final IOException e) {
					// This is highly unlikely to occur, but log it just in case.
					JinjaGeneratorPlugin.logError("Exception closing standard out", e);
				}
			}
		} catch (final IOException e) {
			JinjaGeneratorPlugin.logWarn("Unable to run code generator to get version from " + redhawkCodegen, e);
		}

		if (version == null) {
			version = Version.emptyVersion; // reset to empty version 0.0.0 for unknown version
		}
		codegenVersion = version;
		DEBUG.exitingMethod(version);
		return version;
	}
}
