package com.wpmassociates.exercise.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import com.wpmassociates.exercise.service.*;
import com.wpmassociates.exercise.constants.*;

public class AdServlet extends HttpServlet {

	private PrintWriter printWriter;
	private String responseString = null;
	
	private AdService service = null;

	private ServletContext context = null;
	
	@Override
	public void init() {
		context = getServletConfig().getServletContext();
		service = new AdService(context);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		printWriter = response.getWriter();
		String uri = request.getRequestURI();
		String sentId = uri.substring(Constants.ID_LOCATION);
		Enumeration<String> headerNames = request.getHeaderNames();
      	String accumulator = "Headers\n";
     	while(headerNames.hasMoreElements()) {
       	  	String paramName = headerNames.nextElement();
        	accumulator += "\t" + paramName + "\t";
        	String paramValue = request.getHeader(paramName);
        	accumulator += "\t" + paramValue + "\n";
      	}
		log(accumulator);
		log("Partner id " + sentId);
		int partnerInteger = 0;
		if (sentId != null)
			partnerInteger = Integer.parseInt(sentId);
		responseString = service.retrieveData(Integer.parseInt(sentId));
		response.setContentType("application/json,charset=UTF-8");
		printWriter.write(responseString);
		
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		printWriter = response.getWriter();
		BufferedReader reader = request.getReader();
		response.setContentType("text/plain,charset=UTF-8");
		result = service.processData(reader);
		log("Result is " + result);
		if (result.equals("exists")) 
			printWriter.write("partner id already exists");
		else if (result.equals("added"))
			printWriter.write("partner added");
		else if (result.equals("problem"))
			printWriter.write("partner not added");
	}
	
 }
