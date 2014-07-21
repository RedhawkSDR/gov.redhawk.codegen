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
package gov.redhawk.ide.softpackage.ui.wizard;


import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.ScaProjectPropertiesWizardPage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;

public class NewSoftpackageScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {

	private SoftpackageProjectPropertiesWizardPage p1;
	private SoftpackageImplementationWizardPage p2;
	private SoftpackageTableWizardPage p3;
	
	private final SoftpackageTableWizardPage createNewLibraryPage = new SoftpackageTableWizardPage(true);
	private final SoftpackageTableWizardPage useExistingLibraryPage = new SoftpackageTableWizardPage(false); 

	public NewSoftpackageScaResourceProjectWizard() {
		super();
		p1 = new SoftpackageProjectPropertiesWizardPage("", "Softpackage");
		p2 = new SoftpackageImplementationWizardPage("", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE);		
		p3 = createNewLibraryPage;
		
		// Updates wizard based on selection of "Create new library" or "Use existing library"
		p2.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (p2.getModel().isCreateNewLibrary()) {
					p3 = createNewLibraryPage;
				} else {
					p3 = useExistingLibraryPage;
				}
				getContainer().updateButtons();
			}
		});
	}

	@Override
	public void addPages() {
		setResourcePropertiesPage((ScaProjectPropertiesWizardPage) p1);
		addPage(getResourcePropertiesPage());

		setImplPage((ImplementationWizardPage) p2);
		getImplPage().setImpl(this.getImplementation()); 
		getImplList().add(new ImplementationAndSettings(getImplPage().getImplementation(), getImplPage().getImplSettings()));
		addPage(p2);
		
		addPage(createNewLibraryPage);
		addPage(useExistingLibraryPage); 

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
	public IWizardPage getNextPage(IWizardPage currentPage) {
		if (currentPage == p1) {
			return p2;
		}
		if (currentPage == p2) { 
			return p3;
		}
		return null;
	}

	@Override
	public boolean canFinish() {
		return p1.canFlipToNextPage() && p2.canFlipToNextPage() && p3.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		// TODO temporary output
		System.out.println(p3.toString());
		return true;
	}
}
