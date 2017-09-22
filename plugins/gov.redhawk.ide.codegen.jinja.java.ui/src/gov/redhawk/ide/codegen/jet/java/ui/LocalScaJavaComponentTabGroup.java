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
package gov.redhawk.ide.codegen.jet.java.ui;

import gov.redhawk.ide.debug.ui.tabs.ComponentPropertiesTab;
import gov.redhawk.ide.debug.ui.tabs.LocalComponentMainTab;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab;

/**
 * 
 */
public class LocalScaJavaComponentTabGroup extends AbstractLaunchConfigurationTabGroup {

	/**
	 * 
	 */
	public LocalScaJavaComponentTabGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(final ILaunchConfigurationDialog dialog, final String mode) {
		final ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
		        new LocalComponentMainTab(),
		        new ComponentPropertiesTab(),
		        new JavaMainTab(),
		        new JavaArgumentsTab(),
		        new JavaJRETab(),
		        new JavaClasspathTab(),
		        new SourceLookupTab(),
		        new EnvironmentTab(),
		        new CommonTab()
		};
		setTabs(tabs);
	}

}
