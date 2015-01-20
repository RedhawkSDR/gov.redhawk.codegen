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
package gov.redhawk.ide.codegen.java;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.codegen.java.internal.ScaCore;
import gov.redhawk.ide.idl.IdlJavaUtil;
import gov.redhawk.sca.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mil.jpeojtrs.sca.prf.AbstractProperty;
import mil.jpeojtrs.sca.prf.ConfigurationKind;
import mil.jpeojtrs.sca.prf.Kind;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleSequence;
import mil.jpeojtrs.sca.prf.Struct;
import mil.jpeojtrs.sca.prf.StructSequence;
import mil.jpeojtrs.sca.scd.Ports;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.scd.Uses;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.DceUuidUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.jacorb.idl.Interface;

/**
 * Helper functions to simplify the JET files.
 * 
 * @since 5.1
 */
public class JavaGeneratorUtils {

	// CHECKSTYLE:OFF
	public static final String NEWLINE = "\n";

	private JavaGeneratorUtils() {
	}

	private static final Set<String> RESERVED_KEYWORDS = new HashSet<String>();

	static {
		final String[] reservedKeywordsArray = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
		        "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "if", "goto", "implements", "import", "instanceof",
		        "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
		        "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false", "null" };

		for (final String reservedKeyword : reservedKeywordsArray) {
			JavaGeneratorUtils.RESERVED_KEYWORDS.add(reservedKeyword);
		}
	};

	public static String getJavaType(final String type) {
		if ("string".equals(type)) {
			return "String";
		} else if ("wstring".equals(type)) {
			return "String";
		} else if ("boolean".equals(type)) {
			return "Boolean";
		} else if ("char".equals(type)) {
			return "Character";
		} else if ("wchar".equals(type)) {
			return "Character";
		} else if ("double".equals(type)) {
			return "Double";
		} else if ("float".equals(type)) {
			return "Float";
		} else if ("short".equals(type)) {
			return "Short";
		} else if ("long".equals(type)) {
			return "Integer";
		} else if ("longlong".equals(type)) {
			return "Long";
		} else if ("ulong".equals(type)) {
			return "Long";
		} else if ("ushort".equals(type)) {
			return "Integer";
		} else if ("objref".equals(type)) {
			return "String";
		} else if ("octet".equals(type)) {
			return "Byte";
		} else {
			return "Object";
		}
	}

	public static String toJavaLiteral(final String type, final String value) {
		try {
			if (value == null) {
				return "null";
			} else if ("string".equals(type)) {
				return "\"" + JavaGeneratorUtils.escapeString(value) + "\"";
			} else if ("wstring".equals(type)) {
				return "\"" + JavaGeneratorUtils.escapeString(value) + "\"";
			} else if ("boolean".equals(type)) {
				return Boolean.valueOf(value).toString();
			} else if ("char".equals(type)) {
				return "\'" + JavaGeneratorUtils.escapeChar(value.charAt(0)) + "\'";
			} else if ("wchar".equals(type)) {
				return "\'" + JavaGeneratorUtils.escapeChar(value.charAt(0)) + "\'";
			} else if ("double".equals(type)) {
				return Double.valueOf(value).toString();
			} else if ("float".equals(type)) {
				return Float.valueOf(value).toString() + "F";
			} else if ("short".equals(type)) {
				return "(short)" + Short.valueOf(value).toString();
			} else if ("long".equals(type)) {
				return Integer.valueOf(value).toString();
			} else if ("longlong".equals(type)) {
				return Long.valueOf(value).toString() + "L";
			} else if ("ulong".equals(type)) {
				return Long.valueOf(value).toString() + "L";
			} else if ("ushort".equals(type)) {
				return Integer.valueOf(value).toString();
			} else if ("objref".equals(type)) {
				return "\"" + value + "\"";
			} else if ("octet".equals(type)) {
				return "(byte)" + Byte.valueOf(value.getBytes()[0]).toString();
			} else {
				return "Object";
			}
		} catch (final NumberFormatException e) {
			return "";
		}
	}

	/**
	 * Returns a javadoc starting line with the specified tab indentation level.
	 * 
	 * @param indent the number of tabs to indent
	 * @return a String for a new javadoc comment
	 */
	public static String getJavadocStart(final int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.newIndentedLine(indent));
		builder.append("/**");
		return builder.toString();
	}

	/**
	 * Returns a new javadoc line with the specified tab indentation level.
	 * 
	 * @param indent the number of tabs to indent
	 * @return a String for a new javadoc line
	 */
	public static String getJavadocNewline(final int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.newIndentedLine(indent));
		builder.append(" * ");
		return builder.toString();
	}

	/**
	 * Returns a javadoc ending line with the specified tab indentation level.
	 * 
	 * @param indent the number of tabs to indent
	 * @return a String for a javadoc comment end
	 */
	public static String getJavadocEnd(final int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.newIndentedLine(indent));
		builder.append(" */");
		return builder.toString();
	}

	/**
	 * Returns a new line with the specified tab indentation level.
	 * 
	 * @param indent the number of tabs to indent
	 * @return a String composed of the new line and an appropriate number of tabs
	 */
	public static String newIndentedLine(int indent) {
		final StringBuilder builder = new StringBuilder(JavaGeneratorUtils.NEWLINE);
		while (indent > 0) {
			builder.append("\t");
			indent--;
		}
		return builder.toString();
	}

	/**
	 * Converts a repID to a class name prefix.
	 * 
	 * @param repId
	 * @return
	 * @throws CoreException An error prevents generation of the file
	 */
	public static String repIdToClassPrefix(final String repId) throws CoreException {
		String nameSpace = "";
		String interfaceName = "";
		final Interface intf = IdlJavaUtil.getInstance().getInterface(Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath()),
		        repId.split(":")[1], true);
		if (intf == null) {
			throw new CoreException(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + repId));
		}
		nameSpace = intf.pack_name;
		interfaceName = intf.name();
		return nameSpace + "_" + interfaceName;
	}

	/**
	 * Given a softpkg, make unique names for all properties.
	 * 
	 * @param pkg the softpkg
	 * @param defaultName the name to use if the property doesn't have one. if null use the property ID
	 * @return a LinkedHashMap of the unique property name to the property FeatureMap Entry
	 */
	public static Map<String, AbstractProperty> createPropertiesSet(final SoftPkg pkg, final String defaultName) {
		final Map<String, AbstractProperty> uniqueNameMap = new LinkedHashMap<String, AbstractProperty>();
		final List<String> usedNames = new ArrayList<String>();
		final List<EObject> properties = pkg.getPropertyFile().getProperties().eContents();
		for (final EObject obj : properties) {
			final AbstractProperty prop = (AbstractProperty) obj;
			final String uniqueName = JavaGeneratorUtils.getMemberName(prop, defaultName, usedNames);
			usedNames.add(uniqueName);
			uniqueNameMap.put(uniqueName, prop);
		}
		return uniqueNameMap;
	}

	public static Map<String, Simple> createStructFieldSet(final Struct struct, final String defaultName) {
		final Map<String, Simple> uniqueNameMap = new LinkedHashMap<String, Simple>();
		final List<String> usedNames = new ArrayList<String>();

		for (final Simple simp : struct.getSimple()) {
			final String uniqueName = JavaGeneratorUtils.getMemberName(simp, defaultName, usedNames);
			usedNames.add(uniqueName);
			uniqueNameMap.put(uniqueName, simp);
		}
		return uniqueNameMap;
	}

	public static String propertyKindToArrayInitializer(final List<Kind> kinds) {
		if (kinds.isEmpty()) {
			return "{}";
		} else {
			final StringBuffer buf = new StringBuffer();
			buf.append("{");
			for (final Kind tempKind : kinds) {
				buf.append("\"" + tempKind.getType().getLiteral() + "\"");
				buf.append(",");
			}
			//remove trailing comma
			buf.deleteCharAt(buf.length() - 1);
			buf.append("}");
			return buf.toString();
		}
	}

	public static String propertyConfigurationKindToArrayInitializer(final List<ConfigurationKind> kinds) {
		if (kinds.isEmpty()) {
			return "{}";
		} else {
			final StringBuffer buf = new StringBuffer();
			buf.append("{");
			for (final ConfigurationKind tempKind : kinds) {
				buf.append("\"" + tempKind.getType().getLiteral() + "\"");
				buf.append(",");
			}
			//remove trailing comma
			buf.deleteCharAt(buf.length() - 1);
			buf.append("}");
			return buf.toString();
		}
	}

	public static String simpleSequenceToArrayInitializer(final SimpleSequence prop) {
		if ((prop.getValues() == null) || (prop.getValues().getValue().isEmpty())) {
			return "()";
		} else {
			final StringBuffer buf = new StringBuffer();
			buf.append("(Arrays.asList(");
			for (final String v : prop.getValues().getValue()) {
				buf.append(JavaGeneratorUtils.toJavaLiteral(prop.getType().getLiteral(), v));
				buf.append(",");
			}
			//remove trailing comma
			buf.deleteCharAt(buf.length() - 1);
			buf.append("))");
			return buf.toString();
		}
	}

	/**
	 * @throws CoreException An error prevents generation of the file
	 * @since 4.0
	 */
	public static String getPortName(final String repId) throws CoreException {
		String nameSpace = "";
		String interfaceName = "";
		final Interface intf = IdlJavaUtil.getInstance().getInterface(Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath()),
		        repId.split(":")[1], true);
		if (intf == null) {
			throw new CoreException(new Status(IStatus.ERROR, JavaGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + repId));
		}
		nameSpace = intf.pack_name;
		interfaceName = intf.name();

		return nameSpace + "_" + interfaceName;
	}

	public static String getMemberName(final AbstractProperty prop, final String defaultName, final List<String> usedNames) {
		// To create the Java member name:
		//   1) Use name, if provided
		//   2) If id is a DCE UUID, use the provided defaultName
		//   3) Just use the id
		if (prop.getName() != null && prop.getName().trim().length() != 0) {
			return StringUtil.defaultCreateUniqueString(JavaGeneratorUtils.makeValidIdentifier(prop.getName()), usedNames);
		} else if (DceUuidUtil.isValid(prop.getId())) {
			return StringUtil.defaultCreateUniqueString(JavaGeneratorUtils.makeValidIdentifier(defaultName), usedNames);
		} else {
			return StringUtil.defaultCreateUniqueString(JavaGeneratorUtils.makeValidIdentifier(prop.getId()), usedNames);
		}
	}

	private static final int CHAR_NULL = 0;
	private static final int CHAR_BACKSPACE = 8;
	private static final int CHAR_HORIZONTAL_TAB = 9;
	private static final int CHAR_LINE_FEED = 10;
	private static final int CHAR_FORM_FEED = 12;
	private static final int CHAR_CARRIAGE_RETURN = 13;
	private static final int CHAR_SPACE = 32;
	private static final int CHAR_DOUBLE_QUOTE = 34;
	private static final int CHAR_SINGLE_QUOTE = 39;
	private static final int CHAR_BACKSLASH = 92;

	/**
	 * Escapes an input char literal according to Java rules. Afterwards such a string can safely be used in code when
	 * surrounded with single quotes / apostrophes (&apos;).
	 * 
	 * @param unescapedChar The char literal to be escaped
	 * @return The escaped char literal
	 * @since 5.0
	 */
	public static String escapeChar(final char unescapedChar) {
		if (unescapedChar == JavaGeneratorUtils.CHAR_SINGLE_QUOTE) {
			return "\\'";
		} else if (unescapedChar == JavaGeneratorUtils.CHAR_DOUBLE_QUOTE) {
			return "\"";
		} else {
			return JavaGeneratorUtils.escapeString(String.valueOf(unescapedChar));
		}
	}

	/**
	 * Escapes an input string literal according to Java rules. Afterwards such a string can safely be used in code when
	 * surrounded with double quotes (&quot;).
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
			case CHAR_BACKSPACE:
				buffer.append("\\b");
				break;
			case CHAR_HORIZONTAL_TAB:
				buffer.append("\\t");
				break;
			case CHAR_LINE_FEED:
				buffer.append("\\n");
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
				if (c < JavaGeneratorUtils.CHAR_SPACE) {
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
	 * Modifies a candidate identifier so that it is valid according to Java rules. Illegal characters are changed
	 * to underscores, reserved keywords are suffixed with an underscore, etc. Afterwards the string can be safely
	 * used as an identifier in Java code.
	 * 
	 * @param candidateIdentifier The string to modify (potentially invalid identifier)
	 * @return A valid identifier
	 */
	public static String makeValidIdentifier(final String candidateIdentifier) {
		// Replace invalid characters
		final StringBuilder newCandidateSB = new StringBuilder(candidateIdentifier.length());
		for (int i = 0; i < candidateIdentifier.length(); i++) {
			final char currentChar = candidateIdentifier.charAt(i);
			if (!Character.isJavaIdentifierPart(currentChar)) {
				newCandidateSB.append('_');
			} else {
				newCandidateSB.append(currentChar);
			}
		}
		String newCandidate = newCandidateSB.toString();

		// Prefix if necessary
		if (!Character.isJavaIdentifierStart(newCandidate.charAt(0))) {
			newCandidate = "_" + newCandidate;
		}

		// Postfix if necessary
		if (JavaGeneratorUtils.RESERVED_KEYWORDS.contains(newCandidate)) {
			newCandidate = newCandidate + "_";
		}

		return newCandidate;
	}

	/**
	 * Returns a javadoc starting line with the specified tab indentation level.
	 * 
	 * @param indent the number of tabs to indent
	 * @return a String for a new javadoc comment
	 */
	public static IJavaProject addJavaProjectNature(final IProject project, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, 2);
		IJavaProject javaProject = null;

		progress.subTask("Checking project natures");
		final IProjectDescription desc = project.getDescription();
		if (!project.hasNature(JavaCore.NATURE_ID)) {
			// Add the Java Nature
			final String[] natures = desc.getNatureIds();
			final String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = JavaCore.NATURE_ID;
			desc.setNatureIds(newNatures);
			project.setDescription(desc, progress.newChild(1));

			// Get the resulting Java Project
			javaProject = JavaCore.create(project);

			final IClasspathEntry[] defaultClasspath = new IClasspathEntry[1];
			defaultClasspath[0] = JavaRuntime.getDefaultJREContainerEntry();
			javaProject.setRawClasspath(defaultClasspath, progress.newChild(1));
		} else {
			javaProject = JavaCore.create(project);
		}
		return javaProject;
	}

	public static void addRedhawkJavaClassPaths(final IJavaProject jproject, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, 1);
		final Set<IClasspathEntry> entries = new LinkedHashSet<IClasspathEntry>(Arrays.asList(jproject.getRawClasspath()));

		IClasspathEntry e;
		e = JavaRuntime.getDefaultJREContainerEntry();
		if (!entries.contains(e)) {
			entries.add(e);
		}

		entries.add(JavaCore.newContainerEntry(ScaCore.OSSIE_LIB_CONTAINER_PATH));
		entries.add(JavaCore.newContainerEntry(ScaCore.SOFT_PKG_REF_CONTAINER_PATH));

		jproject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), progress.newChild(1));
	}

	/**
	 * @deprecated The code in {@link #addRedhawkJavaClassPaths} will correctly include all of the .jar files
	 * @param jproject
	 * @param ports
	 * @param monitor
	 * @throws CoreException
	 */
	@Deprecated
	public static void addRedhawkPortClassPaths(final IJavaProject jproject, final Ports ports, final IProgressMonitor monitor) throws CoreException {
		final Set<String> packages = new LinkedHashSet<String>();
		for (final Provides p : ports.getProvides()) {
			final String[] ints = p.getRepID().split(":")[1].split("/");
			packages.add(ints[ints.length - 2]);
		}
		for (final Uses u : ports.getUses()) {
			final String[] ints = u.getRepID().split(":")[1].split("/");
			packages.add(ints[ints.length - 2]);
		}

		final SubMonitor progress = SubMonitor.convert(monitor, packages.size() + 1);
		final Set<IClasspathEntry> entries = new LinkedHashSet<IClasspathEntry>(Arrays.asList(jproject.getRawClasspath()));

		for (final String pack : packages) {
			final String jarFile = "OSSIEHOME/lib/" + pack + "Interfaces.jar";
			final IClasspathEntry e = JavaCore.newVariableEntry(new Path(jarFile), null, null);
			if (!entries.contains(e)) {
				entries.add(e);
			}
			progress.worked(1);
		}

		jproject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), progress.newChild(1));
	}

	public static void addSourceClassPaths(final IJavaProject jproject, final IPath srcPath, final IPath binPath, final IProgressMonitor monitor)
	        throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, 1);
		final Set<IClasspathEntry> entries = new LinkedHashSet<IClasspathEntry>(Arrays.asList(jproject.getRawClasspath()));

		// Add source code to the java project classpath
		for (final IClasspathEntry path : entries) {
			if ((path.getEntryKind() == IClasspathEntry.CPE_SOURCE) && path.getPath().equals(jproject.getProject().getFullPath())) {
				continue;
			}
			entries.add(path);
		}

		final IClasspathEntry e = JavaCore.newSourceEntry(srcPath, new Path[0], binPath);
		entries.add(e);

		jproject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), progress.newChild(1));
	}

	/**
	 * Returns the generated {@link JavaCodegenProperty}s based on the input property map.
	 * 
	 * @param indent the starting tabbed indentation level
	 * @param properties the String to FeatureMap
	 * @return a list of {@link JavaCodegenProperty}
	 */
	public static List<JavaCodegenProperty> createJavaProps(final int indent, final Map<String, AbstractProperty> properties) {
		final List<JavaCodegenProperty> propList = new ArrayList<JavaCodegenProperty>();
		for (final Map.Entry<String, AbstractProperty> entry : properties.entrySet()) {
			final String uniqueName = entry.getKey();
			final AbstractProperty prop = entry.getValue();
			if (prop instanceof Simple) {
				propList.add(new SimpleJavaCodegenProperty(indent, (Simple) prop, uniqueName));
			} else if (prop instanceof SimpleSequence) {
				propList.add(new SimpleSequenceJavaCodegenProperty(indent, (SimpleSequence) prop, uniqueName));
			} else if (prop instanceof Struct) {
				propList.add(new StructJavaCodegenProperty(indent, (Struct) prop, uniqueName));
			} else if (prop instanceof StructSequence) {
				propList.add(new StructSequenceJavaCodegenProperty(indent, (StructSequence) prop, uniqueName));
			}
		}
		return propList;
	}
}
