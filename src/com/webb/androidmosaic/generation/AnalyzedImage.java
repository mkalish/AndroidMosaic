package com.webb.androidmosaic.generation;

import java.util.List;

public class AnalyzedImage {
	//The building block that the Generator works with. (Instead of a raw bitmap with excessive, un-analyzed data)
	private List<List<LABValue>> labValues;
	
	public AnalyzedImage(List<List<LABValue>> labValues){
		this.labValues = labValues;
	}
}
