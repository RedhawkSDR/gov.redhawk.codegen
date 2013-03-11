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
package gov.redhawk.ide.codegen.jet.cplusplus;

import gov.redhawk.ide.codegen.cplusplus.CppHelper;
import gov.redhawk.sca.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mil.jpeojtrs.sca.prf.AccessType;
import mil.jpeojtrs.sca.prf.Action;
import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.PrfPackage;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleRef;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.prf.StructValue;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.DceUuidUtil;

import org.eclipse.emf.ecore.util.FeatureMap.Entry;

/**
 * @since 7.0
 */
public class CppProperties {

	private CppProperties() {
	}

	public static class Property {

		private final String type;
		private final String id;
		private final String name;
		private final String cppName;
		private final Object value;
		private final String mode;
		private final String action;
		private final String units;
		private String kinds;

		/**
		 * Accepts all the necessary parameters to create a CPP Property and a collection
		 * 
		 * @since 9.0
		 */
		public Property(final String type, final String id, final String name, final Object value, final AccessType mode, final Action action,
		        final String units, final List<String> names) {
			this.type = type;
			this.id = id;
			this.name = name;

			// To create the C++ variable name:
			//   1) Use name, if provided
			//   2) If id is a DCE UUID, use "prop"-something-or-other
			//   3) Just use the id
			if (this.name != null && this.name.trim().length() != 0) {
				this.cppName = StringUtil.defaultCreateUniqueString(CppHelper.makeValidIdentifier(this.name), names);
			} else if (DceUuidUtil.isValid(this.id)) {
				this.cppName = StringUtil.defaultCreateUniqueString("prop", names);
			} else {
				this.cppName = StringUtil.defaultCreateUniqueString(CppHelper.makeValidIdentifier(this.id), names);
			}
			
			this.value = value;
			this.mode = mode.getLiteral();
			this.action = (action != null) ? action.getType().getLiteral() : "external"; // SUPPRESS CHECKSTYLE AvoidInline
			this.units = units;
			this.kinds = "";
		}

		/**
		 * The SCA type of the property.
		 * 
		 * @return
		 */
		public String getType() {
			return this.type;
		}

		/**
		 * The C++ type represented by the SCA type {@link #getType()}.
		 * 
		 * @return
		 */
		public String getCppType() {
			return CppProperties.getPropertyTypeMapping(this.type);
		}

		/**
		 * If the property has a value in the 'name' field (it's optional!).
		 * @return
		 */
		public boolean hasName() {
			return this.name != null;
		}

		/**
		 * The name associated with the property (it's optional!).
		 * 
		 * @return
		 * @see #hasName()
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * The C++ identifier name that should be used in generated code for this property.
		 * @return
		 */
		public String getCppName() {
			return this.cppName;
		}

		/**
		 * If the property has a default value associated with it.
		 * 
		 * @return
		 */
		public boolean hasValue() {
			return (this.value != null);
		}

		/**
		 * The property's default value, if any.
		 * 
		 * @return
		 */
		public Object getValue() {
			return this.value;
		}

		/**
		 * The property's default value, if any, expressed as a C++ literal.
		 * <p>
		 * Some examples:
		 * <ul>
		 * <li>"myvalue"</li>
		 * <li>'x'</li>
		 * <li>true</li>
		 * </ul>
		 * @return
		 */
		public String getCppValue() {
			if (hasValue()) {
				return CppProperties.convertToCppValue(this.value, getType());
			}
			return null;
		}

		/**
		 * The ID field of the property (required).
		 * 
		 * @return
		 */
		public String getId() {
			return this.id;
		}

		public String getMode() {
			return this.mode;
		}

		public String getKinds() {
			return this.kinds;
		}

		public String getAction() {
			return this.action;
		}

		public String getUnits() {
			return this.units;
		}

		protected void setKinds(final String kinds) {
			this.kinds = kinds;
		}
	}

	public static class SimpleProperty extends Property {
		/**
		 * @since 7.0
		 */
		public SimpleProperty(final Simple simple, final List<String> names) {
			super(simple.getType().getLiteral(), simple.getId(), simple.getName(), simple.getValue(), simple.getMode(), simple.getAction(), simple.getUnits(), names);
			final StringBuffer buf = new StringBuffer();
			if (simple.getKind().isEmpty()) {
				buf.append("configure");
			} else {
				for (final Iterator<Kind> iterator = simple.getKind().iterator(); iterator.hasNext();) {
					buf.append(iterator.next().getType().getLiteral());
					if (iterator.hasNext()) {
						buf.append(',');
					}
				}
			}
			if (buf.length() > 0) {
				setKinds(buf.toString());
			}
		}
	}

