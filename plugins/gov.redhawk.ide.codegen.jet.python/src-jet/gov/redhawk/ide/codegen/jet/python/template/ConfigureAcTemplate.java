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
package gov.redhawk.ide.codegen.jet.python.template;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

	/**
    * @generated
    */

public class ConfigureAcTemplate
{

  protected static String nl;
  public static synchronized ConfigureAcTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ConfigureAcTemplate result = new ConfigureAcTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "AC_INIT(";
  protected final String TEXT_2 = ", ";
  protected final String TEXT_3 = ")" + NL + "AM_INIT_AUTOMAKE(nostdinc)" + NL + "" + NL + "AC_PROG_INSTALL" + NL + "" + NL + "AC_CORBA_ORB" + NL + "OSSIE_CHECK_OSSIE" + NL + "OSSIE_SDRROOT_AS_PREFIX" + NL + "AM_PATH_PYTHON([2.3])" + NL + "" + NL + "PKG_CHECK_MODULES([OSSIE], [ossie >= 1.8])" + NL + "AC_CHECK_PYMODULE(ossie, [], [AC_MSG_ERROR([the python ossie module is required])])" + NL + "PKG_CHECK_MODULES([OMNIORB], [omniORB4 >= 4.0.0])" + NL + "AC_CHECK_PYMODULE(omniORB, [], [AC_MSG_ERROR([the python omniORB module is required])])" + NL;
  protected final String TEXT_4 = NL + "PKG_CHECK_MODULES(";
  protected final String TEXT_5 = "Interfaces, ";
  protected final String TEXT_6 = "Interfaces";
  protected final String TEXT_7 = ")";
  protected final String TEXT_8 = NL + "AC_CHECK_PYMODULE(bulkio.bulkioInterfaces, [], [AC_MSG_ERROR([the python bulkio.bulkioInterfaces module is required])])";
  protected final String TEXT_9 = NL + "AC_CHECK_PYMODULE(redhawk.redhawkInterfaces, [], [AC_MSG_ERROR([the python redhawk.redhawkInterfaces module is required])])";
  protected final String TEXT_10 = NL + "AC_CHECK_PYMODULE(redhawk.";
  protected final String TEXT_11 = "Interfaces, [], [AC_MSG_ERROR([the python redhawk.";
  protected final String TEXT_12 = "Interfaces module is required])])";
  protected final String TEXT_13 = NL + NL + "AC_CONFIG_FILES(Makefile)" + NL + "" + NL + "AC_OUTPUT";
  protected final String TEXT_14 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softpkg = (SoftPkg) impl.eContainer();
    
    // Determine softpkg version, set to 1.0.0 if none exists
    String version = "1.0.0";
    String softpkgVersion = null;
    if (softpkg != null) {
        softpkgVersion = softpkg.getVersion();
    }  
    if (softpkgVersion != null && !softpkgVersion.equals("")) {
        version = softpkgVersion;
    }

    // Determine what interfaces we require
    Map<String, String> ifaceNameAndVer = new HashMap<String, String>();
    List<Interface> interfaces = softpkg.getDescriptor().getComponent().getInterfaces().getInterface();
    Pattern idlPattern = Pattern.compile("^IDL:(\\w+)/");
    for (Interface iface : interfaces) {
        Matcher match = idlPattern.matcher(iface.getRepid());
        if (match.find()) {
            String interfaceNamespace = match.group(1);
            if ("BULKIO".equals(interfaceNamespace)) {
                ifaceNameAndVer.put("bulkio", " >= 1.8");
            } else if ("REDHAWK".equals(interfaceNamespace)) {
                ifaceNameAndVer.put("redhawk", " >= 1.2.0");
            } else if (! "CF".equals(interfaceNamespace)) {
                ifaceNameAndVer.put(interfaceNamespace.toLowerCase(), "");
            }
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.safeGetImplementationName(impl, implSettings));
    stringBuffer.append(TEXT_2);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_3);
    
    for (String interfaceNamespace : ifaceNameAndVer.keySet()) {
		if (interfaceNamespace.equals("extendedevent")) {  // do not add a module (extendedevent is distributed as a standard part of REDHAWK)
		    continue;
		}

    stringBuffer.append(TEXT_4);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(ifaceNameAndVer.get(interfaceNamespace));
    stringBuffer.append(TEXT_7);
    
        if (interfaceNamespace.equals("bulkio")) { 
    stringBuffer.append(TEXT_8);
    
        } else if (interfaceNamespace.equals("redhawk")) { 
    stringBuffer.append(TEXT_9);
    
        } else { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_12);
    
        } 
    
    }

    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
} 