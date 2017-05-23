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
package gov.redhawk.ide.octave.ui;

import gov.redhawk.eclipsecorba.idl.IdlInterfaceDcl;
import gov.redhawk.eclipsecorba.library.IdlLibrary;
import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusOctaveGenerator;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory2;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.octave.ui.wizard.MFileSelectionWizardPage;
import gov.redhawk.ide.octave.ui.wizard.MFileVariableMappingWizardPage;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.sca.util.SubMonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.prf.AccessType;
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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import BULKIO.dataDoubleHelper;

public class OctaveGeneratorDisplayFactory implements ICodegenDisplayFactory2 {

	private OctaveProjectProperties octaveProjectProperties;
	private MFileSelectionWizardPage mFileSelectionWizardPage;
	private MFileVariableMappingWizardPage mFileVariableMappingWizardPage;

	@Override
	public ICodegenWizardPage[] createPages() {
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();
		this.octaveProjectProperties = new OctaveProjectProperties();
		this.mFileSelectionWizardPage = new MFileSelectionWizardPage(octaveProjectProperties, "", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
		pages.add(this.mFileSelectionWizardPage);
		this.mFileSelectionWizardPage.setDescription("Select an M-File for this component."
			+ " If the primary M-File depends on non-standard methods, select the dependent M-Files ");

		this.mFileVariableMappingWizardPage = new MFileVariableMappingWizardPage(octaveProjectProperties, "", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);

		mFileVariableMappingWizardPage.setDescription("Create a mapping of octave input(s)/output(s) to a REDHAWK port or property. "
			+ "\nPorts and properties may also be modified in the component editor.");

		pages.add(mFileVariableMappingWizardPage);

		return pages.toArray(new ICodegenWizardPage[pages.size()]);

	}

	@Override
	public ICodegenComposite createComposite(Composite parent, int style, FormToolkit toolkit) {
		return new BooleanGeneratorPropertiesComposite(parent, style, toolkit);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);

		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();

		Assert.isTrue(spdFile.exists());
		final SoftPkg eSpd = SoftPkg.Util.getSoftPkg(resourceSet.getResource(spdUri, true));

		// We need to modify the local file variable since this dictates what is being deployed into the SDRROOT.
		// In the octave case, we need the m-files which would normally be left behind since only the binary is
		// deployed.
		// This causes all of the source code to also be deployed as well which isn't really a bad thing.

		// An assumption is made here that there is only a single Octave implementation. This could potentially cause
		// an edge case if someone wants multiple octave implementations for their component.
		OctaveProjectNature.addNature(project, null, newChild);
		Implementation octaveImpl = null;
		
		for (Implementation impl : eSpd.getImplementation()) {
			String implGeneratorId = CodegenUtil.getImplementationSettings(impl).getGeneratorId();
			if (CplusplusOctaveGenerator.ID.equals(implGeneratorId)) {
				octaveImpl = impl;
				break;
			}
		}
		
		if (octaveImpl == null) {
			Status status = new Status(IStatus.ERROR, OctaveProjectPlugin.PLUGIN_ID, "Could not find Octave implementation, local file in SPD file may not be set.");
			OctaveProjectPlugin.getDefault().getLog().log(status);
		} else {
			
			String[] tokens = octaveImpl.getCode().getLocalFile().getName().split("/");
	
			StringBuilder builder = new StringBuilder();
	
			for (int i = 0; i < tokens.length - 1; i++) {
				builder.append(tokens[i]);
				if (i != tokens.length - 2) {
					builder.append("/");
				}
			}
	
			octaveImpl.getCode().getLocalFile().setName(builder.toString());
		}
		
		// You must have the following properties
		// __mFunction (string, read-only)
		// bufferingEnabled (boolean, default "false")
		// diaryEnabled (boolean, default "false")

		// Create __mFunction
		Simple mFunction = createSimple("__mFunction", "__mFunction", PropertyValueType.STRING, octaveProjectProperties.getFunctionName(),
			PropertyConfigurationType.PROPERTY, AccessType.READONLY);
		eSpd.getPropertyFile().getProperties().getSimple().add(mFunction);

		// Create sampleRate
		Simple bufferingEnabled = createSimple("bufferingEnabled", "bufferingEnabled", PropertyValueType.BOOLEAN, "false",
			PropertyConfigurationType.PROPERTY, AccessType.READWRITE);
		eSpd.getPropertyFile().getProperties().getSimple().add(bufferingEnabled);
		
		Simple diaryEnabled = createSimple("diaryEnabled", "diaryEnabled", PropertyValueType.BOOLEAN, "false",
			PropertyConfigurationType.PROPERTY, AccessType.READWRITE);
		eSpd.getPropertyFile().getProperties().getSimple().add(diaryEnabled);
		
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
			throw new CoreException(new Status(Status.ERROR, OctaveProjectPlugin.PLUGIN_ID, "Failed to write Octave settings to XML files", e));
		}

