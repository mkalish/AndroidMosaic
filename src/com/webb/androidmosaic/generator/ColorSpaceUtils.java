package com.webb.androidmosaic.generator;

public class ColorSpaceUtils {
	
	public static LABValue RGBToLAB(int r, int g, int b){
		//rgb to xyz, xyz to lab
		return new LABValue(r,g,b);//TODO, these should be the correct l,a,b values instead
	}
	
	public static void LABColorDistance(Object firstLABValue, Object secondLABValue){
		
	}
}
