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
package gov.redhawk.ide.codegen.jet.python.pattern;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.python.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.python.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.python.template.component.minimal.ResourceBaseTemplate;
import gov.redhawk.ide.codegen.jet.python.template.component.minimal.ResourcePythonTemplate;
import gov.redhawk.ide.codegen.jet.python.template.device.skeleton.DevicePythonTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class MinimalTemplate implements IScaComponentCodegenTemplate {

	public MinimalTemplate() {
	}

	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		String file = "";

		final List<String> sourceFiles = new ArrayList<String>();
		sourceFiles.add(prefix + ".py");
		sourceFiles.add(prefix + "_base.py");
		templ.setSourceFiles(sourceFiles.toArray(new String[sourceFiles.size()]));

		if (fileName.equals(prefix + ".py")) {
			if (templ.isDevice()) {
				file = new DevicePythonTemplate().generate(templ);
			} else {
				file = new ResourcePythonTemplate().generate(templ);
			}
		} else if (fileName.equals(prefix + "_base.py")) {
			file = new ResourceBaseTemplate().generate(templ);
		} else if (fileName.equals("configure.ac")) {
			file = new ConfigureAcTemplate().generate(templ);
		} else if (fileName.equals("Makefile.am")) {
			file = new MakefileAmTemplate().generate(templ);
		} else if (fileName.equals("reconf")) {
			file = new ReconfTemplate().generate(templ);
		} else if (fileName.equals("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec")) {
			file = new ResourceSpecTemplate().generate(templ);
		}

		return file;
	}

	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		fileNames.add("reconf");
		fileNames.add(prefix + ".py");

		return fileNames;
	}

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		fileNames.add("configure.ac");
		fileNames.add("Makefile.am");
		fileNames.add("reconf");
		fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");
		fileNames.add(prefix + ".py");
		fileNames.add(prefix + "_base.py");

		return fileNames;
	}

	public boolean shouldGenerate() {
		return true;
	}
	
	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
	    return srcDir + prefix + ".py";
    }

}
