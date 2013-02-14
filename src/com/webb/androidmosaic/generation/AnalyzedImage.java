package com.webb.androidmosaic.generation;

import java.util.List;

public class AnalyzedImage {
	//The building block that the Generator works with. (Instead of a raw bitmap with excessive, un-analyzed data)
	private final LABValue[][] labValues;
	
	public AnalyzedImage(LABValue[][] labValues){
		this.labValues = labValues;
	}
}
