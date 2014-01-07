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
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory2;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.octave.ui.wizard.MFileSelectionWizardPage;
import gov.redhawk.ide.octave.ui.wizard.MFileVariableMapingWizardPage;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.sca.util.SubMonitor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
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
	private MFileVariableMapingWizardPage mFileVariableMapingWizardPage;
	
	@Override
	public ICodegenWizardPage[] createPages() {
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();
		this.octaveProjectProperties = new OctaveProjectProperties();
		this.mFileSelectionWizardPage = new MFileSelectionWizardPage(octaveProjectProperties, "", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);
		pages.add(this.mFileSelectionWizardPage);

		this.mFileVariableMapingWizardPage = new MFileVariableMapingWizardPage(octaveProjectProperties, "",
			ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);

		mFileVariableMapingWizardPage.setDescription("Create a mapping of octave input(s)/output(s) to a REDHAWK port or property "
			+ "Ports and properties may also be modified in the component editor.");

		pages.add(mFileVariableMapingWizardPage);

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
