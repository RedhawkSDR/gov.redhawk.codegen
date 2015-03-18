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
package gov.redhawk.ide.sharedlibrary.ui.runtime.tests;

import gov.redhawk.ide.swtbot.ProjectExplorerUtils;
import gov.redhawk.ide.swtbot.SharedLibraryUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UIRuntimeTest;
import gov.redhawk.ide.swtbot.scaExplorer.ScaExplorerTestUtils;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

/**
 * Test exporting a Shared Library project to the Target SDR
 */
public class SharedLibraryExportTest extends UIRuntimeTest {

	/**
	 * IDE-1141
	 * Shared Library projects should be exported to the appropriately named container in the Target SDR
	 */
	@Test
	public void exportSharedLibraryTest() {
		final String projectName = "SharedLibExportTest";
		final String projectType = "C++ Library";
		final String[] scaExpPath = { "Target SDR", "Shared Libraries" };

		// Create and Generate
		SharedLibraryUtils.createSharedLibraryProject(bot, projectName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { projectName, projectName + ".spd.xml" });
		SWTBotEditor editor = bot.editorByTitle(projectName);
		StandardTestActions.generateProject(bot, editor);

		// Export to SDR
		SWTBotTreeItem projectNode = ProjectExplorerUtils.waitUntilNodeAppears(bot, projectName);
		projectNode.contextMenu("Export to SDR").click();

		// Confirm export worked
		SWTBotTreeItem scaNode = ScaExplorerTestUtils.waitUntilNodeAppearsInScaExplorer(bot, scaExpPath, projectName);

		// Delete project and make sure it is gone
		scaNode.contextMenu("Delete").click();
		bot.button("Yes").click();
		ScaExplorerTestUtils.waitUntilNodeRemovedFromScaExplorer(bot, scaExpPath, projectName);
	}
}
