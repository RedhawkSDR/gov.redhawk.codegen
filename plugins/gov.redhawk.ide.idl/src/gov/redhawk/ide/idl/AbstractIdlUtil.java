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

import gov.redhawk.ide.idl.internal.RedhawkIdeIdlPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * @since 4.0
 */
public abstract class AbstractIdlUtil {

	/**
	 * Maps a directory's {@link IPath} to a cached {@link List} of all IDL files in it and its
	 * sub-directories (as {@link IPath}s).
	 */
	private final Map<IPath, List<IPath>> cachedIdlPaths = new HashMap<IPath, List<IPath>>();

	/**
	 * Maps an IDL file's {@link IPath} to a cached {@link List} of the interface names it contains (expressed as
	 * the {@link String} "NAMESPACE/NAME").
	 */
	private final Map<IPath, List<String>> cachedIdlInterfaceNames = new HashMap<IPath, List<String>>();

	/**
	 * Maps cached interface names (expressed as a {@link String} "NAMESPACE/NAME") to an {@link Object} representing
	 * the interface's parsed information. The run-time type of the {@link Object} varies according to the particular
	 * sub-class's implementation.
	 */
	private final Map<String, Object> cachedIdlInterfaces = new HashMap<String, Object>();

	protected AbstractIdlUtil() {
	}

	/**
	 * Gets a list of all files with the extension idl found in the search path(s) and their sub-directories. Search
	 * paths may be individual files to consider or directories to search.
	 * 
	 * @param searchPaths The files/directories to examine for idl files (should be absolute paths)
	 * @return A list of absolute paths to files with extension idl
	 * @since 4.0
	 */
	public static List<IPath> findIdlPaths(final List<IPath> searchPaths) {
		final List<IPath> idlsFound = new LinkedList<IPath>();
		for (final IPath searchPath : searchPaths) {
			final File f = searchPath.toFile();
			findIdlPaths(f, idlsFound);
		}
		return idlsFound;
	}

	/**
	 * The method {@link #findIdlPaths(List)} is preferred to this method.
	 * 
	 * @see #findIdlPaths(List)
	 * @since 4.0
	 */
	public static List<IPath> findIdlPaths(final IPath[] searchPaths) {
		return findIdlPaths(Arrays.asList(searchPaths));
	}

	/**
	 * If a directory is specified, searches the directory and all its sub-directories for files with the extension
	 * ".idl" and appends them to the list. If a file is specified, the file is added to the list if its extension is
	 * ".idl". 
	 * 
	 * @param fileOrDir The file to be considered or directory to be searched
	 * @param idlFiles The list to append found idl's to
	 * @since 4.0
	 */
	public static List<IPath> findIdlPaths(final File fileOrDir, final List<IPath> idlFiles) {
		if (fileOrDir.exists() && fileOrDir.canRead()) {
			if (fileOrDir.isDirectory()) {
				final File[] dirlist = fileOrDir.listFiles();
				for (final File tmpfile : dirlist) {
					findIdlPaths(tmpfile, idlFiles);
				}
			} else {
				final Path fpath = new Path(fileOrDir.getPath());
				if ("idl".equals(fpath.getFileExtension())) {
					idlFiles.add(fpath);
				}
			}
		}
		return idlFiles;
	}

