package com.webb.androidmosaic.test;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityTestCase;
import android.util.Log;

import com.webb.androidmosaic.AndroidMosaicApp;
import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.NewStateListener;
import com.webb.androidmosaic.generation.generator.Generator;
import com.webb.androidmosaic.generation.generator.GeneratorFactory;

public class GeneratorSimpleImplTest extends ActivityTestCase {
	
	List<AnalyzedImage> stateHolder;
	int timesUpdated = 0;

	public void testEndToEnd() throws Exception{
		
		String analyzedImageDirectory = "analyzedImages";
        Context testContext = getInstrumentation().getContext();

		File directoryAnalyzedImages = testContext.getDir(analyzedImageDirectory, testContext.MODE_PRIVATE);
		
		AnalyzedImage imageTest = null;
		List<AnalyzedImage> listOfAnalyzedImages = new ArrayList<AnalyzedImage>();
		for (int i = 0; i < 100;i++){
			FileInputStream fis = new FileInputStream("/data/data/com.webb.androidmosaic/app_analyzedImages/"+i);
			ObjectInputStream ois = new ObjectInputStream(fis);
			while(true){
				try{
					imageTest = (AnalyzedImage) ois.readObject();
				} catch (EOFException e){
					break;
				}
				listOfAnalyzedImages.add(imageTest);
			}
			ois.close();
			fis.close();
		}
		
        Resources testRes = testContext.getResources();
        InputStream ts = testRes.openRawResource(R.drawable.red_rectangle);
		Bitmap bitmap = BitmapFactory.decodeStream(ts);
		Configuration gc = new Configuration(listOfAnalyzedImages, 9, 9, Integer.MAX_VALUE);
		Generator gen = GeneratorFactory.getGenerator(gc);
		NewStateListener stateListener = new NewStateListener() {
			@Override
			public void handle(List<AnalyzedImage> state) {
				stateHolder = state;
				timesUpdated++;
			}
		};
		gen.registerNewStateListener(stateListener);
		gen.setTargetImage(bitmap);
		gen.start();
		gen.pause();
		gen.resume();
		Thread.sleep(120000);
		List<Object> iAmStupid = new ArrayList<Object>(); //just for a breakpoint
	}
}
