package com.wpmassociates.exercise.persistence;

import java.util.Properties;

import com.wpmassociates.exercise.domain.*;

public interface StoreData { 
	boolean storeData(int partnerId, JSONMapStorageObject storageObject);
	JSONMapStorageObject retrieveData(int partnerId);
	boolean checkForPartnerId(int partnerId);
	boolean deleteData(int partnerId);
	void setProperties(Properties properties);
 }
