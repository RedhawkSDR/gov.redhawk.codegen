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
package gov.redhawk.ide.codegen.manual;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ManualGeneratorPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.manual";

	/**
	 * @since 5.1
	 */
	// Manual Generator's ID
	public static final String MANUAL_GENERATOR_ID = "gov.redhawk.ide.codegen.manual.ManualGenerator";

	// The shared instance
	private static ManualGeneratorPlugin plugin;

	/**
	 * The constructor
	 */
	public ManualGeneratorPlugin() {
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
		ManualGeneratorPlugin.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		ManualGeneratorPlugin.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ManualGeneratorPlugin getDefault() {
		return ManualGeneratorPlugin.plugin;
	}

	public static String getPluginId() {
		return ManualGeneratorPlugin.getDefault().getBundle().getSymbolicName();
	}

}
