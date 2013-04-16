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
package gov.redhawk.ide.codegen.application;

import gov.redhawk.ide.codegen.CodegenFactory;
import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.IPropertyDescriptor;
import gov.redhawk.ide.codegen.IScaComponentCodegen;
import gov.redhawk.ide.codegen.ITemplateDesc;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.WaveDevSettings;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.dcd.generator.newdevice.DeviceProjectCreator;
import gov.redhawk.ide.pydev.util.AutoConfigPydevInterpreterUtil;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
import gov.redhawk.ide.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Compiler;
import mil.jpeojtrs.sca.spd.HumanLanguage;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.ProgrammingLanguage;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;
import mil.jpeojtrs.sca.util.DceUuidUtil;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionPreferences;
import org.eclipse.cdt.internal.core.LocalProjectScope;
import org.eclipse.cdt.internal.core.pdom.indexer.IndexerPreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.internal.ide.actions.OpenWorkspaceAction;
import org.osgi.service.prefs.BackingStoreException;

// CHECKSTYLE:OFF Ignore Special File
public class CodegeneratorApplication implements IApplication {
	private static final String PLUGIN_ID = "gov.redhawk.ide.codegen.application";

	private static final String PROP_VM = "eclipse.vm"; //$NON-NLS-1$

	private static final String PROP_VMARGS = "eclipse.vmargs"; //$NON-NLS-1$

	private static final String PROP_COMMANDS = "eclipse.commands"; //$NON-NLS-1$

	private static final String PROP_EXIT_CODE = "eclipse.exitcode"; //$NON-NLS-1$

	private static final String PROP_EXIT_DATA = "eclipse.exitdata"; //$NON-NLS-1$

	private static final String CMD_DATA = "-data"; //$NON-NLS-1$

	private static final String CMD_VMARGS = "-vmargs"; //$NON-NLS-1$

	private static final String NEW_LINE = "\n"; //$NON-NLS-1$

	public Object start(final IApplicationContext context) throws Exception {
		final String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		return start(args);
	}

	public void stop() {
		// TODO Auto-generated method stub
	}

