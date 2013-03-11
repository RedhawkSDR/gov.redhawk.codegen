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
package gov.redhawk.ide.codegen.jet.java.template.component;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.java.PortHelper;
import gov.redhawk.ide.codegen.jet.java.JavaJetGeneratorPlugin;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.idl.IdlJavaUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jacorb.idl.Interface;
import org.jacorb.idl.Operation;
import org.jacorb.idl.ParamDecl;
import org.jacorb.idl.ScopedName;

/**
 * @generated
 */
public class ProvidesPortJavaTemplate
{

  protected static String nl;
  public static synchronized ProvidesPortJavaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesPortJavaTemplate result = new ProvidesPortJavaTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL;
  protected final String TEXT_3 = NL + "import java.util.ArrayList;" + NL + "import java.util.HashMap;" + NL + "import java.util.List;" + NL + "import java.util.Iterator;" + NL + "import java.util.Map;" + NL + "import org.omg.CORBA.TCKind;" + NL + "import org.ossie.properties.AnyUtils;" + NL + "import CF.DataType;" + NL;
  protected final String TEXT_4 = NL + "import BULKIO.dataSDDSPackage.AttachError;" + NL + "import BULKIO.dataSDDSPackage.DetachError;" + NL + "import BULKIO.dataSDDSPackage.InputUsageState;" + NL + "import BULKIO.dataSDDSPackage.StreamInputError;";
  protected final String TEXT_5 = NL + "import java.util.ArrayDeque;" + NL + "import java.util.concurrent.Semaphore;" + NL + "import java.util.concurrent.TimeUnit;";
  protected final String TEXT_6 = NL + NL + "import ";
  protected final String TEXT_7 = ".";
  protected final String TEXT_8 = ";" + NL;
  protected final String TEXT_9 = NL + "import ";
  protected final String TEXT_10 = ".*;";
  protected final String TEXT_11 = NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_12 = "_";
  protected final String TEXT_13 = "InPort extends ";
  protected final String TEXT_14 = " {" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected ";
  protected final String TEXT_15 = " parent;" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected String name;" + NL;
  protected final String TEXT_16 = "    ";
  protected final String TEXT_17 = " " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected linkStatistics stats;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Object sriUpdateLock;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Object statUpdateLock;" + NL;
  protected final String TEXT_18 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, sriState> currentHs;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Object dataBufferLock;" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected int maxQueueDepth;" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Semaphore queueSem;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Semaphore dataSem;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected boolean blocking;" + NL + "    ";
  protected final String TEXT_19 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, SDDSStreamDefinition> attachedStreamMap;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, String> attachedUsers;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, streamTimePair> currentHs;" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected boolean sriChanged;" + NL + "    ";
  protected final String TEXT_20 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_21 = "_";
  protected final String TEXT_22 = "InPort(";
  protected final String TEXT_23 = " parent, String portName) " + NL + "    {" + NL + "        this.parent = parent;" + NL + "        this.name = portName;" + NL + "        this.stats = new linkStatistics(this.name);" + NL + "        this.sriUpdateLock = new Object();" + NL + "        this.statUpdateLock = new Object();";
  protected final String TEXT_24 = NL + "        this.currentHs = new HashMap<String, sriState>();" + NL + "        this.dataBufferLock = new Object();" + NL + "        this.maxQueueDepth = 100;" + NL + "        this.queueSem = new Semaphore(this.maxQueueDepth);" + NL + "        this.dataSem = new Semaphore(0);" + NL + "        this.blocking = false;";
  protected final String TEXT_25 = NL + "        this.attachedStreamMap = new HashMap<String, SDDSStreamDefinition>();" + NL + "        this.attachedUsers = new HashMap<String, String>();" + NL + "        this.currentHs = new HashMap<String, streamTimePair>();" + NL + "        this.sriChanged = false;";
  protected final String TEXT_26 = NL + "         " + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL;
  protected final String TEXT_27 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void pushSRI(StreamSRI header) {" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            if (!this.currentHs.containsKey(header.streamID)) {" + NL + "                this.currentHs.put(header.streamID, new sriState(header, true));" + NL + "                if (header.blocking) {" + NL + "                    //If switching to blocking we have to set the semaphore" + NL + "                    synchronized (this.dataBufferLock) {" + NL + "                        if (!blocking) {" + NL + "                                try {" + NL + "                                    queueSem.acquire(data.size());" + NL + "                                } catch (InterruptedException e) {" + NL + "                                    e.printStackTrace();" + NL + "                                }    " + NL + "                        }" + NL + "                        blocking = true;" + NL + "                    }" + NL + "                }" + NL + "            } else {" + NL + "                StreamSRI oldSri = this.currentHs.get(header.streamID).getSRI();" + NL + "                if (!parent.compareSRI(header, oldSri)) {" + NL + "                    this.currentHs.put(header.streamID, new sriState(header, true));" + NL + "                    if (header.blocking) {" + NL + "                        //If switching to blocking we have to set the semaphore" + NL + "                        synchronized (this.dataBufferLock) {" + NL + "                            if (!blocking) {" + NL + "                                    try {" + NL + "                                        queueSem.acquire(data.size());" + NL + "                                    } catch (InterruptedException e) {" + NL + "                                        e.printStackTrace();" + NL + "                                    }    " + NL + "                            }" + NL + "                            blocking = true;" + NL + "                        }" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "        }" + NL + "" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL;
  protected final String TEXT_28 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void enableStats(boolean enable) {" + NL + "        this.stats.setEnabled(enable);" + NL + "    }" + NL;
  protected final String TEXT_29 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void setBitSize(double bitSize) {" + NL + "        synchronized (statUpdateLock) {" + NL + "            this.stats.setBitSize(bitSize);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void updateStats(int elementsReceived, float queueSize, boolean EOS, String streamID, boolean flush) {" + NL + "        synchronized (statUpdateLock) {" + NL + "            this.stats.update(elementsReceived, queueSize, EOS, streamID, flush);" + NL + "        }" + NL + "    };" + NL;
  protected final String TEXT_30 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public PortStatistics statistics() {" + NL + "        synchronized (statUpdateLock) {" + NL + "            return this.stats.retrieve();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public PortUsageType state() {";
  protected final String TEXT_31 = NL + "        int queueSize = 0;" + NL + "        synchronized (dataBufferLock) {" + NL + "            queueSize = data.size();" + NL + "\t        if (queueSize == this.maxQueueDepth) {" + NL + "\t            return PortUsageType.BUSY;" + NL + "\t        } else if (queueSize == 0) {" + NL + "\t            return PortUsageType.IDLE;" + NL + "\t        }" + NL + "\t        return PortUsageType.ACTIVE;" + NL + "\t    }";
  protected final String TEXT_32 = NL + "        //begin-user-code" + NL + "        // TODO you will need to provide a port implementation (queued ports option not selected)" + NL + "        //end-user-code";
  protected final String TEXT_33 = NL + "        if (this.currentHs.size() == 0) {" + NL + "            return PortUsageType.IDLE;" + NL + "        }" + NL + "        return PortUsageType.ACTIVE;";
  protected final String TEXT_34 = NL + "    }" + NL;
  protected final String TEXT_35 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public StreamSRI[] activeSRIs() {" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            ArrayList<StreamSRI> sris = new ArrayList<StreamSRI>();" + NL + "            Iterator<sriState> iter = this.currentHs.values().iterator();" + NL + "            while(iter.hasNext()) {" + NL + "                sris.add(iter.next().getSRI());" + NL + "            }" + NL + "            return sris.toArray(new StreamSRI[sris.size()]);" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public int getCurrentQueueDepth() {" + NL + "        synchronized (this.dataBufferLock) {" + NL + "            return data.size();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public int getMaxQueueDepth() {" + NL + "        synchronized (this.dataBufferLock) {" + NL + "            return this.maxQueueDepth;" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void setMaxQueueDepth(int newDepth) {" + NL + "        synchronized (this.dataBufferLock) {" + NL + "            this.maxQueueDepth = newDepth;" + NL + "            queueSem = new Semaphore(newDepth);" + NL + "        }" + NL + "    }" + NL;
  protected final String TEXT_36 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String getName() {" + NL + "        return this.name;" + NL + "    }";
  protected final String TEXT_37 = NL + "  ";
  protected final String TEXT_38 = "   " + NL + "    /**" + NL + "     * A class to hold packet data." + NL + "     * @generated" + NL + "     */" + NL + "    public class Packet<T extends Object> {" + NL + "        /** @generated */" + NL + "        private final T data;" + NL + "        /** @generated */" + NL + "        private final PrecisionUTCTime time;" + NL + "        /** @generated */" + NL + "        private final boolean endOfStream;" + NL + "        /** @generated */" + NL + "        private final String streamID;" + NL + "        /** @generated */" + NL + "        private final StreamSRI H;" + NL + "        /** @generated */" + NL + "        private final boolean inputQueueFlushed;" + NL + "        /** @generated */" + NL + "        private final boolean sriChanged;" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public Packet(T data, PrecisionUTCTime time, boolean endOfStream, String streamID, StreamSRI H, boolean sriChanged, boolean inputQueueFlushed) {" + NL + "            this.data = data;" + NL + "            this.time = time;" + NL + "            this.endOfStream = endOfStream;" + NL + "            this.streamID = streamID;" + NL + "            this.H = H;" + NL + "            this.inputQueueFlushed = inputQueueFlushed;" + NL + "            this.sriChanged = sriChanged;" + NL + "        };" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public T getData() {" + NL + "            return this.data;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public PrecisionUTCTime getTime() {" + NL + "            return this.time;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public boolean getEndOfStream() {" + NL + "            return this.endOfStream;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public String getStreamID() {" + NL + "            return this.streamID;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public StreamSRI getSRI() {" + NL + "            return this.H;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * This returns true if the input queue for the port was cleared because" + NL + "         * the queue reached its size limit. The number of packets discarded" + NL + "         * before this packet is equal to maxQueueDepth." + NL + "         * @generated" + NL + "         */" + NL + "        public boolean inputQueueFlushed() {" + NL + "            return this.inputQueueFlushed;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public boolean sriChanged() {" + NL + "            return this.sriChanged;" + NL + "        }" + NL + "    };" + NL + "    " + NL + "    /**" + NL + "     * This queue stores all packets received from pushPacket." + NL + "     * @generated" + NL + "     */" + NL + "    private ArrayDeque<Packet<";
  protected final String TEXT_39 = ">> data = new  ArrayDeque<Packet<";
  protected final String TEXT_40 = ">>();" + NL + "    ";
  protected final String TEXT_41 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */";
  protected final String TEXT_42 = NL + "    public void pushPacket(";
  protected final String TEXT_43 = " data, PrecisionUTCTime time, boolean endOfStream, String streamID)";
  protected final String TEXT_44 = NL + "    public void pushPacket(String data, boolean endOfStream, String streamID) ";
  protected final String TEXT_45 = " " + NL + "    {";
  protected final String TEXT_46 = NL + "        synchronized (this.dataBufferLock) {" + NL + "            if (this.maxQueueDepth == 0) {" + NL + "                return;" + NL + "            }" + NL + "        }" + NL + "        " + NL + "        boolean portBlocking = false;" + NL + "        StreamSRI tmpH = new StreamSRI(1, 0.0, 1.0, (short)1, 0, 0.0, 0.0, (short)0, (short)0, streamID, false, new DataType[0]);" + NL + "        boolean sriChanged = false;" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            if (this.currentHs.containsKey(streamID)) {" + NL + "                tmpH = this.currentHs.get(streamID).getSRI();" + NL + "                sriChanged = this.currentHs.get(streamID).isChanged();" + NL + "                this.currentHs.get(streamID).setChanged(false);" + NL + "                portBlocking = blocking;" + NL + "            }" + NL + "        }" + NL + "" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "" + NL + "        // determine whether to block and wait for an empty space in the queue";
  protected final String TEXT_47 = NL + "        Packet<";
  protected final String TEXT_48 = "> p = null;" + NL + "" + NL + "        if (portBlocking) {" + NL + "            p = new Packet<";
  protected final String TEXT_49 = ">(data, time, endOfStream, streamID, tmpH, sriChanged, false);" + NL + "" + NL + "            try {" + NL + "                queueSem.acquire();" + NL + "            } catch (InterruptedException e) {" + NL + "                e.printStackTrace();" + NL + "            }" + NL + "" + NL + "            synchronized (this.dataBufferLock) {" + NL + "                this.stats.update(data.length";
  protected final String TEXT_50 = "()";
  protected final String TEXT_51 = ", this.data.size()/this.maxQueueDepth, endOfStream, streamID, false);" + NL + "                this.data.add(p);" + NL + "                this.dataSem.release();" + NL + "            }" + NL + "        } else {" + NL + "            synchronized (this.dataBufferLock) {" + NL + "\t            if (this.data.size() == this.maxQueueDepth) {" + NL + "\t                this.data.clear();" + NL + "\t                p = new Packet<";
  protected final String TEXT_52 = ">(data, time, endOfStream, streamID, tmpH, sriChanged, true);" + NL + "\t                this.stats.update(data.length";
  protected final String TEXT_53 = "()";
  protected final String TEXT_54 = ", 0, endOfStream, streamID, true);" + NL + "\t            } else {" + NL + "                    p = new Packet<";
  protected final String TEXT_55 = ">(data, time, endOfStream, streamID, tmpH, sriChanged, false);" + NL + "\t                this.stats.update(data.length";
  protected final String TEXT_56 = "()";
  protected final String TEXT_57 = ", this.data.size()/this.maxQueueDepth, endOfStream, streamID, false);" + NL + "\t            }" + NL + "\t            this.data.add(p);" + NL + "\t            this.dataSem.release();" + NL + "\t        }" + NL + "        }";
  protected final String TEXT_58 = NL + "        Packet<";
  protected final String TEXT_59 = "> p = null;" + NL + "" + NL + "        if (portBlocking) {" + NL + "            p = new Packet<";
  protected final String TEXT_60 = ">(data, null, endOfStream, streamID, tmpH, sriChanged, false);" + NL + "" + NL + "            try {" + NL + "                queueSem.acquire();" + NL + "            } catch (InterruptedException e) {" + NL + "                e.printStackTrace();" + NL + "            }" + NL + "" + NL + "            synchronized (this.dataBufferLock) {" + NL + "                this.stats.update(data.length(), this.data.size()/this.maxQueueDepth, endOfStream, streamID, false);" + NL + "                this.data.add(p);" + NL + "            }" + NL + "        } else {" + NL + "            synchronized (this.dataBufferLock) {" + NL + "\t            if (this.data.size() == this.maxQueueDepth) {" + NL + "\t                this.data.clear();" + NL + "\t                p = new Packet<";
  protected final String TEXT_61 = ">(data, null, endOfStream, streamID, tmpH, sriChanged, true);" + NL + "\t                this.stats.update(data.length(), 0, endOfStream, streamID, true);" + NL + "\t            } else {" + NL + "                    p = new Packet<";
  protected final String TEXT_62 = ">(data, null, endOfStream, streamID, tmpH, sriChanged, false);" + NL + "\t                this.stats.update(data.length(), this.data.size()/this.maxQueueDepth, endOfStream, streamID, false);" + NL + "\t            }" + NL + "\t            this.data.add(p);" + NL + "\t        }" + NL + "        }";
  protected final String TEXT_63 = NL + NL + "        //begin-user-code" + NL + "        //end-user-code";
  protected final String TEXT_64 = NL + "        //begin-user-code" + NL + "        // TODO you will need to provide a port implementation (queued ports option not selected)" + NL + "        //end-user-code";
  protected final String TEXT_65 = " " + NL + "        return;" + NL + "    }" + NL;
  protected final String TEXT_66 = "     " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public Packet<";
  protected final String TEXT_67 = "> getPacket(long wait) " + NL + "    {" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "        try {" + NL + "            if (wait < 0) {" + NL + "                this.dataSem.acquire();" + NL + "            } else {" + NL + "                this.dataSem.tryAcquire(wait, TimeUnit.MILLISECONDS);" + NL + "            }" + NL + "        } catch (InterruptedException ex) {" + NL + "            return null;" + NL + "        }" + NL + "        " + NL + "        Packet<";
  protected final String TEXT_68 = "> p = null;" + NL + "        synchronized (this.dataBufferLock) {" + NL + "            p = this.data.poll();" + NL + "        }" + NL + "" + NL + "        if (p != null) {" + NL + "            if (p.getEndOfStream()) {" + NL + "                synchronized (this.sriUpdateLock) {" + NL + "                    if (this.currentHs.containsKey(p.getStreamID())) {" + NL + "                        sriState rem = this.currentHs.remove(p.getStreamID());" + NL + "" + NL + "                        if (rem.getSRI().blocking) {" + NL + "                            boolean stillBlocking = false;" + NL + "                            Iterator<sriState> iter = currentHs.values().iterator();" + NL + "                            while (iter.hasNext()) {" + NL + "                            \tif (iter.next().getSRI().blocking) {" + NL + "                                    stillBlocking = true;" + NL + "                                    break;" + NL + "                                }" + NL + "                            }" + NL + "" + NL + "                            if (!stillBlocking) {" + NL + "                                blocking = false;" + NL + "                            }" + NL + "                        }" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "            " + NL + "            if (blocking) {" + NL + "                queueSem.release();" + NL + "            }" + NL + "        }" + NL + "" + NL + "        return p;" + NL + "    }";
  protected final String TEXT_69 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "\tpublic class sriState {" + NL + "\t\t/** @generated */" + NL + "        protected StreamSRI sri;" + NL + "        /** @generated */" + NL + "        protected boolean changed;" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public sriState(StreamSRI sri, boolean changed) {" + NL + "        \tthis.sri = sri;" + NL + "        \tthis.changed = changed;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public StreamSRI getSRI() {" + NL + "        \treturn this.sri;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public boolean isChanged() {" + NL + "        \treturn this.changed;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void setSRI(StreamSRI sri) {" + NL + "        \tthis.sri = sri;" + NL + "        }" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void setChanged(boolean changed) {" + NL + "        \tthis.changed = changed;" + NL + "        }" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class statPoint {" + NL + "        /** @generated */" + NL + "        int elements;" + NL + "        /** @generated */" + NL + "        float queueSize;" + NL + "        /** @generated */" + NL + "        double secs;" + NL + "        /** @generated */" + NL + "        double usecs;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class linkStatistics {" + NL + "        /** @generated */" + NL + "        protected boolean enabled;" + NL + "        /** @generated */" + NL + "        protected double bitSize;" + NL + "        /** @generated */" + NL + "        protected PortStatistics runningStats;" + NL + "        /** @generated */" + NL + "        protected statPoint[] receivedStatistics;" + NL + "        /** @generated */" + NL + "        protected List< String > activeStreamIDs;" + NL + "        /** @generated */" + NL + "        protected int historyWindow;" + NL + "        /** @generated */" + NL + "        protected int receivedStatistics_idx;" + NL + "        /** @generated */" + NL + "        protected double flushTime;" + NL + "        /** @generated */" + NL + "        protected String portName;" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public linkStatistics(String portName) {" + NL + "            this.enabled = true;" + NL + "            this.bitSize = ";
  protected final String TEXT_70 = " * 8.0;" + NL + "            this.historyWindow = 10;" + NL + "            this.flushTime = 0.0;" + NL + "            this.receivedStatistics_idx = 0;" + NL + "            this.receivedStatistics = new ";
  protected final String TEXT_71 = "_";
  protected final String TEXT_72 = "InPort.statPoint[historyWindow];" + NL + "            this.activeStreamIDs = new ArrayList<String>();" + NL + "            this.portName = portName;" + NL + "            this.runningStats = new PortStatistics();" + NL + "            this.runningStats.portName = this.portName;" + NL + "            this.runningStats.elementsPerSecond = -1.0f;" + NL + "            this.runningStats.bitsPerSecond = -1.0f;" + NL + "            this.runningStats.callsPerSecond = -1.0f;" + NL + "            this.runningStats.averageQueueDepth = -1.0f;" + NL + "            this.runningStats.streamIDs = new String[0];" + NL + "            this.runningStats.timeSinceLastCall = -1.0f;" + NL + "            this.runningStats.keywords = new DataType[0];" + NL + "            for (int i = 0; i < historyWindow; ++i) {" + NL + "                this.receivedStatistics[i] = new ";
  protected final String TEXT_73 = "_";
  protected final String TEXT_74 = "InPort.statPoint();" + NL + "            }" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void setBitSize(double bitSize) {" + NL + "            this.bitSize = bitSize;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void setEnabled(boolean enableStats) {" + NL + "            this.enabled = enableStats;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void update(int elementsReceived, float queueSize, boolean EOS, String streamID, boolean flush) {" + NL + "            if (!this.enabled) {" + NL + "                return;" + NL + "            }" + NL + "            double currTime = System.nanoTime() * 1.0e-9;" + NL + "            this.receivedStatistics[this.receivedStatistics_idx].elements = elementsReceived;" + NL + "            this.receivedStatistics[this.receivedStatistics_idx].queueSize = queueSize;" + NL + "            this.receivedStatistics[this.receivedStatistics_idx++].secs = currTime;" + NL + "            if (!EOS) {" + NL + "                if (!this.activeStreamIDs.contains(streamID)) {" + NL + "                    this.activeStreamIDs.add(streamID);" + NL + "                }" + NL + "            } else {" + NL + "                this.activeStreamIDs.remove(streamID);" + NL + "            }" + NL + "            this.receivedStatistics_idx = this.receivedStatistics_idx % this.historyWindow;" + NL + "            if (flush) {" + NL + "                this.flushTime = currTime;" + NL + "            }" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public PortStatistics retrieve() {" + NL + "            if (!this.enabled) {" + NL + "                return null;" + NL + "            }" + NL + "            double secs = System.nanoTime() * 1.0e-9;" + NL + "            int idx = (this.receivedStatistics_idx == 0) ? (this.historyWindow - 1) : (this.receivedStatistics_idx - 1);" + NL + "            double front_sec = this.receivedStatistics[idx].secs;" + NL + "            double totalTime = secs - this.receivedStatistics[this.receivedStatistics_idx].secs;" + NL + "            double totalData = 0;" + NL + "            float queueSize = 0;" + NL + "            int startIdx = (this.receivedStatistics_idx + 1) % this.historyWindow;" + NL + "            for (int i = startIdx; i != this.receivedStatistics_idx; ) {" + NL + "                totalData += this.receivedStatistics[i].elements;" + NL + "                queueSize += this.receivedStatistics[i].queueSize;" + NL + "                i = (i + 1) % this.historyWindow;" + NL + "            }" + NL + "            int receivedSize = receivedStatistics.length;" + NL + "            synchronized (this.runningStats) {" + NL + "                this.runningStats.timeSinceLastCall = (float)(secs - front_sec);" + NL + "                this.runningStats.bitsPerSecond = (float)((totalData * this.bitSize) / totalTime);" + NL + "                this.runningStats.elementsPerSecond = (float)(totalData / totalTime);" + NL + "                this.runningStats.averageQueueDepth = (float)(queueSize / receivedSize);" + NL + "                this.runningStats.callsPerSecond = (float)((receivedSize - 1) / totalTime);" + NL + "                this.runningStats.streamIDs = this.activeStreamIDs.toArray(new String[0]);" + NL + "                if (flushTime != 0.0) {" + NL + "                    double flushTotalTime = secs - this.flushTime;" + NL + "                    this.runningStats.keywords = new DataType[1];" + NL + "                    this.runningStats.keywords[0] = new DataType();" + NL + "                    this.runningStats.keywords[0].id = \"timeSinceLastFlush\";" + NL + "                    this.runningStats.keywords[0].value = AnyUtils.toAny(new Double(flushTotalTime), TCKind.tk_double);" + NL + "                }" + NL + "            }" + NL + "            return this.runningStats;" + NL + "        }" + NL + "    }" + NL + "    ";
  protected final String TEXT_75 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class streamTimePair {" + NL + "        /** @generated */" + NL + "        StreamSRI stream;" + NL + "        /** @generated */" + NL + "        PrecisionUTCTime time;" + NL + "        " + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public streamTimePair(final StreamSRI stream, final PrecisionUTCTime time) {" + NL + "            this.stream = stream;" + NL + "            this.time = time;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public StreamSRI[] activeSRIs() {" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            return this.currentHs.keySet().toArray(new StreamSRI[0]);" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String attach(SDDSStreamDefinition stream, String userid) throws AttachError, StreamInputError {" + NL + "        final String attachId = this.parent.attach(stream, userid);" + NL + "        this.attachedStreamMap.put(attachId, stream);" + NL + "        this.attachedUsers.put(attachId, userid);" + NL + "" + NL + "        return attachId;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void detach(String attachId) throws DetachError, StreamInputError {" + NL + "        this.parent.detach(attachId);" + NL + "        this.attachedStreamMap.remove(attachId);" + NL + "        this.attachedUsers.remove(attachId);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public StreamSRI[] attachedSRIs() {" + NL + "        StreamSRI[] sris = new StreamSRI[0];" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            sris = new StreamSRI[this.currentHs.size()];" + NL + "            int idx = 0;" + NL + "" + NL + "            for (streamTimePair vals : this.currentHs.values()) {" + NL + "                sris[idx++] = vals.stream;" + NL + "            }" + NL + "        }" + NL + "        return sris;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public SDDSStreamDefinition[] attachedStreams() {" + NL + "        return this.attachedStreamMap.values().toArray(new SDDSStreamDefinition[0]);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String[] attachmentIds() {" + NL + "        return this.attachedStreamMap.keySet().toArray(new String[0]);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public SDDSStreamDefinition getStreamDefinition(String attachId) throws StreamInputError {" + NL + "        return this.attachedStreamMap.get(attachId);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String getUser(String attachId) throws StreamInputError {" + NL + "        return this.attachedUsers.get(attachId);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void pushSRI(StreamSRI H, PrecisionUTCTime T) {" + NL + "        synchronized (this.sriUpdateLock) {" + NL + "            streamTimePair tmpH = this.currentHs.get(H.streamID);" + NL + "            if (tmpH != null) {" + NL + "                tmpH.stream = H;" + NL + "                tmpH.time = T;" + NL + "            \tthis.sriChanged = !this.parent.compareSRI(tmpH.stream, H) || !this.parent.compareTime(tmpH.time, T);" + NL + "            } else {" + NL + "                this.currentHs.put(H.streamID, new streamTimePair(H, T));" + NL + "                this.sriChanged = true;" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public InputUsageState usageState() {" + NL + "        if (this.attachedStreamMap.size() == 0) {" + NL + "            return InputUsageState.IDLE;" + NL + "        } else if (this.attachedStreamMap.size() == 1) {" + NL + "            return InputUsageState.BUSY;" + NL + "        } else {" + NL + "            return InputUsageState.ACTIVE;" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public boolean hasSriChanged() {" + NL + "        return this.sriChanged;" + NL + "    }" + NL;
  protected final String TEXT_76 = NL + "    ";
  protected final String TEXT_77 = "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_78 = "_";
  protected final String TEXT_79 = "InPort(";
  protected final String TEXT_80 = " parent, String portName) " + NL + "    {" + NL + "        this.parent = parent;" + NL + "        this.name = portName;" + NL + "         " + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL;
  protected final String TEXT_81 = NL + "   " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_82 = " ";
  protected final String TEXT_83 = "(";
  protected final String TEXT_84 = ")";
  protected final String TEXT_85 = NL + "    {" + NL + "        //begin-user-code" + NL + "        // TODO you must provide an implementation for this port.";
  protected final String TEXT_86 = NL + "        return ";
  protected final String TEXT_87 = ";";
  protected final String TEXT_88 = NL + "        //end-user-code        " + NL + "    }";
  protected final String TEXT_89 = NL + NL + "}";

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    JavaTemplateParameter template = (JavaTemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    List<String> packages = new ArrayList<String>();
    String pkg = template.getPackage();
    mil.jpeojtrs.sca.spd.SoftPkg softPkg = template.getSoftPkg();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    Interface iface = IdlJavaUtil.getInstance().getInterface(search_paths, template.getPortRepId().split(":")[1], true);
    if (iface == null) {
    	throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + template.getPortRepId())); 
    }

    boolean useJNI = false;
    for (Property tempProp : implSettings.getProperties()) {
        if ("use_jni".equals(tempProp.getId())) {
            useJNI = Boolean.parseBoolean(tempProp.getValue());
        }
    }

    String nameSpace = iface.pack_name;
    String interfaceName = iface.name();
    packages.add(iface.pack_name);
    boolean isBulkio = "BULKIO".equals(nameSpace);
    String poaClass = interfaceName + "POA";
    if (useJNI && isBulkio) {
        poaClass = nameSpace + ".jni." + poaClass;
    }

    if (template.isGenClassDef() && template.isGenSupport()) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append(pkg + ".ports");
    stringBuffer.append(TEXT_2);
    
    }
    if (template.isGenSupport()) {
        if (isBulkio) {   

    stringBuffer.append(TEXT_3);
    
            if ("dataSDDS".equals(interfaceName)) {

    stringBuffer.append(TEXT_4);
    
            } else {

    stringBuffer.append(TEXT_5);
    
            }
        }

    stringBuffer.append(TEXT_6);
    stringBuffer.append(pkg);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    
        for (String pack : packages) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(pack);
    stringBuffer.append(TEXT_10);
    
        }
    } // end if template.isGenSupport()
    if (template.isGenClassDef()) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(poaClass);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_15);
    
        if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_16);
    
// For ports that we are aware of and can provide default implementations
List<String> basicDataPorts = Arrays.asList(new String[] {"dataChar", 
                                                          "dataDouble",
                                                          "dataFile",
                                                          "dataFloat", 
                                                          "dataOctet",
                                                          "dataShort",
                                                          "dataUshort",
                                                          "dataLong", 
                                                          "dataUlong",
                                                          "dataLongLong",
                                                          "dataUlongLong"});
boolean queued_ports = (template.getImplSettingsProperties().get("queued_ports") != null) ? 
                       (Boolean.parseBoolean(template.getImplSettingsProperties().get("queued_ports"))) :
                       true;
boolean isBasicDataPort = basicDataPorts.contains(interfaceName);
boolean isDataXml = "dataXML".equals(interfaceName);
boolean isDataSDDS = "dataSDDS".equals(interfaceName);

    stringBuffer.append(TEXT_17);
    
if (!isDataSDDS) {

    stringBuffer.append(TEXT_18);
    
} else {

    stringBuffer.append(TEXT_19);
    
}

    stringBuffer.append(TEXT_20);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_23);
      if (!isDataSDDS) { 
    stringBuffer.append(TEXT_24);
      } else {
    stringBuffer.append(TEXT_25);
      }
    stringBuffer.append(TEXT_26);
      if (!isDataSDDS) { 
    stringBuffer.append(TEXT_27);
      }
    stringBuffer.append(TEXT_28);
      if (isDataSDDS) { 
    stringBuffer.append(TEXT_29);
      }
    stringBuffer.append(TEXT_30);
      if (isBasicDataPort || isDataXml) {
        if (queued_ports) {

    stringBuffer.append(TEXT_31);
          } else { 
    stringBuffer.append(TEXT_32);
          }
    } else {

    stringBuffer.append(TEXT_33);
    
    }

    stringBuffer.append(TEXT_34);
      if (!isDataSDDS) { 
    stringBuffer.append(TEXT_35);
      }
    stringBuffer.append(TEXT_36);
      if (isBasicDataPort || isDataXml) {
      String dataTransfer = "";
      for (Operation op : IdlJavaUtil.getOperations(iface)) {
        if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
          dataTransfer = IdlJavaUtil.getParamType(IdlJavaUtil.getParams(op)[0]);
          break;
        }
      }

    stringBuffer.append(TEXT_37);
        if (queued_ports) {
    stringBuffer.append(TEXT_38);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_40);
        }
    stringBuffer.append(TEXT_41);
        if (isBasicDataPort) {
    stringBuffer.append(TEXT_42);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_43);
        } else if (isDataXml) {
    stringBuffer.append(TEXT_44);
        }
    stringBuffer.append(TEXT_45);
        if (queued_ports) {
    stringBuffer.append(TEXT_46);
          if (isBasicDataPort) {
    stringBuffer.append(TEXT_47);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_49);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_50);
    }
    stringBuffer.append(TEXT_51);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_52);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_53);
    }
    stringBuffer.append(TEXT_54);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_55);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_56);
    }
    stringBuffer.append(TEXT_57);
          } else if (isDataXml) {
    stringBuffer.append(TEXT_58);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_62);
          }
    stringBuffer.append(TEXT_63);
        } else {
    stringBuffer.append(TEXT_64);
        }
    stringBuffer.append(TEXT_65);
        if (queued_ports) {
    stringBuffer.append(TEXT_66);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_68);
        }
      }
    stringBuffer.append(TEXT_69);
    stringBuffer.append(PortHelper.ifaceToBytes(interfaceName));
    stringBuffer.append(TEXT_70);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_74);
      if (isDataSDDS) {
    stringBuffer.append(TEXT_75);
      }
    
        } else { // else not BULKIO

    stringBuffer.append(TEXT_76);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_80);
    for (Operation op : IdlJavaUtil.getOperations(iface)) {
    
        ScopedName[] raises = IdlJavaUtil.getRaises(op);
        String throwsString = (raises.length > 0) ? " throws " + raises[0].resolvedName() : "";
        for (int i = 1; i < raises.length; ++i) {
            throwsString += ", " + raises[i].resolvedName();
        } 
        ParamDecl[] params = IdlJavaUtil.getParams(op);
        String paramsString = "";
        for (int i = 0; i < params.length; i++) {
          paramsString += IdlJavaUtil.getParamType(params[i]) + " " + params[i].simple_declarator.name();
          if (i < params.length - 1) {
              paramsString += ",";
          }
        }
      
    stringBuffer.append(TEXT_81);
    stringBuffer.append(IdlJavaUtil.getReturnType(op));
    stringBuffer.append(TEXT_82);
    stringBuffer.append(IdlJavaUtil.getOpName(op));
    stringBuffer.append(TEXT_83);
    stringBuffer.append(paramsString);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(throwsString);
    stringBuffer.append(TEXT_85);
     if (!"void".equals(IdlJavaUtil.getReturnType(op))) {
    stringBuffer.append(TEXT_86);
    stringBuffer.append(IdlJavaUtil.getInitialValue(op));
    stringBuffer.append(TEXT_87);
    }
    stringBuffer.append(TEXT_88);
    } // end for Operations
    
        } // end of else nameSpace

    stringBuffer.append(TEXT_89);
    
    } // end if template.isGenClassDef()

    return stringBuffer.toString();
  }
}

// END GENERATED CODE