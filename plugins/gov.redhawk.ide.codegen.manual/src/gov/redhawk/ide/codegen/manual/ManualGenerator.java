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
package gov.redhawk.ide.codegen.manual;

import gov.redhawk.ide.codegen.AbstractCodeGenerator;
import gov.redhawk.ide.codegen.FileStatus;
import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.java.JavaGeneratorUtils;
import gov.redhawk.ide.codegen.python.utils.PythonGeneratorUtils;
import gov.redhawk.ide.cplusplus.utils.CppGeneratorUtils;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;

public class ManualGenerator extends AbstractCodeGenerator {
	// CHECKSTYLE:OFF

	@Override
	public IStatus cleanupSourceFolders(final IProject project, final IProgressMonitor monitor) {
		return new Status(IStatus.OK, ManualGeneratorPlugin.PLUGIN_ID, "No problems perform source folder cleanup");
	}

	/**
	 * The manual generator does *nothing* except setup the project natures correctly.   It doesn't even attempt
	 * to create source folders, setup OSSIE paths, or anything else.
	 * 
	 * It's there for those who like pain or when there is no other alternative yet avialable.
	 * @since 5.0
	 */
	@Override
	public IStatus generate(final ImplementationSettings implSettings, final Implementation impl, final PrintStream out, final PrintStream err, // SUPPRESS CHECKSTYLE NumParameters
	        final IProgressMonitor monitor, final String[] generateFiles, final boolean shouldGenerate, final List<FileToCRCMap> crcMap) {
		SubMonitor progress = SubMonitor.convert(monitor);
		
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();
		final String language = impl.getProgrammingLanguage().getName().toLowerCase().trim();
		
		if (language.equals("java")) {
			progress.setWorkRemaining(2);
			IJavaProject jproject = null;
			try {
				jproject = JavaGeneratorUtils.addJavaProjectNature(project, progress.newChild(1));
			} catch (CoreException e) {
				return e.getStatus();
			}
			try {
				JavaGeneratorUtils.addRedhawkJavaClassPaths(jproject,  progress.newChild(1));
			} catch (CoreException e) {
			    return e.getStatus();
			}
		} else if (language.equals("python")) {
			progress.setWorkRemaining(1);
			try {
				PythonGeneratorUtils.addPythonProjectNature(project, progress.newChild(1));
			} catch (CoreException e) {
				return e.getStatus();
			}
		} else if (language.equals("c++")) {
			progress.setWorkRemaining(2);
			MultiStatus retStatus = new MultiStatus(ManualGeneratorPlugin.PLUGIN_ID, IStatus.OK, "", null);
			CppGeneratorUtils.addCandCPPNatures(project, progress, retStatus);
			if (!retStatus.isOK()) {
				return retStatus;
			}
			CppGeneratorUtils.addManagedNature(project, progress, retStatus, "/", out, shouldGenerate, impl);
			if (!retStatus.isOK()) {
				return retStatus;
			}
		}
		return new Status(IStatus.OK, ManualGeneratorPlugin.PLUGIN_ID, "No problems generating code");
	}
	
	/**
	 * @since 6.0
	 */
	@Override
	public Set<FileStatus> getGeneratedFilesStatus(ImplementationSettings implSettings, SoftPkg softpkg) throws CoreException {
		return Collections.emptySet();
	}

	/**
	 * @since 6.0
	 */
	@Override
	public Map<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		return Collections.emptyMap();
	}

	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
		final ICodeGeneratorDescriptor codeGenDesc = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(settings.getGeneratorId());
		final Code retVal = SpdFactory.eINSTANCE.createCode();
		retVal.setEntryPoint("");
		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName("");
		retVal.setLocalFile(file);
		if (settings.getOutputDir() == null) {
			settings.setOutputDir("");
		}

		return retVal;
	}

	@Override
	public boolean shouldGenerate() {
		return false;
	}

	/**
	 * @since 5.0
	 */
	@Override
	public IFile getDefaultFile(Implementation impl, ImplementationSettings implSettings) {		
		return null;
	}

	/**
	 * @since 5.0
	 */
	@Override
	public IStatus validate() {
		return new Status(IStatus.OK, ManualGeneratorPlugin.PLUGIN_ID, "Validation ok");
	}

}
