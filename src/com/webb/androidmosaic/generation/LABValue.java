package com.webb.androidmosaic.generation;

import java.io.Serializable;

//Consider replacing with an array for performance.
public class LABValue implements Serializable {
	private final float l;
	private final float a;
	private final float b;
	
	public LABValue(float l, float a, float b){
		this.l=l;
		this.a=a;
		this.b=b;
	}
	public float getL() {
		return l;
	}
	public float getA() {
		return a;
	}
	public float getB() {
		return b;
	}
	@Override
	public String toString() {
		return "LABValue [l=" + l + ", a=" + a + ", b=" + b + "]";
	}
}
