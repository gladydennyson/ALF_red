package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

public class EventLogger extends LoggerFactory{

    public EventLogger(){
        this.logger = LogManager.getLogger("eventLog");
    }
}
