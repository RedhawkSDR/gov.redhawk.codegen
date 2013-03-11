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

import gov.redhawk.ide.idl.generator.internal.IdlSpecTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A class to text {@link IdlSpecTemplate}
 */
public class IdlSpecTemplateTest {
	/**
	 * Test generating a idl spec file
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// Generate idl spec content using the template
		final IdlSpecTemplate idlSpecTemplate = IdlSpecTemplate.create(null);
		final GeneratorArgs args = new GeneratorArgs();
		args.setProjectName("testProjectName");
		args.setInterfaceName("testInterface");
		args.setInterfaceVersion("1.0.0");
		args.setIdlFiles(Arrays.asList(new String[] {
			"/var/tmp/some.idl"
		}));
		final String idlSpecContent = idlSpecTemplate.generate(args);
		Assert.assertNotNull(idlSpecContent);

		// Test the content of the idl spec
		Assert.assertTrue(idlSpecContent.contains("Name: testInterface"));
		Assert.assertTrue(idlSpecContent.contains("Version: 1.0.0"));
		Assert.assertTrue(idlSpecContent.contains("BuildRoot: %{_tmppath}/testInterface-root"));
		Assert.assertTrue(idlSpecContent.contains("The library files for the testInterface."));
		Assert.assertTrue(idlSpecContent.contains("%dir %{_prefix}/lib/python/redhawk/testInterfaces"));
		Assert.assertTrue(idlSpecContent.contains("%dir %{_prefix}/lib/python/redhawk/testInterfaces/TEST"));
		Assert.assertTrue(idlSpecContent.contains("%dir %{_prefix}/lib/python/redhawk/testInterfaces/TEST__POA"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/include/redhawk/TEST/some.h"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/share/idl/redhawk/TEST/some.idl"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/some_idl.py"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/some_idl.pyc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/some_idl.pyo"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/__init__.py"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/__init__.pyc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST/__init__.py"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST/__init__.pyc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST__POA/__init__.py"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST__POA/__init__.pyc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/libtestInterfaces.a"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/libtestInterfaces.la"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/libtestInterfaces.so"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/libtestInterfaces.so.0"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/libtestInterfaces.so.0.0.0"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/pkgconfig/testInterface.pc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/__init__.pyo"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST/__init__.pyo"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testInterfaces/TEST__POA/__init__.pyo"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/TESTInterfaces.jar"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/TESTInterfaces.src.jar"));

		// Create the idl spec file from the content
		final File idlSpecFile = TestUtils.createFile(idlSpecContent, ".spec");
		Assert.assertNotNull(idlSpecFile);
		Assert.assertTrue(idlSpecFile.exists());
		Assert.assertTrue(idlSpecFile.getPath().endsWith(".spec"));
	}
}
