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
package gov.redhawk.ide.codegen.jet.cplusplus;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class CplusplusJetGeneratorPlugin extends Plugin {

	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jet.cplusplus";

	private static CplusplusJetGeneratorPlugin plugin;

	public CplusplusJetGeneratorPlugin() {
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		CplusplusJetGeneratorPlugin.plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		CplusplusJetGeneratorPlugin.plugin = null;
		super.stop(context);
	}

	public static CplusplusJetGeneratorPlugin getDefault() {
		return CplusplusJetGeneratorPlugin.plugin;
	}

	public static String getPluginId() {
		return CplusplusJetGeneratorPlugin.getDefault().getBundle().getSymbolicName();
	}

	public static final void logError(final String msg, final Throwable e) {
		CplusplusJetGeneratorPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, msg, e));
	}
}
