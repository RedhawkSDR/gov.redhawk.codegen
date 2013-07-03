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
package gov.redhawk.ide.codegen.jet.python.template.component.pull;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.PythonJetGeneratorPlugin;
import gov.redhawk.ide.codegen.python.utils.PortHelper;
import gov.redhawk.ide.idl.Attribute;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import gov.redhawk.ide.idl.Param;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import java.util.ArrayList;
import java.util.Collections;
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
public class PortGeneratorTemplate
{

  protected static String nl;
  public static synchronized PortGeneratorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PortGeneratorTemplate result = new PortGeneratorTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "from ossie.resource import usesport, providesport" + NL;
  protected final String TEXT_3 = NL + "from ossie.cf import ExtendedCF" + NL + "from omniORB import CORBA" + NL + "import struct #@UnresolvedImport";
  protected final String TEXT_4 = NL + "from ossie.utils import uuid";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "from ";
  protected final String TEXT_7 = " ";
  protected final String TEXT_8 = "import ";
  protected final String TEXT_9 = ", ";
  protected final String TEXT_10 = "__POA #@UnusedImport ";
  protected final String TEXT_11 = NL + "        # '";
  protected final String TEXT_12 = "' port" + NL + "        class Port";
  protected final String TEXT_13 = "In(";
  protected final String TEXT_14 = "):" + NL + "            \"\"\"This class is a port template for the ";
  protected final String TEXT_15 = " port and" + NL + "            should not be instantiated nor modified." + NL + "            " + NL + "            The expectation is that the specific port implementation will extend " + NL + "            from this class instead of the base CORBA class ";
  protected final String TEXT_16 = "." + NL + "            \"\"\"" + NL + "            pass";
  protected final String TEXT_17 = NL + NL + "        # '";
  protected final String TEXT_18 = "' port" + NL + "        class Port";
  protected final String TEXT_19 = "Out(";
  protected final String TEXT_20 = "BULKIO__POA.UsesPortStatisticsProvider";
  protected final String TEXT_21 = "CF__POA.Port";
  protected final String TEXT_22 = "):" + NL + "            \"\"\"This class is a port template for the ";
  protected final String TEXT_23 = " port and" + NL + "            should not be instantiated nor modified." + NL + "            " + NL + "            The expectation is that the specific port implementation will extend " + NL + "            from this class instead of the base CORBA class CF__POA.Port." + NL + "            \"\"\"" + NL + "            pass";
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = "class Port";
  protected final String TEXT_26 = "In_i(";
  protected final String TEXT_27 = "_base.Port";
  protected final String TEXT_28 = "In):" + NL + "    class linkStatistics:" + NL + "        class statPoint:" + NL + "            def __init__(self):" + NL + "                self.elements = 0" + NL + "                self.queueSize = 0.0" + NL + "                self.secs = 0.0" + NL + "                self.streamID = \"\"" + NL + "" + NL + "        def __init__(self, port_ref):" + NL + "            self.enabled = True" + NL + "            self.historyWindow = 10" + NL + "            self.receivedStatistics = []" + NL + "            self.port_ref = port_ref" + NL + "            self.receivedStatistics_idx = 0" + NL + "            self.bitSize = 8" + NL + "            for i in range(self.historyWindow):" + NL + "                self.receivedStatistics.append(self.statPoint())" + NL + "" + NL + "        def setBitSize(self, _bitSize):" + NL + "            self.bitSize = _bitSize" + NL + "" + NL + "        def setEnabled(self, enableStats):" + NL + "            self.enabled = enableStats" + NL + "" + NL + "        def update(self, elementsReceived, queueSize, streamID):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].elements = elementsReceived" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].queueSize = queueSize" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].secs = time.time()" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].streamID = streamID" + NL + "            self.receivedStatistics_idx += 1" + NL + "            self.receivedStatistics_idx = self.receivedStatistics_idx%self.historyWindow" + NL + "" + NL + "        def retrieve(self):" + NL + "            if not self.enabled:" + NL + "                return None" + NL + "" + NL + "            self.runningStats = BULKIO.PortStatistics(portName=self.port_ref.name, averageQueueDepth=-1, elementsPerSecond=-1, bitsPerSecond=-1, callsPerSecond=-1, streamIDs=[], timeSinceLastCall=-1, keywords=[])" + NL + "" + NL + "            listPtr = (self.receivedStatistics_idx + 1) % self.historyWindow    # don't count the first set of data, since we're looking at change in time rather than absolute time" + NL + "            frontTime = self.receivedStatistics[(self.receivedStatistics_idx - 1) % self.historyWindow].secs" + NL + "            backTime = self.receivedStatistics[self.receivedStatistics_idx].secs" + NL + "            totalData = 0.0" + NL + "            queueSize = 0.0" + NL + "            streamIDs = []" + NL + "            while (listPtr != self.receivedStatistics_idx):" + NL + "                totalData += self.receivedStatistics[listPtr].elements" + NL + "                queueSize += self.receivedStatistics[listPtr].queueSize" + NL + "                streamIDptr = 0" + NL + "                foundstreamID = False" + NL + "                while (streamIDptr != len(streamIDs)):" + NL + "                    if (streamIDs[streamIDptr] == self.receivedStatistics[listPtr].streamID):" + NL + "                        foundstreamID = True" + NL + "                        break" + NL + "                    streamIDptr += 1" + NL + "                if (not foundstreamID):" + NL + "                    streamIDs.append(self.receivedStatistics[listPtr].streamID)" + NL + "                listPtr += 1" + NL + "                listPtr = listPtr%self.historyWindow" + NL + "" + NL + "            receivedSize = len(self.receivedStatistics)" + NL + "            currentTime = time.time()" + NL + "            totalTime = currentTime - backTime" + NL + "            if totalTime == 0:" + NL + "                totalTime = 1e6" + NL + "            self.runningStats.bitsPerSecond = (totalData * self.bitSize) / totalTime" + NL + "            self.runningStats.elementsPerSecond = totalData / totalTime" + NL + "            self.runningStats.averageQueueDepth = queueSize / receivedSize" + NL + "            self.runningStats.callsPerSecond = float((receivedSize - 1)) / totalTime" + NL + "            self.runningStats.streamIDs = streamIDs" + NL + "            self.runningStats.timeSinceLastCall = currentTime - frontTime" + NL + "            return self.runningStats" + NL + "" + NL + "    def __init__(self, parent, name, maxsize):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.sri = None" + NL + "        self.queue = Queue.Queue()        " + NL + "        self.maxQueueDepth = maxsize" + NL + "        self.port_lock = threading.Lock()" + NL + "        self._attachedStreams = {} # key=attach_id, value = (streamDef, userid) " + NL + "        self.stats = self.linkStatistics(self)" + NL + "        self.sriDict = {} # key=streamID, value=(StreamSRI, PrecisionUTCTime)" + NL + "        try:" + NL + "            self._attach_cb = getattr(parent, \"attach\")" + NL + "            if not callable(self._attach_cb):" + NL + "                self._attach_cb = None" + NL + "        except AttributeError:" + NL + "            self._attach_cb = None" + NL + "        try:" + NL + "            self._detach_cb = getattr(parent, \"detach\")" + NL + "            if not callable(self._detach_cb):" + NL + "                self._attach_cb = None" + NL + "        except AttributeError:" + NL + "            self._detach_db = None" + NL + "" + NL + "    def setBitSize(self, bitSize):" + NL + "        self.stats.setBitSize(bitSize)" + NL + "" + NL + "    def enableStats(self, enabled):" + NL + "        self.stats.setEnabled(enabled)" + NL + "        " + NL + "    def updateStats(self, elementsReceived, queueSize, streamID):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            self.stats.update(elementsReceived, queueSize, streamID)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def _get_statistics(self):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            recStat = self.stats.retrieve()" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return recStat" + NL + "" + NL + "    def _get_state(self):" + NL + "        if len(self._attachedStreams.values()) == 0:" + NL + "            return BULKIO.IDLE" + NL + "        # default behavior is to limit to one connection" + NL + "        elif len(self._attachedStreams.values()) == 1:" + NL + "            return BULKIO.BUSY" + NL + "        else:" + NL + "            return BULKIO.ACTIVE" + NL + "" + NL + "    def _get_attachedSRIs(self):" + NL + "        sris = []" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for entry in self.sriDict:" + NL + "                sris.append(copy.deepcopy(self.sriDict[entry]))" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return sris" + NL + "" + NL + "    def _get_usageState(self):" + NL + "        if len(self._attachedStreams.values()) == 0:" + NL + "            return ";
  protected final String TEXT_29 = ".";
  protected final String TEXT_30 = ".IDLE" + NL + "        # default behavior is to limit to one connection" + NL + "        elif len(self._attachedStreams.values()) == 1:" + NL + "            return ";
  protected final String TEXT_31 = ".";
  protected final String TEXT_32 = ".BUSY" + NL + "        else:" + NL + "            return ";
  protected final String TEXT_33 = ".";
  protected final String TEXT_34 = ".ACTIVE" + NL + "" + NL + "    def _get_attachedStreams(self):" + NL + "        return [x[0] for x in self._attachedStreams.values()]" + NL + "" + NL + "    def _get_attachmentIds(self):" + NL + "        return self._attachedStreams.keys()" + NL + "" + NL + "    def attach(self, streamDef, userid):" + NL + "        #self._log.debug(\"attach(%s)\", streamDef)" + NL + "        if self._get_usageState() == ";
  protected final String TEXT_35 = ".";
  protected final String TEXT_36 = ".BUSY:" + NL + "            raise ";
  protected final String TEXT_37 = ".";
  protected final String TEXT_38 = ".AttachError(\"No capacity\")" + NL + "" + NL + "        #" + NL + "        # Allocate capacities here if applicable" + NL + "        #" + NL + "" + NL + "        # The attachment succeeded so generate a attachId" + NL + "        attachId = None" + NL + "        try:" + NL + "            if self._attach_cb != None:" + NL + "                attachId = self._attach_cb(streamDef, userid)" + NL + "        except Exception, e:" + NL + "            raise BULKIO.dataSDDS.AttachError(str(e))" + NL + "        " + NL + "        if attachId == None:" + NL + "            attachId = str(uuid.uuid4())" + NL + "" + NL + "        self._attachedStreams[attachId] = (streamDef, userid)" + NL + "" + NL + "        return attachId" + NL + "" + NL + "    def detach(self, attachId):" + NL + "        if not self._attachedStreams.has_key(attachId):" + NL + "            #self._log.debug(\"Stream %s not attached %s\", attachId, self._attachedStreams.keys())" + NL + "            raise ";
  protected final String TEXT_39 = ".";
  protected final String TEXT_40 = ".DetachError(\"Stream %s not attached\" % attachId)" + NL + "" + NL + "        attachedStreamDef, refcnf = self._attachedStreams[attachId]" + NL + "" + NL + "        #" + NL + "        # Deallocate capacity here if applicable" + NL + "        #" + NL + "" + NL + "        try:" + NL + "            if self._detach_cb != None:" + NL + "                self._detach_cb(attachId)" + NL + "        except Exception, e:" + NL + "            raise BULKIO.dataSDDS.DetachError(str(e))" + NL + "" + NL + "        # Remove the attachment from our list" + NL + "        del self._attachedStreams[attachId]" + NL + "" + NL + "    def getStreamDefinition(self, attachId):" + NL + "        try:" + NL + "            return self._attachedStreams[attachId][0]" + NL + "        except KeyError:" + NL + "            raise ";
  protected final String TEXT_41 = ".";
  protected final String TEXT_42 = ".StreamInputError(\"Stream %s not attached\" % attachId)" + NL + "" + NL + "    def getUser(self, attachId):" + NL + "        try:" + NL + "            return self._attachedStreams[attachId][1]" + NL + "        except KeyError:" + NL + "            raise ";
  protected final String TEXT_43 = ".";
  protected final String TEXT_44 = ".StreamInputError(\"Stream %s not attached\" % attachId)" + NL + "" + NL + "    def pushSRI(self, H, T):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            self.sriDict[H.streamID] = (copy.deepcopy(H), copy.deepcopy(T))" + NL + "        finally:" + NL + "            self.port_lock.release()";
  protected final String TEXT_45 = NL + NL + "class Port";
  protected final String TEXT_46 = "In_i(";
  protected final String TEXT_47 = "_base.Port";
  protected final String TEXT_48 = "In):" + NL + "    class linkStatistics:" + NL + "        class statPoint:" + NL + "            def __init__(self):" + NL + "                self.elements = 0" + NL + "                self.queueSize = 0.0" + NL + "                self.secs = 0.0" + NL + "                self.streamID = \"\"" + NL + "" + NL + "        def __init__(self, port_ref):" + NL + "            self.enabled = True" + NL + "            self.flushTime = None" + NL + "            self.historyWindow = 10" + NL + "            self.receivedStatistics = []" + NL + "            self.port_ref = port_ref" + NL + "            self.receivedStatistics_idx = 0" + NL + "            self.bitSize = struct.calcsize(";
  protected final String TEXT_49 = ") * 8" + NL + "            for i in range(self.historyWindow):" + NL + "                self.receivedStatistics.append(self.statPoint())" + NL + "" + NL + "        def setEnabled(self, enableStats):" + NL + "            self.enabled = enableStats" + NL + "" + NL + "        def update(self, elementsReceived, queueSize, streamID, flush):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].elements = elementsReceived" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].queueSize = queueSize" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].secs = time.time()" + NL + "            self.receivedStatistics[self.receivedStatistics_idx].streamID = streamID" + NL + "            self.receivedStatistics_idx += 1" + NL + "            self.receivedStatistics_idx = self.receivedStatistics_idx%self.historyWindow" + NL + "            if flush:" + NL + "                self.flushTime = self.receivedStatistics[self.receivedStatistics_idx].secs" + NL + "" + NL + "        def retrieve(self):" + NL + "            if not self.enabled:" + NL + "                return None" + NL + "" + NL + "            self.runningStats = BULKIO.PortStatistics(portName=self.port_ref.name, averageQueueDepth=-1, elementsPerSecond=-1, bitsPerSecond=-1, callsPerSecond=-1, streamIDs=[], timeSinceLastCall=-1, keywords=[])" + NL + "" + NL + "            listPtr = (self.receivedStatistics_idx + 1) % self.historyWindow    # don't count the first set of data, since we're looking at change in time rather than absolute time" + NL + "            frontTime = self.receivedStatistics[(self.receivedStatistics_idx - 1) % self.historyWindow].secs" + NL + "            backTime = self.receivedStatistics[self.receivedStatistics_idx].secs" + NL + "            totalData = 0.0" + NL + "            queueSize = 0.0" + NL + "            streamIDs = []" + NL + "            while (listPtr != self.receivedStatistics_idx):" + NL + "                totalData += self.receivedStatistics[listPtr].elements" + NL + "                queueSize += self.receivedStatistics[listPtr].queueSize" + NL + "                streamIDptr = 0" + NL + "                foundstreamID = False" + NL + "                while (streamIDptr != len(streamIDs)):" + NL + "                    if (streamIDs[streamIDptr] == self.receivedStatistics[listPtr].streamID):" + NL + "                        foundstreamID = True" + NL + "                        break" + NL + "                    streamIDptr += 1" + NL + "                if (not foundstreamID):" + NL + "                    streamIDs.append(self.receivedStatistics[listPtr].streamID)" + NL + "                listPtr += 1" + NL + "                listPtr = listPtr%self.historyWindow" + NL + "" + NL + "            receivedSize = len(self.receivedStatistics)" + NL + "            currentTime = time.time()" + NL + "            totalTime = currentTime - backTime" + NL + "            if totalTime == 0:" + NL + "                totalTime = 1e6" + NL + "            self.runningStats.bitsPerSecond = (totalData * self.bitSize) / totalTime" + NL + "            self.runningStats.elementsPerSecond = totalData / totalTime" + NL + "            self.runningStats.averageQueueDepth = queueSize / receivedSize" + NL + "            self.runningStats.callsPerSecond = float((receivedSize - 1)) / totalTime" + NL + "            self.runningStats.streamIDs = streamIDs" + NL + "            self.runningStats.timeSinceLastCall = currentTime - frontTime" + NL + "            if not self.flushTime == None:" + NL + "                flushTotalTime = currentTime - self.flushTime" + NL + "                self.runningStats.keywords = [CF.DataType(id=\"timeSinceLastFlush\", value=CORBA.Any(CORBA.TC_double, flushTotalTime))]" + NL + "" + NL + "            return self.runningStats" + NL + "" + NL + "    def __init__(self, parent, name";
  protected final String TEXT_50 = ", maxsize";
  protected final String TEXT_51 = "):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.queue = Queue.Queue(";
  protected final String TEXT_52 = "maxsize";
  protected final String TEXT_53 = "100";
  protected final String TEXT_54 = ")" + NL + "        self.port_lock = threading.Lock()" + NL + "        self.stats = self.linkStatistics(self)" + NL + "        self.blocking = False" + NL + "        self.sriDict = {} # key=streamID, value=StreamSRI" + NL + "" + NL + "    def enableStats(self, enabled):" + NL + "        self.stats.setEnabled(enabled)" + NL + "" + NL + "    def _get_statistics(self):" + NL + "        self.port_lock.acquire()" + NL + "        recStat = self.stats.retrieve()" + NL + "        self.port_lock.release()" + NL + "        return recStat" + NL + "" + NL + "    def _get_state(self):" + NL + "        self.port_lock.acquire()" + NL + "        if self.queue.full():" + NL + "            self.port_lock.release()" + NL + "            return BULKIO.BUSY" + NL + "        elif self.queue.empty():" + NL + "            self.port_lock.release()" + NL + "            return BULKIO.IDLE" + NL + "        else:" + NL + "            self.port_lock.release()" + NL + "            return BULKIO.ACTIVE" + NL + "        self.port_lock.release()" + NL + "        return BULKIO.BUSY" + NL + "" + NL + "    def _get_activeSRIs(self):" + NL + "        self.port_lock.acquire()" + NL + "        activeSRIs = [self.sriDict[entry][0] for entry in self.sriDict]" + NL + "        self.port_lock.release()" + NL + "        return activeSRIs" + NL + "" + NL + "    def getCurrentQueueDepth(self):" + NL + "        self.port_lock.acquire()" + NL + "        depth = self.queue.qsize()" + NL + "        self.port_lock.release()" + NL + "        return depth" + NL + "" + NL + "    def getMaxQueueDepth(self):" + NL + "        self.port_lock.acquire()" + NL + "        depth = self.queue.maxsize" + NL + "        self.port_lock.release()" + NL + "        return depth" + NL + "        " + NL + "    #set to -1 for infinite queue" + NL + "    def setMaxQueueDepth(self, newDepth):" + NL + "        self.port_lock.acquire()" + NL + "        self.queue.maxsize = int(newDepth)" + NL + "        self.port_lock.release()" + NL + "" + NL + "    def pushSRI(self, H):" + NL + "        self.port_lock.acquire()" + NL + "        if H.streamID not in self.sriDict:" + NL + "            self.sriDict[H.streamID] = (copy.deepcopy(H), True)" + NL + "            if H.blocking:" + NL + "                self.blocking = True" + NL + "        else:" + NL + "            sri, sriChanged = self.sriDict[H.streamID]" + NL + "            if not self.parent.compareSRI(sri, H):" + NL + "                self.sriDict[H.streamID] = (copy.deepcopy(H), True)" + NL + "                if H.blocking:" + NL + "                    self.blocking = True" + NL + "        self.port_lock.release()" + NL;
  protected final String TEXT_55 = NL + "    def ";
  protected final String TEXT_56 = "(self";
  protected final String TEXT_57 = ", ";
  protected final String TEXT_58 = "):" + NL + "        self.port_lock.acquire()" + NL + "        if self.queue.maxsize == 0:" + NL + "            self.port_lock.release()" + NL + "            return" + NL + "        packet = None" + NL + "        try:" + NL + "            sri = BULKIO.StreamSRI(1, 0.0, 1.0, 1, 0, 0.0, 0.0, 0, 0, streamID, False, [])" + NL + "            sriChanged = False" + NL + "            if self.sriDict.has_key(streamID):" + NL + "                sri, sriChanged = self.sriDict[streamID]" + NL + "                self.sriDict[streamID] = (sri, False)" + NL + "            else:" + NL + "                self.sriDict[streamID] = (sri, False)" + NL + "                sriChanged = True" + NL + "" + NL + "            if self.blocking:" + NL + "                packet = (";
  protected final String TEXT_59 = ", ";
  protected final String TEXT_60 = ", EOS, streamID, copy.deepcopy(sri), sriChanged, False)" + NL + "                self.stats.update(len(";
  protected final String TEXT_61 = "), float(self.queue.qsize()) / float(self.queue.maxsize), streamID, False)" + NL + "                self.queue.put(packet)" + NL + "            else:" + NL + "                if self.queue.full():" + NL + "                    try:" + NL + "                        self.queue.mutex.acquire()" + NL + "                        self.queue.queue.clear()" + NL + "                        self.queue.mutex.release()" + NL + "                    except Queue.Empty:" + NL + "                        pass" + NL + "                    packet = (";
  protected final String TEXT_62 = ", ";
  protected final String TEXT_63 = ", EOS, streamID, copy.deepcopy(sri), sriChanged, True)" + NL + "                    self.stats.update(len(";
  protected final String TEXT_64 = "), float(self.queue.qsize()) / float(self.queue.maxsize), streamID, True)" + NL + "                else:" + NL + "                    packet = (";
  protected final String TEXT_65 = ", ";
  protected final String TEXT_66 = ", EOS, streamID, copy.deepcopy(sri), sriChanged, False)" + NL + "                    self.stats.update(len(";
  protected final String TEXT_67 = "), float(self.queue.qsize()) / float(self.queue.maxsize), streamID, False)" + NL + "                self.queue.put(packet)" + NL + "        finally:" + NL + "            self.port_lock.release()";
  protected final String TEXT_68 = NL + "    " + NL + "    def getPacket(self):" + NL + "        try:" + NL + "            data, T, EOS, streamID, sri, sriChanged, inputQueueFlushed = self.queue.get(block=False)" + NL + "            " + NL + "            if EOS: " + NL + "                if self.sriDict.has_key(streamID):" + NL + "                    sri, sriChanged = self.sriDict.pop(streamID)" + NL + "                    if sri.blocking:" + NL + "                        stillBlock = False" + NL + "                        for _sri, _sriChanged in self.sriDict.values():" + NL + "                            if _sri.blocking:" + NL + "                                stillBlock = True" + NL + "                                break" + NL + "                        if not stillBlock:" + NL + "                            self.blocking = False" + NL + "            return (data, T, EOS, streamID, sri, sriChanged, inputQueueFlushed)" + NL + "        except Queue.Empty:" + NL + "            return None, None, None, None, None, None, None";
  protected final String TEXT_69 = NL + NL + "class Port";
  protected final String TEXT_70 = "In_i(";
  protected final String TEXT_71 = "_base.Port";
  protected final String TEXT_72 = "In):" + NL + "    def __init__(self, parent, name";
  protected final String TEXT_73 = ", maxsize";
  protected final String TEXT_74 = "):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.sri = None" + NL + "        self.queue = Queue.Queue()" + NL + "        self.port_lock = threading.Lock()";
  protected final String TEXT_75 = NL + NL + "    def ";
  protected final String TEXT_76 = "(self";
  protected final String TEXT_77 = ", ";
  protected final String TEXT_78 = "):" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_79 = NL + NL + "    def _get_";
  protected final String TEXT_80 = "(self):" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_81 = NL + NL + "    def _set_";
  protected final String TEXT_82 = "(self, data):" + NL + "        # TODO:" + NL + "        pass" + NL;
  protected final String TEXT_83 = NL;
  protected final String TEXT_84 = "class Port";
  protected final String TEXT_85 = "Out_i(";
  protected final String TEXT_86 = "_base.Port";
  protected final String TEXT_87 = "Out):" + NL + "    class linkStatistics:" + NL + "        class statPoint:" + NL + "            def __init__(self):" + NL + "                self.elements = 0" + NL + "                self.queueSize = 0.0" + NL + "                self.secs = 0.0" + NL + "                self.streamID = \"\"" + NL + "" + NL + "        def __init__(self, port_ref):" + NL + "            self.enabled = True" + NL + "            self.bitSize = 8" + NL + "            self.historyWindow = 10" + NL + "            self.receivedStatistics = {}" + NL + "            self.port_ref = port_ref" + NL + "            self.receivedStatistics_idx = {}" + NL + "" + NL + "        def setBitSize(self, _bitSize):" + NL + "            self.bitSize = _bitSize" + NL + "" + NL + "        def setEnabled(self, enableStats):" + NL + "            self.enabled = enableStats" + NL + "" + NL + "        def update(self, elementsReceived, queueSize, streamID, connectionId):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            if self.receivedStatistics.has_key(connectionId):" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].elements = elementsReceived" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].queueSize = queueSize" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].secs = time.time()" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].streamID = streamID" + NL + "                self.receivedStatistics_idx[connectionId] += 1" + NL + "                self.receivedStatistics_idx[connectionId] = self.receivedStatistics_idx[connectionId]%self.historyWindow" + NL + "            else:" + NL + "                self.receivedStatistics[connectionId] = []" + NL + "                self.receivedStatistics_idx[connectionId] = 0" + NL + "                for i in range(self.historyWindow):" + NL + "                    self.receivedStatistics[connectionId].append(self.statPoint())" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].elements = elementsReceived" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].queueSize = queueSize" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].secs = time.time()" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].streamID = streamID" + NL + "                self.receivedStatistics_idx[connectionId] += 1" + NL + "                self.receivedStatistics_idx[connectionId] = self.receivedStatistics_idx[connectionId] % self.historyWindow" + NL + "" + NL + "        def retrieve(self):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            retVal = []" + NL + "            for entry in self.receivedStatistics:" + NL + "                runningStats = BULKIO.PortStatistics(portName=self.port_ref.name,averageQueueDepth=-1,elementsPerSecond=-1,bitsPerSecond=-1,callsPerSecond=-1,streamIDs=[],timeSinceLastCall=-1,keywords=[])" + NL + "" + NL + "                listPtr = (self.receivedStatistics_idx[entry] + 1) % self.historyWindow    # don't count the first set of data, since we're looking at change in time rather than absolute time" + NL + "                frontTime = self.receivedStatistics[entry][(self.receivedStatistics_idx[entry] - 1) % self.historyWindow].secs" + NL + "                backTime = self.receivedStatistics[entry][self.receivedStatistics_idx[entry]].secs" + NL + "                totalData = 0.0" + NL + "                queueSize = 0.0" + NL + "                streamIDs = []" + NL + "                while (listPtr != self.receivedStatistics_idx[entry]):" + NL + "                    totalData += self.receivedStatistics[entry][listPtr].elements" + NL + "                    queueSize += self.receivedStatistics[entry][listPtr].queueSize" + NL + "                    streamIDptr = 0" + NL + "                    foundstreamID = False" + NL + "                    while (streamIDptr != len(streamIDs)):" + NL + "                        if (streamIDs[streamIDptr] == self.receivedStatistics[entry][listPtr].streamID):" + NL + "                            foundstreamID = True" + NL + "                            break" + NL + "                        streamIDptr += 1" + NL + "                    if (not foundstreamID):" + NL + "                        streamIDs.append(self.receivedStatistics[entry][listPtr].streamID)" + NL + "                    listPtr += 1" + NL + "                    listPtr = listPtr % self.historyWindow" + NL + "" + NL + "                currentTime = time.time()" + NL + "                totalTime = currentTime - backTime" + NL + "                if totalTime == 0:" + NL + "                    totalTime = 1e6" + NL + "                receivedSize = len(self.receivedStatistics[entry])" + NL + "                runningStats.bitsPerSecond = (totalData * self.bitSize) / totalTime" + NL + "                runningStats.elementsPerSecond = totalData/totalTime" + NL + "                runningStats.averageQueueDepth = queueSize / receivedSize" + NL + "                runningStats.callsPerSecond = float((receivedSize - 1)) / totalTime" + NL + "                runningStats.streamIDs = streamIDs" + NL + "                runningStats.timeSinceLastCall = currentTime - frontTime" + NL + "                usesPortStat = BULKIO.UsesPortStatistics(connectionId=entry, statistics=runningStats)" + NL + "                retVal.append(usesPortStat)" + NL + "            return retVal" + NL + "" + NL + "    def __init__(self, parent, name, max_attachments=1):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.max_attachments = max_attachments" + NL + "        self.outPorts = {} # key=connection_id,  value=port" + NL + "        self.attachedGroup = {} # key=connection_id,  value=attach_id" + NL + "        self.lastStreamData = None" + NL + "        self.lastName = None" + NL + "        self.defaultStreamSRI = BULKIO.StreamSRI(1, 0.0, 0.001, 1, 200, 0.0, 0.001, 1, 1, \"sampleStream\", False, [])" + NL + "        self.defaultTime = BULKIO.PrecisionUTCTime(0, 0, 0, 0, 0)" + NL + "        self.port_lock = threading.Lock()" + NL + "        self.stats = self.linkStatistics(self)" + NL + "        self.sriDict = {} # key=streamID  value=(StreamSRI, PrecisionUTCTime)" + NL + "    " + NL + "    def setBitSize(self, bitSize):" + NL + "        self.stats.setBitSize(bitSize)" + NL + "" + NL + "    def enableStats(self, enabled):" + NL + "        self.stats.setEnabled(enabled)" + NL + "        " + NL + "    def updateStats(self, elementsReceived, queueSize, streamID, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            self.stats.update(elementsReceived, queueSize, streamID, connectionId)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def _get_connections(self):" + NL + "        currentConnections = []" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for id_, port in self.outPorts.items():" + NL + "                currentConnections.append(ExtendedCF.UsesConnection(id_, port))" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return currentConnections" + NL + "" + NL + "    def _get_statistics(self):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            recStat = self.stats.retrieve()" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return recStat" + NL + "" + NL + "    def _get_state(self):" + NL + "        if len(self._attachedStreams.values()) == 0:" + NL + "            return BULKIO.IDLE" + NL + "        # default behavior is to limit to one connection" + NL + "        elif len(self._attachedStreams.values()) == 1:" + NL + "            return BULKIO.BUSY" + NL + "        else:" + NL + "            return BULKIO.ACTIVE" + NL + "" + NL + "    def _get_attachedSRIs(self):" + NL + "        sris = []" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for entry in self.sriDict:" + NL + "                sri, t = self.sriDict[entry]" + NL + "                sris.append(copy.deepcopy(sri))" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return sris" + NL + "" + NL + "    def connectPort(self, connection, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            port = connection._narrow(";
  protected final String TEXT_88 = ".";
  protected final String TEXT_89 = ")" + NL + "            self.outPorts[str(connectionId)] = port" + NL + "            if self.lastStreamData:" + NL + "                self.attachedGroup[str(connectionId)] = port.attach(self.lastStreamData, self.lastName)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "    " + NL + "    def disconnectPort(self, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            entry = self.outPorts.pop(str(connectionId), None)" + NL + "            if connectionId in self.attachedGroup:" + NL + "                try:" + NL + "                    entry.detach(self.attachedGroup.pop(connectionId))" + NL + "                except:" + NL + "                    self.parent._log.exception(\"Unable to detach %s, should not have happened\", str(connectionId))" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def detach(self, attachId=None, connectionId=None):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            if attachId == None:" + NL + "                for entry in self.outPorts:" + NL + "                    try:" + NL + "                        if entry in self.attachedGroup:" + NL + "                            if connectionId == None or entry == connectionId:" + NL + "                                self.outPorts[entry].detach(self.attachedGroup[entry])" + NL + "                                self.attachedGroup.pop(entry)" + NL + "                    except:" + NL + "                        self.parent._log.exception(\"Unable to detach %s\", str(entry))" + NL + "                self.lastStreamData = None" + NL + "                self.lastName = None" + NL + "            else:" + NL + "                for entry in self.attachedGroup:" + NL + "                    try:" + NL + "                        if self.attachedGroup[entry] == attachId:" + NL + "                            if entry in self.outPorts:" + NL + "                                if connectionId == None or entry == connectionId:" + NL + "                                    self.outPorts[entry].detach(self.attachedGroup[entry])" + NL + "                            self.attachedGroup.pop(entry)" + NL + "                            if len(self.attachedGroup) == 0:" + NL + "                                self.lastStreamData = None" + NL + "                                self.lastName = None" + NL + "                            break" + NL + "                    except:" + NL + "                        self.parent._log.exception(\"Unable to detach %s\", str(entry))" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "    " + NL + "    def attach(self, streamData, name):" + NL + "        ids = []" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for entry in self.outPorts:" + NL + "                try:" + NL + "                    if entry in self.attachedGroup:" + NL + "                        self.outPorts[entry].detach(self.attachedGroup[entry])" + NL + "                    self.attachedGroup[entry] = self.outPorts[entry].attach(streamData, name)" + NL + "                    ids.append(self.attachedGroup[entry])" + NL + "                except:" + NL + "                    self.parent._log.exception(\"Unable to deliver update to %s\", str(entry))" + NL + "            self.lastStreamData = streamData" + NL + "            self.lastName = name" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        return ids" + NL + "" + NL + "    def getStreamDefinition(self, attachId):" + NL + "        return self.lastStreamData" + NL + "" + NL + "    def getUser(self, attachId):" + NL + "        return self.lastName" + NL + "    " + NL + "    def pushSRI(self, H, T):" + NL + "        self.port_lock.acquire()" + NL + "        self.sriDict[H.streamID] = (copy.deepcopy(H), copy.deepcopy(T))" + NL + "        self.defaultStreamSRI = H" + NL + "        self.defaultTime = T" + NL + "        try:" + NL + "            for connId, port in self.outPorts.items():" + NL + "                if port != None:" + NL + "                    try:" + NL + "                        port.pushSRI(H, T)" + NL + "                    except Exception:" + NL + "                        self.parent._log.exception(\"The call to pushSRI failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.refreshSRI = False" + NL + "            self.port_lock.release()" + NL + "        ";
  protected final String TEXT_90 = NL + NL + "class Port";
  protected final String TEXT_91 = "Out_i(";
  protected final String TEXT_92 = "_base.Port";
  protected final String TEXT_93 = "Out):" + NL + "    class linkStatistics:" + NL + "        class statPoint:" + NL + "            def __init__(self):" + NL + "                self.elements = 0" + NL + "                self.queueSize = 0.0" + NL + "                self.secs = 0.0" + NL + "                self.streamID = \"\"" + NL + "" + NL + "        def __init__(self, port_ref):" + NL + "            self.enabled = True" + NL + "            self.bitSize = struct.calcsize(";
  protected final String TEXT_94 = ") * 8" + NL + "            self.historyWindow = 10" + NL + "            self.receivedStatistics = {}" + NL + "            self.port_ref = port_ref" + NL + "            self.receivedStatistics_idx = {}" + NL + "" + NL + "        def setEnabled(self, enableStats):" + NL + "            self.enabled = enableStats" + NL + "" + NL + "        def update(self, elementsReceived, queueSize, streamID, connectionId):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            if self.receivedStatistics.has_key(connectionId):" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].elements = elementsReceived" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].queueSize = queueSize" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].secs = time.time()" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].streamID = streamID" + NL + "                self.receivedStatistics_idx[connectionId] += 1" + NL + "                self.receivedStatistics_idx[connectionId] = self.receivedStatistics_idx[connectionId]%self.historyWindow" + NL + "            else:" + NL + "                self.receivedStatistics[connectionId] = []" + NL + "                self.receivedStatistics_idx[connectionId] = 0" + NL + "                for i in range(self.historyWindow):" + NL + "                    self.receivedStatistics[connectionId].append(self.statPoint())" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].elements = elementsReceived" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].queueSize = queueSize" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].secs = time.time()" + NL + "                self.receivedStatistics[connectionId][self.receivedStatistics_idx[connectionId]].streamID = streamID" + NL + "                self.receivedStatistics_idx[connectionId] += 1" + NL + "                self.receivedStatistics_idx[connectionId] = self.receivedStatistics_idx[connectionId] % self.historyWindow" + NL + "" + NL + "        def retrieve(self):" + NL + "            if not self.enabled:" + NL + "                return" + NL + "" + NL + "            retVal = []" + NL + "            for entry in self.receivedStatistics:" + NL + "                runningStats = BULKIO.PortStatistics(portName=self.port_ref.name,averageQueueDepth=-1,elementsPerSecond=-1,bitsPerSecond=-1,callsPerSecond=-1,streamIDs=[],timeSinceLastCall=-1,keywords=[])" + NL + "" + NL + "                listPtr = (self.receivedStatistics_idx[entry] + 1) % self.historyWindow    # don't count the first set of data, since we're looking at change in time rather than absolute time" + NL + "                frontTime = self.receivedStatistics[entry][(self.receivedStatistics_idx[entry] - 1) % self.historyWindow].secs" + NL + "                backTime = self.receivedStatistics[entry][self.receivedStatistics_idx[entry]].secs" + NL + "                totalData = 0.0" + NL + "                queueSize = 0.0" + NL + "                streamIDs = []" + NL + "                while (listPtr != self.receivedStatistics_idx[entry]):" + NL + "                    totalData += self.receivedStatistics[entry][listPtr].elements" + NL + "                    queueSize += self.receivedStatistics[entry][listPtr].queueSize" + NL + "                    streamIDptr = 0" + NL + "                    foundstreamID = False" + NL + "                    while (streamIDptr != len(streamIDs)):" + NL + "                        if (streamIDs[streamIDptr] == self.receivedStatistics[entry][listPtr].streamID):" + NL + "                            foundstreamID = True" + NL + "                            break" + NL + "                        streamIDptr += 1" + NL + "                    if (not foundstreamID):" + NL + "                        streamIDs.append(self.receivedStatistics[entry][listPtr].streamID)" + NL + "                    listPtr += 1" + NL + "                    listPtr = listPtr % self.historyWindow" + NL + "" + NL + "                currentTime = time.time()" + NL + "                totalTime = currentTime - backTime" + NL + "                if totalTime == 0:" + NL + "                    totalTime = 1e6" + NL + "                receivedSize = len(self.receivedStatistics[entry])" + NL + "                runningStats.bitsPerSecond = (totalData * self.bitSize) / totalTime" + NL + "                runningStats.elementsPerSecond = totalData/totalTime" + NL + "                runningStats.averageQueueDepth = queueSize / receivedSize" + NL + "                runningStats.callsPerSecond = float((receivedSize - 1)) / totalTime" + NL + "                runningStats.streamIDs = streamIDs" + NL + "                runningStats.timeSinceLastCall = currentTime - frontTime" + NL + "                usesPortStat = BULKIO.UsesPortStatistics(connectionId=entry, statistics=runningStats)" + NL + "                retVal.append(usesPortStat)" + NL + "            return retVal" + NL + "" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.outConnections = {} # key=connectionId,  value=port" + NL + "        self.refreshSRI = False" + NL + "        self.stats = self.linkStatistics(self)" + NL + "        self.port_lock = threading.Lock()" + NL + "        self.sriDict = {} # key=streamID  value=StreamSRI" + NL + "" + NL + "    def connectPort(self, connection, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            port = connection._narrow(";
  protected final String TEXT_95 = ".";
  protected final String TEXT_96 = ")" + NL + "            self.outConnections[str(connectionId)] = port" + NL + "            self.refreshSRI = True" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def disconnectPort(self, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            self.outConnections.pop(str(connectionId), None)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def enableStats(self, enabled):" + NL + "        self.stats.setEnabled(enabled)" + NL + "        " + NL + "    def _get_connections(self):" + NL + "        currentConnections = []" + NL + "        self.port_lock.acquire()" + NL + "        for id_, port in self.outConnections.items():" + NL + "            currentConnections.append(ExtendedCF.UsesConnection(id_, port))" + NL + "        self.port_lock.release()" + NL + "        return currentConnections" + NL + "" + NL + "    def _get_statistics(self):" + NL + "        self.port_lock.acquire()" + NL + "        recStat = self.stats.retrieve()" + NL + "        self.port_lock.release()" + NL + "        return recStat" + NL + "" + NL + "    def _get_state(self):" + NL + "        self.port_lock.acquire()" + NL + "        numberOutgoingConnections = len(self.outConnections)" + NL + "        self.port_lock.release()" + NL + "        if numberOutgoingConnections == 0:" + NL + "            return BULKIO.IDLE" + NL + "        else:" + NL + "            return BULKIO.ACTIVE" + NL + "        return BULKIO.BUSY" + NL + "" + NL + "    def _get_activeSRIs(self):" + NL + "        self.port_lock.acquire()" + NL + "        sris = []" + NL + "        for entry in self.sriDict:" + NL + "            sris.append(copy.deepcopy(self.sriDict[entry]))" + NL + "        self.port_lock.release()" + NL + "        return sris" + NL + "" + NL + "    def pushSRI(self, H):" + NL + "        self.port_lock.acquire()" + NL + "        self.sriDict[H.streamID] = copy.deepcopy(H)" + NL + "        try:" + NL + "            for connId, port in self.outConnections.items():" + NL + "                if port != None:" + NL + "                    try:" + NL + "                        port.pushSRI(H)" + NL + "                    except Exception:" + NL + "                        self.parent._log.exception(\"The call to pushSRI failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.refreshSRI = False" + NL + "            self.port_lock.release()" + NL;
  protected final String TEXT_97 = NL + "    def ";
  protected final String TEXT_98 = "(self";
  protected final String TEXT_99 = ", ";
  protected final String TEXT_100 = "):" + NL + "        if self.refreshSRI:" + NL + "            if not self.sriDict.has_key(streamID):";
  protected final String TEXT_101 = NL + "                sri = BULKIO.StreamSRI(1, 0.0, 0.0, BULKIO.UNITS_TIME, 0, 0.0, 0.0, BULKIO.UNITS_NONE, 0, streamID, True, []) ";
  protected final String TEXT_102 = NL + "                sri = BULKIO.StreamSRI(1, 0.0, 1.0, BULKIO.UNITS_TIME, 0, 0.0, 0.0, BULKIO.UNITS_NONE, 0, streamID, True, []) ";
  protected final String TEXT_103 = NL + "                self.sriDict[streamID] = copy.deepcopy(sri)" + NL + "            self.pushSRI(self.sriDict[streamID])" + NL + "" + NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            for connId, port in self.outConnections.items():" + NL + "                if port != None:" + NL + "                    try:" + NL + "                        port.";
  protected final String TEXT_104 = "(";
  protected final String TEXT_105 = ", ";
  protected final String TEXT_106 = ")" + NL + "                        self.stats.update(";
  protected final String TEXT_107 = "1";
  protected final String TEXT_108 = "len(";
  protected final String TEXT_109 = ")";
  protected final String TEXT_110 = ", 0, streamID, connId)" + NL + "                    except Exception:" + NL + "                        self.parent._log.exception(\"The call to ";
  protected final String TEXT_111 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "            if EOS==True:" + NL + "                if self.sriDict.has_key(streamID):" + NL + "                    tmp = self.sriDict.pop(streamID)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL;
  protected final String TEXT_112 = NL + NL + "class Port";
  protected final String TEXT_113 = "Out_i(";
  protected final String TEXT_114 = "_base.Port";
  protected final String TEXT_115 = "Out):" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.outConnections = {}" + NL + "        self.port_lock = threading.Lock()" + NL + "" + NL + "    def connectPort(self, connection, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            port = connection._narrow(";
  protected final String TEXT_116 = ".";
  protected final String TEXT_117 = ")" + NL + "            self.outConnections[str(connectionId)] = port" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "    def disconnectPort(self, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            self.outConnections.pop(str(connectionId), None)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "        ";
  protected final String TEXT_118 = NL + "    def ";
  protected final String TEXT_119 = "(self";
  protected final String TEXT_120 = ", ";
  protected final String TEXT_121 = "):";
  protected final String TEXT_122 = NL + "        retVal = \"\"";
  protected final String TEXT_123 = NL + "        retVal = None";
  protected final String TEXT_124 = NL + "        retVal = []";
  protected final String TEXT_125 = NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            try:" + NL + "                for connId, port in self.outConnections.items():" + NL + "                    if port != None:";
  protected final String TEXT_126 = "retVal =";
  protected final String TEXT_127 = " port.";
  protected final String TEXT_128 = "(";
  protected final String TEXT_129 = ", ";
  protected final String TEXT_130 = ")" + NL + "            except Exception:" + NL + "                self.parent._log.exception(\"The call to ";
  protected final String TEXT_131 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL;
  protected final String TEXT_132 = NL + "        return retVal" + NL + " ";
  protected final String TEXT_133 = NL + "    def _get_";
  protected final String TEXT_134 = "(self):";
  protected final String TEXT_135 = NL + "        retVal = \"\"";
  protected final String TEXT_136 = NL + "        retVal = None";
  protected final String TEXT_137 = NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            for connId, port in self.outConnections.items():" + NL + "                if port != None:" + NL + "                    try:" + NL + "                        retVal = port._get_";
  protected final String TEXT_138 = "()" + NL + "                    except Exception:" + NL + "                        self.parent._log.exception(\"The call to _get_";
  protected final String TEXT_139 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "        return retVal" + NL + " ";
  protected final String TEXT_140 = NL + "    def _set_";
  protected final String TEXT_141 = "(self, data):" + NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            for connId, port in self.outConnections.items():" + NL + "                if port != None:" + NL + "                    try:" + NL + "                        port._set_";
  protected final String TEXT_142 = "(data)" + NL + "                    except Exception:" + NL + "                        self.parent._log.exception(\"The call to _set_";
  protected final String TEXT_143 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL;
  protected final String TEXT_144 = " ";

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    List<IPath> search_paths = templ.getSearchPaths();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    Uses usePort = null;
    Provides proPort  = null;
    if (!templ.isProvidesPort()) {
        for (Uses u : uses) {
            if (u.getRepID().equals(templ.getPortRepId())) {
                usePort  = u;
                break;
            }
        }
    } else {
        for (Provides p : provides) {
            if (p.getRepID().equals(templ.getPortRepId())) {
                proPort  = p;
                break;
            }
        }
    }
    boolean havePorts = (usePort != null) || (proPort != null);
    boolean generateGetters = false;
    boolean autoStart = false;
    boolean isResource = !softPkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase());

    List<String> nsList = new ArrayList<String>();
    PortHelper portHelper = new PortHelper();

    if (templ.isGenSupport()) {
        if (usePort != null) {
            String[] repParts = usePort.getRepID().split(":")[1].split("/");
            String ns = repParts[repParts.length - 2];
            if (!nsList.contains(ns)) {
                nsList.add(ns);
            }
        }

        if (proPort != null) {
            String[] repParts = proPort.getRepID().split(":")[1].split("/");
            String ns = repParts[repParts.length - 2];
            if (!nsList.contains(ns)) {
                nsList.add(ns);
            }
        }

        Collections.sort(nsList);

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    
    boolean includedUUID = false;
    boolean extraIncludes = false;
    for (String ns : nsList) {
        boolean isBulkio = "BULKIO".equalsIgnoreCase(ns);
        if ("CF".equalsIgnoreCase(ns)) {
            continue;
        }

        if (isBulkio && !extraIncludes) {

    stringBuffer.append(TEXT_3);
    
            extraIncludes = true;
        }
        if (isBulkio && !isResource && !includedUUID) {

    stringBuffer.append(TEXT_4);
    
            includedUUID = true;
        }

        String importNS = ns;
        if (isBulkio || "REDHAWK".equalsIgnoreCase(ns)) {
            importNS = ns.toLowerCase() + "." + ns.toLowerCase() + "Interfaces";
        } else {
            importNS = "redhawk." + ns.toLowerCase() + "Interfaces";
        }

    stringBuffer.append(TEXT_5);
    if (!ns.startsWith("Cos")) {
    stringBuffer.append(TEXT_6);
    stringBuffer.append(importNS);
    stringBuffer.append(TEXT_7);
    }
    stringBuffer.append(TEXT_8);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_10);
    
    }

    
    } else {

    
    for (Property tempProp : implSettings.getProperties()) {              
        if ("use_old_style".equals(tempProp.getId())) {
            if (!Boolean.parseBoolean(tempProp.getValue())) {
                generateGetters = false;
                continue;
            }
        }
        
        if ("auto_start".equals(tempProp.getId()) || "auto_start_component".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                autoStart = true;
                continue;
            }
        }
    }

    
    }
    if (templ.isGenClassDef()) {
        if (proPort != null) {
            Provides provide = proPort;

    stringBuffer.append(TEXT_11);
    stringBuffer.append(provide.getRepID().split(":")[1]);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PortHelper.idlToCamelPortClass(provide.getRepID()));
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PortHelper.idlToClassName(provide.getRepID()));
    stringBuffer.append(TEXT_14);
    stringBuffer.append(provide.getProvidesName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(PortHelper.idlToClassName(provide.getRepID()));
    stringBuffer.append(TEXT_16);
    
        }

        if (usePort != null) {
            Uses use = usePort;

    stringBuffer.append(TEXT_17);
    stringBuffer.append(use.getRepID().split(":")[1]);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(PortHelper.idlToCamelPortClass(use.getRepID()));
    stringBuffer.append(TEXT_19);
     if (usePort.getRepID().contains("BULKIO")) {
    stringBuffer.append(TEXT_20);
     } else { 
    stringBuffer.append(TEXT_21);
     } 
    stringBuffer.append(TEXT_22);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_23);
    
        } // end if usePort
    } // end if isGenClassDef
    if (templ.isGenClassImpl()) {
        if (proPort != null) {
            Provides tempProvide = proPort;
            String intName = tempProvide.getRepID();
            Interface intf = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            boolean bulkioData = false;
            if (intf == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            String nameSpace = intf.getNameSpace();
            String interfaceName = intf.getName();
            if ("BULKIO".equalsIgnoreCase(nameSpace)) {
                bulkioData = true;
            }

     
    if ("BULKIO".equals(nameSpace)) {

     
        if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_26);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_44);
    
        } else {

    stringBuffer.append(TEXT_45);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_46);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_48);
    stringBuffer.append(PortHelper.repToChar(tempProvide.getRepID()));
    stringBuffer.append(TEXT_49);
    if (bulkioData) {
    stringBuffer.append(TEXT_50);
     } 
    stringBuffer.append(TEXT_51);
    if (bulkioData) {
    stringBuffer.append(TEXT_52);
     } else { 
    stringBuffer.append(TEXT_53);
     } 
    stringBuffer.append(TEXT_54);
     
            // Provides Operations
            for (Operation op : intf.getOperations()) {
                if (!"pushPacket".equals(op.getName())) {
                    continue;
                }
                int numParams = op.getParams().size();

    stringBuffer.append(TEXT_55);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_56);
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_57);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_58);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_59);
    stringBuffer.append((numParams == 4) ? "T" : "None");
    stringBuffer.append(TEXT_60);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_61);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_62);
    stringBuffer.append((numParams == 4) ? "T" : "None");
    stringBuffer.append(TEXT_63);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_64);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_65);
    stringBuffer.append((numParams == 4) ? "T" : "None");
    stringBuffer.append(TEXT_66);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_67);
    
            } // end Provides Operations

    stringBuffer.append(TEXT_68);
    
        } // End provides else statement

    
    } else {

    stringBuffer.append(TEXT_69);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_70);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempProvide.getRepID()));
    stringBuffer.append(TEXT_72);
    if (bulkioData) {
    stringBuffer.append(TEXT_73);
     } 
    stringBuffer.append(TEXT_74);
     
            // Provides Operations
            for (Operation op : intf.getOperations()) {
                int numParams = op.getParams().size();

    stringBuffer.append(TEXT_75);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_76);
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_77);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_78);
    
            } // end Provides Operations
                
            for (Attribute op : intf.getAttributes()) {

    stringBuffer.append(TEXT_79);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_80);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_81);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_82);
    
                } // end if !readonly
            } // end Provides Attributes
    } // End provides else statement

    
        } // end if Provides Ports
        
        if (usePort != null) {
            Uses tempUse = usePort;
            String intName = tempUse.getRepID();
            Interface intf = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            boolean bulkioData = false;
            if (intf == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            String nameSpace = intf.getNameSpace();
            String interfaceName = intf.getName();
            // Check to see if this is a BULKIO data port
            boolean pushSRIFlag = false;
            boolean pushPacketFlag = false;
            if ("BULKIO".equals(nameSpace)) {
                for (Operation op : intf.getOperations()) {
                    if ("pushSRI".equals(op.getName())) {
                        pushSRIFlag = true;
                    } else if ("pushPacket".equals(op.getName())) {
                        pushPacketFlag = true;
                    }
                }
            }        
            bulkioData = (pushSRIFlag && pushPacketFlag);

    
    if ("BULKIO".equals(nameSpace)) {

    
        if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_85);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_87);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_89);
    
        } else {

    stringBuffer.append(TEXT_90);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_91);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_93);
    stringBuffer.append(PortHelper.repToChar(tempUse.getRepID()));
    stringBuffer.append(TEXT_94);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_96);
    
            // Operations (function calls)
            for (Operation op : intf.getOperations()) {
                if (!"pushPacket".equals(op.getName())) {
                    continue;
                }

    stringBuffer.append(TEXT_97);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_98);
    
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_99);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_100);
    
			if ("dataXML".equals(interfaceName)) {

    stringBuffer.append(TEXT_101);
    
			} else {

    stringBuffer.append(TEXT_102);
    
			}

    stringBuffer.append(TEXT_103);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_104);
    
            boolean first_param = true;
            for (Param p : op.getParams()) {
                if (first_param) { 
                    first_param=false;
                } else {
                    
    stringBuffer.append(TEXT_105);
    
                }
                    
    stringBuffer.append(p.getName());
    
            }

    stringBuffer.append(TEXT_106);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_107);
    } else {
    stringBuffer.append(TEXT_108);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_109);
    }
    stringBuffer.append(TEXT_110);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_111);
    
            } /* end for operations */
    } /* end SDDS test */

    
    } else {

    stringBuffer.append(TEXT_112);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_113);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(PortHelper.idlToCamelPortClass(tempUse.getRepID()));
    stringBuffer.append(TEXT_115);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_117);
    
        // Operations (function calls)
        for (Operation op : intf.getOperations()) {
            boolean returnType = PortHelper.hasReturn(op);

    stringBuffer.append(TEXT_118);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_119);
    
    for (Param p : op.getParams()){ if (!"out".equals(p.getDirection())) { 
    stringBuffer.append(TEXT_120);
    stringBuffer.append(p.getName());
    } }
    stringBuffer.append(TEXT_121);
    
            if (returnType) {
                if (op.getReturnType().contains("string")) { 

    stringBuffer.append(TEXT_122);
    
                } else if (PortHelper.getNumReturns(op) == 1) {

    stringBuffer.append(TEXT_123);
    
                } else {

    stringBuffer.append(TEXT_124);
    
                }
            }

    stringBuffer.append(TEXT_125);
    
            if (returnType) {

    stringBuffer.append(TEXT_126);
    
            }

    stringBuffer.append(TEXT_127);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_128);
    
            boolean first_param = true;
            for (Param p : op.getParams()) {
                if (!"out".equals(p.getDirection())) {
                	if (first_param) { 
                    	first_param=false;
                	} else {

    stringBuffer.append(TEXT_129);
    
                	}

    stringBuffer.append(p.getName());
    
				}
            }

    stringBuffer.append(TEXT_130);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_131);
     
            if (returnType) {

    stringBuffer.append(TEXT_132);
    
            }
        } /* end for operations */

        for (Attribute op : intf.getAttributes()) {

    stringBuffer.append(TEXT_133);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_134);
    
            if (op.getReturnType().contains("string")) { 

    stringBuffer.append(TEXT_135);
    
            } else {

    stringBuffer.append(TEXT_136);
    
            }

    stringBuffer.append(TEXT_137);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_138);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_139);
    
            if (!op.isReadonly()) {

    stringBuffer.append(TEXT_140);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_141);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_142);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_143);
    
            }
        } /* end for attributes */
    } /* end SDDS test */

    stringBuffer.append(TEXT_144);
     
        } // end if usePort
    } // end if isGenClassImpl

    return stringBuffer.toString();
  }
}

// END GENERATED CODE