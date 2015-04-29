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

import gov.redhawk.ide.sharedlibrary.ui.wizard.models.SharedLibraryModel;
import gov.redhawk.ide.swtbot.ProjectExplorerUtils;
import gov.redhawk.ide.swtbot.StandardTestActions;
import gov.redhawk.ide.swtbot.UITest;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the ability to create a new shared library project using various types
 */
public class NewSharedLibraryWizardTest extends UITest {

	private String errorMessage;

	@BeforeClass
	public static void beforeClassSetup() {
		// PyDev needs to be configured before running New SCA Shared Libary Project Wizards
		StandardTestActions.configurePyDev(new SWTWorkbenchBot());
	}

	/**
	 * IDE-1099
	 * Test case for creating cpp shared library projects
	 */
	@Test
	public void sharedLibraryCppWizardTest() {
		final String defaultMessage = "Create a new Shared Library";
		final String defaultErrorMessage = "Invalid character present in project name.";
		final String projectName = "SharedLibraryCppTest";

		// Open the new shared library project wizard
		SWTBotMenu fileMenu = bot.menu("File");
		SWTBotMenu newMenu = fileMenu.menu("New");
		SWTBotMenu otherMenu = newMenu.menu("Other...");
		otherMenu.click();
		SWTBotShell wizardShell = bot.shell("New");
		SWTBot wizardBot = wizardShell.bot();
		wizardShell.activate();
		wizardBot.tree().getTreeItem("SCA").expand().getNode("SCA Shared Library Project").select();
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

		nameText.selectAll().typeText(projectName);
		Assert.assertTrue("Default message not displayed as expected.  Expected: " + defaultMessage + ", Actual: " + getErrorMessage(wizardShell),
			defaultMessage.equals(getErrorMessage(wizardShell)));
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		SWTBotCombo typeCombo = wizardBot.comboBoxWithLabel("Type:");
		typeCombo.setSelection(SharedLibraryModel.CPP_TYPE);
		Assert.assertTrue("Finish Button should be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		finishButton.click();

		// Confirm project appears in the project explorer
		ProjectExplorerUtils.selectNode(bot, projectName);
	}

	/**
	 * IDE-1009
	 * Test case for creating octave shared library projects
	 */
	@Test
	public void sharedLibraryOctaveWizardTest() {
		final String projectName = "SharedLibraryOctaveTest";

		// Open the new shared library project wizard
		SWTBotMenu fileMenu = bot.menu("File");
		SWTBotMenu newMenu = fileMenu.menu("New");
		SWTBotMenu otherMenu = newMenu.menu("Other...");
		otherMenu.click();
		SWTBotShell wizardShell = bot.shell("New");
		SWTBot wizardBot = wizardShell.bot();
		wizardShell.activate();
		wizardBot.tree().getTreeItem("SCA").expand().getNode("SCA Shared Library Project").select();
		wizardBot.button("Next >").click();

		// Get all widgets
		SWTBotButton finishButton = wizardBot.button("Finish");
		SWTBotButton nextButton = wizardBot.button("Next >");
		SWTBotButton browseButton = wizardBot.button("Browse");
		SWTBotButton addButton = wizardBot.button("Add");
		SWTBotButton removeButton = wizardBot.button("Remove");
		SWTBotText mFileText = wizardBot.textInGroup("Contents");
		SWTBotList mFileList = wizardBot.listInGroup("Contents");

		SWTBotText nameText = wizardBot.textWithLabel("Project name:");
		nameText.selectAll().typeText(projectName);

		// Validate that mFile widgets are only enabled for octave type
		SWTBotCombo typeCombo = wizardBot.comboBoxWithLabel("Type:");
		typeCombo.setSelection(SharedLibraryModel.CPP_TYPE);
		Assert.assertFalse("M-File text box should not be enabled", mFileText.isEnabled());
		Assert.assertFalse("Browse button should not be enabled", browseButton.isEnabled());
		Assert.assertFalse("Add button should not be enabled", addButton.isEnabled());
		Assert.assertFalse("Remove button should not be enabled", removeButton.isEnabled());

		typeCombo.setSelection(SharedLibraryModel.OCTAVE_TYPE);
		Assert.assertTrue("M-File text box should be enabled", mFileText.isEnabled());
		Assert.assertTrue("Browse button should be enabled", browseButton.isEnabled());
		Assert.assertTrue("Add button should be enabled", addButton.isEnabled());
		Assert.assertTrue("Remove button should be enabled", removeButton.isEnabled());
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());
		Assert.assertFalse("Next Button should not be enabled", nextButton.isEnabled());

		mFileText.selectAll().typeText("qwerty");
		addButton.click();
		Assert.assertEquals("User should not be able to add garbage paths", mFileList.itemCount(), 0);

		mFileText.selectAll().typeText("/var/redhawk");
		addButton.click();
		Assert.assertEquals("User should not be able to add directories", mFileList.itemCount(), 0);

		mFileText.selectAll().typeText("/var/redhawk/sdr/dom/components/SigGen/SigGen.spd.xml");
		addButton.click();
		Assert.assertEquals("File was not added to the list", mFileList.itemCount(), 1);
		Assert.assertTrue("Finish Button should be enabled", finishButton.isEnabled());

		mFileList.select(0);
		removeButton.click();
		Assert.assertEquals("File was not removed from the list", mFileList.itemCount(), 0);
		Assert.assertFalse("Finish Button should not be enabled", finishButton.isEnabled());

		mFileText.selectAll().typeText("/var/redhawk/sdr/dom/components/SigGen/SigGen.spd.xml");
		addButton.click();
		Assert.assertEquals("File was not added to the list", mFileList.itemCount(), 1);
		Assert.assertTrue("Finish Button should be enabled", finishButton.isEnabled());

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
