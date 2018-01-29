/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.codegen.frontend.ui;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import gov.redhawk.frontend.util.TunerProperties.TunerStatusProperty;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;
import gov.redhawk.model.sca.commands.ScaModelCommand;
import gov.redhawk.ui.editor.SCAFormEditor;
import mil.jpeojtrs.sca.prf.AbstractProperty;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.collections.FeatureMapList;

/**
 * @since 1.1
 */
public class EditFrontEndInterfacesSettingsHandler extends AbstractHandler {

	private FrontEndTunerPropsPage frontEndWizardPage;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (!(editor instanceof SCAFormEditor)) {
			return null;
		}

		// Try to get the PRF, SCD, and the editor's editing domain
		Resource resource = ((SCAFormEditor) editor).getMainResource();
		SoftPkg spd = SoftPkg.Util.getSoftPkg(resource);
		if (spd == null) {
			return null;
		}
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		if (prf == null || scd == null) {
			return null;
		}

		// Attempt to recreate FEI device settings
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);

		StructSequence tunerStatusStructSeq = null;

		// Get the current tuner status properties
		for (StructSequence structSequence : prf.getStructSequence()) {
			if (structSequence.getId().equals(TunerStatusProperty.INSTANCE.getId())) {
				tunerStatusStructSeq = structSequence;
				break;
			}
		}

		// If they removed the tuner status property, add it back
		if (tunerStatusStructSeq == null) {
			StructSequence newStructSeq = TunerStatusProperty.INSTANCE.createProperty(feiDevice.isScanner());
			ScaModelCommand.execute(prf, () -> {
				prf.getStructSequence().add(newStructSeq);
			});
			tunerStatusStructSeq = newStructSeq;
		}

		// If they removed the tuner status's inner struct, add it back
		if (tunerStatusStructSeq.getStruct() == null) {
			StructSequence structSeq = tunerStatusStructSeq;
			Struct newStruct = TunerStatusProperty.INSTANCE.createProperty(feiDevice.isScanner()).getStruct();
			ScaModelCommand.execute(tunerStatusStructSeq, () -> {
				structSeq.setStruct(newStruct);
			});
		}

		// Pass it into the wizard page so that the displayed props are the ones they have.
		Set<AbstractProperty> structSeqProperties = new HashSet<>(new FeatureMapList<>(tunerStatusStructSeq.getStruct().getFields(), AbstractProperty.class));
		this.frontEndWizardPage = new FrontEndTunerPropsPage(feiDevice, structSeqProperties);
		this.frontEndWizardPage.setDescription(
			"Select the tuner port type and the set of tuner status properties for the tuner status struct.  Note that required properties may not be removed.");
		Wizard simpleFrontEndWizard = new SimpleFrontEndWizard();
		this.frontEndWizardPage.setCanFinish(true);
		simpleFrontEndWizard.addPage(this.frontEndWizardPage);
		WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), simpleFrontEndWizard);

		// Open the wizard.
		if (dialog.open() == IStatus.OK) {
			Struct tunerStatusStruct = tunerStatusStructSeq.getStruct();
			ScaModelCommand.execute(prf, () -> {
				// Clear existing fields, use the ones the user selected
				tunerStatusStruct.getFields().clear();
				for (AbstractProperty prop : frontEndWizardPage.getSelectedProperties()) {
					if (prop instanceof Simple) {
						tunerStatusStruct.getSimple().add((Simple) prop);
					} else if (prop instanceof SimpleSequence) {
						tunerStatusStruct.getSimpleSequence().add((SimpleSequence) prop);
					}
				}

				// Try and sort the list.
				ECollections.sort(tunerStatusStruct.getSimple(), new Comparator<Simple>() {
					@Override
					public int compare(Simple o1, Simple o2) {
						return (o1).getId().compareTo(o2.getId());
					}
				});
			});
		}

		return null;
	}
}
