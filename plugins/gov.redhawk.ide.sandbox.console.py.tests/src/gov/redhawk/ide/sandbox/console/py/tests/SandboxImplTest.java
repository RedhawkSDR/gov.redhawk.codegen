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
package gov.redhawk.ide.sandbox.console.py.tests;

import gov.redhawk.ExtendedCF.ResourceDesc;
import gov.redhawk.ExtendedCF.SandboxPackage.Depth;
import gov.redhawk.ide.debug.ScaDebugPlugin;
import gov.redhawk.ide.debug.internal.SandboxImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CF.DataType;
import CF.Resource;
import CF.ResourceFactory;
import CF.LifeCyclePackage.ReleaseError;
import CF.ResourceFactoryPackage.CreateResourceFailure;

public class SandboxImplTest {

	private SandboxImpl sandbox;

	@BeforeClass
	public static void setupSdr() throws Exception {
		SandboxTestsActivator.initSdrRoot();
	}

	@Before
	public void setUp() throws Exception {
		this.sandbox = new SandboxImpl(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeviceManager() {
		Assert.assertNotNull(this.sandbox.deviceManager());
	}

	@Test
	public void testNamingContext() {
		Assert.assertNotNull(this.sandbox.namingContext());
	}

	@Test
	public void testAvailableProfiles() {
		Assert.assertNotNull(this.sandbox.availableProfiles());
		final String[] profiles = this.sandbox.availableProfiles();
		Assert.assertEquals(2, profiles.length);
		Assert.assertEquals("components/ExampleComponent/ExampleComponent.spd.xml", profiles[0]);
	}

	@Test
	public void testFileManager() {
		Assert.assertNotNull(this.sandbox.fileManager());
	}

	@Test
	public void testRegisteredResources() throws CreateResourceFailure, ReleaseError, InterruptedException {
		ResourceDesc[] resources = this.sandbox.registeredResources();
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.length);
		final ResourceFactory factory = this.sandbox.getResourceFactory("DCE:2bd01453-1df0-41af-a78e-ffb266abd667");
		Assert.assertNotNull(factory);
		final Resource resource = factory.createResource("helloWorld", new DataType[0]);
		resources = this.sandbox.registeredResources();
		Assert.assertEquals(1, resources.length);
		Assert.assertEquals(1, ScaDebugPlugin.getInstance().getLocalSca().getSandboxWaveform().getComponents().size());
		Assert.assertEquals("components/ExampleComponent/ExampleComponent.spd.xml", resources[0].profile);
		resource.releaseObject();
		Thread.sleep(7000);
		Assert.assertEquals(0, this.sandbox.registeredResources().length);
	}

	@Test
	public void testGetResourceFactory() {
		Assert.assertNotNull(this.sandbox.getResourceFactory("DCE:2bd01453-1df0-41af-a78e-ffb266abd667"));
	}

	@Test
	public void testGetResourceFactoryByProfile() {
		Assert.assertNotNull(this.sandbox.getResourceFactoryByProfile("components/ExampleComponent/ExampleComponent.spd.xml"));
	}

	@Test
	public void testRefreshObjectAndDepth() throws CreateResourceFailure, ReleaseError, InterruptedException {
		ResourceDesc[] resources = this.sandbox.registeredResources();
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.length);
		final ResourceFactory factory = this.sandbox.getResourceFactory("DCE:2bd01453-1df0-41af-a78e-ffb266abd667");
		Assert.assertNotNull(factory);
		final Resource resource = factory.createResource("helloWorld2", new DataType[0]);
		resources = this.sandbox.registeredResources();
		Assert.assertEquals(1, resources.length);
		this.sandbox.refresh(resource, Depth.SELF);
		this.sandbox.refresh(resource, Depth.FULL);
		resource.releaseObject();
		Thread.sleep(7000);
		Assert.assertEquals(0, this.sandbox.registeredResources().length);
	}

}
