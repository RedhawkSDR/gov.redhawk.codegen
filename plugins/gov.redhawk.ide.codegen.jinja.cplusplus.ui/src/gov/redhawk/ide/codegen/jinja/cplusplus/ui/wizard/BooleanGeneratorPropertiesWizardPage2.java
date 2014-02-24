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
package gov.redhawk.ide.codegen.jinja.cplusplus.ui.wizard;

import gov.redhawk.ide.codegen.CodegenPackage;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesWizardPage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.codegen.ui.RedhawkCodegenUiActivator;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceWizard;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

/**
 * @since 1.1
 */
public class BooleanGeneratorPropertiesWizardPage2 extends BooleanGeneratorPropertiesWizardPage {
	protected String template;
	private EContentAdapter templateListener = new EContentAdapter() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void notifyChanged(final Notification msg) {
			super.notifyChanged(msg);
			switch(msg.getFeatureID(ImplementationSettings.class)) {
			case CodegenPackage.IMPLEMENTATION_SETTINGS__TEMPLATE:
				if (msg.getNotifier() instanceof ImplementationSettings) {
					ImplementationSettings newSettings = (ImplementationSettings) msg.getNotifier();
					
					// If our current template is null and the template coming in is not then we just need to add pages.
					if (BooleanGeneratorPropertiesWizardPage2.this.template == null) {
						if (newSettings.getTemplate() != null) {
							BooleanGeneratorPropertiesWizardPage2.this.template = newSettings.getTemplate();
							addCustomPages();
						}
					} else {
						// If our current template is not null and the template coming in is null then we need to remove.
						if (newSettings.getTemplate() == null) {
							removeCustomPages();
							BooleanGeneratorPropertiesWizardPage2.this.template = null;
						} else if (!BooleanGeneratorPropertiesWizardPage2.this.template.equals(newSettings.getTemplate())) {
							// If our current template is not null and the template coming in is not null and they are not the same we need to remove and replace.
							removeCustomPages();
							BooleanGeneratorPropertiesWizardPage2.this.template = newSettings.getTemplate();
							addCustomPages();
						}
					}
				}
			break;
			}
		}
	};

	public BooleanGeneratorPropertiesWizardPage2() {
	}
	
	private void addCustomPages() {
		if (this.getWizard() instanceof NewScaResourceWizard) {
			ICodegenWizardPage[] codeGenTemplatePages = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry().findPageByGeneratorId(this.template);
			((NewScaResourceWizard) this.getWizard()).addTemplatePages(this, codeGenTemplatePages);
		}
		// Otherwise assume the Wizard is taking care of this itself.
	}
	
	private void removeCustomPages() {
		if (this.template == null || "".equals(this.template)) {
			return;
		}
		
		ICodegenWizardPage[] codeGenTemplatePages = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry().findPageByGeneratorId(this.template);
		((NewScaResourceWizard) this.getWizard()).removeTemplatePages(this, codeGenTemplatePages);
		
	}
	
	@Override
	public void configure(SoftPkg softPkg, Implementation spd, ICodeGeneratorDescriptor desc, ImplementationSettings implSettings, String componentType) {
		super.configure(softPkg, spd, desc, implSettings, componentType);
		
		if (!getImplSettings().eAdapters().contains(templateListener)) {
			this.getImplSettings().eAdapters().add(templateListener);
		}
	}
	
	@Override
	public void dispose() {
		removeCustomPages();
		if (getImplSettings() != null && getImplSettings().eAdapters().contains(templateListener)) {
			this.getImplSettings().eAdapters().remove(templateListener);
		}
		super.dispose();
	}
}
