package com.example.alfred.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * Abstract factory class to log on various levels based on the type of logger
 * EventLogger, ExceptionLogger and HealthLogger
 *
 */
public abstract class LoggerFactory {

	// logger variable to log the data
	protected Logger logger;

	public LoggerFactory getLogger(String loggerName) {
		if (loggerName.equals("event"))
			return new EventLogger();
		if (loggerName.equals("exception"))
			return new ExceptionLogger();
		if (loggerName.equals("health"))
			return new HealthLogger();
		return null;
	}

	public void error(Exception exception) {

		ObjectMapper ow = new ObjectMapper();

		try {
			Map<String, Object> message = new HashMap<>();
			message.put("event", exception.getClass());
			message.put("message", exception.getMessage());
			message.put("stack", Throwables.getStackTraceAsString(exception));
			logger.log(Level.ERROR, ow.writeValueAsString(message));
		} catch (Exception e) {
			logger.log(Level.ERROR,
					"{\"event\":\"" + exception.getClass() + "\", \"message\":\"" + exception.getMessage()
							+ "\", \"trace\":\"" + Throwables.getStackTraceAsString(exception) + "\"}");
		}
	}

	public void info(String message) {

		logger.log(Level.INFO, message);
	}

	public void warn(String message) {

		logger.log(Level.WARN, message);
	}

	public void debug(String message) {
		logger.log(Level.DEBUG, message);
	}

}
