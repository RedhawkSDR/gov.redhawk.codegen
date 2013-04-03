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
		Assert.assertTrue(idlSpecContent.contains("Name: testinterfaceInterfaces"));
		Assert.assertTrue(idlSpecContent.contains("Version: 1.0.0"));
		Assert.assertTrue(idlSpecContent.contains("BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-buildroot"));
		Assert.assertTrue(idlSpecContent.contains("Libraries and interface definitions for testInterface."));
		Assert.assertTrue(idlSpecContent.contains("%defattr(-,redhawk,redhawk)"));
		Assert.assertTrue(idlSpecContent.contains("%{_datadir}/idl/redhawk/TESTINTERFACE"));
		Assert.assertTrue(idlSpecContent.contains("%{_includedir}/redhawk/TESTINTERFACE"));
		Assert.assertTrue(idlSpecContent.contains("%{_libdir}/libtestinterfaceInterfaces.*"));
		Assert.assertTrue(idlSpecContent.contains("%{_libdir}/pkgconfig/testInterfaceInterfaces.pc"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/python/redhawk/testinterfaceInterfaces"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/TESTINTERFACEInterfaces.jar"));
		Assert.assertTrue(idlSpecContent.contains("%{_prefix}/lib/TESTINTERFACEInterfaces.src.jar"));

		// Create the idl spec file from the content
		final File idlSpecFile = TestUtils.createFile(idlSpecContent, ".spec");
		Assert.assertNotNull(idlSpecFile);
		Assert.assertTrue(idlSpecFile.exists());
		Assert.assertTrue(idlSpecFile.getPath().endsWith(".spec"));
	}
}
