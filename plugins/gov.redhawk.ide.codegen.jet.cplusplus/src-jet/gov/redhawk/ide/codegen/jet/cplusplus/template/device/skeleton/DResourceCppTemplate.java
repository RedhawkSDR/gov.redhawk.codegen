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
package gov.redhawk.ide.codegen.jet.cplusplus.template.device.skeleton;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import mil.jpeojtrs.sca.scd.SupportsInterface;
import mil.jpeojtrs.sca.spd.SoftPkg;

	/**
    * @generated
    */

public class DResourceCppTemplate
{

  protected static String nl;
  public static synchronized DResourceCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DResourceCppTemplate result = new DResourceCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = " " + NL + "" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = "_i::";
  protected final String TEXT_5 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl) :";
  protected final String TEXT_6 = NL + "          ";
  protected final String TEXT_7 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_8 = ", AggregateDevice_impl ()";
  protected final String TEXT_9 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "_i::";
  protected final String TEXT_12 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev) :";
  protected final String TEXT_13 = NL + "          ";
  protected final String TEXT_14 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_15 = ", AggregateDevice_impl ()";
  protected final String TEXT_16 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = "_i::";
  protected final String TEXT_19 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities) :";
  protected final String TEXT_20 = NL + "          ";
  protected final String TEXT_21 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_22 = ", AggregateDevice_impl ()";
  protected final String TEXT_23 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = "_i::";
  protected final String TEXT_26 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev) :";
  protected final String TEXT_27 = NL + "          ";
  protected final String TEXT_28 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_29 = ", AggregateDevice_impl ()";
  protected final String TEXT_30 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL + "" + NL + "void ";
  protected final String TEXT_31 = "_i::initResource(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl) " + NL + "{" + NL + "    // component_alive flag is turned to false to terminate the main processing thread" + NL + "    component_alive = true;" + NL + "    naming_service_name = lbl;" + NL + "" + NL + "    // this is the signal used to tell the main procesing thread that information is ready" + NL + "    //  it is thrown by the input (provides) port when data is ready" + NL + "    data_in_signal = new omni_condition(&data_in_signal_lock);" + NL + "" + NL + "    //Initialize variables" + NL + "" + NL + "    thread_started = false;" + NL + "};" + NL + "\t  ";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = "_i::~";
  protected final String TEXT_34 = "_i() {" + NL + "};" + NL + "" + NL + "CORBA::Boolean ";
  protected final String TEXT_35 = "_i::allocateCapacity (const CF::Properties & capacities)" + NL + "    throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState) " + NL + "{" + NL + "\t// Allocate the space needed for the following capacities." + NL + "    // TODO" + NL + "    return true;" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_36 = "_i::deallocateCapacity (const CF::Properties & capacities)" + NL + "\tthrow (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState) " + NL + "{" + NL + "\t// Free up space as specified by capacities." + NL + "\t// TODO" + NL + "}" + NL + "" + NL + "CORBA::Object_ptr ";
  protected final String TEXT_37 = "_i::getPort( const char* _id )" + NL + "\tthrow (CF::PortSupplier::UnknownPort, CORBA::SystemException)" + NL + "{" + NL + "\t// This function will return an object reference for the named port which in turn will be used to establish a connection" + NL + "\t// between two separate ports possible." + NL + "\t" + NL + "\t// TODO" + NL + "\treturn NULL;" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_38 = "_i::releaseObject()" + NL + "\tthrow (CF::LifeCycle::ReleaseError, CORBA::SystemException)" + NL + "{" + NL + "\t// This is the part of the code where you will put in the logic that will appropriately cleanup" + NL + "\t// your component.  It is appropriate to do Memory Cleanup here since" + NL + "\t// CORBA doesn't like any cleaning up done in the Destructor." + NL + "\t// TODO" + NL + "}";
  protected final String TEXT_39 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    SoftPkg softPkg = (SoftPkg) templ.getImpl().eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    String deviceType = softPkg.getDescriptor().getComponent().getComponentType();
    boolean aggregateDevice = false;
    if (deviceType.contains(RedhawkIdePreferenceConstants.EXECUTABLE_DEVICE.toLowerCase())) {
        deviceType = "Executable";
    } else if (deviceType.contains(RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase())) {
        deviceType = "Loadable";
    } else { 
        deviceType = "";
    } 
    if (RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase().equals(deviceType)) {
        deviceType = "Loadable";
    }
	for (SupportsInterface inter : softPkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface()) {
		if (inter.getSupportsName().contains(RedhawkIdePreferenceConstants.AGGREGATE_DEVICE)) {
			aggregateDevice = true;
		}
	}

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_7);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_8);
     }
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_14);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_15);
     }
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_21);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_22);
     }
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_28);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_29);
     }
    stringBuffer.append(TEXT_30);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(TEXT_39);
    return stringBuffer.toString();
  }
} 