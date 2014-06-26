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
package gov.redhawk.ide.codegen.cplusplus;

import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.ide.natures.ScaProjectNature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.cdtvariables.ICdtVariableStatus;
import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IOption;
import org.eclipse.cdt.managedbuilder.core.ITool;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.macros.BuildMacroException;
import org.eclipse.cdt.managedbuilder.macros.IBuildMacroProvider;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * A builder designed to automatically include C/C++ code files into
 * an implementation's build. Generates the 'Makefile.am.ide'.
 *
 * @since 6.1
 */
public class CplusplusBuilder extends IncrementalProjectBuilder {

	/**
	 * The name of the builder registered with the extension point
	 */
	public static final String BUILDER_NAME = "gov.redhawk.ide.codegen.jet.cplusplus.builder";
	
	/**
	 * The name of the file containing the auto-inclusion Makefile code.
	 */
	private static final String AUTO_INCLUDE_FILENAME = "Makefile.am.ide";

	/**
	 * Matches files ending with .so and optionally having major/minor/revision extension(s)
	 */
	private static final Pattern LIB_SO_FILENAME = Pattern.compile(".*\\.so(?:\\.\\d+)*$");

	/**
	 * Matches files ending with .a
	 */
	private static final Pattern LIB_A_FILENAME = Pattern.compile(".*\\.a$");

	public CplusplusBuilder() {
		// PASS
	}

