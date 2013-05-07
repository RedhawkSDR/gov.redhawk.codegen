package gov.redhawk.ide.codegen.jinja.internal.ui.preferences;

import gov.redhawk.ide.codegen.jinja.ui.JinjaUiPlugin;
import gov.redhawk.ide.codegen.jinja.ui.preferences.JinjaUiPreferenceConstants;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		JinjaUiPlugin.getDefault().getPreferenceStore().setDefault(JinjaUiPreferenceConstants.CODEGEN_PATH_PREFERENCE, "${OssieHome}/bin/redhawk-codegen");
	}

}
