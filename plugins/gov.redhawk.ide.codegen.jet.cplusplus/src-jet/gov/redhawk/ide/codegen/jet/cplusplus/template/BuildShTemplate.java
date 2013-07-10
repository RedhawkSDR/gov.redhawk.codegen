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
  protected final String TEXT_1 = "#!/bin/sh" + NL + "" + NL + "if [ \"$1\" = 'clean' ]; then" + NL + "  make clean" + NL + "else" + NL + "  # Checks if build is newer than makefile (based on modification time)" + NL + "  if [ ! -e configure ] || [ ! -e Makefile.in ] || [ configure.ac -nt Makefile ] || [ Makefile.am -nt Makefile ]; then" + NL + "    ./reconf" + NL + "    ./configure" + NL + "  fi" + NL + "  make" + NL + "  exit 0" + NL + "fi";
  protected final String TEXT_2 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
} 