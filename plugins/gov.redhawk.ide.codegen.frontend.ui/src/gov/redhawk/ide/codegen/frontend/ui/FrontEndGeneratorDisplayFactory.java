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
package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.ide.codegen.jinja.cplusplus.ui.wizard.BooleanGeneratorPropertiesWizardPage2;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory2;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.sca.util.SubMonitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @since 1.1
 */
public class FrontEndGeneratorDisplayFactory implements ICodegenDisplayFactory2 {

	private BooleanGeneratorPropertiesWizardPage2 propertiesPage;

	/**
	 * 
	 * @deprecated Use createPages instead
	 */
	@Deprecated
	@Override
	public ICodegenWizardPage createPage() {
		return createPages()[0];
	}

	@Override
	public ICodegenComposite createComposite(Composite parent, int style, FormToolkit toolkit) {
		return new BooleanGeneratorPropertiesComposite(parent, style, toolkit);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		// Drop through, no modifications here.
	}

	@Override
	public ICodegenWizardPage[] createPages() {
		this.propertiesPage = new BooleanGeneratorPropertiesWizardPage2();
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();
		pages.add(this.propertiesPage);

		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

}
