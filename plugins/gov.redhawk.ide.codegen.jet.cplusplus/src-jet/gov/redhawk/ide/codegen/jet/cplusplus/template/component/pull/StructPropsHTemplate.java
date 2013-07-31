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

import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties.Property;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties.SimpleProperty;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties.StructProperty;
import gov.redhawk.ide.codegen.jet.cplusplus.CppProperties.StructSequenceProperty;
import java.util.ArrayList;
import java.util.List;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

	/**
    * @generated
    */

public class StructPropsHTemplate
{

  protected static String nl;
  public static synchronized StructPropsHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    StructPropsHTemplate result = new StructPropsHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef STRUCTPROPS_H" + NL + "#define STRUCTPROPS_H" + NL + "" + NL + "/*******************************************************************************************" + NL + "" + NL + "    AUTO-GENERATED CODE. DO NOT MODIFY" + NL + "" + NL + "*******************************************************************************************/" + NL + "" + NL + "#include <ossie/CorbaUtils.h>" + NL + "#include <ossie/PropertyInterface.h>" + NL;
  protected final String TEXT_2 = NL + "struct ";
  protected final String TEXT_3 = " {" + NL + "\t";
  protected final String TEXT_4 = " ()" + NL + "\t{";
  protected final String TEXT_5 = NL + "\t\t";
  protected final String TEXT_6 = " = ";
  protected final String TEXT_7 = ";";
  protected final String TEXT_8 = NL + "\t};" + NL + "" + NL + "    std::string getId() {" + NL + "        return std::string(\"";
  protected final String TEXT_9 = "\");" + NL + "    };" + NL + "\t";
  protected final String TEXT_10 = NL + "\t";
  protected final String TEXT_11 = " ";
  protected final String TEXT_12 = ";";
  protected final String TEXT_13 = NL + "};" + NL + "" + NL + "inline bool operator>>= (const CORBA::Any& a, ";
  protected final String TEXT_14 = "& s) {" + NL + "\tCF::Properties* temp;" + NL + "\tif (!(a >>= temp)) return false;" + NL + "\tCF::Properties& props = *temp;" + NL + "\tfor (unsigned int idx = 0; idx < props.length(); idx++) {";
  protected final String TEXT_15 = NL + "\t\tif (!strcmp(\"";
  protected final String TEXT_16 = "\", props[idx].id)) {";
  protected final String TEXT_17 = NL + "\t\tCORBA::Char ";
  protected final String TEXT_18 = ";";
  protected final String TEXT_19 = NL + "\t\t\tif (!(props[idx].value >>= ";
  protected final String TEXT_20 = ")) return false;";
  protected final String TEXT_21 = NL + "\t\t\ts.";
  protected final String TEXT_22 = " = ";
  protected final String TEXT_23 = ";\t";
  protected final String TEXT_24 = NL + "\t\t}";
  protected final String TEXT_25 = NL + "\t}" + NL + "\treturn true;" + NL + "};" + NL + "" + NL + "inline void operator<<= (CORBA::Any& a, const ";
  protected final String TEXT_26 = "& s) {" + NL + "\tCF::Properties props;" + NL + "\tprops.length(";
  protected final String TEXT_27 = ");";
  protected final String TEXT_28 = NL + "\tprops[";
  protected final String TEXT_29 = "].id = CORBA::string_dup(\"";
  protected final String TEXT_30 = "\");" + NL + "\tprops[";
  protected final String TEXT_31 = "].value <<= ";
  protected final String TEXT_32 = ";";
  protected final String TEXT_33 = NL + "\ta <<= props;" + NL + "};" + NL + "" + NL + "inline bool operator== (const ";
  protected final String TEXT_34 = "& s1, const ";
  protected final String TEXT_35 = "& s2) {";
  protected final String TEXT_36 = NL + "    if (s1.";
  protected final String TEXT_37 = "!=s2.";
  protected final String TEXT_38 = ")" + NL + "        return false;";
  protected final String TEXT_39 = NL + "    return true;" + NL + "};" + NL + "" + NL + "inline bool operator!= (const ";
  protected final String TEXT_40 = "& s1, const ";
  protected final String TEXT_41 = "& s2) {" + NL + "    return !(s1==s2);" + NL + "};" + NL + "" + NL + "template<> inline short StructProperty<";
  protected final String TEXT_42 = ">::compare (const CORBA::Any& a) {" + NL + "    if (super::isNil_) {" + NL + "        if (a.type()->kind() == (CORBA::tk_null)) {" + NL + "            return 0;" + NL + "        }" + NL + "        return 1;" + NL + "    }" + NL;
  protected final String TEXT_43 = NL + "    ";
  protected final String TEXT_44 = " tmp;" + NL + "    if (fromAny(a, tmp)) {" + NL + "        if (tmp != this->value_) {" + NL + "            return 1;" + NL + "        }" + NL + "" + NL + "        return 0;" + NL + "    } else {" + NL + "        return 1;" + NL + "    }" + NL + "}" + NL;
  protected final String TEXT_45 = NL + "inline bool operator== (const ";
  protected final String TEXT_46 = "& s1, const ";
  protected final String TEXT_47 = "& s2) {" + NL + "    if (s1.size() != s2.size()) {" + NL + "        return false;" + NL + "    }" + NL + "    for (unsigned int i=0; i<s1.size(); i++) {" + NL + "        if (s1[i] != s2[i]) {" + NL + "            return false;" + NL + "        }" + NL + "    }" + NL + "    return true;" + NL + "};" + NL + "" + NL + "inline bool operator!= (const ";
  protected final String TEXT_48 = "& s1, const ";
  protected final String TEXT_49 = "& s2) {" + NL + "    return !(s1==s2);" + NL + "};" + NL + "" + NL + "template<> inline short StructSequenceProperty<";
  protected final String TEXT_50 = ">::compare (const CORBA::Any& a) {" + NL + "    if (super::isNil_) {" + NL + "        if (a.type()->kind() == (CORBA::tk_null)) {" + NL + "            return 0;" + NL + "        }" + NL + "        return 1;" + NL + "    }" + NL;
  protected final String TEXT_51 = NL + "    ";
  protected final String TEXT_52 = " tmp;" + NL + "    if (fromAny(a, tmp)) {" + NL + "        if (tmp != this->value_) {" + NL + "            return 1;" + NL + "        }" + NL + "" + NL + "        return 0;" + NL + "    } else {" + NL + "        return 1;" + NL + "    }" + NL + "}";
  protected final String TEXT_53 = NL + NL + "#endif";
  protected final String TEXT_54 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    
    List<Property> properties = CppProperties.getProperties(softPkg);
    List<StructProperty> structs = new ArrayList<StructProperty>();
    for (Property prop : properties) {
      if (prop.getType().equalsIgnoreCase("struct")) {
        structs.add((StructProperty)prop);
      } else if (prop.getType().equalsIgnoreCase("structSequence")) {
        structs.add(((StructSequenceProperty)prop).getStructProperty());
      }
    }

