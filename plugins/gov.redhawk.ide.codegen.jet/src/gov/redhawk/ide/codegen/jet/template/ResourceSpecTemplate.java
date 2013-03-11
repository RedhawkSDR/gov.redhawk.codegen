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
package gov.redhawk.ide.codegen.jet.template;

import gov.redhawk.ide.codegen.jet.TemplateParameter;

import org.eclipse.core.runtime.CoreException;

/**
 * This class acts as a wrapper for {@link gov.redhawk.ide.codegen.jet.template.internal.ResourceSpecTemplate},
 * preventing the need to API version the JET template.
 * 
 * @since 3.1
 */
public class ResourceSpecTemplate {
	
	public String generate(TemplateParameter argument) throws CoreException {
		gov.redhawk.ide.codegen.jet.template.internal.ResourceSpecTemplate template = new gov.redhawk.ide.codegen.jet.template.internal.ResourceSpecTemplate();
		return template.generate(argument);
	}

}
