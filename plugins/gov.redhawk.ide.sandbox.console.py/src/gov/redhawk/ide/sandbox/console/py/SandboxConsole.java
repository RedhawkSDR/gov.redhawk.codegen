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
package gov.redhawk.ide.sandbox.console.py;

import gov.redhawk.ide.debug.ScaDebugPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.statushandlers.StatusManager;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.debug.newconsole.PydevConsole;
import org.python.pydev.debug.newconsole.PydevConsoleFactory;
import org.python.pydev.debug.newconsole.PydevConsoleInterpreter;
import org.python.pydev.debug.newconsole.env.PydevIProcessFactory;
import org.python.pydev.debug.newconsole.env.PydevIProcessFactory.PydevConsoleLaunchInfo;
import org.python.pydev.plugin.PydevPlugin;

import ExtendedCF.Sandbox;

public class SandboxConsole extends PydevConsole {
	private Sandbox sandbox;
	private ListenerList terminateListeners;

	public interface ITerminateListener {
		public void consoleTerminated(SandboxConsole console);
	}

	/**
	 * @since 3.0
	 */
	protected SandboxConsole(PydevConsoleInterpreter interpreter, Sandbox sandbox) {
		super(interpreter, NLS.bind(Messages.RHLocalConsoleFactory_PY_INIT, sandbox.toString()));
		this.sandbox = sandbox;
		this.terminateListeners = new ListenerList();
	}

	protected SandboxConsole(PydevConsoleInterpreter interpreter, String additionalInitialComands) {
		super(interpreter, additionalInitialComands);
	}

	public static SandboxConsole create() throws CoreException {
		try {
			return create(createInterpreter(), ScaDebugPlugin.getInstance().getSandbox(null));
		} catch (ServantNotActive e) {
			throw new CoreException(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, "Error creating sandbox console"));
		} catch (WrongPolicy e) {
			throw new CoreException(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, "Error creating sandbox console"));
		} catch (Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
			throw new CoreException(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, "Error creating sandbox console"));
		}
	}

	public void addTerminateListener(ITerminateListener listener) {
		terminateListeners.add(listener);
	}

	public void removeTerminateListner(ITerminateListener listener) {
		terminateListeners.remove(listener);
	}

	@Override
	public void terminate() {
		for (Object l : terminateListeners.getListeners()) {
			final ITerminateListener tl = (ITerminateListener) l;
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					tl.consoleTerminated(SandboxConsole.this);
				}

				@Override
				public void handleException(Throwable e) {
					IStatus status = new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, "Error with console terminate listener", e);
					RHLocalConsolePlugin.getDefault().getLog().log(status);
				}
			});
		}
		super.terminate();
	}

	/**
	 * @since 3.0
	 */
	public static SandboxConsole create(PydevConsoleInterpreter interpreter, Sandbox sandbox) {
		final SandboxConsole console = new SandboxConsole(interpreter, sandbox);
		return console;
	}

	private static PydevConsoleInterpreter createInterpreter() throws Exception {
		final PydevIProcessFactory processFactory = new PydevIProcessFactory();

		final IInterpreterManager interpreterManager = PydevPlugin.getPythonInterpreterManager();
		final IInterpreterInfo[] interpreters = interpreterManager.getInterpreterInfos();
		if (interpreters.length == 0) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, Messages.RHLocalConsoleFactory_PY_ERROR, null),
			        StatusManager.LOG | StatusManager.SHOW);
		}
		final IInterpreterInfo interpreterInfo = interpreters[0];
		interpreterInfo.updateEnv(new String[] { "IDE_REF=${IDE_REF}" });
		final Collection<String> pythonPath = interpreterInfo.getPythonPath();
		final IPythonNature nature = null;
		final List<IPythonNature> natures = new ArrayList<IPythonNature>();
		PydevConsoleLaunchInfo info = processFactory.createLaunch(interpreterManager, interpreterInfo, pythonPath, nature, natures);

		final PydevConsoleInterpreter interpreter = PydevConsoleFactory.createPydevInterpreter(info, processFactory.getNaturesUsed(), "UTF-8");

		return interpreter;
	}

}
