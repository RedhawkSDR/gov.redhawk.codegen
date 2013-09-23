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
package gov.redhawk.ide.codegen.jinja.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class InputRedirector implements Runnable {
	private final BufferedReader reader;
	private final PrintStream output;
	
	public InputRedirector(final InputStream instream, final PrintStream outstream) {
		this.reader = new BufferedReader(new InputStreamReader(instream));
		this.output = outstream;
	}

	@Override
	public void run() {
		String line;
		try {
			while ((line = this.reader.readLine()) != null) {
				this.output.println(line);
			}
		} catch (final IOException ex) {
			// PASS
		}
    }
}