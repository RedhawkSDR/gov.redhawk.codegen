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

import gov.redhawk.ide.octave.ui.Activator;
import gov.redhawk.ide.octave.ui.OctaveFunctionVariables;
import gov.redhawk.ide.octave.ui.OctaveMFileTableLabelProvider;
import gov.redhawk.ide.octave.ui.OctaveMFileTableMappingEditingSupport;
import gov.redhawk.ide.octave.ui.OctaveMFileTableTypeEditingSupport;
import gov.redhawk.ide.octave.ui.OctaveProjectProperties;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
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
public class MFileVariableMapingWizardPage extends WizardPage {

	private static final ImageDescriptor TITLE_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/octaveLogo.png");
	private static final int NUM_COLUMNS = 1;
	private OctaveProjectProperties octaveProjProps;
	private DataBindingContext dataBindingContext = new DataBindingContext();

	public MFileVariableMapingWizardPage(OctaveProjectProperties octaveProjProps, String name, String componentType) {
		super(name, "Map M-file", MFileVariableMapingWizardPage.TITLE_IMAGE);
		this.octaveProjProps = octaveProjProps;
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

		TableViewerColumn mappingColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		mappingColumn.getColumn().setText("Mapping");

		TableViewerColumn typeColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		typeColumn.getColumn().setText("Type");

		theTableViewer.setContentProvider(new ArrayContentProvider());
		theTableViewer.setLabelProvider(new OctaveMFileTableLabelProvider());

		this.dataBindingContext.bindValue(ViewerProperties.input().observe(theTableViewer),
			BeanProperties.value(propName).observe(this.octaveProjProps));

		// TODO: Combine the Type Editing support class and the Mapping editing support class. 
		EditingSupport octaveTypeEditingSupport = new OctaveMFileTableTypeEditingSupport(typeColumn.getViewer(), this);
		typeColumn.setEditingSupport(octaveTypeEditingSupport);

		EditingSupport octaveMappingEditingSupport = new OctaveMFileTableMappingEditingSupport(mappingColumn.getViewer(), this);
		mappingColumn.setEditingSupport(octaveMappingEditingSupport);
	}

	@Override
	public boolean isPageComplete() {
		if (this.octaveProjProps == null || this.octaveProjProps.getPrimaryMFile() == null)
			return false;

		for (OctaveFunctionVariables inputvar : this.octaveProjProps.getFunctionInputs()) {
			if (inputvar.getMapping() == null || inputvar.getType() == null) {
				return false;
			}
		}

		for (OctaveFunctionVariables outputvar : this.octaveProjProps.getFunctionOutputs()) {
			if (outputvar.getMapping() == null || outputvar.getType() == null) {
				return false;
			}
		}

		return super.isPageComplete();
	}
}
