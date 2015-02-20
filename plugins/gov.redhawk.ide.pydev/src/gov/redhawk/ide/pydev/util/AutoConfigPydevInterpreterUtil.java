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
package gov.redhawk.ide.pydev.util;

import gov.redhawk.ide.pydev.RedhawkIdePyDevPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.MisconfigurationException;
import org.python.pydev.plugin.PydevPlugin;
import org.python.pydev.runners.SimplePythonRunner;
import org.python.pydev.shared_core.string.StringUtils;
import org.python.pydev.shared_core.structure.Tuple;
import org.python.pydev.ui.pythonpathconf.InterpreterInfo;

public final class AutoConfigPydevInterpreterUtil {

	private static final String IDE_ENV_VAR_REF = "IDE_REF=${IDE_REF}";

	private AutoConfigPydevInterpreterUtil() {
	}

	/**
	 * @param monitor
	 * @param runtimePathLocation
	 * @param attemptFixes This is now ignored
	 * @deprecated Use {@link #isPydevConfigured(IProgressMonitor, String)}
	 * @since 3.1
	 */
	@Deprecated
	public static boolean checkPyDevConfiguration(final IProgressMonitor monitor, final String runtimePathLocation, final boolean attemptFixes)
		throws CoreException {
		return isPydevConfigured(monitor, runtimePathLocation);
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

		try {
			final IInterpreterManager man = PydevPlugin.getPythonInterpreterManager(true);

			// Obviously if nothing is configured, then the REDHAWK settings surely are not configured
			if (!man.isConfigured()) {
				return false;
			}

			InterpreterInfo info;
			try {
				info = (InterpreterInfo) man.getDefaultInterpreterInfo(false);
			} catch (final MisconfigurationException e) {
				throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Error with pydev configuration", e));
			}
			if (info == null) {
				return false;
			}
			progress.worked(WORK_STANDARD);

			// TODO: Use the proper Eclipse variable and resolve it
			final String ossiePath;
			if ((runtimePathLocation != null) && (runtimePathLocation.length() > 0) && !"${OSSIEHOME}".equals(runtimePathLocation)) {
				ossiePath = runtimePathLocation;
			} else {
				ossiePath = System.getenv("OSSIEHOME");
			}
			if ((ossiePath == null) || (ossiePath.trim().length() == 0)) {
				throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID,
					"OSSIEHOME environment variable not defined, auto config failed.", null));
			}

