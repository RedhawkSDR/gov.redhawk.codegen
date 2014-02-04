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
package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndGeneratorTemplateDisplayFactory;
import gov.redhawk.ide.codegen.ui.ICodeGeneratorPageRegistry;
import gov.redhawk.ide.codegen.ui.ICodeGeneratorPageRegistry2;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory;
import gov.redhawk.ide.codegen.ui.RedhawkCodegenUiActivator;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.dcd.ui.wizard.NewScaDeviceCreationProjectWizard;
import gov.redhawk.ide.ui.wizard.IImportWizard;
import gov.redhawk.sca.util.SubMonitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class FrontEndDeviceWizard extends NewScaDeviceCreationProjectWizard implements IImportWizard {

	public FrontEndDeviceWizard() {
		super();
	}
	
	@Override
	public void addPages() {
		setResourcePropertiesPage(new FrontEndDeviceProjectPropertiesWizardPage("", "Device"));
		addPage(getResourcePropertiesPage());
		
		setImplPage(new FrontEndImplementationWizardPage("", ICodeGeneratorDescriptor.COMPONENT_TYPE_DEVICE));
		getImplPage().setDescription("Choose the initial settings for the new implementation.");
		getImplPage().setImpl(this.getImplementation());
		addPage(getImplPage());
		
		getImplList().add(new ImplementationAndSettings(getImplPage().getImplementation(), getImplPage().getImplSettings()));
		
		try {
			final Field field = Wizard.class.getDeclaredField("pages");
			field.getModifiers();
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			setWizPages((List<IWizardPage>) field.get(this));
		} catch (final SecurityException e1) {
			// PASS
		} catch (final NoSuchFieldException e1) {
			// PASS
		} catch (final IllegalArgumentException e) {
			// PASS
		} catch (final IllegalAccessException e) {
			// PASS
		}
	}
	
	@Override
	public void generatorChanged(final Implementation impl, final ICodeGeneratorDescriptor codeGeneratorDescriptor, final String previousImplId) {
		if (codeGeneratorDescriptor == null || "".equals(codeGeneratorDescriptor.getName())) {
			return;
		}
		
		// There should only be one implementation
		ImplementationSettings settings = this.getImplList().get(0).getImplementationSettings();
		
		if (codeGeneratorDescriptor.getId().equals(previousImplId)) {
			return;
		} else if (this.getWizPages().size() > 2){ 
			// We need to remove the pages previously added.
			ICodeGeneratorPageRegistry codegenTemplateRegistry = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry();
			
			List<ICodegenDisplayFactory> codegenDisplayFactories = ((ICodeGeneratorPageRegistry2) codegenTemplateRegistry).findCodegenDisplayFactoriesByGeneratorId("redhawk.codegen.jinja.cpp.component.frontend");
			
			for (ICodegenDisplayFactory factory : codegenDisplayFactories) {
				if (factory instanceof FrontEndGeneratorTemplateDisplayFactory) {
					this.removeTemplatePages(getImplPage(), ((FrontEndGeneratorTemplateDisplayFactory) factory).createPages());
				}
			}
			
		}
		
		if ("C++".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate("redhawk.codegen.jinja.cpp.component.frontend");
		} else if ("Java".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate("redhawk.codegen.jinja.java.component.frontend");
		} else if ("Python".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate("redhawk.codegen.jinja.python.component.frontend");
		}
		
		ICodeGeneratorPageRegistry codegenTemplateRegistry = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry();
		
		List<ICodegenDisplayFactory> codegenDisplayFactories = ((ICodeGeneratorPageRegistry2) codegenTemplateRegistry).findCodegenDisplayFactoriesByGeneratorId(settings.getTemplate());
		
		for (ICodegenDisplayFactory factory : codegenDisplayFactories) {
			if (factory instanceof FrontEndGeneratorTemplateDisplayFactory) {
				this.addTemplatePages(getImplPage(), ((FrontEndGeneratorTemplateDisplayFactory) factory).createPages());
			}
		}
		
		settings.setOutputDir(CodegenFileHelper.createDefaultOutputDir(impl.getSoftPkg(), codeGeneratorDescriptor));
		getImplPage().setPageComplete(true);
	}
	
	@Override
	protected void modifyResult(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
	}
}
