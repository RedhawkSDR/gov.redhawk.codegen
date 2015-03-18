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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
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
	 * IDE-1142 - Update the dependency wizard when choosing a shared library
	 */
	@Test
	public void sharedLibraryEditorTest() {
		final String projectName = "SharedLibraryTest";
		final String projectType = "C++ Library";
		// IDE-1111: adding namespace to make sure it works
		final String namespacePrefix = "name.spaced.";

		SharedLibraryUtils.createSharedLibraryProject(bot, namespacePrefix + projectName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { namespacePrefix + projectName, projectName + ".spd.xml" });

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

		// Check that the dependency wizard for SoftPkg reference was updated to show only Shared Libs
		bot.button("Add...", 3).click();

		SWTBotCombo kindCombo = bot.comboBoxWithLabel("Kind:");
		kindCombo.setSelection("SoftPkg Reference");

		SWTBotCombo typeCombo = bot.comboBoxWithLabel("Type:");
		Assert.assertTrue("Only one type should be available for Kind: SoftPkg Reference", typeCombo.itemCount() == 1);
		typeCombo.setSelection(0);
		Assert.assertEquals("The only acceptable type for Kind: SoftPkg Reference is 'other'", "other", typeCombo.getText());

		SWTBotTree referenceTree = bot.treeInGroup("SoftPkg Reference");
		SWTBotTreeItem[] treeItems = referenceTree.getAllItems();
		Assert.assertTrue("There should only be one container node in the Reference tree (Shared Libraries)", treeItems.length == 1);
		Assert.assertEquals("Shared Libraries container should be in the Reference tree", "Shared Libraries", treeItems[0].getText());

	}
}
