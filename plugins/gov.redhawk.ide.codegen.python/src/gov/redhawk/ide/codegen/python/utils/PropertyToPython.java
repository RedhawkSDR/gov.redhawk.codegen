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

import mil.jpeojtrs.sca.prf.AccessType;
import mil.jpeojtrs.sca.prf.Action;
import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.PropertyValueType;

/**
 * Utility class that helps convert the various Prf Property classes to Python code.
 * @since 5.0
 */
public class PropertyToPython {

	private PropertyToPython() {
		//Prevent instantiation
	}
	
	/**
	 * Returns the line of Python that represents the provided name.
	 * 
	 * @param name the String name to convert
	 * @return the String Python representation
	 */
	public static String getPythonName(String name) {
		if (name != null) {
			return "name=\"" + PythonGeneratorUtils.escapeString(name) + "\"";
		}
		return "";
	}
	
	/**
	 * Returns the line of Python that represents the provided units.
	 * 
	 * @param name the String units to convert
	 * @return the String Python representation
	 */
	public static String getPythonUnits(String units) {
		if (units != null) {
			return "units=\"" + PythonGeneratorUtils.escapeString(units) + "\"";
		}
		return "";
	}
	
	/**
	 * Returns the line of Python that represents the provided id.
	 * 
	 * @param id the String id to convert
	 * @return the String Python representation
	 */
	public static String getPythonId(String id) {
		if (id != null) {
			return "id_=\"" + PythonGeneratorUtils.escapeString(id) + "\"";
		} else {
			return "id_=None";
		}
	}

	/**
	 * Returns the default Python value for the given PropertyValueType.
	 * 
	 * @param type the PropertyValueType to obtain the default type for
	 * @return the String default value
	 */
	public static String getDefaultPythonValue(PropertyValueType type) {
		switch (type) {
		case STRING:
		case CHAR:
		case OBJREF:
			return "\"\"";
		case BOOLEAN:
			return "False";
		default:
			return Integer.toString(0);
		}
	}
	
	/**
	 * Returns a Python representation of the specified value/type pair.
	 * 
	 * @param value the value to obtain the Python value for
	 * @param type the PropertyValueType of the value
	 * @return the String Python representation of the specified value
	 */
	public static String getPythonValue(String value, PropertyValueType type) {
		switch (type) {
		case STRING:
			return "\"" + PythonGeneratorUtils.escapeString(value) + "\"";
		case CHAR:
			return "'" + PythonGeneratorUtils.escapeChar(value.charAt(0)) + "'";
		case OBJREF:
			return "\"" + value + "\"";
		case BOOLEAN:
			if ("true".equalsIgnoreCase(value)) {
				return "True";
			} else {
				return "False";
			}
		default:
			return value;
		}
	}

	/**
	 * Returns a Python representation of the specified PropertyValueType.
	 * 
	 * @param type the PropertyValueType to convert
	 * @return the String Python representation of the type
	 */
	public static String getPythonType(PropertyValueType type) {
		if (type != null) {
			return "type_=\"" + type.getLiteral() + "\"";
		} else {
			return "type_=None";
		}
	}

	/**
	 * Returns a Python representation of the specified AccessType.
	 * 
	 * @param mode the AccessType to convert
	 * @return the String Python representation of the mode
	 */
	public static String getPythonMode(AccessType mode) {
		if (mode != null) {
			return "mode=\"" + mode.getLiteral() + "\"";
		} else {
			return "mode=\"readwrite\"";
		}
	}
	
	/**
	 * Returns a Python representation of the specified Action.
	 * 
	 * @param action the Action to convert
	 * @return the String Python representation of the action
	 */
	public static String getPythonAction(Action action) {
		if (action != null) {
			return "action=\"" + action.getType().toString() + "\"";
		} else {
			return "action=\"external\"";
		}
	}

	/**
	 * Returns a Python representation of the specified <code> List </code>
	 * 
	 * @param kinds the List of Kind to convert
	 * @return the String python representation of the kinds
	 */
	public static String getPythonKinds(List<Kind> kinds) {
		StringBuilder builder = new StringBuilder();
		builder.append("kinds=");
		if (!kinds.isEmpty()) {
			builder.append("(");
			for (Kind kind : kinds) {
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

	/**
	 *	Returns a Python representation of the specified description.
	 *
	 * @param description the String description to convert
	 * @return the String Python representation of the description
	 */
	public static String getPythonDescription(String description) {
		return "description=\"\"\"" + description.trim() + "\"\"\"";
	}
}
