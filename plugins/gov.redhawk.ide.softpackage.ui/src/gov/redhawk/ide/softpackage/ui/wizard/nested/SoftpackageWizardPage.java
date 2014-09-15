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

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class SoftpackageWizardPage extends WizardPage {
	
	private static final String PAGE_TITLE = "Add Softpackage Implementations"; // TODO
	public static final String PAGE_DESCRIPTION = "Add implementations of the softpackage library.  For example this could include x86 and x86_64 versions etc."; // TODO

	protected final SoftpackageModel model;
	protected final DataBindingContext dbc;

	protected Composite client;

	public SoftpackageWizardPage(String pagenamme) {
		this(pagenamme, null);
	}

	public SoftpackageWizardPage(String pagenamme, SoftpackageModel model) {
		super(pagenamme, PAGE_TITLE, null);
		setDescription(PAGE_DESCRIPTION);
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
		Combo typeCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setItems(SoftpackageModel.TYPES);
		typeCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		dbc.bindValue(SWTObservables.observeSelection(typeCombo), BeansObservables.observeValue(model, SoftpackageModel.TYPE_NAME));

		label = new Label(composite, SWT.NULL); // a lil bit o' padding

		label = new Label(composite, SWT.NULL);
		label.setText("Implementation:");
		Text implText = new Text(composite, SWT.BORDER);
		implText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		Binding binding = dbc.bindValue(SWTObservables.observeText(implText, SWT.Modify), BeansObservables.observeValue(model, SoftpackageModel.IMPL_NAME),
			new UpdateValueStrategy().setAfterConvertValidator(new IValidator() {

				@Override
				public IStatus validate(Object value) {
					String strValue = (String) value;
					if (!strValue.isEmpty()) {
						if (strValue.contains(" ")) {
							return ValidationStatus.error("Implementation name must not include spaces.");
						} else if (!strValue.matches("^[a-zA-Z0-9._-]+$")) {
							return ValidationStatus.error("Implementation name must be filename safe.");
						}
					}
					return ValidationStatus.ok();
				}

			}), null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);

		if (model.getTypeName() != null) {
			int initialIndex = typeCombo.indexOf(model.getTypeName());
			typeCombo.select((initialIndex < 0) ? 0 : initialIndex);
		}
		
		WizardPageSupport.create(this, dbc);
	}

	/**
	 * Returns this instance's {@link SoftpackageModel}
	 * @return
	 */
	public SoftpackageModel getModel() {
		return model;
	}

}
