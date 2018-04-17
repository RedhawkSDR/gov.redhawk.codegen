/**
 * This file is protected by Copyright.
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 */
package gov.redhawk.ide.internal.pydev;

import java.io.File;

public class PythonConfigUtil {

	private PythonConfigUtil() {
	}

	/**
	 * Generates a 'prefix-style' Python path
	 *
	 * @param ossiehome The location of REDHAWK
	 * @param pythonVersion The Python version string, e.g. "2.6"
	 * @return A File representing the python path
	 */
	public static File getPrefixStylePath(String ossiehome, String pythonVersion) {
		return new File(ossiehome + "/lib/python" + pythonVersion + "/site-packages");
	}

	/**
	 * Generates a 'home-style' Python path
	 *
	 * @param ossiehome The location of REDHAWK
	 * @return A File representing the python path
	 */
	public static File getHomeStylePath(String ossiehome) {
		return new File(ossiehome + "/lib/python");
	}

}
