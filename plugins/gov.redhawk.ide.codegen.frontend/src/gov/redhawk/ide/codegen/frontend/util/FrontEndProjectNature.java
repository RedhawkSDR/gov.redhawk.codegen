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
package gov.redhawk.ide.codegen.frontend.util;

import gov.redhawk.ide.codegen.frontend.FrontEndPlugin;

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

//	/**
//	 * Utility method for adding a nature to a project.
//	 * 
//	 * @param project
//	 *            the project to add the nature
//	 * @param monitor
//	 *            a progress monitor to indicate the duration of the operation,
//	 *            or <code>null</code> if progress reporting is not required.
//	 *  
//	 */
//	public static void addBuilder(final IProject project, final Map<String, String> args) throws CoreException {
//		// This code came from the PDE Help
//		final IProjectDescription desc = project.getDescription();
//		final ICommand[] commands = desc.getBuildSpec();
//		boolean found = false;
//
//		for (int i = 0; i < commands.length; ++i) {
//			if (commands[i].getBuilderName().equals(IdlProjectBuilder.BUILDER_NAME)) {
//				found = true;
//				break;
//			}
//		}
//		if (!found) {
//			// add builder to project
//			final ICommand command = desc.newCommand();
//			command.setBuilderName(IdlProjectBuilder.BUILDER_NAME);
//			command.setArguments(args);
//			final ICommand[] newCommands = new ICommand[commands.length + 1];
//
//			// Add it before other builders.
//			System.arraycopy(commands, 0, newCommands, 1, commands.length);
//			newCommands[0] = command;
//			desc.setBuildSpec(newCommands);
//			project.setDescription(desc, null);
//		}
//	}
//
//	/**
//	 * Utility method to retrieve a builder property for a project.
//	 *  
//	 * @param project The project to search for properties
//	 * @param key The key that we shall use to look for a value
//	 * @return retVal Returns either the String value that was set for the property or an empty string
//	 * @throws CoreException
//	 */
//	public static String getBuilderProperty(final IProject project, final String key) throws CoreException {
//		if (project == null) {
//			return "";
//		}
//
//		if (key == null || key.isEmpty()) {
//			return "";
//		}
//
//		final IProjectDescription desc = project.getDescription();
//		String retVal = "";
//
//		for (final ICommand command : desc.getBuildSpec()) {
//			if (command.getBuilderName().equals(IdlProjectBuilder.BUILDER_NAME)) {
//				retVal = (String) command.getArguments().get(key);
//			}
//		}
//
//		return retVal;
//	}

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
			FrontEndPlugin.logError("Unable to add FrontEnd nature to project", e);
		} finally {
			monitor.done();
		}
		return null;
	}
}
