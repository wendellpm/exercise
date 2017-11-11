package com.wpmassociates.exercise.servlets;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {

	private DateFormat formatter = null;

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		logger.info("Time " + getDate() + " " + getClass().getName() + " attribute created");
	}
	
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		 logger.info("Time " + getDate() + " " + getClass().getName() + " attribute replaced");
	}
	
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		 logger.info("Time " + getDate() + " " + getClass().getName() + " attribute removed");
	}
	
	private String getDate() {
		formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:SS");
		return formatter.format(new Date());
	}
	
 }
