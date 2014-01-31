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
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerTypeSelectionWizardPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndWizardPage2;
import gov.redhawk.ide.codegen.frontend.util.FrontEndProjectNature;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenTemplateDisplayFactory;
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
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructPropertyConfigurationType;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.InheritsInterface;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.Interfaces;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.ScdFactory;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;

import FRONTEND.AnalogTunerHelper;
import FRONTEND.DigitalTunerHelper;

public class FrontEndGeneratorTemplateDisplayFactory implements ICodegenTemplateDisplayFactory {

	private FrontEndTunerPropsPage frontEndTunerPropsWizardPage;
	private FrontEndTunerTypeSelectionWizardPage frontEndTunerTypeSelectionPage ;
	private FrontEndWizardPage2 frontEndWizardPage2;

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
		this.frontEndTunerTypeSelectionPage = new FrontEndTunerTypeSelectionWizardPage("Front End Device Type Selection");
		this.frontEndWizardPage2 = new FrontEndWizardPage2("Tuner Properties Page");
		this.frontEndTunerPropsWizardPage = new FrontEndTunerPropsPage("Port and Property Selection", "Port and Property Selection", null);
		
		pages.add(this.frontEndTunerTypeSelectionPage);
		pages.add(this.frontEndWizardPage2);
		pages.add(this.frontEndTunerPropsWizardPage);
		
		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		
		// Add the properties from the Wizard page.
		Set<FrontEndProp> properties = this.frontEndTunerPropsWizardPage.getSelectedProperties();
		
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
			Simple prop = frontEndProp.getProp();
			if (prop != null) {
				struct.getSimple().add(prop);
			}
		}
		structSeq.setStruct(struct);
		eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);

		// Add the two other required properties
		eSpd.getPropertyFile().getProperties().getStruct().add(FrontEndDeviceUIUtils.INSTANCE.getListenerAllocationStruct());
		eSpd.getPropertyFile().getProperties().getStruct().add(FrontEndDeviceUIUtils.INSTANCE.getTunerAllocationStruct());
		
		
		Ports ports = createPorts(eSpd);
		Provides providesPort = ScdFactory.eINSTANCE.createProvides();
		if (this.frontEndTunerPropsWizardPage.isDigitalTunerPortSelected()) {
			//TODO: This needs to be moved into a helper class similar to dataDoubleHelper
			providesPort.setName("DigitalTuner");
			providesPort.setRepID(DigitalTunerHelper.id());
		} else {
			providesPort.setName("AnalogTuner");
			providesPort.setRepID(AnalogTunerHelper.id());
		}
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), providesPort.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getProvides().add(providesPort);
		
		SoftwareComponent scd = eSpd.getDescriptor().getComponent();
		Properties props = eSpd.getPropertyFile().getProperties();
		
		// Finally add the front end nature to the project
		FrontEndProjectNature.addNature(project, null, newChild);
		 
		try {
			props.eResource().save(null);
			scd.eResource().save(null);
			eSpd.eResource().save(null);
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write Settings to SCA resources.", e));
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
