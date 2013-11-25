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


public class OctaveFunctionVariables extends BeanModelObject {
	
	private String name;
	private OctaveVariableMappingEnum mapping;
	private OctaveVariableTypeEnum type;
	private boolean inputVariable;
	
	public OctaveFunctionVariables(boolean inputVariable) {
		this.inputVariable = inputVariable; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public OctaveVariableMappingEnum getMapping() {
		return mapping;
	}

	public void setMapping(OctaveVariableMappingEnum mapping) {
		firePropertyChange("mapping", this.mapping, this.mapping = mapping);
	}

	public OctaveVariableTypeEnum getType() {
		return type;
	}

	public void setType(OctaveVariableTypeEnum type) {
		firePropertyChange("type", this.type, this.type = type);
	}

	public boolean isInputVariable() {
		return inputVariable;
	}
}
