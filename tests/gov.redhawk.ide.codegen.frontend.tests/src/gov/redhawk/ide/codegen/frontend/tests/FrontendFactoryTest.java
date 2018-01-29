package gov.redhawk.ide.codegen.frontend.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Assert;
import org.junit.Test;

import gov.redhawk.ide.codegen.frontend.FeiDevice;
import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.scd.SoftwareComponent;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

public class FrontendFactoryTest {

	private static final String PLUGIN_ID = "gov.redhawk.ide.codegen.frontend.tests";

	@Test
	public void rxTuner() {
		SoftPkg spd = load("testFiles/RxTuner/RxTuner.spd.xml");
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);
		assertGPS(feiDevice, false, false);
		assertRx(feiDevice, true, 1, false, true);
		assertTx(feiDevice, false, 0);
		assertOther(feiDevice, true, false);
	}

	@Test
	public void ddc() {
		SoftPkg spd = load("testFiles/DDC/DDC.spd.xml");
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);
		assertGPS(feiDevice, false, false);
		assertRx(feiDevice, true, 0, true, true);
		assertTx(feiDevice, false, 0);
		assertOther(feiDevice, true, false);
	}

	@Test
	public void downConverter() {
		SoftPkg spd = load("testFiles/DownConverter/DownConverter.spd.xml");
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);
		assertGPS(feiDevice, true, true);
		assertRx(feiDevice, true, 4, false, false);
		assertTx(feiDevice, false, 0);
		assertOther(feiDevice, false, false);
	}

	@Test
	public void transmitter() {
		SoftPkg spd = load("testFiles/Transmitter/Transmitter.spd.xml");
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);
		assertGPS(feiDevice, false, false);
		assertRx(feiDevice, false, 0, false, false);
		assertTx(feiDevice, true, 1);
		assertOther(feiDevice, false, false);
	}

	@Test
	public void scanner() {
		SoftPkg spd = load("testFiles/Scanner/Scanner.spd.xml");
		Properties prf = spd.getPropertyFile().getProperties();
		SoftwareComponent scd = spd.getDescriptor().getComponent();
		FeiDevice feiDevice = FrontendFactory.eINSTANCE.createFeiDevice(prf, scd);
		assertGPS(feiDevice, false, false);
		assertRx(feiDevice, true, 1, false, true);
		assertTx(feiDevice, false, 0);
		assertOther(feiDevice, true, true);
	}

	private SoftPkg load(String spdPath) {
		ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		URI spdUri = URI.createPlatformPluginURI("/" + PLUGIN_ID + "/" + spdPath, true);
		return SoftPkg.Util.getSoftPkg(resourceSet.getResource(spdUri, true));
	}

	private void assertGPS(FeiDevice feiDevice, boolean ingestGPS, boolean outputGPS) {
		Assert.assertEquals(ingestGPS, feiDevice.isIngestsGPS());
		Assert.assertEquals(outputGPS, feiDevice.isOutputsGPS());
	}

	private void assertRx(FeiDevice feiDevice, boolean rxTuner, int analogInputs, boolean digitalInput, boolean digitalOutput) {
		Assert.assertEquals(rxTuner, feiDevice.isRxTuner());
		Assert.assertEquals(analogInputs, feiDevice.getNumberOfAnalogInputs());
		Assert.assertEquals(digitalInput, feiDevice.isHasDigitalInput());
		Assert.assertEquals(digitalOutput, feiDevice.isHasDigitalOutput());
	}

	private void assertTx(FeiDevice feiDevice, boolean txTuner, int digitalTxInputs) {
		Assert.assertEquals(txTuner, feiDevice.isTxTuner());
		Assert.assertEquals(digitalTxInputs, feiDevice.getNumberOfDigitalInputsForTx());
	}

	private void assertOther(FeiDevice feiDevice, boolean multiOut, boolean scanner) {
		Assert.assertEquals(multiOut, feiDevice.isMultiOut());
		Assert.assertEquals(scanner, feiDevice.isScanner());
	}
}
