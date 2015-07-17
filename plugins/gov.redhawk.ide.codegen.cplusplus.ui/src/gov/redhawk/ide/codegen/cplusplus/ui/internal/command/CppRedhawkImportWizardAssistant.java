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
import gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist;
import gov.redhawk.ide.ui.wizard.RedhawkImportWizardPage1.ProjectRecord;

public class CppRedhawkImportWizardAssistant implements IRedhawkImportProjectWizardAssist {

	@Override
	public boolean setTemplate(ProjectRecord record, ImplementationSettings settings, String lang, ITemplateDesc templateDesc) {
		if ("C++".equals(lang)) {
			if (record.getTemplate() != null && !record.getTemplate().isEmpty()) {
				settings.setTemplate(record.getTemplate().get("cpp"));
			} else {
				settings.setTemplate("redhawk.codegen.jinja.cpp.component.pull");
			}
			return true;
		}
		return false;
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

}
