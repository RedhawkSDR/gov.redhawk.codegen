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
package gov.redhawk.ide.codegen.jinja.template;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;

import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class JinjaTemplate implements IScaComponentCodegenTemplate {

	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings, final Object helperObject)
	        throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shouldGenerate() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDefaultFilename(final SoftPkg softPkg, final ImplementationSettings implSettings, final String srcDir) {
		// TODO Auto-generated method stub
		return null;
	}

}
