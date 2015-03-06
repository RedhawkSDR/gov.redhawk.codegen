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
import gov.redhawk.ide.swtbot.SoftpackageUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UITest;
import gov.redhawk.ide.swtbot.diagram.DiagramTestUtils;

import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
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
		// PyDev needs to be configured before running New SCA Softpackage Project Wizards
		StandardTestActions.configurePyDev();
	}

	/**
	 * IDE-1102 - Hide unnecessary fields in Softpackage project editors
	 */
	@Test
	public void softpackageEditorTest() {
		final String projectName = "SharedLibraryTest";
		final String projectType = "C++ Library";

		SoftpackageUtils.createSoftpackageProject(bot, projectName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { projectName, projectName + ".spd.xml" });

		editor = bot.editorByTitle(projectName);
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.OVERVIEW_TAB);

		// List of sections that should not be in the Softpackage Overview tab
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
			// PASS
		}
	}

	// TODO: Do we need to include Octave project type for this test?
	/**
	 * IDE-1117
	 * Softpackage (shared library) code generation test
	 * Currently only checks case where code generation is initiated from the editor toolbar button
	 */
	@Test
	public void softpackageGenerationTest() {
		final String projectName = "SharedLibraryTest";
		final String projectType = "C++ Library";
		final String[][] possiblePaths = new String[][] { { "cpp", "include", projectName + ".h" }, { "cpp", "src", projectName + ".cpp" },
			{ "cpp", "build.sh" }, { "cpp", "configure.ac" }, { "cpp", projectName + ".pc.in" }, { "cpp", "Makefile.am" }, { "cpp", "Makefile.am.ide" },
			{ "cpp", "reconf" }, { "tests", "test_" + projectName + ".py" }, { "build.sh" }, { projectName + ".spd.xml" }, { projectName + ".spec" } };

		SoftpackageUtils.createSoftpackageProject(bot, projectName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { projectName, projectName + ".spd.xml" });

		editor = bot.editorByTitle(projectName);
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.OVERVIEW_TAB);

		editor.bot().toolbarButton(0).click();
		bot.waitUntil(Conditions.shellIsActive("Regenerate Files"), 10000);
		SWTBotShell fileShell = bot.shell("Regenerate Files");
		fileShell.bot().button("OK").click();

		// Every possible path starts from the same root, in this case the projectName
		for (String[] possiblePath : possiblePaths) {
			final String[] path = new String[possiblePath.length + 1];
			path[0] = projectName;
			for (int i = 1; i < possiblePath.length + 1; i++) {
				path[i] = possiblePath[i - 1];
			}
			ProjectExplorerUtils.selectNode(bot, path);
		}

	}
}
