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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import gov.redhawk.frontend.util.TunerProperties.TunerStatusAllocationProperties;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceUIUtils;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceWizardPlugin;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndProjectValidator;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.graphiti.dcd.ui.project.wizards.ScaDeviceProjectPropertiesWizardPage;
import mil.jpeojtrs.sca.prf.AbstractProperty;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

public class FrontEndTunerPropsPage extends WizardPage implements ICodegenWizardPage {

	private static final int NUM_COLUMNS = 1;
	private Button addTunerStatusPropButton;
	private TableViewer theTableViewer;
	private Button removeTunerStatusPropButton;

	private FeiDevice feiDevice;
	private Adapter deviceAdapter;
	private Map<String, AbstractProperty> feiSpecStatusFields;

	private boolean digitalTunerPortSelected = true;
	private WritableSet<AbstractProperty> selectedProps = new WritableSet<>();
	private ImplementationSettings implSettings;
	private FrontEndProjectValidator validator;
	private boolean viewed = false;

	/**
	 * Used when a new FEI device is being created.
	 * @param feiDevice The details of the FEI device being created
	 */
	public FrontEndTunerPropsPage(FeiDevice feiDevice) {
		super(""); //$NON-NLS-1$

		this.feiDevice = feiDevice;
		deviceAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				// If the user changes whether it's a scanner, we'll see if any more fields need to be added, and then
				// refresh the controls on the page
				if (msg.getFeatureID(FeiDevice.class) == FrontendPackage.FEI_DEVICE__SCANNER) {
					if (theTableViewer != null) {
						addRequiredFields();
						theTableViewer.refresh();
						tableSelectionChange(theTableViewer.getSelection());
					}
				}
				super.notifyChanged(msg);
			}
		};
		feiDevice.eAdapters().add(deviceAdapter);

		this.feiSpecStatusFields = new HashMap<>();
		for (TunerStatusAllocationProperties propDetails : TunerStatusAllocationProperties.values()) {
			feiSpecStatusFields.put(propDetails.getId(), propDetails.createProperty());
		}
	}

	/**
	 * Used when an existing FEI device is being modified.
	 * @param feiDevice A guess at the FEI device details based on the user's XML
	 * @param userFields The fields within the user's existing FEI status property
	 */
	public FrontEndTunerPropsPage(FeiDevice feiDevice, Set< ? extends AbstractProperty> userFields) {
		this(feiDevice);

		// For each of the fields the user has in their status property
		for (AbstractProperty userField : userFields) {
			// Don't add the property if we already added it
			if (selectedProps.stream() //
					.anyMatch(selectedProp -> selectedProp.getId().equals(userField.getId()))) {
				continue;
			}

			// Is this field specified by the spec? Use the spec definition, not the user's
			if (feiSpecStatusFields.containsKey(userField.getId())) {
				selectedProps.add(feiSpecStatusFields.get(userField.getId()));
			} else {
				selectedProps.add(userField);
			}
		}
	}

	private void addRequiredFields() {
		for (AbstractProperty field : feiSpecStatusFields.values()) {
			if (TunerStatusAllocationProperties.fromPropID(field.getId()).isRequired(feiDevice.isScanner())) {
				selectedProps.add(field);
			}
		}
	}

	@Override
	public void createControl(Composite parent) {
		this.viewed = true;
		this.setTitle(Messages.FrontEndTunerPropsPage_Title);
		this.setDescription(Messages.FrontEndTunerPropsPage_Description);

		// Add required fields now so they match what the user's choices on the previous wizard pages. By adding them
		// now (when they're first displaying the page) we ensure they'll match the user's final choices, and not show
		// artifacts of interim choices they played with in the wizard. Once they've seen this page, if they change
		// earlier pages they'll have to remove fields they don't want.
		addRequiredFields();

		final Composite client = new Composite(parent, SWT.NONE);

		// Creates the basic layout of the UI elements
		client.setLayout(new GridLayout(FrontEndTunerPropsPage.NUM_COLUMNS, false));
		createTunerStatusPropSection(client).setInput(this.selectedProps);
		createBindings();
		createListeners();

		this.setControl(client);
	}

	private void createBindings() {
		DataBindingContext ctx = new DataBindingContext();
		// This is really just to get the Finish button to behave
		WizardPageSupport.create(this, ctx);

		IWizardPage[] wizPages = this.getWizard().getPages();
		ScaDeviceProjectPropertiesWizardPage propWizPage = null;

		for (IWizardPage wizPage : wizPages) {
			if (wizPage instanceof ScaDeviceProjectPropertiesWizardPage) {
				propWizPage = (ScaDeviceProjectPropertiesWizardPage) wizPage;
				break;
			}
		}

		// This must come after the creation of the page support since creation of page support updates the
		// error message. The WizardPageSupport doesn't update the error message because no UI elements have changed
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

	private void createListeners() {
		// Button Listener
		this.addTunerStatusPropButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectFrontEndTunerPropsDialog dialog = new SelectFrontEndTunerPropsDialog(theTableViewer.getTable().getShell(), feiDevice);

				// Display all fields that aren't already added
				Set<AbstractProperty> inputSet = new HashSet<>();
				inputSet.addAll(feiSpecStatusFields.values());
				inputSet.removeAll(selectedProps);

				dialog.setInput(inputSet);
				int dialogStatus = dialog.open();
				if (dialogStatus == Window.OK) {
					selectedProps.addAll(dialog.getResult());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		this.removeTunerStatusPropButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedProps.removeAll(((IStructuredSelection) theTableViewer.getSelection()).toList());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Prevent the removal of required props
		theTableViewer.addSelectionChangedListener(event -> {
			tableSelectionChange(event.getSelection());
		});
	}

	private void tableSelectionChange(ISelection currentSelection) {
		for (Object obj : ((IStructuredSelection) currentSelection).toList()) {
			AbstractProperty prop = (AbstractProperty) obj;
			if (TunerStatusAllocationProperties.fromPropID(prop.getId()).isRequired(feiDevice.isScanner())) {
				removeTunerStatusPropButton.setEnabled(false);
				return;
			}
		}
		removeTunerStatusPropButton.setEnabled(!currentSelection.isEmpty());
	}

	private TableViewer createTunerStatusPropSection(Composite parent) {
		Group tunerStatusPropertyGroup = new Group(parent, SWT.NONE);
		tunerStatusPropertyGroup.setText(Messages.FrontEndTunerPropsPage_GroupText);
		tunerStatusPropertyGroup.setLayout(new GridLayout(2, false));
		tunerStatusPropertyGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 350).create());

		theTableViewer = FrontEndDeviceUIUtils.INSTANCE.getTableViewer(tunerStatusPropertyGroup, feiDevice);

		// Create Add/Remove button
		Composite buttonComposite = new Composite(tunerStatusPropertyGroup, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(1, false));
		buttonComposite.setLayoutData(GridDataFactory.fillDefaults().create());

		this.addTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.addTunerStatusPropButton.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/add.gif").createImage()); //$NON-NLS-1$
		this.addTunerStatusPropButton.setToolTipText(Messages.FrontEndTunerPropsPage_AddProperty);
		this.addTunerStatusPropButton.setLayoutData(GridDataFactory.swtDefaults().create());

		this.removeTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.removeTunerStatusPropButton.setImage(
			AbstractUIPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/remove.gif").createImage()); //$NON-NLS-1$
		this.removeTunerStatusPropButton.setToolTipText(Messages.FrontEndTunerPropsPage_RemoveProperty);
		this.removeTunerStatusPropButton.setLayoutData(GridDataFactory.swtDefaults().create());
		this.removeTunerStatusPropButton.setEnabled(false);

		return theTableViewer;
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
		// Make sure that this page has been viewed. The user should see all pages in this wizard.
		if (!viewed) {
			return false;
		}

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
	}

	@Override
	public void setCanFinish(boolean canFinish) {

	}

	public Set<AbstractProperty> getSelectedProperties() {
		final Set<AbstractProperty> retVal = new HashSet<>();
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				retVal.addAll(selectedProps);
			}

		});
		return Collections.unmodifiableSet(retVal);
	}

	public boolean isDigitalTunerPortSelected() {
		return digitalTunerPortSelected;
	}

	public void setDigitalTunerPortSelected(boolean value) {
		this.digitalTunerPortSelected = value;
	}

	@Override
	public void dispose() {
		if (deviceAdapter != null) {
			feiDevice.eAdapters().remove(deviceAdapter);
			deviceAdapter = null;
		}
		super.dispose();
	}

}
