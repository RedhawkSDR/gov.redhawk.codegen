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

import gov.redhawk.ide.idl.generator.internal.BuildShTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;

import org.junit.Test;

/**
 * A class to test {@link BuildShTemplate}
 */
public class BuildShTemplateTest {
	/**
	 * Test generating a build.sh file
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// Generate build.sh content using the template
		final BuildShTemplate buildShTemplate = BuildShTemplate.create(null);
		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName("testProjectName");
		final String buildShContent = buildShTemplate.generate(args);
		Assert.assertNotNull(buildShContent);

		// Create the build.sh file from the content
		final File buildShFile = TestUtils.createFile(buildShContent, ".sh");
		Assert.assertNotNull(buildShFile);
		Assert.assertTrue(buildShFile.exists());
		Assert.assertTrue(buildShFile.getPath().endsWith(".sh"));
	}
}
