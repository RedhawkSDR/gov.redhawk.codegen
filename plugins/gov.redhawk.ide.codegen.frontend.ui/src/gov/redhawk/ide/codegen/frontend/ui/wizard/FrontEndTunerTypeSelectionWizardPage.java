package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class FrontEndTunerTypeSelectionWizardPage extends WizardPage implements ICodegenWizardPage {

	private SoftPkg softpkg;
	private Implementation impl;
	private ICodeGeneratorDescriptor codeGenDescriptor;
	private ImplementationSettings implSettings;
	private boolean apiCanFinish = true;
	private boolean apiCanFlip = true;
	private Button deviceTypeTunerButton;
	private Button deviceTypeAntennaButton;
	private Button ingestGPSCheckbox;
	private Button outputGPSCheckbox;
	private Button receiveOnlyTunerButton;
	private Button transmitOnlyTunerButton;
	private Button bothRxTxButton;

	public FrontEndTunerTypeSelectionWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		createUIElements(client);
		this.setControl(client);
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
		gpsUsageGroup.setLayout(new GridLayout(1,false));
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
		return this.apiCanFinish;
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		this.apiCanFlip = canFlip;
	}

	@Override
	public void setCanFinish(boolean canFinish) {
		this.apiCanFinish = canFinish;
	}

}
