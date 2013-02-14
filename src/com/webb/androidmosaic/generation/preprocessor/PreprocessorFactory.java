package com.webb.androidmosaic.generation.preprocessor;

import com.webb.androidmosaic.generation.Configuration;

public class PreprocessorFactory {

	public static Preprocessor getPreprocessor(Configuration cfg) {
		Preprocessor p = new PreprocessorImpl(cfg);
		return p;
	}

}
