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
package gov.redhawk.ide.codegen.java;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jdt.core.ClasspathVariableInitializer;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JavaGeneratorPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.java";

	/**
	 * @since 2.1
	 */
	public static final String REDHAWK_CONTAINER = "REDHAWK";

	// The shared instance
	private static JavaGeneratorPlugin plugin;

	private final IPreferenceChangeListener ossiehomePreferenceChangeListener = new IPreferenceChangeListener() {

		public void preferenceChange(final PreferenceChangeEvent event) {
			if (event.getKey().equals(RedhawkIdePreferenceConstants.RH_IDE_RUNTIME_PATH_PREFERENCE)) {
				final ClasspathVariableInitializer init = JavaCore.getClasspathVariableInitializer("OSSIEHOME");
				if (init != null) {
					init.initialize("OSSIEHOME");
				}
			}
		}

	};

	/**
	 * The constructor
	 */
	public JavaGeneratorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		JavaGeneratorPlugin.plugin = this;
		RedhawkIdeActivator.getDefault().getPreferenceAccessor().addPreferenceChangeListener(this.ossiehomePreferenceChangeListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		JavaGeneratorPlugin.plugin = null;
		RedhawkIdeActivator.getDefault().getPreferenceAccessor().removePreferenceChangeListener(this.ossiehomePreferenceChangeListener);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JavaGeneratorPlugin getDefault() {
		return JavaGeneratorPlugin.plugin;
	}

	/**
	 * Gets the plugin id.
	 * 
	 * @return the plugin id
	 */
	public static String getPluginId() {
		return JavaGeneratorPlugin.getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Logging functionality
	 * 
	 * @param msg
	 * @param e
	 * @since 2.3
	 */
	public static final void logError(final String msg, final Throwable e) {
		JavaGeneratorPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, msg, e));
	}
}
