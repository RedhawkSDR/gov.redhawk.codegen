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
package gov.redhawk.ide.softpackage.ui.wizard.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SoftpackageModel {
	public static final String[] TYPES = { "C++ Library", "Octave Library" };
	public static final String TYPE_NAME = "typeName";

	private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private String typeName;

	public SoftpackageModel() {
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String newValue) {
		final String oldValue = this.typeName;
		this.typeName = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, TYPE_NAME, oldValue, newValue));
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public String toString() {
		String str = "Type: " + getTypeName() + "\n";

		return str;
	}
}
