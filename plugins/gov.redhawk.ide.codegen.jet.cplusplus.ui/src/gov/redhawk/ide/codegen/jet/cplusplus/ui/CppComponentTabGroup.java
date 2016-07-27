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
package gov.redhawk.ide.codegen.jet.cplusplus.ui;

import gov.redhawk.ide.debug.ui.tabs.ComponentPropertiesTab;
import gov.redhawk.ide.debug.ui.tabs.LocalComponentMainTab;

import org.eclipse.cdt.launch.ui.ApplicationCDebuggerTab;
import org.eclipse.cdt.launch.ui.CArgumentsTab;
import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

/**
 * Controls which UI tabs make up the Redhawk "Sandbox C++ Component" launch configuration
 */
public class CppComponentTabGroup extends AbstractLaunchConfigurationTabGroup {

	public CppComponentTabGroup() {
	}

	@Override
	public void createTabs(final ILaunchConfigurationDialog dialog, final String mode) {
		final ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
		        new LocalComponentMainTab(),
		        new ComponentPropertiesTab(),
		        new CMainTab(),
		        new CArgumentsTab(),
		        new EnvironmentTab(),
		        new ApplicationCDebuggerTab(),
		        new SourceLookupTab(),
		        new RefreshTab(),
		        new CommonTab()
		};
		setTabs(tabs);
	}
}
