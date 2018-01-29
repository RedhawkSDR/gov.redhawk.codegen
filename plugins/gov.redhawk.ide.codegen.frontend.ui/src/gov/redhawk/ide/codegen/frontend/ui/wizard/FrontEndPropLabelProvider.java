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

import gov.redhawk.frontend.util.TunerProperties;
import gov.redhawk.ide.codegen.frontend.FeiDevice;
import mil.jpeojtrs.sca.prf.Simple;

public class FrontEndPropLabelProvider extends LabelProvider implements ITableLabelProvider {

	private FeiDevice feiDevice;

	public FrontEndPropLabelProvider(FeiDevice feiDevice) {
		this.feiDevice = feiDevice;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Simple feiProp = (Simple) element;
		switch (columnIndex) {
		case 0:
			return feiProp.getName();
		case 1:
			return feiProp.getType().getLiteral();
		case 2:
			boolean required = TunerProperties.TunerStatusAllocationProperties.fromPropID(feiProp.getId()).isRequired(feiDevice.isScanner());
			return Boolean.toString(required);
		case 3:
			return feiProp.getDescription();
		default:
			return "";
		}
	}

}
