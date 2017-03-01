package com.wpmassociates.exercise.persistence;

public class StoreFactory { 
	
	public static StoreData useStore(String yesNo) {
		if (yesNo.equals("yes")) return new StoreDataMap();
		return new StoreInDatabase();
	}
 }
