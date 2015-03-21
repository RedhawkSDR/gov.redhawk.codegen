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
package gov.redhawk.ide.sharedlibrary.ui.wizard;

import gov.redhawk.ide.sharedlibrary.ui.wizard.models.SharedLibraryModel;
import gov.redhawk.ide.spd.ui.wizard.ScaResourceProjectPropertiesWizardPage;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SharedLibraryProjectPropertiesWizardPage extends ScaResourceProjectPropertiesWizardPage {

	private Combo typeCombo;
	private final DataBindingContext dbc;
	private final SharedLibraryModel model;
	private Binding currentMFileBindValue;

	private Text mFileTextBox;
	private Button mFileBrowseButton;
	private Button mFileAddButton;
	private ListViewer mFileListViewer;
	private Button mFileRemoveButton;

	public SharedLibraryProjectPropertiesWizardPage(String pageName, String type) {
		super(pageName, type);
		setShowContentsGroup(false);
		setShowComponentIDGroup(false);
		this.model = new SharedLibraryModel();
		this.dbc = new DataBindingContext();
		this.setDescription("Create a new Shared Library");
	}

	@Override
	public void customCreateControl(Composite composite) {
		Group contentsGroup = new Group((Composite) getControl(), SWT.NONE);
		contentsGroup.setText("Contents");
		contentsGroup.setLayout(new GridLayout(4, false));
		contentsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		Label label = new Label(contentsGroup, SWT.NULL);
		label.setText("Type:");

		typeCombo = new Combo(contentsGroup, SWT.BORDER | SWT.READ_ONLY);
		typeCombo.setItems(SharedLibraryModel.TYPES);
		typeCombo.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).span(3, 1).create());
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SharedLibraryWizardPage implPage = (SharedLibraryWizardPage) getNextPage();
				implPage.handleTypeSelectionChanged();
				updatePageControls();
				getWizard().getContainer().updateButtons();
			}

		});
		typeCombo.select(0);

		createMFileSection(contentsGroup);

		bind();
	}

	private void updatePageControls() {
		if (SharedLibraryModel.CPP_TYPE.equals(model.getTypeName())) {
			mFileTextBox.setEnabled(false);
			mFileBrowseButton.setEnabled(false);
			mFileAddButton.setEnabled(false);
			mFileRemoveButton.setEnabled(false);
		} else if (SharedLibraryModel.OCTAVE_TYPE.equals(model.getTypeName())) {
			mFileTextBox.setEnabled(true);
			mFileBrowseButton.setEnabled(true);
			mFileAddButton.setEnabled(true);
			mFileRemoveButton.setEnabled(true);
		}
	}

	/**
	 * @param contentsGroup
	 */
	private void createMFileSection(final Group contentsGroup) {
		mFileTextBox = new Text(contentsGroup, SWT.BORDER);
		mFileTextBox.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		mFileTextBox.setEnabled(false);

		mFileBrowseButton = new Button(contentsGroup, SWT.PUSH);
		mFileBrowseButton.setText("Browse");
		mFileBrowseButton.setLayoutData(GridDataFactory.swtDefaults().create());
		mFileBrowseButton.setEnabled(false);

		mFileAddButton = new Button(contentsGroup, SWT.PUSH);
		mFileAddButton.setText("Add");
		mFileAddButton.setLayoutData(GridDataFactory.swtDefaults().create());
		mFileAddButton.setEnabled(false);

		mFileListViewer = new ListViewer(contentsGroup);
		mFileListViewer.getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());
		setMFileListViewerProviders();

		mFileRemoveButton = new Button(contentsGroup, SWT.PUSH);
		mFileRemoveButton.setText("Remove");
		mFileRemoveButton.setLayoutData(GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).create());
		mFileRemoveButton.setEnabled(false);

		addButtonListeners(contentsGroup);
	}

	private void setMFileListViewerProviders() {
		this.mFileListViewer.setContentProvider(new IStructuredContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof List< ? >) {
					return ((List< ? >) inputElement).toArray();
				}
				return null;
			}
		});

		this.mFileListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof File) {
					return ((File) element).getAbsolutePath();
				}
				return super.getText(element);
			}
		});

		mFileListViewer.setInput(this.model.getmFilesList());
	}

	private void addButtonListeners(final Group contentsGroup) {
		this.mFileBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(contentsGroup.getShell(), SWT.MULTI);
				String firstFilePath = fd.open();
				if (firstFilePath != null) {
					String filePath = (new File(firstFilePath)).getParent();
					String[] selectedFiles = fd.getFileNames();
					for (String fileName : selectedFiles) {
						File newFile = new File(filePath + File.separator + fileName);
						// If the file is not already in the list.
						if (!SharedLibraryProjectPropertiesWizardPage.this.model.getmFilesList().contains(newFile)) {
							SharedLibraryProjectPropertiesWizardPage.this.model.getmFilesList().add(newFile);
							SharedLibraryProjectPropertiesWizardPage.this.mFileListViewer.refresh();
							getWizard().getContainer().updateButtons();
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		this.mFileAddButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// If the file is valid
				Object currentValidationStatus = SharedLibraryProjectPropertiesWizardPage.this.currentMFileBindValue.getValidationStatus().getValue();
				File file = SharedLibraryProjectPropertiesWizardPage.this.model.getCurrentMFile();
				if (((IStatus) currentValidationStatus).isOK() && file != null) {
					// If the file is not already in the list.
					if (!SharedLibraryProjectPropertiesWizardPage.this.model.getmFilesList().contains(file)) {
						SharedLibraryProjectPropertiesWizardPage.this.model.getmFilesList().add(file);
						SharedLibraryProjectPropertiesWizardPage.this.mFileListViewer.refresh();
						SharedLibraryProjectPropertiesWizardPage.this.mFileTextBox.setText("");
						getWizard().getContainer().updateButtons();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		this.mFileRemoveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection activeViewerSelection = SharedLibraryProjectPropertiesWizardPage.this.mFileListViewer.getSelection();
				if (activeViewerSelection instanceof StructuredSelection) {
					for (Object fileToRemove : ((StructuredSelection) activeViewerSelection).toArray()) {
						SharedLibraryProjectPropertiesWizardPage.this.model.getmFilesList().remove(fileToRemove);
					}
					SharedLibraryProjectPropertiesWizardPage.this.mFileListViewer.refresh();
					getWizard().getContainer().updateButtons();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private void bind() {
		// Binds model type name with type combo
		dbc.bindValue(SWTObservables.observeSelection(typeCombo), BeansObservables.observeValue(model, SharedLibraryModel.TYPE_NAME));

		// Binds the mFile Text box for validation purposes
		IObservableValue currentMFileModel = BeanProperties.value("currentMFile").observe(this.model);
		IObservableValue currentMFileTarget = WidgetProperties.text(SWT.Modify).observe(this.mFileTextBox);
		UpdateValueStrategy mFileUpdateStrat = new UpdateValueStrategy();
		mFileUpdateStrat.setConverter(getStringToFileConverter());
		mFileUpdateStrat.setAfterConvertValidator(getMFileNameValidator());
		currentMFileBindValue = dbc.bindValue(currentMFileTarget, currentMFileModel, mFileUpdateStrat, null);
	}

	private IConverter getStringToFileConverter() {
		return new IConverter() {

			@Override
			public Object getToType() {
				return File.class;
			}

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object convert(Object fromObject) {
				if (fromObject == null || ((String) fromObject).equals("")) {
					return null;
				} else {
					return new File((String) fromObject);
				}
			}
		};
	}

	private IValidator getMFileNameValidator() {
		return new IValidator() {

			@Override
			public IStatus validate(Object value) {
				if (SharedLibraryModel.OCTAVE_TYPE.equals(model.getTypeName()) && (value == null || !((File) value).exists())) {
					return ValidationStatus.error("M-File location must be provided");
				}

				return ValidationStatus.ok();
			}
		};
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.model.addPropertyChangeListener(listener);
	}

	@Override
	public boolean isPageComplete() {
		if (typeCombo.getText().isEmpty()) {
			return false;
		}

		if (SharedLibraryModel.OCTAVE_TYPE.equals(model.getTypeName())) {
			if (mFileListViewer.getList().getItemCount() < 1) {
				return false;
			}
		}

		return super.isPageComplete();
	}

	public SharedLibraryModel getModel() {
		return model;
	}

	/*
	 * The next page is the implementation page, which we don't want users going to
	 * The implementation page just exists since we want to take advantage of two super classes, 
	 * and Java doesn't allow multiple-inheritance
	 */
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
}
