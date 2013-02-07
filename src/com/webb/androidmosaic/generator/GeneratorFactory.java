package com.webb.androidmosaic.generator;

public class GeneratorFactory {
	static Generator getGenerator(GeneratorConfiguration cfg){
		return new GeneratorSimpleImpl(); //TODO: Handle config, use caching
	}
}
