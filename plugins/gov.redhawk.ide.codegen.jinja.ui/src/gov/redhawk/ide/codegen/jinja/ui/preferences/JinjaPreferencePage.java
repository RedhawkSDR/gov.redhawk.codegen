package gov.redhawk.ide.codegen.jinja.ui.preferences;

import gov.redhawk.ide.codegen.jinja.ui.JinjaUiPlugin;
import org.eclipse.jface.preference.FileFieldEditor;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JinjaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public JinjaPreferencePage() {
		super(GRID);
		setPreferenceStore(JinjaUiPlugin.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		final FileFieldEditor codegenPath = new FileFieldEditor(JinjaUiPreferenceConstants.CODEGEN_PATH_PREFERENCE, "Code Generator Location:", getFieldEditorParent());
		addField(codegenPath);
	}

}
