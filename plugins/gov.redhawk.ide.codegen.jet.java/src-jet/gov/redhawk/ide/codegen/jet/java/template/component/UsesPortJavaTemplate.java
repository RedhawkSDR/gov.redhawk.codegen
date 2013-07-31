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
import gov.redhawk.ide.codegen.java.JavaCodegenProperty;
import gov.redhawk.ide.codegen.java.StructJavaCodegenProperty;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.idl.IdlJavaUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import mil.jpeojtrs.sca.prf.AbstractProperty;
import mil.jpeojtrs.sca.spd.SoftPkg;
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
public class UsesPortJavaTemplate
{

  protected static String nl;
  public static synchronized UsesPortJavaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesPortJavaTemplate result = new UsesPortJavaTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL;
  protected final String TEXT_3 = NL + "import java.util.HashMap;" + NL + "import java.util.Map;" + NL + "" + NL + "import org.ossie.component.QueryableUsesPort;" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "import CF.PropertiesHelper;" + NL + "import CF.DataType;" + NL + "import org.omg.CORBA.ORB;" + NL + "import org.omg.CORBA.Any;" + NL + "import ";
  protected final String TEXT_4 = ".";
  protected final String TEXT_5 = ".*;";
  protected final String TEXT_6 = NL + "import java.util.Map.Entry;" + NL + "import ExtendedCF.UsesConnection;";
  protected final String TEXT_7 = NL + "import BULKIO.dataSDDSPackage.AttachError;" + NL + "import BULKIO.dataSDDSPackage.DetachError;" + NL + "import BULKIO.dataSDDSPackage.InputUsageState;" + NL + "import BULKIO.dataSDDSPackage.StreamInputError;";
  protected final String TEXT_8 = NL + "import org.ossie.events.*;";
  protected final String TEXT_9 = NL + "import ";
  protected final String TEXT_10 = ".*;";
  protected final String TEXT_11 = NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_12 = "_";
  protected final String TEXT_13 = "OutPort extends MessageSupplierPort {" + NL + "    public ";
  protected final String TEXT_14 = "_";
  protected final String TEXT_15 = "OutPort(String portName) {" + NL + "\t\tsuper(portName);" + NL + "\t}" + NL;
  protected final String TEXT_16 = NL + "    public void sendMessage(final ";
  protected final String TEXT_17 = "_struct message) {" + NL + "    \tfinal List<DataType> outProps = new ArrayList<DataType>();" + NL + "    \tfinal List<DataType> propStruct = new ArrayList<DataType>();" + NL + "    \tAny propStruct_any = ORB.init().create_any();";
  protected final String TEXT_18 = NL + "    \tpropStruct.add(new DataType(\"";
  protected final String TEXT_19 = "\", message.";
  protected final String TEXT_20 = ".toAny()));";
  protected final String TEXT_21 = NL + "    \tPropertiesHelper.insert(propStruct_any, propStruct.toArray(new DataType[propStruct.size()]));" + NL + "    \toutProps.add(new DataType(";
  protected final String TEXT_22 = ", propStruct_any));" + NL + "    \tAny outProps_any = ORB.init().create_any();" + NL + "    \tPropertiesHelper.insert(outProps_any, outProps.toArray(new DataType[outProps.size()]));" + NL + "    \tthis.push(outProps_any);" + NL + "    }" + NL + "    " + NL + "    public void sendMessages(final ArrayList<";
  protected final String TEXT_23 = "_struct> messages) {" + NL + "    \tfinal List<DataType> outProps = new ArrayList<DataType>();" + NL + "\t\tfor (";
  protected final String TEXT_24 = "_struct message : messages) {" + NL + "\t    \tfinal List<DataType> propStruct = new ArrayList<DataType>();" + NL + "\t    \tAny propStruct_any = ORB.init().create_any();";
  protected final String TEXT_25 = NL + "    \t\tpropStruct.add(new DataType(\"";
  protected final String TEXT_26 = "\", message.";
  protected final String TEXT_27 = ".toAny()));";
  protected final String TEXT_28 = NL + "\t    \tPropertiesHelper.insert(propStruct_any, propStruct.toArray(new DataType[propStruct.size()]));" + NL + "\t    \toutProps.add(new DataType(";
  protected final String TEXT_29 = ", propStruct_any));" + NL + "\t\t}" + NL + "    \tAny outProps_any = ORB.init().create_any();" + NL + "    \tPropertiesHelper.insert(outProps_any, outProps.toArray(new DataType[outProps.size()]));" + NL + "    \tthis.push(outProps_any);" + NL + "    }";
  protected final String TEXT_30 = NL + "}";
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = "/**" + NL + " * @generated" + NL + " */";
  protected final String TEXT_34 = NL + "public class ";
  protected final String TEXT_35 = " extends UsesPortStatisticsProviderPOA {" + NL;
  protected final String TEXT_36 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected ";
  protected final String TEXT_37 = " dataOut;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected boolean refreshSRI;" + NL + "    ";
  protected final String TEXT_38 = NL + "    /**" + NL + "     * Map of connection Ids to port objects" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, ";
  protected final String TEXT_39 = "Operations> outConnections = new HashMap<String, ";
  protected final String TEXT_40 = "Operations>();" + NL + "" + NL + "    /**" + NL + "     * Map of connection ID to statistics" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, ";
  protected final String TEXT_41 = ".linkStatistics> stats;" + NL;
  protected final String TEXT_42 = NL + "    /**" + NL + "     * Map of stream IDs to streamSRI's" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, StreamSRI> currentSRIs;" + NL;
  protected final String TEXT_43 = NL + "    /**" + NL + "     * Map of stream IDs to streamSRI/Time pairs" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, BULKIO_dataSDDSOutPort.streamTimePair> currentSRIs;" + NL + "" + NL + "    /**" + NL + "     * Map of attachIds to SDDSStreamDefinition/UserID pairs" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, BULKIO_dataSDDSOutPort.streamdefUseridPair> attachedGroup;" + NL + "" + NL + "    /**" + NL + "     * Map of attachIds to Ports" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, org.omg.CORBA.Object> attachedPorts;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected SDDSStreamDefinition lastStreamData;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected String userId;" + NL;
  protected final String TEXT_44 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "\tprotected String name;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "\tprotected Object updatingPortsLock;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "\tprotected boolean active;" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_45 = "(String portName) " + NL + "    {" + NL + "        this.name = portName;" + NL + "        this.updatingPortsLock = new Object();" + NL + "        this.active = false;" + NL + "        this.outConnections = new HashMap<String, ";
  protected final String TEXT_46 = "Operations>();" + NL + "        this.stats = new HashMap<String, ";
  protected final String TEXT_47 = ".linkStatistics>();";
  protected final String TEXT_48 = NL + "        this.currentSRIs = new HashMap<String, StreamSRI>();";
  protected final String TEXT_49 = NL + "        this.currentSRIs = new HashMap<String, streamTimePair>();" + NL + "        this.attachedGroup = new HashMap<String, ";
  protected final String TEXT_50 = ".streamdefUseridPair>();" + NL + "        this.attachedPorts = new HashMap<String, org.omg.CORBA.Object>();" + NL + "        this.lastStreamData = null;";
  protected final String TEXT_51 = NL + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public PortUsageType state() {" + NL + "        PortUsageType state = PortUsageType.IDLE;" + NL + "" + NL + "        if (this.outConnections.size() > 0) {" + NL + "            state = PortUsageType.ACTIVE;" + NL + "        }" + NL + "" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "" + NL + "        return state;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void enableStats(final boolean enable)" + NL + "    {" + NL + "        for (String connId : outConnections.keySet()) {" + NL + "            stats.get(connId).enableStats(enable);" + NL + "        }" + NL + "    };" + NL;
  protected final String TEXT_52 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void setBitSize(final double bitSize)" + NL + "    {" + NL + "        for (String connId : outConnections.keySet()) {" + NL + "            stats.get(connId).setBitSize(bitSize);" + NL + "        }" + NL + "    };" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void updateStats(final int elementsReceived, final int queueSize, final boolean EOS, final String streamID)" + NL + "    {" + NL + "        for (String connId : outConnections.keySet()) {" + NL + "            stats.get(connId).update(elementsReceived, queueSize, EOS, streamID);" + NL + "        }" + NL + "    };" + NL;
  protected final String TEXT_53 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public UsesPortStatistics[] statistics() {" + NL + "        UsesPortStatistics[] portStats = new UsesPortStatistics[this.outConnections.size()];" + NL + "        int i = 0;" + NL + "        " + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            for (String connId : this.outConnections.keySet()) {" + NL + "                portStats[i] = new UsesPortStatistics(connId, this.stats.get(connId).retrieve());" + NL + "            }" + NL + "        }" + NL + "        " + NL + "        return portStats;" + NL + "    }" + NL;
  protected final String TEXT_54 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public StreamSRI[] activeSRIs() " + NL + "    {" + NL + "        return this.currentSRIs.values().toArray(new StreamSRI[0]);" + NL + "    }" + NL;
  protected final String TEXT_55 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public StreamSRI[] activeSRIs() " + NL + "    {" + NL + "        List<StreamSRI> sris = new ArrayList<StreamSRI>();" + NL + "        for (streamTimePair val : this.currentSRIs.values()) {" + NL + "            sris.add(val.stream);" + NL + "        }" + NL + "        return sris.toArray(new StreamSRI[0]);" + NL + "    }" + NL;
  protected final String TEXT_56 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public boolean isActive() {" + NL + "        return this.active;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void setActive(final boolean active) {" + NL + "        this.active = active;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String getName() {" + NL + "        return this.name;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public HashMap<String, ";
  protected final String TEXT_57 = "Operations> getPorts() {" + NL + "        return new HashMap<String, ";
  protected final String TEXT_58 = "Operations>();" + NL + "    }";
  protected final String TEXT_59 = NL + "        " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void pushPacket(";
  protected final String TEXT_60 = " ";
  protected final String TEXT_61 = "Data, PrecisionUTCTime time, boolean endOfStream, String streamID) " + NL + "    {";
  protected final String TEXT_62 = NL + "        " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void pushPacket(";
  protected final String TEXT_63 = " ";
  protected final String TEXT_64 = "Data, boolean endOfStream, String streamID) " + NL + "    {";
  protected final String TEXT_65 = NL + "        if (this.refreshSRI) {" + NL + "        \tif (!this.currentSRIs.containsKey(streamID)) {";
  protected final String TEXT_66 = NL + "\t\t\t\tStreamSRI sri = new StreamSRI();" + NL + "\t\t\t\tsri.mode = 0;" + NL + "\t\t\t\tsri.xdelta = 0.0;" + NL + "\t\t\t\tsri.ydelta = 0.0;" + NL + "\t\t\t\tsri.subsize = 0;" + NL + "\t\t\t\tsri.xunits = 1; // TIME_S" + NL + "\t\t\t\tsri.streamID = streamID;" + NL + "                sri.blocking = false;";
  protected final String TEXT_67 = NL + "\t\t\t\tStreamSRI sri = new StreamSRI();" + NL + "\t\t\t\tsri.mode = 0;" + NL + "\t\t\t\tsri.xdelta = 1.0;" + NL + "\t\t\t\tsri.ydelta = 0.0;" + NL + "\t\t\t\tsri.subsize = 0;" + NL + "\t\t\t\tsri.xunits = 1; // TIME_S" + NL + "\t\t\t\tsri.streamID = streamID;" + NL + "                sri.blocking = false;";
  protected final String TEXT_68 = NL + "                this.currentSRIs.put(streamID, ";
  protected final String TEXT_69 = "sri";
  protected final String TEXT_70 = "new streamTimePair(sri, time)";
  protected final String TEXT_71 = ");" + NL + "            }" + NL + "            this.pushSRI(this.currentSRIs.get(streamID));" + NL + "        }" + NL + "        " + NL + "        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in" + NL + "            this.dataOut = ";
  protected final String TEXT_72 = "Data;" + NL + "            if (this.active) {" + NL + "                //begin-user-code" + NL + "                //end-user-code" + NL + "                " + NL + "                for (Entry<String, ";
  protected final String TEXT_73 = "Operations> p : this.outConnections.entrySet()) {" + NL + "                    try {";
  protected final String TEXT_74 = NL + "                        p.getValue().pushPacket(this.dataOut, time, endOfStream, streamID);" + NL + "                        this.stats.get(p.getKey()).update(this.dataOut.length";
  protected final String TEXT_75 = "()";
  protected final String TEXT_76 = ", 0, endOfStream, streamID);";
  protected final String TEXT_77 = NL + "                        p.getValue().pushPacket(this.dataOut, endOfStream, streamID);" + NL + "                        this.stats.get(p.getKey()).update(this.dataOut.length(), 0, endOfStream, streamID);";
  protected final String TEXT_78 = NL + "                    } catch(Exception e) {" + NL + "                        System.out.println(\"Call to pushPacket by ";
  protected final String TEXT_79 = " failed\");" + NL + "                    }" + NL + "                }" + NL + "" + NL + "                //begin-user-code" + NL + "                //end-user-code" + NL + "            }" + NL + "\t    if ( endOfStream ) {" + NL + "\t       if ( this.currentSRIs.containsKey(streamID) ) {" + NL + "                    this.currentSRIs.remove(streamID);" + NL + "\t\t}" + NL + "\t    }" + NL + "" + NL + "        }    // don't want to process while command information is coming in" + NL + "        " + NL + "        return;" + NL + "    }";
  protected final String TEXT_80 = NL + " " + NL + "    /**" + NL + "     * pushSRI" + NL + "     *     description: send out SRI describing the data payload" + NL + "     *" + NL + "     *  H: structure of type BULKIO::StreamSRI with the SRI for this stream" + NL + "     *    hversion" + NL + "     *    xstart: start time of the stream" + NL + "     *    xdelta: delta between two samples" + NL + "     *    xunits: unit types from Platinum specification" + NL + "     *    subsize: 0 if the data is one-dimensional" + NL + "     *    ystart" + NL + "     *    ydelta" + NL + "     *    yunits: unit types from Platinum specification" + NL + "     *    mode: 0-scalar, 1-complex" + NL + "     *    streamID: stream identifier" + NL + "     *    sequence<CF::DataType> keywords: unconstrained sequence of key-value pairs for additional description";
  protected final String TEXT_81 = NL + "     *" + NL + "     *  T: structure of type BULKIO::PrecisionUTCTime with the Time for this stream" + NL + "     *    tcmode: timecode mode" + NL + "     *    tcstatus: timecode status" + NL + "     *    toff: Fractional sample offset" + NL + "     *    twsec" + NL + "     *    tfsec";
  protected final String TEXT_82 = NL + "     * @generated" + NL + "     */" + NL + "    public void pushSRI(StreamSRI header";
  protected final String TEXT_83 = ", PrecisionUTCTime time";
  protected final String TEXT_84 = ") " + NL + "    {" + NL + "        // Header cannot be null" + NL + "        if (header == null) return;" + NL + "        // Header cannot have null keywords" + NL + "        if (header.keywords == null) header.keywords = new DataType[0];" + NL + "" + NL + "        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in" + NL + "            if (this.active) {" + NL + "                //begin-user-code" + NL + "                //end-user-code" + NL + "" + NL + "                for (";
  protected final String TEXT_85 = "Operations p : this.outConnections.values()) {" + NL + "                    try {" + NL + "                        p.pushSRI(header";
  protected final String TEXT_86 = ", time";
  protected final String TEXT_87 = ");" + NL + "                    } catch(Exception e) {" + NL + "                        System.out.println(\"Call to pushSRI by ";
  protected final String TEXT_88 = " failed\");" + NL + "                    }" + NL + "                }" + NL + "            }" + NL + "" + NL + "            //begin-user-code" + NL + "            //end-user-code" + NL + "            " + NL + "            this.currentSRIs.put(header.streamID, ";
  protected final String TEXT_89 = "header";
  protected final String TEXT_90 = "new streamTimePair(header, time)";
  protected final String TEXT_91 = ");";
  protected final String TEXT_92 = NL + "            this.refreshSRI = false;";
  protected final String TEXT_93 = NL + NL + "            //begin-user-code" + NL + "            //end-user-code" + NL + "        }    // don't want to process while command information is coming in" + NL + "" + NL + "        return;" + NL + "    }";
  protected final String TEXT_94 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void connectPort(final org.omg.CORBA.Object connection, final String connectionId) throws CF.PortPackage.InvalidPort, CF.PortPackage.OccupiedPort" + NL + "    {" + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            final ";
  protected final String TEXT_95 = "Operations port;" + NL + "            try {" + NL + "            \tport = ";
  protected final String TEXT_96 = ".narrow(connection);" + NL + "            } catch (final Exception ex) {" + NL + "            \tthrow new CF.PortPackage.InvalidPort((short)1, \"Invalid port for connection '\" + connectionId + \"'\");" + NL + "            }" + NL + "            this.outConnections.put(connectionId, port);" + NL + "            this.active = true;" + NL + "            this.stats.put(connectionId, new linkStatistics());";
  protected final String TEXT_97 = NL + "            this.refreshSRI = true;";
  protected final String TEXT_98 = NL + "            if (this.lastStreamData != null) {" + NL + "                try {" + NL + "                    final String attachId = port.attach(this.lastStreamData, this.userId);" + NL + "                    this.attachedGroup.put(attachId, new streamdefUseridPair(this.lastStreamData, this.userId));" + NL + "                    this.attachedPorts.put(attachId, connection);" + NL + "                } catch (AttachError a) {" + NL + "                    // PASS" + NL + "                } catch (StreamInputError e) {" + NL + "                    // PASS" + NL + "                }" + NL + "            }";
  protected final String TEXT_99 = NL + "        " + NL + "            //begin-user-code" + NL + "            //end-user-code" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void disconnectPort(String connectionId) {" + NL + "        synchronized (this.updatingPortsLock) {";
  protected final String TEXT_100 = NL + "            ";
  protected final String TEXT_101 = "Operations port = this.outConnections.remove(connectionId);" + NL + "            this.stats.remove(connectionId);" + NL + "            this.active = (this.outConnections.size() != 0);" + NL;
  protected final String TEXT_102 = NL + "            for (Entry<String, org.omg.CORBA.Object> entry : this.attachedPorts.entrySet()) {" + NL + "                if (entry.getValue().equals((org.omg.CORBA.Object) port)) {" + NL + "                    final String attachId = entry.getKey();" + NL + "                    this.attachedPorts.remove(attachId);" + NL + "                    this.attachedGroup.remove(attachId);" + NL + "                    try {" + NL + "                        port.detach(attachId);" + NL + "                    } catch (DetachError e) {" + NL + "                        // PASS" + NL + "                    } catch (StreamInputError e) {" + NL + "                        // PASS" + NL + "                    }" + NL + "                    break;" + NL + "                }" + NL + "            }" + NL + "            ";
  protected final String TEXT_103 = NL + "            //begin-user-code" + NL + "            //end-user-code" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public UsesConnection[] connections() {" + NL + "        final UsesConnection[] connList = new UsesConnection[this.outConnections.size()];" + NL + "        int i = 0;" + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            for (Entry<String, ";
  protected final String TEXT_104 = "Operations> ent : this.outConnections.entrySet()) {" + NL + "                connList[i++] = new UsesConnection(ent.getKey(), (org.omg.CORBA.Object) ent.getValue());" + NL + "            }" + NL + "        }" + NL + "        return connList;" + NL + "    }" + NL;
  protected final String TEXT_105 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public SDDSStreamDefinition getStreamDefinition(final String attachId)" + NL + "    {" + NL + "        final ";
  protected final String TEXT_106 = ".streamdefUseridPair defPair = this.attachedGroup.get(attachId);" + NL + "        return (defPair == null) ? new SDDSStreamDefinition() : defPair.streamDef;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String getUser(final String attachId)" + NL + "    {" + NL + "        final ";
  protected final String TEXT_107 = ".streamdefUseridPair defPair = this.attachedGroup.get(attachId);" + NL + "        return (defPair == null) ? \"\" : defPair.userId;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public InputUsageState usageState()" + NL + "    {" + NL + "        if (this.attachedGroup.size() == 0) {" + NL + "            return InputUsageState.IDLE;" + NL + "        } else if (this.attachedGroup.size() == 1) {" + NL + "            return InputUsageState.BUSY;" + NL + "        } else {" + NL + "            return InputUsageState.ACTIVE;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public SDDSStreamDefinition[] attachedStreams()" + NL + "    {" + NL + "        return new SDDSStreamDefinition[] { (this.lastStreamData != null) ? this.lastStreamData : new SDDSStreamDefinition()};" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String[] attachmentIds()" + NL + "    {" + NL + "        return this.attachedGroup.keySet().toArray(new String[0]);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public String attach(final SDDSStreamDefinition streamDef, final String userId) throws AttachError, StreamInputError" + NL + "    {" + NL + "        String attachId = null;" + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            this.userId = userId;" + NL + "            this.lastStreamData = streamDef;" + NL + "            for (Entry<String, org.omg.CORBA.Object> entry : this.attachedPorts.entrySet()) {" + NL + "                try {" + NL + "                    ((dataSDDSOperations) entry.getValue()).detach(entry.getKey());" + NL + "                } catch (DetachError d) {" + NL + "                    // PASS" + NL + "                }" + NL + "                this.attachedGroup.remove(entry.getKey());" + NL + "            }" + NL + "            " + NL + "            for (dataSDDSOperations port : this.outConnections.values()) {" + NL + "                attachId = port.attach(streamDef, userId);" + NL + "                this.attachedGroup.put(attachId, new ";
  protected final String TEXT_108 = ".streamdefUseridPair(streamDef, userId));" + NL + "                this.attachedPorts.put(attachId, (org.omg.CORBA.Object) port);" + NL + "            }" + NL + "        }" + NL + "        return attachId;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public void detach(String attachId, String connectionId) throws DetachError, StreamInputError" + NL + "    {" + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            dataSDDSOperations port = this.outConnections.get(connectionId);" + NL + "            if (this.attachedPorts.containsKey(attachId)) {" + NL + "                port.detach(attachId);" + NL + "            }" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class streamTimePair {" + NL + "        /** @generated */" + NL + "        StreamSRI stream;" + NL + "        /** @generated */" + NL + "        PrecisionUTCTime time;" + NL + "        " + NL + "        /** " + NL + "         * @generated" + NL + "         */" + NL + "        public streamTimePair(final StreamSRI stream, final PrecisionUTCTime time) {" + NL + "            this.stream = stream;" + NL + "            this.time = time;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class streamdefUseridPair {" + NL + "        /** @generated */" + NL + "        SDDSStreamDefinition streamDef;" + NL + "        /** @generated */" + NL + "        String userId;" + NL + "        " + NL + "        /** " + NL + "         * @generated" + NL + "         */" + NL + "        public streamdefUseridPair(final SDDSStreamDefinition streamDef, final String userId) {" + NL + "            this.streamDef = streamDef;" + NL + "            this.userId = userId;" + NL + "        }" + NL + "    }" + NL;
  protected final String TEXT_109 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class statPoint implements Cloneable {" + NL + "        /** @generated */" + NL + "        int elements;" + NL + "        /** @generated */" + NL + "        float queueSize;" + NL + "        /** @generated */" + NL + "        double secs;" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public class linkStatistics {" + NL + "        /** @generated */" + NL + "        protected double bitSize;" + NL + "        /** @generated */" + NL + "        protected PortStatistics runningStats;" + NL + "        /** @generated */" + NL + "        protected statPoint[] receivedStatistics;" + NL + "        /** @generated */" + NL + "        protected List<String> activeStreamIDs;" + NL + "        /** @generated */" + NL + "        protected final int historyWindow;" + NL + "        /** @generated */" + NL + "        protected int receivedStatistics_idx;" + NL + "        /** @generated */" + NL + "        protected boolean enabled;" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public linkStatistics() {" + NL + "            this.enabled = true;" + NL + "            this.bitSize = ";
  protected final String TEXT_110 = " * 8.0;" + NL + "            this.historyWindow = 10;" + NL + "            this.receivedStatistics_idx = 0;" + NL + "            this.receivedStatistics = new ";
  protected final String TEXT_111 = ".statPoint[historyWindow];" + NL + "            this.activeStreamIDs = new ArrayList<String>();" + NL + "            this.runningStats = new PortStatistics();" + NL + "            this.runningStats.portName = ";
  protected final String TEXT_112 = ".this.name;" + NL + "            this.runningStats.elementsPerSecond = -1.0f;" + NL + "            this.runningStats.bitsPerSecond = -1.0f;" + NL + "            this.runningStats.callsPerSecond = -1.0f;" + NL + "            this.runningStats.averageQueueDepth = -1.0f;" + NL + "            this.runningStats.streamIDs = new String[0];" + NL + "            this.runningStats.timeSinceLastCall = -1.0f;" + NL + "            this.runningStats.keywords = new DataType[0];" + NL + "            for (int i = 0; i < historyWindow; ++i) {" + NL + "                this.receivedStatistics[i] = new ";
  protected final String TEXT_113 = ".statPoint();" + NL + "            }" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void setBitSize(double bitSize) {" + NL + "            this.bitSize = bitSize;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void enableStats(boolean enable) {" + NL + "            this.enabled = enable;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public void update(int elementsReceived, float queueSize, boolean EOS, String streamID) {" + NL + "            if (!this.enabled) {" + NL + "                return;" + NL + "            }" + NL + "            long nanos = System.nanoTime();" + NL + "            this.receivedStatistics[this.receivedStatistics_idx].elements = elementsReceived;" + NL + "            this.receivedStatistics[this.receivedStatistics_idx].queueSize = queueSize;" + NL + "            this.receivedStatistics[this.receivedStatistics_idx++].secs = nanos * 1.0e-9;" + NL + "            this.receivedStatistics_idx = this.receivedStatistics_idx % this.historyWindow;" + NL + "            if (!EOS) {" + NL + "                if (!this.activeStreamIDs.contains(streamID)) {" + NL + "                    this.activeStreamIDs.add(streamID);" + NL + "                }" + NL + "            } else {" + NL + "                this.activeStreamIDs.remove(streamID);" + NL + "            }" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @generated" + NL + "         */" + NL + "        public PortStatistics retrieve() {" + NL + "            if (!this.enabled) {" + NL + "                return null;" + NL + "            }" + NL + "            long nanos = System.nanoTime();" + NL + "            double secs = nanos * 1.0e-9;" + NL + "            int idx = (this.receivedStatistics_idx == 0) ? (this.historyWindow - 1) : (this.receivedStatistics_idx - 1) % this.historyWindow;" + NL + "            double front_sec = this.receivedStatistics[idx].secs;" + NL + "            double totalTime = secs - this.receivedStatistics[this.receivedStatistics_idx].secs;" + NL + "            double totalData = 0;" + NL + "            float queueSize = 0;" + NL + "            int startIdx = (this.receivedStatistics_idx + 1) % this.historyWindow;" + NL + "            for (int i = startIdx; i != receivedStatistics_idx; ) {" + NL + "                totalData += this.receivedStatistics[i].elements;" + NL + "                queueSize += this.receivedStatistics[i].queueSize;" + NL + "                i = (i + 1) % this.historyWindow;" + NL + "            }" + NL + "            int receivedSize = receivedStatistics.length;" + NL + "            synchronized (this.runningStats) {" + NL + "                this.runningStats.timeSinceLastCall = (float)(secs - front_sec);" + NL + "                this.runningStats.bitsPerSecond = (float)((totalData * this.bitSize) / totalTime);" + NL + "                this.runningStats.elementsPerSecond = (float)(totalData / totalTime);" + NL + "                this.runningStats.averageQueueDepth = (float)(queueSize / receivedSize);" + NL + "                this.runningStats.callsPerSecond = (float)((receivedSize - 1) / totalTime);" + NL + "                this.runningStats.streamIDs = this.activeStreamIDs.toArray(new String[0]);" + NL + "            }" + NL + "            return runningStats;" + NL + "        }" + NL + "    }" + NL + "    ";
  protected final String TEXT_114 = NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_115 = "_";
  protected final String TEXT_116 = "OutPort extends QueryableUsesPort<";
  protected final String TEXT_117 = "Operations> implements ";
  protected final String TEXT_118 = "Operations {" + NL + "" + NL + "    /**" + NL + "     * Map of connection Ids to port objects" + NL + "     * @generated" + NL + "     */" + NL + "    protected Map<String, ";
  protected final String TEXT_119 = "Operations> outConnections = new HashMap<String, ";
  protected final String TEXT_120 = "Operations>();" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_121 = "_";
  protected final String TEXT_122 = "OutPort(String portName) " + NL + "    {" + NL + "        super(portName);" + NL + "" + NL + "        this.outConnections = new HashMap<String, ";
  protected final String TEXT_123 = "Operations>();" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected ";
  protected final String TEXT_124 = "Operations narrow(org.omg.CORBA.Object connection) " + NL + "    {";
  protected final String TEXT_125 = NL + "        ";
  protected final String TEXT_126 = "Operations ops = ";
  protected final String TEXT_127 = ".narrow(connection);" + NL + "        " + NL + "        //begin-user-code " + NL + "        //end-user-code " + NL + "        " + NL + "        return ops; " + NL + "    }" + NL + "" + NL + "    public void connectPort(final org.omg.CORBA.Object connection, final String connectionId) throws CF.PortPackage.InvalidPort, CF.PortPackage.OccupiedPort" + NL + "    {" + NL + "        try {" + NL + "            // don't want to process while command information is coming in" + NL + "            synchronized (this.updatingPortsLock) {" + NL + "                super.connectPort(connection, connectionId);" + NL + "                final ";
  protected final String TEXT_128 = "Operations port = ";
  protected final String TEXT_129 = ".narrow(connection);" + NL + "                this.outConnections.put(connectionId, port);" + NL + "                this.active = true;" + NL + "            }" + NL + "        } catch (final Throwable t) {" + NL + "            t.printStackTrace();" + NL + "        }" + NL + "" + NL + "    }" + NL + "" + NL + "    public void disconnectPort(final String connectionId) {" + NL + "        // don't want to process while command information is coming in" + NL + "        synchronized (this.updatingPortsLock) {" + NL + "            super.disconnectPort(connectionId);" + NL + "            this.outConnections.remove(connectionId);" + NL + "            this.active = (this.outConnections.size() != 0);" + NL + "        }" + NL + "    }" + NL;
  protected final String TEXT_130 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_131 = " ";
  protected final String TEXT_132 = "(";
  protected final String TEXT_133 = ") ";
  protected final String TEXT_134 = NL + "    {";
  protected final String TEXT_135 = " ";
  protected final String TEXT_136 = ") ";
  protected final String TEXT_137 = NL + "    {";
  protected final String TEXT_138 = ", ";
  protected final String TEXT_139 = NL + "        ";
  protected final String TEXT_140 = " retval = ";
  protected final String TEXT_141 = ";" + NL + "        ";
  protected final String TEXT_142 = NL + "        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in" + NL + "            if (this.active) {" + NL + "                //begin-user-code" + NL + "                //end-user-code" + NL + "                " + NL + "                for (";
  protected final String TEXT_143 = "Operations p : this.outConnections.values()) {" + NL + "                    ";
  protected final String TEXT_144 = "retval = ";
  protected final String TEXT_145 = "p.";
  protected final String TEXT_146 = "(";
  protected final String TEXT_147 = ");";
  protected final String TEXT_148 = ");";
  protected final String TEXT_149 = ", ";
  protected final String TEXT_150 = NL + "                }" + NL + "            }" + NL + "        }    // don't want to process while command information is coming in" + NL + "        " + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "        " + NL + "        return";
  protected final String TEXT_151 = " retval";
  protected final String TEXT_152 = ";" + NL + "    }";
  protected final String TEXT_153 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    protected ";
  protected final String TEXT_154 = " Sequence_";
  protected final String TEXT_155 = "_";
  protected final String TEXT_156 = ";";
  protected final String TEXT_157 = NL + "}";
  protected final String TEXT_158 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    JavaTemplateParameter template = (JavaTemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    List<String> packages = new ArrayList<String>();
    SoftPkg softPkg = template.getSoftPkg();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    String pkg = template.getPackage();
    boolean pushXMLPacketCall = false;
    boolean pushPacketCall = false;
    boolean pushSRICall = false;
    Map<String, AbstractProperty> properties = JavaGeneratorUtils.createPropertiesSet(softPkg, "prop");
    List<JavaCodegenProperty> javaProperties = JavaGeneratorUtils.createJavaProps(1, properties);
    String dataTransfer = "";
    String type = "";
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
    String helperName = interfaceName + "Helper";
    if (useJNI && "BULKIO".equals(nameSpace)) {
        helperName = nameSpace + ".jni." + helperName;
    }
    boolean dataSDDSPort = interfaceName.contains("dataSDDS");
    for (Operation op : IdlJavaUtil.getOperations(iface)) {
        if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
            dataTransfer = IdlJavaUtil.getParamType(IdlJavaUtil.getParams(op)[0]);
            if (IdlJavaUtil.getParams(op).length == 4) {
                pushPacketCall = true;
            } else {
                pushXMLPacketCall = true;
            }
        } else if ("pushSRI".equals(IdlJavaUtil.getOpName(op))) {
            pushSRICall = true;
        }
    }
    packages.add(iface.pack_name);
    if (template.isGenClassDef() && template.isGenSupport()) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append(pkg + ".ports");
    stringBuffer.append(TEXT_2);
    
    }
    if (template.isGenSupport()) {

    stringBuffer.append(TEXT_3);
    stringBuffer.append(pkg);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_5);
    
        if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_6);
    
            if (dataSDDSPort) {

    stringBuffer.append(TEXT_7);
    
            }
        }
    	if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_8);
    
    	} else {
        	for (String pack : packages) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(pack);
    stringBuffer.append(TEXT_10);
    
        	}
    	}
    } // end if template.isGenSupport()
    if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_15);
    
		if (javaProperties != null) {
			for (JavaCodegenProperty property : javaProperties) {
				if (property instanceof StructJavaCodegenProperty) {
                    StructJavaCodegenProperty structProperty = (StructJavaCodegenProperty) property;
					for (String kindValue : structProperty.getKindValues()) {
						if (kindValue.equals("message")) {

    stringBuffer.append(TEXT_16);
    stringBuffer.append(property.getId().substring(1,property.getId().length()-1));
    stringBuffer.append(TEXT_17);
    
					for (final String name : structProperty.getFields().keySet()) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_20);
    
					}

    stringBuffer.append(TEXT_21);
    stringBuffer.append(property.getId());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(property.getId().substring(1,property.getId().length()-1));
    stringBuffer.append(TEXT_23);
    stringBuffer.append(property.getId().substring(1,property.getId().length()-1));
    stringBuffer.append(TEXT_24);
    
					for (final String name : structProperty.getFields().keySet()) {

    stringBuffer.append(TEXT_25);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_27);
    
					}

    stringBuffer.append(TEXT_28);
    stringBuffer.append(property.getId());
    stringBuffer.append(TEXT_29);
    
						}
					}
				}
			} 
		}

    stringBuffer.append(TEXT_30);
    
	} else if (template.isGenClassDef()) {

    stringBuffer.append(TEXT_31);
    
        if ("BULKIO".equals(nameSpace)) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    	String className = nameSpace + "_" + interfaceName + "OutPort"; 
    stringBuffer.append(TEXT_34);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_35);
    
        if (pushPacketCall || pushXMLPacketCall) { 

    stringBuffer.append(TEXT_36);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_37);
    
        }

