package com.wpmassociates.exercise.domain;

public class PersistenceResult { 

	private int partnerId;
	private String result;
		
	public PersistenceResult() {}
	
	public PersistenceResult(int id, String result) {
		this.partnerId = id;
		this.result = result;
	}
	

	public int getPartnerId() {return partnerId;}
	
	public void setPartnerId(int id) {
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
