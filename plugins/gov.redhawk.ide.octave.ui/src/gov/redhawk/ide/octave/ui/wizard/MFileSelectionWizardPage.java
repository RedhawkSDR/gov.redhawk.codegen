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
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusOctaveGenerator;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.octave.ui.OctaveFunctionVariables;
import gov.redhawk.ide.octave.ui.OctaveProjectPlugin;
import gov.redhawk.ide.octave.ui.OctaveProjectProperties;
import gov.redhawk.ide.octave.ui.OctaveVariableMappingEnum;
import gov.redhawk.ide.octave.ui.OctaveVariableTypeEnum;
import gov.redhawk.mfile.parser.MFileParser;
import gov.redhawk.mfile.parser.ParseException;
import gov.redhawk.mfile.parser.TokenMgrError;
import gov.redhawk.mfile.parser.model.Function;
import gov.redhawk.mfile.parser.model.MFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @since 8.1
 */
public class MFileSelectionWizardPage extends WizardPage implements ICodegenWizardPage {

	/** The Constant TITLE_IMAGE. */
	private static final ImageDescriptor TITLE_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(OctaveProjectPlugin.PLUGIN_ID, "icons/octaveLogo.png");
	private static final int NUM_COLUMNS = 3;
	private ListViewer depsListViewer = null;
	private Text primaryMFileTextBox;
	private OctaveProjectProperties octaveProjProps;
	private Button dependencyCheckBox;
	private Text dependencyTextBox;
	private Button depsBrowseButton;
	private Button depsAddButton;
	private Button depsRemoveButton;
	private Binding currentDepBindValue;
	private Binding primaryMFileBindValue;

	private Implementation impl;
	private ImplementationSettings implSettings;

	public MFileSelectionWizardPage(final OctaveProjectProperties octaveProjProps, final String name, final String componenttype) {
		super(name, "New M-File", MFileSelectionWizardPage.TITLE_IMAGE);
		this.octaveProjProps = octaveProjProps;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);

		// Creates the basic layout of the UI elements
		createUIElements(client);

