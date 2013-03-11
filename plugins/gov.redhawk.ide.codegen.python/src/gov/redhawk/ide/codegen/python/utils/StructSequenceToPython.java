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

import java.util.List;

import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.StructPropertyConfigurationType;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.util.DceUuidUtil;

/**
 * Utility class that helps convert StructSequences to Python code.
 * 
 * @since 5.0
 */
public class StructSequenceToPython {

	private StructSequenceToPython() {
		//Prevent instantiation
	}

	/**
	 * Gets the Python identifier associated with the structSequence.
	 * 
	 * @param structSequence the StructSequence to get the identifier for
	 */
	public static String getName(StructSequence structSequence) {
		// To create the Python variable name:
		//   1) Use name, if provided
		//   2) If id is a DCE UUID, use "prop"-something-or-other
		//   3) Just use the id
		if (structSequence.getName() != null && structSequence.getName().trim().length() != 0) {
			return PythonGeneratorUtils.makeValidIdentifier(structSequence.getName());
		} else if (DceUuidUtil.isValid(structSequence.getId())) {
			return "structSequence";
		} else {
			return PythonGeneratorUtils.makeValidIdentifier(structSequence.getId());
		}
	}

	/**
	 * Creates a line of Python code to represent StructValues.
	 * 
	 * @param className the Python name of the Struct class associated with the StructValues
	 * @param values the StructValues contained in the sequence
	 * @return the line of Python code created to represent the StructValues
	 */
	public static String getPythonStructValue(String className, StructValues values) {
		StringBuilder builder = new StringBuilder();
		builder.append("defvalue=");

		if (values.getValues().isEmpty()) {
			builder.append("[]");
		} else {
			builder.append("[");

			for (List<Simple> structVals : values.getValues()) {
				builder.append(className);
				builder.append("(");
				for (Simple simple : structVals) {
					builder.append(PropertyToPython.getPythonValue(simple.getValue(), simple.getType()));
					builder.append(",");
				}
				//remove trailing comma
				builder.deleteCharAt(builder.length() - 1);
				builder.append(")");
				builder.append(",");
			}
			//remove trailing comma
			builder.deleteCharAt(builder.length() - 1);
			builder.append("]");
		}
		return builder.toString();
	}

	/**
	 * Returns a Python representation of the specified StructPropertyConfigurationType.
	 * 
	 * @param mode the StructPropertyConfigurationType to convert
	 * @return the String Python representation of the configurationkind
	 */
	public static String getPythonConfigurationKind(StructPropertyConfigurationType type) {
		if (type != null) {
			return "configurationkind=\"" + type.getLiteral() + "\"";
		} else {
			return "configurationkind=\"configure,\"";
		}
	}
}
