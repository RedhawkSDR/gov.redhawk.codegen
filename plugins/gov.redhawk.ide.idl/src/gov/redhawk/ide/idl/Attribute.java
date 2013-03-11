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

/**
 * @since 4.0
 */
public class Attribute {

	private String name;
	private boolean readonly;
	private String dataType;
	private String returnType;
	private String cxxReturnType;
	private boolean cxxReturnTypeVariableLength;
	private String cxxType;

	/**
	 * @since 4.1
	 */
	public Attribute(final String name, final boolean readonly, final String dataType, final String returnType, final String cxxReturnType, final String cxxType, final boolean cxxReturnTypeVariableLength) {
		this.name = name;
		this.readonly = readonly;
		this.dataType = dataType;
		this.returnType = returnType;
		this.cxxReturnType = cxxReturnType;
		this.cxxReturnTypeVariableLength = cxxReturnTypeVariableLength;
		this.cxxType = cxxType;
	}

	public Attribute(final String name, final boolean readonly, final String dataType, final String returnType) {
		this.name = name;
		this.readonly = readonly;
		this.dataType = dataType;
		this.returnType = returnType;
		this.cxxReturnType = null;
		this.cxxReturnTypeVariableLength = false;
		this.cxxType = null;
	}

	public Attribute(final String name, final boolean readonly) {
		this.name = name;
		this.readonly = readonly;
		this.dataType = null;
		this.returnType = null;
		this.cxxReturnType = null;
		this.cxxReturnTypeVariableLength = false;
		this.cxxType = null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public void setReadonly(final boolean readonly) {
		this.readonly = readonly;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(final String dataType) {
		this.dataType = dataType;
	}

	public String getReturnType() {
		return this.returnType;
	}

	public void setReturnType(final String returnType) {
		this.returnType = returnType;
	}

	public String getCxxReturnType() {
		return this.cxxReturnType;
	}

	public void setCxxReturnType(final String cxxReturnType) {
		this.cxxReturnType = cxxReturnType;
	}

	/**
	 * @since 4.1
	 */
	public boolean isCxxReturnTypeVariableLength() {
		return cxxReturnTypeVariableLength;
	}

	/**
	 * @since 4.1
	 */
	public void setCxxReturnTypeVariableLength(boolean cxxReturnTypeVariableLength) {
		this.cxxReturnTypeVariableLength = cxxReturnTypeVariableLength;
	}

	public String getCxxType() {
		return this.cxxType;
	}

	public void setCxxType(final String cxxType) {
		this.cxxType = cxxType;
	}
}
