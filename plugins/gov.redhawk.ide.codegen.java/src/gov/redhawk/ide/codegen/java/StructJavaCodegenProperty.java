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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.Struct;

/**
 * @since 5.1
 */
public class StructJavaCodegenProperty extends AbstractJavaCodegenProperty {

	private final int indent;
	private final String uniqueName;
	private final Struct struct;
	private final Map<String, Simple> fields;

	/**
	 * Creates a {@link StructJavaCodegenProperty}
	 * 
	 * @param indent the starting indentation (number of tabs) for this property
	 * @param struct the {@link Struct} to generate code for
	 * @param uniqueName the property's unique name which may be different than what is returned by it's getName()
	 *            method
	 */
	public StructJavaCodegenProperty(final int indent, final Struct struct, final String uniqueName) {
		this.indent = indent;
		this.uniqueName = uniqueName;
		this.struct = struct;
		this.fields = JavaGeneratorUtils.createStructFieldSet(struct, "field");
		this.id = struct.getId();
		this.name = struct.getName();
		this.mode = struct.getMode().toString();
		this.configurationKind = struct.getConfigurationKind();
	}

	/**
	 * Generates the javadoc for the StructDef.
	 * 
	 * @return the StructDef javadoc {@link String}
	 */
	public String getStructClassDefJavadoc() {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.getJavadocStart(this.indent));
		final String newline = JavaGeneratorUtils.getJavadocNewline(this.indent);
		builder.append(newline);
		builder.append("The structure for property ");
		builder.append(this.id);
		builder.append(newline);
		builder.append(newline);
		builder.append("<!-- begin-user-doc -->");
		builder.append(newline);
		builder.append("<!-- end-user-doc -->");
		builder.append(newline);
		builder.append("@generated");
		builder.append(JavaGeneratorUtils.getJavadocEnd(this.indent));
		return builder.toString();
	}

	/**
	 * Generates the classdef for the StructDef.
	 * 
	 * @return the StructDef classdef {@link String}
	 */
	public String getStructClassDef() {
		final StringBuilder builder = new StringBuilder(this.getStructClassDefJavadoc());
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));

		builder.append("public static class " + this.uniqueName + "_struct extends StructDef {");
		builder.append(JavaGeneratorUtils.NEWLINE);

		for (final Map.Entry<String, Simple> fe : this.fields.entrySet()) {
			final Simple simple = fe.getValue();
			builder.append(new SimpleJavaCodegenProperty(this.indent + 1, simple, fe.getKey(), true).toString());
		}

		builder.append(JavaGeneratorUtils.NEWLINE);
		builder.append(JavaGeneratorUtils.NEWLINE);
		builder.append(this.getStructConstructor(this.indent + 1));
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append("};");
		return builder.toString();
	}

	/**
	 * Generates the constructor for the StructDef.
	 * 
	 * @param indent the starting indent position (number of tabs)
	 * @return the the StructDef constructor {@link String}
	 */
	public String getStructConstructor(final int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.getJavadocStart(indent));
		builder.append(JavaGeneratorUtils.getJavadocNewline(indent));
		builder.append("@generated");
		builder.append(JavaGeneratorUtils.getJavadocEnd(indent));
		builder.append(JavaGeneratorUtils.newIndentedLine(indent));
		builder.append("public " + this.uniqueName + "_struct() {");
		for (final String name : this.fields.keySet()) {
			builder.append(JavaGeneratorUtils.newIndentedLine(indent + 1));
			builder.append("addElement(" + name + ");");
		}
		builder.append(JavaGeneratorUtils.newIndentedLine(indent + 1));
		builder.append("//begin-user-code");
		builder.append(JavaGeneratorUtils.newIndentedLine(indent + 1));
		builder.append("//end-user-code");
		builder.append(JavaGeneratorUtils.newIndentedLine(indent));
		builder.append("}");
		return builder.toString();
	}

	/**
	 * Accessor for the Map from String(name) to Simple.
	 * 
	 * @return the Struct's fields {@link Map}
	 */
	public Map<String, Simple> getFields() {
		return this.fields;
	}

	/**
	 * Returns the generated java code for this struct property.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getStructClassDef());
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append(this.getJavadoc(this.indent));
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append("public final StructProperty<" + this.uniqueName + "_struct> " + this.uniqueName + " =");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
		builder.append("new StructProperty<" + this.uniqueName + "_struct> (");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getId() + ", //id");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getName() + ", //name");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append("new " + this.uniqueName + "_struct()" + ", //type");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append("new " + this.uniqueName + "_struct()" + ", // tmp type");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getMode() + ", //mode");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append("new String[] { ");
		if (this.struct.getConfigurationKind().isEmpty()) {
			builder.append("\"configure\"");
		} else {
			for (final Iterator<ConfigurationKind> iterator = this.struct.getConfigurationKind().iterator(); iterator.hasNext();) {
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

	@Override
	public String getKind() {
		final StringBuilder builder = new StringBuilder("new String[] ");
		builder.append(JavaGeneratorUtils.propertyConfigurationKindToArrayInitializer(this.configurationKind));
		return builder.toString();
	}

	@Override
	public String[] getKindValues() {
		final List<String> retval = new ArrayList<String>();
		for (final ConfigurationKind k : this.configurationKind) {
			retval.add(k.getType().getLiteral());
		}
		return retval.toArray(new String[0]);
	}
}
