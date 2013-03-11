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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.IPortTemplateDesc;
import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.PortRepToGeneratorMap;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.PropertyChangeEventPortTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.ports.PullPortTemplate;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.emf.common.util.EList;

/**
 * @generated
 */
public class PullPortImplCppTemplate
{

  protected static String nl;
  public static synchronized PullPortImplCppTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullPortImplCppTemplate result = new PullPortImplCppTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*******************************************************************************************" + NL + "" + NL + "    AUTO-GENERATED CODE. DO NOT MODIFY" + NL + "" + NL + " \tSource: ";
  protected final String TEXT_2 = NL + " \tGenerated on: ";
  protected final String TEXT_3 = NL + " \t";
  protected final String TEXT_4 = NL + " \t";
  protected final String TEXT_5 = NL + " \t";
  protected final String TEXT_6 = NL + NL + "*******************************************************************************************/" + NL + "" + NL + "#include \"";
  protected final String TEXT_7 = ".h\"";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    EList<Uses> uses = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getUses();
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    HashSet<String> usesReps = new HashSet<String>();
    HashSet<String> providesReps = new HashSet<String>();
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    TemplateParameter portTempl = new TemplateParameter(impl, implSettings, search_paths);
    HashMap<String, IScaPortCodegenTemplate> portMap = new HashMap<String, IScaPortCodegenTemplate>();
    boolean includePropertyChange = false;
    Date date = new Date(System.currentTimeMillis());
    for (PortRepToGeneratorMap p : implSettings.getPortGenerators()) {
        try {
            IPortTemplateDesc template = CodegenUtil.getPortTemplate(p.getGenerator(), null);
            if (template != null) {
                portMap.put(p.getRepId(), template.getTemplate());
            }
        } catch (CoreException e) {
            // TODO What to do here! Throw the exception and not generate anything?
        }
    }
    for (Uses entry : uses) {
        if (PropertyChangeEventPortTemplate.EVENTCHANNEL_REPID.equals(entry.getRepID()) 
                && PropertyChangeEventPortTemplate.EVENTCHANNEL_NAME.equals(entry.getUsesName())) {
            includePropertyChange = true;
            continue;
        }
        usesReps.add(entry.getRepID());
    }
    for (Provides entry : provides) {
        providesReps.add(entry.getRepID());
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_2);
    stringBuffer.append( date.toString() );
    
	String[] output;
	IProduct product = Platform.getProduct();
	if (product != null) {
		output = product.getProperty("aboutText").split("\n");

    stringBuffer.append(TEXT_3);
    stringBuffer.append(output[0]);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(output[1]);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(output[2]);
    
	}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    
    if (includePropertyChange) {

    stringBuffer.append(TEXT_8);
    stringBuffer.append(new PropertyChangeEventPortTemplate().generateClassImplementation(null, false, softPkg, implSettings, portTempl, CodegenUtil.CPP));
    
    }
    for (String intName : usesReps) {
        IScaPortCodegenTemplate gen = portMap.get(intName);
        portTempl.setPortRepId(intName);
        portTempl.setGenSupport(false);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(true);
        if (gen != null) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(gen.generateClassImplementation(intName, false, softPkg, implSettings, portTempl, CodegenUtil.CPP));
    
        } else {

    stringBuffer.append(TEXT_10);
    stringBuffer.append(new PullPortTemplate().generateClassImplementation(intName, false, softPkg, implSettings, portTempl, CodegenUtil.CPP));
    
        }
    }
    for (String intName : providesReps) {
        IScaPortCodegenTemplate gen = portMap.get(intName);
        portTempl.setPortRepId(intName);
        portTempl.setGenSupport(false);
        portTempl.setGenClassDef(false);
        portTempl.setGenClassImpl(true);
        if (gen != null) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append(gen.generateClassImplementation(intName, true, softPkg, implSettings, portTempl, CodegenUtil.CPP));
    
        } else {

    stringBuffer.append(TEXT_12);
    stringBuffer.append(new PullPortTemplate().generateClassImplementation(intName, true, softPkg, implSettings, portTempl, CodegenUtil.CPP));
    
        }
    }

    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE