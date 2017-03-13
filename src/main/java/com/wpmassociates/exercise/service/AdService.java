package com.wpmassociates.exercise.service;

import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;

import com.wpmassociates.exercise.persistence.*;
import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.constants.*;
import com.wpmassociates.exercise.validation.*;

import java.util.Date;
import static java.lang.System.currentTimeMillis;

public class AdService {

	private StoreData persist;
	private Properties properties;
	private String jsonString = null;
	private ServletContext context = null;

	public AdService(ServletContext context) {
		this.context = context;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("default.properties");
		properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException exception) {}
		
		String useMap = properties.getProperty("useMap");
		if (useMap.equals("yes"))
			persist = StoreDataMap.getInstance(context);
		else
			persist = new StoreInDatabase(context, properties);
	}	
	
	public String retrieveData(int partnerId) {
		JSONMapStorageObject adObject = null;
		boolean exists = checkId(partnerId);
		this.context.log("Partner id exists " + exists);
		if (!exists)
			return Constants.NO_RECORD + " " + partnerId;
		adObject = persist.retrieveData(partnerId);
		if (adObject == null)
			return "Add object is null";
		Date date = adObject.getEntryTime();
		long duration = adObject.getDuration();
		if (currentTimeMillis() > date.getTime() + duration)
			return Constants.EXCEEDS;
		else 
			jsonString = adObject.getJsonString();
		return jsonString;
	}
	
	public PersistenceResult processData(BufferedReader reader) {
		StringBuffer buffer = new StringBuffer();
		String stringId = "";
		String line = null;
		try {
			while ((line = reader.readLine()) != null)
				buffer.append(line);
		} catch (Exception exception) {}
		jsonString = buffer.toString();
		int duration = 0;
		String adContent = null;
		int partnerId = 0;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			stringId = jsonObject.getString("partner_id");
			boolean validated = Validator.checkForNumeral(stringId, context);
			if (!validated)
				return new PersistenceResult(stringId, Constants.NUMERIC);
			
			partnerId = Integer.parseInt(stringId);
			duration = Integer.parseInt(jsonObject.getString("duration"));
			adContent = jsonObject.getString("ad_content");
		} catch (JSONException exception) {}
		long milliseconds = duration * Constants.DAYINMILLISECONDS;
		JSONMapStorageObject storageObject = new JSONMapStorageObject(new Date(), jsonString, partnerId, milliseconds,  adContent);	
		
		if (checkId(partnerId))
			return new PersistenceResult(stringId, "exists");
		else
			if (persist.storeData(partnerId, storageObject))
				return new PersistenceResult(stringId, "added");
			else 
				return new PersistenceResult(stringId, "problem");
	}
		
	private boolean checkId(int partnerId) {
		return persist.checkForPartnerId(partnerId);
	}
 }
