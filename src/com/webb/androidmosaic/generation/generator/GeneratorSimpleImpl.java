package com.webb.androidmosaic.generation.generator;

import java.util.Set;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.NewStateListener;

import android.graphics.Bitmap;

public class GeneratorSimpleImpl implements Generator {
	protected GeneratorSimpleImpl(Bitmap target, Set<AnalyzedImage> imagePool, GeneratorConfiguration config){
		//setup work
		//precompute stuff, image pool value averages should not be made by Generator
		//This class should not take Bitmaps, should take an object that is created by a Preprocessor class 
	}
	
	private void mutate() {
		
	}
	
	private void evaluateFitness() {
		
	}

 	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void registerNewStateListener(NewStateListener listener) {
		// TODO Auto-generated method stub
		
	}
}
