package com.webb.androidmosaic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.preprocessor.Preprocessor;
import com.webb.androidmosaic.generation.preprocessor.PreprocessorFactory;

public class AndroidMosaicApp extends Application {
	
	private static final int MAX_DUPLICATES = 2;
	private static final int NUMBER_OF_WIDTH_DIVISIONS = 9;
	private static final int NUMBER_OF_DIVISIONS = 9;
	private List<Bitmap> bitmaps;
	private String imageDirectory;
	private String analyzedImageDirectory;
	private Configuration cfg;
	private Preprocessor preprocessor;
	private List<AnalyzedImage> analyzedImages;
	private File[] processedImages;
	private static int IMAGE_ID = 0;

	
	private void collectBitmaps(File[] imageFiles) {
		for(File file: imageFiles) {
			Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
			bitmaps.add(image);
		}
	}
	
	private void saveAnalyzedImages(List<AnalyzedImage> images) {
		for(AnalyzedImage image: images) {
			try {
				saveAnalyzedImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveAnalyzedImage(AnalyzedImage image) throws IOException {
		String filePath = analyzedImageDirectory + "/" + IMAGE_ID;
		FileOutputStream fos = openFileOutput(filePath, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(image);

	}
	
	
	public void onCreate() {
		
		bitmaps = new ArrayList<Bitmap>();		
		collectBitmaps(new File(imageDirectory).listFiles());
		
		cfg = new Configuration(bitmaps, NUMBER_OF_DIVISIONS, NUMBER_OF_WIDTH_DIVISIONS, MAX_DUPLICATES);
		preprocessor = PreprocessorFactory.getPreprocessor(cfg);
		analyzedImages = preprocessor.analyze(bitmaps);
		
		 
		
		
		
	}
}
