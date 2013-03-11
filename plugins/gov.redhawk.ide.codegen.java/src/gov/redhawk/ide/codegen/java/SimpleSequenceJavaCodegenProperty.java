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

import mil.jpeojtrs.sca.prf.SimpleSequence;

/**
 * @since 5.1
 */
public class SimpleSequenceJavaCodegenProperty extends AbstractJavaCodegenProperty {

	private final int indent;
	private final String uniqueName;
	private final SimpleSequence simpleSequence;

	/**
	 * Creates a {@link SimpleSequenceJavaCodegenProperty}
	 * 
	 * @param indent the starting indentation (number of tabs) for this property
	 * @param simpleSequence the {@link SimpleSequence} to generate code for
	 * @param uniqueName the property's unique name which may be different than what is returned by it's getName()
	 *            method
	 */
	public SimpleSequenceJavaCodegenProperty(final int indent, final SimpleSequence simpleSequence, final String uniqueName) {
		this.indent = indent;
		this.id = simpleSequence.getId();
		this.name = simpleSequence.getName();
		this.type = simpleSequence.getType().getName();
		this.simpleSequence = simpleSequence;
		this.mode = simpleSequence.getMode().toString();
		this.action = simpleSequence.getAction().getType().toString();
		this.kind = simpleSequence.getKind();
		this.uniqueName = uniqueName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		final StringBuilder builder = new StringBuilder("new ArrayList<");
		builder.append(JavaGeneratorUtils.getJavaType(this.type));
		builder.append(">");
		builder.append(JavaGeneratorUtils.simpleSequenceToArrayInitializer(this.simpleSequence));
		return builder.toString();
	}

	/**
	 * Returns the string of Java code representing this simple.
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getJavadoc(this.indent));
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent));
		builder.append("public final SimpleSequenceProperty< ");
		builder.append(JavaGeneratorUtils.getJavaType(this.type));
		builder.append("> ");
		builder.append(this.uniqueName);
		builder.append(" =");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 1));

		builder.append("new SimpleSequenceProperty< ");
		builder.append(JavaGeneratorUtils.getJavaType(this.type));
		builder.append(">(");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getId());
		builder.append(", //id");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getName());
		builder.append(", //name");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getType());
		builder.append(", //type");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getValue());
		builder.append(", //default value");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getMode());
		builder.append(", //mode");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getAction());
		builder.append(", //action");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(this.getKind());
		builder.append(" //kind");
		builder.append(JavaGeneratorUtils.newIndentedLine(this.indent + 2));

		builder.append(");");
		return builder.toString();
	}
}
