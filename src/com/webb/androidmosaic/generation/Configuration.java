package com.webb.androidmosaic.generation;

import java.util.List;

import android.graphics.Bitmap;


public class Configuration {
	private final List<Bitmap> imagePool;
	private final int tileDivisions;
	private final int targetWidthDivisions;

	
	public Configuration(List<Bitmap> imagePool, int tileDivisions, int targetWidthDivisions) {
		this.imagePool = imagePool;
		this.tileDivisions = tileDivisions;
		this.targetWidthDivisions = targetWidthDivisions;
	}
	
	public List<Bitmap> getImagePool() {
		return imagePool;
	}
	
	public int getTileDivisions(){
		return tileDivisions;
	}

	public int getTargetWidthDivisions() {
		return targetWidthDivisions;
	}
}
