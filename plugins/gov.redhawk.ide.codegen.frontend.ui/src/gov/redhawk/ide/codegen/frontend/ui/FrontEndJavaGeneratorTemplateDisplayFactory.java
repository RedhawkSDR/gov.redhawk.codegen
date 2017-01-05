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

import java.util.ArrayList;
import java.util.List;

import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerOptionsWizardPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerTypeSelectionWizardPage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;


public class FrontEndJavaGeneratorTemplateDisplayFactory extends FrontEndGeneratorTemplateDisplayFactory {
	
	@Override
	public ICodegenWizardPage[] createPages() {
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();

		setFeiDevice(FrontendFactory.eINSTANCE.createFeiDevice());

		setFrontEndTunerTypeSelectionPage(new FrontEndTunerTypeSelectionWizardPage(getFeiDevice()));
		setFrontEndTunerOptionsWizardPage(new FrontEndTunerOptionsWizardPage(getFeiDevice()));
		setFrontEndTunerPropsWizardPage(new FrontEndTunerPropsPage(getFeiDevice()));
		
		pages.add(getFrontEndTunerTypeSelectionPage());
		pages.add(getFrontEndTunerOptionsWizardPage());
		pages.add(getFrontEndTunerPropsWizardPage());

		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}
}
