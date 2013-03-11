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
import java.util.Map;

import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.util.DceUuidUtil;

/**
 * Utility class that helps convert Structs to Python code.
 * 
 * @since 5.0
 */
public class StructToPython {

	private StructToPython() {
		//Prevent instantiation
	}

	/**
	 * Gets the Python identifier to use for the Struct declaration
	 * 
	 * @param struct the Struct to get the identifier for
	 */
	public static String getName(final Struct struct) {
		// To create the Python variable name:
		//   1) Use name, if provided
		//   2) If id is a DCE UUID, use "prop"-something-or-other
		//   3) Just use the id
		if (struct.getName() != null && struct.getName().trim().length() != 0) {
			return PythonGeneratorUtils.makeValidIdentifier(struct.getName());
		} else if (DceUuidUtil.isValid(struct.getId())) {
			return "struct";
		} else {
			return PythonGeneratorUtils.makeValidIdentifier(struct.getId());
		}
	}

	/**
	 * Returns a python constructor definition string given a list of names and a map from those names to their
	 * associated simple properties.
	 * 
	 * @param names the list of simple names to allow as constructor parameters
	 * @param nameToSimpleMap the map of simple names to Simple objects; this allows lookup of type to set default
	 *            values
	 * @return the String for the constructor definition
	 */
	public static String getConstructorDef(final List<String> names, final Map<String, Simple> nameToSimpleMap) {
		final StringBuilder builder = new StringBuilder();

		builder.append("def __init__(self,");
		for (final String name : names) {
			builder.append(" ");
			builder.append(name);
			builder.append("=");
			builder.append(PropertyToPython.getDefaultPythonValue(nameToSimpleMap.get(name).getType()));
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("):");
		return builder.toString();
	}

	/**
	 * Returns the Python representation of the structdef.
	 * 
	 * @param className the classname to convert
	 * @return the String Python representaion of the StructDef
	 */
	public static String getStructDef(final String className) {
		return "structdef=" + className;
	}

	/**
	 * Returns a Python representation of the specified StructPropertyConfigurationType.
	 * 
	 * @param mode the StructPropertyConfigurationType to convert
	 * @return the String Python representation of the configurationkind
	 * @since 6.0
	 */
	public static String getPythonConfigurationKinds(final List<ConfigurationKind> kinds) {
		final StringBuilder builder = new StringBuilder();
		builder.append("configurationkind=");
		if (!kinds.isEmpty()) {
			builder.append("(");
			for (final ConfigurationKind kind : kinds) {
				builder.append("\"");
				builder.append(kind.getType().getLiteral());
				builder.append("\"");
				builder.append(",");
			}
			//If only one item in list, need trailing comma from python tuple
			// otherwise, trailing comma is not necessary
			if (kinds.size() > 1) {
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append(")");
			return builder.toString();
		} else {
			builder.append("\"configure\"");
			return builder.toString();
		}
	}

}
