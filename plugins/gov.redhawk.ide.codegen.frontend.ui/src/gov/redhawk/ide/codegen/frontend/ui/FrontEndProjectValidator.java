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
package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.ide.dcd.ui.wizard.DeviceProjectSettings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardPage;

public class FrontEndProjectValidator extends ValidationStatusProvider {

	private final WritableValue status = new WritableValue();
	private final IObservableList models;
	private DeviceProjectSettings projectSettings;
	private IObservableList list = new WritableList();
	private WizardPage page;
	{
		list.add(status);
	}

	public FrontEndProjectValidator(final DeviceProjectSettings projectSettings, WizardPage page) {
		this.status.setValue(Status.OK_STATUS);
		this.models = Observables.emptyObservableList();
		this.projectSettings = projectSettings;
		this.page = page;

		if (!"Device".equals(this.projectSettings.getDeviceType())) {
			this.status.setValue(new Status(IStatus.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Front End Device must be of type: Device"));
		}

		if (this.projectSettings.isAggregate()) {
			this.status.setValue(new Status(IStatus.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Front End Device may not be an Aggregtate Device"));
		}

		updateWizardMessage();

		//  Track the project settings in case they change.
		this.projectSettings.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (!"Device".equals(projectSettings.getDeviceType())) {
					status.setValue(new Status(IStatus.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Front End Device must be of type: Device"));
				} else if (projectSettings.isAggregate()) {
					status.setValue(new Status(IStatus.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Front End Device may not be an Aggregtate Device"));
				} else {
					status.setValue(new Status(IStatus.OK, FrontEndDeviceWizardPlugin.PLUGIN_ID, ""));
				}

				updateWizardMessage();
			}
		});
	}

	protected void updateWizardMessage() {
		if (((IStatus) this.status.getValue()).isOK()) {
			this.page.setErrorMessage(null);
		} else {
			this.page.setErrorMessage(((IStatus) this.status.getValue()).getMessage());
		}
	}

	@Override
	public IObservableValue getValidationStatus() {
		return this.status;
	}

	@Override
	public IObservableList getTargets() {
		return this.list;
	}

	@Override
	public IObservableList getModels() {
		return this.models;
	}

}
