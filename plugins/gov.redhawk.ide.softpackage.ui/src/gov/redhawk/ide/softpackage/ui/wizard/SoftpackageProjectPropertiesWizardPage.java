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
import gov.redhawk.ide.spd.ui.wizard.ScaResourceProjectPropertiesWizardPage;

import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class SoftpackageProjectPropertiesWizardPage extends ScaResourceProjectPropertiesWizardPage {

	private Combo typeCombo;
	private final DataBindingContext dbc;
	private final SoftpackageModel model;

	public SoftpackageProjectPropertiesWizardPage(String pageName, String type) {
		super(pageName, type);
		setShowContentsGroup(false);
		setShowComponentIDGroup(false);
		this.model = new SoftpackageModel();
		this.dbc = new DataBindingContext();
		this.setDescription("Create a new Softpackage library");
	}

	@Override
	public void customCreateControl(Composite composite) {
		Group contentsGroup = new Group((Composite) getControl(), SWT.NONE);
		contentsGroup.setText("Contents");
		contentsGroup.setLayout(new GridLayout(2, false));
		contentsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label label = new Label(contentsGroup, SWT.NULL);
		label.setText("Type:");

		typeCombo = new Combo(contentsGroup, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setItems(SoftpackageModel.TYPES);
		typeCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SoftpackageWizardPage implPage = (SoftpackageWizardPage) getNextPage();
				implPage.handleTypeSelectionChanged();
				getWizard().getContainer().updateButtons();
			}
		});

		bind();
	}

	private void bind() {
		// Binds model type name with type combo
		dbc.bindValue(SWTObservables.observeSelection(typeCombo), BeansObservables.observeValue(model, SoftpackageModel.TYPE_NAME));
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.model.addPropertyChangeListener(listener);
	}

	@Override
	public boolean isPageComplete() {
		if (typeCombo.getText().isEmpty()) {
			return false;
		}
		
		return super.isPageComplete();
	}
	
	public SoftpackageModel getModel() {
		return model;
	}

	/*
	 * The next page is the implementation page, which we don't want users going to
	 * The implementation page just exists since we want to take advantage of two super classes, 
	 * and Java doesn't allow multiple-inheritance
	 */
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
}
