package com.webb.androidmosaic.generation;

import android.graphics.Matrix;

public class ColorSpaceUtils {
	
	//CIE1976
	public static LABValue RGBToLAB(int r, int g, int b){
		//rgb to xyz, xyz to lab
		Matrix m = new Matrix();
		m.setValues(new float[]{0.412453f,0.357580f,0.180423f,0.212671f,0.715160f,0.072169f,0.019334f,0.119193f,0.950227f});
		float[] xyz = new float[]{r,g,b};
		m.mapPoints(xyz);
		int l,a,b2;
		return new LABValue(r,g,b);//TODO, these should be the correct l,a,b values instead
	}
	
	//CIE94 color difference
	public static int LABColorDistance(Object firstLABValue, Object secondLABValue){
		return 0;
	}
}
