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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.push;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
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
public class ResourceCppTemplate
{

  protected static String nl;
  public static synchronized ResourceCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceCppTemplate result = new ResourceCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**************************************************************************" + NL + "    This is the component code. This file contains all the access points" + NL + "     you need to use to be able to access all input and output ports," + NL + "     respond to incoming data, and perform general component housekeeping" + NL + "**************************************************************************/" + NL + "#include <iostream>" + NL + "#include <fstream>" + NL + "" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"";
  protected final String TEXT_3 = NL + "#include \"port_impl.h\"";
  protected final String TEXT_4 = NL + "#include <uuid/uuid.h>" + NL + "" + NL + "/**************************************************************************" + NL + "    Component-level housekeeping (memory and thread management)" + NL + "**************************************************************************/" + NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "_i::";
  protected final String TEXT_7 = "_i(const char *uuid, omni_condition *con," + NL + "    const char *label, ossie::ORB *in_orb) : Resource_impl(uuid)" + NL + "{" + NL + "    std::cout << \"Starting everything\" << std::endl;" + NL + "    component_running = con;" + NL + "    comp_uuid = uuid;" + NL + "" + NL + "    loadProperties();" + NL;
  protected final String TEXT_8 = NL + "    //" + NL + "    // Provides (input) ports" + NL + "    //";
  protected final String TEXT_9 = NL + "    ";
  protected final String TEXT_10 = "_";
  protected final String TEXT_11 = "_In_i* tmp";
  protected final String TEXT_12 = " = new ";
  protected final String TEXT_13 = "_";
  protected final String TEXT_14 = "_In_i(\"";
  protected final String TEXT_15 = "\", this);" + NL + "    registerInPort(tmp";
  protected final String TEXT_16 = ");" + NL;
  protected final String TEXT_17 = NL + "    //" + NL + "    // Uses (output) ports" + NL + "    //";
  protected final String TEXT_18 = NL + "    ";
  protected final String TEXT_19 = "_";
  protected final String TEXT_20 = "_Out_i* tmp";
  protected final String TEXT_21 = " = new ";
  protected final String TEXT_22 = "_";
  protected final String TEXT_23 = "_Out_i(\"";
  protected final String TEXT_24 = "\"";
  protected final String TEXT_25 = ", this";
  protected final String TEXT_26 = ");" + NL + "    registerOutPort(tmp";
  protected final String TEXT_27 = ", tmp";
  protected final String TEXT_28 = "->_this());";
  protected final String TEXT_29 = NL + "    // component_alive flag is turned to false to terminate the main processing thread" + NL + "    component_alive = true;" + NL + "    naming_service_name = label;" + NL + "    orb = in_orb;" + NL + "" + NL + "    // this is the signal used to tell the main procesing thread that information is ready" + NL + "    //  it is thrown by the input (provides) port when data is ready" + NL + "    data_in_signal = new omni_condition(&data_in_signal_lock);" + NL + "" + NL + "    //Initialize variables" + NL + "" + NL + "    thread_started = false;" + NL + "}" + NL;
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = "_i::~";
  protected final String TEXT_32 = "_i(void)" + NL + "{" + NL + "    // The function releaseObject in the Framework-level functions section of this file does the memory housekeeping" + NL + "    //    The reason why it was placed there instead of here is that CORBA doesn't like it when you clean up CORBA" + NL + "    //    stuff in the destructor. Also, the component's ports are separate threads that need to be cleaned up, and" + NL + "    //    that also belongs in the framework-level termination." + NL + "    usleep(1);" + NL + "}" + NL + "" + NL + "/*******************************************************************************************" + NL + "    Framework-level functions" + NL + "    These functions are generally called by the framework to perform housekeeping." + NL + "*******************************************************************************************/" + NL + "void ";
  protected final String TEXT_33 = "_i::initialize() throw (CF::LifeCycle::InitializeError, CORBA::SystemException)" + NL + "{" + NL + "    // This function is called by the framework during construction of the waveform" + NL + "    //    it is called before configure() is called, so whatever values you set in the xml properties file" + NL + "    //    won't be available when this is called. I wouldn't have done it in this order, but this" + NL + "    //    is what the specs call for" + NL + "    // TODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_34 = "_i::start() throw (CORBA::SystemException, CF::Resource::StartError)" + NL + "{" + NL + "    // This is a framework-level start call. This function is called only if this component" + NL + "    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "    //    it calls this component's start function" + NL + "    // The vast majority of components won't have their start function called" + NL + "    // TODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_35 = "_i::stop() throw (CORBA::SystemException, CF::Resource::StopError)" + NL + "{" + NL + "    // This is a framework-level stop call. This function is called only if this component" + NL + "    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "    //    it calls this component's start function" + NL + "    // The vast majority of components won't have their start function called" + NL + "    // TODO    " + NL + "}";
  protected final String TEXT_36 = NL + NL + "/*************************************************************************************************" + NL + "  Port service routines" + NL + "    " + NL + "  These are functions that have been added to the component to service port calls. The file port_impl.cpp" + NL + "  does all the annoying CORBA stuff." + NL + "    " + NL + "  In the case of input ports, the port in port_impl handles all the" + NL + "  receive CORBA stuff and calls the component's function with the same name. The function knows who the call is" + NL + "  from based on the input argument port_name, which is the framework-level port name." + NL + "" + NL + "  If port_name corresponds to an input port, then this function is where you would add your servicing code. Note" + NL + "  that each of these functions contains a process_data_lock mutex for the input port section. This is so that when" + NL + "  the control information is process, the component won't process any data (and that it will finish whatever processing" + NL + "  it's doing before handling the control call)" + NL + "" + NL + "  If port_name is an output port, then this call just makes a call to the appropriate output port (again, implemented" + NL + "  in port_impl.cpp), which will then handle the CORBA stuff for you. To use the output port, just call the function" + NL + "  and give it the name of the port that should be used to output the data." + NL + "  " + NL + "  These ports are where it would be a good place to set the data_in_signal that the main processing thread" + NL + "  uses to iterate." + NL + "*************************************************************************************************/";
  protected final String TEXT_37 = NL;
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = " ";
  protected final String TEXT_40 = "_i::";
  protected final String TEXT_41 = "(std::string port_name";
  protected final String TEXT_42 = ", ";
  protected final String TEXT_43 = ")";
  protected final String TEXT_44 = " ";
  protected final String TEXT_45 = ", unsigned long length";
  protected final String TEXT_46 = " ";
  protected final String TEXT_47 = ")";
  protected final String TEXT_48 = ", ";
  protected final String TEXT_49 = NL + "{";
  protected final String TEXT_50 = NL + "    ";
  protected final String TEXT_51 = " retval = ";
  protected final String TEXT_52 = ";" + NL + "    ";
  protected final String TEXT_53 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());";
  protected final String TEXT_54 = NL + NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "    if (output_port)" + NL + "    {" + NL + "        ";
  protected final String TEXT_55 = NL + "        retval = ";
  protected final String TEXT_56 = "((";
  protected final String TEXT_57 = "_";
  protected final String TEXT_58 = "_Out_i *)p_out->second)->";
  protected final String TEXT_59 = "(";
  protected final String TEXT_60 = ", ";
  protected final String TEXT_61 = "); // send out the command" + NL + "    }";
  protected final String TEXT_62 = NL + "    else if (input_port)" + NL + "    {";
  protected final String TEXT_63 = NL + NL + "    if (input_port)" + NL + "    {";
  protected final String TEXT_64 = NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        ";
  protected final String TEXT_65 = NL + "        // MAKE SURE TO DELETE THIS BUFFER WHEN DONE!!!!!" + NL + "        delete data;";
  protected final String TEXT_66 = NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    " + NL + "    return";
  protected final String TEXT_67 = " retval";
  protected final String TEXT_68 = ";" + NL + "}";
  protected final String TEXT_69 = NL;
  protected final String TEXT_70 = NL;
  protected final String TEXT_71 = " ";
  protected final String TEXT_72 = "_i::";
  protected final String TEXT_73 = "(std::string port_name";
  protected final String TEXT_74 = ", ";
  protected final String TEXT_75 = ")";
  protected final String TEXT_76 = " ";
  protected final String TEXT_77 = ")";
  protected final String TEXT_78 = ", ";
  protected final String TEXT_79 = NL + "{";
  protected final String TEXT_80 = NL + "    ";
  protected final String TEXT_81 = " retval = ";
  protected final String TEXT_82 = ";" + NL + "    ";
  protected final String TEXT_83 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "    if (output_port)" + NL + "    {" + NL + "        ";
  protected final String TEXT_84 = NL + "        retval = ";
  protected final String TEXT_85 = "((";
  protected final String TEXT_86 = "_";
  protected final String TEXT_87 = "_Out_i *)p_out->second)->";
  protected final String TEXT_88 = "(";
  protected final String TEXT_89 = ", ";
  protected final String TEXT_90 = "); // send out the command" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_91 = " retval";
  protected final String TEXT_92 = ";" + NL + "}";
  protected final String TEXT_93 = NL;
  protected final String TEXT_94 = NL;
  protected final String TEXT_95 = " ";
  protected final String TEXT_96 = "_i::";
  protected final String TEXT_97 = "(std::string port_name";
  protected final String TEXT_98 = ", ";
  protected final String TEXT_99 = ")";
  protected final String TEXT_100 = " ";
  protected final String TEXT_101 = ", unsigned long length";
  protected final String TEXT_102 = " ";
  protected final String TEXT_103 = ")";
  protected final String TEXT_104 = ", ";
  protected final String TEXT_105 = NL + "{";
  protected final String TEXT_106 = NL + "    ";
  protected final String TEXT_107 = " retval = ";
  protected final String TEXT_108 = ";" + NL;
  protected final String TEXT_109 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "" + NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL;
  protected final String TEXT_110 = NL + "        // MAKE SURE TO DELETE THIS BUFFER WHEN DONE!!!!!" + NL + "        delete data;";
  protected final String TEXT_111 = NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_112 = " retval";
  protected final String TEXT_113 = ";" + NL + "" + NL + "}";
  protected final String TEXT_114 = NL + NL + "void ";
  protected final String TEXT_115 = "_i::pushSRI(std::string port_name, const BULKIO::StreamSRI& H)" + NL + "{";
  protected final String TEXT_116 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "    ";
  protected final String TEXT_117 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "    ";
  protected final String TEXT_118 = NL + "    if (output_port)" + NL + "    {";
  protected final String TEXT_119 = NL + "        if (!strcmp(port_name.c_str(), \"";
  protected final String TEXT_120 = "\")) {" + NL + "            ((";
  protected final String TEXT_121 = "_";
  protected final String TEXT_122 = "_Out_i*)p_out->second)->pushSRI(H); // send out the command" + NL + "        }";
  protected final String TEXT_123 = NL + "    }" + NL + "    ";
  protected final String TEXT_124 = NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    ";
  protected final String TEXT_125 = NL + "    return;" + NL + "}";
  protected final String TEXT_126 = NL + NL + "void ";
  protected final String TEXT_127 = "_i::pushSRI(std::string port_name, const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T)" + NL + "{";
  protected final String TEXT_128 = NL + "    std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "    p_out = outPorts.find(port_name);" + NL + "    bool output_port = (p_out != outPorts.end());" + NL + "    ";
  protected final String TEXT_129 = NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in;" + NL + "    p_in = inPorts.find(port_name);" + NL + "    bool input_port = (p_in != inPorts.end());" + NL + "    ";
  protected final String TEXT_130 = NL + "    if (output_port)" + NL + "    {";
  protected final String TEXT_131 = NL + "        if (!strcmp(port_name.c_str(), \"";
  protected final String TEXT_132 = "\")) {" + NL + "            ((";
  protected final String TEXT_133 = "_";
  protected final String TEXT_134 = "_Out_i*)p_out->second)->pushSRI(H, T); // send out the command" + NL + "        }";
  protected final String TEXT_135 = NL + "    }" + NL + "    ";
  protected final String TEXT_136 = NL + "    if (input_port) {" + NL + "        process_data_lock.lock();    // don't want to process while command information is coming in" + NL + "        // Process the input command here" + NL + "        // TODO" + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    ";
  protected final String TEXT_137 = NL + "    return;" + NL + "}";
  protected final String TEXT_138 = NL + NL + "/**************************************************************************************" + NL + "    Main processing thread" + NL + "    " + NL + "    General functionality:" + NL + "    This function is running as a separate thread from the component's main thread. The function" + NL + "    is generally in a blocked state, where it is waiting for the data_in_signal to be set." + NL + "    " + NL + "    data_in_signal will only be set automatically when the component is exiting, otherwise" + NL + "    you should set it from either the input port or some other condition." + NL + "**************************************************************************************/" + NL + "" + NL + "void ";
  protected final String TEXT_139 = "_i::run(void *args)" + NL + "{" + NL + "" + NL + "    while(component_alive)" + NL + "    {" + NL + "        // Wait for signal that data is ready (or signal to exit service loop)" + NL + "        data_in_signal->wait();" + NL + "        " + NL + "        // Lock the mutex to prevent control information from changing the operating parameters while a block of" + NL + "        //    data is being processed" + NL + "        process_data_lock.lock();" + NL + "        " + NL + "        // If the component was released (framework-level end) while this was locked, then exit loop" + NL + "        if (!component_alive) {" + NL + "            process_data_lock.unlock();" + NL + "            continue;" + NL + "        }" + NL + "        " + NL + "        // Process data here" + NL + "        // TODO" + NL + "        " + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    thread_exit_lock.lock();    // this is necessary to make sure releaseObject is called before the component is destroyed" + NL + "}";
  protected final String TEXT_140 = NL;

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
    
