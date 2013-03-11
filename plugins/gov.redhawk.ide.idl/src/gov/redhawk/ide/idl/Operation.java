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

import java.util.ArrayList;
import java.util.List;

public class Operation {

	private String name;
	private String returnType;
	private String cxxReturnType;
	private boolean cxxReturnTypeVariableLength;
	private List<Param> params;
	private List<Raises> raises;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
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

	/**
	 * @since 2.0
	 */
	public List<Param> getParams() {
		return this.params;
	}

	/**
	 * @since 2.0
	 */
	public void setParams(final List<Param> params) {
		this.params = new ArrayList<Param>(params);
	}

	/**
	 * @since 3.0
	 */
	public List<Raises> getRaises() {
		return this.raises;
	}

	/**
	 * @since 3.0
	 */
	public void setRaises(final List<Raises> raises) {
		this.raises = new ArrayList<Raises>(raises);
	}

	/**
	 * @since 4.1
	 */
	public Operation(final String name, final String returnType, final String cxxReturnType, final Boolean cxxReturnTypeVariableLength) {
		this.name = name;
		this.returnType = returnType;
		this.cxxReturnType = cxxReturnType;
		this.cxxReturnTypeVariableLength = cxxReturnTypeVariableLength;
		this.params = new ArrayList<Param>();
		this.raises = new ArrayList<Raises>();
	}

	public Operation(final String name, final String returnType) {
		this.name = name;
		this.returnType = returnType;
		this.cxxReturnType = null;
		this.cxxReturnTypeVariableLength = false;
		this.params = new ArrayList<Param>();
		this.raises = new ArrayList<Raises>();
	}

	public Operation(final String name) {
		this.name = name;
		this.returnType = null;
		this.cxxReturnType = null;
		this.params = new ArrayList<Param>();
		this.raises = new ArrayList<Raises>();
	}

	public void addParam(final Param newparam) {
		this.params.add(newparam);
	}

	/**
	 * @since 3.0
	 */
	public void addRaise(final Raises newRaise) {
		this.raises.add(newRaise);
	}
}
