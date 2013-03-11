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
import gov.redhawk.sca.util.Debug;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.pydev.core.IInterpreterInfo;
import org.python.pydev.core.IInterpreterManager;
import org.python.pydev.core.MisconfigurationException;
import org.python.pydev.plugin.PydevPlugin;
import org.python.util.PythonInterpreter;

public final class IdlUtil extends AbstractIdlUtil {

	// These strings are used in error messages
	protected static final String PYDEV_PREFS_LOCATION = "Window Menu -> Preferences, Pydev -> Interpreter-Python";
	protected static final String REDHAWK_PREFS_LOCATION = "Window Menu -> Preferences, REDHAWK -> Target Platform";
	protected static final String ERROR_MSG_PYDEV_MISCONFIGURATION = "Pydev's Python interpreter has not been configured (" + PYDEV_PREFS_LOCATION + ")";
	protected static final String ERROR_MSG_NO_PYTHON_INTERPRETER = "Unable to find Python interpreter. Check Pydev's configuration (" + PYDEV_PREFS_LOCATION
	        + ")";
	protected static final String ERROR_MSG_PYTHON_INTERPRETER_DOES_NOT_EXIST = "The Python interpreter was not found. Check the interpreter's path specified in Pydev's configuration ("
	        + PYDEV_PREFS_LOCATION + ")";
	protected static final String ERROR_MSG_PYTHON_INTERPRETER_NOT_EXECUTABLE = "The Python interpreter is not executable. Check the interpreter's path specified in Pydev's configuration ("
	        + PYDEV_PREFS_LOCATION + ")";
	protected static final String ERROR_MSG_CANT_FIND_IMPORTIDL = "The importIDL.py script could not be found. This may indicate a problem with your REDHAWK IDE.";
	protected static final String ERROR_MSG_CANT_RUN_PYTHON_INTERPRETER = "Unable to start the Python interpreter. Check the interpreter specified in Pydev' configuration ("
	        + PYDEV_PREFS_LOCATION + ")";

	private static final Debug DEBUG = new Debug(RedhawkIdeIdlPlugin.PLUGIN_ID, "interfacesFromFile");

	private static IdlUtil singletonInstance = null;

	private IdlUtil() {
	}

