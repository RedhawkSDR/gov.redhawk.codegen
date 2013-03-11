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
package gov.redhawk.ide.codegen.jet.cplusplus.template;

	/**
    * @generated
    */

public class ReconfTemplate
{

  protected static String nl;
  public static synchronized ReconfTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ReconfTemplate result = new ReconfTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/bin/sh" + NL + "" + NL + "rm -f config.cache" + NL + "" + NL + "# Setup the libtool stuff" + NL + "if [ -e /usr/local/share/aclocal/libtool.m4 ]; then" + NL + "    /bin/cp /usr/local/share/aclocal/libtool.m4 aclocal.d/acinclude.m4" + NL + "elif [ -e /usr/share/aclocal/libtool.m4 ]; then" + NL + "    /bin/cp /usr/share/aclocal/libtool.m4 acinclude.m4" + NL + "fi" + NL + "libtoolize --force --automake" + NL + "" + NL + "# Search in expected locations for the OSSIE acincludes" + NL + "if [ -n ${OSSIEHOME} ] && [ -d ${OSSIEHOME}/share/aclocal/ossie ]; then" + NL + "        OSSIE_AC_INCLUDE=${OSSIEHOME}/share/aclocal/ossie" + NL + "else" + NL + "    echo \"Error: Cannot find the OSSIE aclocal files. This is not expected!\"" + NL + "fi" + NL + "" + NL + "if [ -n ${OSSIE_AC_INCLUDE} ]; then" + NL + "        aclocal -I ${OSSIE_AC_INCLUDE}" + NL + "else" + NL + "        aclocal" + NL + "fi" + NL + "" + NL + "autoconf" + NL + "automake --foreign --add-missing";

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    return stringBuffer.toString();
  }
} 