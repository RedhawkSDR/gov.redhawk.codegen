package gov.redhawk.ide.codegen.jinja.ui;

import gov.redhawk.ide.codegen.jinja.ui.preferences.JinjaUiPreferenceConstants;
import gov.redhawk.sca.util.PluginUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JinjaUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jinja.ui"; //$NON-NLS-1$

	// The shared instance
	private static JinjaUiPlugin plugin;
	
	/**
	 * The constructor
	 */
	public JinjaUiPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static JinjaUiPlugin getDefault() {
		return plugin;
	}

	public IPath getCodegenPath () {
		String codegenPath = getPreferenceStore().getString(JinjaUiPreferenceConstants.CODEGEN_PATH_PREFERENCE).trim();
		if (codegenPath.isEmpty()) {
			return null;
		}
		
		// Do any Eclipse variable substitution first.
		try {
			codegenPath = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(codegenPath, false);
		} catch (CoreException e) {
			this.getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, "Unexpected exception in string substitution", e));
		}
	
		// Try replacing environment variables for any remaining references.
		return new Path(PluginUtil.replaceEnvIn(codegenPath, null));
	}
}
