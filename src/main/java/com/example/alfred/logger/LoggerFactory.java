package com.example.alfred.logger;

/**
 * factory class to return the logger based on the type of logger
 * EventLogger, ExceptionLogger and HealthLogger
 *
 */
public class LoggerFactory implements AbstractLoggerFactory<AlfredLogger> {

	/**
	 * Gets the instance of the loggers specified by the loggerName
	 * 
	 * @param loggerName
	 * @return
	 */
	public AlfredLogger getLogger(String loggerName) {
		if (loggerName.equals("event"))
			return new EventLogger();
		if (loggerName.equals("exception"))
			return new ExceptionLogger();
		if (loggerName.equals("health"))
			return new HealthLogger();
		return null;
	}

}
