package com.webb.androidmosaic.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityTestCase;

import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.generator.Generator;
import com.webb.androidmosaic.generation.generator.GeneratorFactory;

public class GeneratorSimpleImplTest extends ActivityTestCase {

	public void testEndToEnd() throws Exception{
        Context testContext = getInstrumentation().getContext();
        Resources testRes = testContext.getResources();
        InputStream ts = testRes.openRawResource(R.drawable.red_rectangle);
		
		Bitmap bitmap = BitmapFactory.decodeStream(ts);
		List<Bitmap> listOfBitmaps = new ArrayList<Bitmap>();
		listOfBitmaps.add(bitmap);
		Configuration gc = new Configuration(listOfBitmaps, 5, 10, Integer.MAX_VALUE);
		Generator gen = GeneratorFactory.getGenerator(gc);
		gen.setTargetImage(bitmap);
		gen.start();
		gen.pause();
		gen.resume();
	}
}
