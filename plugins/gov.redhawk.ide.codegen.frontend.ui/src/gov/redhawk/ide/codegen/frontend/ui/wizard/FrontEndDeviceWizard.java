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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndGeneratorTemplateDisplayFactory;
import gov.redhawk.ide.codegen.ui.ICodeGeneratorPageRegistry;
import gov.redhawk.ide.codegen.ui.ICodeGeneratorPageRegistry2;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.codegen.ui.RedhawkCodegenUiActivator;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.graphiti.dcd.ui.project.wizards.NewScaDeviceCreationProjectWizard;
import gov.redhawk.ide.ui.wizard.IImportWizard;
import gov.redhawk.sca.util.SubMonitor;
import mil.jpeojtrs.sca.spd.Implementation;

public class FrontEndDeviceWizard extends NewScaDeviceCreationProjectWizard implements IImportWizard {

	private static final String CODEGEN_FRONTEND_CPP_ID = "redhawk.codegen.jinja.cpp.component.frontend";
	private static final String CODEGEN_FRONTEND_JAVA_ID = "redhawk.codegen.jinja.java.component.frontend";
	private static final String CODEGEN_FRONTEND_PYTHON_ID = "redhawk.codegen.jinja.python.component.frontend";

	private ICodegenWizardPage genPage = null;

	public FrontEndDeviceWizard() {
		super();
	}

	@SuppressWarnings("unchecked")
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
		} else if (this.getWizPages().size() > 2) {
			// We need to remove the pages previously added.
			ICodeGeneratorPageRegistry codegenTemplateRegistry = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry();

			List<ICodegenDisplayFactory> codegenDisplayFactories = ((ICodeGeneratorPageRegistry2) codegenTemplateRegistry).findCodegenDisplayFactoriesByGeneratorId(
				CODEGEN_FRONTEND_CPP_ID);

			for (ICodegenDisplayFactory factory : codegenDisplayFactories) {
				if (factory instanceof FrontEndGeneratorTemplateDisplayFactory) {
					this.removeTemplatePages(getImplPage(), ((FrontEndGeneratorTemplateDisplayFactory) factory).createPages());
				}
			}

		}

		// Create a implementation specific codegen setup page.
		ICodegenWizardPage newGenPage = null;
		if ("C++".equals(impl.getProgrammingLanguage().getName()) || "Python".equals(impl.getProgrammingLanguage().getName())) {
			if (genPage != null) {
				genPage.dispose();
			}
			newGenPage = new FrontEndGeneratorPropertiesWizardPage();
			newGenPage.setCanFlipToNextPage(true);
		} else if ("Java".equals(impl.getProgrammingLanguage().getName())) {
			if (!(genPage instanceof FrontEndJavaGeneratorPropertiesWizardPage)) {
				if (genPage != null) {
					genPage.dispose();
				}
				newGenPage = new FrontEndJavaGeneratorPropertiesWizardPage();
				newGenPage.setCanFlipToNextPage(true);
			}
		}

		if (newGenPage != null) {
			newGenPage.setWizard(this);
			genPage = newGenPage;
		}

		// Dispose all pages added by previous implementation selections
		for (int i = getWizPages().size() - 1; i > getWizardPageIndex(getImplPage()); i--) {
			IWizardPage page = getWizPages().get(i);
			page.dispose();
			getWizPages().remove(i);

		}

		if ("C++".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate(CODEGEN_FRONTEND_CPP_ID);
		} else if ("Java".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate(CODEGEN_FRONTEND_JAVA_ID);
		} else if ("Python".equals(impl.getProgrammingLanguage().getName())) {
			settings.setTemplate(CODEGEN_FRONTEND_PYTHON_ID);
		}

		ICodeGeneratorPageRegistry codegenTemplateRegistry = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry();

		List<ICodegenDisplayFactory> codegenDisplayFactories = ((ICodeGeneratorPageRegistry2) codegenTemplateRegistry).findCodegenDisplayFactoriesByGeneratorId(
			settings.getTemplate());
		for (ICodegenDisplayFactory factory : codegenDisplayFactories) {
			if (factory instanceof FrontEndGeneratorTemplateDisplayFactory) {

				// Collect all template specific pages to be created
				FrontEndGeneratorTemplateDisplayFactory feiFactory = (FrontEndGeneratorTemplateDisplayFactory) factory;
				List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>(Arrays.asList(feiFactory.createPages()));

				// Set new codegen setup page as the first page to be created
				pages.add(0, genPage);

				for (ICodegenWizardPage page : pages) {
					page.configure(getSoftPkg(), impl, codeGeneratorDescriptor, settings, getComponentType());
				}
				this.addTemplatePages(getImplPage(), pages.toArray(new ICodegenWizardPage[pages.size()]));
			}
		}

		settings.setOutputDir(CodegenFileHelper.createDefaultOutputDir(impl.getSoftPkg(), codeGeneratorDescriptor));
		getImplPage().setPageComplete(true);
	}

	@Override
	protected void modifyResult(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
	}
}
