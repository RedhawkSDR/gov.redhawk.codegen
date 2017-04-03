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
package gov.redhawk.ide.idl.ui.wizard;

import gov.redhawk.ui.validation.ProjectNameValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * @since 1.1
 */
public class ScaIDLProjectPropertiesWizardPage extends WizardNewProjectCreationPage {

	/** The number of columns our widgets are using */
	public static final int NUM_COLUMNS = 3;

	/** The number of rows our widgets are using */
	public static final int NUM_ROWS = 4;

	/** The add new idl(s) button */
	private Button addButton;

	/** The move idl(s) down button */
	private Button downButton;

	/** The list of all idl files for importing */
	private final List<String> idlFiles = new ArrayList<String>();

	/** The idl group */
	private Group idlGroup = null;

	/** The viewer to handle the idl table */
	private TableViewer idlTableViewer;

	/** The name of the interface module text */
	private Text moduleNameText;

	/** The remove idl(s) button */
	private Button removeButton;

	/** The settings group */
	private Group settingsGroup = null;

	/** The move idl(s) up button */
	private Button upButton;

	/** The module version text */
	private Text versionText;

	protected ScaIDLProjectPropertiesWizardPage(final String pageName) {
		super(pageName);
		setTitle("Create a REDHAWK IDL Project");
		setDescription("Enter the settings for the Custom IDL Project");
	}

	private void updateButtons() {
		if (this.idlFiles.isEmpty()) {
			this.removeButton.setEnabled(false);
			this.upButton.setEnabled(false);
			this.downButton.setEnabled(false);
		} else if (this.idlFiles.size() == 1) {
			this.removeButton.setEnabled(true);
			this.upButton.setEnabled(false);
			this.downButton.setEnabled(false);
		} else {
			this.removeButton.setEnabled(true);
			final IStructuredSelection ss = (IStructuredSelection) this.idlTableViewer.getSelection();
			if (ss.isEmpty()) {
				this.upButton.setEnabled(false);
				this.downButton.setEnabled(false);
			} else {
				final int selectionIndex = this.idlTableViewer.getTable().getSelectionIndex();
				if (selectionIndex == 0) {
					this.upButton.setEnabled(false);
					this.downButton.setEnabled(true);
				} else if (selectionIndex < this.idlFiles.size() - 1) {
					this.upButton.setEnabled(true);
					this.downButton.setEnabled(true);
				} else {
					this.upButton.setEnabled(true);
					this.downButton.setEnabled(false);
				}

			}
		}
	}

	/**
	 * Method that creates a file dialog where the user can import idl file(s) into their project.
	 */
	private void addButtonPressed() {
		final FileDialog dialog = new FileDialog(getShell(), SWT.MULTI);
		dialog.setText("Select IDL file(s)");
		dialog.setFilterExtensions(new String[] { "*.idl" });
		String selectedDirectory = dialog.open();

		if (selectedDirectory != null && Arrays.asList(dialog.getFileNames()).size() > 0) {
			final String[] idls = dialog.getFileNames();

			selectedDirectory = new Path(selectedDirectory).removeLastSegments(1).toString() + IPath.SEPARATOR;

			for (final String file : idls) {
				final String newFile = selectedDirectory + file;
				if (!this.idlFiles.contains(newFile)) {
					this.idlFiles.add(newFile);
				}
			}

			this.idlTableViewer.refresh();
			updateModuleName();
		}
	}

	@Override
	protected boolean validatePage() {
		ProjectNameValidator nameValidator = new ProjectNameValidator();

		if (!super.validatePage()) {
			return false;
		}
		if (this.moduleNameText.getText().length() == 0) {
			setMessage("Must enter a module name.", IMessageProvider.ERROR);
			return false;
		}
		if (this.versionText.getText().length() == 0) {
			setMessage("Must enter a version.", IMessageProvider.ERROR);
			return false;
		}

		IStatus status = nameValidator.validate(getProjectName());
		if (!status.isOK()) {
			setMessage(status.getMessage());
			return false;
		}

		return true;
	}

	private void validate() {
		setPageComplete(validatePage());
		if (isPageComplete()) {
			setErrorMessage(null);
			setMessage(null);
		}
	}

