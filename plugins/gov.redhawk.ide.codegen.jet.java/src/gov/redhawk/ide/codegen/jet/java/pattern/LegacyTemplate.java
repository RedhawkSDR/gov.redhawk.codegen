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
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.codegen.jet.java.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.java.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.java.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.java.template.StartJavaShTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.ProvidesPortJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.UsesPortJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.pull.ResourceJavaTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class LegacyTemplate implements IScaComponentCodegenTemplate {

	public LegacyTemplate() {
	}

	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		final String compJavaName = prefix + ".java";
		String file = "";

		if (!templ.isDevice()) {
			if (fileName.endsWith("InPort.java")) {
				file = new ProvidesPortJavaTemplate().generate(templ);
			} else if (fileName.endsWith("OutPort.java")) {
				file = new UsesPortJavaTemplate().generate(templ);
			} else if (compJavaName.equals(fileName)) {
				file = new ResourceJavaTemplate().generate(templ);
			}
		}

		if ("configure.ac".equals(fileName)) {
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

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		
		fileNames.add("startJava.sh");
		fileNames.add("configure.ac");
		fileNames.add("Makefile.am");
		fileNames.add("reconf");
		fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");

		return fileNames;
	}

	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();

		fileNames.add("startJava.sh");
		fileNames.add("reconf");

		return fileNames;
	}

	public boolean shouldGenerate() {
		return true;
	}

	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
	    return srcDir + prefix + ".java";
    }

}
