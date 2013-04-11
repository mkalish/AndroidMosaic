package com.webb.androidmosaic.util;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.ColorSpaceUtils;
import com.webb.androidmosaic.generation.LABValue;

public class MosaicUtil {

	
	/**
	 * Utility function to resize bitmap to a square
	 * 
	 * @param bitmap the bitmap to crop
	 * @return the cropped bitmap
	 */
	public static Bitmap cropBitMapToSquare(Bitmap bitmap) {
		int shortSideLen = Math.min(bitmap.getHeight(),bitmap.getWidth());
		return Bitmap.createBitmap(bitmap, 0, 0, shortSideLen, shortSideLen);
	}
	
	/**
	 * Utility function to scale bitmap to proper size
	 * 
	 * @param bitmap to the squared bitmap to be scaled
	 * @param divisions the width and height used to scale
	 * @return the scaled bitmap
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, int divisions) {		
		return Bitmap.createScaledBitmap(bitmap, divisions, divisions, false);
	}
	
	/**
	 * 
	 */
	public static  LABValue[][] gatherLabValues(Bitmap bitmap, int divisions) {
		int[] pixels = new int[divisions*divisions];
		LABValue[][] labValues = new LABValue[divisions][divisions];		
		for(int i = 0;i<divisions;i++){
			for(int j = 0;j<divisions;j++){
				int pixel = pixels[i*divisions+j];
				int r = (pixel >> 16) & 0xff;     //bitwise shifting
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				LABValue labValue = ColorSpaceUtils.RGBToLAB(r, g, b);
				labValues[i][j]=labValue;
			}
		}		
		return labValues;
	}
	
	
}
