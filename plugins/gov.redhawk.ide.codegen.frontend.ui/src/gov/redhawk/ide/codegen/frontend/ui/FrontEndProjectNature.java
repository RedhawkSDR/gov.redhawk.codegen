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
package gov.redhawk.ide.codegen.frontend.ui;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * @since 4.1
 */
public class FrontEndProjectNature implements IProjectNature {

	/** The ID of this project nature. */
	public static final String ID = "gov.redhawk.ide.codgen.natures.frontend";

	/** The project associated with this nature. */
	private IProject project;

	@Override
	public void configure() throws CoreException {
	}

	@Override
	public void deconfigure() throws CoreException {
	}

	@Override
	public IProject getProject() {
		return this.project;
	}

	@Override
	public void setProject(final IProject project) {
		this.project = project;
	}

	/**
	 * Utility method for adding a nature to a project.
	 * 
	 * @param project the project to add the nature
	 * @param monitor a progress monitor to indicate the duration of the operation, or <code>null</code> if progress
	 * reporting is not required.
	 */
	public static FrontEndProjectNature addNature(final IProject project, final Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			final IProjectDescription description = project.getDescription();
			final String[] prevNatures = description.getNatureIds();
			for (final String prevNature : prevNatures) {
				if (FrontEndProjectNature.ID.equals(prevNature)) {
					return (FrontEndProjectNature) project.getNature(FrontEndProjectNature.ID);
				}
			}
			final String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = FrontEndProjectNature.ID;
			description.setNatureIds(newNatures);
			
			project.setDescription(description, monitor);

			return (FrontEndProjectNature) project.getNature(FrontEndProjectNature.ID);
		} catch (final CoreException e) {
			FrontEndDeviceWizardPlugin.logError("Unable to add FrontEnd nature to project", e);
		} finally {
			monitor.done();
		}
		return null;
	}
}
