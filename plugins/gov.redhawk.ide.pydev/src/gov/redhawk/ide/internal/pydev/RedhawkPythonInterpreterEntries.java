/**
 * This file is protected by Copyright.
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 */
package gov.redhawk.ide.internal.pydev;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.python.pydev.core.interpreters.IInterpreterNewCustomEntries;

import gov.redhawk.ide.pydev.RedhawkIdePyDevPlugin;

public class RedhawkPythonInterpreterEntries implements IInterpreterNewCustomEntries {

	private String ossiePath;

	public RedhawkPythonInterpreterEntries() {
		IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
		try {
			ossiePath = manager.performStringSubstitution("${OssieHome}");
		} catch (CoreException e) {
			RedhawkIdePyDevPlugin.getDefault().getLog().log(
				new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Unable to resolve OSSIEHOME environment variable"));
			return;
		}
		if (!new File(ossiePath).exists()) {
			String msg = String.format("OSSIEHOME (%s) does not exist", ossiePath);
			RedhawkIdePyDevPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, msg));
			ossiePath = null;
		}
	}

	@Override
	public Collection<String> getAdditionalLibraries() {
		Collection<String> retVal = new ArrayList<String>();

		if (ossiePath != null) {
			// Prefix-style
			for (String version : new String[] { "2.6", "2.7", "3.0", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6" }) {
				File prefixStylePath = PythonConfigUtil.getPrefixStylePath(ossiePath, version);
				if (prefixStylePath.isDirectory()) {
					retVal.add(prefixStylePath.toString());
				}
			}

			// Home-style
			File homeStylePath = PythonConfigUtil.getHomeStylePath(ossiePath);
			if (homeStylePath.isDirectory()) {
				retVal.add(homeStylePath.toString());
			}
		}

		return retVal;
	}

	@Override
	public Collection<String> getAdditionalEnvVariables() {
		return Arrays.asList("IDE_REF=${IDE_REF}");
	}

	@Override
	public Collection<String> getAdditionalBuiltins() {
		return Arrays.asList(//
			"__name__", //
			"bulkio.bulkioInterfaces.BULKIO", //
			"bulkio.bulkioInterfaces.BULKIO__POA", //
			"ossie.cf.CF", //
			"ossie.cf.CF__POA", //
			"ossie.cf.ExtendedCF", //
			"ossie.cf.ExtendedCF__POA", //
			"ossie.cf.PortTypes", //
			"ossie.cf.PortTypes__POA", //
			"ossie.cf.StandardEvent", //
			"ossie.cf.StandardEvent__POA", //
			"redhawk.frontendInterfaces.FRONTEND", //
			"redhawk.frontendInterfaces.FRONTEND__POA");
	}

	@Override
	public Map<String, String> getAdditionalStringSubstitutionVariables() {
		return Collections.emptyMap();
	}

}
