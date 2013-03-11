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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.push;

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.Implementation;
import gov.redhawk.ide.codegen.ImplementationSettings;

	/**
    * @generated
    */

public class MainCppTemplate
{

  protected static String nl;
  public static synchronized MainCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    MainCppTemplate result = new MainCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include <iostream>" + NL + "#include \"ossie/ossieSupport.h\"" + NL + "" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"" + NL + "" + NL + "" + NL + "int main(int argc, char* argv[])" + NL + "" + NL + "{" + NL + "    ossie::ORB *orb = new ossie::ORB;" + NL + "    omni_mutex component_running_mutex;" + NL + "    omni_condition *component_running = new omni_condition(&component_running_mutex);" + NL + "    " + NL + "    std::string naming_context_ior(\"\");" + NL + "    std::string component_identifier(\"\");" + NL + "    std::string name_binding(\"\");" + NL + "" + NL + "    for (int i=0; i < argc; i++) {" + NL + "        if (strcmp(\"NAMING_CONTEXT_IOR\", argv[i]) == 0) {" + NL + "            naming_context_ior = argv[++i];" + NL + "        } else if (strcmp(\"COMPONENT_IDENTIFIER\", argv[i]) == 0) {" + NL + "            component_identifier = argv[++i];" + NL + "        } else if (strcmp(\"NAME_BINDING\", argv[i]) == 0) {" + NL + "            name_binding = argv[++i];" + NL + "        }" + NL + "    }" + NL + "" + NL + "    if ((name_binding == \"\") or (component_identifier == \"\") or (naming_context_ior == \"\")) {" + NL + "        exit(-1);" + NL + "    }" + NL;
  protected final String TEXT_3 = NL + "    ";
  protected final String TEXT_4 = "_i* ";
  protected final String TEXT_5 = "_servant;" + NL + "    CF::Resource_var ";
  protected final String TEXT_6 = "_var;" + NL + "" + NL + "    // Create the ";
  protected final String TEXT_7 = " component servant and object reference" + NL;
  protected final String TEXT_8 = NL + "    ";
  protected final String TEXT_9 = "_servant = new ";
  protected final String TEXT_10 = "_i(component_identifier.c_str(), component_running, name_binding.c_str(), orb);" + NL + "    orb->poa->activate_object(";
  protected final String TEXT_11 = "_servant);";
  protected final String TEXT_12 = NL + "    ";
  protected final String TEXT_13 = "_var = ";
  protected final String TEXT_14 = "_servant->_this();" + NL + "    " + NL + "    CORBA::Object_var applicationObject = orb->orb->string_to_object(naming_context_ior.c_str());" + NL + "    CosNaming::NamingContext_ptr applicationContext = CosNaming::NamingContext::_narrow(applicationObject);" + NL + "    " + NL + "    orb->bind_object_to_name((CORBA::Object_ptr) ";
  protected final String TEXT_15 = "_var, applicationContext, name_binding.c_str());" + NL + "" + NL + "    // This bit is ORB specific" + NL + "    // omniorb is threaded and the servants are running at this point" + NL + "    // so we block on the condition" + NL + "    // The releaseObject method clears the condition and the component exits" + NL + "    component_running->wait();" + NL + "    orb->orb->shutdown(0);" + NL + "" + NL + "}";
  protected final String TEXT_16 = NL;

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
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    return stringBuffer.toString();
  }
} 