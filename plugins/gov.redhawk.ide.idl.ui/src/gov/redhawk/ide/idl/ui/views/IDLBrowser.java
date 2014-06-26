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
package gov.redhawk.ide.idl.ui.views;

import gov.redhawk.ide.RedhawkIdeActivator;
import gov.redhawk.ide.idl.IdlJavaUtil;
import gov.redhawk.ide.idl.ui.IdeIdlUiPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;
import org.jacorb.idl.Interface;

public class IDLBrowser extends ViewPart {

	@Override
	public void createPartControl(final Composite parent) {
		final TreeViewer tree = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Map<String, Interface> interfaces;
		// get search paths from configuration
		final List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());

		// Obtain the interfaces from all IDLs in the search paths and sub-directories
		MultiStatus status = new MultiStatus(IdeIdlUiPlugin.PLUGIN_ID, IStatus.OK, "Problems loading IDLs", null);
		try {
			interfaces = IdlJavaUtil.getInstance().getInterfaces(search_paths, true, status);
			if (!status.isOK()) {
				StatusManager.getManager().handle(status, StatusManager.SHOW | StatusManager.LOG);
			}
		} catch (CoreException e) {
			// Show the exception, use an empty interface mapping
			StatusManager.getManager().handle(
				new Status(e.getStatus().getSeverity(), IdeIdlUiPlugin.PLUGIN_ID, "Failed to get interfaces from " + search_paths, e),
				StatusManager.SHOW | StatusManager.LOG);
			interfaces = new HashMap<String, Interface>();
		}

		final ArrayList<Interface> ifaces = new ArrayList<Interface>();
		ifaces.addAll(interfaces.values());
		Collections.sort(ifaces, new InterfaceComparator());

		tree.setContentProvider(new IdlTreeContentProvider());
		tree.setLabelProvider(new IdlTreeLabelProvider());
		tree.setInput(ifaces);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private class InterfaceComparator implements Comparator<Interface> {

		@Override
		public int compare(final Interface o1, final Interface o2) {
			if (o1 == null) {
				return -1;
			} else if (o2 == null) {
				return 1;
			}
			return o1.id().split(":")[1].compareTo(o2.id().split(":")[1]);
		}

	}
}
