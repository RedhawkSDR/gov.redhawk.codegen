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
package gov.redhawk.ide.codegen.jet.cplusplus.template.device.push;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import java.util.Arrays;
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
public class DResourceSupportCppTemplate
{

  protected static String nl;
  public static synchronized DResourceSupportCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DResourceSupportCppTemplate result = new DResourceSupportCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include <iostream>" + NL + "#include <fstream>" + NL + "#include <sstream>";
  protected final String TEXT_2 = NL + NL + "#include \"";
  protected final String TEXT_3 = ".h\"" + NL;
  protected final String TEXT_4 = NL + "#include \"port_impl.h\"";
  protected final String TEXT_5 = NL + "#include <uuid/uuid.h>" + NL + "" + NL + "" + NL + "/*******************************************************************************************" + NL + "" + NL + "\tThese are auto-generated support functions. You should never have to mess with these" + NL + "     functions." + NL + "" + NL + "*******************************************************************************************/";
  protected final String TEXT_6 = NL + "CORBA::Object_ptr ";
  protected final String TEXT_7 = "_i::getPort(const char* _id) throw (CORBA::SystemException, CF::PortSupplier::UnknownPort)" + NL + "{" + NL + "" + NL + "    std::map<std::string, Port_Provides_base_impl *>::iterator p_in = inPorts.find(std::string(_id));" + NL + "    if (p_in != inPorts.end()) {" + NL;
  protected final String TEXT_8 = NL + "        if (!strcmp(_id,\"";
  protected final String TEXT_9 = "\")) {";
  protected final String TEXT_10 = NL + "            ";
  protected final String TEXT_11 = "_";
  protected final String TEXT_12 = "_In_i *ptr = dynamic_cast<";
  protected final String TEXT_13 = "_";
  protected final String TEXT_14 = "_In_i *>(p_in->second);" + NL + "            if (ptr) {" + NL + "                return ";
  protected final String TEXT_15 = "::";
  protected final String TEXT_16 = "::_duplicate(ptr->_this());" + NL + "            }" + NL + "        }";
  protected final String TEXT_17 = NL + "    }" + NL + "" + NL + "    std::map<std::string, CF::Port_var>::iterator p_out = outPorts_var.find(std::string(_id));" + NL + "    if (p_out != outPorts_var.end()) {" + NL + "        return CF::Port::_duplicate(p_out->second);" + NL + "    }" + NL + "" + NL + "    throw (CF::PortSupplier::UnknownPort());" + NL + "}";
  protected final String TEXT_18 = NL + NL + "void ";
  protected final String TEXT_19 = "_i::releaseObject() throw (CORBA::SystemException, CF::LifeCycle::ReleaseError)" + NL + "{";
  protected final String TEXT_20 = NL + "    while ((*this->devices()).length() > 0) {" + NL + "        CF::Device_ptr dev = (*this->devices())[0];" + NL + "        dev->releaseObject();" + NL + "    }" + NL + "    ";
  protected final String TEXT_21 = NL + NL + "    // deactivate ports" + NL + "    releaseInPorts();" + NL + "    releaseOutPorts();" + NL + "" + NL + "    // This function clears the component running condition so main shuts down everything" + NL + "    Device_impl::releaseObject();" + NL + "}" + NL;
  protected final String TEXT_22 = NL + "        bool compareSRI(BULKIO::StreamSRI &SRI_1, BULKIO::StreamSRI &SRI_2){" + NL + "            if (SRI_1.hversion != SRI_2.hversion)" + NL + "                return false;" + NL + "            if (SRI_1.xstart != SRI_2.xstart)" + NL + "                return false;" + NL + "            if (SRI_1.xdelta != SRI_2.xdelta)" + NL + "                return false;" + NL + "            if (SRI_1.xunits != SRI_2.xunits)" + NL + "                return false;" + NL + "            if (SRI_1.subsize != SRI_2.subsize)" + NL + "                return false;" + NL + "            if (SRI_1.ystart != SRI_2.ystart)" + NL + "                return false;" + NL + "            if (SRI_1.ydelta != SRI_2.ydelta)" + NL + "                return false;" + NL + "            if (SRI_1.yunits != SRI_2.yunits)" + NL + "                return false;" + NL + "            if (SRI_1.mode != SRI_2.mode)" + NL + "                return false;" + NL + "            if (strcmp(SRI_1.streamID, SRI_2.streamID) != 0)" + NL + "                return false;" + NL + "            if (SRI_1.keywords.length() != SRI_2.keywords.length())" + NL + "                return false;" + NL + "            std::string action = \"eq\";" + NL + "            for (unsigned int i=0; i<SRI_1.keywords.length(); i++) {" + NL + "                if (strcmp(SRI_1.keywords[i].id, SRI_2.keywords[i].id)) {" + NL + "                    return false;" + NL + "                }" + NL + "                if (!ossie::compare_anys(SRI_1.keywords[i].value, SRI_2.keywords[i].value, action)) {" + NL + "                    return false;" + NL + "                }" + NL + "            }" + NL + "            return true;" + NL + "        }" + NL + "        ";
  protected final String TEXT_23 = NL + NL + "void ";
  protected final String TEXT_24 = "_i::loadProperties()" + NL + "{";
  protected final String TEXT_25 = NL + "    propSet[\"";
  protected final String TEXT_26 = "\"].id = \"";
  protected final String TEXT_27 = "\";";
  protected final String TEXT_28 = NL + "    propSet[\"";
  protected final String TEXT_29 = "\"].name = \"";
  protected final String TEXT_30 = "\";";
  protected final String TEXT_31 = NL + "    propSet[\"";
  protected final String TEXT_32 = "\"].type = ";
  protected final String TEXT_33 = ";";
  protected final String TEXT_34 = NL + "    propSet[\"";
  protected final String TEXT_35 = "\"].mode = \"";
  protected final String TEXT_36 = "\";";
  protected final String TEXT_37 = NL + "    propSet[\"";
  protected final String TEXT_38 = "\"].units = \"";
  protected final String TEXT_39 = "\";";
  protected final String TEXT_40 = NL + "    propSet[\"";
  protected final String TEXT_41 = "\"].action = \"";
  protected final String TEXT_42 = "\";";
  protected final String TEXT_43 = NL + "    propSet[\"";
  protected final String TEXT_44 = "\"].baseProperty.id = \"";
  protected final String TEXT_45 = "\";";
  protected final String TEXT_46 = NL + "    propSet[\"";
  protected final String TEXT_47 = "\"].baseProperty.value <<= ";
  protected final String TEXT_48 = "\"";
  protected final String TEXT_49 = "\";";
  protected final String TEXT_50 = "CORBA::Any::from_boolean(";
  protected final String TEXT_51 = ");";
  protected final String TEXT_52 = "CORBA::Any::from_char('";
  protected final String TEXT_53 = "');";
  protected final String TEXT_54 = "(CORBA::LongLong)";
  protected final String TEXT_55 = ";";
  protected final String TEXT_56 = "(";
  protected final String TEXT_57 = ")";
  protected final String TEXT_58 = ";";
  protected final String TEXT_59 = NL + "    propSet[\"";
  protected final String TEXT_60 = "\"].kinds.resize(";
  protected final String TEXT_61 = ");";
  protected final String TEXT_62 = NL + "    propSet[\"";
  protected final String TEXT_63 = "\"].kinds[";
  protected final String TEXT_64 = "] = \"";
  protected final String TEXT_65 = "\";";
  protected final String TEXT_66 = "\t" + NL + "}";
  protected final String TEXT_67 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Simple> simpleList = softPkg.getPropertyFile().getProperties().getSimple();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    CppHelper _cppHelper = new CppHelper();
    boolean hasPushPacketCall = false;
    String deviceType = softPkg.getDescriptor().getComponent().getComponentType();
    boolean aggregateDevice = false;
    if (deviceType == RedhawkIdePreferenceConstants.LOADABLE_DEVICE.toLowerCase()) {
        deviceType = "Loadable";
    }
    for (SupportsInterface inter : softPkg.getDescriptor().getComponent().getComponentFeatures().getSupportsInterface()) {
        if (inter.getSupportsName().contains(RedhawkIdePreferenceConstants.AGGREGATE_DEVICE)) {
            aggregateDevice = true;
        }
    }

    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_3);
    
    if ((uses.size() > 0) || (provides.size() > 0)) { 

    stringBuffer.append(TEXT_4);
    
    }

    stringBuffer.append(TEXT_5);
    
    if ((uses.size() > 0) || (provides.size() > 0)) { 

    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    
        for (Provides pro : provides) {
            String entry = pro.getRepID();
            Interface intf = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true); 
            if (intf == null) {
                throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
            }
            String nameSpace = intf.getNameSpace();
            String interfaceName = intf.getName();

    stringBuffer.append(TEXT_8);
    stringBuffer.append(pro.getProvidesName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(nameSpace);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(interfaceName);
    stringBuffer.append(TEXT_16);
    
        }

    stringBuffer.append(TEXT_17);
    
    } // end if has ports

    stringBuffer.append(TEXT_18);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_19);
    
    if (aggregateDevice) {

    stringBuffer.append(TEXT_20);
    
    }

    stringBuffer.append(TEXT_21);
    
    if (hasPushPacketCall) {

    stringBuffer.append(TEXT_22);
    
    }

    stringBuffer.append(TEXT_23);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_24);
    
    for (Simple simple : simpleList) { 

    
        if (simple.getId() != null) { 

    stringBuffer.append(TEXT_25);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(simple.getId());
    stringBuffer.append(TEXT_27);
    
        }
        if (simple.getName() != null) {

    stringBuffer.append(TEXT_28);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_30);
    
        }
        if (simple.getType() != null) {

    stringBuffer.append(TEXT_31);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_32);
    stringBuffer.append(_cppHelper.convertType(simple.getType().getName()));
    stringBuffer.append(TEXT_33);
    
        }
        if (simple.getMode() != null) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_35);
    stringBuffer.append(simple.getMode());
    stringBuffer.append(TEXT_36);
    
        }
        if (simple.getUnits() != null) {

    stringBuffer.append(TEXT_37);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_38);
    stringBuffer.append(simple.getUnits());
    stringBuffer.append(TEXT_39);
    
        }
        if (simple.getAction() != null) {

    stringBuffer.append(TEXT_40);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(simple.getAction().getType());
    stringBuffer.append(TEXT_42);
    
        }
        if (simple.getId() != null) {

    stringBuffer.append(TEXT_43);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_44);
    stringBuffer.append(simple.getId());
    stringBuffer.append(TEXT_45);
    
        }
        if (simple.getValue() != null) { 

    stringBuffer.append(TEXT_46);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_47);
    
            if (simple.getType().toString().equals("string")) {
    
    stringBuffer.append(TEXT_48);
    stringBuffer.append(simple.getValue());
    stringBuffer.append(TEXT_49);
    
            }  else if (simple.getType().toString().equals("boolean")) { 
    
    stringBuffer.append(TEXT_50);
    stringBuffer.append(simple.getValue().toLowerCase());
    stringBuffer.append(TEXT_51);
    
            }  else if (simple.getType().toString().equals("char")) { 
    
    stringBuffer.append(TEXT_52);
    stringBuffer.append(simple.getValue().toLowerCase());
    stringBuffer.append(TEXT_53);
    
            }  else if (simple.getType().toString().equals("longlong")) { 
    
    stringBuffer.append(TEXT_54);
    stringBuffer.append(simple.getValue());
    stringBuffer.append(TEXT_55);
    
            } else { 
    
    stringBuffer.append(TEXT_56);
    stringBuffer.append(simple.getType());
    stringBuffer.append(TEXT_57);
    stringBuffer.append(simple.getValue());
    stringBuffer.append(TEXT_58);
     
            }
        } 
        if (simple.getKind().size() > 0) { 

    stringBuffer.append(TEXT_59);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_60);
    stringBuffer.append(simple.getKind().size());
    stringBuffer.append(TEXT_61);
    
        } 
        for (int i = 0; i < simple.getKind().size(); i++) { 

    stringBuffer.append(TEXT_62);
    stringBuffer.append(simple.getName());
    stringBuffer.append(TEXT_63);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(simple.getKind().get(i).getType());
    stringBuffer.append(TEXT_65);
    
        }
    }

    stringBuffer.append(TEXT_66);
    stringBuffer.append(TEXT_67);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE