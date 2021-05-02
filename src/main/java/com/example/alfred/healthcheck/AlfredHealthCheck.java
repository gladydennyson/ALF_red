package com.example.alfred.healthcheck;


import com.codahale.metrics.health.HealthCheck;

public class AlfredHealthCheck extends HealthCheck{
	
	@Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}

