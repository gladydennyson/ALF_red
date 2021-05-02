package com.example.alfred.common;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Class to fetch application properties and data from the thread context
 *
 */
@Component
public class Properties {

	/**
	 * Application environment to fetch the data from the application properties
	 */
	private @Autowired Environment environment;

	/**
	 * @return true or false based on the application properties
	 */
	public Boolean getEvent() {
		return environment.resolvePlaceholders("${alfred.event}").matches("true");
	}

	/**
	 * @return true or false based on the application properties
	 */
	public Boolean getHealth() {
		return environment.resolvePlaceholders("${alfred.health}").matches("true");
	}

	/**
	 * @return true or false based on the thread context value set in the filter
	 */
	public Boolean getException() {
		return ThreadContext.get("req.exception").matches("true");
	}

	/**
	 * @return request id based on the thread context value set in the filter
	 */
	public String getRequestID() {

		return ThreadContext.get("req.id");
	}

	/**
	 * @return port based on the thread context value set in the filter
	 */
	public String getPort() {
		return ThreadContext.get("req.port");
	}

	/**
	 * @return URL based on the thread context value set in the filter
	 */
	public String getURL() {
		return ThreadContext.get("req.url");
	}

	/**
	 * @return method type (GET, POST ..) based on the thread context value set in
	 *         the filter
	 */
	public String getMethodType() {
		return ThreadContext.get("req.method").toLowerCase();
	}

	/**
	 * @return request headers based on the thread context value set in the filter
	 */
	public String getRequestHeaders() {
		return ThreadContext.get("req.headers");
	}

	/**
	 * @return request body based on the thread context value set in the filter
	 */
	public String getRequestBody() {
		return ThreadContext.get("req.requestBody");
	}

}
