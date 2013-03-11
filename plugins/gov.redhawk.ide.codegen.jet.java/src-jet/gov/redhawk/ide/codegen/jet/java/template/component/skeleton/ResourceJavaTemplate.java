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
package gov.redhawk.ide.codegen.jet.java.template.component.skeleton;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.jet.java.JavaJetGeneratorPlugin;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.idl.IdlJavaUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.jacorb.idl.Interface;
import org.jacorb.idl.Operation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

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
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL;
  protected final String TEXT_3 = NL + "import java.util.Vector;";
  protected final String TEXT_4 = NL + "import java.util.concurrent.ArrayBlockingQueue;";
  protected final String TEXT_5 = NL + NL + "import CF.DataType;" + NL + "import CF.PropertiesHolder;" + NL + "import CF.Resource;" + NL + "import CF.ResourceHelper;" + NL + "import CF.UnknownProperties;" + NL + "import CF.LifeCyclePackage.InitializeError;" + NL + "import CF.LifeCyclePackage.ReleaseError;" + NL + "import CF.PropertySetPackage.InvalidConfiguration;" + NL + "import CF.PropertySetPackage.PartialConfiguration;" + NL + "import CF.ResourcePackage.StartError;" + NL + "import CF.ResourcePackage.StopError;" + NL + "" + NL + "import org.omg.CORBA.UserException;" + NL + "import org.omg.CosNaming.NameComponent;" + NL + "" + NL + "import org.ossie.component.Resource_impl;" + NL;
  protected final String TEXT_6 = NL + "import org.ossie.PropertyContainer;";
  protected final String TEXT_7 = NL + "import CF.PortPOA;";
  protected final String TEXT_8 = NL + NL + "/**" + NL + " * This is the component code. This file contains all the access points" + NL + " * you need to use to be able to access all input and output ports," + NL + " * respond to incoming data, and perform general component housekeeping" + NL + " */" + NL + "public class ";
  protected final String TEXT_9 = " extends Resource_impl implements Runnable {" + NL;
  protected final String TEXT_10 = NL + "    private ArrayBlockingQueue<";
  protected final String TEXT_11 = "> inputQueue";
  protected final String TEXT_12 = ";";
  protected final String TEXT_13 = NL + "    " + NL + "    public ";
  protected final String TEXT_14 = "(String nsIor, String compId, String binding) {" + NL + "        super(nsIor, compId, true, 196608, 2000000);" + NL + "        " + NL + "        System.out.println(\"Starting everything\");" + NL + "        " + NL + "        // component_alive flag is turned to false to terminate the main processing thread" + NL + "        this.setComponentAlive(true);" + NL + "    }" + NL + "" + NL + "" + NL + "\t/*******************************************************************************************" + NL + "\t" + NL + "\t  Framework-level functions" + NL + "\t    " + NL + "\t  These functions are generally called by the framework to perform housekeeping." + NL + "\t" + NL + "\t*******************************************************************************************/" + NL + "\tpublic void start() throws StartError {" + NL + "\t    // This is a framework-level start call. This function is called only if this component" + NL + "\t    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "\t    //    it calls this component's start function" + NL + "\t    // The vast majority of components won't have their start function called" + NL + "\t\t" + NL + "\t\t// TODO" + NL + "    }" + NL + "" + NL + "\tpublic void stop() throws StopError {" + NL + "\t    // This is a framework-level stop call. This function is called only if this component" + NL + "\t    //    happens to be the assembly controller (or the assembly controller is written such that" + NL + "\t    //    it calls this component's stop function" + NL + "\t    // The vast majority of components won't have their stop function called" + NL + "\t\t" + NL + "\t\t// TODO" + NL + "    }" + NL + "" + NL + "\tpublic void initialize() throws InitializeError {" + NL + "\t    // This function is called by the framework during construction of the waveform" + NL + "\t    //    it is called before configure() is called, so whatever values you set in the xml properties file" + NL + "\t    //    won't be available when this is called. I wouldn't have done it in this order, but this" + NL + "\t    //    is what the specs call for" + NL + "\t\t" + NL + "\t\t// TODO" + NL + "    }" + NL + "" + NL + "\tpublic void releaseObject() throws ReleaseError {" + NL + "\t\t// This is the part of the code where you will put in the logic that will appropriately cleanup" + NL + "\t\t// your component.  It is appropriate to do Memory Cleanup here since" + NL + "\t\t// CORBA doesn't like any cleaning up done in the Destructor." + NL + "\t\t" + NL + "\t    // TODO" + NL + "\t}" + NL + "\t" + NL + "\t/** " + NL + "\t * {@inheritDoc}" + NL + "\t * With the ArrayBlockingQueue, add an empty array to it. This will" + NL + "\t * cause anything blocking on it to wake up." + NL + "\t */" + NL + "\tprotected void clearWaits() {";
  protected final String TEXT_15 = NL + "        this.inputQueue";
  protected final String TEXT_16 = ".add(new ";
  protected final String TEXT_17 = ");";
  protected final String TEXT_18 = NL + "    }" + NL + "" + NL + "\t/** Generated in component_support */" + NL + "\tpublic void configure(DataType[] configProperties) throws InvalidConfiguration, PartialConfiguration {" + NL + "\t\t// Configure is called when a Component is asked to change/set a property that has already been previously defined." + NL + "\t\t// Here you will put in the logic that will take care of dealing with the newly requested property, which in turn" + NL + "\t\t// will allow your Components to be overridden at runtime." + NL + "\t\t" + NL + "\t\t// TODO" + NL + "\t\tsynchronized(propSet) {" + NL + "\t\t\t" + NL + "\t    }" + NL + "    }" + NL + "" + NL + "\tpublic void query(PropertiesHolder configProperties) throws UnknownProperties {" + NL + "\t\t// Framework level call that allows a Component to reveal the details of it's properties to interested parties.  You will" + NL + "\t\t// need to take in the request, find the appropriate property and return that value to the one who queried." + NL + "\t\t" + NL + "\t\t// TODO" + NL + "    }" + NL + "" + NL + "    /**************************************************************************************" + NL + "" + NL + "      Main processing thread" + NL + "    " + NL + "      General functionality:" + NL + "      This function is running as a separate thread from the component's main thread. The function" + NL + "      is generally in a blocked state, where it is waiting for data to be added to the inputData queues." + NL + "    " + NL + "      The inputData queues will only have something added to them automatically when the component is " + NL + "      exiting, otherwise you should set it from either the input port or some other condition." + NL + "" + NL + "    **************************************************************************************/" + NL + "" + NL + "    public void run() " + NL + "    {" + NL + "" + NL + "\t    while(this.isComponentAlive())" + NL + "\t    {";
  protected final String TEXT_19 = NL + "\t        // Wait for signal that data is ready (or signal to exit service loop)" + NL + "\t        ";
  protected final String TEXT_20 = " inputData";
  protected final String TEXT_21 = " = null;" + NL + "\t        try {" + NL + "\t            inputData";
  protected final String TEXT_22 = " = this.inputQueue";
  protected final String TEXT_23 = ".take(); // This blocks until there is data available" + NL + "\t        } catch (InterruptedException e) {" + NL + "\t            continue;" + NL + "\t        }" + NL + "\t        " + NL + "\t\t    // If the component was released (framework-level end) while this was locked, then exit loop" + NL + "\t\t    if (!this.isComponentAlive()) {" + NL + "\t\t        continue;" + NL + "\t\t    }" + NL + "\t\t    ";
  protected final String TEXT_24 = NL + "\t        // synchronize on the lock to prevent control information from changing" + NL + "\t        // the operating parameters while a block of data is being processed" + NL + "\t        synchronized (processDataLock) {" + NL + "\t        " + NL + "\t\t        // If the component was released (framework-level end) while this was locked, then exit loop" + NL + "\t\t        if (!this.isComponentAlive()) {" + NL + "\t\t            continue;" + NL + "\t\t        }" + NL + "\t\t        " + NL + "\t\t        // Process data here while the data has been locked" + NL + "\t\t        // TODO" + NL + "\t        }" + NL + "\t    }" + NL + "\t}" + NL + "    " + NL + "\tpublic static void main(String[] args) " + NL + "\t{" + NL + "\t\tString naming_context_ior = \"\";" + NL + "\t\tString component_identifier = \"\";" + NL + "\t\tString name_binding = \"\";" + NL + "" + NL + "\t\t// Grab all the command line arguments" + NL + "\t\tfor (int i = 0; i < args.length; i++) {" + NL + "\t\t\tif (\"NAMING_CONTEXT_IOR\".equals(args[i])) {" + NL + "\t\t\t\tnaming_context_ior = args[++i];" + NL + "\t\t\t} else if (\"COMPONENT_IDENTIFIER\".equals(args[i])) {" + NL + "\t\t\t\tcomponent_identifier = args[++i];" + NL + "\t\t\t} else if (\"NAME_BINDING\".equals(args[i])) {" + NL + "\t\t\t\tname_binding = args[++i];" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "" + NL + "        // Make sure that we have all parameters specified" + NL + "\t\tif (\"\".equals(name_binding) || \"\".equals(component_identifier) || \"\".equals(naming_context_ior)) {" + NL + "\t\t\tSystem.out.println(\"Argument check failed! Binding: \" + name_binding + \" Component ID: \" + component_identifier + \" IOR: \"+ naming_context_ior);" + NL + "\t\t\tSystem.exit(-1);" + NL + "\t\t}" + NL + "" + NL + "\t\t// Create the component servant and object reference" + NL + "\t\t";
  protected final String TEXT_25 = " comp = new ";
  protected final String TEXT_26 = "(naming_context_ior, component_identifier, name_binding);" + NL + "" + NL + "\t\t// bind the Object Reference in the Naming Service" + NL + "        try {" + NL + "    \t\t// get object reference from the servant" + NL + "    \t\torg.omg.CORBA.Object ref = comp.getRootpoa().servant_to_reference(comp);" + NL + "    \t\tResource href = ResourceHelper.narrow(ref);" + NL + "" + NL + "        \tNameComponent[] path = comp.getNcRef().to_name(name_binding);" + NL + "\t        comp.getNcRef().rebind(path, href);" + NL + "        } catch (UserException e) {" + NL + "\t        // PASS" + NL + "        }" + NL + "" + NL + "\t\t// This bit is ORB specific" + NL + "\t\t// The servants are running at this point so we wait until" + NL + "\t\t// The releaseObject method clear the variable and the component exits" + NL + "        System.out.println(\"Waiting for completion.\");" + NL + "\t\twhile (comp.isComponentAlive()) {" + NL + "\t\t\ttry {" + NL + "\t            Thread.sleep(1000);" + NL + "            } catch (InterruptedException e) {" + NL + "\t            // PASS" + NL + "            }" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tSystem.out.println(\"Done, cleaning up\");" + NL + "\t\t" + NL + "\t\ttry {" + NL + "\t\t    // Remove this component from the Name Service" + NL + "\t        comp.getNcRef().unbind(comp.getNcRef().to_name(name_binding));" + NL + "        } catch (UserException e) {" + NL + "\t        // PASS" + NL + "        }" + NL + "        comp.getOrb().shutdown(true);" + NL + "\t\tcomp.getOrb().destroy();" + NL + "\t}" + NL + "}";

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
      
    JavaTemplateParameter template = (JavaTemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    Implementation impl = template.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
    EList<Simple> simpleList = softPkg.getPropertyFile().getProperties().getSimple();
    EList<Provides> provides = ports.getProvides();
    EList<Uses> uses = ports.getUses();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    String pkg = template.getPackage();
    boolean includedProp = false;
    
    // Get a list of all the repId's for the uses ports
    HashSet<String> usesList = new HashSet<String>();
    for (Uses entry : uses) {
        String intName = entry.getRepID().toString();
        usesList.add(intName);
    }
    
    // Get a list of all the repId's for the provides ports
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        String intName = entry.getRepID().toString();
        providesList.add(intName);
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(pkg);
    stringBuffer.append(TEXT_2);
     
    if (simpleList.size() > 0) {

    stringBuffer.append(TEXT_3);
    
    }
    if (providesList.size() > 0) {

    stringBuffer.append(TEXT_4);
    
    }

    stringBuffer.append(TEXT_5);
     
    if (simpleList.size() > 0) {
       if (!includedProp) {
           includedProp = true;

    stringBuffer.append(TEXT_6);
    
       }
    }
    if ((provides.size() > 0) || (uses.size() > 0)) {

    stringBuffer.append(TEXT_7);
    
    }
    // Check the contents of the provides list vs. the uses list
    // and remove all duplicates
    HashSet<String> commonList = new HashSet<String>();
    for (String entry : usesList) {
        if (providesList.contains(entry)) {
            commonList.add(entry);
            continue;
        }
    }
    
    // and remove all duplicates
    providesList.removeAll(commonList);
    usesList.removeAll(commonList);

    HashMap<String, Interface> intMap = new HashMap<String, Interface>();
    for (String entry : commonList) {
        String rep = entry.split(":")[1];
        Interface intf = IdlJavaUtil.getInstance().getInterface(search_paths, rep, true);
        if (intf == null) {
        	throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        intMap.put(entry, intf);
    }
    for (String entry : usesList) {
        String rep = entry.split(":")[1];
        Interface intf = IdlJavaUtil.getInstance().getInterface(search_paths, rep, true);
        if (intf == null) {
        	throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
    	}
        intMap.put(entry, intf);
    }
    for (String entry : providesList) {
        String rep = entry.split(":")[1];
        Interface intf = IdlJavaUtil.getInstance().getInterface(search_paths, rep, true);
        if (intf == null) {
        	throw new CoreException(new Status(IStatus.ERROR, JavaJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
    	}
        intMap.put(entry, intf);
    }

    stringBuffer.append(TEXT_8);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_9);
    
    int inArrayCount = 0;
    for (Provides p : provides) {
        Interface iface = intMap.get(p.getRepID());
        for (Operation op : IdlJavaUtil.getOperations(iface)) {
            if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
                String type = IdlJavaUtil.getParams(op)[0].paramTypeSpec.getJavaTypeName();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(type);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(inArrayCount++);
    stringBuffer.append(TEXT_12);
    
            }
        }
    } // End for (provides)

    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
     
    inArrayCount = 0;
    for (Provides p : provides) {
        Interface iface = intMap.get(p.getRepID());
        for (Operation op : IdlJavaUtil.getOperations(iface)) {
            if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
                String type = IdlJavaUtil.getParams(op)[0].paramTypeSpec.getJavaTypeName();
                String additional = type.contains("[]") ? "[0]" : "()";

    stringBuffer.append(TEXT_15);
    stringBuffer.append(inArrayCount++);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(type.replaceAll("\\[\\]", ""));
    stringBuffer.append(additional);
    stringBuffer.append(TEXT_17);
    
            }
        }
    }

    stringBuffer.append(TEXT_18);
    
    if (provides.size() > 0) {
        inArrayCount = 0;
        for (Provides p : provides) {
            Interface iface = intMap.get(p.getRepID());
            for (Operation op : IdlJavaUtil.getOperations(iface)) {
                if ("pushPacket".equals(IdlJavaUtil.getOpName(op))) {
                    String type = IdlJavaUtil.getParams(op)[0].paramTypeSpec.getJavaTypeName();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(type);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(inArrayCount);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(inArrayCount);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(inArrayCount++);
    stringBuffer.append(TEXT_23);
    
                }
            }
	    } // End for(provides)
    } // End if provides

    stringBuffer.append(TEXT_24);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE