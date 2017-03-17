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

import com.wpmassociates.exercise.constants.*;
import com.wpmassociates.exercise.validation.*;

public class GetFilter implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig filterConfiguration) throws ServletException {
		context = filterConfiguration.getServletContext();
		context.log(getClass().getName() + "initialized");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String method = httpRequest.getMethod();
		context.log("Method " + method);
		if (method.equals("GET")) {
			String uri = httpRequest.getRequestURI();	
			context.log("Requested resource "  +  uri);
			String sentId = uri.substring(Constants.ID_LOCATION);
			boolean validated = Validator.checkForNumeric(sentId, context);
			context.log("Partner id " + sentId + " validated " + validated);
			HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest);
			requestWrapper.setAttribute("isValidated", validated);
			if (validated)
				requestWrapper.setAttribute("partnerId", sentId);
			chain.doFilter(requestWrapper, response);
		}
		chain.doFilter(request, response);
	}
	
	public void destroy() {
		context.log("In destroy method");
	}
}
		
		
		