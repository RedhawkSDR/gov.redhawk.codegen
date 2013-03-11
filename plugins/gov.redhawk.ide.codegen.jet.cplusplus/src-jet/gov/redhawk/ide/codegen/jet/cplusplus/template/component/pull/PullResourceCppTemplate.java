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

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.Date;
import java.util.List;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.SupportsInterface;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.emf.common.util.EList;

	/**
    * @generated
    */

public class PullResourceCppTemplate
{

  protected static String nl;
  public static synchronized PullResourceCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullResourceCppTemplate result = new PullResourceCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "/**************************************************************************" + NL + "" + NL + "    This is the component code. This file contains the child class where" + NL + "    custom functionality can be added to the component. Custom" + NL + "    functionality to the base class can be extended here. Access to" + NL + "    the ports can also be done from this class" + NL + "" + NL + " \tSource: ";
  protected final String TEXT_2 = NL + " \tGenerated on: ";
  protected final String TEXT_3 = NL + " \t";
  protected final String TEXT_4 = NL + " \t";
  protected final String TEXT_5 = NL + " \t";
  protected final String TEXT_6 = NL + NL + "**************************************************************************/" + NL + "" + NL + "#include \"";
  protected final String TEXT_7 = ".h\"" + NL + "" + NL + "PREPARE_LOGGING(";
  protected final String TEXT_8 = "_i)" + NL;
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "_i::";
  protected final String TEXT_11 = "_i(const char *uuid, const char *label) : ";
  protected final String TEXT_12 = NL + "    ";
  protected final String TEXT_13 = "_base(uuid, label)" + NL + "{" + NL + "}";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "_i::";
  protected final String TEXT_16 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl) :";
  protected final String TEXT_17 = NL + "    ";
  protected final String TEXT_18 = "_base(devMgr_ior, id, lbl, sftwrPrfl)" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "_i::";
  protected final String TEXT_21 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev) :";
  protected final String TEXT_22 = NL + "    ";
  protected final String TEXT_23 = "_base(devMgr_ior, id, lbl, sftwrPrfl, compDev)" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = "_i::";
  protected final String TEXT_26 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities) :";
  protected final String TEXT_27 = NL + "    ";
  protected final String TEXT_28 = "_base(devMgr_ior, id, lbl, sftwrPrfl, capacities)" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_29 = NL;
  protected final String TEXT_30 = "_i::";
  protected final String TEXT_31 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev) :";
  protected final String TEXT_32 = NL + "    ";
  protected final String TEXT_33 = "_base(devMgr_ior, id, lbl, sftwrPrfl, capacities, compDev)" + NL + "{" + NL + "}";
  protected final String TEXT_34 = NL;
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = "_i::~";
  protected final String TEXT_37 = "_i()" + NL + "{" + NL + "}" + NL;
  protected final String TEXT_38 = NL + "std::string ";
  protected final String TEXT_39 = "_i::attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid)" + NL + "{" + NL + "    // TODO - Handle the attach call and return the attachment id" + NL + "    return \"\";" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_40 = "_i::detach(const char* userid)" + NL + "{" + NL + "    // TODO - Handle the detach call" + NL + "}" + NL;
  protected final String TEXT_41 = NL + NL + "/***********************************************************************************************" + NL + "" + NL + "    Basic functionality:" + NL + "" + NL + "        The service function is called by the serviceThread object (of type ProcessThread)." + NL + "        This call happens immediately after the previous call if the return value for" + NL + "        the previous call was NORMAL." + NL + "        If the return value for the previous call was NOOP, then the serviceThread waits" + NL + "        an amount of time defined in the serviceThread's constructor." + NL + "        " + NL + "    SRI:" + NL + "        To create a StreamSRI object, use the following code:" + NL + "        \tstream_id = \"\";" + NL + "\t    \tsri = BULKIO::StreamSRI();" + NL + "\t    \tsri.hversion = 1;" + NL + "\t    \tsri.xstart = 0.0;" + NL + "\t    \tsri.xdelta = 0.0;" + NL + "\t    \tsri.xunits = BULKIO::UNITS_TIME;" + NL + "\t    \tsri.subsize = 0;" + NL + "\t    \tsri.ystart = 0.0;" + NL + "\t    \tsri.ydelta = 0.0;" + NL + "\t    \tsri.yunits = BULKIO::UNITS_NONE;" + NL + "\t    \tsri.mode = 0;" + NL + "\t    \tsri.streamID = this->stream_id.c_str();" + NL + "" + NL + "\tTime:" + NL + "\t    To create a PrecisionUTCTime object, use the following code:" + NL + "\t        struct timeval tmp_time;" + NL + "\t        struct timezone tmp_tz;" + NL + "\t        gettimeofday(&tmp_time, &tmp_tz);" + NL + "\t        double wsec = tmp_time.tv_sec;" + NL + "\t        double fsec = tmp_time.tv_usec / 1e6;;" + NL + "\t        BULKIO::PrecisionUTCTime tstamp = BULKIO::PrecisionUTCTime();" + NL + "\t        tstamp.tcmode = BULKIO::TCM_CPU;" + NL + "\t        tstamp.tcstatus = (short)1;" + NL + "\t        tstamp.toff = 0.0;" + NL + "\t        tstamp.twsec = wsec;" + NL + "\t        tstamp.tfsec = fsec;" + NL + "        " + NL + "    Ports:" + NL + "" + NL + "        Data is passed to the serviceFunction through the getPacket call (BULKIO only)." + NL + "        The dataTransfer class is a port-specific class, so each port implementing the" + NL + "        BULKIO interface will have its own type-specific dataTransfer." + NL + "" + NL + "        The argument to the getPacket function is a floating point number that specifies" + NL + "        the time to wait in seconds. A zero value is non-blocking. A negative value" + NL + "        is blocking." + NL + "" + NL + "        Each received dataTransfer is owned by serviceFunction and *MUST* be" + NL + "        explicitly deallocated." + NL + "" + NL + "        To send data using a BULKIO interface, a convenience interface has been added " + NL + "        that takes a std::vector as the data input" + NL + "" + NL + "        NOTE: If you have a BULKIO dataSDDS port, you must manually call " + NL + "              \"port->updateStats()\" to update the port statistics when appropriate." + NL + "" + NL + "        Example:" + NL + "            // this example assumes that the component has two ports:" + NL + "            //  A provides (input) port of type BULKIO::dataShort called short_in" + NL + "            //  A uses (output) port of type BULKIO::dataFloat called float_out" + NL + "            // The mapping between the port and the class is found" + NL + "            // in the component base class header file" + NL + "" + NL + "            BULKIO_dataShort_In_i::dataTransfer *tmp = short_in->getPacket(-1);" + NL + "            if (not tmp) { // No data is available" + NL + "                return NOOP;" + NL + "            }" + NL + "" + NL + "            std::vector<float> outputData;" + NL + "            outputData.resize(tmp->dataBuffer.size());" + NL + "            for (unsigned int i=0; i<tmp->dataBuffer.size(); i++) {" + NL + "                outputData[i] = (float)tmp->dataBuffer[i];" + NL + "            }" + NL + "" + NL + "            // NOTE: You must make at least one valid pushSRI call" + NL + "            if (tmp->sriChanged) {" + NL + "                float_out->pushSRI(tmp->SRI);" + NL + "            }" + NL + "            float_out->pushPacket(outputData, tmp->T, tmp->EOS, tmp->streamID);" + NL + "" + NL + "            delete tmp; // IMPORTANT: MUST RELEASE THE RECEIVED DATA BLOCK" + NL + "            return NORMAL;" + NL + "" + NL + "        Interactions with non-BULKIO ports are left up to the component developer's discretion" + NL + "" + NL + "    Properties:" + NL + "        " + NL + "        Properties are accessed directly as member variables. For example, if the" + NL + "        property name is \"baudRate\", it may be accessed within member functions as" + NL + "        \"baudRate\". Unnamed properties are given a generated name of the form" + NL + "        \"prop_n\", where \"n\" is the ordinal number of the property in the PRF file." + NL + "        Property types are mapped to the nearest C++ type, (e.g. \"string\" becomes" + NL + "        \"std::string\"). All generated properties are declared in the base class" + NL + "        (";
  protected final String TEXT_42 = "_base)." + NL + "    " + NL + "        Simple sequence properties are mapped to \"std::vector\" of the simple type." + NL + "        Struct properties, if used, are mapped to C++ structs defined in the" + NL + "        generated file \"struct_props.h\". Field names are taken from the name in" + NL + "        the properties file; if no name is given, a generated name of the form" + NL + "        \"field_n\" is used, where \"n\" is the ordinal number of the field." + NL + "        " + NL + "        Example:" + NL + "            // This example makes use of the following Properties:" + NL + "            //  - A float value called scaleValue" + NL + "            //  - A boolean called scaleInput" + NL + "              " + NL + "            if (scaleInput) {" + NL + "                dataOut[i] = dataIn[i] * scaleValue;" + NL + "            } else {" + NL + "                dataOut[i] = dataIn[i];" + NL + "            }" + NL + "            " + NL + "        A callback method can be associated with a property so that the method is" + NL + "        called each time the property value changes.  This is done by calling " + NL + "        setPropertyChangeListener(<property name>, this, &";
  protected final String TEXT_43 = "::<callback method>)" + NL + "        in the constructor." + NL + "            " + NL + "        Example:" + NL + "            // This example makes use of the following Properties:" + NL + "            //  - A float value called scaleValue" + NL + "            " + NL + "        //Add to ";
  protected final String TEXT_44 = ".cpp";
  protected final String TEXT_45 = NL + "        ";
  protected final String TEXT_46 = "_i::";
  protected final String TEXT_47 = "_i(const char *uuid, const char *label) :";
  protected final String TEXT_48 = NL + "            ";
  protected final String TEXT_49 = "_base(uuid, label)" + NL + "        {" + NL + "            setPropertyChangeListener(\"scaleValue\", this, &";
  protected final String TEXT_50 = "_i::scaleChanged);" + NL + "        }" + NL + "" + NL + "        void ";
  protected final String TEXT_51 = "_i::scaleChanged(const std::string& id){" + NL + "            std::cout << \"scaleChanged scaleValue \" << scaleValue << std::endl;" + NL + "        }" + NL + "            " + NL + "        //Add to ";
  protected final String TEXT_52 = ".h" + NL + "        void scaleChanged(const std::string&);" + NL + "        " + NL + "        " + NL + "************************************************************************************************/" + NL + "int ";
  protected final String TEXT_53 = "_i::serviceFunction()" + NL + "{" + NL + "    LOG_DEBUG(";
  protected final String TEXT_54 = "_i, \"serviceFunction() example log message\");" + NL + "    " + NL + "    return NOOP;" + NL + "}";
  protected final String TEXT_55 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    boolean hasSddsPort = false;
    Date date = new Date(System.currentTimeMillis());
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    
    for (Provides entry : provides) {
        if (entry.getRepID().contains("BULKIO/dataSDDS")) {
            hasSddsPort = true;
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_2);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_3);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
     if (!templ.isDevice()) {
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
     } else {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    }
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_37);
    if (hasSddsPort) {
    stringBuffer.append(TEXT_38);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_40);
    }
    stringBuffer.append(TEXT_41);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(TEXT_55);
    return stringBuffer.toString();
  }
} 