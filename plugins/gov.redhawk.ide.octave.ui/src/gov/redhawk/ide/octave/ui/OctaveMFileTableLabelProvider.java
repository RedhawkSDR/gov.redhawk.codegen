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
package gov.redhawk.ide.octave.ui;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class OctaveMFileTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		OctaveFunctionVariables octaveVar = (OctaveFunctionVariables) element;
		switch (columnIndex) {

		// Column 1, the name/id field
		case 0:
			if (octaveVar == null) {
				return "";
			}

			if (octaveVar.getMapping() != null && octaveVar.getMapping().equals(OctaveVariableMappingEnum.PORT)) {
				if (octaveVar.isInputVariable()) {
					return octaveVar.getName() + "_in";
				} else {
					return octaveVar.getName() + "_out";
				}
			}

			return octaveVar.getName();

			// Column 2, the property mapping field
		case 1:
			if (octaveVar == null || octaveVar.getMapping() == null) {
				return "";
			}

			return octaveVar.getMapping().getValue();

			// Column 3, the property/port type
		case 2:
			if (octaveVar == null || octaveVar.getType() == null) {
				return "";
			}

			return octaveVar.getType().getValue();

		default:
			return "";
		}

	}

}
