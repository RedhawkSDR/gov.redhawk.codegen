//TBD TODO

//package gov.redhawk.ide.codegen.frontend.ui;
//import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndProp;
//import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndWizardPage;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//import mil.jpeojtrs.sca.prf.Properties;
//import mil.jpeojtrs.sca.prf.Simple;
//import mil.jpeojtrs.sca.prf.StructSequence;
//import mil.jpeojtrs.sca.spd.SoftPkg;
//import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;
//
//import org.eclipse.core.commands.AbstractHandler;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.commands.IHandlerListener;
//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;
//import org.eclipse.emf.common.util.URI;
//import org.eclipse.emf.ecore.resource.ResourceSet;
//import org.eclipse.jface.wizard.Wizard;
//import org.eclipse.jface.wizard.WizardDialog;
//import org.eclipse.ui.IEditorInput;
//import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.handlers.HandlerUtil;
//import org.eclipse.ui.part.FileEditorInput;
//
//public class EditFrontEndInterfacesSettingsHandler extends AbstractHandler {
//
//	private FrontEndWizardPage frontEndWizardPage;
//
//	@Override
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
//		IEditorPart editor = HandlerUtil.getActiveEditor(event);
//		IEditorInput editorInput = editor.getEditorInput();
//		if (editorInput instanceof FileEditorInput) {
//			this.frontEndWizardPage = new FrontEndWizardPage("Port and Property Selection", "Port and Property Selection", null);
//			this.frontEndWizardPage.setDescription("Select the tuner port type and the set of tuner status properties for the tuner status struct.  Note that required properties may not be removed.");
//			IFile inputFile = ((FileEditorInput) editorInput).getFile();
//			final URI spdUri = URI.createPlatformResourceURI(inputFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
//			final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);
//			
//			Properties currentProps = eSpd.getPropertyFile().getProperties();
//			Wizard simpleFrontEndWizard = new SimpleFrontEndWizard();
//			this.frontEndWizardPage.setCanFinish(true);
//			simpleFrontEndWizard.addPage(this.frontEndWizardPage);
//			WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), simpleFrontEndWizard);
//			
//			// Get the current tuner status properties.
//			for (StructSequence structSequence : currentProps.getStructSequence()) {
//				if (structSequence.getId().equals("frontend_tuner_status")) {
//					Set<Simple> origMap = new HashSet<Simple>(structSequence.getStruct().getSimple());
//					break;
//				}
//			}
////			
////			this.frontEndWizardPage
////			
////			
////			if (dialog.open() == IStatus.OK) {
////				
////				Set<Simple> newMap = new HashSet<Simple>();
////				for (FrontEndProp prop : propsSelected) {
////					newMap.add(prop.getProp());
////				}
////				
////				origMap.addAll(newMap);
////				structSequence.getStruct().getSimple().addAll(origMap);
////				
//				
//				Set<FrontEndProp> propsSelected = this.frontEndWizardPage.getSelectedProperties();
//				
//				}
//				try {
//					currentProps.eResource().save(null);
//				} catch (IOException e) {
//					FrontEndDeviceWizardPlugin.getDefault();
//					FrontEndDeviceWizardPlugin.logError("Failed to write Settings to SCA resources.", new CoreException(new Status(Status.ERROR, FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write Settings to SCA resources.", e)));
//				}
//			}
//		}
//		return null;
//	}
//}
