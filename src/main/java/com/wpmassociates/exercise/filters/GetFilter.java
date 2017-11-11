package com.wpmassociates.exercise.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

import com.wpmassociates.exercise.constants.*;
import com.wpmassociates.exercise.validation.*;

import java.util.logging.Logger;

public class GetFilter implements Filter {

	private ServletContext context = null;
	private PrintWriter printWriter = null;
	private Logger logger = null;
		
	public void init(FilterConfig filterConfiguration) throws ServletException {
		context = filterConfiguration.getServletContext();
		context.log(getClass().getName() + "initialized");
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		context.log("in doFilter() method");
		logger.info("in doFilter() method");
		printWriter = response.getWriter();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String method = httpRequest.getMethod();
		context.log("Method " + method);
		if (method.equals("GET")) {
			String uri = httpRequest.getRequestURI();	
			context.log("Requested resource "  +  uri);
			logger.info("Requested resource "  +  uri);		
			String sentId = uri.substring(Constants.ID_LOCATION);
			boolean validated = Validator.checkForNumeric(sentId, context);

			HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest);
			requestWrapper.setAttribute("isValidated", validated);
			if (validated) {
				context.log("Partner id " + sentId + " validated " + validated);
				logger.info("Partner id " + sentId + " validated " + validated);
				requestWrapper.setAttribute("partnerId", sentId);
				chain.doFilter(requestWrapper, response);
			} else {
				String responseString = Constants.NUMERIC;
				response.setContentType("text/plain,charset=UTF-8");
				logger.info("Partner id " + sentId + " not validated.");
				printWriter.write(responseString);
			}
		} else {
			chain.doFilter(request, response);
		}

	}
	
	public void destroy() {
		context.log("In destroy method");
	}
}
		
		
		