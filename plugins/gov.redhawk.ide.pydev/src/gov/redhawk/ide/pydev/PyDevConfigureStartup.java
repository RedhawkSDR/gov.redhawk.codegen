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
package gov.redhawk.ide.pydev;

import gov.redhawk.ide.pydev.util.AutoConfigPydevInterpreterUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.python.pydev.plugin.PydevPlugin;

@Deprecated
public class PyDevConfigureStartup implements IStartup {

	private int result = 0;

	private class ConfigurePythonJob extends Job {
		final boolean manualConfiguration;
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
				final IStatus status = e.getStatus();
				return new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, status.getMessage(), status.getException());
			}
			return Status.OK_STATUS;
		}
	}

	public void earlyStartup() {
		final String app = System.getProperty("eclipse.application");
		final boolean runConfig = app == null || "org.eclipse.ui.ide.workbench".equals(app);
		if (!runConfig) return;
		
		// If PyDev isn't configured at all, then prompt the user
		if (PydevPlugin.getPythonInterpreterManager().isConfigured()) {
			try {
	            boolean configuredCorrectly = AutoConfigPydevInterpreterUtil.isPydevConfigured(new NullProgressMonitor(), null);
	            if (!configuredCorrectly) {
	            	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

	    				public void run() {
	    					final String[] buttons = {
	    					        "Ok", "Cancel"
	    					};
	    					final MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
	    					        "Configure PyDev",
	    					        null,
	    					        "PyDev appears to be mis-configured for REDHAWK, would you like it to be re-configured?",
	    					        MessageDialog.QUESTION,
	    					        buttons,
	    					        0);
	    					dialog.open();
	    					PyDevConfigureStartup.this.result = dialog.getReturnCode();

	    					if (PyDevConfigureStartup.this.result < 1) {
	    						new ConfigurePythonJob(false).schedule();
	    					}
	    				}

	    			});
	            }
            } catch (CoreException e) {
	            RedhawkIdePyDevPlugin.getDefault().getLog().log(e.getStatus());
            }
		} else {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					final String[] buttons = {
					        "Auto", "Manual", "Cancel"
					};
					final MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
					        "Configure PyDev",
					        null,
					        "PyDev has not been configured, would you like it to be auto-configured?",
					        MessageDialog.QUESTION,
					        buttons,
					        0);
					dialog.open();
					PyDevConfigureStartup.this.result = dialog.getReturnCode();

					if (PyDevConfigureStartup.this.result < 2) {
						new ConfigurePythonJob(PyDevConfigureStartup.this.result == 1).schedule();
					}
				}

			});
		}
	}
}
