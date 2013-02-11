package com.webb.androidmosaic.generator;

import java.util.List;

import android.graphics.Bitmap;

public interface Preprocessor {
	//take images, convert to simplified type for generator to work with.
	AnalyzedImage analyze(List<Bitmap> bitmaps);
}
