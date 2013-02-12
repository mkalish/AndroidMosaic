package com.webb.androidmosaic.generation.preprocessor;

public class PreprocessorFactory {

	public static Preprocessor getPreprocessor(PreprocessorConfiguration cfg) {
		Preprocessor p = new PreprocessorImpl(cfg);
		return p;
	}

}
