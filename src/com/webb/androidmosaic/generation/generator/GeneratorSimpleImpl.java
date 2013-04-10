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
	private int targetImageLen;
	private List<AnalyzedImage> poolImageTiles = new ArrayList<AnalyzedImage>();
	private volatile ConcurrentLinkedQueue<NewStateListener> listeners = new ConcurrentLinkedQueue<NewStateListener>();
	private MaxDuplicatesList<AnalyzedImage> maxDupList;
	
	private Random rand = new Random();
	private int maxDuplicates = Integer.MAX_VALUE;
	
	private int imagesPerColumnInSolution = 0;
	private int widthOfTiles = 0;
	
	private Preprocessor preprocessor;
	private Configuration config;
	
	private AtomicBoolean paused = new AtomicBoolean(false); //consider making lighter weight, like volatile boolean
	
	protected GeneratorSimpleImpl(Configuration config){
		this.config = config; //consider unpacking config instead
		this.preprocessor = PreprocessorFactory.getPreprocessor(config);
		this.poolImageTiles = config.getImagePool();
		maxDuplicates = config.getMaxDuplicates();
		this.maxDupList = new MaxDuplicatesList<AnalyzedImage>(maxDuplicates, poolImageTiles);
	}
	
	public int getNumTilesPerRowInSolution(){
		return config.getTargetWidthDivisions();
	}
	
	public int getNumTilesPerColumnInSolution(){
		if (imagesPerColumnInSolution==0){
			throw new RuntimeException("No target image set yet");
		}
		return imagesPerColumnInSolution;
	}
	
	public int getWidthOfTileInPixels(){
		if (widthOfTiles==0){
			throw new RuntimeException("No target image set yet");
		}
		return widthOfTiles;
	}
	
	public void setTargetImage(Bitmap target){
		this.targetImageTiles = analyzeTargetImage(target);
		this.targetImageLen = targetImageTiles.size();
	}

 	public void start() {
 		if(targetImageTiles.size()==0){
 			throw new RuntimeException("Target image tiles are empty");
 		}
		GeneratorLoop gLoop = new GeneratorLoop();
		gLoop.start();
	}
 	
 	private class GeneratorLoop extends Thread {
		int iterationsElapsed = 0;
		float[] currentFitness;
		float cumulativeFitness = 0;
		boolean madeChangeThisiteration = false;
		
		GeneratorLoop(){
			//Initialize solution randomly
			for(int i = 0;i<targetImageTiles.size();i++){
				AnalyzedImage ai = maxDupList.randomPeek();
				maxDupList.takeItem(ai);
				solutionImageTiles.add(ai);
			}
			//Initialize fitness
			currentFitness = evaluateFitness(targetImageTiles, solutionImageTiles);
			for(int i = 0;i<currentFitness.length;i++){
				cumulativeFitness += currentFitness[i];
			}
		}
		
 		@Override
		public void run() {
			while(iterationsElapsed<10000){//TODO:change to reasonable stopping point
				iterationsElapsed++;
				checkPaused();
				
				//mutate
				//swap first, intentionally avoid OO-ness for better performance
				int index1 = rand.nextInt(targetImageLen), index2 = rand.nextInt(targetImageLen);
				AnalyzedImage img1 = solutionImageTiles.get(index1), img2 = solutionImageTiles.get(index2);
				AnalyzedImage target1 = targetImageTiles.get(index1), target2 = targetImageTiles.get(index2);
				float newFitness1 = evaluateFitness(img2, target1);
				float newFitness2 = evaluateFitness(img1, target2);
				float newFitnessChange1 = newFitness1 - currentFitness[index1]; //negative is good
				float newFitnessChange2 = newFitness2 - currentFitness[index2];
				float changeInFitnessSwap = newFitnessChange1 + newFitnessChange2;
				
				//take from pool
				int index3 = rand.nextInt(targetImageLen);
				AnalyzedImage fromPool = maxDupList.randomPeek();
				float newFitness3 = evaluateFitness(fromPool, targetImageTiles.get(index3));
				float changeInFitnessFromPool =  newFitness3 - currentFitness[index3]; //negative is good
				
				if (changeInFitnessSwap<changeInFitnessFromPool){
					if(changeInFitnessSwap<0){
						//take swap
						currentFitness[index1] = newFitness1;
						currentFitness[index2] = newFitness2;
						cumulativeFitness += changeInFitnessSwap;
						solutionImageTiles.set(index1, img2);
						solutionImageTiles.set(index2, img1);
						madeChangeThisiteration = true;
					}
				} else{
					if(changeInFitnessFromPool<0){
						//take from pool
						currentFitness[index3]=newFitness3;
						cumulativeFitness += changeInFitnessFromPool;
						maxDupList.putBack(solutionImageTiles.get(index3));
						maxDupList.takeItem(fromPool);
						solutionImageTiles.set(index3, fromPool);
						madeChangeThisiteration = true;
					} 
				}
				
				if(madeChangeThisiteration){
					for (NewStateListener listener : listeners) {
						List<AnalyzedImage> copiedList = new ArrayList<AnalyzedImage>(targetImageLen);
						for (AnalyzedImage ai : solutionImageTiles){
							copiedList.add(ai); //todo copy this more efficiently
						}
						listener.handle(copiedList, cumulativeFitness);//TODO change to something intelligent
					}
				madeChangeThisiteration = false;
				}
			}
		}
 		
 		private void checkPaused(){
			//Pause if necessary
			if(paused.get()==true){
				try {
					synchronized (paused) {
						paused.wait();
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
 		}
 	}
 	
 	public void resume(){
 		paused.set(false);
 		synchronized (paused) {
 	 		paused.notify();
		}
 	}

	public void pause() {
		paused.set(true);
	}

	public void close() {
		//Release resources if you have any?
	}

	public void registerNewStateListener(NewStateListener listener) {
		listeners.add(listener);
	}
	
	//uses .equals equality
	public void removeNewStateListener(NewStateListener listener){
		listeners.remove(listener);
	}
	
	private float[] evaluateFitness(List<AnalyzedImage> a, List<AnalyzedImage> b){
		int aSize = a.size();
		int bSize = b.size();
		if(aSize!=bSize){
			throw new RuntimeException("Incompatible lists to evaluate fitness on, different sizes");
		}
		float[] result = new float[aSize];
		for(int i = 0;i<aSize;i++){
			result[i] = evaluateFitness(a.get(i), b.get(i));
		}
		return result;
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
		widthOfTiles = targetWidth/widthDivisions; //intentional integer division
	
		int newTargetWidth = widthOfTiles*widthDivisions;
		
		int targetHeight = target.getHeight();
		int heightDivisions = targetHeight/widthOfTiles; //intentional integer division
		imagesPerColumnInSolution = heightDivisions;
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


	public List<AnalyzedImage> getSolutionTiles() {
		return solutionImageTiles;
	}
}
