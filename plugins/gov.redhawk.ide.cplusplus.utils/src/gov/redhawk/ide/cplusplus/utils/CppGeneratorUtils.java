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
package gov.redhawk.ide.cplusplus.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.envvar.IContributedEnvironment;
import org.eclipse.cdt.core.language.settings.providers.ILanguageSettingsProvider;
import org.eclipse.cdt.core.language.settings.providers.ILanguageSettingsProvidersKeeper;
import org.eclipse.cdt.core.language.settings.providers.LanguageSettingsManager;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICLanguageSetting;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.WriteAccessException;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IBuilder;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IManagedProject;
import org.eclipse.cdt.managedbuilder.core.IProjectType;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.core.ManagedCProjectNature;
import org.eclipse.cdt.managedbuilder.envvar.IBuildEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.language.settings.providers.AbstractBuildCommandParser;
import org.eclipse.cdt.managedbuilder.language.settings.providers.AbstractBuildCommandParser.ResourceScope;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import gov.redhawk.ide.cplusplus.utils.internal.ExternalSettingProvider;
import mil.jpeojtrs.sca.spd.Implementation;

public final class CppGeneratorUtils {

	private static final String OCTAVE_PATH_REGEX = ".*include/octave-3.[4-9].[0-9]$";

	/**
	 * Standard include path for REDHAWK
	 */
	private static final String OSSIE_INCLUDE = "${OssieHome}/include";
	/**
	 * Standard include path for omniORB
	 */
	private static final String OMNI_ORB_INCLUDE = "/usr/include/omniORB4";
	/**
	 * Standard include path for omniORB thread
	 */
	private static final String OMNI_ORB_THREAD_INCLUDE = "/usr/include/omnithread";

	/**
	 * The ID of the {@link ILanguageSettingsProvider} that parses GCC build output to discover additional settings
	 * to contribute to the project.
	 */
	private static final String GCC_BUILD_PARSER_ID = "org.eclipse.cdt.managedbuilder.core.GCCBuildCommandParser";

	private CppGeneratorUtils() {
	}

	/**
	 * @param project
	 * @param monitor
	 * @param retStatus
	 * @return
	 * @since 1.0
	 */
	public static MultiStatus addCandCPPNatures(final IProject project, final SubMonitor monitor, final MultiStatus retStatus) {
		// Add C and CC natures to the project if they're not already there
		SubMonitor progress = SubMonitor.convert(monitor, "Checking project natures", 2);

		try {
			if (!project.hasNature(CProjectNature.C_NATURE_ID)) {
				CProjectNature.addCNature(project, progress.newChild(1));
			}
			progress.setWorkRemaining(1);
			if (!project.hasNature(CCProjectNature.CC_NATURE_ID)) {
				CCProjectNature.addCCNature(project, progress.newChild(1));
			}
		} catch (final CoreException e) {
			retStatus.add(new Status(e.getStatus().getSeverity(), CplusplusUtilsPlugin.PLUGIN_ID, "Problems adding C/C++ natures for project", e));
			return retStatus;
		}

		progress.done();
		return retStatus;
	}

