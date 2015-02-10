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
package gov.redhawk.ide.codegen.jinja;

import gov.redhawk.ide.codegen.jinja.preferences.JinjaPreferenceConstants;
import gov.redhawk.sca.util.PluginUtil;
import gov.redhawk.sca.util.ScopedPreferenceAccessor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.variables.VariablesPlugin;
import org.osgi.framework.BundleContext;

public class JinjaGeneratorPlugin extends Plugin {

	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jinja";

	private static JinjaGeneratorPlugin plugin;

	private final ScopedPreferenceAccessor preferenceAccessor = new ScopedPreferenceAccessor(InstanceScope.INSTANCE, JinjaGeneratorPlugin.PLUGIN_ID);

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		JinjaGeneratorPlugin.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		JinjaGeneratorPlugin.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static JinjaGeneratorPlugin getDefault() {
		return JinjaGeneratorPlugin.plugin;
	}

	public static final void logError(final String msg, final Throwable e) {
		JinjaGeneratorPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, msg, e));
	}

	static final void logWarn(final String msg, final Throwable e) {
		JinjaGeneratorPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, JinjaGeneratorPlugin.PLUGIN_ID, msg, e));
	}

	public IPath getCodegenPath() {
		String codegenPath = this.preferenceAccessor.getString(JinjaPreferenceConstants.CODEGEN_PATH_PREFERENCE).trim();
		if (codegenPath.isEmpty()) {
			return null;
		}

		// Do any Eclipse variable substitution first.
		try {
			codegenPath = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(codegenPath, false);
		} catch (final CoreException e) {
			this.getLog().log(new Status(IStatus.WARNING, JinjaGeneratorPlugin.PLUGIN_ID, "Unexpected exception in string substitution", e));
		}

		// Try replacing environment variables for any remaining references.
		return new Path(PluginUtil.replaceEnvIn(codegenPath, null));
	}

	public ScopedPreferenceAccessor getPreferenceAccessor() {
		return this.preferenceAccessor;
	}

}
