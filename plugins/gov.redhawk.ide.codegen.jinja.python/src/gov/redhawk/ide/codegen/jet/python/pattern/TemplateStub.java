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
package gov.redhawk.ide.codegen.jet.python.pattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.python.PythonGeneratorPlugin;
import mil.jpeojtrs.sca.spd.SoftPkg;

/**
 * This is a placeholder codegen template for legacy 1.8 extension points to point to.  It should never be invoked...
 */
public class TemplateStub implements IScaComponentCodegenTemplate {

	public TemplateStub() {
	}

	@Override
	public List<String> getExecutableFileNames(ImplementationSettings implSettings, SoftPkg softPkg) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAllGeneratedFileNames(ImplementationSettings implSettings, SoftPkg softPkg) {
		return new ArrayList<String>();
	}

	@Override
	public String generateFile(String fileName, SoftPkg softPkg, ImplementationSettings implSettings, Object helperObject) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, PythonGeneratorPlugin.PLUGIN_ID, "Unsupported Code Generation Template"));
	}

	@Override
	public boolean shouldGenerate() {
		return false;
	}

	@Override
	public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		return "";
	}

}
