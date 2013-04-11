package com.webb.androidmosaic.generation.preprocessor;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.util.MosaicUtil;

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
		
		Bitmap croppedBitmap = MosaicUtil.cropBitMapToSquare(bitmap);
		
		Bitmap scaledBitmap = MosaicUtil.scaleBitmap(croppedBitmap, divisions);
		
		AnalyzedImage ai = new AnalyzedImage(MosaicUtil.gatherLabValues(scaledBitmap, divisions));
		return ai;
	}
	
	private Bitmap cropToSquare(Bitmap bm, int dimension){
		return Bitmap.createBitmap(bm, 0, 0, dimension, dimension);
	}
}
