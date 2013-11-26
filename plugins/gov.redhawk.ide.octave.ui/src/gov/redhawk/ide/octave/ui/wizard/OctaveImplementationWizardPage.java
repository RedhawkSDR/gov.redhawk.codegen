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
package gov.redhawk.ide.octave.ui.wizard;

import java.util.Arrays;
import java.util.List;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusOctaveGenerator;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 * @since 8.1
 */
public class OctaveImplementationWizardPage extends ImplementationWizardPage {

	public OctaveImplementationWizardPage(String name, String componenttype) {
		super(name, componenttype);
	}
	
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		// Set the Programming language viewer to C++ 

		List<String> languageOptions = Arrays.asList(RedhawkCodegenActivator.getCodeGeneratorsRegistry().getLanguages());
		int indexOfCpp = languageOptions.indexOf("C++");  // TODO: Is there a better way to reference C++ without using a string literal?
		
		this.getProgLangEntryViewer().select(indexOfCpp);
		this.getProgLangEntryViewer().setEnabled(false);

		this.handleProgLangSelection();
		
		// We need to acquire the list of code generators for C++ and then pick the Octave one
		final String temp = this.getProgLangEntryViewer().getText();
		final ICodeGeneratorDescriptor[] tempCodegens = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegenByLanguage(temp, this.getComponenttype());
		
		for (ICodeGeneratorDescriptor codeGeneratorDescriptor : tempCodegens) {
			if (codeGeneratorDescriptor.getId().trim().equals(CplusplusOctaveGenerator.ID)) { 
				this.getCodeGeneratorEntryViewer().setSelection(new StructuredSelection(codeGeneratorDescriptor), true);
				this.handleCodeGenerationSelection(new StructuredSelection(codeGeneratorDescriptor));
				break;
			}
		}
		
		this.getCodeGeneratorEntryViewer().getCombo().setEnabled(false);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
}
