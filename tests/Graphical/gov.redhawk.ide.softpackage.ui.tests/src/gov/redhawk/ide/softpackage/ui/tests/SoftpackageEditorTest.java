/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.softpackage.ui.tests;

import org.junit.Test;

/**
 * Softpackage projects should display many fewer options in the editor pages
 * since they do not have things such as a .prf.xml, .scd.xml, ports, and cannot be launched
 */
public class SoftpackageEditorTest {

	// TODO: Test case for making sure .prf & .scd options do not appear in the Overview or Implementations tab
		// also test to make sure the fields for Ports, Interfaces, Testing, and Launching are not present in the Overview tab
	
	/**
	 * 
	 */
	@Test
	public void softpackageEditorTest() {
		// TODO: use utility method to create a softpackage project
		// TODO: open the .spd.xml and navigate to the overview tab, check that the following are missing:
			// Left Column: .prf and .scd text fields
			// Right Column: sections for Ports, Interfaces, Testing, and Launching
		// TODO: go to the implementation tab and confirm that the .prf option is removed
	}

}
