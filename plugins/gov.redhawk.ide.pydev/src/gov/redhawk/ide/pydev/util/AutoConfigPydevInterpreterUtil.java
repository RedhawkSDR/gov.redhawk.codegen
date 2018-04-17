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
package gov.redhawk.ide.pydev.util;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.python.pydev.ast.interpreter_managers.InterpreterManagersAPI;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.MisconfigurationException;

import gov.redhawk.ide.internal.pydev.PythonConfigUtil;
import gov.redhawk.ide.internal.pydev.RedhawkPythonInterpreterEntries;
import gov.redhawk.ide.pydev.RedhawkIdePyDevPlugin;

public final class AutoConfigPydevInterpreterUtil {

	private AutoConfigPydevInterpreterUtil() {
	}

	/**
	 * This checks if PyDev is properly configured for REDHAWK.
	 * 
	 * @param monitor the progress monitor for status
	 * @param runtimePathLocation optional location of OSSIEHOME
	 * @return true if the the PyDev configuration is correct for the runtimePathLocation
	 * @throws CoreException
	 * @since 3.1
	 */
	public static boolean isPydevConfigured(final IProgressMonitor monitor, final String runtimePathLocation) throws CoreException {
		final int WORK_STANDARD = 1;
		final SubMonitor progress = SubMonitor.convert(monitor, "Checking PyDev configuration", WORK_STANDARD + WORK_STANDARD);

		final IInterpreterManager man = InterpreterManagersAPI.getPythonInterpreterManager(true);

		// Obviously if nothing is configured, then the REDHAWK settings surely are not configured
		if (!man.isConfigured()) {
			return false;
		}

		IInterpreterInfo info;
		try {
			info = (IInterpreterInfo) man.getDefaultInterpreterInfo(false);
		} catch (final MisconfigurationException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Error with pydev configuration", e));
		}
		if (info == null) {
			return false;
		}
		progress.worked(WORK_STANDARD);

		IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
		final String ossiePath;
		try {
			ossiePath = manager.performStringSubstitution("${OssieHome}");
		} catch (CoreException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Unable to resolve OSSIEHOME", e));
		}

		// Check for REDHAWK path(s) and environment in the current configuration
		File prefixStylePath = PythonConfigUtil.getPrefixStylePath(ossiePath, info.getVersion());
		File homeStylePath = PythonConfigUtil.getHomeStylePath(ossiePath);
		if (prefixStylePath.exists() && !info.getPythonPath().contains(prefixStylePath.toString())) {
			return false;
		}
		if (homeStylePath.exists() && !info.getPythonPath().contains(homeStylePath.toString())) {
			return false;
		}
		if (info.getEnvVariables() == null || !Arrays.asList(info.getEnvVariables()).containsAll(new RedhawkPythonInterpreterEntries().getAdditionalEnvVariables())) {
			return false;
		}
		progress.worked(WORK_STANDARD);

		progress.done();
		return true;
	}

	/**
	 * This configures the PyDev interpreter. <b>NOTE: This is a lengthy, blocking operation.</b>
	 * 
	 * @param monitor the IProgressMonitor for status reporting
	 * @param manualConfigure true for user interaction
	 * @param runtimePathLocation This parameter is ignored as of REDHAWK 2.1.3
	 * @since 3.0
	 */
	public static void configurePydev(final IProgressMonitor monitor, final boolean manualConfigure, final String runtimePathLocation) throws CoreException {
		final int WORK_CLEAR_CACHE = 1;
		final int WORK_CREATE_INFO = 10;
		final int WORK_SET_INFO = 100;
		final SubMonitor progress = SubMonitor.convert(monitor, "Configuring Python environment",
			WORK_CLEAR_CACHE + WORK_CREATE_INFO + WORK_CLEAR_CACHE + WORK_CLEAR_CACHE + WORK_SET_INFO);

		final IInterpreterManager man = InterpreterManagersAPI.getPythonInterpreterManager(true);
		man.clearCaches();
		progress.worked(WORK_CLEAR_CACHE);

		// Get details of the Python environment. Note that most of the important changes to the environment are
		// actually supplied by gov.redhawk.ide.internal.pydev.RedhawkPythonInterpreterEntries
		final IInterpreterInfo info = man.createInterpreterInfo("python", progress.newChild(WORK_CREATE_INFO), manualConfigure);
		if (info == null) {
			// If info is null the user canceled in the dialog
			return;
		}
		info.setName("Python");

		// Set as default
		man.setInfos(new IInterpreterInfo[] { info }, null, progress.newChild(WORK_SET_INFO));

		progress.done();
	}
}
