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
package gov.redhawk.ide.codegen.application.tests;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.application.CodegeneratorApplication;
import gov.redhawk.ide.sdr.LoadState;
import gov.redhawk.ide.sdr.ui.SdrUiPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.python.pydev.ast.interpreter_managers.InterpreterManagersAPI;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.MisconfigurationException;

@SuppressWarnings("restriction")
public class GeneratorTest extends TestCase {

	private static final String[] TEST_COMPONENTS = { "basic", "bulkio_ports", "event_props", "props", "sri" };

	@Override
	@Before
	public void setUp() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDescription = workspace.getDescription();
		workspaceDescription.setAutoBuilding(false);
		workspace.setDescription(workspaceDescription);

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				IIntroPart welcome = PlatformUI.getWorkbench().getIntroManager().getIntro();
				PlatformUI.getWorkbench().getIntroManager().closeIntro(welcome);

				IPerspectiveDescriptor pd = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("gov.redhawk.ide.ui.perspectives.sca");
				if (pd != null) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(pd);
				}

				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.console.ConsoleView");
				} catch (PartInitException e) {
					// PASS
				}
			}
		});
	}

	@Override
	@After
	public void tearDown() throws Exception {
	}

	public void test_generators() throws CoreException {
		createAndGenerate("test_cpp", "C++", "resource");
		createAndGenerate("test_py", "Python", "resource");
		createAndGenerate("test_java", "Java", "resource");

		createAndGenerate("dev_cpp", "C++", "device");
		createAndGenerate("dev_py", "Python", "device");
		createAndGenerate("ldev_cpp", "C++", "loadabledevice");
		createAndGenerate("ldev_py", "Python", "loadabledevice");
		createAndGenerate("edev_cpp", "C++", "executabledevice");
		createAndGenerate("edev_py", "Python", "executabledevice");

		for (String component : TEST_COMPONENTS) {
			generateCode(component, null);
			runComponentUnitTest(component);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// PASS
			}
		}
	}

	private void createAndGenerate(String name, String lang, String type) throws CoreException {
		IProject proj = createProject(name, lang, type);
		try {
			generateCode(name, null);
			runComponentUnitTest(name);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// PASS
			}
		} finally {
			proj.delete(true, true, new NullProgressMonitor());
		}
	}

	//	public void test_py_generators() {
	//		for (String component : TEST_COMPONENTS) {
	//			generateCode(component, "Python");
	//			runComponentUnitTest(component);
	//		}
	//	}
	//	
	//	public void test_java_generators() {
	//		for (String component : TEST_COMPONENTS) {
	//			generateCode(component, "Java");
	//			runComponentUnitTest(component);
	//		}
	//	}

	public IProject createProject(String componentName, String lang, String projectType) {
		URL url = null;
		try {
			url = FileLocator.toFileURL(FileLocator.find(Platform.getBundle("gov.redhawk.ide.codegen.tests"), new Path(""), null));
		} catch (IOException e) {
			Assert.fail("Failed to locate test bundle");
		}
		if (url == null) {
			return null;
		}
		File filePath = new File(url.getPath());

		while (!SdrUiPlugin.getDefault().getTargetSdrRoot().getState().equals(LoadState.LOADED)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Assert.fail("Interrupted waiting for SDRROOT to load");
			}
		}

		ArrayList<String> args = new ArrayList<String>();
		args.add("-create");
		args.add(filePath.getAbsolutePath() + "/sdr/dom/components/" + componentName);
		if (lang != null) {
			args.add("-Dlang=" + lang);
		}
		if (projectType != null) {
			args.add("-Dproject-type=" + projectType);
		}

		CodegeneratorApplication generator = new CodegeneratorApplication();
		try {
			generator.start(args.toArray(new String[args.size()]));
		} catch (Exception e) {
			// BEGIN DEBUG CODE
			e.printStackTrace();
			// END DEBUG CODE
			Assert.fail("Generator failed on " + componentName + " : " + e.getMessage());
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject proj = workspace.getRoot().getProject(componentName);
		Assert.assertTrue(proj.exists());
		return proj;
	}

	public void generateCode(String componentName, String lang) {
		URL url = null;
		try {
			url = FileLocator.toFileURL(FileLocator.find(Platform.getBundle("gov.redhawk.ide.codegen.tests"), new Path(""), null));
		} catch (IOException e) {
			Assert.fail("Failed to locate test bundle");
			return;
		}
		File filePath = new File(url.getPath());

		while (!SdrUiPlugin.getDefault().getTargetSdrRoot().getState().equals(LoadState.LOADED)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Assert.fail("Interrupted waiting for SDRROOT to load");
			}
		}

		ArrayList<String> args = new ArrayList<String>();
		args.add("-generate");
		args.add(filePath.getAbsolutePath() + "/sdr/dom/components/" + componentName);
		if (lang != null) {
			args.add("-Dlang=" + lang);
		}

		CodegeneratorApplication generator = new CodegeneratorApplication();
		try {
			generator.start(args.toArray(new String[args.size()]));
		} catch (Exception e) {
			// BEGIN DEBUG CODE
			e.printStackTrace();
			// END DEBUG CODE
			Assert.fail("Generator failed on " + componentName + " : " + e.getMessage());
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject proj = workspace.getRoot().getProject(componentName);
		Assert.assertTrue(proj.exists());

		try {
			proj.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		} catch (CoreException e) {
			Assert.fail("Failed to build project " + componentName + " : " + e.getMessage());
		}

		try {
			int maxSeverity = proj.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (maxSeverity == IMarker.SEVERITY_ERROR) {
				IMarker[] markers = proj.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

				StringBuffer msg = new StringBuffer("Generated code produced errors...");
				for (IMarker marker : markers) {
					if (marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO) == IMarker.SEVERITY_ERROR) {
						msg.append("  " + marker.getAttribute(IMarker.MESSAGE) + "\n");
						msg.append("    at " + marker.getAttribute(IMarker.LOCATION, "unknown") + ":" + marker.getAttribute(IMarker.LINE_NUMBER, 0) + "\n");
					}
				}

				Assert.fail(msg.toString());
			}
		} catch (CoreException e) {
			Assert.fail("Failed to validate project " + componentName + " : " + e.getMessage());
		}

	}

	public void runComponentUnitTest(String componentName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject proj = workspace.getRoot().getProject(componentName);
		Assert.assertTrue(proj.exists());

		ILaunchConfigurationWorkingCopy conf = null;
		try {
			conf = createUnitTestLaunchConfig(proj, componentName);
		} catch (CoreException e1) {
			Assert.fail("Error creating unittest launch configurationL: " + e1);
		}

		if (conf != null) {
			ILaunch launched = null;
			try {
				try {
					launched = conf.launch(ILaunchManager.RUN_MODE, null);
				} catch (CoreException e1) {
					Assert.fail("Error running unittest launch configurationL: " + e1);
				}
				if (launched != null) {
					while (!launched.isTerminated()) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Assert.fail("Interrupted while waiting for unittest to finish");
						}
					}

					for (IProcess process : launched.getProcesses()) {
						try {
							if (process.isTerminated() && process.getExitValue() != 0) {
								Assert.fail("Unittest failed with exit status " + process.getExitValue());
							}
						} catch (DebugException e) {
							Assert.fail("Error while checking unittest status");
						}
					}
				}
			} finally {
				if (launched != null) {
					if (launched.canTerminate()) {
						try {
							launched.terminate();
						} catch (DebugException e) {
							// PASS
						}
					}
				}
			}
		}
	}

	public static ILaunchConfigurationWorkingCopy createUnitTestLaunchConfig(final IProject proj, final String componentName) throws CoreException {
		IFolder testFolder = proj.getFolder("tests");
		if (!testFolder.exists()) {
			return null;
		}

		IFile testScript = testFolder.getFile("test_" + componentName + ".py");
		if (!testScript.exists()) {
			return null;
		}

		final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		final ILaunchConfigurationType type = manager.getLaunchConfigurationType(IExternalToolConstants.ID_PROGRAM_LAUNCH_CONFIGURATION_TYPE);

		final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, testScript.getName());

		final IPath sdrPath = SdrUiPlugin.getDefault().getTargetSdrPath();
		final IPath ossieHome = RedhawkIdeActivator.getDefault().getRuntimePath();
		final Map<String, String> environmentMap = new HashMap<String, String>();
		environmentMap.put("OSSIEHOME", ossieHome.toOSString());
		environmentMap.put("SDRROOT", sdrPath.toOSString());

		String ldPath = System.getenv("LD_LIBRARY_PATH");
		final String ossie32Lib = ossieHome.append("/lib").toOSString();
		if (ldPath != null) {
			ldPath = ossie32Lib + ":" + ldPath;
		} else {
			ldPath = ossie32Lib;
		}
		environmentMap.put("LD_LIBRARY_PATH", ldPath);

		final String ossie64Lib = ossieHome.append("/lib64").toOSString();
		if (ldPath != null) {
			ldPath = ossie64Lib + ":" + ldPath;
		} else {
			ldPath = ossie64Lib;
		}
		environmentMap.put("LD_LIBRARY_PATH", ldPath);

		String path = System.getenv("PATH");
		final String ossiePath = ossieHome.append("/bin").toOSString();
		if (path != null) {
			path = ossiePath + ":" + path;
		} else {
			path = ossiePath;
		}
		environmentMap.put("PATH", path);

		String pythonPath = System.getenv("PYTHONPATH");
		final String ossiePython = ossieHome.append("/lib/python").toOSString();
		if (pythonPath != null) {
			pythonPath = ossiePython + ":" + pythonPath;
		} else {
			pythonPath = ossiePython;
		}
		environmentMap.put("PYTHONPATH", pythonPath);

		String command = null;
		final IInterpreterManager pyman = InterpreterManagersAPI.getPythonInterpreterManager();
		IInterpreterInfo info = null;
		try {
			if (pyman != null) {
				info = pyman.getDefaultInterpreterInfo(true);
				command = info.getExecutableOrJar();
			}
		} catch (final MisconfigurationException e) {
			throw new CoreException(new Status(IStatus.ERROR, "", "Error getting PyDev configuration", e));
		}

		workingCopy.setAttribute(IExternalToolConstants.ATTR_LOCATION, command);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, testScript.getLocation().toOSString());
		workingCopy.setAttribute(IExternalToolConstants.ATTR_WORKING_DIRECTORY, testFolder.getLocation().toOSString());
		workingCopy.setAttribute(IExternalToolConstants.ATTR_SHOW_CONSOLE, true);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_BUILDER_ENABLED, false);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_BUILD_SCOPE, "${none}");
		workingCopy.setAttribute(IExternalToolConstants.ATTR_INCLUDE_REFERENCED_PROJECTS, (String) null);
		workingCopy.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, environmentMap);

		return workingCopy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(final String[] args) {
		TestRunner.run(GeneratorTest.class);
	}
}
