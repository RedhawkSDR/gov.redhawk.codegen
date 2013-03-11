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

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.Implementation;
import gov.redhawk.ide.codegen.ImplementationSettings;

	/**
    * @generated
    */

public class DMainCppTemplate
{

  protected static String nl;
  public static synchronized DMainCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DMainCppTemplate result = new DMainCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#include \"";
  protected final String TEXT_2 = ".h\"" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = "_i *devicePtr;" + NL + "" + NL + "CREATE_LOGGER(Test";
  protected final String TEXT_5 = "Device)" + NL + "" + NL + "void signal_catcher(int sig)" + NL + "{" + NL + "    // IMPORTANT Don't call exit(...) in this function" + NL + "    // issue all CORBA calls that you need for cleanup here before calling ORB shutdown" + NL + "    LOG_DEBUG(Test";
  protected final String TEXT_6 = "Device, \"Terminate signal \" << sig << \" received\")" + NL + "    if (devicePtr) {" + NL + "        devicePtr->halt();" + NL + "    } else {" + NL + "        LOG_DEBUG(Test";
  protected final String TEXT_7 = "Device, \"Device not instantiated\")" + NL + "    }" + NL + "    LOG_DEBUG(Test";
  protected final String TEXT_8 = "Device, \"Device halted\")" + NL + "}" + NL + "" + NL + "int main(int argc, char *argv[])" + NL + "{" + NL + "    struct sigaction sa;" + NL + "    sa.sa_handler = signal_catcher;" + NL + "    sa.sa_flags = 0;" + NL + "    devicePtr = 0;" + NL + "" + NL + "    Device_impl::start_device(&devicePtr, sa, argc, argv);" + NL + "}";
  protected final String TEXT_9 = NL;

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
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
} 