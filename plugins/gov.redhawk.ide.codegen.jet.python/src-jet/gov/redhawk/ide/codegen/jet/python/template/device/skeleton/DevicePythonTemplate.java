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
package gov.redhawk.ide.codegen.jet.python.template.device.skeleton;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;

	/**
    * @generated
    */

public class DevicePythonTemplate
{

  protected static String nl;
  public static synchronized DevicePythonTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DevicePythonTemplate result = new DevicePythonTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python" + NL + "from ossie.cf import CF, CF__POA" + NL + "from ossie.device import Device, start_device" + NL + "import logging" + NL + "" + NL + "class ";
  protected final String TEXT_2 = "(CF__POA.Device, Device):" + NL + "    def __init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams):" + NL + "        Device.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams, PROPERTIES)    " + NL + "" + NL + "    ###########################################" + NL + "    # CF::LifeCycle" + NL + "    ###########################################" + NL + "    def initialize(self):" + NL + "        # This function is called by the framework during construction of the waveform" + NL + "        # it is called before configure() is called, so whatever values you set in the xml properties file" + NL + "        # won't be available when this is called. I wouldn't have done it in this order, but this" + NL + "        # is what the spec call for" + NL + "        # TODO:" + NL + "        pass" + NL + "    " + NL + "    def releaseObject(self):" + NL + "        # This is the part of the code where you will put in the logic that will appropriately cleanup" + NL + "        # your component.  It is appropriate to do Memory Cleanup here since" + NL + "        # CORBA doesn't like any cleaning up done in the Destructor." + NL + "        # TODO:" + NL + "        pass" + NL + "" + NL + "    ###########################################" + NL + "    # CF::PropertySet" + NL + "    ###########################################" + NL + "    def query(self, configProperties):" + NL + "        # Framework level call that allows a Component to reveal the details of it's properties to interested parties.  You will" + NL + "        # need to take in the request, find the appropriate property and return that value to the one who queried." + NL + "        # TODO:" + NL + "        pass" + NL + "    " + NL + "    def configure(self, configProperties):" + NL + "        # Configure is called when a Component is asked to change/set a property that has already been previously defined." + NL + "        # Here you will put in the logic that will take care of dealing with the newly requested property, which in turn" + NL + "        # will allow your Components to be overridden at runtime." + NL + "        # TODO:" + NL + "        pass" + NL + "    " + NL + "    ###########################################" + NL + "    # CF::TestableObject" + NL + "    ###########################################" + NL + "    def runTest(self, properties, testid):" + NL + "        # Allows for the ability to perform stand alone testing of an SCA Component - useful for built in test (BIT) operations." + NL + "        # This function will need to run the test specified by the TestID, along with the values provided by testValues." + NL + "        # TODO:" + NL + "        pass" + NL + "       " + NL + "    ###########################################" + NL + "    # CF::PortSupplier" + NL + "    ###########################################" + NL + "    def getPort(self, name):" + NL + "        # This function will return an object reference for the named port which in turn will be used to establish a connection" + NL + "        # between two separate ports possible." + NL + "        # TODO:" + NL + "        return None" + NL + "    " + NL + "###########################################                    " + NL + "# program execution" + NL + "###########################################" + NL + "if __name__ == \"__main__\":" + NL + "    logging.getLogger().setLevel(logging.WARN)" + NL + "    logging.debug(\"Starting Device\")" + NL + "    start_device(";
  protected final String TEXT_3 = ")";

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	TemplateParameter templ = (TemplateParameter) argument;
	mil.jpeojtrs.sca.spd.Implementation impl = templ.getImpl();
	ImplementationSettings implSettings = templ.getImplSettings();
	mil.jpeojtrs.sca.spd.SoftPkg softPkg = (mil.jpeojtrs.sca.spd.SoftPkg) impl.eContainer();
	String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
} 