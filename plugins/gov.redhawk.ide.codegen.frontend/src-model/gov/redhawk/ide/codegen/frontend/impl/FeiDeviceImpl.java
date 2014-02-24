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
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isAntenna <em>Antenna</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isIngestsGPS <em>Ingests GPS</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isOutputsGPS <em>Outputs GPS</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isRxTuner <em>Rx Tuner</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getNumberOfAnalogInputs <em>Number Of Analog
 * Inputs</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isHasDigitalInput <em>Has Digital Input</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalInputType <em>Digital Input Type</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isHasDigitalOutput <em>Has Digital Output</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalOutputType <em>Digital Output Type</em>}
 * </li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isMultiOut <em>Multi Out</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#isTxTuner <em>Tx Tuner</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getNumberOfDigitalInputsForTx <em>Number Of Digital
 * Inputs For Tx</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getDigitalInputTypeForTx <em>Digital Input Type For
 * Tx</em>}</li>
 * <li>{@link gov.redhawk.ide.codegen.frontend.impl.FeiDeviceImpl#getTunerStatusStruct <em>Tuner Status Struct</em>}
 * </li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FeiDeviceImpl extends MinimalEObjectImpl.Container implements FeiDevice {
	/**
	 * The default value of the '{@link #isAntenna() <em>Antenna</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAntenna()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ANTENNA_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAntenna() <em>Antenna</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAntenna()
	 * @generated
	 * @ordered
	 */
	protected boolean antenna = ANTENNA_EDEFAULT;

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
	 * The default value of the '{@link #isRxTuner() <em>Rx Tuner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRxTuner()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RX_TUNER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isRxTuner() <em>Rx Tuner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRxTuner()
	 * @generated
	 * @ordered
	 */
	protected boolean rxTuner = RX_TUNER_EDEFAULT;

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
	protected static final Definition DIGITAL_INPUT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDigitalInputType() <em>Digital Input Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDigitalInputType()
	 * @generated
	 * @ordered
	 */
	protected Definition digitalInputType = DIGITAL_INPUT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasDigitalOutput() <em>Has Digital Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDigitalOutput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_DIGITAL_OUTPUT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isHasDigitalOutput() <em>Has Digital Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDigitalOutput()
	 * @generated
	 * @ordered
	 */
	protected boolean hasDigitalOutput = HAS_DIGITAL_OUTPUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDigitalOutputType() <em>Digital Output Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDigitalOutputType()
	 * @generated
	 * @ordered
	 */
	protected static final Definition DIGITAL_OUTPUT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDigitalOutputType() <em>Digital Output Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDigitalOutputType()
	 * @generated
	 * @ordered
	 */
	protected Definition digitalOutputType = DIGITAL_OUTPUT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMultiOut() <em>Multi Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiOut()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTI_OUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiOut() <em>Multi Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiOut()
	 * @generated
	 * @ordered
	 */
	protected boolean multiOut = MULTI_OUT_EDEFAULT;

	/**
	 * The default value of the '{@link #isTxTuner() <em>Tx Tuner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTxTuner()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TX_TUNER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTxTuner() <em>Tx Tuner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTxTuner()
	 * @generated
	 * @ordered
	 */
	protected boolean txTuner = TX_TUNER_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfDigitalInputsForTx() <em>Number Of Digital Inputs For Tx</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfDigitalInputsForTx()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfDigitalInputsForTx() <em>Number Of Digital Inputs For Tx</em>}'
	 * attribute.
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
	protected static final Definition DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDigitalInputTypeForTx() <em>Digital Input Type For Tx</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDigitalInputTypeForTx()
	 * @generated
	 * @ordered
	 */
	protected Definition digitalInputTypeForTx = DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT;

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
	protected FeiDeviceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FrontendPackage.Literals.FEI_DEVICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAntenna() {
		return antenna;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAntenna(boolean newAntenna) {
		boolean oldAntenna = antenna;
		antenna = newAntenna;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__ANTENNA, oldAntenna, antenna));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIngestsGPS() {
		return ingestsGPS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIngestsGPS(boolean newIngestsGPS) {
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
	public boolean isOutputsGPS() {
		return outputsGPS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputsGPS(boolean newOutputsGPS) {
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
	public boolean isRxTuner() {
		return rxTuner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRxTuner(boolean newRxTuner) {
		boolean oldRxTuner = rxTuner;
		rxTuner = newRxTuner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__RX_TUNER, oldRxTuner, rxTuner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfAnalogInputs() {
		return numberOfAnalogInputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfAnalogInputs(int newNumberOfAnalogInputs) {
		int oldNumberOfAnalogInputs = numberOfAnalogInputs;
		numberOfAnalogInputs = newNumberOfAnalogInputs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS, oldNumberOfAnalogInputs,
				numberOfAnalogInputs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasDigitalInput() {
		return hasDigitalInput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasDigitalInput(boolean newHasDigitalInput) {
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
	public Definition getDigitalInputType() {
		return digitalInputType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDigitalInputType(Definition newDigitalInputType) {
		Definition oldDigitalInputType = digitalInputType;
		digitalInputType = newDigitalInputType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE, oldDigitalInputType, digitalInputType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasDigitalOutput() {
		return hasDigitalOutput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasDigitalOutput(boolean newHasDigitalOutput) {
		boolean oldHasDigitalOutput = hasDigitalOutput;
		hasDigitalOutput = newHasDigitalOutput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT, oldHasDigitalOutput, hasDigitalOutput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Definition getDigitalOutputType() {
		return digitalOutputType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDigitalOutputType(Definition newDigitalOutputType) {
		Definition oldDigitalOutputType = digitalOutputType;
		digitalOutputType = newDigitalOutputType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE, oldDigitalOutputType, digitalOutputType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultiOut() {
		return multiOut;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultiOut(boolean newMultiOut) {
		boolean oldMultiOut = multiOut;
		multiOut = newMultiOut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__MULTI_OUT, oldMultiOut, multiOut));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTxTuner() {
		return txTuner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTxTuner(boolean newTxTuner) {
		boolean oldTxTuner = txTuner;
		txTuner = newTxTuner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__TX_TUNER, oldTxTuner, txTuner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfDigitalInputsForTx() {
		return numberOfDigitalInputsForTx;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfDigitalInputsForTx(int newNumberOfDigitalInputsForTx) {
		int oldNumberOfDigitalInputsForTx = numberOfDigitalInputsForTx;
		numberOfDigitalInputsForTx = newNumberOfDigitalInputsForTx;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX, oldNumberOfDigitalInputsForTx,
				numberOfDigitalInputsForTx));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Definition getDigitalInputTypeForTx() {
		return digitalInputTypeForTx;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDigitalInputTypeForTx(Definition newDigitalInputTypeForTx) {
		Definition oldDigitalInputTypeForTx = digitalInputTypeForTx;
		digitalInputTypeForTx = newDigitalInputTypeForTx;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX, oldDigitalInputTypeForTx,
				digitalInputTypeForTx));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaStructProperty getTunerStatusStruct() {
		return tunerStatusStruct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTunerStatusStruct(ScaStructProperty newTunerStatusStruct) {
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case FrontendPackage.FEI_DEVICE__ANTENNA:
			return isAntenna();
		case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
			return isIngestsGPS();
		case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
			return isOutputsGPS();
		case FrontendPackage.FEI_DEVICE__RX_TUNER:
			return isRxTuner();
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
			return getNumberOfAnalogInputs();
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
			return isHasDigitalInput();
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
			return getDigitalInputType();
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT:
			return isHasDigitalOutput();
		case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
			return getDigitalOutputType();
		case FrontendPackage.FEI_DEVICE__MULTI_OUT:
			return isMultiOut();
		case FrontendPackage.FEI_DEVICE__TX_TUNER:
			return isTxTuner();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case FrontendPackage.FEI_DEVICE__ANTENNA:
			setAntenna((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
			setIngestsGPS((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
			setOutputsGPS((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__RX_TUNER:
			setRxTuner((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
			setNumberOfAnalogInputs((Integer) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
			setHasDigitalInput((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
			setDigitalInputType((Definition) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT:
			setHasDigitalOutput((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
			setDigitalOutputType((Definition) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__MULTI_OUT:
			setMultiOut((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__TX_TUNER:
			setTxTuner((Boolean) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
			setNumberOfDigitalInputsForTx((Integer) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
			setDigitalInputTypeForTx((Definition) newValue);
			return;
		case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
			setTunerStatusStruct((ScaStructProperty) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case FrontendPackage.FEI_DEVICE__ANTENNA:
			setAntenna(ANTENNA_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
			setIngestsGPS(INGESTS_GPS_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
			setOutputsGPS(OUTPUTS_GPS_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__RX_TUNER:
			setRxTuner(RX_TUNER_EDEFAULT);
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
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT:
			setHasDigitalOutput(HAS_DIGITAL_OUTPUT_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
			setDigitalOutputType(DIGITAL_OUTPUT_TYPE_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__MULTI_OUT:
			setMultiOut(MULTI_OUT_EDEFAULT);
			return;
		case FrontendPackage.FEI_DEVICE__TX_TUNER:
			setTxTuner(TX_TUNER_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case FrontendPackage.FEI_DEVICE__ANTENNA:
			return antenna != ANTENNA_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
			return ingestsGPS != INGESTS_GPS_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
			return outputsGPS != OUTPUTS_GPS_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__RX_TUNER:
			return rxTuner != RX_TUNER_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
			return numberOfAnalogInputs != NUMBER_OF_ANALOG_INPUTS_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
			return hasDigitalInput != HAS_DIGITAL_INPUT_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
			return DIGITAL_INPUT_TYPE_EDEFAULT == null ? digitalInputType != null : !DIGITAL_INPUT_TYPE_EDEFAULT.equals(digitalInputType);
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT:
			return hasDigitalOutput != HAS_DIGITAL_OUTPUT_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
			return DIGITAL_OUTPUT_TYPE_EDEFAULT == null ? digitalOutputType != null : !DIGITAL_OUTPUT_TYPE_EDEFAULT.equals(digitalOutputType);
		case FrontendPackage.FEI_DEVICE__MULTI_OUT:
			return multiOut != MULTI_OUT_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__TX_TUNER:
			return txTuner != TX_TUNER_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
			return numberOfDigitalInputsForTx != NUMBER_OF_DIGITAL_INPUTS_FOR_TX_EDEFAULT;
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
			return DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT == null ? digitalInputTypeForTx != null
				: !DIGITAL_INPUT_TYPE_FOR_TX_EDEFAULT.equals(digitalInputTypeForTx);
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
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (Antenna: ");
		result.append(antenna);
		result.append(", ingestsGPS: ");
		result.append(ingestsGPS);
		result.append(", outputsGPS: ");
		result.append(outputsGPS);
		result.append(", RxTuner: ");
		result.append(rxTuner);
		result.append(", numberOfAnalogInputs: ");
		result.append(numberOfAnalogInputs);
		result.append(", hasDigitalInput: ");
		result.append(hasDigitalInput);
		result.append(", digitalInputType: ");
		result.append(digitalInputType);
		result.append(", hasDigitalOutput: ");
		result.append(hasDigitalOutput);
		result.append(", digitalOutputType: ");
		result.append(digitalOutputType);
		result.append(", MultiOut: ");
		result.append(multiOut);
		result.append(", TxTuner: ");
		result.append(txTuner);
		result.append(", numberOfDigitalInputsForTx: ");
		result.append(numberOfDigitalInputsForTx);
		result.append(", digitalInputTypeForTx: ");
		result.append(digitalInputTypeForTx);
		result.append(", tunerStatusStruct: ");
		result.append(tunerStatusStruct);
		result.append(')');
		return result.toString();
	}

} // FeiDeviceImpl