	@Override
	protected IProject[] build(final int kind, @SuppressWarnings("rawtypes") final Map args, final IProgressMonitor monitor) throws CoreException {
		try {
			final IProject project = getProject();

			// Remove our markers, if any
			final IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
			for (final IMarker marker : markers) {
				if (marker.getAttribute(IMarker.SOURCE_ID, "").equals(CplusplusBuilder.class.getCanonicalName())) {
					marker.delete();
				}
			}

			// Do nothing if this project doesn't have the correct natures
			if (project.getNature(CProjectNature.C_NATURE_ID) == null && project.getNature(CCProjectNature.CC_NATURE_ID) == null) {
				return null;
			}
			if (project.getNature(ScaProjectNature.ID) == null) {
				return null;
			}

			// We don't have a concept of partial build for this builder, as it's too difficult to determine if we
			// need to regenerate. Instead, we just generate the contents of the output file, and write it to disk if
			// it has changed or didn't exist
			fullBuild(monitor);

			if (kind == IncrementalProjectBuilder.FULL_BUILD) {
				try {
					ICProject cProject = CoreModel.getDefault().getCModel().getCProject(project.getName());
					if ((cProject != null) && (cProject.exists())) {
						CCorePlugin.getIndexManager().update(new ICElement[] { cProject }, IIndexManager.UPDATE_ALL | IIndexManager.UPDATE_EXTERNAL_FILES_FOR_PROJECT);
					}
				} catch (CoreException e) {
					GccGeneratorPlugin.getDefault().getLog().log(new Status(e.getStatus().getSeverity(), GccGeneratorPlugin.PLUGIN_ID, "Failed to build c project", e));
				}
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}

		return null;
	}

	/**
	 * Performs a complete re-build of the auto-inclusion Makefile (AUTO_INCLUDE_FILENAME). The file will contain all C/C++
	 * source files in any sub-directories of a source code folder, provided the files have not been excluded. The file
	 * will also contain any libraries the user added to the path via properties of the project.
	 *
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @throws CoreException The auto-inclusion makefile cannot be created
	 */
	private void fullBuild(final IProgressMonitor monitor) throws CoreException {
		// Get the current configuration from CDT
		final IProject project = getProject();
		final IManagedBuildInfo buildInfo = ManagedBuildManager.getBuildInfo(project, true);
		if (buildInfo == null) {
			throw new CoreException(new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, IResourceStatus.BUILD_FAILED,
			        "C/C++ managed build information was not available. This is usually a temporary error that "
			                + "indicates the Eclipse CDT has not finished background loading your project's information.", null));
		}
		final IConfiguration config = buildInfo.getDefaultConfiguration();

		// Get the source entries (source code folders), and tools from CDT for the current configuration
		final ICSourceEntry[] sourceEntries = config.getSourceEntries();
		final ITool[] tools = config.getRootFolderInfo().getTools();

		int totalWork = sourceEntries.length * 2 + tools.length * 2;
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating auto-inclusion Makefile", totalWork);

		// Find include information
		final List<String> includeDirsList = new ArrayList<String>();
		final Set<String> symbols = new LinkedHashSet<String>();
		findIncludeInfo(tools, progress.newChild(tools.length), includeDirsList, symbols);

		totalWork -= tools.length;

		final IBuildMacroProvider provider = ManagedBuildManager.getBuildMacroProvider();
		final Set<String> includeDirs = new LinkedHashSet<String>();
		final MultiStatus includeStatus = new MultiStatus(GccGeneratorPlugin.PLUGIN_ID, IStatus.OK,
		        "Unable to expand variable(s) in includepaths. Please check your include path settings for the project.", null);
		for (String includeDir : includeDirsList) {
			// Expand library path and add to a unique list
			try {
				includeDir = provider.resolveValue(includeDir, null, null, IBuildMacroProvider.CONTEXT_CONFIGURATION, config);
			} catch (final BuildMacroException e) {
				if (e.getStatus() instanceof ICdtVariableStatus) {
					final ICdtVariableStatus badCdtStatus = (ICdtVariableStatus) e.getStatus();
					includeStatus.add(new Status(IStatus.ERROR, badCdtStatus.getPlugin(), "Unable to resolve variable '"
					        + badCdtStatus.getReferencedMacroName() + "' in include path '" + badCdtStatus.getExpression() + "'"));
				} else {
					includeStatus.add(new Status(e.getStatus().getSeverity(), GccGeneratorPlugin.PLUGIN_ID,
					        "Unable to resolve variables in include path '" + includeDir + "'", e));
				}
				continue;
			}
			final IPath includeDirPath = new Path(includeDir);
			if (!includeDirPath.isAbsolute() && includeDirPath.segmentCount() > 0 && "..".equals(includeDirPath.segment(0))) {
				includeDir = project.getLocation().append(includeDirPath).toOSString();
			}
			includeDirs.add(includeDir);
		}
		// Can't continue if we have include status problems
		if (!includeStatus.isOK()) {
			for (final IStatus s : includeStatus.getChildren()) {
				final IMarker marker = project.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.MESSAGE, s.getMessage());
				marker.setAttribute(IMarker.SOURCE_ID, CplusplusBuilder.class.getCanonicalName());
			}
			throw new CoreException(includeStatus);
		}

		// Find library information
		final List<String> libraryDirsList = new ArrayList<String>();
		final List<String> librariesList = new ArrayList<String>();
		findLibraryInfo(tools, progress.newChild(tools.length), libraryDirsList, librariesList);
		totalWork -= tools.length;

