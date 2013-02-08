package com.webb.androidmosaic.generator;

import java.util.Set;

import android.graphics.Bitmap;

/**
 * This class will handle taking information from the UI code in whatever form is most handy
 * and then translate it to the simple inputs that the generator implementations expect.
 */
public class GeneratorFactory {
	static Generator getGenerator(GeneratorConfiguration cfg){ //These arguments will expand
		Bitmap targetImage = null;
		Set<Bitmap> imagePool = null;
		return new GeneratorSimpleImpl(targetImage, imagePool, cfg); //TODO: Handle config, use caching
	}
}
