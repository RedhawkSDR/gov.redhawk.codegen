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
import gov.redhawk.ide.swtbot.WaveformUtils;
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

	/**
	 * IDE-1141
	 * Make sure any project type that is dragged onto the Shared Libraries container installs to expected location
	 */
	@Test
	public void dndExportTest() {

		// Create and generate shared library project
		final String sharedLibName = "SharedLibExportTest";
		final String projectType = "C++ Library";
		SharedLibraryUtils.createSharedLibraryProject(bot, sharedLibName, projectType);
		ProjectExplorerUtils.openProjectInEditor(bot, new String[] { sharedLibName, sharedLibName + ".spd.xml" });
		SWTBotEditor editor = bot.editorByTitle(sharedLibName);
		StandardTestActions.generateProject(bot, editor);
		bot.closeAllEditors();

		// Create and generate component
		final String waveformName = "WaveformExportTest";
		WaveformUtils.createNewWaveform(bot, waveformName);
		bot.closeAllEditors();

		// Drag-and-drop both projects onto the Shared Libraries container
		// Confirm projects are installed into their correct locations
		// Clean up
		SWTBotTreeItem sharedLibContainer = ScaExplorerTestUtils.getTreeItemFromScaExplorer(bot, new String[] { "Target SDR" }, "Shared Libraries");

		SWTBotTreeItem sharedLibPrExpNode = ProjectExplorerUtils.selectNode(bot, sharedLibName);
		sharedLibPrExpNode.dragAndDrop(sharedLibContainer);
		final String[] sharedLibPath = { "Target SDR", "Shared Libraries" };
		SWTBotTreeItem sharedLibScaNode = ScaExplorerTestUtils.waitUntilNodeAppearsInScaExplorer(bot, sharedLibPath, sharedLibName);
		sharedLibScaNode.contextMenu("Delete").click();
		bot.button("Yes").click();
		ScaExplorerTestUtils.waitUntilNodeRemovedFromScaExplorer(bot, sharedLibPath, sharedLibName);

		SWTBotTreeItem waveformPrExpNode = ProjectExplorerUtils.selectNode(bot, waveformName);
		waveformPrExpNode.dragAndDrop(sharedLibContainer);
		final String[] waveformPath = { "Target SDR", "Waveforms" };
		SWTBotTreeItem waveformScaNode = ScaExplorerTestUtils.waitUntilNodeAppearsInScaExplorer(bot, waveformPath, waveformName);
		waveformScaNode.contextMenu("Delete").click();
		bot.button("Yes").click();
		ScaExplorerTestUtils.waitUntilNodeRemovedFromScaExplorer(bot, waveformPath, waveformName);

	}
}
