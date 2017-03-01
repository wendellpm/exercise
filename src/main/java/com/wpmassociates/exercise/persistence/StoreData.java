package com.wpmassociates.exercise.persistence;

import java.util.Properties;

import com.wpmassociates.exercise.domain.*;

public interface StoreData { 
	void init(Properties properties);
	boolean storeData(int partnerId, JSONMapStorageObject storageObject);
	JSONMapStorageObject retrieveData(int partnerId);
	boolean deleteData(int partnerId);
 }
