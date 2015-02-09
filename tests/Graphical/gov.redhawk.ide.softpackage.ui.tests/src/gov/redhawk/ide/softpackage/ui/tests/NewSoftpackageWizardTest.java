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

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
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

	// TODO: Test case for creating octave softpackage projects

	@BeforeClass
	public static void beforeClassSetup() {
		// PyDev needs to be configured before running New SCA Softpackage Project Wizards
		StandardTestActions.configurePyDev();
	}

	/**
	 * IDE-1099
	 */
	@Test
	public void softpackageWizardTest() {
		final String defaultMessage = "Create a new Softpackage library";
		final String defaultErrorMessage = "Invalid character present in project name.";
		final String projectName = "SoftpackageTest";

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

	// TODO: Do we need to include Octave project type for this test?
	@Test
	public void softpackageGenerationTest() {
		final String projectName = "SoftpackageTest";
		final String projectType = "C++ Library";
		final String[][] possiblePaths = new String[][] { { "cpp", "include", projectName + ".h" }, { "cpp", "src", projectName + ".cpp" },
			{ "cpp", "build.sh" }, { "cpp", "configure.ac" }, { "cpp", projectName + ".pc.in" }, { "cpp", "Makefile.am" }, { "cpp", "Makefile.am.ide" },
			{ "cpp", "reconf" }, { "tests", "test_" + projectName + ".py" }, { "build.sh" }, { projectName + ".spd.xml" }, { projectName + ".spec" } };

		SoftpackageUtils.createSoftpackageProject(bot, projectName, projectType);

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
