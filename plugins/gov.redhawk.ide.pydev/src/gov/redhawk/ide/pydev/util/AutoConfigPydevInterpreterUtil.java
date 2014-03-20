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
import org.python.pydev.core.docutils.StringUtils;
import org.python.pydev.plugin.PydevPlugin;
import org.python.pydev.runners.SimplePythonRunner;
import org.python.pydev.shared_core.structure.Tuple;
import org.python.pydev.ui.pythonpathconf.InterpreterInfo;

public final class AutoConfigPydevInterpreterUtil {

	private AutoConfigPydevInterpreterUtil() {

	}

	/**
	 * This checks if PyDev contains the required paths.
	 * 
	 * @param monitor the progress monitor for status
	 * @param runtimePathLocation optional location of OSSIEHOME
	 * @return true if the locations that we add are already included
	 * @throws CoreException
	 * @since 3.1
	 */
	public static boolean isPydevConfigured(final IProgressMonitor monitor, final String runtimePathLocation) throws CoreException {
		return AutoConfigPydevInterpreterUtil.checkPyDevConfiguration(monitor, runtimePathLocation, true);
	}

	/**
	 * This checks if PyDev is configured for REDHAWK.
	 * 
	 * @param monitor the progress monitor for status
	 * @param runtimePathLocation optional location of OSSIEHOME
	 * @param attemptFixes if true this function will attempt to fix the configure; if the fix is successful (without user interaction) true will be returned
	 * @return true if the the pydev configuration is correct; if false the PyDev configuration will need to be restored
	 * @throws CoreException
	 * @since 3.1
	 */
	public static boolean checkPyDevConfiguration(final IProgressMonitor monitor, final String runtimePathLocation, final boolean attemptFixes)
			throws CoreException {
		final IInterpreterManager man = PydevPlugin.getPythonInterpreterManager(true);

		// Obviously if nothing is configured, then the REDHAWK settings surely are not configured
		if (!man.isConfigured()) {
			return false;
		}

		boolean configured = false;
		try {
			monitor.beginTask("Checking Python Environment", 300); // SUPPRESS CHECKSTYLE MagicNumber
			//			final SubMonitor submonitor = SubMonitor.convert(monitor);

			InterpreterInfo info;
			try {
				info = (InterpreterInfo) man.getDefaultInterpreterInfo(true);
			} catch (final MisconfigurationException e) {
				throw new CoreException(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Error with pydev configuration", e));
			}

			// Must skip the following steps in case of user selecting cancel from the Manual PyDev window
			if (info != null) {
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

				configured = info.libs.contains(ossiePath + "/lib/python" + info.getVersion() + "/site-packages") // Prefix-style
						|| info.libs.contains(ossiePath + "/lib/python"); // Home-style

				configured &= info.libs.contains(ossiePath + "/lib");
				if (configured == false) {
					RedhawkIdePyDevPlugin.getDefault().getLog().log(
						new Status(IStatus.WARNING, RedhawkIdePyDevPlugin.PLUGIN_ID, "PyDev configuration has incorrect $OSSIEHOME Python paths"));
				}

				if (attemptFixes) {
					if (info.getEnvVariables() == null) {
						info.setEnvVariables(new String[] { "IDE_REF=${IDE_REF}" });
					} else {
						info.updateEnv(new String[] { "IDE_REF=${IDE_REF}" });
					}
					man.setInfos(new InterpreterInfo[] { info }, null, null);
				} else {
					if (info.getEnvVariables() == null || Arrays.asList(info.getEnvVariables()).contains("IDE_REF=${IDE_REF}")) {
						RedhawkIdePyDevPlugin.getDefault().getLog().log(
							new Status(IStatus.WARNING, RedhawkIdePyDevPlugin.PLUGIN_ID, "PyDev configuration lacks IDE_REF environment variable"));
						configured &= false;
					}
				}
			}
		} finally {
			monitor.done();
		}
		return configured;
	}

	/**
	 * This configures the PyDev interpreter.
	 * 
	 * @param monitor the IProgressMonitor for status reporting
	 * @param manualConfigure true for user interaction
	 * @param runtimePathLocation optional location of OSSIEHOME
	 * @since 3.0
	 */
	public static void configurePydev(final IProgressMonitor monitor, final boolean manualConfigure, final String runtimePathLocation) throws CoreException {
		try {
			monitor.beginTask("Configuring Python Environment", 300); // SUPPRESS CHECKSTYLE MagicNumber
			final SubMonitor submonitor = SubMonitor.convert(monitor);
			final IInterpreterManager man = PydevPlugin.getPythonInterpreterManager(true);
			man.clearCaches();

			File script = PydevPlugin.getScriptWithinPySrc("interpreterInfo.py");
			final Tuple<String, String> outTup = new SimplePythonRunner().runAndGetOutputWithInterpreter("python", script.getAbsolutePath(), null, null, null,
				submonitor.newChild(50), null);

			final InterpreterInfo info = InterpreterInfo.fromString(outTup.o1, manualConfigure);

			// Must skip the following steps in case of user selecting cancel from the Manual PyDev window
			if (info != null) {

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
				if (new File(ossiePath + "/lib/python" + info.getVersion() + "/site-packages").isDirectory()) {
					info.libs.add(ossiePath + "/lib/python" + info.getVersion() + "/site-packages");
				}
				// Home-style
				if (new File(ossiePath + "/lib/python").isDirectory()) {
					info.libs.add(ossiePath + "/lib/python");
				}
				info.libs.add(ossiePath + "/lib");
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
				info.restoreCompiledLibs(submonitor.newChild(50)); // SUPPRESS CHECKSTYLE MagicNumber
				info.setName("Python");
				if (info.getEnvVariables() == null) {
					info.setEnvVariables(new String[] { "IDE_REF=${IDE_REF}" });
				} else {
					info.updateEnv(new String[] { "IDE_REF=${IDE_REF}" });
				}
				man.setInfos(new IInterpreterInfo[] { info }, null, null);
				PydevPlugin.setPythonInterpreterManager(man);
			}
		} finally {
			monitor.done();
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
}
