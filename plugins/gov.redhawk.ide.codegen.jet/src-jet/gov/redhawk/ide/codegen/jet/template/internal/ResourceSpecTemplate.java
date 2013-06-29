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
package gov.redhawk.ide.codegen.jet.template.internal;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.WaveDevSettings;
import gov.redhawk.ide.codegen.jet.Activator;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mil.jpeojtrs.sca.scd.ComponentType;
import mil.jpeojtrs.sca.scd.Interface;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.spd.Implementation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;

/**
 * @generated
 */
public class ResourceSpecTemplate
{

  protected static String nl;
  public static synchronized ResourceSpecTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceSpecTemplate result = new ResourceSpecTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "# By default, the RPM will install to the standard REDHAWK SDR root location (/var/redhawk/sdr)" + NL + "# You can override this at install time using --prefix /new/sdr/root when invoking rpm (preferred method, if you must)" + NL + "%{!?_sdrroot: %define _sdrroot /var/redhawk/sdr}" + NL + "%define _prefix %{_sdrroot}" + NL + "Prefix: %{_prefix}" + NL + "" + NL + "# Point install paths to locations within our target SDR root" + NL + "%define _sysconfdir    %{_prefix}/etc" + NL + "%define _localstatedir %{_prefix}/var" + NL + "%define _mandir        %{_prefix}/man" + NL + "%define _infodir       %{_prefix}/info" + NL + "" + NL + "Name: ";
  protected final String TEXT_2 = NL + "Summary: ";
  protected final String TEXT_3 = " %{name}";
  protected final String TEXT_4 = " (";
  protected final String TEXT_5 = ")";
  protected final String TEXT_6 = NL + "Version: ";
  protected final String TEXT_7 = NL + "Release: 1" + NL + "License: None" + NL + "Group: REDHAWK/";
  protected final String TEXT_8 = "s" + NL + "Source: %{name}-%{version}.tar.gz" + NL + "BuildRoot: %{_tmppath}/%{name}-root" + NL + "" + NL + "Requires: redhawk >= 1.8" + NL + "BuildRequires: redhawk >= 1.8" + NL + "BuildRequires: autoconf automake libtool" + NL;
  protected final String TEXT_9 = NL + "# Interface requirements" + NL + "Requires:";
  protected final String TEXT_10 = " ";
  protected final String TEXT_11 = "Interfaces";
  protected final String TEXT_12 = NL + "BuildRequires:";
  protected final String TEXT_13 = " ";
  protected final String TEXT_14 = "Interfaces";
  protected final String TEXT_15 = NL;
  protected final String TEXT_16 = NL + "BuildArch: noarch" + NL;
  protected final String TEXT_17 = NL + "# C++ requirements" + NL + "Requires: libomniORB4.1" + NL + "Requires: boost >= 1.41" + NL + "Requires: apache-log4cxx >= 0.10" + NL + "BuildRequires: boost-devel >= 1.41" + NL + "BuildRequires: libomniORB4.1-devel" + NL + "BuildRequires: apache-log4cxx-devel >= 0.10" + NL;
  protected final String TEXT_18 = NL + "# Java requirements" + NL + "Requires: java >= 1.6" + NL + "BuildRequires: java-devel >= 1.6" + NL;
  protected final String TEXT_19 = NL + "# Python requirements" + NL + "Requires: python omniORBpy" + NL + "BuildRequires: libomniORBpy3-devel" + NL + "BuildRequires: python-devel >= 2.3" + NL;
  protected final String TEXT_20 = NL + NL + "%description";
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = " %{name}";
  protected final String TEXT_24 = NL + NL + "%prep" + NL + "%setup" + NL + "" + NL + "%build";
  protected final String TEXT_25 = NL + "# Implementation ";
  protected final String TEXT_26 = NL + "pushd ";
  protected final String TEXT_27 = NL + "./reconf" + NL + "%define _bindir %{_prefix}/";
  protected final String TEXT_28 = "/";
  protected final String TEXT_29 = "/";
  protected final String TEXT_30 = NL + "%configure" + NL + "make" + NL + "popd";
  protected final String TEXT_31 = NL + NL + "%install" + NL + "rm -rf $RPM_BUILD_ROOT";
  protected final String TEXT_32 = NL + "# Implementation ";
  protected final String TEXT_33 = NL + "pushd ";
  protected final String TEXT_34 = NL + "%define _bindir %{_prefix}/";
  protected final String TEXT_35 = "/";
  protected final String TEXT_36 = "/";
  protected final String TEXT_37 = " " + NL + "make install DESTDIR=$RPM_BUILD_ROOT" + NL + "popd";
  protected final String TEXT_38 = NL + NL + "%clean" + NL + "rm -rf $RPM_BUILD_ROOT" + NL + "" + NL + "%files" + NL + "%defattr(-,redhawk,redhawk)" + NL + "%dir %{_prefix}/";
  protected final String TEXT_39 = "/%{name}" + NL + "%{_prefix}/";
  protected final String TEXT_40 = "/%{name}/";
  protected final String TEXT_41 = NL + "%{_prefix}/";
  protected final String TEXT_42 = "/%{name}/";
  protected final String TEXT_43 = NL + "%{_prefix}/";
  protected final String TEXT_44 = "/%{name}/";
  protected final String TEXT_45 = NL + "%{_prefix}/";
  protected final String TEXT_46 = "/%{name}/";

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    final TemplateParameter param = (TemplateParameter) argument;
    final EMap<String, ImplementationSettings> settings = ((WaveDevSettings) param.getImplSettings().eContainer().eContainer()).getImplSettings();
    final String name = param.getSoftPkg().getName();    
    final String version;
    if (param.getSoftPkg().getVersion() != null && param.getSoftPkg().getVersion().trim().length() != 0) {
        version = param.getSoftPkg().getVersion();
    } else {
        version = "1.0.0";
    }

