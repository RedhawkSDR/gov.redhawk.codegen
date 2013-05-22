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
package gov.redhawk.ide.idl.tests;

import gov.redhawk.ide.idl.generator.newidl.IDLProjectCreator;

import java.util.Arrays;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Test;

/**
 * A class to test {@link IdlProjectCreator}
 */
public class IdlProjectCreatorTest {
	/**
	 * Test creating a project
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateEmptyProject() throws CoreException {
		final IProject project = IDLProjectCreator.createEmptyProject("idlProjectTest", null, new NullProgressMonitor());
		Assert.assertNotNull(project);
		Assert.assertEquals("idlProjectTest", project.getName());
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		if (project.exists()) {
			project.delete(true, new NullProgressMonitor());
		}
	}
	
	@After
	public void afterTest() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("idlProjectTest");
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			if (project.exists()) {
				project.delete(true, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			// PASS
		}
		
	}

	/**
	 * Test creating the IDL files
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateIDLFiles() throws CoreException {
		final IProject project = IDLProjectCreator.createEmptyProject("idlProjectTest", null, new NullProgressMonitor());
		Assert.assertNotNull(project);
		Assert.assertEquals("idlProjectTest", project.getName());

		final IWorkspaceRoot workspaceRoot = (IWorkspaceRoot) project.getParent();
		IFile file = workspaceRoot.getFile(new Path("testFiles/file1.idl"));

		IDLProjectCreator.createIDLFiles(project, "testInterfaceName", "1.0.0", Arrays.asList(new String[] {
			file.getFullPath().toOSString()
		}), new NullProgressMonitor());

		file = project.getFile("build.sh");
		Assert.assertTrue(file.exists());

		file = project.getFile("reconf");
		Assert.assertTrue(file.exists());

		file = project.getFile("configure.ac");
		Assert.assertTrue(file.exists());

		file = project.getFile("Makefile.am");
		Assert.assertTrue(file.exists());

		file = project.getFile("testinterfacenameInterfaces.spec");
		IPath location = file.getLocation();
		Assert.assertTrue(file.exists());

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		if (project.exists()) {
			project.delete(true, new NullProgressMonitor());
		}
	}
}
