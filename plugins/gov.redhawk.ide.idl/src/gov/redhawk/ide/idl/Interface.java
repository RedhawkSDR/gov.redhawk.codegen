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

public class Interface implements Comparable<Interface> {
	private String name;
	private String nameSpace;
	private final String filename;
	private final String fullpath;
	private ArrayList<Attribute> attributes;
	private ArrayList<Operation> operations;

	public Interface(final String name, final String nameSpace, final String filename, final String fullpath) {

		this.setName(name);
		this.setNameSpace(nameSpace);
		this.filename = filename;
		this.fullpath = fullpath;
		this.setAttributes(new ArrayList<Attribute>());
		this.setOperations(new ArrayList<Operation>());
	}

	/**
	 * @since 4.0
	 */
	public void addAttr(final Attribute newattr) {
		this.getAttributes().add(newattr);
	}

	public void addOp(final Operation newop) {
		this.getOperations().add(newop);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNameSpace(final String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getNameSpace() {
		return this.nameSpace;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setOperations(final ArrayList<Operation> operations) {
		this.operations = operations;
	}

	public ArrayList<Operation> getOperations() {
		return this.operations;
	}

	/**
	 * @since 4.0
	 */
	public void setAttributes(final ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @since 4.0
	 */
	public ArrayList<Attribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Interface) {
			final Interface tmp_obj = (Interface) obj;
			return ((tmp_obj.getNameSpace().equals(this.nameSpace)) && (tmp_obj.getName().equals(this.name)));
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (this.name != null) {
			return this.nameSpace.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public int compareTo(final Interface o) {
		if (o == null) {
			return -1;
		}

		if (!this.nameSpace.equals(o.nameSpace)) {
			return this.nameSpace.compareTo(o.nameSpace);
		}

		return this.name.compareTo(o.name);
	}

	/**
	 * @return
	 * @since 2.0
	 */
	public String getFullPath() {
		return this.fullpath;
	}
}
