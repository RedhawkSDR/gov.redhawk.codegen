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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since 1.1
 */
public class PathLocatorUtil {
	private boolean breakOnFirstResult = false;
	private boolean resultFound = false;
	private String excludePathRegex = null;
	private String matchRegex = null;

	public List<File> getFileListings(File startingDir) {
		List<File> results = new ArrayList<File>();

		// Check common locations only if expecting only a single result. No reason to do it if returning multiple
		// paths.
		if (breakOnFirstResult) {
			checkCommonLocations(results);
		}

		// Stops deep dives once a result is found, when only expecting a single path
		if (breakOnFirstResult && resultFound) {
			return results;
		}

		File[] filesAndDirs = startingDir.listFiles();
		if (filesAndDirs == null) {
			return results;
		}

		List<File> filesDirs = Arrays.asList(filesAndDirs);
		outer: for (File file : filesDirs) {
			if (file.isDirectory()) {
				// Directory found, recursive call
				if (file.toString().matches(excludePathRegex)) {
					continue outer;
				}
				if (file.toString().matches(matchRegex)) {
					results.add(file);
					resultFound = true;
					if (breakOnFirstResult) {
						return results;
					}
				}

				List<File> deeperList = getFileListings(file);
				results.addAll(deeperList);
			}
		}
		return results;
	}

	private void checkCommonLocations(List<File> results) {
		File[] commonPaths = { new File("/usr/include"), new File("/usr/local/include") };

		outer: for (File path : commonPaths) {
			if (!path.exists()) {
				continue;
			}

			File[] contents = path.listFiles();
			for (File file : contents) {
				if (file.isDirectory() && file.toString().matches(matchRegex)) {
					results.add(file);
					resultFound = true;
					break outer;
				}
			}
		}
	}

	/**
	 * If true will stop traversing file tree when match is found
	 * @param breakOnFirstResult
	 */
	public void setBreakOnFirstResult(boolean breakOnFirstResult) {
		this.breakOnFirstResult = breakOnFirstResult;
	}

	/**
	 * Regular expression denoting which folders to skip when traversing the file tree
	 * @param excludePaths
	 */
	public void setExcludePathRegex(String excludePaths) {
		this.excludePathRegex = excludePaths;
	}

	/**
	 * The regular expression which files and folders will match to
	 * @param matchRegex
	 */
	public void setMatchRegex(String matchRegex) {
		this.matchRegex = matchRegex;
	}
}
