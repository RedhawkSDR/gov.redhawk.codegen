/**
 */
package gov.redhawk.ide.codegen.frontend.provider;

import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemColorProvider;
import org.eclipse.emf.edit.provider.ITableItemFontProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link gov.redhawk.ide.codegen.frontend.FeiDevice} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FeiDeviceItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, ITableItemLabelProvider, ITableItemColorProvider, ITableItemFontProvider, IItemColorProvider,
		IItemFontProvider {
	/**
	* This constructs an instance from a factory and a notifier.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	public FeiDeviceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	* This returns the property descriptors for the adapted class.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addAntennaPropertyDescriptor(object);
			addIngestsGPSPropertyDescriptor(object);
			addOutputsGPSPropertyDescriptor(object);
			addRxTunerPropertyDescriptor(object);
			addNumberOfAnalogInputsPropertyDescriptor(object);
			addHasDigitalInputPropertyDescriptor(object);
			addDigitalInputTypePropertyDescriptor(object);
			addHasDigitalOutputPropertyDescriptor(object);
			addDigitalOutputTypePropertyDescriptor(object);
			addMultiOutPropertyDescriptor(object);
			addTxTunerPropertyDescriptor(object);
			addNumberOfDigitalInputsForTxPropertyDescriptor(object);
			addDigitalInputTypeForTxPropertyDescriptor(object);
			addTunerStatusStructPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	* This adds a property descriptor for the Antenna feature.
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @generated
	*/
	protected void addAntennaPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_Antenna_feature"), getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_Antenna_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__ANTENNA, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Ingests GPS feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIngestsGPSPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_ingestsGPS_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_ingestsGPS_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__INGESTS_GPS, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Outputs GPS feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addOutputsGPSPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_outputsGPS_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_outputsGPS_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__OUTPUTS_GPS, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Rx Tuner feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addRxTunerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_RxTuner_feature"), getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_RxTuner_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__RX_TUNER, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Number Of Analog Inputs feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addNumberOfAnalogInputsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_numberOfAnalogInputs_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_numberOfAnalogInputs_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS, true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Has Digital Input feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addHasDigitalInputPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_hasDigitalInput_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_hasDigitalInput_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_INPUT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Digital Input Type feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addDigitalInputTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_digitalInputType_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_digitalInputType_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__DIGITAL_INPUT_TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Has Digital Output feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addHasDigitalOutputPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_hasDigitalOutput_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_hasDigitalOutput_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__HAS_DIGITAL_OUTPUT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Digital Output Type feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addDigitalOutputTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_digitalOutputType_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_digitalOutputType_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__DIGITAL_OUTPUT_TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Multi Out feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addMultiOutPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_MultiOut_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_MultiOut_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__MULTI_OUT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Tx Tuner feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addTxTunerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_TxTuner_feature"), getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_TxTuner_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__TX_TUNER, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Number Of Digital Inputs For Tx feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addNumberOfDigitalInputsForTxPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_numberOfDigitalInputsForTx_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_numberOfDigitalInputsForTx_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX, true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Digital Input Type For Tx feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addDigitalInputTypeForTxPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_digitalInputTypeForTx_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_digitalInputTypeForTx_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	* This adds a property descriptor for the Tuner Status Struct feature.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	protected void addTunerStatusStructPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_FeiDevice_tunerStatusStruct_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_FeiDevice_tunerStatusStruct_feature", "_UI_FeiDevice_type"),
			FrontendPackage.Literals.FEI_DEVICE__TUNER_STATUS_STRUCT, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	* This returns FeiDevice.gif.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/FeiDevice"));
	}

	/**
	* This returns the label text for the adapted class.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	public String getText(Object object) {
		FeiDevice feiDevice = (FeiDevice) object;
		return getString("_UI_FeiDevice_type") + " " + feiDevice.isAntenna();
	}

	/**
	* This handles model notifications by calling {@link #updateChildren} to update any cached
	* children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(FeiDevice.class)) {
		case FrontendPackage.FEI_DEVICE__ANTENNA:
		case FrontendPackage.FEI_DEVICE__INGESTS_GPS:
		case FrontendPackage.FEI_DEVICE__OUTPUTS_GPS:
		case FrontendPackage.FEI_DEVICE__RX_TUNER:
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_ANALOG_INPUTS:
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_INPUT:
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE:
		case FrontendPackage.FEI_DEVICE__HAS_DIGITAL_OUTPUT:
		case FrontendPackage.FEI_DEVICE__DIGITAL_OUTPUT_TYPE:
		case FrontendPackage.FEI_DEVICE__MULTI_OUT:
		case FrontendPackage.FEI_DEVICE__TX_TUNER:
		case FrontendPackage.FEI_DEVICE__NUMBER_OF_DIGITAL_INPUTS_FOR_TX:
		case FrontendPackage.FEI_DEVICE__DIGITAL_INPUT_TYPE_FOR_TX:
		case FrontendPackage.FEI_DEVICE__TUNER_STATUS_STRUCT:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	* This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	* that can be created under this object.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	* Return the resource locator for this item provider's resources.
	* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	* @generated
	*/
	@Override
	public ResourceLocator getResourceLocator() {
		return FrontendEditPlugin.INSTANCE;
	}

}
