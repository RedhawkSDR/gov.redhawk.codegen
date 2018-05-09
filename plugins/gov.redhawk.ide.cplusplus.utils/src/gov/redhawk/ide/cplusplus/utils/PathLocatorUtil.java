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
package gov.redhawk.ide.cplusplus.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @since 1.1
 */
public class PathLocatorUtil {

	private boolean breakOnFirstResult = false;
	private Pattern excludePathRegex = null;
	private Pattern excludePathSegmentRegex = null;
	private Pattern matchRegex = null;

	public List<File> getFileListings(File startingDir) {
		return getFileListings(Arrays.asList(startingDir));
	}

	/**
	 * @since 2.2
	 */
	public List<File> getFileListings(List<File> startingDirs) {
		final List<File> results = new ArrayList<File>();
		for (File startingDir : startingDirs) {
			try {
				Files.walkFileTree(startingDir.toPath(), new FileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						if (excludePathRegex != null && excludePathRegex.matcher(dir.toString()).matches()) {
							return FileVisitResult.SKIP_SUBTREE;
						} else if (excludePathSegmentRegex != null && excludePathSegmentRegex.matcher(dir.getFileName().toString()).matches()) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						return checkForMatch(dir);
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (excludePathRegex != null && excludePathRegex.matcher(file.toString()).matches()) {
							return FileVisitResult.CONTINUE;
						} else if (excludePathSegmentRegex != null && excludePathSegmentRegex.matcher(file.getFileName().toString()).matches()) {
							return FileVisitResult.CONTINUE;
						}
						return checkForMatch(file);
					}

					private FileVisitResult checkForMatch(Path path) {
						if (matchRegex.matcher(path.toString()).matches()) {
							results.add(path.toFile());
							if (breakOnFirstResult) {
								return FileVisitResult.TERMINATE;
							}
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				// PASS
			}

			if (breakOnFirstResult && !results.isEmpty()) {
				return results;
			}
		}

		return results;
	}

	/**
	 * If true will stop traversing file tree when match is found
	 * @param breakOnFirstResult
	 */
	public void setBreakOnFirstResult(boolean breakOnFirstResult) {
		this.breakOnFirstResult = breakOnFirstResult;
	}

	/**
	 * Paths matching this regular expression will be excluded from the search.
	 * @param excludePaths
	 */
	public void setExcludePathRegex(String excludePaths) {
		this.excludePathRegex = Pattern.compile(excludePaths);
	}

	/**
	 * If the last segment of a path matches this regular expression it will be excluded from the search.
	 * @since 2.2
	 */
	public void setExcludePathSegmentRegex(String excludePathSegements) {
		this.excludePathSegmentRegex = Pattern.compile(excludePathSegements);
	}

	/**
	 * Paths matching this regular expression will be considered a match.
	 * @param matchRegex
	 */
	public void setMatchRegex(String matchRegex) {
		this.matchRegex = Pattern.compile(matchRegex);
	}
}
