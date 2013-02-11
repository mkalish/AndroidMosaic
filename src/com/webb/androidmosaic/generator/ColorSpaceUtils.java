package com.webb.androidmosaic.generator;

public class ColorSpaceUtils {
	
	//CIE1976
	public static LABValue RGBToLAB(int r, int g, int b){
		//rgb to xyz, xyz to lab
		return new LABValue(r,g,b);//TODO, these should be the correct l,a,b values instead
	}
	
	//CIE94 color difference
	public static int LABColorDistance(Object firstLABValue, Object secondLABValue){
		return 0;
	}
}
