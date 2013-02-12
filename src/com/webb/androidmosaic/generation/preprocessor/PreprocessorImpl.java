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
		
		//crop to square
		int shortSideLen = Math.min(bitmap.getHeight(),bitmap.getWidth());
		bitmap = cropToSquare(bitmap, shortSideLen);
		
		bitmap = getResizedBitmap(bitmap, divisions, divisions);
		
		//Retrieve data from formatted bitmap
		int[] pixels = new int[divisions^2];
		bitmap.getPixels(pixels, 0, divisions, 0, 0, divisions, divisions);
		
		//Initialize labValues "array"
		List<List<LABValue>> labValues = new ArrayList<List<LABValue>>(divisions);
		for (List<LABValue> list : labValues){
			list = new ArrayList<LABValue>(divisions);
		}
		
		for(int i = 0;i<divisions;i++){
			for(int j = 0;j<divisions;j++){
				int pixel = pixels[i+j];
				//strip alpha
				pixel = pixel << 8;
				int r = pixel >> 24;
				int g = (pixel << 8) >> 24;
				int b = (pixel << 16) >> 24;
				LABValue labValue = ColorSpaceUtils.RGBToLAB(r, g, b);
				labValues.get(i).set(j, labValue);
			}
		}
		AnalyzedImage ai = new AnalyzedImage(labValues);
		return ai;
	}
	
	private Bitmap cropToSquare(Bitmap bm, int dimension){
		return Bitmap.createBitmap(bm, 0, 0, dimension, dimension);
	}
	//Taken from the interbutt
	private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}

}
