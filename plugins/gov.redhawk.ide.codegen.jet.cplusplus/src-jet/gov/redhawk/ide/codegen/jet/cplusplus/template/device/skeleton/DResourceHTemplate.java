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

public class DResourceHTemplate
{

  protected static String nl;
  public static synchronized DResourceHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DResourceHTemplate result = new DResourceHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef ";
  protected final String TEXT_2 = "_IMPL_H" + NL + "#define ";
  protected final String TEXT_3 = "_IMPL_H" + NL + "" + NL + "#include <stdlib.h>" + NL + "#include <string>" + NL + "#include <map>" + NL + "#include <list>" + NL + "" + NL + "#include \"CF/cf.h\"";
  protected final String TEXT_4 = NL + "#include \"CF/AggregateDevices.h\"";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + "#include \"ossie/AggregateDevice_impl.h\"";
  protected final String TEXT_7 = NL + "#include \"ossie/";
  protected final String TEXT_8 = "Device_impl.h\"" + NL + "#include \"ossie/ossieSupport.h\"" + NL + "using namespace std;" + NL + "#include <sys/time.h>" + NL + "#include <queue>" + NL + "#include <fstream>" + NL + "" + NL + "class ";
  protected final String TEXT_9 = "_i;" + NL + "class ";
  protected final String TEXT_10 = "_i : " + NL + "\t";
  protected final String TEXT_11 = "public virtual POA_CF::AggregateExecutableDevice, ";
  protected final String TEXT_12 = "public virtual POA_CF::AggregateLoadableDevice, ";
  protected final String TEXT_13 = "public virtual POA_CF::AggregatePlainDevice, ";
  protected final String TEXT_14 = "public ExecutableDevice_impl ";
  protected final String TEXT_15 = "public LoadableDevice_impl ";
  protected final String TEXT_16 = "public Device_impl ";
  protected final String TEXT_17 = " , public AggregateDevice_impl ";
  protected final String TEXT_18 = NL + "{" + NL + "    public:";
  protected final String TEXT_19 = NL + "        ";
  protected final String TEXT_20 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);";
  protected final String TEXT_21 = NL + "        ";
  protected final String TEXT_22 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities);";
  protected final String TEXT_23 = NL + "        ";
  protected final String TEXT_24 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compositeDev);";
  protected final String TEXT_25 = NL + "        ";
  protected final String TEXT_26 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compositeDev);" + NL + "        " + NL + "        void initResource(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);" + NL + "" + NL + "        ~";
  protected final String TEXT_27 = "_i(void);" + NL + "" + NL + "        CORBA::Boolean allocateCapacity (const CF::Properties & capacities)" + NL + "                throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState);" + NL + "        void deallocateCapacity (const CF::Properties & capacities)" + NL + "                throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState);" + NL + "" + NL + "        CORBA::Object_ptr getPort( const char* _id ) throw (CF::PortSupplier::UnknownPort, CORBA::SystemException);" + NL + "" + NL + "        void releaseObject() throw (CF::LifeCycle::ReleaseError, CORBA::SystemException);" + NL + "" + NL + "    private:" + NL + "        // For component shutdown" + NL + "        string comp_uuid;" + NL + "" + NL + "        bool component_alive;" + NL + "" + NL + "        string naming_service_name;" + NL + "        ossie::ORB *orb;" + NL + "" + NL + "        // Threading stuff" + NL + "        omni_condition *data_in_signal;" + NL + "        omni_mutex data_in_signal_lock;" + NL + "        omni_mutex process_data_lock;" + NL + "        omni_mutex thread_exit_lock;" + NL + "        omni_mutex attribute_access;\t// used when modifying variables";
  protected final String TEXT_28 = NL + "        CF::DeviceSequence *devSeq;";
  protected final String TEXT_29 = NL + "        // Functional members" + NL + "        // Housekeeping and data management variables" + NL + "        " + NL + "        bool thread_started;      " + NL + "};" + NL + "#endif";
  protected final String TEXT_30 = NL;

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
    boolean execDevice = false;
    boolean loadableDevice = false;
    if (deviceType.contains(RedhawkIdePreferenceConstants.EXECUTABLE_DEVICE.toLowerCase())) {
        deviceType = "Executable";
        execDevice = true;
    } else if (deviceType.contains(RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase())) {
        deviceType = "Loadable";
        loadableDevice = true;
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
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_3);
    
    if (aggregateDevice) {

    stringBuffer.append(TEXT_4);
    
    }

    stringBuffer.append(TEXT_5);
     
    if (aggregateDevice) {

    stringBuffer.append(TEXT_6);
    
    }

    stringBuffer.append(TEXT_7);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
     if (aggregateDevice) {
	        if (execDevice) {
    stringBuffer.append(TEXT_11);
     } 
            else if (loadableDevice) {
    stringBuffer.append(TEXT_12);
     }
            else {
    stringBuffer.append(TEXT_13);
     }
	   } 
     if (execDevice) {
    stringBuffer.append(TEXT_14);
     } 
     else if (loadableDevice) {
    stringBuffer.append(TEXT_15);
     } 
     else {
    stringBuffer.append(TEXT_16);
     } 
     if (aggregateDevice) { 
    stringBuffer.append(TEXT_17);
     } 
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_27);
    
    if (aggregateDevice) {

    stringBuffer.append(TEXT_28);
    
    }

    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    return stringBuffer.toString();
  }
} 