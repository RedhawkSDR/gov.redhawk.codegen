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
package gov.redhawk.ide.codegen.jet.cplusplus.ports;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.IScaPortCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;

import java.util.Arrays;
import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * @since 9.1
 */
public class MessagingPortTemplate implements IScaPortCodegenTemplate {
	public static final String MESSAGECHANNEL_REPID = "IDL:ExtendedEvent/MessageEvent:1.0";

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg, final String language) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateFile(final String fileName, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateClassDefinition(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateClassImplementation(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateClassSupport(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateClassInstantiator(final String repId, final boolean providesPort, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject, final String language) throws CoreException {
		// TODO Auto-generated method stub
		if (providesPort) {
			return "MessageConsumerPort";
		}
		final List<IPath> searchPaths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
		final Interface intf = IdlUtil.getInstance().getInterface(searchPaths, repId.split(":")[1], true);
		final String nameSpace = intf.getNameSpace();
		final String interfaceName = intf.getName();
		return nameSpace + "_" + interfaceName + "_Out_i";
	}

	@Override
	public boolean shouldGenerate(final String language) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInterfaces(final String[] interfaces) {
		// TODO Auto-generated method stub

	}

}
