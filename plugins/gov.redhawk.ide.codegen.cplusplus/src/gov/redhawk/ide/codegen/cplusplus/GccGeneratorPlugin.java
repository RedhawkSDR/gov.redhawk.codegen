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
package gov.redhawk.ide.codegen.cplusplus;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GccGeneratorPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.cplusplus";

	// The shared instance
	private static GccGeneratorPlugin plugin;

	/**
	 * The constructor
	 */
	public GccGeneratorPlugin() {
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
		GccGeneratorPlugin.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		GccGeneratorPlugin.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static GccGeneratorPlugin getDefault() {
		return GccGeneratorPlugin.plugin;
	}

	/**
	 * Gets the plugin id.
	 * 
	 * @return the plugin id
	 */
	public static String getPluginId() {
		return GccGeneratorPlugin.getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Logging functionality
	 * 
	 * @param msg The message to log
	 * @param e The associated exception, if any
	 * @since 3.2
	 */
	public static final void logError(final String msg, final Throwable e) {
		GccGeneratorPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, GccGeneratorPlugin.PLUGIN_ID, msg, e));
	}
}
