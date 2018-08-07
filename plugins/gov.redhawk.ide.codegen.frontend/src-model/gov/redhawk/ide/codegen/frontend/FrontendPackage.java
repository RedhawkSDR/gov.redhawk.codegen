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
package gov.redhawk.ide.codegen.frontend;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see gov.redhawk.ide.codegen.frontend.FrontendFactory
 * @model kind="package"
 * annotation="http://www.eclipse.org/emf/2002/GenModel prefix='Frontend' dataTypeConverters='true'
 * binaryCompatibleReflectiveMethods='true' fileExtensions='xml' colorProviders='true' fontProviders='true'
 * resource='XML' templateDirectory='/gov.redhawk.ide.codegen.frontend/templates' forceOverwrite='true'
 * modelPluginVariables='org.eclipse.xtext.xbase.lib' tableProviders='true' runtimeVersion='2.9' codeFormatting='true'
 * commentFormatting='true' dynamicTemplates='true' contentTypeIdentifier='http://codegen.redhawk.gov/frontend/1.0.0'
 * modelDirectory='/gov.redhawk.ide.codegen.frontend/src-model'
 * editDirectory='/gov.redhawk.ide.codegen.frontend.edit/src-gen' basePackage='gov.redhawk.ide.codegen'"
 * @generated
 */
