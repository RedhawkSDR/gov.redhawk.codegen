package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class FrontEndWizardPage2 extends WizardPage implements ICodegenWizardPage {

	private ImplementationSettings implSettings;

	public FrontEndWizardPage2(String pageName) {
		super(pageName);
	}

	public FrontEndWizardPage2(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {

		final Composite client = new Composite(parent, SWT.NULL);

		createUIElements(client);

		this.setControl(client);
	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));
		
		
		//TODO Logic to determine which group to show
		//if (thisIsAReceiver) {
		//	createReceiverGroup(client);
		// } else if (thisIsATransmitter) {
		//	createTransmitterGroup(client);
		// } else {
			createReceiverGroup(client);
			createTransmitterGroup(client);
		// }
	}

	private void createReceiverGroup(Composite client) {
		Group receiverGroup = new Group(client, SWT.SHADOW_ETCHED_IN);
		receiverGroup.setText("Receiver Properties");
		receiverGroup.setLayout(new GridLayout(2, false));
		receiverGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		
		createInputControl(receiverGroup);
		createOutputControl(receiverGroup);
	}


	private void createTransmitterGroup(Composite client) {
		Group transmitterGroup = new Group(client, SWT.SHADOW_ETCHED_IN);
		transmitterGroup.setText("Transmitter Properties");
		transmitterGroup.setLayout(new GridLayout(4, false));
		transmitterGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		
		Label numDigitalInLabel = new Label(transmitterGroup, SWT.None);
		numDigitalInLabel.setText("Number of Digital input ports: ");
		
		Spinner numDigitalSpinner = new Spinner(transmitterGroup, SWT.None);
		numDigitalSpinner.setMinimum(1);
		
		Label digitalInputTypeLabel = new Label(transmitterGroup, SWT.None);
		digitalInputTypeLabel.setText("Digital Input Type: ");
		digitalInputTypeLabel.setLayoutData(GridDataFactory.fillDefaults().indent(50, 0).align(SWT.CENTER, SWT.CENTER).create());
		
		//TODO Generate combo items dynamically
		Combo digitalInputCombo = new Combo(transmitterGroup, SWT.None);
		digitalInputCombo.setText("Temp");
	}

	private void createInputControl(Group parent) {
		Composite inputContainer = new Composite(parent, SWT.None);
		inputContainer.setLayout(new GridLayout(1, false));
		inputContainer.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		
		Button analogInputButton = new Button(inputContainer, SWT.RADIO);
		analogInputButton.setText("Analog Input (default)");
		analogInputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		final Composite analogIn = createAnalogIn(inputContainer);
		analogInputButton.setSelection(true);
		
		Button digitalInputButton = new Button(inputContainer, SWT.RADIO);
		digitalInputButton.setText("Digital Input");
		digitalInputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		final Composite digitalIn = createDigitalIn(inputContainer);
		
		// TODO Gray out disabled areas
		analogInputButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				analogIn.setEnabled(true);
				digitalIn.setEnabled(false);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		digitalInputButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				digitalIn.setEnabled(true);
				analogIn.setEnabled(false);
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
		analogIn.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.CENTER, SWT.CENTER).create());
		
		Label numAnalogLabel = new Label(analogIn, SWT.None);
		numAnalogLabel.setText("Number of Analog input ports: ");
		
		Spinner numAnalogSpinner = new Spinner(analogIn, SWT.None);
		numAnalogSpinner.setMinimum(1);
		
		return analogIn;
	}
	
	private Composite createDigitalIn(Composite parent) {
		Composite digitalIn = new Composite(parent, SWT.SHADOW_NONE);
		digitalIn.setLayout(new GridLayout(2, false));
		digitalIn.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).indent(30, 0).align(SWT.CENTER, SWT.CENTER).create());
		
		Label digitalInputTypeLabel = new Label(digitalIn, SWT.None);
		digitalInputTypeLabel.setText("Digital Input Type: ");
		
		//TODO Generate combo items dynamically
		Combo digitalInputCombo = new Combo(digitalIn, SWT.None);
		digitalInputCombo.setText("Temp");
		
		return digitalIn;
	}
	
	private void createOutputControl(Group parent) {
		Composite outputContainer = new Composite(parent, SWT.None);
		outputContainer.setLayout(new GridLayout(1, false));
		outputContainer.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		
		Button analogOutputButton = new Button(outputContainer, SWT.RADIO);
		analogOutputButton.setText("Analog Output");
		analogOutputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		
		Button digitalOutputButton = new Button(outputContainer, SWT.RADIO);
		digitalOutputButton.setText("Digital Output");
		digitalOutputButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
		final Composite digitalOut = createDigitalOut(outputContainer);
		digitalOutputButton.setSelection(true);
		
		// TODO Gray out disabled areas
		analogOutputButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				digitalOut.setEnabled(false);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		digitalOutputButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				digitalOut.setEnabled(true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});		
	}

	private Composite createDigitalOut(Composite parent) {
		Composite digitalOut = new Composite(parent, SWT.SHADOW_NONE);
		digitalOut.setLayout(new GridLayout(2, false));
		digitalOut.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).indent(30, 0).align(SWT.CENTER, SWT.CENTER).create());
		
		Label digitalOutputTypeLabel = new Label(digitalOut, SWT.None);
		digitalOutputTypeLabel.setText("Digital Output Type: ");
		
		Combo digitalOutputCombo = new Combo(digitalOut, SWT.None);
		digitalOutputCombo.setText("Temp");
		
		Button multiOutCheck = new Button(digitalOut, SWT.CHECK);
		multiOutCheck.setText("Multi-out");
		
		return digitalOut;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCanFinish(boolean canFinish) {
		// TODO Auto-generated method stub
	}
}