	public Object start(final String[] args) throws Exception {
		boolean usage = (args.length == 0);

		String[] preserveFiles = new String[0];
		String create_project = null;
		String generate_project = null;
		String lang = null;
		String codegenId = null;
		String templateId = null;
		String project_type = null;

		for (int i = 0; i < args.length; i++) {
			final String arg = args[i];
			if (arg.equals("-create")) {
				create_project = args[++i];
			} else if (arg.equals("-generate")) {
				generate_project = args[++i];
			} else if (arg.startsWith("-Dlang=")) {
				lang = arg.substring(7).toLowerCase();
			} else if (arg.startsWith("-Dcodegen=")) {
				codegenId = arg.substring(10);
			} else if (arg.startsWith("-Dtemplate=")) {
				templateId = arg.substring(11);
			} else if (arg.startsWith("-Dproject-type=")) {
				project_type = arg.substring(15);
			} else if (arg.startsWith("-Dpreserve=")) {
				if (arg.length() > 11) {
					preserveFiles = arg.substring(11).split(",");
				} else {
					preserveFiles = new String[] {
						"*"
					};
				}
			} else if (arg.equals("-Dusage")) {
				usage = true;
			}
		}

		// Check various error conditions
		if ((create_project != null) && (generate_project != null)) {
			System.err.println("You cannot combine -create with -generate");
			usage = true;
		}
		if ((create_project == null) && (generate_project == null)) {
			System.err.println("You must provide either -create or -generate");
			usage = true;
		}

		// Show usage and exit if necessary
		if (usage) {
			System.out.println("Usage: eclipse -nosplash -application gov.redhawk.ide.codegen.tests.commandLineGenerator [OPTION]... -create PROJECT_PATH -Dlang=LANG");
			System.out.println("  or:  eclipse -nosplash -application gov.redhawk.ide.codegen.tests.commandLineGenerator [OPTION]... -generate PROJECT_PATH");
			System.out.println("");
			System.out.println("Common Options:");
			System.out.println("\t-Dlang=<lang> specifiy the programming language (C++, Java, or Python).  Required when creating a new project");
			System.out.println("\t-Dproject-type=<id> The type of project to create (resource, device, loadabledevice, executabledevice).  Default: resource.");
			System.out.println("\t-Dcodegen=<id> Optional, the id of the code generator to use.");
			System.out.println("\t-Dtemplate=<id> Optional, the id of the template to use.");
			System.out.println("");
			System.out.println("Less Common Options:");
			System.out.println("\t-Dpreserve=<file1,file2> Optional, the files to keep from being overwritten on generation.");
			System.out.println("\t\t\tIf no files are specified, all files are not overwritten, otherwise everything");
			System.out.println("\t\t\texcept for the comma delimited files will be overwritten on generation.");
			System.out.println("examples:");
			System.out.println("\trhgen -generate /path/to/project");
			System.out.println("\trhgen -create /path/to/project -Dlang=Python");

			// Wait for CDT to finish starting indexers(they get started even though indexing is disabled)
			CCorePlugin.getIndexManager().joinIndexer(IIndexManager.FOREVER, new NullProgressMonitor());

			return IApplication.EXIT_OK;
		}

		// This was originally put in to try to create temporary workspaces; however
		// that didn't work as intended because when the ResourcesPlugin starts it
		// will create a workspace in the default area before any of this code is called
		// so you still get a plethora of workspace folders...
		//
		// The code is kept here for two reasons:
		//  1. So we can remember how to use the equinox relaunch capability
		//  2. So that if we do need to overrideArgs...the code is already in-place
		final HashMap<String, String> overrideArgs = new HashMap<String, String>();
		// If we have modified any of the command line arguments we need to do a re-launch
		if (!overrideArgs.isEmpty()) {
			final String command_line = buildCommandLine(overrideArgs);
			if (command_line == null) {
				System.out.println("Failed to build command_line");
				return IApplication.EXIT_OK;
			}
			System.setProperty("eclipse.exitcode", Integer.toString(24));
			System.setProperty(IApplicationContext.EXIT_DATA_PROPERTY, command_line);
			return IApplication.EXIT_RELAUNCH;
		}

		final Location workspaceLocation = Platform.getInstanceLocation();
		if (workspaceLocation == null) {
			System.err.println("Error: no workspace setup");
			return IApplication.EXIT_OK;
		}
		System.out.println("CommandLine Generator starting...");
		//		System.out.println("  Workspace: " + workspaceLocation.getURL());

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();
		final IWorkspaceDescription description = workspace.getDescription();

		// Turn off auto building of the workspace
		description.setAutoBuilding(false);

		// Turn off CDT indexer
		disableGlobalCDTIndexer();

		// Attempt to see if PyDev is configured, required for C++ and Python generation
		if (!AutoConfigPydevInterpreterUtil.isPydevConfigured(new NullProgressMonitor(), "")) {
			// Configure PyDev with defaults
			System.out.println("Configuring PyDev");
			AutoConfigPydevInterpreterUtil.configurePydev(new NullProgressMonitor(), false, "");
			System.out.println("Configured PyDev");
		}

		// If we were requested to generate code
		if (create_project != null) {
			create_project(create_project, lang, codegenId, templateId, project_type, new NullProgressMonitor());
		} else if (generate_project != null) {
			generate_code(generate_project, lang, codegenId, templateId, preserveFiles, new NullProgressMonitor());
		}

		System.out.println("Goodbye");

		return IApplication.EXIT_OK;
	}

