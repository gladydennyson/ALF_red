package com.example.alfred.healthcheck;


import com.codahale.metrics.health.HealthCheck;

/**
 * Application health check from the metrics library
 *
 */
public class AlfredHealthCheck extends HealthCheck{
	
	/**
	 * overriding the check method to return the health
	 */
	@Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}

