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

import java.util.ArrayList;
import java.util.List;

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
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndProp;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndPropLabelProvider;
import mil.jpeojtrs.sca.prf.Simple;

public enum FrontEndDeviceUIUtils {
	INSTANCE;

	protected static final String TUNER_DEVICE_KIND_NAME = FE_TUNER_DEVICE_KIND.value;

	// Antenna device kind name is still in flux but should eventually be defined within the IDL
	protected static final String ANTENNA_DEVICE_KIND_NAME = "FRONTEND::RFSOURCE";

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

		for (TunerStatusAllocationProperties propDetails : TunerStatusAllocationProperties.values()) {
			Simple prop = (Simple) propDetails.createProperty();
			FrontEndProp wrappedProp;
			if (propDetails.isRequired()) {
				wrappedProp = new FrontEndProp(prop, true);
				requiredFrontEndProps.add(wrappedProp);
			} else {
				wrappedProp = new FrontEndProp(prop, false);
				optionalFrontEndProps.add(wrappedProp);
			}
			allFrontEndProps.add(wrappedProp);
		}
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
		CheckboxTableViewer theTableViewer = new CheckboxTableViewer(
			createTable(parent, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));
		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setComparator(getTableSorter());

		addColumns(theTableViewer);

		return theTableViewer;
	}

	public TableViewer getTableViewer(Composite parent) {
		// Define the Table Viewer
		TableViewer theTableViewer = new TableViewer(createTable(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL));

		theTableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		theTableViewer.setComparator(getTableSorter());

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
		ColumnViewerToolTipSupport.enableFor(theTableViewer);

		final FrontEndPropLabelProvider lp = new FrontEndPropLabelProvider();

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

	private ViewerComparator getTableSorter() {
		ViewerComparator vs = new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				FrontEndProp frontEndProp1 = (FrontEndProp) e1;
				FrontEndProp frontEndProp2 = (FrontEndProp) e2;

				// Required properties should be displayed last since the user will only really interact with
				// non-required.
				if (frontEndProp1.isRequired() != frontEndProp2.isRequired()) {
					if (frontEndProp1.isRequired()) {
						return 1;
					} else {
						return -1;
					}
				}

				// If they are both in the same category sort by Name/Id alphabetically
				return frontEndProp1.getProp().getId().compareTo(frontEndProp2.getProp().getId());
			}
		};
		return vs;
	}
}
