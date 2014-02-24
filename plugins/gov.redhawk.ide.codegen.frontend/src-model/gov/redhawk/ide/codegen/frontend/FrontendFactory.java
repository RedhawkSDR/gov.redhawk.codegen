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
// BEGIN GENERATED CODE
package gov.redhawk.ide.codegen.frontend;

import gov.redhawk.eclipsecorba.idl.Definition;

import gov.redhawk.model.sca.ScaStructProperty;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see gov.redhawk.ide.codegen.frontend.FrontendPackage
 * @generated
 */
public interface FrontendFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FrontendFactory eINSTANCE = gov.redhawk.ide.codegen.frontend.impl.FrontendFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Fei Device</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fei Device</em>'.
	 * @generated
	 */
	FeiDevice createFeiDevice();

	/**
	 * Returns an instance of data type '<em>Tuner Status Struct</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	ScaStructProperty createTunerStatusStruct(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>Tuner Status Struct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertTunerStatusStruct(ScaStructProperty instanceValue);

	/**
	 * Returns an instance of data type '<em>IDL Def</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	Definition createIDLDef(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>IDL Def</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertIDLDef(Definition instanceValue);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FrontendPackage getFrontendPackage();

} // FrontendFactory
