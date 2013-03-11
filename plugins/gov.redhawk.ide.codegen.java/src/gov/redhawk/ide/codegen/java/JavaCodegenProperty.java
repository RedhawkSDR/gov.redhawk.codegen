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

import gov.redhawk.ide.codegen.util.CodegenProperty;

/**
 * Extends {@link CodegenProperty} with Java specific methods.
 * 
 * @since 5.1
 */
public interface JavaCodegenProperty extends CodegenProperty {

	/**
	 * Returns this property's javadoc with proper indentation.
	 * 
	 * @param indent the starting tabbed indentation level
	 * @return the javadoc for the property in String format
	 */
	String getJavadoc(int indent);
}
