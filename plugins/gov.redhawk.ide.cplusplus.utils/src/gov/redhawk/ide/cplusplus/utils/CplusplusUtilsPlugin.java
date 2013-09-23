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
package gov.redhawk.ide.cplusplus.utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CplusplusUtilsPlugin implements BundleActivator {

	private static BundleContext context;

	public static final String PLUGIN_ID = "gov.redhawk.ide.cplusplus.utils";

	static BundleContext getContext() {
		return CplusplusUtilsPlugin.context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		CplusplusUtilsPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		CplusplusUtilsPlugin.context = null;
	}

}
