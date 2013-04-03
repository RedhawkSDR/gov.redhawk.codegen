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
package gov.redhawk.ide.sandbox.console.py.tests;

import gov.redhawk.ide.sdr.SdrRoot;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.ide.sdr.ui.preferences.SdrUiPreferenceConstants;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SandboxTestsActivator implements BundleActivator {

	public static final String PLUGIN_ID = "gov.redhawk.ide.sandbox.console.py.tests";

	private static BundleContext context;

	static BundleContext getContext() {
		return SandboxTestsActivator.context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(final BundleContext bundleContext) throws Exception {
		SandboxTestsActivator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(final BundleContext bundleContext) throws Exception {
		SandboxTestsActivator.context = null;
	}

	public static SdrRoot initSdrRoot() throws IOException, URISyntaxException,
			InterruptedException {
		final URL url = FileLocator.find(Platform
				.getBundle(SandboxTestsActivator.PLUGIN_ID), new Path("sdr"),
				null);
		final SdrRoot root = SdrUiPlugin.getDefault().getTargetSdrRoot();
		root.load(null);
		final URL fileURL = FileLocator.toFileURL(url);
		SdrUiPlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						SdrUiPreferenceConstants.SCA_LOCAL_SDR_PATH_PREFERENCE,
						new File(fileURL.toURI()).getAbsolutePath());
		root.reload(null);
		return root;
	}
	
}
