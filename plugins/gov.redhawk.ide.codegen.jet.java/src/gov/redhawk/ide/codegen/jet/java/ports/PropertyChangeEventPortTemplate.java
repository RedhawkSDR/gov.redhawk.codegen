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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class PropertyChangeEventPortTemplate implements IScaPortCodegenTemplate {
	public static final String EVENTCHANNEL_REPID = "IDL:omg.org/CosEventChannelAdmin/EventChannel:1.0";
	public static final String EVENTCHANNEL_NAME = "propEvent";
	
	public PropertyChangeEventPortTemplate() {
	}

	public String generateFile(final String fileName, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {

		return "";
	}

	public String generateClassDefinition(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		return "";
	}

	public String generateClassImplementation(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// Implementation is handled by the base class(PropertyChangeEventPort)
		return "";
	}

	public String generateClassSupport(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {

		return "import ossie.events.PropertyChangeEventPort";
	}

	public String generateClassInstantiator(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		return "PropertyChangeEventPort()";
	}

	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		return new ArrayList<String>();
	}

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		final List<String> fileNames = new ArrayList<String>();

		return fileNames;
	}

	public boolean shouldGenerate(final String language) {
		return true;
	}

	public void setInterfaces(final String[] interfaces) {
		Arrays.asList(interfaces);
	}

}
