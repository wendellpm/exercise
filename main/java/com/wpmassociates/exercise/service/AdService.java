package com.wpmassociates.exercise.service;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.Writer;
import java.io.FileWriter;
import java.io.InputStream;

import com.wpmassociates.exercise.persistence.*;
import com.wpmassociates.exercise.domain.*;

import java.util.Date;
import java.util.Properties;
import static java.lang.System.currentTimeMillis;

public class AdService {

	private StoreData persist;
	private Properties properties;
	
	private final String PARTNER_ID = "partner_id";
	private final String EXCEEDS = "{\"Error\":\"campaign duration exceeded}\"";
	private final long DAYINMILLISECONDS = 1000 * 60 * 60 * 24;
	private String jsonString = null;

	public void init() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("default.properties");
		properties = new Properties();
		try {
		properties.load(input);
		} catch (IOException exception) {}
		String yesNo = (String)properties.get("useMap");
		persist = StoreFactory.useStore(yesNo);
		persist.init(properties);
	}	
	
	public String retrieveData(int partnerId) {
		JSONMapStorageObject adObject = persist.retrieveData(partnerId);
		Date date = adObject.getEntryTime();
		long duration = adObject.getDuration();
		long currentMilliseconds = currentTimeMillis();
		long expirationMilliseconds = date.getTime() + duration;
		if (currentMilliseconds > expirationMilliseconds)
			return EXCEEDS;
		else 
			jsonString = adObject.getJsonString();
		return jsonString;
	}
	
	public boolean processData(BufferedReader reader) {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			while ((line = reader.readLine()) != null)
				buffer.append(line);
		} catch (Exception exception) {}
		jsonString = buffer.toString();
		JSONObject jsonObject = new JSONObject(jsonString);
		int partnerId = Integer.parseInt(jsonObject.getString("partner_id"));
		int duration = Integer.parseInt(jsonObject.getString("duration"));
		String adContent = jsonObject.getString("ad_content");
		long milliseconds = duration * DAYINMILLISECONDS;
		JSONMapStorageObject storageObject = new JSONMapStorageObject(new Date(), jsonString, milliseconds, partnerId, adContent);	
		return persist.storeData(partnerId, storageObject);
	}
	
 }
