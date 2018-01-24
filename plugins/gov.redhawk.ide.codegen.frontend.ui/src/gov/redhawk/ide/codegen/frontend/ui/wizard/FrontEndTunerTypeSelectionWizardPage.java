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
package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndProjectValidator;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.graphiti.dcd.ui.project.wizards.ScaDeviceProjectPropertiesWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class FrontEndTunerTypeSelectionWizardPage extends WizardPage implements ICodegenWizardPage {

	private ImplementationSettings implSettings;
	private FeiDevice feiDevice;
	private boolean apiCanFinish = true;

	private Button ingestGPSCheckbox;
	private Button outputGPSCheckbox;

	private Button receiveOnlyTunerButton;
	private Button transmitOnlyTunerButton;
	private Button bothRxTxButton;

	private Button scannerCheckbox;

	private DataBindingContext ctx;

	private FrontEndProjectValidator validator;

	public FrontEndTunerTypeSelectionWizardPage(FeiDevice feiDevice) {
		super("");
		this.feiDevice = feiDevice;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		createUIElements(client);
		createBindings();
		this.receiveOnlyTunerButton.setSelection(true);
		//		this.deviceTypeTunerButton.setSelection(true);
		this.feiDevice.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				FrontEndTunerTypeSelectionWizardPage.this.getWizard().getContainer().updateButtons();
			}
		});
		this.setControl(client);
	}

	private void createBindings() {
		// create new Context
		this.ctx = new DataBindingContext();

		// Binding for GPS ingest & output
		this.ctx.bindValue(WidgetProperties.selection().observe(this.ingestGPSCheckbox),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__INGESTS_GPS), new UpdateValueStrategy(), new UpdateValueStrategy());

		this.ctx.bindValue(WidgetProperties.selection().observe(this.outputGPSCheckbox),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__OUTPUTS_GPS), new UpdateValueStrategy(), new UpdateValueStrategy());

		// Bindings for the tuner type is difficult since it is a 3 way with the third option being both the 1st and 2nd so listeners are used.
		this.bothRxTxButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setRxTuner(true);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setTxTuner(true);
			}
		});

		this.receiveOnlyTunerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setRxTuner(true);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setTxTuner(false);
			}
		});

		this.transmitOnlyTunerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setRxTuner(false);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setTxTuner(true);
			}
		});

		// Bindings for scanner
		ctx.bindValue(WidgetProperties.selection().observe(scannerCheckbox), EMFObservables.observeValue(feiDevice, FrontendPackage.Literals.FEI_DEVICE__SCANNER));

		IWizardPage[] wizPages = this.getWizard().getPages();
		ScaDeviceProjectPropertiesWizardPage propWizPage = null;

		for (IWizardPage wizPage : wizPages) {
			if (wizPage instanceof ScaDeviceProjectPropertiesWizardPage) {
				propWizPage = (ScaDeviceProjectPropertiesWizardPage) wizPage;
				break;
			}
		}

		// This must come after the creation of the page support since creation of page support updates the 
		// error message.  The WizardPageSupport doesn't update the error message because no UI elements have changed
		// so this is a bit of a hack.
		if (propWizPage != null) {
			this.validator = new FrontEndProjectValidator(propWizPage.getProjectSettings(), this);
			ctx.addValidationStatusProvider(validator);
			IObservableValue<IStatus> validationStatus = validator.getValidationStatus();
			validationStatus.addChangeListener(new IChangeListener() {

				@Override
				public void handleChange(ChangeEvent event) {
					if (validator != null) {
						updateErrorMessage();
					}
				}
			});

			updateErrorMessage();
		}

	}

	protected void updateErrorMessage() {
		IStatus status = (IStatus) this.validator.getValidationStatus().getValue();
		if (status.isOK()) {
			this.setErrorMessage(null);
		} else {
			this.setErrorMessage(status.getMessage());
		}
	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));

		this.setTitle("FrontEnd Interfaces Device Type Selection");
		this.setDescription("Select options about the type of device to be created.");

		// GPS Usage Group
		Group gpsUsageGroup = new Group(client, SWT.BORDER);
		gpsUsageGroup.setLayout(new GridLayout(1, false));
		gpsUsageGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		gpsUsageGroup.setText("GPS Usage");

		this.ingestGPSCheckbox = new Button(gpsUsageGroup, SWT.CHECK);
		this.ingestGPSCheckbox.setText("This device will ingest GPS data");
		this.ingestGPSCheckbox.setLayoutData(GridDataFactory.fillDefaults().create());
		this.ingestGPSCheckbox.setToolTipText("Selecting this checkbox will add an input GPS Info port.");

		this.outputGPSCheckbox = new Button(gpsUsageGroup, SWT.CHECK);
		this.outputGPSCheckbox.setText("This device will output GPS data");
		this.outputGPSCheckbox.setLayoutData(GridDataFactory.fillDefaults().create());
		this.outputGPSCheckbox.setToolTipText("Selecting this checkbox will add an output GPS Info port.");

		// Tuner Type Group
		Group tunerTypeGroup = new Group(client, SWT.BORDER);
		tunerTypeGroup.setText("Tuner Type");
		tunerTypeGroup.setLayout(new GridLayout(1, false));
		tunerTypeGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		this.receiveOnlyTunerButton = new Button(tunerTypeGroup, SWT.RADIO);
		this.receiveOnlyTunerButton.setText("Receive only (default)");
		this.receiveOnlyTunerButton.setLayoutData(GridDataFactory.fillDefaults().create());

		this.transmitOnlyTunerButton = new Button(tunerTypeGroup, SWT.RADIO);
		this.transmitOnlyTunerButton.setText("Transmit only");
		this.transmitOnlyTunerButton.setLayoutData(GridDataFactory.fillDefaults().create());

		this.bothRxTxButton = new Button(tunerTypeGroup, SWT.RADIO);
		this.bothRxTxButton.setText("Both receive and transmit");
		this.bothRxTxButton.setLayoutData(GridDataFactory.fillDefaults().create());

		// Scanner capability
		Group scannerGroup = new Group(client, SWT.BORDER);
		scannerGroup.setText("Scanner Capability");
		scannerGroup.setLayout(new GridLayout(1, false));
		scannerGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		scannerCheckbox = new Button(scannerGroup, SWT.CHECK);
		scannerCheckbox.setText("This device provides a programmable scanning capability");
		scannerCheckbox.setLayoutData(GridDataFactory.fillDefaults().create());
		scannerCheckbox.setToolTipText("Selecting this will ensure the control ports implement FRONTEND::ScanningTuner and add applicable properties.");
	}

	@Override
	public void configure(SoftPkg softpkg, Implementation impl, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		this.implSettings = implSettings;

	}

	@Override
	public ImplementationSettings getSettings() {
		return this.implSettings;
	}

	@Override
	public boolean canFinish() {
		return this.isPageComplete();
	}

	@Override
	public boolean isPageComplete() {
		// Page is complete as long as the validator is okay.
		if (this.validator == null && this.apiCanFinish) {
			return true;
		} else if (this.validator != null && ((IStatus) this.validator.getValidationStatus().getValue()).isOK() && this.apiCanFinish) {
			return super.isPageComplete();
		} else {
			return false;
		}
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		// Ignored
	}

	@Override
	public void setCanFinish(boolean canFinish) {
		this.apiCanFinish = canFinish;
	}

	public FeiDevice getFeiDevice() {
		return this.feiDevice;
	}

}
