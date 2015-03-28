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
package gov.redhawk.ide.codegen.jinja.java.template;

import java.io.File;

import mil.jpeojtrs.sca.spd.SoftPkg;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.template.JinjaTemplate;

public class DefaultJavaTemplate extends JinjaTemplate {

	@Override
	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		// Source directory
		final String outputDir = srcDir + "src" + File.separator;

		// Directories from the package name
		String packagePath = "";
		for (final Property property : implSettings.getProperties()) {
			if ("java_package".equals(property.getId())) {
				packagePath = property.getValue().replace('.', File.separatorChar) + File.separator;
				break;
			}
		}

		// The class name (from the basename)
		String className = softPkg.getName();
		int index = className.lastIndexOf('.');
		if (index != -1) {
			className = className.substring(index + 1);
		}

		return outputDir + packagePath + className + ".java";
	}

}
