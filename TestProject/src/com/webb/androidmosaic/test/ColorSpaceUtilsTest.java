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
		LABValue lab = ColorSpaceUtils.RGBToLAB(150, 50, 50);
		Assert.assertEquals(59.84, lab.getL(), 0.01);
		Assert.assertEquals(30.83, lab.getA(), 0.01);
		Assert.assertEquals(13.21, lab.getB(), 0.01);
	}

	public void testLABColorDistance() {
		LABValue lab = ColorSpaceUtils.RGBToLAB(255, 129, 180); 
		LABValue lab2 = ColorSpaceUtils.RGBToLAB(229, 127, 176);
		float deltaE = ColorSpaceUtils.LABColorDistance(lab, lab2);
		Assert.assertEquals(2.54, deltaE, 0.01);
	}
	

}
