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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public final class ScaCore {
	private ScaCore() {

	}

	public static final IPath OSSIE_LIB_CONTAINER_PATH = new Path(JavaGeneratorPlugin.PLUGIN_ID + ".ossieLib"); //$NON-NLS-1$
	
	public static final IPath SOFT_PKG_REF_CONTAINER_PATH = new Path(JavaGeneratorPlugin.PLUGIN_ID + ".softPkgRef"); //$NON-NLS-1$

}
