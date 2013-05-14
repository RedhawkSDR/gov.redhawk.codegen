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

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.MessagingPortTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.PropertyChangeEventPortTemplate;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.idl.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.scd.SupportsInterface;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

/**
 * @generated
 */
public class PullResourceBaseHTemplate
{

  protected static String nl;
  public static synchronized PullResourceBaseHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullResourceBaseHTemplate result = new PullResourceBaseHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef ";
  protected final String TEXT_2 = "_IMPL_BASE_H" + NL + "#define ";
  protected final String TEXT_3 = "_IMPL_BASE_H" + NL + "" + NL + "#include <boost/thread.hpp>" + NL + "#include <ossie/Resource_impl.h>";
  protected final String TEXT_4 = NL + "#include \"CF/AggregateDevices.h\"";
  protected final String TEXT_5 = NL + "#include \"ossie/AggregateDevice_impl.h\"";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL + "#include \"ossie/";
  protected final String TEXT_8 = "Device_impl.h\"";
  protected final String TEXT_9 = NL + "#include \"port_impl.h\"";
  protected final String TEXT_10 = NL + "#include \"struct_props.h\"";
  protected final String TEXT_11 = NL + NL + "#define NOOP 0" + NL + "#define FINISH -1" + NL + "#define NORMAL 1" + NL + "" + NL + "class ";
  protected final String TEXT_12 = "_base;" + NL;
  protected final String TEXT_13 = NL + "#include <ossie/prop_helpers.h>";
  protected final String TEXT_14 = NL + NL + "template < typename TargetClass >" + NL + "class ProcessThread" + NL + "{" + NL + "    public:" + NL + "        ProcessThread(TargetClass *_target, float _delay) :" + NL + "            target(_target)" + NL + "        {" + NL + "            _mythread = 0;" + NL + "            _thread_running = false;" + NL + "            _udelay = (__useconds_t)(_delay * 1000000);" + NL + "        };" + NL + "" + NL + "        // kick off the thread" + NL + "        void start() {" + NL + "            if (_mythread == 0) {" + NL + "                _thread_running = true;" + NL + "                _mythread = new boost::thread(&ProcessThread::run, this);" + NL + "            }" + NL + "        };" + NL + "" + NL + "        // manage calls to target's service function" + NL + "        void run() {" + NL + "            int state = NORMAL;" + NL + "            while (_thread_running and (state != FINISH)) {" + NL + "                state = target->serviceFunction();" + NL + "                if (state == NOOP) usleep(_udelay);" + NL + "            }" + NL + "        };" + NL + "" + NL + "        // stop thread and wait for termination" + NL + "        bool release(unsigned long secs = 0, unsigned long usecs = 0) {" + NL + "            _thread_running = false;" + NL + "            if (_mythread)  {" + NL + "                if ((secs == 0) and (usecs == 0)){" + NL + "                    _mythread->join();" + NL + "                } else {" + NL + "                    boost::system_time waitime= boost::get_system_time() + boost::posix_time::seconds(secs) +  boost::posix_time::microseconds(usecs) ;" + NL + "                    if (!_mythread->timed_join(waitime)) {" + NL + "                        return 0;" + NL + "                    }" + NL + "                }" + NL + "                delete _mythread;" + NL + "                _mythread = 0;" + NL + "            }" + NL + "    " + NL + "            return 1;" + NL + "        };" + NL + "" + NL + "        virtual ~ProcessThread(){" + NL + "            if (_mythread != 0) {" + NL + "                release(0);" + NL + "                _mythread = 0;" + NL + "            }" + NL + "        };" + NL + "" + NL + "        void updateDelay(float _delay) { _udelay = (__useconds_t)(_delay * 1000000); };" + NL + "" + NL + "    private:" + NL + "        boost::thread *_mythread;" + NL + "        bool _thread_running;" + NL + "        TargetClass *target;" + NL + "        __useconds_t _udelay;" + NL + "        boost::condition_variable _end_of_run;" + NL + "        boost::mutex _eor_mutex;" + NL + "};" + NL + "" + NL + "class ";
  protected final String TEXT_15 = "_base : public ";
  protected final String TEXT_16 = NL + "{";
  protected final String TEXT_17 = NL + "    friend class ";
  protected final String TEXT_18 = "_";
  protected final String TEXT_19 = "_In_i;";
  protected final String TEXT_20 = NL + "    friend class ";
  protected final String TEXT_21 = "_";
  protected final String TEXT_22 = "_Out_i;";
  protected final String TEXT_23 = NL + "    friend class PropertyChangeEventPort_i;";
  protected final String TEXT_24 = NL + NL + "    public:";
  protected final String TEXT_25 = " ";
  protected final String TEXT_26 = NL + "        ";
  protected final String TEXT_27 = "_base(const char *uuid, const char *label);";
  protected final String TEXT_28 = NL + "        ";
  protected final String TEXT_29 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);";
  protected final String TEXT_30 = NL + "        ";
  protected final String TEXT_31 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev);";
  protected final String TEXT_32 = NL + "        ";
  protected final String TEXT_33 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities);";
  protected final String TEXT_34 = NL + "        ";
  protected final String TEXT_35 = "_base(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev);";
  protected final String TEXT_36 = NL + NL + "        void start() throw (CF::Resource::StartError, CORBA::SystemException);" + NL + "" + NL + "        void stop() throw (CF::Resource::StopError, CORBA::SystemException);" + NL;
  protected final String TEXT_37 = NL + "        CORBA::Object_ptr getPort(const char* _id) throw (CF::PortSupplier::UnknownPort, CORBA::SystemException);" + NL;
  protected final String TEXT_38 = NL + "        void releaseObject() throw (CF::LifeCycle::ReleaseError, CORBA::SystemException);" + NL + "" + NL + "        void initialize() throw (CF::LifeCycle::InitializeError, CORBA::SystemException);" + NL + "" + NL + "        void loadProperties();" + NL + "" + NL + "        virtual int serviceFunction() = 0;" + NL;
  protected final String TEXT_39 = NL + "        bool compareSRI(BULKIO::StreamSRI &SRI_1, BULKIO::StreamSRI &SRI_2){" + NL + "            if (SRI_1.hversion != SRI_2.hversion)" + NL + "                return false;" + NL + "            if (SRI_1.xstart != SRI_2.xstart)" + NL + "                return false;" + NL + "            if (SRI_1.xdelta != SRI_2.xdelta)" + NL + "                return false;" + NL + "            if (SRI_1.xunits != SRI_2.xunits)" + NL + "                return false;" + NL + "            if (SRI_1.subsize != SRI_2.subsize)" + NL + "                return false;" + NL + "            if (SRI_1.ystart != SRI_2.ystart)" + NL + "                return false;" + NL + "            if (SRI_1.ydelta != SRI_2.ydelta)" + NL + "                return false;" + NL + "            if (SRI_1.yunits != SRI_2.yunits)" + NL + "                return false;" + NL + "            if (SRI_1.mode != SRI_2.mode)" + NL + "                return false;" + NL + "            if (strcmp(SRI_1.streamID, SRI_2.streamID) != 0)" + NL + "                return false;" + NL + "            if (SRI_1.keywords.length() != SRI_2.keywords.length())" + NL + "                return false;" + NL + "            std::string action = \"eq\";" + NL + "            for (unsigned int i=0; i<SRI_1.keywords.length(); i++) {" + NL + "                if (strcmp(SRI_1.keywords[i].id, SRI_2.keywords[i].id)) {" + NL + "                    return false;" + NL + "                }" + NL + "                if (!ossie::compare_anys(SRI_1.keywords[i].value, SRI_2.keywords[i].value, action)) {" + NL + "                    return false;" + NL + "                }" + NL + "            }" + NL + "            if (SRI_1.blocking != SRI_2.blocking) {" + NL + "                return false;" + NL + "            }" + NL + "            return true;" + NL + "        }" + NL + "        ";
  protected final String TEXT_40 = NL + "    protected:" + NL + "        ProcessThread<";
  protected final String TEXT_41 = "_base> *serviceThread; " + NL + "        boost::mutex serviceThreadLock;  ";
  protected final String TEXT_42 = NL + NL + "        // Member variables exposed as properties";
  protected final String TEXT_43 = NL + "        ";
  protected final String TEXT_44 = " ";
  protected final String TEXT_45 = ";";
  protected final String TEXT_46 = NL + NL + "        // Ports";
  protected final String TEXT_47 = NL + "        MessageConsumerPort *";
  protected final String TEXT_48 = ";";
  protected final String TEXT_49 = NL + "        ";
  protected final String TEXT_50 = "_";
  protected final String TEXT_51 = "_In_i *";
  protected final String TEXT_52 = ";";
  protected final String TEXT_53 = NL + "        PropertyEventSupplier *";
  protected final String TEXT_54 = ";";
  protected final String TEXT_55 = NL + "        ";
  protected final String TEXT_56 = "_";
  protected final String TEXT_57 = "_Out_i *";
  protected final String TEXT_58 = ";";
  protected final String TEXT_59 = NL + "    " + NL + "    private:" + NL + "        void construct();" + NL + "" + NL + "};" + NL + "#endif";
  protected final String TEXT_60 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    List<CppProperties.Property> properties = CppProperties.getProperties(softPkg);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    String deviceType = "";
    String baseClass = "Resource_impl";
    boolean hasPorts = (uses.size() > 0) || (provides.size() > 0);
    boolean hasPushPacketCall = false;
    boolean aggregateDevice = false;

    // TODO: Refactor this long block of code (and other similar blocks) into one handy place that can just give you an enum
	final List<SupportsInterface> supportedInterfaces = softPkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface();
    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/Device:1.0")) {
            deviceType = "";
            baseClass = "Device_impl";
            break;
        }
    }

    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/LoadableDevice:1.0")) {
            deviceType = "Loadable";
            baseClass = "LoadableDevice_impl";
            break;
        }
    }

    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/ExecutableDevice:1.0")) {
            deviceType = "Executable"; 
            baseClass = "ExecutableDevice_impl";
            break;
        }
    }
    
    for (SupportsInterface inter : supportedInterfaces) {
        if (inter.getRepId().equals("IDL:CF/AggregateDevice:1.0")) {
            aggregateDevice = true;
            
	        if ("Executable".equals(deviceType)) {
	            baseClass = "virtual POA_CF::AggregateExecutableDevice, public " + baseClass;
	        } else if ("Loadable".equals(deviceType)) {
	            baseClass = "virtual POA_CF::AggregateLoadableDevice, public " + baseClass;
	        } else {
	            baseClass = "virtual POA_CF::AggregatePlainDevice, public " + baseClass;
	        }
	        baseClass += ", public AggregateDevice_impl ";
	        break;
        }
   }
    
    HashSet<String> usesList = new HashSet<String>();
    boolean hasPropChangePort = false;
    for (Uses entry : uses) {
        String intName = entry.getRepID();
        if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(intName) 
                && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(entry.getUsesName())) {
            hasPropChangePort = true;
        } else {
            usesList.add(intName);
        }
    }
    
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        String intName = entry.getRepID();
        providesList.add(intName);
    }

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        for (Operation op : iface.getOperations()) {
            if ("pushPacket".equals(op.getName())) {
                hasPushPacketCall = true;
                break;
            }
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_3);
    if (aggregateDevice) {
    stringBuffer.append(TEXT_4);
    }
    if (aggregateDevice) {
    stringBuffer.append(TEXT_5);
    }
    stringBuffer.append(TEXT_6);
    if (templ.isDevice()) {
    stringBuffer.append(TEXT_7);
    stringBuffer.append(deviceType);
    stringBuffer.append(TEXT_8);
    }
    if (hasPorts) {
    stringBuffer.append(TEXT_9);
    }
    List<Struct> structProps = new ArrayList<Struct>();
    structProps.addAll(softPkg.getPropertyFile().getProperties().getStruct());
    for (StructSequence structSequence : softPkg.getPropertyFile().getProperties().getStructSequence()) {
        if (structSequence.getStruct() != null) {
            structProps.add(structSequence.getStruct());
        }
    }
    if (!structProps.isEmpty()) {
    stringBuffer.append(TEXT_10);
    }
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    if (hasPushPacketCall) {
    stringBuffer.append(TEXT_13);
    }
    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(baseClass);
    stringBuffer.append(TEXT_16);
    

    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = iface.getNameSpace();
        String interfaceName = iface.getName();

    stringBuffer.append(TEXT_17);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_19);
    
    }

    for (String entry : usesList) {
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (intf == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        String nameSpace = intf.getNameSpace();
        String interfaceName = intf.getName();

    stringBuffer.append(TEXT_20);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_22);
    
    }
    if (hasPropChangePort) {

    stringBuffer.append(TEXT_23);
    
    }

    stringBuffer.append(TEXT_24);
     if (!templ.isDevice()) {
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_27);
     } else { 
    stringBuffer.append(TEXT_28);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_35);
    }
    stringBuffer.append(TEXT_36);
    
    if ((uses.size() > 0) || (provides.size() > 0)) {

    stringBuffer.append(TEXT_37);
    
    }

    stringBuffer.append(TEXT_38);
    if (hasPushPacketCall) {
    stringBuffer.append(TEXT_39);
    }
    stringBuffer.append(TEXT_40);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_41);
    if (properties.size() > 0) { 
    stringBuffer.append(TEXT_42);
    }
      for (CppProperties.Property prop : properties) {

    stringBuffer.append(TEXT_43);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_44);
    stringBuffer.append(prop.getCppName());
    stringBuffer.append(TEXT_45);
    
    }
    if (hasPorts) { 
    stringBuffer.append(TEXT_46);
    }
    
    for (Provides pro : provides) {
        String entry = pro.getRepID();
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (intf == null) { 
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        } 
        String nameSpace = intf.getNameSpace();
        String interfaceName = intf.getName();
        if (MessagingPortTemplate.MESSAGECHANNEL_REPID.equals(entry)) {

    stringBuffer.append(TEXT_47);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_48);
    
        } else {

    stringBuffer.append(TEXT_49);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_52);
    
		}
    }
    
    for (Uses use : uses) {
        String entry = use.getRepID();
        Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true); 
        if (intf == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry)); 
        }
        String nameSpace = intf.getNameSpace();
        String interfaceName = intf.getName();
        // Loop over provides ports to see if there is a matching interface and port name for the current uses port
        // If so, ignore the uses port
        // This is to support bi-directional ports
        boolean foundMatchingProvides = false;
        for (Provides pro : provides) {
            String entryProvides = pro.getRepID();
            Interface intfProvides = IdlUtil.getInstance().getInterface(search_paths, entryProvides.split(":")[1], true);
            if (intfProvides == null) { 
                throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entryProvides));
            } 
            String nameSpaceProvides = intfProvides.getNameSpace();
            String interfaceNameProvides = intfProvides.getName();
            if (entry.equals(entryProvides) && use.getUsesName().equals(pro.getProvidesName())) {
                foundMatchingProvides = true;
                break;
            }
        } // for (Provides pro : provides)
        if(foundMatchingProvides == false){
            if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(entry) 
                    && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(use.getUsesName())) {

    stringBuffer.append(TEXT_53);
    stringBuffer.append(PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME);
    stringBuffer.append(TEXT_54);
    
            } else {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(use.getUsesName());
    stringBuffer.append(TEXT_58);
    
            }
        } // if(foundMatchingProvides == false)
    } // for (Uses use : uses)

    stringBuffer.append(TEXT_59);
    stringBuffer.append(TEXT_60);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE