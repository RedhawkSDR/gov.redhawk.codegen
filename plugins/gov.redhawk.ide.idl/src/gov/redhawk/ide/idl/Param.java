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
package gov.redhawk.ide.idl;

public class Param {
	private String name;
	private String dataType;
	private String cxxType;
	private String direction;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(final String dataType) {
		this.dataType = dataType;
	}

	public String getCxxType() {
		return this.cxxType;
	}

	public void setCxxType(final String cxxType) {
		this.cxxType = cxxType;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(final String direction) {
		this.direction = direction;
	}

	public Param(final String name, final String dataType, final String cxxType, final String direction) {
		this.name = name;
		this.dataType = dataType;
		this.cxxType = cxxType;
		this.direction = direction;
	}

	public Param(final String name, final String direction) {
		this.name = name;
		this.dataType = null;
		this.cxxType = null;
		this.direction = direction;
	}
}
