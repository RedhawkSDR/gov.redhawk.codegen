/**
 */
package gov.redhawk.ide.codegen.frontend.impl;

import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;

import gov.redhawk.model.sca.ScaStructProperty;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fei Device</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIsAntenna <em>Is Antenna</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIngestsGPS <em>Ingests GPS</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isOutputsGPS <em>Outputs GPS</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIsRxTuner <em>Is Rx Tuner</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isHasAnalogInput <em>Has Analog Input</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getNumberOfAnalogInputs <em>Number Of Analog Inputs</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isHasDigitalInput <em>Has Digital Input</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalInputType <em>Digital Input Type</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIsMultiOut <em>Is Multi Out</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalOutputType <em>Digital Output Type</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIsTxTuner <em>Is Tx Tuner</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getNumberOfDigitalInputsForTx <em>Number Of Digital Inputs For Tx</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalInputTypeForTx <em>Digital Input Type For Tx</em>}</li>
 *   <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getTunerStatusStruct <em>Tuner Status Struct</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeiDeviceImpl extends MinimalEObjectImpl.Container implements FeiDevice
{
  /**
   * The default value of the '{@link #isIsAntenna() <em>Is Antenna</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsAntenna()
   * @generated
   * @ordered
   */
  protected static final boolean IS_ANTENNA_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsAntenna() <em>Is Antenna</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsAntenna()
   * @generated
   * @ordered
   */
  protected boolean isAntenna = IS_ANTENNA_EDEFAULT;

  /**
   * The default value of the '{@link #isIngestsGPS() <em>Ingests GPS</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIngestsGPS()
   * @generated
   * @ordered
   */
  protected static final boolean INGESTS_GPS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIngestsGPS() <em>Ingests GPS</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIngestsGPS()
   * @generated
   * @ordered
   */
  protected boolean ingestsGPS = INGESTS_GPS_EDEFAULT;

  /**
   * The default value of the '{@link #isOutputsGPS() <em>Outputs GPS</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isOutputsGPS()
   * @generated
   * @ordered
   */
  protected static final boolean OUTPUTS_GPS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isOutputsGPS() <em>Outputs GPS</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isOutputsGPS()
   * @generated
   * @ordered
   */
  protected boolean outputsGPS = OUTPUTS_GPS_EDEFAULT;

  /**
   * The default value of the '{@link #isIsRxTuner() <em>Is Rx Tuner</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsRxTuner()
   * @generated
   * @ordered
   */
  protected static final boolean IS_RX_TUNER_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsRxTuner() <em>Is Rx Tuner</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsRxTuner()
   * @generated
   * @ordered
   */
  protected boolean isRxTuner = IS_RX_TUNER_EDEFAULT;

  /**
   * The default value of the '{@link #isHasAnalogInput() <em>Has Analog Input</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasAnalogInput()
   * @generated
   * @ordered
   */
  protected static final boolean HAS_ANALOG_INPUT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isHasAnalogInput() <em>Has Analog Input</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasAnalogInput()
   * @generated
   * @ordered
   */
  protected boolean hasAnalogInput = HAS_ANALOG_INPUT_EDEFAULT;

  /**
   * The default value of the '{@link #getNumberOfAnalogInputs() <em>Number Of Analog Inputs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfAnalogInputs()
   * @generated
   * @ordered
   */
  protected static final int NUMBER_OF_ANALOG_INPUTS_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getNumberOfAnalogInputs() <em>Number Of Analog Inputs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfAnalogInputs()
   * @generated
   * @ordered
   */
  protected int numberOfAnalogInputs = NUMBER_OF_ANALOG_INPUTS_EDEFAULT;

  /**
   * The default value of the '{@link #isHasDigitalInput() <em>Has Digital Input</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasDigitalInput()
   * @generated
   * @ordered
   */
  protected static final boolean HAS_DIGITAL_INPUT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isHasDigitalInput() <em>Has Digital Input</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasDigitalInput()
   * @generated
   * @ordered
   */
  protected boolean hasDigitalInput = HAS_DIGITAL_INPUT_EDEFAULT;

  /**
   * The default value of the '{@link #getDigitalInputType() <em>Digital Input Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalInputType()
   * @generated
   * @ordered
   */
  protected static final String DIGITAL_INPUT_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDigitalInputType() <em>Digital Input Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalInputType()
   * @generated
   * @ordered
   */
  protected String digitalInputType = DIGITAL_INPUT_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #isIsMultiOut() <em>Is Multi Out</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsMultiOut()
   * @generated
   * @ordered
   */
  protected static final boolean IS_MULTI_OUT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsMultiOut() <em>Is Multi Out</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsMultiOut()
   * @generated
   * @ordered
   */
  protected boolean isMultiOut = IS_MULTI_OUT_EDEFAULT;

  /**
   * The default value of the '{@link #getDigitalOutputType() <em>Digital Output Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalOutputType()
   * @generated
   * @ordered
   */
  protected static final String DIGITAL_OUTPUT_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDigitalOutputType() <em>Digital Output Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalOutputType()
   * @generated
   * @ordered
   */
  protected String digitalOutputType = DIGITAL_OUTPUT_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #isIsTxTuner() <em>Is Tx Tuner</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsTxTuner()
   * @generated
   * @ordered
   */
  protected static final boolean IS_TX_TUNER_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsTxTuner() <em>Is Tx Tuner</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsTxTuner()
   * @generated
   * @ordered
   */
  protected boolean isTxTuner = IS_TX_TUNER_EDEFAULT;

  /**
   * The default value of the '{@link #getNumberOfDigitalInputsForTx() <em>Number Of Digital Inputs For Tx</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfDigitalInputsForTx()
   * @generated
   * @ordered
   */
  protected static final int NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getNumberOfDigitalInputsForTx() <em>Number Of Digital Inputs For Tx</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfDigitalInputsForTx()
   * @generated
   * @ordered
   */
  protected int numberOfDigitalInputsForTx = NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT;

  /**
   * The default value of the '{@link #getDigitalInputTypeForTx() <em>Digital Input Type For Tx</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalInputTypeForTx()
   * @generated
   * @ordered
   */
  protected static final String DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDigitalInputTypeForTx() <em>Digital Input Type For Tx</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDigitalInputTypeForTx()
   * @generated
   * @ordered
   */
  protected String digitalInputTypeForTx = DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT;

  /**
   * The default value of the '{@link #getTunerStatusStruct() <em>Tuner Status Struct</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTunerStatusStruct()
   * @generated
   * @ordered
   */
  protected static final ScaStructProperty TUNER_STATUS_STRUCT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTunerStatusStruct() <em>Tuner Status Struct</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTunerStatusStruct()
   * @generated
   * @ordered
   */
  protected ScaStructProperty tunerStatusStruct = TUNER_STATUS_STRUCT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FeiDeviceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return FrontendPackage.Literals.FEI_DEVICE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsAntenna()
  {
    return isAntenna;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsAntenna(boolean newIsAntenna)
  {
    boolean oldIsAntenna = isAntenna;
    isAntenna = newIsAntenna;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__IS_ANTENNA, oldIsAntenna, isAntenna));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIngestsGPS()
  {
    return ingestsGPS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIngestsGPS(boolean newIngestsGPS)
  {
    boolean oldIngestsGPS = ingestsGPS;
    ingestsGPS = newIngestsGPS;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__INGESTS_GPS, oldIngestsGPS, ingestsGPS));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isOutputsGPS()
  {
    return outputsGPS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOutputsGPS(boolean newOutputsGPS)
  {
    boolean oldOutputsGPS = outputsGPS;
    outputsGPS = newOutputsGPS;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__OUTPUTS_GPS, oldOutputsGPS, outputsGPS));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsRxTuner()
  {
    return isRxTuner;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsRxTuner(boolean newIsRxTuner)
  {
    boolean oldIsRxTuner = isRxTuner;
    isRxTuner = newIsRxTuner;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__IS_RX_TUNER, oldIsRxTuner, isRxTuner));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isHasAnalogInput()
  {
    return hasAnalogInput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHasAnalogInput(boolean newHasAnalogInput)
  {
    boolean oldHasAnalogInput = hasAnalogInput;
    hasAnalogInput = newHasAnalogInput;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__HAS_ANALOG_INPUT, oldHasAnalogInput, hasAnalogInput));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getNumberOfAnalogInputs()
  {
    return numberOfAnalogInputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfAnalogInputs(int newNumberOfAnalogInputs)
  {
    int oldNumberOfAnalogInputs = numberOfAnalogInputs;
    numberOfAnalogInputs = newNumberOfAnalogInputs;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS, oldNumberOfAnalogInputs, numberOfAnalogInputs));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isHasDigitalInput()
  {
    return hasDigitalInput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHasDigitalInput(boolean newHasDigitalInput)
  {
    boolean oldHasDigitalInput = hasDigitalInput;
    hasDigitalInput = newHasDigitalInput;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT, oldHasDigitalInput, hasDigitalInput));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDigitalInputType()
  {
    return digitalInputType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDigitalInputType(String newDigitalInputType)
  {
    String oldDigitalInputType = digitalInputType;
    digitalInputType = newDigitalInputType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE, oldDigitalInputType, digitalInputType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsMultiOut()
  {
    return isMultiOut;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsMultiOut(boolean newIsMultiOut)
  {
    boolean oldIsMultiOut = isMultiOut;
    isMultiOut = newIsMultiOut;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__IS_MULTI_OUT, oldIsMultiOut, isMultiOut));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDigitalOutputType()
  {
    return digitalOutputType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDigitalOutputType(String newDigitalOutputType)
  {
    String oldDigitalOutputType = digitalOutputType;
    digitalOutputType = newDigitalOutputType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE, oldDigitalOutputType, digitalOutputType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsTxTuner()
  {
    return isTxTuner;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsTxTuner(boolean newIsTxTuner)
  {
    boolean oldIsTxTuner = isTxTuner;
    isTxTuner = newIsTxTuner;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__IS_TX_TUNER, oldIsTxTuner, isTxTuner));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getNumberOfDigitalInputsForTx()
  {
    return numberOfDigitalInputsForTx;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfDigitalInputsForTx(int newNumberOfDigitalInputsForTx)
  {
    int oldNumberOfDigitalInputsForTx = numberOfDigitalInputsForTx;
    numberOfDigitalInputsForTx = newNumberOfDigitalInputsForTx;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX, oldNumberOfDigitalInputsForTx, numberOfDigitalInputsForTx));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDigitalInputTypeForTx()
  {
    return digitalInputTypeForTx;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDigitalInputTypeForTx(String newDigitalInputTypeForTx)
  {
    String oldDigitalInputTypeForTx = digitalInputTypeForTx;
    digitalInputTypeForTx = newDigitalInputTypeForTx;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX, oldDigitalInputTypeForTx, digitalInputTypeForTx));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScaStructProperty getTunerStatusStruct()
  {
    return tunerStatusStruct;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTunerStatusStruct(ScaStructProperty newTunerStatusStruct)
  {
    ScaStructProperty oldTunerStatusStruct = tunerStatusStruct;
    tunerStatusStruct = newTunerStatusStruct;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT, oldTunerStatusStruct, tunerStatusStruct));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case FrontendPackage.FEI_DEVICE__IS_ANTENNA:
        return isIsAntenna();
      case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
        return isIngestsGPS();
      case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
        return isOutputsGPS();
      case FrontendPackage.FEI_DEVICE__IS_RX_TUNER:
        return isIsRxTuner();
      case FrontendPackage.FEI_DEVICE__HAS_ANALOG_INPUT:
        return isHasAnalogInput();
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
        return getNumberOfAnalogInputs();
      case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
        return isHasDigitalInput();
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
        return getDigitalInputType();
      case FrontendPackage.FEI_DEVICE__IS_MULTI_OUT:
        return isIsMultiOut();
      case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
        return getDigitalOutputType();
      case FrontendPackage.FEI_DEVICE__IS_TX_TUNER:
        return isIsTxTuner();
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
        return getNumberOfDigitalInputsForTx();
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
        return getDigitalInputTypeForTx();
      case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
        return getTunerStatusStruct();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case FrontendPackage.FEI_DEVICE__IS_ANTENNA:
        setIsAntenna((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
        setIngestsGPS((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
        setOutputsGPS((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__IS_RX_TUNER:
        setIsRxTuner((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__HAS_ANALOG_INPUT:
        setHasAnalogInput((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
        setNumberOfAnalogInputs((Integer)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
        setHasDigitalInput((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
        setDigitalInputType((String)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__IS_MULTI_OUT:
        setIsMultiOut((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
        setDigitalOutputType((String)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__IS_TX_TUNER:
        setIsTxTuner((Boolean)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
        setNumberOfDigitalInputsForTx((Integer)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
        setDigitalInputTypeForTx((String)newValue);
        return;
      case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
        setTunerStatusStruct((ScaStructProperty)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case FrontendPackage.FEI_DEVICE__IS_ANTENNA:
        setIsAntenna(IS_ANTENNA_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
        setIngestsGPS(INGESTS_GPS_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
        setOutputsGPS(OUTPUTS_GPS_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__IS_RX_TUNER:
        setIsRxTuner(IS_RX_TUNER_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__HAS_ANALOG_INPUT:
        setHasAnalogInput(HAS_ANALOG_INPUT_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
        setNumberOfAnalogInputs(NUMBER_OF_ANALOG_INPUTS_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
        setHasDigitalInput(HAS_DIGITAL_INPUT_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
        setDigitalInputType(DIGITAL_INPUT_TYPE_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__IS_MULTI_OUT:
        setIsMultiOut(IS_MULTI_OUT_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
        setDigitalOutputType(DIGITAL_OUTPUT_TYPE_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__IS_TX_TUNER:
        setIsTxTuner(IS_TX_TUNER_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
        setNumberOfDigitalInputsForTx(NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
        setDigitalInputTypeForTx(DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT);
        return;
      case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
        setTunerStatusStruct(TUNER_STATUS_STRUCT_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case FrontendPackage.FEI_DEVICE__IS_ANTENNA:
        return isAntenna != IS_ANTENNA_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
        return ingestsGPS != INGESTS_GPS_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
        return outputsGPS != OUTPUTS_GPS_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__IS_RX_TUNER:
        return isRxTuner != IS_RX_TUNER_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__HAS_ANALOG_INPUT:
        return hasAnalogInput != HAS_ANALOG_INPUT_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
        return numberOfAnalogInputs != NUMBER_OF_ANALOG_INPUTS_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
        return hasDigitalInput != HAS_DIGITAL_INPUT_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
        return DIGITAL_INPUT_TYPE_EDEFAULT == null ? digitalInputType != null : !DIGITAL_INPUT_TYPE_EDEFAULT.equals(digitalInputType);
      case FrontendPackage.FEI_DEVICE__IS_MULTI_OUT:
        return isMultiOut != IS_MULTI_OUT_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
        return DIGITAL_OUTPUT_TYPE_EDEFAULT == null ? digitalOutputType != null : !DIGITAL_OUTPUT_TYPE_EDEFAULT.equals(digitalOutputType);
      case FrontendPackage.FEI_DEVICE__IS_TX_TUNER:
        return isTxTuner != IS_TX_TUNER_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
        return numberOfDigitalInputsForTx != NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT;
      case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
        return DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT == null ? digitalInputTypeForTx != null : !DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT.equals(digitalInputTypeForTx);
      case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
        return TUNER_STATUS_STRUCT_EDEFAULT == null ? tunerStatusStruct != null : !TUNER_STATUS_STRUCT_EDEFAULT.equals(tunerStatusStruct);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (isAntenna: ");
    result.append(isAntenna);
    result.append(", ingestsGPS: ");
    result.append(ingestsGPS);
    result.append(", outputsGPS: ");
    result.append(outputsGPS);
    result.append(", isRxTuner: ");
    result.append(isRxTuner);
    result.append(", hasAnalogInput: ");
    result.append(hasAnalogInput);
    result.append(", numberOfAnalogInputs: ");
    result.append(numberOfAnalogInputs);
    result.append(", hasDigitalInput: ");
    result.append(hasDigitalInput);
    result.append(", digitalInputType: ");
    result.append(digitalInputType);
    result.append(", isMultiOut: ");
    result.append(isMultiOut);
    result.append(", digitalOutputType: ");
    result.append(digitalOutputType);
    result.append(", isTxTuner: ");
    result.append(isTxTuner);
    result.append(", numberOfDigitalInputsForTx: ");
    result.append(numberOfDigitalInputsForTx);
    result.append(", digitalInputTypeForTx: ");
    result.append(digitalInputTypeForTx);
    result.append(", tunerStatusStruct: ");
    result.append(tunerStatusStruct);
    result.append(')');
    return result.toString();
  }

} //FeiDeviceImpl
