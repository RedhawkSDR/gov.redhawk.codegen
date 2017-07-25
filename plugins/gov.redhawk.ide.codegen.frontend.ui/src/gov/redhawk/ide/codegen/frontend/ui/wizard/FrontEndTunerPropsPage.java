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
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceUIUtils;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceWizardPlugin;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndProjectValidator;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.dcd.ui.wizard.ScaDeviceProjectPropertiesWizardPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class FrontEndTunerPropsPage extends WizardPage implements ICodegenWizardPage {

	private static final int NUM_COLUMNS = 1;
	private boolean digitalTunerPortSelected = true;
	private Button addTunerStatusPropButton;
	private TableViewer theTableViewer;
	private Table theTable;
	private Button removeTunerStatusPropButton;
	private WritableSet selectedProps = new WritableSet();
	private ImplementationSettings implSettings;
	private FrontEndProjectValidator validator;
	private boolean viewed = false;

	public FrontEndTunerPropsPage(FeiDevice feiDevice) {
		super(""); //$NON-NLS-1$
		if (this.selectedProps.isEmpty()) {
			selectedProps.addAll(FrontEndDeviceUIUtils.INSTANCE.getRequiredFrontEndProps());
		}
	}

	public FrontEndTunerPropsPage(Set<Simple> props) {
		super(""); //$NON-NLS-1$

		if (this.selectedProps.isEmpty()) {
			selectedProps.addAll(FrontEndDeviceUIUtils.INSTANCE.getRequiredFrontEndProps());

			for (Simple prop : props) {
				// Maybe it is required, but it's a set so we won't add it if it's already added and all required are added already
				selectedProps.add(new FrontEndProp(prop, false));
			}
		}
	}

	@Override
	public void createControl(Composite parent) {
		this.viewed  = true;
		this.setTitle(Messages.FrontEndTunerPropsPage_Title);
		this.setDescription(Messages.FrontEndTunerPropsPage_Description);

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
				SelectFrontEndTunerPropsDialog dialog = new SelectFrontEndTunerPropsDialog(theTable.getShell());
				Set<FrontEndProp> inputSet = new HashSet<FrontEndProp>();
				inputSet.addAll(FrontEndDeviceUIUtils.INSTANCE.getOptionalFrontEndProps());

				// Don't display any that we have already added.
				inputSet.removeAll(FrontEndTunerPropsPage.this.selectedProps);

				dialog.setInput(new ArrayList<FrontEndProp>(inputSet));
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
		theTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				for (Object obj : ((IStructuredSelection) event.getSelection()).toList()) {
					if (((FrontEndProp) obj).isRequired()) {
						removeTunerStatusPropButton.setEnabled(false);
						return;
					}
				}
				removeTunerStatusPropButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
	}

	private TableViewer createTunerStatusPropSection(Composite parent) {
		Group tunerStatusPropertyGroup = new Group(parent, SWT.NONE);
		tunerStatusPropertyGroup.setText(Messages.FrontEndTunerPropsPage_GroupText);
		tunerStatusPropertyGroup.setLayout(new GridLayout(2, false));
		tunerStatusPropertyGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 350).create());

		theTableViewer = FrontEndDeviceUIUtils.INSTANCE.getTableViewer(tunerStatusPropertyGroup);
		theTable = theTableViewer.getTable();

		// Create Add/Remove button
		Composite buttonComposite = new Composite(tunerStatusPropertyGroup, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(1, false));
		buttonComposite.setLayoutData(GridDataFactory.fillDefaults().create());

		this.addTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.addTunerStatusPropButton.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/add.gif").createImage()); //$NON-NLS-1$
		this.addTunerStatusPropButton.setToolTipText(Messages.FrontEndTunerPropsPage_AddProperty);
		this.addTunerStatusPropButton.setLayoutData(GridDataFactory.swtDefaults().create());

		this.removeTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.removeTunerStatusPropButton.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/remove.gif").createImage()); //$NON-NLS-1$
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
		// Make sure that this page has been viewed.  The user should see all pages in this wizard.
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

	public Set<FrontEndProp> getSelectedProperties() {
		final Set<FrontEndProp> retVal = new HashSet<FrontEndProp>();
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

}
