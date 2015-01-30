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

import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SoftpackageCreateNewLibraryWizardPage extends SoftpackageWizardPage {
	
	public SoftpackageCreateNewLibraryWizardPage(String pagename, String componentType) {
		super(pagename, new SoftpackageModel(true), componentType);
	}
	
	@Override 
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		final Group optionalFlags = new Group(client, SWT.NONE);
		optionalFlags.setLayout(new GridLayout(2, false));
		optionalFlags.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		optionalFlags.setText("C++ Optional Flags");
		bindEnablementToCppType(optionalFlags);
		
		Label label = new Label(optionalFlags, SWT.NULL);
		label.setText("Compiler Flags:");
		bindEnablementToCppType(label);
		
		final Text compilerFlags = new Text(optionalFlags, SWT.BORDER);
		compilerFlags.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create()); 
		bindEnablementToCppType(compilerFlags);
		dbc.bindValue(SWTObservables.observeText(compilerFlags, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.COMPILER_FLAGS));

		label = new Label(optionalFlags, SWT.NULL);
		label.setText("Linker Flags:"); 
		bindEnablementToCppType(label);
		
		final Text linkerFlags = new Text(optionalFlags, SWT.BORDER);
		linkerFlags.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create()); 
		bindEnablementToCppType(linkerFlags);
		dbc.bindValue(SWTObservables.observeText(linkerFlags, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.LINKER_FLAGS));
		
		WizardPageSupport.create(this, dbc);
	}

	/**
	 * Binds enablement given control to the model's value regarding cpp options
	 * @param control
	 */
	private void bindEnablementToCppType(Control control) {
		dbc.bindValue(SWTObservables.observeEnabled(control), BeansObservables.observeValue(model, SoftpackageModel.ENABLED_CPP_OPTIONS));
	}

}
