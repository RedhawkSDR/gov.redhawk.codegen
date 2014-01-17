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
package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.eclipsecorba.idl.IdlInterfaceDcl;
import gov.redhawk.eclipsecorba.library.IdlLibrary;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndProp;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndWizardPage;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory2;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.sca.util.SubMonitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructPropertyConfigurationType;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.InheritsInterface;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.Interfaces;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.ScdFactory;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;

public class FrontEndGeneratorDisplayFactory implements ICodegenDisplayFactory2 {

	private FrontEndWizardPage frontEndWizardPage;

	@Override
	public ICodegenWizardPage createPage() {
		ICodegenWizardPage[] pages = createPages();
		
		if (pages.length > 0) {
			return pages[0];
		} else {
			return null;
		}
	}

	/**
	 * Provides the standard Boolean Generator Properties Composite that the standard C++ Generator & Template provide.
	 */
	@Override
	public ICodegenComposite createComposite(Composite parent, int style, org.eclipse.ui.forms.widgets.FormToolkit toolkit) {
		return new BooleanGeneratorPropertiesComposite(parent, style, toolkit);
	}

	@Override
	public ICodegenWizardPage[] createPages() {
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();
		this.frontEndWizardPage = new FrontEndWizardPage("Port and Property Selection", "Port and Property Selection", null);
		this.frontEndWizardPage.setDescription("Select the tuner port type and the set of tuner status properties for the tuner status struct.  Note that required properties may not be removed.");
		pages.add(this.frontEndWizardPage);
		
		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		
		Set<FrontEndProp> properties = this.frontEndWizardPage.getSelectedProperties();
		
		StructSequence structSeq = PrfFactory.eINSTANCE.createStructSequence();
		structSeq.setId("frontend_tuner_status");
		structSeq.setName("frontend_tuner_status");
		final ConfigurationKind kind = PrfFactory.eINSTANCE.createConfigurationKind();
		kind.setType(StructPropertyConfigurationType.CONFIGURE);
		structSeq.getConfigurationKind().add(kind);
		
		Struct struct = PrfFactory.eINSTANCE.createStruct();
		struct.setId("frontend_tuner_status_struct");
		struct.setName("frontend_tuner_status_struct");
		
		for (FrontEndProp frontEndProp : properties) {
			struct.getSimple().add(frontEndProp.getProp());
		}
		structSeq.setStruct(struct);
		eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);

		Ports ports = createPorts(eSpd);
		Uses usesPort = ScdFactory.eINSTANCE.createUses();
		if (this.frontEndWizardPage.isDigitalTunerPortSelected()) {
			//TODO: This needs to be moved into a helper class similar to dataDoubleHelper
			usesPort.setName("DigitalTuner");
			usesPort.setRepID("IDL:FRONTEND/DigitalTuner:1.0");
		} else {
			usesPort.setName("AnalogTuner");
			usesPort.setRepID("IDL:FRONTEND/AnalogTuner:1.0");
		}
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), usesPort.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getUses().add(usesPort);
		
		SoftwareComponent scd = eSpd.getDescriptor().getComponent();
		Properties props = eSpd.getPropertyFile().getProperties();
		
		try {
			eSpd.eResource().save(null);
			scd.eResource().save(null);
			props.eResource().save(null);
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write Octave Settings to SCA resources.", e));
		}
	}
	
	private void addInterface(final IdlLibrary library, final String repId, Interfaces interfaces) {

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


}
