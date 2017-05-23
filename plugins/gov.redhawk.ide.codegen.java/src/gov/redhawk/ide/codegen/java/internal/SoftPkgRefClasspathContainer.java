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
package gov.redhawk.ide.codegen.java.internal;

import gov.redhawk.ide.codegen.java.JavaGeneratorPlugin;

import java.io.File;
import java.util.ArrayList;

import mil.jpeojtrs.sca.spd.Dependency;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SoftPkgRef;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * 
 */
public class SoftPkgRefClasspathContainer implements IClasspathContainer {

	private IPath containerPath;
	private IJavaProject javaProject;
	private ArrayList<IClasspathEntry> paths;

	public SoftPkgRefClasspathContainer(final IPath containerPath, final IJavaProject javaProject) throws CoreException {
		this.containerPath = containerPath;
		this.javaProject = javaProject;
		IFile softPkgFile = this.javaProject.getProject().getFile(getBasename() + SpdPackage.FILE_EXTENSION);

		paths = new ArrayList<IClasspathEntry>();

		ResourceSet set = ScaResourceFactoryUtil.createResourceSet();
		Resource resource;
		try {
			resource = set.getResource(URI.createPlatformResourceURI(softPkgFile.getFullPath().toPortableString(), true), true);
			resource.load(set.getLoadOptions());
		} catch (Exception e) { // SUPPRESS CHECKSTYLE Logged Catch all exception
			throw new CoreException(new Status(Status.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Failed to load spd file: " + softPkgFile, e));
		}
		SoftPkg spd = SoftPkg.Util.getSoftPkg(resource);
		EList<Implementation> impls = spd.getImplementation();
		// TODO How do we determine which impl to use here?
		Implementation impl = impls.get(0);
		EList<Dependency> deps = impl.getDependency();
		for (Dependency dep : deps) {
			SoftPkgRef ref = dep.getSoftPkgRef();
			if (ref != null) {
				SoftPkg depSpd = ref.getSoftPkg();
				if (depSpd != null && spd.eResource() != null && depSpd.eResource().getURI() != null) {
					if (depSpd.eResource().getURI().isPlatformResource()) {
						IFile jarFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(depSpd.eResource().getURI().toPlatformString(true)));
						paths.add(JavaCore.newProjectEntry(jarFile.getProject().getFullPath()));
					} else {
						// TODO How do we determine which impl to use here?
						String jarFile = depSpd.getImplementation().get(0).getCode().getLocalFile().getName();
						URI uri = depSpd.eResource().getURI().trimSegments(1).appendSegments(jarFile.split("/"));
						IFileStore store = EFS.getStore(java.net.URI.create(uri.toString()));
						if (store.fetchInfo().exists()) {
							File local = store.toLocalFile(EFS.NONE, null);
							paths.add(JavaCore.newLibraryEntry(new Path(local.getAbsolutePath()), null, null));
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IClasspathEntry[] getClasspathEntries() {
		return paths.toArray(new IClasspathEntry[paths.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return "SoftPkg Refs";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPath getPath() {
		return containerPath;
	}

	/**
	 * Returns the 'basename' of the project (the last segment after a dot)
	 * @return the basename
	 */
	private String getBasename() {
		final String name = javaProject.getProject().getName();
		if (name.indexOf('.') == -1) {
			return name;
		}
		return name.substring(name.lastIndexOf('.') + 1, name.length());
	}

}