			// Check for REDHAWK path(s) and environment in the current configuration
			File prefixStylePath = getPrefixStylePath(ossiePath, info.getVersion());
			File homeStylePath = getHomeStylePath(ossiePath);
			if (prefixStylePath.exists() && !info.libs.contains(prefixStylePath.toString())) {
				return false;
			}
			if (homeStylePath.exists() && !info.libs.contains(homeStylePath.toString())) {
				return false;
			}
			if (info.getEnvVariables() == null || !Arrays.asList(info.getEnvVariables()).contains(IDE_ENV_VAR_REF)) {
				return false;
			}
			progress.worked(WORK_STANDARD);
		} finally {
			progress.done();
		}

		return true;
	}

	/**
	 * This configures the PyDev interpreter. <b>NOTE: This is a lengthy, blocking operation.</b>
	 * 
	 * @param monitor the IProgressMonitor for status reporting
	 * @param manualConfigure true for user interaction
	 * @param runtimePathLocation optional location of OSSIEHOME
	 * @since 3.0
	 */
	public static void configurePydev(final IProgressMonitor monitor, final boolean manualConfigure, final String runtimePathLocation) throws CoreException {
		final int WORK_SMALL = 1;
		final int WORK_EXEC_INFO_SCRIPT = 10;
		final int WORK_SET_INFO = 100;
		final SubMonitor submonitor = SubMonitor.convert(monitor, "Configuring Python environment", WORK_SMALL + WORK_EXEC_INFO_SCRIPT + WORK_SMALL
			+ WORK_SMALL + WORK_SET_INFO);

		try {
			final IInterpreterManager man = PydevPlugin.getPythonInterpreterManager(true);
			man.clearCaches();
			submonitor.worked(WORK_SMALL);

			// Run script to get details of the Python environment
			File script = PydevPlugin.getScriptWithinPySrc("interpreterInfo.py");
			final Tuple<String, String> outTup = new SimplePythonRunner().runAndGetOutputWithInterpreter("python", script.getAbsolutePath(), null, null, null,
				submonitor.newChild(WORK_EXEC_INFO_SCRIPT), null);

			// Parse the output of the script. Ask the user for input via dialog if manualConfigure == true.
			final InterpreterInfo info = InterpreterInfo.fromString(outTup.o1, manualConfigure);
			submonitor.worked(WORK_SMALL);

			// If info is null the user canceled in the dialog
			if (info == null) {
				return;
			}

			// TODO: Use the proper Eclipse variable and resolve it
			info.libs.addAll(AutoConfigPydevInterpreterUtil.parseString(outTup.o1));
			final String ossiePath;
			if ((runtimePathLocation != null) && (runtimePathLocation.length() > 0) && !"${OSSIEHOME}".equals(runtimePathLocation)) {
				ossiePath = runtimePathLocation;
			} else {
				ossiePath = System.getenv("OSSIEHOME");
			}
			if ((ossiePath == null) || (ossiePath.trim().length() == 0)) {
				throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID,
					"OSSIEHOME environment variable not defined, auto config failed.", null));
			}
			if (!new File(ossiePath).exists()) {
				throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "OSSIEHOME=" + ossiePath
					+ " does not exist, auto config failed.", null));
			}

			// Prefix-style
			File prefixStylePath = getPrefixStylePath(ossiePath, info.getVersion());
			if (prefixStylePath.isDirectory() && !info.libs.contains(prefixStylePath.toString())) {
				info.libs.add(prefixStylePath.toString());
			}

			// Home-style
			File homeStylePath = getHomeStylePath(ossiePath);
			if (homeStylePath.isDirectory() && !info.libs.contains(homeStylePath.toString())) {
				info.libs.add(prefixStylePath.toString());
			}

			info.addForcedLib("__name__");
			info.addForcedLib("bulkio.bulkioInterfaces.BULKIO");
			info.addForcedLib("bulkio.bulkioInterfaces.BULKIO__POA");
			info.addForcedLib("ossie.cf.CF");
			info.addForcedLib("ossie.cf.CF__POA");
			info.addForcedLib("ossie.cf.ExtendedCF");
			info.addForcedLib("ossie.cf.ExtendedCF__POA");
			info.addForcedLib("ossie.cf.PortTypes");
			info.addForcedLib("ossie.cf.PortTypes__POA");
			info.addForcedLib("ossie.cf.StandardEvent");
			info.addForcedLib("ossie.cf.StandardEvent__POA");
			info.addForcedLib("redhawk.frontendInterfaces.FRONTEND");
			info.addForcedLib("redhawk.frontendInterfaces.FRONTEND__POA");
			info.restoreCompiledLibs(submonitor.newChild(WORK_SMALL));
			info.setName("Python");
			if (info.getEnvVariables() == null) {
				info.setEnvVariables(new String[] { IDE_ENV_VAR_REF });
			} else {
				info.updateEnv(new String[] { IDE_ENV_VAR_REF });
			}

			man.setInfos(new IInterpreterInfo[] { info }, null, submonitor.newChild(WORK_SET_INFO));

			PydevPlugin.setPythonInterpreterManager(man);
		} finally {
			submonitor.done();
		}
	}

	private static ArrayList<String> parseString(String received) {
		final Tuple<String, String> predefCompsPath = AutoConfigPydevInterpreterUtil.splitOnFirst(received, "@PYDEV_PREDEF_COMPS_PATHS@");
		final Tuple<String, String> stringSubstitutionVarsSplit = AutoConfigPydevInterpreterUtil.splitOnFirst(predefCompsPath.o1, "@PYDEV_STRING_SUBST_VARS@");
		received = stringSubstitutionVarsSplit.o1.replaceAll("\n", "").replaceAll("\r", "");

		if (received.startsWith("Name:")) {
			final int endNameIndex = received.indexOf(":EndName:");
			if (endNameIndex != -1) {
				received = received.substring(endNameIndex + ":EndName:".length());
			}
		}

		final Tuple<String, String> envVarsSplit = StringUtils.splitOnFirst(received, '^');
		final Tuple<String, String> forcedSplit = StringUtils.splitOnFirst(envVarsSplit.o1, '$');
		final String[] exeAndLibs1 = StringUtils.splitOnFirst(forcedSplit.o1, '@').o1.split("\\|");

		final ArrayList<String> toAsk = new ArrayList<String>();
		for (int i = 1; i < exeAndLibs1.length; i++) { // start at 1 (0 is exe)
			String trimmed = exeAndLibs1[i].trim();
			if ((trimmed.length() > 0) && trimmed.endsWith("OUT_PATH") && (trimmed.indexOf("site-packages") != -1)) {
				trimmed = trimmed.substring(0, trimmed.length() - 8); // SUPPRESS CHECKSTYLE MagicNumber
				toAsk.add(trimmed);
			}
		}
		return toAsk;
	}

	private static Tuple<String, String> splitOnFirst(final String fullRep, final String toSplit) {
		final int i = fullRep.indexOf(toSplit);
		if (i != -1) {
			return new Tuple<String, String>(fullRep.substring(0, i), fullRep.substring(i + toSplit.length()));
		} else {
			return new Tuple<String, String>(fullRep, "");
		}
	}

	/**
	 * @deprecated Use {@link #configurePydev(IProgressMonitor, boolean, String)} instead
	 */
	@Deprecated
	public static void configurePydev(final IProgressMonitor monitor, final int i, final String pathLocation) throws CoreException {
		AutoConfigPydevInterpreterUtil.configurePydev(monitor, i > 0, pathLocation);
	}

	/**
	 * Generates a 'prefix-style' Python path
	 *
	 * @param ossiehome The location of REDHAWK
	 * @param pythonVersion The Python version string, e.g. "2.6"
	 * @return A File representing the python path
	 */
	private static File getPrefixStylePath(String ossiehome, String pythonVersion) {
		return new File(ossiehome + "/lib/python" + pythonVersion + "/site-packages");
	}

	/**
	 * Generates a 'home-style' Python path
	 *
	 * @param ossiehome The location of REDHAWK
	 * @return A File representing the python path
	 */
	private static File getHomeStylePath(String ossiehome) {
		return new File(ossiehome + "/lib/python");
	}
}
