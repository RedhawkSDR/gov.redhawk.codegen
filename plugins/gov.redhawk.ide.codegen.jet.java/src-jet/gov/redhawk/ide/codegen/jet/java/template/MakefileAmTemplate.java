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
package gov.redhawk.ide.codegen.jet.java.template;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.util.AutoMakeUtil;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.HashSet;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.scd.ComponentType;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

/**
 * @generated
 */
public class MakefileAmTemplate
{

  protected static String nl;
  public static synchronized MakefileAmTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    MakefileAmTemplate result = new MakefileAmTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = ".jar: $(";
  protected final String TEXT_3 = "_jar_SOURCES)" + NL + "\tmkdir -p bin" + NL + "\t$(JAVAC) -cp $(OSSIE_HOME)/lib/CFInterfaces.jar:$(OSSIE_HOME)/lib/log4j-1.2.15.jar:$(OSSIE_HOME)/lib/ossie.jar";
  protected final String TEXT_4 = ":$(OSSIE_HOME)/lib/";
  protected final String TEXT_5 = "Interfaces.jar";
  protected final String TEXT_6 = " -d bin $(";
  protected final String TEXT_7 = "_jar_SOURCES)" + NL + "\t$(JAR) cf ./";
  protected final String TEXT_8 = ".jar -C bin ." + NL + "" + NL + "clean-local:" + NL + "\trm -rf bin" + NL + "" + NL + "ossieName = ";
  protected final String TEXT_9 = NL + "bindir = $(prefix)/";
  protected final String TEXT_10 = "/";
  protected final String TEXT_11 = "/";
  protected final String TEXT_12 = "/";
  protected final String TEXT_13 = "/" + NL + "bin_PROGRAMS = ";
  protected final String TEXT_14 = ".jar";
  protected final String TEXT_15 = NL;
  protected final String TEXT_16 = "_jar_SOURCES := $(shell find ./src -name \"*.java\")" + NL + "" + NL + "xmldir = $(prefix)/";
  protected final String TEXT_17 = "/";
  protected final String TEXT_18 = "/";
  protected final String TEXT_19 = "/" + NL + "dist_xml_DATA = ";
  protected final String TEXT_20 = " ";
  protected final String TEXT_21 = " ";
  protected final String TEXT_22 = " " + NL;
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "dir = $(prefix)/";
  protected final String TEXT_25 = "/";
  protected final String TEXT_26 = "/";
  protected final String TEXT_27 = "/";
  protected final String TEXT_28 = "/" + NL + "dist_";
  protected final String TEXT_29 = "_SCRIPTS = startJava.sh";
  protected final String TEXT_30 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter template = (TemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    Implementation impl = template.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();
    EList<Provides> provides = ports.getProvides();
    EList<Uses> uses = ports.getUses();
    IResource resource = ModelUtil.getResource(implSettings);
    
    String sdrSubDir = "dom";
    String subFolder = "components";

    SoftwareComponent component = softPkg.getDescriptor().getComponent();
    final ComponentType scdComponentType = SoftwareComponent.Util.getWellKnownComponentType(component); 
    if (scdComponentType == ComponentType.DEVICE) {
    	sdrSubDir = "dev";
     	subFolder = "devices";
    } else if (scdComponentType == ComponentType.SERVICE) {
    	sdrSubDir = "dev";
     	subFolder = "services";
    }
    
    String xmlLocation = "";
    for (String temp : implSettings.getOutputDir().split("/")) {
    	if (temp.length() > 1) {
    		xmlLocation += "../";
    	}
    }
    
    // Get a list of all the packages for the uses ports
    HashSet<String> packages = new HashSet<String>();
    for (Uses entry : uses) {
        if (!(entry.getRepID().equals("IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0") && entry.getUsesName().equals("propEvent"))) {
		    final String[] ints = entry.getRepID().split(":")[1].split("/");
            packages.add(ints[ints.length - 2]);
        }
    }
    // Get a list of all the packages for the provides ports
    for (Provides entry : provides) {
		final String[] ints = entry.getRepID().split(":")[1].split("/");
        packages.add(ints[ints.length - 2]);
    }
    
    final String amDerivedVar = AutoMakeUtil.createDerivedVariableName(gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings));

    stringBuffer.append(TEXT_1);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings));
    stringBuffer.append(TEXT_2);
    stringBuffer.append(amDerivedVar);
    stringBuffer.append(TEXT_3);
    
    for (String pack : packages) {
        
    stringBuffer.append(TEXT_4);
    stringBuffer.append(pack);
    stringBuffer.append(TEXT_5);
    
    }

    stringBuffer.append(TEXT_6);
    stringBuffer.append(amDerivedVar);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings));
    stringBuffer.append(TEXT_9);
    stringBuffer.append(sdrSubDir);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(subFolder);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings));
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(amDerivedVar);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(sdrSubDir);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(subFolder);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(xmlLocation);
    stringBuffer.append(ModelUtil.getPrfFileName(softPkg.getPropertyFile()));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(xmlLocation);
    stringBuffer.append(ModelUtil.getScdFileName(softPkg));
    stringBuffer.append(TEXT_21);
    stringBuffer.append(xmlLocation);
    stringBuffer.append(ModelUtil.getSpdFileName(softPkg));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(sdrSubDir);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(sdrSubDir);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(subFolder);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(resource.getProject().getName());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(implSettings.getOutputDir());
    stringBuffer.append(TEXT_28);
    stringBuffer.append(sdrSubDir);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE