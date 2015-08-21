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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RunnableWithResult;

import gov.redhawk.ide.sdr.SdrPackage;
import gov.redhawk.ide.sdr.SdrRoot;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;
import gov.redhawk.model.sca.commands.ScaModelCommand;
import gov.redhawk.model.sca.util.ModelUtil;
import mil.jpeojtrs.sca.spd.Dependency;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaEcoreUtils;

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

	private Adapter sdrrootlistener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			switch (msg.getFeatureID(SdrPackage.class)) {
			case SdrPackage.SDR_ROOT__LOAD_STATUS:
				// The value is the changed load status which will go to null during loading
				// once loading is finished the status will change to reflect that of the SDRROOT.
				if (msg.getNewValue() != null) {
					Job refreshExternalSettingProviderJob = new Job("Refresh External Settings Providers") {

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							CoreModel.getDefault().getProjectDescriptionManager().updateExternalSettingsProviders(new String[] { ExternalSettingProvider.ID },
								monitor);
							return Status.OK_STATUS;
						}

					};
					refreshExternalSettingProviderJob.setSystem(true); // hide from progress monitor
					refreshExternalSettingProviderJob.setUser(false);
					refreshExternalSettingProviderJob.schedule(1000);
				}
				break;
			default:
				break;
			}
		}
	};

	public ExternalSettingProvider() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		SdrRoot sdrroot = SdrUiPlugin.getDefault().getTargetSdrRoot();

		// We add two listeners, one to the workspace and one to the SDRROOT.
		// The workspace listener will be ignored if it already exists but we check for the sdrroot eAdapter
		if (sdrroot != null && !sdrroot.eAdapters().contains(sdrrootlistener)) {
			sdrroot.eAdapters().add(sdrrootlistener);
		}

		workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public CExternalSetting[] getSettings(final IProject project, ICConfigurationDescription cfg) {
		// Create empty settings entry
		final List<ICSettingEntry> settingEntries = new ArrayList<ICSettingEntry>();
		final SoftPkg spd = ModelUtil.getSoftPkg(project);
		if (spd == null) {
			return new CExternalSetting[0];
		}
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

		// We need to check if the project contains any shared library dependencies for all the implementations
		for (Implementation impl : spd.getImplementation()) {
			EList<Dependency> deps = impl.getDependency();
			for (Dependency dep : deps) {
				// Construct the include path based on the dependency local file name
				String sharedLibraryPath = ScaEcoreUtils.getFeature(dep, SpdPackage.Literals.DEPENDENCY__SOFT_PKG_REF,
					SpdPackage.Literals.SOFT_PKG_REF__LOCAL_FILE, SpdPackage.Literals.LOCAL_FILE__NAME);
				if (sharedLibraryPath == null) {
					continue;
				}
				IPath includeDirPath = new Path("${SdrRoot}").append("dom").append(sharedLibraryPath).removeLastSegments(1).append("include");

				// This check prevents us from getting into a circular loop in the case where there
				// is a circular dependency within the shared library list.
				if (incPaths.contains(includeDirPath)) {
					continue;
				}

				incPaths.add(includeDirPath);

				// Retrieve the SoftPkg object for the shared library and recurse. May return null if the SPD file
				// isn't present in the SDRROOT.
				SoftPkg sharedLibrary = ScaEcoreUtils.getFeature(dep, SpdPackage.Literals.DEPENDENCY__SOFT_PKG_REF,
					SpdPackage.Literals.SOFT_PKG_REF__SOFT_PKG);
				populateIncludePaths(sharedLibrary, incPaths);
			}
		}
	}
}
