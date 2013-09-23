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
package gov.redhawk.ide.codegen.python;

import gov.redhawk.ide.natures.ScaProjectNature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.python.pydev.core.IPythonPathNature;
import org.python.pydev.plugin.nature.PythonNature;

/**
 * A builder designed to automatically include Python code files into an implementation's build.
 * 
 * @since 7.0
 */
public class PythonSourceInclude extends IncrementalProjectBuilder {

	/**
	 * The name of the builder registered with the extension point
	 */
	public static final String BUILDER_NAME = "gov.redhawk.ide.codegen.jet.python.sourceinclude";

	/**
	 * Matches files ending with .py
	 */
	private static final Pattern PY_FILENAME = Pattern.compile(".*\\.py$");

	@Override
	protected IProject[] build(final int kind, @SuppressWarnings("rawtypes") final Map args, final IProgressMonitor monitor) throws CoreException {
		try {
			final IProject project = getProject();

			// Do nothing if this project doesn't have the correct natures
			if (project.getNature(PythonNature.PYTHON_NATURE_ID) == null || project.getNature(ScaProjectNature.ID) == null) {
				return null;
			}

			// We don't have a concept of partial build for this builder, as it's too difficult to determine if we
			// need to regenerate. Instead, we just generate the contents of the output file, and write it to disk if
			// it has changed or didn't exist
			fullBuild(monitor, project);
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}

		return null;
	}
	
	/**
	 * Performs a complete re-build of the auto-inclusion Makefile (Makefile.am.ide). The file will contain all Python
	 * source files in any sub-directories of a source code folder, provided the files have not been excluded.
	 * 
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @param project The project to build the includsion file for
	 * @throws CoreException The auto-inclusion makefile cannot be created
	 */
	private void fullBuild(final IProgressMonitor monitor, final IProject project) throws CoreException {
		// Get the source path(s)
		final PythonNature nature = PythonNature.getPythonNature(project);
		if (nature == null) {
			return;
		}
		final IPythonPathNature pathNature = nature.getPythonPathNature();
		if (pathNature == null) {
			return;
		}
		final Set<String> sourcePaths = pathNature.getProjectSourcePathSet(true);
		
		final int FIND_FILES_WORK = 1;
		final int GENERATE_MAKEFILE_WORK = 1;
		int totalWork = sourcePaths.size() * (FIND_FILES_WORK + GENERATE_MAKEFILE_WORK);
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating auto-inclusion Makefile", totalWork);
		
		// Find Python source files
		for (final String sourcePath : sourcePaths) {
			final IFolder sourceFolder = project.getWorkspace().getRoot().getFolder(new Path(sourcePath));
			if (!sourceFolder.exists()) {
				continue;
			}
			final IPath sourceFolderPath = sourceFolder.getLocation();
			final Map<String, Boolean> sourceFileToExecPerm = new HashMap<String, Boolean>();
			
			// Visit the source folder's children
			sourceFolder.accept(new IResourceVisitor() {
				@Override
				public boolean visit(IResource resource) throws CoreException {
					if (resource instanceof IFile) {
						final IFile file = (IFile) resource;
						if (PY_FILENAME.matcher(file.getName()).matches()) {
							sourceFileToExecPerm.put(file.getLocation().makeRelativeTo(sourceFolderPath).toOSString(), file.getResourceAttributes().isExecutable());
						}
						return false;
					} else if (resource instanceof IContainer) {
						return true;
					}
					return false;
				}
			});
			progress.worked(FIND_FILES_WORK);
			totalWork -= FIND_FILES_WORK;
			
			// Generate (or delete) the Makefile.am.ide file
			final IFile makefile = sourceFolder.getFile(new Path("Makefile.am.ide"));
			if (sourceFileToExecPerm.isEmpty()) {
				if (makefile.exists()) {
					makefile.delete(true, progress.newChild(GENERATE_MAKEFILE_WORK));
				}
			} else {
				final byte[] makefileContent = generateMakefileContent(sourceFileToExecPerm);
				
				if (makefile.exists()) {
					// Avoid writing the file out if it would be identical - avoids a change notification
					if (!contentsMatch(makefile, makefileContent)) {
						makefile.setContents(new ByteArrayInputStream(makefileContent), true, false, progress.newChild(GENERATE_MAKEFILE_WORK));
					}
				} else {
					makefile.create(new ByteArrayInputStream(makefileContent), true, progress.newChild(GENERATE_MAKEFILE_WORK));
				}
			}
			totalWork -= GENERATE_MAKEFILE_WORK;
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
	 * @param sourceFiles The sources files to needed to compile the component (.py files)
	 * @return The new contents of the auto-generated makefile
	 */
	private byte[] generateMakefileContent(final Map<String, Boolean> sourceFiles) {
		final StringBuilder makefileContents = new StringBuilder();
		makefileContents.append("# This file is regularly auto-generated by the REDHAWK IDE. Do not modify!\n");
		boolean firstExec = true, firstNonExec = true;
		for (final String sourceFile : sourceFiles.keySet()) {
			if (sourceFiles.get(sourceFile)) {
				// It's executable
				makefileContents.append("redhawk_SCRIPTS_auto ");
				if (firstExec) {
					makefileContents.append("= ");
					firstExec = false;
				} else {
					makefileContents.append("+= ");
				}
			} else {
				// It's not exectuable
				makefileContents.append("redhawk_DATA_auto ");
				if (firstNonExec) {
					makefileContents.append("= ");
					firstNonExec = false;
				} else {
					makefileContents.append("+= ");
				}
			}
			makefileContents.append(sourceFile);
			makefileContents.append('\n');
		}
		return makefileContents.toString().getBytes();
	}

}
