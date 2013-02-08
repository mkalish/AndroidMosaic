package com.webb.androidmosaic.generator;

import java.util.Set;

import android.graphics.Bitmap;

public class GeneratorFactory {
	static Generator getGenerator(GeneratorConfiguration cfg){ //These arguments will expand
		Bitmap targetImage = null;
		Set<Bitmap> imagePool = null;
		return new GeneratorSimpleImpl(targetImage, imagePool, cfg); //TODO: Handle config, use caching
	}
}
