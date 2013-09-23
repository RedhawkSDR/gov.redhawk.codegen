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
package gov.redhawk.ide.codegen.jet.java.ports;

import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.ide.codegen.jet.java.template.component.ProvidesPortJavaTemplate;
import gov.redhawk.ide.codegen.jet.java.template.component.UsesPortJavaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class PullPortTemplate implements IScaPortCodegenTemplate {

	private List<String> interfaces;

	public PullPortTemplate() {
	}

	@Override
	public String generateFile(final String fileName, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		String file = "";

		templ.setGenSupport(true);
		templ.setGenClassDef(true);
		templ.setGenClassImpl(false);

		if (providesPort) {
			file = new ProvidesPortJavaTemplate().generate(templ);
		} else {
			file = new UsesPortJavaTemplate().generate(templ);
		}
		return file;
	}

	@Override
	public String generateClassDefinition(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		String definition = "";

		templ.setGenSupport(false);
		templ.setGenClassDef(true);
		templ.setGenClassImpl(false);

		if (providesPort) {
			definition = new ProvidesPortJavaTemplate().generate(templ);
		} else {
			definition = new UsesPortJavaTemplate().generate(templ);
		}
		return definition;
	}

	@Override
	public String generateClassSupport(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		final JavaTemplateParameter templ = (JavaTemplateParameter) helperObject;
		String support = "";

		templ.setGenSupport(true);
		templ.setGenClassDef(false);
		templ.setGenClassImpl(false);

		if (providesPort) {
			support = new ProvidesPortJavaTemplate().generate(templ);
		} else {
			support = new UsesPortJavaTemplate().generate(templ);
		}
		return support;
	}

	@Override
	public String generateClassImplementation(String repId, boolean providesPort, SoftPkg softPkg, ImplementationSettings implSettings, Object helperObject,
	        String language) throws CoreException {
		return null;
	}

	@Override
	public String generateClassInstantiator(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		return null;
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) throws CoreException {
		final List<String> fileNames = new ArrayList<String>();
		final Ports ports = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts();

		if (ports != null) {
			for (Uses use : ports.getUses()) {
				// Check to see that we handle this port
				for (String iface : this.interfaces) {
					if (Pattern.matches(iface, use.getRepID())) {
						fileNames.add(JavaGeneratorUtils.getPortName(use.getRepID()) + "OutPort.java");
					}
				}
			}
			for (Provides pro : ports.getProvides()) {
				// Check to see that we handle this port
				for (String iface : this.interfaces) {
					if (Pattern.matches(iface, pro.getRepID())) {
						fileNames.add(JavaGeneratorUtils.getPortName(pro.getRepID()) + "InPort.java");
					}
				}
			}
		}

		return fileNames;
	}

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		final List<String> fileNames = new ArrayList<String>();

		return fileNames;
	}

	@Override
	public boolean shouldGenerate(final String language) {
		return true;
	}

	@Override
	public void setInterfaces(String[] interfaces) {
		this.interfaces = Arrays.asList(interfaces);
	}
}
