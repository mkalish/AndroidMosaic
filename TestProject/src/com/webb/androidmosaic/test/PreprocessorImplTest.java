package com.webb.androidmosaic.test;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityTestCase;

import com.webb.androidmosaic.generation.preprocessor.Preprocessor;
import com.webb.androidmosaic.generation.preprocessor.PreprocessorConfiguration;
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
		PreprocessorConfiguration pc = new PreprocessorConfiguration(5);
		Preprocessor p = new PreprocessorImpl(pc);
		List<Bitmap> listOfBitmaps = new ArrayList<Bitmap>();
		listOfBitmaps.add(bitmap);
		p.analyze(listOfBitmaps);
	}

}
