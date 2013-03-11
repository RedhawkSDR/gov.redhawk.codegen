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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.IPortTemplateDesc;
import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.PortRepToGeneratorMap;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.ports.PropertyChangeEventPortTemplate;
import gov.redhawk.ide.codegen.python.utils.PropertyToPython;
import gov.redhawk.ide.codegen.python.utils.SimpleToPython;
import gov.redhawk.ide.codegen.python.utils.SimpleSequenceToPython;
import gov.redhawk.ide.codegen.python.utils.StructToPython;
import gov.redhawk.ide.codegen.python.utils.StructSequenceToPython;
import gov.redhawk.ide.codegen.python.utils.StructValues;
import gov.redhawk.ide.codegen.python.utils.PortHelper;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.sca.util.StringUtil;
import gov.redhawk.sca.util.StringUpdateStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.emf.common.util.EList;
import gov.redhawk.model.sca.util.ModelUtil;

/**
 * @generated
 */
public class ResourceBaseTemplate
{

  protected static String nl;
  public static synchronized ResourceBaseTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceBaseTemplate result = new ResourceBaseTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python" + NL + "#" + NL + "# AUTO-GENERATED CODE.  DO NOT MODIFY!";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = "#" + NL + "# Source: ";
  protected final String TEXT_4 = NL + "# Generated on: ";
  protected final String TEXT_5 = NL + "# ";
  protected final String TEXT_6 = NL + "# ";
  protected final String TEXT_7 = NL + "# ";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "from ossie.cf import CF, CF__POA" + NL + "from ossie.utils import uuid" + NL;
  protected final String TEXT_10 = NL + "from ossie.resource import Resource";
  protected final String TEXT_11 = NL + "from ossie.device import ";
  protected final String TEXT_12 = "Device ";
  protected final String TEXT_13 = ", AggregateDevice";
  protected final String TEXT_14 = NL + "from ossie.properties import simple_property";
  protected final String TEXT_15 = NL + "from ossie.properties import simpleseq_property";
  protected final String TEXT_16 = NL + "from ossie.properties import struct_property";
  protected final String TEXT_17 = NL + "from ossie.properties import structseq_property";
  protected final String TEXT_18 = NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "from ossie.resource import usesport, providesport" + NL;
  protected final String TEXT_21 = NL + "from ossie.cf import ExtendedCF" + NL + "from omniORB import CORBA" + NL + "import struct #@UnresolvedImport";
  protected final String TEXT_22 = NL + "from ossie.utils import uuid";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "from ";
  protected final String TEXT_25 = " ";
  protected final String TEXT_26 = "import ";
  protected final String TEXT_27 = ", ";
  protected final String TEXT_28 = "__POA #@UnusedImport ";
  protected final String TEXT_29 = NL + "import Queue, copy, time, threading" + NL + "" + NL + "class ";
  protected final String TEXT_30 = "_base(CF__POA.Resource, Resource):" + NL + "        def __init__(self, identifier, execparams):" + NL + "            loggerName = execparams['NAME_BINDING'].replace('/', '.')" + NL + "            Resource.__init__(self, identifier, execparams)" + NL + "" + NL + "        ######################################################################" + NL + "        # PORTS" + NL + "        # " + NL + "        # DO NOT ADD PORTS HERE.  You can add ports in your derived class" + NL + "        # or in the .scd file." + NL + "        ";
  protected final String TEXT_31 = NL + "        # Typical usage:" + NL + "        #   1. Create a class that implements the port" + NL + "        #       class Port";
  protected final String TEXT_32 = "(";
  protected final String TEXT_33 = ")" + NL + "        #" + NL + "        #   2. In your derived classes initialize instantiate the implementation" + NL + "        #       self.port_";
  protected final String TEXT_34 = " = Port";
  protected final String TEXT_35 = "()" + NL + "        port_";
  protected final String TEXT_36 = " = providesport(name=\"";
  protected final String TEXT_37 = "\"," + NL + "                                        repid=\"";
  protected final String TEXT_38 = "\"," + NL + "                                        type_=\"";
  protected final String TEXT_39 = "\"";
  protected final String TEXT_40 = "control\"";
  protected final String TEXT_41 = "," + NL + "                                        )" + NL + "        ";
  protected final String TEXT_42 = NL + NL + "        # Typical usage:" + NL + "        #   1. Create a class that implements the port" + NL + "        #       class Port";
  protected final String TEXT_43 = "(CF__POA.Port))" + NL + "        #" + NL + "        #   2. In your derived classes initialize instantiate the implementation" + NL + "        #       self.port_";
  protected final String TEXT_44 = " = Port";
  protected final String TEXT_45 = "()" + NL + "        port_";
  protected final String TEXT_46 = " = usesport(name=\"";
  protected final String TEXT_47 = "\"," + NL + "                                        repid=\"";
  protected final String TEXT_48 = "\"," + NL + "                                        type_=\"";
  protected final String TEXT_49 = "\"";
  protected final String TEXT_50 = "control\"";
  protected final String TEXT_51 = "," + NL + "                                       )";
  protected final String TEXT_52 = "        " + NL + "" + NL + "        ######################################################################" + NL + "        # PROPERTIES" + NL + "        # " + NL + "        # DO NOT ADD PROPS HERE.  You can add properties in your derived class" + NL + "        # or in the .prf file.";
  protected final String TEXT_53 = "       ";
  protected final String TEXT_54 = NL + "        ";
  protected final String TEXT_55 = " = simple_property(";
  protected final String TEXT_56 = ",";
  protected final String TEXT_57 = NL + "                                          ";
  protected final String TEXT_58 = ", ";
  protected final String TEXT_59 = NL + "                                          ";
  protected final String TEXT_60 = ",";
  protected final String TEXT_61 = NL + "                                          defvalue=";
  protected final String TEXT_62 = ",";
  protected final String TEXT_63 = NL + "                                          ";
  protected final String TEXT_64 = ", ";
  protected final String TEXT_65 = NL + "                                          ";
  protected final String TEXT_66 = ",";
  protected final String TEXT_67 = NL + "                                          ";
  protected final String TEXT_68 = ",";
  protected final String TEXT_69 = NL + "                                          ";
  protected final String TEXT_70 = ",";
  protected final String TEXT_71 = NL + "                                          ";
  protected final String TEXT_72 = " ";
  protected final String TEXT_73 = NL + "                                          )";
  protected final String TEXT_74 = " ";
  protected final String TEXT_75 = NL + "        ";
  protected final String TEXT_76 = " = simpleseq_property(";
  protected final String TEXT_77 = ",";
  protected final String TEXT_78 = NL + "                                          ";
  protected final String TEXT_79 = ", ";
  protected final String TEXT_80 = "  ";
  protected final String TEXT_81 = NL + "                                          ";
  protected final String TEXT_82 = ",";
  protected final String TEXT_83 = NL + "                                          ";
  protected final String TEXT_84 = ",";
  protected final String TEXT_85 = NL + "                                          ";
  protected final String TEXT_86 = ", ";
  protected final String TEXT_87 = NL + "                                          ";
  protected final String TEXT_88 = ",";
  protected final String TEXT_89 = NL + "                                          ";
  protected final String TEXT_90 = ",";
  protected final String TEXT_91 = NL + "                                          ";
  protected final String TEXT_92 = ",";
  protected final String TEXT_93 = NL + "                                          ";
  protected final String TEXT_94 = " ";
  protected final String TEXT_95 = NL + "                                          )";
  protected final String TEXT_96 = NL + "        class ";
  protected final String TEXT_97 = "(object):";
  protected final String TEXT_98 = NL + "            ";
  protected final String TEXT_99 = " = simple_property(";
  protected final String TEXT_100 = ",";
  protected final String TEXT_101 = NL + "                                          ";
  protected final String TEXT_102 = ", ";
  protected final String TEXT_103 = NL + "                                          ";
  protected final String TEXT_104 = ",";
  protected final String TEXT_105 = NL + "                                          defvalue=";
  protected final String TEXT_106 = ",";
  protected final String TEXT_107 = NL + "                                          )";
  protected final String TEXT_108 = NL + "        ";
  protected final String TEXT_109 = NL + "            ";
  protected final String TEXT_110 = NL + "                self.";
  protected final String TEXT_111 = " = ";
  protected final String TEXT_112 = NL + "        " + NL + "            def __init__(self, **kw):" + NL + "                \"\"\"Construct an initialized instance of this struct definition\"\"\"" + NL + "                for attrname, classattr in type(self).__dict__.items():" + NL + "                    if type(classattr) == simple_property:" + NL + "                        classattr.initialize(self)" + NL + "                for k,v in kw.items():" + NL + "                    setattr(self,k,v)";
  protected final String TEXT_113 = NL + NL + "            def __str__(self):" + NL + "                \"\"\"Return a string representation of this structure\"\"\"" + NL + "                d = {}";
  protected final String TEXT_114 = NL + "                d[\"";
  protected final String TEXT_115 = "\"] = self.";
  protected final String TEXT_116 = NL + "                return str(d)" + NL + "" + NL + "            def getId(self):" + NL + "                return \"";
  protected final String TEXT_117 = "\"" + NL + "" + NL + "            def isStruct(self):" + NL + "                return True" + NL + "" + NL + "            def getMembers(self):" + NL + "                return [";
  protected final String TEXT_118 = "(\"";
  protected final String TEXT_119 = "\",self.";
  protected final String TEXT_120 = ")";
  protected final String TEXT_121 = ",";
  protected final String TEXT_122 = "]" + NL;
  protected final String TEXT_123 = NL + "        ";
  protected final String TEXT_124 = NL + "        ";
  protected final String TEXT_125 = " = struct_property(";
  protected final String TEXT_126 = ",";
  protected final String TEXT_127 = NL + "                                          ";
  protected final String TEXT_128 = ", ";
  protected final String TEXT_129 = NL + "                                          ";
  protected final String TEXT_130 = ",";
  protected final String TEXT_131 = NL + "                                          ";
  protected final String TEXT_132 = ",";
  protected final String TEXT_133 = NL + "                                          ";
  protected final String TEXT_134 = ",";
  protected final String TEXT_135 = NL + "                                          ";
  protected final String TEXT_136 = " ";
  protected final String TEXT_137 = NL + "                                          )";
  protected final String TEXT_138 = NL + "                ";
  protected final String TEXT_139 = NL + "        ";
  protected final String TEXT_140 = " = structseq_property(";
  protected final String TEXT_141 = ",";
  protected final String TEXT_142 = NL + "                                          ";
  protected final String TEXT_143 = ", ";
  protected final String TEXT_144 = NL + "                                          ";
  protected final String TEXT_145 = ",                          ";
  protected final String TEXT_146 = NL + "                                          ";
  protected final String TEXT_147 = ",";
  protected final String TEXT_148 = NL + "                                          ";
  protected final String TEXT_149 = ",";
  protected final String TEXT_150 = NL + "                                          ";
  protected final String TEXT_151 = ",";
  protected final String TEXT_152 = NL + "                                          ";
  protected final String TEXT_153 = " ";
  protected final String TEXT_154 = NL + "                                          )";
  protected final String TEXT_155 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	Date date = new Date(System.currentTimeMillis());

