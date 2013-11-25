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
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesWizardPage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.octave.ui.Activator;
import gov.redhawk.ide.octave.ui.OctaveFunctionVariables;
import gov.redhawk.ide.octave.ui.OctaveProjectProperties;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
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
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import BULKIO.dataDoubleHelper;

/**
 * @since 8.1
 */
public class NewOctaveScaResourceProjectWizard extends NewScaResourceWizard implements IImportWizard {

	private MFileSelectionWizardPage mfileSelectionWizardPage = null;
	private boolean addFollowOnPages = true;
	private OctaveProjectProperties octaveProjectProperties;
	private MFileVariableMapingWizardPage mFileVariableMapingWizardPage;

	public NewOctaveScaResourceProjectWizard() {
		super(ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
		this.setWindowTitle("New Octave Component Project");
		this.setNeedsProgressMonitor(true);
		this.octaveProjectProperties = new OctaveProjectProperties();
	}

	@Override
	public void addPages() {
		setResourcePropertiesPage(new ScaResourceProjectPropertiesWizardPage("", "Octave Component"));
		ScaProjectPropertiesWizardPage wizardPage = getResourcePropertiesPage();
		this.addPage(wizardPage);

		setImplPage(new OctaveImplementationWizardPage("", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
		getImplPage().setDescription("Choose the initial settings for the new implementation.");
		this.addPage(getImplPage());

		this.getImplList().add(new ImplementationAndSettings(getImplPage().getImplementation(), getImplPage().getImplSettings()));

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
		return super.performFinish();
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

		List<IWizardPage> wizPages = getWizPages();
		ImplementationSettings settings = null;
		for (IWizardPage page : wizPages) {
			if (page instanceof BooleanGeneratorPropertiesWizardPage) {
				BooleanGeneratorPropertiesWizardPage wizPage = (BooleanGeneratorPropertiesWizardPage) page;
				settings = wizPage.getSettings();
			}
		}
		if (settings != null) {
			String outputDirStr = settings.getOutputDir();
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
		initPort(providesPort, var, "_in", eSpd);
		ports.getProvides().add(providesPort);
	}
	
	private void initPort(AbstractPort port, OctaveFunctionVariables var, String type, final SoftPkg eSpd) {
		port.setName(var.getName() + type);
		port.setRepID(dataDoubleHelper.id());
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), port.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
	}
	
	private void createUsesPort(final SoftPkg eSpd, OctaveFunctionVariables var) {
		Ports ports = createPorts(eSpd);
		Uses usesPort = ScdFactory.eINSTANCE.createUses();
		initPort(usesPort, var, "_out", eSpd);
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

	/*
	 * Overriding this since it assumes that any new page is an additional implementations page and replaces it if found. 
	 */
	@Override
	public void generatorChanged(Implementation impl, ICodeGeneratorDescriptor codeGeneratorDescriptor) {
		super.generatorChanged(impl, codeGeneratorDescriptor);

		// Now that we've dynamically added the codegen wizard page we need to set it so the next button is set to true.
		for (IWizardPage wizardPage : this.getWizPages()) {
			if (wizardPage instanceof ICodegenWizardPage) {
				((ICodegenWizardPage) wizardPage).setCanFlipToNextPage(true);
			}
		}

		// Pages that are specific to the Octave Project must be added after the implementation page is dynamically added.
		if (addFollowOnPages) {
			setMFileSelectionPage(new MFileSelectionWizardPage(this.octaveProjectProperties, "", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
			this.mfileSelectionWizardPage.setDescription("Choose the primary and any supporitng m-files for this Component. "
				+ "The dependent M-file list may also be modified after creation.");
			addPage(getMFileSelectionPage());

			setMFileVariableMapingWizardPage(new MFileVariableMapingWizardPage(this.octaveProjectProperties, "",
				ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
			this.mFileVariableMapingWizardPage.setDescription("Create a mapping of octave input(s)/output(s) to a REDHAWK port or property "
				+ "Ports and properties may also be modified in the component editor.");

			addPage(getMFileVariableMapingWizardPage());
			addFollowOnPages = false;
		}
		return;
	}

}
