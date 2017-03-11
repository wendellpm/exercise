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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientRequest {

	private static String partnerId = null;
	private static String duration = null;
	private static String adContent = null;
	private static Map<String, String> partnerAd = new HashMap<String, String>();

	private static HttpResponse httpResponse = null;
	private static BufferedReader reader = null;
	private static StringBuffer result = null;
	private static Scanner scanner = null;
	
	public static void main(String...inputs) {
		scanner = new Scanner(in);
		out.print("Enter partnerId ");
		partnerId = scanner.nextLine();
		partnerAd.put("partner_id", partnerId);
		out.print("Enter duration as days ");
		duration = scanner.nextLine();
		partnerAd.put("duration", duration);
		out.print("Enter ad content ");
		adContent = scanner.nextLine();
		partnerAd.put("ad_content", adContent);
		String jsonString = new GsonBuilder().create().toJson(partnerAd, Map.class);
		String response = makePostRequest("http://localhost:8080/ad", jsonString);
		out.println("Response is " + response);
	}
	
	/*
	private static void doGet(Scanner scanner) {
		out.print("Enter partner id ");
		partnerId = scanner.next();
		String response = makeGetRequest("http://localhost:8080/ad");
		out.println("Response is " + response);
	}

	*/
	
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

	/*
	private static String makeGetRequest(String uri) {
		try {
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Accept", "text/plain");
			httpGet.setHeader("Content-type", "text/plain");
			httpResponse = new DefaultHttpClient().execute(httpGet);
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
	*/
	
	private static String handleResponse(HttpResponse response) { 
		out.println("Response Code " + response.getStatusLine().getStatusCode());
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
