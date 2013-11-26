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
package gov.redhawk.ide.codegen.jet.python.ports;

import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.python.template.component.pull.PortGeneratorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class PullPortTemplate implements IScaPortCodegenTemplate {

	public PullPortTemplate() {
	}

	@Override
	@SuppressWarnings("deprecation")
	public String generateFile(final String fileName, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
		final Object helperObject, final String language) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		String file = "";

		final List<String> sourceFiles = new ArrayList<String>();
		sourceFiles.add(implSettings.getName() + "_base.py");
		templ.setSourceFiles(sourceFiles.toArray(new String[sourceFiles.size()]));

		return file;
	}

	@Override
	public String generateClassDefinition(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
		final Object helperObject, final String language) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		String file = "";

		templ.setGenClassDef(true);
		templ.setGenClassImpl(false);
		templ.setGenSupport(false);
		templ.setProvidesPort(providesPort);
		templ.setPortRepId(repId);

		file = new PortGeneratorTemplate().generate(templ);

		return file;
	}

	@Override
	public String generateClassImplementation(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
		final Object helperObject, final String language) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		String file = "";

		templ.setGenClassDef(false);
		templ.setGenClassImpl(true);
		templ.setGenSupport(false);
		templ.setProvidesPort(providesPort);
		templ.setPortRepId(repId);

		file = new PortGeneratorTemplate().generate(templ);

		return file;
	}

	@Override
	public String generateClassSupport(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
		final Object helperObject, final String language) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		String file = "";

		templ.setGenClassDef(false);
		templ.setGenClassImpl(false);
		templ.setGenSupport(true);
		templ.setProvidesPort(providesPort);
		templ.setPortRepId(repId);

		file = new PortGeneratorTemplate().generate(templ);

		return file;
	}

	@Override
	public String generateClassInstantiator(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
		final Object helperObject, final String language) throws CoreException {
		return null;
	}

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		final List<String> fileNames = new ArrayList<String>();

		return fileNames;
	}

	@Override
	public boolean shouldGenerate(final String language) {
		return true;
	}

	@Override
	public void setInterfaces(final String[] interfaces) {
		Arrays.asList(interfaces);
	}

}
