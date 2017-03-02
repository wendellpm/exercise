package com.wpmassociates.exercise.persistence;

import com.wpmassociates.exercise.domain.*;

public interface StoreData { 
	boolean storeData(int partnerId, JSONMapStorageObject storageObject);
	JSONMapStorageObject retrieveData(int partnerId);
	boolean deleteData(int partnerId);
 }