	private void create_project(final String project_path, final String lang, final String codegenId, final String templateId, String project_type,
	        final IProgressMonitor progressMonitor) throws CoreException {
		final SubMonitor monitor = SubMonitor.convert(progressMonitor, 2);
		final IPath projectPath = new Path(project_path);

		if (projectPath.toFile().exists()) {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Provided project path must not yet exist", null));
		}

		if (lang == null) {
			throw new CoreException(new Status(IStatus.ERROR,
			        CodegeneratorApplication.PLUGIN_ID,
			        "You must provide a programming language when creating a new component project",
			        null));
		}

		// Clean-up project_type
		if ((project_type == null) || ("resource".equals(project_type))) {
			create_component_project(projectPath, lang, codegenId, templateId, monitor.newChild(1));
		} else if ("device".equals(project_type)) {
			project_type = "device";
			create_device_project(projectPath, "Device", lang, codegenId, templateId, monitor.newChild(1));
		} else if ("loadabledevice".equals(project_type)) {
			project_type = "device";
			create_device_project(projectPath, "Loadable", lang, codegenId, templateId, monitor.newChild(1));
		} else if ("executabledevice".equals(project_type)) {
			project_type = "device";
			create_device_project(projectPath, "Executable", lang, codegenId, templateId, monitor.newChild(1));
		} else {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Unsupported project type", null));
		}
	}

	private void create_component_project(final IPath projectPath, final String lang, final String codegenId, final String templateId,
	        final IProgressMonitor progressMonitor) throws CoreException {
		final SubMonitor monitor = SubMonitor.convert(progressMonitor, 1);

		final String projectName = projectPath.lastSegment();
		final java.net.URI locationURI = projectPath.toFile().toURI();

		final ICodeGeneratorDescriptor code_gen = findCodeGen(lang, codegenId);

		final ITemplateDesc template = findCodeGenTemplate(templateId, code_gen);

		// Create the implementation
		final SoftPkg spd = SpdFactory.eINSTANCE.createSoftPkg();
		final Implementation impl = SpdFactory.eINSTANCE.createImplementation();
		spd.getImplementation().add(impl);
		final ImplementationSettings settings = CodegenFactory.eINSTANCE.createImplementationSettings();

		initializeSoftPkg(lang, projectName, code_gen, template, spd, impl, settings);

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(final IProgressMonitor progress_monitor) throws CoreException, InvocationTargetException, InterruptedException {
				final SubMonitor monitor = SubMonitor.convert(progress_monitor, 2);
				final IProject project = ComponentProjectCreator.createEmptyProject(projectName, locationURI, monitor.newChild(1));

				ComponentProjectCreator.createComponentFiles(project, spd.getName(), spd.getId(), "", monitor.newChild(1));

				ProjectCreator.addImplementation(project, spd.getName(), impl, settings, monitor.newChild(1));

				// Setup the IDL Path
				ResourceUtils.createIdlLibraryResource(project, monitor.newChild(1));
			}
		};
		try {
			operation.run(monitor.newChild(1));
		} catch (final InvocationTargetException e) {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Failure creating project", e));
		} catch (final InterruptedException e) {
			// pass
		}
	}

	private void create_device_project(final IPath projectPath, final String deviceType, final String lang, final String codegenId, final String templateId,
	        final IProgressMonitor progressMonitor) throws CoreException {
		final SubMonitor monitor = SubMonitor.convert(progressMonitor, 1);

		final String projectName = projectPath.lastSegment();
		final java.net.URI locationURI = projectPath.toFile().toURI();

		final ICodeGeneratorDescriptor code_gen = findCodeGen(lang, codegenId);

		final ITemplateDesc template = findCodeGenTemplate(templateId, code_gen);

		// Create the implementation
		final SoftPkg spd = SpdFactory.eINSTANCE.createSoftPkg();
		final Implementation impl = SpdFactory.eINSTANCE.createImplementation();
		spd.getImplementation().add(impl);
		final ImplementationSettings settings = CodegenFactory.eINSTANCE.createImplementationSettings();

		initializeSoftPkg(lang, projectName, code_gen, template, spd, impl, settings);

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(final IProgressMonitor progress_monitor) throws CoreException, InvocationTargetException, InterruptedException {
				final SubMonitor monitor = SubMonitor.convert(progress_monitor, 2);

				final IProject project = DeviceProjectCreator.createEmptyProject(projectName, locationURI, monitor.newChild(1));

				DeviceProjectCreator.createDeviceFiles(project, spd.getName(), spd.getId(), "", deviceType, false, monitor.newChild(1));

				ProjectCreator.addImplementation(project, spd.getName(), impl, settings, monitor.newChild(1));

				// Setup the IDL Path
				ResourceUtils.createIdlLibraryResource(project, monitor.newChild(1));
			}
		};
		try {
			operation.run(monitor.newChild(1));
		} catch (final InvocationTargetException e) {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Failure creating project", e));
		} catch (final InterruptedException e) {
			// pass
		}
	}

	private void initializeSoftPkg(final String lang, final String projectName, final ICodeGeneratorDescriptor code_gen, final ITemplateDesc template,
	        final SoftPkg spd, final Implementation impl, final ImplementationSettings settings) {
		spd.setId(DceUuidUtil.createDceUUID());
		spd.setName(projectName);

		final ProgrammingLanguage pl = SpdFactory.eINSTANCE.createProgrammingLanguage();
		final HumanLanguage hl = SpdFactory.eINSTANCE.createHumanLanguage();
		pl.setName(lang);
		impl.setProgrammingLanguage(pl);
		hl.setName(RedhawkCodegenActivator.ENGLISH);
		impl.setHumanLanguage(hl);
		if (code_gen.getCompiler() != null) {
			final Compiler c = SpdFactory.eINSTANCE.createCompiler();
			c.setName(code_gen.getCompiler());
			c.setVersion(code_gen.getCompilerVersion());
			impl.setCompiler(c);
		} else {
			impl.setCompiler(null);
		}
		if (code_gen.getRuntime() != null) {
			final mil.jpeojtrs.sca.spd.Runtime r = SpdFactory.eINSTANCE.createRuntime();
			r.setName(code_gen.getRuntime());
			r.setVersion(code_gen.getRuntimeVersion());
			impl.setRuntime(r);
		} else {
			impl.setRuntime(null);
		}

		settings.setGeneratorId(code_gen.getId());
		settings.setOutputDir(CodegenFileHelper.createDefaultOutputDir(spd, code_gen));
		settings.setTemplate(template.getId());
		impl.setId(settings.getOutputDir());
	}

	private ITemplateDesc findCodeGenTemplate(final String templateId, final ICodeGeneratorDescriptor code_gen) throws CoreException {
		ITemplateDesc template = null;
		final ITemplateDesc[] temps = RedhawkCodegenActivator.getCodeGeneratorTemplatesRegistry().findTemplatesByCodegen(code_gen.getId(), "resource");
		if (templateId == null) {
			for (final ITemplateDesc t : temps) {
				if (t.isSelectable() && !t.notDefaultableGenerator()) {
					template = t;
					break;
				}
			}
		} else {
			template = RedhawkCodegenActivator.getCodeGeneratorTemplatesRegistry().findTemplate(templateId);
		}

		if (template == null) {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Could not appropriate code generator template", null));
		}
		return template;
	}

	private ICodeGeneratorDescriptor findCodeGen(final String lang, final String codegenId) throws CoreException {
		ICodeGeneratorDescriptor code_gen = null;
		if (codegenId == null) {
			// TODO the code generator registry should eventually be fixed to be case-insensitive
			String kludge_lang = lang;
			if (lang.equalsIgnoreCase("c++")) {
				kludge_lang = "C++";
			} else if (lang.equalsIgnoreCase("python")) {
				kludge_lang = "Python";
			} else if (lang.equalsIgnoreCase("java")) {
				kludge_lang = "Java";
			}
			final ICodeGeneratorDescriptor[] code_gens = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegenByLanguage(kludge_lang, "resource");
			for (final ICodeGeneratorDescriptor cg : code_gens) {
				if (!cg.notDefaultableGenerator()) {
					code_gen = cg;
					break;
				}
			}
		} else {
			code_gen = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(codegenId);
		}

		if (code_gen == null) {
			throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Could not find appropriate code generator", null));
		}
		return code_gen;
	}

	// TODO - turn this into an OSGi command
	private void generate_code(final String project_path, final String lang, String codegenId, final String templateId, final String[] preserveFiles,
	        final NullProgressMonitor progressMonitor) throws CoreException {
		final SubMonitor monitor = SubMonitor.convert(progressMonitor, 2);

		final ResourceSet set = new ResourceSetImpl();

		final IPath projectPath = new Path(project_path);
		final IProject project = openProject(projectPath);
		final SoftPkg softPkg = getSoftPkg(project);

		if (softPkg == null) {
			throw new IllegalStateException("Could not load spd.xml for project");
		}

		// Create or open the existing settings
		final WaveDevSettings waveDev = getWaveDevSettings(set, softPkg, codegenId, templateId);
		if (waveDev == null) {
			throw new IllegalStateException("Could not load wavedev settings for project");
		}

		final EMap<String, ImplementationSettings> implSet = waveDev.getImplSettings();

		// Try generate each implementation, or just the specified language
		for (final Implementation impl : softPkg.getImplementation()) {
			final String currLang = impl.getProgrammingLanguage().getName();
			if ((lang != null) && !lang.equals(currLang.toLowerCase())) {
				continue;
			}

			// Prepare for generation
			final ImplementationSettings settings = implSet.get(impl.getId());
			final ArrayList<FileToCRCMap> crcMap = new ArrayList<FileToCRCMap>();

			System.out.println("\n\nGenerating " + currLang + " code for " + softPkg.getName());

			// Validate the settings name
			final String implName = CodegenFileHelper.safeGetImplementationName(impl, settings);
			if (!implName.equals(CodegenUtil.getValidName(implName))) {
				System.err.println("Invalid characters in implementation name for " + implName);
				continue;
			} else if (settings.getGeneratorId() != null) {
				// Find the desired code generator
				codegenId = settings.getGeneratorId();
				final ICodeGeneratorDescriptor codeGenDesc = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(codegenId);
				if (codeGenDesc == null) {
					System.err.println("The code generator(" + codegenId + ") for this implementation could not be found.");
					continue;
				}
				// Get the actual code generator
				final IScaComponentCodegen generator = codeGenDesc.getGenerator();
				// Get files to generate
				final Set<String> fileList = generator.getGeneratedFiles(settings, softPkg).keySet();
				// Remove files we don't want to delete
				if (preserveFiles.length != 0) {
					if ("*".equals(preserveFiles[0])) {
						fileList.clear();
					} else {
						for (final String f : preserveFiles) {
							if (fileList.contains(f)) {
								fileList.remove(f);
							}
						}
					}
				}
				// Generate the files
				final IStatus status = generator.generate(settings,
				        impl,
				        System.out,
				        System.err,
				        monitor.newChild(1),
				        fileList.toArray(new String[0]),
				        generator.shouldGenerate(),
				        crcMap);
				// Save the workspace
				final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

					@Override
					protected void execute(final IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
						final IStatus saveStatus = ResourcesPlugin.getWorkspace().save(true, monitor);
						// Check the save results, hopefully this worked
						if (!saveStatus.isOK()) {
							System.err.println("Generated files, but there was a problem saving the workspace: " + saveStatus.getMessage());
						}
					}
				};
				try {
					operation.run(monitor.newChild(1));
				} catch (final InvocationTargetException e) {
					throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Error saving resources", e));
				} catch (final InterruptedException e) {
					throw new CoreException(new Status(IStatus.ERROR, CodegeneratorApplication.PLUGIN_ID, "Error saving resources", e));
				}

				// Check the results
				if (!status.isOK()) {
					System.err.println("\nErrors occurred generating " + currLang + " code: " + status.getMessage());
					continue;
				} else {
					System.out.println("\nDone generating " + currLang + " code!");
				}
			} else {
				System.err.println("No generator specified for implementation: " + implName + ". No code generated.");
			}
		}
		
		project.build(IncrementalProjectBuilder.FULL_BUILD, monitor.newChild(1));

	}

	private WaveDevSettings getWaveDevSettings(final ResourceSet set, final SoftPkg softPkg, final String codegenId, final String templateId)
	        throws CoreException {
		WaveDevSettings retVal = null;
		// First, try to get the .wavedev from disk. This will throw an exception if it fails.
		try {
			retVal = CodegenUtil.getWaveDevSettings(set.getResource(CodegenUtil.getSettingsURI(softPkg), true));
		} catch (final Exception e) {
			System.out.println("Unable to find the settings file, inferring defaults");
		}

		// if we weren't able to find the wavedev, create it
		if (retVal == null) {
			retVal = CodegenFactory.eINSTANCE.createWaveDevSettings();
			// Recreate the basic settings for each implementation
			// This makes assumptions that the defaults are selected for everything
			for (final Implementation impl : softPkg.getImplementation()) {
				final ImplementationSettings settings = CodegenFactory.eINSTANCE.createImplementationSettings();
				final String lang = impl.getProgrammingLanguage().getName();

				// Find the code generator if specified, otherwise pick the first one returned by the registry
				ICodeGeneratorDescriptor codeGenDesc = null;
				if (codegenId != null) {
					codeGenDesc = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(codegenId);
				} else {
					final ICodeGeneratorDescriptor[] codeGens = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegenByLanguage(lang);
					if (codeGens.length > 0) {
						codeGenDesc = codeGens[0];
					}
				}

				// Proceed if we found one
				if (codeGenDesc != null) {
					final IScaComponentCodegen generator = codeGenDesc.getGenerator();

					// Assume that there is <name>[/].+<other> format for the entrypoint
					// Pick out <name> for both the output dir and settings name
					final String lf = impl.getCode().getEntryPoint();
					final String name = lf.substring(0, lf.indexOf('/'));

					// Set the generator, settings name and output directory
					settings.setGeneratorId(generator.getClass().getCanonicalName());
					settings.setName(name);
					settings.setOutputDir(lf.substring(0, lf.lastIndexOf('/')));

					// Find the template if specified, otherwise pick the first selectable and defaultable one returned by the registry
					ITemplateDesc templateDesc = null;
					if (templateId != null) {
						templateDesc = RedhawkCodegenActivator.getCodeGeneratorTemplatesRegistry().findTemplate(templateId);
					} else {
						final ITemplateDesc[] templates = RedhawkCodegenActivator.getCodeGeneratorTemplatesRegistry()
						        .findTemplatesByCodegen(settings.getGeneratorId());
						for (final ITemplateDesc itd : templates) {
							if (itd.isSelectable() && !itd.notDefaultableGenerator()) {
								templateDesc = itd;
								break;
							}
						}
					}

					// If we found the template, use it
					if (templateDesc != null) {
						// Set the properties to their default values
						for (final IPropertyDescriptor prop : templateDesc.getPropertyDescriptors()) {
							final Property p = CodegenFactory.eINSTANCE.createProperty();
							p.setId(prop.getKey());
							p.setValue(prop.getDefaultValue());
							settings.getProperties().add(p);
						}
						// Set the template
						settings.setTemplate(templateDesc.getId());
					} else {
						System.err.println("Unable to find a valid template! Desired: " + templateId);
					}
				} else {
					System.err.println("Unable to find a valid Code Generator! Desired: " + codegenId);
				}
				// Save the created settings
				retVal.getImplSettings().put(impl.getId(), settings);
			}
			// Create the URI to the .wavedev file
			final URI uri = URI.createPlatformResourceURI(softPkg.getName() + "/." + softPkg.getName() + ".wavedev", false);
			final Resource res = set.createResource(uri);

			// Add the WaveDevSettings to the resource and save to disk to persist the newly created WaveDevSettings
			res.getContents().add(retVal);
			try {
				res.save(null);
			} catch (final IOException e) {

			}
		}
		return retVal;
	}

	private IProject openProject(final IPath projectPath) throws CoreException {
		final ResourceSet set = new ResourceSetImpl();
		final IProgressMonitor progressMonitor = new NullProgressMonitor();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();
		final IWorkspaceDescription description = workspace.getDescription();
		final File projectPathFile = projectPath.toFile();
		final IPath projectDescriptionPath = projectPath.append(".project");

		if (!projectPathFile.isDirectory()) {
			throw new IllegalStateException("Provided project path must be a directory");
		}

		if (!projectDescriptionPath.toFile().exists()) {
			throw new IllegalStateException("Provided project path must include .project file");
		}

		final IProjectDescription projDesc = workspace.loadProjectDescription(projectDescriptionPath);
		final IProject project = root.getProject(projDesc.getName());

		if (project.exists()) {
			// If the project exists, make sure that it is the same project the user requested
			// ...this should only happen if the user forced a workspace with -data
			// that already contained a project with the same name but different path
			// from the one provided on the command line
			if (!project.getLocation().equals(projectPath.makeAbsolute())) {
				throw new IllegalStateException("Provided project path conflicts with existing project in workspace");
			}
		} else {
			// If the project doesn't exist in the workspace, create a linked project
			projDesc.setName(projDesc.getName());
			if (Platform.getLocation().isPrefixOf(projectPath)) {
				projDesc.setLocation(null);
			} else {
				projDesc.setLocation(projectPath);
			}

			final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				@Override
				protected void execute(final IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {

					final SubMonitor progressMonitor = SubMonitor.convert(monitor, 1);
					System.out.println("Loading project " + projDesc.getName() + " " + projDesc.getLocation());
					project.create(projDesc, progressMonitor.newChild(1));
				}
			};

			try {
				operation.run(new NullProgressMonitor());
			} catch (final InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (final InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		// Finally open the project
		Assert.isTrue(project.exists());

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(final IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
				project.open(monitor);
			}
		};

		try {
			operation.run(new NullProgressMonitor());
		} catch (final InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (final InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return project;
	}

	private SoftPkg getSoftPkg(final IProject project) {
		final ResourceSet set = new ResourceSetImpl();
		final IFile softPkgFile = project.getFile(project.getName() + ".spd.xml");
		final Resource resource = set.getResource(URI.createFileURI(softPkgFile.getLocation().toString()), true);
		return SoftPkg.Util.getSoftPkg(resource);
	}

	private void disableGlobalCDTIndexer() {
		// The Job code is borrowed from CCoreInternals ...
		final Job job = new Job("Disable CDT Indexer") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					final Properties props = new Properties();
					props.setProperty(IndexerPreferences.KEY_INDEXER_ID, IPDOMManager.ID_NO_INDEXER);
					IndexerPreferences.setProperties(null, IndexerPreferences.SCOPE_INSTANCE, props);
					InstanceScope.INSTANCE.getNode(CCorePlugin.PLUGIN_ID).flush();
				} catch (final BackingStoreException e) {
					CCorePlugin.log(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
			System.out.println("Exception waiting for indexer disable: " + e.getMessage());
		}

		// Join any auto builds currently running (should be none)
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (final Exception e1) {
			// PASS
		}
	}

	private void disableCDTProjectIndexer(final IProject project) throws CoreException {
		// Disable the C++ indexer for this project
		//     - This was pieced together mostly from IndexerBlock.java
		final Properties props = new Properties();
		props.setProperty(IndexerPreferences.KEY_INDEXER_ID, IPDOMManager.ID_NO_INDEXER);
		IndexerPreferences.setProperties(project, IndexerPreferences.SCOPE_PROJECT_PRIVATE, props);
		IndexerPreferences.setScope(project, IndexerPreferences.SCOPE_PROJECT_PRIVATE);
		final ICProjectDescriptionManager prjDescMgr = CCorePlugin.getDefault().getProjectDescriptionManager();
		final ICProjectDescription prefs = prjDescMgr.getProjectDescription(project, true);
		if (prefs != null) {
			prefs.setConfigurationRelations(ICProjectDescriptionPreferences.CONFIGS_INDEPENDENT);
			final ICConfigurationDescription config = prefs.getConfigurationByName("Debug");
			if (config != null) {
				prefs.setDefaultSettingConfiguration(config);
			}
			prjDescMgr.setProjectDescription(project, prefs);
		}

		// The Job code is borrowed from CCoreInternals ...
		final Job job = new Job("Disable Project Indexer Job") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					new LocalProjectScope(project).getNode(CCorePlugin.PLUGIN_ID).flush();
					InstanceScope.INSTANCE.getNode(CCorePlugin.PLUGIN_ID).flush();
				} catch (final BackingStoreException e) {
					CCorePlugin.log(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		// using workspace rule, see bug 240888
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();

		// Wait for the job to finish
		try {
			job.join();
		} catch (final InterruptedException e) {
			System.out.println("Exception waiting for indexer disable: " + e.getMessage());
		}
	}

	/**
	 * Create and return a string with command line options for eclipse.exe that
	 * will launch a new workbench that is the same as the currently running
	 * one, but using the argument directory as its workspace.
	 * 
	 * @param arguments to override or add
	 * @return a string of command line options or null on error
	 * 
	 * @see OpenWorkspaceAction for place where this code was cloned from
	 */
	private String buildCommandLine(final Map<String, String> overrides) {
		String property = System.getProperty(CodegeneratorApplication.PROP_VM);
		if (property == null) {
			System.out.println("Internal error: aborting because PROP_VM was not found");
			return null;
		}

		final StringBuffer result = new StringBuffer(512);
		result.append(property);
		result.append(CodegeneratorApplication.NEW_LINE);

		// append the vmargs and commands. Assume that these already end in \n
		final String vmargs = System.getProperty(CodegeneratorApplication.PROP_VMARGS);
		if (vmargs != null) {
			result.append(vmargs);
		}

		// append the rest of the args, replacing or adding -data as required
		property = System.getProperty(CodegeneratorApplication.PROP_COMMANDS);
		for (final Map.Entry<String, String> override : overrides.entrySet()) {
			if (property == null) {
				result.append(override.getKey());
				result.append(CodegeneratorApplication.NEW_LINE);
				if (override.getValue() != null) {
					result.append(override.getValue());
					result.append(CodegeneratorApplication.NEW_LINE);
				}
			} else {
				// find the index of the arg to replace its value
				int cmd_data_pos = property.lastIndexOf(override.getKey());
				if (cmd_data_pos != -1) {
					cmd_data_pos += override.getKey().length() + 1;
					result.append(property.substring(0, cmd_data_pos));
					if (override.getValue() != null) {
						result.append(override.getValue());
					}
					result.append(property.substring(property.indexOf('\n', cmd_data_pos)));
				} else {
					result.append(override.getKey());
					result.append(CodegeneratorApplication.NEW_LINE);
					if (override.getValue() != null) {
						result.append(override.getValue());
						result.append(CodegeneratorApplication.NEW_LINE);
					}
					result.append(property);
				}
			}
		}

		// put the vmargs back at the very end (the eclipse.commands property
		// already contains the -vm arg)
		if (vmargs != null) {
			result.append(CodegeneratorApplication.CMD_VMARGS);
			result.append(CodegeneratorApplication.NEW_LINE);
			result.append(vmargs);
		}

		return result.toString();
	}

}
