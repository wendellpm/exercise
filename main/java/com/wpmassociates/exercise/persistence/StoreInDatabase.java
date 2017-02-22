package com.wpmassociates.exercise.persistence;

import java.util.Properties;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONException;

import com.wpmassociates.exercise.domain.*;

public class StoreInDatabase implements StoreData { 

	private Connection connection = null;            
    private PreparedStatement preparedStatement = null;     
    private String insertStatement = null;
	private String queryStatement = null;
	private String deleteStatement = null;
	private Timestamp timestamp = null;
	private ResultSet resultSet = null;
	private JSONMapStorageObject adObject = null;
	private int duration = 0;
	private String adContent = null;

	public void init(Properties properties) {
		try {
			String url = (String)properties.get("mysqlUrl");
			String databaseName = (String)properties.get("databaseName");
			String username = (String)properties.get("dbUsername");
			String password = (String)properties.get("dbPassword");
			Class.forName("com.mysql.jdbc.Driver");
			String connectionDatabase = url + "/" + databaseName;
			connection = DriverManager.getConnection(connectionDatabase, username, password);
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
            queryStatement = "select json_string, entry_date, json_string from json where partner_id = ?";  
            preparedStatement = connection.prepareStatement(queryStatement);
			preparedStatement.setInt(1, partnerId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				jsonString = resultSet.getString(1);
				timestamp = resultSet.getTimestamp(2);
				jsonString = resultSet.getString(3);
			}
			long entryTime = timestamp.getTime();
			JSONObject jsonObject = new JSONObject(jsonString);
			int duration = Integer.parseInt((String)jsonObject.get("duration"));
			String adContent = (String)jsonObject.get("ad-content");
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
 }
