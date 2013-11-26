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
package gov.redhawk.ide.codegen.jet.java.pattern;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.codegen.jet.java.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.java.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.java.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.java.template.StartJavaShTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.skeleton.ResourceJavaTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class ManualTemplate implements IScaComponentCodegenTemplate {
	// CHECKSTYLE:OFF

	public ManualTemplate() {
	}

	@Override
	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		String file = "";

		for (final Property prop : implSettings.getProperties()) {
			if (prop.getId().equals("generate_skeleton")) {
				if ("FALSE".equals(prop.getValue())) {
					return file;
				}
			}
		}

		if (!templ.isDevice() && (prefix + ".java").equals(fileName)) {
			file = new ResourceJavaTemplate().generate(templ);
		} else if ("configure.ac".equals(fileName)) {
			file = new ConfigureAcTemplate().generate(templ);
		} else if ("Makefile.am".equals(fileName)) {
			file = new MakefileAmTemplate().generate(templ);
		} else if ("reconf".equals(fileName)) {
			file = new ReconfTemplate().generate(templ);
		} else if ("startJava.sh".equals(fileName)) {
			file = new StartJavaShTemplate().generate(templ);
		} else if (fileName.equals("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec")) {
			file = new ResourceSpecTemplate().generate(templ);
		}

		return file;
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		
		fileNames.add("startJava.sh");
		fileNames.add("configure.ac");
		fileNames.add("Makefile.am");
		fileNames.add("reconf");
		fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");

		return fileNames;
	}

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();

		fileNames.add("startJava.sh");
		fileNames.add("reconf");

		return fileNames;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
	    return srcDir + prefix + ".java";
    }

}
