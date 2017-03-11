package com.wpmassociates.exercise.client;

import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import static java.lang.System.in;
import static java.lang.System.out;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientRequest {

	private static String partnerId = null;
	private static String duration = null;
	private static String adContent = null;
	private static Map<String, String> partnerAd = new HashMap<String, String>();
	private static String jsonString = "";
	private static HttpResponse httpResponse = null;
	private static BufferedReader reader = null;
	private static StringBuffer result = null;
	
	public static void main(String...inputs) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(in);
			out.print("Enter partnerId ");
			if (scanner.hasNext()) {
				partnerId = scanner.next();
				partnerAd.put("partner_id", partnerId);
			}
			out.print("Enter duration as days ");
			if (scanner.hasNext()) {
				duration = scanner.next();
				partnerAd.put("duration", duration);
			}
			out.print("Enter ad content ");
			if (scanner.hasNext()) {
				adContent = scanner.next();
				partnerAd.put("ad_content", adContent);
			}
		} catch (Exception exception) {}	
			jsonString = new GsonBuilder().create().toJson(partnerAd, Map.class);
			String response = makePostRequest("/ad", jsonString);
			out.println("Response is " + response);
	}

	private static String makePostRequest(String uri, String jsonString) {
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(jsonString));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpResponse = new DefaultHttpClient().execute(httpPost);
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

	private static String handleResponse(HttpResponse response) { 
		out.println("Response Code " +                                    response.getStatusLine().getStatusCode());
		try {
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException exception) {}
		return result.toString();
	}
}



/*
HttpURLConnection httpcon;  
String url = null;
String data = null;
String result = null;
try {
  //Connect
  httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
  httpcon.setDoOutput(true);
  httpcon.setRequestProperty("Content-Type", "application/json");
  httpcon.setRequestProperty("Accept", "application/json");
  httpcon.setRequestMethod("POST");
  httpcon.connect();

  //Write       
  OutputStream os = httpcon.getOutputStream();
  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
  writer.write(data);
  writer.close();
  os.close();

  //Read        
  BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

  String line = null; 
  StringBuilder sb = new StringBuilder();         

  while ((line = br.readLine()) != null) {  
    sb.append(line); 
  }         

  br.close();  
  result = sb.toString();

} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
} 
*/