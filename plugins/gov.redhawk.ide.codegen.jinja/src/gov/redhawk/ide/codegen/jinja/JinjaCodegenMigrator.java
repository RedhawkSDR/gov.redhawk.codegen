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
package gov.redhawk.ide.codegen.jinja;

import gov.redhawk.ide.codegen.ICodegenTemplateMigrator;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.IOException;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * 
 */
public class JinjaCodegenMigrator implements ICodegenTemplateMigrator {

	@Override
	public void migrate(IProgressMonitor monitor, ITemplateDesc template, Implementation impl, ImplementationSettings implSettings) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Jinja update_project...", 2);
		IFile resource = ModelUtil.getResource(implSettings);
		String fullPath = resource.getLocation().toOSString();
		String ossieHome = System.getenv("OSSIEHOME");
		ProcessBuilder builder = new ProcessBuilder(ossieHome + "/bin/update_project", fullPath);
		try {
			final Process process = builder.start();
			if (subMonitor.isCanceled()) {
				process.destroy();
				throw new OperationCanceledException();
			}
			subMonitor.newChild(1).beginTask("Calling " + builder.command(), IProgressMonitor.UNKNOWN);
			Integer exitValue = null;
			while (!subMonitor.isCanceled()) {
				try {
					exitValue = process.exitValue();
					break;
				} catch (IllegalThreadStateException e) {
					// PASS							
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// PASS
				}
			}
			if (exitValue != null && exitValue != 0) {
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "update_project returned with error code " + exitValue, null));
			}

			if (subMonitor.isCanceled()) {
				process.destroy();
				throw new OperationCanceledException();
			}

			IProject project = resource.getProject();
			project.refreshLocal(IResource.DEPTH_INFINITE, subMonitor.newChild(1));
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Failed to invoke Jinja Generator Migration Script.", e));
		} finally {
			subMonitor.done();
		}
	}

}
