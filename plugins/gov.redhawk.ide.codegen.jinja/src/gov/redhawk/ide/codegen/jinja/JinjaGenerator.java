package gov.redhawk.ide.codegen.jinja;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.model.sca.util.ModelUtil;

public class JinjaGenerator {

	protected List<String> settingsToArguments(ImplementationSettings implSettings, SoftPkg softpkg) {
		List<String> arguments = new ArrayList<String>();
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();
		final IPath workspaceRoot = project.getWorkspace().getRoot().getLocation();
		String spdFile = workspaceRoot.toOSString() + softpkg.eResource().getURI().toPlatformString(true);

		arguments.add("--impl");
		arguments.add(implSettings.getId());
		arguments.add("--impldir");
		arguments.add(implSettings.getOutputDir());
		arguments.add("--template");
		arguments.add(implSettings.getTemplate());
		for (Property property : implSettings.getProperties()) {
			arguments.add("-B"+property.getId()+"="+property.getValue());
		}
		arguments.add(spdFile);
		
		return arguments;
	}
	
	public IStatus generate(ImplementationSettings implSettings,
			Implementation impl, PrintStream out, PrintStream err,
			IProgressMonitor monitor, String[] generateFiles,
			boolean shouldGenerate, List<FileToCRCMap> crcMap) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();
		final SoftPkg softpkg = impl.getSoftPkg();
		
		ArrayList<String> arguments = new ArrayList<String>();
		arguments.add("redhawk-codegen");
		arguments.add("-C");
		arguments.add(project.getLocation().toOSString());
		arguments.addAll(settingsToArguments(implSettings, softpkg));
		String[] command = arguments.toArray(new String[arguments.size()]);
		
		try {
			java.lang.Process process = java.lang.Runtime.getRuntime().exec(command);
			if (out != null) {
				// Print the command to the console.
				for (String arg : command) {
				  out.print(arg + " ");
				}
				out.println();
				
				InputStreamReader instream = new InputStreamReader(process.getInputStream());
				BufferedReader reader = new BufferedReader(instream);
				String line;
				while ((line = reader.readLine()) != null) {
					out.println(line);
				}
			} else {
				process.waitFor();
			}
		} catch (final Exception e) {
			return new Status(IStatus.ERROR, JinjaGeneratorPlugin.PLUGIN_ID, "Generation failed");
		}
		return new Status(IStatus.OK, JinjaGeneratorPlugin.PLUGIN_ID, "Generation complete");
	}

	public HashMap<String, Boolean> getGeneratedFiles(ImplementationSettings implSettings, SoftPkg softpkg)	throws CoreException {
		HashMap<String, Boolean> fileList = new HashMap<String, Boolean>();
		
		ArrayList<String> arguments = new ArrayList<String>();
		arguments.add("redhawk-codegen");
		arguments.add("-l");
		arguments.addAll(settingsToArguments(implSettings, softpkg));
		String[] command = arguments.toArray(new String[arguments.size()]);
		
		try {
			java.lang.Process process = java.lang.Runtime.getRuntime().exec(command);
			InputStreamReader instream = new InputStreamReader(process.getInputStream());
			BufferedReader reader = new BufferedReader(instream);
			String line;
			while ((line = reader.readLine()) != null) {
				fileList.put(line, true);
			}
		} catch (final Exception e) {
			return null;
		}
		return fileList;
	}
	

	public IStatus validate() {
		return new Status(IStatus.OK, JinjaGeneratorPlugin.PLUGIN_ID, "Validation ok");
	}
}
