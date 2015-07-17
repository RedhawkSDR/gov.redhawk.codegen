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
package gov.redhawk.ide.codegen.jinja.python.ui;

import org.eclipse.core.runtime.CoreException;

import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist;
import gov.redhawk.ide.ui.wizard.RedhawkImportWizardPage1.ProjectRecord;

/**
 * 
 */
public class PythonRedhawkImportWizardAssistant implements IRedhawkImportProjectWizardAssist {

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist#handlesImplId(java.lang.String)
	 */
	@Override
	public boolean handlesImplId(String id) {
		return "python".equalsIgnoreCase(id);
	}

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist#getDefaultTemplate()
	 */
	@Override
	public String getDefaultTemplate() {
		return "redhawk.codegen.jinja.python.component.pull";
	}

	@Override
	public boolean setTemplate(ProjectRecord record, ImplementationSettings settings, String lang, ITemplateDesc templateDesc) throws CoreException {
		if ("Python".equals(lang)) {
			if (record.getTemplate() != null && !record.getTemplate().isEmpty()) {
				settings.setTemplate(record.getTemplate().get("python"));
			} else {
				settings.setTemplate("redhawk.codegen.jinja.python.component.pull");
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean handlesLanguage(String lang) {
		return "Python".equalsIgnoreCase(lang);
	}

	@Override
	public void setupWaveDev(String projectName, ImplementationSettings settings) {
		// TODO Auto-generated method stub
	}

}
