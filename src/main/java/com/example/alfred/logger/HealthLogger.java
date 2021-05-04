package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

/**
 * class that sets the logger to the health logger
 *
 */
public class HealthLogger extends AlfredLogger {

	/**
	 * Constructor to set the health logger
	 */
	public HealthLogger() {

		this.logger = LogManager.getLogger("healthLog");
	}

}
