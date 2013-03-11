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
package gov.redhawk.ide.codegen.jet.python.template.component.minimal;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.Date;

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
  protected final String TEXT_1 = "#!/usr/bin/env python " + NL + "#" + NL + "# AUTO-GENERATED";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = "#" + NL + "# Source: ";
  protected final String TEXT_4 = NL + "# Generated on: ";
  protected final String TEXT_5 = NL + "# ";
  protected final String TEXT_6 = NL + "# ";
  protected final String TEXT_7 = NL + "# ";
  protected final String TEXT_8 = NL + " " + NL + "from ossie.cf import CF, CF__POA #@UnusedImport" + NL + "from ossie.resource import start_component" + NL + "from ";
  protected final String TEXT_9 = "_base import ";
  protected final String TEXT_10 = "_base" + NL + "" + NL + "class ";
  protected final String TEXT_11 = "(";
  protected final String TEXT_12 = "_base):" + NL + "" + NL + "    def initialize(self):" + NL + "        \"\"\"" + NL + "        The purpose of the initialize operation is to provide a mechanism to" + NL + "        set a component to a known initial state. For example, data structures" + NL + "        may be set to initial values, memory may be allocated, hardware devices" + NL + "        may be configured to some state, etc.  " + NL + "" + NL + "        The initialize operation shall raise an CF.LifeCycle.InitializeError" + NL + "        exception when an initialization error occurs." + NL + "" + NL + "        This function is the first call made by the framework after" + NL + "        constructing the component.  " + NL + "        \"\"\"";
  protected final String TEXT_13 = NL + "        ";
  protected final String TEXT_14 = "_base.initialize(self)  # DO NOT REMOVE THIS LINE" + NL + "" + NL + "        # TODO: add your implementation here" + NL + "" + NL + "    def start(self):" + NL + "        \"\"\"The start operation is provided to command the resource implementing" + NL + "        this interface to start internal processing." + NL + "" + NL + "        The start operation shall raise the CF.Resource.StartError exception if an error" + NL + "        occurs while starting the resource." + NL + "" + NL + "        IMPORTANT: The start() operation will not be called unless your component" + NL + "        is the waveform assembly controller *or* the assembly controller explicitly" + NL + "        tells your component to start." + NL + "        \"\"\"" + NL + "" + NL + "        pass # TODO: add your implementation here" + NL + "" + NL + "    def stop(self):" + NL + "        \"\"\"The stop operation is provided to command the resource implementing" + NL + "        this interface to stop internal processing." + NL + "" + NL + "        The stop operation shall not inhibit subsequent configure, query, and start operations." + NL + "" + NL + "        The stop operation shall raise the CF.Resource.StopError exception if" + NL + "        an error occurs while stopping the resource." + NL + "" + NL + "        IMPORTANT: The stop() operation will not be called unless your component" + NL + "        is the waveform assembly controller *or* the assembly controller explicitly" + NL + "        tells your component to start." + NL + "        \"\"\"" + NL + "" + NL + "        pass # TODO: add your implementation here" + NL + "" + NL + "    def releaseObject(self):" + NL + "        \"\"\"The purpose of the releaseObject operation is to provide a means by" + NL + "        which an instantiated component may be torn down." + NL + "" + NL + "        The releaseObject operation shall raise a CF.LifeCycle.ReleaseError" + NL + "        exception when a release error occurs." + NL + "        \"\"\"" + NL + "" + NL + "        # TODO: add your implementation here" + NL;
  protected final String TEXT_15 = NL + "        ";
  protected final String TEXT_16 = "_base.releaseObject(self)  # DO NOT REMOVE THIS LINE" + NL + "" + NL + "    def getPort(self, name):" + NL + "        \"\"\"The getPort operation provides a mechanism to obtain a specific" + NL + "        consumer or producer port. A port supplier may contain zero-to-many" + NL + "        consumer and producer port components. The exact number is specified in" + NL + "        the components software profile SCD (section 3.1.3.5). Multiple input" + NL + "        and/or output ports provide flexibility for port suppliers that manage" + NL + "        varying priority levels and categories of incoming and outgoing" + NL + "        messages, provide multi-threaded message handling, or other special" + NL + "        message processing." + NL + "" + NL + "        The getPort operation shall return the CORBA object reference that is" + NL + "        associated with the input port name." + NL + "" + NL + "        The getPort operation shall raise an UnknownPort exception if the port" + NL + "        name is invalid." + NL + "        \"\"\"" + NL + "" + NL + "        return ";
  protected final String TEXT_17 = "_base.getPort(self, name)  # DO NOT REMOVE THIS LINE" + NL + "" + NL + "    def runTest(self, properties, testid):" + NL + "        \"\"\"The runTest operation allows components to be black box tested." + NL + "        This allows built-in tests (BITs) to be implemented which provide a" + NL + "        means to isolate faults (both software and hardware) within the system." + NL + "" + NL + "        The runTest operation shall raise the CF.TestableObject.UnknownTest" + NL + "        exception when there is no underlying test implementation that is" + NL + "        associated with the input testId given.  " + NL + "" + NL + "        The runTest operation shall raise the CF.UnknownProperties exception" + NL + "        when the input parameter testValues contains any CF DataTypes that are" + NL + "        not known by the components test implementation or any values that are" + NL + "        out of range for the requested test. The exception parameter" + NL + "        invalidProperties shall contain the invalid testValues properties id(s)" + NL + "        that are not known by the component or the value(s) are out of range." + NL + "        \"\"\"" + NL + "" + NL + "        raise CF.TestableObject.UnknownTest(\"unknown test: %s\" % str(testid)) # TODO: add your implementation here" + NL + "" + NL + "if __name__ == '__main__':" + NL + "    start_component(";
  protected final String TEXT_18 = ")";
  protected final String TEXT_19 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Date date = new Date(System.currentTimeMillis());
    Implementation impl = templ.getImpl();
    mil.jpeojtrs.sca.spd.SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_4);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_5);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_8);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
} 