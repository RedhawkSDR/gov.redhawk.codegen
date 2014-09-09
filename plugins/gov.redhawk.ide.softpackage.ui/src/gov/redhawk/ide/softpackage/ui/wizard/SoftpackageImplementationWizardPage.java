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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @since 8.1
 */
public class SoftpackageImplementationWizardPage extends ImplementationWizardPage {

	public static final String PAGE_DESCRIPTION = "Provide a variant identifier, the version for the project.";

	private final SoftpackageImplementationWizardPageModel model;
	private final DataBindingContext dbc;

	private boolean pageComplete;

	/**
	 * The Model for this page.
	 * Contains "Variant name" and the choice between creating a new library or using an existing one
	 */
	public class SoftpackageImplementationWizardPageModel {
		public static final String VARIANT_NAME = "variantName";
		public static final String ENABLE_CREATE_NEW_LIBRARY = "createNewLibrary";
		public static final String ENABLE_USE_EXISTING_LIBRARY = "useExistingLibrary";

		private String variantName;
		private boolean createNewLibrary = true;
		private boolean useExistingLibrary;

		private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

		public String getVariantName() {
			return this.variantName;
		}

		public void setVariantName(String newValue) {
			final String oldValue = this.variantName;
			this.variantName = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, SoftpackageImplementationWizardPageModel.VARIANT_NAME, oldValue, newValue));
		}

		public boolean isCreateNewLibrary() {
			return createNewLibrary;
		}

		public void setCreateNewLibrary(boolean newValue) {
			final boolean oldValue = this.createNewLibrary;
			this.createNewLibrary = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, SoftpackageImplementationWizardPageModel.ENABLE_CREATE_NEW_LIBRARY, oldValue, newValue));
		}

		public boolean isUseExistingLibrary() {
			return useExistingLibrary;
		}

		public void setUseExistingLibrary(boolean newValue) {
			final boolean oldValue = this.useExistingLibrary;
			this.useExistingLibrary = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, SoftpackageImplementationWizardPageModel.ENABLE_USE_EXISTING_LIBRARY, oldValue, newValue));
		}

		public void addPropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.removePropertyChangeListener(listener);
		}

	}

	public SoftpackageImplementationWizardPage(String name, String componenttype) {
		super(name, componenttype);
		this.setDescription(PAGE_DESCRIPTION);
		model = new SoftpackageImplementationWizardPageModel();
		dbc = new DataBindingContext();
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		// TODO variant identifier, the version for the project
		Composite composite = new Composite(client, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		Label label = new Label(composite, SWT.NULL);
		label.setText("Variant:");
		label.setLayoutData(GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).create());

		Text variantText = new Text(composite, SWT.BORDER);
		variantText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		variantText.setText("");

		Binding binding = dbc.bindValue(SWTObservables.observeText(variantText, SWT.Modify),
			BeansObservables.observeValue(model, SoftpackageImplementationWizardPageModel.VARIANT_NAME),
			new UpdateValueStrategy().setAfterConvertValidator(new IValidator() {

				@Override
				public IStatus validate(Object value) {
					String str = (String) value;
					if (str == null || str.isEmpty()) {
						return ValidationStatus.error("Variant name must not be empty.");
					}
					if (!Character.isLetterOrDigit(str.charAt(0))) {
						return ValidationStatus.error("First character of variant name must be alphanumeric.");
					}
					if (str.contains(" ")) {
						return ValidationStatus.error("Variant name must not include spaces.");
					}
					if (!str.matches("^[a-zA-Z0-9._-]+$")) {
						return ValidationStatus.error("Variant name must be filename safe.");
					}
					return ValidationStatus.ok();
				}

			}), null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);

		// start from scratch or to use an existing library
		Button button = this.createRadioButton(client, "Create a new library");
		dbc.bindValue(WidgetProperties.selection().observe(button),
			BeansObservables.observeValue(model, SoftpackageImplementationWizardPageModel.ENABLE_CREATE_NEW_LIBRARY));

		button = this.createRadioButton(client, "Use an existing library");
		dbc.bindValue(WidgetProperties.selection().observe(button),
			BeansObservables.observeValue(model, SoftpackageImplementationWizardPageModel.ENABLE_USE_EXISTING_LIBRARY));

		this.setControl(client);

		WizardPageSupport.create(this, dbc);

		variantText.setFocus();
	}

	/**
	 * Creates a radio button within the client using the given text
	 * which updates the databinding context upon selection.
	 * @param client
	 * @param text
	 * @return
	 */
	private Button createRadioButton(Composite client, String text) {
		final Button button = new Button(client, SWT.RADIO);
		button.setText(text);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(button);
		return button;
	}

	/**
	 * Returns this instance's {@link SoftpackageImplementationWizardPage.SoftpackageImplementationWizardPageModel}
	 * @return model
	 */
	public SoftpackageImplementationWizardPageModel getModel() {
		return model;
	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete() && getNextPage() != null;
	}

	@Override
	public void setPageComplete(boolean complete) {
		pageComplete = complete;
		super.setPageComplete(complete);
	}

	@Override
	public boolean isPageComplete() {
		return pageComplete;
	}

	/**
	 * Adds given listener to this object's Model
	 * @param listener
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.model.addPropertyChangeListener(listener);
	}

}
