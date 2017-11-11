package com.wpmassociates.exercise.test;

import com.wpmassociates.exercise.client.*;

import junit.framework.TestCase;

import java.util.Map;
import java.util.TreeMap;

public class CommonCodeTest extends TestCase {

	private Map<String, String> partnerAd = null;
	
	private String partnerId = "10";
	
	public CommonCodeTest() {
		partnerAd = new TreeMap<String, String>(CommonCode.getComparator());	
	}

	public void testMakePostRequest() throws Exception {
		partnerAd.put("partner_id", partnerId);
		partnerAd.put("duration", "1");
		partnerAd.put("ad_content", "This is the ad content");
		String response = CommonCode.makePostRequest(partnerAd);
	}
	
	public void testMakeGetRequest() throws Exception {
		String response = CommonCode.makeGetRequest(partnerId);
	}
	
}

