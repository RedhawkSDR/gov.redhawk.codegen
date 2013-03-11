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

import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.Simple;

import org.eclipse.emf.common.util.EList;

/**
 * @since 5.1
 */
public class SimpleJavaCodegenProperty extends AbstractJavaCodegenProperty {

	private final String value;
	private final EList<Kind> kind;
	private final int indent;
	private final String uniqueName;
	private final boolean isMemberOfStruct;

	/**
	 * Creates a {@link SimpleJavaCodegenProperty}
	 * 
	 * @param indent the starting indentation (number of tabs) for this property
	 * @param simple the {@link Simple} to generate code for
	 * @param uniqueName the property's unique name which may be different than what is returned by it's getName()
	 *            method
	 */
	public SimpleJavaCodegenProperty(final int indent, final Simple simple, final String uniqueName) {
		this.indent = indent;
		this.id = simple.getId();
		this.name = simple.getName();
		if (simple.getType() != null) {
			this.type = simple.getType().getName();	
		} else {
			this.type = null;
		}
		this.value = simple.getValue();
		if (simple.getMode() != null) {
			this.mode = simple.getMode().toString();
		} else {
			this.mode = null;
		}
		if (simple.getAction() != null && simple.getAction().getType() != null) {
			this.action = simple.getAction().getType().toString();
		} else {
			this.action = null;
		}
		this.kind = simple.getKind();
		this.uniqueName = uniqueName;
		this.isMemberOfStruct = false;
	}

	/**
	 * Creates a {@link SimpleJavaCodegenProperty}
	 * 
	 * @param indent the starting indentation (number of tabs) for this property
	 * @param simple the {@link Simple} to generate code for
	 * @param uniqueName the property's unique name which may be different than what is returned by it's getName()
	 *            method
	 * @param isMemberOfStruct identifies whether or not the property is part of a larger struct property, if so
	 *            eventing for this property are not enabled           
	 */
	public SimpleJavaCodegenProperty(final int indent, final Simple simple, final String uniqueName, final boolean isMemberOfStruct) {
		this.indent = indent;
		this.id = simple.getId();
		this.name = simple.getName();
		if (simple.getType() != null) {
			this.type = simple.getType().getName();	
		} else {
			this.type = null;
		}
		this.value = simple.getValue();
		if (simple.getMode() != null) {
			this.mode = simple.getMode().toString();
		} else {
			this.mode = null;
		}
		if (simple.getAction() != null && simple.getAction().getType() != null) {
			this.action = simple.getAction().getType().toString();
		} else {
			this.action = null;
		}
		this.kind = simple.getKind();
		this.uniqueName = uniqueName;
		this.isMemberOfStruct = isMemberOfStruct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return JavaGeneratorUtils.toJavaLiteral(this.type, this.value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getKind() {
		final StringBuilder builder = new StringBuilder("new String[] ");
		builder.append(JavaGeneratorUtils.propertyKindToArrayInitializer(this.kind));
		return builder.toString();
	}

	/**
	 * Returns the string of Java code representing this simple.
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getJavadoc(this.indent));
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append("public final SimpleProperty<" + JavaGeneratorUtils.getJavaType(this.type) + "> " + this.uniqueName + " =");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));
		builder.append("new SimpleProperty<" + JavaGeneratorUtils.getJavaType(this.type) + ">(");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getId() + ", //id");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getName() + ", //name");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getType() + ", //type");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getValue() + ", //default value");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getMode() + ", //mode");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getAction() + ", //action");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(this.getKind() + " //kind");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));
		builder.append(");");
		return builder.toString();
	}
}
