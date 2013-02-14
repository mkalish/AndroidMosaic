package com.webb.androidmosaic.generation.preprocessor;

import java.util.ArrayList;
import java.util.List;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.ColorSpaceUtils;
import com.webb.androidmosaic.generation.LABValue;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PreprocessorImpl implements Preprocessor {
	PreprocessorConfiguration pc;
	
	public PreprocessorImpl(PreprocessorConfiguration pc){
		this.pc = pc;
	}

	public List<AnalyzedImage> analyze(List<Bitmap> bitmaps) {
		List<AnalyzedImage> result = new ArrayList<AnalyzedImage>(bitmaps.size());
		for (Bitmap bitmap : bitmaps){
			AnalyzedImage ai = analyze(bitmap);
			result.add(ai);
		}
		return result;
	}
	
	private AnalyzedImage analyze (Bitmap bitmap){
		int divisions = pc.getDivisions();
		
		//crop to square, TODO: should probably crop from center
		int shortSideLen = Math.min(bitmap.getHeight(),bitmap.getWidth());
		bitmap = cropToSquare(bitmap, shortSideLen);
		
		//There might be a better way to resize
		bitmap = Bitmap.createScaledBitmap(bitmap, divisions, divisions, false);
		
		//Retrieve data from formatted bitmap
		int[] pixels = new int[divisions*divisions];
		bitmap.getPixels(pixels, 0, divisions, 0, 0, divisions, divisions);
		
		//Initialize labValues "array"
		List<List<LABValue>> labValues = new ArrayList<List<LABValue>>(divisions);
		
		for (int i = 0; i <divisions;i++){
			List<LABValue> l = new ArrayList<LABValue>(divisions);
			labValues.add(l);
		}
		
		//(row,column)
		for(int i = 0;i<divisions;i++){
			for(int j = 0;j<divisions;j++){
				int pixel = pixels[i*divisions+j];
				int r = (pixel >> 16) & 0xff;     //bitwise shifting
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				LABValue labValue = ColorSpaceUtils.RGBToLAB(r, g, b);
				labValues.get(i).add(labValue);
			}
		}
		AnalyzedImage ai = new AnalyzedImage(labValues);
		return ai;
	}
	
	private Bitmap cropToSquare(Bitmap bm, int dimension){
		return Bitmap.createBitmap(bm, 0, 0, dimension, dimension);
	}
}
