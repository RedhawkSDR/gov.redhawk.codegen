package gov.redhawk.ide.codegen.frontend.ui.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.emf.ecore.util.EcoreUtil;

import mil.jpeojtrs.sca.prf.Simple;


public class FrontEndProp {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	private boolean required = false;
	private Simple prop;
	
	public FrontEndProp(Simple prop, boolean required) {
		setProp(prop);
		setRequired(required);
	}

	private void setProp(Simple prop) {
		firePropertyChange("prop", this.prop, this.prop = prop);
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
		firePropertyChange("required", this.required, this.required = required);
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
