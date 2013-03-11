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
package gov.redhawk.ide.codegen.python.utils;

import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.util.DceUuidUtil;

/**
 * Utility class that helps convert Simples to Python code.
 * 
 * @since 5.0
 */
public class SimpleToPython {

	private SimpleToPython() {
		//Prevent instantiation
	}

	/**
	 * Gets the Python identifier associated with the simple.
	 * 
	 * @param simple the Simple to get the identifier for
	 */
	public static String getName(Simple simple) {
		// To create the Python variable name:
		//   1) Use name, if provided
		//   2) If id is a DCE UUID, use "prop"-something-or-other
		//   3) Just use the id
		if (simple.getName() != null && simple.getName().trim().length() != 0) {
			return PythonGeneratorUtils.makeValidIdentifier(simple.getName());
		} else if (DceUuidUtil.isValid(simple.getId())) {
			return "simple";
		} else {
			return PythonGeneratorUtils.makeValidIdentifier(simple.getId());
		}
	}

}
