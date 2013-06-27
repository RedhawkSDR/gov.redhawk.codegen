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
package gov.redhawk.ide.codegen.jet.java.template;

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.resources.IResource;

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
  protected final String TEXT_1 = "AC_INIT(";
  protected final String TEXT_2 = ", ";
  protected final String TEXT_3 = ")" + NL + "AM_INIT_AUTOMAKE(nostdinc)" + NL + "" + NL + "AC_CORBA_ORB" + NL + "OSSIE_CHECK_OSSIE" + NL + "OSSIE_SDRROOT_AS_PREFIX" + NL + "" + NL + "export PKG_CONFIG_PATH=\"$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig\"" + NL + "PKG_CHECK_MODULES([OSSIE], [ossie >= 1.8])" + NL + "" + NL + "AC_CHECK_PROG([IDLJ], [idlj], [idlj], [AC_MSG_ERROR([cannot find idlj program])])" + NL + "AC_CHECK_PROG([JAVAC], [javac], [javac], [AC_MSG_ERROR([cannot find Java compiler])])" + NL + "AC_CHECK_PROG([JAR], [jar], [jar], [AC_MSG_ERROR([cannot find jar program])])" + NL;
  protected final String TEXT_4 = NL + "PKG_CHECK_MODULES([INTERFACEDEPS], [";
  protected final String TEXT_5 = "Interfaces";
  protected final String TEXT_6 = " ";
  protected final String TEXT_7 = "Interfaces";
  protected final String TEXT_8 = "])";
  protected final String TEXT_9 = NL + NL + "AC_CONFIG_FILES(Makefile)" + NL + "AC_OUTPUT";
  protected final String TEXT_10 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    IResource resource = ModelUtil.getResource(implSettings);
    Implementation impl = templ.getImpl();
    SoftPkg softpkg = (SoftPkg) impl.eContainer();
    if (softpkg == null) {
        throw new IllegalArgumentException("Unable to determine Spd for implementation: " + impl.getId());
    }
    String xmlDest = "";
    if (softpkg.getDescriptor().getComponent().getComponentType().contains(RedhawkIdePreferenceConstants.DEVICE.toLowerCase())) {
    	xmlDest = "dev"; 
    } else {
    	xmlDest = "dom";
    }
    
    // Determine softpkg version, set to 1.0.0 if none exists
    String version = "1.0.0";
    String softpkgVersion = softpkg.getVersion();
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
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_3);
    
    boolean first = true;
    for (String interfaceNamespace : ifaceNameAndVer.keySet()) {
	    if (interfaceNamespace.equals("extendedevent")) {  // do not add a module (extendedevent is distributed as a standard part of REDHAWK)
	    	continue;
	    }
	    if (first) {
	        first = false;

    stringBuffer.append(TEXT_4);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(ifaceNameAndVer.get(interfaceNamespace));
    
        } else {

    stringBuffer.append(TEXT_6);
    stringBuffer.append(interfaceNamespace);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(ifaceNameAndVer.get(interfaceNamespace));
    
        }
    }
    if (!first) {

    stringBuffer.append(TEXT_8);
    
    }

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
} 