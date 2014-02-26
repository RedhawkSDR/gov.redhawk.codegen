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
package gov.redhawk.ide.codegen.cplusplus;

import gov.redhawk.ide.sdr.util.AbstractEnvMap;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaEcoreUtils;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;

/**
 * @since 6.2
 */
public class OctaveEnvMap extends AbstractEnvMap {

	@Override
	public void initEnv(Implementation impl, Map<String, String> envMap) throws CoreException {
		envMap.put("OCTAVEPATH", get_OCTAVE_Path(impl));
	}

	@Override
	protected boolean addToPath(Set<String> path, Implementation impl) throws CoreException {
		boolean retVal = super.addToPath(path, impl);

		if (retVal) {
			String relativeCodePath = ScaEcoreUtils.getFeature(impl, SpdPackage.Literals.IMPLEMENTATION__CODE, SpdPackage.Literals.CODE__LOCAL_FILE,
				SpdPackage.Literals.LOCAL_FILE__NAME);

			if (impl.eResource() != null) {
				String newPath = createPath(relativeCodePath, impl.eResource().getURI());
				if (newPath != null) {
					path.add(newPath);
				}
			}
		}
		return retVal;
	}

	public String createPath(String relativeCodePath, URI spdUri) throws CoreException {
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

	public String get_OCTAVE_Path(Implementation impl) throws CoreException {
		LinkedHashSet<String> octavePath = new LinkedHashSet<String>();
		octavePath.add("${env_var:OCTAVEPATH}");

		if (impl != null) {
			addToPath(octavePath, impl);
		}

		StringBuilder octavePathString = new StringBuilder();
		Iterator<String> i = octavePath.iterator();
		String s = i.next();
		octavePathString.append(s);
		// Insert in reverse order
		for (; i.hasNext();) {
			s = i.next();
			octavePathString.insert(0, s + File.pathSeparatorChar);
		}
		return octavePathString.toString();
	}

	@Override
	public boolean handles(Implementation impl) {
		// TODO This is not restrictive enough, need to find a better way to key off for octave
		String language = AbstractEnvMap.getImplProgrammingLanguage(impl);
		return "C++".equalsIgnoreCase(language);
	}

}