    stringBuffer.append(TEXT_1);
    for (StructProperty struct : structs ) {
	List<SimpleProperty> fields = struct.getFields();
    stringBuffer.append(TEXT_2);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_4);
    	for (SimpleProperty field : fields ) {
		if (field.hasValue()) {
    stringBuffer.append(TEXT_5);
    stringBuffer.append(field.getCppName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(field.getCppValue());
    stringBuffer.append(TEXT_7);
    		}
	}
    stringBuffer.append(TEXT_8);
    stringBuffer.append(CppHelper.escapeString(struct.getId()));
    stringBuffer.append(TEXT_9);
    	for (SimpleProperty field : fields ) {
    stringBuffer.append(TEXT_10);
    stringBuffer.append(field.getCppType());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(field.getCppName());
    stringBuffer.append(TEXT_12);
    	}
    stringBuffer.append(TEXT_13);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_14);
    
	for (SimpleProperty field : fields ) {

    stringBuffer.append(TEXT_15);
    stringBuffer.append(CppHelper.escapeString(field.getId()));
    stringBuffer.append(TEXT_16);
    
		String insert = "s." + field.getCppName();
		String tempName = "temp_" + field.getCppName();
		if (field.getType().equals("char")) {
			insert = "CORBA::Any::to_char(" + tempName + ")";
    stringBuffer.append(TEXT_17);
    stringBuffer.append(tempName);
    stringBuffer.append(TEXT_18);
      	} else if (field.getType().equals("octet")) {
			insert = "CORBA::Any::to_octet(" + insert + ")";
		}
    stringBuffer.append(TEXT_19);
    stringBuffer.append(insert);
    stringBuffer.append(TEXT_20);
    		if (field.getType().equals("char")) {
    stringBuffer.append(TEXT_21);
    stringBuffer.append(field.getCppName());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(tempName);
    stringBuffer.append(TEXT_23);
    		}

    stringBuffer.append(TEXT_24);
    
	}

    stringBuffer.append(TEXT_25);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(fields.size());
    stringBuffer.append(TEXT_27);
    
	int index = 0;
	for (SimpleProperty field : fields ) {
		String extract = "s." + field.getCppName();
		if (field.getType().equals("char") || field.getType().equals("octet")) {
			extract = "CORBA::Any::from_" + field.getType() + "(" + extract + ")";
		}
    stringBuffer.append(TEXT_28);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(CppHelper.escapeString(field.getId()));
    stringBuffer.append(TEXT_30);
    stringBuffer.append(index++);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(extract);
    stringBuffer.append(TEXT_32);
    	}
    stringBuffer.append(TEXT_33);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_34);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_35);
    
	index = 0;
	for (SimpleProperty field : fields ) {
		String extract = "s." + field.getCppName();
		if (field.getType().equals("char") || field.getType().equals("octet")) {
			extract = "CORBA::Any::from_" + field.getType() + "(" + extract + ")";
		}

    stringBuffer.append(TEXT_36);
    stringBuffer.append(field.getCppName());
    stringBuffer.append(TEXT_37);
    stringBuffer.append(field.getCppName());
    stringBuffer.append(TEXT_38);
    
	}
    stringBuffer.append(TEXT_39);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_40);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(struct.getCppType());
    stringBuffer.append(TEXT_44);
    
	}
	for (CppProperties.Property prop : properties) {
		if (prop instanceof CppProperties.StructSequenceProperty) { 

    stringBuffer.append(TEXT_45);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_46);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_47);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_48);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_49);
    stringBuffer.append(((CppProperties.StructSequenceProperty) prop).getStructProperty().getCppType());
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(prop.getCppType());
    stringBuffer.append(TEXT_52);
    
	}

    }
    stringBuffer.append(TEXT_53);
    stringBuffer.append(TEXT_54);
    return stringBuffer.toString();
  }
} 