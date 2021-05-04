package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

/**
 * class that sets the logger to the event logger
 *
 */
public class EventLogger extends AlfredLogger {

	/**
	 * Constructor to set the event logger
	 */
	public EventLogger() {
		this.logger = LogManager.getLogger("eventLog");
	}
}
