package com.webb.androidmosaic.generator;

import java.util.List;

import android.graphics.Bitmap;

public interface NewStateListener {
	void handle(List<List<Bitmap>> state); //Could Change to something more intelligent than Bitmap
}