    stringBuffer.append(TEXT_38);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_41);
    
        if (pushPacketCall || pushXMLPacketCall) { 

    stringBuffer.append(TEXT_42);
    
        } else if (dataSDDSPort) { 

    stringBuffer.append(TEXT_43);
    
        } 

    stringBuffer.append(TEXT_44);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_47);
    
        if (!dataSDDSPort) { 

    stringBuffer.append(TEXT_48);
    
        } else { 

    stringBuffer.append(TEXT_49);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_50);
    
        } 

    stringBuffer.append(TEXT_51);
    
        if (dataSDDSPort) { 

    stringBuffer.append(TEXT_52);
    
        } 

    stringBuffer.append(TEXT_53);
    
        if (!dataSDDSPort) { 

    stringBuffer.append(TEXT_54);
    
        } else {

    stringBuffer.append(TEXT_55);
    
        }

    stringBuffer.append(TEXT_56);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_58);
    
        for (Operation op : IdlJavaUtil.getOperations(iface)) {
            ScopedName[] raises = IdlJavaUtil.getRaises(op);
            String throwsString = (raises.length > 0) ? "throws " + raises[0].resolvedName() : "";
            for (int i = 1; i < raises.length; ++i) {
                throwsString += ", " + raises[i].resolvedName();
            }
            if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
                type = dataTransfer.endsWith("[]") ? dataTransfer.substring(0, dataTransfer.length() - 2).toLowerCase() : dataTransfer.toLowerCase();
                if (pushPacketCall) {

    stringBuffer.append(TEXT_59);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(type);
    stringBuffer.append(TEXT_61);
    
                } else {

    stringBuffer.append(TEXT_62);
    stringBuffer.append(dataTransfer);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(type);
    stringBuffer.append(TEXT_64);
    
                }

    stringBuffer.append(TEXT_65);
    
			if ("dataXML".equals(interfaceName)) {

    stringBuffer.append(TEXT_66);
    
			} else {

    stringBuffer.append(TEXT_67);
    
			}

    stringBuffer.append(TEXT_68);
    if (!dataSDDSPort) {
    stringBuffer.append(TEXT_69);
    } else {
    stringBuffer.append(TEXT_70);
    }
    stringBuffer.append(TEXT_71);
    stringBuffer.append(type);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_73);
    
                if (pushPacketCall) {

    stringBuffer.append(TEXT_74);
    if ("dataFile".equals(interfaceName)) {
    stringBuffer.append(TEXT_75);
    }
    stringBuffer.append(TEXT_76);
    
                } else {

    stringBuffer.append(TEXT_77);
    
                }

    stringBuffer.append(TEXT_78);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_79);
    
                continue;
            } else if ("pushSRI".equals(IdlJavaUtil.getOpName(op))) {

    stringBuffer.append(TEXT_80);
    
                if (dataSDDSPort) {

    stringBuffer.append(TEXT_81);
    
                }

    stringBuffer.append(TEXT_82);
    if (dataSDDSPort) {
    stringBuffer.append(TEXT_83);
    }
    stringBuffer.append(TEXT_84);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_85);
    if ("dataSDDS".equals(interfaceName)) {
    stringBuffer.append(TEXT_86);
    }
    stringBuffer.append(TEXT_87);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_88);
    if (!dataSDDSPort) {
    stringBuffer.append(TEXT_89);
    } else {
    stringBuffer.append(TEXT_90);
    }
    stringBuffer.append(TEXT_91);
    
        if (pushPacketCall || pushXMLPacketCall) { 

    stringBuffer.append(TEXT_92);
    
        } 

    stringBuffer.append(TEXT_93);
    
                continue;
            }
        }            

    stringBuffer.append(TEXT_94);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(helperName);
    stringBuffer.append(TEXT_96);
    
        if (pushPacketCall || pushXMLPacketCall) { 

    stringBuffer.append(TEXT_97);
    
        } else { 

    stringBuffer.append(TEXT_98);
    
        } 

    stringBuffer.append(TEXT_99);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_101);
    
        if (dataSDDSPort) { 

    stringBuffer.append(TEXT_102);
    
        } 

    stringBuffer.append(TEXT_103);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_104);
    
        if (dataSDDSPort) { 

    stringBuffer.append(TEXT_105);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_108);
    
        } 

    stringBuffer.append(TEXT_109);
    stringBuffer.append(PortHelper.ifaceToBytes(interfaceName));
    stringBuffer.append(TEXT_110);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_113);
    
        } else {

    stringBuffer.append(TEXT_114);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(helperName);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(helperName);
    stringBuffer.append(TEXT_129);
    
            for (Operation op : IdlJavaUtil.getOperations(iface)) {
                ScopedName[] raises = IdlJavaUtil.getRaises(op);
                String throwsString = (raises.length > 0) ? "throws " + raises[0].resolvedName() : "";
                for (int i = 1; i < raises.length; ++i) {
                    throwsString += ", " + raises[i].resolvedName();
                }
                ParamDecl[] params = IdlJavaUtil.getParams(op);

    stringBuffer.append(TEXT_130);
    stringBuffer.append(IdlJavaUtil.getReturnType(op));
    stringBuffer.append(TEXT_131);
    stringBuffer.append(IdlJavaUtil.getOpName(op));
    stringBuffer.append(TEXT_132);
    
                if (params.length == 0) {
    stringBuffer.append(TEXT_133);
    stringBuffer.append(throwsString);
    stringBuffer.append(TEXT_134);
    
                }
                for (int i = 0; i < params.length; i++) {

    stringBuffer.append(IdlJavaUtil.getParamType(params[i]));
    stringBuffer.append(TEXT_135);
    stringBuffer.append(params[i].simple_declarator.name());
    
                    if (i == (params.length - 1)) {

    stringBuffer.append(TEXT_136);
    stringBuffer.append(throwsString);
    stringBuffer.append(TEXT_137);
    
                    } else {

    stringBuffer.append(TEXT_138);
    
                    }
                }  

                if (!"void".equals(IdlJavaUtil.getReturnType(op))) {

    stringBuffer.append(TEXT_139);
    stringBuffer.append(IdlJavaUtil.getReturnType(op));
    stringBuffer.append(TEXT_140);
    stringBuffer.append(IdlJavaUtil.getInitialValue(op));
    stringBuffer.append(TEXT_141);
    
                }

    stringBuffer.append(TEXT_142);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_143);
    if (!"void".equals(IdlJavaUtil.getReturnType(op))) {
    stringBuffer.append(TEXT_144);
    }
    stringBuffer.append(TEXT_145);
    stringBuffer.append(IdlJavaUtil.getOpName(op));
    stringBuffer.append(TEXT_146);
    
                if (params.length == 0) {

    stringBuffer.append(TEXT_147);
    
                }
        
                for (int i = 0; i < params.length; i++) {

    stringBuffer.append(params[i].simple_declarator.name());
    
                    if (i == (params.length - 1)) {

    stringBuffer.append(TEXT_148);
    
                    } else {

    stringBuffer.append(TEXT_149);
    
                    }
                }

    stringBuffer.append(TEXT_150);
    
                if (!"void".equals(IdlJavaUtil.getReturnType(op))) {

    stringBuffer.append(TEXT_151);
    
                }

    stringBuffer.append(TEXT_152);
        
            }
            
            for (Operation op : IdlJavaUtil.getOperations(iface)) {
                ParamDecl[] params = IdlJavaUtil.getParams(op);
                if ((!"pushSRI".equals(IdlJavaUtil.getOpName(op))) && (!"pushPacket".equals(IdlJavaUtil.getOpName(op)))) {
                    int counter = 0;
                    for (ParamDecl par : params) {
                        String iteratorBase = IdlJavaUtil.getParamType(par);
                        if (iteratorBase.endsWith("[]")) {

    stringBuffer.append(TEXT_153);
    stringBuffer.append(iteratorBase);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(IdlJavaUtil.getOpName(op));
    stringBuffer.append(TEXT_155);
    stringBuffer.append(counter++);
    stringBuffer.append(TEXT_156);
    
                        }
                    }
                }
            }
        } // end if !BULKIO

    stringBuffer.append(TEXT_157);
    
    } // end if template.isGenClassDef()

    stringBuffer.append(TEXT_158);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE