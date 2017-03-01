package com.wpmassociates.exercise.service;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.Writer;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Set;
import java.io.InputStream;

import com.wpmassociates.exercise.persistence.*;
import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.constants.*;

import java.util.Date;
import static java.lang.System.currentTimeMillis;

public class AdService {

	private Writer fileWriter;
	private StoreData persist;
	private Properties properties;
	private String jsonString = null;

	public AdService() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("com/wpmassociates/exercise/service/default.properties");
		properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException exception) {}
		String propertiesString = "";
		Set<String> keys = properties.stringPropertyNames();
		for (String key : keys)
			propertiesString += (key + " " + properties.getProperty(key) + "\n");
		try {
			writeToFile(propertiesString);
		} catch (IOException exception) {}
		String useMap = properties.getProperty("useMap");
		if (useMap.equals("yes"))
			persist = new StoreDataMap();
		else 
			persist = new StoreInDatabase();
		persist.init(properties);
	}	
	
	public String retrieveData(int partnerId) {
		
		JSONMapStorageObject adObject = null;
		adObject = persist.retrieveData(partnerId);
		Date date = adObject.getEntryTime();
		long duration = adObject.getDuration();
		try {
			writeToFile("Duration " + duration);
		} catch (IOException exception) {}
		if (currentTimeMillis() > date.getTime() + duration)
			return Constants.EXCEEDS;
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
		long milliseconds = duration * Constants.DAYINMILLISECONDS;
		JSONMapStorageObject storageObject = new JSONMapStorageObject(new Date(), jsonString, milliseconds, partnerId, adContent);	
		return persist.storeData(partnerId, storageObject);
	}
	
	private void writeToFile(String string) throws IOException {
		fileWriter = new FileWriter("C:/Documents and Settings/Wendell/Desktop/log.txt", true);
		fileWriter.write(string);
		fileWriter.flush();
		fileWriter.close();
	}
	
 }
