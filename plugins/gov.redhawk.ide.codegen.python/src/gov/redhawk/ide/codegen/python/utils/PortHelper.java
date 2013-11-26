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

import gov.redhawk.ide.idl.Operation;
import gov.redhawk.ide.idl.Param;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.scd.Uses;

/**
 * @since 4.0
 */
public class PortHelper {

	private final List<String> names = new ArrayList<String>();
	private Integer index = 0;

	public PortHelper() {

	}

	public String validateName(String name) {
		name = PortHelper.cleanName(name);

		if (!this.names.contains(name)) {
			this.names.add(name);
			return name;
		} else {
			name += (++this.index).toString();
			this.names.add(name);
		}

		return name;
	}

	public static String nameToCamelCase(String name) {
		name = name.trim();
		name = name.replace(" ", "_");
		name = name.replace("-", "_");

		// Handle some common name suffixes
		if (name.endsWith("Port")) {
			name = name.substring(0, name.indexOf("Port"));
		}

		if (name.endsWith("Struct")) {
			name = name.substring(0, name.indexOf("Struct"));
		}

		if ("true".equalsIgnoreCase(name)) {
			name = "True";
		}

		if ("false".equals(name)) {
			name = "False";
		}

		if (name.length() > 2) {
			final String temp = name.substring(1, name.length());
			name = name.substring(0, 1).toUpperCase() + temp;
		}

		final String[] parts = name.split("_");
		final StringBuffer newName = new StringBuffer();
		for (final String part : parts) {
		        if (part.length() > 0) {
			        newName.append(part.substring(0, 1).toUpperCase() + part.substring(1));
                        }
		}

		return newName.toString();
	}

	public static String idlToClassName(final String idlName) {
		final StringBuilder converted = new StringBuilder("");
		String idl = idlName.split(":")[1];
		String[] idlParts = idl.split("/"); 

		converted.append(idlParts[idlParts.length - 2]);
		converted.append("__POA.");
		converted.append(idlParts[idlParts.length - 1]);

		return converted.toString();
	}

	public static String cleanName(String name) {
		name = name.trim();
		name = name.replace(" ", "_");
		name = name.replace("-", "_");

		return name;
	}

	public static String getProperInterfaceName(final Uses use) {
		String idl = use.getRepID().split(":")[1];
		String[] idlParts = idl.split("/"); 
		return idlParts[idlParts.length - 1];
	}

	/**
	 * @since 5.0
	 */
	public static boolean hasReturn(final Operation op) {
		boolean ret = !"void".equals(op.getReturnType());

		if (!ret) {
			// For Python, *out parameters are returned from the function call
			for (final Param p : op.getParams()) {
				if (p.getDirection().contains("out")) {
					ret = true;
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * @since 5.0
	 */
	public static int getNumReturns(final Operation op) {
		int numReturns = ("void".equals(op.getReturnType())) ? 0 : 1; // SUPPRESS CHECKSTYLE AvoidInline

		// For Python, *out parameters are returned from the function call
		for (final Param p : op.getParams()) {
			if (p.getDirection().contains("out")) {
				numReturns++;
			}
		}

		return numReturns;
	}
	
	/**
     * @since 5.0
     */
	public static String idlToCamelPortClass(final String idlName) {
		final StringBuilder converted = new StringBuilder("");
		String idl = idlName.split(":")[1];
		String[] idlParts = idl.split("/"); 

		converted.append(idlParts[idlParts.length - 2]);
		converted.append("_");
		converted.append(idlParts[idlParts.length - 1]);

		return PortHelper.nameToCamelCase(converted.toString());
	}

	/**
     * @since 5.0
     */
	public static String repToChar(String repID) {
	    String rep = repID.split(":")[1].split("/")[1];
	    char c = 'c';
	    if ("dataDouble".equals(rep)) {
	    	c = 'd';
	    } else if ("dataFile".equals(rep)) {
            c = 'c';
	    } else if ("dataFloat".equals(rep)) {
            c = 'f';
	    } else if ("dataOctet".equals(rep)) {
            c = 'B';
	    } else if ("dataShort".equals(rep)) {
            c = 'h';
	    } else if ("dataUlong".equals(rep)) {
            c = 'L';
	    } else if ("dataXML".equals(rep)) {
            c = 'c';
	    }
	    return "'" + c + "'";
    }
}