	/**
	 * Gets all interfaces in the specified search paths and their sub-directories. Any discovered results are cached
	 * to allow future requests to be serviced more quickly.
	 *  
	 * @param <T> The particular object type the sub-class uses to represent parsed interfaces
	 * @param searchPaths A list of paths to search for IDL files; the search includes sub-directories // TODO: Accurate? Add info on includes?
	 * @param cached True to use previously cached data when possible, false to ensure all data is (re-)loaded
	 * @param status If non-null, requests that {@link CoreException}s be added to the {@link MultiStatus} rather than
	 * thrown
	 * @return A mapping of interface names (expressed as "NAMESPACE/NAME") to interface objects
	 * @throws CoreException A problem occurs while retrieving interfaces
	 * @throws IllegalArgumentException No search paths are specified
	 */
	protected < T > Map<String, T> getInterfaces(final List<IPath> searchPaths, final boolean cached, final MultiStatus status) throws CoreException {
		if (searchPaths == null || searchPaths.size() == 0) {
			throw new IllegalArgumentException("Must specify one or more search paths");
		}
		
		MultiStatus localStatus = status;
		if (localStatus == null) {
			localStatus = new MultiStatus(RedhawkIdeIdlPlugin.PLUGIN_ID, IStatus.OK, "Problems searching for interfaces", null);
		}

		// First, create a unique list of IDL files from the search paths
		Set<IPath> idlFiles = new HashSet<IPath>();
		for (IPath searchPath : searchPaths) {
			// If we were instructed to use the cache, get the list of IDL files from the cache (if possible) and merge
			// those into our set. Otherwise perform a search on the path for IDLs, cache the results, and then merge
			// them into our set.
			if (cached && this.cachedIdlPaths.containsKey(searchPath)) {
				idlFiles.addAll(this.cachedIdlPaths.get(searchPath));
			} else {
				List<IPath> discoveredIdlFiles = new LinkedList<IPath>();
				AbstractIdlUtil.findIdlPaths(searchPath.toFile(), discoveredIdlFiles);
				if (discoveredIdlFiles.size() > 0) {
					this.cachedIdlPaths.put(searchPath, discoveredIdlFiles);
					idlFiles.addAll(discoveredIdlFiles);
				}
			}
		}

		// Now, create a mapping of IDL names to interface objects
		Map<String, Object> retInterfaces = new HashMap<String, Object>();

		// If instructed to use the cache, find the parsed interface information from each IDL file in the cache. We'll
		// remove IDL files from our list if we find their info in our cache. When we're done, our list of IDL files
		// will contain IDL files we still don't have info for.
		if (cached) {
			Iterator<IPath> iterator = idlFiles.iterator();
			while (iterator.hasNext()) {
				IPath idlFile = iterator.next();
				if (cachedIdlInterfaceNames.containsKey(idlFile)) {
					iterator.remove();
					for (String interfaceName : cachedIdlInterfaceNames.get(idlFile)) {
						if (cachedIdlInterfaces.containsKey(interfaceName)) {
							retInterfaces.put(interfaceName, cachedIdlInterfaces.get(interfaceName));
						} else {
							// This case shouldn't happen; it indicates we somehow know the interfaces in the IDL file
							// (and thus must have parsed it), and yet don't have the interface object...
							RedhawkIdeIdlPlugin.logError("Internal error - Unable to find cached interface specified by cached IDL file", null);
						}
					}
				}
			}
		}

		// For any IDL files we still don't have interfaces from, parse them using the sub-class's implementation
		for (IPath idlFile : idlFiles) {
			Map<String, Object> idlInterfaces;
			try {
				idlInterfaces = interfacesFromFileGeneric(idlFile, searchPaths); // TODO: Is using searchPaths here ok?
				cachedIdlInterfaceNames.put(idlFile, new LinkedList<String>(idlInterfaces.keySet()));
				cachedIdlInterfaces.putAll(idlInterfaces);
				retInterfaces.putAll(idlInterfaces);
			} catch (CoreException e) {
				localStatus.add(new Status(e.getStatus().getSeverity(), RedhawkIdeIdlPlugin.PLUGIN_ID, "Failed to get interfaces from file " + idlFile, e));
			}
		}
		
		// If the user didn't request status and we had errors, throw a CoreException
		if (status == null && localStatus.getSeverity() == IStatus.ERROR) {
			throw new CoreException(localStatus);
		}

		@SuppressWarnings("unchecked")
		final Map<String, T> retInterfacesCast = (Map<String, T>) retInterfaces;
		return retInterfacesCast;
	}

