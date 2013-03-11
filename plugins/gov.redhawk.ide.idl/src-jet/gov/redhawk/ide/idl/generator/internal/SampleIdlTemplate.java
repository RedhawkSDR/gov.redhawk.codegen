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
public class SampleIdlTemplate
{

  protected static String nl;
  public static synchronized SampleIdlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    SampleIdlTemplate result = new SampleIdlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef _";
  protected final String TEXT_2 = "_IDL_" + NL + "#define _";
  protected final String TEXT_3 = "_IDL_" + NL + "" + NL + "module ";
  protected final String TEXT_4 = " {" + NL + "" + NL + "\t// TODO define interfaces and data types here" + NL + "\tinterface SampleInterface {" + NL + "\t    string echo(in string msg);" + NL + "\t};" + NL + "\t" + NL + "};" + NL + "" + NL + "#endif";

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	GeneratorArgs args = (GeneratorArgs) argument;
	String interfaceName = args.getInterfaceName() + "Interfaces";
	String idlModuleName = interfaceName.split("Interfaces")[0].toUpperCase();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(idlModuleName);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
} 