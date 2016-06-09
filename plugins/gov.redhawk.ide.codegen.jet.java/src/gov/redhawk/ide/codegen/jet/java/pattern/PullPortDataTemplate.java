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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.IPortTemplateDesc;
import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.PortRepToGeneratorMap;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.codegen.jet.java.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.java.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.java.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.java.template.StartJavaShTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.ProvidesPortJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.UsesPortJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.pull.DeviceJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.pull.ResourceJavaTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class PullPortDataTemplate implements IScaComponentCodegenTemplate {

	public PullPortDataTemplate() {
	}

	@Override
	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		final String compJavaName = prefix + ".java";
		String file = "";

		if (!templ.isDevice()) {
			if (fileName.endsWith("InPort.java")) {
				file = generatePort(fileName, true, softPkg, templ, implSettings);
			} else if (fileName.endsWith("OutPort.java")) {
				file = generatePort(fileName, false, softPkg, templ, implSettings);
			} else if (compJavaName.equals(fileName)) {
				file = new ResourceJavaTemplate().generate(templ);
			}
		} else if (templ.isDevice()) {
			if (fileName.endsWith("InPort.java")) {
				file = generatePort(fileName, true, softPkg, templ, implSettings);
			} else if (fileName.endsWith("OutPort.java")) {
				file = generatePort(fileName, false, softPkg, templ, implSettings);
			} else if (compJavaName.equals(fileName)) {
				file = new DeviceJavaTemplate().generate(templ);
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
		} else if (("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec").equals(fileName)) {
			file = new ResourceSpecTemplate().generate(templ);
		}

		return file;
	}

	private String generatePort(String fileName, boolean providesPort, SoftPkg softPkg,  JavaTemplateParameter templ, ImplementationSettings implSettings) throws CoreException {
	    String file = null;

	    for (PortRepToGeneratorMap p : implSettings.getPortGenerators()) {
	    	if (p.getRepId().equals(templ.getPortRepId())) {
	    		try {
	                IPortTemplateDesc template = CodegenUtil.getPortTemplate(p.getGenerator(), null);
	                if (template != null) {
	                	file = template.getTemplate().generateFile(fileName, providesPort, softPkg, implSettings, templ, CodegenUtil.JAVA);
	                }
	                break;
                } catch (CoreException e) {
    				// PASS
                }
	    	}
	    }
	    
	    if (file == null) {
	    	if (providesPort) {
	    		file = new ProvidesPortJavaTemplate().generate(templ);
	    	} else {
				file = new UsesPortJavaTemplate().generate(templ);
	    	}
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
