package gov.redhawk.ide.codegen.jinja.python.template;

import java.io.File;

import mil.jpeojtrs.sca.spd.SoftPkg;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.template.JinjaTemplate;

public class DefaultPythonTemplate extends JinjaTemplate {

	@Override
    public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = softPkg.getName();
		return srcDir + File.separator + prefix + ".py";
    }

}
