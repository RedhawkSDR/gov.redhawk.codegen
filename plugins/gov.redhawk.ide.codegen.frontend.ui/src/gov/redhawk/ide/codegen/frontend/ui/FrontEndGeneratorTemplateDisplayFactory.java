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
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndProp;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerOptionsWizardPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerTypeSelectionWizardPage;
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
import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.PropertyConfigurationType;
import mil.jpeojtrs.sca.prf.PropertyValueType;
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

import FRONTEND.AnalogTunerHelper;
import FRONTEND.DigitalTunerHelper;
import FRONTEND.GPSHelper;
import FRONTEND.RFInfoHelper;
import FRONTEND.RFSourceHelper;

public class FrontEndGeneratorTemplateDisplayFactory implements ICodegenTemplateDisplayFactory {

	private FrontEndTunerPropsPage frontEndTunerPropsWizardPage;
	private FrontEndTunerTypeSelectionWizardPage frontEndTunerTypeSelectionPage ;
	private FrontEndTunerOptionsWizardPage frontEndTunerOptionsWizardPage;
	private FeiDevice feiDevice;

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
		
		this.feiDevice = FrontendFactory.eINSTANCE.createFeiDevice();
		
		this.frontEndTunerTypeSelectionPage = new FrontEndTunerTypeSelectionWizardPage(feiDevice);
		this.frontEndTunerOptionsWizardPage = new FrontEndTunerOptionsWizardPage(feiDevice);
		this.frontEndTunerPropsWizardPage = new FrontEndTunerPropsPage(feiDevice);
		
		pages.add(this.frontEndTunerTypeSelectionPage);
		pages.add(this.frontEndTunerOptionsWizardPage);
		pages.add(this.frontEndTunerPropsWizardPage);
		
		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
		
		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		SoftwareComponent eScd = eSpd.getDescriptor().getComponent();
		
		if (this.feiDevice.isIngestsGPS()) {
			addGPSProvidesPort(eSpd);
		}
		
		if (this.feiDevice.isOutputsGPS()) {
			addGPSUsesPort(eSpd);
		}
		
		if (this.feiDevice.isAntenna()) {
			addAntennaSpecificPorts(eSpd);
			addAntennaSpecificProps(eSpd);
		} else {
			addTunerSpecificProps(eSpd);
			
			if (this.feiDevice.isRxTuner()) {
				if (this.feiDevice.isHasAnalogInput()) {
					addRFInfoPorts(eSpd, this.feiDevice.getNumberOfAnalogInputs());
					
					if (this.feiDevice.isHasAnalogOutput()) {
						addProvidesPort(eSpd, "AnalogTuner_in", AnalogTunerHelper.id());
						addRFInfoPort(eSpd, "RFInfo_out");
						addRFSourcePort(eSpd);
					} else {
						addProvidesPort(eSpd, "DigitalTuner_in", DigitalTunerHelper.id());
						addUsesPort(eSpd, "Dig_out", this.feiDevice.getDigitalOutputType());
						if (this.feiDevice.isMultiOut()) {
							addMultiOutProperty(eSpd);
						}
					}
					
					
				} else {
					// Not analog input
					addProvidesPort(eSpd, "Dig_in", this.feiDevice.getDigitalInputType());
					addUsesPort(eSpd, "Dig_out", this.feiDevice.getDigitalOutputType());
					if (this.feiDevice.isMultiOut()) {
						addMultiOutProperty(eSpd);
					}
				}
			}
			
			if (this.feiDevice.isTxTuner()) {
				addDigitalTunerProvidesPorts(eSpd, this.feiDevice.getNumberOfDigitalInputsForTx());
				addRFInfoPorts(eSpd, this.feiDevice.getNumberOfDigitalInputsForTx());
			}
		}
		
		// Finally add the front end nature to the project
		FrontEndProjectNature.addNature(project, null, newChild);
		 
