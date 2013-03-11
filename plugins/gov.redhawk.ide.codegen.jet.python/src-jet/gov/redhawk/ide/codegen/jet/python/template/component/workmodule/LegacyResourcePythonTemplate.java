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
package gov.redhawk.ide.codegen.jet.python.template.component.workmodule;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.scd.Provides;
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
public class LegacyResourcePythonTemplate
{

  protected static String nl;
  public static synchronized LegacyResourcePythonTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    LegacyResourcePythonTemplate result = new LegacyResourcePythonTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python ";
  protected final String TEXT_2 = "xmpy";
  protected final String TEXT_3 = NL + "#" + NL + "from ossie.cf import CF, CF__POA" + NL + "from ossie.resource import Resource, start_component" + NL + "from omniORB import any";
  protected final String TEXT_4 = NL + "from ";
  protected final String TEXT_5 = ".";
  protected final String TEXT_6 = "Interfaces import ";
  protected final String TEXT_7 = ", ";
  protected final String TEXT_8 = "__POA ";
  protected final String TEXT_9 = NL + "import os, time, signal, copy" + NL + "import logging";
  protected final String TEXT_10 = NL + "import threading ";
  protected final String TEXT_11 = NL + "import WorkModule";
  protected final String TEXT_12 = NL + NL + NL + "from ";
  protected final String TEXT_13 = "Props import PROPERTIES" + NL;
  protected final String TEXT_14 = NL + NL + "'''provides port(s)'''";
  protected final String TEXT_15 = NL + NL + "class ";
  protected final String TEXT_16 = "_";
  protected final String TEXT_17 = "_in_i(";
  protected final String TEXT_18 = "__POA.";
  protected final String TEXT_19 = "):" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name";
  protected final String TEXT_20 = NL + NL + "    def ";
  protected final String TEXT_21 = "(self";
  protected final String TEXT_22 = ", ";
  protected final String TEXT_23 = "):";
  protected final String TEXT_24 = "        " + NL + "        # Queue the incoming data to be later worked on in the Workmodule" + NL + "        # TODO:" + NL + "        self.parent.work_mod.queueData(";
  protected final String TEXT_25 = ", ";
  protected final String TEXT_26 = ", ";
  protected final String TEXT_27 = ", ";
  protected final String TEXT_28 = ")";
  protected final String TEXT_29 = NL + "        # TODO:";
  protected final String TEXT_30 = NL + "        pass";
  protected final String TEXT_31 = NL + NL + "    def _get_";
  protected final String TEXT_32 = "(self):" + NL + "        # TODO:" + NL + "        pass";
  protected final String TEXT_33 = NL + NL + "    def _set_";
  protected final String TEXT_34 = "(self, data):" + NL + "        # TODO:" + NL + "        pass" + NL;
  protected final String TEXT_35 = NL + NL + "'''uses port(s)'''";
  protected final String TEXT_36 = NL + NL + "class ";
  protected final String TEXT_37 = "_";
  protected final String TEXT_38 = "_out_i(CF__POA.Port):" + NL + "    def __init__(self, parent, name):" + NL + "        self.parent = parent" + NL + "        self.name = name" + NL + "        self.outPorts = {}";
  protected final String TEXT_39 = NL + "        self.refreshSRI = False" + NL + "        self.defaultStreamSRI = BULKIO.StreamSRI(1, 0.0, 0.001, 1, 200, 0.0, 0.001, 1, 1, \"sampleStream\", [])";
  protected final String TEXT_40 = NL + "        self.port_lock = threading.Lock()" + NL + "" + NL + "    def connectPort(self, connection, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        port = connection._narrow(";
  protected final String TEXT_41 = "__POA.";
  protected final String TEXT_42 = ")" + NL + "        self.outPorts[str(connectionId)] = port";
  protected final String TEXT_43 = NL + "        self.refreshSRI = True";
  protected final String TEXT_44 = NL + "        self.port_lock.release()" + NL + "" + NL + "    def disconnectPort(self, connectionId):" + NL + "        self.port_lock.acquire()" + NL + "        if self.outPorts.has_key(str(connectionId)):" + NL + "            self.outPorts.pop(str(connectionId), None)" + NL + "        else:" + NL + "            self.port_lock.release()" + NL + "            return" + NL + "        self.port_lock.release()";
  protected final String TEXT_45 = NL + "    def ";
  protected final String TEXT_46 = "(self";
  protected final String TEXT_47 = ", ";
  protected final String TEXT_48 = "):";
  protected final String TEXT_49 = NL + "        retVal = \"\"";
  protected final String TEXT_50 = NL + "        retVal = None";
  protected final String TEXT_51 = NL + "        retVal = []";
  protected final String TEXT_52 = NL + "        self.defaultStreamSRI = ";
  protected final String TEXT_53 = NL + "        if self.refreshSRI:" + NL + "            self.pushSRI(self.defaultStreamSRI)";
  protected final String TEXT_54 = NL + "        self.port_lock.acquire()" + NL + "        try:" + NL + "            for port in self.outPorts:" + NL + "                if self.outPorts[port] != None:";
  protected final String TEXT_55 = "retVal =";
  protected final String TEXT_56 = " self.outPorts[port].";
  protected final String TEXT_57 = "(";
  protected final String TEXT_58 = ", ";
  protected final String TEXT_59 = ")" + NL + "        except:" + NL + "            self.parent._log.exception(\"The call to ";
  protected final String TEXT_60 = " failed\")" + NL + "        self.port_lock.release()";
  protected final String TEXT_61 = NL + "        return retVal ";
  protected final String TEXT_62 = NL + "    def _get_";
  protected final String TEXT_63 = "(self):";
  protected final String TEXT_64 = NL + "        retVal = \"\"";
  protected final String TEXT_65 = NL + "        retVal = None";
  protected final String TEXT_66 = NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            try:" + NL + "                for connId, port in self.outPorts.items():" + NL + "                    if port != None:" + NL + "                        retVal = port.";
  protected final String TEXT_67 = "()" + NL + "            except Exception:" + NL + "                self.parent._log.exception(\"The call to ";
  protected final String TEXT_68 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL + "" + NL + "        return retVal" + NL + " ";
  protected final String TEXT_69 = NL + "    def _set_";
  protected final String TEXT_70 = "(self, data):" + NL + "        self.port_lock.acquire()" + NL + "" + NL + "        try:    " + NL + "            try:" + NL + "                for connId, port in self.outPorts.items():" + NL + "                    if port != None:" + NL + "                        port.";
  protected final String TEXT_71 = "(data)" + NL + "            except Exception:" + NL + "                self.parent._log.exception(\"The call to ";
  protected final String TEXT_72 = " failed on port %s connection %s instance %s\", self.name, connId, port)" + NL + "        finally:" + NL + "            self.port_lock.release()" + NL;
  protected final String TEXT_73 = NL + NL + "class ";
  protected final String TEXT_74 = "_i(CF__POA.Resource, Resource):";
  protected final String TEXT_75 = NL + "    \"\"\"";
  protected final String TEXT_76 = "\"\"\"";
  protected final String TEXT_77 = NL + "    \"\"\"<DESCRIPTION GOES HERE>\"\"\"";
  protected final String TEXT_78 = NL + NL + "    # The signal to send and the number of seconds (max) to wait" + NL + "    # for the process to actually exit before trying the next signals." + NL + "    # None means wait until the process dies (which we have to do to" + NL + "    # avoid creating zombie processes" + NL + "    STOP_SIGNALS = ((signal.SIGINT, 1), (signal.SIGTERM, 5), (signal.SIGKILL, None))" + NL + "    def __init__(self, identifier, execparams):" + NL + "        loggerName = execparams['NAME_BINDING'].replace('/', '.')" + NL + "        Resource.__init__(self, identifier, execparams, propertydefs=PROPERTIES, loggerName=loggerName)";
  protected final String TEXT_79 = NL + "        self._props[\"execparams\"] = \" \".join([\"%s %s\" % x for x in execparams.items()])";
  protected final String TEXT_80 = NL + "        self._pid = None" + NL;
  protected final String TEXT_81 = NL + "        self.ports = {}";
  protected final String TEXT_82 = NL + NL + "        '''provides'''";
  protected final String TEXT_83 = NL + "        portRef = ";
  protected final String TEXT_84 = "_";
  protected final String TEXT_85 = "_in_i(self, \"";
  protected final String TEXT_86 = "\")" + NL + "        self.ports['";
  protected final String TEXT_87 = "'] = portRef";
  protected final String TEXT_88 = "    " + NL + "" + NL + "        '''uses'''";
  protected final String TEXT_89 = NL + "        portRef = ";
  protected final String TEXT_90 = "_";
  protected final String TEXT_91 = "_out_i(self, \"";
  protected final String TEXT_92 = "\")" + NL + "        self.ports['";
  protected final String TEXT_93 = "'] = portRef" + NL;
  protected final String TEXT_94 = NL + "        self.work_mod = WorkModule.WorkClass(self)";
  protected final String TEXT_95 = NL + NL + "    def getPort(self, name):" + NL + "        if self.ports.has_key(name):" + NL + "            return self.ports[str(name)]._this()" + NL + "        raise CF.PortSupplier.UnknownPort()" + NL + "        ";
  protected final String TEXT_96 = NL + "    def releaseObject(self):" + NL + "        self.work_mod.Release()";
  protected final String TEXT_97 = NL + NL + "    def start(self):" + NL + "        # This is a framework-level start call. This function is called only if this component" + NL + "        #    happens to be the assembly controller (or the assembly controller is written such that" + NL + "        #    it calls this component's start function" + NL + "        # The vast majority of components won't have their start function called" + NL + "        # TODO:" + NL + "        pass" + NL + "        " + NL + "    def stop(self):" + NL + "        # This is a framework-level stop call. This function is called only if this component" + NL + "        #    happens to be the assembly controller (or the assembly controller is written such that" + NL + "        #    it calls this component's stop function" + NL + "        # The vast majority of components won't have their stop function called" + NL + "        # TODO:" + NL + "        pass" + NL + "    " + NL + "if __name__ == '__main__':" + NL + "    logging.getLogger().setLevel(logging.DEBUG)" + NL + "    start_component(";
  protected final String TEXT_98 = "_i)";

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
    Properties properties = softpkg.getPropertyFile().getProperties();
    Boolean havePorts = false;
    Boolean workModule = false;
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
        if ("use_old_style".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                workModule = true;
                break;
            }
        }
    }

    stringBuffer.append(TEXT_1);
        if (implSettings.getGeneratorId().contains("XMPY")) {
    stringBuffer.append(TEXT_2);
        }    
    stringBuffer.append(TEXT_3);
     
    if (havePorts) { 
        for (String ns : nsSet) {
            String lowerNS = ns.toLowerCase();

    stringBuffer.append(TEXT_4);
    stringBuffer.append(lowerNS);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(lowerNS);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_8);
    
        }
    } 

    stringBuffer.append(TEXT_9);
     
    if (havePorts) { 

    stringBuffer.append(TEXT_10);
    
    }
    if (workModule) { 

    stringBuffer.append(TEXT_11);
     
    }

    stringBuffer.append(TEXT_12);
    stringBuffer.append( PREFIX );
    stringBuffer.append(TEXT_13);
     
    // Ports 
    if (havePorts) {
        if (provides.size() > 0) {

    stringBuffer.append(TEXT_14);
    
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

    stringBuffer.append(TEXT_15);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_19);
     
            // Provides Operations
            for (Operation op : iface.getOperations()) {
                int numParams = op.getParams().size();

    stringBuffer.append(TEXT_20);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_21);
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_22);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_23);
            
                if ("pushPacket".equals(op.getName()) && workModule) {        

    stringBuffer.append(TEXT_24);
    stringBuffer.append(op.getParams().get(0).getName());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(numParams == 4 ? "T" : "None");
    stringBuffer.append(TEXT_26);
    stringBuffer.append(numParams == 4 ? "EOS" : "None");
    stringBuffer.append(TEXT_27);
    stringBuffer.append(numParams == 4 ? "streamID" : "None");
    stringBuffer.append(TEXT_28);
    
                }  else { // End if statement pushPacket conditional                

    stringBuffer.append(TEXT_29);
    
                } // End else statement pushPacket

    stringBuffer.append(TEXT_30);
    
            } // end Provides Operations
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_31);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_32);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_33);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_34);
    
                } // end if !readonly
            } // end Provides Attributes
        } // end for Provides Ports
        
        // Uses Ports
        if (uses.size() > 0) {

    stringBuffer.append(TEXT_35);
    
        }
        for (Uses tempUse : uses) {
            String intName = tempUse.getRepID(); 
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, intName.split(":")[1], true);
            boolean bulkioData = false;
            if (iface == null) {
              	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + intName));
         	}
         	System.out.println("attrs: " + iface.getAttributes());
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();
            // Check to see if this is a BULKIO data port
            boolean pushSRIFlag = false;
            boolean pushPacketFlag = false;
            if ("BULKIO".equals(nameSpace)){
                for (Operation op : iface.getOperations()){
                    if ("pushSRI".equals(op.getName())){
                        pushSRIFlag = true;
                    }
                    if ("pushPacket".equals(op.getName())){
                        pushPacketFlag = true;
                    }
                }     
                bulkioData = (pushSRIFlag && pushPacketFlag);
            }

    stringBuffer.append(TEXT_36);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_38);
    
            if (bulkioData){

    stringBuffer.append(TEXT_39);
    
            }

    stringBuffer.append(TEXT_40);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_42);
    
            if (bulkioData) {

    stringBuffer.append(TEXT_43);
    
            }

    stringBuffer.append(TEXT_44);
    
            // Operations (function calls)
            for (Operation op : iface.getOperations()) {
                boolean returnType = PortHelper.hasReturn(op);

    stringBuffer.append(TEXT_45);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_46);
    
    for (Param p : op.getParams()){
    stringBuffer.append(TEXT_47);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_48);
    
                if (returnType) {
                    if (op.getReturnType().contains("string")) { 

    stringBuffer.append(TEXT_49);
    
                    } else if (PortHelper.getNumReturns(op) == 1) {

    stringBuffer.append(TEXT_50);
    
                    } else {

    stringBuffer.append(TEXT_51);
    
                    }
                }
                if (bulkioData && "pushSRI".equals(op.getName())) {

    stringBuffer.append(TEXT_52);
    
                    boolean first_param = true;
                    for (Param p : op.getParams()){
                        if (first_param) { 
                            first_param=false;
                        }

    stringBuffer.append(p.getName());
    
                    } 
                } else if (bulkioData && "pushPacket".equals(op.getName())) {

    stringBuffer.append(TEXT_53);
    
                } // end if pushPacket

    stringBuffer.append(TEXT_54);
    
                if (returnType) {

    stringBuffer.append(TEXT_55);
    
                }

    stringBuffer.append(TEXT_56);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_57);
    
                boolean first_param = true;
                for (Param p : op.getParams()) {
                    if (first_param) { 
                        first_param=false;
                    } else {

    stringBuffer.append(TEXT_58);
    
                    }

    stringBuffer.append(p.getName());
    
                }

    stringBuffer.append(TEXT_59);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_60);
     
                if (returnType) {

    stringBuffer.append(TEXT_61);
    
                }
            } /* end for operations */
            for (Attribute op : iface.getAttributes()) {

    stringBuffer.append(TEXT_62);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_63);
    
                if (op.getReturnType().contains("string")) { 

    stringBuffer.append(TEXT_64);
    
                } else {

    stringBuffer.append(TEXT_65);
    
                }

    stringBuffer.append(TEXT_66);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_67);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_68);
    
                if (!op.isReadonly()) {

    stringBuffer.append(TEXT_69);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_70);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_71);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_72);
    
                }
            } /* end for attributes */
        } // end for Uses Ports
    } // end if Ports 

    stringBuffer.append(TEXT_73);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_74);
     
    if (softpkg.getDescription() != null) { 

    stringBuffer.append(TEXT_75);
    stringBuffer.append(softpkg.getDescription());
    stringBuffer.append(TEXT_76);
     
    } else {

    stringBuffer.append(TEXT_77);
     
    }

    stringBuffer.append(TEXT_78);
    
    for (Simple simple : properties.getSimple()) {
        if (simple.getKind().contains("execparam")) { 

    stringBuffer.append(TEXT_79);
    
        }
    }

    stringBuffer.append(TEXT_80);
    
    if ((provides.size() > 0) || (uses.size() > 0)) {

    stringBuffer.append(TEXT_81);
    
    }
    if (havePorts && (provides.size() > 0)) {

    stringBuffer.append(TEXT_82);
     
        for (Provides tempProvide : provides) { 
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, tempProvide.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + tempProvide.getRepID()));
            }
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();
 
    stringBuffer.append(TEXT_83);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(tempProvide.getProvidesName());
    stringBuffer.append(TEXT_86);
    stringBuffer.append(tempProvide.getProvidesName());
    stringBuffer.append(TEXT_87);
     
        }
    } // end if have provides ports
    
    if (havePorts && (uses.size() > 0)) {

    stringBuffer.append(TEXT_88);
     
        for (Uses tempUse : uses) { 
            Interface iface = IdlUtil.getInstance().getInterface(search_paths, tempUse.getRepID().split(":")[1], true);
            if (iface == null) {
            	throw new CoreException(new Status(IStatus.ERROR, PythonJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + tempUse.getRepID()));
        	}
            String nameSpace = iface.getNameSpace();
            String interfaceName = iface.getName();

    stringBuffer.append(TEXT_89);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(tempUse.getUsesName());
    stringBuffer.append(TEXT_92);
    stringBuffer.append(tempUse.getUsesName());
    stringBuffer.append(TEXT_93);
    
        }
    } // end if have uses ports
    
    if (workModule) { 

    stringBuffer.append(TEXT_94);
    
    }

    stringBuffer.append(TEXT_95);
    
    if (workModule) {

    stringBuffer.append(TEXT_96);
    
    }

    stringBuffer.append(TEXT_97);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_98);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE