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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.idl.Attribute;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class PullPortImplCppUsesTemplate
{

  protected static String nl;
  public static synchronized PullPortImplCppUsesTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullPortImplCppUsesTemplate result = new PullPortImplCppUsesTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "PREPARE_ALT_LOGGING(";
  protected final String TEXT_2 = "_";
  protected final String TEXT_3 = "_Out_i,";
  protected final String TEXT_4 = "_i)";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_7 = "_";
  protected final String TEXT_8 = "_Out_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_9 = NL + "BULKIO_dataSDDS_Out_i::BULKIO_dataSDDS_Out_i(std::string port_name, ";
  protected final String TEXT_10 = "_base *_parent) : Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    parent = static_cast<";
  protected final String TEXT_11 = "_i *> (_parent);" + NL + "    lastStreamData = NULL;" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL + "" + NL + "BULKIO_dataSDDS_Out_i::~BULKIO_dataSDDS_Out_i()" + NL + "{" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamDefinition* BULKIO_dataSDDS_Out_i::getStreamDefinition(const char* attachId)" + NL + "{" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        if (strcmp((*groupIter).first.c_str(), attachId) == 0) {" + NL + "            return (*groupIter).second.first;" + NL + "        }" + NL + "        groupIter++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_Out_i::getUser(const char* attachId)" + NL + "{" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        if (strcmp((*groupIter).first.c_str(), attachId) == 0) {" + NL + "            return CORBA::string_dup((*groupIter).second.second.c_str());" + NL + "        }" + NL + "        groupIter++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "BULKIO::dataSDDS::InputUsageState BULKIO_dataSDDS_Out_i::usageState()" + NL + "{" + NL + "    if (attachedGroup.size() == 0) {" + NL + "        return BULKIO::dataSDDS::IDLE;" + NL + "    } else if (attachedGroup.size() == 1) {" + NL + "        return BULKIO::dataSDDS::BUSY;" + NL + "    } else {" + NL + "        return BULKIO::dataSDDS::ACTIVE;" + NL + "    }" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamSequence* BULKIO_dataSDDS_Out_i::attachedStreams()" + NL + "{" + NL + "    BULKIO::SDDSStreamSequence* seq = new BULKIO::SDDSStreamSequence();" + NL + "    seq->length(1);" + NL + "    (*seq)[0] = *lastStreamData;" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "BULKIO::StringSequence* BULKIO_dataSDDS_Out_i::attachmentIds()" + NL + "{" + NL + "    BULKIO::StringSequence* seq = new BULKIO::StringSequence();" + NL + "    seq->length(attachedGroup.size());" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "    unsigned int i = 0;" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        (*seq)[i++] = CORBA::string_dup((*groupIter).first.c_str());" + NL + "        groupIter++;" + NL + "    }" + NL + "" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_Out_i::attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid) throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "    std::string attachId;" + NL + "    user_id = userid;" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string>::iterator portIter;" + NL + "    BULKIO::dataSDDS::_var_type port = NULL;" + NL + "    lastStreamData = new BULKIO::SDDSStreamDefinition(stream);" + NL + "    portIter = attachedPorts.begin();" + NL + "    while (portIter != attachedPorts.end()) {" + NL + "        port = (*portIter).first;" + NL + "        port->detach(attachedPorts[port].c_str());" + NL + "        attachedGroup.erase((*portIter).second);" + NL + "        portIter++;" + NL + "    }" + NL + "    std::vector< std::pair<BULKIO::dataSDDS::_var_type, std::string> >::iterator portIter2 = outConnections.begin();" + NL + "    while (portIter2 != outConnections.end()) {" + NL + "        port = (*portIter2).first;" + NL + "        attachId = port->attach(stream, user_id.c_str());" + NL + "        attachedGroup.insert(std::make_pair(attachId, std::make_pair(lastStreamData, user_id)));" + NL + "        attachedPorts[port] = attachId;" + NL + "        portIter2++;" + NL + "    }" + NL + "    return CORBA::string_dup(attachId.c_str());" + NL + "}" + NL + "" + NL + "void BULKIO_dataSDDS_Out_i::detach(const char* attachId, const char* connectionId)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "    std::vector< std::pair<BULKIO::dataSDDS::_var_type, std::string> >::iterator portIter = outConnections.begin();" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string>::iterator portIter2;" + NL + "    while (portIter != outConnections.end()) {" + NL + "        portIter2 = attachedPorts.begin();" + NL + "        if (!strcmp(connectionId, (*portIter).second.c_str())) {" + NL + "        \twhile (portIter2 != attachedPorts.end()) {" + NL + "        \t\tif ((*portIter2).first == (*portIter).first) {" + NL + "        \t\t\t(*(*portIter).first).detach(attachedPorts[(*portIter).first].c_str());" + NL + "        \t\t\treturn;" + NL + "        \t\t}" + NL + "        \t\tportIter2++;" + NL + "        \t}" + NL + "        }" + NL + "        portIter++;" + NL + "    }" + NL + "}" + NL + "" + NL + "/*" + NL + " * pushSRI" + NL + " *     description: send out SRI describing the data payload" + NL + " *" + NL + " *  H: structure of type BULKIO::StreamSRI with the SRI for this stream" + NL + " *    hversion" + NL + " *    xstart: start time of the stream" + NL + " *    xdelta: delta between two samples" + NL + " *    xunits: unit types from Platinum specification" + NL + " *    subsize: 0 if the data is one-dimensional" + NL + " *    ystart" + NL + " *    ydelta" + NL + " *    yunits: unit types from Platinum specification" + NL + " *    mode: 0-scalar, 1-complex" + NL + " *    streamID: stream identifier" + NL + " *    sequence<CF::DataType> keywords: unconstrained sequence of key-value pairs for additional description" + NL + " *" + NL + " *  T: structure of type BULKIO::PrecisionUTCTime with the Time for this stream" + NL + " *    tcmode: timecode mode" + NL + " *    tcstatus: timecode status" + NL + " *    toff: Fractional sample offset" + NL + " *    twsec" + NL + " *    tfsec" + NL + " */" + NL + "void BULKIO_dataSDDS_Out_i::pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T)" + NL + "{" + NL + "    std::vector < std::pair < BULKIO::dataSDDS_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "" + NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ((*i).first)->pushSRI(H, T);" + NL + "            } catch(...) {" + NL + "                LOG_ERROR(BULKIO_dataSDDS_Out_i,\"Call to pushSRI by BULKIO_dataSDDS_Out_i failed\");" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    currentSRIs[std::string(H.streamID)] = std::make_pair(H, T);" + NL + "    refreshSRI = false;" + NL + "" + NL + "    return;" + NL + "}" + NL + NL;
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "_";
  protected final String TEXT_14 = "_Out_i::";
  protected final String TEXT_15 = "_";
  protected final String TEXT_16 = "_Out_i(std::string port_name, ";
  protected final String TEXT_17 = "_base *_parent) :" + NL + "Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    parent = static_cast<";
  protected final String TEXT_18 = "_i *> (_parent);" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "_";
  protected final String TEXT_21 = "_Out_i::~";
  protected final String TEXT_22 = "_";
  protected final String TEXT_23 = "_Out_i()" + NL + "{" + NL + "}" + NL + "" + NL + "/*" + NL + " * pushSRI" + NL + " *     description: send out SRI describing the data payload" + NL + " *" + NL + " *  H: structure of type BULKIO::StreamSRI with the SRI for this stream" + NL + " *    hversion" + NL + " *    xstart: start time of the stream" + NL + " *    xdelta: delta between two samples" + NL + " *    xunits: unit types from Platinum specification" + NL + " *    subsize: 0 if the data is one-dimensional" + NL + " *    ystart" + NL + " *    ydelta" + NL + " *    yunits: unit types from Platinum specification" + NL + " *    mode: 0-scalar, 1-complex" + NL + " *    streamID: stream identifier" + NL + " *    sequence<CF::DataType> keywords: unconstrained sequence of key-value pairs for additional description" + NL + " */" + NL + "void ";
  protected final String TEXT_24 = "_";
  protected final String TEXT_25 = "_Out_i::pushSRI(const BULKIO::StreamSRI& H)" + NL + "{" + NL + "    std::vector < std::pair < ";
  protected final String TEXT_26 = "::";
  protected final String TEXT_27 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "" + NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ((*i).first)->pushSRI(H);" + NL + "            } catch(...) {" + NL + "                LOG_ERROR(";
  protected final String TEXT_28 = "_";
  protected final String TEXT_29 = "_Out_i, \"Call to pushSRI by ";
  protected final String TEXT_30 = "_";
  protected final String TEXT_31 = "_Out_i failed\");" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    currentSRIs[std::string(H.streamID)] = H;" + NL + "    refreshSRI = false;" + NL + "" + NL + "    return;" + NL + "}" + NL + NL;
  protected final String TEXT_32 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_33 = "_";
  protected final String TEXT_34 = "_Out_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = "_";
  protected final String TEXT_37 = "_Out_i::";
  protected final String TEXT_38 = "_";
  protected final String TEXT_39 = "_Out_i(std::string port_name, ";
  protected final String TEXT_40 = "_base *_parent) :" + NL + "Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    parent = static_cast<";
  protected final String TEXT_41 = "_i *> (_parent);" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL;
  protected final String TEXT_42 = NL;
  protected final String TEXT_43 = "_";
  protected final String TEXT_44 = "_Out_i::~";
  protected final String TEXT_45 = "_";
  protected final String TEXT_46 = "_Out_i()" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_47 = NL;
  protected final String TEXT_48 = NL;
  protected final String TEXT_49 = " ";
  protected final String TEXT_50 = "_";
  protected final String TEXT_51 = "_Out_i::";
  protected final String TEXT_52 = "(";
  protected final String TEXT_53 = ")";
  protected final String TEXT_54 = " ";
  protected final String TEXT_55 = ")";
  protected final String TEXT_56 = ", ";
  protected final String TEXT_57 = NL + "{";
  protected final String TEXT_58 = NL + "    ";
  protected final String TEXT_59 = " retval";
  protected final String TEXT_60 = " = ";
  protected final String TEXT_61 = ";";
  protected final String TEXT_62 = NL + "    std::vector < std::pair < ";
  protected final String TEXT_63 = "::";
  protected final String TEXT_64 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL;
  protected final String TEXT_65 = NL + "    Sequence_";
  protected final String TEXT_66 = "_";
  protected final String TEXT_67 = ".length(";
  protected final String TEXT_68 = ".size());" + NL + "    memcpy(&Sequence_";
  protected final String TEXT_69 = "_";
  protected final String TEXT_70 = "[0], &";
  protected final String TEXT_71 = "[0], ";
  protected final String TEXT_72 = ".size() * sizeof(";
  protected final String TEXT_73 = "[0]));" + NL;
  protected final String TEXT_74 = NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ";
  protected final String TEXT_75 = "retval =";
  protected final String TEXT_76 = "((*i).first)->";
  protected final String TEXT_77 = "(";
  protected final String TEXT_78 = ");";
  protected final String TEXT_79 = "Sequence_";
  protected final String TEXT_80 = "_";
  protected final String TEXT_81 = ");";
  protected final String TEXT_82 = ", ";
  protected final String TEXT_83 = NL + "            } catch(...) {" + NL + "                LOG_ERROR(";
  protected final String TEXT_84 = "_";
  protected final String TEXT_85 = "_Out_i, \"Call to ";
  protected final String TEXT_86 = " by ";
  protected final String TEXT_87 = "_";
  protected final String TEXT_88 = "_Out_i failed\");" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_89 = " retval";
  protected final String TEXT_90 = ";" + NL + "}";
  protected final String TEXT_91 = NL;
  protected final String TEXT_92 = " ";
  protected final String TEXT_93 = "_";
  protected final String TEXT_94 = "_Out_i::";
  protected final String TEXT_95 = "()" + NL + "{";
  protected final String TEXT_96 = NL + "    ";
  protected final String TEXT_97 = " retval";
  protected final String TEXT_98 = " = ";
  protected final String TEXT_99 = ";";
  protected final String TEXT_100 = NL + "    std::vector < std::pair < ";
  protected final String TEXT_101 = "::";
  protected final String TEXT_102 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "    " + NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                retval = ((*i).first)->";
  protected final String TEXT_103 = "();" + NL + "            } catch(...) {" + NL + "                LOG_ERROR(";
  protected final String TEXT_104 = "_";
  protected final String TEXT_105 = "_Out_i, \"Call to ";
  protected final String TEXT_106 = " by ";
  protected final String TEXT_107 = "_";
  protected final String TEXT_108 = "_Out_i failed\");" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    return";
  protected final String TEXT_109 = " retval";
  protected final String TEXT_110 = ";" + NL + "}" + NL;
  protected final String TEXT_111 = NL + "void ";
  protected final String TEXT_112 = "_";
  protected final String TEXT_113 = "_Out_i::";
  protected final String TEXT_114 = "(";
  protected final String TEXT_115 = " data)" + NL + "{" + NL + "    std::vector < std::pair < ";
  protected final String TEXT_116 = "::";
  protected final String TEXT_117 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "    " + NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ((*i).first)->";
  protected final String TEXT_118 = "(data);" + NL + "            } catch(...) {" + NL + "                LOG_ERROR(";
  protected final String TEXT_119 = "_";
  protected final String TEXT_120 = "_Out_i, \"Call to ";
  protected final String TEXT_121 = " by ";
  protected final String TEXT_122 = "_";
  protected final String TEXT_123 = "_Out_i failed\");" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    return;" + NL + "}" + NL;
  protected final String TEXT_124 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    Implementation impl = templ.getImpl();
    ImplementationSettings implSettings = templ.getImplSettings();
    SoftPkg softpkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softpkg, implSettings);
    EList<Uses> uses = softpkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    Uses use = null;
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    CppHelper _cppHelper = new CppHelper();
    for (Uses entry : uses) {
        String intName = entry.getRepID();
        if (intName.equals(templ.getPortRepId())) {
            use = entry;
            break;
        }
    }

    if (use != null && templ.isGenClassImpl() && (!use.getRepID().equals("IDL:ExtendedEvent/MessageEvent:1.0"))) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
        if (iface != null) {
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_4);
    
            if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_8);
    
            if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    
            } else {

    stringBuffer.append(TEXT_12);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_31);
    
            } // end else (if dataSDDS)

    
            } else {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_46);
    
            for (Operation op : iface.getOperations()) {
                int numParams = op.getParams().size();
                int numVector = 0;
                ArrayList<String> vectorList = new ArrayList<String>();

    stringBuffer.append(TEXT_47);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_49);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_52);
    
                if (numParams == 0) { 
    stringBuffer.append(TEXT_53);
    
                }
                for (int i = 0; i < numParams; i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                    if (iteratorBase.length() > 11) {
                        if (iteratorBase.substring(0, 11).equals("std::vector")) {
                            numVector++;
                            vectorList.add(op.getParams().get(i).getName());
                        }
                    }
                
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
                
    stringBuffer.append(TEXT_54);
    stringBuffer.append(op.getParams().get(i).getName());
    
                    if (i == (numParams - 1)) {
                        
    stringBuffer.append(TEXT_55);
    
                    } else {
                        
    stringBuffer.append(TEXT_56);
    
                    }
                } // end for params

    stringBuffer.append(TEXT_57);
    
                if (!"void".equals(op.getCxxReturnType())) {
                    String initialValue = _cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength());

    stringBuffer.append(TEXT_58);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_59);
    if (!"".equals(initialValue)) {
    stringBuffer.append(TEXT_60);
    stringBuffer.append(initialValue);
    }
    stringBuffer.append(TEXT_61);
    
                }

    stringBuffer.append(TEXT_62);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_64);
    
                    for (int i = 0; i < numVector; i++) {

    stringBuffer.append(TEXT_65);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_66);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_68);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_69);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_71);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_72);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_73);
    
                    }

    stringBuffer.append(TEXT_74);
      if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_75);
    } else {}
    stringBuffer.append(TEXT_76);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_77);
    if (numParams == 0) {
    stringBuffer.append(TEXT_78);
    
                }
                for (int j = 0; j < numParams; j++) {
                    String paramName = op.getParams().get(j).getName();
                    boolean vectorParam = false;
                    for (int i = 0; i < numVector; i++) {
                        if (paramName.equals(vectorList.get(i))) {
                    
    stringBuffer.append(TEXT_79);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_80);
    stringBuffer.append(i);
    
                            vectorParam = true;
                            break;
                        }
                    }
                    if (!vectorParam) {
                
    stringBuffer.append(paramName);
    
                    }
                    if (j == (numParams - 1)) {
                        
    stringBuffer.append(TEXT_81);
    
                    } else {
                        
    stringBuffer.append(TEXT_82);
    
                    }
                } // end for params

    stringBuffer.append(TEXT_83);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_86);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_88);
     if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_89);
    }
    stringBuffer.append(TEXT_90);
    
            } // end for Operations

            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_91);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_92);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_95);
    
                if (!"void".equals(op.getCxxReturnType())) {
                    String initialValue = _cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength());

    stringBuffer.append(TEXT_96);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_97);
    if (!"".equals(initialValue)) {
    stringBuffer.append(TEXT_98);
    stringBuffer.append(initialValue);
    }
    stringBuffer.append(TEXT_99);
    
                }

    stringBuffer.append(TEXT_100);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_103);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_106);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_108);
     if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_109);
    }
    stringBuffer.append(TEXT_110);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_111);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_114);
    stringBuffer.append(_cppHelper.getCppMapping(op.getCxxType()));
    stringBuffer.append(TEXT_115);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_118);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_121);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_123);
    
                } // end if readonly
            } // end for attributes

            }
        // end if interfaces
        } else {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + use.getRepID()));
        }

    } 

    stringBuffer.append(TEXT_124);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE