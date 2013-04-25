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
package gov.redhawk.ide.idl;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.idl.internal.RedhawkIdeIdlPlugin;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.jacorb.idl.ConstrTypeSpec;
import org.jacorb.idl.Definition;
import org.jacorb.idl.EnumType;
import org.jacorb.idl.Interface;
import org.jacorb.idl.Method;
import org.jacorb.idl.NameTable;
import org.jacorb.idl.OpDecl;
import org.jacorb.idl.Operation;
import org.jacorb.idl.ParamDecl;
import org.jacorb.idl.ScopedName;
import org.jacorb.idl.StructType;
import org.jacorb.idl.SymbolList;
import org.jacorb.idl.TypeDeclaration;
import org.jacorb.idl.TypeMap;
import org.jacorb.idl.TypeSpec;
import org.jacorb.idl.parser;

public class IdlJavaUtil extends AbstractIdlUtil {

	private static final int INITIAL_TYPESPEC_COUNT = 5000;
	private static Hashtable<String, TypeSpec> typemap = new Hashtable<String, TypeSpec>(INITIAL_TYPESPEC_COUNT);

	private static IdlJavaUtil singletonInstance = null;

	private IdlJavaUtil() {
	}

	/**
	 * Gets the singleton instance of this class.
	 * 
	 * @since 4.0
	 */
	public static IdlJavaUtil getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new IdlJavaUtil();
		}
		return singletonInstance;
	}

	/**
	 * @since 3.0
	 */
	public static Interface getInterface(final TypeSpec ts) {
		if (ts instanceof ConstrTypeSpec) {
			final ConstrTypeSpec cts = (ConstrTypeSpec) ts;
			if (cts.c_type_spec instanceof Interface) {
				return (Interface) cts.c_type_spec;
			}
		}
		return null;
	}

	/**
	 * @since 3.0
	 */
	public static Operation[] getOperations(final Interface iface) {
		return ((iface != null) && (iface.body) != null) ? iface.body.getMethods() : new Operation[0]; // SUPPRESS CHECKSTYLE AvoidInline
	}

	/**
	 * @since 3.0
	 */
	public static Operation[] getOperations(final TypeSpec ts) {
		if (ts instanceof ConstrTypeSpec) {
			final ConstrTypeSpec cts = (ConstrTypeSpec) ts;
			if (cts.c_type_spec instanceof Interface) {
				return ((Interface) cts.c_type_spec).body.getMethods();
			}
		}
		return new Operation[0];
	}

	/**
	 * @since 3.0
	 */
	@SuppressWarnings("unchecked")
	public static ParamDecl[] getParams(final Operation op) {
		if (op instanceof OpDecl) {
			final OpDecl od = (OpDecl) op;
			return (ParamDecl[]) od.paramDecls.toArray(new ParamDecl[od.paramDecls.size()]);
		} else if (op instanceof Method) {
			final Method m = (Method) op;
			if (m.parameterType != null) {
				ParamDecl p = new ParamDecl(0, m.parameterType, "data");
				p.paramAttribute = ParamDecl.MODE_IN;
				return new ParamDecl[] { p };
			}
			return new ParamDecl[0];
		}
		return new ParamDecl[0];
	}

	/**
	 * @since 3.0
	 */
	@SuppressWarnings("unchecked")
	public static ScopedName[] getRaises(final Operation op) {
		if (op instanceof OpDecl) {
			final OpDecl od = (OpDecl) op;
			return (ScopedName[]) od.raisesExpr.nameList.toArray(new ScopedName[od.raisesExpr.nameList.size()]);
		}
		return new ScopedName[0];
	}

	/**
	 * @since 3.0
	 */
	public static String getOpName(final Operation op) {
		if (op instanceof OpDecl) {
			final OpDecl od = (OpDecl) op;
			return od.opName();
		} else if (op instanceof Method) {
			final Method m = (Method) op;
			return m.name();
		}
		return "";
	}

	/**
	 * @since 3.0
	 */
	public static String getReturnType(final Operation op) {
		if (op instanceof OpDecl) {
			final OpDecl od = (OpDecl) op;
			return od.opTypeSpec.typeName().replaceAll("java.lang.", "");
		} else if (op instanceof Method) {
			final Method m = (Method) op;
			return (m.resultType != null) ? m.resultType.typeName().replaceAll("java.lang.", "") : "void"; // SUPPRESS CHECKSTYLE AvoidInline
		}
		return "";
	}

	/**
	 * @since 3.0
	 */
	public static String getParamType(final ParamDecl p) {
		if (p.paramAttribute == ParamDecl.MODE_IN) {
			return p.paramTypeSpec.typeName().replaceAll("java.lang.", "");
		}
		return p.paramTypeSpec.holderName();
	}

	/**
	 * @since 3.0
	 */
	public static String getInitialValue(final Operation op) {
		final String type = IdlJavaUtil.getReturnType(op);
		if ("String".equals(type)) {
			return "\"\"";
		} else if ("boolean".equals(type)) {
			return "false";
		} else if (type.contains(".")) {
			return "null";
		} else if (type.contains("[]")) {
			return "null";
		}
		return "0";
	}

	/**
	 * @since 3.0
	 */
	public static EnumType[] getEnums(final TypeSpec ts) {
		if (ts instanceof ConstrTypeSpec) {
			final ConstrTypeSpec cts = (ConstrTypeSpec) ts;
			if (cts.c_type_spec instanceof Interface) {
				final Vector< ? > elements = ((Interface) cts.c_type_spec).body.v;
				final ArrayList<EnumType> enumList = new ArrayList<EnumType>();
				for (int i = 0; i < elements.size(); ++i) {
					final Definition def = (Definition) elements.get(i);
					if (def.get_declaration() instanceof TypeDeclaration) {
						final TypeDeclaration td = (TypeDeclaration) def.get_declaration();
						if (td.type_decl instanceof EnumType) {
							enumList.add((EnumType) td.type_decl);
						}
					}

				}
				return enumList.toArray(new EnumType[enumList.size()]);
			}
		}
		return new EnumType[0];
	}

	/**
	 * @since 3.0
	 */
	public static StructType[] getStructs(final TypeSpec ts) {
		if (ts instanceof ConstrTypeSpec) {
			final ConstrTypeSpec cts = (ConstrTypeSpec) ts;
			if (cts.c_type_spec instanceof Interface) {
				final Vector< ? > elements = ((Interface) cts.c_type_spec).body.v;
				final ArrayList<StructType> structList = new ArrayList<StructType>();
				for (int i = 0; i < elements.size(); ++i) {
					final Definition def = (Definition) elements.get(i);
					if (def.get_declaration() instanceof StructType) {
						structList.add((StructType) def.get_declaration());
					}

				}
				return structList.toArray(new StructType[structList.size()]);
			}
		}
		return new StructType[0];
	}

	@SuppressWarnings("hiding")
	@Override
	public < Interface > Map<String, Interface> getInterfaces(final List<IPath> searchPaths, final boolean cached, MultiStatus status) throws CoreException {
		return super.getInterfaces(searchPaths, cached, status);
	}

	@SuppressWarnings("hiding")
	@Override
	public < Interface > List<Interface> getInterfaces(final List<IPath> searchPaths, final List<String> requestedInterfaces, final boolean cached, final MultiStatus status)
	        throws CoreException {
		return super.getInterfaces(searchPaths, requestedInterfaces, cached, status);
	}

	@SuppressWarnings("hiding")
	@Override
	public < Interface > Interface getInterface(List<IPath> searchPaths, String requestedInterface, boolean cached) throws CoreException {
		return super.getInterface(searchPaths, requestedInterface, cached);
	}

	@Override
	protected Map<String, Object> interfacesFromFileGeneric(IPath idlFile, List<IPath> idlIncludePaths) throws CoreException {
		final Map<String, Object> retMap = new HashMap<String, Object>();
		final List<Interface> interfaces = IdlJavaUtil.interfacesFromFile(idlFile, idlIncludePaths);
		if (interfaces != null) {
			for (final Interface intf : interfaces) {
				retMap.put(intf.id().split(":")[1], intf);
			}
		}
		return retMap;
	}

	/**
	 * Parses an IDL file and returns a list of its interfaces.
	 *  
	 * @param idlFile The IDL file to parse
	 * @param includePaths The include paths for the IDL parser; if none are provide, the parser uses the IDL include
	 * paths from preferences
	 * @return A list of the interfaces parsed from the IDL file
	 * @throws CoreException A problem occurs while parsing the IDL file
	 * @since 4.0
	 */
	public static List<Interface> interfacesFromFile(final IPath idlFile, List<IPath> includePaths) throws CoreException {
		final StringWriter writer = new StringWriter();
		List<Interface> idlFiles = new ArrayList<Interface>();

		// Format include path string
		String paths;
		if (includePaths == null || includePaths.size() == 0) {
			includePaths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
		}
		paths = "-I";
		for (final IPath includePath : includePaths) {
			paths += includePath.toOSString() + File.pathSeparator;
		}

		try {
			parser.compile(new String[] { "-syntax", "-d", "/tmp/idl", paths, idlFile.toOSString() }, writer);
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Unable to parse " + idlFile, e));
		}

		for (final Object s : NameTable.parsed_interfaces.keySet()) {
			final String intName = (String) s;
			if (TypeMap.map(intName) != null) {
				IdlJavaUtil.typemap.put(intName, TypeMap.map(intName));
			} else if (TypeMap.map(intName.replaceAll("Package", "")) != null) {
				IdlJavaUtil.typemap.put(intName, TypeMap.map(intName.replaceAll("Package", "")));
				IdlJavaUtil.typemap.put(intName.replaceAll("Package", ""), TypeMap.map(intName.replaceAll("Package", "")));
			}
			final TypeSpec ts = IdlJavaUtil.typemap.get(s);
			final Interface i = IdlJavaUtil.getInterface(ts);
			if (i != null) {
				idlFiles.add(i);
			}
		}

		return idlFiles;
	}

	public static void main(final String[] args) {
	}

	/**
	 * @since 3.0
	 */
	public static String getInterfaceName(String repId) {
		repId = repId.split(":")[1].replaceAll("/", ".");
		final Interface i = IdlJavaUtil.getInterface(IdlJavaUtil.typemap.get(repId));
		return i.name();
	}

	/**
	 * @since 3.0
	 */
	public static SymbolList getInheritsInterface(String repId) {
		repId = repId.split(":")[1].replaceAll("/", ".");
		final Interface i = IdlJavaUtil.getInterface(IdlJavaUtil.typemap.get(repId));
		return i.inheritanceSpec;
	}

	/**
	 * @since 3.1
	 */
	public static String getReturnCast(final Operation op) {
		if (IdlJavaUtil.getReturnType(op).equalsIgnoreCase("int")) {
			return "(Integer)";
		} else if (IdlJavaUtil.getReturnType(op).equalsIgnoreCase("char")) {
			return "(Character)";
		} else if (IdlJavaUtil.getReturnType(op).equalsIgnoreCase("boolean")) {
			return "(Boolean)";
		} else if (IdlJavaUtil.getReturnType(op).equalsIgnoreCase("float")) {
			return "(Float)";
		} else if (IdlJavaUtil.getReturnType(op).equalsIgnoreCase("double")) {
			return "(Double)";
		}

		return "";
	}

	/**
	 * Checks that some of the pre-requisites to using the class are met.
	 * 
	 * @return An {@link IStatus} indicating any issues found; problems of severity {@link IStatus#ERROR} indicate
	 * some of the utility class's methods will fail if called
	 * @since 4.0
	 */
	public static IStatus validate() {
		return new Status(IStatus.OK, RedhawkIdeIdlPlugin.PLUGIN_ID, "Validation ok");
	}
}
