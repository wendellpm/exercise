package com.wpmassociates.exercise.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/GetFilter")
public class GetFilter implements Filter {

	private ServletContext context;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
		context.log("GetFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		context.log(this.getClass().getDeclaredMethods()[2] + " does nothing yet");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}

}
