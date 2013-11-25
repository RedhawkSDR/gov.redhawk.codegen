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
package gov.redhawk.ide.pydev.tests;

import gov.redhawk.ide.pydev.util.AutoConfigPydevInterpreterUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AutoConfigPydevInterpreterUtilTest {

	@Rule
	public ExpectedException exception = ExpectedException.none(); // SUPPRESS CHECKSTYLE Public

	@Test
	public void testGoodOssieHome() throws Exception {
		final NullProgressMonitor nullMon = new NullProgressMonitor();
		AutoConfigPydevInterpreterUtil.configurePydev(nullMon, false, System.getenv("OSSIEHOME"));
	}

	@Test
	public void testBadOssieHome() throws Exception {
		final NullProgressMonitor nullMon = new NullProgressMonitor();

		this.exception.expect(CoreException.class);
		this.exception.expectMessage("OSSIEHOME=/tpm/oofoofbarkbark/osise does not exist, auto config failed.");

		AutoConfigPydevInterpreterUtil.configurePydev(nullMon, false, "/tpm/oofoofbarkbark/osise does not exist, auto config failed.");
	}

	@Test
	public void testSystemEnvOssieHome() throws Exception {
		final NullProgressMonitor nullMon = new NullProgressMonitor();
		final String path = System.getenv("OSSIEHOME");
		//If the environment is not configured this should throw an exception; otherwise will succeed
		if (path == null || path.isEmpty()) {
			this.exception.expect(CoreException.class);
			this.exception.expectMessage("OSSIEHOME environment variable not defined, auto config failed.");
		}
		AutoConfigPydevInterpreterUtil.configurePydev(nullMon, false, path);
	}
}
