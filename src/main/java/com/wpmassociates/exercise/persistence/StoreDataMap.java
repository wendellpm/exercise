package com.wpmassociates.exercise.persistence;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletContext;

import com.wpmassociates.exercise.domain.*;

public class StoreDataMap implements StoreData { 

	private Map<Integer, JSONMapStorageObject> storageMap;

	public StoreDataMap() {
		if (storageMap == null) 
			storageMap = new HashMap<Integer, JSONMapStorageObject>();
	}
	
	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
		storageMap.put(partnerId, storageObject);
		return (storageMap.get(partnerId) != null);
	}
		
	public JSONMapStorageObject retrieveData(int partnerId) {
		return storageMap.get(partnerId);
	}
	
	public boolean deleteData(int partnerId) {
		storageMap.remove(partnerId);
		return (storageMap.containsKey(partnerId));
	}
	
	public boolean checkForPartnerId(int partnerId, ServletContext context) {
		return storageMap.containsKey(partnerId);
	}
	
 }
