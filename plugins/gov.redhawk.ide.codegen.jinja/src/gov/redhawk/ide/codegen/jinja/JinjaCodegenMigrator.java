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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.util.NamedThreadFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;

/**
 * 
 */
public class JinjaCodegenMigrator implements ICodegenTemplateMigrator {
	
	private static final ExecutorService EXECUTOR_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory(JinjaCodegenMigrator.class.getName()));

	@Override
	public void migrate(IProgressMonitor monitor, ITemplateDesc template, Implementation impl, ImplementationSettings implSettings) throws CoreException {
		IFile resource = ModelUtil.getResource(implSettings);
		String fullPath = resource.getLocation().toOSString();
		String ossieHome = System.getenv("OSSIEHOME");
		ProcessBuilder builder = new ProcessBuilder(ossieHome + "/bin/update_project", fullPath);
		try {
			final Process process = builder.start();
			Future< ? > future = EXECUTOR_POOL.submit(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							process.exitValue();
							break;
						} catch (IllegalThreadStateException e) {
							// PASS							
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// PASS
						}
					}
				}
				
			});
			try {
				future.get(30, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Interupted Exception:" + builder.command(), e));
			} catch (ExecutionException e) {
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Execution Exception:" + builder.command(), e));
			} catch (TimeoutException e) {
				process.destroy();
				throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Timeout waiting on:" + builder.command(), e));
			}
			
			IProject project = resource.getProject();
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			
			// Refresh Contents of models
			implSettings.eResource().load(null);
			impl.eResource().load(null);
		} catch (IOException e) {
			throw new CoreException(new Status(Status.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Failed to invoke Jinja Generator Migration Script.", e));
		}
	}

}
