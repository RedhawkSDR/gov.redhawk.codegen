package gov.redhawk.ide.codegen.jinja.java.template;

import java.io.File;

import mil.jpeojtrs.sca.spd.SoftPkg;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;
import gov.redhawk.ide.codegen.jinja.template.JinjaTemplate;

public class DefaultJavaTemplate extends JinjaTemplate {

	@Override
    public String getDefaultFilename(SoftPkg softPkg, ImplementationSettings implSettings, String srcDir) {
		final String prefix = softPkg.getName();
		final String outputDir = srcDir + File.separator + "src";
		String packagePath = "";
		for (final Property property : implSettings.getProperties()) {
			if ("java_package".equals(property.getId())) {
				packagePath = property.getValue().replace('.', File.separatorChar) + File.separator;
				break;
			}
		}
		return outputDir + File.separator + packagePath + prefix + ".java";
	}

}