	/**
	 * Create control for our Wizard
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite composite = (Composite) getControl();

		// Interface Settings Group
		createInterfaceSettingsGroup(composite);
		this.settingsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		// IDL group
		createIdlGroup(composite);
		this.idlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		// Working Set group
		createWorkingSetGroup(composite, new StructuredSelection(), new String[] { "org.eclipse.ui.resourceWorkingSetPage" });

		setErrorMessage(null);
		setMessage(null);
		setPageComplete(false);
	}

	private void createIdlGroup(final Composite parent) {
		this.idlGroup = new Group(parent, SWT.NONE);
		this.idlGroup.setText("Import existing IDL Files");
		this.idlGroup.setLayout(new GridLayout(ScaIDLProjectPropertiesWizardPage.NUM_COLUMNS, false));

		this.idlTableViewer = new TableViewer(this.idlGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		this.idlTableViewer.setContentProvider(new ArrayContentProvider());
		this.idlTableViewer.setLabelProvider(new LabelProvider());
		this.idlTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, ScaIDLProjectPropertiesWizardPage.NUM_ROWS));
		this.idlTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				updateButtons();
			}
		});
		this.idlTableViewer.setInput(this.idlFiles);

		this.addButton = new Button(this.idlGroup, SWT.NONE);
		this.addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.addButton.setText("Add...");
		this.addButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				addButtonPressed();
				validate();
				updateButtons();
			}
		});

		this.removeButton = new Button(this.idlGroup, SWT.NONE);
		this.removeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.removeButton.setText("Remove");
		this.removeButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				removeButtonPressed();
				validate();
				updateButtons();
			}
		});

		this.upButton = new Button(this.idlGroup, SWT.NONE);
		this.upButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.upButton.setText("Move Up");
		this.upButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				upButtonPressed();
				updateButtons();
			}
		});

		this.downButton = new Button(this.idlGroup, SWT.NONE);
		this.downButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.downButton.setText("Move Down");
		this.downButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				downButtonPressed();
				updateButtons();
			}
		});

		updateButtons();
	}

	private void createInterfaceSettingsGroup(final Composite parent) {
		this.settingsGroup = new Group(parent, SWT.NONE);
		this.settingsGroup.setText("Interface Settings");
		this.settingsGroup.setLayout(new GridLayout(2, false));

		Label l = new Label(this.settingsGroup, SWT.NONE);
		l.setText("Module Name:");
		this.moduleNameText = new Text(this.settingsGroup, SWT.BORDER);
		this.moduleNameText.setText("example");
		this.moduleNameText.setEnabled(true);
		this.moduleNameText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		this.moduleNameText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				validate();
			}
		});

		l = new Label(this.settingsGroup, SWT.NONE);
		l.setText("Version:");
		this.versionText = new Text(this.settingsGroup, SWT.BORDER);
		this.versionText.setText("1.0.0");
		this.versionText.setEnabled(true);
		this.versionText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		this.versionText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				validate();
			}
		});
	}

	/**
	 * Method that will take a group of file(s) and move them down in the hierarchy
	 */
	private void downButtonPressed() {
		if (!this.idlTableViewer.getSelection().isEmpty()) {

			final int index = this.idlTableViewer.getTable().getSelectionIndex();

			if (index + 1 < this.idlFiles.size()) {
				Collections.swap(this.idlFiles, index, index + 1);
				this.idlTableViewer.refresh();
			}
		}
	}

	/**
	 * Method that removes all selected idl file(s) from the table
	 */
	private void removeButtonPressed() {
		if (this.idlTableViewer.getSelection() != null) {
			final StructuredSelection ss = (StructuredSelection) this.idlTableViewer.getSelection();
			this.idlFiles.remove(ss.getFirstElement());
			this.idlTableViewer.refresh();
		}
	}

	/**
	 * Method that will take a group of file(s) and move them up in the hierarchy
	 */
	private void upButtonPressed() {
		if (!this.idlTableViewer.getSelection().isEmpty()) {
			final int index = this.idlTableViewer.getTable().getSelectionIndex();

			if (index > 0) {
				Collections.swap(this.idlFiles, index, index - 1);
				this.idlTableViewer.refresh();
			}
		}
	}

	/**
	 * Scan the first idl file in the list for a module name and if it's different than the current
	 * module name, ask the user if they want to update it.
	 */
	private void updateModuleName() {
		final File file = new File(this.idlFiles.get(0));

		try (Scanner input = new Scanner(file)) {
			while (input.hasNext()) {
				final String line = input.nextLine();

				if (line.contains("module")) {
					final String[] splitNames = line.split("[ ]+");

					for (final String token : splitNames) {
						if (!"module".equals(token) && token.matches("[A-Z]+") && !this.moduleNameText.getText().equalsIgnoreCase(token)) {
							final MessageDialog dialog = new MessageDialog(getShell(), "Update Module Name", null, "Module " + token
								+ " was found in the imported IDL file(s).  Update your project's model name?", MessageDialog.QUESTION, new String[] { "No", "Yes" }, 1);
							final int selection = dialog.open();

							if (selection == 1) {
								this.moduleNameText.setText(token.toLowerCase());
							}
						}
					}

					break;
				}
			}
		} catch (final FileNotFoundException e) {
			return;
		}
	}

	public String getModuleName() {
		return this.moduleNameText.getText();
	}

	public String getInterfaceVersion() {
		return this.versionText.getText();
	}

	public List<String> getIdlFiles() {
		return Collections.unmodifiableList(this.idlFiles);
	}

}
