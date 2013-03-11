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
package gov.redhawk.ide.idl.generator.newidl;

import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.idl.generator.internal.BuildShTemplate;
import gov.redhawk.ide.idl.generator.internal.ConfigureAcTemplate;
import gov.redhawk.ide.idl.generator.internal.IdlSpecTemplate;
import gov.redhawk.ide.idl.generator.internal.MakefileAmIdeTemplate;
import gov.redhawk.ide.idl.generator.internal.MakefileAmTemplate;
import gov.redhawk.ide.idl.generator.internal.ReconfLaunchTemplate;
import gov.redhawk.ide.idl.generator.internal.ReconfTemplate;
import gov.redhawk.ide.idl.generator.internal.SampleIdlTemplate;
import gov.redhawk.ide.idl.internal.RedhawkIdeIdlPlugin;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * @since 4.1
 */
public class IDLProjectCreator extends ProjectCreator {

	private IDLProjectCreator() {

	}

	/**
	 * Creates a new SCA idl project without any files. Should be invoked in the context of a
	 * {@link org.eclipse.ui.actions.WorkspaceModifyOperation WorkspaceModifyOperation}.
	 * 
	 * @param projectName The project name
	 * @param projectLocation the location on disk to create the project
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @return The newly created project
	 * @throws CoreException A problem occurs while creating the project
	 */
	public static IProject createEmptyProject(final String projectName, final URI projectLocation, final IProgressMonitor monitor) throws CoreException {
		final String[] additionalNatureIDs = new String[] {};
		return createEmptyProject(projectName, projectLocation, additionalNatureIDs, monitor);
	}

	/**
	 * Creates the basic files for an IDL in an empty SCA component project. Should be invoked in the context of a
	 * {@link org.eclipse.ui.actions.WorkspaceModifyOperation WorkspaceModifyOperation}.
	 * 
	 * @param project The project to generate files in
	 * @param monitor the progress monitor to use for reporting progress to the user. It is the caller's responsibility
	 *  to call done() on the given monitor. Accepts null, indicating that no progress should be
	 *  reported and that the operation cannot be canceled.
	 * @throws CoreException An error occurs while generating files
	 */
	public static void createIDLFiles(final IProject project, final String interfaceName, final String interfaceVersion, final List<String> idlFiles,
	        final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating SCA IDL project files", 2);

		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName(project.getName());
		args.setInterfaceName(interfaceName);
		args.setInterfaceVersion(interfaceVersion);
		args.setIdlFiles(idlFiles);

		// Generate file content from templates
		final String buildSh = new BuildShTemplate().generate(args);
		final String reconf = new ReconfTemplate().generate(args);
		final String reconfLaunch = new ReconfLaunchTemplate().generate(args);
		final String configureAc = new ConfigureAcTemplate().generate(args);
		final String makefileAm = new MakefileAmTemplate().generate(args);
		final String makefileAmIde = new MakefileAmIdeTemplate().generate(args);
		final String idlSpec = new IdlSpecTemplate().generate(args);
		progress.worked(1);

		// Check that files/folders don't exist already
		final IFile buildShFile = project.getFile("build.sh");
		if (buildShFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + buildShFile.getName() + " already exists.", null));
		}

		final IFile reconfFile = project.getFile("reconf");
		if (reconfFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + reconfFile.getName() + " already exists.", null));
		}

		final IFile reconfLaunchFile = project.getFile("reconf.launch");
		if (reconfFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + reconfLaunchFile.getName() + " already exists.", null));
		}

		final IFile configureAcFile = project.getFile("configure.ac");
		if (configureAcFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + configureAcFile.getName() + " already exists.", null));
		}

		final IFile makefileAmFile = project.getFile("Makefile.am");
		if (configureAcFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + makefileAmFile.getName() + " already exists.", null));
		}
		
		final IFile makefileAmIdeFile = project.getFile("Makefile.am.ide");
		if (makefileAmIdeFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + makefileAmIdeFile.getName() + " already exists.", null));
		}

		final IFile idlSpecFile = project.getFile(interfaceName.toLowerCase() + "Interfaces.spec");
		if (idlSpecFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "File " + idlSpecFile.getName() + " already exists.", null));
		}
		
		// Write files to disk
		try {
			buildShFile.create(new ByteArrayInputStream(buildSh.getBytes("UTF-8")), true, progress.newChild(1));
			makeExecutable(buildShFile);
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}

		try {
			reconfFile.create(new ByteArrayInputStream(reconf.getBytes("UTF-8")), true, progress.newChild(1));
			makeExecutable(reconfFile);
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}

		try {
			reconfLaunchFile.create(new ByteArrayInputStream(reconfLaunch.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}

		try {
			configureAcFile.create(new ByteArrayInputStream(configureAc.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}

		try {
			makefileAmFile.create(new ByteArrayInputStream(makefileAm.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}
		
		try {
			makefileAmIdeFile.create(new ByteArrayInputStream(makefileAmIde.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}
		
		try {
			idlSpecFile.create(new ByteArrayInputStream(idlSpec.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}
		
		// If the user didn't import any files...and some now
		if (idlFiles.size() == 0) {
			final String sampleIdl = new SampleIdlTemplate().generate(args);
			final IFile sampleIdlFile = project.getFile(interfaceName + ".idl");
			if (!sampleIdlFile.exists()) {
				try {
					sampleIdlFile.create(new ByteArrayInputStream(sampleIdl.getBytes("UTF-8")), true, progress.newChild(1));
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
				}
			}
		}
	}
	
	private static void makeExecutable(IFile file) {
		ResourceAttributes attr = file.getResourceAttributes();
		attr.setExecutable(true);
		try {
	        file.setResourceAttributes(attr);
        } catch (CoreException e) {
	        // PASS
        }
	}
}
