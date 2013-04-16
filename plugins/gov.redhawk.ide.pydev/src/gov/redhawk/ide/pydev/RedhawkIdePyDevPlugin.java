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

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.pydev.util.AutoConfigPydevInterpreterUtil;
import gov.redhawk.sca.util.ScopedPreferenceAccessor;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceNode;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.python.pydev.ui.pythonpathconf.PythonInterpreterPreferencesPage;

import com.python.pydev.analysis.AnalysisPlugin;
import com.python.pydev.analysis.AnalysisPreferenceInitializer;

/**
 * The activator class controls the plug-in life cycle
 */
public class RedhawkIdePyDevPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.pydev";

	// The shared instance
	private static RedhawkIdePyDevPlugin plugin;

	private static final int TOTAL_WORK = 3;

	private static final int CONFIGURE_PYDEV = 1;

	private static final int RETRIEVE_NODES = 1;

	private static final int CREATE_NEW_PAGE_INSTANCE = 1;

	/**
	 * Case where preference has not been set
	 */
	private static final int IMPORT_PREFERENCE_UNSET = 3;

	private static final String PYDEV_PLUGIN_PREFS_ID = "org.python.pydev.prefs";

	private static final String REDHAWK_PREFERENCES_ID = "gov.redhawk.ui.preferences";

	private static final String REDHAWK_IDE_TARGET_PLATFORM_ID = "gov.redhawk.ide.ui.targetplatform";

	private static final String PYTHON_INTERPRETER_PREFERENCE_PAGE_ID = "org.python.pydev.ui.pythonpathconf.interpreterPreferencesPagePython";

	private final WorkspaceJob pythonJob = new WorkspaceJob("Updating Python Environment") {

		@Override
		public boolean belongsTo(final Object family) {
			if (family.equals(ResourcesPlugin.FAMILY_MANUAL_REFRESH)) {
				return true;
			}

			return super.belongsTo(family);
		}

		@Override
		public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
			try {
				monitor.beginTask("Updating Python Environment", RedhawkIdePyDevPlugin.TOTAL_WORK);

				// Update python path
				AutoConfigPydevInterpreterUtil.configurePydev(monitor, false,
				        getRedhawkIdePreferenceStore().getString(RedhawkIdePreferenceConstants.RH_IDE_RUNTIME_PATH_PREFERENCE));

				monitor.worked(RedhawkIdePyDevPlugin.CONFIGURE_PYDEV);

				// Retrieve both the current node and the Python Preference node
				final PreferenceManager prefManager = PlatformUI.getWorkbench().getPreferenceManager();
				final WorkbenchPreferenceNode node = (WorkbenchPreferenceNode) prefManager.find(RedhawkIdePyDevPlugin.PYDEV_PLUGIN_PREFS_ID + IPath.SEPARATOR
				        + RedhawkIdePyDevPlugin.PYTHON_INTERPRETER_PREFERENCE_PAGE_ID);
				final WorkbenchPreferenceNode otherNode = (WorkbenchPreferenceNode) prefManager.find(RedhawkIdePyDevPlugin.REDHAWK_PREFERENCES_ID
				        + IPath.SEPARATOR + RedhawkIdePyDevPlugin.REDHAWK_IDE_TARGET_PLATFORM_ID);

				monitor.worked(RedhawkIdePyDevPlugin.RETRIEVE_NODES);

				if (node != null) {
					// Use the UI thread to get access to a Composite to fully instantiate the PythonInterpreterPreference page
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

						public void run() {
							// Have the Python Preference node dispose its contents so we can update them later
							if (node.getPage() != null) {
								node.disposeResources();
							}

							// Have Python Preference recreate a new page
							final PythonInterpreterPreferencesPage page = new PythonInterpreterPreferencesPage();
							page.init(getWorkbench());

							if (otherNode.getPage() != null) {
								if (otherNode.getPage().getControl().getParent() != null) {
									page.createControl(otherNode.getPage().getControl().getParent());

									// Associate our created page with the Python Preference node
									node.setPage(page);
								}
							}
						}
					});
					monitor.worked(RedhawkIdePyDevPlugin.CREATE_NEW_PAGE_INSTANCE);
				}
				monitor.done();
			} catch (final CoreException e) {
				final IStatus status = e.getStatus();
				return new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, status.getMessage(), status.getException());
			}
			return Status.OK_STATUS;
		}
	};

	private final ScopedPreferenceAccessor preferenceAccessor = new ScopedPreferenceAccessor(InstanceScope.INSTANCE, RedhawkIdeActivator.PLUGIN_ID);

	private final IPreferenceChangeListener ossiehomePreferenceChangeListener = new IPreferenceChangeListener() {

		public void preferenceChange(final PreferenceChangeEvent event) {

			if (event.getKey().equals(RedhawkIdePreferenceConstants.RH_IDE_RUNTIME_PATH_PREFERENCE)) {
				RedhawkIdePyDevPlugin.this.pythonJob.setName("Updating Runtime Path");
				RedhawkIdePyDevPlugin.this.pythonJob.setPriority(Job.LONG);
				RedhawkIdePyDevPlugin.this.pythonJob.setUser(true);
				RedhawkIdePyDevPlugin.this.pythonJob.schedule();
			}
		}
	};

	private ScopedPreferenceStore idePreferenceStore;

	/**
	 * The constructor
	 */
	public RedhawkIdePyDevPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		RedhawkIdePyDevPlugin.plugin = this;
		RedhawkIdePyDevPlugin.getDefault().getPreferenceAccessor().addPreferenceChangeListener(this.ossiehomePreferenceChangeListener);

		// Check to see if the Undefined Variable From Input preference has been set yet
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(AnalysisPlugin.getPluginID());
		final int preference = prefs.getInt(AnalysisPreferenceInitializer.SEVERITY_UNDEFINED_IMPORT_VARIABLE, RedhawkIdePyDevPlugin.IMPORT_PREFERENCE_UNSET);

		switch (preference) {
		case IMPORT_PREFERENCE_UNSET:
			// If it hasn't, then set it to be Ignored
			prefs.putInt(AnalysisPreferenceInitializer.SEVERITY_UNDEFINED_IMPORT_VARIABLE, 0);

			try {
				prefs.flush();
			} catch (final BackingStoreException e) {
				RedhawkIdePyDevPlugin.getDefault().getLog()
				        .log(new Status(IStatus.ERROR, RedhawkIdePyDevPlugin.PLUGIN_ID, "Unable to save preferences for Pydev Code Analysis."));
			}
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		if (this.idePreferenceStore != null) {
			this.idePreferenceStore = null;
		}
		RedhawkIdePyDevPlugin.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RedhawkIdePyDevPlugin getDefault() {
		return RedhawkIdePyDevPlugin.plugin;
	}

	public ScopedPreferenceAccessor getPreferenceAccessor() {
		return this.preferenceAccessor;
	}

	public IPreferenceStore getRedhawkIdePreferenceStore() {
		if (this.idePreferenceStore == null) {
			this.idePreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, RedhawkIdeActivator.PLUGIN_ID);
		}
		return this.idePreferenceStore;
	}
}
