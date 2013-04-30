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

	public void run() {
		String line;
		try {
			while ((line = this.reader.readLine()) != null) {
				this.output.println(line);
			}
		} catch (final IOException ex) {
		}
    }
}