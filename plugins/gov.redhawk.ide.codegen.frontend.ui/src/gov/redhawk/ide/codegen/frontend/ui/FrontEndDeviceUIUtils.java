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
package gov.redhawk.ide.codegen.frontend.ui;

import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeColumnViewerLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import FRONTEND.FE_TUNER_DEVICE_KIND;
import gov.redhawk.frontend.util.TunerProperties.TunerStatusAllocationProperties;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndPropLabelProvider;
import mil.jpeojtrs.sca.prf.AbstractProperty;

public enum FrontEndDeviceUIUtils {
	INSTANCE;

	protected static final String TUNER_DEVICE_KIND_NAME = FE_TUNER_DEVICE_KIND.value;

	// Antenna device kind name is still in flux but should eventually be defined within the IDL
	protected static final String ANTENNA_DEVICE_KIND_NAME = "FRONTEND::RFSOURCE";

	private FrontEndDeviceUIUtils() {
	}

	public CheckboxTableViewer getCheckboxTableViewer(Composite parent, FeiDevice feiDevice) {
		CheckboxTableViewer theTableViewer = new CheckboxTableViewer(
			createTable(parent, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));
		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setComparator(getTableSorter(feiDevice));

		addColumns(theTableViewer, feiDevice);

		return theTableViewer;
	}

	public TableViewer getTableViewer(Composite parent, FeiDevice feiDevice) {
		// Define the Table Viewer
		TableViewer theTableViewer = new TableViewer(createTable(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));

		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setComparator(getTableSorter(feiDevice));

		addColumns(theTableViewer, feiDevice);

		return theTableViewer;
	}

	private Table createTable(Composite parent, int style) {
		// Define table layout
		TableLayout theTableLayout = new TableLayout();
		theTableLayout.addColumnData(new ColumnWeightData(25));
		theTableLayout.addColumnData(new ColumnWeightData(10));
		theTableLayout.addColumnData(new ColumnWeightData(10));
		theTableLayout.addColumnData(new ColumnWeightData(55));

		// Define the table
		Table theTable = new Table(parent, style);
		theTable.setLinesVisible(true);
		theTable.setHeaderVisible(true);
		theTable.setLayout(theTableLayout);

		return theTable;
	}

	private void addColumns(TableViewer theTableViewer, FeiDevice feiDevice) {
		ColumnViewerToolTipSupport.enableFor(theTableViewer);

		final FrontEndPropLabelProvider lp = new FrontEndPropLabelProvider(feiDevice);

		TableViewerColumn nameIDColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		nameIDColumn.setLabelProvider(new TreeColumnViewerLabelProvider(lp) {
			@Override
			public String getText(Object element) {
				return lp.getColumnText(element, 0);
			}
		});
		nameIDColumn.getColumn().setText("Name/ID");

		TableViewerColumn typeColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		typeColumn.setLabelProvider(new TreeColumnViewerLabelProvider(lp) {
			@Override
			public String getText(Object element) {
				return lp.getColumnText(element, 1);
			}
		});
		typeColumn.getColumn().setText("Type");

		TableViewerColumn required = new TableViewerColumn(theTableViewer, SWT.NONE);
		required.setLabelProvider(new TreeColumnViewerLabelProvider(lp) {
			@Override
			public String getText(Object element) {
				return lp.getColumnText(element, 2);
			}
		});
		required.getColumn().setText("Required");

		TableViewerColumn descColumn = new TableViewerColumn(theTableViewer, SWT.NONE);
		descColumn.getColumn().setText("Description");
		descColumn.setLabelProvider(new TreeColumnViewerLabelProvider(lp) {

			@Override
			public String getText(Object element) {
				return lp.getColumnText(element, 3);
			}

			@Override
			public int getToolTipDisplayDelayTime(Object object) {
				return 50;
			}

			@Override
			public String getToolTipText(Object element) {
				return getText(element);
			}

			@Override
			public int getToolTipTimeDisplayed(Object object) {
				return 5000;
			}

			@Override
			public boolean useNativeToolTip(Object object) {
				return true;
			}
		});

		theTableViewer.setContentProvider(new ObservableSetContentProvider());
	}

	private ViewerComparator getTableSorter(final FeiDevice device) {
		ViewerComparator vs = new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				AbstractProperty frontEndProp1 = (AbstractProperty) e1;
				AbstractProperty frontEndProp2 = (AbstractProperty) e2;

				// Required properties should be displayed last since the user will only really interact with
				// non-required.
				boolean required1 = TunerStatusAllocationProperties.fromPropID(frontEndProp1.getId()).isRequired(device.isScanner());
				boolean required2 = TunerStatusAllocationProperties.fromPropID(frontEndProp2.getId()).isRequired(device.isScanner());
				if (required1 != required2) {
					return (required1) ? 1 : -1;
				}

				// If they are both in the same category sort by ID
				return frontEndProp1.getId().compareTo(frontEndProp2.getId());
			}
		};
		return vs;
	}
}
