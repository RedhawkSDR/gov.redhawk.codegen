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

import gov.redhawk.ide.idl.generator.internal.ReconfTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A class to test {@link ReconfTemplate}
 */
public class ReconfTemplateTest {
	/**
	 * Test generating a reconf file
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// Generate reconf content using the template
		final ReconfTemplate reconfTemplate = ReconfTemplate.create(null);
		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName("testProjectName");
		final String reconfContent = reconfTemplate.generate(args);
		Assert.assertNotNull(reconfContent);

		// Create the reconf file from the content
		final File reconfFile = TestUtils.createFile(reconfContent, "");
		Assert.assertNotNull(reconfFile);
		Assert.assertTrue(reconfFile.exists());
	}
}
