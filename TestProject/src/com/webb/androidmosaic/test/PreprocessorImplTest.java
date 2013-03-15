package com.webb.androidmosaic.test;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityTestCase;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.preprocessor.Preprocessor;
import com.webb.androidmosaic.generation.preprocessor.PreprocessorImpl;

public class PreprocessorImplTest extends ActivityTestCase{

	public void testPreprocessorImpl() {
		fail("Not yet implemented");
	}

	public void testAnalyze() {
        Context testContext = getInstrumentation().getContext();
        Resources testRes = testContext.getResources();
        InputStream ts = testRes.openRawResource(R.drawable.red_rectangle);
		
		Bitmap bitmap = BitmapFactory.decodeStream(ts);
		List<Bitmap> listOfBitmaps = new ArrayList<Bitmap>();
		listOfBitmaps.add(bitmap);
		List<AnalyzedImage> dummyList = new ArrayList<AnalyzedImage>();
		Configuration gc = new Configuration(dummyList, 5, 10, 10);
		Preprocessor p = new PreprocessorImpl(gc);
		p.analyze(listOfBitmaps);
	}

}