public interface FrontendPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "frontend";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://codegen.redhawk.gov/frontend/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "frontend";

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eCONTENT_TYPE = "http://codegen.redhawk.gov/frontend/1.0.0";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FrontendPackage eINSTANCE = gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl.init();

	/**
	 * The meta object id for the '{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl <em>Fei Device</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl
	 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getFeiDevice()
	 * @generated
	 */
	int FEI_DEVICE = 0;

	/**
	 * The feature id for the '<em><b>Antenna</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__ANTENNA = 0;

	/**
	 * The feature id for the '<em><b>Ingests GPS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__INGESTS_GPS = 1;

	/**
	 * The feature id for the '<em><b>Outputs GPS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__OUTPUTS_GPS = 2;

	/**
	 * The feature id for the '<em><b>Rx Tuner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__RX_TUNER = 3;

	/**
	 * The feature id for the '<em><b>Number Of Analog Inputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS = 4;

	/**
	 * The feature id for the '<em><b>Has Digital Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__HAS_DIGITAL_INPUT = 5;

	/**
	 * The feature id for the '<em><b>Digital Input Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__DIGITAL_INPUT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Has Digital Output</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__HAS_DIGITAL_OUTPUT = 7;

	/**
	 * The feature id for the '<em><b>Digital Output Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__DIGITAL_OUTPUT_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Multi Out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__MULTI_OUT = 9;

	/**
	 * The feature id for the '<em><b>Tx Tuner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__TX_TUNER = 10;

	/**
	 * The feature id for the '<em><b>Number Of Digital Inputs For Tx</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX = 11;

	/**
	 * The feature id for the '<em><b>Digital Input Type For Tx</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX = 12;

	/**
	 * The feature id for the '<em><b>Scanner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__SCANNER = 13;

	/**
	 * The feature id for the '<em><b>Tuner Status Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE__TUNER_STATUS_STRUCT = 14;

	/**
	 * The number of structural features of the '<em>Fei Device</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE_FEATURE_COUNT = 15;

	/**
	 * The number of operations of the '<em>Fei Device</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEI_DEVICE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>Tuner Status Struct</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.model.sca.ScaStructProperty
	 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getTunerStatusStruct()
	 * @generated
	 */
	int TUNER_STATUS_STRUCT = 1;

	/**
	 * The meta object id for the '<em>IDL Def</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.eclipsecorba.idl.Definition
	 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getIDLDef()
	 * @generated
	 */
	int IDL_DEF = 2;

	/**
	 * Returns the meta object for class '{@link gov.redhawk.ide.codegen.frontend.FeiDevice <em>Fei Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fei Device</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice
	 * @generated
	 */
	EClass getFeiDevice();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isAntenna
	 * <em>Antenna</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Antenna</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isAntenna()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_Antenna();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIngestsGPS
	 * <em>Ingests GPS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ingests GPS</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIngestsGPS()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_IngestsGPS();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isOutputsGPS
	 * <em>Outputs GPS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Outputs GPS</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isOutputsGPS()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_OutputsGPS();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isRxTuner <em>Rx
	 * Tuner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rx Tuner</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isRxTuner()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_RxTuner();

	/**
	 * Returns the meta object for the attribute
	 * '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfAnalogInputs <em>Number Of Analog Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Analog Inputs</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfAnalogInputs()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_NumberOfAnalogInputs();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalInput
	 * <em>Has Digital Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Digital Input</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalInput()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_HasDigitalInput();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputType
	 * <em>Digital Input Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Digital Input Type</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputType()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_DigitalInputType();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalOutput
	 * <em>Has Digital Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Digital Output</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalOutput()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_HasDigitalOutput();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalOutputType
	 * <em>Digital Output Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Digital Output Type</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalOutputType()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_DigitalOutputType();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isMultiOut <em>Multi
	 * Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multi Out</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isMultiOut()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_MultiOut();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isTxTuner <em>Tx
	 * Tuner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tx Tuner</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isTxTuner()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_TxTuner();

	/**
	 * Returns the meta object for the attribute
	 * '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfDigitalInputsForTx <em>Number Of Digital Inputs For
	 * Tx</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Digital Inputs For Tx</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfDigitalInputsForTx()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_NumberOfDigitalInputsForTx();

	/**
	 * Returns the meta object for the attribute
	 * '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputTypeForTx <em>Digital Input Type For Tx</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Digital Input Type For Tx</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputTypeForTx()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_DigitalInputTypeForTx();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isScanner
	 * <em>Scanner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scanner</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isScanner()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_Scanner();

	/**
	 * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getTunerStatusStruct
	 * <em>Tuner Status Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tuner Status Struct</em>'.
	 * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getTunerStatusStruct()
	 * @see #getFeiDevice()
	 * @generated
	 */
	EAttribute getFeiDevice_TunerStatusStruct();

	/**
	 * Returns the meta object for data type '{@link gov.redhawk.model.sca.ScaStructProperty <em>Tuner Status
	 * Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Tuner Status Struct</em>'.
	 * @see gov.redhawk.model.sca.ScaStructProperty
	 * @model instanceClass="gov.redhawk.model.sca.ScaStructProperty"
	 * @generated
	 */
	EDataType getTunerStatusStruct();

	/**
	 * Returns the meta object for data type '{@link gov.redhawk.eclipsecorba.idl.Definition <em>IDL Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IDL Def</em>'.
	 * @see gov.redhawk.eclipsecorba.idl.Definition
	 * @model instanceClass="gov.redhawk.eclipsecorba.idl.Definition"
	 * @generated
	 */
	EDataType getIDLDef();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FrontendFactory getFrontendFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl <em>Fei
		 * Device</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl
		 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getFeiDevice()
		 * @generated
		 */
		EClass FEI_DEVICE = eINSTANCE.getFeiDevice();

		/**
		 * The meta object literal for the '<em><b>Antenna</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__ANTENNA = eINSTANCE.getFeiDevice_Antenna();

		/**
		 * The meta object literal for the '<em><b>Ingests GPS</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__INGESTS_GPS = eINSTANCE.getFeiDevice_IngestsGPS();

		/**
		 * The meta object literal for the '<em><b>Outputs GPS</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__OUTPUTS_GPS = eINSTANCE.getFeiDevice_OutputsGPS();

		/**
		 * The meta object literal for the '<em><b>Rx Tuner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__RX_TUNER = eINSTANCE.getFeiDevice_RxTuner();

		/**
		 * The meta object literal for the '<em><b>Number Of Analog Inputs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS = eINSTANCE.getFeiDevice_NumberOfAnalogInputs();

		/**
		 * The meta object literal for the '<em><b>Has Digital Input</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__HAS_DIGITAL_INPUT = eINSTANCE.getFeiDevice_HasDigitalInput();

		/**
		 * The meta object literal for the '<em><b>Digital Input Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__DIGITAL_INPUT_TYPE = eINSTANCE.getFeiDevice_DigitalInputType();

		/**
		 * The meta object literal for the '<em><b>Has Digital Output</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__HAS_DIGITAL_OUTPUT = eINSTANCE.getFeiDevice_HasDigitalOutput();

		/**
		 * The meta object literal for the '<em><b>Digital Output Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__DIGITAL_OUTPUT_TYPE = eINSTANCE.getFeiDevice_DigitalOutputType();

		/**
		 * The meta object literal for the '<em><b>Multi Out</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__MULTI_OUT = eINSTANCE.getFeiDevice_MultiOut();

		/**
		 * The meta object literal for the '<em><b>Tx Tuner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__TX_TUNER = eINSTANCE.getFeiDevice_TxTuner();

		/**
		 * The meta object literal for the '<em><b>Number Of Digital Inputs For Tx</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX = eINSTANCE.getFeiDevice_NumberOfDigitalInputsForTx();

		/**
		 * The meta object literal for the '<em><b>Digital Input Type For Tx</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX = eINSTANCE.getFeiDevice_DigitalInputTypeForTx();

		/**
		 * The meta object literal for the '<em><b>Scanner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__SCANNER = eINSTANCE.getFeiDevice_Scanner();

		/**
		 * The meta object literal for the '<em><b>Tuner Status Struct</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEI_DEVICE__TUNER_STATUS_STRUCT = eINSTANCE.getFeiDevice_TunerStatusStruct();

		/**
		 * The meta object literal for the '<em>Tuner Status Struct</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see gov.redhawk.model.sca.ScaStructProperty
		 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getTunerStatusStruct()
		 * @generated
		 */
		EDataType TUNER_STATUS_STRUCT = eINSTANCE.getTunerStatusStruct();

		/**
		 * The meta object literal for the '<em>IDL Def</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see gov.redhawk.eclipsecorba.idl.Definition
		 * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getIDLDef()
		 * @generated
		 */
		EDataType IDL_DEF = eINSTANCE.getIDLDef();

	}

} // FrontendPackage
