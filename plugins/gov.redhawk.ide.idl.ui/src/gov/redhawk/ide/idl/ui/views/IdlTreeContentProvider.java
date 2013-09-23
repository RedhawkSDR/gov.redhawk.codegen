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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jacorb.idl.Interface;

public class IdlTreeContentProvider implements ITreeContentProvider {
	private Map<String, List<Interface>> moduleHash;

	@Override
	public Object[] getElements(final Object inputElement) {
		//iterate through list and organize based on module name (namespace)
		this.moduleHash = new HashMap<String, List<Interface>>();

		if (inputElement instanceof Collection) {
			final Collection< ? > tmpList = (Collection< ? >) inputElement;
			for (final Object obj : tmpList) {
				if (obj instanceof Interface) {
					final Interface i = (Interface) obj;
					if (!this.moduleHash.containsKey(i.pack_name)) {
						this.moduleHash.put(i.pack_name, new ArrayList<Interface>());
					}
					this.moduleHash.get(i.pack_name).add(i);
				}
			}
		}

		return this.moduleHash.keySet().toArray();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof String) {
			final String nameSpace = (String) parentElement;
			if (!this.moduleHash.get(nameSpace).isEmpty()) {
				return this.moduleHash.get(nameSpace).toArray();
			} else {
				return Collections.EMPTY_LIST.toArray();
			}
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof Interface) {
			final Interface el_int = (Interface) element;
			final Set<String> keyset = this.moduleHash.keySet();
			if (keyset.contains(el_int.pack_name)) {
				return el_int.pack_name;
			}
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof String) {
			final String nameSpace = (String) element;
			return !this.moduleHash.get(nameSpace).isEmpty();
		}
		return false;
	}
}
