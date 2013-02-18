package com.webb.androidmosaic.generation.preprocessor;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.ColorSpaceUtils;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.LABValue;

public class PreprocessorImpl implements Preprocessor {
	Configuration gc;
	
	public PreprocessorImpl(Configuration gc){
		this.gc = gc;
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
		int divisions = gc.getTileDivisions();
		//crop to square, TODO: should probably crop from center
		int shortSideLen = Math.min(bitmap.getHeight(),bitmap.getWidth());
		bitmap = cropToSquare(bitmap, shortSideLen);
		
		//There might be a better way to resize
		bitmap = Bitmap.createScaledBitmap(bitmap, divisions, divisions, false);
		
		//Retrieve data from formatted bitmap
		int[] pixels = new int[divisions*divisions];
		bitmap.getPixels(pixels, 0, divisions, 0, 0, divisions, divisions);
		
		//Initialize labValues "array"
		LABValue[][] labValues = new LABValue[divisions][];
		
		for (int i = 0; i <divisions;i++){
			labValues[i] = new LABValue[divisions];
		}
		
		//(row,column)
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
		AnalyzedImage ai = new AnalyzedImage(labValues);
		return ai;
	}
	
	private Bitmap cropToSquare(Bitmap bm, int dimension){
		return Bitmap.createBitmap(bm, 0, 0, dimension, dimension);
	}
}