	/**
	 * @since 7.0
	 */
	public static class SimpleSeqProperty extends Property {
		/**
		 * @since 7.0
		 */
		public SimpleSeqProperty(final SimpleSequence seq, final List<String> names) {
			super(seq.getType().getLiteral(), seq.getId(), seq.getName(), (seq.getValues() != null) ? seq.getValues() // SUPPRESS CHECKSTYLE AvoidInline
			        .getValue()
			        .toArray() : null, seq.getMode(), seq.getAction(), seq.getUnits(), names);
			final StringBuffer buf = new StringBuffer();
			if (seq.getKind().isEmpty()) {
				buf.append("configure");
			} else {
				for (final Iterator<Kind> iterator = seq.getKind().iterator(); iterator.hasNext();) {
					buf.append(iterator.next().getType().getLiteral());
					if (iterator.hasNext()) {
						buf.append(',');
					}
				}
			}
			if (buf.length() > 0) {
				setKinds(buf.toString());
			}
		}

		@Override
		public String getCppType() {
			return "std::vector<" + CppProperties.getPropertyTypeMapping(getType()) + ">";
		}

		public String[] getCppValues() {
			final List<String> values = new ArrayList<String>();
			for (final Object obj : (Object[]) this.getValue()) {
				values.add(CppProperties.convertToCppValue(obj, getType()));
			}
			return values.toArray(new String[0]);
		}

		@Override
		public String getCppValue() {
			// Return the C++ name of the vector itself, so it can be used as the initializer to addProperty().
			return getCppName();
		}

	}

	public static class StructProperty extends Property {

		private final String cppValue;
		private final List<SimpleProperty> fields = new ArrayList<SimpleProperty>();
		private final Map<String, SimpleProperty> idToPropMap = new HashMap<String, SimpleProperty>();

		/**
		 * @since 7.0
		 */
		public StructProperty(final Struct struct, final List<String> names) {
			super("struct", struct.getId(), struct.getName(), null, struct.getMode(), null, "", names);
			boolean hasValues = false;
			final List<String> fieldNames = new ArrayList<String>();
			for (final Simple simple : struct.getSimple()) {
				final SimpleProperty prop = new SimpleProperty(simple, fieldNames);
				fieldNames.add(prop.getCppName());
				if (prop.hasValue()) {
					hasValues = true;
				}
				this.idToPropMap.put(simple.getId(), prop);
				this.fields.add(prop);
			}
			final StringBuffer buf = new StringBuffer();
			if (struct.getConfigurationKind().isEmpty()) {
				buf.append("configure");
			} else {
				for (final Iterator<ConfigurationKind> iterator = struct.getConfigurationKind().iterator(); iterator.hasNext();) {
					buf.append(iterator.next().getType().getLiteral());
					if (iterator.hasNext()) {
						buf.append(',');
					}
				}
			}
			if (buf.length() > 0) {
				setKinds(buf.toString());
			}
			if (hasValues) {
				this.cppValue = null;
			} else {
				this.cppValue = getCppType() + "()";
			}
		}

		@Override
		public String getCppType() {
			if (this.getName() != null) {
				return CppHelper.makeValidIdentifier(this.getName()) + "_struct";
			} else {
				return CppHelper.makeValidIdentifier(this.getId()) + "_struct";
			}

		}

		@Override
		public boolean hasValue() {
			return (this.cppValue != null);
		}

		@Override
		public String getCppValue() {
			return this.cppValue;
		}

		/**
		 * @since 7.0
		 */
		public Map<String, SimpleProperty> getIdToPropertyMap() {
			return this.idToPropMap;
		}

		public List<SimpleProperty> getFields() {
			return this.fields;
		}

	}

	/**
	 * @since 7.0
	 */
	public static class StructSequenceProperty extends Property {

		private final StructProperty structProperty;
		private final Struct struct;
		private final Map<CppProperties.SimpleProperty, List<String>> valuesMap = new HashMap<CppProperties.SimpleProperty, List<String>>();
		private final StructSequence structSequence;

		public StructSequenceProperty(final StructSequence structSequence, final List<String> names) {
			super("structSequence", structSequence.getId(), structSequence.getName(), null, structSequence.getMode(), null, "", names);
			this.structSequence = structSequence;
			this.struct = structSequence.getStruct();
			this.structProperty = new StructProperty(this.struct, names);

			final StringBuffer buf = new StringBuffer();
			if (structSequence.getConfigurationKind().isEmpty()) {
				buf.append("configure");
			} else {
				for (final Iterator<ConfigurationKind> iterator = structSequence.getConfigurationKind().iterator(); iterator.hasNext();) {
					buf.append(iterator.next().getType().getLiteral());
					if (iterator.hasNext()) {
						buf.append(',');
					}
				}
			}
			if (buf.length() > 0) {
				setKinds(buf.toString());
			}
			final List<SimpleProperty> simples = this.structProperty.getFields();

			for (final SimpleProperty simple : simples) {
				final List<String> value = new ArrayList<String>();
				if (!structSequence.getStructValue().isEmpty()) {
					for (final StructValue structValue : structSequence.getStructValue()) {
						boolean found = false;
						for (final SimpleRef ref : structValue.getSimpleRef()) {
							if (ref.getRefID().equals(simple.getId())) {
								value.add(CppProperties.convertToCppValue(ref.getValue(), simple.getType()));
								found = true;
								break;
							}
						}
						if (!found) {
							//Add the default value
							value.add(CppProperties.getDefaultForType(simple.getType()));
						}
					}
				}
				this.valuesMap.put(simple, value);
			}
		}

