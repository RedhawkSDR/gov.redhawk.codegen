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

import gov.redhawk.ide.sdr.IdeSdrActivator;
import gov.redhawk.ide.sdr.SdrRoot;
import gov.redhawk.ide.sdr.TargetSdrRoot;
import gov.redhawk.ide.sdr.preferences.IdeSdrPreferenceConstants;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.jacorb.JacorbActivator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SandboxTestsActivator implements BundleActivator {

	public static final String PLUGIN_ID = "gov.redhawk.ide.sandbox.console.py.tests";

	private static BundleContext context;

	static BundleContext getContext() {
		return SandboxTestsActivator.context;
	}

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		SandboxTestsActivator.context = bundleContext;
		JacorbActivator.getDefault().init();
	}

	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		SandboxTestsActivator.context = null;
	}

	public static SdrRoot initSdrRoot() throws IOException, URISyntaxException, InterruptedException {
		final URL url = FileLocator.find(Platform.getBundle(SandboxTestsActivator.PLUGIN_ID), new Path("sdr"), null);
		final SdrRoot root = TargetSdrRoot.getSdrRoot();
		root.load(null);
		final URL fileURL = FileLocator.toFileURL(url);
		InstanceScope.INSTANCE.getNode(IdeSdrActivator.PLUGIN_ID).put(IdeSdrPreferenceConstants.SCA_LOCAL_SDR_PATH_PREFERENCE,
			new File(fileURL.toURI()).getAbsolutePath());
		root.reload(null);
		return root;
	}

}
