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
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.model.sca.util.ModelUtil;
import gov.redhawk.sca.util.SubMonitor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class SoftpackageGenerator {

	private SoftpackageGenerator() {

	}

	/**
	 * @param string
	 * @param subMonitor
	 * @param pageImpl
	 * @param settings
	 * @throws CoreException
	 * 
	 */
	public static void generateFiles(ImplementationSettings implSettings, Implementation impl, SubMonitor monitor, String spdFileLocation) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Generating Softpkg Files...", 3);
		PrintStream out = System.out;
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final ArrayList<String> args = new ArrayList<String>();
		try {
			final String redhawkCodegen = JinjaGeneratorPlugin.getDefault().getCodegenPath().toFile().getPath();
			args.add(redhawkCodegen);
		} catch (Exception e) { // SUPPRESS CHECKSTYLE INLINE
			new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "Exception during code generation '", e);
		}

		// Force overwrite of existing files; we assume that the user has already signed off on this.
		args.add("-f");

		// Set base output directory to the project location
		args.add("-C");
		args.add(project.getLocation().toOSString());

		// Turn the settings into command-line flags
		// TODO: See seetingsToOptions in JinjaGenerator, maybe pull this out into it's own method?
		args.add("--impl");
		args.add(implSettings.getId());
		args.add("--impldir");
		args.add(implSettings.getOutputDir());
		args.add("--template");
		args.add(implSettings.getTemplate());
		for (final Property property : implSettings.getProperties()) {
			args.add("-B " + property.getId() + "=" + property.getValue());
		}
		args.add(spdFileLocation);

		final String[] command = args.toArray(new String[args.size()]);
		for (final String arg : args) {
			if (arg == null) {
				throw new CoreException(new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "Error found in code-generation command: \n" + args));
			}
			out.print(arg + " ");
		}
		out.println();
		subMonitor.worked(1);

		try {
			Process process = java.lang.Runtime.getRuntime().exec(command);
			process.waitFor();
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor.newChild(1));
		} catch (final IOException e) {
			new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "IOException during code generation '", e);
		} catch (CoreException e) {
			new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "CoreException during code generation '", e);
		} catch (InterruptedException e) {
			new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "InterruptedException during code generation '", e);
		} finally {
			subMonitor.done();
		}
	}

}
