package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

public class HealthLogger extends LoggerFactory {

	public HealthLogger() {

		this.logger = LogManager.getLogger("healthLog");
	}

}
