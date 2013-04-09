/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.idl.tests;

import gov.redhawk.ide.idl.generator.internal.MakefileAmTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A class to text {@link MakefileAmTemplate}
 */
public class MakefileAmTemplateTest {
	/**
	 * Test generating a Makefile.Am file
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// Generate Makefile.am content using the template
		final MakefileAmTemplate makefileTemplate = MakefileAmTemplate.create(null);
		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName("testProjectName");
		args.setInterfaceName("testInterfaceName");
		args.setInterfaceVersion("1.0.0");
		args.setIdlFiles(Arrays.asList(new String[] {
			"/var/tmp/some.idl"
		}));
		final String makefileContent = makefileTemplate.generate(args);
		Assert.assertNotNull(makefileContent);

		// Verify the contents of MakeFile.am
		Assert.assertTrue(makefileContent.contains("IDL_MODULE := $(redhawk_IDL_MODULE_auto)"));
		Assert.assertTrue(makefileContent.contains("IDL_FILES := $(redhawk_IDL_FILES_auto)"));
		Assert.assertTrue(makefileContent.contains("lib_LTLIBRARIES = libtestinterfacenameInterfaces.la"));
		Assert.assertTrue(makefileContent.contains("libtestinterfacenameInterfaces_la_SOURCES = $(redhawk_IDL_SOURCES_auto)"));
		Assert.assertTrue(makefileContent.contains("src/cpp/$(LIBRARY_NAME)/$(IDL_MODULE)/%SK.cpp"));
		Assert.assertTrue(makefileContent.contains("src/cpp/$(LIBRARY_NAME)/$(IDL_MODULE)/%DynSK.cpp"));
		Assert.assertTrue(makefileContent.contains("src/cpp/$(LIBRARY_NAME)/$(IDL_MODULE)/%.h"));

		// Create the Makefile.am file from the content
		final File makefileAmFile = TestUtils.createFile(makefileContent, ".am");
		Assert.assertNotNull(makefileAmFile);
		Assert.assertTrue(makefileAmFile.exists());
		Assert.assertTrue(makefileAmFile.getPath().endsWith(".am"));
	}
}
