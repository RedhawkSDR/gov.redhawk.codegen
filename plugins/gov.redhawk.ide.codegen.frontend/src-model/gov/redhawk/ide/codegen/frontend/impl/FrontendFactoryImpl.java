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
// BEGIN GENERATED CODE
package gov.redhawk.ide.codegen.frontend.impl;

import gov.redhawk.eclipsecorba.idl.Definition;
import gov.redhawk.frontend.util.TunerProperties.ConnectionTableProperty;
import gov.redhawk.ide.codegen.frontend.*;

import gov.redhawk.model.sca.ScaStructProperty;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.PortType;
import mil.jpeojtrs.sca.scd.PortTypeContainer;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.ScdFactory;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.scd.Uses;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import FRONTEND.GPSHelper;
import FRONTEND.RFInfoHelper;
import FRONTEND.ScanningTunerHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FrontendFactoryImpl extends EFactoryImpl implements FrontendFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FrontendFactory init() {
		try {
			FrontendFactory theFrontendFactory = (FrontendFactory) EPackage.Registry.INSTANCE.getEFactory(FrontendPackage.eNS_URI);
			if (theFrontendFactory != null) {
				return theFrontendFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FrontendFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrontendFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case FrontendPackage.FEI_DEVICE:
			return createFeiDevice();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case FrontendPackage.TUNER_STATUS_STRUCT:
			return createTunerStatusStructFromString(eDataType, initialValue);
		case FrontendPackage.IDL_DEF:
			return createIDLDefFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case FrontendPackage.TUNER_STATUS_STRUCT:
			return convertTunerStatusStructToString(eDataType, instanceValue);
		case FrontendPackage.IDL_DEF:
			return convertIDLDefToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeiDevice createFeiDevice() {
		FeiDeviceImpl feiDevice = new FeiDeviceImpl();
		return feiDevice;
	}

	// END GENERATED CODE

	@Override
	public FeiDevice createFeiDevice(Properties prf, SoftwareComponent scd) {
		FeiDevice feiDevice = createFeiDevice();

		// PRF checks
		feiDevice.setMultiOut(prf.getProperty(ConnectionTableProperty.INSTANCE.getId()) != null);

		// SCD checks
		final String bulkioPrefix = "IDL:BULKIO/";
		final Interface gps = ScdFactory.eINSTANCE.createInterface(GPSHelper.id());
		final Interface rfInfo = ScdFactory.eINSTANCE.createInterface(RFInfoHelper.id());
		final Interface scanningTuner = ScdFactory.eINSTANCE.createInterface(ScanningTunerHelper.id());

		feiDevice.setIngestsGPS(false);
		feiDevice.setScanner(false);
		int analogInputs = 0;
		boolean digitalInput = false;
		for (Provides providesPort : scd.getComponentFeatures().getPorts().getProvides()) {
			if (providesPort.getInterface().isInstance(gps)) {
				feiDevice.setIngestsGPS(true);
			}
			if (providesPort.getInterface().isInstance(rfInfo)) {
				analogInputs++;
			}
			if (providesPort.getInterface().isInstance(scanningTuner)) {
				feiDevice.setScanner(true);
			}
			if (providesPort.getInterface().getRepid().startsWith(bulkioPrefix) && !providesPort.getName().contains("TX_in")) {
				for (PortTypeContainer container : providesPort.getPortType()) {
					if (PortType.DATA.equals(container.getType())) {
						digitalInput = true;
						break;
					}
				}
			}
		}
		feiDevice.setRxTuner(analogInputs > 0 || digitalInput);
		feiDevice.setHasDigitalInput(analogInputs == 0 && digitalInput);
		feiDevice.setNumberOfAnalogInputs(analogInputs);

		feiDevice.setOutputsGPS(false);
		boolean digitalOutput = false;
		int txOutputs = 0, analogOutputs = 0;
		for (Uses usesPort : scd.getComponentFeatures().getPorts().getUses()) {
			if (usesPort.getInterface().isInstance(gps)) {
				feiDevice.setOutputsGPS(true);
			}
			if (usesPort.getInterface().isInstance(rfInfo)) {
				if (usesPort.getName().startsWith("RFInfoTX_out")) {
					txOutputs++;
				} else {
					analogOutputs++;
				}
			}
			if (usesPort.getInterface().getRepid().startsWith(bulkioPrefix) && !usesPort.getName().contains("TX_out")) {
				for (PortTypeContainer container : usesPort.getPortType()) {
					if (PortType.DATA.equals(container.getType())) {
						digitalOutput = true;
						break;
					}
				}
			}
		}
		feiDevice.setHasDigitalOutput(analogOutputs == 0 && digitalOutput);
		feiDevice.setTxTuner(txOutputs > 0);
		feiDevice.setNumberOfDigitalInputsForTx(txOutputs);

		return feiDevice;
	}

	// BEGIN GENERATED CODE

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaStructProperty createTunerStatusStruct(String literal) {
		return (ScaStructProperty) super.createFromString(FrontendPackage.Literals.TUNER_STATUS_STRUCT, literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaStructProperty createTunerStatusStructFromString(EDataType eDataType, String initialValue) {
		return createTunerStatusStruct(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTunerStatusStruct(ScaStructProperty instanceValue) {
		return super.convertToString(FrontendPackage.Literals.TUNER_STATUS_STRUCT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTunerStatusStructToString(EDataType eDataType, Object instanceValue) {
		return convertTunerStatusStruct((ScaStructProperty) instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Definition createIDLDef(String literal) {
		return (Definition) super.createFromString(FrontendPackage.Literals.IDL_DEF, literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Definition createIDLDefFromString(EDataType eDataType, String initialValue) {
		return createIDLDef(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIDLDef(Definition instanceValue) {
		return super.convertToString(FrontendPackage.Literals.IDL_DEF, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIDLDefToString(EDataType eDataType, Object instanceValue) {
		return convertIDLDef((Definition) instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrontendPackage getFrontendPackage() {
		return (FrontendPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FrontendPackage getPackage() {
		return FrontendPackage.eINSTANCE;
	}

} // FrontendFactoryImpl
