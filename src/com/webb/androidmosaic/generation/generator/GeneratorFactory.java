package com.webb.androidmosaic.generation.generator;

import java.util.Set;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;

import android.graphics.Bitmap;

/**
 * This class will handle taking information from the UI code in whatever form is most handy
 * and then translate it to the simple inputs that the generator implementations expect.
 */
public class GeneratorFactory {
	public static Generator getGenerator(Configuration cfg){ //These arguments will expand
		Bitmap targetImage = null;
		Set<AnalyzedImage> imagePool = null;
		return new GeneratorSimpleImpl(cfg); //TODO: Maybe unpack config before sending it in?
	}
}
