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
package gov.redhawk.ide.sharedlibrary.ui.tests;

import gov.redhawk.ide.swtbot.ProjectExplorerUtils;
import gov.redhawk.ide.swtbot.SharedLibraryUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UITest;
import gov.redhawk.ide.swtbot.diagram.DiagramTestUtils;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Shared Library projects should display many fewer options in the editor pages
 * since they do not have things such as a .prf.xml, .scd.xml, ports, and cannot be launched
 */
public class SharedLibraryEditorTest extends UITest {

	SWTBotEditor editor;

	@BeforeClass
	public static void beforeClassSetup() {
		// PyDev needs to be configured before running New SCA Shared Library Project Wizards
		StandardTestActions.configurePyDev();
	}

	/**
	 * IDE-1102 - Hide unnecessary fields in Shared Library project editors
	 */
	@Test
	public void sharedLibraryEditorTest() {
		final String projectName = "SharedLibraryTest";
		final String projectType = "C++ Library";

		SharedLibraryUtils.createSharedLibraryProject(bot, projectName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { projectName, projectName + ".spd.xml" });

		editor = bot.editorByTitle(projectName);
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.OVERVIEW_TAB);

		// List of sections that should not be in the Shared Library Overview tab
		String[] badSections = { "Ports", "Interfaces", "Component Content", "Testing", "PRF:", "SCD:" };

		for (String section : badSections) {
			try {
				bot.label(section);
				Assert.fail("Unapplicable section found: " + section);
			} catch (WidgetNotFoundException e) {
				continue;
			}
		}

		// Do same test for .prf section in the Implementations tab
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.IMPLEMENTATIONS);
		String section = "Property File:";
		try {
			bot.label(section);
			Assert.fail("Unapplicable section found: " + section);
		} catch (WidgetNotFoundException e) {
			// PASS - We expected the widget not to be found
		}
	}
}
