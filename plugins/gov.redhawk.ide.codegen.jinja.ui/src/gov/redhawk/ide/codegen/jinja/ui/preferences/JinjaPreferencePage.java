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
package gov.redhawk.ide.codegen.jinja.ui.preferences;

import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;
import gov.redhawk.ide.codegen.jinja.preferences.JinjaPreferenceConstants;
import gov.redhawk.ide.codegen.jinja.ui.JinjaUiPlugin;
import gov.redhawk.sca.util.PluginUtil;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class JinjaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static class FileFieldEditorWithVariableSubstitution extends FileFieldEditor {
		private FileFieldEditorWithVariableSubstitution(final String name, final String labelText, final Composite parent) {
			super(name, labelText, false, StringFieldEditor.VALIDATE_ON_KEY_STROKE, parent);
		}

		@Override
		protected boolean checkState() {
			String fileName = getTextControl().getText();
			try {
				fileName = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(fileName, false);
			} catch (final CoreException e) {
				JinjaUiPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, JinjaUiPlugin.PLUGIN_ID, "Unexpected error in variable substitution", e));
			}

			final File file = new File(PluginUtil.replaceEnvIn(fileName, null));
			if (!file.exists()) {
				showErrorMessage("File does not exist");
				return false;
			} else if (!file.canExecute()) {
				showErrorMessage("File is not executable");
				return false;
			} else {
				clearErrorMessage();
				return true;
			}
		}
	}

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
		final FileFieldEditor codegenPath = new FileFieldEditorWithVariableSubstitution(JinjaPreferenceConstants.CODEGEN_PATH_PREFERENCE,
		        "Code Generator Location:", getFieldEditorParent());

		addField(codegenPath);
	}
}
