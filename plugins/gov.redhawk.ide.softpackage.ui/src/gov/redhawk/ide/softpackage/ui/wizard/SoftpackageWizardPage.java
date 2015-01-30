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

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SpdPackage;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class SoftpackageWizardPage extends ImplementationWizardPage {

	private static final String PAGE_TITLE = "Define Softpackage Implementation"; // TODO
	public static final String PAGE_DESCRIPTION = "Define an implementation of the softpackage library.  For example this could include x86 and x86_64 versions etc."; // TODO

	protected final SoftpackageModel model;
	protected final DataBindingContext dbc;
	private Combo typeCombo;
	private Text implText;

	protected Composite client;

	public SoftpackageWizardPage(String pagename, SoftpackageModel model, String componentType) {
		super(pagename, componentType);
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
		typeCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setItems(SoftpackageModel.TYPES);
		typeCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleTypeSelectionChanged();
			}
		});

		label = new Label(composite, SWT.NULL);
		label.setText("Implementation:");
		implText = new Text(composite, SWT.BORDER);
		implText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());

		if (model.getTypeName() != null) {
			int initialIndex = typeCombo.indexOf(model.getTypeName());
			typeCombo.select((initialIndex < 0) ? 0 : initialIndex);
		}

		this.bind();

		WizardPageSupport.create(this, dbc);
	}

	private void handleTypeSelectionChanged() {

		// TODO: use the type to determine the code generator ID, hardcoding for now
		String codeGenId = "gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusGenerator";

		// Update impl and implSettings values to match selected type
		ICodeGeneratorDescriptor tempCodeGen = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(codeGenId);
		Implementation implementation = getImplementation();
		ImplementationSettings settings = getImplSettings();

		settings.setGeneratorId(tempCodeGen.getId());
		implementation.setId(model.getTypeName());
		implementation.getCompiler().setName(tempCodeGen.getCompiler());
		implementation.getCompiler().setVersion(tempCodeGen.getCompilerVersion());
		this.getProgLang().setName(tempCodeGen.getLanguage());
	}

	@Override
	public boolean isPageComplete() {
		return !typeCombo.getText().isEmpty();
	}

	/**
	 * Creates the databindings that are used by this page
	 */
	private void bind() {
		// Bind the impl programming language to the combo box selection
		// Need null check - Multiple child classes, but one child class can have an implementation at a time
		if (this.getImplementation() != null) {
			dbc.bindValue(SWTObservables.observeText(typeCombo),
				EMFObservables.observeValue(this.getProgLang(), SpdPackage.Literals.PROGRAMMING_LANGUAGE__NAME));
		}

		// TODO: Is this binding needed at this point? Seems redundant with other programming lanuage binding, may need
		// to do one or the other
		// Binds model type name with type combo
		dbc.bindValue(SWTObservables.observeSelection(typeCombo), BeansObservables.observeValue(model, SoftpackageModel.TYPE_NAME));

		// Binds model implementation name with implementation text box, and provides validation
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
	}

	public SoftpackageModel getModel() {
		return model;
	}
}
