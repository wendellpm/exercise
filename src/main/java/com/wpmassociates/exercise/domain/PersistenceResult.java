package com.wpmassociates.exercise.domain;

public class PersistenceResult { 

	private String partnerId;
	private String result;
		
	public PersistenceResult() {}
	
	public PersistenceResult(String id, String result) {
		this.partnerId = id;
		this.result = result;
	}
	
	public String getPartnerId() {return partnerId;}
	
	public void setPartnerId(String id) {
		this.partnerId = id;
	}
	
	public String getResult() {return result;}
	
	public void setResult(String result) {
		this.result = result;
	}

	public String toString() {
		return "Id " + getPartnerId() + "\nresult " + getResult();
	}
 }
