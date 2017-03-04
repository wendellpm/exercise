package com.wpmassociates.exercise.persistence;

import java.util.Properties;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;

import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.constants.*;
import com.wpmassociates.exercise.service.*;

public class StoreInDatabase implements StoreData { 

	private Connection connection = null;            
    private PreparedStatement preparedStatement = null;     
    private String insertStatement = null;
	private String queryStatement = null;
	private String deleteStatement = null;
	private Timestamp timestamp = null;
	private ResultSet resultSet = null;
	private JSONMapStorageObject adObject = null;
	private String adContent = null;
	
	public StoreInDatabase(Properties properties) {
		try {
			Class.forName(properties.getProperty("driverName"));
			connection = DriverManager.getConnection(properties.getProperty("mysqlUrl") + "/" + properties.getProperty("databaseName"), properties.getProperty("dbUsername"), properties.getProperty("dbPassword"));
		} catch (SQLException | ClassNotFoundException exception) {
			exception.getMessage();
		}
	}

	public boolean storeData(int partnerId, JSONMapStorageObject storageObject) {
		boolean result = false;
        try { 
			String jsonString = storageObject.getJsonString();
			long entryTime = storageObject.getEntryTime().getTime();
			timestamp = new Timestamp(entryTime);
            insertStatement = "insert into json(partner_id, json_string, entry_date) values(?, ?, ?)"; 
            preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, jsonString);
			preparedStatement.setTimestamp(3, timestamp);
			result = preparedStatement.execute();
        } catch (Exception exception){
			exception.getMessage();
		} finally {                                                       
            if (preparedStatement != null) {
				try {
					preparedStatement.close();                      
				} catch (SQLException exception) {}
			} 
        } 
		return result;
	}	
		
	public JSONMapStorageObject retrieveData(int partnerId) {
		String jsonString = null;
        try { 
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
			long duration = Integer.parseInt((String)jsonObject.get("duration")) * Constants.DAYINMILLISECONDS;
			String adContent = (String)jsonObject.get("ad_content");
			adObject = new JSONMapStorageObject(new Date(entryTime), jsonString, duration, partnerId, adContent);
        } catch (Exception exception){
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
		try {
		queryStatement = "select partner_id from json where partner_id = ?";
        preparedStatement = connection.prepareStatement(queryStatement);
		preparedStatement.setInt(1, partnerId);
		resultSet = preparedStatement.executeQuery();
		} catch (Exception exception){
			exception.getMessage();
		} finally {                                                       
            if (preparedStatement != null) {
				try {
					preparedStatement.close();                      
				} catch (SQLException exception) {}
			} 
	    } 
		return (resultSet != null);
	}
	
	
 }
