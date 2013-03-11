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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
public class DPortImplCppTemplate
{

  protected static String nl;
  public static synchronized DPortImplCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DPortImplCppTemplate result = new DPortImplCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/****************************************************************************" + NL + "" + NL + "" + NL + "    WARNING:" + NL + "        This is auto-generated code, and you should modify it only if you" + NL + "        know exactly what you're doing. All input and output functionality" + NL + "        should only be accessed through the main component file" + NL + "" + NL + "" + NL + "****************************************************************************/" + NL + "" + NL + "#include \"port_impl.h\"" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"";
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_5 = "_";
  protected final String TEXT_6 = "_Out_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_7 = NL + "BULKIO_dataSDDS_Out_i::BULKIO_dataSDDS_Out_i(std::string port_name, ";
  protected final String TEXT_8 = "_i *_parent) : Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    parent = _parent;" + NL + "    lastStreamData = NULL;" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL + "" + NL + "BULKIO_dataSDDS_Out_i::~BULKIO_dataSDDS_Out_i()" + NL + "{" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamDefinition* BULKIO_dataSDDS_Out_i::getStreamDefinition(const char* attachId)" + NL + "{" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        if (strcmp((*groupIter).first.c_str(), attachId) == 0) {" + NL + "            return (*groupIter).second.first;" + NL + "        }" + NL + "        groupIter++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_Out_i::getUser(const char* attachId)" + NL + "{" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        if (strcmp((*groupIter).first.c_str(), attachId) == 0) {" + NL + "            return CORBA::string_dup((*groupIter).second.second.c_str());" + NL + "        }" + NL + "        groupIter++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "BULKIO::dataSDDS::InputUsageState BULKIO_dataSDDS_Out_i::usageState()" + NL + "{" + NL + "    if (attachedGroup.size() == 0) {" + NL + "        return BULKIO::dataSDDS::IDLE;" + NL + "    } else if (attachedGroup.size() == 1) {" + NL + "        return BULKIO::dataSDDS::BUSY;" + NL + "    } else {" + NL + "        return BULKIO::dataSDDS::ACTIVE;" + NL + "    }" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamSequence* BULKIO_dataSDDS_Out_i::attachedStreams()" + NL + "{" + NL + "    BULKIO::SDDSStreamSequence* seq = new BULKIO::SDDSStreamSequence();" + NL + "    seq->length(1);" + NL + "    (*seq)[1] = *lastStreamData;" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "BULKIO::StringSequence* BULKIO_dataSDDS_Out_i::attachmentIds()" + NL + "{" + NL + "    BULKIO::StringSequence* seq = new BULKIO::StringSequence();" + NL + "    seq->length(attachedGroup.size());" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> >::iterator groupIter;" + NL + "    groupIter = attachedGroup.begin();" + NL + "    int i = 0;" + NL + "    while (groupIter != attachedGroup.end()) {" + NL + "        (*seq)[i++] = CORBA::string_dup((*groupIter).first.c_str());" + NL + "        groupIter++;" + NL + "    }" + NL + "" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_Out_i::attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid) throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError)" + NL + "{" + NL + "    std::string attachId;" + NL + "    user_id = userid;" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string>::iterator portIter;" + NL + "    BULKIO::dataSDDS::_var_type port = NULL;" + NL + "    lastStreamData = new BULKIO::SDDSStreamDefinition(stream);" + NL + "    portIter = attachedPorts.begin();" + NL + "    while (portIter != attachedPorts.end()) {" + NL + "        port = (*portIter).first;" + NL + "        port->detach(attachedPorts[port].c_str());" + NL + "        attachedGroup.erase((*portIter).second);" + NL + "        portIter++;" + NL + "    }" + NL + "    std::vector< std::pair<BULKIO::dataSDDS::_var_type, std::string> >::iterator portIter2 = outConnections.begin();" + NL + "    while (portIter2 != outConnections.end()) {" + NL + "        port = (*portIter2).first;" + NL + "        attachId = port->attach(stream, user_id.c_str());" + NL + "        attachedGroup.insert(std::make_pair(attachId, std::make_pair(lastStreamData, user_id)));" + NL + "        attachedPorts[port] = attachId;" + NL + "        portIter2++;" + NL + "    }" + NL + "    return CORBA::string_dup(attachId.c_str());" + NL + "}" + NL + "" + NL + "void BULKIO_dataSDDS_Out_i::detach(const char* attachId)" + NL + "{" + NL + "    std::vector< std::pair<BULKIO::dataSDDS::_var_type, std::string> >::iterator portIter = outConnections.begin();" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string>::iterator portIter2;" + NL + "    while (portIter != outConnections.end()) {" + NL + "        portIter2 = attachedPorts.begin();" + NL + "        while (portIter2 != attachedPorts.end()) {" + NL + "            if ((*portIter2).first == (*portIter).first) {" + NL + "                (*(*portIter).first).detach(attachedPorts[(*portIter).first].c_str());" + NL + "                return;" + NL + "            }" + NL + "            portIter2++;" + NL + "        }" + NL + "        portIter++;" + NL + "    }" + NL + "}" + NL + "void BULKIO_dataSDDS_Out_i::pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "" + NL + "    if (active) {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            try {" + NL + "                outConnections[i].first->pushSRI(H, T);" + NL + "            } catch(...) {" + NL + "                std::cout << \"Call to pushSRI by BULKIO_dataSDDS_Out_i failed\" << std::endl;" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    currentSRIs[std::string(H.streamID)] = std::make_pair(H, T);" + NL + "    refreshSRI = false;" + NL + "" + NL + "    return;" + NL + "}";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "_";
  protected final String TEXT_11 = "_Out_i::";
  protected final String TEXT_12 = "_";
  protected final String TEXT_13 = "_Out_i(std::string port_name) :" + NL + "Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL + NL;
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "_";
  protected final String TEXT_16 = "_Out_i::~";
  protected final String TEXT_17 = "_";
  protected final String TEXT_18 = "_Out_i()" + NL + "{" + NL + "}" + NL + "" + NL + "/*" + NL + " * pushSRI" + NL + " *     description: send out SRI describing the data payload" + NL + " *" + NL + " *  H: structure of type BULKIO::StreamSRI with the SRI for this stream" + NL + " *    hversion" + NL + " *    xstart" + NL + " *    xdelta" + NL + " *    xunits: unit types from Platinum specification" + NL + " *    subsize: 0 if the data is one-dimensional" + NL + " *    ystart" + NL + " *    ydelta" + NL + " *    yunits: unit types from Platinum specification" + NL + " *    mode: 0-scalar, 1-complex" + NL + " *    streamID: stream identifier" + NL + " *    sequence<CF::DataType> keywords: unconstrained sequence of key-value pairs for additional description" + NL + " */" + NL + "void ";
  protected final String TEXT_19 = "_";
  protected final String TEXT_20 = "_Out_i::pushSRI(const BULKIO::StreamSRI& H)" + NL + "{" + NL + "    std::vector < std::pair < ";
  protected final String TEXT_21 = "::";
  protected final String TEXT_22 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "" + NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ((*i).first)->pushSRI(H);" + NL + "            } catch(...) {" + NL + "                std::cout << \"Call to pushSRI by ";
  protected final String TEXT_23 = "_";
  protected final String TEXT_24 = "_Out_i failed\" << std::endl;" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    currentSRIs[std::string(H.streamID)] = H;" + NL + "    refreshSRI = false;" + NL + "" + NL + "    return;" + NL + "}" + NL + NL;
  protected final String TEXT_25 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_26 = "_";
  protected final String TEXT_27 = "_Out_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_28 = NL;
  protected final String TEXT_29 = "_";
  protected final String TEXT_30 = "_Out_i::";
  protected final String TEXT_31 = "_";
  protected final String TEXT_32 = "_Out_i(std::string port_name) : " + NL + "Port_Uses_base_impl(port_name)" + NL + "{" + NL + "    recConnectionsRefresh = false;" + NL + "    recConnections.length(0);" + NL + "}" + NL;
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = "_";
  protected final String TEXT_35 = "_Out_i::~";
  protected final String TEXT_36 = "_";
  protected final String TEXT_37 = "_Out_i()" + NL + "{" + NL + "}";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = NL;
  protected final String TEXT_40 = " ";
  protected final String TEXT_41 = "_";
  protected final String TEXT_42 = "_Out_i::";
  protected final String TEXT_43 = "(";
  protected final String TEXT_44 = ")";
  protected final String TEXT_45 = " ";
  protected final String TEXT_46 = ")";
  protected final String TEXT_47 = ", ";
  protected final String TEXT_48 = NL + "{";
  protected final String TEXT_49 = NL + "    ";
  protected final String TEXT_50 = " retval = ";
  protected final String TEXT_51 = ";";
  protected final String TEXT_52 = NL + "    std::vector < std::pair < ";
  protected final String TEXT_53 = "::";
  protected final String TEXT_54 = "_var, std::string > >::iterator i;" + NL + "" + NL + "    updatingPortsLock.lock();    // don't want to process while command information is coming in" + NL;
  protected final String TEXT_55 = NL + "    std::copy(begin, end, data);" + NL;
  protected final String TEXT_56 = NL + "    Sequence_";
  protected final String TEXT_57 = "_";
  protected final String TEXT_58 = ".length(";
  protected final String TEXT_59 = ".size());" + NL + "    memcpy(&Sequence_";
  protected final String TEXT_60 = "_";
  protected final String TEXT_61 = "[0], &";
  protected final String TEXT_62 = "[0], ";
  protected final String TEXT_63 = ".size() * sizeof(";
  protected final String TEXT_64 = "[0]));" + NL;
  protected final String TEXT_65 = NL + "    if (active) {" + NL + "        for (i = outConnections.begin(); i != outConnections.end(); ++i) {" + NL + "            try {" + NL + "                ";
  protected final String TEXT_66 = "retval =";
  protected final String TEXT_67 = "((*i).first)->";
  protected final String TEXT_68 = "(";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = "Sequence_";
  protected final String TEXT_71 = "_";
  protected final String TEXT_72 = ");";
  protected final String TEXT_73 = ", ";
  protected final String TEXT_74 = NL + "            } catch(...) {" + NL + "                std::cout << \"Call to ";
  protected final String TEXT_75 = " by ";
  protected final String TEXT_76 = "_";
  protected final String TEXT_77 = "_Out_i failed\" << std::endl;" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    updatingPortsLock.unlock();    // don't want to process while command information is coming in" + NL + "" + NL + "    return";
  protected final String TEXT_78 = " retval";
  protected final String TEXT_79 = ";" + NL + "}";
  protected final String TEXT_80 = NL;
  protected final String TEXT_81 = "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_82 = "_";
  protected final String TEXT_83 = "_In_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_84 = NL + "BULKIO_dataSDDS_In_i::BULKIO_dataSDDS_In_i(std::string port_name, ";
  protected final String TEXT_85 = "_i *_parent) : Port_Provides_base_impl(port_name)" + NL + "{" + NL + "    parent = _parent;" + NL + "}" + NL + "" + NL + "BULKIO_dataSDDS_In_i::~BULKIO_dataSDDS_In_i()" + NL + "{" + NL + "}" + NL + "" + NL + "BULKIO::PortStatistics * BULKIO_dataSDDS_In_i::statistics()" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(statUpdateLock);" + NL + "    BULKIO::PortStatistics_var recStat = new BULKIO::PortStatistics(stats.retrieve());" + NL + "    return recStat._retn();" + NL + "}" + NL + "" + NL + "BULKIO::PortUsageType BULKIO_dataSDDS_In_i::state()" + NL + "{" + NL + "    if (attachedStreamMap.size() == 0) {" + NL + "        return BULKIO::IDLE;" + NL + "    } else if (attachedStreamMap.size() == 1) {" + NL + "        return BULKIO::BUSY;" + NL + "    } else {" + NL + "        return BULKIO::ACTIVE;" + NL + "    }" + NL + "}" + NL + "" + NL + "BULKIO::StreamSRISequence * BULKIO_dataSDDS_In_i::attachedSRIs()" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(sriUpdateLock);" + NL + "    BULKIO::StreamSRISequence_var sris = new BULKIO::StreamSRISequence(currentHs.size());" + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> >::iterator sriIter;" + NL + "    unsigned int idx = 0;" + NL + "" + NL + "    sriIter = currentHs.begin();" + NL + "    while (sriIter != currentHs.end()) {" + NL + "        sris[idx] = (*sriIter).second.first;" + NL + "        sriIter++;" + NL + "    }" + NL + "    return sris._retn();" + NL + "}" + NL + "" + NL + "void BULKIO_dataSDDS_In_i::pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(sriUpdateLock);" + NL + "    bool foundSRI = false;" + NL + "    BULKIO::StreamSRI tmpH = H;" + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> >::iterator sriIter;" + NL + "" + NL + "    sriIter = currentHs.begin();" + NL + "    while (sriIter != currentHs.end()) {" + NL + "        if (H.streamID == (*sriIter).second.first.streamID) {" + NL + "            foundSRI = true;" + NL + "            break;" + NL + "        }" + NL + "        sriIter++;" + NL + "    }" + NL + "    if (!foundSRI) {" + NL + "        currentHs.insert(std::make_pair(CORBA::string_dup(H.streamID), std::make_pair(H, T)));" + NL + "    } else {" + NL + "        (*sriIter).second = std::make_pair(H, T);" + NL + "    }" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamDefinition* BULKIO_dataSDDS_In_i::getStreamDefinition(const char* attachId) {" + NL + "    std::map<std::string, BULKIO::SDDSStreamDefinition*>::iterator portIter2;" + NL + "    portIter2 = attachedStreamMap.begin();" + NL + "    // use: attachedPorts[(*portIter).first] :instead" + NL + "    while (portIter2 != attachedStreamMap.end()) {" + NL + "        if (strcmp((*portIter2).first.c_str(), attachId) == 0) {" + NL + "            return (*portIter2).second;" + NL + "        }" + NL + "        portIter2++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_In_i::getUser(const char* attachId)" + NL + "{" + NL + "    std::map<std::string, std::string>::iterator portIter2;" + NL + "    portIter2 = attachedUsers.begin();" + NL + "    while (portIter2 != attachedUsers.end()) {" + NL + "        if (strcmp((*portIter2).first.c_str(), attachId) == 0) {" + NL + "            return CORBA::string_dup((*portIter2).second.c_str());" + NL + "        }" + NL + "        portIter2++;" + NL + "    }" + NL + "    return NULL;" + NL + "}" + NL + "" + NL + "BULKIO::dataSDDS::InputUsageState BULKIO_dataSDDS_In_i::usageState() {" + NL + "    if (attachedStreamMap.size() == 0) {" + NL + "        return BULKIO::dataSDDS::IDLE;" + NL + "    } else if (attachedStreamMap.size() == 1) {" + NL + "        return BULKIO::dataSDDS::BUSY;" + NL + "    } else {" + NL + "        return BULKIO::dataSDDS::ACTIVE;" + NL + "    }" + NL + "}" + NL + "" + NL + "BULKIO::SDDSStreamSequence* BULKIO_dataSDDS_In_i::attachedStreams() {" + NL + "    BULKIO::SDDSStreamSequence* seq = new BULKIO::SDDSStreamSequence();" + NL + "    seq->length(attachedStreamMap.size());" + NL + "    std::map<std::string, BULKIO::SDDSStreamDefinition*>::iterator portIter2;" + NL + "    portIter2 = attachedStreamMap.begin();" + NL + "    int i = 0;" + NL + "    while (portIter2 != attachedStreamMap.end()) {" + NL + "        (*seq)[i++] = *((*portIter2).second);" + NL + "        portIter2++;" + NL + "    }" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "BULKIO::StringSequence* BULKIO_dataSDDS_In_i::attachmentIds() {" + NL + "    BULKIO::StringSequence* seq = new BULKIO::StringSequence();" + NL + "    seq->length(attachedStreamMap.size());" + NL + "    std::map<std::string, BULKIO::SDDSStreamDefinition*>::iterator portIter2;" + NL + "    portIter2 = attachedStreamMap.begin();" + NL + "    int i = 0;" + NL + "    while (portIter2 != attachedStreamMap.end()) {" + NL + "        (*seq)[i++] = CORBA::string_dup((*portIter2).first.c_str());" + NL + "        portIter2++;" + NL + "    }" + NL + "    return seq;" + NL + "}" + NL + "" + NL + "char* BULKIO_dataSDDS_In_i::attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid) throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError) {" + NL + "    std::string attachId;" + NL + "" + NL + "    attachId = parent->attach(name, stream, userid);" + NL + "    attachedStreamMap.insert(std::make_pair(attachId, new BULKIO::SDDSStreamDefinition(stream)));" + NL + "    attachedUsers.insert(std::make_pair(attachId, std::string(userid)));" + NL + "" + NL + "    return CORBA::string_dup(attachId.c_str());" + NL + "}" + NL + "" + NL + "void BULKIO_dataSDDS_In_i::detach(const char* attachId) {" + NL + "    attachedStreamMap.erase(attachId);" + NL + "    attachedUsers.erase(attachId);" + NL + "}" + NL;
  protected final String TEXT_86 = NL;
  protected final String TEXT_87 = "_";
  protected final String TEXT_88 = "_In_i::";
  protected final String TEXT_89 = "_";
  protected final String TEXT_90 = "_In_i(std::string port_name, ";
  protected final String TEXT_91 = "_i *_parent) : " + NL + "Port_Provides_base_impl(port_name)" + NL + "{" + NL + "    currentState = BULKIO::IDLE;" + NL + "}" + NL;
  protected final String TEXT_92 = NL;
  protected final String TEXT_93 = "_";
  protected final String TEXT_94 = "_In_i::~";
  protected final String TEXT_95 = "_";
  protected final String TEXT_96 = "_In_i()" + NL + "{" + NL + "}" + NL + "" + NL + "BULKIO::PortStatistics * ";
  protected final String TEXT_97 = "_";
  protected final String TEXT_98 = "_In_i::statistics()" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(sriUpdateLock);" + NL + "    BULKIO::PortStatistics_var recStat = new BULKIO::PortStatistics(stats.retrieve());" + NL + "    // NOTE: You must delete the object that this function returns!" + NL + "    return recStat._retn();" + NL + "}" + NL + "" + NL + "BULKIO::PortUsageType ";
  protected final String TEXT_99 = "_";
  protected final String TEXT_100 = "_In_i::state()" + NL + "{" + NL + "    return currentState;" + NL + "}" + NL + "" + NL + "BULKIO::StreamSRISequence * ";
  protected final String TEXT_101 = "_";
  protected final String TEXT_102 = "_In_i::activeSRIs()" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(sriUpdateLock);" + NL + "    BULKIO::StreamSRISequence_var retSRI = new BULKIO::StreamSRISequence(currentHs);" + NL + "    // NOTE: You must delete the object that this function returns!" + NL + "    return retSRI._retn();" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_103 = "_";
  protected final String TEXT_104 = "_In_i::pushSRI(const BULKIO::StreamSRI& H)" + NL + "{" + NL + "    parent->pushSRI(name, H);" + NL + "}" + NL;
  protected final String TEXT_105 = NL;
  protected final String TEXT_106 = " ";
  protected final String TEXT_107 = "_";
  protected final String TEXT_108 = "_In_i::";
  protected final String TEXT_109 = "(";
  protected final String TEXT_110 = ")";
  protected final String TEXT_111 = " ";
  protected final String TEXT_112 = ")";
  protected final String TEXT_113 = ", ";
  protected final String TEXT_114 = NL + "{";
  protected final String TEXT_115 = NL + "    new_message.resize(";
  protected final String TEXT_116 = ".length());" + NL + "    memcpy(&new_message[0], &";
  protected final String TEXT_117 = "[0], ";
  protected final String TEXT_118 = ".length() * sizeof(";
  protected final String TEXT_119 = "[0]));" + NL + "    ";
  protected final String TEXT_120 = NL + NL + "    // This is somewhat dangerous. The const_cast<> removes the const on the data" + NL + "    // sequence that's passed in so we can call get_buffer on it. get_buffer(1)" + NL + "    // essentially steals the buffer out of the sequence from OmniORB. However," + NL + "    // once you do this you MUST delete the buffer when you're done with it" + NL + "    // otherwise you'll run out of memory.  By doing this, we reduce the transfers" + NL + "    // to one memcpy/connection for the send process." + NL + "    //" + NL + "    // MAKE SURE TO DELETE THIS BUFFER!!!!!" + NL + "    unsigned long length = ";
  protected final String TEXT_121 = ".length();" + NL + "    new_message = const_cast<";
  protected final String TEXT_122 = "*>(&";
  protected final String TEXT_123 = ")->get_buffer(1);" + NL + "    ";
  protected final String TEXT_124 = NL + NL + "    parent->";
  protected final String TEXT_125 = "(name";
  protected final String TEXT_126 = ", ";
  protected final String TEXT_127 = ");";
  protected final String TEXT_128 = ");";
  protected final String TEXT_129 = ", ";
  protected final String TEXT_130 = NL + "    stats.update(";
  protected final String TEXT_131 = "1";
  protected final String TEXT_132 = ".length()";
  protected final String TEXT_133 = ", EOS, streamID);";
  protected final String TEXT_134 = NL + "    stats.update(strlen(";
  protected final String TEXT_135 = "), EOS, streamID);";
  protected final String TEXT_136 = NL + "}";
  protected final String TEXT_137 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_138 = "_";
  protected final String TEXT_139 = "_In_i definition" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_140 = NL;
  protected final String TEXT_141 = "_";
  protected final String TEXT_142 = "_In_i::";
  protected final String TEXT_143 = "_";
  protected final String TEXT_144 = "_In_i(std::string port_name, ";
  protected final String TEXT_145 = "_i *_parent) : " + NL + "Port_Provides_base_impl(port_name)" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_146 = NL;
  protected final String TEXT_147 = "_";
  protected final String TEXT_148 = "_In_i::~";
  protected final String TEXT_149 = "_";
  protected final String TEXT_150 = "_In_i()" + NL + "{" + NL + "}";
  protected final String TEXT_151 = NL;
  protected final String TEXT_152 = NL;
  protected final String TEXT_153 = " ";
  protected final String TEXT_154 = "_";
  protected final String TEXT_155 = "_In_i::";
  protected final String TEXT_156 = "(";
  protected final String TEXT_157 = ")";
  protected final String TEXT_158 = " ";
  protected final String TEXT_159 = ")";
  protected final String TEXT_160 = ", ";
  protected final String TEXT_161 = NL + "{";
  protected final String TEXT_162 = NL + "    ";
  protected final String TEXT_163 = "char";
  protected final String TEXT_164 = " tmpVal";
  protected final String TEXT_165 = "[255]";
  protected final String TEXT_166 = ";";
  protected final String TEXT_167 = NL + "    vector_";
  protected final String TEXT_168 = "_";
  protected final String TEXT_169 = ".resize(";
  protected final String TEXT_170 = ".length());" + NL + "    memcpy(&vector_";
  protected final String TEXT_171 = "_";
  protected final String TEXT_172 = "[0], &";
  protected final String TEXT_173 = "[0], ";
  protected final String TEXT_174 = ".length() * sizeof(";
  protected final String TEXT_175 = "[0]));" + NL + "    ";
  protected final String TEXT_176 = NL;
  protected final String TEXT_177 = "    strcpy(tmpVal, ";
  protected final String TEXT_178 = "    tmpVal = ";
  protected final String TEXT_179 = "    ";
  protected final String TEXT_180 = "parent->";
  protected final String TEXT_181 = "(name";
  protected final String TEXT_182 = ", ";
  protected final String TEXT_183 = ")";
  protected final String TEXT_184 = ".c_str())";
  protected final String TEXT_185 = ";";
  protected final String TEXT_186 = "vector_";
  protected final String TEXT_187 = "_";
  protected final String TEXT_188 = ").c_str()";
  protected final String TEXT_189 = ");";
  protected final String TEXT_190 = ", ";
  protected final String TEXT_191 = NL + "    return ";
  protected final String TEXT_192 = "CORBA::string_dup(";
  protected final String TEXT_193 = "tmpVal";
  protected final String TEXT_194 = ")";
  protected final String TEXT_195 = "; ";
  protected final String TEXT_196 = NL + "}";
  protected final String TEXT_197 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softpkg = (SoftPkg) impl.eContainer();
    EList<Uses> uses = softpkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softpkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softpkg, implSettings);
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
    
    CppHelper _cppHelper = new CppHelper();
    HashSet<String> usesList = new HashSet<String>();
    for (Uses entry : uses) {
        String intName = entry.getRepID();
        usesList.add(intName);
    }
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        String intName = entry.getRepID();
        providesList.add(intName);
    }
    
    for (String entry : usesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true); 
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();
        if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_6);
    
            if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    
            } else {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_24);
    
            } // end else (if dataSDDS)

    
        } else {

    stringBuffer.append(TEXT_25);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_37);
     
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            boolean pushPacketCall = false;
            int numVector = 0;
            ArrayList<String> vectorList = new ArrayList<String>();

    stringBuffer.append(TEXT_38);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_40);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_43);
    
            if (numParams == 0) {

    stringBuffer.append(TEXT_44);
    
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
    stringBuffer.append(TEXT_45);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_46);
    
                } else {

    stringBuffer.append(TEXT_47);
    
                }
            } // end for params

    stringBuffer.append(TEXT_48);
    
            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_49);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_50);
    stringBuffer.append(_cppHelper.getInitialValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_51);
    
            }

    stringBuffer.append(TEXT_52);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_54);
    
            if (pushPacketCall) {

    stringBuffer.append(TEXT_55);
    
            } else {
                for (int i = 0; i < numVector; i++) {

    stringBuffer.append(TEXT_56);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_57);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_59);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_60);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_62);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_63);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_64);
    
		        }
		    }

    stringBuffer.append(TEXT_65);
    
            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_66);
    
            }

    stringBuffer.append(TEXT_67);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_68);
    
            if (numParams == 0) {

    stringBuffer.append(TEXT_69);
    
            }
            for (int j = 0; j < numParams; j++) {
                String paramName = op.getParams().get(j).getName();
                boolean vectorParam = false;
                for (int i = 0; i < numVector; i++) {
                    if (paramName.equals(vectorList.get(i))) {

    stringBuffer.append(TEXT_70);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_71);
    stringBuffer.append(i);
    
                        vectorParam = true;
                        break;
                    }
                }
                if (!vectorParam) {

    stringBuffer.append(paramName);
    
                }
                if (j == (numParams - 1)) {

    stringBuffer.append(TEXT_72);
    
                } else {

    stringBuffer.append(TEXT_73);
    
                }
            } // end for params

    stringBuffer.append(TEXT_74);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_75);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_77);
     if (!"void".equals(op.getCxxReturnType())) {
    stringBuffer.append(TEXT_78);
    }
    stringBuffer.append(TEXT_79);
    
            } // end for Operations
        } //end if !BulkIO
	} // end for usesList

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();
        if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_83);
    
            if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_84);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_85);
    
            } else {

    stringBuffer.append(TEXT_86);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_104);
    
        for (Operation op : iface.getOperations()) {
            if (!"pushPacket".equals(op.getName())) {
                continue;
            }
            int numParams = op.getParams().size();
            boolean pushPacketCall = false;
            boolean hasPushPacketFileCall = false;
            boolean hasPushPacketXMLCall = false;
            int numVector = 0;
            ArrayList<String> vectorList = new ArrayList<String>();
            if (numParams == 4) {
                if (!"dataFile".equals(interfaceName)) {
                    pushPacketCall = true;
                } else {
                    hasPushPacketFileCall = true;
                }
            }
            if ("dataXML".equals(interfaceName)) {
                hasPushPacketXMLCall = true;
            }

    stringBuffer.append(TEXT_105);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_106);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_109);
    
            if (op.getParams().size() == 0) {

    stringBuffer.append(TEXT_110);
    
            }
            for (int i = 0; i < numParams; i++) {
                String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                if (iteratorBase.length() > 11) {
                    if (iteratorBase.substring(0,11).equals("std::vector")) {
                        numVector++;
                        vectorList.add(op.getParams().get(i).getName());
                    }
                }

    stringBuffer.append(op.getParams().get(i).getCxxType());
    stringBuffer.append(TEXT_111);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_112);
    
                } else {

    stringBuffer.append(TEXT_113);
    
                }
            } // end for params

    stringBuffer.append(TEXT_114);
    
            String ppFirstParam = "";
            if (pushPacketCall) {
                String firstParam = op.getParams().get(0).getName();
                if (memcpyBuffer) {
                    ppFirstParam = "new_message,";

    stringBuffer.append(TEXT_115);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_119);
    
                } else {
                    ppFirstParam = "new_message, length,";
                    String dataTransfer = op.getParams().get(0).getCxxType();
                    int beginningIndex = dataTransfer.startsWith("const") ? 6 : 0;
                    if (dataTransfer.endsWith("&")) {
                        dataTransfer = dataTransfer.substring(beginningIndex, dataTransfer.length() - 1);
                    } else {
                        dataTransfer = dataTransfer.substring(beginningIndex, dataTransfer.length());
                    }

    stringBuffer.append(TEXT_120);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(firstParam);
    stringBuffer.append(TEXT_123);
    
                } // end else !memcpyBuffer
            } // end if pushPacket

    stringBuffer.append(TEXT_124);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_125);
    
            if (numParams != 0) {

    stringBuffer.append(TEXT_126);
    
            } else {

    stringBuffer.append(TEXT_127);
    
        	} // end params = 0
	        for (int j = 0; j < numParams; j++) {
	            String paramName = op.getParams().get(j).getName();
	            if (pushPacketCall && (j == 0)) {

    stringBuffer.append(ppFirstParam);
    
	              continue;
	            }

    stringBuffer.append(paramName);
    
	            if (j == (numParams - 1)) {

    stringBuffer.append(TEXT_128);
    
        		} else {

    stringBuffer.append(TEXT_129);
    
        		}
    		} // end for params
            if (!hasPushPacketXMLCall) {

    stringBuffer.append(TEXT_130);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_131);
    } else {
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_132);
    }
    stringBuffer.append(TEXT_133);
    
            } else {

    stringBuffer.append(TEXT_134);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_135);
    
            } // end if pushPacket !pushpacketxml

    stringBuffer.append(TEXT_136);
    
        } // end for operations

    
        } // end else !dataSDDS

    
        } else {

    stringBuffer.append(TEXT_137);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_150);
    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            int numVector = 0;
            ArrayList<String> vectorList = new ArrayList<String>();

    stringBuffer.append(TEXT_151);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_153);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_156);
    
            if (op.getParams().size() == 0) {

    stringBuffer.append(TEXT_157);
    
            }
            for (int i = 0; i < numParams; i++) {
                String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                if (iteratorBase.length() > 11) {
                    if (iteratorBase.substring(0,11).equals("std::vector")) {
                        numVector++;
                        vectorList.add(op.getParams().get(i).getName());
                    }
                }

    stringBuffer.append(op.getParams().get(i).getCxxType());
    stringBuffer.append(TEXT_158);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_159);
    
                } else {

    stringBuffer.append(TEXT_160);
    
                }
            } // end for params

    stringBuffer.append(TEXT_161);
    
            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_162);
    
                if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_163);
    
                } else {

    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    
                }

    stringBuffer.append(TEXT_164);
    
                if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_165);
    
                }

    stringBuffer.append(TEXT_166);
    
            } // end if !returnType == void
            for (int i = 0; i < numVector; i++) {

    stringBuffer.append(TEXT_167);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_168);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_170);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_171);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_173);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_174);
    stringBuffer.append(vectorList.get(i));
    stringBuffer.append(TEXT_175);
    
            } // end numVectors

    stringBuffer.append(TEXT_176);
    
    		if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_177);
    
            } else if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_178);
    
            } else {

    stringBuffer.append(TEXT_179);
    
            }

    stringBuffer.append(TEXT_180);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_181);
    
            if (numParams != 0) {

    stringBuffer.append(TEXT_182);
    
            } else {

    stringBuffer.append(TEXT_183);
    
            	if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_184);
    
            	}

    stringBuffer.append(TEXT_185);
    
        	} // end params = 0
	        for (int j = 0; j < numParams; j++) {
	            String paramName = op.getParams().get(j).getName();
	            boolean vectorParam = false;
	            for (int k = 0; k < numVector; k++) {
	                if (paramName.equals(vectorList.get(k))) {

    stringBuffer.append(TEXT_186);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_187);
    stringBuffer.append(k);
    
	                    vectorParam = true;
	                    break;
                	}
            	}
            	if (!vectorParam) {

    stringBuffer.append(paramName);
    
            	}
	            if (j == (numParams - 1)) {
	                if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_188);
    
            		}

    stringBuffer.append(TEXT_189);
    
        		} else {

    stringBuffer.append(TEXT_190);
    
        		}
    		} // end for params
            if (!"void".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_191);
    
                if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_192);
    
                }

    stringBuffer.append(TEXT_193);
    
                if ("char*".equals(op.getCxxReturnType())) {

    stringBuffer.append(TEXT_194);
    
                }

    stringBuffer.append(TEXT_195);
    
            } // end if !returnType == void

    stringBuffer.append(TEXT_196);
    
        } // end for operations
        } // end else !BULKIO
    } // end for providesList

    stringBuffer.append(TEXT_197);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE