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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;

import FRONTEND.AnalogTunerHelper;
import FRONTEND.DigitalTunerHelper;
import FRONTEND.GPSHelper;
import FRONTEND.RFInfoHelper;
import FRONTEND.RFSourceHelper;
import gov.redhawk.eclipsecorba.idl.IdlInterfaceDcl;
import gov.redhawk.eclipsecorba.library.IdlLibrary;
import gov.redhawk.frontend.util.TunerProperties;
import gov.redhawk.frontend.util.TunerProperties.ConnectionTableProperty;
import gov.redhawk.frontend.util.TunerProperties.TunerStatusProperty;
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
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.InheritsInterface;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.Interfaces;
import mil.jpeojtrs.sca.scd.PortType;
import mil.jpeojtrs.sca.scd.PortTypeContainer;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.ScdFactory;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

public class FrontEndGeneratorTemplateDisplayFactory implements ICodegenTemplateDisplayFactory {

	private FrontEndTunerPropsPage frontEndTunerPropsWizardPage;
	private FrontEndTunerTypeSelectionWizardPage frontEndTunerTypeSelectionPage;
	private FrontEndTunerOptionsWizardPage frontEndTunerOptionsWizardPage;
	private FeiDevice feiDevice;
	private Set<FrontEndProp> tunerStatusStructProps;

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

		setFeiDevice(FrontendFactory.eINSTANCE.createFeiDevice());

		this.setFrontEndTunerTypeSelectionPage(new FrontEndTunerTypeSelectionWizardPage(getFeiDevice()));
		this.setFrontEndTunerOptionsWizardPage(new FrontEndTunerOptionsWizardPage(getFeiDevice()));
		this.setFrontEndTunerPropsWizardPage(new FrontEndTunerPropsPage(getFeiDevice()));

