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
package gov.redhawk.ide.idl.generator.newidl;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 4.1
 */
public class GeneratorArgs {
	private List<String> idlFiles = new ArrayList<String>();
	private String interfaceName = "";
	private String interfaceVersion = "";
	private String projectName;
	private String softPkgFile;

	public List<String> getIdlFiles() {
	    return idlFiles;
    }

	public String getInterfaceName() {
	    return interfaceName;
    }

	public String getInterfaceVersion() {
	    return interfaceVersion;
    }

	public String getProjectName() {
		return this.projectName;
	}

	public String getSoftPkgFile() {
		return this.softPkgFile;
	}
	
	public void setIdlFiles(List<String> idlFiles) {
	    this.idlFiles = idlFiles;
    }
	
	public void setInterfaceName(String interfaceName) {
	    this.interfaceName = interfaceName;
    }
	
	public void setInterfaceVersion(String interfaceVersion) {
	    this.interfaceVersion = interfaceVersion;
    }

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public void setSoftPkgFile(final String softPkgFile) {
		this.softPkgFile = softPkgFile;
	}
}
