package com.webb.androidmosaic.generation;

public class LABValue {
	int l;

	int a;
	int b;
	
	public LABValue(int l, int a, int b){
		this.l=l;
		this.a=a;
		this.b=b;
	}
	@Override
	public String toString() {
		return "LABValue [l=" + l + ", a=" + a + ", b=" + b + "]";
	}
}
