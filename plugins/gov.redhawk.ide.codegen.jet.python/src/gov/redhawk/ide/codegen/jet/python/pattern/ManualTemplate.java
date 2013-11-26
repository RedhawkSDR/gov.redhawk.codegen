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
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.python.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.python.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.python.template.component.skeleton.ResourcePythonTemplate;
import gov.redhawk.ide.codegen.jet.python.template.device.skeleton.DevicePythonTemplate;
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
		final TemplateParameter templ = (TemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		final boolean generate = getGenerateSkeletonProperty(implSettings);
		if (generate) {
			templ.setSourceFiles(new String[] { prefix + ".py" });
		}
		
		String file = "";
		if (fileName.equals(prefix + ".py")) {
			if (templ.isDevice()) {
				file = new DevicePythonTemplate().generate(templ);
			} else {
				file = new ResourcePythonTemplate().generate(templ);
			}
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

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);

		final boolean generate = getGenerateSkeletonProperty(implSettings);

		if (generate) {
			fileNames.add("reconf");
			fileNames.add(prefix + ".py");
		}

		return fileNames;
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		final boolean generate = getGenerateSkeletonProperty(implSettings);

		if (generate) {
			fileNames.add("configure.ac");
			fileNames.add("Makefile.am");
			fileNames.add("reconf");
			fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");
			fileNames.add(prefix + ".py");
		}

		return fileNames;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}
	
	@Override
	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
	    return srcDir + prefix + ".py";
    }
	
	private boolean getGenerateSkeletonProperty(final ImplementationSettings implSettings) {
		for (final Property tempProp : implSettings.getProperties()) {
			if ("generate_skeleton".equals(tempProp.getId())) {
				if ("TRUE".equalsIgnoreCase(tempProp.getValue())) {
					return true;
				}
			}
		}
		return false;
	}

}
