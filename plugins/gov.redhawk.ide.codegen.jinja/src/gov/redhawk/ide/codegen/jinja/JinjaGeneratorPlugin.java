package gov.redhawk.ide.codegen.jinja;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class JinjaGeneratorPlugin extends Plugin {

	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jinja";

	private static JinjaGeneratorPlugin plugin;

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
	
}
