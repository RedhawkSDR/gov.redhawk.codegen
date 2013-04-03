/** 
 * REDHAWK HEADER
 *
 * Identification: $Revision: 9205 $
 */
package gov.redhawk.ide.codegen.jet.java;

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.debug.AbstractWorkspaceLaunchConfigurationFactory;
import gov.redhawk.ide.debug.ILaunchConfigurationFactory;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * @since 6.1
 * 
 */
public class JavaLaunchConfigurationFactory extends AbstractWorkspaceLaunchConfigurationFactory implements ILaunchConfigurationFactory {

	@Override
	public ILaunchConfigurationWorkingCopy createLaunchConfiguration(final String usageName, final String implId, final SoftPkg spd) throws CoreException {
		final ILaunchConfigurationWorkingCopy retVal = super.createLaunchConfiguration(usageName, implId, spd);
		final Implementation impl = spd.getImplementation(implId);
		final IFile resource = getResource(spd.eResource().getURI());
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, resource.getProject().getName());
		final ImplementationSettings settings = CodegenUtil.getImplementationSettings(impl);
		final String mainClass = JavaGeneratorProperties.getMainClass(impl, settings);
		retVal.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainClass);
		return retVal;
	}

	public void setProgramArguments(final String progArgs, final ILaunchConfigurationWorkingCopy config) throws CoreException {
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, progArgs);
	}

	public String getProgramArguments(final ILaunchConfiguration config) throws CoreException {
		return config.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
	}

}
