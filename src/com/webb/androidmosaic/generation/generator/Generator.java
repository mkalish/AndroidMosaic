package com.webb.androidmosaic.generation.generator;

import java.util.List;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.NewStateListener;


public interface Generator {
	void setTargetImage(Bitmap target);
	//Method to Start the generator
	void start();
	//Method to Pause the generator
	void pause();
	void resume();
	//Method to Kill the generator
	void close();
	//Method to provide a state-snapshot buffer
	void registerNewStateListener(NewStateListener listener);
	//Potential configuration methods, could instead put configuration in the factory
	void removeNewStateListener(NewStateListener listener);
	List<AnalyzedImage> getSolutionTiles();
	
	int getNumTilesPerRowInSolution();
	int getNumTilesPerColumnInSolution();
	int getWidthOfTileInPixels();
}
