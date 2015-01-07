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

import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.jet.java.ui.JavaJetGeneratorPropertiesWizardPage;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

// In the standard use of the JavaJetGeneratorPropertiesWizardPage the wizad page will dynamically add pages
// in the FEI implementation the parent generator does this.  
// In this class we simply prevent the addition of dynamic pages being added and lock down the template choice 

public class FrontEndJavaGeneratorPropertiesWizardPage extends JavaJetGeneratorPropertiesWizardPage {
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		ITemplateDesc[] inputs = (ITemplateDesc[]) getTemplateViewer().getInput();
		ITemplateDesc feiInput = null;
		
		for (ITemplateDesc input:inputs) {
			if ("redhawk.codegen.jinja.java.component.frontend".equals(input.getId())) {
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
