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
import com.wpmassociates.exercise.domain.*;
import com.wpmassociates.exercise.validation.*;

public class AdServlet extends HttpServlet {

	private PrintWriter printWriter;
	private String responseString = null;
	
	private AdService service = null;

	private ServletContext context = null;
	
	@Override
	public void init() {
		context = getServletConfig().getServletContext();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service = new AdService(context);
		String sentId = (String)request.getAttribute("partnerId");
		boolean validated = (Boolean)request.getAttribute("isValidated");
		context.log("doGet id is " + sentId);
		printWriter = response.getWriter();

		Enumeration<String> headerNames = request.getHeaderNames();
      	String accumulator = "Headers\n";
     	while(headerNames.hasMoreElements()) {
       	  	String paramName = headerNames.nextElement();
        	accumulator += "\t" + paramName + "\t";
        	String paramValue = request.getHeader(paramName);
        	accumulator += "\t" + paramValue + "\n";
      	}
		context.log(accumulator);

		int partnerInteger = 0;
		
		if (validated){
			partnerInteger = Integer.parseInt(sentId);
			responseString = service.retrieveData(Integer.parseInt(sentId));	
			response.setContentType("application/json,charset=UTF-8");
		} else {
			responseString = Constants.NUMERIC;
			response.setContentType("text/plain,charset=UTF-8");
		}	
		printWriter.write(responseString);
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service = new AdService(context);
		PersistenceResult result = null;
		printWriter = response.getWriter();
		BufferedReader reader = request.getReader();
		response.setContentType("text/plain,charset=UTF-8");
		result = service.processData(reader);
		if (result.getResult().equals("exists")) 
			context.log(Constants.ALREADY_EXISTS + result.getPartnerId());
		else if (result.getResult().equals("added"))
			context.log(Constants.ADDED + result.getPartnerId());
		else if (result.getResult().equals("problem"))
			context.log(Constants.NOT_ADDED + result.getPartnerId());
		else if (result.getResult().equals(Constants.NUMERIC))
			context.log(Constants.NUMERIC + result.getPartnerId());
	
	}
	
 }
