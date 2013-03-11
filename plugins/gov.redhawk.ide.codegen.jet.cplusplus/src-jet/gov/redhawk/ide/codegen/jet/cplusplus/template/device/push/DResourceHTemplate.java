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
import java.util.HashSet;
import java.util.List;
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
  protected final String TEXT_8 = "Device_impl.h\"" + NL + "#include \"ossie/ossieSupport.h\"" + NL + "" + NL + "#include <sys/time.h>" + NL + "#include <queue>" + NL + "#include <fstream>" + NL + "" + NL + "class ";
  protected final String TEXT_9 = "_i;";
  protected final String TEXT_10 = NL + "#include \"port_impl.h\"" + NL + "#include <ossie/prop_helpers.h>";
  protected final String TEXT_11 = NL + "class ";
  protected final String TEXT_12 = "_i : ";
  protected final String TEXT_13 = "public virtual POA_CF::AggregateExecutableDevice, ";
  protected final String TEXT_14 = "public virtual POA_CF::AggregateLoadableDevice, ";
  protected final String TEXT_15 = "public virtual POA_CF::AggregatePlainDevice, ";
  protected final String TEXT_16 = "public ExecutableDevice_impl ";
  protected final String TEXT_17 = "public LoadableDevice_impl ";
  protected final String TEXT_18 = "public Device_impl ";
  protected final String TEXT_19 = " , public AggregateDevice_impl ";
  protected final String TEXT_20 = NL + "{";
  protected final String TEXT_21 = NL + "    friend class ";
  protected final String TEXT_22 = "_";
  protected final String TEXT_23 = "_In_i;";
  protected final String TEXT_24 = NL + "    friend class ";
  protected final String TEXT_25 = "_";
  protected final String TEXT_26 = "_Out_i;";
  protected final String TEXT_27 = NL + "    public:";
  protected final String TEXT_28 = NL + "        ";
  protected final String TEXT_29 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);";
  protected final String TEXT_30 = NL + "        ";
  protected final String TEXT_31 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities);";
  protected final String TEXT_32 = NL + "        ";
  protected final String TEXT_33 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compositeDev);";
  protected final String TEXT_34 = NL + "        ";
  protected final String TEXT_35 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compositeDev);" + NL + "" + NL + "        void initResource(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);" + NL + "" + NL + "        ~";
  protected final String TEXT_36 = "_i(void);" + NL + "" + NL + "        CORBA::Boolean allocateCapacity (const CF::Properties & capacities)" + NL + "                throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState);" + NL + "        void deallocateCapacity (const CF::Properties & capacities)" + NL + "                throw (CORBA::SystemException, CF::Device::InvalidCapacity, CF::Device::InvalidState);" + NL;
  protected final String TEXT_37 = NL + "        CORBA::Object_ptr getPort(const char* _id) throw (CF::PortSupplier::UnknownPort, CORBA::SystemException);" + NL + "        bool compareSRI(BULKIO::StreamSRI &SRI_1, BULKIO::StreamSRI &SRI_2);" + NL;
  protected final String TEXT_38 = NL + "        void releaseObject() throw (CF::LifeCycle::ReleaseError, CORBA::SystemException);" + NL + "" + NL + "        // main omni_thread function" + NL + "        void loadProperties();" + NL;
  protected final String TEXT_39 = NL + "        // Operations for interface: ";
  protected final String TEXT_40 = NL + "        ";
  protected final String TEXT_41 = " ";
  protected final String TEXT_42 = "(std::string port_name";
  protected final String TEXT_43 = ", ";
  protected final String TEXT_44 = ");";
  protected final String TEXT_45 = " ";
  protected final String TEXT_46 = ", unsigned long length";
  protected final String TEXT_47 = " ";
  protected final String TEXT_48 = ");";
  protected final String TEXT_49 = ", ";
  protected final String TEXT_50 = NL + NL + "        void pushPacket(std::string port_name, ";
  protected final String TEXT_51 = " data, CORBA::ULong length, BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {";
  protected final String TEXT_52 = NL + NL + "        void pushPacket(std::string port_name, ";
  protected final String TEXT_53 = " URL, BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {";
  protected final String TEXT_54 = NL + "            std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "            p_out = outPorts.find(port_name);" + NL + "            bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "            if (output_port)" + NL + "            {";
  protected final String TEXT_55 = NL + "                ((";
  protected final String TEXT_56 = "_";
  protected final String TEXT_57 = "_Out_i*)p_out->second)->pushPacket(data, length, T, EOS, streamID); // send out the command";
  protected final String TEXT_58 = NL + "                ((";
  protected final String TEXT_59 = "_";
  protected final String TEXT_60 = "_Out_i*)p_out->second)->pushPacket(URL, T, EOS, streamID); // send out the command";
  protected final String TEXT_61 = NL + "            }" + NL + "" + NL + "            return;" + NL + "        }";
  protected final String TEXT_62 = NL;
  protected final String TEXT_63 = NL + "        // Operations for interface: ";
  protected final String TEXT_64 = NL + "        ";
  protected final String TEXT_65 = " ";
  protected final String TEXT_66 = "(std::string port_name";
  protected final String TEXT_67 = ", ";
  protected final String TEXT_68 = ");";
  protected final String TEXT_69 = " ";
  protected final String TEXT_70 = ");";
  protected final String TEXT_71 = ", ";
  protected final String TEXT_72 = NL + "        void pushPacket(std::string port_name, ";
  protected final String TEXT_73 = " data, CORBA::ULong length, BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {";
  protected final String TEXT_74 = NL + "        void pushPacket(std::string port_name, ";
  protected final String TEXT_75 = " URL, BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {";
  protected final String TEXT_76 = NL + "            std::map<std::string, Port_Uses_base_impl *>::iterator p_out;" + NL + "            p_out = outPorts.find(port_name);" + NL + "            bool output_port = (p_out != outPorts.end());" + NL + "" + NL + "            if (output_port)" + NL + "            {";
  protected final String TEXT_77 = NL + "                ((";
  protected final String TEXT_78 = "_";
  protected final String TEXT_79 = "_Out_i*)p_out->second)->pushPacket(data, length, T, EOS, streamID); // send out the command";
  protected final String TEXT_80 = NL + "                ((";
  protected final String TEXT_81 = "_";
  protected final String TEXT_82 = "_Out_i*)p_out->second)->pushPacket(URL, T, EOS, streamID); // send out the command";
  protected final String TEXT_83 = NL + "            }" + NL + "" + NL + "            return;" + NL + "        }";
  protected final String TEXT_84 = NL;
  protected final String TEXT_85 = NL + "        // Operations for interface: ";
  protected final String TEXT_86 = NL + "        ";
  protected final String TEXT_87 = " ";
  protected final String TEXT_88 = "(std::string port_name";
  protected final String TEXT_89 = ", ";
  protected final String TEXT_90 = ");";
  protected final String TEXT_91 = " ";
  protected final String TEXT_92 = ", unsigned long length";
  protected final String TEXT_93 = " ";
  protected final String TEXT_94 = ");";
  protected final String TEXT_95 = ", ";
  protected final String TEXT_96 = NL;
  protected final String TEXT_97 = NL + "        void pushSRI(std::string port_name, const BULKIO::StreamSRI& H);";
  protected final String TEXT_98 = NL + "        void pushSRI(std::string port_name, const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T);";
  protected final String TEXT_99 = NL + NL + "    private:" + NL + "        // For component shutdown" + NL + "        std::string comp_uuid;" + NL + "" + NL + "        bool component_alive;" + NL + "" + NL + "        std::string naming_service_name;" + NL + "" + NL + "        ossie::ORB *orb;" + NL + "" + NL + "        // Threading stuff" + NL + "        omni_condition *data_in_signal;" + NL + "        omni_mutex data_in_signal_lock;" + NL + "        omni_mutex process_data_lock;" + NL + "        omni_mutex thread_exit_lock;" + NL + "        omni_mutex attribute_access;    // used when modifying variables";
  protected final String TEXT_100 = NL + "        CF::DeviceSequence *devSeq;";
  protected final String TEXT_101 = NL + "        // Functional members" + NL + "        // Housekeeping and data management variables" + NL + "        bool thread_started;" + NL + "};" + NL + "#endif";
  protected final String TEXT_102 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
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
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_10);
    
    }

    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
     if (aggregateDevice) {
            if (execDevice) {
    stringBuffer.append(TEXT_13);
     } 
            else if (loadableDevice) {
    stringBuffer.append(TEXT_14);
     }
            else {
    stringBuffer.append(TEXT_15);
     }
       } 
     if (execDevice) {
    stringBuffer.append(TEXT_16);
     } 
     else if (loadableDevice) {
    stringBuffer.append(TEXT_17);
     } 
     else {
    stringBuffer.append(TEXT_18);
     } 
     if (aggregateDevice) { 
    stringBuffer.append(TEXT_19);
     } 
    stringBuffer.append(TEXT_20);
    
    CppHelper _cppHelper = new CppHelper();
    HashSet<String> usesList = new HashSet<String>();
    for (Uses entry : uses) {
        usesList.add(entry.getRepID());
    }
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        providesList.add(entry.getRepID());
    }

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_23);
    
    }

    for (String entry : usesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_26);
    
    }

    stringBuffer.append(TEXT_27);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_36);
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_37);
    
    }

    stringBuffer.append(TEXT_38);
    
    HashSet<String> commonList = new HashSet<String>();
    for (String entry : usesList) {
        if (providesList.contains(entry)) {
            commonList.add(entry);
            continue;
        }
    }

    providesList.removeAll(commonList);
    usesList.removeAll(commonList);
    
    boolean writePushSRI = false;
    boolean writeSDDSPushSRI = false;
    for (String entry : commonList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();

    stringBuffer.append(TEXT_39);
    stringBuffer.append(entry);
    
        boolean dataFile = "dataFile".equals(interfaceName);
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            boolean pushPacket = false;
            boolean writePushPacket_uses = false;
            if ("pushPacket".equals(op.getName())) {
                writePushPacket_uses = !"dataXML".equals(iface.getName());
                pushPacket = true;
            } else if ("pushSRI".equals(op.getName())) {
                writePushSRI |= !"dataSDDS".equals(interfaceName);
                writeSDDSPushSRI |= "dataSDDS".equals(interfaceName);
                continue;
            }

    stringBuffer.append(TEXT_40);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_41);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_42);
    
            if (numParams != 0) {

    stringBuffer.append(TEXT_43);
    
            } else {

    stringBuffer.append(TEXT_44);
    
            }
            for (int i = 0; i < numParams; i++) {
                if (pushPacket && (i == 0) && !memcpyBuffer) {
                    String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(i).getCxxType());
                    if (dataTransfer.startsWith("std::vector")) {
                        if (dataTransfer.endsWith("& ")) {
                            dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                        } else { 
                            dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                        }
                    } 

    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_46);
    
                } else {

    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_47);
    stringBuffer.append(op.getParams().get(i).getName());
    
                }
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_48);
    
                } else {

    stringBuffer.append(TEXT_49);
    
                }
            } // end for params

            if (pushPacket && writePushPacket_uses) {
                String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(0).getCxxType());
                if (dataTransfer.startsWith("std::vector")) {
                    if (dataTransfer.endsWith("& ")) {
                        dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                    } else { 
                        dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                    }
                }
                
                if (!dataFile) {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_51);
    
                } else {

    stringBuffer.append(TEXT_52);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_53);
    
                }

    stringBuffer.append(TEXT_54);
    
                    if (!dataFile) {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_57);
    
                    } else {

    stringBuffer.append(TEXT_58);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_60);
    
                    }

    stringBuffer.append(TEXT_61);
    
            } // end if pushPacket
        } // end for Operations

    stringBuffer.append(TEXT_62);
    
    } // end for commonList
    
    for (String entry : usesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();

    stringBuffer.append(TEXT_63);
    stringBuffer.append(entry);
    
        boolean dataFile = "dataFile".equals(interfaceName);
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            boolean writePushPacket_uses = false;
            if ("pushPacket".equals(op.getName())) {
                writePushPacket_uses = !"dataXML".equals(iface.getName());
            } else if ("pushSRI".equals(op.getName())) {
                writePushSRI |= !"dataSDDS".equals(interfaceName);
                writeSDDSPushSRI |= "dataSDDS".equals(interfaceName);
                continue;
            }
            
            if (!writePushPacket_uses) {

    stringBuffer.append(TEXT_64);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_65);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_66);
    
                if (numParams != 0) {

    stringBuffer.append(TEXT_67);
    
                } else {

    stringBuffer.append(TEXT_68);
    
                }
                for (int i = 0; i < numParams; i++) {

    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_69);
    stringBuffer.append(op.getParams().get(i).getName());
    
                    if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_70);
    
                    } else {

    stringBuffer.append(TEXT_71);
    
                    }
                }
            } else { 
                String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(0).getCxxType());
                if (dataTransfer.startsWith("std::vector")) {
                    if (dataTransfer.endsWith("& ")) {
                        dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                    } else { 
                        dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                    }
                }
                
                if (!dataFile) {

    stringBuffer.append(TEXT_72);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_73);
    
                } else {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_75);
    
                }

    stringBuffer.append(TEXT_76);
    
                if (!dataFile) {

    stringBuffer.append(TEXT_77);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_79);
    
                } else {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_82);
    
                }

    stringBuffer.append(TEXT_83);
    
            } // end if writePushPacket_uses
        } // end for Operations

    stringBuffer.append(TEXT_84);
    
    } // end for usesList

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }

    stringBuffer.append(TEXT_85);
    stringBuffer.append(entry);
    
        String interfaceName = iface.getName();    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            boolean pushPacket = false;
            if ("pushPacket".equals(op.getName())) {
                pushPacket = true;
            } else if ("pushSRI".equals(op.getName())) {
                writePushSRI |= !"dataSDDS".equals(interfaceName);
                writeSDDSPushSRI |= "dataSDDS".equals(interfaceName);
                continue;
            }

    stringBuffer.append(TEXT_86);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxReturnType()));
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_87);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_88);
    
            if (numParams != 0) {

    stringBuffer.append(TEXT_89);
    
            } else {

    stringBuffer.append(TEXT_90);
    
            }
            for (int i = 0; i < numParams; i++) {
                if (pushPacket && (i == 0) && !memcpyBuffer) {
                    String dataTransfer = _cppHelper.getCppMapping(op.getParams().get(i).getCxxType());
                    if (dataTransfer.startsWith("std::vector")) {
                        if (dataTransfer.endsWith("& ")) {
                            dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 3) + "*";
                        } else { 
                            dataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2) + "*";
                        }
                    } 

    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(op.getParams().get(i).getName());
    stringBuffer.append(TEXT_92);
    
                } else {

    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_93);
    stringBuffer.append(op.getParams().get(i).getName());
    
                }
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_94);
    
                } else {

    stringBuffer.append(TEXT_95);
    
                }
            } // end for params
        } // end for Operations

    stringBuffer.append(TEXT_96);
    
    } // end for providesList

    if (writePushSRI) {

    stringBuffer.append(TEXT_97);
    
    }
    if (writeSDDSPushSRI) {

    stringBuffer.append(TEXT_98);
    
    }

    stringBuffer.append(TEXT_99);
    
    if (aggregateDevice) {

    stringBuffer.append(TEXT_100);
    
    }

    stringBuffer.append(TEXT_101);
    stringBuffer.append(TEXT_102);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE