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
package gov.redhawk.ide.codegen.jet;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.runtime.IPath;

public class TemplateParameter {
	private Implementation impl;

	private ImplementationSettings implSettings;

	private List<IPath> searchPaths;

	private boolean isDevice;

	private String[] sourceFiles;

	private boolean genClassDef = true;

	private boolean genClassImpl = false;

	private boolean genSupport = true;

	private String portRepId = "";
	
	private boolean providesPort = false;
	
	private String portName = "";

	/**
	 * @since 3.1
	 */
	public TemplateParameter(final Implementation impl, final ImplementationSettings implSettings) {
		this(impl, implSettings, new ArrayList<IPath>(), true, true, true);
	}

	/**
     * @since 3.0
     */
	public TemplateParameter(final Implementation impl, final ImplementationSettings implSettings,
	        final boolean genSupport, final boolean genClassDef, final boolean genClassImpl) {
		this(impl, implSettings, new ArrayList<IPath>(), genSupport, genClassDef, genClassImpl);
	}

	/**
	 * @since 3.0
	 */
	public TemplateParameter(final Implementation impl, final ImplementationSettings implSettings, final List<IPath> searchPaths) {
		this(impl, implSettings, searchPaths, true, true, true);
	}
	
	/**
	 * @since 3.0
	 */
	public TemplateParameter(final Implementation impl, final ImplementationSettings implSettings, final List<IPath> searchPaths,
	        final boolean genSupport, final boolean genClassDef, final boolean genClassImpl) {
		this.impl = impl;
		this.implSettings = implSettings;
		this.searchPaths = searchPaths;
		this.isDevice = false;
		this.sourceFiles = new String[0];
		this.genClassDef = genClassDef;
		this.genSupport = genSupport;
	}

	public Implementation getImpl() {
		return this.impl;
	}

	/**
	 * @since 2.3
	 */
	public SoftPkg getSoftPkg() {
		return (SoftPkg) this.impl.eContainer();
	}

	public void setImpl(final Implementation impl) {
		this.impl = impl;
	}

	public ImplementationSettings getImplSettings() {
		return this.implSettings;
	}

	/**
	 * @since 2.3
	 */
	public Map<String, String> getImplSettingsProperties() {
		final Map<String, String> result = new HashMap<String, String>();
		if (this.implSettings != null) {
			for (final Property prop : this.implSettings.getProperties()) {
				result.put(prop.getId(), prop.getValue());
			}
		}

		return result;
	}

	/**
	 * @since 2.1
	 */
	public void setImplSettings(final ImplementationSettings implSettings) {
		this.implSettings = implSettings;
	}

	/**
	 * @since 3.0
	 */
	public List<IPath> getSearchPaths() {
		return this.searchPaths;
	}

	/**
	 * @since 3.0
	 */
	public void setSearchPaths(final List<IPath> searchPaths) {
		this.searchPaths = searchPaths;
	}

	/**
	 * This returns true if this represents a device.
	 * 
	 * @return true if this represents a device
	 * @since 3.0
	 */
	public boolean isDevice() {
		return this.isDevice;
	}

	/**
	 * This is used to indicate if this template represents a device.
	 * 
	 * @param isDevice true if this is a device
	 * @since 3.0
	 */
	public void setDevice(final boolean isDevice) {
		this.isDevice = isDevice;
	}

	/**
	 * @since 3.0
	 */
	public String[] getSourceFiles() {
		return this.sourceFiles;
	}

	/**
	 * @since 3.0
	 */
	public void setSourceFiles(final String[] sourceFiles) {
		this.sourceFiles = sourceFiles;
	}
	
	/**
	 * @since 3.0
	 */
	public boolean isGenClassDef() {
		return this.genClassDef;
	}

	/**
	 * @since 3.0
	 */
	public void setGenClassDef(final boolean genClassDef) {
		this.genClassDef = genClassDef;
	}

	/**
     * @since 3.0
     */
	public void setGenClassImpl(boolean genClassImpl) {
	    this.genClassImpl = genClassImpl;
    }

	/**
     * @since 3.0
     */
	public boolean isGenClassImpl() {
	    return genClassImpl;
    }

	/**
	 * @since 3.0
	 */
	public boolean isGenSupport() {
		return this.genSupport;
	}

	/**
	 * @since 3.0
	 */
	public void setGenSupport(final boolean genSupport) {
		this.genSupport = genSupport;
	}

	/**
     * @since 3.0
     */
	public void setPortRepId(String portRep) {
	    this.portRepId = portRep;
    }

	/**
     * @since 3.0
     */
	public String getPortRepId() {
	    return portRepId;
    }

	/**
     * @since 3.1
     */
	public void setPortName(String portName) {
	    this.portName = portName;
    }

	/**
     * @since 3.1
     */
	public String getPortName() {
	    return this.portName;
    }

	/**
     * @since 3.0
     */
	public void setProvidesPort(boolean providesPort) {
	    this.providesPort = providesPort;
    }

	/**
     * @since 3.0
     */
	public boolean isProvidesPort() {
	    return providesPort;
    }

}
