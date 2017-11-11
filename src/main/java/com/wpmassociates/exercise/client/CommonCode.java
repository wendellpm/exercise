package com.wpmassociates.exercise.client;
 
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
 
public class CommonCode {
	
 	private static String URL = "http://localhost:8080/ad";
		
	public static String makePostRequest(Map<String, String> partnerAd) {
		try {
			String jsonString = new GsonBuilder().create().toJson(partnerAd, Map.class);
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new StringEntity(jsonString));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Connection", "keep-alive");
			HttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);
			return handleResponse(httpResponse);
		} catch (ClientProtocolException exception) {
			exception.printStackTrace();
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static String makeGetRequest(String partnerId) {
		try {
			HttpGet httpGet = new HttpGet(URL + "/" + partnerId);
			httpGet.setHeader("Accept", "text/plain");
			httpGet.setHeader("Content-type", "text/plain");
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
			return handleResponse(httpResponse);
		} catch (ClientProtocolException exception) {
			exception.printStackTrace();
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public static String handleResponse(HttpResponse response) { 
		StringBuffer result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException exception) {}
		StatusLine statusLine = response.getStatusLine();
		return result.toString() + "code " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase();
	}

	public static Comparator<String> getComparator() {
		Comparator<String> comparator = new Comparator<String>(){
			   public int compare(String first, String second) {
			       return second.compareTo(first);
			   }
		};
		return comparator;
	}
}
