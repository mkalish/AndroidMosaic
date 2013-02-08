package com.webb.androidmosaic.generator;

public interface Generator {
	//Method to Start the generator
	void start();
	//Method to Pause the generator
	void pause();
	//Method to Kill the generator
	void close();
	//Method to provide a state-snapshot buffer
	void registerNewStateListener(NewStateListener listener);
	//Potential configuration methods, could instead put configuration in the factory
}
