package com.example.alfred.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthLogger{

    private Logger logger;

    public HealthLogger(){

        this.logger = LogManager.getLogger("healthLog");
        System.out.println("-------"+this.logger);
    }

    public void debug(String message) {

        logger.log(Level.DEBUG,  message);
    }

    public void info(String message) {

        logger.log(Level.INFO, message);
    }

    public void warn(String message) {

        logger.log(Level.WARN, message);
    }


}
