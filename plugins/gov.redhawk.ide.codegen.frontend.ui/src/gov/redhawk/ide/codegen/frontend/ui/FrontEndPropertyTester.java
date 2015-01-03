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
package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.ui.editor.SCAFormEditor;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @since 1.1
 * 
 */
public class FrontEndPropertyTester extends PropertyTester {

	/**
	 * 
	 */
	public FrontEndPropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof SCAFormEditor) {
			SCAFormEditor editor = (SCAFormEditor) receiver;
			Resource resource = editor.getMainResource();
			IProject project = ModelUtil.getProject(resource);
			try {
				if (project != null) {
					return project.hasNature(FrontEndProjectNature.ID);
				}
			} catch (CoreException e) {
				return false;
			}
		}

		return false;
	}

}
