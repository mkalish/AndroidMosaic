package com.webb.androidmosaic.generation.generator;

import static com.webb.androidmosaic.generation.ColorSpaceUtils.LABColorDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import android.graphics.Bitmap;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.LABValue;
import com.webb.androidmosaic.generation.MaxDuplicatesList;
import com.webb.androidmosaic.generation.NewStateListener;
import com.webb.androidmosaic.generation.preprocessor.Preprocessor;
import com.webb.androidmosaic.generation.preprocessor.PreprocessorFactory;

public class GeneratorSimpleImpl implements Generator {
	private List<AnalyzedImage> targetImageTiles = new ArrayList<AnalyzedImage>();
	private List<AnalyzedImage> solutionImageTiles = new ArrayList<AnalyzedImage>();
	private List<AnalyzedImage> poolImageTiles = new ArrayList<AnalyzedImage>();
	private volatile ConcurrentLinkedQueue<NewStateListener> listeners;
	private MaxDuplicatesList<AnalyzedImage> maxDupList;
	
	private Random rand = new Random();
	private int maxDuplicates = Integer.MAX_VALUE;
	
	private Preprocessor preprocessor;
	private Configuration config;
	
	private AtomicBoolean paused = new AtomicBoolean(false); //consider making lighter weight, like volatile boolean
	
	protected GeneratorSimpleImpl(Configuration config){
		this.config = config; //consider unpacking config instead
		
		this.preprocessor = PreprocessorFactory.getPreprocessor(config);
		this.poolImageTiles = preprocessor.analyze(config.getImagePool());
		maxDuplicates = config.getMaxDuplicates();
		this.maxDupList = new MaxDuplicatesList<AnalyzedImage>(maxDuplicates, poolImageTiles);
	}
	
	public void setTargetImage(Bitmap target){
		this.targetImageTiles = analyzeTargetImage(target);
	}

 	public void start() {
 		if(targetImageTiles==null){
 			throw new RuntimeException("Target image tiles are null");
 		}
		GeneratorLoop gLoop = new GeneratorLoop();
		gLoop.start();
	}
 	
 	private class GeneratorLoop extends Thread {
		int iterationsElapsed = 0;
		float currentFitness = Float.MAX_VALUE;
		
		GeneratorLoop(){
			//Initialize solution randomly
			for(int i = 0;i<poolImageTiles.size();i++){
				solutionImageTiles.add(maxDupList.getItem());
			}
			//Initialize fitness
			//should probably be an array of floats for efficient incremental updates
			currentFitness = evaluateFitness(targetImageTiles, solutionImageTiles); 
		}
		
 		@Override
		public void run() {
			while(iterationsElapsed<10000){//TODO:change to reasonable stopping point
				iterationsElapsed++;
				
				checkPaused();
				
				//mutate
					//Either swap tiles, or pull from pool
				//check fitness, incremental calculation
				
				//take new state
				
				//call handler
				for (NewStateListener listener : listeners) {
					listener.handle(null);//TODO change to something intelligent
				}
			}
		}
 		
 		private void checkPaused(){
			//Pause if necessary
			if(paused.get()==true){
				try {
					paused.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
 		}
 	}
 	
 	public void resume(){
 		paused.set(false);
 		paused.notify();
 	}

	public void pause() {
		paused.set(true);
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void registerNewStateListener(NewStateListener listener) {
		listeners.add(listener);
	}
	
	//uses .equals equality
	public void removeNewStateListener(NewStateListener listener){
		listeners.remove(listener);
	}
	
	private void mutate() {
		
	}
	
	private float evaluateFitness(List<AnalyzedImage> a, List<AnalyzedImage> b){
		int aSize = a.size();
		int bSize = b.size();
		if(aSize!=bSize){
			throw new RuntimeException("Incompatible lists to evaluate fitness on, different sizes");
		}
		float fitness = 0;
		for(int i = 0;i<aSize;i++){
			fitness += evaluateFitness(a.get(i), b.get(i));
		}
		return fitness;
	}
	
	//Higher fitness is worse
	private float evaluateFitness(AnalyzedImage a, AnalyzedImage b) {
		float fitnessSum = 0;
		LABValue[][] aValues = a.getLabValues();
		LABValue[][] bValues = b.getLabValues();
		int size = aValues.length;
		for(int i = 0;i<size;i++){
			for (int j = 0;j<size;j++){
				fitnessSum += LABColorDistance(aValues[i][j],bValues[i][j]);//TODO:Make sure this is never negative!
			}
		}
		
		return fitnessSum;
	}
	
	//(row,column)
	private List<AnalyzedImage> analyzeTargetImage(Bitmap target){
		//calculate tile dimensions
		int widthDivisions = config.getTargetWidthDivisions();
		int targetWidth = target.getWidth();
		int widthOfTiles = targetWidth/widthDivisions; //intentional integer division
		int newTargetWidth = widthOfTiles*widthDivisions;
		
		int targetHeight = target.getHeight();
		int heightDivisions = targetHeight/widthOfTiles; //intentional integer division
		int newTargetHeight = widthOfTiles*heightDivisions;
		
		//crop target, TODO: consider cropping from center
		target = Bitmap.createBitmap(target,0, 0, newTargetWidth, newTargetHeight);
		List<Bitmap> tiles = new ArrayList<Bitmap>(widthDivisions*heightDivisions);
		for (int i = 0;i<heightDivisions;i++){
			for (int j = 0;j<widthDivisions;j++){
				Bitmap tile = Bitmap.createBitmap(target,j*widthOfTiles,i*widthOfTiles, widthOfTiles, widthOfTiles);
				tiles.add(tile);
			}
		}
		return preprocessor.analyze(tiles);
	}
}
