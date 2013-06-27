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

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.IPortTemplateDesc;
import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.PortRepToGeneratorMap;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.PropertyChangeEventPortTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.MessagingPortTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.PullPortTemplate;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import gov.redhawk.ide.RedhawkIdeActivator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.SupportsInterface;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.emf.common.util.EList;

/**
 * @generated
 */
public class PullResourceBaseCppTemplate
{

  protected static String nl;
  public static synchronized PullResourceBaseCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullResourceBaseCppTemplate result = new PullResourceBaseCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "#include \"";
  protected final String TEXT_2 = "_base.h\"" + NL + "" + NL + "/*******************************************************************************************" + NL + "" + NL + "    AUTO-GENERATED CODE. DO NOT MODIFY" + NL + "    " + NL + " \tSource: ";
  protected final String TEXT_3 = NL + " \tGenerated on: ";
  protected final String TEXT_4 = NL + " \t";
  protected final String TEXT_5 = NL + " \t";
  protected final String TEXT_6 = NL + " \t";
  protected final String TEXT_7 = NL + NL + "*******************************************************************************************/" + NL + "" + NL + "/******************************************************************************************" + NL + "" + NL + "    The following class functions are for the base class for the component class. To" + NL + "    customize any of these functions, do not modify them here. Instead, overload them" + NL + "    on the child class" + NL + "" + NL + "******************************************************************************************/" + NL;
  protected final String TEXT_8 = " ";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "_base::";
  protected final String TEXT_11 = "_base(const char *uuid, const char *label) :" + NL + "                                     Resource_impl(uuid, label), serviceThread(0) {" + NL + "    construct();" + NL + "}";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "_base::";
  protected final String TEXT_14 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl) :";
  protected final String TEXT_15 = NL + "          ";
  protected final String TEXT_16 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_17 = ", AggregateDevice_impl ()";
  protected final String TEXT_18 = ", serviceThread(0){" + NL + "    construct();" + NL + "}" + NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "_base::";
  protected final String TEXT_21 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev) :";
  protected final String TEXT_22 = NL + "          ";
  protected final String TEXT_23 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_24 = ", AggregateDevice_impl ()";
  protected final String TEXT_25 = ", serviceThread(0){" + NL + "    construct();" + NL + "}" + NL;
  protected final String TEXT_26 = NL;
  protected final String TEXT_27 = "_base::";
  protected final String TEXT_28 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities) :";
  protected final String TEXT_29 = NL + "          ";
  protected final String TEXT_30 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl) ";
  protected final String TEXT_31 = ", AggregateDevice_impl ()";
  protected final String TEXT_32 = ", serviceThread(0){" + NL + "    construct();" + NL + "}" + NL;
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = "_base::";
  protected final String TEXT_35 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev) :";
  protected final String TEXT_36 = NL + "          ";
  protected final String TEXT_37 = "Device_impl (devMgr_ior, id, lbl, sftwrPrfl, compDev) ";
  protected final String TEXT_38 = ", AggregateDevice_impl ()";
  protected final String TEXT_39 = ", serviceThread(0){" + NL + "    construct();" + NL + "}";
  protected final String TEXT_40 = NL + NL + "void ";
  protected final String TEXT_41 = "_base::construct()" + NL + "{" + NL + "    Resource_impl::_started = false;" + NL + "    loadProperties();" + NL + "    serviceThread = 0;" + NL + "    " + NL + "    PortableServer::ObjectId_var oid;";
  protected final String TEXT_42 = NL + "    ";
  protected final String TEXT_43 = " = new ";
  protected final String TEXT_44 = ";" + NL + "    oid = ossie::corba::RootPOA()->activate_object(";
  protected final String TEXT_45 = ");";
  protected final String TEXT_46 = NL + "    ";
  protected final String TEXT_47 = " = new ";
  protected final String TEXT_48 = ";" + NL + "    oid = ossie::corba::RootPOA()->activate_object(";
  protected final String TEXT_49 = ");";
  protected final String TEXT_50 = NL + "    ";
  protected final String TEXT_51 = "->registerProperty(this->_identifier, this->naming_service_name, this->getPropertyFromId(\"";
  protected final String TEXT_52 = "\"));";
  protected final String TEXT_53 = NL + "    this->registerPropertyChangePort(";
  protected final String TEXT_54 = ");";
  protected final String TEXT_55 = NL;
  protected final String TEXT_56 = NL + "    registerInPort(";
  protected final String TEXT_57 = ");";
  protected final String TEXT_58 = NL + "    registerOutPort(";
  protected final String TEXT_59 = ", ";
  protected final String TEXT_60 = "->_this());";
  protected final String TEXT_61 = NL + "}" + NL + "" + NL + "/*******************************************************************************************" + NL + "    Framework-level functions" + NL + "    These functions are generally called by the framework to perform housekeeping." + NL + "*******************************************************************************************/" + NL + "void ";
  protected final String TEXT_62 = "_base::initialize() throw (CF::LifeCycle::InitializeError, CORBA::SystemException)" + NL + "{";
  protected final String TEXT_63 = NL + "    try {" + NL + "        start();" + NL + "    } catch (CF::Resource::StartError& ex) {" + NL + "    \tCF::StringSequence msg(1);" + NL + "    \tmsg[0] = ex.msg;" + NL + "        throw CF::LifeCycle::InitializeError(msg);" + NL + "    }";
  protected final String TEXT_64 = NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_65 = "_base::start() throw (CORBA::SystemException, CF::Resource::StartError)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(serviceThreadLock);" + NL + "    if (serviceThread == 0) {";
  protected final String TEXT_66 = NL + "        ";
  protected final String TEXT_67 = "->unblock();";
  protected final String TEXT_68 = NL + "        serviceThread = new ProcessThread<";
  protected final String TEXT_69 = "_base>(this, 0.1);" + NL + "        serviceThread->start();" + NL + "    }" + NL + "    " + NL + "    if (!Resource_impl::started()) {" + NL + "    \tResource_impl::start();" + NL + "    }" + NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_70 = "_base::stop() throw (CORBA::SystemException, CF::Resource::StopError)" + NL + "{" + NL + "    boost::mutex::scoped_lock lock(serviceThreadLock);" + NL + "    // release the child thread (if it exists)" + NL + "    if (serviceThread != 0) {";
  protected final String TEXT_71 = NL + "        ";
  protected final String TEXT_72 = "->block();";
  protected final String TEXT_73 = NL + "        if (!serviceThread->release(2)) {" + NL + "            throw CF::Resource::StopError(CF::CF_NOTSET, \"Processing thread did not die\");" + NL + "        }" + NL + "        serviceThread = 0;" + NL + "    }" + NL + "    " + NL + "    if (Resource_impl::started()) {" + NL + "    \tResource_impl::stop();" + NL + "    }" + NL + "}" + NL;
  protected final String TEXT_74 = NL + "CORBA::Object_ptr ";
  protected final String TEXT_75 = "_base::getPort(const char* _id) throw (CORBA::SystemException, CF::PortSupplier::UnknownPort)" + NL + "{" + NL + "" + NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in = inPorts.find(std::string(_id));" + NL + "    if (p_in != inPorts.end()) {" + NL;
  protected final String TEXT_76 = NL + "        if (!strcmp(_id,\"";
  protected final String TEXT_77 = "\")) {" + NL + "            MessageConsumerPort *ptr = dynamic_cast<MessageConsumerPort *>(p_in->second);" + NL + "            if (ptr) {" + NL + "                return ";
  protected final String TEXT_78 = "::";
  protected final String TEXT_79 = "::_duplicate(ptr->_this());" + NL + "            }" + NL + "        }";
  protected final String TEXT_80 = NL + "        if (!strcmp(_id,\"";
  protected final String TEXT_81 = "\")) {";
  protected final String TEXT_82 = NL + "            ";
  protected final String TEXT_83 = "_";
  protected final String TEXT_84 = "_In_i *ptr = dynamic_cast<";
  protected final String TEXT_85 = "_";
  protected final String TEXT_86 = "_In_i *>(p_in->second);" + NL + "            if (ptr) {" + NL + "                return ";
  protected final String TEXT_87 = "::";
  protected final String TEXT_88 = "::_duplicate(ptr->_this());" + NL + "            }" + NL + "        }";
  protected final String TEXT_89 = NL + "    }" + NL + "" + NL + "    std::map<std::string, CF::Port_var>::iterator p_out = outPorts_var.find(std::string(_id));" + NL + "    if (p_out != outPorts_var.end()) {" + NL + "        return CF::Port::_duplicate(p_out->second);" + NL + "    }" + NL + "" + NL + "    throw (CF::PortSupplier::UnknownPort());" + NL + "}";
  protected final String TEXT_90 = NL + NL + "void ";
  protected final String TEXT_91 = "_base::releaseObject() throw (CORBA::SystemException, CF::LifeCycle::ReleaseError)" + NL + "{" + NL + "    // This function clears the component running condition so main shuts down everything" + NL + "    try {" + NL + "        stop();" + NL + "    } catch (CF::Resource::StopError& ex) {" + NL + "        // TODO - this should probably be logged instead of ignored" + NL + "    }" + NL + "" + NL + "    // deactivate ports" + NL + "    releaseInPorts();" + NL + "    releaseOutPorts();" + NL;
  protected final String TEXT_92 = NL + "    delete(";
  protected final String TEXT_93 = ");";
  protected final String TEXT_94 = NL + "    delete(";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = NL;
  protected final String TEXT_97 = " " + NL + "    Resource_impl::releaseObject();";
  protected final String TEXT_98 = NL + "    ";
  protected final String TEXT_99 = "Device_impl::releaseObject();";
  protected final String TEXT_100 = NL + "}" + NL + "" + NL + "void ";
  protected final String TEXT_101 = "_base::loadProperties()" + NL + "{";
  protected final String TEXT_102 = NL + "    // Set the sequence with its initial values";
  protected final String TEXT_103 = NL + "    ";
  protected final String TEXT_104 = ".push_back(";
  protected final String TEXT_105 = ");";
  protected final String TEXT_106 = "            ";
  protected final String TEXT_107 = NL + "        ";
  protected final String TEXT_108 = ".resize(";
  protected final String TEXT_109 = ");";
  protected final String TEXT_110 = NL + "        ";
  protected final String TEXT_111 = "[";
  protected final String TEXT_112 = "].";
  protected final String TEXT_113 = " = ";
  protected final String TEXT_114 = ";";
  protected final String TEXT_115 = NL + "    addProperty(";
  protected final String TEXT_116 = ",";
  protected final String TEXT_117 = NL + "                ";
  protected final String TEXT_118 = ", ";
  protected final String TEXT_119 = NL + "               \"";
  protected final String TEXT_120 = "\",";
  protected final String TEXT_121 = NL + "               \"";
  protected final String TEXT_122 = "\",";
  protected final String TEXT_123 = NL + "               \"\",";
  protected final String TEXT_124 = NL + "               \"";
  protected final String TEXT_125 = "\"," + NL + "               \"";
  protected final String TEXT_126 = "\"," + NL + "               \"";
  protected final String TEXT_127 = "\"," + NL + "               \"";
  protected final String TEXT_128 = "\");" + NL;
  protected final String TEXT_129 = NL + "}";
  protected final String TEXT_130 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    List<CppProperties.Property> properties = CppProperties.getProperties(softPkg);
    Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
    EList<Provides> provides = ports.getProvides();
    EList<Uses> uses = ports.getUses();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    TemplateParameter portTempl = new TemplateParameter(impl, implSettings, search_paths);
    boolean autoStart = false;
    Date date = new Date(System.currentTimeMillis());
    for (Property tempProp : implSettings.getProperties()) {
        if ("auto_start".equals(tempProp.getId())) {
            if (Boolean.parseBoolean(tempProp.getValue())) {
                autoStart = true;
                break;
            }
        }
    }
    
    HashMap<String, IScaPortCodegenTemplate> portMap = new HashMap<String, IScaPortCodegenTemplate>();
    for (PortRepToGeneratorMap p : implSettings.getPortGenerators()) {
        try {
            IPortTemplateDesc template = CodegenUtil.getPortTemplate(p.getGenerator(), null);
            if (template != null) {
                portMap.put(p.getRepId(), template.getTemplate());
            }
        } catch (CoreException e) {
            // TODO What to do here! Throw the exception and not generate anything?
        }
    }
    String deviceType = "";
    boolean aggregateDevice = false;

    // TODO: Refactor this long block of code (and other similar blocks) into one handy place that can just give you an enum
    final List<SupportsInterface> supportedInterfaces = softPkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface();
    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/Device:1.0")) {
            deviceType = "";
            break;
        }
    }

    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/LoadableDevice:1.0")) {
            deviceType = "Loadable"; 
            break;
        }
    }

    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/ExecutableDevice:1.0")) {
            deviceType = "Executable"; 
            break;
        }
    }

    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/AggregateDevice:1.0")) {
            aggregateDevice = true;
            break;
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_3);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_4);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_7);
    
    if (!templ.isDevice()) {

    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    
    } else {

    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_16);
    
        if (aggregateDevice) {

    stringBuffer.append(TEXT_17);
    
        }

    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_23);
    
        if (aggregateDevice) {

    stringBuffer.append(TEXT_24);
    
        }

    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_30);
    
        if (aggregateDevice) {

    stringBuffer.append(TEXT_31);
    
        }

    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_37);
    
        if (aggregateDevice) {

    stringBuffer.append(TEXT_38);
    
        }

    stringBuffer.append(TEXT_39);
    }
    stringBuffer.append(TEXT_40);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_41);
    
    for (Provides pro : provides) {
        String entry = pro.getRepID();
        IScaPortCodegenTemplate gen = portMap.get(entry);
        portTempl.setPortRepId(entry);
        portTempl.setPortName(pro.getProvidesName());
        portTempl.setProvidesPort(true);
        portTempl.setGenSupport(false);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(false);
        String inst = null;
        if (MessagingPortTemplate.MESSAGECHANNEL_REPID.equals(entry)) {
            inst = new MessagingPortTemplate().generateClassInstantiator(entry, true, softPkg, implSettings, portTempl, CodegenUtil.CPP);
            inst += "(\""+pro.getProvidesName()+"\")";
        } else if (gen != null) {
            inst = gen.generateClassInstantiator(entry, true, softPkg, implSettings, portTempl, CodegenUtil.CPP);
        } else {
            inst = new PullPortTemplate().generateClassInstantiator(entry, true, softPkg, implSettings, portTempl, CodegenUtil.CPP);
        }

    stringBuffer.append(TEXT_42);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_43);
    stringBuffer.append(inst.trim());
    stringBuffer.append(TEXT_44);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_45);
    
    }
    for (Uses use : uses) {
        String entry = use.getRepID();
        IScaPortCodegenTemplate gen = portMap.get(entry);
        portTempl.setPortRepId(entry);
        portTempl.setPortName(use.getUsesName());
        portTempl.setProvidesPort(false);
        portTempl.setGenSupport(false);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(false);
        String inst = null;
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
            if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(entry) 
                    && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(use.getUsesName())) {
                inst = new PropertyChangeEventPortTemplate().generateClassInstantiator(entry, false, softPkg, implSettings, portTempl, CodegenUtil.CPP);
                inst += "(\""+use.getUsesName()+"\")";
            } else if (MessagingPortTemplate.MESSAGECHANNEL_REPID.equals(entry)) {
        
                inst = new MessagingPortTemplate().generateClassInstantiator(entry, false, softPkg, implSettings, portTempl, CodegenUtil.CPP);
                inst += "(\""+use.getUsesName()+"\")";
            
            } else if (gen != null) {
                inst = gen.generateClassInstantiator(entry, false, softPkg, implSettings, portTempl, CodegenUtil.CPP);
            } else {
                inst = new PullPortTemplate().generateClassInstantiator(entry, false, softPkg, implSettings, portTempl, CodegenUtil.CPP);
            }
        if (inst == null) {
            throw new IllegalArgumentException("Unable to determine port class instantiator: " + entry);
        }

    stringBuffer.append(TEXT_46);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_47);
    stringBuffer.append(inst.trim());
    stringBuffer.append(TEXT_48);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_49);
    
    	    if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(entry) 
                    && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(use.getUsesName())) {
	    		for (CppProperties.Property prop : properties) {
            		if (prop.getKinds().indexOf("event") != -1) {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_51);
    stringBuffer.append(CppHelper.escapeString(prop.getId()));
    stringBuffer.append(TEXT_52);
    
        		    }
    		    }

    stringBuffer.append(TEXT_53);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_54);
    
    	    }
    	}  //if (foundMatchingProvides == false)
    }
    if ((provides.size() > 0) || (uses.size() > 0)) {

    stringBuffer.append(TEXT_55);
    
}
    for (Provides pro : provides) {

    stringBuffer.append(TEXT_56);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_57);
    
    }
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

    stringBuffer.append(TEXT_58);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_59);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_60);
    
        }
    }

    stringBuffer.append(TEXT_61);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_62);
    if (autoStart) {
    stringBuffer.append(TEXT_63);
    }
    stringBuffer.append(TEXT_64);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_65);
    
    for (Provides pro : provides) {
        String entry = pro.getRepID();
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true); 
        if (intf == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String interfaceName = intf.getName();
        boolean hasPushPacketCall = false;
        boolean hasPushPacketXMLCall = false;
        boolean hasPushPacketFileCall = false;
        for (Operation op : intf.getOperations()) {
            int numParams = op.getParams().size();
            if ("pushPacket".equals(op.getName()) && "dataFile".equals(interfaceName)) {
                hasPushPacketFileCall = true;
            } else if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                hasPushPacketCall = true;
            } else if ("pushPacket".equals(op.getName()) && "dataXML".equals(interfaceName)) {
                hasPushPacketXMLCall = true;
            }
        }
        if (hasPushPacketCall || hasPushPacketXMLCall || hasPushPacketFileCall) {

    stringBuffer.append(TEXT_66);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_67);
    
        }
    }

    stringBuffer.append(TEXT_68);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_70);
    
    for (Provides pro : provides) {
        String entry = pro.getRepID();
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (intf == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        
        String interfaceName = intf.getName();
        boolean hasPushPacketCall = false;
        boolean hasPushPacketXMLCall = false;
        boolean hasPushPacketFileCall = false;
        for (Operation op : intf.getOperations()) {
            int numParams = op.getParams().size();
            if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                hasPushPacketCall = true;
                break;
            } else if ("pushPacket".equals(op.getName()) && "dataXML".equals(interfaceName)) {
                hasPushPacketXMLCall = true;
                break;
            } else if ("pushPacket".equals(op.getName()) && "dataFile".equals(interfaceName)) {
                hasPushPacketFileCall = true;
                break;
            }
        }
        if (hasPushPacketCall || hasPushPacketXMLCall || hasPushPacketFileCall) {

    stringBuffer.append(TEXT_71);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_72);
    
        }
    }

    stringBuffer.append(TEXT_73);
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_75);
    
        for (Provides pro : provides) {
            String entry = pro.getRepID();
            Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true); 
            if (intf == null) {
                throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
            }
            String nameSpace = intf.getNameSpace();
            String interfaceName = intf.getName();
        	if (MessagingPortTemplate.MESSAGECHANNEL_REPID.equals(entry)) {

    stringBuffer.append(TEXT_76);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_77);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_79);
    
        	} else {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_81);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_88);
    
        	}
        }

    stringBuffer.append(TEXT_89);
    
    }

    stringBuffer.append(TEXT_90);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_91);
    
    for (Provides pro : provides) {

    stringBuffer.append(TEXT_92);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_93);
    
    }
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

    stringBuffer.append(TEXT_94);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_95);
    
        }
    }

    stringBuffer.append(TEXT_96);
    
    if (!templ.isDevice()) {

    stringBuffer.append(TEXT_97);
    
    } else {

    stringBuffer.append(TEXT_98);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_99);
    
    }

    stringBuffer.append(TEXT_100);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_101);
    
    for (CppProperties.Property prop : properties) {
        if (prop.hasValue() && (prop instanceof CppProperties.SimpleSeqProperty)) {

    stringBuffer.append(TEXT_102);
    
            for(String v : ((CppProperties.SimpleSeqProperty)prop).getCppValues()) {

    stringBuffer.append(TEXT_103);
    stringBuffer.append(prop.getCppName());
    stringBuffer.append(TEXT_104);
    stringBuffer.append(v);
    stringBuffer.append(TEXT_105);
    
            }
        } else if (prop instanceof CppProperties.StructSequenceProperty) {

    stringBuffer.append(TEXT_106);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(prop.getCppName());
    stringBuffer.append(TEXT_108);
    stringBuffer.append(((CppProperties.StructSequenceProperty)prop).numberOfStructValues());
    stringBuffer.append(TEXT_109);
    
            Map<CppProperties.SimpleProperty, List<String>> myMap = ((CppProperties.StructSequenceProperty)prop).getValueMap(); 
            for (CppProperties.SimpleProperty simple : myMap.keySet()) {
                if (!myMap.get(simple).isEmpty()) {
                    int i = 0;
                    for (String val : myMap.get(simple)) {

    stringBuffer.append(TEXT_110);
    stringBuffer.append(prop.getCppName());
    stringBuffer.append(TEXT_111);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(simple.getCppName());
    stringBuffer.append(TEXT_113);
    stringBuffer.append(val);
    stringBuffer.append(TEXT_114);
    
                        i++;
                    }
                }
            }
        }
        

    stringBuffer.append(TEXT_115);
    stringBuffer.append(prop.getCppName());
    stringBuffer.append(TEXT_116);
    
        if (prop.hasValue()) {

    stringBuffer.append(TEXT_117);
    stringBuffer.append(prop.getCppValue());
    stringBuffer.append(TEXT_118);
    
        }

    stringBuffer.append(TEXT_119);
    stringBuffer.append(CppHelper.escapeString(prop.getId()));
    stringBuffer.append(TEXT_120);
    
        if (prop.hasName()) {

    stringBuffer.append(TEXT_121);
    stringBuffer.append(CppHelper.escapeString(prop.getName()));
    stringBuffer.append(TEXT_122);
    
        } else {

    stringBuffer.append(TEXT_123);
    
        }

    stringBuffer.append(TEXT_124);
    stringBuffer.append(CppHelper.escapeString(prop.getMode()));
    stringBuffer.append(TEXT_125);
    stringBuffer.append(CppHelper.escapeString(prop.getUnits()));
    stringBuffer.append(TEXT_126);
    stringBuffer.append(CppHelper.escapeString(prop.getAction()));
    stringBuffer.append(TEXT_127);
    stringBuffer.append(CppHelper.escapeString(prop.getKinds()));
    stringBuffer.append(TEXT_128);
    
    }

    stringBuffer.append(TEXT_129);
    stringBuffer.append(TEXT_130);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE