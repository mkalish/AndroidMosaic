package com.webb.androidmosaic.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MaxDuplicatesList<E> {
	private int maxDuplicates = Integer.MAX_VALUE;
	private List<E> validItems;
	private Set<E> invalidItems;
	private Map<E,Integer> usageCounts;
	private Random rnd = new Random();
	
	public MaxDuplicatesList(int maxDuplicates, List<E> seed){
		this.maxDuplicates = maxDuplicates;
		int size = seed.size();
		this.validItems = new ArrayList<E>(size);
		this.invalidItems = new HashSet<E>(size);
		this.usageCounts = new HashMap<E,Integer>(size);
		Integer count = Integer.valueOf(0);
		for(E e : seed){
			validItems.add(e);
			usageCounts.put(e, count);
		}
	}
	
	public E randomPeek(){
		int size = validItems.size();
		if (size==0){
			//shit, catch this earlier in the class that uses it.
			throw new RuntimeException("No more items available from MaxDuplicatesList");
		}
		int randomNumber = rnd.nextInt(size);
		E e = validItems.get(randomNumber);
		return e;
	}
	
	public void takeItem(E e){
		Integer count = usageCounts.get(e);
		count += 1;
		usageCounts.put(e, count);
		if (count==maxDuplicates){
			validItems.remove(e);
			invalidItems.add(e);
		}
	}
	
	public void putBack(E e){
		Integer count = usageCounts.get(e);
		if(count==maxDuplicates){
			invalidItems.remove(e);
			validItems.add(e);
		}
		count -= 1;
		usageCounts.put(e, count);
	}
}

