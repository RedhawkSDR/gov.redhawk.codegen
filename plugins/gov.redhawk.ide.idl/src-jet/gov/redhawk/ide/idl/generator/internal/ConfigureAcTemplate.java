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
  protected final String TEXT_2 = "Interfaces, ";
  protected final String TEXT_3 = ")" + NL + "" + NL + "AM_INIT_AUTOMAKE(nostdinc)" + NL + "AC_PROG_CC" + NL + "AC_PROG_CXX" + NL + "AC_PROG_INSTALL" + NL + "AC_PROG_LIBTOOL" + NL + "" + NL + "AM_PATH_PYTHON([2.3])" + NL + "" + NL + "OSSIE_CHECK_OSSIE" + NL + "OSSIE_OSSIEHOME_AS_PREFIX" + NL + "OSSIE_PYTHON_INSTALL_SCHEME" + NL + "" + NL + "AC_CORBA_ORB" + NL + "AC_CHECK_PROG([IDL], [omniidl], [omniidl], [no])" + NL + "if test \"$IDL\" = no; then" + NL + "  AC_MSG_ERROR([cannot find omniidl program])" + NL + "fi" + NL + "AC_LANG_PUSH([C++])" + NL + "PKG_CHECK_MODULES([OMNIORB], [omniORB4 >= 4.1.0])" + NL + "PKG_CHECK_MODULES(OSSIE, ossie >= 1.7.0,,exit)" + NL + "" + NL + "# If you depend on other IDL modules, such as CF or BULKIO add them here" + NL + "# PKG_CHECK_MODULES([BULKIO], [bulkioInterfaces >= 1.7.0])" + NL + "# AC_CHECK_PYMODULE(bulkio.bulkioInterfaces, [], [AC_MSG_ERROR([the python bulkio.bulkioInterfaces module is required])])" + NL + "" + NL + "# Optionally include java support" + NL + "AC_ARG_ENABLE([java], AS_HELP_STRING([--disable-java], [Disable framework java support]))" + NL + "" + NL + "HAVE_JAVASUPPORT=no" + NL + "if test \"x$enable_java\" != \"xno\"; then" + NL + "  # Ensure JAVA_HOME is set" + NL + "  AC_ARG_VAR([JAVA_HOME], [Java Development Kit (JDK) location])" + NL + "  test -r /usr/share/java-utils/java-functions && \\" + NL + "    . /usr/share/java-utils/java-functions && \\" + NL + "    set_jvm" + NL + "  AC_MSG_CHECKING([for a valid JAVA_HOME])" + NL + "  if test -n \"$JAVA_HOME\" -a -d \"$JAVA_HOME\"; then" + NL + "    AC_MSG_RESULT([$JAVA_HOME])" + NL + "    java_test_paths=$JAVA_HOME/jre/sh$PATH_SEPARATOR$JAVA_HOME/bin$PATH_SEPARATOR$PATH" + NL + "  else" + NL + "    AC_MSG_RESULT([no - this may impact Java tool detection])" + NL + "    java_test_paths=$PATH" + NL + "  fi" + NL + "" + NL + "  # Locate tools we need" + NL + "  AC_PATH_PROG([JAVAC], [javac], [no], [$java_test_paths])" + NL + "  AC_PATH_PROG([JAR], [jar], [no], [$java_test_paths])" + NL + "  AC_PATH_PROG([IDLJ], [idlj], [no], [$java_test_paths])" + NL + "  AS_IF([test \"$JAVAC\" = no -o \"$JAR\" = no -o \"$IDLJ\" = no]," + NL + "        [AC_MSG_ERROR([Cannot find a required Java tool])])" + NL + "" + NL + "  HAVE_JAVASUPPORT=yes" + NL + "fi" + NL + "AM_CONDITIONAL(HAVE_JAVASUPPORT, test $HAVE_JAVASUPPORT = yes)" + NL + "" + NL + "AC_CONFIG_FILES(Makefile)" + NL + "AC_OUTPUT";
  protected final String TEXT_4 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    GeneratorArgs args = (GeneratorArgs) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(args.getInterfaceName().toLowerCase());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(args.getInterfaceVersion());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
} 