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
package gov.redhawk.ide.codegen.cplusplus;

import java.util.HashSet;
import java.util.Set;

import org.omg.CORBA.TCKind;

/**
 * @since 3.1
 */
public class CppHelper {

	private static final Set<String> RESERVED_KEYWORDS = new HashSet<String>();

	static {
		final String[] reservedKeywordsArray = { "and", "and_eq", "alignas", "alignof", "asm", "auto", "bitand", "bitor", "bool", "break", "case", "catch",
		        "char", "char16_t", "char32_t", "class", "compl", "const", "constexpr", "const_cast", "continue", "decltype", "default", "delete", "double",
		        "dynamic_cast", "else", "enum", "explicit", "export", "extern", "false", "float", "for", "friend", "goto", "if", "inline", "int", "long",
		        "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator", "or", "or_eq", "private", "protected", "public", "register",
		        "reinterpret_cast", "return", "short", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch", "template", "this",
		        "thread_local", "throw", "true", "try", "typedef", "typeid", "typename", "union", "unsigned", "using", "virtual", "void", "volatile",
		        "wchar_t", "while", "xor", "xor_eq" };

		for (final String reservedKeyword : reservedKeywordsArray) {
			CppHelper.RESERVED_KEYWORDS.add(reservedKeyword);
		}
	};

	public int convertType(final String value) {
		if (value.equals("short")) {
			return TCKind._tk_short;
		} else if (value.equals("long")) {
			return TCKind._tk_long;
		} else if (value.equals("unsigned short")) {
			return TCKind._tk_ushort;
		} else if (value.equals("unsigned long")) {
			return TCKind._tk_ulong;
		} else if (value.equals("float")) {
			return TCKind._tk_float;
		} else if (value.equals("double")) {
			return TCKind._tk_double;
		} else if (value.equals("char")) {
			// TODO Not really correct for OmniORB
			return TCKind._tk_char;
		} else if (value.equals("unsigned char")) {
			return TCKind._tk_octet;
		} else if (value.equals("string")) {
			return TCKind._tk_string;
		}
		return TCKind._tk_null;
	}

	public String getBasicMapping(final String value) {
		final String CORBA_NS = "CORBA::"; // SUPPRESS CHECKSTYLE LocalFinalVariableName

		if (value.equals("char*")) {
			return "std::string";
		}

		String lastValue = value.substring(value.length() - 1);
		if (lastValue.equals("&")) {
			lastValue = "&";
		} else {
			lastValue = "";
		}

		if (value.startsWith(CORBA_NS)) {
			final String subType = value.substring(CORBA_NS.length());
			if (subType.equals("Short")) {
				return "short" + lastValue;
			} else if (subType.equals("Float")) {
				return "float" + lastValue;
			} else if (subType.equals("Double")) {
				return "double" + lastValue;
			} else if (subType.equals("Long")) {
				return "long" + lastValue;
			} else if (subType.equals("ULong")) {
				return "unsigned long" + lastValue;
			} else if (subType.equals("UShort")) {
				return "unsigned short" + lastValue;
			} else if (subType.equals("WString")) {
				return "const char *";
			} else if (subType.equals("Char")) {
				// TODO OmniORB maps chars to unsigned chars for some reason. This may have to change if switching ORB's
				return "unsigned char" + lastValue;
			} else if (subType.equals("Boolean")) {
				return "bool" + lastValue;
			} else if (subType.equals("WChar")) {
				return "unsigned char" + lastValue;
			} else if (subType.equals("Octet")) {
				return "unsigned char" + lastValue;
			} else {
				return value;
			}
		}

		return value;
	}

	/**
	 * @since 6.0
	 */
	public String pointerReturnValue(final String cxxType, final String returnType, final boolean cxxTypeVariableLength) {
		final String SEQ = "Sequence"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		if (cxxType.endsWith(SEQ)) {
			return "*";
		} else if (returnType.equals("struct")) {
			if (cxxTypeVariableLength) {
				return "*";
			} else {
				return "";
			}
		} else if (returnType.equals("sequence")) {
			return "*";
		}

		return "";
	}

