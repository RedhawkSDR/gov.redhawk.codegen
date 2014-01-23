/**
 */
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
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see gov.redhawk.ide.codegen.frontend.FrontendFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel prefix='Frontend' dataTypeConverters='true' binaryCompantibleReflectiveMethods='true' fileExtensions='xml' colorProviders='true' fontProviders='true' tablesProviders='true' resource='XML' templateDirectory='/gov.redhawk.ide.codegen.frontend/templates' forceOverwrite='true' modelPluginVariables='org.eclipse.xtext.xbase.lib' tableProviders='true' contentTypeIdentifier='http://codegen.redhawk.gov/frontend/1.0.0' modelDirectory='/gov.redhawk.ide.codegen.frontend/src-model' editDirectory='/gov.redhawk.ide.codegen.frontend.edit/src-gen' basePackage='gov.redhawk.ide.codegen'"
 * @generated
 */
public interface FrontendPackage extends EPackage
{
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
   * The meta object id for the '{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl <em>Fei Device</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl
   * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getFeiDevice()
   * @generated
   */
  int FEI_DEVICE = 0;

  /**
   * The feature id for the '<em><b>Is Antenna</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__IS_ANTENNA = 0;

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
   * The feature id for the '<em><b>Is Rx Tuner</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__IS_RX_TUNER = 3;

  /**
   * The feature id for the '<em><b>Has Analog Input</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__HAS_ANALOG_INPUT = 4;

  /**
   * The feature id for the '<em><b>Number Of Analog Inputs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS = 5;

  /**
   * The feature id for the '<em><b>Has Digital Input</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__HAS_DIGITAL_INPUT = 6;

  /**
   * The feature id for the '<em><b>Digital Input Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__DIGITAL_INPUT_TYPE = 7;

  /**
   * The feature id for the '<em><b>Is Multi Out</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__IS_MULTI_OUT = 8;

  /**
   * The feature id for the '<em><b>Digital Output Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__DIGITAL_OUTPUT_TYPE = 9;

  /**
   * The feature id for the '<em><b>Is Tx Tuner</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__IS_TX_TUNER = 10;

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
   * The feature id for the '<em><b>Tuner Status Struct</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE__TUNER_STATUS_STRUCT = 13;

  /**
   * The number of structural features of the '<em>Fei Device</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEI_DEVICE_FEATURE_COUNT = 14;

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
   * Returns the meta object for class '{@link gov.redhawk.ide.codegen.frontend.FeiDevice <em>Fei Device</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fei Device</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice
   * @generated
   */
  EClass getFeiDevice();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIsAntenna <em>Is Antenna</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Antenna</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIsAntenna()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_IsAntenna();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIngestsGPS <em>Ingests GPS</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ingests GPS</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIngestsGPS()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_IngestsGPS();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isOutputsGPS <em>Outputs GPS</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Outputs GPS</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isOutputsGPS()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_OutputsGPS();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIsRxTuner <em>Is Rx Tuner</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Rx Tuner</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIsRxTuner()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_IsRxTuner();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isHasAnalogInput <em>Has Analog Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Has Analog Input</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isHasAnalogInput()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_HasAnalogInput();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfAnalogInputs <em>Number Of Analog Inputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Analog Inputs</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfAnalogInputs()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_NumberOfAnalogInputs();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalInput <em>Has Digital Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Has Digital Input</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isHasDigitalInput()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_HasDigitalInput();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputType <em>Digital Input Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Digital Input Type</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputType()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_DigitalInputType();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIsMultiOut <em>Is Multi Out</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Multi Out</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIsMultiOut()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_IsMultiOut();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalOutputType <em>Digital Output Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Digital Output Type</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalOutputType()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_DigitalOutputType();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#isIsTxTuner <em>Is Tx Tuner</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Tx Tuner</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#isIsTxTuner()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_IsTxTuner();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfDigitalInputsForTx <em>Number Of Digital Inputs For Tx</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Digital Inputs For Tx</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getNumberOfDigitalInputsForTx()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_NumberOfDigitalInputsForTx();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputTypeForTx <em>Digital Input Type For Tx</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Digital Input Type For Tx</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getDigitalInputTypeForTx()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_DigitalInputTypeForTx();

  /**
   * Returns the meta object for the attribute '{@link gov.redhawk.ide.codegen.frontend.FeiDevice#getTunerStatusStruct <em>Tuner Status Struct</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tuner Status Struct</em>'.
   * @see gov.redhawk.ide.codegen.frontend.FeiDevice#getTunerStatusStruct()
   * @see #getFeiDevice()
   * @generated
   */
  EAttribute getFeiDevice_TunerStatusStruct();

  /**
   * Returns the meta object for data type '{@link gov.redhawk.model.sca.ScaStructProperty <em>Tuner Status Struct</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Tuner Status Struct</em>'.
   * @see gov.redhawk.model.sca.ScaStructProperty
   * @model instanceClass="gov.redhawk.model.sca.ScaStructProperty"
   * @generated
   */
  EDataType getTunerStatusStruct();

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
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl <em>Fei Device</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl
     * @see gov.redhawk.ide.codegen.frontend.impl.FrontendPackageImpl#getFeiDevice()
     * @generated
     */
    EClass FEI_DEVICE = eINSTANCE.getFeiDevice();

    /**
     * The meta object literal for the '<em><b>Is Antenna</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__IS_ANTENNA = eINSTANCE.getFeiDevice_IsAntenna();

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
     * The meta object literal for the '<em><b>Is Rx Tuner</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__IS_RX_TUNER = eINSTANCE.getFeiDevice_IsRxTuner();

    /**
     * The meta object literal for the '<em><b>Has Analog Input</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__HAS_ANALOG_INPUT = eINSTANCE.getFeiDevice_HasAnalogInput();

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
     * The meta object literal for the '<em><b>Is Multi Out</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__IS_MULTI_OUT = eINSTANCE.getFeiDevice_IsMultiOut();

    /**
     * The meta object literal for the '<em><b>Digital Output Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__DIGITAL_OUTPUT_TYPE = eINSTANCE.getFeiDevice_DigitalOutputType();

    /**
     * The meta object literal for the '<em><b>Is Tx Tuner</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEI_DEVICE__IS_TX_TUNER = eINSTANCE.getFeiDevice_IsTxTuner();

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

  }

} //FrontendPackage
