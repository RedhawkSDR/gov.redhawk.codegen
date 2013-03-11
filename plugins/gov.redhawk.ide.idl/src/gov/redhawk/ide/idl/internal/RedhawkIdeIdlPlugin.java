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
package gov.redhawk.ide.idl.internal;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
/**
 * The activator class controls the plug-in life cycle
 */
public class RedhawkIdeIdlPlugin extends Plugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.idl";

	// The shared instance
	private static RedhawkIdeIdlPlugin plugin;

	/**
	 * The constructor
	 */
	public RedhawkIdeIdlPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		RedhawkIdeIdlPlugin.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		RedhawkIdeIdlPlugin.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RedhawkIdeIdlPlugin getDefault() {
		return RedhawkIdeIdlPlugin.plugin;
	}

	/**
	 * Logging functionality
	 * 
	 * @param msg
	 * @param e
	 */
	public static final void logError(final String msg, final Throwable e) {
		RedhawkIdeIdlPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, msg, e));
	}

	public Path getPythonImportIdlScriptPath() {
		URL url = FileLocator.find(getBundle(), new Path("python/importIDL.py"), null);
		try {
			url = FileLocator.toFileURL(url);
		} catch (IOException e) {
			return null;
		}
		return new Path(url.getFile());
	}
}