	/**
	 * @since 6.0
	 */
	public String varReturnValue(final String cxxType, final String returnType) {
		final String SEQ = "Sequence"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		if (cxxType.endsWith(SEQ)) {
			return "_var";
		} else if (returnType.equals("struct")) {
			return "_var";
		} else if (returnType.equals("sequence")) {
			return "_var";
		}

		return "";
	}

	public String getCppMapping(final String value) {
		final String SEQ_REFERENCE = "Sequence&"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		final String CF_PORTTYPES = "const PortTypes::"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		final String CF_TYPES = "const CF::"; // SUPPRESS CHECKSTYLE LocalFinalVariableName

		if (value.startsWith("const PortTypes::") && value.endsWith(SEQ_REFERENCE)) {
			final String subType = value.substring(CF_PORTTYPES.length(), value.length() - SEQ_REFERENCE.length());
			if (subType.equals("Short")) {
				return "std::vector<CORBA::Short>& ";
			} else if (subType.equals("Float")) {
				return "std::vector<CORBA::Float>& ";
			} else if (subType.equals("Double")) {
				return "std::vector<CORBA::Double>& ";
			} else if (subType.equals("Long")) {
				return "std::vector<CORBA::Long>& ";
			} else if (subType.equals("LongLong")) {
				return "std::vector<CORBA::LongLong>& ";
			} else if (subType.equals("Ulong")) {
				return "std::vector<CORBA::ULong>& ";
			} else if (subType.equals("UlongLong")) {
				return "std::vector<CORBA::ULongLong>& ";
			} else if (subType.equals("Ushort")) {
				return "std::vector<CORBA::UShort>& ";
			} else if (subType.equals("Wstring")) {
				return "std::vector<string>& ";
			} else if (subType.equals("Char")) {
				// TODO OmniORB maps chars to unsigned chars for some reason. This may have to change if switching ORB's
				return "std::vector<unsigned char>& ";
			} else if (subType.equals("Boolean")) {
				return "std::vector<bool>& ";
			} else if (subType.equals("Wchar")) {
				return "std::vector<unsigned char>& ";
			} else if (subType.equals("String")) {
				return "std::vector<string>& ";
			} else {
				return getBasicMapping(value);
			}
		} else {
			if (value.startsWith(CF_TYPES) && value.endsWith(SEQ_REFERENCE)) {
				final String subType = value.substring(CF_TYPES.length(), value.length() - SEQ_REFERENCE.length());
				if (subType.equals("Octet")) {
					return "std::vector<unsigned char>& ";
				}
			}
			return getBasicMapping(value);
		}
	};

	public String getBaseSequenceMapping(final String value) {
		final String SEQ_REFERENCE = "Sequence&"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		final String CF_PORTTYPES = "const PortTypes::"; // SUPPRESS CHECKSTYLE LocalFinalVariableName
		final String CF_TYPES = "const CF::"; // SUPPRESS CHECKSTYLE LocalFinalVariableName

		if (value.startsWith("const PortTypes::") && value.endsWith(SEQ_REFERENCE)) {
			final String subType = value.substring(CF_PORTTYPES.length(), value.length() - SEQ_REFERENCE.length());
			if (subType.equals("Short")) {
				return "std::vector<CORBA::Short>";
			} else if (subType.equals("Float")) {
				return "std::vector<CORBA::Float>";
			} else if (subType.equals("Double")) {
				return "std::vector<CORBA::Double>";
			} else if (subType.equals("Long")) {
				return "std::vector<CORBA::Long>";
			} else if (subType.equals("LongLong")) {
				return "std::vector<CORBA::LongLong>";
			} else if (subType.equals("Ulong")) {
				return "std::vector<CORBA::ULong>";
			} else if (subType.equals("UlongLong")) {
				return "std::vector<CORBA::ULongLong>";
			} else if (subType.equals("Ushort")) {
				return "std::vector<CORBA::UShort>";
			} else if (subType.equals("Wstring")) {
				return "std::vector<string>";
			} else if (subType.equals("Char")) {
				// TODO OmniORB maps chars to unsigned chars for some reason. This may have to change if switching ORB's
				return "std::vector<unsigned char>";
			} else if (subType.equals("Boolean")) {
				return "std::vector<bool>";
			} else if (subType.equals("Wchar")) {
				return "std::vector<unsigned char>";
			} else if (subType.equals("String")) {
				return "std::vector<string>";
			} else {
				return getBasicMapping(value);
			}
		} else {
			if (value.startsWith(CF_TYPES) && value.endsWith(SEQ_REFERENCE)) {
				final String subType = value.substring(CF_TYPES.length(), value.length() - SEQ_REFERENCE.length());
				if (subType.equals("Octet")) {
					return "std::vector<unsigned char>";
				}
			}
			return getBasicMapping(value);
		}
	};