    // Interfaces
    final Set<String> interfaceNamespaces = new HashSet<String>();
	final List<Interface> interfaces = param.getSoftPkg().getDescriptor().getComponent().getInterfaces().getInterface();
	final Pattern idlPattern = Pattern.compile("^IDL:(\\w+)/");
    for (Interface iface : interfaces) {
        final Matcher match = idlPattern.matcher(iface.getRepid());
        if (match.find()) {
            if (! "CF".equals(match.group(1))) {
                interfaceNamespaces.add(match.group(1));
            }
        }
    }
    
    // Language
    boolean hasCpp = false, hasJava = false, hasPython = false;
    for (final Implementation impl : param.getSoftPkg().getImplementation()) {
        final String language = impl.getProgrammingLanguage().getName();
        if ("C++".equals(language)) {
            hasCpp = true;
        } else if ("Java".equals(language)) {
            hasJava = true;
        } else if ("Python".equals(language)) {
            hasPython = true;
        }
    }
    
    // Strings that depend on project type
    final String sdrrootFolder;
    final String projectType;
    final SoftwareComponent scd = param.getSoftPkg().getDescriptor().getComponent();
    final ComponentType componentType = SoftwareComponent.Util.getWellKnownComponentType(scd);
    switch (componentType) {
    case RESOURCE:
        sdrrootFolder = "dom/components";
        projectType = "Component";
        break;
    case DEVICE:
        sdrrootFolder = "dev/devices";
        projectType = "Device";
        break;
    case SERVICE:
    	sdrrootFolder = "dev/services";
        projectType = "Service";
        break;
    default:
    	// For backwards compatibility, support non-spec strings the IDE used to generate
        try {
            if (scd != null && scd.getComponentType() != null) {
                final String compTypeStr = scd.getComponentType();
                if (compTypeStr.equals("executabledevice") || compTypeStr.equals("loadabledevice")) {
                    sdrrootFolder = "dev/devices";
                    projectType = "Device";
                    break;
                }
            }
        } catch (NullPointerException e) {
            // PASS
        }
    	throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Template file does not support the specified project type"));
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(projectType);
    stringBuffer.append(TEXT_3);
    
    if (param.getSoftPkg().getTitle() != null && param.getSoftPkg().getTitle().trim().length() > 0) {
        
    stringBuffer.append(TEXT_4);
    stringBuffer.append(param.getSoftPkg().getTitle());
    stringBuffer.append(TEXT_5);
    
	}
    stringBuffer.append(TEXT_6);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(projectType);
    stringBuffer.append(TEXT_8);
    
	if (interfaceNamespaces.size() > 0) {
		boolean foundOther = false;
		// these interfaces are available through the redhawk install
		//  so no additional rpms are needed
		for (String ifaceNamespace : interfaceNamespaces) {
			if ((!ifaceNamespace.equals("CF")) &&
			 (!ifaceNamespace.equals("PortTypes")) &&
			 (!ifaceNamespace.equals("ExtendedEvent")) &&
			 (!ifaceNamespace.equals("ExtendedCF")) &&
			 (!ifaceNamespace.equals("StandardEvent")) &&
			 (!ifaceNamespace.equals("WKP"))) {
			 	foundOther = true;
			 	break;
			 }
		}
		if (foundOther) {

    stringBuffer.append(TEXT_9);
    
        for (String ifaceNamespace : interfaceNamespaces) {
			if ((!ifaceNamespace.equals("CF")) &&
			 (!ifaceNamespace.equals("PortTypes")) &&
			 (!ifaceNamespace.equals("ExtendedEvent")) &&
			 (!ifaceNamespace.equals("ExtendedCF")) &&
			 (!ifaceNamespace.equals("StandardEvent")) &&
			 (!ifaceNamespace.equals("WKP"))) {
            
    stringBuffer.append(TEXT_10);
    stringBuffer.append(ifaceNamespace.toLowerCase());
    stringBuffer.append(TEXT_11);
    
			 }
        }

    stringBuffer.append(TEXT_12);
    
        for (String ifaceNamespace : interfaceNamespaces) {
			if ((!ifaceNamespace.equals("CF")) &&
			 (!ifaceNamespace.equals("PortTypes")) &&
			 (!ifaceNamespace.equals("ExtendedEvent")) &&
			 (!ifaceNamespace.equals("ExtendedCF")) &&
			 (!ifaceNamespace.equals("StandardEvent")) &&
			 (!ifaceNamespace.equals("WKP"))) {
            
    stringBuffer.append(TEXT_13);
    stringBuffer.append(ifaceNamespace.toLowerCase());
    stringBuffer.append(TEXT_14);
    
			 }
        }
        
    stringBuffer.append(TEXT_15);
    
		}
    }
    if (!hasCpp && (hasJava | hasPython)) {

    stringBuffer.append(TEXT_16);
    
    }
    if (hasCpp) {

    stringBuffer.append(TEXT_17);
    
    }
    if (hasJava) {

    stringBuffer.append(TEXT_18);
    
    }
    if (hasPython) {

    stringBuffer.append(TEXT_19);
    
    }

    stringBuffer.append(TEXT_20);
    
    if (param.getSoftPkg().getDescription() != null) {

    stringBuffer.append(TEXT_21);
    stringBuffer.append(param.getSoftPkg().getDescription());
    
    } else {

    stringBuffer.append(TEXT_22);
    stringBuffer.append(projectType);
    stringBuffer.append(TEXT_23);
    
    }

    stringBuffer.append(TEXT_24);
    
    for (final Implementation impl : param.getSoftPkg().getImplementation()) {
        final ImplementationSettings implSettings = settings.get(impl.getId());
        if (implSettings == null) {
            continue;
        }

    stringBuffer.append(TEXT_25);
    stringBuffer.append(impl.getId());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_30);
    
    }

    stringBuffer.append(TEXT_31);
    
    for (final Implementation impl : param.getSoftPkg().getImplementation()) {
        final ImplementationSettings implSettings = settings.get(impl.getId());
        if (implSettings == null) {
            continue;
        }

    stringBuffer.append(TEXT_32);
    stringBuffer.append(impl.getId());
    stringBuffer.append(TEXT_33);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_34);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_37);
    
    }

    stringBuffer.append(TEXT_38);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(ModelUtil.getSpdFileName(param.getSoftPkg()));
    
    if (param.getSoftPkg().getPropertyFile() != null) {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(ModelUtil.getPrfFileName(param.getSoftPkg().getPropertyFile()));
    
    }

    stringBuffer.append(TEXT_43);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(ModelUtil.getScdFileName(param.getSoftPkg()));
    
    for (final Implementation impl : param.getSoftPkg().getImplementation()) {
        final ImplementationSettings implSettings = settings.get(impl.getId());
        if (implSettings == null) {
            continue;
        }
        
    stringBuffer.append(TEXT_45);
    stringBuffer.append(sdrrootFolder);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(implSettings.getOutputDir());
    
    }

    return stringBuffer.toString();
  }
}

// END GENERATED CODE