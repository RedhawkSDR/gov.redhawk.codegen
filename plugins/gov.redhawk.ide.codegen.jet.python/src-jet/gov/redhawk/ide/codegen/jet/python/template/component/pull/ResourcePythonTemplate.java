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
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import java.util.HashSet;
import java.util.Date;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.core.runtime.IProduct;
import gov.redhawk.model.sca.util.ModelUtil;

	/**
    * @generated
    */

public class ResourcePythonTemplate
{

  protected static String nl;
  public static synchronized ResourcePythonTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourcePythonTemplate result = new ResourcePythonTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python ";
  protected final String TEXT_2 = "xmpy";
  protected final String TEXT_3 = NL + "#" + NL + "# AUTO-GENERATED";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "#" + NL + "# Source: ";
  protected final String TEXT_6 = NL + "# Generated on: ";
  protected final String TEXT_7 = NL + "# ";
  protected final String TEXT_8 = NL + "# ";
  protected final String TEXT_9 = NL + "# ";
  protected final String TEXT_10 = NL + "from ossie.resource import Resource, start_component";
  protected final String TEXT_11 = NL + "from ossie.device import start_device";
  protected final String TEXT_12 = NL + "import logging";
  protected final String TEXT_13 = NL + "from ossie.utils import uuid";
  protected final String TEXT_14 = NL + NL + "from ";
  protected final String TEXT_15 = "_base import * " + NL + "" + NL + "class ";
  protected final String TEXT_16 = "_i(";
  protected final String TEXT_17 = "_base):";
  protected final String TEXT_18 = NL + "    \"\"\"";
  protected final String TEXT_19 = "\"\"\"";
  protected final String TEXT_20 = NL + "    \"\"\"<DESCRIPTION GOES HERE>\"\"\"";
  protected final String TEXT_21 = NL + "    def initialize(self):" + NL + "        \"\"\"" + NL + "        This is called by the framework immediately after your component registers with the NameService." + NL + "        " + NL + "        In general, you should add customization here and not in the __init__ constructor.  If you have " + NL + "        a custom port implementation you can override the specific implementation here with a statement" + NL + "        similar to the following:" + NL + "          self.some_port = MyPortImplementation()" + NL + "        \"\"\"";
  protected final String TEXT_22 = NL + "        ";
  protected final String TEXT_23 = "_base.initialize(self)" + NL + "        # TODO add customization here." + NL + "        ";
  protected final String TEXT_24 = NL + "    def updateUsageState(self):" + NL + "        \"\"\"" + NL + "        This is called automatically after allocateCapacity or deallocateCapacity are called." + NL + "        Your implementation should determine the current state of the device:" + NL + "           self._usageState = CF.Device.IDLE   # not in use" + NL + "           self._usageState = CF.Device.ACTIVE # in use, with capacity remaining for allocation" + NL + "           self._usageState = CF.Device.BUSY   # in use, with no capacity remaining for allocation" + NL + "        \"\"\"" + NL + "        return NOOP" + NL;
  protected final String TEXT_25 = NL + NL + "    def process(self):" + NL + "        \"\"\"" + NL + "        Basic functionality:" + NL + "        " + NL + "            The process method should process a single \"chunk\" of data and then return. This method" + NL + "            will be called from the processing thread again, and again, and again until it returns" + NL + "            FINISH or stop() is called on the component.  If no work is performed, then return NOOP." + NL + "            " + NL + "        StreamSRI:" + NL + "            To create a StreamSRI object, use the following code (this generates a normalized SRI that does not flush the queue when full):" + NL + "                self.sri = BULKIO.StreamSRI(1, 0.0, 0.0, BULKIO.UNITS_TIME, 0, 0.0, 0.0, BULKIO.UNITS_NONE, 0, self.stream_id, True, [])" + NL + "" + NL + "        PrecisionUTCTime:" + NL + "            To create a PrecisionUTCTime object, use the following code:" + NL + "                tmp_time = time.time()" + NL + "                wsec = math.modf(tmp_time)[1]" + NL + "                fsec = math.modf(tmp_time)[0]" + NL + "                tstamp = BULKIO.PrecisionUTCTime(BULKIO.TCM_CPU, BULKIO.TCS_VALID, 0, wsec, fsec)" + NL + "  " + NL + "        Ports:" + NL + "" + NL + "            Each port instance is accessed through members of the following form: self.port_<PORT NAME>" + NL + "            " + NL + "            Data is obtained in the process function through the getPacket call (BULKIO only) on a" + NL + "            provides port member instance. The getPacket function call is non-blocking - if no data" + NL + "            is available, it will return immediately with all values == None." + NL + "            " + NL + "            To send data, call the appropriate function in the port directly. In the case of BULKIO," + NL + "            convenience functions have been added in the port classes that aid in output." + NL + "            " + NL + "            Interactions with non-BULKIO ports are left up to the component developer's discretion." + NL + "            " + NL + "        Properties:" + NL + "        " + NL + "            Properties are accessed directly as member variables. If the property name is baudRate," + NL + "            then accessing it (for reading or writing) is achieved in the following way: self.baudRate." + NL + "            " + NL + "        Example:" + NL + "        " + NL + "            # This example assumes that the component has two ports:" + NL + "            #   - A provides (input) port of type BULKIO.dataShort called dataShort_in" + NL + "            #   - A uses (output) port of type BULKIO.dataFloat called dataFloat_out" + NL + "            # The mapping between the port and the class if found in the component" + NL + "            # base class." + NL + "            # This example also makes use of the following Properties:" + NL + "            #   - A float value called amplitude" + NL + "            #   - A boolean called increaseAmplitude" + NL + "            " + NL + "            data, T, EOS, streamID, sri, sriChanged, inputQueueFlushed = self.port_dataShort_in.getPacket()" + NL + "            " + NL + "            if data == None:" + NL + "                return NOOP" + NL + "                " + NL + "            outData = range(len(data))" + NL + "            for i in range(len(data)):" + NL + "                if self.increaseAmplitude:" + NL + "                    outData[i] = float(data[i]) * self.amplitude" + NL + "                else:" + NL + "                    outData[i] = float(data[i])" + NL + "                " + NL + "            # NOTE: You must make at least one valid pushSRI call" + NL + "            if sriChanged:" + NL + "                self.port_dataFloat_out.pushSRI(sri);" + NL + "" + NL + "            self.port_dataFloat_out.pushPacket(outData, T, EOS, streamID)" + NL + "            return NORMAL" + NL + "            " + NL + "        \"\"\"" + NL + "" + NL + "        # TODO fill in your code here" + NL + "        self._log.debug(\"process() example log message\")" + NL + "        return NOOP" + NL + "        " + NL + "  " + NL + "if __name__ == '__main__':" + NL + "    logging.getLogger().setLevel(logging.WARN)" + NL + "    logging.debug(\"Starting ";
  protected final String TEXT_26 = "\")";
  protected final String TEXT_27 = NL + "    start_component(";
  protected final String TEXT_28 = "_i)";
  protected final String TEXT_29 = NL + "    start_device(";
  protected final String TEXT_30 = "_i)";
  protected final String TEXT_31 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    Date date = new Date(System.currentTimeMillis());
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    boolean isResource = true;
    boolean hasSddsPort = false;
    String OSSIENAME = "Component";
    HashSet<String> nsSet = new HashSet<String>();
    
