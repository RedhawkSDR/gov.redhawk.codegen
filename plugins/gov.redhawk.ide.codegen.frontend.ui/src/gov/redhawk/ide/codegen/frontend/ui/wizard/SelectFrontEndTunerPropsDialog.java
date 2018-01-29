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
package gov.redhawk.ide.codegen.frontend.ui.wizard;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.ui.FrontEndDeviceUIUtils;
import mil.jpeojtrs.sca.prf.AbstractProperty;

/**
 * @since 1.1
 */
public class SelectFrontEndTunerPropsDialog extends Dialog {

	private static final int DIALOG_HORIZONTAL_HINT = 800;
	private static final int DIALOG_VERTICAL_HINT = 400;
	private CheckboxTableViewer theTableViewer;
	private final WritableSet<AbstractProperty> input = new WritableSet<>();
	private final WritableSet<AbstractProperty> output = new WritableSet<>();
	private DataBindingContext context = new DataBindingContext();
	private FeiDevice feiDevice;

	protected SelectFrontEndTunerPropsDialog(Shell parentShell, FeiDevice feiDevice) {
		super(parentShell);
		this.feiDevice = feiDevice;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		createUIElements(parent);
		return parent;
	}

	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(SelectFrontEndTunerPropsDialog.DIALOG_HORIZONTAL_HINT,
			SelectFrontEndTunerPropsDialog.DIALOG_VERTICAL_HINT).create());
		createTable(client);
	}

	private void createTable(Composite client) {
		theTableViewer = FrontEndDeviceUIUtils.INSTANCE.getCheckboxTableViewer(client, feiDevice);
		theTableViewer.setInput(this.input);
		context.bindSet(ViewersObservables.observeCheckedElements(theTableViewer, AbstractProperty.class), this.output);
	}

	public void setInput(Collection<AbstractProperty> input) {
		this.input.clear();
		this.input.addAll(input);
	}

	public Set<AbstractProperty> getResult() {
		return Collections.unmodifiableSet(this.output);
	}

}
