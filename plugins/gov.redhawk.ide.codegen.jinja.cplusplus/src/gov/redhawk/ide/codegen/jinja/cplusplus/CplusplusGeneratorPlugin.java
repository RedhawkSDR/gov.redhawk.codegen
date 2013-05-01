package gov.redhawk.ide.codegen.jinja.cplusplus;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class CplusplusGeneratorPlugin extends Plugin {

	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jinja.cplusplus";

	private static BundleContext context;

	static BundleContext getContext() {
		return CplusplusGeneratorPlugin.context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		CplusplusGeneratorPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		CplusplusGeneratorPlugin.context = null;
	}

}
