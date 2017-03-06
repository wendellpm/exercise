package com.wpmassociates.exercise.service;

import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.wpmassociates.exercise.persistence.*;
import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.constants.*;

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
			persist = new StoreDataMap();
		else 
			persist = new StoreInDatabase(properties);
	}	
	
	public String retrieveData(int partnerId) {
		
		JSONMapStorageObject adObject = null;
		this.context.log("Partner id exists " + checkId(partnerId));
		if (!checkId(partnerId))
			return Constants.NO_RECORD + " " + partnerId;
		
		adObject = persist.retrieveData(partnerId);
		Date date = adObject.getEntryTime();
		long duration = adObject.getDuration();
		
		if (currentTimeMillis() > date.getTime() + duration)
			return Constants.EXCEEDS;
		else 
			jsonString = adObject.getJsonString();
		return jsonString;
	}
	
	public String processData(BufferedReader reader) {
		
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
		long milliseconds = duration * Constants.DAYINMILLISECONDS;
		JSONMapStorageObject storageObject = new JSONMapStorageObject(new Date(), jsonString, milliseconds, partnerId, adContent);	
		
		if (checkId(partnerId) == true)
			return "exists";
		if (persist.storeData(partnerId, storageObject)) {
			this.context.log("Partner id added");
			return "added";
		} else 
			return "problem";
	}
		
	private boolean checkId(int partnerId) {
		return persist.checkForPartnerId(partnerId, this.context);
	}
 }
