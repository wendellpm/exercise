package com.wpmassociates.exercise.persistence;

import java.util.Properties;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONException;
import javax.servlet.ServletContext;
import java.util.Date;

import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.constants.*;

public class StoreInDatabase implements StoreData { 

	private Connection connection = null; 
	private Properties properties = null;
    private PreparedStatement preparedStatement = null;     
    private String insertStatement = null;
	private String queryStatement = null;
	private String testStatement = null;
	private Timestamp timestamp = null;
	private ResultSet resultSet = null;
	private JSONMapStorageObject adObject = null;
	private ServletContext context = null;

	
	public StoreInDatabase(ServletContext context) {
		this.context = context;		
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	private Properties getProperties() {
		return this.properties;
	}
	
	private void setConnection(Connection connection) {
		this.connection = getConnection();
	}
	
	
	private Connection getConnection() {
		try {
			properties = this.getProperties();
			if (properties == null)
				context.log("Properties null");
			else 
				context.log("Properties not null");
			connection = DriverManager.getConnection(properties.getProperty("mysqlUrl") + "/" + properties.getProperty("databaseName"), properties.getProperty("dbUsername"), properties.getProperty("dbPassword"));
		} catch (SQLException exception) {}
		return connection;
		}
	
	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
		boolean added = false;
        try { 
			String jsonString = storageObject.getJsonString();
			long entryTime = storageObject.getEntryTime().getTime();
			timestamp = new Timestamp(entryTime);
            insertStatement = "insert into json(partner_id, json_string, entry_date) values(?, ?, ?)"; 
            preparedStatement = getConnection().prepareStatement(insertStatement);
			preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, jsonString);
			preparedStatement.setTimestamp(3, timestamp);
			return preparedStatement.execute();
        } catch (Exception exception){
			exception.getMessage();
        }
		return false;
	}	
		
	public JSONMapStorageObject retrieveData(int partnerId) {
		String jsonString = null;
		context.log("Partner id in retrieveData() " + partnerId);
        try { 
			jsonString = "{\"partner_id\":\"10\", \"duration\":\"1\";\"ad_content\":\"This is the ad content\"}";
			JSONObject jsonObject = new JSONObject(jsonString);
			long duration = Constants.DAYINMILLISECONDS;
			String adContent = "This is the ad content";
			adObject = new JSONMapStorageObject(new Date(), jsonString, partnerId, duration, adContent);
        } catch (JSONException exception){
				exception.getMessage();
        }
		return adObject;
	}		

	public boolean deleteData(int partnerId) {
		//to be implemented
		return true;
	}
	
	public boolean checkForPartnerId(int partnerId) {
		int exists = 1;
		context.log("In checkForPartnerId " + partnerId);
		
		try {
			connection = DriverManager.getConnection(properties.getProperty("mysqlUrl") + "/" + properties.getProperty("databaseName"), properties.getProperty("dbUsername"), properties.getProperty("dbPassword"));
			if (connection == null)
				context.log("connection value null in checkForPartnerId()");
			testStatement = "select partner_id from json where partner_id = ?";
			preparedStatement = getConnection().prepareStatement(testStatement);
        if (preparedStatement == null)
			context.log("preparedStatement null in checkForPartnerId()");
        preparedStatement.setInt(1, partnerId);
		exists = preparedStatement.executeUpdate();
		context.log("Exists is " + exists);
		} catch (Exception exception){
			exception.getMessage();
		} 
	   	return true;
	}
	
	
 }
