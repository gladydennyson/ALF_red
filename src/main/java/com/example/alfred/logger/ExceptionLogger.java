package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

/**
 * class that sets the logger to the exception logger
 *
 */
public class ExceptionLogger extends AlfredLogger {

	/**
	 * Constructor to set the exception logger
	 */
	public ExceptionLogger() {
		this.logger = LogManager.getLogger("exceptionLog");
	}

}
