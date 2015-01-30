/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.softpackage.codegen;

public class SoftPackageSpdFileTemplate {
	protected static String nl;

	public static synchronized SoftPackageSpdFileTemplate create(String lineSeparator) {
		nl = lineSeparator;
		SoftPackageSpdFileTemplate result = new SoftPackageSpdFileTemplate();
		nl = null;
		return result;
	}

	// CHECKSTYLE:OFF
	public final String NL = (nl == null) ? (System.getProperties().getProperty("line.separator")) : nl;
	protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL
		+ "<!DOCTYPE softpkg PUBLIC \"-//JTRS//DTD SCA V2.2.2 SPD//EN\" \"softpkg.dtd\">" + NL + "<softpkg id=\"";
	protected final String TEXT_2 = "\" name=\"";
	protected final String TEXT_3 = "\" type=\"sca_compliant\">" + NL + "    <title></title>" + NL + "    <author>" + NL + "        <name>";
	protected final String TEXT_4 = "</name>" + NL + "    </author>" + NL + "</softpkg>";
	// CHECKSTYLE:ON

	public String generate(Object argument) {
		final StringBuffer stringBuffer = new StringBuffer();
		GeneratorArgsSoftpkg args = (GeneratorArgsSoftpkg) argument;
		stringBuffer.append(TEXT_1);
		stringBuffer.append(args.getSoftPkgId());
		stringBuffer.append(TEXT_2);
		stringBuffer.append(args.getSoftPkgName());
		stringBuffer.append(TEXT_3);
		stringBuffer.append(args.getAuthorName());
		stringBuffer.append(TEXT_4);
		return stringBuffer.toString();
	}

}
