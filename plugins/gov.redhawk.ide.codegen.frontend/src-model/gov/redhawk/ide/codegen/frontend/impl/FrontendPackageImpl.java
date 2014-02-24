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

import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;

import gov.redhawk.model.sca.ScaStructProperty;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FrontendPackageImpl extends EPackageImpl implements FrontendPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass feiDeviceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType tunerStatusStructEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType idlDefEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see gov.redhawk.ide.codegen.frontend.FrontendPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FrontendPackageImpl() {
		super(eNS_URI, FrontendFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link FrontendPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FrontendPackage init() {
		if (isInited)
			return (FrontendPackage) EPackage.Registry.INSTANCE.getEPackage(FrontendPackage.eNS_URI);

		// Obtain or create and register package
		FrontendPackageImpl theFrontendPackage = (FrontendPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof FrontendPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new FrontendPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theFrontendPackage.createPackageContents();

		// Initialize created meta-data
		theFrontendPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFrontendPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FrontendPackage.eNS_URI, theFrontendPackage);
		return theFrontendPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFeiDevice() {
		return feiDeviceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_Antenna() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_IngestsGPS() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_OutputsGPS() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_RxTuner() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_NumberOfAnalogInputs() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_HasDigitalInput() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_DigitalInputType() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_HasDigitalOutput() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_DigitalOutputType() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_MultiOut() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_TxTuner() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_NumberOfDigitalInputsForTx() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_DigitalInputTypeForTx() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeiDevice_TunerStatusStruct() {
		return (EAttribute) feiDeviceEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTunerStatusStruct() {
		return tunerStatusStructEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIDLDef() {
		return idlDefEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrontendFactory getFrontendFactory() {
		return (FrontendFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		feiDeviceEClass = createEClass(FEI_DEVICE);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__ANTENNA);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__INGESTS_GPS);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__OUTPUTS_GPS);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__RX_TUNER);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__HAS_DIGITAL_INPUT);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__DIGITAL_INPUT_TYPE);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__HAS_DIGITAL_OUTPUT);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__DIGITAL_OUTPUT_TYPE);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__MULTI_OUT);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__TX_TUNER);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX);
		createEAttribute(feiDeviceEClass, FEI_DEVICE__TUNER_STATUS_STRUCT);

		// Create data types
		tunerStatusStructEDataType = createEDataType(TUNER_STATUS_STRUCT);
		idlDefEDataType = createEDataType(IDL_DEF);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(feiDeviceEClass, FeiDevice.class, "FeiDevice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeiDevice_Antenna(), theEcorePackage.getEBoolean(), "Antenna", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_IngestsGPS(), theEcorePackage.getEBoolean(), "ingestsGPS", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_OutputsGPS(), theEcorePackage.getEBoolean(), "outputsGPS", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_RxTuner(), theEcorePackage.getEBoolean(), "RxTuner", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_NumberOfAnalogInputs(), theEcorePackage.getEInt(), "numberOfAnalogInputs", null, 0, 1, FeiDevice.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_HasDigitalInput(), theEcorePackage.getEBoolean(), "hasDigitalInput", null, 0, 1, FeiDevice.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_DigitalInputType(), this.getIDLDef(), "digitalInputType", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_HasDigitalOutput(), theEcorePackage.getEBoolean(), "hasDigitalOutput", null, 0, 1, FeiDevice.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_DigitalOutputType(), this.getIDLDef(), "digitalOutputType", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_MultiOut(), theEcorePackage.getEBoolean(), "MultiOut", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_TxTuner(), theEcorePackage.getEBoolean(), "TxTuner", null, 0, 1, FeiDevice.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_NumberOfDigitalInputsForTx(), theEcorePackage.getEInt(), "numberOfDigitalInputsForTx", null, 0, 1, FeiDevice.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_DigitalInputTypeForTx(), this.getIDLDef(), "digitalInputTypeForTx", null, 0, 1, FeiDevice.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeiDevice_TunerStatusStruct(), this.getTunerStatusStruct(), "tunerStatusStruct", null, 0, 1, FeiDevice.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(tunerStatusStructEDataType, ScaStructProperty.class, "TunerStatusStruct", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(idlDefEDataType, Definition.class, "IDLDef", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // FrontendPackageImpl
