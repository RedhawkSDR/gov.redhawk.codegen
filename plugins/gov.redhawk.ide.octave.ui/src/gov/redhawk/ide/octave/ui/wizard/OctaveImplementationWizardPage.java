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
package gov.redhawk.ide.octave.ui.wizard;

import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.codegen.internal.CodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusOctaveGenerator;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * @since 8.1
 */
public class OctaveImplementationWizardPage extends ImplementationWizardPage {

	public OctaveImplementationWizardPage(String name, String componenttype) {
		super(name, componenttype);
	}
	
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		// Set the Programming language viewer to C++ 
		List<String> languageOptions = Arrays.asList(RedhawkCodegenActivator.getCodeGeneratorsRegistry().getLanguages());
		int indexOfCpp = languageOptions.indexOf("C++");
		this.getProgLangEntryViewer().select(indexOfCpp);
		this.getProgLangEntryViewer().setEnabled(false);

		
		ViewerFilter filter = new ViewerFilter() {
			
			@SuppressWarnings("restriction")
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (CplusplusOctaveGenerator.ID.equalsIgnoreCase(((CodeGeneratorDescriptor) element).getId())) {
					return true;
				}
				return false;
			}
		};
		
		ViewerFilter[] filters = new ViewerFilter[1];
		filters[0] = filter;
		
		getCodeGeneratorEntryViewer().setFilters(filters);
		
		// Must do this after the filter is set since it is used in the selection
		this.handleProgLangSelection();
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
}
