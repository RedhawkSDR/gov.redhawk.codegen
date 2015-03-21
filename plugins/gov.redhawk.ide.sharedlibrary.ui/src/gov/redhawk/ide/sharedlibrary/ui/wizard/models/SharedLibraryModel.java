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
package gov.redhawk.ide.sharedlibrary.ui.wizard.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharedLibraryModel {
	public static final String CPP_TYPE = "C++ Library";
	public static final String OCTAVE_TYPE = "Octave Library";
	public static final String[] TYPES = { CPP_TYPE, OCTAVE_TYPE };
	public static final String TYPE_NAME = "typeName";

	private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private String typeName;
	private File currentMFile = null;
	private List<File> mFilesList = new ArrayList<File>();

	public SharedLibraryModel() {
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String newValue) {
		final String oldValue = this.typeName;
		this.typeName = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, TYPE_NAME, oldValue, newValue));
	}

	/**
	 * Get a List of the shared library m-files.
	 * @return List of full paths to the m-files.
	 */
	public List<File> getmFilesList() {
		return mFilesList;
	}

	public File getCurrentMFile() {
		return currentMFile;
	}

	public void setCurrentMFile(File newMFile) {
		File oldMFile = this.currentMFile;
		this.currentMFile = newMFile;
		firePropertyChange("currentMFile", oldMFile, this.currentMFile);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this.pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	public String toString() {
		String str = "Type: " + getTypeName() + "\n";

		return str;
	}
}
