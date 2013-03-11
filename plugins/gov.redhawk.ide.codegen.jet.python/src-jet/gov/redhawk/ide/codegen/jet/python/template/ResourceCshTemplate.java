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

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.ImplementationSettings;
import mil.jpeojtrs.sca.spd.Implementation;
import gov.redhawk.model.sca.util.ModelUtil;
import org.eclipse.core.resources.IResource;

	/**
    * @generated
    */

public class ResourceCshTemplate
{

  protected static String nl;
  public static synchronized ResourceCshTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceCshTemplate result = new ResourceCshTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env tcsh" + NL + "" + NL + "# Find out where this script is located" + NL + "#set myPATH = $0" + NL + "#set myDIR = $myPATH:h" + NL + "" + NL + "# Pull out SCA execparams that we care about" + NL + "#set i = 1" + NL + "#set max = $#argv" + NL + "" + NL + "#if (! $?XMDISK) then" + NL + "#   echo \"XMDISK is not defined\"" + NL + "#  goto FAIL" + NL + "#endif" + NL + "" + NL + "source $XMDISK/xm/unix/xmstart" + NL + "" + NL + "#if ($?DLSR_OPT) then" + NL + "#  xmopt dlsr $DLSR_OPT" + NL + "#endif" + NL + "" + NL + "# The assumption here is that either the users 'xmstartup' (useful during development) " + NL + "# or the 'sitemods/unix/xmstart' (ideal for deployment) have setup all option-tree" + NL + "# locations via xmopt" + NL + "" + NL + "# Set the path so that we know exactly what we are getting" + NL + "xm xmp XMPY,SYS" + NL + "# Launch the SCA device code" + NL + "chmod +x `pwd`/components/";
  protected final String TEXT_2 = "/";
  protected final String TEXT_3 = ".py" + NL + "`pwd`/components/";
  protected final String TEXT_4 = "/";
  protected final String TEXT_5 = ".py $argv" + NL + "" + NL + "xmend" + NL + "" + NL + "SUCCESS:" + NL + "  exit" + NL + "" + NL + "FAIL:" + NL + "  echo \"** Failed to start **\"" + NL + "  exit -1";

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    IResource resource = ModelUtil.getResource(implSettings);

    stringBuffer.append(TEXT_1);
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.safeGetImplementationName(impl, implSettings));
    stringBuffer.append(TEXT_3);
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.safeGetImplementationName(impl, implSettings));
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
} 