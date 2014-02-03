package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdPackage;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class FrontEndTunerTypeSelectionWizardPage extends WizardPage implements ICodegenWizardPage {

	private SoftPkg softpkg;
	private Implementation impl;
	private ICodeGeneratorDescriptor codeGenDescriptor;
	private ImplementationSettings implSettings;
	private FeiDevice feiDevice;
	private boolean apiCanFinish = true;
	private boolean apiCanFlip = true;
	private Button deviceTypeTunerButton;
	private Button deviceTypeAntennaButton;
	private Button ingestGPSCheckbox;
	private Button outputGPSCheckbox;
	private Button receiveOnlyTunerButton;
	private Button transmitOnlyTunerButton;
	private Button bothRxTxButton;
	private DataBindingContext ctx;
	private Group tunerTypeGroup;

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
		this.deviceTypeTunerButton.setSelection(true);
		this.feiDevice.eAdapters().add(new AdapterImpl(){
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

		// Binding for the antenna only option
		this.ctx.bindValue(SWTObservables.observeSelection(this.deviceTypeAntennaButton),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__IS_ANTENNA), new UpdateValueStrategy(), new UpdateValueStrategy());

		// Binding for GPS ingest & output
		this.ctx.bindValue(SWTObservables.observeSelection(this.ingestGPSCheckbox),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__INGESTS_GPS), new UpdateValueStrategy(), new UpdateValueStrategy());

		this.ctx.bindValue(SWTObservables.observeSelection(this.outputGPSCheckbox),
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__OUTPUTS_GPS), new UpdateValueStrategy(), new UpdateValueStrategy());

		// Need custom update value strat for the group binding since false -> true and true -> false
		UpdateValueStrategy tunerTypeGroupUVS = new UpdateValueStrategy();
		tunerTypeGroupUVS.setConverter(new IConverter() {
			
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
				return !((Boolean)fromObject).booleanValue();
			}
		});
		
		this.ctx.bindValue(SWTObservables.observeEnabled(this.tunerTypeGroup), 
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__IS_ANTENNA), new UpdateValueStrategy(), tunerTypeGroupUVS);
		
		this.ctx.bindValue(SWTObservables.observeEnabled(this.receiveOnlyTunerButton), 
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__IS_ANTENNA), new UpdateValueStrategy(), tunerTypeGroupUVS);
		
		this.ctx.bindValue(SWTObservables.observeEnabled(this.transmitOnlyTunerButton), 
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__IS_ANTENNA), new UpdateValueStrategy(), tunerTypeGroupUVS);
		
		this.ctx.bindValue(SWTObservables.observeEnabled(this.bothRxTxButton), 
			EMFObservables.observeValue(this.feiDevice, FrontendPackage.Literals.FEI_DEVICE__IS_ANTENNA), new UpdateValueStrategy(), tunerTypeGroupUVS);
		
		// Bindings for the tuner type is difficult since it is a 3 way with the third option being both the 1st and 2nd so listeners are used.
		this.bothRxTxButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsRxTuner(true);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsTxTuner(true);
			}
		});
		
		this.receiveOnlyTunerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsRxTuner(true);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsTxTuner(false);
			}
		});
		
		this.transmitOnlyTunerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsRxTuner(false);
				FrontEndTunerTypeSelectionWizardPage.this.feiDevice.setIsTxTuner(true);
			}
		});
		
		

	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));

		this.setTitle("Front End Interfaces Device Type Selection");
		this.setDescription("Select the device type and if this device will ingest and/or output GPS data.  If the device is a tuner, select whether this device is receive only, can transmit, or both.");

		// Device Type Group
		Group deviceTypeGroup = new Group(client, SWT.BORDER);
		deviceTypeGroup.setText("Device Type");
		deviceTypeGroup.setLayout(new GridLayout(2, false));
		deviceTypeGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		this.deviceTypeTunerButton = new Button(deviceTypeGroup, SWT.RADIO);
		this.deviceTypeTunerButton.setText("Tuner (default)");
		this.deviceTypeTunerButton.setLayoutData(GridDataFactory.fillDefaults().create());
		this.deviceTypeTunerButton.setToolTipText("Frontend tuners includes RX and TX devices, digitizers, channelizers and analog receivers.");

		this.deviceTypeAntennaButton = new Button(deviceTypeGroup, SWT.RADIO);
		this.deviceTypeAntennaButton.setText("Antenna Only");
		this.deviceTypeAntennaButton.setLayoutData(GridDataFactory.fillDefaults().create());
		this.deviceTypeAntennaButton.setToolTipText("Frontend antennas provide an RF Info Uses and RF Source Provides port.");

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
		this.tunerTypeGroup = new Group(client, SWT.BORDER);
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

	}

	@Override
	public void configure(SoftPkg softpkg, Implementation impl, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		this.softpkg = softpkg;
		this.impl = impl;
		this.codeGenDescriptor = desc;
		this.implSettings = implSettings;

	}

	@Override
	public ImplementationSettings getSettings() {
		return this.implSettings;
	}

	@Override
	public boolean canFinish() {
		return (this.apiCanFinish && this.feiDevice.isIsAntenna());
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		this.apiCanFlip = canFlip;
	}

	@Override
	public boolean canFlipToNextPage() {
		return (super.canFlipToNextPage() && !this.feiDevice.isIsAntenna());
	}
	
	@Override
	public void setCanFinish(boolean canFinish) {
		this.apiCanFinish = canFinish;
	}
	
	public FeiDevice getFeiDevice() {
		return this.feiDevice;
	}

}
