package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory2;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;
import gov.redhawk.sca.util.SubMonitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class FrontEndGeneratorDisplayFactory implements ICodegenDisplayFactory2 {

	private ModifiedBooleanGeneratorPropertiesWizardPage propertiesPage;

	/**
	 * 
	 * @deprecated Use createPages instead
	 */
	@Override
	public ICodegenWizardPage createPage() {
		return createPages()[0];
	}

	@Override
	public ICodegenComposite createComposite(Composite parent, int style, FormToolkit toolkit) {
		return new BooleanGeneratorPropertiesComposite(parent, style, toolkit);
	}

	@Override
	public void modifyProject(IProject project, IFile spdFile, SubMonitor newChild) throws CoreException {
		// Drop through, no modifications here.
	}

	@Override
	public ICodegenWizardPage[] createPages() {
		this.propertiesPage = new ModifiedBooleanGeneratorPropertiesWizardPage();
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();
		pages.add(this.propertiesPage);
		
		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}

}
