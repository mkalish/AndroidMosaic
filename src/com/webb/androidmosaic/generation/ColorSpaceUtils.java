package com.webb.androidmosaic.generation;

public class ColorSpaceUtils {
	static final float Xref = 0.950456f;
	static final float Yref = 1.000000f;
	static final float Zref = 1.088754f;
	
	// CIE1976
	public static LABValue RGBToLAB(int r, int g, int b) {
		float rf = 0.0039215f * (float) r;
		float gf = 0.0039215f * (float) g;
		float bf = 0.0039215f * (float) b;

		float X = 0.412453f * rf + 0.357580f * gf + 0.180423f * bf;
		float Y = 0.212671f * rf + 0.715160f * gf + 0.072169f * bf;
		float Z = 0.019334f * rf + 0.119193f * gf + 0.950227f * bf;

		float l, a, bb;

		if (Y > 0.008856) {
			l = (float) (116f * Math.pow(Y, 0.3333333) - 16);
		} else {
			l = 903.3f * Y;
		}

		a = 500f * (labPowWrapper(X / Xref) - labPowWrapper(Y / Yref));

		bb = 200f * (labPowWrapper(Y / Yref) - labPowWrapper(Z / Zref));

		return new LABValue(l, a, bb);
	}
	
	private static float labPowWrapper(float input){
		if (input > 0.008856){
			return (float)Math.pow(input, 0.3333333);
		} else{
			return input*7.787f + 16f/116f;
		}
	}
	
	//CIE94 color difference
	public static float LABColorDistance(LABValue lab1, LABValue lab2){
		float l1 = lab1.getL(), a1 = lab1.getA(), b1 = lab1.getB();
		float l2 = lab2.getL(), a2 = lab2.getA(), b2 = lab2.getB();
		float deltaL = l1-l2;
		float c1 = (float)Math.sqrt((a1*a1)+(b1*b1));
		float c2 = (float)Math.sqrt((a2*a2)+(b2*b2));
		float deltaC = c1-c2;
		float deltaA = a1-a2;
		float deltaB = b1-b2;
		float deltaH = (float)Math.sqrt((deltaA*deltaA)+(deltaB*deltaB)-(deltaC*deltaC));
		float sl = 1f;
		float sc = 1f + 0.045f*c1;
		float sh = 1f + 0.015f*c1;
		float term1 = (deltaL/sl);
		float term2 = (deltaC/sc);
		float term3 = (deltaH/sh);
		float deltaE = (float)Math.sqrt((term1*term1)+(term2*term2)+(term3*term3));
		return deltaE;
	}
}
