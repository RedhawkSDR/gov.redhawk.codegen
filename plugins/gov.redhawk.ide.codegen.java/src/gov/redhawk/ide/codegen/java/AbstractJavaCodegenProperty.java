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
import java.util.List;

import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Kind;

/**
 * Provides a default implementation of {@link JavaCodegenProperty} for all property types.
 * 
 * @since 5.1
 */
public class AbstractJavaCodegenProperty implements JavaCodegenProperty {

	protected String id;
	protected String name;
	protected String type;
	protected String description;
	protected String mode;
	protected String action;
	protected List<Kind> kind;
	protected List<ConfigurationKind> configurationKind;

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return "\"" + JavaGeneratorUtils.escapeString(this.id) + "\"";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return (this.name != null) ? "\"" + JavaGeneratorUtils.escapeString(this.name) + "\"" : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return "\"" + this.type + "\"";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getJavadoc(final int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.getJavadocStart(indent));
		builder.append(JavaGeneratorUtils.getJavadocNewline(indent));
		builder.append("The property ");
		builder.append(this.id);
		final String newline = JavaGeneratorUtils.getJavadocNewline(indent);
		builder.append(newline);
		if (this.getDescription() == null || this.getDescription().equals("")) {
			builder.append("If the meaning of this property isn't clear, a description should be added.");
		} else {
			builder.append(this.getDescription());
		}
		builder.append(newline);
		builder.append(newline);
		builder.append("<!-- begin-user-doc -->");
		builder.append(newline);
		builder.append("<!-- end-user-doc -->");
		builder.append(newline);
		builder.append("@generated");
		builder.append(JavaGeneratorUtils.getJavadocEnd(indent));
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getMode() {
		return "\"" + this.mode + "\"";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getAction() {
		return "\"" + this.action + "\"";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKind() {
		final StringBuilder builder = new StringBuilder("new String[] ");
		builder.append(JavaGeneratorUtils.propertyKindToArrayInitializer(this.kind));
		return builder.toString();
	}
	
	public String [] getKindValues() {
		List<String> retval = new ArrayList<String>();
		for (Kind k : this.kind) {
			retval.add(k.getType().getLiteral());
		}
		return retval.toArray(new String[retval.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValue() {
		return "";
	}
}
