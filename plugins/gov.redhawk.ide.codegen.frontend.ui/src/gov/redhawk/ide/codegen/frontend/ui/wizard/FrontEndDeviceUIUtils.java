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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.PropertyValueType;
import mil.jpeojtrs.sca.prf.Simple;

public enum FrontEndDeviceUIUtils { 
	INSTANCE;
	private List<FrontEndProp> allFrontEndProps = null;
	private List<FrontEndProp> optionalFrontEndProps = null;
	private List<FrontEndProp> requiredFrontEndProps = null;
	
	
	private FrontEndDeviceUIUtils() {
		initFriProps();
	}
	
	private void initFriProps() {
		
		allFrontEndProps = new ArrayList<FrontEndProp>();
		optionalFrontEndProps = new ArrayList<FrontEndProp>();
		requiredFrontEndProps = new ArrayList<FrontEndProp>();
		
		// Required Props
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"tuner_type", 
			PropertyValueType.STRING, 
			"Type description of tuner."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"allocation_id_csv", 
			PropertyValueType.STRING, 
			"Comma separated list of current Allocation IDs."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"center_frequency", 
			PropertyValueType.DOUBLE, 
			"Current center frequency in Hz."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"bandwidth", 
			PropertyValueType.DOUBLE, 
			"Current bandwidth in Hz"), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"sample_rate", 
			PropertyValueType.DOUBLE, 
			"Current sample rate in Hz."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"group_id", 
			PropertyValueType.STRING, 
			"Unique ID that specifies a group of Device."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"rf_flow_id", 
			PropertyValueType.STRING, 
			"Specifies a certain RF flow to allocate against."), true));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"enabled", 
			PropertyValueType.BOOLEAN, 
			"Indicates if tuner is enabled."), true));
		
		
		// Optional Props
		
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"bandwidth_tolerance", 
			PropertyValueType.DOUBLE, 
			"Allowable percentage over requested bandwidth."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"sample_rate_tolerance", 
			PropertyValueType.DOUBLE, 
			"Allowable percentage over requested sample rate."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"complex", 
			PropertyValueType.BOOLEAN, 
			"Indicates if the output data is complex."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"gain", 
			PropertyValueType.DOUBLE, 
			"Current gain in dB."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"agc", 
			PropertyValueType.BOOLEAN, 
			"Indicates if the tuner has Automatic Gain Control enabled."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"valid", 
			PropertyValueType.BOOLEAN, 
			"Indicates if the tuner is in a valid state."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"available_frequency", 
			PropertyValueType.STRING, 
			"Valid potential center frequencies for the tuner in Hz."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"available_bandwidth", 
			PropertyValueType.STRING, 
			"Valid potential bandwidth for the tuner in Hz."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"available_gain", 
			PropertyValueType.STRING, 
			"Valid potential gain for the tuner in dB."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"available_sample_rate", 
			PropertyValueType.STRING, 
			"Valid potential sample rates for the tuner."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"reference_source", 
			PropertyValueType.LONG, 
			"Indicates internal vs external reference source."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"output_format", 
			PropertyValueType.STRING, 
			"Indicates current output data format."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"output_multicast", 
			PropertyValueType.STRING, 
			"Multicast address for SDDS output."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"output_vlan", 
			PropertyValueType.LONG, 
			"Virtual Local Area Network (VLAN) number for SDDS output."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"output_port", 
			PropertyValueType.LONG, 
			"Port number for SDDS output."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"decimation", 
			PropertyValueType.LONG, 
			"Current decimation of tuner."), false));
		
		allFrontEndProps.add(new FrontEndProp(createSimple(
			"tuner_number", 
			PropertyValueType.SHORT, 
			"Physical tuner ID."), false));
		
		for (FrontEndProp prop : allFrontEndProps) {
			if (prop.isRequired())
				this.requiredFrontEndProps.add(prop);
			else
				this.optionalFrontEndProps.add(prop);
		}
	}

	private Simple createSimple(String name, PropertyValueType type, String description) {
		Simple tmpProp = PrfFactory.eINSTANCE.createSimple();
		tmpProp.setName(name);
		tmpProp.setId("FRONTEND::tuner_status::" + name);
		tmpProp.setType(type);
		tmpProp.setDescription(description);
		
		return tmpProp;
	}
	
	/**
	 * Provides a List of the front end props each indicating if they are required or not.
	 * @return The list of Front end properties.
	 */
	public List<FrontEndProp> getAllFrontEndProps() {
		return allFrontEndProps;
	}
	
	public List<FrontEndProp> getRequiredFrontEndProps() {
		return requiredFrontEndProps;
	}
	
	public List<FrontEndProp> getOptionalFrontEndProps() {
		return optionalFrontEndProps;
	}
	
	
	public CheckboxTableViewer getCheckboxTableViewer(Composite parent) {
		CheckboxTableViewer theTableViewer = new CheckboxTableViewer(createTable(parent, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));
		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setSorter(getTableSorter());
		
		addColumns(theTableViewer);
		
		return theTableViewer;
	}
	public TableViewer getTableViewer(Composite parent) {
		// Define the Table Viewer
		TableViewer theTableViewer = new TableViewer(createTable(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));
		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setSorter(getTableSorter());
		
		addColumns(theTableViewer);

		return theTableViewer;
	}
	
	private Table createTable(Composite parent, int style) {
		// Define table layout
		TableLayout theTableLayout = new TableLayout();
		theTableLayout.addColumnData(new ColumnWeightData(30));
		theTableLayout.addColumnData(new ColumnWeightData(10));
		theTableLayout.addColumnData(new ColumnWeightData(10));
		theTableLayout.addColumnData(new ColumnWeightData(50));

		// Define the table
		Table theTable = new Table(parent, style);
		theTable.setLinesVisible(true);
		theTable.setHeaderVisible(true);
		theTable.setLayout(theTableLayout);
		
		return theTable;
	}
	
	private void addColumns(TableViewer theTableViewer) {
		TableViewerColumn nameIDColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		nameIDColumn.getColumn().setText("Name/ID");

		TableViewerColumn typeColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		typeColumn.getColumn().setText("Type");

		TableViewerColumn required = new TableViewerColumn(theTableViewer, SWT.NONE);
		required.getColumn().setText("Required");
		
		TableViewerColumn descColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		descColumn.getColumn().setText("Description");
		
		theTableViewer.setContentProvider(new ArrayContentProvider());
		theTableViewer.setLabelProvider(new FrontEndPropLabelProvider());
	}

	private ViewerSorter getTableSorter() {
		ViewerSorter vs = new ViewerSorter() {@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			FrontEndProp frontEndProp1 = (FrontEndProp) e1;
			FrontEndProp frontEndProp2 = (FrontEndProp) e2;
			
			// Required properties should be displayed last since the user will only really interact with non-required.
			if (frontEndProp1.isRequired() != frontEndProp2.isRequired()) {
				if (frontEndProp1.isRequired())
					return 1;
				else 
					return -1;
			}
			
			// If they are both in the same category sort by Name/Id alphabetically
			return frontEndProp1.getProp().getId().compareTo(frontEndProp2.getProp().getId());
		}
		};
		return vs;
		
	}
	
}
