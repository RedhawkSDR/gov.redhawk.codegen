/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.softpackage.codegen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import gov.redhawk.ide.codegen.CodegenUtil;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.IScaComponentCodegen;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.WaveDevSettings;
import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.ide.spd.IdeSpdPlugin;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
import gov.redhawk.sca.util.SubMonitor;

@SuppressWarnings("restriction")
public class SoftPackageProjectCreator extends ComponentProjectCreator {

	public static IFile createComponentFiles(final IProject project, final String spdName, final String spdId, final String authorName,
		final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating SCA softpackage files", 3);

		// Define the softpackage spd.xml base content, not including any implementations
		final GeneratorArgsSoftpkg args = new GeneratorArgsSoftpkg();
		args.setProjectName(project.getName());
		args.setAuthorName(authorName);
		args.setSoftPkgFile(spdName + SpdPackage.FILE_EXTENSION);
		args.setSoftPkgId(spdId);
		args.setSoftPkgName(spdName);

		final String spdContent = new SoftPackageSpdFileTemplate().generate(args);
		progress.worked(1);

		// Make sure spdFile does not already exist
		final IFile spdFile = project.getFile(spdName + SpdPackage.FILE_EXTENSION);
		if (spdFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, IdeSpdPlugin.PLUGIN_ID, "File " + spdFile.getName() + " already exists.", null));
		}

		// Crate the softpackage spd.xml
		try {
			spdFile.create(new ByteArrayInputStream(spdContent.getBytes("UTF-8")), true, progress.newChild(1));
		} catch (final UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, IdeSpdPlugin.PLUGIN_ID, "Internal Error", e));
		}

		return spdFile;
	}

	public static void addImplementation(final IProject project, final String spdName, final Implementation impl, final ImplementationSettings settings,
		final IProgressMonitor monitor) throws CoreException {

		String spdFileName = (spdName == null) ? project.getName() + SpdPackage.FILE_EXTENSION : spdName + SpdPackage.FILE_EXTENSION;
		final SubMonitor progress = SubMonitor.convert(monitor, 2);

		final IFile spdFile = project.getFile(spdFileName);
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
		final IFile waveDevFile = project.getFile(CodegenUtil.getWaveDevSettingsURI(spdUri).lastSegment());
		final URI waveDevUri = URI.createPlatformResourceURI(waveDevFile.getFullPath().toString(), true);

		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();

		Assert.isTrue(waveDevFile.exists());
		Assert.isTrue(spdFile.exists());
		// Fill in the implementation's code settings (i.e. localfile, entrypoint, etc) using the code generator
		// specified in the implementation settings
		final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
		final WaveDevSettings wavedevSettings = (WaveDevSettings) resourceSet.getResource(waveDevUri, true).getEObject("/");
		Assert.isNotNull(eSpd);
		Assert.isNotNull(wavedevSettings);

		// Add the implementation to the SPD
		if (!eSpd.getImplementation().contains(impl)) {
			eSpd.getImplementation().add(impl);
		}
		Assert.isTrue(impl.eContainer().equals(eSpd));

		// Create WaveDevSettings and add them to the WaveDev file
		wavedevSettings.getImplSettings().put(impl.getId(), settings);

		ProjectCreator.setupImplementation(eSpd, impl, settings);

		final ICodeGeneratorDescriptor codeGenDesc = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(settings.getGeneratorId());
		final IScaComponentCodegen generator = codeGenDesc.getGenerator();
		final Code codeSettings = generator.getInitialCodeSettings(eSpd, settings, impl);
		impl.setCode(codeSettings);

		// TODO: Need to created a SoftPackage<LANG>CodeGenerator 
		// to make sure codeFileType, entryPoint, and runtime are correct
		// Once this is resolved, we can probably just rely on the ProjectCreator addImplementation method
		impl.getCode().setType(CodeFileType.SHARED_LIBRARY);
		impl.getCode().setEntryPoint(null);
		impl.setRuntime(null);

		if (CodegenUtil.canPrimary(impl.getProgrammingLanguage().getName())) {
			final EMap<String, ImplementationSettings> settingsMap = wavedevSettings.getImplSettings();
			boolean primary = true;
			for (final Implementation tmpImpl : eSpd.getImplementation()) {
				if (tmpImpl.getProgrammingLanguage().getName().equals(impl.getProgrammingLanguage().getName())) {
					final ImplementationSettings set = settingsMap.get(tmpImpl.getId());
					if (set.isPrimary()) {
						primary = false; // Don't change the primary if another implementation of the same language is
											// primary
					}
				}
			}
			settings.setPrimary(primary);
		}

		// Save EMF resources
		try {
			final SubMonitor loopProgress = progress.newChild(1).setWorkRemaining(resourceSet.getResources().size());
			for (final Resource resource : resourceSet.getResources()) {
				loopProgress.setTaskName("Saving " + resource.getURI().lastSegment());
				resource.save(null);
				loopProgress.worked(1);
			}
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "Internal Error", e));
		}
	}
}
