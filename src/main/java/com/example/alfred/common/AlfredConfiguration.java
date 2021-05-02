package com.example.alfred.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to scan the project package.
 * 
 * To be imported by the user application to enable the framework
 *
 */
@Configuration
@ComponentScan(basePackages = "com.example.alfred")
public class AlfredConfiguration {
	
}