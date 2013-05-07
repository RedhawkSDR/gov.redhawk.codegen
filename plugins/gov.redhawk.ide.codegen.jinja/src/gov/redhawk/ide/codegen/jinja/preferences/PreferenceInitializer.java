package gov.redhawk.ide.codegen.jinja.preferences;

import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		JinjaGeneratorPlugin.getDefault().getPreferenceAccessor().setDefault(JinjaPreferenceConstants.CODEGEN_PATH_PREFERENCE, "${OssieHome}/bin/redhawk-codegen");
	}

}