	/**
	 * Gets the singleton instance of this class.
	 * 
	 * @since 4.0
	 */
	public static IdlUtil getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new IdlUtil();
		}
		return singletonInstance;
	}

	@SuppressWarnings("hiding")
	@Override
	public < Interface > Map<String, Interface> getInterfaces(final List<IPath> searchPaths, final boolean cached, final MultiStatus status)
	        throws CoreException {
		return super.getInterfaces(searchPaths, cached, status);
	}

	@SuppressWarnings("hiding")
	@Override
	public < Interface > List<Interface> getInterfaces(final List<IPath> searchPaths, final List<String> requestedInterfaces, final boolean cached,
	        final MultiStatus status) throws CoreException {
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
		final List<Interface> interfaces = IdlUtil.interfacesFromFile(idlFile, idlIncludePaths);
		if (interfaces != null) {
			for (final Interface intf : interfaces) {
				retMap.put(intf.getNameSpace() + "/" + intf.getName(), intf);
			}
		}
		return retMap;
	}

	/**
	 * Parses an IDL file and returns a list of its interfaces. 
	 * 
	 * @param idlfile The IDL file to parse
	 * @param includePaths The include paths for the IDL parser; if none are provide, the parser uses the IDL include
	 * paths from preferences
	 * @return A list of the interfaces parsed from the IDL file
	 * @throws CoreException A problem occurs while parsing the IDL file
	 * @since 4.0
	 */
	public static List<Interface> interfacesFromFile(final IPath idlfile, List<IPath> includePaths) throws CoreException {
		// Format include path string
		if (includePaths == null || includePaths.size() == 0) {
			includePaths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
		}
		String paths = "";
		for (final IPath includePath : includePaths) {
			paths += includePath.toOSString() + ",";
		}
		paths = paths.substring(0, paths.length() - 1);

		String command = null;
		final IInterpreterManager pyman = PydevPlugin.getPythonInterpreterManager();
		IInterpreterInfo info = null;
		try {
			if (pyman != null) {
				info = pyman.getDefaultInterpreterInfo(new NullProgressMonitor());
				command = info.getExecutableOrJar();
			}
		} catch (final MisconfigurationException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_PYDEV_MISCONFIGURATION));
		}

		String arg1 = RedhawkIdeIdlPlugin.getDefault().getPythonImportIdlScriptPath().toString();
		if (arg1 == null) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_CANT_FIND_IMPORTIDL));
		}

		final String arg2 = "-f";
		final String arg3 = idlfile.toOSString();
		final String arg4 = "--string";
		final String arg5 = "-i";
		final String arg6 = paths;

		final ProcessBuilder builder = new ProcessBuilder(command, arg1, arg2, arg3, arg4, arg5, arg6);
		Process newprocess = null;
		String parserOutput = null;
		String errorOutput = null;

		try {
			newprocess = builder.start();
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_CANT_RUN_PYTHON_INTERPRETER, e));
		}

		try {
			final InputStream tmpis = newprocess.getInputStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(tmpis));
			StringBuilder buffer = new StringBuilder();

			for (int c = reader.read(); c != -1; c = reader.read()) {
				buffer.append((char) c);
			}

			parserOutput = buffer.toString();

			final InputStream errorStream = newprocess.getErrorStream();
			final BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			buffer = new StringBuilder();

			for (int c = errorReader.read(); c != -1; c = errorReader.read()) {
				buffer.append((char) c);
			}
			errorOutput = buffer.toString();
		} catch (IOException e) {
			// We'll just log - any problems will be detected below
			RedhawkIdeIdlPlugin.logError("Unable to read standard output or standard error after invoking omniIDL.py", e);
		}

		// Create Interfaces from output
		final ArrayList<Interface> ret_interfaces = new ArrayList<Interface>();

		if (errorOutput != null && errorOutput.length() > 0) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Failed to parse: " + idlfile + ". Error: " + errorOutput));
		}
		if (parserOutput == null || parserOutput.length() == 0) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Failed to parse: " + idlfile + ". Error: No output from parser"));
		}

		try {
			final PythonInterpreter interp = new PythonInterpreter();
			// The interfaces are returned one per line
			// Parse each interface individually to handle large files
			for (String iface : parserOutput.split("\n")) {
				if (iface != null && iface.trim().length() > 0) {
					// Wrap the interface in []'s, then parse
					interp.exec("ints = [" + iface + "]");
					final PyObject ints = interp.get("ints");
					for (int i = 0; i < ints.__len__(); i++) {
						final PyObject tmpint = ints.__getitem__(i);
						final String tmp_name = tmpint.__getitem__(new PyString("name")).toString();
						final String tmp_nameSpace = tmpint.__getitem__(new PyString("nameSpace")).toString();
						final String tmp_filename = tmpint.__getitem__(new PyString("filename")).toString();
						final String tmp_fullpath = tmpint.__getitem__(new PyString("fullpath")).toString();

						// create new interface
						final Interface new_interface = new Interface(tmp_name, tmp_nameSpace, tmp_filename, tmp_fullpath);

						// deal with attributes
						final PyObject attrs = tmpint.__getitem__(new PyString("attributes"));
						for (int attrIter = 0; attrIter < attrs.__len__(); attrIter++) {
							final PyObject tmpattr = attrs.__getitem__(attrIter);
							final String attr_name = tmpattr.__getitem__(new PyString("name")).toString();
							final String attr_readonly = tmpattr.__getitem__(new PyString("readonly")).toString();
							final String attr_dataType = tmpattr.__getitem__(new PyString("dataType")).toString();
							final String attr_returnType = tmpattr.__getitem__(new PyString("returnType")).toString();
							
							final PyObject tuple_cxxReturnType = tmpattr.__getitem__(new PyString("cxxReturnType"));
							final String attr_cxxReturnType = tuple_cxxReturnType.__getitem__(0).toString();
							final Boolean atrr_cxxReturnTypeVariable = (tuple_cxxReturnType.__getitem__(1).__int__().getValue() != 0);
							
							final String attr_cxxType = tmpattr.__getitem__(new PyString("cxxType")).toString();

							// create new Attribute
							final Attribute new_attribute = new Attribute(attr_name, "1".equals(attr_readonly), attr_dataType, attr_returnType, attr_cxxReturnType,
									attr_cxxType, atrr_cxxReturnTypeVariable);

							// add the new Attribute to the interface
							new_interface.addAttr(new_attribute);
						}

						// deal with operations
						final PyObject ops = tmpint.__getitem__(new PyString("operations"));
						for (int opIter = 0; opIter < ops.__len__(); opIter++) {
							final PyObject tmpop = ops.__getitem__(opIter);
							final String op_name = tmpop.__getitem__(new PyString("name")).toString();
							final String op_returnType = tmpop.__getitem__(new PyString("returnType")).toString();
							
							final PyObject tuple_cxxReturnType = tmpop.__getitem__(new PyString("cxxReturnType"));
							final String op_cxxReturnType = tuple_cxxReturnType.__getitem__(0).toString();
							final Boolean op_cxxReturnTypeVariable = (tuple_cxxReturnType.__getitem__(1).__int__().getValue() != 0);

							// create new Operation
							final Operation new_operation = new Operation(op_name, op_returnType, op_cxxReturnType, op_cxxReturnTypeVariable);

							// deal with Params
							final PyObject params = tmpop.__getitem__(new PyString("params"));
							for (int paramIter = 0; paramIter < params.__len__(); paramIter++) {
								final PyObject tmpparam = params.__getitem__(paramIter);
								final String param_name = tmpparam.__getitem__(new PyString("name")).toString();
								final String param_dataType = tmpparam.__getitem__(new PyString("dataType")).toString();
								final String param_cxxType = tmpparam.__getitem__(new PyString("cxxType")).toString();
								final String param_direction = tmpparam.__getitem__(new PyString("direction")).toString();

								// create new Param and add to Operation
								final Param new_param = new Param(param_name, param_dataType, param_cxxType, param_direction);
								new_operation.addParam(new_param);
							}

							// deal with Raises
							final PyObject raises = tmpop.__getitem__(new PyString("raises"));
							for (int raisesIter = 0; raisesIter < raises.__len__(); raisesIter++) {
								final PyObject tmpRaises = raises.__getitem__(raisesIter);
								final String raises_name = tmpRaises.__getitem__(new PyString("name")).toString();

								// create new Raises and add to Operation
								final Raises new_raises = new Raises(raises_name);
								new_operation.addRaise(new_raises);
							}

							// add the new Operation to the interface
							new_interface.addOp(new_operation);
						}

						ret_interfaces.add(new_interface);
					}

					// Debug print-outs
					if (IdlUtil.DEBUG.enabled) {
						final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						final PrintStream out = new PrintStream(buffer);
						for (final Interface i : ret_interfaces) {
							out.println("-----------------------------------------------------");
							out.println("\tInterface: " + i.getNameSpace() + ":" + i.getName());
							out.println("\t\t\tfilename: " + i.getFilename());
							out.println("\t\t\tfullpath: " + i.getFullPath());
							out.println("\t\tOperations:");
							for (final Operation o : i.getOperations()) {
								out.println("                name:" + o.getName() + ",returnType:" + o.getReturnType() + ",cxxReturnType:" + o.getCxxReturnType());
								out.println("                params:");
								for (final Param p : o.getParams()) {
									out.println("                    name:" + p.getName() + ",dataType:" + p.getDataType() + ",cxxType:" + p.getCxxType()
											+ ",direction: " + p.getDirection());
								}
							}
						}
						IdlUtil.DEBUG.trace(buffer.toString());
					}
				}

			}

			return ret_interfaces;
		} catch (final Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Unable to parse " + idlfile, e));
		}
	}

	/**
	 * Checks that some of the pre-requisites to using the class are met (i.e. Pydev is configured, omniidl can be
	 * found, etc.)
	 * 
	 * @return An {@link IStatus} indicating any issues found; problems of severity {@link IStatus#ERROR} indicate
	 * some of the utility class's methods will fail if called
	 * @since 4.0
	 */
	public static IStatus validate() {
		// Check the Python interpreter manager
		final IInterpreterManager interpreterManager = PydevPlugin.getPythonInterpreterManager();
		if (!interpreterManager.isConfigured()) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Pydev's Python interpreter has not been configured (" + PYDEV_PREFS_LOCATION + ")");
		}

		// Check the info for the default interpreter
		IInterpreterInfo info;
		try {
			info = interpreterManager.getDefaultInterpreterInfo(new NullProgressMonitor());
		} catch (MisconfigurationException e) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_PYDEV_MISCONFIGURATION, e);
		}

		String command = info.getExecutableOrJar();
		if (command == null) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_NO_PYTHON_INTERPRETER);
		}
		/* Unfortunately this check required Java 1.6 :(
		File commandFile = new File(command);
		if (!commandFile.exists()) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_PYTHON_INTERPRETER_DOES_NOT_EXIST);
		}*/

		// Ensure we can find importIDL.py on the Python path
		IPath importIDLPath = RedhawkIdeIdlPlugin.getDefault().getPythonImportIdlScriptPath();
		if (!importIDLPath.toFile().exists()) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, ERROR_MSG_CANT_FIND_IMPORTIDL);
		}

		// Ensure IDL include directories exist
		List<String> badIncludeDirs = new LinkedList<String>();
		for (IPath path : RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath()) {
			if (!path.toFile().exists()) {
				badIncludeDirs.add(path.toOSString());
			}
		}
		if (badIncludeDirs.size() > 0) {
			MultiStatus status = new MultiStatus(RedhawkIdeIdlPlugin.PLUGIN_ID, IStatus.ERROR,
			        "Some IDL include directories do not exist. Check your REDHAWK configuration (" + REDHAWK_PREFS_LOCATION + ")", null);
			for (String badIncludeDir : badIncludeDirs) {
				status.add(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Directory does not exist: " + badIncludeDir));
			}
			return status;
		}

		// Check import of omniidl
		final ProcessBuilder builder = new ProcessBuilder(command, "-c", "import omniidl");
		try {
			Process proc = builder.start();

			final InputStream errorStream = proc.getErrorStream();
			final BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			final StringBuilder buffer = new StringBuilder();
			for (int c = errorReader.read(); c != -1; c = errorReader.read()) {
				buffer.append((char) c);
			}
			if (buffer.toString().contains("ImportError")) {
				return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID,
				        "The module omniidl was not found on the Python interpreter's path. Ensure you are using the correct Python interpreter ("
				                + PYDEV_PREFS_LOCATION + "), then check your interpreter's path.");
			} else if (buffer.length() > 0) {
				return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "An error occurred while testing the omniidl parser: " + buffer.toString());
			}
		} catch (Exception e) {
			return new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "An exception occurred while testing the omniidl parser", e);
		}

		return new Status(IStatus.OK, RedhawkIdeIdlPlugin.PLUGIN_ID, "Validation ok");
	}

	public static void main(final String[] args) {
		// BEGIN DEBUG CODE
		System.out.println("TO RUN THIS, YOU NEED TO ADD org.eclipse.ui TO THE");
		System.out.println("required plugins IN THE gov.redhawk.ide/plugin.xml FILE\n\n");
		System.out.println("OSSIEHOME: " + System.getenv("OSSIEHOME"));

		final List<IPath> mysearchPaths = new ArrayList<IPath>();
		final IPath foo = new Path(System.getenv("OSSIEHOME") + "/share/idl/ossie");
		mysearchPaths.add(foo);
		final List<String> scdinfo = Arrays.asList("BULKIO/dataFloat", "BULKIO/dataXML");
		final String str_scdinfo = "<" + scdinfo.get(0) + "," + scdinfo.get(1) + ">";

		List<Interface> myints;
		try {
			myints = IdlUtil.getInstance().getInterfaces(mysearchPaths, scdinfo, false, null);
		} catch (CoreException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("\nInterfaces (objects) found for request " + str_scdinfo);
		for (final Interface tmpi : myints) {
			System.out.println("  " + tmpi.getNameSpace() + ":" + tmpi.getName());
		}

		final List<IPath> mypaths2 = AbstractIdlUtil.findIdlPaths(mysearchPaths);
		System.out.println("\nIDL Files found for paths " + mysearchPaths);
		for (final IPath tmpip : mypaths2) {
			System.out.println("  " + tmpip);
		}
		// END DEBUG CODE
	}
}