		// Expand variables in libraries / paths
		final MultiStatus libraryStatus = new MultiStatus(GccGeneratorPlugin.PLUGIN_ID, IStatus.OK,
		        "Unable to expand variable(s) in library names / paths. Please check your libraries and library path settings for the project.", null);
		final Set<String> libraryDirs = new LinkedHashSet<String>();
		final Set<String> librariesExplicit = new LinkedHashSet<String>();
		final Set<String> librariesLinkerResolve = new LinkedHashSet<String>();
		for (String libraryDir : libraryDirsList) {
			// Expand library path and add to a unique list
			if (libraryDir.length() > 2 && libraryDir.charAt(0) == '"' && libraryDir.charAt(libraryDir.length() - 1) == '"') {
				libraryDir = libraryDir.substring(1, libraryDir.length() - 1);
			}
			libraryDir = libraryDir.trim();
			try {
				libraryDir = provider.resolveValue(libraryDir, null, null, IBuildMacroProvider.CONTEXT_CONFIGURATION, config);
			} catch (final BuildMacroException e) {
				if (e.getStatus() instanceof ICdtVariableStatus) {
					final ICdtVariableStatus badCdtStatus = (ICdtVariableStatus) e.getStatus();
					libraryStatus.add(new Status(IStatus.ERROR, badCdtStatus.getPlugin(), "Unable to resolve variable '"
					        + badCdtStatus.getReferencedMacroName() + "' in library path '" + badCdtStatus.getExpression() + "'"));
				} else {
					libraryStatus.add(new Status(e.getStatus().getSeverity(), GccGeneratorPlugin.PLUGIN_ID,
					        "Unable to resolve variables in library path '" + libraryDir + "'", e));
				}
				continue;
			}
			final IPath libraryDirPath = new Path(libraryDir);
			if (!libraryDirPath.isAbsolute() && libraryDirPath.segmentCount() > 0 && "..".equals(libraryDirPath.segment(0))) {
				libraryDir = project.getLocation().append(libraryDirPath).toOSString();
			}
			libraryDirs.add(libraryDir);
		}
		for (String library : librariesList) {
			// Expand library name
			if (library.length() > 2 && library.charAt(0) == '"' && library.charAt(library.length() - 1) == '"') {
				library = library.substring(1, library.length() - 1);
			}
			library = library.trim();
			try {
				library = provider.resolveValue(library, null, null, IBuildMacroProvider.CONTEXT_CONFIGURATION, config);
			} catch (final BuildMacroException e) {
				if (e.getStatus() instanceof ICdtVariableStatus) {
					final ICdtVariableStatus badCdtStatus = (ICdtVariableStatus) e.getStatus();
					libraryStatus.add(new Status(IStatus.ERROR, badCdtStatus.getPlugin(), "Unable to resolve variable '"
					        + badCdtStatus.getReferencedMacroName() + "' in library '" + badCdtStatus.getExpression() + "'"));
				} else {
					libraryStatus.add(new Status(e.getStatus().getSeverity(), GccGeneratorPlugin.PLUGIN_ID, "Unable to resolve variables in library '"
					        + library + "'", e));
				}
				continue;
			}

			// Did they specify it as a specific library, or one to be resolved by the linker?
			if (CplusplusBuilder.LIB_SO_FILENAME.matcher(library).matches() || CplusplusBuilder.LIB_A_FILENAME.matcher(library).matches()) {
				librariesExplicit.add(library);
			} else {
				librariesLinkerResolve.add(library);
			}
		}

