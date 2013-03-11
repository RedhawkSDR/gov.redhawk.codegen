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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.skeleton;

import gov.redhawk.ide.codegen.ImplementationSettings;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.Implementation;
import gov.redhawk.ide.codegen.jet.TemplateParameter;

	/**
    * @generated
    */

public class ResourceCppTemplate
{

  protected static String nl;
  public static synchronized ResourceCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceCppTemplate result = new ResourceCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**************************************************************************" + NL + "    This is the component code. This file contains all the access points" + NL + "     you need to use to be able to access all input and output ports," + NL + "     respond to incoming data, and perform general component housekeeping" + NL + "**************************************************************************/" + NL + "#include <iostream>" + NL + "#include <fstream>" + NL + "" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"" + NL + "#include <uuid/uuid.h>" + NL + "" + NL + "/**************************************************************************" + NL + "    Component-level housekeeping (memory and thread management)" + NL + "**************************************************************************/" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = "_i::";
  protected final String TEXT_5 = "_i(const char *uuid, omni_condition *con," + NL + "    const char *label, ossie::ORB *in_orb) : Resource_impl(uuid)" + NL + "{" + NL + "    cout << \"Starting everything\" << endl;" + NL + "    component_running = con;" + NL + "    comp_uuid = uuid;" + NL + "    " + NL + "    // component_alive flag is turned to false to terminate the main processing thread" + NL + "    component_alive = true;" + NL + "    naming_service_name = label;" + NL + "    orb = in_orb;" + NL + "" + NL + "    // this is the signal used to tell the main procesing thread that information is ready" + NL + "    //  it is thrown by the input (provides) port when data is ready" + NL + "    data_in_signal = new omni_condition(&data_in_signal_lock);" + NL + "" + NL + "    //Initialize variables" + NL + "" + NL + "    thread_started = false;" + NL + "}" + NL + NL;
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = "_i::~";
  protected final String TEXT_8 = "_i(void)" + NL + "{" + NL + "    // The function releaseObject in the Framework-level functions section of this file does the memory housekeeping" + NL + "    //    The reason why it was placed there instead of here is that CORBA doesn't like it when you clean up CORBA" + NL + "    //    stuff in the destructor. Also, the component's ports are separate threads that need to be cleaned up, and" + NL + "    //    that also belongs in the framework-level termination." + NL + "    usleep(1);" + NL + "}" + NL + "" + NL + "/*******************************************************************************************" + NL + "    Framework-level functions" + NL + "    These functions are generally called by the framework to perform housekeeping." + NL + "*******************************************************************************************/" + NL + "void ";
  protected final String TEXT_9 = "_i::initialize() throw (CF::LifeCycle::InitializeError, CORBA::SystemException)" + NL + "{" + NL + "    // This function is called by the framework during construction of the waveform" + NL + "    //    it is called before configure() is called, so whatever values you set in the xml properties file" + NL + "    //    won't be available when this is called. I wouldn't have done it in this order, but this" + NL + "    //    is what the specs call for" + NL + "\t//" + NL + "\t//\tTODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_10 = "_i::start() throw (CORBA::SystemException, CF::Resource::StartError)" + NL + "{" + NL + "    // This is a framework-level start call. This function is called only if this component" + NL + "    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "    //    it calls this component's start function" + NL + "    // The vast majority of components won't have their start function called" + NL + "\t//" + NL + "\t//\tTODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_11 = "_i::stop() throw (CORBA::SystemException, CF::Resource::StopError)" + NL + "{" + NL + "    // This is a framework-level stop call. This function is called only if this component" + NL + "    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "    //    it calls this component's start function" + NL + "    // The vast majority of components won't have their stop function called" + NL + "\t//" + NL + "\t//\tTODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_12 = "_i::releaseObject() throw (CF::LifeCycle::ReleaseError, CORBA::SystemException)" + NL + "{" + NL + "\t// This is the part of the code where you will put in the logic that will appropriately cleanup" + NL + "\t// your component.  It is appropriate to do Memory Cleanup here since" + NL + "\t// CORBA doesn't like any cleaning up done in the Destructor." + NL + "\t// TODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_13 = "_i::configure(const CF::Properties&) throw (CORBA::SystemException, CF::PropertySet::InvalidConfiguration, CF::PropertySet::PartialConfiguration)" + NL + "{" + NL + "\t// Configure is called when a Component is asked to change/set a property that has already been previously defined." + NL + "\t// Here you will put in the logic that will take care of dealing with the newly requested property, which in turn" + NL + "\t// will allow your Components to be overridden at runtime." + NL + "\t// TODO" + NL + "}" + NL + "" + NL + "CORBA::Object* ";
  protected final String TEXT_14 = "_i::getPort (const char *) throw (CF::PortSupplier::UnknownPort, CORBA::SystemException)" + NL + "{" + NL + "\t// This function will return an object reference for the named port which in turn will be used to establish a connection" + NL + "\t// between two separate ports possible." + NL + "\t// TODO" + NL + "\treturn 0;" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_15 = "_i::query (CF::Properties & configProperties) throw (CF::UnknownProperties, CORBA::SystemException)" + NL + "{" + NL + "\t// Framework level call that allows a Component to reveal the details of it's properties to interested parties.  You will" + NL + "\t// need to take in the request, find the appropriate property and return that value to the one who queried." + NL + "\t// TODO" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_16 = "_i::runTest (CORBA::ULong TestID, CF::Properties & testValues) throw (CF::UnknownProperties, CF::TestableObject::UnknownTest, CORBA::SystemException)" + NL + "{" + NL + "\t// Allows for the ability to perform stand alone testing of an SCA Component - useful for built in test (BIT) operations." + NL + "\t// This function will need to run the test specified by the TestID, along with the values provided by testValues." + NL + "\t// TODO" + NL + "}" + NL + "" + NL + "char * ";
  protected final String TEXT_17 = "_i::identifier () throw (CORBA::SystemException)" + NL + "{" + NL + "\t// Provide the ID of your Component to those who may be interested." + NL + "\t// TODO" + NL + "\treturn NULL;" + NL + "}" + NL + "" + NL + "/**************************************************************************************" + NL + "    Main processing thread" + NL + "    " + NL + "    General functionality:" + NL + "    This function is running as a separate thread from the component's main thread. The function" + NL + "    is generally in a blocked state, where it is waiting for the data_in_signal to be set." + NL + "    " + NL + "    data_in_signal will only be set automatically when the component is exiting, otherwise" + NL + "    you should set it from either the input port or some other condition." + NL + "**************************************************************************************/" + NL + "" + NL + "void ";
  protected final String TEXT_18 = "_i::run(void *args)" + NL + "{" + NL + "" + NL + "    while(component_alive)" + NL + "    {" + NL + "        // Wait for signal that data is ready (or signal to exit service loop)" + NL + "        data_in_signal->wait();" + NL + "        " + NL + "        // Lock the mutex to prevent control information from changing the operating parameters while a block of" + NL + "        //    data is being processed" + NL + "        process_data_lock.lock();" + NL + "        " + NL + "        // If the component was released (framework-level end) while this was locked, then exit loop" + NL + "        if (!component_alive) {" + NL + "            process_data_lock.unlock();" + NL + "            continue;" + NL + "        }" + NL + "        " + NL + "        // While the process_data_lock is locked, you can perform work on your data." + NL + "        // TODO" + NL + "        " + NL + "        process_data_lock.unlock();" + NL + "    }" + NL + "    thread_exit_lock.lock();    // this is necessary to make sure releaseObject is called before the component is destroyed" + NL + "}";
  protected final String TEXT_19 = NL;

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

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
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