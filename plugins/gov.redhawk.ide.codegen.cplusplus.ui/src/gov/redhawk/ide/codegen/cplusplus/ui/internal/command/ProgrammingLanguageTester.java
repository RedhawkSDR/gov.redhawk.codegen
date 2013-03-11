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
package gov.redhawk.ide.codegen.cplusplus.ui.internal.command;

import gov.redhawk.ide.codegen.CodegenUtil;
import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;

/**
 * 
 */
public class ProgrammingLanguageTester extends PropertyTester {

	public static final String ID = "gov.redhawk.ide.codegen.cplusplus.ui.command.ProgrammingLanguageTester";

	/**
	 * 
	 */
	public ProgrammingLanguageTester() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean test(final Object element, final String property, final Object[] args, final Object expectedValue) {
		if (!(element instanceof EObject || element instanceof FeatureMap.Entry || element instanceof IWrapperItemProvider)) {
			return false;
		}

		EObject object = null;
		if (element instanceof EObject) {
			object = (EObject) element;
		} else {
			final Object unwrapped = AdapterFactoryEditingDomain.unwrap(element);
			if (unwrapped instanceof EObject) {
				object = (EObject) unwrapped;
			}
		}

		boolean stat = false;
		if (object instanceof Implementation) {
			final Implementation impl = (Implementation) object;
			if (impl.getProgrammingLanguage() != null) {
				final String progLang = impl.getProgrammingLanguage().getName();
				if (args.length > 0) {
					for (final Object arg : args) {
						if (progLang.equals(arg.toString())) {
							stat = true;
							break;
						}
					}
				} else {
					stat = CodegenUtil.canPrimary(progLang);
				}
			}
		}

		return stat;
	}

}
