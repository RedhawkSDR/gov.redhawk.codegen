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
package gov.redhawk.ide.sandbox.console.py;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.statushandlers.StatusManager;
import org.python.pydev.ast.interpreter_managers.InterpreterManagersAPI;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.debug.newconsole.PydevConsoleFactory;
import org.python.pydev.debug.newconsole.PydevConsoleInterpreter;
import org.python.pydev.debug.newconsole.env.PydevIProcessFactory;
import org.python.pydev.debug.newconsole.env.PydevIProcessFactory.PydevConsoleLaunchInfo;

import gov.redhawk.ide.debug.ScaDebugPlugin;

/**
 * Used when opening a "REDHAWK Python Sandbox" in the "Console" view
 */
public class RHLocalConsoleFactory implements IConsoleFactory {

	@Override
	public void openConsole() {
		try {
			final PydevConsoleFactory factory = new PydevConsoleFactory();
			final PydevConsoleInterpreter interpreter = createInterpreter();
			final String additionalInitialComands = getSandboxConsoleInitialCommands();
			factory.createConsole(interpreter, additionalInitialComands);
		} catch (final Exception e) { // SUPPRESS CHECKSTYLE PyDev's throws are very broad
			StatusManager.getManager().handle(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, Messages.RHLocalConsoleFactory_PY_ERROR, e),
				StatusManager.LOG | StatusManager.SHOW);
		}
	}

	/**
	 * @return A new {@link PydevConsoleInterpreter} from the first Python interpreter in the settings.
	 * @throws CoreException If there isn't a Python interpreter configured in the settings
	 * @throws Exception The overly-broad throw from various PyDev code that we invoke
	 */
	static PydevConsoleInterpreter createInterpreter() throws CoreException, Exception {
		final IInterpreterManager interpreterManager = InterpreterManagersAPI.getPythonInterpreterManager();
		final IInterpreterInfo[] interpreters = interpreterManager.getInterpreterInfos();
		if (interpreters.length == 0) {
			throw new CoreException(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, "No available Python interpreter info"));
		}
		final IInterpreterInfo interpreterInfo = interpreters[0];
		final Collection<String> pythonPath = interpreterInfo.getPythonPath();
		final IPythonNature nature = null;
		final List<IPythonNature> natures = new ArrayList<IPythonNature>();

		final PydevIProcessFactory processFactory = new PydevIProcessFactory();
		PydevConsoleLaunchInfo info = processFactory.createLaunch(interpreterManager, interpreterInfo, pythonPath, nature, natures);
		return PydevConsoleFactory.createPydevInterpreter(info, processFactory.getNaturesUsed(), "UTF-8");
	}

	/**
	 * @return A string representing the initial lines of commands to provide to Python after starting
	 */
	static String getSandboxConsoleInitialCommands() {
		try {
			InputStream stream = RHLocalConsoleFactory.class.getResourceAsStream("sandboxConsoleInit.py");
			String commands = IOUtils.toString(stream);
			String sandboxIOR = ScaDebugPlugin.getInstance().getLocalSca(new NullProgressMonitor()).getIor();
			String idePath = Platform.getInstallLocation().getURL().getPath();
			return String.format(commands, sandboxIOR, idePath);
		} catch (CoreException | IOException e) {
			return "# Unable to initialize console. See the IDE's error log for details.\n";
		}
	}

}
