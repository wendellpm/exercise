package com.wpmassociates.exercise.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

import com.wpmassociates.exercise.persistence.*;
import com.wpmassociates.exercise.service.*;

public class AdServlet extends HttpServlet {

	private PrintWriter printWriter;
	private AdService service;
	String returnString = null;
	
	private final String PARTNER_ID = "partner_id";

	@Override
	public void init() {
		service = new AdService();
		service.init();
	}	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		printWriter = response.getWriter();
		String partnerId = request.getParameter(PARTNER_ID);
		returnString = service.retrieveData(Integer.parseInt(partnerId));
		response.setContentType("application/json,charset=UTF-8");
		printWriter.write(returnString);
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		printWriter = response.getWriter();
		BufferedReader reader = request.getReader();
		boolean success = service.processData(reader);
		response.setContentType("text/plain,charset=UTF-8");
		if (success) printWriter.write("success");
	}
	
 }
