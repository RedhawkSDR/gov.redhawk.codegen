/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.softpackage.codegen;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.spd.IdeSpdPlugin;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
import gov.redhawk.sca.util.SubMonitor;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SpdPackage;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

@SuppressWarnings("restriction")
public class SoftPackageProjectCreator extends ComponentProjectCreator {

	public static IFile createComponentFiles(final IProject project, final String spdName, final String spdId, final String authorName,
		final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating SCA softpackage files", 3);

		// Define the softpackage spd.xml base content, not including any implementations
		final GeneratorArgsSoftpkg args = new GeneratorArgsSoftpkg();
		args.setProjectName(project.getName());
		args.setAuthorName(authorName);
		args.setSoftPkgFile(spdName + SpdPackage.FILE_EXTENSION);
		args.setSoftPkgId(spdId);
		args.setSoftPkgName(spdName);

		final String spdContent = new SoftPackageSpdFileTemplate().generate(args);
		progress.worked(1);

		// Make sure spdFile does not already exist
		final IFile spdFile = project.getFile(spdName + SpdPackage.FILE_EXTENSION);
		if (spdFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, IdeSpdPlugin.PLUGIN_ID, "File " + spdFile.getName() + " already exists.", null));
		}

		// Crate the softpackage spd.xml
		try {
			spdFile.create(new ByteArrayInputStream(spdContent.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, IdeSpdPlugin.PLUGIN_ID, "Internal Error", e));
		}

		return spdFile;
	}

	public static void addImplementation(final IProject project, final String spdName, final Implementation impl, final ImplementationSettings settings,
		final IProgressMonitor monitor) throws CoreException {
		ProjectCreator.addImplementation(project, spdName, impl, settings, monitor);
	}
}
