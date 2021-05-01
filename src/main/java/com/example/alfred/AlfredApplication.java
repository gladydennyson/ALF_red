package com.example.alfred;

import com.example.alfred.logger.EventLogger;
import com.example.alfred.logger.HealthLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlfredApplication {

	//test
	private static HealthLogger logger = new HealthLogger();

	public static void main(String[] args) {
		System.out.println("Hello!");
		logger.info("health log");
		SpringApplication.run(AlfredApplication.class, args);
	}

}
