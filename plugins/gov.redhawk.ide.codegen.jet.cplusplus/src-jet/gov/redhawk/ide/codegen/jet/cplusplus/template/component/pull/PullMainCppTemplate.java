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

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import gov.redhawk.ide.codegen.ImplementationSettings;

	/**
    * @generated
    */

public class PullMainCppTemplate
{

  protected static String nl;
  public static synchronized PullMainCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullMainCppTemplate result = new PullMainCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include <iostream>" + NL + "#include \"ossie/ossieSupport.h\"" + NL + "" + NL + "#include \"";
  protected final String TEXT_2 = ".h\"" + NL;
  protected final String TEXT_3 = NL + " ";
  protected final String TEXT_4 = "int main(int argc, char* argv[])" + NL + "{";
  protected final String TEXT_5 = NL + "    ";
  protected final String TEXT_6 = "_i* ";
  protected final String TEXT_7 = "_servant;" + NL + "    Resource_impl::start_component(";
  protected final String TEXT_8 = "_servant, argc, argv);" + NL + "    return 0;" + NL + "}";
  protected final String TEXT_9 = NL + " ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "_i *devicePtr;" + NL + "" + NL + "void signal_catcher(int sig)" + NL + "{" + NL + "    // IMPORTANT Don't call exit(...) in this function" + NL + "    // issue all CORBA calls that you need for cleanup here before calling ORB shutdown" + NL + "    if (devicePtr) {" + NL + "        devicePtr->halt();" + NL + "    }" + NL + "}" + NL + "" + NL + "int main(int argc, char *argv[])" + NL + "{" + NL + "    struct sigaction sa;" + NL + "    sa.sa_handler = signal_catcher;" + NL + "    sa.sa_flags = 0;" + NL + "    devicePtr = 0;" + NL + "" + NL + "    Device_impl::start_device(&devicePtr, sa, argc, argv);" + NL + "    return 0;" + NL + "}";
  protected final String TEXT_12 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    ImplementationSettings implSettings = templ.getImplSettings();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    
    String deviceType = softPkg.getDescriptor().getComponent().getComponentType();
    boolean isDevice = false;
    if (deviceType.equalsIgnoreCase("device")) {
        deviceType = "";
        isDevice = true;
    } else if (deviceType.equalsIgnoreCase("executabledevice")) {
        deviceType = "Executable";
        isDevice = true;
    } else if (deviceType.equalsIgnoreCase("loadabledevice")) {
        deviceType = "Loadable";
        isDevice = true;
    } else { 
        deviceType = "";
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_2);
    if (!isDevice) {
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    } else {
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    }
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
} 