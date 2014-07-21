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
package gov.redhawk.ide.softpackage.ui.wizard.nested;

import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;

import org.eclipse.jface.wizard.Wizard;

public class SoftpackageTableWizard extends Wizard {

	private final SoftpackageWizardPage page;
	private SoftpackageModel model;

	public SoftpackageTableWizard(boolean createNewLibrary) {
		this(createNewLibrary, null);
	}

	public SoftpackageTableWizard(boolean createNewLibrary, SoftpackageModel model) {
		if (createNewLibrary) {
			page = new SoftpackageCreateNewLibraryWizardPage(model);
		} else {
			page = new SoftpackageUseExistingLibraryWizardPage(model);
		} 
	}

	@Override
	public void addPages() {
		addPage(page);
	} 

	@Override
	public boolean performFinish() {
		this.model = page.getModel();
		return true;
	} 

	public SoftpackageModel getModel() {
		return this.model;
	}
}
