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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class SelectFRIPropertyDialog extends Dialog {

	private static final int DIALOG_HORIZONTAL_HINT=800;
	private static final int DIALOG_VERTICAL_HINT=400;
	private List<FrontEndProp> output = null;
	private CheckboxTableViewer theTableViewer;
	private List<FrontEndProp> input = null;
	
	protected SelectFRIPropertyDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		createUIElements(parent);
		return parent;
	}


	private void createUIElements(Composite client) {
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(DIALOG_HORIZONTAL_HINT, DIALOG_VERTICAL_HINT).create());
		createTable(client);
	}

	private void createTable(Composite client) {
		theTableViewer = FrontEndDeviceUIUtils.INSTANCE.getCheckboxTableViewer(client);
		theTableViewer.setInput(this.input);
	}
	
	public void setInput(List<FrontEndProp> input) {
		this.input = input;
	}
	
	public List<FrontEndProp> getResult() {
		return this.output;
	}
	
	@Override
	protected void okPressed() {
		output = new ArrayList<FrontEndProp>();
		Object[] checkedElements = this.theTableViewer.getCheckedElements();
		for (Object element : checkedElements) {
			output.add((FrontEndProp) element);
		}
		super.okPressed();
	}
	
}
