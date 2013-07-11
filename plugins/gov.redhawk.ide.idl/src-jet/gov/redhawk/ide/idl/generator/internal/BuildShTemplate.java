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
public class BuildShTemplate
{

  protected static String nl;
  public static synchronized BuildShTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    BuildShTemplate result = new BuildShTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/bin/sh" + NL + "" + NL + "if [ \"$1\" = \"clean\" ]; then" + NL + "  make clean" + NL + "elif [ \"$1\" = \"rpm\" ]; then" + NL + "  # A very simplistic RPM build scenario" + NL + "  mydir=`dirname $0`" + NL + "  tmpdir=`mktemp -d`" + NL + "  cp -r ${mydir} ${tmpdir}/";
  protected final String TEXT_2 = "-";
  protected final String TEXT_3 = NL + "  tar czf ${tmpdir}/";
  protected final String TEXT_4 = "-";
  protected final String TEXT_5 = ".tar.gz --exclude=\".svn\" -C ${tmpdir} ";
  protected final String TEXT_6 = "-";
  protected final String TEXT_7 = NL + "  rpmbuild -ta ${tmpdir}/";
  protected final String TEXT_8 = "-";
  protected final String TEXT_9 = ".tar.gz" + NL + "  rm -rf $tmpdir" + NL + "else" + NL + "  # Checks if build is newer than makefile (based on modification time)" + NL + "  if [ ! -e configure ] || [ ! -e Makefile.in ] || [ configure.ac -nt Makefile ] || [ Makefile.am -nt Makefile ]; then" + NL + "    ./reconf" + NL + "    ./configure" + NL + "  fi" + NL + "  make" + NL + "fi";
  protected final String TEXT_10 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    final GeneratorArgs args = (GeneratorArgs) argument;
    final String rpmname = args.getInterfaceName().toLowerCase() + "Interfaces";
    final String version = args.getInterfaceVersion();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(rpmname);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(rpmname);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(rpmname);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(rpmname);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(version);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
} 