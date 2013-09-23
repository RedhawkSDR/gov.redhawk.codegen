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

import gov.redhawk.ide.RedhawkIdeActivator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

/**
 * 
 */
public class OssieLibClasspathContainer implements IClasspathContainer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IClasspathEntry[] getClasspathEntries() {
		final IPath runtimePath = RedhawkIdeActivator.getDefault().getRuntimePath();
		if (runtimePath != null) {
			final File root = new File(runtimePath.toFile(), "lib");
			final List<IClasspathEntry> retVal = new ArrayList<IClasspathEntry>();
			if (root.isDirectory()) {
				for (final File file : root.listFiles()) {
					if (file.isFile() && file.getName().endsWith(".jar")) {
						retVal.add(JavaCore.newLibraryEntry(new Path(file.getAbsolutePath()), null, null));
					}
				}
			}
			return retVal.toArray(new IClasspathEntry[retVal.size()]);
		}
		return new IClasspathEntry[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return "REDHAWK System Library";
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
		return ScaCore.OSSIE_LIB_CONTAINER_PATH;
	}

}