		// Can't continue if we have library status problems
		if (!libraryStatus.isOK()) {
			for (final IStatus s : libraryStatus.getChildren()) {
				final IMarker marker = project.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.MESSAGE, s.getMessage());
				marker.setAttribute(IMarker.SOURCE_ID, CplusplusBuilder.class.getCanonicalName());
			}
			throw new CoreException(libraryStatus);
		}

		for (final ICSourceEntry sourceEntry : sourceEntries) {
			// Get the source files associated with this source entry
			final List<String> sourceFiles = new SourceFileFinder().getSourceFiles(sourceEntry);
			progress.worked(1);

			// Ignore this source entry if it's invalid
			if (sourceFiles == null) {
				continue;
			}

			// Generate the 'AUTO_INCLUDE_FILENAME' content from our list of included C/C++ source files
			// libraries, and library directories
			final byte[] makefileContent = generateMakefileContent(sourceFiles, librariesExplicit, libraryDirs, librariesLinkerResolve, includeDirs, symbols);

			// Write the file to disk
			final IPath sourcePath = sourceEntry.getFullPath();
			final IContainer sourceFolder = (IContainer) project.findMember(sourcePath);
			final IFile makefile = sourceFolder.getFile(new Path(CplusplusBuilder.AUTO_INCLUDE_FILENAME));
			if (makefile.exists()) {
				// Avoid writing the file out if it would be identical - avoids a change notification
				if (!contentsMatch(makefile, makefileContent)) {
					makefile.setContents(new ByteArrayInputStream(makefileContent), true, false, progress.newChild(1));
				}
			} else {
				makefile.create(new ByteArrayInputStream(makefileContent), true, progress.newChild(1));
			}
		}
	}

	/**
	 * Pulls all library include directories and libraries to link against from a list of CDT tools.
	 *
	 * @param tools The tools to query for library information
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @param libraryDirsList A list to append library include directories to
	 * @param librariesList A list to append libraries to link against to
	 */
	private void findLibraryInfo(final ITool[] tools, final IProgressMonitor monitor, final List<String> libraryDirsList, final List<String> librariesList) {
		int totalWork = tools.length;
		final SubMonitor progress = SubMonitor.convert(monitor, totalWork);

		for (final ITool tool : tools) {
			if (tool.getId().contains(".cpp.linker.")) { // This tool is for linking C++
				IOption option = tool.getOptionBySuperClassId("gnu.cpp.link.option.libs");
				if (option != null) {
					try {
						final String[] libs = option.getLibraries();
						for (final String lib : libs) {
							librariesList.add(lib);
						}
					} catch (final BuildException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("Unable to retrieve library information from tool option", e);
					}
				}

				option = tool.getOptionBySuperClassId("gnu.cpp.link.option.paths");
				if (option != null) {
					try {
						@SuppressWarnings("unchecked")
						final List<String> value = (List<String>) option.getValue();
						libraryDirsList.addAll(value);
					} catch (final ClassCastException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("CDT returned an unexpected value type from a tool option", e);
					}
				}

				progress.worked(1);
			} else if (tool.getId().contains(".c.linker.")) { // Else if the tool is for linking C
				IOption option = tool.getOptionBySuperClassId("gnu.c.link.option.libs");
				if (option != null) {
					try {
						final String[] libs = option.getLibraries();
						for (final String lib : libs) {
							librariesList.add(lib);
						}
					} catch (final BuildException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("Unable to retrieve library information from tool option", e);
					}
				}

				option = tool.getOptionBySuperClassId("gnu.c.link.option.paths");
				if (option != null) {
					try {
						@SuppressWarnings("unchecked")
						final List<String> value = (List<String>) option.getValue();
						libraryDirsList.addAll(value);
					} catch (final ClassCastException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("CDT returned an unexpected value type from a tool option", e);
					}
				}

				progress.worked(1);
			}
			progress.setWorkRemaining(--totalWork);
		}
	}

	/**
	 * Pulls all include directories to compile against from a list of CDT tools.
	 *
	 * @param tools The tools to query for library information
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @param includeDirsList A list to append include directories to
	 * @param symbolSet A set to append user symbols to
	 */
	private void findIncludeInfo(final ITool[] tools, final IProgressMonitor monitor, final List<String> includeDirsList, final Set<String> symbolSet) {
		int totalWork = tools.length;
		final SubMonitor progress = SubMonitor.convert(monitor, totalWork);

		for (final ITool tool : tools) {
			if (tool.getId().contains(".cpp.compiler.")) { // Else if the tool is for linking C
				IOption option = tool.getOptionBySuperClassId("gnu.cpp.compiler.option.include.paths");
				if (option != null) {
					try {
						final String[] incPaths = option.getIncludePaths();
						for (String incPath : incPaths) {
							// Remove quotes, trim
							if (incPath.length() > 2 && incPath.charAt(0) == '"' && incPath.charAt(incPath.length() - 1) == '"') {
								incPath = incPath.substring(1, incPath.length() - 1);
							}
							incPath = incPath.trim();

							// Only keep the path if it's not a CDT-only path
							if (!CppGeneratorUtils.isPathForCDTOnly(incPath)) {
								includeDirsList.add(incPath);
							}
						}
					} catch (final BuildException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("Unable to retrieve library information from tool option", e);
					}
				}
				option = tool.getOptionBySuperClassId("gnu.cpp.compiler.option.preprocessor.def");
				if (option != null) {
					try {
						final String[] symbols = option.getDefinedSymbols();
						for (final String symbol : symbols) {
							symbolSet.add(symbol);
						}
					} catch (final BuildException e) {
						// This should only occur if we've written our code incorrectly
						GccGeneratorPlugin.logError("Unable to retrieve library information from tool option", e);
					}
				}
				progress.worked(1);
			}
			progress.setWorkRemaining(--totalWork);
		}
	}

	/**
	 * Used to find source files associated with a CDT source path.
	 */
	private class SourceFileFinder implements ICElementVisitor {

		private ICSourceEntry sourceEntry;
		private IResource sourceFolder;
		private List<String> sourceFiles;

		/**
		 * Gets a {@link List} of the source files belonging to an {@link ICSourceEntry}. The list contains paths to
		 * each file relative to the {@link ICSourceEntry}.
		 *
		 * @param sourceEntry The source entry to create a list of files for
		 * @return The list of paths to files, relative to the source entry, or null if the source entry is invalid.
		 * @throws CoreException
		 */
		public List<String> getSourceFiles(final ICSourceEntry sourceEntry) throws CoreException {
			this.sourceEntry = sourceEntry;

			// Get IResource for source entry (path should be project-relative)
			final IPath sourcePath = this.sourceEntry.getFullPath();
			this.sourceFolder = getProject().findMember(sourcePath);

			// Ensure we found it and it's a container (basically not a file)
			if (this.sourceFolder == null || !(this.sourceFolder instanceof IContainer)) {
				return null;
			}

			// Get the associated ICElement (CDT model) and collect a list of included code files
			final ICElement sourceRoot = CoreModel.getDefault().create(this.sourceFolder);
			if (sourceRoot == null) {
				throw new CoreException(new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, IResourceStatus.BUILD_FAILED,
				        "C/C++ element model not available. This is usually a temporary error that "
				                + "indicates the Eclipse CDT has not finished background loading your project's information.", null));
			}

			// Visit the CModel tree belonging to the source entry to collect all included source files
			this.sourceFiles = new LinkedList<String>();
			sourceRoot.accept(this);
			return this.sourceFiles;
		}

		/**
		 * NOTE: This method is only intended to be invoked from within the class.
		 * <p />
		 * {@inheritDoc}
		 */
		@Override
		public boolean visit(final ICElement element) throws CoreException {
			final IPath relativePath = element.getResource().getProjectRelativePath();
			if (element instanceof ITranslationUnit && !CDataUtil.isExcluded(relativePath, this.sourceEntry)) {
				// This is a code file that isn't excluded from the current build configuration
				this.sourceFiles.add(element.getPath().makeRelativeTo(this.sourceFolder.getFullPath()).toOSString());
				return false;
			} else if (element instanceof ICContainer && !CDataUtil.isExcluded(relativePath, this.sourceEntry)) {
				// This is a sub-folder that isn't excluded from the current build configuration
				return true;
			}
			return (element instanceof ICProject && !CDataUtil.isExcluded(relativePath, this.sourceEntry));
		}

	}

	/**
	 * Checks the contents of a file against a byte array.
	 *
	 * @param file The existing file
	 * @param content The content to verify against the file
	 * @return True if the contents match exactly, false otherwise (or under any error condition)
	 */
	private boolean contentsMatch(final IFile file, final byte[] content) {
		InputStream in = null;

		try {
			if (file == null || content == null || !file.exists()) {
				return false;
			}
			in = file.getContents(true);

			final CRC32 crc1 = new CRC32();
			crc1.update(content);

			final CRC32 crc2 = new CRC32();
			final int bufferSize = 4096;
			final byte[] buffer = new byte[bufferSize];
			int len;
			while (true) {
				len = in.read(buffer, 0, bufferSize);
				if (len == -1) {
					break;
				} else {
					crc2.update(buffer, 0, len);
				}
			}

			return crc1.getValue() == crc2.getValue();
		} catch (final CoreException e) {
			return false;
		} catch (final IOException e) {
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					// PASS
				}
			}
		}
	}

	/**
	 * Creates the contents of the IDE's auto-inclusion makefile
	 *
	 * @param sourceFiles The sources files to needed to compile the component (e.g. .h, .cpp, etc.)
	 * @param librariesExplicit Libraries (by file name) that are needed to link the component (e.g. ld -l flag, but
	 * specified as 'libmylib.so')
	 * @param libraryDirs Directories that should be searched for libraries (i.e. ld -L flag)
	 * @param librariesLinkerResolve Libraries that the linker should link against (i.e. ld -l flag)
	 * @param includeDirs Directories that should be added to the compile include path (i.e. g++ -I flag)
	 * @param symbols Symbols that should be defined when compiling (i.e. -D flags)
	 * @return The new contents of the auto-generated makefile
	 */
	private byte[] generateMakefileContent(final List<String> sourceFiles, final Set<String> librariesExplicit, final Set<String> libraryDirs,
	        final Set<String> librariesLinkerResolve, final Set<String> includeDirs, final Set<String> symbols) {
		final StringBuilder makefileContents = new StringBuilder();
		makefileContents.append("# This file is regularly auto-generated by the REDHAWK IDE. Do not modify!\n");
		makefileContents.append("# Files can be excluded by right-clicking on the file in the project explorer\n");
		makefileContents.append("# and choosing Resource Configurations -> Exclude from build. Re-include files\n");
		makefileContents.append("# by opening the Properties dialog of your project and choosing C/C++ Build ->\n");
		makefileContents.append("# Tool Chain Editor, and un-checking \"Exclude resource from build \"\n");
		boolean first = true;
		for (final String sourceFile : sourceFiles) {
			if (first) {
				makefileContents.append("redhawk_SOURCES_auto = ");
				first = false;
			} else {
				makefileContents.append("redhawk_SOURCES_auto += ");
			}
			makefileContents.append(sourceFile);
			makefileContents.append('\n');
		}
		first = true;
		for (final String library : librariesExplicit) {
			if (first) {
				makefileContents.append("redhawk_LDADD_auto = ");
				first = false;
			} else {
				makefileContents.append("redhawk_LDADD_auto += ");
			}
			makefileContents.append(library);
			makefileContents.append('\n');
		}
		first = true;
		for (final String libraryDir : libraryDirs) {
			if (first) {
				makefileContents.append("redhawk_LDFLAGS_auto = -L");
				first = false;
			} else {
				makefileContents.append("redhawk_LDFLAGS_auto += -L");
			}
			makefileContents.append(libraryDir);
			makefileContents.append('\n');
		}
		for (final String library : librariesLinkerResolve) {
			if (first) {
				makefileContents.append("redhawk_LDFLAGS_auto = -l");
				first = false;
			} else {
				makefileContents.append("redhawk_LDFLAGS_auto += -l");
			}
			makefileContents.append(library);
			makefileContents.append('\n');
		}
		first = true;
		for (final String include : includeDirs) {
			if (first) {
				makefileContents.append("redhawk_INCLUDES_auto = -I");
				first = false;
			} else {
				makefileContents.append("redhawk_INCLUDES_auto += -I");
			}
			makefileContents.append(include);
			makefileContents.append('\n');
		}
		for (final String symbol : symbols) {
			if (first) {
				makefileContents.append("redhawk_INCLUDES_auto = -D");
				first = false;
			} else {
				makefileContents.append("redhawk_INCLUDES_auto += -D");
			}
			makefileContents.append(symbol);
			makefileContents.append('\n');
		}
		return makefileContents.toString().getBytes();
	}
}
