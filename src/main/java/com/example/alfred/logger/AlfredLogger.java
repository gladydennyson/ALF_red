package com.example.alfred.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.example.alfred.common.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * Logger class that implements the basic logging functions
 *
 */
public class AlfredLogger {

	// logger variable to log the data
	protected Logger logger;

	/**
	 * loggs an exception with the name, message and the stacktrace
	 * 
	 * @param exception
	 */
	public void error(Exception exception) {

		ObjectMapper ow = new ObjectMapper();

		// adds the name message and stacktrace to the a map and writes it as a
		// serealized json string
		try {
			Map<String, Object> message = new HashMap<>();
			message.put("event", exception.getClass());
			message.put("message", exception.getMessage());
			message.put("stack", Throwables.getStackTraceAsString(exception));
			message.put("requestid", new Properties().getRequestID());
			logger.log(Level.ERROR, ow.writeValueAsString(message));
		} catch (Exception e) {
			// in case the object mapper fails
			logger.log(Level.ERROR,
					"{\"event\":\"" + exception.getClass() + "\", \"message\":\"" + exception.getMessage()
							+ "\", \"requestid\":\"" + new Properties().getRequestID() + "\", \"trace\":\""
							+ Throwables.getStackTraceAsString(exception) + "\"}");
		}
	}

	/**
	 * implements the info log
	 * 
	 * @param message
	 */
	public void info(String message) {

		logger.log(Level.INFO, message);
	}

	/**
	 * implements the warning log
	 * 
	 * @param message
	 */
	public void warn(String message) {

		logger.log(Level.WARN, message);
	}

	/**
	 * implements the debug log
	 * 
	 * @param message
	 */
	public void debug(String message) {
		logger.log(Level.DEBUG, message);
	}
}