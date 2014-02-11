package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndProp;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class EditFrontEndInterfacesSettingsHandler extends AbstractHandler {

	private FrontEndTunerPropsPage frontEndWizardPage;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof FileEditorInput) {

			IFile inputFile = ((FileEditorInput) editorInput).getFile();
			final URI spdUri = URI.createPlatformResourceURI(inputFile.getFullPath().toString(), true).appendFragment(SoftPkg.EOBJECT_PATH);
			final SoftPkg eSpd = (SoftPkg) resourceSet.getEObject(spdUri, true);

			Properties currentProps = eSpd.getPropertyFile().getProperties();

			StructSequence tunerStatusStructSeq = null;
			Struct tunerStatusStruct = null;
			Set<Simple> structSeqProperties = null;

			// Get the current tuner status properties.
			for (StructSequence structSequence : currentProps.getStructSequence()) {
				if (structSequence.getId().equals(FrontEndDeviceUIUtils.TUNER_STATUS_STRUCT_SEQ_ID)) {
					tunerStatusStructSeq = structSequence;
					break;
				}
			}

			// They must have removed their Tuner Status Struct Seq Property, we need to add it back for them.
			if (tunerStatusStructSeq == null) {
				tunerStatusStructSeq = PrfFactory.eINSTANCE.createStructSequence();
				tunerStatusStructSeq.setId(FrontEndDeviceUIUtils.TUNER_STATUS_STRUCT_SEQ_ID);
				tunerStatusStructSeq.setName(FrontEndDeviceUIUtils.TUNER_STATUS_STRUCT_SEQ_NAME);
				currentProps.getStructSequence().add(tunerStatusStructSeq);
			}

			// They must have removed their Tuner Status Struct Property, we need to add it back for them.
			if (tunerStatusStructSeq.getStruct() == null) {
				tunerStatusStruct = PrfFactory.eINSTANCE.createStruct();
				tunerStatusStruct.setName(FrontEndDeviceUIUtils.TUNER_STATUS_STRUCT_NAME);
				tunerStatusStruct.setId(FrontEndDeviceUIUtils.TUNER_STATUS_STRUCT_ID);
				tunerStatusStructSeq.setStruct(tunerStatusStruct);
			}

			tunerStatusStruct = tunerStatusStructSeq.getStruct();
			
			// struct seq properties is there current list of simples.
//			structSeqProperties = new HashSet<Simple>(EcoreUtil.copyAll(tunerStatusStruct.getSimple()));
			structSeqProperties = new HashSet<Simple>(tunerStatusStruct.getSimple());
			
			// Pass it into the wizard page so that the displayed props are the ones they have.
			this.frontEndWizardPage = new FrontEndTunerPropsPage(structSeqProperties);
			this.frontEndWizardPage.setDescription("Select the tuner port type and the set of tuner status properties for the tuner status struct.  Note that required properties may not be removed.");
			Wizard simpleFrontEndWizard = new SimpleFrontEndWizard();
			this.frontEndWizardPage.setCanFinish(true);
			simpleFrontEndWizard.addPage(this.frontEndWizardPage);
			WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), simpleFrontEndWizard);

			// Open the wizard.
			if (dialog.open() == IStatus.OK) {

				// Props selected is the full set of properties they want.
				Set<FrontEndProp> propsSelected = this.frontEndWizardPage.getSelectedProperties();
				
				// Adding the new props.
				for (FrontEndProp prop : propsSelected) {
					boolean unique = true;
					for (Simple simp : tunerStatusStruct.getSimple()) {
						if (simp.getId().equals(prop.getProp().getId())) {
							// already part of the list don't add.
							unique = false;
							break;
						}
					}
					if (unique) {
						tunerStatusStruct.getSimple().add(prop.getProp());
					}
				}
				
				// Can't remove them while iterating so store those to remove.
				List<Simple> simplesToRemove = new ArrayList<Simple>();
				
				// Removing the props the user wants removed.
				for (Simple simp : tunerStatusStruct.getSimple()) {
					boolean remove = true;
					for (FrontEndProp prop : propsSelected) {
						if (simp.getId().equals(prop.getProp().getId())) {
							// Already part of the list, don't remove.
							remove = false;
							break;
						}
					}
					if (remove) {
						simplesToRemove.add(simp);
					}
				}
				
				// Remove all throws exception, must remove each one individually.
				for (Simple simp : simplesToRemove) {
					tunerStatusStruct.getSimple().remove(simp);
				}

				try {
					eSpd.eResource().save(null);
					currentProps.eResource().save(null);
				} catch (IOException e) {
					FrontEndDeviceWizardPlugin.getDefault();
					FrontEndDeviceWizardPlugin.logError("Failed to write Settings to SCA resources.", new CoreException(new Status(Status.ERROR,
						FrontEndDeviceWizardPlugin.PLUGIN_ID, "Failed to write Settings to SCA resources.", e)));
				}
			}
		}
		return null;
	}
}