    int port_count = 0;
    
    if (provides.size() > 0) {

    stringBuffer.append(TEXT_8);
    
        for (Provides pro : provides) {
            String intName = pro.getRepID();
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            if (iface == null) {
                throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_16);
    
            port_count = port_count + 1; 
        } // end for provides
    } // end if provides
    
    if (uses.size() > 0) { 

    stringBuffer.append(TEXT_17);
    
        for (Uses use : uses) {
            String intName = use.getRepID();
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            if (iface == null) {
                throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_18);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_24);
    if ("dataSDDS".equals(interfaceName)) {
    stringBuffer.append(TEXT_25);
    }
    stringBuffer.append(TEXT_26);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(port_count);
    stringBuffer.append(TEXT_28);
    
            port_count = port_count + 1;
        } // end for uses
    } // end if uses

    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_35);
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_36);
    
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

    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_39);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_41);
    if (numParams != 0) {
    stringBuffer.append(TEXT_42);
    } else {
    stringBuffer.append(TEXT_43);
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
    stringBuffer.append(TEXT_44);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_45);
    
                        } else {
                            
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
                            
    stringBuffer.append(TEXT_46);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        }
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_47);
    
                        } else {
                            
    stringBuffer.append(TEXT_48);
    
                        }
                    } // end for params

    stringBuffer.append(TEXT_49);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_51);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_52);
    
                    }

                    if (provides.size() > 0) {

    stringBuffer.append(TEXT_53);
    
                    }
                    if (!pushPacket) {

    
                        if (uses.size() > 0) {

    stringBuffer.append(TEXT_54);
    
                            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_55);
             }
    stringBuffer.append(TEXT_56);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_59);
    
                            for (int i = 0; i < numParams; i++) {
                                
    stringBuffer.append(op.getParams().get(i).getName());
    
                                if (i != (numParams - 1)) {
                                    
    stringBuffer.append(TEXT_60);
    
                                }
                            }
        
    stringBuffer.append(TEXT_61);
    
                        }

    stringBuffer.append(TEXT_62);
    
                    } else {

    stringBuffer.append(TEXT_63);
    
                    }

    stringBuffer.append(TEXT_64);
    
                    if (!memcpyBuffer && pushPacket) {

    stringBuffer.append(TEXT_65);
    
                    }

    stringBuffer.append(TEXT_66);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_67);
    }
    stringBuffer.append(TEXT_68);
    
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

    stringBuffer.append(TEXT_69);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_71);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_73);
    if (numParams != 0) {
    stringBuffer.append(TEXT_74);
    } else {
    stringBuffer.append(TEXT_75);
    }
                    for (int i = 0; i < numParams; i++) {
                        
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
                        
    stringBuffer.append(TEXT_76);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_77);
    
                        } else {
                            
    stringBuffer.append(TEXT_78);
    
                        }
                    }

    stringBuffer.append(TEXT_79);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_81);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_82);
    
                    }

    stringBuffer.append(TEXT_83);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_84);
     }
    stringBuffer.append(TEXT_85);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_88);
    
                    for (int i = 0; i < numParams; i++) {
                        
    stringBuffer.append(op.getParams().get(i).getName());
    
                        if (i != (numParams - 1)) {
                            
    stringBuffer.append(TEXT_89);
    
                        }
                    }
        
    stringBuffer.append(TEXT_90);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_91);
    }
    stringBuffer.append(TEXT_92);
    
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

    stringBuffer.append(TEXT_93);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_95);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_97);
    if (numParams != 0) {
    stringBuffer.append(TEXT_98);
    } else {
    stringBuffer.append(TEXT_99);
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
    stringBuffer.append(TEXT_100);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_101);
    
                        } else {
                        
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_102);
    stringBuffer.append(op.getParams().get(i).getName());
    
                        }
                        if (i == (numParams - 1)) {
                            
    stringBuffer.append(TEXT_103);
    
                        } else {
                            
    stringBuffer.append(TEXT_104);
    
                        }
                    }

    stringBuffer.append(TEXT_105);
    
                    if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_106);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_107);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_108);
    
                    }

    stringBuffer.append(TEXT_109);
    
                    if (!memcpyBuffer && pushPacket) {

    stringBuffer.append(TEXT_110);
    
                    }

    stringBuffer.append(TEXT_111);
    if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_112);
    }
    stringBuffer.append(TEXT_113);
    
                } // end for operations
            } // end if interfaces
        } // end for providesList
    
        if (writePushSRI_uses || writePushSRI_provides) {

    stringBuffer.append(TEXT_114);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_115);
    
            if (writePushSRI_uses) {

    stringBuffer.append(TEXT_116);
    
            }
            if (writePushSRI_provides) {

    stringBuffer.append(TEXT_117);
    
            }
            if (writePushSRI_uses) {

    stringBuffer.append(TEXT_118);
    
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

    stringBuffer.append(TEXT_119);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_120);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_122);
    
                            break;
                        } // end if pushSRI
                    } // end for op
                } //end for use 

    stringBuffer.append(TEXT_123);
    
            } // end if uses sri
            if (writePushSRI_provides) { 

    stringBuffer.append(TEXT_124);
    
            } 

    stringBuffer.append(TEXT_125);
    
        } // end if writePushSRI
        if (writeSDDSPushSRI_uses || writeSDDSPushSRI_provides) {

    stringBuffer.append(TEXT_126);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_127);
    
            if (writeSDDSPushSRI_uses) {

    stringBuffer.append(TEXT_128);
    
            }
            if (writeSDDSPushSRI_provides) {

    stringBuffer.append(TEXT_129);
    
            }
            if (writeSDDSPushSRI_uses) {

    stringBuffer.append(TEXT_130);
    
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

    stringBuffer.append(TEXT_131);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_132);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_134);
    
                            break;
                        } // end if pushSRI
                    } // end for op
                } //end for use 

    stringBuffer.append(TEXT_135);
    
            } // end if uses sri
            if (writeSDDSPushSRI_provides) { 

    stringBuffer.append(TEXT_136);
    
            } 

    stringBuffer.append(TEXT_137);
    
        } // end if writeSDDSPushSRI
    
    } // end if has ports

    stringBuffer.append(TEXT_138);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(TEXT_140);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE