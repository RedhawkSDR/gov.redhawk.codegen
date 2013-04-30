package gov.redhawk.ide.codegen.jinja.template;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;

import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

public class JinjaTemplate implements IScaComponentCodegenTemplate {

	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings, final Object helperObject)
	        throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shouldGenerate() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDefaultFilename(final SoftPkg softPkg, final ImplementationSettings implSettings, final String srcDir) {
		// TODO Auto-generated method stub
		return null;
	}

}
