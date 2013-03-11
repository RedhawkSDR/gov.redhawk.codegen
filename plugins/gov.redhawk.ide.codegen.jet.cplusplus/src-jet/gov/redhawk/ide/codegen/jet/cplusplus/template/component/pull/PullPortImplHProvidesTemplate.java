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
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties;
import gov.redhawk.ide.idl.Attribute;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mil.jpeojtrs.sca.scd.Provides;
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
public class PullPortImplHProvidesTemplate
{

  protected static String nl;
  public static synchronized PullPortImplHProvidesTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullPortImplHProvidesTemplate result = new PullPortImplHProvidesTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include \"ossie/MessageInterface.h\"";
  protected final String TEXT_2 = NL + "#include \"COS/";
  protected final String TEXT_3 = ".hh\"";
  protected final String TEXT_4 = NL + "#include \"";
  protected final String TEXT_5 = ".h\"";
  protected final String TEXT_6 = NL + NL + "// ----------------------------------------------------------------------------------------" + NL + "// ";
  protected final String TEXT_7 = "_";
  protected final String TEXT_8 = "_In_i declaration" + NL + "// ----------------------------------------------------------------------------------------";
  protected final String TEXT_9 = NL + "class BULKIO_dataSDDS_In_i : public POA_BULKIO::dataSDDS, public Port_Provides_base_impl" + NL + "{" + NL + "public:" + NL + "    BULKIO_dataSDDS_In_i(std::string port_name, ";
  protected final String TEXT_10 = "_base *_parent);" + NL + "" + NL + "    ~BULKIO_dataSDDS_In_i();" + NL + "" + NL + "    BULKIO::PortUsageType state();" + NL + "    BULKIO::PortStatistics* statistics();" + NL + "    BULKIO::StreamSRISequence* attachedSRIs();" + NL + "" + NL + "    class linkStatistics" + NL + "    {" + NL + "        public:" + NL + "            struct statPoint {" + NL + "                unsigned int elements;" + NL + "                float queueSize;" + NL + "                double secs;" + NL + "                double usecs;" + NL + "            };" + NL + "" + NL + "            linkStatistics() {" + NL + "                bitSize = 8.0;" + NL + "                historyWindow = 10;" + NL + "                receivedStatistics_idx = 0;" + NL + "                receivedStatistics.resize(historyWindow);" + NL + "                activeStreamIDs.resize(0);" + NL + "                runningStats.elementsPerSecond = -1.0;" + NL + "                runningStats.bitsPerSecond = -1.0;" + NL + "                runningStats.callsPerSecond = -1.0;" + NL + "                runningStats.averageQueueDepth = -1.0;" + NL + "                runningStats.streamIDs.length(0);" + NL + "                runningStats.timeSinceLastCall = -1;" + NL + "                enabled = true;" + NL + "            };" + NL + "" + NL + "            ~linkStatistics() {" + NL + "            }" + NL + "" + NL + "            void setBitSize(double _bitSize) {" + NL + "                bitSize = _bitSize;" + NL + "            }" + NL + "" + NL + "            void setEnabled(bool enable) {" + NL + "                enabled = enable;" + NL + "            }" + NL + "" + NL + "            void update(unsigned int elementsReceived, float queueSize, bool EOS, std::string streamID) {" + NL + "                if (!enabled) {" + NL + "                    return;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "                receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                receivedStatistics[receivedStatistics_idx].queueSize = queueSize;" + NL + "                receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                if (!EOS) {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    bool foundStreamID = false;" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            foundStreamID = true;" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                    if (!foundStreamID) {" + NL + "                        activeStreamIDs.push_back(streamID);" + NL + "                    }" + NL + "                } else {" + NL + "                    std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                    while (p != activeStreamIDs.end()) {" + NL + "                        if (*p == streamID) {" + NL + "                            activeStreamIDs.erase(p);" + NL + "                            break;" + NL + "                        }" + NL + "                        p++;" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "" + NL + "            BULKIO::PortStatistics retrieve() {" + NL + "                if (!enabled) {" + NL + "                    return runningStats;" + NL + "                }" + NL + "                struct timeval tv;" + NL + "                struct timezone tz;" + NL + "                gettimeofday(&tv, &tz);" + NL + "" + NL + "                int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                double front_sec = receivedStatistics[idx].secs;" + NL + "                double front_usec = receivedStatistics[idx].usecs;" + NL + "                double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "                double totalTime = secDiff + usecDiff;" + NL + "                double totalData = 0;" + NL + "                float queueSize = 0;" + NL + "                int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                    totalData += receivedStatistics[i].elements;" + NL + "                    queueSize += receivedStatistics[i].queueSize;" + NL + "                    i = (i + 1) % historyWindow;" + NL + "                }" + NL + "                runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                runningStats.averageQueueDepth = (queueSize / historyWindow);" + NL + "                runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                runningStats.streamIDs.length(streamIDsize);" + NL + "                for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                    if (p == activeStreamIDs.end()) {" + NL + "                        break;" + NL + "                    }" + NL + "                    runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                    p++;" + NL + "                }" + NL + "                return runningStats;" + NL + "            }" + NL + "" + NL + "        protected:" + NL + "            bool enabled;" + NL + "            double bitSize;" + NL + "            BULKIO::PortStatistics runningStats;" + NL + "            std::vector<statPoint> receivedStatistics;" + NL + "            std::list< std::string > activeStreamIDs;" + NL + "            unsigned long historyWindow;" + NL + "            long receivedStatistics_idx;" + NL + "    };" + NL + "" + NL + "    void pushSRI(const BULKIO::StreamSRI& H, const BULKIO::PrecisionUTCTime& T);" + NL + "" + NL + "    BULKIO::SDDSStreamDefinition* getStreamDefinition(const char* attachId);" + NL + "" + NL + "    char* getUser(const char* attachId);" + NL + "" + NL + "    BULKIO::dataSDDS::InputUsageState usageState();" + NL + "" + NL + "    BULKIO::SDDSStreamSequence* attachedStreams();" + NL + "    " + NL + "    BULKIO::StringSequence* attachmentIds();" + NL + "" + NL + "    char* attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid)" + NL + "        throw (BULKIO::dataSDDS::AttachError, BULKIO::dataSDDS::StreamInputError);" + NL + "" + NL + "    void detach(const char* attachId);" + NL + "" + NL + "    void enableStats(bool enable) {" + NL + "        stats.setEnabled(enable);" + NL + "    };" + NL + "" + NL + "    void setBitSize(double bitSize) {" + NL + "        stats.setBitSize(bitSize);" + NL + "    };" + NL + "" + NL + "    void updateStats(unsigned int elementsReceived, float queueSize, bool EOS, std::string streamID) {" + NL + "        boost::mutex::scoped_lock lock(statUpdateLock);" + NL + "        stats.update(elementsReceived, queueSize, EOS, streamID);" + NL + "    };" + NL + "" + NL + "protected:";
  protected final String TEXT_11 = NL + "    ";
  protected final String TEXT_12 = "_i *parent;" + NL + "    // maps a stream ID to a pair of Stream and userID" + NL + "    std::map<std::string, BULKIO::SDDSStreamDefinition*> attachedStreamMap;" + NL + "    std::map<std::string, std::string > attachedUsers;" + NL + "    std::map<std::string, std::pair<BULKIO::StreamSRI, BULKIO::PrecisionUTCTime> > currentHs;" + NL + "    boost::mutex statUpdateLock;" + NL + "    boost::mutex sriUpdateLock;" + NL + "    // statistics" + NL + "    linkStatistics stats;" + NL + "" + NL + "};";
  protected final String TEXT_13 = NL + "class ";
  protected final String TEXT_14 = "_";
  protected final String TEXT_15 = "_In_i : public POA_";
  protected final String TEXT_16 = "::";
  protected final String TEXT_17 = ", public Port_Provides_base_impl" + NL + "{" + NL + "    public:";
  protected final String TEXT_18 = NL + "        ";
  protected final String TEXT_19 = "_";
  protected final String TEXT_20 = "_In_i(std::string port_name, ";
  protected final String TEXT_21 = "_base *_parent);" + NL + "        ~";
  protected final String TEXT_22 = "_";
  protected final String TEXT_23 = "_In_i();" + NL;
  protected final String TEXT_24 = NL + "        ";
  protected final String TEXT_25 = " ";
  protected final String TEXT_26 = "(";
  protected final String TEXT_27 = ");";
  protected final String TEXT_28 = " ";
  protected final String TEXT_29 = ");";
  protected final String TEXT_30 = ", ";
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = NL + "        ";
  protected final String TEXT_33 = " ";
  protected final String TEXT_34 = "();";
  protected final String TEXT_35 = NL + "        void ";
  protected final String TEXT_36 = "(";
  protected final String TEXT_37 = " data);" + NL;
  protected final String TEXT_38 = NL + "        int getCurrentQueueDepth();" + NL + "        int getMaxQueueDepth();" + NL + "        void setMaxQueueDepth(int newDepth);" + NL + "" + NL + "        class linkStatistics" + NL + "        {" + NL + "            public:" + NL + "                struct statPoint {" + NL + "                    unsigned int elements;" + NL + "                    float queueSize;" + NL + "                    double secs;" + NL + "                    double usecs;" + NL + "                };" + NL + "" + NL + "                linkStatistics() {";
  protected final String TEXT_39 = NL + "                    bitSize = sizeof(char) * 8.0;";
  protected final String TEXT_40 = NL + "                    bitSize = sizeof(";
  protected final String TEXT_41 = ") * 8.0;";
  protected final String TEXT_42 = NL + "                    historyWindow = 10;" + NL + "                    receivedStatistics_idx = 0;" + NL + "                    receivedStatistics.resize(historyWindow);" + NL + "                    activeStreamIDs.resize(0);" + NL + "                    runningStats.elementsPerSecond = -1.0;" + NL + "                    runningStats.bitsPerSecond = -1.0;" + NL + "                    runningStats.callsPerSecond = -1.0;" + NL + "                    runningStats.averageQueueDepth = -1.0;" + NL + "                    runningStats.streamIDs.length(0);" + NL + "                    runningStats.timeSinceLastCall = -1;" + NL + "                    enabled = true;" + NL + "                    flush_sec = 0;" + NL + "                    flush_usec = 0;" + NL + "                };" + NL + "" + NL + "                ~linkStatistics() {" + NL + "                }" + NL + "" + NL + "                void setEnabled(bool enableStats) {" + NL + "                    enabled = enableStats;" + NL + "                }" + NL + "" + NL + "                void update(unsigned int elementsReceived, float queueSize, bool EOS, std::string streamID, bool flush) {" + NL + "                    if (!enabled) {" + NL + "                        return;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "                    receivedStatistics[receivedStatistics_idx].elements = elementsReceived;" + NL + "                    receivedStatistics[receivedStatistics_idx].queueSize = queueSize;" + NL + "                    receivedStatistics[receivedStatistics_idx].secs = tv.tv_sec;" + NL + "                    receivedStatistics[receivedStatistics_idx++].usecs = tv.tv_usec;" + NL + "                    receivedStatistics_idx = receivedStatistics_idx % historyWindow;" + NL + "                    if (flush) {" + NL + "                        flush_sec = tv.tv_sec;" + NL + "                        flush_usec = tv.tv_usec;" + NL + "                    }" + NL + "                    if (!EOS) {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        bool foundStreamID = false;" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                foundStreamID = true;" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                        if (!foundStreamID) {" + NL + "                            activeStreamIDs.push_back(streamID);" + NL + "                        }" + NL + "                    } else {" + NL + "                        std::list<std::string>::iterator p = activeStreamIDs.begin();" + NL + "                        while (p != activeStreamIDs.end()) {" + NL + "                            if (*p == streamID) {" + NL + "                                activeStreamIDs.erase(p);" + NL + "                                break;" + NL + "                            }" + NL + "                            p++;" + NL + "                        }" + NL + "                    }" + NL + "                }" + NL + "" + NL + "                BULKIO::PortStatistics retrieve() {" + NL + "                    if (!enabled) {" + NL + "                        return runningStats;" + NL + "                    }" + NL + "                    struct timeval tv;" + NL + "                    struct timezone tz;" + NL + "                    gettimeofday(&tv, &tz);" + NL + "" + NL + "                    int idx = (receivedStatistics_idx == 0) ? (historyWindow - 1) : (receivedStatistics_idx - 1);" + NL + "                    double front_sec = receivedStatistics[idx].secs;" + NL + "                    double front_usec = receivedStatistics[idx].usecs;" + NL + "                    double secDiff = tv.tv_sec - receivedStatistics[receivedStatistics_idx].secs;" + NL + "                    double usecDiff = (tv.tv_usec - receivedStatistics[receivedStatistics_idx].usecs) / ((double)1e6);" + NL + "                    double totalTime = secDiff + usecDiff;" + NL + "                    double totalData = 0;" + NL + "                    float queueSize = 0;" + NL + "                    int startIdx = (receivedStatistics_idx + 1) % historyWindow;" + NL + "                    for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                        totalData += receivedStatistics[i].elements;" + NL + "                        queueSize += receivedStatistics[i].queueSize;" + NL + "                        i = (i + 1) % historyWindow;" + NL + "                    }" + NL + "                    runningStats.bitsPerSecond = ((totalData * bitSize) / totalTime);" + NL + "                    runningStats.elementsPerSecond = (totalData / totalTime);" + NL + "                    runningStats.averageQueueDepth = (queueSize / historyWindow);" + NL + "                    runningStats.callsPerSecond = (double(historyWindow - 1) / totalTime);" + NL + "                    runningStats.timeSinceLastCall = (((double)tv.tv_sec) - front_sec) + (((double)tv.tv_usec - front_usec) / ((double)1e6));" + NL + "                    unsigned int streamIDsize = activeStreamIDs.size();" + NL + "                    std::list< std::string >::iterator p = activeStreamIDs.begin();" + NL + "                    runningStats.streamIDs.length(streamIDsize);" + NL + "                    for (unsigned int i = 0; i < streamIDsize; i++) {" + NL + "                        if (p == activeStreamIDs.end()) {" + NL + "                            break;" + NL + "                        }" + NL + "                        runningStats.streamIDs[i] = CORBA::string_dup((*p).c_str());" + NL + "                        p++;" + NL + "                    }" + NL + "                    if ((flush_sec != 0) && (flush_usec != 0)) {" + NL + "                        double flushTotalTime = (((double)tv.tv_sec) - flush_sec) + (((double)tv.tv_usec - flush_usec) / ((double)1e6));" + NL + "                        runningStats.keywords.length(1);" + NL + "                        runningStats.keywords[0].id = CORBA::string_dup(\"timeSinceLastFlush\");" + NL + "                        runningStats.keywords[0].value <<= CORBA::Double(flushTotalTime);" + NL + "                    }" + NL + "                    return runningStats;" + NL + "                }" + NL + "" + NL + "            protected:" + NL + "                bool enabled;" + NL + "                double bitSize;" + NL + "                BULKIO::PortStatistics runningStats;" + NL + "                std::vector<statPoint> receivedStatistics;" + NL + "                std::list< std::string > activeStreamIDs;" + NL + "                unsigned long historyWindow;" + NL + "                long receivedStatistics_idx;" + NL + "                double flush_sec;" + NL + "                double flush_usec;" + NL + "        };" + NL + "        " + NL + "        void enableStats(bool enable) {" + NL + "            stats.setEnabled(enable);" + NL + "        };" + NL;
  protected final String TEXT_43 = NL + NL + "        class dataTransfer" + NL + "        {" + NL + "            public:" + NL + "                dataTransfer(";
  protected final String TEXT_44 = " data, const BULKIO::PrecisionUTCTime &_T, bool _EOS, const char* _streamID, BULKIO::StreamSRI &_H, bool _sriChanged, bool _inputQueueFlushed)" + NL + "                {";
  protected final String TEXT_45 = NL + "                    dataBuffer = data;";
  protected final String TEXT_46 = NL + "                    int dataLength = data.length();" + NL + "" + NL + "#ifdef EXPECTED_VECTOR_IMPL";
  protected final String TEXT_47 = NL + "                    std::_Vector_base<char, _seqVector::seqVectorAllocator<char> >::_Vector_impl *vectorPointer = (std::_Vector_base<char, _seqVector::seqVectorAllocator<char> >::_Vector_impl *) ((void*) & dataBuffer);" + NL + "                    unsigned char *tmp_2 = const_cast<PortTypes::CharSequence*>(&data)->get_buffer(1);" + NL + "                    char *tmp = (char *) tmp_2;" + NL + "                    vectorPointer->_M_start = tmp;";
  protected final String TEXT_48 = NL + "                    std::_Vector_base<";
  protected final String TEXT_49 = ", _seqVector::seqVectorAllocator<";
  protected final String TEXT_50 = "> >::_Vector_impl *vectorPointer = (std::_Vector_base<";
  protected final String TEXT_51 = ", _seqVector::seqVectorAllocator<";
  protected final String TEXT_52 = "> >::_Vector_impl *) ((void*) & dataBuffer);" + NL + "                    vectorPointer->_M_start = ";
  protected final String TEXT_53 = "const_cast<";
  protected final String TEXT_54 = "*>(&data)->get_buffer(1);";
  protected final String TEXT_55 = NL + "                    vectorPointer->_M_finish = vectorPointer->_M_start + dataLength;" + NL + "                    vectorPointer->_M_end_of_storage = vectorPointer->_M_finish;" + NL + "" + NL + "#else" + NL + "                    dataBuffer.resize(dataLength);" + NL + "                    if (dataLength > 0) {" + NL + "                        memcpy(&dataBuffer[0], &data[0], dataLength * sizeof(data[0]));" + NL + "                    }" + NL + "" + NL + "#endif";
  protected final String TEXT_56 = NL + "                    T = _T;" + NL + "                    EOS = _EOS;" + NL + "                    streamID = _streamID;" + NL + "                    SRI = _H;" + NL + "                    sriChanged = _sriChanged;" + NL + "                    inputQueueFlushed = _inputQueueFlushed;" + NL + "                };" + NL;
  protected final String TEXT_57 = NL + "                std::string dataBuffer;";
  protected final String TEXT_58 = NL + "#ifdef EXPECTED_VECTOR_IMPL";
  protected final String TEXT_59 = NL + "                std::vector< char, _seqVector::seqVectorAllocator<char> > dataBuffer;";
  protected final String TEXT_60 = NL + "                std::vector< ";
  protected final String TEXT_61 = ", _seqVector::seqVectorAllocator<";
  protected final String TEXT_62 = "> > dataBuffer;";
  protected final String TEXT_63 = NL + "#else";
  protected final String TEXT_64 = NL + "                std::vector<char> dataBuffer;";
  protected final String TEXT_65 = NL + "                std::vector<";
  protected final String TEXT_66 = "> dataBuffer;";
  protected final String TEXT_67 = NL + "#endif";
  protected final String TEXT_68 = NL + "                BULKIO::PrecisionUTCTime T;" + NL + "                bool EOS;" + NL + "                std::string streamID;" + NL + "                BULKIO::StreamSRI SRI;" + NL + "                bool sriChanged;" + NL + "                bool inputQueueFlushed;" + NL + "        };" + NL + "" + NL + "        dataTransfer *getPacket(float timeout);" + NL + "        void block();" + NL + "        void unblock();";
  protected final String TEXT_69 = NL + "        class dataTransfer" + NL + "        {" + NL + "            public:" + NL + "                dataTransfer(const char *data, bool _EOS, const char* _streamID, BULKIO::StreamSRI &_H, bool _sriChanged, bool _inputQueueFlushed)" + NL + "                {" + NL + "                    dataBuffer = data;" + NL + "                    EOS = _EOS;" + NL + "                    streamID = _streamID;" + NL + "                    SRI = _H;" + NL + "                    sriChanged = _sriChanged;" + NL + "                    inputQueueFlushed = _inputQueueFlushed;" + NL + "                };" + NL + "" + NL + "                std::string dataBuffer;" + NL + "                bool EOS;" + NL + "                std::string streamID;" + NL + "                BULKIO::StreamSRI SRI;" + NL + "                bool sriChanged;" + NL + "                bool inputQueueFlushed;" + NL + "        };" + NL + "" + NL + "        dataTransfer *getPacket(float timeout);" + NL + "        void block();" + NL + "        void unblock();" + NL;
  protected final String TEXT_70 = NL + NL + "    protected:";
  protected final String TEXT_71 = NL + "        ";
  protected final String TEXT_72 = "_i *parent;" + NL + "        std::deque<dataTransfer *> workQueue;" + NL + "        std::map<std::string, std::pair<BULKIO::StreamSRI, bool> > currentHs;" + NL + "        boost::mutex dataBufferLock;" + NL + "        boost::mutex sriUpdateLock;" + NL + "        omni_mutex dataAvailableMutex;" + NL + "        omni_condition* dataAvailable;" + NL + "        unsigned long secs, nsecs, timeout_secs, timeout_nsecs;" + NL + "        bool breakBlock;" + NL + "        bool blocking;" + NL + "        queueSemaphore* queueSem;" + NL;
  protected final String TEXT_73 = NL + "        // statistics" + NL + "        linkStatistics stats;" + NL;
  protected final String TEXT_74 = NL + NL + "    protected:";
  protected final String TEXT_75 = NL + "        ";
  protected final String TEXT_76 = "_i *parent;" + NL + "        boost::mutex portAccess;" + NL;
  protected final String TEXT_77 = NL + "    ";
  protected final String TEXT_78 = " vector_";
  protected final String TEXT_79 = "_";
  protected final String TEXT_80 = ";" + NL + "    ";
  protected final String TEXT_81 = NL + "};";
  protected final String TEXT_82 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    List<CppProperties.Property> properties = CppProperties.getProperties(softPkg);
    Provides pro = null;
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());

    CppHelper _cppHelper = new CppHelper();
    for (Provides entry : provides) {
        String intName = entry.getRepID();
        if (intName.equals(templ.getPortRepId())) {
            pro = entry;
            if (templ.isGenSupport()) {
                Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
                if (iface == null) {
                    throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
                }
                if (iface.getNameSpace().equals("ExtendedEvent")) {

    stringBuffer.append(TEXT_1);
    
                	continue;
                }
                if (iface.getFullPath().contains("/COS/")) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(iface.getFilename());
    stringBuffer.append(TEXT_3);
    
                } else {

    stringBuffer.append(TEXT_4);
    stringBuffer.append(iface.getNameSpace() + "/" + iface.getFilename());
    stringBuffer.append(TEXT_5);
    
                }
            }
            break;
        }
    }    

    if ((pro != null) && templ.isGenClassDef()) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, pro.getRepID().split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + pro.getRepID()));
        }
        
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();
        boolean pushPacketCall = false;
        boolean pushPacketXMLCall = false;
        String ppDataTransfer = "";

    stringBuffer.append(TEXT_6);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_8);
    
        if ("BULKIO".equals(nameSpace) && "dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    
        } else if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {
        	// no need to add a port declaration for provides message consumer
        } else {

    stringBuffer.append(TEXT_13);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_23);
    
            for (Operation op : iface.getOperations()) {
                int numParams = op.getParams().size();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_26);
    
                if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                    ppDataTransfer = _cppHelper.getBaseSequenceMapping(op.getParams().get(0).getCxxType());
                    if (ppDataTransfer.startsWith("std::vector")) {
                        if (ppDataTransfer.endsWith("& ")) {
                            ppDataTransfer = ppDataTransfer.substring(12, ppDataTransfer.length() - 2);
                        } else { 
                            ppDataTransfer = ppDataTransfer.substring(12, ppDataTransfer.length() - 1);
                        }
                    } else if ("dataFile".equals(interfaceName)) {
                        ppDataTransfer = "char";
                    }
                    pushPacketCall = true;
                } else if ("pushPacket".equals(op.getName()) && "dataXML".equals(interfaceName)) {
                    ppDataTransfer = "char";
                    pushPacketXMLCall = true;
                }
                if (numParams == 0) {
                    
    stringBuffer.append(TEXT_27);
    
                }
                for (int i = 0; i < numParams; i++) {
        
    stringBuffer.append(op.getParams().get(i).getCxxType());
    
        
    stringBuffer.append(TEXT_28);
    stringBuffer.append(op.getParams().get(i).getName());
    
                    if (i == (numParams - 1)) {
                        
    stringBuffer.append(TEXT_29);
    
                    } else {
                        
    stringBuffer.append(TEXT_30);
    
                    }
                } // end for params
            } // end for operations

    stringBuffer.append(TEXT_31);
    
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(op.getCxxReturnType());
    stringBuffer.append(_cppHelper.pointerReturnValue(op.getCxxReturnType(), op.getReturnType(), op.isCxxReturnTypeVariableLength()));
    stringBuffer.append(TEXT_33);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_34);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_35);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_36);
    stringBuffer.append(op.getCxxType());
    stringBuffer.append(TEXT_37);
    
                } // end if readonly
            } // end for attributes
            
            if (pushPacketCall || pushPacketXMLCall) {
                String firstType = "";
                String cast = "";

    stringBuffer.append(TEXT_38);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_39);
    
                    } else {

    stringBuffer.append(TEXT_40);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_41);
    
                    }

    stringBuffer.append(TEXT_42);
    
                if (pushPacketCall) {

    stringBuffer.append(TEXT_43);
    
                for (Operation op : iface.getOperations()) {
                    int numParams = op.getParams().size();
                    if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                        
    stringBuffer.append(op.getParams().get(0).getCxxType());
    
                        firstType = op.getParams().get(0).getCxxType();
                        int start = (firstType.startsWith("const ")) ? 6 : 0;
                        int end = (firstType.endsWith("&")) ? firstType.length() - 1 : firstType.length();
                        firstType = firstType.substring(start, end);
                        
                        if (ppDataTransfer.endsWith("long")) {
                        	cast = "(" + ppDataTransfer + " int *)";
                        }
                    }
                }
                
    stringBuffer.append(TEXT_44);
    
                    if ("dataFile".equals(interfaceName)) {

    stringBuffer.append(TEXT_45);
    
                    } else {

    stringBuffer.append(TEXT_46);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_47);
    
                    } else {

    stringBuffer.append(TEXT_48);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_52);
    if (!cast.equals("")) {
    stringBuffer.append(cast);
     } 
    stringBuffer.append(TEXT_53);
    stringBuffer.append(firstType);
    stringBuffer.append(TEXT_54);
    
                    }

    stringBuffer.append(TEXT_55);
    
                    }

    stringBuffer.append(TEXT_56);
    
                    if ("dataFile".equals(interfaceName)) {

    stringBuffer.append(TEXT_57);
    
                    } else {

    stringBuffer.append(TEXT_58);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_59);
    
                    } else {

    stringBuffer.append(TEXT_60);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_62);
    
                    }

    stringBuffer.append(TEXT_63);
    
                    if ("dataChar".equals(interfaceName)) {

    stringBuffer.append(TEXT_64);
    
                    } else {

    stringBuffer.append(TEXT_65);
    stringBuffer.append(ppDataTransfer);
    stringBuffer.append(TEXT_66);
    
                    }

    stringBuffer.append(TEXT_67);
    
                    }

    stringBuffer.append(TEXT_68);
    
                } else if (pushPacketXMLCall) {

    stringBuffer.append(TEXT_69);
    
                }
            }
            if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_70);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_72);
    
                if (pushPacketCall || pushPacketXMLCall) {

    stringBuffer.append(TEXT_73);
    
                }
            } else {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_76);
    
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

    stringBuffer.append(TEXT_77);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_79);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_80);
    
                    }
                } // end for Operations
            } // end if pushPacket

    stringBuffer.append(TEXT_81);
    
        } // end not BULKIO or pushXML
    } // end if genClassDef

    stringBuffer.append(TEXT_82);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE