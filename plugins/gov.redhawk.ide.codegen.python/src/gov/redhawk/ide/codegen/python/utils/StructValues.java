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
package gov.redhawk.ide.codegen.python.utils;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.prf.PropertyValueType;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleRef;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.prf.StructValue;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Provides convenient access to a StructSequence's Struct Values.
 * @since 5.0
 */
public class StructValues {

	private final StructSequence structSequence;
	private final List<List<Simple>> myValues = new ArrayList<List<Simple>>();

	public StructValues(final StructSequence structSequence) {
		this.structSequence = structSequence;
		for (final StructValue value : structSequence.getStructValue()) {
			final List<Simple> simpleValues = new ArrayList<Simple>();
			for (final Simple simple : structSequence.getStruct().getSimple()) {
				final Simple mySimple = EcoreUtil.copy(simple);
				//Lookup the ref
				boolean found = false;
				for (final SimpleRef ref : value.getSimpleRef()) {
					if (ref.getRefID().equals(simple.getId())) {
						//Add the value and stop looking
						mySimple.setValue(ref.getValue());
						simpleValues.add(mySimple);
						found = true;
						break;
					}
				}
				if (!found) {
					mySimple.setValue(getDefaultValue(mySimple.getType()));
					simpleValues.add(mySimple);
				}
			}
			this.myValues.add(simpleValues);
		}
	}

	/**
	 * Returns the List of List of Simples that comprise this StructSequences StructValues.
	 * @return
	 */
	public List<List<Simple>> getValues() {
		return this.myValues;
	}

	/**
	 * Returns the StructSequence used to create this object.
	 * 
	 * @return the StructSequence used to create this StructValues
	 */
	public StructSequence getStructSequence() {
		return this.structSequence;
	}

	/**
	 * Return the default value given a property value type.
	 * 
	 * @param type the PropertyValueType to get the default value for
	 * @return the String representation of the default value.
	 */
	private String getDefaultValue(final PropertyValueType type) {
		//This is different that PropertyToPython;  we're setting simple values, not python values
		//Is this duplicated somewhere else?  Is it language specific?  Might remove later.
		switch (type) {
		case CHAR:
		case OBJREF:
		case STRING:
			return "";
		case BOOLEAN:
			return Boolean.toString(false);
		default:
			return Integer.toString(0);
		}
	}
}
