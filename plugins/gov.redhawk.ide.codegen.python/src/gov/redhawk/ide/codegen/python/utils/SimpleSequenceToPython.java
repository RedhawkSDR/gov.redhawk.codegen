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

import mil.jpeojtrs.sca.prf.PropertyValueType;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.prf.Values;
import mil.jpeojtrs.sca.util.DceUuidUtil;

/**
 * Utility class that helps convert SimpleSequences to Python code.
 * 
 * @since 5.0
 */
public class SimpleSequenceToPython {

	private SimpleSequenceToPython() {
		//Prevent instantiation
	}

	/**
	 * Gets the Python identifier associated with the simpleSequence.
	 * 
	 * @param simpleSequence the SimpleSequence to get the identifier for
	 */
	public static String getName(SimpleSequence simpleSequence) {
		// To create the Python variable name:
		//   1) Use name, if provided
		//   2) If id is a DCE UUID, use "prop"-something-or-other
		//   3) Just use the id
		if (simpleSequence.getName() != null && simpleSequence.getName().trim().length() != 0) {
			return PythonGeneratorUtils.makeValidIdentifier(simpleSequence.getName());
		} else if (DceUuidUtil.isValid(simpleSequence.getId())) {
			return "simpleSequence";
		} else {
			return PythonGeneratorUtils.makeValidIdentifier(simpleSequence.getId());
		}
	}

	/**
	 * Returns a single line of Python code that represents the SimpleSequence values.
	 * 
	 * @param values the values associated with the SimpleSequence
	 * @param type the type of the SimpleSequence
	 * @return the String of PythonCode to represent the SimpleSequence values
	 */
	public static String getPythonValues(Values values, PropertyValueType type) {
		StringBuilder builder = new StringBuilder();
		if (values == null) {
			builder.append("defvalue=None");
			return builder.toString();
		} else {
			builder.append("defvalue=");
			if (PropertyValueType.CHAR.equals(type)) {
				builder.append("\"");
				for (String value : values.getValue()) {
					builder.append(value.charAt(0));
				}
				builder.append("\"");
			} else if (PropertyValueType.OCTET.equals(type)) {
				builder.append("\"");
				for (String value : values.getValue()) {
					// Java does not support unsigned bytes so a short is used here instead.
					// Values > 127  would cause exceptions otherwise.  Fixes bug #265
					short b = Short.parseShort(value);
					builder.append("\\x" + String.format("%02x", b));
				}
				builder.append("\"");
			} else {
				builder.append("(");
				for (String value : values.getValue()) {
					builder.append(PropertyToPython.getPythonValue(value, type));
					builder.append(",");
				}
				if (values.getValue().size() > 1) {
					// If the list has more than one element remove the trailing comma
					// so the code looks cleaner (although technically this is not necessary).
					// HOWEVER, if there is only one element you *must* include the comma because
					// ("a") is not a single-element tuple, it's just "a" while ("a",) is a single
					// element tuple
					builder.deleteCharAt(builder.length() - 1);
				}
				builder.append(")");
			}
			return builder.toString();
		}
	}
}
