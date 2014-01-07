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
package gov.redhawk.ide.octave.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.octave.ui.Activator;
import gov.redhawk.ide.octave.ui.OctaveFunctionVariables;
import gov.redhawk.ide.octave.ui.OctaveMFileTableLabelProvider;
import gov.redhawk.ide.octave.ui.OctaveMFileTableMappingEditingSupport;
import gov.redhawk.ide.octave.ui.OctaveMFileTableTypeEditingSupport;
import gov.redhawk.ide.octave.ui.OctaveProjectProperties;
import gov.redhawk.ide.octave.ui.OctaveVariableMappingEnum;
import gov.redhawk.ide.octave.ui.OctaveVariableTypeEnum;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @since 8.1
 */
public class MFileVariableMapingWizardPage extends WizardPage implements ICodegenWizardPage {

	private static final ImageDescriptor TITLE_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/octaveLogo.png");
	private static final int NUM_COLUMNS = 1;
	private OctaveProjectProperties octaveProjProps;
	private DataBindingContext dataBindingContext = new DataBindingContext();
	private TableViewerColumn typeColumn;
	private TableViewerColumn mappingColumn;
	private WritableValue validationValue = new WritableValue();
	private IValidator validator = new IValidator() {

		@Override
		public IStatus validate(Object value) {
			StringBuilder builder = new StringBuilder();
			List<OctaveFunctionVariables> vars = new ArrayList<OctaveFunctionVariables>();
			vars.addAll(octaveProjProps.getFunctionInputs());
			vars.addAll(octaveProjProps.getFunctionOutputs());

			for (OctaveFunctionVariables ofv : vars) {
				OctaveVariableMappingEnum mapping = ofv.getMapping();
				OctaveVariableTypeEnum type = ofv.getType();

				if (mapping.equals(OctaveVariableMappingEnum.PORT) && type.equals(OctaveVariableTypeEnum.String)) {
					builder.append("Variable " + ofv.getName() + " may not have a port type of string.\n");
				}

				if (mapping.equals(OctaveVariableMappingEnum.PROPERTY_SEQUENCE) && type.equals(OctaveVariableTypeEnum.String)) {
					builder.append("Variable " + ofv.getName() + " may not have a sequence property of string type.\n");
				}
			}
			IStatus mStatus;
			if (builder.length() == 0) {
				mStatus = Status.OK_STATUS;
			} else {
				mStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, builder.toString(), null);
			}
			validationValue.setValue(mStatus);
			return mStatus;
		}
	};
	
	// These aren't really used but are needed because of the inheritance 
	private Implementation impl;
	private ImplementationSettings implSettings;

	public MFileVariableMapingWizardPage(OctaveProjectProperties octaveProjProps, String name, String componentType) {
		super(name, "Map M-file", MFileVariableMapingWizardPage.TITLE_IMAGE);
		this.octaveProjProps = octaveProjProps;
		validationValue.setValue(Status.OK_STATUS);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);

		// Creates the basic layout of the UI elements
		createUIElements(client);

		this.setControl(client);
	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(MFileVariableMapingWizardPage.NUM_COLUMNS, false));

		final Group mFileInputsGroup = new Group(client, SWT.None);
		mFileInputsGroup.setLayout(new GridLayout(1, false));
		mFileInputsGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(MFileVariableMapingWizardPage.NUM_COLUMNS, 1).create());
		mFileInputsGroup.setText("Inputs");

		final Group mFileOutputsGroup = new Group(client, SWT.None);
		mFileOutputsGroup.setLayout(new GridLayout(1, false));
		mFileOutputsGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(MFileVariableMapingWizardPage.NUM_COLUMNS, 1).create());
		mFileOutputsGroup.setText("Outputs");

		createOctaveTable(mFileInputsGroup, "functionInputs");
		createOctaveTable(mFileOutputsGroup, "functionOutputs");

		dataBindingContext.addValidationStatusProvider(new ValidationStatusProvider() {
			private IObservableList list = new WritableList();
			{
				list.add(validationValue);
			}

			@Override
			public IObservableValue getValidationStatus() {
				return validationValue;
			}

			@Override
			public IObservableList getTargets() {
				return list;
			}

			@Override
			public IObservableList getModels() {
				return Observables.emptyObservableList();
			}
		});

		WizardPageSupport.create(this, this.dataBindingContext);

		this.setControl(client);
	}

	private void createOctaveTable(Group mFileInputsGroup, String propName) {
		// Example code found: http://www.subshell.com/en/subshell/blog/Eclipse-RCP-Comboboxes-inside-a-JFace-TableViewer100.html
		TableLayout theTableLayout = new TableLayout();
		theTableLayout.addColumnData(new ColumnWeightData(1));
		theTableLayout.addColumnData(new ColumnWeightData(1));
		theTableLayout.addColumnData(new ColumnWeightData(1));

		Table theTable = new Table(mFileInputsGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		theTable.setLinesVisible(true);
		theTable.setHeaderVisible(true);
		theTable.setLayout(theTableLayout);

		TableViewer theTableViewer = new TableViewer(theTable);
		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		TableViewerColumn nameIDColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		nameIDColumn.getColumn().setText("Name/ID");

		mappingColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		mappingColumn.getColumn().setText("Mapping");

		typeColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		typeColumn.getColumn().setText("Type");

		theTableViewer.setContentProvider(new ArrayContentProvider());
		theTableViewer.setLabelProvider(new OctaveMFileTableLabelProvider());

		Binding binding = this.dataBindingContext.bindValue(ViewerProperties.input().observe(theTableViewer), BeanProperties.value(propName).observe(this.octaveProjProps), null, null);

		EditingSupport octaveTypeEditingSupport = new OctaveMFileTableTypeEditingSupport(typeColumn.getViewer(), this.validator);
		typeColumn.setEditingSupport(octaveTypeEditingSupport);

		EditingSupport octaveMappingEditingSupport = new OctaveMFileTableMappingEditingSupport(mappingColumn.getViewer(), this.validator);
		mappingColumn.setEditingSupport(octaveMappingEditingSupport);
		
		// add cool control decoration
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
	}

	@Override
	public boolean isPageComplete() {
		if (this.octaveProjProps == null || this.octaveProjProps.getPrimaryMFile() == null) {
			return false;
		}

		
		return super.isPageComplete();
	}

	public DataBindingContext getDataBindingContext() {
		return dataBindingContext;
	}

	@Override
	public void configure(SoftPkg softpkg, Implementation impl, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		this.impl = impl;
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
