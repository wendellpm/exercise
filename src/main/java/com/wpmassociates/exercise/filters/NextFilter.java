package com.wpmassociates.exercise.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/NextFilter")
public class NextFilter implements Filter {

	private ServletContext context = null;
	
	public void init(FilterConfig filterConfiguration) throws ServletException {
		context = filterConfiguration.getServletContext();
		context.log("NextFilter initialized");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		context.log(this.getClass().getDeclaredMethods()[2] + " shows the uri for the requested resource");
		String uri = httpRequest.getRequestURI();
		context.log("Requested resource "  +  uri);
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}

}
		
		
		