	/**
	 * Gets a list of requested interfaces. Any discovered results are cached to allow future requests to be serviced
	 * more quickly.
	 * 
	 * @param <T> The particular object type the sub-class uses to represent parsed interfaces
	 * @param searchPaths The search paths to look in for IDL files (sub-directories are also searched) // TODO: Accurate? Add info on includes?
	 * @param requestedInterfaces the interfaces to search for, specified as "NAMESPACE/NAME"
	 * @param cached True to attempt to use previously cached results; false to ensure results are loaded from disk
	 * @param status If non-null, requests that {@link CoreException}s be added to the {@link MultiStatus} rather than
	 * thrown
	 * @return A list of the requested interfaces; interfaces which cannot be found are omitted
	 * @throws CoreException An unrecoverable error occurred while retrieving interfaces, or an interface could not be
	 * found and there were recoverable errors which may have prevented the interface from being found 
	 * @since 3.0
	 */
	protected < T > List<T> getInterfaces(final List<IPath> searchPaths, final List<String> requestedInterfaces, final boolean cached, final MultiStatus status)
	        throws CoreException {
		MultiStatus localStatus = status;
		if (localStatus == null) {
			localStatus = new MultiStatus(RedhawkIdeIdlPlugin.PLUGIN_ID, IStatus.OK, "Problems searching for interfaces", null);
		}
		Map<String, Object> idlInterfaces;
		
		// We'll only invoke getInterfaces if we're told to not use the cache, or if we later have trouble finding something in the cache
		boolean invokedGetInterfaces;
		if (cached) {
			idlInterfaces = cachedIdlInterfaces;
			invokedGetInterfaces = false;
		} else {
			idlInterfaces = getInterfaces(searchPaths, cached, localStatus);
			invokedGetInterfaces = true;
		}

		List<Object> retInt = new ArrayList<Object>(requestedInterfaces.size());
		for (String requestedInterface : requestedInterfaces) {
			// See if we have the interface already
			Object iface = idlInterfaces.get(requestedInterface);
			
			// If we didn't find it, try by the last 2 segments
			if (iface == null) {
				final String[] ifaceSplit = requestedInterface.split("/");
				if (ifaceSplit.length > 1) {
					iface = idlInterfaces.get(ifaceSplit[ifaceSplit.length - 2] + "/" + ifaceSplit[ifaceSplit.length - 1]);
				}
			}

			// If still not found, see if we haven't invoked getInterfaces yet
			if (iface == null && !invokedGetInterfaces) {
				idlInterfaces = getInterfaces(searchPaths, cached, localStatus);
				invokedGetInterfaces = true;
				
				// Now see if we have the interface
				iface = idlInterfaces.get(requestedInterface);
				
				// If we didn't find it, try by the last 2 segments
				if (iface == null) {
					final String[] ifaceSplit = requestedInterface.split("/");
					if (ifaceSplit.length > 1) {
						iface = idlInterfaces.get(ifaceSplit[ifaceSplit.length - 2] + "/" + ifaceSplit[ifaceSplit.length - 1]);
					}
				} 
			}

			// If we didn't come up with an interface
			if (iface == null) {
				// Add a warning
				localStatus.add(new Status(IStatus.WARNING, RedhawkIdeIdlPlugin.PLUGIN_ID, "Could not find interface " + requestedInterface));
				
				// If status wasn't requested and there are errors, throw a CoreException
				if (status == null) {
					throw new CoreException(localStatus);
				}
			} else {
				retInt.add(iface);
			}
		}
		
		@SuppressWarnings("unchecked")
		List<T> retIntCast = (List<T>) retInt;
		return retIntCast;
	}

	/**
	 * Gets a requested interface. Any discovered results are cached to allow future requests to be serviced more
	 * quickly.
	 * 
	 * @param <T> The particular object type the sub-class uses to represent parsed interfaces
	 * @param searchPaths The search paths to look in for IDL files (sub-directories are also searched) // TODO: Accurate? Add info on includes?
	 * @param requestedInterface The desired interface, specified as "NAMESPACE/NAME"
	 * @param cached True to attempt to use previously cached results; false to ensure results are loaded from disk
	 * @return The interface, if its is found; otherwise null
	 * @throws CoreException An unrecoverable error occurred while retrieving interfaces, or the interface could not be
	 * found and there were recoverable errors which may have prevented the interface from being found
	 */
	protected < T > T getInterface(final List<IPath> searchPaths, final String requestedInterface, final boolean cached) throws CoreException {
		MultiStatus status = new MultiStatus(RedhawkIdeIdlPlugin.PLUGIN_ID, IStatus.OK, "Problems while searching for interface " + requestedInterface, null);
		List<T> interfaces = getInterfaces(searchPaths, Arrays.asList(requestedInterface), cached, status);
		if (interfaces.size() == 0) {
			if (status.getSeverity() == IStatus.ERROR) {
				throw new CoreException(status); 
			} else {
				return null;
			}
		} else if (interfaces.size() == 1) {
			return interfaces.get(0);
		} else {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID,
			        "Internal error - received more than one interface matching request"));
		}
	}

	/**
	 * Parses an IDL file using the sub-class's parser. The {@link Map} should relate interface names (expressed as
	 * "NAMESPACE/NAME") to the particular object the sub-class uses to represent a parsed interface.
	 * <p />
	 * The interface objects should all derive from a common type, which should be used when invoking
	 * {@link #getInterfaces(List, List, boolean, MultiStatus)} and {@link #getInterfaces(List, boolean, MultiStatus)}.
	 *    
	 * @param idlFile The IDL file to be parsed
	 * @param idlIncludePaths The IDL include paths to pass to the parser
	 * @return A mapping of interface names to interface objects
	 * @throws CoreException A problem occurred while parsing the IDL file
	 */
	protected abstract Map<String, Object> interfacesFromFileGeneric(IPath idlFile, List<IPath> idlIncludePaths) throws CoreException;

}
