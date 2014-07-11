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
package gov.redhawk.ide.softpackage.ui.wizard;

import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @since 8.1
 */
public class SoftpackageImplementationWizardPage extends ImplementationWizardPage {

	public SoftpackageImplementationWizardPage(String name, String componenttype) {
		super(name, componenttype);
	}
	
	
	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		this.setControl(client);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
}
