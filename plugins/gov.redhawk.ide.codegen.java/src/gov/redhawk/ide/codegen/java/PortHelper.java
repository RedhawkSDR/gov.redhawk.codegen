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
package gov.redhawk.ide.codegen.java;

/**
 * @since 5.0
 */
public class PortHelper {

	private PortHelper() {
	}

	/**
	 * This converts an interface name to its corresponding number of bytes per
	 * element.
	 * @param iface the interface to calculate
	 * @return number of bytes per element for the interface
	 */
	public static double ifaceToBytes(final String iface) {
		double size = 1.0;
		if ("dataDouble".equals(iface)) {
			size = 8.0; // SUPPRESS CHECKSTYLE MagicNumber
		} else if ("dataFile".equals(iface)) {
			size = 1.0;
		} else if ("dataFloat".equals(iface)) {
			size = 4.0; // SUPPRESS CHECKSTYLE MagicNumber
		} else if ("dataOctet".equals(iface)) {
			size = 1.0;
		} else if ("dataShort".equals(iface)) {
			size = 2.0; // SUPPRESS CHECKSTYLE MagicNumber
		} else if ("dataUlong".equals(iface)) {
			size = 4.0; // SUPPRESS CHECKSTYLE MagicNumber
		} else if ("dataXML".equals(iface)) {
			size = 1.0;
		}
		return size;
	}

}
