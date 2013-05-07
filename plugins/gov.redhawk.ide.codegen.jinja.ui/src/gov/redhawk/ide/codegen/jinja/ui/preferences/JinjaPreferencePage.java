package gov.redhawk.ide.codegen.jinja.ui.preferences;

import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;
import gov.redhawk.ide.codegen.jinja.preferences.JinjaPreferenceConstants;
import gov.redhawk.ide.codegen.jinja.ui.JinjaUiPlugin;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class JinjaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public JinjaPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(JinjaUiPlugin.getDefault().getPreferenceStore());
	}

	public void init(final IWorkbench workbench) {
		// Modify settings in the base plug-in rather than the UI plug-in, which just provides this editor.
		this.setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, JinjaGeneratorPlugin.PLUGIN_ID));
	}

	@Override
	protected void createFieldEditors() {
		final FileFieldEditor codegenPath = new FileFieldEditor(JinjaPreferenceConstants.CODEGEN_PATH_PREFERENCE, "Code Generator Location:",
		        getFieldEditorParent());
		addField(codegenPath);
	}

}
