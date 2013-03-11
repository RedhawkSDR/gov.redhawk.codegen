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
package gov.redhawk.ide.codegen.jet.cplusplus.template.device.push;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.SupportsInterface;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

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
  protected final String TEXT_1 = NL + "#include \"";
  protected final String TEXT_2 = ".h\"";
  protected final String TEXT_3 = NL + "#include \"port_impl.h\"";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "_i::";
  protected final String TEXT_7 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl) :";
  protected final String TEXT_8 = NL + "          ";
  protected final String TEXT_9 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_10 = ", AggregateDevice_impl ()";
  protected final String TEXT_11 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "_i::";
  protected final String TEXT_14 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev) :";
  protected final String TEXT_15 = NL + "          ";
  protected final String TEXT_16 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_17 = ", AggregateDevice_impl ()";
  protected final String TEXT_18 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "_i::";
  protected final String TEXT_21 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities) :";
  protected final String TEXT_22 = NL + "          ";
  protected final String TEXT_23 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_24 = ", AggregateDevice_impl ()";
  protected final String TEXT_25 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL;
  protected final String TEXT_26 = NL;
  protected final String TEXT_27 = "_i::";
  protected final String TEXT_28 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev) :";
  protected final String TEXT_29 = NL + "          ";
  protected final String TEXT_30 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_31 = ", AggregateDevice_impl ()";
  protected final String TEXT_32 = "{" + NL + "    initResource(devMgr_ior, id, lbl, sftwrPrfl);" + NL + "};" + NL + "" + NL + "void ";
  protected final String TEXT_33 = "_i::initResource(char *devMgr_ior, char *id, char *label, char *sftwrPrfl) " + NL + "{" + NL + "    loadProperties();" + NL;
  protected final String TEXT_34 = NL + "    //" + NL + "    // Provides (input) ports" + NL + "    //";
  protected final String TEXT_35 = NL + "    ";
  protected final String TEXT_36 = "_";
  protected final String TEXT_37 = "_In_i* tmp";
  protected final String TEXT_38 = " = new ";
  protected final String TEXT_39 = "_";
  protected final String TEXT_40 = "_In_i(\"";
  protected final String TEXT_41 = "\", this);" + NL + "    registerInPort(tmp";
  protected final String TEXT_42 = ");" + NL;
  protected final String TEXT_43 = NL + "    //" + NL + "    // Uses (output) ports" + NL + "    //";
  protected final String TEXT_44 = NL + "    ";
  protected final String TEXT_45 = "_";
  protected final String TEXT_46 = "_Out_i* tmp";
  protected final String TEXT_47 = " = new ";
  protected final String TEXT_48 = "_";
  protected final String TEXT_49 = "_Out_i(\"";
  protected final String TEXT_50 = "\"";
  protected final String TEXT_51 = ", this";
  protected final String TEXT_52 = ");" + NL + "    registerOutPort(tmp";
  protected final String TEXT_53 = ", tmp";
  protected final String TEXT_54 = "->_this());" + NL;
  protected final String TEXT_55 = NL + "    naming_service_name = label;" + NL + "" + NL + "    //Initialize variables" + NL + "    // TODO" + NL + "    thread_started = false;" + NL + "};" + NL;
  protected final String TEXT_56 = NL;
  protected final String TEXT_57 = "_i::~";
  protected final String TEXT_58 = "_i() {" + NL + "};" + NL + "" + NL + "CORBA::Boolean ";
  protected final String TEXT_59 = "_i::allocateCapacity (const CF::Properties & capacities)" + NL + "    throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState) " + NL + "{" + NL + "    " + NL + "    bool response = true;" + NL + "    std::string tmp;" + NL + "    propertyContainer *pCptr;" + NL + "" + NL + "    for (unsigned int i = 0; i < capacities.length(); i++) {" + NL + "        tmp = capacities[i].id;" + NL + "        pCptr = getPropFromId(tmp);" + NL + "        if (!pCptr) {" + NL + "            response = false;" + NL + "            break;" + NL + "        } else if (pCptr->compare(capacities[i].value) > 0) {" + NL + "            response = false;" + NL + "            break;" + NL + "        }" + NL + "    }" + NL + "    if (!response) {" + NL + "        return false;" + NL + "    }" + NL + "        " + NL + "    for (unsigned int i = 0; i < capacities.length(); i++) {" + NL + "        tmp = capacities[i].id;" + NL + "        pCptr = getPropFromId(tmp);" + NL + "        pCptr->decrement(capacities[i].value);" + NL + "    }" + NL + "    return true;" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_60 = "_i::deallocateCapacity (const CF::Properties & capacities)" + NL + "\tthrow (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState) " + NL + "{" + NL + "" + NL + "    std::string tmp;" + NL + "    propertyContainer *pCptr;" + NL + "" + NL + "    for (unsigned int i=0; i<capacities.length(); i++) {" + NL + "        tmp = capacities[i].id;" + NL + "        pCptr = getPropFromId(tmp);" + NL + "        if (!pCptr) {" + NL + "            continue;" + NL + "        }" + NL + "        pCptr->increment(capacities[i].value);" + NL + "    }" + NL + "}";
  protected final String TEXT_61 = NL + NL + "/*************************************************************************************************" + NL + "  Port service routines" + NL + "    " + NL + "  These are functions that have been added to the component to service port calls. The file port_impl.cpp" + NL + "  does all the annoying CORBA stuff." + NL + "    " + NL + "  In the case of input ports, the port in port_impl handles all the" + NL + "  receive CORBA stuff and calls the component's function with the same name. The function knows who the call is" + NL + "  from based on the input argument port_name, which is the framework-level port name." + NL + "" + NL + "  If port_name corresponds to an input port, then this function is where you would add your servicing code. Note" + NL + "  that each of these functions contains a process_data_lock mutex for the input port section. This is so that when" + NL + "  the control information is process, the component won't process any data (and that it will finish whatever processing" + NL + "  it's doing before handling the control call)" + NL + "" + NL + "  If port_name is an output port, then this call just makes a call to the appropriate output port (again, implemented" + NL + "  in port_impl.cpp), which will then handle the CORBA stuff for you. To use the output port, just call the function" + NL + "  and give it the name of the port that should be used to output the data." + NL + "  " + NL + "  These ports are where it would be a good place to set the data_in_signal that the main processing thread" + NL + "  uses to iterate." + NL + "*************************************************************************************************/";
  protected final String TEXT_62 = NL;
  protected final String TEXT_63 = NL;
  protected final String TEXT_64 = " ";
  protected final String TEXT_65 = "_i::";
  protected final String TEXT_66 = "(std::string port_name";
  protected final String TEXT_67 = ", ";
  protected final String TEXT_68 = ")";
  protected final String TEXT_69 = " ";
  protected final String TEXT_70 = ", unsigned long length";
  protected final String TEXT_71 = " ";
  protected final String TEXT_72 = ")";
  protected final String TEXT_73 = ", ";
  protected final String TEXT_74 = NL + "{";
  protected final String TEXT_75 = NL + "    ";
  protected final String TEXT_76 = " retval = ";
  protected final String TEXT_77 = ";" + NL + "    ";
  protected final String TEXT_78 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());";
  protected final String TEXT_79 = NL + NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "    if (output_port)" + NL + "    {" + NL + "        ";
  protected final String TEXT_80 = NL + "        retval = ";
  protected final String TEXT_81 = "((";
  protected final String TEXT_82 = "_";
  protected final String TEXT_83 = "_Out_i *)p_out->second)->";
  protected final String TEXT_84 = "(";
  protected final String TEXT_85 = ", ";
  protected final String TEXT_86 = "); // send out the command" + NL + "    }";
  protected final String TEXT_87 = NL + "    else if (input_port)" + NL + "    {";
  protected final String TEXT_88 = NL + NL + "    if (input_port)" + NL + "    {";
  protected final String TEXT_89 = NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        ";
  protected final String TEXT_90 = NL + "        // MAKE SURE TO DELETE THIS BUFFER WHEN DONE!!!!!" + NL + "        delete data;";
  protected final String TEXT_91 = NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    " + NL + "    return";
  protected final String TEXT_92 = " retval";
  protected final String TEXT_93 = ";" + NL + "}";
  protected final String TEXT_94 = NL;
  protected final String TEXT_95 = NL;
  protected final String TEXT_96 = " ";
  protected final String TEXT_97 = "_i::";
  protected final String TEXT_98 = "(std::string port_name";
  protected final String TEXT_99 = ", ";
  protected final String TEXT_100 = ")";
  protected final String TEXT_101 = " ";
  protected final String TEXT_102 = ")";
  protected final String TEXT_103 = ", ";
  protected final String TEXT_104 = NL + "{";
  protected final String TEXT_105 = NL + "    ";
  protected final String TEXT_106 = " retval = ";
  protected final String TEXT_107 = ";" + NL + "    ";
  protected final String TEXT_108 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "    if (output_port)" + NL + "    {" + NL + "        ";
  protected final String TEXT_109 = NL + "        retval = ";
  protected final String TEXT_110 = "((";
  protected final String TEXT_111 = "_";
  protected final String TEXT_112 = "_Out_i *)p_out->second)->";
  protected final String TEXT_113 = "(";
  protected final String TEXT_114 = ", ";
  protected final String TEXT_115 = "); // send out the command" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_116 = " retval";
  protected final String TEXT_117 = ";" + NL + "}";
  protected final String TEXT_118 = NL;
  protected final String TEXT_119 = NL;
  protected final String TEXT_120 = " ";
  protected final String TEXT_121 = "_i::";
  protected final String TEXT_122 = "(std::string port_name";
  protected final String TEXT_123 = ", ";
  protected final String TEXT_124 = ")";
  protected final String TEXT_125 = " ";
  protected final String TEXT_126 = ", unsigned long length";
  protected final String TEXT_127 = " ";
  protected final String TEXT_128 = ")";
  protected final String TEXT_129 = ", ";
  protected final String TEXT_130 = NL + "{";
  protected final String TEXT_131 = NL + "    ";
  protected final String TEXT_132 = " retval = ";
  protected final String TEXT_133 = ";" + NL;
  protected final String TEXT_134 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "" + NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL;
  protected final String TEXT_135 = NL + "        // MAKE SURE TO DELETE THIS BUFFER WHEN DONE!!!!!" + NL + "        delete data;";
  protected final String TEXT_136 = NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_137 = " retval";
  protected final String TEXT_138 = ";" + NL + "" + NL + "}";
  protected final String TEXT_139 = NL + NL + "void ";
  protected final String TEXT_140 = "_i::pushSRI(std::string port_name, const BULKIO::StreamSRI& H)" + NL + "{";
  protected final String TEXT_141 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "    ";
  protected final String TEXT_142 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "    ";
  protected final String TEXT_143 = NL + "    if (output_port)" + NL + "    {";
  protected final String TEXT_144 = NL + "        if (!strcmp(port_name.c_str(), \"";
  protected final String TEXT_145 = "\")) {" + NL + "            ((";
  protected final String TEXT_146 = "_";
  protected final String TEXT_147 = "_Out_i*)p_out->second)->pushSRI(H); // send out the command" + NL + "        }";
  protected final String TEXT_148 = NL + "    }" + NL + "    ";
  protected final String TEXT_149 = NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    ";
  protected final String TEXT_150 = NL + "    return;" + NL + "}";
  protected final String TEXT_151 = NL + NL + "void ";
  protected final String TEXT_152 = "_i::pushSRI(std::string port_name, const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T)" + NL + "{";
  protected final String TEXT_153 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "    ";
  protected final String TEXT_154 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "    ";
  protected final String TEXT_155 = NL + "    if (output_port)" + NL + "    {";
  protected final String TEXT_156 = NL + "        if (!strcmp(port_name.c_str(), \"";
  protected final String TEXT_157 = "\")) {" + NL + "            ((";
  protected final String TEXT_158 = "_";
  protected final String TEXT_159 = "_Out_i*)p_out->second)->pushSRI(H, T); // send out the command" + NL + "        }";
  protected final String TEXT_160 = NL + "    }" + NL + "    ";
  protected final String TEXT_161 = NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    ";
  protected final String TEXT_162 = NL + "    return;" + NL + "}";
  protected final String TEXT_163 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
    EList<Provides> provides = ports.getProvides();
    EList<Uses> uses = ports.getUses();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    CppHelper _cppHelper = new CppHelper();
    String deviceType = softPkg.getDescriptor().getComponent().getComponentType();
    boolean aggregateDevice = false;
    if (deviceType.contains(RedhawkIdePreferenceConstants.EXECUTABLE_DEVICE.toLowerCase())) {
        deviceType = "Executable";
    } else if (deviceType.contains(RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase())) {
        deviceType = "Loadable";
    } else { 
        deviceType = "";
    } 
    if (deviceType == RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase()) {
        deviceType = "Loadable";
    }
	for (SupportsInterface inter : softPkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface()) {
		if (inter.getSupportsName().contains(RedhawkIdePreferenceConstants.AGGREGATE_DEVICE)) {
			aggregateDevice = true;
		}
	}
    boolean memcpyBuffer = false;
    for (Property tempProp : implSettings.getProperties()) {
    	if ("memcpy_buffer".equals(tempProp.getId())) {
    		if (Boolean.parseBoolean(tempProp.getValue())) {
    			memcpyBuffer = true;
    			break;
    		}
    	}
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    
    if ((uses.size() > 0)||(provides.size() > 0)) {

    stringBuffer.append(TEXT_3);
    
    }

    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_9);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_10);
     }
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_16);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_17);
     }
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_23);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_24);
     }
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_30);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_31);
     }
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    
    int port_count = 0;
    
    if (provides.size() > 0) {

    stringBuffer.append(TEXT_34);
    
        for (Provides pro : provides) {
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, pro.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + pro.getRepID()));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_35);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_42);
    
            port_count = port_count + 1; 
        } // end for provides
    } // end if provides
    
    if (uses.size() > 0) { 

    stringBuffer.append(TEXT_43);
    
        for (Uses use : uses) {
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + use.getRepID()));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_44);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_50);
    if ("dataSDDS".equals(interfaceName)) {
    stringBuffer.append(TEXT_51);
    }
    stringBuffer.append(TEXT_52);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_54);
    
            port_count = port_count + 1;
        } // end for uses
    } // end if uses

    stringBuffer.append(TEXT_55);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_60);
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_61);
    
        boolean writePushSRI_provides = false;
        boolean writePushSRI_uses = false;
        boolean writeSDDSPushSRI_provides = false;
        boolean writeSDDSPushSRI_uses = false;
        HashMap<String, Interface> intMap = new HashMap<String, Interface>();
        HashSet<String> usesList = new HashSet<String>();
        

        for (Uses entry : uses) {
            String intName = entry.getRepID();
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            intMap.put(intName, iface);
            String interfaceName = iface.getName();    
            for (Operation op : iface.getOperations()) {
                if ("pushSRI".equals(op.getName())) {
                    writePushSRI_uses |= !"dataSDDS".equals(interfaceName);
                    writeSDDSPushSRI_uses |= "dataSDDS".equals(interfaceName);
                    break;
                }
            }
           	usesList.add(intName);
        }
        
        HashSet<String> providesList = new HashSet<String>();
        for (Provides entry : provides) {
            String intName = entry.getRepID();
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            intMap.put(intName, iface);
            String interfaceName = iface.getName();    
            for (Operation op : iface.getOperations()) {
                if ("pushSRI".equals(op.getName())) {
                    writePushSRI_provides |= !"dataSDDS".equals(interfaceName);
                    writeSDDSPushSRI_provides |= "dataSDDS".equals(interfaceName);
                    break;
                }
            }
            providesList.add(intName);
        }
        HashSet<String> commonList = new HashSet<String>();
        for (String entry : usesList) {
            if (providesList.contains(entry)) {
                commonList.add(entry);
                continue;
            }
        }
        
        usesList.removeAll(commonList);
        providesList.removeAll(commonList);

        for (String entry : commonList) {
            Interface iface = intMap.get(entry);
            if (iface != null) {
                String nameSpace = iface.getNameSpace();
                String interfaceName = iface.getName();    
                for (Operation op : iface.getOperations()) {
                    int numParams = op.getParams().size();
                    boolean pushPacket = false;
                    if ("pushSRI".equals(op.getName())) {
                        continue;
                    } else if ("pushPacket".equals(op.getName()) && !"dataXML".equals(interfaceName)) {
                        pushPacket = true;
                    }

    stringBuffer.append(TEXT_62);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_64);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_66);
    if (numParams != 0) {
    stringBuffer.append(TEXT_67);
    } else {
    stringBuffer.append(TEXT_68);
    }
                    for (int i = 0; i < numParams; i++) {
                        if (pushPacket && (i == 0) && !memcpyBuffer && !"dataFile".equals(interfaceName)) {
                            String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(i).getCxxType());
                            if (dataTransfer.startsWith("std::vector")) {
                                if (dataTransfer.endsWith("& ")) {
                                    dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                                } else { 
                                    dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                                }
                            } 
                            
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_70);
    
                        } else {
                            
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
                            
    stringBuffer.append(TEXT_71);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        }
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_72);
    
                        } else {
                            
    stringBuffer.append(TEXT_73);
    
                        }
                    } // end for params

    stringBuffer.append(TEXT_74);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_75);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_76);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_77);
    
                    }

                    if (provides.size() > 0) {

    stringBuffer.append(TEXT_78);
    
                    }
                    if (!pushPacket) {

    
                        if (uses.size() > 0) {

    stringBuffer.append(TEXT_79);
    
                            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_80);
             }
    stringBuffer.append(TEXT_81);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_84);
    
                            for (int i = 0; i < numParams; i++) {
                                
    stringBuffer.append(op.getParams().get(i).getName());
    
                                if (i != (numParams - 1)) {
                                    
    stringBuffer.append(TEXT_85);
    
                                }
                            }
        
    stringBuffer.append(TEXT_86);
    
                        }

    stringBuffer.append(TEXT_87);
    
                    } else {

    stringBuffer.append(TEXT_88);
    
                    }

    stringBuffer.append(TEXT_89);
    
                    if (!memcpyBuffer && pushPacket) {

    stringBuffer.append(TEXT_90);
    
                    }

    stringBuffer.append(TEXT_91);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_92);
    }
    stringBuffer.append(TEXT_93);
    
                } // end for operations
            } // end if interfaces
        } // end for commonList

        for (String entry : usesList) {
            Interface iface = intMap.get(entry);
            if (iface != null) {
                String nameSpace = iface.getNameSpace();
                String interfaceName = iface.getName();    
                for (Operation op : iface.getOperations()) {
                    int numParams = op.getParams().size();
                    if ("pushPacket".equals(op.getName()) && !"dataXML".equals(interfaceName)) {
                        continue;
                    } else if ("pushSRI".equals(op.getName())) {
                        continue;
                    }

    stringBuffer.append(TEXT_94);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_96);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_98);
    if (numParams != 0) {
    stringBuffer.append(TEXT_99);
    } else {
    stringBuffer.append(TEXT_100);
    }
                    for (int i = 0; i < numParams; i++) {
                        
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
                        
    stringBuffer.append(TEXT_101);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_102);
    
                        } else {
                            
    stringBuffer.append(TEXT_103);
    
                        }
                    }

    stringBuffer.append(TEXT_104);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_105);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_106);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_107);
    
                    }

    stringBuffer.append(TEXT_108);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_109);
     }
    stringBuffer.append(TEXT_110);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_113);
    
                    for (int i = 0; i < numParams; i++) {
                        
    stringBuffer.append(op.getParams().get(i).getName());
    
                        if (i != (numParams - 1)) {
                            
    stringBuffer.append(TEXT_114);
    
                        }
                    }
        
    stringBuffer.append(TEXT_115);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_116);
    }
    stringBuffer.append(TEXT_117);
    
                } // end for Operations
            } // end if interfaces
        } // end if usesList

        for (String entry : providesList) {
            Interface iface = intMap.get(entry);
            if (iface != null) {
                for (Operation op : iface.getOperations()) {
                    int numParams = op.getParams().size();
                    boolean pushPacket = false;
                    if ("pushSRI".equals(op.getName())) {
                        continue;
                    } else if ("pushPacket".equals(op.getName())) {
                        pushPacket = true;
                    }

    stringBuffer.append(TEXT_118);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_120);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_122);
    if (numParams != 0) {
    stringBuffer.append(TEXT_123);
    } else {
    stringBuffer.append(TEXT_124);
    }
                    for (int i = 0; i < numParams; i++) {
                        if (pushPacket && (i == 0) && !memcpyBuffer && !"dataFile".equals(iface.getName())) {
                            String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(i).getCxxType());
                            if (dataTransfer.startsWith("std::vector")) {
                                if (dataTransfer.endsWith("& ")) {
                                    dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                                } else { 
                                    dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                                }
                            } 
                        
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_126);
    
                        } else {
                        
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_127);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        }
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_128);
    
                        } else {
                            
    stringBuffer.append(TEXT_129);
    
                        }
                    }

    stringBuffer.append(TEXT_130);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_131);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_132);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_133);
    
                    }

    stringBuffer.append(TEXT_134);
    
                    if (!memcpyBuffer && pushPacket) {

    stringBuffer.append(TEXT_135);
    
                    }

    stringBuffer.append(TEXT_136);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_137);
    }
    stringBuffer.append(TEXT_138);
    
                } // end for operations
            } // end if interfaces
        } // end for providesList
    
        if (writePushSRI_uses || writePushSRI_provides) {

    stringBuffer.append(TEXT_139);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_140);
    
            if (writePushSRI_uses) {

    stringBuffer.append(TEXT_141);
    
            }
            if (writePushSRI_provides) {

    stringBuffer.append(TEXT_142);
    
            }
            if (writePushSRI_uses) {

    stringBuffer.append(TEXT_143);
    
                for (Uses use : uses) {
                    String intName = use.getRepID();
                    Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
                    if (iface == null) {
                    	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
                    }
                    String nameSpace = iface.getNameSpace();
                    String interfaceName = iface.getName();
                    for (Operation op : iface.getOperations()) {
                        if ("pushSRI".equals(op.getName()) && !"dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_144);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_145);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_147);
    
                            break;
                        } // end if pushSRI
                    } // end for op
                } //end for use 

    stringBuffer.append(TEXT_148);
    
            } // end if uses sri
            if (writePushSRI_provides) { 

    stringBuffer.append(TEXT_149);
    
            } 

    stringBuffer.append(TEXT_150);
    
        } // end if writePushSRI
        if (writeSDDSPushSRI_uses || writeSDDSPushSRI_provides) {

    stringBuffer.append(TEXT_151);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_152);
    
            if (writeSDDSPushSRI_uses) {

    stringBuffer.append(TEXT_153);
    
            }
            if (writeSDDSPushSRI_provides) {

    stringBuffer.append(TEXT_154);
    
            }
            if (writeSDDSPushSRI_uses) {

    stringBuffer.append(TEXT_155);
    
                for (Uses use : uses) {
                    String intName = use.getRepID();
                    Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
                    if (iface == null) {
                    	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
                    }
                    String nameSpace = iface.getNameSpace();
                    String interfaceName = iface.getName();
                    for (Operation op : iface.getOperations()) {
                        if ("pushSRI".equals(op.getName()) && "dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_156);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_157);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_159);
    
                            break;
                        } // end if pushSRI
                    } // end for op
                } //end for use 

    stringBuffer.append(TEXT_160);
    
            } // end if uses sri
            if (writeSDDSPushSRI_provides) { 

    stringBuffer.append(TEXT_161);
    
            } 

    stringBuffer.append(TEXT_162);
    
        } // end if writeSDDSPushSRI
    
    } // end if has ports

    stringBuffer.append(TEXT_163);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE