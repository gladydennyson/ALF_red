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
		return ThreadContext.get("req.exception").matches("true");
	}
	
	public String getRequestID() {
		
		return ThreadContext.get("req.id");
    }
	
	public String getPort() {
		return ThreadContext.get("req.port");
    }
	
	public String getURL() {
		return ThreadContext.get("req.url");
	}

	public String getMethodType() {
		return ThreadContext.get("req.method").toLowerCase();
	}
	
	public String getRequestHeaders() {
		return ThreadContext.get("req.headers");
    }
	
	public String getRequestBody() {
		return ThreadContext.get("req.requestBody");
    }

}
