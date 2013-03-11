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
package gov.redhawk.ide.codegen.jet.python.template.service;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.CoreException;
import java.util.Date;
import java.util.List;
import gov.redhawk.model.sca.util.ModelUtil;

	/**
    * @generated
    */

public class MinimalServicePythonTemplate
{

  protected static String nl;
  public static synchronized MinimalServicePythonTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    MinimalServicePythonTemplate result = new MinimalServicePythonTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python " + NL + "#";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = "#" + NL + "# Source: ";
  protected final String TEXT_4 = NL + "# Generated on: ";
  protected final String TEXT_5 = NL + "# ";
  protected final String TEXT_6 = NL + "# ";
  protected final String TEXT_7 = NL + "# ";
  protected final String TEXT_8 = NL + NL + "import sys, signal, copy, os" + NL + "import logging" + NL + "" + NL + "from ossie.cf import CF, CF__POA #@UnusedImport" + NL + "from ossie.service import start_service" + NL + "from omniORB import CORBA, URI, PortableServer" + NL;
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL + NL + "           " + NL + "class ";
  protected final String TEXT_11 = "(";
  protected final String TEXT_12 = "):" + NL + "    " + NL + "    def __init__(self, name=\"";
  protected final String TEXT_13 = "\", execparams={}):" + NL + "        self.name = name" + NL + "        self._log = logging.getLogger(self.name)" + NL + "        logging.getLogger().setLevel(logging.DEBUG)" + NL + "       " + NL + "    def releaseObject(self):" + NL + "        pass" + NL;
  protected final String TEXT_14 = NL + "    def ";
  protected final String TEXT_15 = "(self";
  protected final String TEXT_16 = ", ";
  protected final String TEXT_17 = "):" + NL + "        # TODO" + NL + "        pass" + NL;
  protected final String TEXT_18 = "        " + NL + "    def _get_";
  protected final String TEXT_19 = "(self):" + NL + "        # TODO" + NL + "        pass" + NL;
  protected final String TEXT_20 = NL + "    def _set_";
  protected final String TEXT_21 = "(self, data):" + NL + "        # TODO:" + NL + "        pass" + NL;
  protected final String TEXT_22 = NL + "            " + NL + "if __name__ == '__main__':" + NL + "    if len(sys.argv) > 1:" + NL + "        # If there are arguments, use standard service launch" + NL + "        # You may change the thread_policy to your preference" + NL + "        start_service(";
  protected final String TEXT_23 = ", thread_policy=PortableServer.SINGLE_THREAD_MODEL)  " + NL + "    else:" + NL + "        # Otherwise, assume we are being run manually so print out our IOR" + NL + "        orb = CORBA.ORB_init(sys.argv)" + NL + "        o = ";
  protected final String TEXT_24 = "()" + NL + "        print orb.object_to_string(o._this())" + NL + "        orb.run()";
  protected final String TEXT_25 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	Date date = new Date(System.currentTimeMillis());
	TemplateParameter templ = (TemplateParameter) argument;
	List<IPath> search_paths = templ.getSearchPaths();
	mil.jpeojtrs.sca.spd.Implementation impl = templ.getImpl();
	ImplementationSettings implSettings = templ.getImplSettings();
	mil.jpeojtrs.sca.spd.SoftPkg softPkg = (mil.jpeojtrs.sca.spd.SoftPkg) impl.eContainer();
	String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_4);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_5);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_8);
    
    String basePoa = null;
    gov.redhawk.ide.idl.Interface intf = null;
    if (softPkg.getDescriptor() != null) { // this is generally an error condition
        final String repId = softPkg.getDescriptor().getComponent().getComponentRepID().getRepid();
        final String interfaceName = repId.split(":")[1];
        
        // getInterface is extra fragile because it assumes MODULE/IFACE naming when in fact you could have
        // MODULE/SUBMODULE/IFACE.  IdlUtil should accept full repIds.
        try {
            intf = gov.redhawk.ide.idl.IdlUtil.getInstance().getInterface(search_paths, interfaceName, true);
        } catch (CoreException e) {
			// PASS
		} 
    	final String importStmt = gov.redhawk.ide.codegen.python.utils.PythonGeneratorUtils.guessPythonImportForRepId(repId, true);
    	if (importStmt != null) {
    	    basePoa = gov.redhawk.ide.codegen.python.utils.PortHelper.idlToClassName(repId);

    stringBuffer.append(TEXT_9);
    stringBuffer.append(importStmt);
    
        }
    }
    
    if (basePoa == null) {
        basePoa = "object"; // fall-back behavior
    }

    stringBuffer.append(TEXT_10);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(basePoa);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_13);
     
    if (intf != null) {
        // Operations
        for (gov.redhawk.ide.idl.Operation op : intf.getOperations()) {
            int numParams = op.getParams().size();

    stringBuffer.append(TEXT_14);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_15);
    for (gov.redhawk.ide.idl.Param p : op.getParams()){
    stringBuffer.append(TEXT_16);
    stringBuffer.append(p.getName());
    }
    stringBuffer.append(TEXT_17);
          } // end "for (gov.redhawk.ide.idl.Operation op : intf.getOperations())" 
    
        // Attributes        
        for (gov.redhawk.ide.idl.Attribute op : intf.getAttributes()) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_19);
              if (!op.isReadonly()) {
    stringBuffer.append(TEXT_20);
    stringBuffer.append(op.getName());
    stringBuffer.append(TEXT_21);
              } // end "if (!op.isReadonly())" 
          } // end "for (Attribute op : intf.getAttributes())" 
       } // end "if (intf != null)" 
    stringBuffer.append(TEXT_22);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
} 