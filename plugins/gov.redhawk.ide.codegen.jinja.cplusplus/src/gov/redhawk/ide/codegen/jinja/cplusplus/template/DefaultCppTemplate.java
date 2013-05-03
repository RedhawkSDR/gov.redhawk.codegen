package gov.redhawk.ide.codegen.jinja.cplusplus.template;

import java.io.File;

import mil.jpeojtrs.sca.spd.SoftPkg;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.template.JinjaTemplate;

public class DefaultCppTemplate extends JinjaTemplate {

	@Override
    public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		return srcDir + File.separator + softPkg.getName() + ".cpp";
	}
}
