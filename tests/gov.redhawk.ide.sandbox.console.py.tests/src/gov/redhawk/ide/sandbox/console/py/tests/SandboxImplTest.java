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
import gov.redhawk.ExtendedCF.Sandbox;
import gov.redhawk.ExtendedCF.SandboxPackage.Depth;
import gov.redhawk.ide.debug.ScaDebugPlugin;
import gov.redhawk.ide.sdr.SdrRoot;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.SystemException;

import CF.DataType;
import CF.Resource;
import CF.ResourceFactory;
import CF.LifeCyclePackage.ReleaseError;
import CF.ResourceFactoryPackage.CreateResourceFailure;

public class SandboxImplTest {
	
	public static final ScheduledExecutorService POOL = Executors.newSingleThreadScheduledExecutor();

	private static SdrRoot root;

	private Sandbox sandbox;

	@BeforeClass
	public static void setupSdr() throws Exception {
		root = SandboxTestsActivator.initSdrRoot();
	}

	@Before
	public void setUp() throws Exception {
		this.sandbox = ScaDebugPlugin.getInstance().getSandbox();
	}

	@After
	public void tearDown() throws Exception {
		while(sandbox.registeredResources().length > 0) {
			for (ResourceDesc resource : this.sandbox.registeredResources()) {
				try {
					resource.resource.releaseObject();
				} catch (SystemException e) {
					// PASS
				}
			}
			Thread.sleep(500);
		}
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
		synchronized(root){
			Assert.assertNotNull(this.sandbox.availableProfiles());
			final String[] profiles = this.sandbox.availableProfiles();
			Assert.assertEquals(2, profiles.length);
			Assert.assertEquals("/components/ExampleComponent/ExampleComponent.spd.xml", profiles[0]);
		}
	}

	@Test
	public void testFileManager() {
		Assert.assertNotNull(this.sandbox.fileManager());
	}
	
	private ResourceFactory getResourceFactory() {
		synchronized(root) {
			ResourceFactory retVal = this.sandbox.getResourceFactory("DCE:2bd01453-1df0-41af-a78e-ffb266abd667");
			Assert.assertNotNull(retVal);
			return retVal;
		}
	}

	@Test
	public void testRegisteredResources() throws CreateResourceFailure, ReleaseError, InterruptedException, ExecutionException, TimeoutException {
		ResourceDesc[] resources = this.sandbox.registeredResources();
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.length);
		final ResourceFactory factory = getResourceFactory();
		Future<Resource> future = POOL.submit(new Callable<Resource>() {

			public Resource call() throws Exception {
				return factory.createResource("helloWorld", new DataType[0]);
			}
			
		});
		
		final Resource resource = future.get(30, TimeUnit.SECONDS);
		resources = this.sandbox.registeredResources();
		Assert.assertEquals(1, resources.length);
		Assert.assertEquals(1, ScaDebugPlugin.getInstance().getLocalSca().getSandboxWaveform().getComponents().size());
		Assert.assertEquals("/components/ExampleComponent/ExampleComponent.spd.xml", resources[0].profile);
	}

	@Test
	public void testGetResourceFactory() {
		final ResourceFactory factory = getResourceFactory();
		Assert.assertNotNull(factory);
	}

	@Test
	public void testGetResourceFactoryByProfile() {
		synchronized(root){
			Assert.assertNotNull(this.sandbox.getResourceFactoryByProfile("/components/ExampleComponent/ExampleComponent.spd.xml"));
		}
	}

	@Test
	public void testRefreshObjectAndDepth() throws CreateResourceFailure, ReleaseError, InterruptedException, ExecutionException, TimeoutException {
		ResourceDesc[] resources = this.sandbox.registeredResources();
		Assert.assertNotNull(resources);
		Assert.assertEquals(0, resources.length);
		final ResourceFactory factory = getResourceFactory();
		Future<Resource> future = POOL.submit(new Callable<Resource>() {

			public Resource call() throws Exception {
				return factory.createResource("helloWorld2", new DataType[0]);
			}
			
		});

		final Resource resource = future.get(30, TimeUnit.SECONDS);
		resources = this.sandbox.registeredResources();
		Assert.assertEquals(1, resources.length);
		this.sandbox.refresh(resource, Depth.SELF);
		this.sandbox.refresh(resource, Depth.FULL);
	}

}
