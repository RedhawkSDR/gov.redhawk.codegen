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

import gov.redhawk.eclipsecorba.idl.Definition;
import gov.redhawk.eclipsecorba.idl.IdlInterfaceDcl;
import gov.redhawk.eclipsecorba.library.IdlLibrary;
import gov.redhawk.eclipsecorba.library.RepositoryModule;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceWizardPlugin;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndProjectValidator;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.dcd.ui.wizard.ScaDeviceProjectPropertiesWizardPage;
import gov.redhawk.ui.RedhawkUiActivator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.statushandlers.StatusManager;

public class FrontEndTunerOptionsWizardPage extends WizardPage implements ICodegenWizardPage {

	private ImplementationSettings implSettings;
	private FeiDevice feiDevice;
	private Composite client;
	private DataBindingContext ctx;
	private Definition[] propertyTypes;
	private Composite parent;
	private FrontEndProjectValidator validator;
	private IBaseLabelProvider definitionComboViewerLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			final Definition def = (Definition) element;
			return def.getName();
		}
	};

	public FrontEndTunerOptionsWizardPage(FeiDevice feiDevice) {
		super("");
		this.feiDevice = feiDevice;
		populatePropertyTypes();

		// Initialize the model object
		if (propertyTypes != null && propertyTypes.length > 0) {
			this.feiDevice.setDigitalInputTypeForTx(propertyTypes[0]);
			this.feiDevice.setDigitalInputType(propertyTypes[0]);
			this.feiDevice.setDigitalOutputType(propertyTypes[0]);
		} 
	}

	@Override
	public void createControl(Composite parent) {
		this.setTitle("FrontEnd Interface Tuner Options");
		this.setDescription("Select the input and output types for this FrontEnd Interfaces Tuner Device");
		this.parent = parent;
		client = new Composite(parent, SWT.NULL);
		ctx = new DataBindingContext();

		createUIElements(client);

		this.setControl(client);

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
			IObservableValue validationStatus = validator.getValidationStatus();
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

		if (this.propertyTypes == null || this.propertyTypes.length == 0) {
			this.validator.setCustomValidationStatus(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to Load IDL Library.", null));
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

	private void populatePropertyTypes() {
		final IdlLibrary idlLibrary = RedhawkUiActivator.getDefault().getIdlLibraryService().getLibrary();
		RepositoryModule bulkioIdl;
		List<Definition> bulkioTypes = new ArrayList<Definition>();

		IStatus loadStatus = idlLibrary.getLoadStatus();
		
		if (loadStatus == null || loadStatus.getSeverity() == IStatus.ERROR) {
			try {
				new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(true, true, new IRunnableWithProgress() {
					
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						try {
							idlLibrary.load(monitor);
						} catch (CoreException e) {
							throw new InvocationTargetException(e);
						}
						
					}
				});
			} catch (InterruptedException e) {
				// PASS
			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to Load IDL Library.", e), StatusManager.SHOW | StatusManager.LOG);
			}
		}
		
		// Grab array of available BULKIO types
		for (Definition def : idlLibrary.getDefinitions()) {
			if ("BULKIO".equals(def.getName())) {
				bulkioIdl = (RepositoryModule) def;

				// We don't want these to show up in any combos
				List<String> removedTypes = new ArrayList<String>();
				removedTypes.add("IDL:BULKIO/ProvidesPortStatisticsProvider:1.0");
				removedTypes.add("IDL:BULKIO/UsesPortStatisticsProvider:1.0");
				removedTypes.add("IDL:BULKIO/dataFile:1.0");
				removedTypes.add("IDL:BULKIO/updateSRI:1.0");
				removedTypes.add("IDL:BULKIO/dataXML:1.0");

				for (Definition bulkioDef : bulkioIdl.getDefinitions()) {
					if (bulkioDef instanceof IdlInterfaceDcl) {
						if (removedTypes.contains(bulkioDef.getRepId())) {
							continue;
						}
						bulkioTypes.add(bulkioDef);
					}
				}
			}
		}
		Collections.sort(bulkioTypes, new Comparator<Definition>() {

			@Override
			public int compare(Definition o1, Definition o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
			
		});
		propertyTypes = bulkioTypes.toArray(new Definition[0]);
	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));

		if (!feiDevice.isTxTuner()) {
			createReceiverGroup(client);
		} else if (!feiDevice.isRxTuner()) {
			createTransmitterGroup(client);
		} else {
			createReceiverGroup(client);
			createTransmitterGroup(client);
		}
	}

	// Create Receiver Group and all sub-methods
	private void createReceiverGroup(Composite client) {
		Group receiverGroup = new Group(client, SWT.SHADOW_ETCHED_IN);
		receiverGroup.setText("Receiver Properties");
		receiverGroup.setLayout(new GridLayout(2, false));
		receiverGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		createInputControl(receiverGroup);
		createOutputControl(receiverGroup);
	}

	private void createInputControl(Group parent) {
		Composite inputContainer = new Composite(parent, SWT.None);
		inputContainer.setLayout(new GridLayout(1, false));
		inputContainer.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		GridData layoutData = GridDataFactory.fillDefaults().grab(true, false).indent(30, 0).align(SWT.LEFT, SWT.CENTER).create();

		Button analogInputButton = new Button(inputContainer, SWT.RADIO);
		analogInputButton.setText("Analog Input (default)");
		analogInputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		createAnalogIn(inputContainer).setLayoutData(layoutData);
		UpdateValueStrategy uvs = booleanConverter();
		ctx.bindValue(WidgetProperties.selection().observe(analogInputButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT), uvs, uvs);

		Button digitalInputButton = new Button(inputContainer, SWT.RADIO);
		digitalInputButton.setText("Digital Input");
		digitalInputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		createDigitalIn(inputContainer).setLayoutData(layoutData);
		ctx.bindValue(WidgetProperties.selection().observe(digitalInputButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT));

		digitalInputButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				feiDevice.setHasDigitalOutput(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private Composite createAnalogIn(Composite parent) {
		Composite analogIn = new Composite(parent, SWT.SHADOW_NONE);
		analogIn.setLayout(new GridLayout(2, false));

		Label numAnalogLabel = new Label(analogIn, SWT.None);
		numAnalogLabel.setText("Number of Analog input ports: ");

		Spinner numAnalogSpinner = new Spinner(analogIn, SWT.BORDER);
		numAnalogSpinner.setMinimum(1);
		GC gc = new GC(numAnalogSpinner);
		try {
			Point size = gc.textExtent("XXX");
			numAnalogSpinner.setLayoutData(GridDataFactory.fillDefaults().hint(size.x, SWT.DEFAULT).create());
		} finally {
			gc.dispose();
		}
		UpdateValueStrategy uvs = booleanConverter();
		ctx.bindValue(WidgetProperties.selection().observe(numAnalogSpinner),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS));
		ctx.bindValue(WidgetProperties.enabled().observe(numAnalogSpinner),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT), uvs, uvs);
		return analogIn;
	}

	private Composite createDigitalIn(Composite parent) {
		Composite digitalIn = new Composite(parent, SWT.SHADOW_NONE);
		digitalIn.setLayout(new GridLayout(2, false));

		Label digitalInputTypeLabel = new Label(digitalIn, SWT.None);
		digitalInputTypeLabel.setText("Digital Input Type: ");

		ComboViewer digitalInputCombo = new ComboViewer(digitalIn, SWT.READ_ONLY);
		digitalInputCombo.setContentProvider(new ArrayContentProvider());
		digitalInputCombo.setLabelProvider(definitionComboViewerLabelProvider);
		digitalInputCombo.setInput(propertyTypes);
		ctx.bindValue(ViewersObservables.observeSingleSelection(digitalInputCombo),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__DIGITAL_INPUT_TYPE));

		ctx.bindValue(WidgetProperties.enabled().observe(digitalInputCombo.getCombo()),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT));
		return digitalIn;
	}

	private void createOutputControl(Group parent) {
		Composite outputContainer = new Composite(parent, SWT.None);
		outputContainer.setLayout(new GridLayout(1, false));
		outputContainer.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		Button analogOutputButton = new Button(outputContainer, SWT.RADIO);
		analogOutputButton.setText("Analog Output");
		analogOutputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		UpdateValueStrategy uvs = booleanConverter();
		ctx.bindValue(WidgetProperties.enabled().observe(analogOutputButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT), uvs, uvs);
		ctx.bindValue(WidgetProperties.selection().observe(analogOutputButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_OUTPUT), uvs, uvs);

		Button digitalOutputButton = new Button(outputContainer, SWT.RADIO);
		digitalOutputButton.setText("Digital Output (default)");
		digitalOutputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		createDigitalOut(outputContainer);
		ctx.bindValue(WidgetProperties.selection().observe(digitalOutputButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_OUTPUT));
	}

	private Composite createDigitalOut(Composite parent) {
		Composite digitalOut = new Composite(parent, SWT.SHADOW_NONE);
		digitalOut.setLayout(new GridLayout(2, false));
		digitalOut.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).indent(30, 0).align(SWT.CENTER, SWT.CENTER).create());

		Label digitalOutputTypeLabel = new Label(digitalOut, SWT.None);
		digitalOutputTypeLabel.setText("Digital Output Type: ");

		ComboViewer digitalOutputCombo = new ComboViewer(digitalOut, SWT.READ_ONLY);
		digitalOutputCombo.setContentProvider(new ArrayContentProvider());
		digitalOutputCombo.setLabelProvider(definitionComboViewerLabelProvider);
		digitalOutputCombo.setInput(propertyTypes);
		ctx.bindValue(ViewersObservables.observeSingleSelection(digitalOutputCombo),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__DIGITAL_OUTPUT_TYPE));
		ctx.bindValue(WidgetProperties.enabled().observe(digitalOutputCombo.getCombo()),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_OUTPUT));

		Button multiOutCheck = new Button(digitalOut, SWT.CHECK);
		multiOutCheck.setText("Multi-out");
		ctx.bindValue(WidgetProperties.selection().observe(multiOutCheck),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__MULTI_OUT));
		ctx.bindValue(WidgetProperties.enabled().observe(multiOutCheck),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_OUTPUT));
		return digitalOut;
	} //End Receiver Group

	//Create Transmitter Group and all sub-methods
	private void createTransmitterGroup(Composite client) {
		Group transmitterGroup = new Group(client, SWT.SHADOW_ETCHED_IN);
		transmitterGroup.setText("Transmitter Properties");
		transmitterGroup.setLayout(new GridLayout(4, false));
		transmitterGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		Label numDigitalInLabel = new Label(transmitterGroup, SWT.None);
		numDigitalInLabel.setText("Number of Digital input ports: ");

		Spinner numDigitalSpinner = new Spinner(transmitterGroup, SWT.BORDER);
		GC gc = new GC(numDigitalSpinner);
		try {
			Point size = gc.textExtent("XXX");
			numDigitalSpinner.setLayoutData(GridDataFactory.fillDefaults().hint(size.x, SWT.DEFAULT).create());
		} finally {
			gc.dispose();
		}
		numDigitalSpinner.setMinimum(1);
		ctx.bindValue(WidgetProperties.selection().observe(numDigitalSpinner),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX));

		Label digitalInputTypeLabel = new Label(transmitterGroup, SWT.None);
		digitalInputTypeLabel.setText("Digital Input Type: ");
		digitalInputTypeLabel.setLayoutData(GridDataFactory.fillDefaults().indent(50, 0).align(SWT.CENTER, SWT.CENTER).create());

		ComboViewer digitalInputCombo = new ComboViewer(transmitterGroup, SWT.READ_ONLY);
		digitalInputCombo.setContentProvider(new ArrayContentProvider());
		digitalInputCombo.setLabelProvider(definitionComboViewerLabelProvider);
		digitalInputCombo.setInput(propertyTypes);

		ctx.bindValue(ViewersObservables.observeSingleSelection(digitalInputCombo),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX));
	} //End Transmitter Group

	private UpdateValueStrategy booleanConverter() {
		UpdateValueStrategy converter = new UpdateValueStrategy();
		converter.setConverter(new IConverter() {

			@Override
			public Object getToType() {
				return Boolean.class;
			}

			@Override
			public Object getFromType() {
				return Boolean.class;
			}

			@Override
			public Object convert(Object fromObject) {
				return !((Boolean) fromObject).booleanValue();
			}
		});
		return converter;
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
		if (this.validator == null) {
			return true;
		} else if (((IStatus) this.validator.getValidationStatus().getValue()).isOK()) {
			return super.isPageComplete();
		} else {
			return false;
		}
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCanFinish(boolean canFinish) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			client.dispose();
			createControl(parent);
			parent.layout(true, true);
		}
	}

}
