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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.python.pydev.debug.newconsole.PydevConsole;
import org.python.pydev.debug.newconsole.PydevConsoleCommunication;
import org.python.pydev.debug.newconsole.PydevConsoleInterpreter;

/**
 * Used within {@link RHSandboxConsoleView}
 */
public class SandboxConsole extends PydevConsole {

	private ListenerList<ITerminateListener> terminateListeners;

	public interface ITerminateListener {
		public void consoleTerminated(SandboxConsole console);
	}

	protected SandboxConsole(PydevConsoleInterpreter interpreter, String additionalInitialCommands) {
		super(interpreter, additionalInitialCommands);
		this.terminateListeners = new ListenerList<ITerminateListener>();
	}

	public static SandboxConsole create() throws CoreException {
		try {
			PydevConsoleInterpreter interpreter = RHLocalConsoleFactory.createInterpreter();

			// Must "say hello" via the communication before back-and-forth communication works with the console
			PydevConsoleCommunication consoleCommunication = (PydevConsoleCommunication) interpreter.getConsoleCommunication();
			consoleCommunication.hello(new NullProgressMonitor());

			String additionalInitialCommands = RHLocalConsoleFactory.getSandboxConsoleInitialCommands();

			return new SandboxConsole(interpreter, additionalInitialCommands);
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
		for (final ITerminateListener tl : terminateListeners) {
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

}
