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
package gov.redhawk.ide.cplusplus.utils.internal;

import gov.redhawk.model.sca.commands.ScaModelCommand;
import gov.redhawk.model.sca.util.ModelUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Dependency;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SoftPkgRef;
import mil.jpeojtrs.sca.spd.impl.DependencyImpl;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CExternalSetting;
import org.eclipse.cdt.core.settings.model.CIncludePathEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICPathEntry;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.extension.CExternalSettingProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RunnableWithResult;

/**
 * @since 1.2
 */
public class ExternalSettingProvider extends CExternalSettingProvider {
	public static final String ID = "gov.redhawk.ide.cplusplus.utils.CppExternalSettingProvider";

	// This listener will fire when any resource in the workspace changes
	// we check to see if that resource was an SPD file and call the updateExternalSettingsProviders if that occurs.
	private static IResourceChangeListener listener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta rootDelta = event.getDelta();
			for (IResourceDelta projectDelta : rootDelta.getAffectedChildren()) {
				for (IResourceDelta fileDelta : projectDelta.getAffectedChildren()) {
					if (fileDelta.getResource().getName().endsWith(".spd.xml")) {
						// Refresh the dynamic C++ include paths in case there has been change to a shared library in
						// the SDR.
						CoreModel.getDefault().getProjectDescriptionManager().updateExternalSettingsProviders(new String[] { ExternalSettingProvider.ID },
							new NullProgressMonitor());
					}
				}
			}
		}
	};

	public ExternalSettingProvider() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public CExternalSetting[] getSettings(final IProject project, ICConfigurationDescription cfg) {
		// Create empty settings entry
		final List<ICSettingEntry> settingEntries = new ArrayList<ICSettingEntry>();

		final SoftPkg spd = ModelUtil.getSoftPkg(project);
		Set<IPath> incPaths;
		try {
			incPaths = ScaModelCommand.runExclusive(spd, new RunnableWithResult.Impl<Set<IPath>>() {

				@Override
				public void run() {
					Set<IPath> incPaths = new HashSet<IPath>();
					populateIncludePaths(spd, incPaths);
					setResult(incPaths);
				}
			});
		} catch (InterruptedException e) {
			incPaths = Collections.emptySet();
		}

		for (IPath incPath : incPaths) {
			final ICPathEntry pathEntry = new CIncludePathEntry(incPath, ICSettingEntry.READONLY);
			settingEntries.add(pathEntry);
		}

		final ICSettingEntry[] settings = settingEntries.toArray(new ICSettingEntry[settingEntries.size()]);
		return new CExternalSetting[] { new CExternalSetting(null, null, null, settings) };
	}

	/**
	 * Recursive method to dive down into a softpackage, find any shared library dependencies for it and any children
	 * @param spd The Soft Package to begin the recurive dive
	 * @param incPaths The Set to store the include paths.
	 */
	private void populateIncludePaths(SoftPkg spd, Set<IPath> incPaths) {
		if (spd == null) {
			return;
		}
		boolean diveDeeper = true;

		// We need to check if the project contains any shared library dependencies for all the implementations
		for (Implementation impl : spd.getImplementation()) {
			EList<Dependency> deps = impl.getDependency();
			for (Dependency dep : deps) {
				if (dep instanceof DependencyImpl) {
					DependencyImpl depImpl = (DependencyImpl) dep;
					SoftPkgRef sharedLibraryRef = depImpl.getSoftPkgRef();

					if (sharedLibraryRef != null) {
						SoftPkg sharedLibSoftPackage = sharedLibraryRef.getSoftPkg();

						URI libUri = sharedLibSoftPackage.eResource().getURI();
						if ("sdrdom".equals(libUri.scheme())) {

							// Form the path which is $SdrRoot/dom/<sharedLib>/include however we do it in two steps
							// so we can strip off the spd.xml file name and append include
							// This is also so that we remain platform agnostic and do not use any slash characters
							IPath incdirPath = (new Path("${SdrRoot}")).append(new Path("dom")).append(libUri.path());
							incdirPath = (new Path(incdirPath.toFile().getParent())).append(new Path("include"));

							// This checks prevents us from getting into a circular loop in the case where there
							// is a circular dependency within the shared library list.
							if (incPaths.contains(incdirPath)) {
								diveDeeper = false;
							} else {
								incPaths.add(incdirPath);
							}
						}

						// It's possible the shared library soft package has shared libraries of its own
						if (diveDeeper) {
							populateIncludePaths(sharedLibSoftPackage, incPaths);
						}
					}
				}
			}
		}
	}
}