		// Each of the pages is passed the same settings object so we can grab it from any of them.
		ImplementationSettings settings = this.mFileSelectionWizardPage.getSettings();

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
				try (InputStream inputStream = Files.newInputStream(f.toPath())) {
					targetFile.create(inputStream, true, null);
				} catch (FileNotFoundException e) {
					throw new CoreException(new Status(Status.ERROR, OctaveProjectPlugin.PLUGIN_ID, "Failed to find M-File to copy into project.", e));
				} catch (IOException e) {
					Status status = new Status(IStatus.WARNING, OctaveProjectPlugin.PLUGIN_ID, "Unable to close input file", e);
					OctaveProjectPlugin.getDefault().getLog().log(status);
				}
			}
		}
	}

	private Simple createSimple(String id, String name, PropertyValueType propType, String value, PropertyConfigurationType propConfigType, AccessType mode) {
		// Create __mFunction
		Simple simpProp = PrfFactory.eINSTANCE.createSimple();
		simpProp.setId(id);
		simpProp.setName(name);
		simpProp.setType(propType);
		simpProp.setValue(value);
		final Kind kind = PrfFactory.eINSTANCE.createKind();
		kind.setType(propConfigType);
		simpProp.getKind().add(kind);
		simpProp.setMode(mode);

		return simpProp;
	}

	private void createProvidesPort(final SoftPkg eSpd, OctaveFunctionVariables var) {
		Ports ports = createPorts(eSpd);
		Provides providesPort = ScdFactory.eINSTANCE.createProvides();
		initPort(providesPort, var, eSpd);
		ports.getProvides().add(providesPort);
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

	private void initPort(AbstractPort port, OctaveFunctionVariables var, final SoftPkg eSpd) {
		port.setName(var.getName());
		port.setRepID(dataDoubleHelper.id());
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), port.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
	}

	private void addInterface(final IdlLibrary library, final String repId, Interfaces interfaces) {
		if (containsInterface(interfaces, repId)) {
			return;
		}

		final Interface i = ScdFactory.eINSTANCE.createInterface();
		i.setName(repId);
		i.setRepid(repId);

		final IdlInterfaceDcl idlInter = (IdlInterfaceDcl) library.find(repId);
		// If the interface isn't present in the IdlLibrary, there's nothing to do
		if (idlInter != null) {
			i.setName(idlInter.getName());

			// Add all the inherited interfaces first.
			for (final IdlInterfaceDcl inherited : idlInter.getInheritedInterfaces()) {
				final InheritsInterface iface = ScdFactory.eINSTANCE.createInheritsInterface();
				iface.setRepid(inherited.getRepId());
				i.getInheritsInterfaces().add(iface);

				// If the inherited interface isn't already present, make a recursive call to add it.
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
		if (var.getDefaultValue() != null) {
			simple.setValue(var.getDefaultValue());
		}
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

	@Override
	public ICodegenWizardPage createPage() {
		ICodegenWizardPage[] pages = createPages();

		if (pages.length > 0) {
			return pages[0];
		} else {
			return null;
		}
	}
}
