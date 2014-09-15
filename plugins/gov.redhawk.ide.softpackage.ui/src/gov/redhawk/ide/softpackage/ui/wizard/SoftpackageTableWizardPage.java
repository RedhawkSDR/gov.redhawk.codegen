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
package gov.redhawk.ide.softpackage.ui.wizard;

import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;
import gov.redhawk.ide.softpackage.ui.wizard.nested.SoftpackageTableWizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class SoftpackageTableWizardPage extends WizardPage {

	private static final String CREATE_NEW_LIBRARY = "Create New Library";
	private static final String USE_EXISTING_LIBRARY = "Use Existing Library";
	private static final String PAGE_DESCRIPTION = "Provide types and optional implementation identifiers.";

	private final boolean isCreateNewLibrary; 
	private final SoftpackageTableWizardPageModel model;
	private final DataBindingContext dbc;

	private TableViewer tableViewer;
	private Composite client;
	
	/**
	 * {@link SoftpackageTableWizardPage.SoftpackageTableWizardPageModel}
	 * The Model for this page consists of a list of SoftpackageModel objects
	 * as defined by the user. This list will be used for code generation.
	 */
	public class SoftpackageTableWizardPageModel {
		public static final String LIST = "list";

		private final List<SoftpackageModel> list = new ArrayList<SoftpackageModel>();

		private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

		public SoftpackageTableWizardPageModel() {
			
		}

		public List<SoftpackageModel> getList() {
			return list;
		}

		public void add(SoftpackageModel value) {
			if (value != null) {
				this.list.add(value);
				this.pcs.firePropertyChange(new PropertyChangeEvent(this, LIST, null, this.list));
			}
		} 
		
		public void remove(SoftpackageModel value) {
			if (value != null) {
				this.list.remove(value);
				this.pcs.firePropertyChange(new PropertyChangeEvent(this, LIST, null, this.list));
			}
		}

		public void addPropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(final PropertyChangeListener listener) {
			this.pcs.removePropertyChangeListener(listener);
		}
	}

	public SoftpackageTableWizardPage(String pageName, boolean isCreateNewLibrary) {
		super(pageName, (isCreateNewLibrary) ? CREATE_NEW_LIBRARY : USE_EXISTING_LIBRARY, null);
		this.isCreateNewLibrary = isCreateNewLibrary;
		this.setDescription(PAGE_DESCRIPTION);
		this.model = new SoftpackageTableWizardPageModel();
		this.dbc = new DataBindingContext();
		this.setPageComplete(false);
	}  

	@Override
	public void createControl(Composite parent) {
		client = new Composite(parent, SWT.NULL);
		client.setLayout(new GridLayout(2, false));
		client.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create()); 

		this.createTableViewer();
		
		// Add buttons to the page
		final Button addButton = createButton("Add");
		final Button editButton = createButton("Edit");
		final Button removeButton = createButton("Remove");

		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// open wizard dialog and add if "OK" selected
				SoftpackageTableWizard wizard = new SoftpackageTableWizard(isCreateNewLibrary);
				if ((new WizardDialog(client.getShell(), wizard)).open() == Window.OK) {
					model.add(wizard.getModel());
					updatePage();
				}
			}
		}); 
		
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// make copy of first element in selection
				SoftpackageModel selected = (SoftpackageModel) getTableSelection().getFirstElement();
				SoftpackageModel copy = new SoftpackageModel(selected);
				// open wizard dialog using the copy as a model
				SoftpackageTableWizard wizard = new SoftpackageTableWizard(isCreateNewLibrary, copy);
				WizardDialog wdialog = new WizardDialog(client.getShell(), wizard);
				// if changes made and "OK" selected, replace original with edited copy
				if (wdialog.open() == Window.OK && !selected.equals(copy)) {
					model.remove(selected);
					model.add(copy);
					updatePage();
				}
			} 
		});
		
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { 
				// remove all selected items
				for (Object obj : getTableSelection().toArray()) {
					model.remove((SoftpackageModel) obj);
				}
				updatePage();
			}
		});

		// initial button enablement
		editButton.setEnabled(!getTableSelection().isEmpty());
		removeButton.setEnabled(!getTableSelection().isEmpty());

		// update button enablement based on selection of elements in table
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) { 
				boolean isSelected = !getTableSelection().isEmpty();
				editButton.setEnabled(isSelected);
				removeButton.setEnabled(isSelected); 
			}
		});

		this.setControl(client);
	}

	/**
	 * Creates a TableViewer which updates based on the model
	 */
	private TableViewer createTableViewer() {	
		Composite tableComposite = new Composite(client, SWT.NONE);
		tableComposite.setLayoutData(GridDataFactory.fillDefaults().span(1, 4).grab(true, true).create());
		
		tableViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER); 
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(model.list);

		tableComposite.setLayout(this.createTableViewerColumns()); 
		
		final Table table = tableViewer.getTable(); 
		table.setHeaderVisible(true);
		table.setLinesVisible(true); 
		
		return tableViewer;
	}

	/**
	 * Creates the TableViewerColumns "Type" and "Implementation" in the tableViewer
	 * @return tableColumnLayout for the table composite
	 */
	private TableColumnLayout createTableViewerColumns() {
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		String[] columnNames = {"Type", "Implementation"};

		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerColumn.getColumn().setText(columnNames[0]);
		tableColumnLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(20, 180, false)); 
		viewerColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((SoftpackageModel) cell.getElement()).getTypeName());
			}
		});

		viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerColumn.getColumn().setText(columnNames[1]);
		tableColumnLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(20, 180, false)); 
		viewerColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((SoftpackageModel) cell.getElement()).getImplName());
			}
		});
		tableViewer.setColumnProperties(columnNames); 
		
		return tableColumnLayout;
	}

	/**
	 * Create a button on the given composite with certain text 
	 * @param buttonText
	 * @return the button created
	 */
	private Button createButton(final String buttonText) {
		final Button button = new Button(client, SWT.PUSH);
		button.setText(buttonText);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(button);
		return button;
	}

	/**
	 * Gets elements selected in the TableViewer
	 * @return selection
	 */
	private IStructuredSelection getTableSelection() {
		return (IStructuredSelection) this.tableViewer.getSelection();
	}
	/**
	 * Updates models, table, buttons, and wizard page
	 */
	protected void updatePage() {
		dbc.updateModels();
		tableViewer.refresh();
		setPageComplete(!model.list.isEmpty());
	}

	/**
	 * Returns {@link SoftpackageTableWizardPage.SoftpackageTableWizardPageModel} of this instance
	 * @return model
	 */
	public SoftpackageTableWizardPageModel getModel() {
		return this.model;
	}

	/**
	 * Returns a string representation of the model
	 */
	public String toString() {
		String str = "\n";
		for (SoftpackageModel item : model.list) {
			str += item.toString() + "\n";
		}
		return str;
	}
}
