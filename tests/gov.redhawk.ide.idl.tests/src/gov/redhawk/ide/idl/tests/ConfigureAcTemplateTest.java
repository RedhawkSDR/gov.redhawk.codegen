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

import gov.redhawk.ide.idl.generator.internal.ConfigureAcTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A class to text {@link ConfigureAcTemplate}
 */
public class ConfigureAcTemplateTest {
	/**
	 * Test generating a configure.ac file
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// Generate configure.ac content using the template
		final ConfigureAcTemplate configureAcTemplate = ConfigureAcTemplate.create(null);
		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName("testProjectName");
		args.setInterfaceName("testinterface");
		args.setInterfaceVersion("1.0.0");
		args.setIdlFiles(Arrays.asList(new String[] {
			"/var/tmp/some.idl"
		}));
		final String configureAcContent = configureAcTemplate.generate(args);
		Assert.assertNotNull(configureAcContent);

		// Verify the contents of configure.ac
		Assert.assertTrue(configureAcContent.contains("AC_INIT(testinterfaceInterfaces, 1.0.0"));

		// Create the configure.ac file from the content
		final File configureAcFile = TestUtils.createFile(configureAcContent, ".ac");
		Assert.assertNotNull(configureAcFile);
		Assert.assertTrue(configureAcFile.exists());
		Assert.assertTrue(configureAcFile.getPath().endsWith(".ac"));
	}
}
