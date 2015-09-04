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

import gov.redhawk.eclipsecorba.idl.impl.SpecificationImpl;
import gov.redhawk.eclipsecorba.idl.util.IdlResourceParseException;
import gov.redhawk.ide.builders.SCAMarkerUtil;
import gov.redhawk.ide.idl.generator.internal.MakefileAmIdeTemplate;
import gov.redhawk.ide.idl.generator.newidl.GeneratorArgs;
import gov.redhawk.ide.idl.internal.RedhawkIdeIdlPlugin;
import gov.redhawk.ide.natures.ScaProjectNature;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Resource visitor used for incremental REDHAWK IDL project builds.
 */
class IDLBuildVisitor implements IResourceDeltaVisitor, IResourceVisitor {

	private ResourceSet set = ScaResourceFactoryUtil.createResourceSet();

	private SCAMarkerUtil markerUtil = SCAMarkerUtil.INSTANCE;

	public static final String IDL_MARKER_TYPE = "gov.redhawk.ide.idl.problem"; //$NON-NLS-1$
	
	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IProject) {
			return true;
		}
		
		if (resource.exists()) {
			resource.deleteMarkers(IDL_MARKER_TYPE, true, IResource.DEPTH_ZERO);
			
			try {
				URI uri = URI.createFileURI(resource.getLocation().toString());
				if ((uri != null) && (uri.path().endsWith(".idl"))) {
					Resource eResource;
					// Verify that we can parse the IDL
					try {
						eResource = set.getResource(uri, true);
					} catch (final WrappedException e) {
						final IMarker marker = resource.createMarker(IDL_MARKER_TYPE);
						marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
						marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
						marker.setAttribute(IMarker.SOURCE_ID, "");
						
						
						if (e.getCause() instanceof IdlResourceParseException) {
							final IdlResourceParseException pe = (IdlResourceParseException) e.getCause();
							marker.setAttribute(IMarker.MESSAGE, "Syntax error in IDL file");
							marker.setAttribute(IMarker.LOCATION, "line " + pe.getLine());
							marker.setAttribute(IMarker.LINE_NUMBER, pe.getLine());
						} else {
							marker.setAttribute(IMarker.LOCATION, " ");
							marker.setAttribute(IMarker.MESSAGE, e.getCause().toString());
						}
						
						return false;
					}
					
					final Diagnostic diagnostic = this.markerUtil.getDiagnostician().validate(eResource.getEObject("/"));
					this.markerUtil.createMarkers(eResource, diagnostic);
					EObject root = eResource.getContents().get(0);
					SpecificationImpl idlFile = (SpecificationImpl) root;
					return true;
				}
			} catch (final Exception e) { // SUPPRESS CHECKSTYLE Fallback
				return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		return visit(resource);
	}

}

/**
 * @since 4.1
 */
public class IdlProjectBuilder extends IncrementalProjectBuilder {

	public static final String MODULE_NAME_ARG = "gov.redhawk.ide.idl.moduleName";
	/**
	 * The name of the builder registered with the extension point
	 */
	public static final String BUILDER_NAME = "gov.redhawk.ide.idl.builder";

	/**
	 * Matches files ending with .idl
	 */
	private static final Pattern IDL_FILENAME = Pattern.compile(".*\\.idl$");
	
	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		try {
			final IProject project = getProject();

			// Remove our markers, if any
			final IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
			for (final IMarker marker : markers) {
				if (marker.getAttribute(IMarker.SOURCE_ID, "").equals(IdlProjectBuilder.class.getCanonicalName())) {
					marker.delete();
				}
			}

			// Do nothing if this project doesn't have the correct natures
			if (project.getNature(CProjectNature.C_NATURE_ID) == null && project.getNature(CCProjectNature.CC_NATURE_ID) == null) {
				return null;
			}
			if (project.getNature(ScaProjectNature.ID) == null) {
				return null;
			}

			// We always rebuild the Makefile.am.ide
			generateMakefileAmInclude(monitor, args);
			
			if (kind == IncrementalProjectBuilder.FULL_BUILD) {
				getProject().accept(new IDLBuildVisitor());
			} else {
				final IResourceDelta delta = getDelta(getProject());
				if (delta == null) {
					getProject().accept(new IDLBuildVisitor());
				} else {
					delta.accept(new IDLBuildVisitor());
				}
			}
			
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
		return null;
	}
	
	/**
	 *
	 */
	private void generateMakefileAmInclude(final IProgressMonitor monitor, @SuppressWarnings("rawtypes") Map args) throws CoreException {
		// Get the current configuration from CDT
		final IProject project = getProject();

		ArrayList<String> idlFiles = new ArrayList<String>();
		for (IResource member : project.members()) {
			if ((member instanceof IFile) && (IDL_FILENAME.matcher(member.getName()).matches())) {
			    idlFiles.add(member.getName());	
			}
		}

		final SubMonitor progress = SubMonitor.convert(monitor, "Creating auto-inclusion Makefile", 1);
		
		final GeneratorArgs gargs = new GeneratorArgs();
		gargs.setIdlFiles(idlFiles);
		if (args != null) {
			String moduleName = (String) args.get(MODULE_NAME_ARG);
			if (moduleName == null) {
				moduleName = "";
			}
			gargs.setInterfaceName(moduleName);
		}

		final String makefileAmIde = new MakefileAmIdeTemplate().generate(gargs);
		final IFile makefileAmIdeFile = project.getFile("Makefile.am.ide");		
		
		try {
			if (makefileAmIdeFile.exists()) {
				makefileAmIdeFile.setContents(new ByteArrayInputStream(makefileAmIde.getBytes("UTF-8")), IResource.FORCE, progress.newChild(1));
			} else {
				makefileAmIdeFile.create(new ByteArrayInputStream(makefileAmIde.getBytes("UTF-8")), true, progress.newChild(1));
			}
		} catch (UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, RedhawkIdeIdlPlugin.PLUGIN_ID, "Internal Error", e));
		}
	}
	
}
