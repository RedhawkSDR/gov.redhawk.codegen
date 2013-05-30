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
		final String prefix = softPkg.getName();
		final String outputDir = srcDir + "src" + File.separator;
		String packagePath = "";
		for (final Property property : implSettings.getProperties()) {
			if ("java_package".equals(property.getId())) {
				packagePath = property.getValue().replace('.', File.separatorChar) + File.separator;
				break;
			}
		}
		return outputDir + packagePath + prefix + ".java";
	}

}
