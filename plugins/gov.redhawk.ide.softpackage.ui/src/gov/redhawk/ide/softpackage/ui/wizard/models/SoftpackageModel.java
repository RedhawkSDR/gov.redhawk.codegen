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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

public class SoftpackageModel {
	public static final String[] TYPES = {"cpp", "directory", "octave"};
	public static final String[] HEADER_FILE_EXTENSION_FILTERS = {"*.h;*.hh", "*.*"};
	public static final String[] HEADER_FILE_EXTENSION_FILTER_NAMES = {"Header Files (*.h, *.hh)", "All Files"};
	public static final String[] PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTERS = {"*.pc", "*.*"};
	public static final String[] PACKAGE_CONFIGURATION_FILE_EXTENSION_FILTER_NAMES = {"Package Configuration Files (*.pc)", "All Files"};
	
	public static final String TYPE_NAME = "typeName";
	public static final String IMPL_NAME = "implName"; 
	public static final String ENABLED_CPP_OPTIONS = "enabledCppOptions";
	public static final String ENABLED_CREATE_OPTIONS = "enabledCreateLibraryOptions";
	public static final String ENABLED_USE_EXISTING_OPTIONS = "enabledUseExistingLibraryOptions"; 
	public static final String COMPILER_FLAGS = "compilerFlags";
	public static final String LINKER_FLAGS = "linkerFlags"; 
	public static final String PACKAGE_CONFIGURATION_PATH = "packageConfiguration";


	private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private boolean enabledCppOptions;
	private boolean enabledCreateOptions;
	private boolean enabledUseExistingOptions;

	private String typeName;
	private String implName;
	private String compilerFlags;
	private String linkerFlags;
	private String packageConfigurationPath;

	private final List<String> libraryPaths = new ArrayList<String>();
	private final List<String> headerPaths = new ArrayList<String>();
	private final transient WritableList headerPathsList = new WritableList(headerPaths, String.class);
	private final transient WritableList libraryPathsList = new WritableList(libraryPaths, String.class);

	public SoftpackageModel(boolean createNewLibrary) {
		setEnabledCreateLibraryOptions(createNewLibrary);
		setEnabledUseExistingLibraryOptions(!createNewLibrary);
	}

	public SoftpackageModel() { 
		setTypeName("cpp");
	}

	/**
	 * copy constructor
	 * @param model
	 */
	public SoftpackageModel(SoftpackageModel model) {
		this.typeName = model.getTypeName();
		this.implName = model.getImplName();
		this.enabledCppOptions = model.isEnabledCppOptions();
		this.enabledCreateOptions = model.isEnabledCreateLibraryOptions();
		this.enabledUseExistingOptions = model.isEnabledUseExistingLibraryOptions();
		this.compilerFlags = model.getCompilerFlags();
		this.libraryPaths.addAll(model.getLibraries());
		this.headerPaths.addAll(model.getHeaders());
		this.packageConfigurationPath = model.getPackageConfiguration();
		this.linkerFlags = model.getLinkerFlags();
	}

	public String getImplName() {
		return implName;
	}

	public void setImplName(String newValue) {
		final String oldValue = this.typeName;
		this.implName = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, IMPL_NAME, oldValue, newValue));
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String newValue) {
		final String oldValue = this.typeName;
		this.typeName = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, TYPE_NAME, oldValue, newValue));
		setEnabledCppOptions("cpp".equals(typeName));
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public boolean isEnabledCreateLibraryOptions() {
		return enabledCreateOptions;
	}

	public void setEnabledCreateLibraryOptions(boolean newValue) {
		final boolean oldValue = this.enabledCreateOptions;
		this.enabledCreateOptions = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, ENABLED_CREATE_OPTIONS, oldValue, newValue));
		if (this.enabledUseExistingOptions == this.enabledCreateOptions) {
			setEnabledUseExistingLibraryOptions(!this.enabledUseExistingOptions);
		}
	}

	public boolean isEnabledCppOptions() {
		return enabledCppOptions;
	}

	public void setEnabledCppOptions(boolean newValue) {
		final boolean oldValue = this.enabledCppOptions;
		this.enabledCppOptions = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, ENABLED_CPP_OPTIONS, oldValue, newValue));
	}

	public String getCompilerFlags() { 
		return compilerFlags; 
	}

	public void setCompilerFlags(String newValue) { 
		final String oldValue = this.compilerFlags;
		this.compilerFlags = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, COMPILER_FLAGS, oldValue, newValue)); 
	}

	public String getLinkerFlags() { 
		return linkerFlags; 
	}

	public void setLinkerFlags(String newValue) { 
		final String oldValue = this.linkerFlags;
		this.linkerFlags = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, LINKER_FLAGS, oldValue, newValue)); 
	}
	public boolean isEnabledUseExistingLibraryOptions() {
		return enabledUseExistingOptions;
	}

	public void setEnabledUseExistingLibraryOptions(boolean newValue) {
		final boolean oldValue = this.enabledUseExistingOptions;
		this.enabledUseExistingOptions = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, ENABLED_USE_EXISTING_OPTIONS, oldValue, newValue));
		if (this.enabledCreateOptions == this.enabledUseExistingOptions) {
			setEnabledCreateLibraryOptions(!this.enabledCreateOptions);
		}
	}

	public List<String> getLibraries() {
		return libraryPaths;
	}
	public IObservableList getLibrariesList() {
		return libraryPathsList;
	}

	public List<String> getHeaders() {
		return headerPaths;
	}
	
	public IObservableList getHeadersList() {
		return headerPathsList;
	}

	public String getPackageConfiguration() {
		return packageConfigurationPath;
	}

	public void setPackageConfiguration(String newValue) {
		final String oldValue = packageConfigurationPath;
		this.packageConfigurationPath = newValue;
		this.pcs.firePropertyChange(new PropertyChangeEvent(this, PACKAGE_CONFIGURATION_PATH, oldValue, newValue));
	}

	public String toString() {
		String str = "Type: " + getTypeName() + "\n" 
				+ "Implementation: " + valueOrNone(getImplName()) + "\n";
		
		if (isEnabledCreateLibraryOptions()) {
			if (isEnabledCppOptions()) {
				str += "Compiler flags: " + valueOrNone(getCompilerFlags()) + "\n"
					+ "Linker flags: " + valueOrNone(getLinkerFlags()) + "\n";
			}
		} 
		
		if (isEnabledUseExistingLibraryOptions()) {
			str += "Library(s): " + (getLibraries().isEmpty() ? "none \n" : "\n");
			for (String file : getLibraries()) {
				str += "\t" + file + "\n";
			}
			if (isEnabledCppOptions()) {
				str += "Header(s): " + (getHeaders().isEmpty() ? "none \n" : "\n");
				for (String file : getHeaders()) {
					str += "\t" + file + "\n";
				}
				str += "Package Configuration: " + valueOrNone(getPackageConfiguration()) + "\n";
			}
		}
		return str;
	}
	
	/** 
	 * @param str
	 * @return the value of str or "none" if string is empty
	 */
	private static String valueOrNone(String str) {
		return str.isEmpty() ? "none" : str;
	}
}

