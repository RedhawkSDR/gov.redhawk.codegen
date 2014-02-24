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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import mil.jpeojtrs.sca.prf.Simple;

import org.eclipse.emf.ecore.util.EcoreUtil;

public class FrontEndProp {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private boolean required = false;
	private Simple prop;

	public FrontEndProp(Simple prop, boolean required) {
		setProp(prop);
		setRequired(required);
	}

	private void setProp(Simple prop) {
		Simple oldProp = this.prop;
		this.prop = prop;
		firePropertyChange("prop", oldProp, this.prop);
	}

	public Simple getProp() {
		return EcoreUtil.copy(this.prop);
	}

	/**
	 * Returns true if this property is required to be Front End Interfaces
	 * compliant
	 * @return If the property is required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Sets if this property is required by Front End Interfaces
	 * @param required True if required, false otherwise.
	 */
	protected void setRequired(boolean required) {
		boolean oldRequired = this.required;
		this.required = required;
		firePropertyChange("required", oldRequired, this.required);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	// Equality and hash code are based off of the properties Id field which is useful in the cases where a FrontEnd
	// prop is being added to a set.  
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof FrontEndProp) {
			return ((FrontEndProp) obj).getProp().getId().equals(this.getProp().getId());
		}

		if (obj instanceof Simple) {
			return ((Simple) obj).getId().equals(this.getProp().getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.getProp().getId().hashCode();
	}

}