		// A simple content provider for the List of IFile elements.
		this.depsListViewer.setContentProvider(new IStructuredContentProvider() {

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

		// Label provider that prints the full OS Specific file path to the objects in the IFile array.
		this.depsListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof File) {
					return ((File) element).getAbsolutePath();
				}
				return super.getText(element);
			}
		});

		this.depsListViewer.setInput(this.octaveProjProps.getmFileDepsList());

		bindValues();

		this.setControl(client);
	}

	private void createUIElements(final Composite client) {
		client.setLayout(new GridLayout(MFileSelectionWizardPage.NUM_COLUMNS, false));

		final Label primaryMFileLabel = new Label(client, SWT.NULL);
		primaryMFileLabel.setText("Primary M-file:");

		primaryMFileTextBox = new Text(client, SWT.BORDER);
		primaryMFileTextBox.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		final Button mFileSelectionBrowseButton = new Button(client, SWT.PUSH);
		mFileSelectionBrowseButton.setText("Browse");
		mFileSelectionBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(client.getShell(), SWT.OPEN);
				String selectedFile = fd.open();

				if (selectedFile != null) {
					MFileSelectionWizardPage.this.primaryMFileTextBox.setText(selectedFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final Group dependenciesGroup = new Group(client, SWT.None);
		dependenciesGroup.setText("Dependencies");
		dependenciesGroup.setLayout(new GridLayout(3, false));
		dependenciesGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(MFileSelectionWizardPage.NUM_COLUMNS, 1).create());

		dependencyCheckBox = new Button(dependenciesGroup, SWT.CHECK);
		dependencyCheckBox.setLayoutData(GridDataFactory.fillDefaults().span(3, 1).create());
		dependencyCheckBox.setText("Primary M-file depends on non-standard M-files");
		dependencyCheckBox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				MFileSelectionWizardPage.this.dependencyTextBox.setText("");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		dependencyTextBox = new Text(dependenciesGroup, SWT.BORDER);
		dependencyTextBox.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		depsBrowseButton = new Button(dependenciesGroup, SWT.PUSH);
		depsBrowseButton.setText("Browse");
		depsBrowseButton.setLayoutData(GridDataFactory.swtDefaults().create());
		depsBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(client.getShell(), SWT.MULTI);
				String firstFilePath = fd.open();
				if (firstFilePath != null) {
					String filePath = (new File(firstFilePath)).getParent();
					String[] selectedFiles = fd.getFileNames();
					for (String fileName : selectedFiles) {
						MFileSelectionWizardPage.this.octaveProjProps.getmFileDepsList().add(new File(filePath + File.separator + fileName));
					}
					MFileSelectionWizardPage.this.depsListViewer.refresh();	
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		depsAddButton = new Button(dependenciesGroup, SWT.PUSH);
		depsAddButton.setText("Add");
		depsAddButton.setLayoutData(GridDataFactory.swtDefaults().create());

		this.depsListViewer = new ListViewer(dependenciesGroup);
		depsListViewer.getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		depsRemoveButton = new Button(dependenciesGroup, SWT.PUSH);
		depsRemoveButton.setText("Remove");
		depsRemoveButton.setLayoutData(GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).create());

		depsAddButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// If the file is valid
				Object currentValidationStatus = MFileSelectionWizardPage.this.currentDepBindValue.getValidationStatus().getValue();
				if (((IStatus) currentValidationStatus).isOK() && MFileSelectionWizardPage.this.octaveProjProps.getCurrentDepFile() != null) {
					// If the file is not already in the list.
					if (Files.isDirectory(MFileSelectionWizardPage.this.octaveProjProps.getCurrentDepFile().toPath(), LinkOption.NOFOLLOW_LINKS)) {
						return;
					}
					
					if (!MFileSelectionWizardPage.this.octaveProjProps.getmFileDepsList().contains(
						MFileSelectionWizardPage.this.octaveProjProps.getCurrentDepFile())) {
						MFileSelectionWizardPage.this.octaveProjProps.getmFileDepsList().add(MFileSelectionWizardPage.this.octaveProjProps.getCurrentDepFile());
						MFileSelectionWizardPage.this.depsListViewer.refresh();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		depsRemoveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection activeViewerSelection = MFileSelectionWizardPage.this.depsListViewer.getSelection();
				if (activeViewerSelection instanceof StructuredSelection) {
					for (Object fileToRemove : ((StructuredSelection) activeViewerSelection).toArray()) {
						MFileSelectionWizardPage.this.octaveProjProps.getmFileDepsList().remove(fileToRemove);
					}
					MFileSelectionWizardPage.this.depsListViewer.refresh();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private void bindValues() {
		// create new Context
		DataBindingContext ctx = new DataBindingContext();

		// Defining the Model objects as Beans
		@SuppressWarnings("unchecked")
		IObservableValue< ? > primaryMFileModel = BeanProperties.value("primaryMFile").observe(this.octaveProjProps);
		@SuppressWarnings("unchecked")
		IObservableValue< ? > currentDepFileModel = BeanProperties.value("currentDepFile").observe(this.octaveProjProps);
		@SuppressWarnings("unchecked")
		IObservableValue< ? > hasDepsModel = BeanProperties.value("hasDeps").observe(this.octaveProjProps);

		// Defining the Target objects
		IObservableValue< ? > primaryMFileTarget = WidgetProperties.text(SWT.Modify).observe(this.primaryMFileTextBox);
		IObservableValue< ? > currentDepFileTarget = WidgetProperties.text(SWT.Modify).observe(this.dependencyTextBox);
		IObservableValue< ? > hasDepsTarget = WidgetProperties.selection().observe(this.dependencyCheckBox);

		// The target objects that need to be disabled when the hasDeps model object changes.
		IObservableValue< ? > depsTextBoxTarget = WidgetProperties.enabled().observe(this.dependencyTextBox);
		IObservableValue< ? > depsAddButtonTarget = WidgetProperties.enabled().observe(this.depsAddButton);
		IObservableValue< ? > depsBrowseButtonTarget = WidgetProperties.enabled().observe(this.depsBrowseButton);
		IObservableValue< ? > depsRemoveButtonTarget = WidgetProperties.enabled().observe(this.depsRemoveButton);
		IObservableValue< ? > depsListViewerTarget = WidgetProperties.enabled().observe(this.depsListViewer.getControl());

		// Define my strategy for the Primary & Deps M-File bindings.
		UpdateValueStrategy primaryMFileUpdateStrat = new UpdateValueStrategy();
		primaryMFileUpdateStrat.setConverter(getStringToFileConverter());
		primaryMFileUpdateStrat.setAfterConvertValidator(getPrimaryMFileNameValidator());

		UpdateValueStrategy depsMFileUpdateStrat = new UpdateValueStrategy();
		depsMFileUpdateStrat.setConverter(getStringToFileConverter());
		depsMFileUpdateStrat.setAfterConvertValidator(getDepMFileNameValidator());

		// connect them
		primaryMFileBindValue = ctx.bindValue(primaryMFileTarget, primaryMFileModel, primaryMFileUpdateStrat, null);
		currentDepBindValue = ctx.bindValue(currentDepFileTarget, currentDepFileModel, depsMFileUpdateStrat, null);
		ctx.bindValue(hasDepsTarget, hasDepsModel);

		// The bindings for the disabling of the controls
		ctx.bindValue(depsTextBoxTarget, hasDepsModel, null, new UpdateValueStrategy());
		ctx.bindValue(depsAddButtonTarget, hasDepsModel, null, new UpdateValueStrategy());
		ctx.bindValue(depsBrowseButtonTarget, hasDepsModel, null, new UpdateValueStrategy());
		ctx.bindValue(depsRemoveButtonTarget, hasDepsModel, null, new UpdateValueStrategy());
		ctx.bindValue(depsListViewerTarget, hasDepsModel, null, new UpdateValueStrategy());

		// add cool control decoration
		ControlDecorationSupport.create(primaryMFileBindValue, SWT.TOP | SWT.LEFT);
		ControlDecorationSupport.create(currentDepBindValue, SWT.TOP | SWT.LEFT);

		WizardPageSupport.create(this, ctx);
	}

	private IValidator getPrimaryMFileNameValidator() {
		return new IValidator() {

			@Override
			public IStatus validate(Object value) {
				if (value == null) {
					return ValidationStatus.error("Please provide an Octave script");
				}
				if (!((File) value).exists()) {
					return ValidationStatus.error("File does not exist");
				}
				if (((File) value).isDirectory()) {
					return ValidationStatus.error("Location is a directory");
				}

				// Parse the current file selection and set the Octave Project Properties object
				try {
					parseFile((File) value);
				} catch (IOException | ParseException | TokenMgrError e) {
					return ValidationStatus.error("Error parsing script file: " + e.getMessage());
				}

				return ValidationStatus.ok();
			}
		};
	}

	private IValidator getDepMFileNameValidator() {
		return new IValidator() {

			@Override
			public IStatus validate(Object value) {
				if (!MFileSelectionWizardPage.this.octaveProjProps.getHasDeps() || value == null) {
					return ValidationStatus.ok();
				} else if (value == null || !((File) value).exists()) {
					return ValidationStatus.error("M-File location must be provided");
				}

				return ValidationStatus.ok();
			}
		};
	}

	private IConverter getStringToFileConverter() {
		return new Converter(String.class, File.class) {
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

	/**
	 * Parses the m-file set the function inputs and outputs. 
	 * @param file The m-file to parse
	 * @throws ParseException If the M-File cannot be parsed the values are not set
	 * @throws FileNotFoundException If the M-File cannot be found.
	 */
	private void parseFile(File file) throws FileNotFoundException, ParseException {
		MFile mFile = MFileParser.parse(new FileInputStream(file), null);
		Function function = mFile.getFunction();

		List<OctaveFunctionVariables> inputs = new ArrayList<OctaveFunctionVariables>();
		if (function == null) {
			throw new ParseException("No function was found in the script file");
		}
		for (String inputVar : function.getInputs()) {
			OctaveFunctionVariables ofv = new OctaveFunctionVariables(true);
			ofv.setName(inputVar);
			// Default values
			if (function.getInputDefaultValues().containsKey(inputVar)) {
				ofv.setMapping(OctaveVariableMappingEnum.PROPERTY_SIMPLE);
				ofv.setDefaultValue(function.getInputDefaultValues().get(inputVar).toString());
			} else {
				ofv.setMapping(OctaveVariableMappingEnum.PORT);
			}
			ofv.setType(OctaveVariableTypeEnum.Double_Real);
			inputs.add(ofv);
		}
		this.octaveProjProps.setFunctionInputs(inputs);

		List<OctaveFunctionVariables> outputs = new ArrayList<OctaveFunctionVariables>();
		for (String outputVar : function.getOutputs()) {
			OctaveFunctionVariables ofv = new OctaveFunctionVariables(false);
			ofv.setName(outputVar);
			// Default values
			ofv.setMapping(OctaveVariableMappingEnum.PORT);
			ofv.setType(OctaveVariableTypeEnum.Double_Real);
			outputs.add(ofv);
		}
		this.octaveProjProps.setFunctionOutputs(outputs);
		this.octaveProjProps.setFunctionName(function.getName());
	}


	@Override
	public void configure(SoftPkg softpkg, Implementation impl, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		this.impl = impl;
		this.implSettings = implSettings;
		
		this.implSettings.setOutputDir("cpp");
		this.implSettings.setTemplate(CplusplusOctaveGenerator.TEMPLATE);
	}

	@Override
	public ImplementationSettings getSettings() {
		return this.implSettings;
	}
	
	public Implementation getImpl() {
		return this.impl;
	}

	@Override
	public boolean canFinish() {
		return false;
	}

	@Override
	public void setCanFlipToNextPage(boolean canFlip) {
		
	}

	@Override
	public void setCanFinish(boolean canFinish) {
	}
}
