package com.example.alfred.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
