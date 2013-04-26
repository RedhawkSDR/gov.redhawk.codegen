package gov.redhawk.ide.codegen.jinja.python.template;

import java.util.List;

import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.CoreException;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;

public class PullPortTemplate implements IScaComponentCodegenTemplate {

	public PullPortTemplate() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getExecutableFileNames(
			ImplementationSettings implSettings, SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getAllGeneratedFileNames(
			ImplementationSettings implSettings, SoftPkg softPkg) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateFile(String fileName, SoftPkg softPkg,
			ImplementationSettings implSettings, Object helperObject)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shouldGenerate() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDefaultFilename(SoftPkg softPkg,
			ImplementationSettings implSettings, String srcDir) {
		// TODO Auto-generated method stub
		return null;
	}

}
