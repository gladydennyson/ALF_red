package com.example.alfred.common;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Properties {

	private @Autowired Environment environment;

	public Boolean getEvent() {
		return environment.resolvePlaceholders("${alfred.event}").matches("true");
	}

	public Boolean getHealth() {
		return environment.resolvePlaceholders("${alfred.health}").matches("true");
	}

	public Boolean getException() {
		return ThreadContext.get("exception")!=null?ThreadContext.get("exception").matches("true"):false;
	}

	public String getURL() {
		return ThreadContext.get("exception");
	}

	public String getURLType() {
		return ThreadContext.get("exception");
	}
	
	public String getRequestBody() {
        return ThreadContext.get("exception");
    }
}
