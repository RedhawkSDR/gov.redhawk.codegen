package gov.redhawk.ide.codegen.frontend;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class FrontEndPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.frontend"; //$NON-NLS-1$

	// The shared instance
	private static FrontEndPlugin plugin;
	
	/**
	 * The constructor
	 */
	public FrontEndPlugin() {
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
	public static FrontEndPlugin getDefault() {
		return plugin;
	}

	public static void logError(String msg, CoreException e) {
		FrontEndPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, FrontEndPlugin.PLUGIN_ID, msg, e));
	}

}
