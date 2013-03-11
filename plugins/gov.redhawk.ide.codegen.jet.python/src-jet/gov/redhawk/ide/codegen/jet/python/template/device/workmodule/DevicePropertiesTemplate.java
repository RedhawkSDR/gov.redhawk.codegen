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
package gov.redhawk.ide.codegen.jet.python.template.device.workmodule;

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.model.sca.util.ModelUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.emf.common.util.EList;

	/**
    * @generated
    */

public class DevicePropertiesTemplate
{

  protected static String nl;
  public static synchronized DevicePropertiesTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DevicePropertiesTemplate result = new DevicePropertiesTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python" + NL + "#" + NL + "# AUTO-GENERATED CODE.  DO NOT MODIFY!" + NL + "#" + NL + "# Source: ";
  protected final String TEXT_2 = NL + "# Generated on: ";
  protected final String TEXT_3 = NL + NL + NL + "PROPERTIES = (";
  protected final String TEXT_4 = NL + "              (" + NL + "              # ID";
  protected final String TEXT_5 = NL + "              u'";
  protected final String TEXT_6 = "',";
  protected final String TEXT_7 = NL + "              None, ";
  protected final String TEXT_8 = NL + "              # NAME  ";
  protected final String TEXT_9 = " " + NL + "              u'";
  protected final String TEXT_10 = "', ";
  protected final String TEXT_11 = NL + "              None, ";
  protected final String TEXT_12 = NL + "              # TYPE";
  protected final String TEXT_13 = " " + NL + "              u'";
  protected final String TEXT_14 = "', ";
  protected final String TEXT_15 = NL + "              None, ";
  protected final String TEXT_16 = NL + "              # MODE";
  protected final String TEXT_17 = " " + NL + "              u'";
  protected final String TEXT_18 = "',  ";
  protected final String TEXT_19 = NL + "              u'readwrite', ";
  protected final String TEXT_20 = NL + "              # DEFAULT";
  protected final String TEXT_21 = " " + NL + "              '";
  protected final String TEXT_22 = "', ";
  protected final String TEXT_23 = NL + "              ";
  protected final String TEXT_24 = ", ";
  protected final String TEXT_25 = NL + "              None, ";
  protected final String TEXT_26 = NL + "              # UNITS ";
  protected final String TEXT_27 = " " + NL + "              '";
  protected final String TEXT_28 = "', ";
  protected final String TEXT_29 = NL + "              None, ";
  protected final String TEXT_30 = NL + "              # ACTION";
  protected final String TEXT_31 = " " + NL + "              u'";
  protected final String TEXT_32 = "', ";
  protected final String TEXT_33 = NL + "              u'external', ";
  protected final String TEXT_34 = NL + "              # KINDS";
  protected final String TEXT_35 = NL + "              (u";
  protected final String TEXT_36 = "'";
  protected final String TEXT_37 = "', ";
  protected final String TEXT_38 = "),";
  protected final String TEXT_39 = NL + "              (u'configure', ),";
  protected final String TEXT_40 = NL + "              ),";
  protected final String TEXT_41 = NL + "              (" + NL + "              # ID ";
  protected final String TEXT_42 = " " + NL + "              u'";
  protected final String TEXT_43 = "', ";
  protected final String TEXT_44 = NL + "              None, ";
  protected final String TEXT_45 = NL + "              # NAME";
  protected final String TEXT_46 = " " + NL + "              u'";
  protected final String TEXT_47 = "',  ";
  protected final String TEXT_48 = NL + "              None, ";
  protected final String TEXT_49 = NL + "              # TYPE";
  protected final String TEXT_50 = " " + NL + "              u'";
  protected final String TEXT_51 = "', ";
  protected final String TEXT_52 = NL + "              None, ";
  protected final String TEXT_53 = NL + "              # MODE";
  protected final String TEXT_54 = " " + NL + "              u'";
  protected final String TEXT_55 = "', ";
  protected final String TEXT_56 = NL + "              u'readwrite', ";
  protected final String TEXT_57 = NL + "              # DEFAULT";
  protected final String TEXT_58 = " ";
  protected final String TEXT_59 = " ";
  protected final String TEXT_60 = NL + "                ( ";
  protected final String TEXT_61 = " '";
  protected final String TEXT_62 = "', ";
  protected final String TEXT_63 = " ) , ";
  protected final String TEXT_64 = NL + "                None, ";
  protected final String TEXT_65 = NL + "                " + NL + "              # UNITS";
  protected final String TEXT_66 = " " + NL + "              '";
  protected final String TEXT_67 = "', ";
  protected final String TEXT_68 = NL + "              None, ";
  protected final String TEXT_69 = NL + "              # ACTION";
  protected final String TEXT_70 = " " + NL + "              u'";
  protected final String TEXT_71 = "', ";
  protected final String TEXT_72 = NL + "              u'external', ";
  protected final String TEXT_73 = NL + "              # KINDS";
  protected final String TEXT_74 = NL + "              (u";
  protected final String TEXT_75 = "'";
  protected final String TEXT_76 = "', ";
  protected final String TEXT_77 = "),";
  protected final String TEXT_78 = NL + "              (u'configure', ),";
  protected final String TEXT_79 = NL + "              ),";
  protected final String TEXT_80 = NL + "             )";

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    Properties properties = softPkg.getPropertyFile().getProperties();
    Date date = new Date(System.currentTimeMillis());
    EList<Simple> simpleList = properties.getSimple();
    EList<SimpleSequence> sseqList = properties.getSimpleSequence();
    Iterator<Simple> simpleIter = simpleList.iterator();
    Iterator<SimpleSequence> sseqIterator = sseqList.iterator();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ModelUtil.getPrfFileName(softPkg.getPropertyFile()));
    stringBuffer.append(TEXT_2);
    stringBuffer.append(date.toString());
    stringBuffer.append(TEXT_3);
     
    while (simpleIter.hasNext()) {
        Simple tempSimple = simpleIter.next(); 

    stringBuffer.append(TEXT_4);
     if (tempSimple.getId() != null ) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append(tempSimple.getId());
    stringBuffer.append(TEXT_6);
     } else { 
    stringBuffer.append(TEXT_7);
     } 
    stringBuffer.append(TEXT_8);
     if (tempSimple.getName() != null ) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(tempSimple.getName());
    stringBuffer.append(TEXT_10);
     } else { 
    stringBuffer.append(TEXT_11);
     } 
    stringBuffer.append(TEXT_12);
     if (tempSimple.getType() != null ) { 
    stringBuffer.append(TEXT_13);
    stringBuffer.append(tempSimple.getType().getLiteral());
    stringBuffer.append(TEXT_14);
     } else { 
    stringBuffer.append(TEXT_15);
     } 
    stringBuffer.append(TEXT_16);
     if (tempSimple.getMode() != null ) { 
    stringBuffer.append(TEXT_17);
    stringBuffer.append(tempSimple.getMode().getLiteral());
    stringBuffer.append(TEXT_18);
     } else { 
    stringBuffer.append(TEXT_19);
     } 
    stringBuffer.append(TEXT_20);
     if (tempSimple.getValue() != null ) { 
     if ((tempSimple.getType().getLiteral() == "string") || (tempSimple.getType().getLiteral() == "char")) { 
    stringBuffer.append(TEXT_21);
    stringBuffer.append(tempSimple.getValue());
    stringBuffer.append(TEXT_22);
     } else { 
    stringBuffer.append(TEXT_23);
    stringBuffer.append(tempSimple.getValue());
    stringBuffer.append(TEXT_24);
     } } else { 
    stringBuffer.append(TEXT_25);
     } 
    stringBuffer.append(TEXT_26);
     if (tempSimple.getUnits() != null ) { 
    stringBuffer.append(TEXT_27);
    stringBuffer.append(tempSimple.getUnits());
    stringBuffer.append(TEXT_28);
     } else { 
    stringBuffer.append(TEXT_29);
     } 
    stringBuffer.append(TEXT_30);
     if (tempSimple.getAction() != null ) { 
    stringBuffer.append(TEXT_31);
    stringBuffer.append(tempSimple.getAction().getType().toString());
    stringBuffer.append(TEXT_32);
     } else { 
    stringBuffer.append(TEXT_33);
     } 
    stringBuffer.append(TEXT_34);
     if (!tempSimple.getKind().isEmpty()) { 
    stringBuffer.append(TEXT_35);
     for (Kind tempKind : tempSimple.getKind()) { 
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tempKind.getType().getLiteral());
    stringBuffer.append(TEXT_37);
     } 
    stringBuffer.append(TEXT_38);
     } else { 
    stringBuffer.append(TEXT_39);
     } 
    stringBuffer.append(TEXT_40);
     
    } 
    while (sseqIterator.hasNext()) {
        SimpleSequence tempSSimple = sseqIterator.next(); 

    stringBuffer.append(TEXT_41);
     if (tempSSimple.getId() != null ) { 
    stringBuffer.append(TEXT_42);
    stringBuffer.append(tempSSimple.getId());
    stringBuffer.append(TEXT_43);
     } else { 
    stringBuffer.append(TEXT_44);
     } 
    stringBuffer.append(TEXT_45);
     if (tempSSimple.getName() != null ) { 
    stringBuffer.append(TEXT_46);
    stringBuffer.append(tempSSimple.getName());
    stringBuffer.append(TEXT_47);
     } else { 
    stringBuffer.append(TEXT_48);
     } 
    stringBuffer.append(TEXT_49);
     if (tempSSimple.getType() != null ) { 
    stringBuffer.append(TEXT_50);
    stringBuffer.append(tempSSimple.getType().getLiteral());
    stringBuffer.append(TEXT_51);
     } else { 
    stringBuffer.append(TEXT_52);
     } 
    stringBuffer.append(TEXT_53);
     if (tempSSimple.getMode() != null ) { 
    stringBuffer.append(TEXT_54);
    stringBuffer.append(tempSSimple.getMode().getLiteral());
    stringBuffer.append(TEXT_55);
     } else { 
    stringBuffer.append(TEXT_56);
     } 
    stringBuffer.append(TEXT_57);
     if (tempSSimple.getValues() != null) { 
    stringBuffer.append(TEXT_58);
     List<String> values = tempSSimple.getValues().getValue(); 
    stringBuffer.append(TEXT_59);
     Iterator<String> iterator = values.iterator(); 
    stringBuffer.append(TEXT_60);
     while (iterator.hasNext()) { 
    stringBuffer.append(TEXT_61);
    stringBuffer.append(iterator.next());
    stringBuffer.append(TEXT_62);
     } 
    stringBuffer.append(TEXT_63);
     } else { 
    stringBuffer.append(TEXT_64);
     } 
    stringBuffer.append(TEXT_65);
     if (tempSSimple.getUnits() != null ) { 
    stringBuffer.append(TEXT_66);
    stringBuffer.append(tempSSimple.getUnits());
    stringBuffer.append(TEXT_67);
     } else { 
    stringBuffer.append(TEXT_68);
     } 
    stringBuffer.append(TEXT_69);
     if (tempSSimple.getAction() != null ) { 
    stringBuffer.append(TEXT_70);
    stringBuffer.append(tempSSimple.getAction().getType().toString());
    stringBuffer.append(TEXT_71);
     } else { 
    stringBuffer.append(TEXT_72);
     } 
    stringBuffer.append(TEXT_73);
     if (!tempSSimple.getKind().isEmpty()) { 
    stringBuffer.append(TEXT_74);
     for (Kind tempKind : tempSSimple.getKind()) { 
    stringBuffer.append(TEXT_75);
    stringBuffer.append(tempKind.getType().getLiteral());
    stringBuffer.append(TEXT_76);
     } 
    stringBuffer.append(TEXT_77);
     } else { 
    stringBuffer.append(TEXT_78);
     } 
    stringBuffer.append(TEXT_79);
     
    } 

    stringBuffer.append(TEXT_80);
    return stringBuffer.toString();
  }
} 