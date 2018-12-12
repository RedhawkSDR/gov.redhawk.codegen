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

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.util.ImplementationAndSettings;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceProjectWizard;
import gov.redhawk.ide.ui.wizard.IImportWizard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * @since 8.1
 */
public class NewOctaveScaResourceProjectWizard extends NewScaResourceProjectWizard implements IImportWizard {

	public NewOctaveScaResourceProjectWizard() {
		super();
	}

	@Override
	public void addPages() {
		setResourcePropertiesPage(new OctaveProjectPropertiesWizardPage("", "Component"));
		addPage(getResourcePropertiesPage());
		
		setImplPage(new OctaveImplementationWizardPage("", ICodeGeneratorDescriptor.COMPONENT_TYPE_RESOURCE));
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
			@SuppressWarnings("unchecked")
			List<IWizardPage> pages = (List<IWizardPage>) field.get(this);
			setWizPages(pages);
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
}
