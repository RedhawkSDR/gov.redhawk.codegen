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
package gov.redhawk.ide.codegen.java;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleRef;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.prf.StructValue;

/**
 * @since 5.1
 */
public class StructSequenceJavaCodegenProperty extends AbstractJavaCodegenProperty {

	private final int indent;
	private final String uniqueName;
	private final String structName;
	private final String structNameWithSuffix;
	private final StructSequence structSequence;
	private final Struct struct;
	private final StructJavaCodegenProperty structJavaCodegenProperty;

	/**
	 * Creates a {@link StructSequenceJavaCodegenProperty}
	 * 
	 * @param indent the starting indentation (number of tabs) for this property
	 * @param structSequence the {@link StructSequence} to generate code for
	 * @param uniqueName the property's unique name which may be different than what is returned by it's getName()
	 *            method
	 */
	public StructSequenceJavaCodegenProperty(final int indent, final StructSequence structSequence, final String uniqueName) {
		this.indent = indent;
		this.uniqueName = uniqueName;
		this.structSequence = structSequence;
		this.struct = structSequence.getStruct();
		// TODO: We need to make this unique against simples and struct seq's; need to pass the list here rather than use empty list!
		final List<String> usedMemberNames = Collections.emptyList();
		this.structName = JavaGeneratorUtils.getMemberName(this.struct, "struct", usedMemberNames);
		this.structNameWithSuffix = this.structName + "_struct";
		this.structJavaCodegenProperty = new StructJavaCodegenProperty(this.indent, this.struct, this.structName);
		this.id = structSequence.getId();
		this.name = structSequence.getName();
		this.mode = structSequence.getMode().toString();
	}

	/**
	 * Returns a Simple given a property name.
	 * 
	 * @param name the property name to lookup
	 * @return the Simple associated with the name
	 */
	private Simple lookupSimple(final String name) {
		return this.structJavaCodegenProperty.getFields().get(name);

	}

	/**
	 * Returns a property name given its id.
	 * 
	 * @param id the id to lookup
	 * @return the associated property name
	 */
	private String lookupFieldName(final String id) {
		for (final Entry<String, Simple> entry : this.structJavaCodegenProperty.getFields().entrySet()) {
			if (entry.getValue().getId().equals(id)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns a String of the Java code required to set the StructValue defaults.
	 * 
	 * @return the String of code to set the appropriate StructValue defaults
	 */
	public String getStructVals() {
		final StringBuilder builder = new StringBuilder("ArrayList<" + this.structNameWithSuffix + "> structVals_" + this.structNameWithSuffix + " = new ArrayList<" + this.structNameWithSuffix
		        + ">();");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
		int i = 1;
		for (final StructValue value : this.structSequence.getStructValue()) {
			builder.append(this.structNameWithSuffix + " " + this.structName + i + " = new " + this.structNameWithSuffix + "();");
			for (final SimpleRef ref : value.getSimpleRef()) {
				builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
				final String name = this.lookupFieldName(ref.getRefID());
				builder.append(this.structName + i + "." + name + ".setValue("
				        + JavaGeneratorUtils.toJavaLiteral(this.lookupSimple(name).getType().getLiteral(), ref.getValue()) + ");");
			}
			builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
			builder.append("structVals_" + this.structNameWithSuffix + ".add(" + this.structName + i + ");");
			builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
			i++;
		}
		return builder.toString();
	}

	/**
	 * Returns the String of Java code to create the new StructSequenceProperty.
	 */
	public String getNewStructSequence() {
		final StringBuilder builder = new StringBuilder();
		builder.append("this." + this.uniqueName + " = new StructSequenceProperty<" + this.structName + "_struct> (");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getId() + ", //id");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getName() + ", //name");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.structName + "_struct.class, //type");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append("structVals_" + this.structNameWithSuffix + ", //defaultValue");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getMode() + ", //mode");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append("new String[] { ");
		if (this.structSequence.getConfigurationKind().isEmpty()) {
			builder.append("\"configure\"");
		} else {
			for (final Iterator<ConfigurationKind> iterator = this.structSequence.getConfigurationKind().iterator(); iterator.hasNext();) {
				builder.append("\"" + iterator.next().getType().getLiteral() + "\"");
				if (iterator.hasNext()) {
					builder.append(",");
				}
			}
		}
		builder.append(" } //kind");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(");");
		return builder.toString();
	}

	/**
	 * Returns the string of Java code representing this struct sequence.
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.structJavaCodegenProperty.getStructClassDef());
		builder.append(JavaGeneratorUtils.NEWLINE);
		builder.append(this.getJavadoc(this.indent));
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append("public final StructSequenceProperty<" + this.structNameWithSuffix + "> " + this.uniqueName + ";");
		return builder.toString();
	}
}
