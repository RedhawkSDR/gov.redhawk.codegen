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
package gov.redhawk.ide.idl.generator.internal;

import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

/**
 * @generated
 */
public class IdlSpecTemplate
{

  protected static String nl;
  public static synchronized IdlSpecTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    IdlSpecTemplate result = new IdlSpecTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "# By default, the RPM will install to the standard REDHAWK OSSIE root location (/usr/local/redhawk/core)" + NL + "%{!?_ossiehome: %define _ossiehome /usr/local/redhawk/core}" + NL + "%define _prefix %{_ossiehome}" + NL + "Prefix: %{_prefix}" + NL + "" + NL + "# Point install paths to locations within our target OSSIE root" + NL + "%define _sysconfdir    %{_prefix}/etc" + NL + "%define _localstatedir %{_prefix}/var" + NL + "%define _mandir        %{_prefix}/man" + NL + "%define _infodir       %{_prefix}/info" + NL + "" + NL + "# Assume Java support by default. Use \"rpmbuild --without java\" to disable, or define RPM variable \"_without_java\"" + NL + "%{!?_with_java: %{!?_without_java: %define _with_java 1}}" + NL + "" + NL + "Summary: The ";
  protected final String TEXT_2 = " library for REDHAWK" + NL + "Name: ";
  protected final String TEXT_3 = "Interfaces" + NL + "Version: ";
  protected final String TEXT_4 = NL + "Release: 1" + NL + "License: None" + NL + "Group: REDHAWK/Interfaces" + NL + "Source: %{name}-%{version}.tar.gz " + NL + "BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-buildroot" + NL + "Requires: redhawk >= 1.7" + NL + "BuildRequires: redhawk >= 1.7" + NL + "BuildRequires: autoconf automake libtool" + NL + "BuildRequires: omniORB" + NL + "BuildRequires: python omniORBpy omniORBpy-devel" + NL + "%{?_with_java:BuildRequires: jdk}" + NL + "" + NL + "%description" + NL + "Libraries and interface definitions for ";
  protected final String TEXT_5 = "." + NL + "" + NL + "%prep" + NL + "%setup" + NL + "" + NL + "%build" + NL + "./reconf" + NL + "%configure %{?_without_java: --disable-java}" + NL + "make" + NL + "" + NL + "%install" + NL + "rm -rf --preserve-root $RPM_BUILD_ROOT" + NL + "make install DESTDIR=$RPM_BUILD_ROOT" + NL + "" + NL + "%clean" + NL + "rm -rf --preserve-root $RPM_BUILD_ROOT" + NL + "" + NL + "%files" + NL + "%defattr(-,redhawk,redhawk)" + NL + "%{_datadir}/idl/redhawk/";
  protected final String TEXT_6 = NL + "%{_includedir}/redhawk/";
  protected final String TEXT_7 = NL + "%{_libdir}/lib";
  protected final String TEXT_8 = "Interfaces.*" + NL + "%{_libdir}/pkgconfig/";
  protected final String TEXT_9 = "Interfaces.pc" + NL + "%{_prefix}/lib/python/redhawk/";
  protected final String TEXT_10 = "Interfaces" + NL + "%if %{?_with_java:1}%{!?_with_java:0}" + NL + "%{_prefix}/lib/";
  protected final String TEXT_11 = "Interfaces.jar" + NL + "%{_prefix}/lib/";
  protected final String TEXT_12 = "Interfaces.src.jar" + NL + "%endif" + NL + "" + NL + "%post" + NL + "/sbin/ldconfig" + NL + "" + NL + "%postun" + NL + "/sbin/ldconfig";
  protected final String TEXT_13 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	final GeneratorArgs args = (GeneratorArgs) argument;
	final String idlModuleNameLower = args.getInterfaceName().toLowerCase();
	final String idlModuleName = args.getInterfaceName().toUpperCase();
	final String version = args.getInterfaceVersion();	

    stringBuffer.append(TEXT_1);
    stringBuffer.append(args.getInterfaceName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(idlModuleNameLower);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(args.getInterfaceName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(idlModuleNameLower);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(args.getInterfaceName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(idlModuleNameLower);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
} 