		pages.add(this.getFrontEndTunerTypeSelectionPage());
		pages.add(this.getFrontEndTunerOptionsWizardPage());
		pages.add(this.getFrontEndTunerPropsWizardPage());

		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);

		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		SoftwareComponent eScd = eSpd.getDescriptor().getComponent();

		if (getFeiDevice().isIngestsGPS()) {
			addGPSProvidesPort(eSpd);
		}

		if (getFeiDevice().isOutputsGPS()) {
			addGPSUsesPort(eSpd);
		}

		if (getFeiDevice().isAntenna()) {
			setDeviceKindName(eSpd, FrontEndDeviceUIUtils.ANTENNA_DEVICE_KIND_NAME);
			addAntennaSpecificPorts(eSpd);
			addAntennaSpecificProps(eSpd);
		} else { // It's a Tuner
			// Add the front end nature to the project if it's a tuner.
			FrontEndProjectNature.addNature(project, null, newChild);

			addTunerSpecificProps(eSpd);
			setDeviceKindName(eSpd, FrontEndDeviceUIUtils.TUNER_DEVICE_KIND_NAME);
			if (getFeiDevice().isRxTuner()) {
				if (!getFeiDevice().isHasDigitalInput()) { // Has analog input
					addRFInfoProvidesPorts(eSpd, getFeiDevice().getNumberOfAnalogInputs());

					if (getFeiDevice().isHasDigitalOutput()) { // Has digital output
						addDigitalTunerPort(eSpd);
						addUsesDataPort(eSpd, getFeiDevice().getDigitalOutputType().getName() + "_out", getFeiDevice().getDigitalOutputType().getRepId());
						if (getFeiDevice().isMultiOut()) {
							StructSequence structSeq = ConnectionTableProperty.INSTANCE.createProperty();
							eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);
						}
					} else { // It has Analog Output
						addProvidesControlPort(eSpd, "AnalogTuner_in", AnalogTunerHelper.id());
						addRFInfoUsesPort(eSpd, "RFInfo_out");
						// addRFSourcePort(eSpd); // Holding off on supporting RFSourcePorts until post CCB
					}

				} else { // Has Digital Input
					// If it has Digital Input it must have Digital Output
					addDigitalTunerPort(eSpd);
					addProvidesDataPort(eSpd, getFeiDevice().getDigitalInputType().getName() + "_in", getFeiDevice().getDigitalInputType().getRepId());
					addUsesDataPort(eSpd, getFeiDevice().getDigitalOutputType().getName() + "_out", getFeiDevice().getDigitalOutputType().getRepId());
					if (getFeiDevice().isMultiOut()) {
						StructSequence structSeq = ConnectionTableProperty.INSTANCE.createProperty();
						eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);
					}
				}
			}

			if (getFeiDevice().isTxTuner()) {
				addDigitalTunerPort(eSpd);
				addRFInfoUsesTXPorts(eSpd, getFeiDevice().getNumberOfDigitalInputsForTx());
				addProvidesDataPorts(eSpd, getFeiDevice().getDigitalInputTypeForTx().getName() + "TX_in", getFeiDevice().getDigitalInputTypeForTx().getRepId(),
					getFeiDevice().getNumberOfDigitalInputsForTx());
			}
		}

		Properties ePrf = eSpd.getPropertyFile().getProperties();
		try {
			eScd.eResource().save(null);
			eSpd.eResource().save(null);
			ePrf.eResource().save(null);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write settings to XML files.", e));
		}

	}

	private void addProvidesDataPorts(SoftPkg eSpd, String name, String repId, int numberOfPorts) {
		addProvidesDataPort(eSpd, name, repId);

		for (int i = 2; i < numberOfPorts + 1; i++) {
			addProvidesDataPort(eSpd, name + "_" + i, repId);
		}
	}

	private void addDigitalTunerPort(SoftPkg eSpd) {
		Ports ports = createPorts(eSpd);
		for (Provides providesPort : ports.getProvides()) {
			if (providesPort.getRepID().equals(DigitalTunerHelper.id())) {
				// Only one digital tuner provides port allowed.
				return;
			}
		}
		addProvidesControlPort(eSpd, "DigitalTuner_in", DigitalTunerHelper.id());
	}

	private void setDeviceKindName(SoftPkg eSpd, String name) {
		for (Simple simpProp : eSpd.getPropertyFile().getProperties().getSimple()) {
			if ("device_kind".equals(simpProp.getName())) {
				simpProp.setValue(name);
				return;
			}
		}
	}

	private Ports addProvidesDataPort(SoftPkg eSpd, String name, String repId) {
		return addProvidesPort(eSpd, name, repId, PortType.DATA);
	}

	private Ports addProvidesControlPort(SoftPkg eSpd, String name, String repId) {
		return addProvidesPort(eSpd, name, repId, PortType.CONTROL);
	}

	private Ports addProvidesPort(SoftPkg eSpd, String name, String repId, PortType portType) {
		Ports ports = createPorts(eSpd);
		Provides portToAdd = ScdFactory.eINSTANCE.createProvides();

		PortTypeContainer portTypeContainer = ScdFactory.eINSTANCE.createPortTypeContainer();
		portTypeContainer.setType(portType);
		portToAdd.getPortType().add(portTypeContainer);

		portToAdd.setRepID(repId);
		portToAdd.setName(name);
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), portToAdd.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getProvides().add(portToAdd);

		return ports;
	}

	private Ports addUsesDataPort(SoftPkg eSpd, String name, String repId) {
		return addUsesPort(eSpd, name, repId, PortType.DATA);
	}

	private Ports addUsesControlPort(SoftPkg eSpd, String name, String repId) {
		return addUsesPort(eSpd, name, repId, PortType.CONTROL);
	}

	private Ports addUsesPort(SoftPkg eSpd, String name, String repId, PortType portType) {
		Ports ports = createPorts(eSpd);
		Uses portToAdd = ScdFactory.eINSTANCE.createUses();

		PortTypeContainer portTypeContainer = ScdFactory.eINSTANCE.createPortTypeContainer();
		portTypeContainer.setType(portType);
		portToAdd.getPortType().add(portTypeContainer);

		portToAdd.setRepID(repId);
		portToAdd.setName(name);
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), portToAdd.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getUses().add(portToAdd);

		return ports;
	}

	private Ports addRFSourcePort(SoftPkg eSpd) {
		return addProvidesControlPort(eSpd, "RFSource_in", RFSourceHelper.id());
	}

	private Ports addRFInfoUsesPort(SoftPkg eSpd, String name) {
		return addUsesDataPort(eSpd, name, RFInfoHelper.id());
	}

	private Ports addRFInfoProvidesPort(SoftPkg eSpd, String name) {
		return addProvidesDataPort(eSpd, name, RFInfoHelper.id());
	}

	private void addRFInfoUsesTXPorts(SoftPkg eSpd, int numberOfPorts) {
		addRFInfoUsesPort(eSpd, "RFInfoTX_out");

		for (int i = 2; i < numberOfPorts + 1; i++) {
			addRFInfoUsesPort(eSpd, "RFInfoTX_out_" + i);
		}
	}

	private void addRFInfoProvidesPorts(SoftPkg eSpd, int numberOfPorts) {
		addRFInfoProvidesPort(eSpd, "RFInfo_in");

		for (int i = 2; i < numberOfPorts + 1; i++) {
			addRFInfoProvidesPort(eSpd, "RFInfo_in_" + i);
		}
	}

	private void addTunerSpecificProps(SoftPkg eSpd) {
		// If the tunerStatusStructProps is null then we must have come through the Wizard otherwise, maybe someoneone
		// set it and we should accept that.
		if (this.tunerStatusStructProps == null && this.getFrontEndTunerPropsWizardPage() != null) {
			this.tunerStatusStructProps = this.getFrontEndTunerPropsWizardPage().getSelectedProperties();
		}

		// Make things easier for the user by sorting the list here
		final List<FrontEndProp> sortedList;
		if (this.tunerStatusStructProps == null) {
			sortedList = Collections.emptyList();
		} else {
			sortedList = new ArrayList<FrontEndProp>(this.tunerStatusStructProps);
		}
		Collections.sort(sortedList, new Comparator<FrontEndProp>() {
			public int compare(FrontEndProp fep1, FrontEndProp fep2) {
				return fep1.getProp().getName().compareTo(fep2.getProp().getName());
			}
		});

		// Get a copy of the tuner status struct, but use the fields the user has selected
		StructSequence structSeq = TunerStatusProperty.INSTANCE.createProperty();
		Struct struct = structSeq.getStruct();
		structSeq.getStruct().getFields().clear();
		for (FrontEndProp frontEndProp : sortedList) {
			Simple prop = frontEndProp.getProp();
			if (prop != null) {
				struct.getSimple().add(prop);
			}
		}
		eSpd.getPropertyFile().getProperties().getStructSequence().add(structSeq);

		// Add the two other required properties
		eSpd.getPropertyFile().getProperties().getStruct().add(TunerProperties.ListenerAllocationProperty.INSTANCE.createProperty());
		eSpd.getPropertyFile().getProperties().getStruct().add(TunerProperties.TunerAllocationProperty.INSTANCE.createProperty());

		// Have to remember to set this back to null since this is a singleton
		this.tunerStatusStructProps = null;
	}

	private void addAntennaSpecificProps(SoftPkg eSpd) {
		// TODO: Need to find out what the properties are.
	}

	private void addAntennaSpecificPorts(SoftPkg eSpd) {
		Ports ports = addRFInfoUsesPort(eSpd, "RFInfo_out");

		// RF Source
		Provides rfSourcePort = ScdFactory.eINSTANCE.createProvides();
		rfSourcePort.setName("RFSource_In");
		rfSourcePort.setRepID(RFSourceHelper.id());
		addInterface(SdrUiPlugin.getDefault().getTargetSdrRoot().getIdlLibrary(), rfSourcePort.getRepID(), eSpd.getDescriptor().getComponent().getInterfaces());
		ports.getProvides().add(rfSourcePort);
	}

	private void addGPSUsesPort(SoftPkg eSpd) {
		addUsesDataPort(eSpd, "GPS_out", GPSHelper.id());
	}

	private void addGPSProvidesPort(SoftPkg eSpd) {
		addProvidesDataPort(eSpd, "GPS_in", GPSHelper.id());
	}

	private void addInterface(final IdlLibrary library, final String repId, Interfaces interfaces) {

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

		// Don't add it if we already have it.
		for (Interface curInterface : interfaces.getInterface()) {
			if (curInterface.getRepid().equals(i.getRepid())) {
				return;
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

	public void setTunerStatusStructProps(Set<FrontEndProp> tunerStatusStructProps) {
		this.tunerStatusStructProps = tunerStatusStructProps;
	}

	protected FeiDevice getFeiDevice() {
		return feiDevice;
	}

	protected void setFeiDevice(FeiDevice feiDevice) {
		this.feiDevice = feiDevice;
	}

	protected FrontEndTunerPropsPage getFrontEndTunerPropsWizardPage() {
		return frontEndTunerPropsWizardPage;
	}

	protected void setFrontEndTunerPropsWizardPage(FrontEndTunerPropsPage frontEndTunerPropsWizardPage) {
		this.frontEndTunerPropsWizardPage = frontEndTunerPropsWizardPage;
	}

	protected FrontEndTunerTypeSelectionWizardPage getFrontEndTunerTypeSelectionPage() {
		return frontEndTunerTypeSelectionPage;
	}

	protected void setFrontEndTunerTypeSelectionPage(FrontEndTunerTypeSelectionWizardPage frontEndTunerTypeSelectionPage) {
		this.frontEndTunerTypeSelectionPage = frontEndTunerTypeSelectionPage;
	}

	protected FrontEndTunerOptionsWizardPage getFrontEndTunerOptionsWizardPage() {
		return frontEndTunerOptionsWizardPage;
	}

	protected void setFrontEndTunerOptionsWizardPage(FrontEndTunerOptionsWizardPage frontEndTunerOptionsWizardPage) {
		this.frontEndTunerOptionsWizardPage = frontEndTunerOptionsWizardPage;
	}

}
