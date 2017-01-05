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
package gov.redhawk.ide.codegen.frontend.ui.wizard;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.jinja.cplusplus.ui.wizard.BooleanGeneratorPropertiesWizardPage2;

/**
 * Intended for use with C++ and Python implementations
 */
public class FrontEndGeneratorPropertiesWizardPage extends BooleanGeneratorPropertiesWizardPage2 {
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		ITemplateDesc[] inputs = (ITemplateDesc[]) getTemplateViewer().getInput();
		ITemplateDesc feiInput = null;
		for (ITemplateDesc input : inputs) {
			if (input.getId() != null && input.getId().matches(".*frontend")) {
				feiInput = input;
				break;
			}
		}

		if (feiInput != null) {
			getTemplateViewer().setSelection(new StructuredSelection(feiInput));
		}

		getTemplateViewer().getControl().setEnabled(false);
	}

	@Override
	protected void addCustomPages() {
		// FALL THROUGH
	}

	@Override
	protected void removeCustomPages() {
		// FALL THROUGH
	}
}
