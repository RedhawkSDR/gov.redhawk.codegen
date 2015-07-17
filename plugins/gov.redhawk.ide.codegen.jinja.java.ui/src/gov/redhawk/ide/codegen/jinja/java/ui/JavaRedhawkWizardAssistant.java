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
package gov.redhawk.ide.codegen.jinja.java.ui;

import java.util.List;

import gov.redhawk.ide.codegen.CodegenFactory;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.ui.wizard.IRedhawkImportProjectWizardAssist;
import gov.redhawk.ide.ui.wizard.RedhawkImportWizardPage1.ProjectRecord;

/**
 * 
 */
public class JavaRedhawkWizardAssistant implements IRedhawkImportProjectWizardAssist {

	@Override
	public boolean setTemplate(ProjectRecord record, ImplementationSettings settings, String lang, ITemplateDesc templateDesc) {
		if ("Java".equals(lang)) {
			if (record.getTemplate() != null && !record.getTemplate().isEmpty()) {
				settings.setTemplate(record.getTemplate().get("java"));
			} else {
				settings.setTemplate("redhawk.codegen.jinja.java.component.pull");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean handlesImplId(String id) {
		return "java".equals(id);
	}

	@Override
	public String getDefaultTemplate() {
		return "redhawk.codegen.jinja.java.component.pull";
	}

	@Override
	public boolean handlesLanguage(String lang) {
		return "Java".equalsIgnoreCase(lang);
	}

	@Override
	public void setupWaveDev(String projectName, ImplementationSettings settings) {
		boolean hasUseJni = false;
		List<Property> properties = settings.getProperties();
		for (Property prop : properties) {
			// Validate java_package name and create a default one if necessary
			if ("java_package".equals(prop.getId())) {
				if (prop.getValue() == null || prop.getValue().isEmpty()) {
					prop.setValue(projectName + ".java");
				}
			}
			// Check for use_jni and populate if it is found but empty
			if ("use_jni".equals(prop.getId())) {
				hasUseJni = true;
				if (prop.getValue() == null || prop.getValue().isEmpty()) {
					prop.setValue("TRUE");
				}
			}
		}
		// if use_jni is not found, build it with TRUE as default
		if (!hasUseJni) {
			final Property useJni = CodegenFactory.eINSTANCE.createProperty();
			useJni.setId("use_jni");
			useJni.setValue("TRUE");
			settings.getProperties().add(useJni);
		}
	}

}
