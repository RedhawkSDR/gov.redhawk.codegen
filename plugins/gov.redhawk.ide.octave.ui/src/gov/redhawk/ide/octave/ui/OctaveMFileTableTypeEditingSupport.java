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
// Based on examples found here: http://www.subshell.com/en/subshell/blog/Eclipse-RCP-Comboboxes-inside-a-JFace-TableViewer100.html

package gov.redhawk.ide.octave.ui;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class OctaveMFileTableTypeEditingSupport extends EditingSupport {

	private ComboBoxViewerCellEditor cellEditor = null;
	private ColumnViewer viewer;
	private IValidator validator;

	public OctaveMFileTableTypeEditingSupport(ColumnViewer viewer, IValidator validator) {
		super(viewer);
		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
		cellEditor.setLabelProvider(new LabelProvider());
		cellEditor.setContentProvider(new ArrayContentProvider());
		cellEditor.setInput(OctaveVariableTypeEnum.values());
		this.validator = validator;
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof OctaveFunctionVariables) {
			OctaveFunctionVariables octaveFuncVar = (OctaveFunctionVariables) element;
			return octaveFuncVar.getType();
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof OctaveFunctionVariables && value instanceof OctaveVariableTypeEnum) {
			OctaveFunctionVariables octFuncVar = (OctaveFunctionVariables) element;
			OctaveVariableTypeEnum newType = (OctaveVariableTypeEnum) value;
			/* only set new value if it differs from old one */
			if (octFuncVar.getType() == null || !octFuncVar.getType().equals(newType)) {
				octFuncVar.setType(newType);
				validator.validate(null);
				this.viewer.refresh();
			}
		}
	}

}
