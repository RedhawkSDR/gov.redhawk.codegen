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

import gov.redhawk.ide.codegen.internal.CodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusFrontEndGenerator;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

public class FrontEndImplementationWizardPage extends ImplementationWizardPage {

	ViewerFilter[] viewerFilters = new ViewerFilter[1];
	
	public FrontEndImplementationWizardPage(String name, String componentTypeDevice) {
		super(name, componentTypeDevice);
		ViewerFilter viewerFilter = new ViewerFilter() {
			
			@SuppressWarnings("restriction")
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (CplusplusFrontEndGenerator.ID.equalsIgnoreCase(((CodeGeneratorDescriptor) element).getId())) {
					return true;
				}
				return false;
			}
		};
		this.viewerFilters[0] = viewerFilter;
		
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.getCodeGeneratorEntryViewer().setFilters(this.viewerFilters);
	}

}
