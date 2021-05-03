package com.example.alfred.logger;

/**
 * Generic interface to get the logger type based on the logger name
 *
 * @param <T>
 */
public interface AbstractLoggerFactory<T> {
	T getLogger(String loggerName);
}