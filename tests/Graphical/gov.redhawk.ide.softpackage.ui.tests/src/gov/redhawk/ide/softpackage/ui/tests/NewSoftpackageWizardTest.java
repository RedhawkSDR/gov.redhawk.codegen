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

import gov.redhawk.ide.swtbot.ProjectExplorerUtils;
import gov.redhawk.ide.swtbot.SoftpackageUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UITest;
import gov.redhawk.ide.swtbot.WaitForEditorCondition;
import gov.redhawk.ide.swtbot.diagram.DiagramTestUtils;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the ability to create a new softpackage project using various types
 */
public class NewSoftpackageWizardTest extends UITest {

	private String errorMessage;

	@BeforeClass
	public static void beforeClassSetup() {
		// PyDev needs to be configured before running New SCA Softpackage Project Wizards
		StandardTestActions.configurePyDev();
	}

	// TODO: Test case for creating octave softpackage projects
	/**
	 * IDE-1099
	 */
	@Test
	public void softpackageWizardTest() {
		final String defaultMessage = "Create a new Softpackage library";
		final String defaultErrorMessage = "Invalid character present in project name.";
		final String projectName = "SharedLibraryTest";

		// Open the new softpackage project wizard
		SWTBotMenu fileMenu = bot.menu("File");
		SWTBotMenu newMenu = fileMenu.menu("New");
		SWTBotMenu otherMenu = newMenu.menu("Other...");
		otherMenu.click();
		SWTBotShell wizardShell = bot.shell("New");
		SWTBot wizardBot = wizardShell.bot();
		wizardShell.activate();
		wizardBot.tree().getTreeItem("SCA").expand().getNode("SCA Softpackage Project").select();
		wizardBot.button("Next >").click();

		SWTBotButton finishButton = wizardBot.button("Finish");
		SWTBotButton nextButton = wizardBot.button("Next >");
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		// Validate project name field
		SWTBotText nameText = wizardBot.textWithLabel("Project name:");
		nameText.selectAll().typeText("Not Valid");
		Assert.assertTrue("Error message not displayed as expected.  Expected: " + defaultErrorMessage + ", Actual: " + getErrorMessage(wizardShell),
			defaultErrorMessage.equals(getErrorMessage(wizardShell)));
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		nameText.selectAll().typeText("Not!Valid");
		Assert.assertTrue("Error message not displayed as expected.  Expected: " + defaultErrorMessage + ", Actual: " + getErrorMessage(wizardShell),
			defaultErrorMessage.equals(getErrorMessage(wizardShell)));
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		nameText.selectAll().typeText("SoftpackageTest");
		Assert.assertTrue("Default message not displayed as expected.  Expected: " + defaultMessage + ", Actual: " + getErrorMessage(wizardShell),
			defaultMessage.equals(getErrorMessage(wizardShell)));
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		SWTBotCombo typeCombo = wizardBot.comboBoxWithLabel("Type:");
		typeCombo.setSelection("C++ Library");
		Assert.assertTrue("Finish Button should be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		finishButton.click();

		// Confirm project appears in the project explorer
		ProjectExplorerUtils.selectNode(bot, projectName);
	}

	// Have to do some fancy dancing to get the dialog error message
	private String getErrorMessage(final SWTBotShell shell) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				Shell w = shell.widget;
				WizardDialog dialog = (WizardDialog) w.getData();
				setErrorMessage(dialog.getMessage());
			}
		});

		return this.errorMessage;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