    if (softPkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase())) {
        isResource = false;
        OSSIENAME = "Device";
    }
    
    if (uses.size() > 0) {
        for (Uses tempUse : uses) {
		    final String[] ints = tempUse.getRepID().split(":")[1].split("/");
            nsSet.add(ints[ints.length - 2]);
        }
    }
    if (provides.size() > 0) {
        for (Provides tempProvide : provides) {
            String rep = tempProvide.getRepID();
            if (rep.contains("BULKIO/dataSDDS")) {
                hasSddsPort = true;
            }
		    final String[] ints = rep.split(":")[1].split("/");
            nsSet.add(ints[ints.length - 2]);
        }
    } 

    stringBuffer.append(TEXT_1);
        if (implSettings.getGeneratorId().contains("XMPY")) {
    stringBuffer.append(TEXT_2);
     }
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_6);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_7);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(output[2]);
    
	}

     if (isResource) { 
    stringBuffer.append(TEXT_10);
     } else { 
    stringBuffer.append(TEXT_11);
     } 
    stringBuffer.append(TEXT_12);
     if (hasSddsPort) { 
    stringBuffer.append(TEXT_13);
     } 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( PREFIX );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_17);
     
    if (softPkg.getDescription() != null) { 

    stringBuffer.append(TEXT_18);
    stringBuffer.append(softPkg.getDescription());
    stringBuffer.append(TEXT_19);
     
    } else {

    stringBuffer.append(TEXT_20);
     
    }

    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_23);
     if (!isResource) { 
    stringBuffer.append(TEXT_24);
     } 
    stringBuffer.append(TEXT_25);
    stringBuffer.append(OSSIENAME);
    stringBuffer.append(TEXT_26);
     if (isResource) { 
    stringBuffer.append(TEXT_27);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_28);
     } else { 
    stringBuffer.append(TEXT_29);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_30);
     } 
    stringBuffer.append(TEXT_31);
    return stringBuffer.toString();
  }
} 