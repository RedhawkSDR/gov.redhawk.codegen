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
import gov.redhawk.ide.idl.Attribute;
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
public class PortImplHTemplate
{

  protected static String nl;
  public static synchronized PortImplHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PortImplHTemplate result = new PortImplHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef __CORBA_H_EXTERNAL_GUARD__" + NL + "#include <omniORB4/CORBA.h>" + NL + "#endif" + NL;
  protected final String TEXT_2 = NL + "#include \"ossie/Port_impl.h\"" + NL + "#include <list>" + NL + "" + NL + "class ";
  protected final String TEXT_3 = "_i;" + NL;
  protected final String TEXT_4 = NL + "#include \"";
  protected final String TEXT_5 = ".h\"";
  protected final String TEXT_6 = NL + "#include \"ossie/CF/QueryablePort.h\"";
  protected final String TEXT_7 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_8 = "_";
  protected final String TEXT_9 = "_Out_i declaration" + NL + "// ----------------------------------------------------------------------------------------" + NL + "#ifndef _";
  protected final String TEXT_10 = "_";
  protected final String TEXT_11 = "_Out_i" + NL + "#define _";
  protected final String TEXT_12 = "_";
  protected final String TEXT_13 = "_Out_i" + NL;
  protected final String TEXT_14 = NL + "class BULKIO_dataSDDS_Out_i : public Port_Uses_base_impl, public virtual POA_BULKIO::UsesPortStatisticsProvider" + NL + "{" + NL + "public:" + NL + "    BULKIO_dataSDDS_Out_i(std::string port_name, ";
  protected final String TEXT_15 = "_i *_parent);" + NL + "" + NL + "    ~BULKIO_dataSDDS_Out_i();" + NL + "" + NL + "    class linkStatistics" + NL + "    {" + NL + "        public:" + NL + "            struct statPoint {" + NL + "                unsigned int elements;" + NL + "                double secs;" + NL + "                double usecs;" + NL + "            };" + NL + "" + NL + "            linkStatistics() {" + NL + "                bitSize = 8.0;" + NL + "                historyWindow = 10;" + NL + "                activeStreamIDs.resize(0);" + NL + "                receivedStatistics_idx = 0;" + NL + "                receivedStatistics.resize(historyWindow);" + NL + "                runningStats.elementsPerSecond = -1.0;" + NL + "                runningStats.bitsPerSecond = -1.0;" + NL + "                runningStats.callsPerSecond = -1.0;" + NL + "                runningStats.streamIDs.length(0);" + NL + "                runningStats.timeSinceLastCall = -1;" + NL + "                enabled = true;" + NL + "            };" + NL + "" + NL + "            void setBitSize(double _bitSize) {" + NL + "                bitSize = _bitSize;" + NL + "            }" + NL + "" + NL + "            void setEnabled(bool enableStats) {" + NL + "                enabled = enableStats;" + NL + "            }" + NL + "" + NL + "            void update(unsigned int elementsReceived, bool EOS, std::string streamID) {" + NL + "                if (!enabled) {" + NL + "                    return;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "                receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                if (!EOS) {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    bool foundStreamID = false;" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            foundStreamID = true;" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                    if (!foundStreamID) {" + NL + "                        activeStreamIDs.push_back(streamID);" + NL + "                    }" + NL + "                } else {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            activeStreamIDs.erase(p);" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                }" + NL + "            };" + NL + "" + NL + "            BULKIO::PortStatistics retrieve() {" + NL + "                if (!enabled) {" + NL + "                    return runningStats;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "" + NL + "                int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                double front_sec = receivedStatistics[idx].secs;" + NL + "                double front_usec = receivedStatistics[idx].usecs;" + NL + "                double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "" + NL + "                double totalTime = secDiff + usecDiff;" + NL + "                double totalData = 0;" + NL + "                int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                    totalData += receivedStatistics[i].elements;" + NL + "                    i = (i + 1) % historyWindow;" + NL + "                }" + NL + "                runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                runningStats.averageQueueDepth = 0;" + NL + "                runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                runningStats.streamIDs.length(streamIDsize);" + NL + "                for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                    if (p == activeStreamIDs.end()) {" + NL + "                        break;" + NL + "                    }" + NL + "                    runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                    p++;" + NL + "                }" + NL + "                return runningStats;" + NL + "            };" + NL + "" + NL + "        private:" + NL + "            bool enabled;" + NL + "            double bitSize;" + NL + "            BULKIO::PortStatistics runningStats;" + NL + "            std::vector<statPoint> receivedStatistics;" + NL + "            std::list< std::string > activeStreamIDs;" + NL + "            unsigned long historyWindow;" + NL + "            int receivedStatistics_idx;" + NL + "    };" + NL + "" + NL + "    BULKIO::UsesPortStatisticsSequence * statistics()" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "        BULKIO::UsesPortStatisticsSequence_var recStat = new BULKIO::UsesPortStatisticsSequence();" + NL + "        recStat->length(outConnections.size());" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            recStat[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "            recStat[i].statistics = stats[outConnections[i].second].retrieve();" + NL + "        }" + NL + "        return recStat._retn();" + NL + "    };" + NL + "" + NL + "    BULKIO::PortUsageType state()" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "        if (outConnections.size() > 0) {" + NL + "            return BULKIO::ACTIVE;" + NL + "        } else {" + NL + "            return BULKIO::IDLE;" + NL + "        }" + NL + "" + NL + "        return BULKIO::BUSY;" + NL + "    };" + NL + "" + NL + "    void enableStats(bool enable)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].setEnabled(enable);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void setBitSize(double bitSize)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].setBitSize(bitSize);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void updateStats(unsigned int elementsReceived, bool EOS, std::string streamID)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].update(elementsReceived, EOS, streamID);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void connectPort(CORBA::Object_ptr connection, const char* connectionId)" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        BULKIO::dataSDDS_var port = BULKIO::dataSDDS::_narrow(connection);" + NL + "        if (lastStreamData != NULL) {" + NL + "            // TODO - use the username instead" + NL + "            std::string attachId = port->attach(*lastStreamData, user_id.c_str());" + NL + "            attachedGroup.insert(std::make_pair(attachId, std::make_pair(lastStreamData, user_id)));" + NL + "            attachedPorts.insert(std::make_pair(port, attachId));" + NL + "        }" + NL + "        outConnections.push_back(std::make_pair(port, connectionId));" + NL + "        active = true;" + NL + "        refreshSRI = true;" + NL + "    };" + NL + "" + NL + "    void disconnectPort(const char* connectionId)" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            if (outConnections[i].second == connectionId) {" + NL + "                outConnections[i].first->detach(attachedPorts[outConnections[i].first].c_str());" + NL + "                outConnections.erase(outConnections.begin() + i);" + NL + "                break;" + NL + "            }" + NL + "        }" + NL + "" + NL + "        if (outConnections.size() == 0) {" + NL + "            active = false;" + NL + "        }" + NL + "    };" + NL + "" + NL + "    ExtendedCF::UsesConnectionSequence * connections() " + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        if (recConnectionsRefresh) {" + NL + "            recConnections.length(outConnections.size());" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                recConnections[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                recConnections[i].port = CORBA::Object::_duplicate(outConnections[i].first);" + NL + "            }" + NL + "            recConnectionsRefresh = false;" + NL + "        }" + NL + "        ExtendedCF::UsesConnectionSequence_var retVal = new ExtendedCF::UsesConnectionSequence(recConnections);" + NL + "        return retVal._retn();" + NL + "    };" + NL + "" + NL + "    std::vector< std::pair<BULKIO::dataSDDS_var, std::string> > _getConnections()" + NL + "    {" + NL + "        return outConnections;" + NL + "    };" + NL + "    " + NL + "    BULKIO::SDDSStreamDefinition* getStreamDefinition(const char* attachId);" + NL + "" + NL + "    char* getUser(const char* attachId);" + NL + "" + NL + "    BULKIO::dataSDDS::InputUsageState usageState();" + NL + "" + NL + "    BULKIO::SDDSStreamSequence* attachedStreams();" + NL + "" + NL + "    BULKIO::StringSequence* attachmentIds();" + NL + "" + NL + "    char* attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid) throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError);" + NL + "" + NL + "    void detach(const char* attachId);" + NL + "    " + NL + "    void pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T);" + NL + "    " + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> > currentSRIs;" + NL + "" + NL + "private:";
  protected final String TEXT_16 = NL + "    ";
  protected final String TEXT_17 = "_i *parent;" + NL + "    // maps a stream ID to a pair of Stream and userID" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> > attachedGroup;" + NL + "" + NL + "    BULKIO::SDDSStreamDefinition* lastStreamData;" + NL + "    std::vector < std::pair<BULKIO::dataSDDS_var, std::string> > outConnections;" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string> attachedPorts;" + NL + "    std::string user_id;" + NL + "    ExtendedCF::UsesConnectionSequence recConnections;" + NL + "    bool recConnectionsRefresh;" + NL + "    std::map<std::string, linkStatistics> stats;";
  protected final String TEXT_18 = NL + "class ";
  protected final String TEXT_19 = "_";
  protected final String TEXT_20 = "_Out_i : public Port_Uses_base_impl, public virtual POA_BULKIO::UsesPortStatisticsProvider" + NL + "{" + NL + "    public:";
  protected final String TEXT_21 = NL + "        ";
  protected final String TEXT_22 = "_";
  protected final String TEXT_23 = "_Out_i(std::string port_name);" + NL + "        ~";
  protected final String TEXT_24 = "_";
  protected final String TEXT_25 = "_Out_i();";
  protected final String TEXT_26 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  ";
  protected final String TEXT_27 = ": structure containing the payload to send out" + NL + "         *  T: constant of type BULKIO::PrecisionUTCTime containing the timestamp for the outgoing data." + NL + "         *    tcmode: timecode mode" + NL + "         *    tcstatus: timecode status " + NL + "         *    toff: fractional sample offset" + NL + "         *    twsec: J1970 GMT " + NL + "         *    tfsec: fractional seconds: 0.0 to 1.0" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        void pushPacket(";
  protected final String TEXT_28 = " ";
  protected final String TEXT_29 = ", const BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {" + NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) != currentSRIs.end()) {" + NL + "                    pushSRI(currentSRIs[streamID]);" + NL + "                }" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_30 = "::";
  protected final String TEXT_31 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(";
  protected final String TEXT_32 = ", T, EOS, streamID.c_str());" + NL + "                        stats[(*port).second].update(1, 0, streamID);" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_33 = "_";
  protected final String TEXT_34 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "        };";
  protected final String TEXT_35 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  ";
  protected final String TEXT_36 = ": structure containing the payload to send out" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        void pushPacket(";
  protected final String TEXT_37 = " ";
  protected final String TEXT_38 = ", bool EOS, const char* streamID) {" + NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) != currentSRIs.end()) {" + NL + "                    pushSRI(currentSRIs[streamID]);" + NL + "                }" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_39 = "::";
  protected final String TEXT_40 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(";
  protected final String TEXT_41 = ", EOS, streamID);" + NL + "                        stats[(*port).second].update(strlen(";
  protected final String TEXT_42 = "), 0, std::string(streamID));" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_43 = "_";
  protected final String TEXT_44 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "        };";
  protected final String TEXT_45 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  data: structure containing the payload to send out" + NL + "         *  T: constant of type BULKIO::PrecisionUTCTime containing the timestamp for the outgoing data." + NL + "         *    tcmode: timecode mode" + NL + "         *    tcstatus: timecode status " + NL + "         *    toff: fractional sample offset" + NL + "         *    twsec: J1970 GMT " + NL + "         *    tfsec: fractional seconds: 0.0 to 1.0" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        void pushPacket(";
  protected final String TEXT_46 = " data, CORBA::ULong length, BULKIO::PrecisionUTCTime& T, bool EOS, std::string& streamID) {" + NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) != currentSRIs.end()) {" + NL + "                    pushSRI(currentSRIs[streamID]);" + NL + "                }" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        \t// Magic is below, make a new sequence using the data from the Iterator" + NL + "        \t// as the data for the sequence.  The 'false' at the end is whether or not" + NL + "        \t// CORBA is allowed to delete the buffer when the sequence is destroyed." + NL + "        \t";
  protected final String TEXT_47 = " seq = ";
  protected final String TEXT_48 = "(length, length, ";
  protected final String TEXT_49 = "(CORBA::ULong*)";
  protected final String TEXT_50 = "&(data[0]), false);" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_51 = "::";
  protected final String TEXT_52 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(seq, T, EOS, streamID.c_str());" + NL + "                        stats[(*port).second].update(length, 0, streamID);" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_53 = "_";
  protected final String TEXT_54 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "            updatingPortsLock.unlock();\t// don't want to process while command information is coming in" + NL + "        };";
  protected final String TEXT_55 = NL;
  protected final String TEXT_56 = NL + "        ";
  protected final String TEXT_57 = " ";
  protected final String TEXT_58 = "(";
  protected final String TEXT_59 = ");";
  protected final String TEXT_60 = "::iterator begin, ";
  protected final String TEXT_61 = "::iterator end,";
  protected final String TEXT_62 = " ";
  protected final String TEXT_63 = ");";
  protected final String TEXT_64 = ", ";
  protected final String TEXT_65 = NL + "        class linkStatistics" + NL + "        {" + NL + "            public:" + NL + "                struct statPoint {" + NL + "                    unsigned int elements;" + NL + "                    double secs;" + NL + "                    double usecs;" + NL + "                };" + NL + "                " + NL + "                linkStatistics() {" + NL + "                    bitSize = sizeof(";
  protected final String TEXT_66 = ") * 8.0;" + NL + "                    historyWindow = 10;" + NL + "                    activeStreamIDs.resize(0);" + NL + "                    receivedStatistics_idx = 0;" + NL + "                    receivedStatistics.resize(historyWindow);" + NL + "                    runningStats.elementsPerSecond = -1.0;" + NL + "                    runningStats.bitsPerSecond = -1.0;" + NL + "                    runningStats.callsPerSecond = -1.0;" + NL + "                    runningStats.streamIDs.length(0);" + NL + "                    runningStats.timeSinceLastCall = -1;" + NL + "                    enabled = true;" + NL + "                };" + NL + "" + NL + "                void setEnabled(bool enableStats) {" + NL + "                    enabled = enableStats;" + NL + "                }" + NL + "" + NL + "                void update(unsigned int elementsReceived, bool EOS, std::string streamID) {" + NL + "                    if (!enabled) {" + NL + "                        return;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "                    receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                    receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                    receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                    receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                    if (!EOS) {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        bool foundStreamID = false;" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                foundStreamID = true;" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                        if (!foundStreamID) {" + NL + "                            activeStreamIDs.push_back(streamID);" + NL + "                        }" + NL + "                    } else {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                activeStreamIDs.erase(p);" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                    }" + NL + "                };" + NL + "" + NL + "                BULKIO::PortStatistics retrieve() {" + NL + "                    if (!enabled) {" + NL + "                        return runningStats;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "" + NL + "                    int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                    double front_sec = receivedStatistics[idx].secs;" + NL + "                    double front_usec = receivedStatistics[idx].usecs;" + NL + "                    double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                    double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "" + NL + "                    double totalTime = secDiff + usecDiff;" + NL + "                    double totalData = 0;" + NL + "                    int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                    for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                        totalData += receivedStatistics[i].elements;" + NL + "                        i = (i + 1) % historyWindow;" + NL + "                    }" + NL + "                    runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                    runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                    runningStats.averageQueueDepth = 0;" + NL + "                    runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                    runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                    unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                    std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                    runningStats.streamIDs.length(streamIDsize);" + NL + "                    for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                        if (p == activeStreamIDs.end()) {" + NL + "                            break;" + NL + "                        }" + NL + "                        runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                        p++;" + NL + "                    }" + NL + "                    return runningStats;" + NL + "                };" + NL + "" + NL + "            private:" + NL + "                bool enabled;" + NL + "                double bitSize;" + NL + "                BULKIO::PortStatistics runningStats;" + NL + "                std::vector<statPoint> receivedStatistics;" + NL + "                std::list< std::string > activeStreamIDs;" + NL + "                unsigned long historyWindow;" + NL + "                int receivedStatistics_idx;" + NL + "        };" + NL + "" + NL + "        BULKIO::UsesPortStatisticsSequence * statistics()" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "            BULKIO::UsesPortStatisticsSequence_var recStat = new BULKIO::UsesPortStatisticsSequence();" + NL + "            recStat->length(outConnections.size());" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                recStat[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                recStat[i].statistics = stats[outConnections[i].second].retrieve();" + NL + "            }" + NL + "            return recStat._retn();" + NL + "        };" + NL + "" + NL + "        BULKIO::PortUsageType state()" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "            if (outConnections.size() > 0) {" + NL + "                return BULKIO::ACTIVE;" + NL + "            } else {" + NL + "                return BULKIO::IDLE;" + NL + "            }" + NL + "" + NL + "            return BULKIO::BUSY;" + NL + "        };" + NL + "        " + NL + "        void enableStats(bool enable)" + NL + "        {" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                stats[outConnections[i].second].setEnabled(enable);" + NL + "            }" + NL + "        };" + NL + "" + NL + "        ExtendedCF::UsesConnectionSequence * connections() " + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (recConnectionsRefresh) {" + NL + "                recConnections.length(outConnections.size());" + NL + "                for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                    recConnections[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                    recConnections[i].port = CORBA::Object::_duplicate(outConnections[i].first);" + NL + "                }" + NL + "                recConnectionsRefresh = false;" + NL + "            }" + NL + "            ExtendedCF::UsesConnectionSequence_var retVal = new ExtendedCF::UsesConnectionSequence(recConnections);" + NL + "            // NOTE: You must delete the object that this function returns!" + NL + "            return retVal._retn();" + NL + "        };" + NL + "" + NL + "        void connectPort(CORBA::Object_ptr connection, const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in";
  protected final String TEXT_67 = NL + "            ";
  protected final String TEXT_68 = "::";
  protected final String TEXT_69 = "_var port = ";
  protected final String TEXT_70 = "::";
  protected final String TEXT_71 = "::_narrow(connection);" + NL + "            outConnections.push_back(std::make_pair(port, connectionId));" + NL + "            active = true;" + NL + "            refreshSRI = true;" + NL + "        };" + NL + "" + NL + "        void disconnectPort(const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                if (outConnections[i].second == connectionId) {" + NL + "                    outConnections.erase(outConnections.begin() + i);" + NL + "                    break;" + NL + "                }" + NL + "            }" + NL + "" + NL + "            if (outConnections.size() == 0) {" + NL + "                active = false;" + NL + "            }" + NL + "        };" + NL + "" + NL + "        std::vector< std::pair<";
  protected final String TEXT_72 = "::";
  protected final String TEXT_73 = "_var, std::string> > _getConnections()" + NL + "        {" + NL + "            return outConnections;" + NL + "        };" + NL + "" + NL + "        std::map<std::string, BULKIO::StreamSRI> currentSRIs;" + NL + "        " + NL + "    private:";
  protected final String TEXT_74 = NL + "    ";
  protected final String TEXT_75 = NL + "    ";
  protected final String TEXT_76 = " Sequence_";
  protected final String TEXT_77 = "_";
  protected final String TEXT_78 = "; ";
  protected final String TEXT_79 = NL + NL + "        std::vector < std::pair<";
  protected final String TEXT_80 = "::";
  protected final String TEXT_81 = "_var, std::string> > outConnections;" + NL + "        ExtendedCF::UsesConnectionSequence recConnections;" + NL + "        bool recConnectionsRefresh;" + NL + "        std::map<std::string, linkStatistics> stats;";
  protected final String TEXT_82 = NL + "};" + NL + "#endif";
  protected final String TEXT_83 = NL + NL + "class ";
  protected final String TEXT_84 = "_";
  protected final String TEXT_85 = "_Out_i : public Port_Uses_base_impl, public POA_ExtendedCF::QueryablePort" + NL + "{" + NL + "    public:";
  protected final String TEXT_86 = NL + "        ";
  protected final String TEXT_87 = "_";
  protected final String TEXT_88 = "_Out_i(std::string port_name);" + NL + "        ~";
  protected final String TEXT_89 = "_";
  protected final String TEXT_90 = "_Out_i();";
  protected final String TEXT_91 = NL;
  protected final String TEXT_92 = NL + "        ";
  protected final String TEXT_93 = " ";
  protected final String TEXT_94 = "(";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = " ";
  protected final String TEXT_97 = ");";
  protected final String TEXT_98 = ", ";
  protected final String TEXT_99 = NL + "        ";
  protected final String TEXT_100 = " ";
  protected final String TEXT_101 = "();" + NL;
  protected final String TEXT_102 = NL + "        void ";
  protected final String TEXT_103 = "(";
  protected final String TEXT_104 = " data);" + NL;
  protected final String TEXT_105 = NL + "        ExtendedCF::UsesConnectionSequence * connections() " + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (recConnectionsRefresh) {" + NL + "                recConnections.length(outConnections.size());" + NL + "                for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                    recConnections[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                    recConnections[i].port = CORBA::Object::_duplicate(outConnections[i].first);" + NL + "                }" + NL + "                recConnectionsRefresh = false;" + NL + "            }" + NL + "            ExtendedCF::UsesConnectionSequence_var retVal = new ExtendedCF::UsesConnectionSequence(recConnections);" + NL + "            // NOTE: You must delete the object that this function returns!" + NL + "            return retVal._retn();" + NL + "        };" + NL + "" + NL + "        void connectPort(CORBA::Object_ptr connection, const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in";
  protected final String TEXT_106 = NL + "            ";
  protected final String TEXT_107 = "::";
  protected final String TEXT_108 = "_var port = ";
  protected final String TEXT_109 = "::";
  protected final String TEXT_110 = "::_narrow(connection);" + NL + "            outConnections.push_back(std::make_pair(port, connectionId));" + NL + "            active = true;" + NL + "            refreshSRI = true;" + NL + "        };" + NL + "" + NL + "        void disconnectPort(const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                if (outConnections[i].second == connectionId) {" + NL + "                    outConnections.erase(outConnections.begin() + i);" + NL + "                    break;" + NL + "                }" + NL + "            }" + NL + "" + NL + "            if (outConnections.size() == 0) {" + NL + "                active = false;" + NL + "            }" + NL + "        };" + NL + "" + NL + "        std::vector< std::pair<";
  protected final String TEXT_111 = "::";
  protected final String TEXT_112 = "_var, std::string> > _getConnections()" + NL + "        {" + NL + "            return outConnections;" + NL + "        };" + NL + "        " + NL + "    private:";
  protected final String TEXT_113 = NL + "    ";
  protected final String TEXT_114 = NL + "    ";
  protected final String TEXT_115 = " Sequence_";
  protected final String TEXT_116 = "_";
  protected final String TEXT_117 = "; ";
  protected final String TEXT_118 = NL + NL + "        std::vector < std::pair<";
  protected final String TEXT_119 = "::";
  protected final String TEXT_120 = "_var, std::string> > outConnections;" + NL + "        ExtendedCF::UsesConnectionSequence recConnections;" + NL + "        bool recConnectionsRefresh;" + NL + "};" + NL + "#endif";
  protected final String TEXT_121 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_122 = "_";
  protected final String TEXT_123 = "_In_i declaration" + NL + "// ----------------------------------------------------------------------------------------" + NL + "#ifndef _";
  protected final String TEXT_124 = "_";
  protected final String TEXT_125 = "_In_i" + NL + "#define _";
  protected final String TEXT_126 = "_";
  protected final String TEXT_127 = "_In_i" + NL;
  protected final String TEXT_128 = NL + "class BULKIO_dataSDDS_In_i : public POA_BULKIO::dataSDDS, public Port_Provides_base_impl" + NL + "{" + NL + "public:" + NL + "    BULKIO_dataSDDS_In_i(std::string port_name, ";
  protected final String TEXT_129 = "_i *_parent);" + NL + "" + NL + "    ~BULKIO_dataSDDS_In_i();" + NL + "" + NL + "    BULKIO::PortUsageType state();" + NL + "    BULKIO::PortStatistics* statistics();" + NL + "    BULKIO::StreamSRISequence* attachedSRIs();" + NL + "" + NL + "    class linkStatistics" + NL + "    {" + NL + "        public:" + NL + "            struct statPoint {" + NL + "                unsigned int elements;" + NL + "                double secs;" + NL + "                double usecs;" + NL + "            };" + NL + "" + NL + "            linkStatistics() {" + NL + "                bitSize = 8.0;" + NL + "                historyWindow = 10;" + NL + "                receivedStatistics_idx = 0;" + NL + "                receivedStatistics.resize(historyWindow);" + NL + "                activeStreamIDs.resize(0);" + NL + "                runningStats.elementsPerSecond = -1.0;" + NL + "                runningStats.bitsPerSecond = -1.0;" + NL + "                runningStats.callsPerSecond = -1.0;" + NL + "                runningStats.streamIDs.length(0);" + NL + "                runningStats.timeSinceLastCall = -1;" + NL + "                enabled = true;" + NL + "            };" + NL + "" + NL + "            ~linkStatistics() {" + NL + "            }" + NL + "" + NL + "            void setBitSize(double _bitSize) {" + NL + "                bitSize = _bitSize;" + NL + "            }" + NL + "" + NL + "            void setEnabled(bool enable) {" + NL + "                enabled = enable;" + NL + "            }" + NL + "" + NL + "            void update(unsigned int elementsReceived, bool EOS, std::string streamID) {" + NL + "                if (!enabled) {" + NL + "                    return;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "                receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                if (!EOS) {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    bool foundStreamID = false;" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            foundStreamID = true;" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                    if (!foundStreamID) {" + NL + "                        activeStreamIDs.push_back(streamID);" + NL + "                    }" + NL + "                } else {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            activeStreamIDs.erase(p);" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "" + NL + "            BULKIO::PortStatistics retrieve() {" + NL + "                if (!enabled) {" + NL + "                    return runningStats;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "" + NL + "                int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                double front_sec = receivedStatistics[idx].secs;" + NL + "                double front_usec = receivedStatistics[idx].usecs;" + NL + "                double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "                double totalTime = secDiff + usecDiff;" + NL + "                double totalData = 0;" + NL + "                int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                    totalData += receivedStatistics[i].elements;" + NL + "                    i = (i + 1) % historyWindow;" + NL + "                }" + NL + "                runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                runningStats.streamIDs.length(streamIDsize);" + NL + "                for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                    if (p != activeStreamIDs.end()) {" + NL + "                        break;" + NL + "                    }" + NL + "                    runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                    p++;" + NL + "                }" + NL + "                return runningStats;" + NL + "            }" + NL + "" + NL + "        private:" + NL + "            bool enabled;" + NL + "            double bitSize;" + NL + "            BULKIO::PortStatistics runningStats;" + NL + "            std::vector<statPoint> receivedStatistics;" + NL + "            std::list< std::string > activeStreamIDs;" + NL + "            unsigned long historyWindow;" + NL + "            long receivedStatistics_idx;" + NL + "    };" + NL + "" + NL + "    void pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T);" + NL + "" + NL + "    BULKIO::SDDSStreamDefinition* getStreamDefinition(const char* attachId);" + NL + "" + NL + "    char* getUser(const char* attachId);" + NL + "" + NL + "    BULKIO::dataSDDS::InputUsageState usageState();" + NL + "" + NL + "    BULKIO::SDDSStreamSequence* attachedStreams();" + NL + "    " + NL + "    BULKIO::StringSequence* attachmentIds();" + NL + "" + NL + "    char* attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid)" + NL + "        throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError);" + NL + "" + NL + "    void detach(const char* attachId);" + NL + "" + NL + "    void enableStats(bool enable) {" + NL + "        stats.setEnabled(enable);" + NL + "    };" + NL + "" + NL + "    void setBitSize(double bitSize) {" + NL + "        stats.setBitSize(bitSize);" + NL + "    };" + NL + "" + NL + "    void updateStats(unsigned int elementsReceived, bool EOS, std::string streamID) {" + NL + "        boost::mutex::scoped_lock lock(statUpdateLock);" + NL + "        stats.update(elementsReceived, EOS, streamID);" + NL + "    };" + NL + "" + NL + "private:";
  protected final String TEXT_130 = NL + "    ";
  protected final String TEXT_131 = "_i *parent;" + NL + "    // maps a stream ID to a pair of Stream and userID" + NL + "    std::map<std::string, BULKIO::SDDSStreamDefinition*> attachedStreamMap;" + NL + "    std::map<std::string, std::string > attachedUsers;" + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> > currentHs;" + NL + "    boost::mutex statUpdateLock;" + NL + "    boost::mutex sriUpdateLock;" + NL + "    // statistics" + NL + "    linkStatistics stats;" + NL;
  protected final String TEXT_132 = NL + "class ";
  protected final String TEXT_133 = "_";
  protected final String TEXT_134 = "_In_i : public POA_";
  protected final String TEXT_135 = "::";
  protected final String TEXT_136 = ", public Port_Provides_base_impl" + NL + "{" + NL + "    public:";
  protected final String TEXT_137 = NL + "        ";
  protected final String TEXT_138 = "_";
  protected final String TEXT_139 = "_In_i(std::string port_name, ";
  protected final String TEXT_140 = "_i *_parent);" + NL + "        ~";
  protected final String TEXT_141 = "_";
  protected final String TEXT_142 = "_In_i();" + NL + "" + NL + "        void setState(BULKIO::PortUsageType state) { currentState = state; };";
  protected final String TEXT_143 = NL + "        ";
  protected final String TEXT_144 = " ";
  protected final String TEXT_145 = "(";
  protected final String TEXT_146 = ");";
  protected final String TEXT_147 = " ";
  protected final String TEXT_148 = ");";
  protected final String TEXT_149 = ", ";
  protected final String TEXT_150 = NL;
  protected final String TEXT_151 = NL + "        ";
  protected final String TEXT_152 = " ";
  protected final String TEXT_153 = "();";
  protected final String TEXT_154 = NL + "        void ";
  protected final String TEXT_155 = "(";
  protected final String TEXT_156 = " data);" + NL;
  protected final String TEXT_157 = NL + NL + "        class linkStatistics" + NL + "        {" + NL + "            public:" + NL + "                struct statPoint {" + NL + "                    unsigned int elements;" + NL + "                    double secs;" + NL + "                    double usecs;" + NL + "                };" + NL + "" + NL + "                linkStatistics() {" + NL + "                    bitSize = sizeof(";
  protected final String TEXT_158 = ") * 8.0;" + NL + "                    historyWindow = 10;" + NL + "                    receivedStatistics_idx = 0;" + NL + "                    receivedStatistics.resize(historyWindow);" + NL + "                    activeStreamIDs.resize(0);" + NL + "                    runningStats.elementsPerSecond = -1.0;" + NL + "                    runningStats.bitsPerSecond = -1.0;" + NL + "                    runningStats.callsPerSecond = -1.0;" + NL + "                    runningStats.streamIDs.length(0);" + NL + "                    runningStats.timeSinceLastCall = -1;" + NL + "                    enabled = true;" + NL + "                };" + NL + "" + NL + "                ~linkStatistics() {" + NL + "                }" + NL + "" + NL + "                void setEnabled(bool enableStats) {" + NL + "                    enabled = enableStats;" + NL + "                }" + NL + "" + NL + "                void update(unsigned int elementsReceived, bool EOS, std::string streamID) {" + NL + "                    if (!enabled) {" + NL + "                        return;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "                    receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                    receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                    receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                    receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                   if (!EOS) {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        bool foundStreamID = false;" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                foundStreamID = true;" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                        if (!foundStreamID) {" + NL + "                            activeStreamIDs.push_back(streamID);" + NL + "                        }" + NL + "                    } else {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                activeStreamIDs.erase(p);" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                    }" + NL + "                }" + NL + "" + NL + "                BULKIO::PortStatistics retrieve() {" + NL + "                    if (!enabled) {" + NL + "                        return runningStats;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "" + NL + "                    int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                    double front_sec = receivedStatistics[idx].secs;" + NL + "                    double front_usec = receivedStatistics[idx].usecs;" + NL + "                    double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                    double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "                    double totalTime = secDiff + usecDiff;" + NL + "                    double totalData = 0;" + NL + "                    int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                    for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                        totalData += receivedStatistics[i].elements;" + NL + "                        i = (i + 1) % historyWindow;" + NL + "                    }" + NL + "                    runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                    runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                    runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                    runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                    unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                    std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                    runningStats.streamIDs.length(streamIDsize);" + NL + "                    for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                        if (p != activeStreamIDs.end()) {" + NL + "                            break;" + NL + "                        }" + NL + "                        runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                        p++;" + NL + "                    }" + NL + "                    return runningStats;" + NL + "                }" + NL + "" + NL + "            private:" + NL + "                bool enabled;" + NL + "                double bitSize;" + NL + "                BULKIO::PortStatistics runningStats;" + NL + "                std::vector<statPoint> receivedStatistics;" + NL + "                std::list< std::string > activeStreamIDs;" + NL + "                unsigned long historyWindow;" + NL + "                long receivedStatistics_idx;" + NL + "        };" + NL + "        " + NL + "        void enableStats(bool enable) {" + NL + "            stats.setEnabled(enable);" + NL + "        };" + NL + "" + NL + "    private:";
  protected final String TEXT_159 = NL + "        ";
  protected final String TEXT_160 = " new_message;";
  protected final String TEXT_161 = NL + "        ";
  protected final String TEXT_162 = "* new_message;";
  protected final String TEXT_163 = NL + "        ";
  protected final String TEXT_164 = "_i *parent;" + NL + "        BULKIO::StreamSRISequence currentHs;" + NL + "        boost::mutex sriUpdateLock;" + NL + "        bool sriChanged;" + NL + "        BULKIO::PortUsageType currentState;" + NL + "        // statistics" + NL + "        linkStatistics stats;" + NL;
  protected final String TEXT_165 = NL + "    private:";
  protected final String TEXT_166 = NL + "    ";
  protected final String TEXT_167 = " vector_";
  protected final String TEXT_168 = "_";
  protected final String TEXT_169 = ";" + NL + "    ";
  protected final String TEXT_170 = NL;
  protected final String TEXT_171 = NL + "class ";
  protected final String TEXT_172 = "_";
  protected final String TEXT_173 = "_In_i : public POA_";
  protected final String TEXT_174 = "::";
  protected final String TEXT_175 = ", public Port_Provides_base_impl" + NL + "{" + NL + "    public:";
  protected final String TEXT_176 = NL + "        ";
  protected final String TEXT_177 = "_";
  protected final String TEXT_178 = "_In_i(std::string port_name, ";
  protected final String TEXT_179 = "_i *_parent);" + NL + "        ~";
  protected final String TEXT_180 = "_";
  protected final String TEXT_181 = "_In_i();" + NL;
  protected final String TEXT_182 = NL + "        ";
  protected final String TEXT_183 = " ";
  protected final String TEXT_184 = "(";
  protected final String TEXT_185 = ");";
  protected final String TEXT_186 = " ";
  protected final String TEXT_187 = ");";
  protected final String TEXT_188 = ", ";
  protected final String TEXT_189 = NL;
  protected final String TEXT_190 = NL + "        ";
  protected final String TEXT_191 = " ";
  protected final String TEXT_192 = "();";
  protected final String TEXT_193 = NL + "        void ";
  protected final String TEXT_194 = "(";
  protected final String TEXT_195 = " data);" + NL;
  protected final String TEXT_196 = NL + "    private:";
  protected final String TEXT_197 = NL + "    ";
  protected final String TEXT_198 = " vector_";
  protected final String TEXT_199 = "_";
  protected final String TEXT_200 = ";" + NL + "    ";
  protected final String TEXT_201 = NL + "};" + NL + "#endif" + NL;
  protected final String TEXT_202 = NL;
  protected final String TEXT_203 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    boolean memcpyBuffer = false;
    for (Property tempProp : implSettings.getProperties()) {
    	if ("memcpy_buffer".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                memcpyBuffer = true;
                break;
            }
        }
    }

    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_3);
    
    CppHelper _cppHelper = new CppHelper();
    HashSet<String> includeList = new HashSet<String>();    
    HashSet<String> usesList = new HashSet<String>();
    boolean includeQueryablePort = false;
    for (Uses entry : uses) {
        String intName = entry.getRepID();
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
        }
        includeList.add(iface.getNameSpace() + "/" + iface.getFilename());
   	    usesList.add(intName);
        if (!intName.startsWith("IDL:BULKIO/")) {
            includeQueryablePort = true;
        }
    }
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        String intName = entry.getRepID();
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
        }
        includeList.add(iface.getNameSpace() + "/" + iface.getFilename());
        providesList.add(intName);
        if (!intName.startsWith("IDL:BULKIO/")) {
            includeQueryablePort = true;
        }
    }
    
    for (String entry : includeList) {

    stringBuffer.append(TEXT_4);
    stringBuffer.append(entry);
    stringBuffer.append(TEXT_5);
    
    }
    
    if (includeQueryablePort) {

    stringBuffer.append(TEXT_6);
    
    }
    
    for (String entry : usesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();
        String dataTransfer = "";
        String tmpDataTransfer = "";
        String ppDataTransfer = "";
        String rawTransferType = "char";
        boolean isBulkio = "BULKIO".equals(nameSpace);

    stringBuffer.append(TEXT_7);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_13);
    
        if (isBulkio) {

    
        if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_17);
    
        } else {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_25);
    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            if ("pushPacket".equals(op.getName())) {
                if (numParams == 4) {
	                ppDataTransfer = _cppHelper.getCppMapping(op.getParams().get(0).getCxxType());
	                if (ppDataTransfer.startsWith("std::vector")) {
	                    if (ppDataTransfer.endsWith("& ")) {
	                        ppDataTransfer = ppDataTransfer.substring(12, ppDataTransfer.length() - 3) + "*";
	                    } else { 
	                        ppDataTransfer = ppDataTransfer.substring(12, ppDataTransfer.length() - 2) + "*";
	                    }
	                    rawTransferType = ppDataTransfer;
                    } else if ("dataFile".equals(interfaceName)) {
                        ppDataTransfer = "char";
	                }
                }
                dataTransfer = op.getParams().get(0).getCxxType();
                if (dataTransfer.endsWith("&")) {
                   tmpDataTransfer = dataTransfer.substring(6, dataTransfer.length()-1);
                } else {
                   tmpDataTransfer = dataTransfer.substring(6, dataTransfer.length());
                }
                if ("dataFile".equals(interfaceName)) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(op.getParams().get(0).getCxxType());
    stringBuffer.append(TEXT_28);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_32);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_34);
    
                    } else if ("dataXML".equals(interfaceName)) {

    stringBuffer.append(TEXT_35);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_36);
    stringBuffer.append(op.getParams().get(0).getCxxType());
    stringBuffer.append(TEXT_37);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_38);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_44);
    
                } else {

    stringBuffer.append(TEXT_45);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(tmpDataTransfer);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(tmpDataTransfer);
    stringBuffer.append(TEXT_48);
    
                        if (tmpDataTransfer.contains("UlongSequence")) {
                                                                                      
    stringBuffer.append(TEXT_49);
    
                        }
                                                                                      
    stringBuffer.append(TEXT_50);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_54);
    
                }
                continue;
            } // end if opName = pushPacket

    stringBuffer.append(TEXT_55);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_57);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_58);
    
            if (numParams == 0) {

    stringBuffer.append(TEXT_59);
    
            }
            for (int i = 0; i < numParams; i++) {
                if ("pushPacket".equals(op.getName()) && (numParams == 4) && (i == 0)) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());

    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_61);
    
                    continue;
                }

    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_62);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_63);
    
                } else {

    stringBuffer.append(TEXT_64);
    
                }
            } // end for params
        } // end for operations

    stringBuffer.append(TEXT_65);
    stringBuffer.append(rawTransferType);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_73);
      
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            if (!"pushSRI".equals(op.getName()) && !("pushPacket".equals(op.getName()) && (numParams == 4))) {
                for (int i = 0; i < numParams; i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                    if (iteratorBase.length() > 11) {
                        if (iteratorBase.startsWith("std::vector")) {
                            String corbaBase = op.getParams().get(i).getCxxType();
                            int beginingIndex = 0;
                            if (corbaBase.startsWith("const")) {
                                beginingIndex = 6;
                            }
                            if (corbaBase.endsWith("&")) {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()-1));
    
                            } else {

    stringBuffer.append(TEXT_75);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()));
    
                            }

    stringBuffer.append(TEXT_76);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_77);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_78);
    
                        }
                    }
                } // end for params
            } // end if not pushSRI && not pushPacket
        } // end for operations

    stringBuffer.append(TEXT_79);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_81);
    
        } // end else !dataSDDS

    stringBuffer.append(TEXT_82);
    
        } else {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_90);
    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();

    stringBuffer.append(TEXT_91);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_93);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_94);
    
            if (numParams == 0) {

    stringBuffer.append(TEXT_95);
    
            }
            for (int i = 0; i < numParams; i++) {

    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    stringBuffer.append(TEXT_96);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_97);
    
                } else {

    stringBuffer.append(TEXT_98);
    
                }
            } // end for params
        } // end for operations
        
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_99);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_100);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_101);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_102);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_103);
    stringBuffer.append(op.getCxxType());
    stringBuffer.append(TEXT_104);
    
                } // end if readonly
            } // end for attributes

    stringBuffer.append(TEXT_105);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_112);
      
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            if (!"pushSRI".equals(op.getName()) && !("pushPacket".equals(op.getName()) && (numParams == 4))) {
                for (int i = 0; i < numParams; i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                    if (iteratorBase.length() > 11) {
                        if (iteratorBase.startsWith("std::vector")) {
                            String corbaBase = op.getParams().get(i).getCxxType();
                            int beginingIndex = 0;
                            if (corbaBase.startsWith("const")) {
                                beginingIndex = 6;
                            }
                            if (corbaBase.endsWith("&")) {

    stringBuffer.append(TEXT_113);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()-1));
    
                            } else {

    stringBuffer.append(TEXT_114);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()));
    
                            }

    stringBuffer.append(TEXT_115);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_116);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_117);
    
                        }
                    }
                } // end for params
            } // end if not pushSRI && not pushPacket
        } // end for operations


    stringBuffer.append(TEXT_118);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_120);
    
        }
    } // end for uses ports

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
        	throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();
        boolean pushPacketCall = false;
        boolean pushPacketXMLCall = false;
        String dataTransfer = "";
        String ppDataTransfer = "";
        boolean isBulkio = "BULKIO".equals(nameSpace);

    stringBuffer.append(TEXT_121);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_127);
    
        if (isBulkio) {

    
        if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_128);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_131);
    
        } else {

    stringBuffer.append(TEXT_132);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_142);
    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();

    stringBuffer.append(TEXT_143);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_144);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_145);
    
            if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                dataTransfer = _cppHelper.getBaseSequenceMapping(op.getParams().get(0).getCxxType());
                if (dataTransfer.startsWith("std::vector")) {
                    if (ppDataTransfer.endsWith("& ")) {
                        ppDataTransfer = dataTransfer.substring(12, dataTransfer.length() - 2);
                    } else { 
                        ppDataTransfer = dataTransfer.substring(12, dataTransfer.length() - 1);
                    }
                } else if ("dataFile".equals(interfaceName)) {
                    ppDataTransfer = "char";
                }
                pushPacketCall = true;
            } else if ("pushPacket".equals(op.getName()) && "dataXML".equals(interfaceName)) {
                dataTransfer = "char*";
                ppDataTransfer = "char";
                pushPacketXMLCall = true;
            }
            if (numParams == 0) {

    stringBuffer.append(TEXT_146);
    
            }
            for (int i = 0; i < numParams; i++) {

    stringBuffer.append(op.getParams().get(i).getCxxType());
    stringBuffer.append(TEXT_147);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_148);
    
                } else {

    stringBuffer.append(TEXT_149);
    
                }
            } // end for params
        } // end for operations

    stringBuffer.append(TEXT_150);
    
        for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_151);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_152);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_153);
    
            if (!op.isReadonly()) {

    stringBuffer.append(TEXT_154);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_155);
    stringBuffer.append(op.getCxxType());
    stringBuffer.append(TEXT_156);
    
            } // end if readonly
        } // end for attributes
            
        if (pushPacketCall || pushPacketXMLCall) {

    stringBuffer.append(TEXT_157);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_158);
    
            if (memcpyBuffer) {

    stringBuffer.append(TEXT_159);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_160);
    
            } else {

    stringBuffer.append(TEXT_161);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_162);
    
            }

    stringBuffer.append(TEXT_163);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_164);
    
        } else {// end if pushPacket

    stringBuffer.append(TEXT_165);
    
            for (Operation op : iface.getOperations()) {
                ArrayList<String> vectorList = new ArrayList<String>();
                for (int i = 0; i < op.getParams().size(); i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                    if (iteratorBase.length() > 11) {
                        if (iteratorBase.substring(0, 11).equals("std::vector")) {
                            vectorList.add(op.getParams().get(i).getName());
                        }
                    }
                }
                for (int i = 0; i < vectorList.size(); i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
        
    stringBuffer.append(TEXT_166);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_168);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_169);
    
                }
            } // end for Operations
        } // end else !pushPacket
        } // end else !BulkIO

    stringBuffer.append(TEXT_170);
    
        } else {

    stringBuffer.append(TEXT_171);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_181);
    
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();

    stringBuffer.append(TEXT_182);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_183);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_184);
    
            if (numParams == 0) {

    stringBuffer.append(TEXT_185);
    
            }
            for (int i = 0; i < numParams; i++) {

    stringBuffer.append(op.getParams().get(i).getCxxType());
    stringBuffer.append(TEXT_186);
    stringBuffer.append(op.getParams().get(i).getName());
    
                if (i == (numParams - 1)) {

    stringBuffer.append(TEXT_187);
    
                } else {

    stringBuffer.append(TEXT_188);
    
                }
            } // end for params
        } // end for operations

    stringBuffer.append(TEXT_189);
    
        for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_190);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_191);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_192);
    
            if (!op.isReadonly()) {

    stringBuffer.append(TEXT_193);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_194);
    stringBuffer.append(op.getCxxType());
    stringBuffer.append(TEXT_195);
    
            } // end if readonly
        } // end for attributes

    stringBuffer.append(TEXT_196);
    
            for (Operation op : iface.getOperations()) {
                ArrayList<String> vectorList = new ArrayList<String>();
                for (int i = 0; i < op.getParams().size(); i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                    if (iteratorBase.length() > 11) {
                        if (iteratorBase.substring(0, 11).equals("std::vector")) {
                            vectorList.add(op.getParams().get(i).getName());
                        }
                    }
                }
                for (int i = 0; i < vectorList.size(); i++) {
                    String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
        
    stringBuffer.append(TEXT_197);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_199);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_200);
    
                }
            } // end for Operations
        } // end else !BulkIO

    stringBuffer.append(TEXT_201);
    
    } // end for providesList

    stringBuffer.append(TEXT_202);
    stringBuffer.append(TEXT_203);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE