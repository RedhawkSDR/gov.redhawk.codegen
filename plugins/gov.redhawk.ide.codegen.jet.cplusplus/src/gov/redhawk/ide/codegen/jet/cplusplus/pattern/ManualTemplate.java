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
package gov.redhawk.ide.codegen.jet.cplusplus.pattern;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.template.BuildShTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.skeleton.MainCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.skeleton.ResourceCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.skeleton.ResourceHTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.device.skeleton.DMainCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.device.skeleton.DResourceCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.device.skeleton.DResourceHTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.model.sca.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class ManualTemplate implements IScaComponentCodegenTemplate {

	public ManualTemplate() {
	}

	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		String file = "";
		boolean isSkeleton = false;

		for (final Property prop : implSettings.getProperties()) {
			if (prop.getId().equals("generate_skeleton")) {
				if ("TRUE".equalsIgnoreCase(prop.getValue())) {
					isSkeleton = true;
				}
			}
		}

		// Only generate code if Generate Skeleton is checked
		if (isSkeleton) {
			final List<String> sourceFiles = new ArrayList<String>();
			sourceFiles.add(prefix + ".cpp");
			sourceFiles.add(prefix + ".h");
			sourceFiles.add("main.cpp");
			templ.setSourceFiles(sourceFiles.toArray(new String[sourceFiles.size()]));

			if (fileName.equals("main.cpp")) {
				if (templ.isDevice()) {
					file = new DMainCppTemplate().generate(templ);
				} else {
					file = new MainCppTemplate().generate(templ);
				}
			} else if (fileName.equals(prefix + ".cpp")) {
				if (templ.isDevice()) {
					file = new DResourceCppTemplate().generate(templ);
				} else {
					file = new ResourceCppTemplate().generate(templ);
				}
			} else if (fileName.equals(prefix + ".h")) {
				if (templ.isDevice()) {
					file = new DResourceHTemplate().generate(templ);
				} else {
					file = new ResourceHTemplate().generate(templ);
				}
			}
		}

		// Always generate the build files
		if (fileName.equals("build.sh")) {
			file = new BuildShTemplate().generate(templ);
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

		fileNames.add("build.sh");
		fileNames.add("reconf");

		return fileNames;
	}

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final IProject project = ModelUtil.getProject(softPkg);
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		boolean isSkeleton = false;

		for (final Property tempProp : implSettings.getProperties()) {
			if ("generate_skeleton".equals(tempProp.getId())) {
				if ("TRUE".equalsIgnoreCase(tempProp.getValue())) {
					isSkeleton = true;
					break;
				}
			}
		}

		fileNames.add("build.sh");
		fileNames.add("configure.ac");
		fileNames.add("Makefile.am");
		fileNames.add("reconf");
		fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");

		if (isSkeleton) {
			fileNames.add("main.cpp");
			if ((project == null) || !project.getFile(implSettings.getOutputDir() + IPath.SEPARATOR + prefix + ".cpp").exists()) {
				fileNames.add(prefix + ".cpp");
			}
			if ((project == null) || !project.getFile(implSettings.getOutputDir() + IPath.SEPARATOR + prefix + ".h").exists()) {
				fileNames.add(prefix + ".h");
			}
		}

		return fileNames;
	}

	public boolean shouldGenerate() {
		return true;
	}

	public String getDefaultFilename(final SoftPkg softPkg, final ImplementationSettings implSettings, final String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		return srcDir + prefix + ".cpp";
	}

}
