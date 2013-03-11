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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.IPortTemplateDesc;
import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.PortRepToGeneratorMap;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.ports.PropertyChangeEventPortTemplate;
import gov.redhawk.ide.codegen.jet.python.ports.PullPortTemplate;
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
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
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
  protected final String TEXT_29 = NL + "import Queue, copy, time, threading";
  protected final String TEXT_30 = NL + "from ossie import events";
  protected final String TEXT_31 = NL + "from ossie import events";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = NL + NL + "NOOP = -1" + NL + "NORMAL = 0" + NL + "FINISH = 1" + NL + "class ProcessThread(threading.Thread):" + NL + "    def __init__(self, target, pause=0.0125):" + NL + "        threading.Thread.__init__(self)" + NL + "        self.setDaemon(True)" + NL + "        self.target = target" + NL + "        self.pause = pause" + NL + "        self.stop_signal = threading.Event()" + NL + "" + NL + "    def stop(self):" + NL + "        self.stop_signal.set()" + NL + "" + NL + "    def updatePause(self, pause):" + NL + "        self.pause = pause" + NL + "" + NL + "    def run(self):" + NL + "        state = NORMAL" + NL + "        while (state != FINISH) and (not self.stop_signal.isSet()):" + NL + "            state = self.target()" + NL + "            if (state == NOOP):" + NL + "                # If there was no data to process sleep to avoid spinning" + NL + "                time.sleep(self.pause)" + NL + "" + NL + "class ";
  protected final String TEXT_34 = "_base(CF__POA.";
  protected final String TEXT_35 = "Resource, Resource";
  protected final String TEXT_36 = "Aggregate";
  protected final String TEXT_37 = "Plain";
  protected final String TEXT_38 = "Device,";
  protected final String TEXT_39 = "Device, ";
  protected final String TEXT_40 = "Device";
  protected final String TEXT_41 = ", AggregateDevice";
  protected final String TEXT_42 = "):" + NL + "        # These values can be altered in the __init__ of your derived class" + NL + "" + NL + "        PAUSE = 0.0125 # The amount of time to sleep if process return NOOP" + NL + "        TIMEOUT = 5.0 # The amount of time to wait for the process thread to die when stop() is called" + NL + "        DEFAULT_QUEUE_SIZE = 100 # The number of BulkIO packets that can be in the queue before pushPacket will block" + NL + "        ";
  protected final String TEXT_43 = NL + "        def __init__(self, identifier, execparams):" + NL + "            loggerName = (execparams['NAME_BINDING'].replace('/', '.')).rsplit(\"_\", 1)[0]" + NL + "            Resource.__init__(self, identifier, execparams, loggerName=loggerName)";
  protected final String TEXT_44 = NL + "        def __init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams):";
  protected final String TEXT_45 = NL + "            ExecutableDevice.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams)";
  protected final String TEXT_46 = NL + "            LoadableDevice.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams)";
  protected final String TEXT_47 = NL + "            Device.__init__(self, devmgr, uuid, label, softwareProfile, compositeDevice, execparams)";
  protected final String TEXT_48 = NL + "            AggregateDevice.__init__(self)";
  protected final String TEXT_49 = NL + "            self.threadControlLock = threading.RLock()" + NL + "            self.process_thread = None" + NL + "            # self.auto_start is deprecated and is only kept for API compatability" + NL + "            # with 1.7.X and 1.8.0 components.  This variable may be removed" + NL + "            # in future releases" + NL + "            self.auto_start = ";
  protected final String TEXT_50 = "True";
  protected final String TEXT_51 = "False";
  protected final String TEXT_52 = NL + "            " + NL + "        def initialize(self):";
  protected final String TEXT_53 = NL + "            ";
  protected final String TEXT_54 = ".initialize(self)" + NL + "            " + NL + "            # Instantiate the default implementations for all ports on this component";
  protected final String TEXT_55 = NL + "            self.port_";
  protected final String TEXT_56 = " = events.MessageConsumerPort(thread_sleep=0.1)";
  protected final String TEXT_57 = NL + "            self.port_";
  protected final String TEXT_58 = " = ";
  protected final String TEXT_59 = NL;
  protected final String TEXT_60 = NL + "            self.port_";
  protected final String TEXT_61 = " = events.MessageSupplierPort()";
  protected final String TEXT_62 = NL + "            self.port_";
  protected final String TEXT_63 = " = ";
  protected final String TEXT_64 = NL + "            " + NL + "            # Autostart the Resource" + NL + "            if self.auto_start:" + NL + "                try:" + NL + "                    self.start()" + NL + "                except Exception, e:" + NL + "                    raise CF.Resource.InitializeError(CF.CF_NOTSET, \"Error autostarting component: \" + str(e))";
  protected final String TEXT_65 = NL + NL + "        def start(self):" + NL + "            self.threadControlLock.acquire()" + NL + "            try:";
  protected final String TEXT_66 = NL + "                ";
  protected final String TEXT_67 = ".start(self)" + NL + "                if self.process_thread == None:" + NL + "                    self.process_thread = ProcessThread(target=self.process, pause=self.PAUSE)" + NL + "                    self.process_thread.start()" + NL + "            finally:" + NL + "                self.threadControlLock.release()" + NL + "" + NL + "        def process(self):" + NL + "            \"\"\"The process method should process a single \"chunk\" of data and then return.  This method will be called" + NL + "            from the processing thread again, and again, and again until it returns FINISH or stop() is called on the" + NL + "            component.  If no work is performed, then return NOOP\"\"\"" + NL + "            raise NotImplementedError" + NL + "" + NL + "        def stop(self):" + NL + "            self.threadControlLock.acquire()" + NL + "            try:" + NL + "                process_thread = self.process_thread" + NL + "                self.process_thread = None" + NL + "" + NL + "                if process_thread != None:" + NL + "                    process_thread.stop()" + NL + "                    process_thread.join(self.TIMEOUT)" + NL + "                    if process_thread.isAlive():" + NL + "                        raise CF.Resource.StopError(CF.CF_NOTSET, \"Processing thread did not die\")";
  protected final String TEXT_68 = NL + "                ";
  protected final String TEXT_69 = ".stop(self)" + NL + "            finally:" + NL + "                self.threadControlLock.release()" + NL + "" + NL + "        def releaseObject(self):" + NL + "            try:" + NL + "                self.stop()" + NL + "            except Exception:" + NL + "                self._log.exception(\"Error stopping\")" + NL + "            self.threadControlLock.acquire()" + NL + "            try:";
  protected final String TEXT_70 = NL + "                ";
  protected final String TEXT_71 = ".releaseObject(self)" + NL + "            finally:" + NL + "                self.threadControlLock.release()" + NL + "" + NL + "        ######################################################################" + NL + "        # PORTS" + NL + "        # " + NL + "        # DO NOT ADD NEW PORTS HERE.  You can add ports in your derived class, in the SCD xml file, " + NL + "        # or via the IDE." + NL + "        ";
  protected final String TEXT_72 = NL + "        def compareSRI(self, a, b):" + NL + "            if a.hversion != b.hversion:" + NL + "                return False" + NL + "            if a.xstart != b.xstart:" + NL + "                return False" + NL + "            if a.xdelta != b.xdelta:" + NL + "                return False" + NL + "            if a.xunits != b.xunits:" + NL + "                return False" + NL + "            if a.subsize != b.subsize:" + NL + "                return False" + NL + "            if a.ystart != b.ystart:" + NL + "                return False" + NL + "            if a.ydelta != b.ydelta:" + NL + "                return False" + NL + "            if a.yunits != b.yunits:" + NL + "                return False" + NL + "            if a.mode != b.mode:" + NL + "                return False" + NL + "            if a.streamID != b.streamID:" + NL + "                return False" + NL + "            if a.blocking != b.blocking:" + NL + "                return False" + NL + "            if len(a.keywords) != len(b.keywords):" + NL + "                return False" + NL + "            for keyA, keyB in zip(a.keywords, b.keywords):" + NL + "                if keyA.value._t != keyB.value._t:" + NL + "                    return False" + NL + "                if keyA.value._v != keyB.value._v:" + NL + "                    return False" + NL + "            return True";
  protected final String TEXT_73 = NL;
  protected final String TEXT_74 = NL;
  protected final String TEXT_75 = NL;
  protected final String TEXT_76 = NL;
  protected final String TEXT_77 = NL;
  protected final String TEXT_78 = NL + NL + "        def get_port_";
  protected final String TEXT_79 = "(self):" + NL + "            # You must implement this function and return an" + NL + "            # instance of Port";
  protected final String TEXT_80 = NL + "            raise NotImplementedError";
  protected final String TEXT_81 = NL + NL + "        port_";
  protected final String TEXT_82 = " = providesport(name=\"";
  protected final String TEXT_83 = "\"," + NL + "                                            repid=\"";
  protected final String TEXT_84 = "\"," + NL + "                                            type_=\"";
  protected final String TEXT_85 = "\"";
  protected final String TEXT_86 = "control\"";
  protected final String TEXT_87 = ",";
  protected final String TEXT_88 = NL + "                                            fget=get_port_";
  protected final String TEXT_89 = ")";
  protected final String TEXT_90 = NL + NL + "        def get_port_";
  protected final String TEXT_91 = "(self):" + NL + "            # You must implement this function and return an" + NL + "            # instance of Port";
  protected final String TEXT_92 = NL + "            raise NotImplementedError";
  protected final String TEXT_93 = NL + NL + "        port_";
  protected final String TEXT_94 = " = usesport(name=\"";
  protected final String TEXT_95 = "\"," + NL + "                                            repid=\"";
  protected final String TEXT_96 = "\"," + NL + "                                            type_=\"";
  protected final String TEXT_97 = "\"";
  protected final String TEXT_98 = "control\"";
  protected final String TEXT_99 = ",";
  protected final String TEXT_100 = NL + "                                            fget=get_port_";
  protected final String TEXT_101 = ")";
  protected final String TEXT_102 = "        " + NL + "" + NL + "        ######################################################################" + NL + "        # PROPERTIES" + NL + "        # " + NL + "        # DO NOT ADD NEW PROPERTIES HERE.  You can add properties in your derived class, in the PRF xml file" + NL + "        # or by using the IDE.";
  protected final String TEXT_103 = "       ";
  protected final String TEXT_104 = NL + "        ";
  protected final String TEXT_105 = " = simple_property(";
  protected final String TEXT_106 = ",";
  protected final String TEXT_107 = NL + "                                          ";
  protected final String TEXT_108 = ", ";
  protected final String TEXT_109 = NL + "                                          ";
  protected final String TEXT_110 = ",";
  protected final String TEXT_111 = NL + "                                          defvalue=";
  protected final String TEXT_112 = ",";
  protected final String TEXT_113 = NL + "                                          ";
  protected final String TEXT_114 = ", ";
  protected final String TEXT_115 = NL + "                                          ";
  protected final String TEXT_116 = ",";
  protected final String TEXT_117 = NL + "                                          ";
  protected final String TEXT_118 = ",";
  protected final String TEXT_119 = NL + "                                          ";
  protected final String TEXT_120 = ",";
  protected final String TEXT_121 = NL + "                                          ";
  protected final String TEXT_122 = " ";
  protected final String TEXT_123 = NL + "                                          )";
  protected final String TEXT_124 = " ";
  protected final String TEXT_125 = NL + "        ";
  protected final String TEXT_126 = " = simpleseq_property(";
  protected final String TEXT_127 = ",";
  protected final String TEXT_128 = NL + "                                          ";
  protected final String TEXT_129 = ", ";
  protected final String TEXT_130 = "  ";
  protected final String TEXT_131 = NL + "                                          ";
  protected final String TEXT_132 = ",";
  protected final String TEXT_133 = NL + "                                          ";
  protected final String TEXT_134 = ",";
  protected final String TEXT_135 = NL + "                                          ";
  protected final String TEXT_136 = ", ";
  protected final String TEXT_137 = NL + "                                          ";
  protected final String TEXT_138 = ",";
  protected final String TEXT_139 = NL + "                                          ";
  protected final String TEXT_140 = ",";
  protected final String TEXT_141 = NL + "                                          ";
  protected final String TEXT_142 = ",";
  protected final String TEXT_143 = NL + "                                          ";
  protected final String TEXT_144 = " ";
  protected final String TEXT_145 = NL + "                                          )";
  protected final String TEXT_146 = NL + "        class ";
  protected final String TEXT_147 = "(object):";
  protected final String TEXT_148 = NL + "            ";
  protected final String TEXT_149 = " = simple_property(";
  protected final String TEXT_150 = ",";
  protected final String TEXT_151 = NL + "                                          ";
  protected final String TEXT_152 = ", ";
  protected final String TEXT_153 = NL + "                                          ";
  protected final String TEXT_154 = ",";
  protected final String TEXT_155 = NL + "                                          defvalue=";
  protected final String TEXT_156 = ",";
  protected final String TEXT_157 = NL + "                                          )";
  protected final String TEXT_158 = NL + "        ";
  protected final String TEXT_159 = NL + "            ";
  protected final String TEXT_160 = NL + "                self.";
  protected final String TEXT_161 = " = ";
  protected final String TEXT_162 = NL + "        " + NL + "            def __init__(self, **kw):" + NL + "                \"\"\"Construct an initialized instance of this struct definition\"\"\"" + NL + "                for attrname, classattr in type(self).__dict__.items():" + NL + "                    if type(classattr) == simple_property:" + NL + "                        classattr.initialize(self)" + NL + "                for k,v in kw.items():" + NL + "                    setattr(self,k,v)";
  protected final String TEXT_163 = NL + NL + "            def __str__(self):" + NL + "                \"\"\"Return a string representation of this structure\"\"\"" + NL + "                d = {}";
  protected final String TEXT_164 = NL + "                d[\"";
  protected final String TEXT_165 = "\"] = self.";
  protected final String TEXT_166 = NL + "                return str(d)" + NL + "" + NL + "            def getId(self):" + NL + "                return \"";
  protected final String TEXT_167 = "\"" + NL + "" + NL + "            def isStruct(self):" + NL + "                return True" + NL + "" + NL + "            def getMembers(self):" + NL + "                return [";
  protected final String TEXT_168 = "(\"";
  protected final String TEXT_169 = "\",self.";
  protected final String TEXT_170 = ")";
  protected final String TEXT_171 = ",";
  protected final String TEXT_172 = "]" + NL;
  protected final String TEXT_173 = NL + "        ";
  protected final String TEXT_174 = NL + "        ";
  protected final String TEXT_175 = " = struct_property(";
  protected final String TEXT_176 = ",";
  protected final String TEXT_177 = NL + "                                          ";
  protected final String TEXT_178 = ", ";
  protected final String TEXT_179 = NL + "                                          ";
  protected final String TEXT_180 = ",";
  protected final String TEXT_181 = NL + "                                          ";
  protected final String TEXT_182 = ",";
  protected final String TEXT_183 = NL + "                                          ";
  protected final String TEXT_184 = ",";
  protected final String TEXT_185 = NL + "                                          ";
  protected final String TEXT_186 = " ";
  protected final String TEXT_187 = NL + "                                          )";
  protected final String TEXT_188 = NL + "                ";
  protected final String TEXT_189 = NL + "        ";
  protected final String TEXT_190 = " = structseq_property(";
  protected final String TEXT_191 = ",";
  protected final String TEXT_192 = NL + "                                          ";
  protected final String TEXT_193 = ", ";
  protected final String TEXT_194 = NL + "                                          ";
  protected final String TEXT_195 = ",                          ";
  protected final String TEXT_196 = NL + "                                          ";
  protected final String TEXT_197 = ",";
  protected final String TEXT_198 = NL + "                                          ";
  protected final String TEXT_199 = ",";
  protected final String TEXT_200 = NL + "                                          ";
  protected final String TEXT_201 = ",";
  protected final String TEXT_202 = NL + "                                          ";
  protected final String TEXT_203 = " ";
  protected final String TEXT_204 = NL + "                                          )";
  protected final String TEXT_205 = NL + NL + "'''provides port(s)'''";
  protected final String TEXT_206 = NL;
  protected final String TEXT_207 = NL;
  protected final String TEXT_208 = NL + NL + "'''uses port(s)'''";
  protected final String TEXT_209 = NL;
  protected final String TEXT_210 = NL;
  protected final String TEXT_211 = NL;
  protected final String TEXT_212 = NL;

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

    
    for (Property tempProp : implSettings.getProperties()) {              
        if ("use_old_style".equals(tempProp.getId())) {
            if (!Boolean.parseBoolean(tempProp.getValue())) {
                generateGetters = false;
                continue;
            }
        }
        
        if ("auto_start".equals(tempProp.getId()) || "auto_start_component".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                autoStart = true;
                continue;
            }
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

    
    includePorts = false;

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
    
    List<String> imports = new ArrayList<String>();
    boolean foundUsesMessageEvent = false;
    for (String intName : usesReps) {
        if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {

    stringBuffer.append(TEXT_30);
    
        	foundUsesMessageEvent = true;
        	break;
        }
    }
    if (!foundUsesMessageEvent) {
    	for (String intName : providesReps) {
        	if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {

    stringBuffer.append(TEXT_31);
    
	        	break;
    	    }
    	}
    }
    for (String intName : usesReps) {
        IScaPortCodegenTemplate gen = portMap.get(intName);
        portTempl.setPortRepId(intName);
        portTempl.setGenSupport(true);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(false);
        String imp = "";
        if (gen != null) {
            imp = gen.generateClassSupport(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
        } else {
            imp = new PullPortTemplate().generateClassSupport(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
        }
        for (String s : imp.split("\n")) {
            if ((s.trim().length() > 0) && !imports.contains(s)) {
                imp = new PropertyChangeEventPortTemplate().generateClassSupport(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
                if (s.contains("ExtendedEvent")) {
                    continue;
        		}
                imports.add(s);
            }
        }
    }
    if (includePropertyChange) {
        imports.add(new PropertyChangeEventPortTemplate().generateClassSupport(null, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    }
    for (String intName : providesReps) {
        IScaPortCodegenTemplate gen = portMap.get(intName);
        portTempl.setPortRepId(intName);
        portTempl.setGenSupport(true);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(false);
        String imp = "";
        if (gen != null) {
            imp = gen.generateClassSupport(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
        } else {
            imp = new PullPortTemplate().generateClassSupport(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
        }
        for (String s : imp.split("\n")) {
            if ((s.trim().length() > 0) && !imports.contains(s)) {
                imp = new PropertyChangeEventPortTemplate().generateClassSupport(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
                if (s.contains("ExtendedEvent")) {
                    continue;
        		}
                imports.add(s);
            }
        }
    }
    for (String imp : imports) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(imp);
    
    }

    stringBuffer.append(TEXT_33);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_34);
     if (isResource) { 
    stringBuffer.append(TEXT_35);
     } else { if (aggregateDevice) {
    stringBuffer.append(TEXT_36);
    
        if ("Executable".equals(deviceType)) {
    stringBuffer.append(deviceType);
     
        } else if ("Loadable".equals(deviceType)) {
    stringBuffer.append(deviceType);
    
        } else {
    stringBuffer.append(TEXT_37);
    
        }
    stringBuffer.append(TEXT_38);
    
    } else {
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_39);
    
    }
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_40);
    
    if (aggregateDevice) {
    stringBuffer.append(TEXT_41);
    } }
    stringBuffer.append(TEXT_42);
     if (isResource) { 
    stringBuffer.append(TEXT_43);
     } else { 
    stringBuffer.append(TEXT_44);
    
    if (execDevice) {

    stringBuffer.append(TEXT_45);
     
    } else if (loadableDevice) {

    stringBuffer.append(TEXT_46);
     
    } else {

    stringBuffer.append(TEXT_47);
     
    }
    if (aggregateDevice) {

    stringBuffer.append(TEXT_48);
    
    } 
 } 

    stringBuffer.append(TEXT_49);
    if (autoStart) {
    stringBuffer.append(TEXT_50);
    } else {
    stringBuffer.append(TEXT_51);
    }
    stringBuffer.append(TEXT_52);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(OSSIENAME);
    stringBuffer.append(TEXT_54);
    
    for (Provides provide : provides) {
        if (provide.getRepID().equals("IDL:ExtendedEvent/MessageEvent:1.0")) {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(PortHelper.cleanName(provide.getProvidesName()));
    stringBuffer.append(TEXT_56);
    
        } else {
	        String portInitializer = null;
    	    IScaPortCodegenTemplate gen = portMap.get(provide.getRepID());
        	portTempl.setPortRepId(provide.getRepID());
	        portTempl.setGenSupport(false);
    	    portTempl.setGenClassDef(false);
        	portTempl.setGenClassImpl(false);
	        if (gen != null) {
    	        portInitializer = gen.generateClassInstantiator(provide.getRepID(), true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
        	}
	        if ((portInitializer == null) || "".equals(portInitializer.trim())) {
    	        if ("BULKIO".equals(provide.getRepID().split(":")[1].split("/")[0])) {
        	        portInitializer = "Port" + PortHelper.idlToCamelPortClass(provide.getRepID()) + "In_i(self, \"" + provide.getProvidesName() + "\", self.DEFAULT_QUEUE_SIZE)";
            	} else {
	                portInitializer = "Port" + PortHelper.idlToCamelPortClass(provide.getRepID()) + "In_i(self, \"" + provide.getProvidesName() + "\")";
    	        }
        	}

    stringBuffer.append(TEXT_57);
    stringBuffer.append(PortHelper.cleanName(provide.getProvidesName()));
    stringBuffer.append(TEXT_58);
    stringBuffer.append(portInitializer);
     
    	}
    }

    stringBuffer.append(TEXT_59);
    
    for (Uses use : uses) {
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
            if (use.getRepID().equals("IDL:ExtendedEvent/MessageEvent:1.0")) {

    stringBuffer.append(TEXT_60);
    stringBuffer.append(PortHelper.cleanName(use.getUsesName()));
    stringBuffer.append(TEXT_61);
    
            } else {
                String portInitializer = null;
                IScaPortCodegenTemplate gen = portMap.get(use.getRepID());
                portTempl.setPortRepId(use.getRepID());
                portTempl.setGenSupport(false);
                portTempl.setGenClassDef(false);
                portTempl.setGenClassImpl(false);
                if (gen != null) {
                    portInitializer = gen.generateClassInstantiator(use.getRepID(), false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
                } else if (PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(use.getUsesName()) && PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(use.getRepID())) {
                    portInitializer = new PropertyChangeEventPortTemplate().generateClassInstantiator(null, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON);
                }
                if (portInitializer == null || "".equals(portInitializer.trim())) {
                    portInitializer = "Port" + PortHelper.idlToCamelPortClass(use.getRepID()) + "Out_i(self, \"" + use.getUsesName() + "\")";
                }

    stringBuffer.append(TEXT_62);
    stringBuffer.append(PortHelper.cleanName(use.getUsesName()));
    stringBuffer.append(TEXT_63);
    stringBuffer.append(portInitializer);
    
            }
        } // if (foundMatchingProvides == false)
    } // end uses loop
    
    if (autoStart) { 

    stringBuffer.append(TEXT_64);
    
    }

    stringBuffer.append(TEXT_65);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(OSSIENAME);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(OSSIENAME);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(OSSIENAME);
    stringBuffer.append(TEXT_71);
    
    if (havePorts) {

    stringBuffer.append(TEXT_72);
    
        if (provides.size() > 0) {
            for (String intName : providesReps) {
        		if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {
            		continue;
        		}
                IScaPortCodegenTemplate gen = portMap.get(intName);
                portTempl.setPortRepId(intName);
                portTempl.setGenSupport(false);
                portTempl.setGenClassDef(true);
                portTempl.setGenClassImpl(false);
                if (gen != null) {

    stringBuffer.append(TEXT_73);
    stringBuffer.append(gen.generateClassDefinition(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                } else {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(new PullPortTemplate().generateClassDefinition(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                }
            }
        }
        
        if (uses.size() > 0) {
            if (includePropertyChange) {

    stringBuffer.append(TEXT_75);
    stringBuffer.append(new PropertyChangeEventPortTemplate().generateClassDefinition(null, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
            }
            for (String intName : usesReps) {
        		if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {
            		continue;
        		}
                IScaPortCodegenTemplate gen = portMap.get(intName);
                portTempl.setPortRepId(intName);
                portTempl.setGenSupport(false);
                portTempl.setGenClassDef(true);
                portTempl.setGenClassImpl(false);
                if (gen != null) {

    stringBuffer.append(TEXT_76);
    stringBuffer.append(gen.generateClassDefinition(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
        		} else {

    stringBuffer.append(TEXT_77);
    stringBuffer.append(new PullPortTemplate().generateClassDefinition(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                }
            }
        } // end if uses > 0
        for (Provides provide : provides) {
            if (generateGetters) {

    stringBuffer.append(TEXT_78);
    stringBuffer.append(provide.getProvidesName());
    stringBuffer.append(TEXT_79);
    stringBuffer.append(PortHelper.idlToCamelPortClass(provide.getRepID()));
    stringBuffer.append(TEXT_80);
    
            }

    stringBuffer.append(TEXT_81);
    stringBuffer.append(PortHelper.cleanName(provide.getProvidesName()));
    stringBuffer.append(TEXT_82);
    stringBuffer.append(provide.getProvidesName());
    stringBuffer.append(TEXT_83);
    stringBuffer.append(provide.getRepID());
    stringBuffer.append(TEXT_84);
    if (!provide.getPortType().isEmpty()) {
    stringBuffer.append(provide.getPortType().get(0).getType().getName());
    stringBuffer.append(TEXT_85);
     } else {
    stringBuffer.append(TEXT_86);
    }
    stringBuffer.append(TEXT_87);
     if (generateGetters) {
    stringBuffer.append(TEXT_88);
    stringBuffer.append(provide.getProvidesName());
     } 
    stringBuffer.append(TEXT_89);
    
        } // end provides loop
        for (Uses use : uses) {
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
                if (generateGetters) {

    stringBuffer.append(TEXT_90);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_91);
    stringBuffer.append(PortHelper.idlToCamelPortClass(use.getRepID()));
    stringBuffer.append(TEXT_92);
    
                }

    stringBuffer.append(TEXT_93);
    stringBuffer.append(PortHelper.cleanName(use.getUsesName()));
    stringBuffer.append(TEXT_94);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_95);
    stringBuffer.append(use.getRepID());
    stringBuffer.append(TEXT_96);
    if (!use.getPortType().isEmpty()) {
    stringBuffer.append(use.getPortType().get(0).getType().getName());
    stringBuffer.append(TEXT_97);
     } else {
    stringBuffer.append(TEXT_98);
    }
    stringBuffer.append(TEXT_99);
     if (generateGetters) {
    stringBuffer.append(TEXT_100);
    stringBuffer.append(use.getUsesName());
     } 
    stringBuffer.append(TEXT_101);
        
            } // if (foundMatchingProvides == false)
        } // end uses loop
    } // end if havePorts

    stringBuffer.append(TEXT_102);
      
    List<String> simpleNames = new ArrayList<String>();
    for (Simple tempSimple : properties.getSimple()) {
        String simpleName = SimpleToPython.getName(tempSimple);
        String simple = StringUtil.defaultCreateUniqueString(simpleName, simpleNames);
        simpleNames.add(simple); 
    stringBuffer.append(TEXT_103);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(simple);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(PropertyToPython.getPythonId(tempSimple.getId()));
    stringBuffer.append(TEXT_106);
     if (tempSimple.getName() != null ) { 
    stringBuffer.append(TEXT_107);
    stringBuffer.append(PropertyToPython.getPythonName(tempSimple.getName()));
    stringBuffer.append(TEXT_108);
     } 
    stringBuffer.append(TEXT_109);
    stringBuffer.append(PropertyToPython.getPythonType(tempSimple.getType()));
    stringBuffer.append(TEXT_110);
     if (tempSimple.getValue() != null ) { 
    stringBuffer.append(TEXT_111);
    stringBuffer.append(PropertyToPython.getPythonValue(tempSimple.getValue(), tempSimple.getType()));
    stringBuffer.append(TEXT_112);
    }
     if (tempSimple.getUnits() != null ) { 
    stringBuffer.append(TEXT_113);
    stringBuffer.append(PropertyToPython.getPythonUnits(tempSimple.getUnits()));
    stringBuffer.append(TEXT_114);
     } 
    stringBuffer.append(TEXT_115);
    stringBuffer.append(PropertyToPython.getPythonMode(tempSimple.getMode()));
    stringBuffer.append(TEXT_116);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(PropertyToPython.getPythonAction(tempSimple.getAction()));
    stringBuffer.append(TEXT_118);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(PropertyToPython.getPythonKinds(tempSimple.getKind()));
     if (tempSimple.getDescription() != null ) { 
    stringBuffer.append(TEXT_120);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(PropertyToPython.getPythonDescription(tempSimple.getDescription()));
    stringBuffer.append(TEXT_122);
    }
    stringBuffer.append(TEXT_123);
    
    }
    List<String> seqNames = new ArrayList<String>();
    for (SimpleSequence simpleSeq : properties.getSimpleSequence()) {
        String seqName = SimpleSequenceToPython.getName(simpleSeq);
        String seq = StringUtil.defaultCreateUniqueString(seqName, seqNames);
        seqNames.add(seq); 
    stringBuffer.append(TEXT_124);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(seq);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(PropertyToPython.getPythonId(simpleSeq.getId()));
    stringBuffer.append(TEXT_127);
     if (simpleSeq.getName() != null ) { 
    stringBuffer.append(TEXT_128);
    stringBuffer.append(PropertyToPython.getPythonName(simpleSeq.getName()));
    stringBuffer.append(TEXT_129);
     } 
    stringBuffer.append(TEXT_130);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(PropertyToPython.getPythonType(simpleSeq.getType()));
    stringBuffer.append(TEXT_132);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(SimpleSequenceToPython.getPythonValues(simpleSeq.getValues(), simpleSeq.getType()));
    stringBuffer.append(TEXT_134);
     if (simpleSeq.getUnits() != null ) { 
    stringBuffer.append(TEXT_135);
    stringBuffer.append(PropertyToPython.getPythonUnits(simpleSeq.getUnits()));
    stringBuffer.append(TEXT_136);
     } 
    stringBuffer.append(TEXT_137);
    stringBuffer.append(PropertyToPython.getPythonMode(simpleSeq.getMode()));
    stringBuffer.append(TEXT_138);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(PropertyToPython.getPythonAction(simpleSeq.getAction()));
    stringBuffer.append(TEXT_140);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(PropertyToPython.getPythonKinds(simpleSeq.getKind()));
     if (simpleSeq.getDescription() != null ) { 
    stringBuffer.append(TEXT_142);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(PropertyToPython.getPythonDescription(simpleSeq.getDescription()));
    stringBuffer.append(TEXT_144);
    }
    stringBuffer.append(TEXT_145);
    
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

    stringBuffer.append(TEXT_146);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_147);
    
        List<String> structSimpleNames = new ArrayList<String>();
        Map<String, Simple> uniqueNameToSimpleMap = new HashMap<String, Simple>(); 
        for (Simple simple : struct.getSimple()) { 
            String baseName = SimpleToPython.getName(simple);
            String myName = StringUtil.defaultCreateUniqueString(baseName, structSimpleNames);
            structSimpleNames.add(myName);
            uniqueNameToSimpleMap.put(myName, simple);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(myName);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(PropertyToPython.getPythonId(simple.getId()));
    stringBuffer.append(TEXT_150);
     if (simple.getName() != null ) { 
    stringBuffer.append(TEXT_151);
    stringBuffer.append(PropertyToPython.getPythonName(simple.getName()));
    stringBuffer.append(TEXT_152);
     } 
    stringBuffer.append(TEXT_153);
    stringBuffer.append(PropertyToPython.getPythonType(simple.getType()));
    stringBuffer.append(TEXT_154);
     if (simple.getValue() != null ) { 
    stringBuffer.append(TEXT_155);
    stringBuffer.append(PropertyToPython.getPythonValue(simple.getValue(), simple.getType()));
    stringBuffer.append(TEXT_156);
    }
    stringBuffer.append(TEXT_157);
    
        }
        if (struct.eContainer() instanceof StructSequence) {
    stringBuffer.append(TEXT_158);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(StructToPython.getConstructorDef(structSimpleNames, uniqueNameToSimpleMap));
    for (String name : structSimpleNames) {
    stringBuffer.append(TEXT_160);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(name);
    }
    } else {
    stringBuffer.append(TEXT_162);
    }
    stringBuffer.append(TEXT_163);
     for (String propName : structSimpleNames) { 
    stringBuffer.append(TEXT_164);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(propName);
     } 
    stringBuffer.append(TEXT_166);
    stringBuffer.append(struct.getId());
    stringBuffer.append(TEXT_167);
     
                for (String propName : structSimpleNames) { 
                	
    stringBuffer.append(TEXT_168);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(propName);
    stringBuffer.append(TEXT_170);
    
                	if (structSimpleNames.indexOf(propName)!=structSimpleNames.size()-1) {
                		
    stringBuffer.append(TEXT_171);
    
                	}
                } 
    stringBuffer.append(TEXT_172);
     if (!(struct.eContainer() instanceof StructSequence)) {
    stringBuffer.append(TEXT_173);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(structName);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(PropertyToPython.getPythonId(struct.getId()));
    stringBuffer.append(TEXT_176);
     if (struct.getName() != null ) { 
    stringBuffer.append(TEXT_177);
    stringBuffer.append(PropertyToPython.getPythonName(struct.getName()));
    stringBuffer.append(TEXT_178);
     } 
    stringBuffer.append(TEXT_179);
    stringBuffer.append(StructToPython.getStructDef(className));
    stringBuffer.append(TEXT_180);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(StructToPython.getPythonConfigurationKinds(struct.getConfigurationKind()));
    stringBuffer.append(TEXT_182);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(PropertyToPython.getPythonMode(struct.getMode()));
     if (struct.getDescription() != null ) { 
    stringBuffer.append(TEXT_184);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(PropertyToPython.getPythonDescription(struct.getDescription()));
    stringBuffer.append(TEXT_186);
    }
    stringBuffer.append(TEXT_187);
    
        } else { //StructSequence
            StructSequence structSeq = (StructSequence) struct.eContainer();
            String structSeqName = StructSequenceToPython.getName(structSeq);
            String uniqueStructSeqName = StringUtil.defaultCreateUniqueString(structSeqName, structSeqNames);
            structSeqNames.add(uniqueStructSeqName);
            StructValues values = new StructValues(structSeq);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(uniqueStructSeqName);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(PropertyToPython.getPythonId(structSeq.getId()));
    stringBuffer.append(TEXT_191);
     if (structSeq.getName() != null ) { 
    stringBuffer.append(TEXT_192);
    stringBuffer.append(PropertyToPython.getPythonName(structSeq.getName()));
    stringBuffer.append(TEXT_193);
     } 
    stringBuffer.append(TEXT_194);
    stringBuffer.append(StructToPython.getStructDef(className));
    stringBuffer.append(TEXT_195);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(StructSequenceToPython.getPythonStructValue(className, values));
    stringBuffer.append(TEXT_197);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(StructToPython.getPythonConfigurationKinds(structSeq.getConfigurationKind()));
    stringBuffer.append(TEXT_199);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(PropertyToPython.getPythonMode(structSeq.getMode()));
     if (structSeq.getDescription() != null ) { 
    stringBuffer.append(TEXT_201);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(PropertyToPython.getPythonDescription(structSeq.getDescription()));
    stringBuffer.append(TEXT_203);
    }
    stringBuffer.append(TEXT_204);
        
        }         
    }

     
    // Ports 
    if (havePorts) {
        if (provides.size() > 0) {

    stringBuffer.append(TEXT_205);
    
            // Provides Ports
            for (String intName : providesReps) {
                if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {
                	continue;
                }
                IScaPortCodegenTemplate gen = portMap.get(intName);
                portTempl.setPortRepId(intName);
                portTempl.setGenSupport(false);
                portTempl.setGenClassDef(false);
                portTempl.setGenClassImpl(true);
                if (gen != null) {

    stringBuffer.append(TEXT_206);
    stringBuffer.append(gen.generateClassImplementation(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                } else {

    stringBuffer.append(TEXT_207);
    stringBuffer.append(new PullPortTemplate().generateClassImplementation(intName, true, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                }
            } // end for provides ports
        } //end if provides ports
        
        // Uses Ports
        if (uses.size() > 0) {

    stringBuffer.append(TEXT_208);
    
            if (includePropertyChange) {

    stringBuffer.append(TEXT_209);
    stringBuffer.append(new PropertyChangeEventPortTemplate().generateClassImplementation(null, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
            }
            for (String intName : usesReps) {
                if (intName.equals("IDL:ExtendedEvent/MessageEvent:1.0")) {
                	continue;
                }
                IScaPortCodegenTemplate gen = portMap.get(intName);
                portTempl.setPortRepId(intName);
                portTempl.setGenSupport(false);
                portTempl.setGenClassDef(false);
                portTempl.setGenClassImpl(true);
                if (gen != null) {

    stringBuffer.append(TEXT_210);
    stringBuffer.append(gen.generateClassImplementation(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                } else {

    stringBuffer.append(TEXT_211);
    stringBuffer.append(new PullPortTemplate().generateClassImplementation(intName, false, softPkg, implSettings, portTempl, CodegenUtil.PYTHON));
    
                }
            } // end for Uses Ports
        } // end if uses ports
    } // end if Ports 

    stringBuffer.append(TEXT_212);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE