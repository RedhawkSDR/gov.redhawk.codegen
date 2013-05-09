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
package gov.redhawk.ide.codegen.python.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.core.IPythonPathNature;
import org.python.pydev.plugin.nature.PythonNature;

/**
 * @since 5.1
 */
public class PythonGeneratorUtils {

	private PythonGeneratorUtils() {
	}

	private static final Set<String> RESERVED_KEYWORDS = new HashSet<String>();

	static {
		final String[] reservedKeywordsArray = { "and", "as", "assert", "break", "class", "continue", "def", "del", "elif", "else", "except", "exec",
		        "finally", "for", "from", "global", "if", "import", "in", "is", "lambda", "not", "or", "pass", "print", "raise", "return", "try", "while",
		        "with", "yield", "None" };

		for (final String reservedKeyword : reservedKeywordsArray) {
			PythonGeneratorUtils.RESERVED_KEYWORDS.add(reservedKeyword);
		}
	};

	private static final int CHAR_NULL = 0;
	private static final int CHAR_BELL = 7;
	private static final int CHAR_BACKSPACE = 8;
	private static final int CHAR_HORIZONTAL_TAB = 9;
	private static final int CHAR_LINE_FEED = 10;
	private static final int CHAR_VERTICAL_TAB = 11;
	private static final int CHAR_FORM_FEED = 12;
	private static final int CHAR_CARRIAGE_RETURN = 13;
	private static final int CHAR_SPACE = 32;
	private static final int CHAR_DOUBLE_QUOTE = 34;
	private static final int CHAR_SINGLE_QUOTE = 39;
	private static final int CHAR_BACKSLASH = 92;

	/**
	 * Escapes an input char literal according to Python rules. Afterwards such a string can safely be used in code when
	 * surrounded with single quotes / apostrophes (&apos;).
	 * 
	 * @param unescapedChar The char literal to be escaped
	 * @return The escaped char literal
	 * @since 5.0
	 */
	public static String escapeChar(final char unescapedChar) {
		if (unescapedChar == PythonGeneratorUtils.CHAR_SINGLE_QUOTE) {
			return "\\'";
		} else if (unescapedChar == PythonGeneratorUtils.CHAR_DOUBLE_QUOTE) {
			return "\"";
		} else {
			return PythonGeneratorUtils.escapeString(String.valueOf(unescapedChar));
		}
	}

	/**
	 * Escapes an input string literal according to Python rules. Afterwards such a string can safely be used in code
	 * when surrounded with double quotes (&quot;).
	 * 
	 * @param unescapedString The string literal to be escaped
	 * @return The escaped string literal
	 * @since 5.0
	 */
	public static String escapeString(final String unescapedString) {
		final StringBuffer buffer = new StringBuffer(unescapedString.length() * 2);
		for (final char c : unescapedString.toCharArray()) {
			switch (c) {
			case CHAR_NULL:
				buffer.append("\\0");
				break;
			case CHAR_BELL:
				buffer.append("\\a");
				break;
			case CHAR_BACKSPACE:
				buffer.append("\\b");
				break;
			case CHAR_HORIZONTAL_TAB:
				buffer.append("\\t");
				break;
			case CHAR_LINE_FEED:
				buffer.append("\\n");
				break;
			case CHAR_VERTICAL_TAB:
				buffer.append("\\v");
				break;
			case CHAR_FORM_FEED:
				buffer.append("\\f");
				break;
			case CHAR_CARRIAGE_RETURN:
				buffer.append("\\r");
				break;
			case CHAR_DOUBLE_QUOTE:
				buffer.append("\\\"");
				break;
			case CHAR_BACKSLASH:
				buffer.append("\\\\");
				break;
			default:
				if (c < PythonGeneratorUtils.CHAR_SPACE) {
					buffer.append("\\x");
					buffer.append(Integer.toHexString(c));
				} else {
					buffer.append(c);
				}
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Modifies a candidate identifier so that it is valid according to Python rules. Illegal characters are changed to
	 * underscores, reserved keywords are suffixed with an underscore, etc. Afterwards the string can be safely used as
	 * an identifier in Python code.
	 * 
	 * @param candidateIdentifier The string to modify (potentially invalid identifier)
	 * @return A valid identifier
	 * @since 6.0
	 */
	public static String makeValidIdentifier(String candidateIdentifier) {
		// Replace invalid characters
		final StringBuilder newCandidateSB = new StringBuilder(candidateIdentifier.length());
		for (int i = 0; i < candidateIdentifier.length(); i++) {
			char currentChar = candidateIdentifier.charAt(i);
			if ((currentChar < 'a' || currentChar > 'z') && (currentChar < 'A' || currentChar > 'Z') && (currentChar < '0' || currentChar > '9')
			        && (currentChar != '_')) {
				newCandidateSB.append('_');
			} else {
				newCandidateSB.append(currentChar);
			}
		}
		String newCandidate = newCandidateSB.toString();

		// Prefix if necessary
		if (newCandidate.length() > 0) {
			char first = newCandidate.charAt(0);
			if ((first >= '0' && first <= '9') || (first == '_')) {
				newCandidate = "prop" + newCandidate;
			}
		}

		// Postfix if necessary
		if (PythonGeneratorUtils.RESERVED_KEYWORDS.contains(newCandidate)) {
			newCandidate = newCandidate + "_";
		}

		return newCandidate;
	}

	/**
	 * Given a repId, guess the appropriate import statement.
	 * <p>
	 * This is a guess because REDHAWK currently deviates from the default behavior specified by OmniORBpy and the CORBA
	 * langauge mapping.
	 * <p>
	 * For example, the following RepId's translate to the following imports:
	 * <p>
	 * <table>
	 * <tr><th>RepId</th><th>Default</th><th>REDHAWK</th></tr>
	 * <tr><td>IDL:BULKIO/dataFloat:1.0</td><td>"import BULKIO"</td><td>"from bulkio.bulkioInterfaces import BULKIO"</td></tr>
	 * <tr><td>IDL:CF/Resource:1.0</td><td>"import CF"</td><td>"from ossie.cf import CF"</td></tr>
	 * <tr><td>IDL:FRONTEND/Resource:1.0</td><td>"import FRONTEND"</td><td>"from redhawk.frontendInterfaces import FRONTEND"</td></tr>
	 * </table>
	 * 
	 * @param repId
	 * @return the import statement or null if one could not be guessed or the repId is invalid.
	 * @since 6.0
	 */
	public static String guessPythonImportForRepId(final String repId, final boolean includePOA) {
		final String[] repIdParts = PythonGeneratorUtils.splitRepId(repId);
		if (repIdParts == null) {
			return null;
		}
		final List<String> parts = new ArrayList<String>(Arrays.asList(repIdParts));

		String module = null;
		if (parts.get(0).equals("CF")) {
			// CF special case
			module = "ossie.cf";
		} else if (parts.get(0).equals("BULKIO")) {
			// BULKIO special case
			module = "bulkio.bulkioInterfaces";
		} else if (parts.get(0).equals("omg.org")) { // from the #pragma in the omniORB idl's
			module = null;
			parts.remove(0);
		} else {
			// normal case
			module = "redhawk." + parts.get(0).toLowerCase() + "Interfaces";
		}

		String importStmt = null;
		if (module != null) {
			importStmt = "from " + module + " import ";
		} else {
			importStmt = "import ";
		}

		importStmt += parts.get(0);
		if (includePOA) {
			importStmt += ", " + parts.get(0) + "__POA";
		}

		return importStmt;
	}

	/**
	 * Splits a RepId into constituent parts.
	 * 
	 * @param repId a repId
	 * @return the split parts or null if the repId is invalid
	 * @since 6.0
	 */
	public static String[] splitRepId(final String repId) {
		final int IDLLEN = 4;
		if (!(repId.startsWith("IDL:") && repId.endsWith(":1.0"))) {
			return null;
		}
		final String[] parts = repId.substring(IDLLEN, repId.length() - IDLLEN).split("/");
		return parts;
	}

	/**
	 * Attempts to cleanly add the python project nature to the project.
	 * 
	 * @param project
	 * @param monitor
	 * @return the python nature or null if there was an issue
	 * @since 6.0
	 */
	public static IPythonNature addPythonProjectNature(final IProject project, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, 1);

		IPythonNature pythonNature = null;
		boolean hasPythonNature;
		hasPythonNature = project.hasNature(PythonNature.PYTHON_NATURE_ID);

		if (!hasPythonNature) {
			progress.subTask("Configuring new Python project nature");
			pythonNature = PythonNature.addNature(project, progress.newChild(1), null, null, null, null, null);
		}
		return pythonNature;
	}

	/**
	 * Attempts to add the specified workspace relative path to the PyDev python path setting.
	 * 
	 * @param project the project to add to
	 * @param path the workspace relative path to add
	 * @param monitor the IProgressMonitor for status
	 * @return true if the path was added, false if it was already present
	 * @since 6.0
	 */
	public static boolean addPythonSourcePath(final IProject project, final String path, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, 1);

		IPythonNature pythonNature = null;
		boolean hasPythonNature;
		hasPythonNature = project.hasNature(PythonNature.PYTHON_NATURE_ID);

		if (!hasPythonNature) {
			PythonGeneratorUtils.addPythonProjectNature(project, progress);
		}

		pythonNature = (IPythonNature) project.getNature(PythonNature.PYTHON_NATURE_ID);
		final IPythonPathNature pathNature = pythonNature.getPythonPathNature();
		String sourcePath = pathNature.getProjectSourcePath(true);
		final String[] paths = sourcePath.split("|");
		for (final String p : paths) {
			if (p.equals(path)) {
				return false;
			}
		}

		if (sourcePath.length() == 0) {
			sourcePath = path;
		} else {
			sourcePath += "|" + path;
		}
		pathNature.setProjectSourcePath(sourcePath);

		return true;
	}
}
