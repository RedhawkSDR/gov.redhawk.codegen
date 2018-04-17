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
package gov.redhawk.ide.pydev;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.python.pydev.ast.interpreter_managers.InterpreterManagersAPI;

import gov.redhawk.ide.pydev.util.AutoConfigPydevInterpreterUtil;

public class PyDevConfigureStartup implements IStartup {

	private class ConfigurePythonJob extends Job {
		private final boolean manualConfiguration;

		/**
		 * @param name
		 */
		public ConfigurePythonJob(boolean manualConfiguration) {
			super("Configuring Python Environment");
			this.manualConfiguration = manualConfiguration;
			setPriority(Job.LONG);
			setUser(true);
			setSystem(false);
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			try {
				AutoConfigPydevInterpreterUtil.configurePydev(monitor, manualConfiguration, "");
			} catch (final CoreException e) {
				final IStatus status = new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Failed to configure PyDev.", e);
				return status;
			}
			return Status.OK_STATUS;
		}
	}

	@Override
	public void earlyStartup() {
		// The application will be null when debugging, but is set in the built product
		final String app = System.getProperty("eclipse.application");
		final boolean runConfig = app == null || "org.eclipse.ui.ide.workbench".equals(app);
		if (!runConfig) {
			return;
		}

		// Don't show the PyDev pop-up
		// NOTE: The earlyStartup() method will run after some workbench activities such as restoring editors the user
		// left open when they last exited.
		System.setProperty("pydev.funding.hide", "true");

		// If PyDev isn't configured at all, then prompt the user
		if (InterpreterManagersAPI.getPythonInterpreterManager().isConfigured()) {
			try {
				boolean configuredCorrectly = AutoConfigPydevInterpreterUtil.isPydevConfigured(new NullProgressMonitor(), null);
				if (!configuredCorrectly) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							final String[] buttons = { "Ok", "Cancel" };
							final MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Configure PyDev", null,
								"PyDev appears to be mis-configured for REDHAWK, would you like it to be re-configured?", MessageDialog.QUESTION, buttons, 0);
							if (dialog.open() == 0) {
								new ConfigurePythonJob(false).schedule();
							}
						}

					});
				}
			} catch (CoreException e) {
				RedhawkIdePyDevPlugin.getDefault().getLog().log(
					new Status(e.getStatus().getSeverity(), RedhawkIdePyDevPlugin.PLUGIN_ID, "Failed to check PyDev configuration", e));
			}
		} else {
			new ConfigurePythonJob(false).schedule();
		}
	}
}
