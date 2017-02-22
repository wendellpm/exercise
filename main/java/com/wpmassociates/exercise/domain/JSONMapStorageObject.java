package com.wpmassociates.exercise.domain;

import java.util.Date;

public class JSONMapStorageObject { 

	private Date entryTime;
	private String jsonString;
	private long duration;
	private int partnerId;
	private String adContent;
	
	public JSONMapStorageObject() {}
	
	public JSONMapStorageObject(Date entryTime, String jsonString, long duration, int partnerId, String adContent) {
		this.entryTime = entryTime;
		this.jsonString = jsonString;
		this.duration = duration;
		this.partnerId = partnerId;
		this.adContent = adContent;
	}

	public void setEntryTime(Date time) {
		this.entryTime = time;
	}
	
	public Date getEntryTime() {return entryTime;}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {	return duration;
	}
	
	public void setJsonString(String string) {
		this.jsonString = string;
	}
	
	public String getJsonString() {return jsonString;}
		
	public int getPartnerId() {return partnerId;}
	
	public void setPartnerId(int id) {
		this.partnerId = id;
	}
	
	public String getAdContent() {return adContent;}
	
	public void setAdContent(String content) {
		this.adContent = content;
	}

 }
