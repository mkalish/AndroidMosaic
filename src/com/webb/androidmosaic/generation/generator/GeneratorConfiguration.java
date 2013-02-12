package com.webb.androidmosaic.generation.generator;

import java.util.List;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.AnalyzedImage;

public class GeneratorConfiguration {
	private List<AnalyzedImage> analyzedImages;
	private Bitmap targetImage;
	
	public GeneratorConfiguration(List<AnalyzedImage> ais, Bitmap targetImage) {
		this.analyzedImages = ais;
		this.targetImage = targetImage;
	}
	
	public List<AnalyzedImage> getAnalyzedImages() {
		return analyzedImages;
	}

	public Bitmap getTargetImage() {
		return targetImage;
	}
}