	/**
	 * @since 6.0
	 */
	public String getInitialValue(final String cxxType, final String returnType, final boolean cxxTypeVariableLength) {
		final boolean ptr = !("".equals(pointerReturnValue(cxxType, returnType, cxxTypeVariableLength)));

		if (ptr) {
			return "NULL";
		} else if (cxxType.equals("CORBA::Object_ptr")) {
			return "CORBA::Object::_nil()";
		} else if ("char*".equals(cxxType)) {
			return "\"\"";
		} else if ("bool".equals(cxxType) || "boolean".equals(returnType)) {
			return "false";
		} else if ("enum".equals(returnType)) {
			return "(" + cxxType + ")0";
		} else if ("double".equals(returnType) || "float".equals(returnType)) {
			return "0.0";
		} else if ("short".equals(returnType) || "long".equals(returnType) || "longlong".equals(returnType) || "ushort".equals(returnType)
		        || "ulong".equals(returnType) || "ulonglong".equals(returnType)) {
			return "0";
		} else {
			return "";
		}
	}

	/**
	 * @since 5.0
	 */
	public String vectorize(final String type) {
		final int INSERT_IDX = 2;
		final StringBuilder retVal = new StringBuilder(type);

		if (type.startsWith("std::vector")) {
			retVal.insert(type.length() - INSERT_IDX, ", ALLOCATOR");
		}

		return retVal.toString();
	}

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
	 * Escapes an input char literal according to C/C++ rules. Afterwards such a string can safely be used in code when
	 * surrounded with single quotes / apostrophes (&apos;).
	 * 
	 * @param unescapedChar The char literal to be escaped
	 * @return The escaped char literal
	 * @since 5.1
	 */
	public static String escapeChar(final char unescapedChar) {
		if (unescapedChar == CppHelper.CHAR_SINGLE_QUOTE) {
			return "\\'";
		} else if (unescapedChar == CppHelper.CHAR_DOUBLE_QUOTE) {
			return "\"";
		} else {
			return CppHelper.escapeString(String.valueOf(unescapedChar));
		}
	}

	/**
	 * Escapes an input string literal according to C/C++ rules. Afterwards such a string can safely be used in code
	 * when surrounded with double quotes (&quot;).
	 * 
	 * @param unescapedString The string literal to be escaped
	 * @return The escaped string literal
	 * @since 5.1
	 */
	public static String escapeString(final String unescapedString) {
		if (unescapedString == null) {
			return "";
		}
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
				if (c < CppHelper.CHAR_SPACE) {
					buffer.append("\\x");
					buffer.append(Integer.toHexString((int) c));
				} else {
					buffer.append(c);
				}
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Modifies a candidate identifier so that it is valid according to C/C++ rules. Illegal characters are changed to
	 * underscores, reserved keywords are suffixed with an underscore, etc. Afterwards the string can be safely used as
	 * an identifier in C/C++ code.
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
		char first = newCandidate.charAt(0);
		if (first >= '0' && first <= '9') {
			newCandidate = "_" + newCandidate;
		}

		// Postfix if necessary
		if (CppHelper.RESERVED_KEYWORDS.contains(newCandidate)) {
			newCandidate = newCandidate + "_";
		}

		return newCandidate;
	}

}
