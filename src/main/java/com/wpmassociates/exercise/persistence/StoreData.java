package com.wpmassociates.exercise.persistence;

import javax.servlet.ServletContext;

import com.wpmassociates.exercise.domain.*;

public interface StoreData { 
	boolean storeData(int partnerId, JSONMapStorageObject storageObject);
	JSONMapStorageObject retrieveData(int partnerId);
	boolean checkForPartnerId(int partnerId, ServletContext context);
	boolean deleteData(int partnerId);
 }
