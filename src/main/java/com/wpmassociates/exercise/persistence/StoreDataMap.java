package com.wpmassociates.exercise.persistence;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import java.util.Properties;

import com.wpmassociates.exercise.domain.*;

public class StoreDataMap implements StoreData { 

	private static Map<Integer, JSONMapStorageObject> storageMap;
	private static ServletContext context = null;
	private static StoreDataMap storeDataMap;
	
	private StoreDataMap() {
		storageMap = new HashMap<Integer, JSONMapStorageObject>();
	}
	
	public static StoreDataMap getInstance(ServletContext thisContext) {
		if (storeDataMap == null) {
			context = thisContext;
			context.log("Map is empty");
			storeDataMap = new StoreDataMap();
		} else {
			Set<Map.Entry<Integer, JSONMapStorageObject>> set = storageMap.entrySet();
			for (Map.Entry<Integer, JSONMapStorageObject> entry : set) {
				int key = entry.getKey();
				JSONMapStorageObject object = entry.getValue();
				context.log("Key " + key + " value " + object.toString());
			}
		}
		return storeDataMap;
	}
	

	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
		storageMap.put(partnerId, storageObject);
		JSONMapStorageObject object = storageMap.get(partnerId);
		context.log("Data stored " + object.getPartnerId());
		return (object != null);
	}
		
	public JSONMapStorageObject retrieveData(int partnerId) {
		JSONMapStorageObject object = storageMap.get(partnerId);
		context.log("Data retrieved " + object.getPartnerId());
		return object;
	}
	
	public boolean deleteData(int partnerId) {
		storageMap.remove(partnerId);
		return (storageMap.containsKey(partnerId));
	}
	
	public boolean checkForPartnerId(int partnerId) {
		boolean hasKey = storageMap.containsKey(partnerId);
		context.log("Has key " + hasKey);
		return hasKey;
	}
	
	public void setProperties(Properties properties)
	{}
	
 }
