package com.wpmassociates.exercise.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import java.util.Properties;

import java.util.logging.Logger;

import com.wpmassociates.exercise.domain.*;

public class StoreDataMap implements StoreData { 

	private static Logger logger = Logger.getLogger(StoreDataMap.class.getName());
	
	private static Map<Integer, JSONMapStorageObject> storageMap;
	private static ServletContext context = null;
	private static StoreDataMap storeDataMap;
	
	private StoreDataMap() {
		storageMap = new ConcurrentHashMap<Integer, JSONMapStorageObject>();
	}
	
	public static StoreDataMap getInstance(ServletContext thisContext) {
		if (storeDataMap == null) {
			context = thisContext;
			context.log("Map is empty, create new Map object");
			storeDataMap = new StoreDataMap();
		} else {
			context.log("Map already exists");
			Set<Map.Entry<Integer, JSONMapStorageObject>> set = storageMap.entrySet();
			if (set != null) {
			context.log("Entries in current map");
			for (Map.Entry<Integer, JSONMapStorageObject> entry : set) {
				int key = entry.getKey();
				JSONMapStorageObject object = entry.getValue();
				context.log("Key " + key + " value " + object.toString());
				logger.info("Key " + key + " value " + object.toString());
			}
			} else {
				context.log("No entries in map");
			}
			
		}
		return storeDataMap;
	}
	

	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
		context.log("Partner id " + partnerId + " object " + storageObject.toString());
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
	{logger.info("No properties to set here, but needed for interface");}
	
 }
