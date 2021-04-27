package com.example.alfred.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Properties {

	private @Autowired Environment environment;

	public Boolean getEvent() {
        return environment.resolvePlaceholders("${alfred.event}").matches("True");
    }
	
}
