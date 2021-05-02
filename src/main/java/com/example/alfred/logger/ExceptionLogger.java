package com.example.alfred.logger;

import org.apache.logging.log4j.LogManager;

public class ExceptionLogger extends LoggerFactory{

    public ExceptionLogger(){
        this.logger = LogManager.getLogger("exceptionLog");
    }

}