    stringBuffer.append(TEXT_1);
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    List<IPath> search_paths = templ.getSearchPaths();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    Properties properties = softPkg.getPropertyFile().getProperties();
	String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    String deviceType = softPkg.getDescriptor().getComponent().getComponentType();
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    HashSet<String> usesReps = new HashSet<String>();
    HashSet<String> providesReps = new HashSet<String>();
    Uses usePort = null;
    Provides proPort  = null;
    boolean havePorts = false;
    boolean generateGetters = false;
    boolean autoStart = false;
    boolean aggregateDevice = false;
    boolean execDevice = false;
    boolean loadableDevice = false;
    boolean isResource = true;
    boolean includePropertyChange = false;
    String OSSIENAME = "Resource";
    List<String> nsList = new ArrayList<String>();
    PortHelper portHelper = new PortHelper();
    TemplateParameter portTempl = new TemplateParameter(impl, implSettings, search_paths);

    if (softPkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase())) {
        isResource = false;
        OSSIENAME = "Device";
    }

    for (mil.jpeojtrs.sca.scd.Interface inter : softPkg.getDescriptor().getComponent().getInterfaces().getInterface()) {
        if (inter.getName().contains(RedhawkIdePreferenceConstants.DEVICE)) {
            deviceType = "";
            break;
        }
    }

    for (mil.jpeojtrs.sca.scd.Interface inter : softPkg.getDescriptor().getComponent().getInterfaces().getInterface()) {
        if (inter.getName().contains(RedhawkIdePreferenceConstants.LOADABLE_DEVICE)) {
            deviceType = "Loadable"; 
            loadableDevice = true;
            OSSIENAME = "LoadableDevice";
            break;
        }
    }

    for (mil.jpeojtrs.sca.scd.Interface inter : softPkg.getDescriptor().getComponent().getInterfaces().getInterface()) {
        if (inter.getName().contains(RedhawkIdePreferenceConstants.EXECUTABLE_DEVICE)) {
            deviceType = "Executable"; 
            execDevice = true;
            OSSIENAME = "ExecutableDevice";
            break;
        }
    }

    for (mil.jpeojtrs.sca.scd.Interface inter : softPkg.getDescriptor().getComponent().getInterfaces().getInterface()) {
        if (inter.getName().contains(RedhawkIdePreferenceConstants.AGGREGATE_DEVICE)) {
            aggregateDevice = true;
            break;
        }
    }

    if (uses.size() > 0) {
        havePorts = true;
        for (Uses tempUse : uses) {
            if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(tempUse.getRepID()) 
                    && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(tempUse.getUsesName())) {
                includePropertyChange = true;
                continue;
            }
            usesReps.add(tempUse.getRepID());
            String[] repParts = tempUse.getRepID().split(":")[1].split("/");
            String ns = repParts[repParts.length - 2];
            if (!nsList.contains(ns)) {
                nsList.add(ns);
            }
        }
    }
    if (provides.size() > 0) {
        havePorts = true;
        for (Provides tempProvide : provides) {
            providesReps.add(tempProvide.getRepID());
            String[] repParts = tempProvide.getRepID().split(":")[1].split("/");
            String ns = repParts[repParts.length - 2];
            if (!nsList.contains(ns)) {
                nsList.add(ns);
            }
        }
    }

    boolean includePorts = havePorts || includePropertyChange;
    Collections.sort(nsList);

    HashMap<String, IScaPortCodegenTemplate> portMap = new HashMap<String, IScaPortCodegenTemplate>();
    for (PortRepToGeneratorMap p : implSettings.getPortGenerators()) {
        IPortTemplateDesc template = CodegenUtil.getPortTemplate(p.getGenerator(), null);
        if (template != null) {
               portMap.put(p.getRepId(), template.getTemplate());
        }
    }

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
    stringBuffer.append(TEXT_9);
    if (isResource) {
    stringBuffer.append(TEXT_10);
    } else {
    stringBuffer.append(TEXT_11);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_12);
     if (aggregateDevice) {
    stringBuffer.append(TEXT_13);
    }
    }
     if (!properties.getSimple().isEmpty() || !properties.getStruct().isEmpty() || !properties.getStructSequence().isEmpty()) { 
    stringBuffer.append(TEXT_14);
    }
     if (!properties.getSimpleSequence().isEmpty()) { 
    stringBuffer.append(TEXT_15);
    }
     if (!properties.getStruct().isEmpty()) { 
    stringBuffer.append(TEXT_16);
    }
     if (!properties.getStructSequence().isEmpty()) { 
    stringBuffer.append(TEXT_17);
    }
    stringBuffer.append(TEXT_18);
     
    if (includePorts) {

    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    
    boolean includedUUID = false;
    boolean extraIncludes = false;
    for (String ns : nsList) {
        boolean isBulkio = "BULKIO".equalsIgnoreCase(ns);
        if ("CF".equalsIgnoreCase(ns)) {
            continue;
        }

        if (isBulkio && !extraIncludes) {

    stringBuffer.append(TEXT_21);
    
            extraIncludes = true;
        }
        if (isBulkio && !isResource && !includedUUID) {

    stringBuffer.append(TEXT_22);
    
            includedUUID = true;
        }

        String importNS = ns;
        if (isBulkio || "REDHAWK".equalsIgnoreCase(ns)) {
            importNS = ns.toLowerCase() + "." + ns.toLowerCase() + "Interfaces";
        } else {
            importNS = "redhawk." + ns.toLowerCase() + "Interfaces";
        }

    stringBuffer.append(TEXT_23);
    if (!ns.startsWith("Cos")) {
    stringBuffer.append(TEXT_24);
    stringBuffer.append(importNS);
    stringBuffer.append(TEXT_25);
    }
    stringBuffer.append(TEXT_26);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(ns);
    stringBuffer.append(TEXT_28);
    
    }

    
    }

    stringBuffer.append(TEXT_29);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_30);
     if (havePorts) {
        if (provides.size() > 0) {
            for (Provides provide : provides) {
		String portName = portHelper.validateName(provide.getProvidesName());

    stringBuffer.append(TEXT_31);
    stringBuffer.append(PortHelper.nameToCamelCase(provide.getProvidesName()));
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PortHelper.idlToClassName(provide.getRepID()));
    stringBuffer.append(TEXT_33);
    stringBuffer.append(portName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PortHelper.nameToCamelCase(provide.getProvidesName()));
    stringBuffer.append(TEXT_35);
    stringBuffer.append(portName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(provide.getProvidesName());
    stringBuffer.append(TEXT_37);
    stringBuffer.append(provide.getRepID());
    stringBuffer.append(TEXT_38);
    if (!provide.getPortType().isEmpty()) {
    stringBuffer.append(provide.getPortType().get(0).getType().getName());
    stringBuffer.append(TEXT_39);
     } else {
    stringBuffer.append(TEXT_40);
    }
    stringBuffer.append(TEXT_41);
    
            }
        }
        
        if (uses.size() > 0) {
            for (Uses use : uses) {
		String portName = portHelper.validateName(use.getUsesName());

    stringBuffer.append(TEXT_42);
    stringBuffer.append(PortHelper.nameToCamelCase(use.getUsesName()));
    stringBuffer.append(TEXT_43);
    stringBuffer.append(portName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(PortHelper.nameToCamelCase(use.getUsesName()));
    stringBuffer.append(TEXT_45);
    stringBuffer.append(portName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_47);
    stringBuffer.append(use.getRepID());
    stringBuffer.append(TEXT_48);
    if (!use.getPortType().isEmpty()) {
    stringBuffer.append(use.getPortType().get(0).getType().getName());
    stringBuffer.append(TEXT_49);
     } else {
    stringBuffer.append(TEXT_50);
    }
    stringBuffer.append(TEXT_51);
        
            }
        }
    }

    stringBuffer.append(TEXT_52);
      
    List<String> simpleNames = new ArrayList<String>();
    for (Simple tempSimple : properties.getSimple()) {
        String simpleName = SimpleToPython.getName(tempSimple);
        String simple = StringUtil.defaultCreateUniqueString(simpleName, simpleNames);
        simpleNames.add(simple); 
    stringBuffer.append(TEXT_53);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(simple);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(PropertyToPython.getPythonId(tempSimple.getId()));
    stringBuffer.append(TEXT_56);
     if (tempSimple.getName() != null ) { 
    stringBuffer.append(TEXT_57);
    stringBuffer.append(PropertyToPython.getPythonName(tempSimple.getName()));
    stringBuffer.append(TEXT_58);
     } 
    stringBuffer.append(TEXT_59);
    stringBuffer.append(PropertyToPython.getPythonType(tempSimple.getType()));
    stringBuffer.append(TEXT_60);
     if (tempSimple.getValue() != null ) { 
    stringBuffer.append(TEXT_61);
    stringBuffer.append(PropertyToPython.getPythonValue(tempSimple.getValue(), tempSimple.getType()));
    stringBuffer.append(TEXT_62);
    }
     if (tempSimple.getUnits() != null ) { 
    stringBuffer.append(TEXT_63);
    stringBuffer.append(PropertyToPython.getPythonUnits(tempSimple.getUnits()));
    stringBuffer.append(TEXT_64);
     } 
    stringBuffer.append(TEXT_65);
    stringBuffer.append(PropertyToPython.getPythonMode(tempSimple.getMode()));
    stringBuffer.append(TEXT_66);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(PropertyToPython.getPythonAction(tempSimple.getAction()));
    stringBuffer.append(TEXT_68);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(PropertyToPython.getPythonKinds(tempSimple.getKind()));
     if (tempSimple.getDescription() != null ) { 
    stringBuffer.append(TEXT_70);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(PropertyToPython.getPythonDescription(tempSimple.getDescription()));
    stringBuffer.append(TEXT_72);
    }
    stringBuffer.append(TEXT_73);
    
    }
    List<String> seqNames = new ArrayList<String>();
    for (SimpleSequence simpleSeq : properties.getSimpleSequence()) {
        String seqName = SimpleSequenceToPython.getName(simpleSeq);
        String seq = StringUtil.defaultCreateUniqueString(seqName, seqNames);
        seqNames.add(seq); 
    stringBuffer.append(TEXT_74);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(seq);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(PropertyToPython.getPythonId(simpleSeq.getId()));
    stringBuffer.append(TEXT_77);
     if (simpleSeq.getName() != null ) { 
    stringBuffer.append(TEXT_78);
    stringBuffer.append(PropertyToPython.getPythonName(simpleSeq.getName()));
    stringBuffer.append(TEXT_79);
     } 
    stringBuffer.append(TEXT_80);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(PropertyToPython.getPythonType(simpleSeq.getType()));
    stringBuffer.append(TEXT_82);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(SimpleSequenceToPython.getPythonValues(simpleSeq.getValues(), simpleSeq.getType()));
    stringBuffer.append(TEXT_84);
     if (simpleSeq.getUnits() != null ) { 
    stringBuffer.append(TEXT_85);
    stringBuffer.append(PropertyToPython.getPythonUnits(simpleSeq.getUnits()));
    stringBuffer.append(TEXT_86);
     } 
    stringBuffer.append(TEXT_87);
    stringBuffer.append(PropertyToPython.getPythonMode(simpleSeq.getMode()));
    stringBuffer.append(TEXT_88);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(PropertyToPython.getPythonAction(simpleSeq.getAction()));
    stringBuffer.append(TEXT_90);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(PropertyToPython.getPythonKinds(simpleSeq.getKind()));
     if (simpleSeq.getDescription() != null ) { 
    stringBuffer.append(TEXT_92);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(PropertyToPython.getPythonDescription(simpleSeq.getDescription()));
    stringBuffer.append(TEXT_94);
    }
    stringBuffer.append(TEXT_95);
    
    }
    List<String> structNames = new ArrayList<String>();
    List<String> structSeqNames = new ArrayList<String>();
    
    List<Struct> propertyStructs = new ArrayList<Struct>(properties.getStruct());
    for (StructSequence seq : properties.getStructSequence()) {
        propertyStructs.add(seq.getStruct());
    }
    char [][] filter = {{' ', '_'}, {'-', '_'}}; // TODO: This is so, so, disgusting
    for (Struct struct : propertyStructs) {
        String structName = StructToPython.getName(struct);
        String className = PortHelper.nameToCamelCase(StringUtil.defaultCreateUniqueString(structName, structNames));
        className = StringUtil.cleanUp(className, filter);
        structNames.add(className);

    stringBuffer.append(TEXT_96);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_97);
    
        List<String> structSimpleNames = new ArrayList<String>();
        Map<String, Simple> uniqueNameToSimpleMap = new HashMap<String, Simple>(); 
        for (Simple simple : struct.getSimple()) { 
            String baseName = SimpleToPython.getName(simple);
            String myName = StringUtil.defaultCreateUniqueString(baseName, structSimpleNames);
            structSimpleNames.add(myName);
            uniqueNameToSimpleMap.put(myName, simple);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(myName);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(PropertyToPython.getPythonId(simple.getId()));
    stringBuffer.append(TEXT_100);
     if (simple.getName() != null ) { 
    stringBuffer.append(TEXT_101);
    stringBuffer.append(PropertyToPython.getPythonName(simple.getName()));
    stringBuffer.append(TEXT_102);
     } 
    stringBuffer.append(TEXT_103);
    stringBuffer.append(PropertyToPython.getPythonType(simple.getType()));
    stringBuffer.append(TEXT_104);
     if (simple.getValue() != null ) { 
    stringBuffer.append(TEXT_105);
    stringBuffer.append(PropertyToPython.getPythonValue(simple.getValue(), simple.getType()));
    stringBuffer.append(TEXT_106);
    }
    stringBuffer.append(TEXT_107);
    
        }
        if (struct.eContainer() instanceof StructSequence) {
    stringBuffer.append(TEXT_108);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(StructToPython.getConstructorDef(structSimpleNames, uniqueNameToSimpleMap));
    for (String name : structSimpleNames) {
    stringBuffer.append(TEXT_110);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(name);
    }
    } else {
    stringBuffer.append(TEXT_112);
    }
    stringBuffer.append(TEXT_113);
     for (String propName : structSimpleNames) { 
    stringBuffer.append(TEXT_114);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(propName);
     } 
    stringBuffer.append(TEXT_116);
    stringBuffer.append(struct.getId());
    stringBuffer.append(TEXT_117);
     
                for (String propName : structSimpleNames) { 
                	
    stringBuffer.append(TEXT_118);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_120);
    
                	if (structSimpleNames.indexOf(propName)!=structSimpleNames.size()-1) {
                		
    stringBuffer.append(TEXT_121);
    
                	}
                } 
    stringBuffer.append(TEXT_122);
     if (!(struct.eContainer() instanceof StructSequence)) {
    stringBuffer.append(TEXT_123);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(structName);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(PropertyToPython.getPythonId(struct.getId()));
    stringBuffer.append(TEXT_126);
     if (struct.getName() != null ) { 
    stringBuffer.append(TEXT_127);
    stringBuffer.append(PropertyToPython.getPythonName(struct.getName()));
    stringBuffer.append(TEXT_128);
     } 
    stringBuffer.append(TEXT_129);
    stringBuffer.append(StructToPython.getStructDef(className));
    stringBuffer.append(TEXT_130);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(StructToPython.getPythonConfigurationKinds(struct.getConfigurationKind()));
    stringBuffer.append(TEXT_132);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(PropertyToPython.getPythonMode(struct.getMode()));
     if (struct.getDescription() != null ) { 
    stringBuffer.append(TEXT_134);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(PropertyToPython.getPythonDescription(struct.getDescription()));
    stringBuffer.append(TEXT_136);
    }
    stringBuffer.append(TEXT_137);
    
        } else { //StructSequence
            StructSequence structSeq = (StructSequence) struct.eContainer();
            String structSeqName = StructSequenceToPython.getName(structSeq);
            String uniqueStructSeqName = StringUtil.defaultCreateUniqueString(structSeqName, structSeqNames);
            structSeqNames.add(uniqueStructSeqName);
            StructValues values = new StructValues(structSeq);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(uniqueStructSeqName);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(PropertyToPython.getPythonId(structSeq.getId()));
    stringBuffer.append(TEXT_141);
     if (structSeq.getName() != null ) { 
    stringBuffer.append(TEXT_142);
    stringBuffer.append(PropertyToPython.getPythonName(structSeq.getName()));
    stringBuffer.append(TEXT_143);
     } 
    stringBuffer.append(TEXT_144);
    stringBuffer.append(StructToPython.getStructDef(className));
    stringBuffer.append(TEXT_145);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(StructSequenceToPython.getPythonStructValue(className, values));
    stringBuffer.append(TEXT_147);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(StructToPython.getPythonConfigurationKinds(structSeq.getConfigurationKind()));
    stringBuffer.append(TEXT_149);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(PropertyToPython.getPythonMode(structSeq.getMode()));
     if (structSeq.getDescription() != null ) { 
    stringBuffer.append(TEXT_151);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(PropertyToPython.getPythonDescription(structSeq.getDescription()));
    stringBuffer.append(TEXT_153);
    }
    stringBuffer.append(TEXT_154);
        
        }         
    }

    stringBuffer.append(TEXT_155);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE