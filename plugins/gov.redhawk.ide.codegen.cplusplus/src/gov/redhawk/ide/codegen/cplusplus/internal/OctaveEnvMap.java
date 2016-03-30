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
package gov.redhawk.ide.codegen.cplusplus.internal;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;

import gov.redhawk.ide.sdr.util.AbstractEnvMap;
import mil.jpeojtrs.sca.spd.Implementation;

/**
 * @since 6.2
 */
public class OctaveEnvMap extends AbstractEnvMap {

	@Override
	public void initEnv(Implementation impl, Map<String, String> envMap) throws CoreException {
		envMap.put("OCTAVE_PATH", get_OCTAVE_Path(impl));
	}

	private String get_OCTAVE_Path(Implementation impl) throws CoreException {
		LinkedHashSet<String> octavePaths = new LinkedHashSet<String>();

		// Dependencies
		List<Implementation> depImpls = getDependencyImplementations(impl);
		for (Implementation depImpl : depImpls) {
			addToPath(octavePaths, depImpl);
		}

		// CF
		octavePaths.add("${OssieHome}/lib");

		// Pre-existing
		octavePaths.add("${env_var:OCTAVE_PATH}");

		StringBuilder octavePathString = new StringBuilder();
		for (String octavePath : octavePaths) {
			octavePathString.append(octavePath);
			octavePathString.append(File.pathSeparatorChar);
		}
		octavePathString.setLength(octavePathString.length() - 1);
		return octavePathString.toString();
	}

	@Override
	protected String createPath(String relativeCodePath, URI spdUri) throws CoreException {
		if (relativeCodePath == null || spdUri == null) {
			return null;
		}

		URI fullPathUri = spdUri.trimSegments(1).appendSegments(URI.createFileURI(relativeCodePath).segments());
		if (fullPathUri.isPlatformResource()) {
			fullPathUri = CommonPlugin.resolve(spdUri);
		}

		IFileStore store = EFS.getStore(java.net.URI.create(fullPathUri.toString()));
		IFileInfo info = store.fetchInfo();
		if (info.exists()) {
			if (info.isDirectory()) {
				return getAbsolutePath(fullPathUri);
			} else {
				return getAbsolutePath(fullPathUri.trimSegments(1));
			}
		}

		return null;
	}

}
