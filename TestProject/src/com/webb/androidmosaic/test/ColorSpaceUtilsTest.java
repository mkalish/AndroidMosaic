package com.webb.androidmosaic.test;

import com.webb.androidmosaic.generation.ColorSpaceUtils;
import com.webb.androidmosaic.generation.LABValue;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ColorSpaceUtilsTest extends TestCase {

	public ColorSpaceUtilsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRGBToLAB() {
		LABValue lab = ColorSpaceUtils.RGBToLAB(150, 50, 50); //CURRENTLY TO XYZ
		Assert.assertEquals(0,lab.getL());
		Assert.assertEquals(0,lab.getA());
		Assert.assertEquals(0,lab.getB());

	}

	public void testLABColorDistance() {
		fail("Not yet implemented");
	}

}
