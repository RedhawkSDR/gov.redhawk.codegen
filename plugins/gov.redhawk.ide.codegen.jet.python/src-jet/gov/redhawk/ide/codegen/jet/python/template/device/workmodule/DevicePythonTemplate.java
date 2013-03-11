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
package gov.redhawk.ide.codegen.jet.python.template.device.workmodule;

import gov.redhawk.ide.RedhawkIdeActivator;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.SupportsInterface;
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
  protected final String TEXT_1 = "#!/usr/bin/env python" + NL + "from ossie.cf import CF, CF__POA" + NL + "from ossie.device import ";
  protected final String TEXT_2 = "Device, ";
  protected final String TEXT_3 = "AggregateDevice,";
  protected final String TEXT_4 = "start_device" + NL + "import commands, os, sys" + NL + "import logging" + NL;
  protected final String TEXT_5 = NL + "from ";
  protected final String TEXT_6 = ".";
  protected final String TEXT_7 = "Interfaces import ";
  protected final String TEXT_8 = ", ";
  protected final String TEXT_9 = "__POA ";
  protected final String TEXT_10 = NL + "import time, signal, copy";
  protected final String TEXT_11 = NL + "import threading";
  protected final String TEXT_12 = NL + "import WorkModule";
  protected final String TEXT_13 = NL + NL + "from ";
  protected final String TEXT_14 = "Props import PROPERTIES" + NL;
  protected final String TEXT_15 = NL + NL + "'''provides port(s)'''";
  protected final String TEXT_16 = NL + NL + "class ";
  protected final String TEXT_17 = "_";
  protected final String TEXT_18 = "_in_i(";
  protected final String TEXT_19 = "__POA.";
  protected final String TEXT_20 = "):" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name";
  protected final String TEXT_21 = NL + NL + "    def ";
  protected final String TEXT_22 = "(self";
  protected final String TEXT_23 = ", ";
  protected final String TEXT_24 = "):";
  protected final String TEXT_25 = "        " + NL + "        # Queue the incoming data to be later worked on in the Workmodule" + NL + "        # TODO:" + NL + "        self.parent.work_mod.queueData(";
  protected final String TEXT_26 = ", ";
  protected final String TEXT_27 = ", ";
  protected final String TEXT_28 = ", ";
  protected final String TEXT_29 = ")";
  protected final String TEXT_30 = NL + "        # Optionally manipulate or output incoming data" + NL + "        # TODO:";
  protected final String TEXT_31 = NL + "        pass";
  protected final String TEXT_32 = NL + NL + "    def _get_";
  protected final String TEXT_33 = "(self):" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_34 = NL + NL + "    def _set_";
  protected final String TEXT_35 = "(self, data):" + NL + "        # TODO:" + NL + "        pass" + NL;
  protected final String TEXT_36 = NL;
  protected final String TEXT_37 = NL + NL + "'''uses port(s)'''";
  protected final String TEXT_38 = NL + NL + "class ";
  protected final String TEXT_39 = "_";
  protected final String TEXT_40 = "_out_i(CF__POA.Port):" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.outPorts = {}";
  protected final String TEXT_41 = NL + "        self.refreshSRI = False" + NL + "        self.defaultStreamSRI = BULKIO.StreamSRI(1, 0.0, 0.001, 1, 200, 0.0, 0.001, 1, 1, \"sampleStream\", [])";
  protected final String TEXT_42 = NL + "        self.port_lock = threading.Lock()" + NL + "" + NL + "    def connectPort(self, connection, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        port = connection._narrow(";
  protected final String TEXT_43 = "__POA.";
  protected final String TEXT_44 = ")" + NL + "        self.outPorts[str(connectionId)] = port";
  protected final String TEXT_45 = NL + "        self.refreshSRI = True";
  protected final String TEXT_46 = NL + "        self.port_lock.release()" + NL + "" + NL + "    def disconnectPort(self, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        if self.outPorts.has_key(str(connectionId)):" + NL + "            self.outPorts.pop(str(connectionId), None)" + NL + "        else:" + NL + "            self.port_lock.release()" + NL + "            return" + NL + "        self.port_lock.release()";
  protected final String TEXT_47 = NL + NL + "    def ";
  protected final String TEXT_48 = "(self";
  protected final String TEXT_49 = ", ";
  protected final String TEXT_50 = "):";
  protected final String TEXT_51 = NL + "        retVal = \"\"";
  protected final String TEXT_52 = NL + "        retVal = None";
  protected final String TEXT_53 = NL + "        retVal = []";
  protected final String TEXT_54 = NL + "        self.defaultStreamSRI = ";
  protected final String TEXT_55 = NL + "        if self.refreshSRI:" + NL + "            self.pushSRI(self.defaultStreamSRI)";
  protected final String TEXT_56 = NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for port in self.outPorts:" + NL + "                if self.outPorts[port] != None:";
  protected final String TEXT_57 = "retVal =";
  protected final String TEXT_58 = " self.outPorts[port].";
  protected final String TEXT_59 = "(";
  protected final String TEXT_60 = ", ";
  protected final String TEXT_61 = ")" + NL + "        except:" + NL + "            self.parent._log.exception(\"The call to ";
  protected final String TEXT_62 = " failed\")" + NL + "        self.port_lock.release()";
  protected final String TEXT_63 = NL + "        return retVal";
  protected final String TEXT_64 = NL + NL + "    def _get_";
  protected final String TEXT_65 = "(self):" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_66 = NL + NL + "    def _set_";
  protected final String TEXT_67 = "(self, data):" + NL + "        # TODO:" + NL + "        pass" + NL;
  protected final String TEXT_68 = NL + NL + "class ";
  protected final String TEXT_69 = "(CF__POA.";
  protected final String TEXT_70 = "Aggregate";
  protected final String TEXT_71 = "Plain";
  protected final String TEXT_72 = "Device,";
  protected final String TEXT_73 = "Device, ";
  protected final String TEXT_74 = "Device";
  protected final String TEXT_75 = ", AggregateDevice";
  protected final String TEXT_76 = "):" + NL + "    def __init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams):";
  protected final String TEXT_77 = NL + "        ExecutableDevice.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams, PROPERTIES)";
  protected final String TEXT_78 = NL + "        LoadableDevice.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams, PROPERTIES)";
  protected final String TEXT_79 = NL + "        Device.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams, PROPERTIES)";
  protected final String TEXT_80 = NL + "        AggregateDevice.__init__(self)";
  protected final String TEXT_81 = NL + "        self.ports = {}";
  protected final String TEXT_82 = "    ";
  protected final String TEXT_83 = NL + NL + "        '''provides'''";
  protected final String TEXT_84 = NL + "        portRef = ";
  protected final String TEXT_85 = "_";
  protected final String TEXT_86 = "_in_i(self, \"";
  protected final String TEXT_87 = "\")" + NL + "        self.ports['";
  protected final String TEXT_88 = "'] = portRef";
  protected final String TEXT_89 = "    " + NL + "" + NL + "        '''uses'''";
  protected final String TEXT_90 = NL + "        portRef = ";
  protected final String TEXT_91 = "_";
  protected final String TEXT_92 = "_out_i(self, \"";
  protected final String TEXT_93 = "\")" + NL + "        self.ports['";
  protected final String TEXT_94 = "'] = portRef";
  protected final String TEXT_95 = NL + NL + "    def getPort(self, name):" + NL + "        if self.ports.has_key(name):" + NL + "            return self.ports[str(name)]._this()" + NL + "        raise CF.PortSupplier.UnknownPort()" + NL;
  protected final String TEXT_96 = NL + "    def releaseObject(self):" + NL + "        self.work_mod.Release()";
  protected final String TEXT_97 = NL + NL + "    ###########################################" + NL + "    # CF::LifeCycle" + NL + "    ###########################################" + NL + "    # This function is called by the framework during construction of the waveform" + NL + "    #    it is called before configure() is called, so whatever values you set in the xml properties file" + NL + "    #    won't be available when this is called. I wouldn't have done it in this order, but this" + NL + "    #    is what the spec call for" + NL + "    def initialize(self):";
  protected final String TEXT_98 = NL + "        self.work_mod = WorkModule.WorkClass(self)";
  protected final String TEXT_99 = NL + "        pass ";
  protected final String TEXT_100 = NL + "    ###########################################" + NL + "    # CF::TestableObject" + NL + "    ###########################################" + NL + "    # Allows for the ability to perform stand alone testing of an SCA Component - useful for built in test (BIT) operations." + NL + "    # This function will need to run the test specified by the TestID, along with the values provided by testValues." + NL + "    def runTest(self, properties, testid):" + NL + "        # TODO:" + NL + "        pass" + NL + "       " + NL + "    ###########################################" + NL + "    # CF::Device" + NL + "    ###########################################";
  protected final String TEXT_101 = NL + "    # overrides allocateCapacity for ";
  protected final String TEXT_102 = NL + "    def allocate_";
  protected final String TEXT_103 = "(self, value):" + NL + "        # Allocate space for the following property  " + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_104 = NL + "    # overrides deallocateCapacity for ";
  protected final String TEXT_105 = NL + "    def deallocate_";
  protected final String TEXT_106 = "(self, value):" + NL + "        # Deallocate space for the property" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_107 = NL + NL + "###########################################                    " + NL + "# program execution" + NL + "###########################################" + NL + "if __name__ == \"__main__\":" + NL + "    logging.getLogger().setLevel(logging.WARN)" + NL + "    logging.debug(\"Starting Device\")" + NL + "    start_device(";
  protected final String TEXT_108 = ")";
  protected final String TEXT_109 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument; 
    ImplementationSettings implSettings = templ.getImplSettings(); 
    Implementation impl = templ.getImpl(); 
    SoftPkg softpkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softpkg, implSettings); 
    EList<Uses> uses = softpkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses(); 
    EList<Provides> provides = softpkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides(); 
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath()); 
    String deviceType = softpkg.getDescriptor().getComponent().getComponentType();
    boolean aggregateDevice = false;
    boolean execDevice = false;
    boolean loadableDevice = false;
    boolean havePorts = false;
    boolean workModule = false;
    if (deviceType.contains(RedhawkIdePreferenceConstants.EXECUTABLE_DEVICE.toLowerCase())) { 
        deviceType = "Executable"; execDevice = true;
    } else if (deviceType.contains(RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase())) { 
        deviceType = "Loadable"; loadableDevice = true;
    } else { 
        deviceType = "";
    } 
    for (SupportsInterface inter : softpkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface()) {
        if (inter.getSupportsName().contains(RedhawkIdePreferenceConstants.AGGREGATE_DEVICE)) {
            aggregateDevice = true;
        }
    }
    HashSet<String> nsSet = new HashSet<String>();
    if (uses.size() > 0) {
        havePorts = true;
        for (Uses tempUse : uses) {
           nsSet.add(tempUse.getRepID().split(":")[1].split("/")[0]);
        }
    }
    if (provides.size() > 0) {
        havePorts = true;
        for (Provides tempProvide : provides) {
           nsSet.add(tempProvide.getRepID().split(":")[1].split("/")[0]);
        }
    } 
    for (String file : templ.getSourceFiles()) {
        if ("WorkModule.py".equals(file)) {
            workModule = true;
            break;
        }
    }
    for (Property tempProp : implSettings.getProperties()) {
        if (tempProp.getId().equals("use_workmodule")) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                workModule = true;
                break;
            }
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_2);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_3);
    } 
    stringBuffer.append(TEXT_4);
     
    if (havePorts) { 
        for (String ns : nsSet) {
            String lowerNS = ns.toLowerCase();

    stringBuffer.append(TEXT_5);
    stringBuffer.append(lowerNS);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(lowerNS);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_9);
    
        }
    }

    stringBuffer.append(TEXT_10);
     
    if (havePorts) { 

    stringBuffer.append(TEXT_11);
     
    }
    if (workModule) { 

    stringBuffer.append(TEXT_12);
    
    }

    stringBuffer.append(TEXT_13);
    stringBuffer.append( PREFIX );
    stringBuffer.append(TEXT_14);
     
    // Ports 
    if (havePorts) {
        if (provides.size() > 0) {

    stringBuffer.append(TEXT_15);
    
        }

        // Provides Ports
        for (Provides tempProvide : provides) {
            String intName = tempProvide.getRepID();
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_16);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_20);
     
            // Provides Operations
            for (Operation op : iface.getOperations()) {
                int numParams = op.getParams().size();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_22);
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_23);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_24);
            
                if ("pushPacket".equals(op.getName()) && (workModule)) {        

    stringBuffer.append(TEXT_25);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(numParams == 4 ? "T" : "None");
    stringBuffer.append(TEXT_27);
    stringBuffer.append(numParams == 4 ? "EOS" : "None");
    stringBuffer.append(TEXT_28);
    stringBuffer.append(numParams == 4 ? "streamID" : "None");
    stringBuffer.append(TEXT_29);
    
                }  else { // End if statement pushPacket conditional                

    stringBuffer.append(TEXT_30);
    
                } // End else statement pushPacket

    stringBuffer.append(TEXT_31);
    
            } // end for Provides Operations
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_33);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_35);
    
                } // end if !readonly
            } // end Provides Attributes
        } // end for Provides Ports 
    stringBuffer.append(TEXT_36);
     
        // Uses Ports
        if (uses.size() > 0) {

    stringBuffer.append(TEXT_37);
    
        }
        for (Uses tempUse : uses) {
            String intName = tempUse.getRepID(); 
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            boolean bulkioData = false;
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();
            // Check to see if this is a BULKIO data port
            boolean pushSRIFlag = false;
            boolean pushPacketFlag = false;
            if ("BULKIO".equals(nameSpace)){
                for (Operation op : iface.getOperations()){
                    if ("pushSRI".equals(op.getName())) {
                        pushSRIFlag = true;
                    }
                    if ("pushPacket".equals(op.getName())) {
                        pushPacketFlag = true;
                    }
                }
                bulkioData = (pushSRIFlag && pushPacketFlag);
            } // end if BULKIO namespace

    stringBuffer.append(TEXT_38);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_40);
    
                if (bulkioData){

    stringBuffer.append(TEXT_41);
    
                }

    stringBuffer.append(TEXT_42);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_44);
    
                if (bulkioData) {

    stringBuffer.append(TEXT_45);
    
                }

    stringBuffer.append(TEXT_46);
    
                // Operations (function calls)
                for (Operation op : iface.getOperations()) {
                    boolean returnType = PortHelper.hasReturn(op);

    stringBuffer.append(TEXT_47);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_48);
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_49);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_50);
    
                    if (returnType) {
                        if (op.getReturnType().contains("string")) { 

    stringBuffer.append(TEXT_51);
    
                        } else if (PortHelper.getNumReturns(op) == 1) {

    stringBuffer.append(TEXT_52);
    
                        } else {

    stringBuffer.append(TEXT_53);
    
                        }
                    }
                    if (bulkioData && "pushSRI".equals(op.getName())) {

    stringBuffer.append(TEXT_54);
    
                        boolean first_param = true;
                        for (Param p : op.getParams()) {
                            if (first_param) { 
                                first_param=false;
                            }

    stringBuffer.append(p.getName());
    
                        }
                    }
                    if (bulkioData && "pushPacket".equals(op.getName())) {

    stringBuffer.append(TEXT_55);
    
                    }

    stringBuffer.append(TEXT_56);
    
                    if (returnType) {

    stringBuffer.append(TEXT_57);
    
                    }

    stringBuffer.append(TEXT_58);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_59);
    
                    boolean first_param = true;
                    for (Param p : op.getParams()) {
                        if (first_param) { 
                            first_param=false;
                        } else {

    stringBuffer.append(TEXT_60);
    
                        }

    stringBuffer.append(p.getName());
    
                    }

    stringBuffer.append(TEXT_61);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_62);
    
                    if (returnType) {

    stringBuffer.append(TEXT_63);
    
                    }
                } // end Operations
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_64);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_65);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_66);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_67);
    
                } // end if !readonly
            } // end Provides Attributes
        } // end for  Uses Ports
    } // end Ports

    stringBuffer.append(TEXT_68);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_69);
     
    if (aggregateDevice) {
    stringBuffer.append(TEXT_70);
    
        if ("Executable".equals(deviceType)) {
    stringBuffer.append(deviceType);
     
        } else if ("Loadable".equals(deviceType)) {
    stringBuffer.append(deviceType);
    
        } else {
    stringBuffer.append(TEXT_71);
    
        }
    stringBuffer.append(TEXT_72);
    
    } else {
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_73);
    
    }
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_74);
    
    if (aggregateDevice) {
    stringBuffer.append(TEXT_75);
    }
    stringBuffer.append(TEXT_76);
     
    if (execDevice) {

    stringBuffer.append(TEXT_77);
     
    } else if (loadableDevice) {

    stringBuffer.append(TEXT_78);
     
    } else {

    stringBuffer.append(TEXT_79);
     
    }
    if (aggregateDevice) {

    stringBuffer.append(TEXT_80);
    
    }

    
    if (havePorts) {

    stringBuffer.append(TEXT_81);
    
    }

    stringBuffer.append(TEXT_82);
    
    if (havePorts && (provides.size() > 0)) {

    stringBuffer.append(TEXT_83);
     
        for (Provides tempProvide : provides) {
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, tempProvide.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + tempProvide.getRepID()));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_84);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(tempProvide.getProvidesName());
    stringBuffer.append(TEXT_87);
    stringBuffer.append(tempProvide.getProvidesName());
    stringBuffer.append(TEXT_88);
     
        }
    } // end if has provides ports
    if (havePorts && (uses.size() > 0)) {

    stringBuffer.append(TEXT_89);
     
        for (Uses tempUse : uses) { 
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, tempUse.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + tempUse.getRepID()));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_90);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(tempUse.getUsesName());
    stringBuffer.append(TEXT_93);
    stringBuffer.append(tempUse.getUsesName());
    stringBuffer.append(TEXT_94);
    
        }
    } // end if have uses ports

    stringBuffer.append(TEXT_95);
    
    if (workModule) {

    stringBuffer.append(TEXT_96);
    
    }

    stringBuffer.append(TEXT_97);
     
    if (workModule) { 

    stringBuffer.append(TEXT_98);
     
    } else {

    stringBuffer.append(TEXT_99);
    
    } 
    

    stringBuffer.append(TEXT_100);
    
    for (Simple simple : softpkg.getPropertyFile().getProperties().getSimple()) { 
        if ("allocation".equals(simple.getKind().get(0).getType().getName())) {
            if ("external".equals(simple.getAction().getType().getName())) {

    stringBuffer.append(TEXT_101);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_102);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_103);
    
            }
        }
        if ("readwrite".equals(simple.getMode().getName())) {

    stringBuffer.append(TEXT_104);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_105);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_106);
     
        }
    } // end for simple properties


    stringBuffer.append(TEXT_107);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(TEXT_109);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE