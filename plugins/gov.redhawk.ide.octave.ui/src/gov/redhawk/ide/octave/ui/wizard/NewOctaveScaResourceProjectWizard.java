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

import gov.redhawk.eclipsecorba.idl.IdlInterfaceDcl;
import gov.redhawk.eclipsecorba.library.IdlLibrary;
import gov.redhawk.ide.codegen.CodegenFactory;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusOctaveGenerator;
import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.octave.ui.Activator;
import gov.redhawk.ide.octave.ui.OctaveFunctionVariables;
import gov.redhawk.ide.octave.ui.OctaveProjectProperties;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
import gov.redhawk.ide.spd.ui.ComponentUiPlugin;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceWizard;
import gov.redhawk.ide.spd.ui.wizard.ScaResourceProjectPropertiesWizardPage;
import gov.redhawk.ide.ui.wizard.IImportWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;
import gov.redhawk.sca.util.SubMonitor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.PropertyConfigurationType;
import mil.jpeojtrs.sca.prf.PropertyValueType;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.scd.AbstractPort;
import mil.jpeojtrs.sca.scd.InheritsInterface;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.Interfaces;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.ScdFactory;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Compiler;
import mil.jpeojtrs.sca.spd.HumanLanguage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.ProgrammingLanguage;
import mil.jpeojtrs.sca.spd.Runtime;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import BULKIO.dataDoubleHelper;

/**
 * @since 8.1
 */
public class NewOctaveScaResourceProjectWizard extends NewScaResourceWizard implements IImportWizard {

	private OctaveProjectProperties octaveProjectProperties;
	private MFileSelectionWizardPage mfileSelectionWizardPage = null;
	private MFileVariableMapingWizardPage mFileVariableMapingWizardPage;
	private ImplementationSettings settings = null;

