package com.webb.androidmosaic.generation;

import java.io.Serializable;
import java.util.List;

public class AnalyzedImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8069593812516247346L;
	//The building block that the Generator works with. (Instead of a raw bitmap with excessive, un-analyzed data)
	private final LABValue[][] labValues;
	
	public AnalyzedImage(LABValue[][] labValues){
		this.labValues = labValues;
	}
	
	public LABValue[][] getLabValues(){
		return labValues;
	}
	

}
