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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @since 1.1
 */
public class FrontEndPropLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		FrontEndProp feiProp = (FrontEndProp) element;
		switch (columnIndex) {
		case 0:
			return feiProp.getProp().getName();
		case 1:
			return feiProp.getProp().getType().getName();
		case 2:
			return Boolean.toString(feiProp.isRequired());
		case 3:
			return feiProp.getProp().getDescription();
		default:
			return "";
		}
	}

}
