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

	private Connection connection; 
	private Properties properties;
    private PreparedStatement preparedStatement;     
    private String insertStatement = null;
	private String queryStatement = null;
	private String deleteStatement = null;
	private Timestamp timestamp = null;
	private ResultSet resultSet = null;
	private JSONMapStorageObject adObject = null;
	private ServletContext context = null;

	public StoreInDatabase(ServletContext context, Properties properties) {
		this.context = context;	
		setProperties(properties);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
        context.log("In storeData method " + partnerId + " " + storageObject.toString());
		int added = 0;
        try { 
			String jsonString = storageObject.getJsonString();
			long entryTime = storageObject.getEntryTime().getTime();
			timestamp = new Timestamp(entryTime);
            insertStatement = "insert into json(partner_id, json_string, entry_date) values(?, ?, ?)"; 
            String driverName = properties.getProperty("driverName");
			Class.forName(driverName);      
            String dataBaseString = properties.getProperty("mysqlUrl");
			dataBaseString += "/";
			dataBaseString += properties.getProperty("databaseName");
			String user = properties.getProperty("dbUsername");
			String password = properties.getProperty("dbPassword");
			connection = DriverManager.getConnection(dataBaseString, user, password);
            preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, jsonString);
			preparedStatement.setTimestamp(3, timestamp);	
			added = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception){
			exception.getMessage();
		} finally {                                                       
            if (preparedStatement != null) {
				try {
					preparedStatement.close();                      
				} catch (SQLException exception) {}
			}                                                        
                                             
        } 
        context.log("Before return added " + added);
		return (added == 1);
	}	
		
	public JSONMapStorageObject retrieveData(int partnerId) {
		String jsonString = null;
		context.log("Partner id in retrieveData() " + partnerId);
        try { 
        	String driverName = properties.getProperty("driverName");
 			Class.forName(driverName);  
            String dataBaseString = properties.getProperty("mysqlUrl");
 			dataBaseString += "/";
 			dataBaseString += properties.getProperty("databaseName");
 			String user = properties.getProperty("dbUsername");
 			String password = properties.getProperty("dbPassword");
 			connection = DriverManager.getConnection(dataBaseString, user, password);
         	queryStatement = "select json_string, entry_date from json where partner_id = ?";  
            preparedStatement = connection.prepareStatement(queryStatement);
			preparedStatement.setInt(1, partnerId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet == null)
				throw new SQLException("ResultSet null");
			resultSet.first();
			jsonString = resultSet.getString("json_string");
			timestamp = resultSet.getTimestamp("entry_date");
			if (jsonString == null || timestamp == null)
				throw new SQLException("Null values returned");
			long entryTime = timestamp.getTime();
			JSONObject jsonObject = new JSONObject(jsonString);
			long duration = Long.valueOf(Integer.parseInt((String)jsonObject.get("duration"))) * Constants.DAYINMILLISECONDS;
			String adContent = (String)jsonObject.get("ad_content");
			adObject = new JSONMapStorageObject(new Date(entryTime), jsonString, partnerId, duration, adContent);
        } catch (SQLException | JSONException | ClassNotFoundException exception){
			exception.getMessage();
		} finally {                                                       
            if (preparedStatement != null) {
				try {
					preparedStatement.close();                      
				} catch (SQLException exception) {}
			}                                          
        } 
		return adObject;
	}		

	public boolean deleteData(int partnerId) {
		//to be implemented
		return true;
	}
	
	public boolean checkForPartnerId(int partnerId) {
		boolean exists = false;
		context.log("In checkForPartnerId " + partnerId);
		try {
			queryStatement = "select partner_id from json where partner_id = ?";
			String driverName = properties.getProperty("driverName");
			Class.forName(driverName);
			String dataBaseString = properties.getProperty("mysqlUrl");
			dataBaseString += "/";
			dataBaseString += properties.getProperty("databaseName");
			String user = properties.getProperty("dbUsername");
			String password = properties.getProperty("dbPassword");
			connection = DriverManager.getConnection(dataBaseString, user, password);
			preparedStatement = connection.prepareStatement(queryStatement);
			preparedStatement.setInt(1, partnerId);
			resultSet = preparedStatement.executeQuery();
			exists = (resultSet.first());
		} catch (SQLException | ClassNotFoundException exception){
			exception.getMessage();
		} 
		context.log("Exists is " + exists);
	   	return exists;
	}
 }
