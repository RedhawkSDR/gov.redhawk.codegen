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
package gov.redhawk.ide.idl.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.jacorb.idl.Interface;

public class IdlTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(final Object element) {
		if (element instanceof Interface) {
			final Interface tmp_int = (Interface) element;
			return tmp_int.name();
		}
		return super.getText(element);
	}
}