	/**
	 * @param project
	 * @param monitor
	 * @param retStatus
	 * @param destinationDirectory
	 * @param out
	 * @param impl
	 * @return
	 * @since 1.1
	 */
	public static MultiStatus addManagedNature(final IProject project, final SubMonitor monitor, final MultiStatus retStatus, final String destinationDirectory,
		final PrintStream out, final Implementation impl) {
		SubMonitor progress = SubMonitor.convert(monitor, 2);

		// Based on whether or not the managed C project nature has been added, we know whether or not the project has
		// been previously configured for development
		boolean hasManagedNature;
		try {
			hasManagedNature = project.hasNature(ManagedCProjectNature.MNG_NATURE_ID);
		} catch (final CoreException e) {
			retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID,
				"Unable to deterine if the project has been configured with the managed C nature; cannot proceed with code generation", e));
			return retStatus;
		}

		ICProjectDescription projectDesc;
		if (hasManagedNature) {
			progress.subTask("Adding to existing C++ project nature");

			if (out != null) {
				out.println("Environment is configured correctly for C++ development...");
			}

			// Get the managed build info for the project
			final IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
			if (info == null) {
				retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, IResourceStatus.BUILD_FAILED,
					"C/C++ manged build information was not available", null));
				return retStatus;
			}

			// Ensure the configurations correctly target our implementation
			final IConfiguration[] configArray = info.getManagedProject().getConfigurations();
			for (final IConfiguration tempConfig : configArray) {
				final IStatus status = CppGeneratorUtils.configureBuilder(tempConfig);
				if (!status.isOK()) {
					retStatus.add(status);
					if (status.getSeverity() == IStatus.ERROR) {
						return retStatus;
					}
				}
				CppGeneratorUtils.configureSourceFolders(null, destinationDirectory, tempConfig);
			}

			// Get the existing project description
			projectDesc = CoreModel.getDefault().getProjectDescription(project);
		} else {
			progress.subTask("Configuring new C++ project nature");

			if (out != null) {
				out.println("C++ environment for " + project.getName() + " is not yet configured, attempting to configure it...");
			}

			// Create several of the CDT objects related to a C/C++ managed project
			final CoreModel coreModel = CoreModel.getDefault();
			try {
				projectDesc = coreModel.createProjectDescription(project, false);
			} catch (final CoreException e) {
				retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Unable to create a C++ project description", e));
				return retStatus;
			}
			ManagedBuildManager.createBuildInfo(project);
			final IProjectType projectType = ManagedBuildManager.getProjectType("cdt.managedbuild.target.gnu.exe");
			final IManagedProject managedProject;
			try {
				managedProject = ManagedBuildManager.createManagedProject(project, projectType);
			} catch (final BuildException e) {
				retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Unable to get GNU EXE project type (Eclipse CDT error)", e));
				return retStatus;
			}

			// Create new configurations based on our target project type's default configurations
			final IConfiguration[] defaultConfigs = projectType.getConfigurations();
			for (final IConfiguration defaultConfig : defaultConfigs) {
				final IConfiguration config = managedProject.createConfiguration(defaultConfig,
					ManagedBuildManager.calculateChildId(defaultConfig.getId(), null));

				if (impl != null) {
					config.setArtifactName((new Path(impl.getCode().getLocalFile().getName())).lastSegment());
				}

				final IStatus status = CppGeneratorUtils.configureBuilder(config);
				if (!status.isOK()) {
					retStatus.add(status);
					if (status.getSeverity() == IStatus.ERROR) {
						return retStatus;
					}
				}
				CppGeneratorUtils.configureSourceFolders(null, destinationDirectory, config);

				// Create a configuration description from the configuration
				try {
					projectDesc.createConfiguration(ManagedBuildManager.CFG_DATA_PROVIDER_ID, config.getConfigurationData());
				} catch (final WriteAccessException e) {
					retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Internal error - unable to create configuration description", e));
					return retStatus;
				} catch (final CoreException e) {
					retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Problem creating C++ configuratinon description", e));
					return retStatus;
				}
			}
		}
		progress.worked(1);

		// Perform setup of each configuration in the project
		for (final ICConfigurationDescription configDescription : projectDesc.getConfigurations()) {
			CppGeneratorUtils.addExternalSettingsProviders(configDescription);
			CppGeneratorUtils.addIncludePaths(configDescription);
			CppGeneratorUtils.addBuildEnvironVars(configDescription);
			CppGeneratorUtils.addErrorParsers(configDescription);
			CppGeneratorUtils.addLanguageSettingsProvider(configDescription);
			try {
				if (project.hasNature("gov.redhawk.ide.codgen.natures.octave")) {
					List<File> pathArray = locateOctaveIncludeDir();
					for (File path : pathArray) {
						addCustomIncludePaths(configDescription, path.toString());
					}
				}
			} catch (CoreException e) {
				retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Unable to access the project for the octave nature", e));
			} catch (IOException io) {
				retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Error while locating Octave Include folder", io));
			}
		}

		try {
			CoreModel.getDefault().setProjectDescription(project, projectDesc, false, progress.newChild(1));
			if (out != null) {
				out.println("C++ environment is now configured for development");
			}
		} catch (CoreException e) {
			retStatus.add(new Status(IStatus.ERROR, CplusplusUtilsPlugin.PLUGIN_ID, "Unable to set C++ project description", e));
			return retStatus;
		}

		progress.done();
		return retStatus;
	}

	private static List<File> locateOctaveIncludeDir() throws IOException {
		PathLocatorUtil fileLocator = new PathLocatorUtil();
		fileLocator.setBreakOnFirstResult(true);
		fileLocator.setExcludePathRegex(".*(bin|share|lib|lib64|games|etc|java).*");
		fileLocator.setMatchRegex(OCTAVE_PATH_REGEX);
		File startingDir = new File("/usr");
		List<File> files = fileLocator.getFileListings(startingDir);
		if (files == null || files.isEmpty()) {
			throw new IOException("Octave path not found. Searching under: " + startingDir);
		}
		return files;
	}

	/**
	 * Configures the build command and path for the specified {@link IConfiguration}. Turns managed build off.
	 * 
	 * @param config The {@link IConfiguration} to be modified
	 * @return The status of the operation
	 * @since 1.2
	 */
	public static IStatus configureBuilder(final IConfiguration config) {
		try {
			final IBuilder bld = config.getEditableBuilder();

			if (bld != null) {
				IPath buildPath = new Path("${ProjDirPath}");
				bld.setBuildPath(buildPath.toOSString());
				buildPath = buildPath.append("build.sh");
				bld.setCommand(buildPath.toOSString());
				bld.setManagedBuildOn(false);
			}
		} catch (final CoreException e) {
			return new Status(IStatus.WARNING, CplusplusUtilsPlugin.PLUGIN_ID, "Unable to configure C/C++ builder");
		}
		return new Status(IStatus.OK, CplusplusUtilsPlugin.PLUGIN_ID, "Builder configuration ok");
	}

	/**
	 * Some of the include paths that we add to a CDT project are intended only so CDT can resolve symbols when
	 * parsing. These paths aren't needed when compiling since autoconf / automake are already handling them. Call
	 * this method to see if a path is a CDT-only include path.
	 * 
	 * @param path True if the path is intended for CDT's use only, and shouldn't be added on to a compile command
	 * @since 1.0
	 */
	public static boolean isPathForCDTOnly(String path) {
		// TODO: How would we adapt if we stopped using one of these paths? Would we still check it here?
		return OSSIE_INCLUDE.equals(path) || OMNI_ORB_INCLUDE.equals(path) || OMNI_ORB_THREAD_INCLUDE.equals(path);
	}

	/**
	 * Adds include paths to the project so the CDT parser can resolve
	 * references to REDHAWK code, omniORB, etc.
	 * @param configDescription A project configuration description
	 */
	private static void addIncludePaths(final ICConfigurationDescription configDescription) {
		final ICLanguageSetting[] languageSettings = configDescription.getRootFolderDescription().getLanguageSettings();
		ICLanguageSetting lang = null;
		for (final ICLanguageSetting set : languageSettings) {
			if (set.getId().contains("cpp.compiler")) {
				lang = set;
				break;
			}
		}

		if (lang == null) {
			return;
		}

		final Set<ICLanguageSettingEntry> includePathSettings = new HashSet<ICLanguageSettingEntry>(lang.getSettingEntriesList(ICSettingEntry.INCLUDE_PATH));
		includePathSettings.add((ICLanguageSettingEntry) CDataUtil.createEntry(ICSettingEntry.INCLUDE_PATH, OSSIE_INCLUDE, OSSIE_INCLUDE, null, 0));
		includePathSettings.add((ICLanguageSettingEntry) CDataUtil.createEntry(ICSettingEntry.INCLUDE_PATH, OMNI_ORB_INCLUDE, OMNI_ORB_INCLUDE, null, 0));
		includePathSettings.add(
			(ICLanguageSettingEntry) CDataUtil.createEntry(ICSettingEntry.INCLUDE_PATH, OMNI_ORB_THREAD_INCLUDE, OMNI_ORB_THREAD_INCLUDE, null, 0));
		lang.setSettingEntries(ICSettingEntry.INCLUDE_PATH, new ArrayList<ICLanguageSettingEntry>(includePathSettings));
	}

	/**
	 * Adds the specified path to the configuration.
	 * @param configDescription A project configuration description
	 * @param path The path to be added
	 */
	private static void addCustomIncludePaths(final ICConfigurationDescription configDescription, String path) {
		final ICLanguageSetting[] languageSettings = configDescription.getRootFolderDescription().getLanguageSettings();
		ICLanguageSetting lang = null;
		for (final ICLanguageSetting set : languageSettings) {
			if (set.getId().contains("cpp.compiler")) {
				lang = set;
				break;
			}
		}

		if (lang == null) {
			return;
		}

		final List<ICLanguageSettingEntry> includePathSettings = lang.getSettingEntriesList(ICSettingEntry.INCLUDE_PATH);

		includePathSettings.add((ICLanguageSettingEntry) CDataUtil.createEntry(ICSettingEntry.INCLUDE_PATH, path, path, null, 0));
		lang.setSettingEntries(ICSettingEntry.INCLUDE_PATH, includePathSettings);
	}

	/**
	 * Add build environment variables to the configuration. These variables get set in the environment that the
	 * build command is invoked in.
	 * 
	 * @param configDescription A project configuration description
	 */
	private static void addBuildEnvironVars(final ICConfigurationDescription configDescription) {
		final IContributedEnvironment env = CCorePlugin.getDefault().getBuildEnvironmentManager().getContributedEnvironment();
		if (env.getVariable("OSSIEHOME", configDescription) == null) {
			env.addVariable("OSSIEHOME", "${OssieHome}", IBuildEnvironmentVariable.ENVVAR_REPLACE, null, configDescription);
		}
		// Turn on verbosity (automake silencing rules). Important for auto-discovery of include paths.
		if (env.getVariable("V", configDescription) == null) {
			env.addVariable("V", "1", IBuildEnvironmentVariable.ENVVAR_REPLACE, null, configDescription);
		}
	}

	/**
	 * Add a REDHAWK external settings provider to the CDT configuration (if not already present).
	 * <p/>
	 * The settings provider ensures include paths for shared libraries are added to C++ projects dynamically.
	 * @param configDescription A project configuration description
	 */
	private static void addExternalSettingsProviders(final ICConfigurationDescription configDescription) {
		String[] providers = configDescription.getExternalSettingsProviderIds();
		final Set<String> newProviders = new LinkedHashSet<String>(Arrays.asList(providers));
		newProviders.add(ExternalSettingProvider.ID);
		configDescription.setExternalSettingsProviderIds(newProviders.toArray(new String[newProviders.size()]));
	}

	/**
	 * Adds several error parsers to the CDT configuration (if not already present).
	 * @param configDescription A project configuration description
	 */
	private static void addErrorParsers(final ICConfigurationDescription configDescription) {
		// Order is important - we add the REDHAWK parser first which eliminates spurious errors from reconf
		String[] parserIDs = configDescription.getBuildSetting().getErrorParserIDs();
		Set<String> newParserIDs = new LinkedHashSet<String>();
		newParserIDs.add("gov.redhawk.ide.codegen.cpp.reconfParser");
		newParserIDs.add("org.eclipse.cdt.autotools.core.ErrorParser");
		newParserIDs.addAll(Arrays.asList(parserIDs));
		configDescription.getBuildSetting().setErrorParserIDs(newParserIDs.toArray(new String[newParserIDs.size()]));
	}

	/**
	 * Adds language settings providers to the configuration, such as the GCC build output parser.
	 * @param configDescription A project configuration description
	 */
	private static void addLanguageSettingsProvider(ICConfigurationDescription configDescription) {
		// Check if our provider is already being used
		ILanguageSettingsProvidersKeeper keeper = ((ILanguageSettingsProvidersKeeper) configDescription);
		List<ILanguageSettingsProvider> providers = new ArrayList<ILanguageSettingsProvider>(keeper.getLanguageSettingProviders());
		for (ILanguageSettingsProvider provider : providers) {
			if (GCC_BUILD_PARSER_ID.equals(provider.getId())) {
				return;
			}
		}

		// During project *creation*, we must ensure the default providers IDs are set, and we need to create
		// instances of them and add them. If the default provider IDs aren't set, CDT will ignore our providers and
		// replace them with defaults.
		if (keeper.getDefaultLanguageSettingsProvidersIds() == null) {
			String[] defaultProviderIDs = ManagedBuildManager.getConfigurationForDescription(configDescription).getDefaultLanguageSettingsProviderIds();
			keeper.setDefaultLanguageSettingsProvidersIds(defaultProviderIDs);
			providers = LanguageSettingsManager.createLanguageSettingsProviders(defaultProviderIDs);
			keeper.setLanguageSettingProviders(providers);
		}

		// Add our provider to the existing provider(s)
		AbstractBuildCommandParser newProvider = (AbstractBuildCommandParser) LanguageSettingsManager.getExtensionProviderCopy(GCC_BUILD_PARSER_ID, false);
		newProvider.setResourceScope(ResourceScope.FOLDER);
		providers.add(newProvider);
		keeper.setLanguageSettingProviders(providers);
	}

	/**
	 * If necessary, adds the implementation folder to the list of C/C++ code
	 * sources. Removes any erroneous or old source paths.
	 * 
	 * @param oldSource List of old source directories to remove from the source list
	 * @param destinationDirectory The implementation directory (relative to
	 * project directory)
	 * @param config The {@link IConfiguration} to be modified
	 * @since 1.0
	 */
	public static void configureSourceFolders(final List<String> oldSource, final String outputDir, final IConfiguration config) {
		final ArrayList<ICSourceEntry> entries = new ArrayList<ICSourceEntry>(Arrays.asList(config.getSourceEntries()));
		final ArrayList<ICSourceEntry> badEntries = new ArrayList<ICSourceEntry>();

		// Create an entry for our implementation directory
		final ICSourceEntry implementationEntry = (ICSourceEntry) CDataUtil.createEntry(ICSettingEntry.SOURCE_PATH, outputDir, null, null,
			ICSettingEntry.VALUE_WORKSPACE_PATH | ICSettingEntry.RESOLVED);

		boolean hasImplEntry = false;
		for (final ICSourceEntry entry : entries) {
			// Remove exclusionPatterns for easy comparison
			ICSourceEntry entryNoExlusion = (ICSourceEntry) CDataUtil.createEntry(entry.getKind(), entry.getName(), entry.getValue(), null,
				ICSettingEntry.VALUE_WORKSPACE_PATH | ICSettingEntry.RESOLVED);

			// Check if a source path for our implementation directory already exists
			if (entryNoExlusion.equals(implementationEntry)) {
				hasImplEntry = true;
				continue;
			}

			// Mark any bad source entries for removal
			if (entry.getLocation() == null || ((oldSource != null) && oldSource.contains(entry.getLocation().toString()))) {
				badEntries.add(entry);
			}
		}

		entries.removeAll(badEntries);

		// Add our implementation directory as a source path if it's not already there
		if (!hasImplEntry) {
			entries.add(implementationEntry);
		}

		// The root of the project gets added as a source path when the project is initially created - this removes it
		final ICSourceEntry rootSourceEntry = (ICSourceEntry) CDataUtil.createEntry(ICSettingEntry.SOURCE_PATH, "", null, null,
			ICSettingEntry.VALUE_WORKSPACE_PATH | ICSettingEntry.RESOLVED);
		entries.remove(rootSourceEntry);

		final ICSourceEntry[] e = new ICSourceEntry[entries.size()];
		entries.toArray(e);
		config.setSourceEntries(e);
	}
}
