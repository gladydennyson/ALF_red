package com.example.alfred;

import com.example.alfred.logger.EventLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlfredApplication {

	//test
	private static EventLogger logger = new EventLogger();

	public static void main(String[] args) {
		System.out.println("Hello!");
		logger.debug("test event log");
		SpringApplication.run(AlfredApplication.class, args);
	}

}
