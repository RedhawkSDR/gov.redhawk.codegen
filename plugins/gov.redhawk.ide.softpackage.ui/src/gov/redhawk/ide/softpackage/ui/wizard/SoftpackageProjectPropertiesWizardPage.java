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

import gov.redhawk.ide.spd.ui.wizard.ScaResourceProjectPropertiesWizardPage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SoftpackageProjectPropertiesWizardPage extends ScaResourceProjectPropertiesWizardPage {

	private String type;
	private final SoftpackageImplementationWizardPageModel model;
	private final DataBindingContext dbc;

	public SoftpackageProjectPropertiesWizardPage(String pageName, String type) {
		super(pageName, type);
		setShowContentsGroup(false);
		this.type = type;
		this.dbc = new DataBindingContext();
		this.model = new SoftpackageImplementationWizardPageModel();
	}

	/**
	 * The Model for this page.
	 * Used to update page two of the wizard depending on if 'new' or 'existing' project is selected
	 */
	public class SoftpackageImplementationWizardPageModel {
		public static final String CREATE_NEW_LIBRARY = "createNewLibrary";

		private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		private boolean createNewLibrary = true;

		public boolean isCreateNewLibrary() {
			return createNewLibrary;
		}

		public void setCreateNewLibrary(boolean newValue) {
			final boolean oldValue = this.createNewLibrary;
			this.createNewLibrary = newValue;
			this.pcs.firePropertyChange(new PropertyChangeEvent(this, SoftpackageImplementationWizardPageModel.CREATE_NEW_LIBRARY, oldValue, newValue));
		}

		public void addPropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.removePropertyChangeListener(listener);
		}

	}

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.spd.ui.wizard.ScaResourceProjectPropertiesWizardPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Group spContentsGroup = new Group((Composite) getControl(), SWT.NONE);
		spContentsGroup.setText("Contents");
		spContentsGroup.setLayout(new GridLayout(1, true));
		spContentsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		final Button createNewResourceButton = new Button(spContentsGroup, SWT.RADIO);
		createNewResourceButton.setLayoutData(new GridLayout(1, true));
		createNewResourceButton.setText("Create a new " + type.toLowerCase() + " library");
		createNewResourceButton.setLayoutData(GridDataFactory.fillDefaults().create());
		createNewResourceButton.setSelection(true);
		dbc.bindValue(WidgetProperties.selection().observe(createNewResourceButton),
			BeansObservables.observeValue(model, SoftpackageImplementationWizardPageModel.CREATE_NEW_LIBRARY));

		Button useExistingButton = new Button(spContentsGroup, SWT.RADIO);
		GridDataFactory.generate(useExistingButton, 1, 1);
		useExistingButton.setText("Use existing library" + type.toLowerCase() + " library");
		useExistingButton.setLayoutData(GridDataFactory.fillDefaults().create());
	}

	public SoftpackageImplementationWizardPageModel getModel() {
		return model;
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.model.addPropertyChangeListener(listener);
	}

}