	public NewOctaveScaResourceProjectWizard() {
		super(ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
		this.setWindowTitle("New Octave Component Project");
		this.setNeedsProgressMonitor(true);
		this.octaveProjectProperties = new OctaveProjectProperties();
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page instanceof ScaResourceProjectPropertiesWizardPage) {
			return getMFileSelectionPage();
		}
		return super.getNextPage(page);
	}
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page instanceof MFileSelectionWizardPage) {
			return getResourcePropertiesPage();
		}
		return super.getPreviousPage(page);
	}
	
	@Override
	public void addPages() {
		setResourcePropertiesPage(new ScaResourceProjectPropertiesWizardPage("", "Octave Component"));
		ScaProjectPropertiesWizardPage wizardPage = getResourcePropertiesPage();

		// Do not allow users to import an SPD file.  We do not allow java or Python Octave projects.  
		wizardPage.setShowContentsGroup(false);
		wizardPage.setShowComponentIDGroup(false);
		wizardPage.setShowWorkingSetGroup(false);
		
		this.addPage(wizardPage);

		setMFileSelectionPage(new MFileSelectionWizardPage(this.octaveProjectProperties, "", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
		this.mfileSelectionWizardPage.setDescription("Choose the primary and any supporitng m-files for this Component. "
			+ "The dependent M-file list may also be modified after creation.");
		addPage(getMFileSelectionPage());

		setMFileVariableMapingWizardPage(new MFileVariableMapingWizardPage(this.octaveProjectProperties, "",
			ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
		this.mFileVariableMapingWizardPage.setDescription("Create a mapping of octave input(s)/output(s) to a REDHAWK port or property "
			+ "Ports and properties may also be modified in the component editor.");

		addPage(getMFileVariableMapingWizardPage());

		
		try {
			final Field field = Wizard.class.getDeclaredField("pages");
			field.getModifiers();
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			this.setWizPages((List<IWizardPage>) field.get(this));
		} catch (final SecurityException e1) {
			// PASS
		} catch (final NoSuchFieldException e1) {
			// PASS
		} catch (final IllegalArgumentException e) {
			// PASS
		} catch (final IllegalAccessException e) {
			// PASS
		}

	}

	@Override
	public boolean performFinish() {
		this.updateEntryPoints();
		final IWorkingSet[] workingSets = getResourcePropertiesPage().getSelectedWorkingSets();
		final String projectName = getResourcePropertiesPage().getProjectName();
		final java.net.URI locationURI;
		if (getResourcePropertiesPage().useDefaults()) {
			locationURI = null;
		} else {
			locationURI = getResourcePropertiesPage().getLocationURI();
		}
		getResourcePropertiesPage().getProjectHandle();
		
		// Create the SCA component project
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(final IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
				try {
					final SubMonitor progress = SubMonitor.convert(monitor, "Creating project...", 4 + getImplList().size()); // SUPPRESS CHECKSTYLE MagicNumber 

					// Create an empty project
					final IProject project = createEmptyProject(projectName, locationURI, progress.newChild(1));
					try {
						if (workingSets.length > 0) {
							PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
						}
						BasicNewProjectResourceWizard.updatePerspective(getfConfig());

						// Create the SCA XML files
						setOpenEditorOn(createComponentFiles(project, projectName, getSoftPkg().getId(), null, progress.newChild(1)));

						// Create the implementation
						final Implementation impl = SpdFactory.eINSTANCE.createImplementation();
						impl.setDescription("The implementation contains descriptive information about the template for a software component.");
						impl.setId("");
						
						
						final ICodeGeneratorDescriptor[] tempCodegens = 
								RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegenByLanguage("C++", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
						
						ICodeGeneratorDescriptor tmpCodeGen = null;
						
						for (ICodeGeneratorDescriptor codeGeneratorDescriptor : tempCodegens) {
							if (codeGeneratorDescriptor.getId().trim().equals(CplusplusOctaveGenerator.ID)) {
								tmpCodeGen = codeGeneratorDescriptor;
								break;
							}
						}
						
						final Compiler compiler = SpdFactory.eINSTANCE.createCompiler();
						compiler.setName(tmpCodeGen.getCompiler());
						compiler.setVersion(tmpCodeGen.getCompilerVersion());
						impl.setCompiler(compiler);
						
						final Runtime runtime = SpdFactory.eINSTANCE.createRuntime();
						runtime.setName(tmpCodeGen.getRuntime());
						runtime.setVersion(tmpCodeGen.getCompilerVersion());
						impl.setRuntime(runtime);
						
						ProgrammingLanguage progLang = SpdFactory.eINSTANCE.createProgrammingLanguage();
						progLang.setName("C++");
						impl.setProgrammingLanguage(progLang);
						
						impl.setId("cpp");
						impl.setRuntime(null);
						
						HumanLanguage humanLang = SpdFactory.eINSTANCE.createHumanLanguage();
						humanLang.setName(RedhawkCodegenActivator.ENGLISH);
						impl.setHumanLanguage(humanLang);
						
						
						settings  = CodegenFactory.eINSTANCE.createImplementationSettings();
						settings.setGeneratedOn(null);
						settings.setGeneratorId(CplusplusOctaveGenerator.ID);
						settings.setOutputDir("cpp");
						settings.setTemplate(CplusplusOctaveGenerator.TEMPLATE);
						
						
						ProjectCreator.addImplementation(project, projectName, impl, settings, progress.newChild(1));
						
						String spdFileName = project.getName() + SpdPackage.FILE_EXTENSION; //SUPPRESS CHECKSTYLE AvoidInLine
						final IFile spdFile = project.getFile(spdFileName);
						
						// Add Additional behavior with this method
						modifyResult(project, spdFile,  progress.newChild(1));
						project.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(1));

					} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
						if (project != null) {
							project.delete(true, progress.newChild(1));
						}
						throw e;
					}
				} catch (final CoreException e) {
					throw e;
				} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
					throw new CoreException(new Status(IStatus.ERROR, ComponentUiPlugin.PLUGIN_ID, "Error creating project", e));
				} finally {

					if (monitor != null) {
						monitor.done();
					}
				}
			}

		};

		try {
			this.getContainer().run(true, false, operation);

			// Open the default editor for the new SCA component; also invoke code generator for manual templates
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			final IFile spdFile = this.getOpenEditorOn();
			if ((spdFile != null) && spdFile.exists()) {
				try {
					IDE.openEditor(activePage, spdFile, true);
				} catch (final PartInitException e) {
					// PASS
				}
			}

			// Only update perspective on new component projects (not imports)

			return true;
		} catch (final InvocationTargetException e1) {
			if (e1.getCause() instanceof CoreException) {
				ComponentUiPlugin.logException(e1);
			}
			return false;
		} catch (final InterruptedException e1) {
			return true;
		}

	}

	@Override
	protected void modifyResult(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);

		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();

		Assert.isTrue(spdFile.exists());
		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		
		// You must have a property of type exec param called __mFunction that has the a type of String
		// and the value is the name of the m-function
		Simple simple = PrfFactory.eINSTANCE.createSimple();
		simple.setId("__mFunction");
		simple.setName("__mFunction");
		simple.setValue(octaveProjectProperties.getFunctionName());
		simple.setType(PropertyValueType.STRING);
		final Kind kind = PrfFactory.eINSTANCE.createKind();
		kind.setType(PropertyConfigurationType.EXECPARAM);
		simple.getKind().add(kind);
		eSpd.getPropertyFile().getProperties().getSimple().add(simple);
		
		for (OctaveFunctionVariables var : octaveProjectProperties.getFunctionInputs()) {
			switch (var.getMapping()) {
			case PORT:
				createProvidesPort(eSpd, var);
				break;
			case PROPERTY_SIMPLE:
				createSimpleProperty(eSpd, var);
				break;
			case PROPERTY_SEQUENCE:
				createSequenceProperty(eSpd, var);
				break;
			default:
				break;
			}
		}

		for (OctaveFunctionVariables var : octaveProjectProperties.getFunctionOutputs()) {
			switch (var.getMapping()) {
			case PORT:
				createUsesPort(eSpd, var);
				break;
			case PROPERTY_SIMPLE:
				createSimpleProperty(eSpd, var);
				break;
			case PROPERTY_SEQUENCE:
				createSequenceProperty(eSpd, var);
				break;
			default:
				break;
			}
		}

		SoftwareComponent scd = eSpd.getDescriptor().getComponent();
		Properties props = eSpd.getPropertyFile().getProperties();

		try {
			eSpd.eResource().save(null);
			scd.eResource().save(null);
			props.eResource().save(null);
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, "Failed to write Octave Settings to SCA resources.", e));
		}

		if (this.settings != null) {
			String outputDirStr = this.settings.getOutputDir();
			IFolder outputDir = project.getFolder(new Path(outputDirStr));
			if (!outputDir.exists()) {
				outputDir.create(true, true, null);
			}
			List<File> files = new ArrayList<File>();
			files.add(octaveProjectProperties.getPrimaryMFile());
			files.addAll(octaveProjectProperties.getmFileDepsList());
			
			for (File f : files) {
				IFile targetFile = outputDir.getFile(f.getName());
				InputStream inputStream = null;
				try {
					inputStream = new BufferedInputStream(new FileInputStream(f));
					targetFile.create(inputStream, true, null);
				} catch (FileNotFoundException e) {
					throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, "Failed to find M-File to copy into project.", e));
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							// PASS
						}
					}
				}
				
			}
		}
	}

	private void addInterface(final IdlLibrary library, final String repId, Interfaces interfaces) {
		if (containsInterface(interfaces, repId)) {
			return;
		}

		final Interface i = ScdFactory.eINSTANCE.createInterface();
		i.setName(repId);
		i.setRepid(repId);

		final IdlInterfaceDcl idlInter = (IdlInterfaceDcl) library.find(repId);
		//If the interface isn't present in the IdlLibrary, there's nothing to do
		if (idlInter != null) {
			i.setName(idlInter.getName());

			//Add all the inherited interfaces first.
			for (final IdlInterfaceDcl inherited : idlInter.getInheritedInterfaces()) {
				final InheritsInterface iface = ScdFactory.eINSTANCE.createInheritsInterface();
				iface.setRepid(inherited.getRepId());
				i.getInheritsInterfaces().add(iface);

				//If the inherited interface isn't already present, make a recursive call to add it.
				addInterface(library, inherited.getRepId(), interfaces);
			}
		}
		interfaces.getInterface().add(i);
	}

	private boolean containsInterface(Interfaces interfaces, String repId) {
		for (Interface i : interfaces.getInterface()) {
			if (i.getRepid().equals(repId)) {
				return true;
			}
		}
		return false;
	}

	private void createProvidesPort(final SoftPkg eSpd, OctaveFunctionVariables var) {
		Ports ports = createPorts(eSpd);
		Provides providesPort = ScdFactory.eINSTANCE.createProvides();
		initPort(providesPort, var, eSpd);
		ports.getProvides().add(providesPort);
	}
	
	private void initPort(AbstractPort port, OctaveFunctionVariables var, final SoftPkg eSpd) {
		port.setName(var.getName());
		port.setRepID(dataDoubleHelper.id());
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), port.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
	}
	
	private void createUsesPort(final SoftPkg eSpd, OctaveFunctionVariables var) {
		Ports ports = createPorts(eSpd);
		Uses usesPort = ScdFactory.eINSTANCE.createUses();
		initPort(usesPort, var, eSpd);
		ports.getUses().add(usesPort);
	}

	private void createSimpleProperty(final SoftPkg eSpd, OctaveFunctionVariables var) {
		for (Simple s : eSpd.getPropertyFile().getProperties().getSimple()) {
			if (s.getId().equals(var.getName())) {
				return;
			}
		}
		Simple simple = PrfFactory.eINSTANCE.createSimple();
		simple.setId(var.getName());
		simple.setName(var.getName());
		switch (var.getType()) {
		case Double_Complex:
			simple.setType(PropertyValueType.DOUBLE);
			simple.setComplex(true);
			break;
		case Double_Real:
			simple.setType(PropertyValueType.DOUBLE);
			break;
		case String:
			simple.setType(PropertyValueType.STRING);
			break;
		}
		eSpd.getPropertyFile().getProperties().getSimple().add(simple);
	}
	
	private void createSequenceProperty(final SoftPkg eSpd, OctaveFunctionVariables var) {
		for (SimpleSequence ss : eSpd.getPropertyFile().getProperties().getSimpleSequence()) {
			if (ss.getId().equals(var.getName())) {
				return;
			}
		}
		SimpleSequence simpleSeq = PrfFactory.eINSTANCE.createSimpleSequence();
		simpleSeq.setId(var.getName());
		simpleSeq.setName(var.getName());
		switch (var.getType()) {
		case Double_Complex:
			simpleSeq.setType(PropertyValueType.DOUBLE);
			simpleSeq.setComplex(true);
			break;
		case Double_Real:
			simpleSeq.setType(PropertyValueType.DOUBLE);
			break;
		case String:
			simpleSeq.setType(PropertyValueType.STRING);
			break;
		}
		eSpd.getPropertyFile().getProperties().getSimpleSequence().add(simpleSeq);
	}

	private Ports createPorts(SoftPkg eSpd) {
		SoftwareComponent scd = eSpd.getDescriptor().getComponent();
		if (scd.getComponentFeatures() == null) {
			scd.setComponentFeatures(ScdFactory.eINSTANCE.createComponentFeatures());
		}
		if (scd.getComponentFeatures().getPorts() == null) {
			scd.getComponentFeatures().setPorts(ScdFactory.eINSTANCE.createPorts());
		}
		return scd.getComponentFeatures().getPorts();
	}

	@Override
	protected IFile createComponentFiles(IProject project, String name, String spdId, String authorName, IProgressMonitor monitor) throws CoreException {
		return ComponentProjectCreator.createComponentFiles(project, name, spdId, authorName, monitor);
	}

	@Override
	protected IProject createEmptyProject(String projectName, java.net.URI locationURI, IProgressMonitor monitor) throws CoreException {
		return ComponentProjectCreator.createEmptyProject(projectName, locationURI, monitor);
	}

	/**
	 * @since 8.0
	 */
	protected void setMFileSelectionPage(MFileSelectionWizardPage mfileSelectionWizardPage) {
		this.mfileSelectionWizardPage = mfileSelectionWizardPage;
	}

	/**
	 * @since 8.0
	 */
	protected void setMFileVariableMapingWizardPage(MFileVariableMapingWizardPage mFileVariableMapingWizardPage) {
		this.mFileVariableMapingWizardPage = mFileVariableMapingWizardPage;
	}

	/**
	 * @return 
	 * @since 8.0
	 */
	protected WizardPage getMFileSelectionPage() {
		return this.mfileSelectionWizardPage;
	}

	/**
	 * @return 
	 * @since 8.0
	 */
	protected WizardPage getMFileVariableMapingWizardPage() {
		return this.mFileVariableMapingWizardPage;
	}
	
	@Override
	public void switchingResourcePage() {
		// Create a softpkg
		final SoftPkg newSoftPkg = SpdFactory.eINSTANCE.createSoftPkg();
		newSoftPkg.setName(getResourcePropertiesPage().getProjectName());
		newSoftPkg.setId(getID());
		this.setSoftPkg(newSoftPkg);
	}
}
