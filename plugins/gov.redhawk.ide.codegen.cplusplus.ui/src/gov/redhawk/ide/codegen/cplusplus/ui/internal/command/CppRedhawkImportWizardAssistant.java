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
package gov.redhawk.ide.codegen.cplusplus.ui.internal.command;

import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.manual.ManualGeneratorPlugin;
import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist;
import gov.redhawk.ide.ui.wizard.RedhawkImportWizardPage1.ProjectRecord;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;

public class CppRedhawkImportWizardAssistant implements IRedhawkImportProjectWizardAssist {

	@Override
	public boolean setTemplate(ProjectRecord record, ImplementationSettings settings, String lang, ITemplateDesc templateDesc) {
		if ("C++".equals(lang)) {
			if (record.getTemplate() != null) {
				settings.setTemplate(record.getTemplate());
			} else {
				settings.setTemplate("redhawk.codegen.jinja.cpp.component.pull");
			}
			return true;
		}
		return false;
	}

	@Override
	public void setupNatures(List<String> natures, IProject dotProject, IProgressMonitor monitor) {
		if (natures.contains("cpp")) {
			MultiStatus retStatus = new MultiStatus(ManualGeneratorPlugin.PLUGIN_ID, IStatus.OK, "", null);
			CppGeneratorUtils.addCandCPPNatures(dotProject, SubMonitor.convert(monitor), retStatus);
			CppGeneratorUtils.addManagedNature(dotProject, SubMonitor.convert(monitor), retStatus, "/", System.out, true, null);
		}
	}

	@Override
	public void setupNatures(File importSource, IProject project, IProgressMonitor monitor) {
		if (new File(importSource + "/cpp").exists()) {
			MultiStatus retStatus = new MultiStatus(ManualGeneratorPlugin.PLUGIN_ID, IStatus.OK, "", null);
			CppGeneratorUtils.addCandCPPNatures(project, SubMonitor.convert(monitor), retStatus);
			CppGeneratorUtils.addManagedNature(project, SubMonitor.convert(monitor), retStatus, "/", System.out, true, null);
		}
	}

	@Override
	public boolean handlesImplId(String id) {
		return "cpp".equalsIgnoreCase(id);
	}
	
	@Override
	public boolean handlesLanguage(String lang) {
		return "C++".equalsIgnoreCase(lang);
	}

	@Override
	public String getDefaultTemplate() {
		return "redhawk.codegen.jinja.cpp.component.pull";
	}

	@Override
	public void setupWaveDev(String projectName, ImplementationSettings settings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handlesNature(String nature) {
		return "cpp".equalsIgnoreCase(nature);
	}

}