		try {
			eScd.eResource().save(null);
			eSpd.eResource().save(null);
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write Settings to SCA resources.", e));
		}
		
	}
	
	private void addDigitalTunerProvidesPorts(SoftPkg eSpd, int numberOfDigitalInputsForTx) {
		
		addProvidesPort(eSpd, "DigitalTuner_in", DigitalTunerHelper.id());
		
		for (int i = 2; i < numberOfDigitalInputsForTx + 1; i++) {
			addProvidesPort(eSpd, "DigitalTuner_in_" + i, DigitalTunerHelper.id());
		}
	}


	private Ports addProvidesPort(SoftPkg eSpd, String name, String repId) {
		Ports ports = createPorts(eSpd);
		Provides portToAdd = ScdFactory.eINSTANCE.createProvides();
		portToAdd.setRepID(repId);
		portToAdd.setName(name);
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), portToAdd.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getProvides().add(portToAdd);
		
		return ports;		
	}

	private Ports addUsesPort(SoftPkg eSpd, String name, String repId) {
		Ports ports = createPorts(eSpd);
		Uses portToAdd = ScdFactory.eINSTANCE.createUses();
		portToAdd.setRepID(repId);
		portToAdd.setName(name);
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), portToAdd.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getUses().add(portToAdd);
		
		return ports;
	}

	private Ports addRFSourcePort(SoftPkg eSpd) {
		return addProvidesPort(eSpd, "RFSource_in", RFSourceHelper.id());
	}

	private Ports addRFInfoPort(SoftPkg eSpd, String name) {
		return addUsesPort(eSpd, name, RFInfoHelper.id());
	}

	private void addRFInfoPorts(SoftPkg eSpd, int numberOfPorts) {
		addRFInfoPort(eSpd, "RFInfo_out");
		
		for (int i = 2; i < numberOfPorts + 1; i++) {
			addRFInfoPort(eSpd, "RFInfo_out_" + i);
		}
	}
	
	private void addMultiOutProperty(SoftPkg eSpd) {
		
		StructSequence structSeq = PrfFactory.eINSTANCE.createStructSequence();
		structSeq.setId("connectionTable");
		
		final ConfigurationKind structKind = PrfFactory.eINSTANCE.createConfigurationKind();
		structKind.setType(StructPropertyConfigurationType.CONFIGURE);
		structSeq.getConfigurationKind().add(structKind);
		
		Struct struct = PrfFactory.eINSTANCE.createStruct();
		struct.setId("connection_descriptor");
		
		Simple connectionName = PrfFactory.eINSTANCE.createSimple();
		connectionName.setId("connection_name");
		connectionName.setType(PropertyValueType.STRING);
		
		final Kind connectionNameKind = PrfFactory.eINSTANCE.createKind();
		connectionNameKind.setType(PropertyConfigurationType.CONFIGURE);
		connectionName.getKind().add(connectionNameKind);
		
		Simple streamId = PrfFactory.eINSTANCE.createSimple();
		streamId.setId("stream_id");
		streamId.setType(PropertyValueType.STRING);
		
		final Kind streamIdKind = PrfFactory.eINSTANCE.createKind();
		streamIdKind.setType(PropertyConfigurationType.CONFIGURE);
		streamId.getKind().add(streamIdKind);
		
		Simple portName = PrfFactory.eINSTANCE.createSimple();
		portName.setId("port_name");
		portName.setType(PropertyValueType.STRING);
		
		final Kind portNameKind = PrfFactory.eINSTANCE.createKind();
		portNameKind.setType(PropertyConfigurationType.CONFIGURE);
		portName.getKind().add(portNameKind);
		
		struct.getSimple().add(connectionName);
		struct.getSimple().add(streamId);
		struct.getSimple().add(portName);
		
		structSeq.setStruct(struct);
		
		eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);
	}

	private void addTunerSpecificProps(SoftPkg eSpd) {
		
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
	}

	private void addAntennaSpecificProps(SoftPkg eSpd) {
		// TODO: Need to find out what the properties are.
	}

	private void addAntennaSpecificPorts(SoftPkg eSpd) {
		Ports ports = addRFInfoPort(eSpd, "RFInfo_out");
		
		// RF Source
		Provides rfSourcePort = ScdFactory.eINSTANCE.createProvides();
		rfSourcePort.setName("RFSource_In");
		rfSourcePort.setRepID(RFSourceHelper.id());
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), rfSourcePort.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getProvides().add(rfSourcePort);
	}

	private void addGPSUsesPort(SoftPkg eSpd) {
		addUsesPort(eSpd, "GPS_in", GPSHelper.id());
	}
	
	private void addGPSProvidesPort(SoftPkg eSpd) {
		addProvidesPort(eSpd, "GPS_out", GPSHelper.id());
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
