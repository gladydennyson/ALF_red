package com.example.alfred.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration class to scan the project package.
 * 
 * To be imported by the user application to enable the framework
 *
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = "com.example.alfred")
public class AlfredConfiguration {
	
}