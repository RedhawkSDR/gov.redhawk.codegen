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
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceWizardPlugin;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;

import java.util.HashSet;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class FrontEndWizardPage extends WizardPage implements ICodegenWizardPage {

	private static final int NUM_COLUMNS = 1;
	private Button digitalTunerPortButton;
	private Button analogTunerPortButton;
	private boolean digitalTunerPortSelected = true;
	private Button addTunerStatusPropButton;
	private TableViewer theTableViewer;
	private Table theTable;
	private Button removeTunerStatusPropButton;
	private Set<FrontEndProp> selectedProps = new HashSet<FrontEndProp>();
	private ImplementationSettings implSettings;
	private Implementation impl;
	
	public FrontEndWizardPage(String pageName) {
		super(pageName);
	}

	public FrontEndWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		selectedProps.addAll(FrontEndDeviceUIUtils.INSTANCE.getRequiredFrontEndProps());

		// Creates the basic layout of the UI elements
		createUIElements(client);
		createListeners();
		createBindings();
		
		this.setControl(client);
	}

	private void createBindings() {
		DataBindingContext ctx = new DataBindingContext();
		ISWTObservableValue target = WidgetProperties.selection().observe(digitalTunerPortButton);
		IObservableValue model = PojoProperties.value("digitalTunerPortSelected").observe(this);

		
		ctx.bindValue(target, model);
	}

	private void createListeners() {
		// Button Listener
		this.addTunerStatusPropButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectFRIPropertyDialog dialog = new SelectFRIPropertyDialog(theTable.getShell());
				dialog.setInput(FrontEndDeviceUIUtils.INSTANCE.getOptionalFrontEndProps());
				int dialogStatus = dialog.open();
				if (dialogStatus == Dialog.OK) {
					selectedProps.addAll(dialog.getResult());
					theTableViewer.refresh();
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
				for (TableItem selection : theTable.getSelection()) {
					selectedProps.remove(selection.getData());
				}
				theTableViewer.refresh();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		// Prevent the removal of required props
		this.theTable.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] itemsSelected = theTable.getSelection();
				for (TableItem selection : itemsSelected) {
					if (((FrontEndProp)selection.getData()).isRequired()) {
						removeTunerStatusPropButton.setEnabled(false);
						return;
					}
				}
				removeTunerStatusPropButton.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(FrontEndWizardPage.NUM_COLUMNS, false));
		createTunerPortSection(client);
		createTunerStatusPropSection(client).setInput(this.selectedProps);
	}

	private void createTunerPortSection(Composite client) {
		Group tunerPortTypeSelectionGroup = new Group(client, SWT.None);
		tunerPortTypeSelectionGroup.setText("Tuner Port Type");
		tunerPortTypeSelectionGroup.setLayout(new GridLayout(1, false));
		tunerPortTypeSelectionGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		
		this.digitalTunerPortButton = new Button(tunerPortTypeSelectionGroup, SWT.RADIO);
		this.digitalTunerPortButton.setText("Digital Tuner Port (default)");
		this.digitalTunerPortButton.setLayoutData(GridDataFactory.fillDefaults().create());
		this.digitalTunerPortButton.setSelection(true);
		
		this.analogTunerPortButton = new Button(tunerPortTypeSelectionGroup, SWT.RADIO);
		this.analogTunerPortButton.setText("Analog Tuner Port");
		this.analogTunerPortButton.setLayoutData(GridDataFactory.fillDefaults().create());
	}

	private TableViewer createTunerStatusPropSection(Composite parent) {
		Group tunerStatusPropertyGroup = new Group(parent, SWT.None);
		tunerStatusPropertyGroup.setText("Tuner Status Property Selection");
		tunerStatusPropertyGroup.setLayout(new GridLayout(2, false));
		tunerStatusPropertyGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		
		theTableViewer = FrontEndDeviceUIUtils.INSTANCE.getTableViewer(tunerStatusPropertyGroup);
		theTable = theTableViewer.getTable();
		
		// Create Add/Remove button
		Composite buttonComposite = new Composite(tunerStatusPropertyGroup, SWT.None);
		buttonComposite.setLayout(new GridLayout(1, false));
		buttonComposite.setLayoutData(GridDataFactory.fillDefaults().create());
		
		this.addTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.addTunerStatusPropButton.setImage(FrontEndDeviceWizardPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/add.gif").createImage());
		this.addTunerStatusPropButton.setLayoutData(GridDataFactory.swtDefaults().create());

		this.removeTunerStatusPropButton = new Button(buttonComposite, SWT.PUSH);
		this.removeTunerStatusPropButton.setImage(FrontEndDeviceWizardPlugin.imageDescriptorFromPlugin(FrontEndDeviceWizardPlugin.PLUGIN_ID, "icons/remove.gif").createImage());
		this.removeTunerStatusPropButton.setLayoutData(GridDataFactory.swtDefaults().create());
		
		return theTableViewer;
	}

	@Override
	public void configure(SoftPkg softpkg, Implementation impl, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		this.implSettings = implSettings;
		this.impl = impl;
		//TODO: From the impl, get the scd and find the device type and do validation via bindings
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
	public void setCanFlipToNextPage(boolean canFlip) {
	}

	@Override
	public void setCanFinish(boolean canFinish) {
	}

	public Set<FrontEndProp> getSelectedProperties() {
		return this.selectedProps;
	}
	
	public boolean isDigitalTunerPortSelected() {
		return digitalTunerPortSelected;
	}
	
	public void setDigitalTunerPortSelected(boolean value) {
		this.digitalTunerPortSelected = value;
	}

}
