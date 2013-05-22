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
 // BEGIN GENERATED CODE
package gov.redhawk.ide.sandbox.console.py;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "gov.redhawk.ide.sandbox.console.py.messages"; //$NON-NLS-1$
	public static String RHLocalConsoleFactory_PY_ERROR;
	public static String RHLocalConsoleFactory_PY_INIT;
	static {
		// initialize resource bundle
		NLS.initializeMessages(Messages.BUNDLE_NAME, Messages.class);
		
		
		final InputStream stream = Messages.class.getResourceAsStream("sandboxConsoleInit.py");
		try {
	        Messages.RHLocalConsoleFactory_PY_INIT = IOUtils.toString(stream) + RHLocalConsoleFactory.DONE_INIT_COMMENT + "\n";
        } catch (final IOException e) {
        	// PASS
        }
	}

	private Messages() {
	}
}
