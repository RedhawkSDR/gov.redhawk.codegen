package gov.redhawk.ide.codegen.frontend.ui;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import gov.redhawk.ide.codegen.CodegenPackage;
import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesWizardPage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.ide.codegen.ui.RedhawkCodegenUiActivator;
import gov.redhawk.ide.spd.ui.wizard.NewScaResourceWizard;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

public class ModifiedBooleanGeneratorPropertiesWizardPage extends BooleanGeneratorPropertiesWizardPage {

	
	protected String template;


	public ModifiedBooleanGeneratorPropertiesWizardPage() {
		this.setMessage("DEBUG MESSAGE TESTING");
	}
	private void addCustomPages() {
		ICodegenWizardPage[] codeGenTemplatePages = RedhawkCodegenUiActivator.getCodeGeneratorsTemplateRegistry().findPageByGeneratorId(this.template);
		((NewScaResourceWizard) this.getWizard()).addTemplatePages(this, codeGenTemplatePages);
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
		
		this.getImplSettings().eAdapters().add(new EContentAdapter() {
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
						if (ModifiedBooleanGeneratorPropertiesWizardPage.this.template == null || !newSettings.getTemplate().equals(ModifiedBooleanGeneratorPropertiesWizardPage.this.template)) {
							removeCustomPages();
							ModifiedBooleanGeneratorPropertiesWizardPage.this.template = newSettings.getTemplate();
							addCustomPages();
						}
							
					}
				break;
				}
			}
		});
	}
}
