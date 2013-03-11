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
package gov.redhawk.ide.codegen.jet.java.template.component.pull;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.java.JavaCodegenProperty;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.codegen.java.StructSequenceJavaCodegenProperty;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.eclipse.core.runtime.IPath;
import mil.jpeojtrs.sca.prf.AbstractProperty;
import mil.jpeojtrs.sca.prf.PropertyConfigurationType;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;

/**
 * @generated
 */
public class ResourceJavaTemplate
{

  protected static String nl;
  public static synchronized ResourceJavaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceJavaTemplate result = new ResourceJavaTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.ArrayList;" + NL + "import java.util.Arrays;" + NL + "import java.util.List;" + NL + "import java.util.Properties;" + NL + "import org.omg.CORBA.ORB;" + NL + "import org.omg.PortableServer.POA;" + NL + "import org.omg.PortableServer.POAPackage.ServantNotActive;" + NL + "import org.omg.PortableServer.POAPackage.WrongPolicy;" + NL + "import org.omg.CosNaming.NamingContextPackage.CannotProceed;" + NL + "import org.omg.CosNaming.NamingContextPackage.InvalidName;" + NL + "import org.omg.CosNaming.NamingContextPackage.NotFound;" + NL + "import CF.PropertiesHolder;" + NL + "import CF.ResourceHelper;" + NL + "import CF.UnknownProperties;" + NL + "import CF.LifeCyclePackage.InitializeError;" + NL + "import CF.LifeCyclePackage.ReleaseError;" + NL + "import CF.InvalidObjectReference;" + NL + "import CF.PropertySetPackage.InvalidConfiguration;" + NL + "import CF.PropertySetPackage.PartialConfiguration;" + NL + "import CF.ResourcePackage.StartError;" + NL + "import CF.ResourcePackage.StopError;" + NL + "import CF.DataType;" + NL + "import org.omg.CORBA.UserException;" + NL + "import org.omg.CosNaming.NameComponent;" + NL + "import org.apache.log4j.Logger;" + NL + "import org.ossie.component.*;";
  protected final String TEXT_4 = NL + "import org.ossie.properties.*;";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + "import org.ossie.properties.AnyUtils;";
  protected final String TEXT_7 = NL + "import BULKIO.StreamSRI;";
  protected final String TEXT_8 = NL + "import BULKIO.PrecisionUTCTime;" + NL + "import BULKIO.SDDSStreamDefinition;" + NL + "import BULKIO.dataSDDSPackage.AttachError;" + NL + "import BULKIO.dataSDDSPackage.DetachError;" + NL + "import BULKIO.dataSDDSPackage.StreamInputError;";
  protected final String TEXT_9 = NL + "import ";
  protected final String TEXT_10 = ".ports.*;";
  protected final String TEXT_11 = NL + "import org.ossie.events.*;";
  protected final String TEXT_12 = NL + NL + "/**" + NL + " * This is the component code. This file contains all the access points" + NL + " * you need to use to be able to access all input and output ports," + NL + " * respond to incoming data, and perform general component housekeeping" + NL + " *" + NL + " * Source: ";
  protected final String TEXT_13 = NL + " * Generated on: ";
  protected final String TEXT_14 = NL + " * ";
  protected final String TEXT_15 = NL + " * ";
  protected final String TEXT_16 = NL + " * ";
  protected final String TEXT_17 = " " + NL + " " + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_18 = " extends Resource implements Runnable {" + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public final static Logger logger = Logger.getLogger(";
  protected final String TEXT_19 = ".class.getName());";
  protected final String TEXT_20 = NL + "    ";
  protected final String TEXT_21 = " " + NL + "    // Provides/inputs";
  protected final String TEXT_22 = NL + "    public MessageConsumerPort port_";
  protected final String TEXT_23 = ";";
  protected final String TEXT_24 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_25 = "InPort port_";
  protected final String TEXT_26 = ";";
  protected final String TEXT_27 = NL + NL + "    // Uses/outputs";
  protected final String TEXT_28 = NL + "    /**" + NL + "     * @generated" + NL + "     */";
  protected final String TEXT_29 = NL + "    public PropertyEventSupplier port_";
  protected final String TEXT_30 = ";";
  protected final String TEXT_31 = NL + "    public ";
  protected final String TEXT_32 = "OutPort port_";
  protected final String TEXT_33 = ";";
  protected final String TEXT_34 = NL + "    public ";
  protected final String TEXT_35 = "_";
  protected final String TEXT_36 = "OutPort port_";
  protected final String TEXT_37 = ";";
  protected final String TEXT_38 = NL + "    public ";
  protected final String TEXT_39 = "OutPort port_";
  protected final String TEXT_40 = ";";
  protected final String TEXT_41 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public ";
  protected final String TEXT_42 = "() " + NL + "    {" + NL + "        super();";
  protected final String TEXT_43 = NL + "        ";
  protected final String TEXT_44 = NL + "        ";
  protected final String TEXT_45 = "  ";
  protected final String TEXT_46 = "  ";
  protected final String TEXT_47 = NL + "        addProperty(";
  protected final String TEXT_48 = ");";
  protected final String TEXT_49 = NL + NL + "        // Provides/input";
  protected final String TEXT_50 = NL + "        this.port_";
  protected final String TEXT_51 = " = new MessageConsumerPort(\"";
  protected final String TEXT_52 = "\");" + NL + "        this.addPort(\"";
  protected final String TEXT_53 = "\", this.port_";
  protected final String TEXT_54 = ");";
  protected final String TEXT_55 = NL + "        this.port_";
  protected final String TEXT_56 = " = new ";
  protected final String TEXT_57 = "InPort(this, \"";
  protected final String TEXT_58 = "\");" + NL + "        this.addPort(\"";
  protected final String TEXT_59 = "\", this.port_";
  protected final String TEXT_60 = ");";
  protected final String TEXT_61 = NL + NL + "        // Uses/output";
  protected final String TEXT_62 = NL + "        this.port_";
  protected final String TEXT_63 = " = new PropertyEventSupplier(\"";
  protected final String TEXT_64 = "\");";
  protected final String TEXT_65 = NL + "        this.port_";
  protected final String TEXT_66 = " = new ";
  protected final String TEXT_67 = "_";
  protected final String TEXT_68 = "OutPort(\"";
  protected final String TEXT_69 = "\");";
  protected final String TEXT_70 = NL + "        this.port_";
  protected final String TEXT_71 = " = new ";
  protected final String TEXT_72 = "OutPort(\"";
  protected final String TEXT_73 = "\");";
  protected final String TEXT_74 = NL + "        this.addPort(\"";
  protected final String TEXT_75 = "\", this.port_";
  protected final String TEXT_76 = ");";
  protected final String TEXT_77 = NL + "    " + NL + "       //begin-user-code" + NL + "       //end-user-code" + NL + "    }" + NL;
  protected final String TEXT_78 = NL + NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public CF.Resource setup(final String compId, final String compName, final ORB orb, final POA poa) throws ServantNotActive, WrongPolicy" + NL + "    {" + NL + "    \tCF.Resource retval = super.setup(compId, compName, orb, poa);" + NL + "    \t";
  protected final String TEXT_79 = NL + "        this.port_";
  protected final String TEXT_80 = ".registerProperty(this.compId, this.compName, this.";
  protected final String TEXT_81 = ");";
  protected final String TEXT_82 = NL + "        " + NL + "        this.registerPropertyChangePort(this.port_";
  protected final String TEXT_83 = ");" + NL + "        " + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "        " + NL + "    \treturn retval;" + NL + "    }";
  protected final String TEXT_84 = NL + "    ";
  protected final String TEXT_85 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    @Override" + NL + "\tpublic void initialize() throws InitializeError {" + NL + "\t\tsuper.initialize();";
  protected final String TEXT_86 = NL + "\t\ttry {" + NL + "\t\t\tsuper.start();" + NL + "\t\t} catch (StartError e) {" + NL + "\t\t\tthrow new InitializeError(\"Error auto-starting component\", new String[] { e.msg });" + NL + "\t\t}";
  protected final String TEXT_87 = NL + "\t}" + NL;
  protected final String TEXT_88 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public boolean compareSRI(StreamSRI SRI_1, StreamSRI SRI_2){" + NL + "        if (SRI_1.hversion != SRI_2.hversion)" + NL + "            return false;" + NL + "        if (SRI_1.xstart != SRI_2.xstart)" + NL + "            return false;" + NL + "        if (SRI_1.xdelta != SRI_2.xdelta)" + NL + "            return false;" + NL + "        if (SRI_1.xunits != SRI_2.xunits)" + NL + "            return false;" + NL + "        if (SRI_1.subsize != SRI_2.subsize)" + NL + "            return false;" + NL + "        if (SRI_1.ystart != SRI_2.ystart)" + NL + "            return false;" + NL + "        if (SRI_1.ydelta != SRI_2.ydelta)" + NL + "            return false;" + NL + "        if (SRI_1.yunits != SRI_2.yunits)" + NL + "            return false;" + NL + "        if (SRI_1.mode != SRI_2.mode)" + NL + "            return false;" + NL + "        if (SRI_1.streamID != SRI_2.streamID)" + NL + "            return false;" + NL + "        if (SRI_1.keywords.length != SRI_2.keywords.length)" + NL + "            return false;" + NL + "        String action = \"eq\";" + NL + "        for (int i=0; i < SRI_1.keywords.length; i++) {" + NL + "            if (!SRI_1.keywords[i].id.equals(SRI_2.keywords[i].id)) {" + NL + "                return false;" + NL + "            }" + NL + "            if (!SRI_1.keywords[i].value.type().equivalent(SRI_2.keywords[i].value.type())) {" + NL + "                return false;" + NL + "            }" + NL + "            if (AnyUtils.compareAnys(SRI_1.keywords[i].value, SRI_2.keywords[i].value, action)) {" + NL + "                return false;" + NL + "            }" + NL + "        }" + NL + "        return true;" + NL + "    }" + NL;
  protected final String TEXT_89 = NL + "    /**" + NL + "     * @generated" + NL + "     */" + NL + "    public boolean compareTime(final PrecisionUTCTime T1, final PrecisionUTCTime T2){" + NL + "    \tif (T1.tcmode != T2.tcmode)" + NL + "    \t\treturn false;" + NL + "    \tif (T1.tcstatus != T2.tcstatus)" + NL + "    \t\treturn false;" + NL + "    \tif (T1.tfsec != T2.tfsec)" + NL + "    \t\treturn false;" + NL + "    \tif (T1.toff != T2.toff)" + NL + "    \t\treturn false;" + NL + "    \tif (T1.twsec != T2.twsec)" + NL + "    \t\treturn false;" + NL + "    \treturn true;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * This method is used to handle the attach call and return an attachId for the connection." + NL + "     * The value of the attachId should be unique across attach calls within this component instance." + NL + "     * " + NL + "     * @param stream the new stream definition" + NL + "     * @param userId the userId for the stream" + NL + "     * @return an id for this attach call that is unique across all calls within this component instance." + NL + "\t * @throws AttachError" + NL + "\t * @throws StreamInputError" + NL + "     * @generated" + NL + "     */" + NL + "\tpublic String attach(SDDSStreamDefinition stream, String userId) throws AttachError, StreamInputError {" + NL + "\t\tString attachId = \"\";" + NL + "\t\t" + NL + "        //begin-user-code" + NL + "\t\t// TODO Fill in this method to handle attach and set attachId to a value unique to this component " + NL + "        //end-user-code" + NL + "" + NL + "\t\treturn attachId;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "     * This method is used to handle the detach call." + NL + "\t * " + NL + "\t * @param attachId the attachId from a previous call to attach" + NL + "\t * @throws DetachError" + NL + "\t * @throws StreamInputError" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void detach(String attachId) throws DetachError, StreamInputError {" + NL + "        //begin-user-code" + NL + "\t\t// TODO Fill in this method to handle detach " + NL + "        //end-user-code" + NL + "\t}" + NL;
  protected final String TEXT_90 = NL + "    /**" + NL + "     *" + NL + "     * Main processing thread" + NL + "     *" + NL + "     * <!-- begin-user-doc -->" + NL + "     * " + NL + "     * General functionality:" + NL + "     * " + NL + "     *    This function is running as a separate thread from the component's main thread. " + NL + "     *    " + NL + "     *    The IDE uses JMerge during the generation (and re-generation) process.  To keep" + NL + "     *    customizations to this file from being over-written during subsequent generations," + NL + "     *    put your customization in between the following tags:" + NL + "     *      - //begin-user-code" + NL + "     *      - //end-user-code" + NL + "     *    or, alternatively, set the @generated flag located before the code you wish to " + NL + "     *    modify, in the following way:" + NL + "     *      - \"@generated NOT\"" + NL + "     * " + NL + "     * StreamSRI:" + NL + "     *    To create a StreamSRI object, use the following code:" + NL + "     *        this.stream_id = \"stream\";" + NL + "     * \t\t  StreamSRI sri = new StreamSRI();" + NL + "     * \t\t  sri.mode = 0;" + NL + "     * \t\t  sri.xdelta = 0.0;" + NL + "     * \t\t  sri.ydelta = 1.0;" + NL + "     * \t\t  sri.subsize = 0;" + NL + "     * \t\t  sri.xunits = 1; // TIME_S" + NL + "     * \t\t  sri.streamID = (this.stream_id.getValue() != null) ? this.stream_id.getValue() : \"\";" + NL + "     * " + NL + "     * PrecisionUTCTime:" + NL + "     *    To create a PrecisionUTCTime object, use the following code:" + NL + "     * \t\t  long tmp_time = System.currentTimeMillis();" + NL + "     * \t\t  double wsec = tmp_time / 1000;" + NL + "     * \t\t  double fsec = tmp_time % 1000;" + NL + "     * \t\t  PrecisionUTCTime tstamp = new PrecisionUTCTime(BULKIO.TCM_CPU.value, (short)1, (short)0, wsec, fsec);" + NL + "     * " + NL + "     * Ports:" + NL + "     * " + NL + "     *    Each port instance is accessed through members of the following form: this.port_<PORT NAME>" + NL + "     * " + NL + "     *    Data is obtained in the run function through the getPacket call (BULKIO only) on a" + NL + "     *    provides port member instance. The getPacket function call is non-blocking; it takes" + NL + "     *    one argument which is the time to wait on new data. If you pass 0, it will return" + NL + "     *    immediately if no data available (won't wait)." + NL + "     *    " + NL + "     *    To send data, call the appropriate function in the port directly. In the case of BULKIO," + NL + "     *    convenience functions have been added in the port classes that aid in output." + NL + "     *    " + NL + "     *    Interactions with non-BULKIO ports are left up to the component developer's discretion." + NL + "     *    " + NL + "     * Properties:" + NL + "     * " + NL + "     *    Properties are accessed through members of the same name with helper functions. If the " + NL + "     *    property name is baudRate, then reading the value is achieved by: this.baudRate.getValue();" + NL + "     *    and writing a new value is achieved by: this.baudRate.setValue(new_value);" + NL + "     *    " + NL + "     * Example:" + NL + "     * " + NL + "     *    This example assumes that the component has two ports:" + NL + "     *        - A provides (input) port of type BULKIO::dataShort called dataShort_in" + NL + "     *        - A uses (output) port of type BULKIO::dataFloat called dataFloat_out" + NL + "     *    The mapping between the port and the class is found the class of the same name." + NL + "     *    This example also makes use of the following Properties:" + NL + "     *        - A float value called amplitude with a default value of 2.0" + NL + "     *        - A boolean called increaseAmplitude with a default value of true" + NL + "     *    " + NL + "     *    BULKIO_dataShortInPort.Packet<short[]> data = this.port_dataShort_in.getPacket(125);" + NL + "     *" + NL + "     *    if (data != null) {" + NL + "     *        float[] outData = new float[data.getData().length];" + NL + "     *        for (int i = 0; i < data.getData().length; i++) {" + NL + "     *            if (this.increaseAmplitude.getValue()) {" + NL + "     *                outData[i] = (float)data.getData()[i] * this.amplitude.getValue();" + NL + "     *            } else {" + NL + "     *                outData[i] = (float)data.getData()[i];" + NL + "     *            }" + NL + "     *        }" + NL + "     *" + NL + "     *        // NOTE: You must make at least one valid pushSRI call" + NL + "     *        if (data.sriChanged()) {" + NL + "     *            this.port_dataFloat_out.pushSRI(data.getSRI());" + NL + "     *        }" + NL + "     *        this.port_dataFloat_out.pushPacket(outData, data.getTime(), data.getEndOfStream(), data.getStreamID());" + NL + "     *    }" + NL + "     *      " + NL + "     * <!-- end-user-doc -->" + NL + "     * " + NL + "     * @generated" + NL + "     */" + NL + "    public void run() " + NL + "    {" + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "        " + NL + "        while(this.started())" + NL + "        {" + NL + "            //begin-user-code" + NL + "            // Process data here" + NL + "            try {" + NL + "                logger.debug(\"run() example log message\");" + NL + "                Thread.sleep(1000);" + NL + "            } catch (InterruptedException e) {" + NL + "                break;" + NL + "            }" + NL + "            " + NL + "            //end-user-code" + NL + "        }" + NL + "        " + NL + "        //begin-user-code" + NL + "        //end-user-code" + NL + "    }" + NL + "        " + NL + "    /**" + NL + "     * The main function of your component.  If no args are provided, then the" + NL + "     * CORBA object is not bound to an SCA Domain or NamingService and can" + NL + "     * be run as a standard Java application." + NL + "     * " + NL + "     * @param args" + NL + "     * @generated" + NL + "     */" + NL + "    public static void main(String[] args) " + NL + "    {" + NL + "        final Properties orbProps = new Properties();" + NL + "" + NL + "        //begin-user-code" + NL + "        // TODO You may add extra startup code here, for example:" + NL + "        // orbProps.put(\"com.sun.CORBA.giop.ORBFragmentSize\", Integer.toString(fragSize));" + NL + "        //end-user-code" + NL + "" + NL + "        try {" + NL + "            Resource.start_component(";
  protected final String TEXT_91 = ".class, args, orbProps);" + NL + "        } catch (InvalidObjectReference e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (NotFound e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (CannotProceed e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (InvalidName e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (ServantNotActive e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (WrongPolicy e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (InstantiationException e) {" + NL + "            e.printStackTrace();" + NL + "        } catch (IllegalAccessException e) {" + NL + "            e.printStackTrace();" + NL + "        }" + NL + "" + NL + "        //begin-user-code" + NL + "        // TODO You may add extra shutdown code here" + NL + "        //end-user-code" + NL + "    }" + NL + "}";
  protected final String TEXT_92 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    JavaTemplateParameter template = (JavaTemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    SoftPkg softPkg = template.getSoftPkg();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    String pkg = template.getPackage();
    Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
    EList<Provides> provides = ports.getProvides();
    EList<Uses> uses = ports.getUses();
    Map<String, AbstractProperty> properties = JavaGeneratorUtils.createPropertiesSet(softPkg, "prop");
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    boolean proBulkio = false;
    boolean autoStart = false;
    boolean hasSDDSProvides = false;
    boolean hasProvidesMessagePort = false;
    Date date = new Date(System.currentTimeMillis());
    
    for (Property tempProp : implSettings.getProperties()) {              
        if ("auto_start".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                autoStart = true;
                continue;
            }
        }
    }
    for (Provides pro : provides) {
        final String repId = pro.getRepID(); 
        if (repId.startsWith("IDL:BULKIO")) {
            proBulkio = true;
            if (repId.contains("dataSDDS")) {
                hasSDDSProvides = true;
                break;
            }
        }
    }

    stringBuffer.append(TEXT_2);
    stringBuffer.append(pkg);
    stringBuffer.append(TEXT_3);
    if (properties.size() > 0){
    stringBuffer.append(TEXT_4);
    }
    stringBuffer.append(TEXT_5);
    if ((provides.size() > 0) || (uses.size() > 0)){
      if (proBulkio){
        if (properties.size() == 0){
    stringBuffer.append(TEXT_6);
        }
    stringBuffer.append(TEXT_7);
        if (hasSDDSProvides){
    stringBuffer.append(TEXT_8);
        }
      }
boolean foundGeneratedPort = false;
boolean foundPortFromEvents = false;
for (Provides pro : provides) {
	if (!pro.getRepID().equals("IDL:ExtendedEvent/MessageEvent:1.0")) {
		foundGeneratedPort = true;
	} else {
		foundPortFromEvents = true;
	}
}
for (Uses use : uses) {
	if (!(use.getRepID().equals("IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0") && use.getUsesName().equals("propEvent"))) {
		foundGeneratedPort = true;
	} else {
		foundPortFromEvents = true;
	}
}
if (foundGeneratedPort) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(pkg);
    stringBuffer.append(TEXT_10);
    
}
if (foundPortFromEvents) {

    stringBuffer.append(TEXT_11);
    
}
}

    stringBuffer.append(TEXT_12);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_13);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_14);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_19);
    List<JavaCodegenProperty> javaProperties = JavaGeneratorUtils.createJavaProps(1, properties);
    for (JavaCodegenProperty property : javaProperties) {
    stringBuffer.append(TEXT_20);
    stringBuffer.append(property.toString());
    }
    stringBuffer.append(TEXT_21);
    for (Provides pro : provides) {
      Interface iface = IdlUtil.getInstance().getInterface(search_paths, pro.getRepID().split(":")[1], true);
      String nameSpace = iface.getNameSpace();
      String interfaceName = iface.getName();
	  if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {
	  	 hasProvidesMessagePort = true;

    stringBuffer.append(TEXT_22);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_23);
    
	  } else {

    stringBuffer.append(TEXT_24);
    stringBuffer.append(JavaGeneratorUtils.repIdToClassPrefix(pro.getRepID()));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_26);
    
	  }
}

    stringBuffer.append(TEXT_27);
    

