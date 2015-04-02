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
package gov.redhawk.ide.codegen.runtime.tests;

import gov.redhawk.ide.swtbot.ComponentUtils;
import gov.redhawk.ide.swtbot.MenuUtils;
import gov.redhawk.ide.swtbot.ProjectExplorerUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UITest;
import gov.redhawk.ide.swtbot.diagram.DiagramTestUtils;

import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodeGeneratorVersionTest extends UITest {

	private final String compName = "CodegenVersionTest";
	private final String compSpd = compName + ".spd.xml";
	private final String compLanguage = "Python";
	private final String defaultTypeText = "sca_compliant";
	private final String expectedVersion = "2.0.0";
	private SWTBotEditor editor;
	private SWTBotText typeText;

	@BeforeClass
	public static void beforeClassSetup() {
		StandardTestActions.configurePyDev();
	}

	@Test
	public void generatorVersionTest() {

		ComponentUtils.createComponentProject(bot, compName, compLanguage);
		ProjectExplorerUtils.openProjectInEditor(bot, compName, compSpd);

		typeText = bot.textWithLabel("Type:");
		Assert.assertEquals("Type is not set to default value", defaultTypeText, typeText.getText());

		// Generate using toolbar button
		editor = bot.editorByTitle(compName);
		StandardTestActions.generateProject(bot, editor);
		assertCodegenVersionUpdated();
		resetTypeToDefault();

		// Generate using context menu on Implementations page
		openProjectAndConfirmDefault();
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.IMPLEMENTATIONS);
		SWTBotTreeItem implTreeItem = editor.bot().tree().getAllItems()[0].select();
		implTreeItem.contextMenu("Generate Component").click();
		completeGenerateWizard();
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.OVERVIEW_TAB);
		assertCodegenVersionUpdated();
		resetTypeToDefault();

		// Generate using Project > Generate Component menu
		openProjectAndConfirmDefault();
		bot.menu("Project").menu("Generate Component").click();
		completeGenerateWizard();
		assertCodegenVersionUpdated();
		resetTypeToDefault();

		// Generate using Project Explorer context menu
		openProjectAndConfirmDefault();
		SWTBotTreeItem spdNode = ProjectExplorerUtils.selectNode(bot, compName, compSpd);
		spdNode.select().contextMenu("Generate Component").click();
		completeGenerateWizard();
		assertCodegenVersionUpdated();
		resetTypeToDefault();

		// Close the editor and try this way of generating again
		openProjectAndConfirmDefault();
		bot.closeAllEditors();
		spdNode.select().contextMenu("Generate Component").click();
		completeGenerateWizard();
		ProjectExplorerUtils.openProjectInEditor(bot, compName, compSpd);
		editor = bot.editorByTitle(compName);
		assertCodegenVersionUpdated();
	}

	private void openProjectAndConfirmDefault() {
		ProjectExplorerUtils.openProjectInEditor(bot, compName, compSpd);
		editor = bot.editorByTitle(compName);
		DiagramTestUtils.openTabInEditor(editor, DiagramTestUtils.OVERVIEW_TAB);
		typeText = bot.textWithLabel("Type:");
		Assert.assertEquals("Type was not reset to default value", defaultTypeText, typeText.getText());
	}

	private void completeGenerateWizard() {
		bot.waitUntil(Conditions.shellIsActive("Regenerate Files"), 10000);
		SWTBotShell fileShell = bot.shell("Regenerate Files");

		fileShell.bot().button("OK").click();

		try {
			SWTBotShell genShell = bot.shell("Generating...");
			bot.waitUntil(Conditions.shellCloses(genShell));
		} catch (WidgetNotFoundException e) {
			// PASS
			// Sometimes the Generating shell closes so fast that SWTBot misses it
		}
	}

	private void assertCodegenVersionUpdated() {
		editor.show();
		typeText = bot.textWithLabel("Type:");
		Assert.assertEquals("Generator version type was not updated", expectedVersion, typeText.getText());
		DiagramTestUtils.openTabInEditor(editor, compSpd);
		String editorText = editor.bot().styledText().getText();
		final String versionXmlString = "(?s).* type=\"" + expectedVersion + "\">" + ".*";
		Assert.assertTrue("The spd.xml text does not reflect the updated code generator version", editorText.matches(versionXmlString));
	}

	private void resetTypeToDefault() {
		String editorText = editor.bot().styledText().getText();
		editorText = editorText.replace(expectedVersion, defaultTypeText);
		editor.bot().styledText().setText(editorText);
		MenuUtils.save(editor);
		bot.closeAllEditors();

		// TODO: This avoids a weird issue where the generator button becomes disabled.
		// Have not been able to reproduce manually
		ProjectExplorerUtils.selectNode(bot, compName, compSpd).contextMenu("Refresh").click();
	}
}
