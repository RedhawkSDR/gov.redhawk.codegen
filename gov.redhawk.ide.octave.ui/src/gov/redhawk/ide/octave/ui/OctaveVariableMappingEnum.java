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

public enum OctaveVariableMappingEnum {
PROPERTY_SIMPLE("Property (Simple)"), PROPERTY_SEQUENCE("Property (Sequence)"), PORT("Port");


	private String value;

	OctaveVariableMappingEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    @Override
    public String toString() {
        return this.getValue();
    }
}
