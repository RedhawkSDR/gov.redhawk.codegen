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

/**
 * @generated
 */
public class ReconfLaunchTemplate
{

  protected static String nl;
  public static synchronized ReconfLaunchTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ReconfLaunchTemplate result = new ReconfLaunchTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + NL + "<launchConfiguration type=\"org.eclipse.ui.externaltools.ProgramLaunchConfigurationType\">" + NL + "<stringAttribute key=\"org.eclipse.debug.core.ATTR_REFRESH_SCOPE\" value=\"${project}\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LOCATION\" value=\"${project_loc:/reconf}\"/>" + NL + "<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY\" value=\"${project_loc}\"/>" + NL + "</launchConfiguration>";

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