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
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.idl.Attribute;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
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
public class PullPortImplHUsesTemplate
{

  protected static String nl;
  public static synchronized PullPortImplHUsesTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullPortImplHUsesTemplate result = new PullPortImplHUsesTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include \"ossie/MessageInterface.h\"";
  protected final String TEXT_2 = NL + "#include \"COS/";
  protected final String TEXT_3 = ".hh\"";
  protected final String TEXT_4 = NL + "#include \"";
  protected final String TEXT_5 = ".h\"";
  protected final String TEXT_6 = " " + NL + "#include \"struct_props.h\" ";
  protected final String TEXT_7 = NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_8 = "_";
  protected final String TEXT_9 = "_Out_i declaration" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_10 = NL + "class BULKIO_dataSDDS_Out_i : public Port_Uses_base_impl, public virtual POA_BULKIO::UsesPortStatisticsProvider" + NL + "{" + NL + "public:" + NL + "    BULKIO_dataSDDS_Out_i(std::string port_name, ";
  protected final String TEXT_11 = "_base *_parent);" + NL + "" + NL + "    ~BULKIO_dataSDDS_Out_i();" + NL + "" + NL + "    class linkStatistics" + NL + "    {" + NL + "        public:" + NL + "            struct statPoint {" + NL + "                unsigned int elements;" + NL + "                float queueSize;" + NL + "                double secs;" + NL + "                double usecs;" + NL + "            };" + NL + "" + NL + "            linkStatistics() {" + NL + "                bitSize = 8.0;" + NL + "                historyWindow = 10;" + NL + "                activeStreamIDs.resize(0);" + NL + "                receivedStatistics_idx = 0;" + NL + "                receivedStatistics.resize(historyWindow);" + NL + "                runningStats.elementsPerSecond = -1.0;" + NL + "                runningStats.bitsPerSecond = -1.0;" + NL + "                runningStats.callsPerSecond = -1.0;" + NL + "                runningStats.averageQueueDepth = -1.0;" + NL + "                runningStats.streamIDs.length(0);" + NL + "                runningStats.timeSinceLastCall = -1;" + NL + "                enabled = true;" + NL + "            };" + NL + "" + NL + "            void setBitSize(double _bitSize) {" + NL + "                bitSize = _bitSize;" + NL + "            }" + NL + "" + NL + "            void setEnabled(bool enableStats) {" + NL + "                enabled = enableStats;" + NL + "            }" + NL + "" + NL + "            void update(unsigned int elementsReceived, float queueSize, bool EOS, std::string streamID) {" + NL + "                if (!enabled) {" + NL + "                    return;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "                receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                receivedStatistics[receivedStatistics_idx].queueSize = queueSize;" + NL + "                receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                if (!EOS) {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    bool foundStreamID = false;" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            foundStreamID = true;" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                    if (!foundStreamID) {" + NL + "                        activeStreamIDs.push_back(streamID);" + NL + "                    }" + NL + "                } else {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            activeStreamIDs.erase(p);" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                }" + NL + "            };" + NL + "" + NL + "            BULKIO::PortStatistics retrieve() {" + NL + "                if (!enabled) {" + NL + "                    return runningStats;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "" + NL + "                int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                double front_sec = receivedStatistics[idx].secs;" + NL + "                double front_usec = receivedStatistics[idx].usecs;" + NL + "                double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "" + NL + "                double totalTime = secDiff + usecDiff;" + NL + "                double totalData = 0;" + NL + "                float queueSize = 0;" + NL + "                int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                    totalData += receivedStatistics[i].elements;" + NL + "                    queueSize += receivedStatistics[i].queueSize;" + NL + "                    i = (i + 1) % historyWindow;" + NL + "                }" + NL + "                runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                runningStats.averageQueueDepth = (queueSize / historyWindow);" + NL + "                runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                runningStats.streamIDs.length(streamIDsize);" + NL + "                for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                    if (p == activeStreamIDs.end()) {" + NL + "                        break;" + NL + "                    }" + NL + "                    runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                    p++;" + NL + "                }" + NL + "                return runningStats;" + NL + "            };" + NL + "" + NL + "        protected:" + NL + "            bool enabled;" + NL + "            double bitSize;" + NL + "            BULKIO::PortStatistics runningStats;" + NL + "            std::vector<statPoint> receivedStatistics;" + NL + "            std::list< std::string > activeStreamIDs;" + NL + "            unsigned long historyWindow;" + NL + "            int receivedStatistics_idx;" + NL + "    };" + NL + "" + NL + "    BULKIO::UsesPortStatisticsSequence * statistics()" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "        BULKIO::UsesPortStatisticsSequence_var recStat = new BULKIO::UsesPortStatisticsSequence();" + NL + "        recStat->length(outConnections.size());" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            recStat[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "            recStat[i].statistics = stats[outConnections[i].second].retrieve();" + NL + "        }" + NL + "        return recStat._retn();" + NL + "    };" + NL + "" + NL + "    BULKIO::PortUsageType state()" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "        if (outConnections.size() > 0) {" + NL + "            return BULKIO::ACTIVE;" + NL + "        } else {" + NL + "            return BULKIO::IDLE;" + NL + "        }" + NL + "" + NL + "        return BULKIO::BUSY;" + NL + "    };" + NL + "" + NL + "    void enableStats(bool enable)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].setEnabled(enable);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void setBitSize(double bitSize)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].setBitSize(bitSize);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void updateStats(unsigned int elementsReceived, unsigned int queueSize, bool EOS, std::string streamID)" + NL + "    {" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            stats[outConnections[i].second].update(elementsReceived, queueSize, EOS, streamID);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    void connectPort(CORBA::Object_ptr connection, const char* connectionId)" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        BULKIO::dataSDDS_var port = BULKIO::dataSDDS::_narrow(connection);" + NL + "        if (lastStreamData != NULL) {" + NL + "            // TODO - use the username instead" + NL + "            std::string attachId = port->attach(*lastStreamData, user_id.c_str());" + NL + "            attachedGroup.insert(std::make_pair(attachId, std::make_pair(lastStreamData, user_id)));" + NL + "            attachedPorts.insert(std::make_pair(port, attachId));" + NL + "        }" + NL + "        outConnections.push_back(std::make_pair(port, connectionId));" + NL + "        active = true;" + NL + "        recConnectionsRefresh = true;" + NL + "        refreshSRI = true;" + NL + "    };" + NL + "" + NL + "    void disconnectPort(const char* connectionId)" + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "            if (outConnections[i].second == connectionId) {" + NL + "            \tif (attachedPorts.find(outConnections[i].first) != attachedPorts.end()) {" + NL + "                    outConnections[i].first->detach(attachedPorts[outConnections[i].first].c_str());" + NL + "                }" + NL + "                outConnections.erase(outConnections.begin() + i);" + NL + "                break;" + NL + "            }" + NL + "        }" + NL + "" + NL + "        if (outConnections.size() == 0) {" + NL + "            active = false;" + NL + "        }" + NL + "        recConnectionsRefresh = true;" + NL + "    };" + NL + "" + NL + "    ExtendedCF::UsesConnectionSequence * connections() " + NL + "    {" + NL + "        boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "        if (recConnectionsRefresh) {" + NL + "            recConnections.length(outConnections.size());" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                recConnections[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                recConnections[i].port = CORBA::Object::_duplicate(outConnections[i].first);" + NL + "            }" + NL + "            recConnectionsRefresh = false;" + NL + "        }" + NL + "        ExtendedCF::UsesConnectionSequence_var retVal = new ExtendedCF::UsesConnectionSequence(recConnections);" + NL + "        return retVal._retn();" + NL + "    };" + NL + "" + NL + "    std::vector< std::pair<BULKIO::dataSDDS_var, std::string> > _getConnections()" + NL + "    {" + NL + "        return outConnections;" + NL + "    };" + NL + "    " + NL + "    void pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T);" + NL + "" + NL + "    BULKIO::SDDSStreamDefinition* getStreamDefinition(const char* attachId);" + NL + "" + NL + "    char* getUser(const char* attachId);" + NL + "" + NL + "    BULKIO::dataSDDS::InputUsageState usageState();" + NL + "" + NL + "    BULKIO::SDDSStreamSequence* attachedStreams();" + NL + "" + NL + "    BULKIO::StringSequence* attachmentIds();" + NL + "" + NL + "    char* attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid) throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError);" + NL + "" + NL + "    void detach(const char* attachId, const char* connectionId);" + NL + "" + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> > currentSRIs;" + NL + "" + NL + "protected:";
  protected final String TEXT_12 = NL + "    ";
  protected final String TEXT_13 = "_i *parent;" + NL + "    // maps a stream ID to a pair of Stream and userID" + NL + "    std::map<std::string, std::pair<BULKIO::SDDSStreamDefinition*, std::string> > attachedGroup;" + NL + "" + NL + "    BULKIO::SDDSStreamDefinition* lastStreamData;" + NL + "    std::vector < std::pair<BULKIO::dataSDDS_var, std::string> > outConnections;" + NL + "    std::map<BULKIO::dataSDDS::_var_type, std::string> attachedPorts;" + NL + "    std::string user_id;" + NL + "    ExtendedCF::UsesConnectionSequence recConnections;" + NL + "    bool recConnectionsRefresh;" + NL + "    std::map<std::string, linkStatistics> stats;" + NL + "};";
  protected final String TEXT_14 = NL + "class ";
  protected final String TEXT_15 = "_";
  protected final String TEXT_16 = "_Out_i : public MessageSupplierPort" + NL + "{" + NL + "    public:";
  protected final String TEXT_17 = NL + "        ";
  protected final String TEXT_18 = "_";
  protected final String TEXT_19 = "_Out_i(std::string port_name) : MessageSupplierPort(port_name) {" + NL + "        };";
  protected final String TEXT_20 = NL + "        void sendMessage(propertyChange< ";
  protected final String TEXT_21 = " > message) {" + NL + "            CF::Properties outProps;" + NL + "            CORBA::Any data;" + NL + "            outProps.length(1);" + NL + "            outProps[0].id = CORBA::string_dup(message.getId().c_str());" + NL + "            outProps[0].value <<= message;" + NL + "            data <<= outProps;" + NL + "            push(data);" + NL + "        };" + NL + "" + NL + "        void sendMessages(std::vector<propertyChange< ";
  protected final String TEXT_22 = "> > messages) {" + NL + "            CF::Properties outProps;" + NL + "            CORBA::Any data;" + NL + "            outProps.length(messages.size());" + NL + "            for (unsigned int i=0; i<messages.size(); i++) {" + NL + "                outProps[i].id = CORBA::string_dup(messages[i].getId().c_str());" + NL + "                outProps[i].value <<= messages[i];" + NL + "            }" + NL + "            data <<= outProps;" + NL + "            push(data);" + NL + "        };";
  protected final String TEXT_23 = NL + "        void sendMessage(";
  protected final String TEXT_24 = " message) {" + NL + "            CF::Properties outProps;" + NL + "            CORBA::Any data;" + NL + "            outProps.length(1);" + NL + "            outProps[0].id = CORBA::string_dup(message.getId().c_str());" + NL + "            outProps[0].value <<= message;" + NL + "            data <<= outProps;" + NL + "            push(data);" + NL + "        };" + NL + "" + NL + "        void sendMessages(std::vector<";
  protected final String TEXT_25 = "> messages) {" + NL + "            CF::Properties outProps;" + NL + "            CORBA::Any data;" + NL + "            outProps.length(messages.size());" + NL + "            for (unsigned int i=0; i<messages.size(); i++) {" + NL + "                outProps[i].id = CORBA::string_dup(messages[i].getId().c_str());" + NL + "                outProps[i].value <<= messages[i];" + NL + "            }" + NL + "            data <<= outProps;" + NL + "            push(data);" + NL + "        };";
  protected final String TEXT_26 = NL + "};";
  protected final String TEXT_27 = NL + "class ";
  protected final String TEXT_28 = "_";
  protected final String TEXT_29 = "_Out_i : public Port_Uses_base_impl, public ";
  protected final String TEXT_30 = "virtual POA_BULKIO::UsesPortStatisticsProvider";
  protected final String TEXT_31 = "POA_ExtendedCF::QueryablePort";
  protected final String TEXT_32 = NL + "{" + NL + "    public:";
  protected final String TEXT_33 = NL + "        ";
  protected final String TEXT_34 = "_";
  protected final String TEXT_35 = "_Out_i(std::string port_name, ";
  protected final String TEXT_36 = "_base *_parent);" + NL + "        ~";
  protected final String TEXT_37 = "_";
  protected final String TEXT_38 = "_Out_i();";
  protected final String TEXT_39 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  ";
  protected final String TEXT_40 = ": structure containing the payload to send out" + NL + "         *  T: constant of type BULKIO::PrecisionUTCTime containing the timestamp for the outgoing data." + NL + "         *    tcmode: timecode mode" + NL + "         *    tcstatus: timecode status " + NL + "         *    toff: fractional sample offset" + NL + "         *    twsec: J1970 GMT " + NL + "         *    tfsec: fractional seconds: 0.0 to 1.0" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        void pushPacket(";
  protected final String TEXT_41 = " ";
  protected final String TEXT_42 = ", const BULKIO::PrecisionUTCTime& T, bool EOS, const std::string& streamID) {" + NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) == currentSRIs.end()) {" + NL + "                    BULKIO::StreamSRI sri;" + NL + "                    sri.hversion = 1;" + NL + "                    sri.xstart = 0.0;" + NL + "                    sri.xdelta = 1.0;" + NL + "                    sri.xunits = BULKIO::UNITS_TIME;" + NL + "                    sri.subsize = 0;" + NL + "                    sri.ystart = 0.0;" + NL + "                    sri.ydelta = 0.0;" + NL + "                    sri.yunits = BULKIO::UNITS_NONE;" + NL + "                    sri.mode = 0;" + NL + "                    sri.blocking = false;" + NL + "                    sri.streamID = streamID.c_str();" + NL + "                    currentSRIs[streamID] = sri;" + NL + "                }" + NL + "                pushSRI(currentSRIs[streamID]);" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_43 = "::";
  protected final String TEXT_44 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(";
  protected final String TEXT_45 = ", T, EOS, streamID.c_str());" + NL + "                        stats[(*port).second].update(1, 0, EOS, streamID);" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_46 = "_";
  protected final String TEXT_47 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "            // for end of stream,  remove old sri" + NL + "            try {" + NL + "                if ( EOS ) currentSRIs.erase(streamID);" + NL + "            }" + NL + "            catch(...){" + NL + "            }" + NL + "        };";
  protected final String TEXT_48 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  ";
  protected final String TEXT_49 = ": structure containing the payload to send out" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        void pushPacket(";
  protected final String TEXT_50 = " ";
  protected final String TEXT_51 = ", bool EOS, const std::string& streamID) {" + NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) == currentSRIs.end()) {" + NL + "                    BULKIO::StreamSRI sri;" + NL + "                    sri.hversion = 1;" + NL + "                    sri.xstart = 0.0;" + NL + "                    sri.xdelta = 0.0;" + NL + "                    sri.xunits = BULKIO::UNITS_TIME;" + NL + "                    sri.subsize = 0;" + NL + "                    sri.ystart = 0.0;" + NL + "                    sri.ydelta = 0.0;" + NL + "                    sri.yunits = BULKIO::UNITS_NONE;" + NL + "                    sri.mode = 0;" + NL + "                    sri.blocking = false;" + NL + "                    sri.streamID = streamID.c_str();" + NL + "                    struct timeval tmp_time;" + NL + "                    struct timezone tmp_tz;" + NL + "                    gettimeofday(&tmp_time, &tmp_tz);" + NL + "                    double wsec = tmp_time.tv_sec;" + NL + "                    double fsec = tmp_time.tv_usec / 1e6;;" + NL + "                    BULKIO::PrecisionUTCTime tstamp = BULKIO::PrecisionUTCTime();" + NL + "                    tstamp.tcmode = BULKIO::TCM_CPU;" + NL + "                    tstamp.tcstatus = (short)1;" + NL + "                    tstamp.toff = 0.0;" + NL + "                    tstamp.twsec = wsec;" + NL + "                    tstamp.tfsec = fsec;" + NL + "                    currentSRIs[streamID] = sri;" + NL + "                }" + NL + "                pushSRI(currentSRIs[streamID]);" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_52 = "::";
  protected final String TEXT_53 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(";
  protected final String TEXT_54 = ", EOS, streamID.c_str());" + NL + "                        stats[(*port).second].update(strlen(";
  protected final String TEXT_55 = "), 0, EOS, streamID);" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_56 = "_";
  protected final String TEXT_57 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "            // for end of stream,  remove old sri" + NL + "            try {" + NL + "                if ( EOS ) currentSRIs.erase(streamID);" + NL + "            }" + NL + "            catch(...){" + NL + "            }" + NL + "" + NL + "        };";
  protected final String TEXT_58 = NL + "        " + NL + "        /*" + NL + "         * pushPacket" + NL + "         *     description: push data out of the port" + NL + "         *" + NL + "         *  data: structure containing the payload to send out" + NL + "         *  T: constant of type BULKIO::PrecisionUTCTime containing the timestamp for the outgoing data." + NL + "         *    tcmode: timecode mode" + NL + "         *    tcstatus: timecode status " + NL + "         *    toff: fractional sample offset" + NL + "         *    twsec: J1970 GMT " + NL + "         *    tfsec: fractional seconds: 0.0 to 1.0" + NL + "         *  EOS: end-of-stream flag" + NL + "         *  streamID: stream identifier" + NL + "         */" + NL + "        template <typename ALLOCATOR>";
  protected final String TEXT_59 = NL + "        void pushPacket(";
  protected final String TEXT_60 = " data, BULKIO::PrecisionUTCTime& T, bool EOS, const std::string& streamID) {";
  protected final String TEXT_61 = NL + "        void pushPacket(";
  protected final String TEXT_62 = " data, BULKIO::PrecisionUTCTime& T, bool EOS, const std::string& streamID) {";
  protected final String TEXT_63 = NL + "            if (refreshSRI) {" + NL + "                if (currentSRIs.find(streamID) == currentSRIs.end()) {" + NL + "                    BULKIO::StreamSRI sri;" + NL + "                    sri.hversion = 1;" + NL + "                    sri.xstart = 0.0;" + NL + "                    sri.xdelta = 1.0;" + NL + "                    sri.xunits = BULKIO::UNITS_TIME;" + NL + "                    sri.subsize = 0;" + NL + "                    sri.ystart = 0.0;" + NL + "                    sri.ydelta = 0.0;" + NL + "                    sri.yunits = BULKIO::UNITS_NONE;" + NL + "                    sri.mode = 0;" + NL + "                    sri.blocking = false;" + NL + "                    sri.streamID = streamID.c_str();" + NL + "                    currentSRIs[streamID] = sri;" + NL + "                }" + NL + "                pushSRI(currentSRIs[streamID]);" + NL + "            }" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            // Magic is below, make a new sequence using the data from the Iterator" + NL + "            // as the data for the sequence.  The 'false' at the end is whether or not" + NL + "            // CORBA is allowed to delete the buffer when the sequence is destroyed.";
  protected final String TEXT_64 = NL + "            ";
  protected final String TEXT_65 = " seq = ";
  protected final String TEXT_66 = "(data.size(), data.size(), ";
  protected final String TEXT_67 = "(CORBA::ULong*)";
  protected final String TEXT_68 = "(CORBA::Char*)";
  protected final String TEXT_69 = "&(data[0]), false);" + NL + "            if (active) {" + NL + "                std::vector < std::pair < ";
  protected final String TEXT_70 = "::";
  protected final String TEXT_71 = "_var, std::string > >::iterator port;" + NL + "                for (port = outConnections.begin(); port != outConnections.end(); port++) {" + NL + "                    try {" + NL + "                        ((*port).first)->pushPacket(seq, T, EOS, streamID.c_str());" + NL + "                        stats[(*port).second].update(data.size(), 0, EOS, streamID);" + NL + "                    } catch(...) {" + NL + "                        std::cout << \"Call to pushPacket by ";
  protected final String TEXT_72 = "_";
  protected final String TEXT_73 = "_Out_i failed\" << std::endl;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "            // for end of stream,  remove old sri" + NL + "            try {" + NL + "                if ( EOS ) currentSRIs.erase(streamID);" + NL + "            }" + NL + "            catch(...){" + NL + "            }" + NL + "" + NL + "        };";
  protected final String TEXT_74 = NL;
  protected final String TEXT_75 = NL + "        ";
  protected final String TEXT_76 = " ";
  protected final String TEXT_77 = "(";
  protected final String TEXT_78 = ");";
  protected final String TEXT_79 = "::iterator begin, ";
  protected final String TEXT_80 = "::iterator end,";
  protected final String TEXT_81 = " ";
  protected final String TEXT_82 = ");";
  protected final String TEXT_83 = ", ";
  protected final String TEXT_84 = NL + "        ";
  protected final String TEXT_85 = " ";
  protected final String TEXT_86 = "();" + NL;
  protected final String TEXT_87 = NL + "        void ";
  protected final String TEXT_88 = "(";
  protected final String TEXT_89 = " data);" + NL;
  protected final String TEXT_90 = NL + "        class linkStatistics" + NL + "        {" + NL + "            public:" + NL + "                struct statPoint {" + NL + "                    unsigned int elements;" + NL + "                    float queueSize;" + NL + "                    double secs;" + NL + "                    double usecs;" + NL + "                };" + NL + "                " + NL + "                linkStatistics() {";
  protected final String TEXT_91 = NL + "                    bitSize = sizeof(char) * 8.0;";
  protected final String TEXT_92 = NL + "                    bitSize = sizeof(";
  protected final String TEXT_93 = ") * 8.0;";
  protected final String TEXT_94 = NL + "                    historyWindow = 10;" + NL + "                    activeStreamIDs.resize(0);" + NL + "                    receivedStatistics_idx = 0;" + NL + "                    receivedStatistics.resize(historyWindow);" + NL + "                    runningStats.elementsPerSecond = -1.0;" + NL + "                    runningStats.bitsPerSecond = -1.0;" + NL + "                    runningStats.callsPerSecond = -1.0;" + NL + "                    runningStats.averageQueueDepth = -1.0;" + NL + "                    runningStats.streamIDs.length(0);" + NL + "                    runningStats.timeSinceLastCall = -1;" + NL + "                    enabled = true;" + NL + "                };" + NL + "" + NL + "                void setEnabled(bool enableStats) {" + NL + "                    enabled = enableStats;" + NL + "                }" + NL + "" + NL + "                void update(unsigned int elementsReceived, float queueSize, bool EOS, std::string streamID) {" + NL + "                    if (!enabled) {" + NL + "                        return;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "                    receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                    receivedStatistics[receivedStatistics_idx].queueSize = queueSize;" + NL + "                    receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                    receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                    receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                    if (!EOS) {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        bool foundStreamID = false;" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                foundStreamID = true;" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                        if (!foundStreamID) {" + NL + "                            activeStreamIDs.push_back(streamID);" + NL + "                        }" + NL + "                    } else {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                activeStreamIDs.erase(p);" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                    }" + NL + "                };" + NL + "" + NL + "                BULKIO::PortStatistics retrieve() {" + NL + "                    if (!enabled) {" + NL + "                        return runningStats;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "" + NL + "                    int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                    double front_sec = receivedStatistics[idx].secs;" + NL + "                    double front_usec = receivedStatistics[idx].usecs;" + NL + "                    double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                    double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "" + NL + "                    double totalTime = secDiff + usecDiff;" + NL + "                    double totalData = 0;" + NL + "                    float queueSize = 0;" + NL + "                    int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                    for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                        totalData += receivedStatistics[i].elements;" + NL + "                        queueSize += receivedStatistics[i].queueSize;" + NL + "                        i = (i + 1) % historyWindow;" + NL + "                    }" + NL + "                    runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                    runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                    runningStats.averageQueueDepth = (queueSize / historyWindow);" + NL + "                    runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                    runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                    unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                    std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                    runningStats.streamIDs.length(streamIDsize);" + NL + "                    for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                        if (p == activeStreamIDs.end()) {" + NL + "                            break;" + NL + "                        }" + NL + "                        runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                        p++;" + NL + "                    }" + NL + "                    return runningStats;" + NL + "                };" + NL + "" + NL + "            protected:" + NL + "                bool enabled;" + NL + "                double bitSize;" + NL + "                BULKIO::PortStatistics runningStats;" + NL + "                std::vector<statPoint> receivedStatistics;" + NL + "                std::list< std::string > activeStreamIDs;" + NL + "                unsigned long historyWindow;" + NL + "                int receivedStatistics_idx;" + NL + "        };" + NL + "" + NL + "        BULKIO::UsesPortStatisticsSequence * statistics()" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "            BULKIO::UsesPortStatisticsSequence_var recStat = new BULKIO::UsesPortStatisticsSequence();" + NL + "            recStat->length(outConnections.size());" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                recStat[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                recStat[i].statistics = stats[outConnections[i].second].retrieve();" + NL + "            }" + NL + "            return recStat._retn();" + NL + "        };" + NL + "" + NL + "        BULKIO::PortUsageType state()" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);" + NL + "            if (outConnections.size() > 0) {" + NL + "                return BULKIO::ACTIVE;" + NL + "            } else {" + NL + "                return BULKIO::IDLE;" + NL + "            }" + NL + "" + NL + "            return BULKIO::BUSY;" + NL + "        };" + NL + "        " + NL + "        void enableStats(bool enable)" + NL + "        {" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                stats[outConnections[i].second].setEnabled(enable);" + NL + "            }" + NL + "        };" + NL;
  protected final String TEXT_95 = NL + NL + "        ExtendedCF::UsesConnectionSequence * connections() " + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            if (recConnectionsRefresh) {" + NL + "                recConnections.length(outConnections.size());" + NL + "                for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                    recConnections[i].connectionId = CORBA::string_dup(outConnections[i].second.c_str());" + NL + "                    recConnections[i].port = CORBA::Object::_duplicate(outConnections[i].first);" + NL + "                }" + NL + "                recConnectionsRefresh = false;" + NL + "            }" + NL + "            ExtendedCF::UsesConnectionSequence_var retVal = new ExtendedCF::UsesConnectionSequence(recConnections);" + NL + "            // NOTE: You must delete the object that this function returns!" + NL + "            return retVal._retn();" + NL + "        };" + NL + "" + NL + "        void connectPort(CORBA::Object_ptr connection, const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in";
  protected final String TEXT_96 = NL + "            ";
  protected final String TEXT_97 = "::";
  protected final String TEXT_98 = "_var port = ";
  protected final String TEXT_99 = "::";
  protected final String TEXT_100 = "::_narrow(connection);" + NL + "            outConnections.push_back(std::make_pair(port, connectionId));" + NL + "            active = true;" + NL + "            recConnectionsRefresh = true;";
  protected final String TEXT_101 = NL + "            refreshSRI = true;";
  protected final String TEXT_102 = NL + "        };" + NL + "" + NL + "        void disconnectPort(const char* connectionId)" + NL + "        {" + NL + "            boost::mutex::scoped_lock lock(updatingPortsLock);   // don't want to process while command information is coming in" + NL + "            for (unsigned int i = 0; i < outConnections.size(); i++) {" + NL + "                if (outConnections[i].second == connectionId) {" + NL + "                    outConnections.erase(outConnections.begin() + i);" + NL + "                    break;" + NL + "                }" + NL + "            }" + NL + "" + NL + "            if (outConnections.size() == 0) {" + NL + "                active = false;" + NL + "            }" + NL + "            recConnectionsRefresh = true;" + NL + "        };" + NL + "" + NL + "        std::vector< std::pair<";
  protected final String TEXT_103 = "::";
  protected final String TEXT_104 = "_var, std::string> > _getConnections()" + NL + "        {" + NL + "            return outConnections;" + NL + "        };";
  protected final String TEXT_105 = NL + "        std::map<std::string, BULKIO::StreamSRI> currentSRIs;";
  protected final String TEXT_106 = NL + NL + "    protected:";
  protected final String TEXT_107 = NL + "        ";
  protected final String TEXT_108 = "_i *parent;" + NL + "        std::vector < std::pair<";
  protected final String TEXT_109 = "::";
  protected final String TEXT_110 = "_var, std::string> > outConnections;" + NL + "        ExtendedCF::UsesConnectionSequence recConnections;" + NL + "        bool recConnectionsRefresh;";
  protected final String TEXT_111 = NL + "        std::map<std::string, linkStatistics> stats;";
  protected final String TEXT_112 = NL + "    ";
  protected final String TEXT_113 = NL + "    ";
  protected final String TEXT_114 = " Sequence_";
  protected final String TEXT_115 = "_";
  protected final String TEXT_116 = "; ";
  protected final String TEXT_117 = NL + "};";
  protected final String TEXT_118 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    Implementation impl = templ.getImpl();
    ImplementationSettings implSettings = templ.getImplSettings();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    List<CppProperties.Property> properties = CppProperties.getProperties(softPkg);
    Uses use = null;
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    CppHelper _cppHelper = new CppHelper();
    for (Uses entry : uses) {
        String intName = entry.getRepID();
        if (intName.equals(templ.getPortRepId())) {
            use = entry;
            if (templ.isGenSupport()) {
                Interface intf = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
                if (intf == null) {
                    throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
                }
                if (intf.getNameSpace().equals("ExtendedEvent")) {

    stringBuffer.append(TEXT_1);
    
                	continue;
                }
                if (intf.getFullPath().contains("/COS/")) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(intf.getFilename());
    stringBuffer.append(TEXT_3);
    
                } else {

    stringBuffer.append(TEXT_4);
    stringBuffer.append(intf.getNameSpace() + "/" + intf.getFilename());
    stringBuffer.append(TEXT_5);
    
            	}
            }
            break;
        }
    }

	for (CppProperties.Property prop : properties) { 
		if (prop.getKinds().indexOf("message") != -1) { 

    stringBuffer.append(TEXT_6);
     
			break; 
		} 
	} 

    if (use != null && templ.isGenClassDef()) {
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
        if (intf == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + use.getRepID()));
        }

        String nameSpace = intf.getNameSpace();
        String interfaceName = intf.getName();
        boolean pushPacketCall = false;
        boolean isBULKIO = "BULKIO".equals(nameSpace);
        String dataTransfer = "";
        String tmpDataTransfer = "";
        String rawTransferType = "char";

    stringBuffer.append(TEXT_7);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_9);
    
        if (isBULKIO && "dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
    
        } else if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_14);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_19);
    
		for (CppProperties.Property prop : properties) {
			if (prop.getKinds().indexOf("message") != -1) {
        		if (prop.getKinds().indexOf("event") != -1) {         

    stringBuffer.append(TEXT_20);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_22);
    
				} else {

    stringBuffer.append(TEXT_23);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_25);
    
				}
			}
		}

    stringBuffer.append(TEXT_26);
    
        } else {
            String ppDataTransfer = "";

    stringBuffer.append(TEXT_27);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_29);
    if (isBULKIO) {
    stringBuffer.append(TEXT_30);
    } else {
    stringBuffer.append(TEXT_31);
    }
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_38);
    
            for (Operation op : intf.getOperations()) {
                int numParams = op.getParams().size();
                if ("pushPacket".equals(op.getName())) {
                    if (numParams == 4) {
                        ppDataTransfer = _cppHelper.getCppMapping(op.getParams().get(0).getCxxType());
                        if (ppDataTransfer.startsWith("std::vector")) {
                            if (ppDataTransfer.endsWith("& ")) {
                                rawTransferType = ppDataTransfer.substring(12, ppDataTransfer.length() - 3);
                            } else { 
                                rawTransferType = ppDataTransfer.substring(12, ppDataTransfer.length() - 2);
                            }
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
                    pushPacketCall = true;
                    if ("dataFile".equals(interfaceName)) {

    stringBuffer.append(TEXT_39);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_40);
    stringBuffer.append(op.getParams().get(0).getCxxType());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_45);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_47);
    
                        continue;
                    } else if ("dataXML".equals(interfaceName)) {

    stringBuffer.append(TEXT_48);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_49);
    stringBuffer.append(op.getParams().get(0).getCxxType());
    stringBuffer.append(TEXT_50);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_51);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_54);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_55);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_57);
    
                        continue;
                    } else {

    stringBuffer.append(TEXT_58);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_59);
    stringBuffer.append(_cppHelper.vectorize("std::vector<char>&"));
    stringBuffer.append(TEXT_60);
    
                    } else {

    stringBuffer.append(TEXT_61);
    stringBuffer.append(_cppHelper.vectorize(ppDataTransfer.trim()));
    stringBuffer.append(TEXT_62);
    
                    }

    stringBuffer.append(TEXT_63);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(tmpDataTransfer);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(tmpDataTransfer);
    stringBuffer.append(TEXT_66);
    
                        if (tmpDataTransfer.contains("UlongSequence")) {
                                                                                      
    stringBuffer.append(TEXT_67);
    
                        } else if (tmpDataTransfer.contains("PortTypes::CharSequence")) {
                                                                                      
    stringBuffer.append(TEXT_68);
    
                        }
                                                                                      
    stringBuffer.append(TEXT_69);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_73);
    
                        continue;
                    }
                } // end if opName = pushPacket

    stringBuffer.append(TEXT_74);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_76);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_77);
    
                if (numParams == 0) { 
    stringBuffer.append(TEXT_78);
    
                }
                for (int i = 0; i < numParams; i++) {
                    if ("pushPacket".equals(op.getName()) && (numParams == 4) && (i == 0)) {
                        String iteratorBase = _cppHelper.getBaseSequenceMapping(op.getParams().get(i).getCxxType());
                        pushPacketCall = true;
        
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_80);
    
                        continue;
                    }
        
    stringBuffer.append(_cppHelper.getCppMapping(op.getParams().get(i).getCxxType()));
    
        
    stringBuffer.append(TEXT_81);
    stringBuffer.append(op.getParams().get(i).getName());
    
                    if (i == (numParams - 1)) {
                        
    stringBuffer.append(TEXT_82);
    
                    } else {
                        
    stringBuffer.append(TEXT_83);
    
                    }
                } // end for params
            } // end for operations

            if (!isBULKIO) {
                for (Attribute op : intf.getAttributes()) {

    stringBuffer.append(TEXT_84);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.varReturnValue(op.getCxxReturnType(), op.getReturnType()));
    stringBuffer.append(TEXT_85);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_86);
    
                    if (!op.isReadonly()) {

    stringBuffer.append(TEXT_87);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_88);
    stringBuffer.append(op.getCxxType());
    stringBuffer.append(TEXT_89);
    
                    } // end if readonly
                } // end for attributes
            } else {

    stringBuffer.append(TEXT_90);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_91);
    
                    } else {

    stringBuffer.append(TEXT_92);
    stringBuffer.append(rawTransferType);
    stringBuffer.append(TEXT_93);
    
                    }

    stringBuffer.append(TEXT_94);
    
            } // end ifBULKIO

    stringBuffer.append(TEXT_95);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_100);
    
            if (pushPacketCall) {

    stringBuffer.append(TEXT_101);
    
            }

    stringBuffer.append(TEXT_102);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_104);
    
            if (isBULKIO) {

    stringBuffer.append(TEXT_105);
    
            } // end if isBULKIO

    stringBuffer.append(TEXT_106);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_110);
    
            if (isBULKIO) {

    stringBuffer.append(TEXT_111);
    
            } // end if isBULKIO

            for (Operation op : intf.getOperations()) {
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
                            
    stringBuffer.append(TEXT_112);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()-1));
    
                                } else {
                            
    stringBuffer.append(TEXT_113);
    stringBuffer.append(corbaBase.substring(beginingIndex, corbaBase.length()));
    
                                }
                        
    stringBuffer.append(TEXT_114);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_115);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_116);
    
                            }
                        }
                    } // end for params
                } // end if not pushSRI && not pushPacket
            } // end for operations


    stringBuffer.append(TEXT_117);
    
        }
    } // end if genClassDef

    stringBuffer.append(TEXT_118);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE