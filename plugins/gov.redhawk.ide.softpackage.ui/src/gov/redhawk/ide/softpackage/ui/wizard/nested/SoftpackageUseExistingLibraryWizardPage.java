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
package gov.redhawk.ide.softpackage.ui.wizard.nested;

import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;

import java.util.Arrays;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class SoftpackageUseExistingLibraryWizardPage extends SoftpackageWizardPage {

	public abstract class SoftpackageWizardGroup extends Composite {

		private final Group group; 
		private final Text fileText;
		private final List fileList;
		private final Button browseButton, addButton, deleteButton;

		public SoftpackageWizardGroup(Composite parent, String title, String[] items) {
			this(parent, title, items, null, null);
		}

		public SoftpackageWizardGroup(Composite parent, String title) {
			this(parent, title, null, null, null);
		}

		public SoftpackageWizardGroup(Composite parent, String title, String[] items, final String[] extensions, final String[] names) {
			super(parent, SWT.NONE);
			setLayout(new GridLayout(1, false));
			setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group = new Group(this, SWT.NONE);
			group.setLayout(new GridLayout(3, false));
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setText(title); 

			fileText = new Text(group, SWT.BORDER);
			fileText.setEditable(false);
			fileText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());

			browseButton = new Button(group, SWT.NONE);
			browseButton.setText("Browse...");

			addButton = addButtonToGroup("Add");

			fileList = new List(group, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
			fileList.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 2).create());

			deleteButton = addButtonToGroup("Delete");
			deleteButton.setEnabled(fileList.getItems().length > 0);   

			fileText.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(final ModifyEvent e) {
					addButton.setEnabled(!fileText.getText().isEmpty());
				}
			});

			browseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					final FileDialog dialog = new FileDialog(browseButton.getShell(), SWT.OPEN);
					if (extensions != null) {
						dialog.setFilterExtensions(extensions);
						dialog.setFilterNames(names);
					}
					final String result = dialog.open();
					if (result != null) {
						fileText.setText(result);
					}
				}
			});

			addButton.setEnabled(!fileText.getText().isEmpty());
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					fileList.add(fileText.getText());
					fileText.setText("");
					deleteButton.setEnabled(fileList.getItems().length > 0 && fileList.getSelectionCount() > 0); 
					updateModels();
				}
			});

			fileList.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) { 
					deleteButton.setEnabled(fileList.getItems().length > 0 && fileList.getSelectionCount() > 0); 
					updateModels();
				}
			});

			deleteButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					fileList.remove(fileList.getSelectionIndices());
					deleteButton.setEnabled(fileList.getItems().length > 0 && fileList.getSelectionCount() > 0); 
					updateModels();
				}
			}); 

			setList(items);
		}

		private Button addButtonToGroup(String text) {
			final Button button = new Button(group, SWT.NONE);
			button.setText(text);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(button);
			return button;
		}

		/**
		 * called when changes are made to the list of file paths
		 */
		public abstract void updateModels();

		/**
		 * sets list of file paths
		 * @param items
		 */
		public void setList(String[] items) {
			if (items != null) {
				fileList.setItems(items);
				updateModels();
			}
		}

		/**
		 * returns list of file paths
		 * @return
		 */
		public String[] getList() {
			return fileList.getItems();
		}
	} 

	public SoftpackageUseExistingLibraryWizardPage() {
		super(new SoftpackageModel(false));
	}

	public SoftpackageUseExistingLibraryWizardPage(SoftpackageModel model) {
		super(model);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		((WizardDialog) this.getWizard().getContainer()).setMinimumPageSize(350, 460);
		
		// library file selection group
		new SoftpackageWizardGroup(client, "Library(s)", model.getLibraries().toArray(new String[0])) {

			@Override
			public void updateModels() {
				model.setLibraries(Arrays.asList(this.getList()));
				dbc.updateModels();
			} 
		};

		// header file selection group
		final SoftpackageWizardGroup headerGroup = new SoftpackageWizardGroup(client, "Header(s)", model.getHeaders().toArray(new String[0]), 
			SoftpackageModel.HEADER_FILE_EXTENSION_FILTERS, SoftpackageModel.HEADER_FILE_EXTENSION_FILTER_NAMES) {

			@Override
			public void updateModels() { 
				model.setHeaders(Arrays.asList(this.getList()));
				dbc.updateModels();
				validateCppOptions();
			} 
		};

		// header file group only available for cpp type
		bindEnablementToCppType(headerGroup.group);
		bindEnablementToCppType(headerGroup.fileText);
		bindEnablementToCppType(headerGroup.browseButton);
		bindEnablementToCppType(headerGroup.fileList);

		// header group button stuff determined by both cpp and the selection of list items
		typeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean cpp = "cpp".equals(typeCombo.getText());
				headerGroup.addButton.setEnabled(cpp && !headerGroup.fileText.getText().isEmpty());
				headerGroup.deleteButton.setEnabled(cpp && headerGroup.fileList.getItemCount() > 0 && headerGroup.fileList.getSelectionCount() > 0);
				validateCppOptions();
			}
		});

		final Group packageConfigGroup = new Group(client, SWT.NULL);
		packageConfigGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		packageConfigGroup.setText("Package Configuration");
		packageConfigGroup.setLayout(new GridLayout(2, false));

		final Text fileText = new Text(packageConfigGroup, SWT.BORDER);
		fileText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		fileText.setEditable(false);
		fileText.setText(model.getPackageConfiguration());
		fileText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) { 
				model.setPackageConfiguration(fileText.getText());
				dbc.updateModels(); 
				validateCppOptions();
			}
		}); 

		final Button browseButton = new Button(packageConfigGroup, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final FileDialog dialog = new FileDialog(browseButton.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(SoftpackageModel.PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTERS);
				dialog.setFilterNames(SoftpackageModel.PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTER_NAMES);
				final String result = dialog.open();
				if (result != null) {
					fileText.setText(result); 
					validateCppOptions();
				}
			}
		});

		// package config group only available for cpp type
		bindEnablementToCppType(packageConfigGroup);
		bindEnablementToCppType(fileText);
		bindEnablementToCppType(browseButton);
	}

	protected void validateCppOptions() { 
		String err = null;
		if ("cpp".equals(typeCombo.getText())) { 
			boolean bothEmpty = model.getHeaders().isEmpty() && model.getPackageConfiguration().isEmpty();
			boolean bothNotEmpty = !model.getHeaders().isEmpty() && !model.getPackageConfiguration().isEmpty();
			err = (bothEmpty || bothNotEmpty) ? null : "Must include both a header file(s) and a package configuration file, or neither.";
		}
		if (err != null) {
			setErrorMessage(err); 
			setPageComplete(err == null);  
		} else { 
			super.validatePage();
		}
	}

	@Override
	protected void validatePage() {
		super.validatePage();
		if (getErrorMessage() == null || getErrorMessage().isEmpty()) {
			validateCppOptions();
		}
	}
}
