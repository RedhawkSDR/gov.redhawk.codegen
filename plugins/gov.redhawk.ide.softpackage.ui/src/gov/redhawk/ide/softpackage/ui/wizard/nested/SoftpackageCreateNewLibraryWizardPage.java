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

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SoftpackageCreateNewLibraryWizardPage extends SoftpackageWizardPage {
	
	public SoftpackageCreateNewLibraryWizardPage() {
		super(new SoftpackageModel(true));
	}
	
	public SoftpackageCreateNewLibraryWizardPage(SoftpackageModel model) {
		super(model);
	}
	
	@Override
	protected void specificCreateControl() {
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
		compilerFlags.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) { 
				dbc.updateModels();
			}
		});
		bindEnablementToCppType(compilerFlags);
		dbc.bindValue(SWTObservables.observeText(compilerFlags, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.COMPILER_FLAGS));

		label = new Label(optionalFlags, SWT.NULL);
		label.setText("Linker Flags:"); 
		bindEnablementToCppType(label);
		
		final Text linkerFlags = new Text(optionalFlags, SWT.BORDER);
		linkerFlags.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create()); 
		linkerFlags.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) { 
				dbc.updateModels();
			}
		});
		bindEnablementToCppType(linkerFlags);
		dbc.bindValue(SWTObservables.observeText(linkerFlags, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.LINKER_FLAGS));
	}

}