		@Override
		public String getCppType() {
			return "std::vector<" + this.structProperty.getCppType() + ">";
		}

		public Map<SimpleProperty, List<String>> getValueMap() {
			return this.valuesMap;
		}

		public StructProperty getStructProperty() {
			return this.structProperty;
		}

		public int numberOfStructValues() {
			return this.structSequence.getStructValue().size();
		}
	}

	/**
	 * @since 7.0
	 */
	public static List<Property> getProperties(final SoftPkg pkg) {
		final List<Property> properties = new ArrayList<Property>();
		final List<String> names = new ArrayList<String>();
		final Properties pkgProperties = pkg.getPropertyFile().getProperties();
		if (pkgProperties != null) {
			for (final Entry e : pkgProperties.getProperties()) {
				Property property = null;
				switch (e.getEStructuralFeature().getFeatureID()) {
				case PrfPackage.PROPERTIES__SIMPLE:
					property = new SimpleProperty((Simple) e.getValue(), names);
					break;
				case PrfPackage.PROPERTIES__SIMPLE_SEQUENCE:
					property = new SimpleSeqProperty((SimpleSequence) e.getValue(), names);
					break;
				case PrfPackage.PROPERTIES__STRUCT:
					property = new StructProperty((Struct) e.getValue(), names);
					break;
				case PrfPackage.PROPERTIES__STRUCT_SEQUENCE:
					property = new StructSequenceProperty((StructSequence) e.getValue(), names);
					break;
				default:
					break;
				}
				if (property != null) {
					names.add(property.getCppName());
					properties.add(property);
					if (property.getType().equalsIgnoreCase("structsequence")) {
						names.add(((StructSequenceProperty) property).structProperty.getCppName());
					}

				}
			}
		}

		return properties;
	}

	/**
	 * Convenience method for converting values to cpp values.
	 * 
	 * @param obj the value to convert
	 * @param type the type of value
	 * @return the cpp value
	 * @since 7.0
	 */
	public static String convertToCppValue(final Object obj, final String type) {
		if (type.equals("string") || type.equals("objref")) {
			return "\"" + CppHelper.escapeString(obj.toString()) + "\"";
		} else if (type.equals("char")) {
			return "\'" + CppHelper.escapeChar(obj.toString().charAt(0)) + "\'";
		} else if (type.equals("boolean")) {
			if (Boolean.parseBoolean((String) obj)) {
				return "true";
			} else {
				return "false";
			}
		} else {
			return (String) obj;
		}
	}

	/**
	 * Convenience method for returning a default value for a type.
	 * 
	 * @param type the type to obtain a default for
	 * @return the default value for that type
	 * @since 7.0
	 */
	public static String getDefaultForType(final String type) {
		if (type.equals("string") || type.equals("objref")) {
			return "\"\"";
		} else if (type.equals("char")) {
			return "\'\\0\'";
		} else if (type.equals("boolean")) {
			return "false";
		} else { //TODO other cases if necessary
			return Integer.toString(0);
		}
	}

	/**
	 * Maps the SCA type specified in the property's XML to a string representing the CORBA type.
	 * 
	 * @param type The SCA data type
	 * @return The CORBA data type
	 */
	public static String getPropertyTypeMapping(final String type) {
		if (type.equals("string") || type.equals("objref")) {
			return "std::string";
		} else if (type.equals("boolean")) {
			return "bool";
		} else if (type.equals("octet")) {
			return "unsigned char";
		} else if (type.equals("short")) {
			return "short";
		} else if (type.equals("ushort")) {
			return "unsigned short";
		} else if (type.equals("long")) {
			return "CORBA::Long";
		} else if (type.equals("longlong")) {
			return "CORBA::LongLong";
		} else if (type.equals("ulong")) {
			return "CORBA::ULong";
		} else if (type.equals("ulonglong")) {
			return "CORBA::ULongLong";
		} else if (type.equals("float")) {
			return "float";
		} else if (type.equals("double")) {
			return "double";
		}
		return type;
	}

}
