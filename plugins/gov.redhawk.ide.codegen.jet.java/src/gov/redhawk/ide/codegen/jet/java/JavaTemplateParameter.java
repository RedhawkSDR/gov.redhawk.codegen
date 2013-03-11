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
package gov.redhawk.ide.codegen.jet.java;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import mil.jpeojtrs.sca.spd.Implementation;

public class JavaTemplateParameter extends TemplateParameter {

	private String pkg = null;

	private boolean virtual = false;

	public JavaTemplateParameter(final Implementation impl, final ImplementationSettings implSettings, final String pkg) {
		this(impl, implSettings, pkg, false, true, true, false);
	}

	/**
	 * @since 4.0
	 */
	public JavaTemplateParameter(final Implementation impl, final ImplementationSettings implSettings, final String pkg, final boolean virtual,
	        final boolean genSupport, final boolean genClassDef, final boolean genClassImpl) {
		super(impl, implSettings, genSupport, genClassDef, genClassImpl);
		this.pkg = pkg;
		this.setPortRepId("");
		this.virtual = virtual;
	}

	/**
	 * @since 2.1
	 */
	public boolean isVirtual() {
		return this.virtual;
	}

	/**
	 * @since 2.1
	 */
	public void setVirtual(final boolean virtual) {
		this.virtual = virtual;
	}

	/**
	 * @since 2.1
	 */
	public void setPackage(final String pkg) {
		this.pkg = pkg;
	}

	/**
	 * @since 2.1
	 */
	public String getPackage() {
		return this.pkg;
	}

}
