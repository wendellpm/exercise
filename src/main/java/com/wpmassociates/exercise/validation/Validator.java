package com.wpmassociates.exercise.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;

public class Validator{
	
	public static boolean checkForNumeric(String input, ServletContext context) {	
		context.log("Input is " + input);
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(input);
		boolean match = matcher.matches();
		context.log("There is a match " + match);
		return match;
	}
	
}	
	