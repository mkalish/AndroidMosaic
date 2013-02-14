package com.webb.androidmosaic.generation.preprocessor;

import java.util.List;

import com.webb.androidmosaic.generation.AnalyzedImage;

import android.graphics.Bitmap;

public interface Preprocessor {
	//take images, convert to simplified type for generator to work with. Returns images in the order received
	List<AnalyzedImage> analyze(List<Bitmap> bitmaps);
}
