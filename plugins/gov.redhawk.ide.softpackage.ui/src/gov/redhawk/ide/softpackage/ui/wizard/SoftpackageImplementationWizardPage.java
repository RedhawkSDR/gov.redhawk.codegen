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

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	
	private final Model model; 
	private final DataBindingContext dbc;
	private Text variantText;

	/**
	 * The Model for this page. 
	 * Contains "Variant name" and the choice between creating a new library or using an existing one
	 */
	public class Model {
		public static final String VARIANT_NAME = "variantName"; 
		public static final String ENABLE_CREATE_NEW_LIBRARY = "createNewLibrary";
		public static final String ENABLE_USE_EXISTING_LIBRARY = "useExistingLibrary";

		private String variantName;
		private boolean createNewLibrary; 
		private boolean useExistingLibrary;

		private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

		public Model() { 
		}

		public String getVariantName() {
			return this.variantName;
		}

		public void setVariantName(String newValue) {
			final String oldValue = this.variantName;
			this.variantName = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, Model.VARIANT_NAME, oldValue, newValue));
		}
		
		public boolean isCreateNewLibrary() {
			return createNewLibrary;
		}

		public void setCreateNewLibrary(boolean newValue) {
			final boolean oldValue = this.createNewLibrary;
			this.createNewLibrary = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, Model.ENABLE_CREATE_NEW_LIBRARY, oldValue, newValue));
		}

		public boolean isUseExistingLibrary() {
			return useExistingLibrary;
		}

		public void setUseExistingLibrary(boolean newValue) {
			final boolean oldValue = this.useExistingLibrary;
			this.useExistingLibrary = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, Model.ENABLE_USE_EXISTING_LIBRARY, oldValue, newValue));
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
		model = new Model();
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

		variantText = new Text(composite, SWT.BORDER); 
		variantText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create()); 
		variantText.setText("");
		variantText.setFocus();
		variantText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				String err = variantNameFilenameSafe(variantText.getText());
				setErrorMessage(err);
				setPageComplete(err == null);
				dbc.updateModels();
			}
		});
		dbc.bindValue(SWTObservables.observeText(variantText, SWT.Modify), BeansObservables.observeValue(model, Model.VARIANT_NAME));
		setErrorMessage(null);
		
		// start from scratch or to use an existing library
		Button button = this.createRadioButton(client, "Create a new library"); 
		dbc.bindValue(WidgetProperties.selection().observe(button), BeansObservables.observeValue(model, Model.ENABLE_CREATE_NEW_LIBRARY));
		button.setSelection(true);
		
		button = this.createRadioButton(client, "Use an existing library");
		dbc.bindValue(WidgetProperties.selection().observe(button), BeansObservables.observeValue(model, Model.ENABLE_USE_EXISTING_LIBRARY));

		setPageComplete(false);
		this.setControl(client);
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
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				dbc.updateModels();
			}
		});
		return button;
	}

	protected String variantNameFilenameSafe(String str) {
		if (str == null || str.isEmpty()) {
			return "Variant name must not be empty.";
		} 
		if (!Character.isLetterOrDigit(str.charAt(0))) {
			return "First character of variant name must be alphanumeric.";
		}
		if (str.contains(" ")) {
			return "Variant name must not include spaces.";
		}
		if (!str.matches("^[a-zA-Z0-9_-]+$")) {
			return "Variant name must be filename safe.";
		}
		return null;
	} 
	
	/**
	 * Returns this instance's {@link SoftpackageImplementationWizardPage.Model}
	 * @return model
	 */
	public Model getModel() {
		return model;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return getErrorMessage() == null && !variantText.getText().isEmpty();
	}

	/**
	 * Adds given listener to this object's Model
	 * @param listener
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.model.addPropertyChangeListener(listener);
	}

}
