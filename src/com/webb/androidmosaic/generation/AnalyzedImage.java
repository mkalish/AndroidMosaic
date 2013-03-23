package com.webb.androidmosaic.generation;

import java.io.Serializable;

public class AnalyzedImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8069593812516247346L;
	//The building block that the Generator works with. (Instead of a raw bitmap with excessive, un-analyzed data)
	private final LABValue[][] labValues;
	
	/**
	 * Field to hold reference to saved bitmap
	 */
	private String file;
	
	public AnalyzedImage(LABValue[][] labValues){
		this.labValues = labValues;
	}
	
	public LABValue[][] getLabValues(){
		return labValues;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
	

}