for (Uses use : uses) {
    stringBuffer.append(TEXT_28);
    
      Interface iface = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
      String nameSpace = iface.getNameSpace();
      String interfaceName = iface.getName();
      String entry = use.getRepID();
      // Loop over provides ports to see if there is a matching interface and port name for the current uses port
      // If so, ignore the uses port
      // This is to support bi-directional ports
      boolean foundMatchingProvides = false;
      for (Provides pro : provides) {
          String entryProvides = pro.getRepID();
          if (entry.equals(entryProvides) && use.getUsesName().equals(pro.getProvidesName())) {
              foundMatchingProvides = true;
              break;
          }
      }
      if (foundMatchingProvides == false){ 
          if (use.getRepID().equals("IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0")) {
              if (use.getUsesName().equals("propEvent")) { 
    stringBuffer.append(TEXT_29);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_30);
                } else {

    stringBuffer.append(TEXT_31);
    stringBuffer.append(JavaGeneratorUtils.repIdToClassPrefix(use.getRepID()));
    stringBuffer.append(TEXT_32);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_33);
                }
	      } else if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_37);
    
	      } else {

    stringBuffer.append(TEXT_38);
    stringBuffer.append(JavaGeneratorUtils.repIdToClassPrefix(use.getRepID()));
    stringBuffer.append(TEXT_39);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_40);
    
          }
      }

    }
    stringBuffer.append(TEXT_41);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_42);
    for (JavaCodegenProperty property : javaProperties) {
    if (property instanceof StructSequenceJavaCodegenProperty) {
    stringBuffer.append(TEXT_43);
    stringBuffer.append(((StructSequenceJavaCodegenProperty)property).getStructVals());
    stringBuffer.append(TEXT_44);
    stringBuffer.append(((StructSequenceJavaCodegenProperty)property).getNewStructSequence());
    }
    stringBuffer.append(TEXT_45);
    }
    stringBuffer.append(TEXT_46);
    for (String prop : properties.keySet()) {
    stringBuffer.append(TEXT_47);
    stringBuffer.append(prop);
    stringBuffer.append(TEXT_48);
    }
    stringBuffer.append(TEXT_49);
    for (Provides pro : provides)
{
    Interface iface = IdlUtil.getInstance().getInterface(search_paths, pro.getRepID().split(":")[1], true);
    String nameSpace = iface.getNameSpace();
    String interfaceName = iface.getName();

    if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_51);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_52);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_53);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_54);
    
    } else {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_56);
    stringBuffer.append(JavaGeneratorUtils.repIdToClassPrefix(pro.getRepID()));
    stringBuffer.append(TEXT_57);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_58);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_59);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_60);
    
	}
}

    stringBuffer.append(TEXT_61);
    for (Uses use : uses) {
    Interface iface = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
    String nameSpace = iface.getNameSpace();
    String interfaceName = iface.getName();
    String entry = use.getRepID();
    // Loop over provides ports to see if there is a matching interface and port name for the current uses port
    // If so, ignore the uses port
    // This is to support bi-directional ports
    boolean foundMatchingProvides = false;
    for (Provides pro : provides) {
        String entryProvides = pro.getRepID();
        if (entry.equals(entryProvides) && use.getUsesName().equals(pro.getProvidesName())) {
            foundMatchingProvides = true;
            break;
        }
    }
    if (foundMatchingProvides == false){
        if ("propEvent".equals(use.getUsesName()) && "IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0".equals(use.getRepID())) {

    stringBuffer.append(TEXT_62);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_63);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_64);
    
        } else if (nameSpace.equals("ExtendedEvent") && interfaceName.equals("MessageEvent")) {

    stringBuffer.append(TEXT_65);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_66);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_69);
    
        } else {

    stringBuffer.append(TEXT_70);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_71);
    stringBuffer.append(JavaGeneratorUtils.repIdToClassPrefix(use.getRepID()));
    stringBuffer.append(TEXT_72);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_73);
    
        }

    stringBuffer.append(TEXT_74);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_75);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_76);
      } // if (foundMatchingProvides == false)
} // for (Uses use: uses) 
    stringBuffer.append(TEXT_77);
    for (Uses use : uses) {
    Interface iface = IdlUtil.getInstance().getInterface(search_paths, use.getRepID().split(":")[1], true);
    String nameSpace = iface.getNameSpace();
    String interfaceName = iface.getName();
    String entry = use.getRepID();
    // Loop over provides ports to see if there is a matching interface and port name for the current uses port
    // If so, ignore the uses port
    // This is to support bi-directional ports
    boolean foundMatchingProvides = false;
    for (Provides pro : provides) {
        String entryProvides = pro.getRepID();
        if (entry.equals(entryProvides) && use.getUsesName().equals(pro.getProvidesName())) {
            foundMatchingProvides = true;
            break;
        }
    }
    if (foundMatchingProvides == false){
        if ("propEvent".equals(use.getUsesName()) && "IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0".equals(use.getRepID())) {

    stringBuffer.append(TEXT_78);
    
			for (String prop : properties.keySet()) {
				if (properties.get(prop).isKind(PropertyConfigurationType.EVENT)) {

    stringBuffer.append(TEXT_79);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_80);
    stringBuffer.append(prop);
    stringBuffer.append(TEXT_81);
    
				}
			}

    stringBuffer.append(TEXT_82);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_83);
    
        	break;
        }

      } // if (foundMatchingProvides == false)
} // for (Uses use: uses) 
    stringBuffer.append(TEXT_84);
    if (hasProvidesMessagePort || autoStart) {
    stringBuffer.append(TEXT_85);
       if (autoStart){
    stringBuffer.append(TEXT_86);
       }
    stringBuffer.append(TEXT_87);
    }
    if (proBulkio) {
    stringBuffer.append(TEXT_88);
      if (hasSDDSProvides){
    stringBuffer.append(TEXT_89);
      }
    }
    stringBuffer.append(TEXT_90);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(TEXT_92);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE