package com.example.alfred;

import com.example.alfred.logger.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class LoggerFactoryTest {
    //to be tested

    private EventLogger eventLogger;
    private ExceptionLogger exceptionLogger;
    private  HealthLogger healthLogger;
    @BeforeEach
    public void init() throws Exception{
        eventLogger = new EventLogger();
        exceptionLogger = new ExceptionLogger();
        healthLogger = new HealthLogger();
    }
    @Test
    public void testGetLogger() {
       assert (new LoggerFactory().getLogger("event") == eventLogger);
        assert (new LoggerFactory().getLogger("health") == healthLogger);
        assert (new LoggerFactory().getLogger("exception") == exceptionLogger);
    }
}
