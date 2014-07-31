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

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory; 
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent; 
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class SoftpackageWizardPage extends WizardPage { 

	public static final String PAGE_TITLE = "Add Softpackage"; //TODO
	public static final String PAGE_DESCRIPTION = ""; //TODO

	protected final SoftpackageModel model;
	protected final DataBindingContext dbc;
	protected Text implText;
	protected Combo typeCombo;
	protected Composite client;  

	public SoftpackageWizardPage() {
		this(null);
	}

	public SoftpackageWizardPage(SoftpackageModel model) {
		super(PAGE_TITLE);
		this.setTitle(PAGE_TITLE);
		this.setDescription(PAGE_DESCRIPTION);
		this.model = (model == null) ? new SoftpackageModel() : model;
		dbc = new DataBindingContext();
	}

	@Override
	public void createControl(Composite parent) {
		client = new Composite(parent, SWT.NULL);
		client.setLayout(new GridLayout(1, false)); 
		client.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		this.setControl(client);

		Composite composite = new Composite(client, SWT.NULL);
		composite.setLayout(new GridLayout(5, false)); 
		composite.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create()); 

		Label label = new Label(composite, SWT.NULL); 
		label.setText("Type:"); 
		typeCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setItems(SoftpackageModel.TYPES); 
		typeCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		typeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) { 
				dbc.updateModels();
			}
		});
		dbc.bindValue(SWTObservables.observeSelection(typeCombo), BeansObservables.observeValue(model, SoftpackageModel.TYPE_NAME));

		label = new Label(composite, SWT.NULL); //a lil bit o' padding

		label = new Label(composite, SWT.NULL); 
		label.setText("Implementation:"); 
		implText = new Text(composite, SWT.BORDER);
		implText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		implText.addModifyListener(new  ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				dbc.updateModels();
				validatePage();
			}
		});
		dbc.bindValue(SWTObservables.observeText(implText, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.IMPL_NAME)); 

		int initialIndex = typeCombo.indexOf(model.getTypeName());
		typeCombo.select((initialIndex < 0) ? 0 : initialIndex); 
	} 

	/**
	 * Binds enablement given control to the model's value regarding cpp options
	 * @param control
	 */
	protected void bindEnablementToCppType(Control control) {
		dbc.bindValue(SWTObservables.observeEnabled(control), BeansObservables.observeValue(model, SoftpackageModel.ENABLED_CPP_OPTIONS));
	}

	/**
	 * Returns this instance's {@link SoftpackageModel}
	 * @return
	 */
	public SoftpackageModel getModel() {
		return model;
	}

	protected void validatePage() {
		String err = null;
		if (!implText.getText().isEmpty()) {
			if (implText.getText().contains(" ")) {
				err = "Implementation name must not include spaces.";
			} else if (!implText.getText().matches("^[a-zA-Z0-9_-]+$")) {
				err = "Implementation name must be filename safe.";
			} 
		}
		setErrorMessage(err);
		setPageComplete(err == null);
	}

}
