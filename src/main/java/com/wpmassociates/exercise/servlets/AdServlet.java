package com.wpmassociates.exercise.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

import com.wpmassociates.exercise.service.*;
import com.wpmassociates.exercise.constants.*;

public class AdServlet extends HttpServlet {

	private PrintWriter printWriter;
	private String responseString = null;
	
	private AdService service = null;

	@Override
	public void init() {
		service = new AdService();		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		printWriter = response.getWriter();
		String partnerId = request.getParameter(Constants.PARTNER_ID);
		responseString = service.retrieveData(Integer.parseInt(partnerId));
		response.setContentType("application/json,charset=UTF-8");
		printWriter.write(responseString);
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		printWriter = response.getWriter();
		BufferedReader reader = request.getReader();
		boolean success = service.processData(reader);
		response.setContentType("text/plain,charset=UTF-8");
		if (success) 
			printWriter.write("success");
		else 
			printWriter.write("partner id already exists");
	}
	
 }
