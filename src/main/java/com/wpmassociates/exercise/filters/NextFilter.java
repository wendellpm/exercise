package com.wpmassociates.exercise.filters;

import java.io.IOException;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Reader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.xml.transform.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

@WebFilter("/NextFilter")
public class NextFilter implements Filter {

	private ServletContext context;
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
		context.log(getClass().getName() + " initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		context.log(this.getClass().getDeclaredMethods()[2] + " does output conversion");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String contentType = null;;
		String styleSheet = null;
		String type = request.getParameter("type");
		if (type == null || type.equals("")) {
			contentType = "text/html";
			styleSheet = "/xml/html.xsl";
		} else {
			if (type.equals("xml")) {
				contentType = "text/plain";
				styleSheet = "/xml/xml.xsl";
			} else {
				contentType = "text/html";
				styleSheet = "/xml/html.xsl";
			}
		}
		response.setContentType(contentType);
		String stylePath = filterConfig.getServletContext().getRealPath(styleSheet);
		Source styleSource = new StreamSource(stylePath);
		PrintWriter out = response.getWriter();
		CharResponseWrapper responseWrapper = new CharResponseWrapper(httpResponse);
		chain.doFilter(request, responseWrapper);

		StringReader sr = new StringReader(responseWrapper.toString());
		Source xmlSource = new StreamSource((Reader)sr);

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(styleSource);
			CharArrayWriter caw = new CharArrayWriter();
			StreamResult result  = new StreamResult(caw);
			transformer.transform(xmlSource, result);
			response.setContentLength(caw.toString().length());
			out.write(caw.toString());
		} catch (Exception exception) {
			out.println(exception.toString());
			out.write(responseWrapper.toString());
		}
		*/
	}

	@Override
	public void destroy() {}


}