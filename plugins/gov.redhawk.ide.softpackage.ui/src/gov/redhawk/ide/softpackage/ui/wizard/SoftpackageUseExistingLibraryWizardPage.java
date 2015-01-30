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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerObservableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SoftpackageUseExistingLibraryWizardPage extends SoftpackageWizardPage {

	private static class PathItem {
		private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		private String value;

		public PathItem(String text) {
			this.value = text;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			String oldValue = this.value;
			this.value = value;
			pcs.firePropertyChange("value", oldValue, this.value);
		}

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			pcs.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			pcs.removePropertyChangeListener(listener);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}

			PathItem other = (PathItem) obj;
			if (value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!value.equals(other.value)) {
				return false;
			}
			return true;
		}

	}

	public class SoftpackageWizardGroup extends Composite {

		private List<PathItem> fileList = new ArrayList<PathItem>();
		private WritableList writeableList;
		private WritableValue enabled = new WritableValue(true, Boolean.class);

		public SoftpackageWizardGroup(Composite parent, String title, IObservableList backingList) {
			this(parent, title, null, null, backingList);
		}

		public SoftpackageWizardGroup(Composite parent, final String title, final String[] extensions, final String[] names, IObservableList backingList) {
			super(parent, SWT.NONE);
			setLayout(new GridLayout(1, false));
			Group group = new Group(this, SWT.NONE);
			dbc.bindValue(WidgetProperties.enabled().observe(group), enabled);
			group.setLayout(new GridLayout(3, false));
			group.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
			group.setText(title);

			final Text fileText = new Text(group, SWT.BORDER);
			dbc.bindValue(WidgetProperties.enabled().observe(fileText), enabled);
			fileText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

			final ISWTObservableValue fileTextObserver = SWTObservables.observeText(fileText, SWT.Modify);
			final Button browseButton = new Button(group, SWT.NONE);
			dbc.bindValue(WidgetProperties.enabled().observe(browseButton), enabled);
			browseButton.setText("Browse...");

			final Button addButton = addButtonToGroup(group, "Add");
			ComputedValue addButtonEnabled = new ComputedValue() {

				@Override
				protected Object calculate() {
					String fileTextStr = (String) fileTextObserver.getValue();
					boolean exists = new File(fileTextStr).exists();

					boolean textEnabled = (Boolean) enabled.getValue();

					return textEnabled && !fileTextStr.isEmpty() && exists;
				}

			};
			Binding binding = dbc.bindValue(fileTextObserver, new WritableValue("", String.class),
				new UpdateValueStrategy().setAfterConvertValidator(new IValidator() {

					@Override
					public IStatus validate(Object value) {
						String fileTextStr = (String) value;
						if (fileTextStr.isEmpty() || new File(fileTextStr).exists()) {
							return ValidationStatus.ok();
						} else {
							return ValidationStatus.error("No such file or directory: " + fileTextStr);
						}
					}

				}), null);
			ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);

			dbc.bindValue(SWTObservables.observeEnabled(addButton), addButtonEnabled);

			final ListViewer fileListViewer = new ListViewer(group, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
			dbc.bindValue(WidgetProperties.enabled().observe(fileListViewer.getControl()), enabled);
			fileListViewer.getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 2).create());

			writeableList = new WritableList(this.fileList, PathItem.class);
			ViewerSupport.bind(fileListViewer, writeableList, BeanProperties.value(PathItem.class, "value"));

			final Button deleteButton = addButtonToGroup(group, "Delete");
			final IViewerObservableList selectionObserver = ViewersObservables.observeMultiSelection(fileListViewer);

			ComputedValue deleteButtonEnabled = new ComputedValue() {

				@Override
				protected Object calculate() {
					boolean textEnabled = (Boolean) enabled.getValue();
					return textEnabled && !selectionObserver.isEmpty();
				}

			};
			dbc.bindValue(SWTObservables.observeEnabled(deleteButton), deleteButtonEnabled);

			browseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					String startingPosition = fileText.getText();

					final FileDialog dialog = new FileDialog(browseButton.getShell(), SWT.OPEN | SWT.MULTI);
					dialog.setText("Select " + title);
					dialog.setFileName(startingPosition);
					if (extensions != null) {
						dialog.setFilterExtensions(extensions);
						dialog.setFilterNames(names);
					}
					dialog.open();
					String[] results = dialog.getFileNames();
					String path = dialog.getFilterPath();
					for (String result : results) {
						PathItem newItem = new PathItem(path + File.separator + result);
						if (!writeableList.contains(newItem)) {
							writeableList.add(newItem);
						}
					}
					fileText.setText(path);
				}
			});

			addButton.setEnabled(!fileText.getText().isEmpty());
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					PathItem newItem = new PathItem(fileText.getText());
					if (!writeableList.contains(newItem)) {
						writeableList.add(newItem);
					}
					fileText.setText("");
				}
			});

			deleteButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					List< ? > selection = ((IStructuredSelection) fileListViewer.getSelection()).toList();
					writeableList.removeAll(selection);
				}
			});

			dbc.bindList(writeableList, backingList, new UpdateListStrategy().setConverter(new Converter(PathItem.class, String.class) {

				@Override
				public Object convert(Object fromObject) {
					return ((PathItem) fromObject).value;
				}

			}), new UpdateListStrategy().setConverter(new Converter(String.class, PathItem.class) {

				@Override
				public Object convert(Object fromObject) {
					return new PathItem((String) fromObject);
				}

			}));
		}

		private Button addButtonToGroup(Group group, String text) {
			final Button button = new Button(group, SWT.NONE);
			button.setText(text);
			GridDataFactory.fillDefaults().applyTo(button);
			return button;
		}

		/**
		 * sets list of file paths
		 * @param items
		 */
		public void setList(String[] items) {
			if (items != null) {
				writeableList.clear();
				for (String s : items) {
					writeableList.add(new PathItem(s));
				}

			}
		}

		/**
		 * returns list of file paths
		 * @return
		 */
		public List<PathItem> getList() {
			return fileList;
		}
	}

	public SoftpackageUseExistingLibraryWizardPage(String pageName, String componentType) {
		super(pageName, new SoftpackageModel(false), componentType);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		((WizardDialog) this.getWizard().getContainer()).setMinimumPageSize(350, 460);

		// library file selection group
		SoftpackageWizardGroup libraryGroup = new SoftpackageWizardGroup(client, "Library(s)", model.getLibrariesList());
		libraryGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		// header file selection group
		final SoftpackageWizardGroup headerGroup = new SoftpackageWizardGroup(client, "Header(s)", SoftpackageModel.HEADER_FILE_EXTENSION_FILTERS,
			SoftpackageModel.HEADER_FILE_EXTENSION_FILTER_NAMES, model.getHeadersList());
		headerGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		// header file group only available for cpp type
		dbc.bindValue(headerGroup.enabled, BeansObservables.observeValue(model, SoftpackageModel.ENABLED_CPP_OPTIONS));

		final Group packageConfigGroup = new Group(client, SWT.NONE);
		packageConfigGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		packageConfigGroup.setText("Package Configuration");
		packageConfigGroup.setLayout(new GridLayout(2, false));

		Label spacer = new Label(client, SWT.NONE);
		spacer.setText(" ");
		spacer.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		final Text fileText = new Text(packageConfigGroup, SWT.BORDER);
		fileText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		Binding binding = dbc.bindValue(SWTObservables.observeText(fileText, SWT.Modify),
			BeansObservables.observeValue(model, SoftpackageModel.PACKAGE_CONFIGURATION_PATH),
			new UpdateValueStrategy().setAfterConvertValidator(new IValidator() {

				@Override
				public IStatus validate(Object value) {
					String strValue = (String) value;
					if (strValue.isEmpty()) {
						return ValidationStatus.ok();
					} else {
						File packageFile = new File(strValue);
						if (packageFile.exists()) {
							if (!packageFile.isFile() || !packageFile.getName().endsWith(".pc")) {
								return ValidationStatus.error("Invalid package configuration file (*.pc)");
							} else {
								return ValidationStatus.ok();
							}
						} else {
							return ValidationStatus.error("No such file or directory " + strValue);
						}
					}
				}

			}), null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);

		final Button browseButton = new Button(packageConfigGroup, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final FileDialog dialog = new FileDialog(browseButton.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(SoftpackageModel.PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTERS);
				dialog.setFilterNames(SoftpackageModel.PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTER_NAMES);
				dialog.setFileName(fileText.getText());
				final String result = dialog.open();
				if (result != null) {
					fileText.setText(result);
				}
			}
		});

		// package config group only available for cpp type
		bindEnablementToCppType(packageConfigGroup);
		bindEnablementToCppType(fileText);
		bindEnablementToCppType(browseButton);

		final IObservableValue packageConfigurationObserver = BeansObservables.observeValue(model, SoftpackageModel.PACKAGE_CONFIGURATION_PATH);

		final IObservableValue typeObserver = BeansObservables.observeValue(model, SoftpackageModel.TYPE_NAME);
		dbc.addValidationStatusProvider(new ValidationStatusProvider() {

			private ComputedValue validationStatus = new ComputedValue() {

				@Override
				protected Object calculate() {
					String typeName = (String) typeObserver.getValue();
					String packageConfiguration = (String) packageConfigurationObserver.getValue();
					boolean headersEmpty = model.getHeadersList().isEmpty();
					if ("cpp".equals(typeName)) {
						boolean bothEmpty = headersEmpty && (packageConfiguration == null || packageConfiguration.isEmpty());
						boolean bothNotEmpty = !headersEmpty && (packageConfiguration != null && !packageConfiguration.isEmpty());
						if (!bothEmpty && !bothNotEmpty) {
							return ValidationStatus.error("Must include both a header file(s) and a package configuration file, or neither.");
						}
					}
					return ValidationStatus.ok();
				}
			};
			private IObservableList targets = new WritableList();
			{
				targets.add(validationStatus);
			}
			private IObservableList models = Observables.emptyObservableList();

			@Override
			public IObservableValue getValidationStatus() {
				return validationStatus;
			}

			@Override
			public IObservableList getTargets() {
				return targets;
			}

			@Override
			public IObservableList getModels() {
				return models;
			}
		});

		WizardPageSupport.create(this, dbc);

	}

	/**
	 * Binds enablement given control to the model's value regarding cpp options
	 * @param control
	 */
	private void bindEnablementToCppType(Control control) {
		dbc.bindValue(SWTObservables.observeEnabled(control), BeansObservables.observeValue(model, SoftpackageModel.ENABLED_CPP_OPTIONS));
	}
}
