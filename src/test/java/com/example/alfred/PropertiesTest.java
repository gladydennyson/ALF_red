package com.example.alfred;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class PropertiesTest {


    //to be tested and verified.
    private @Autowired
    Environment environment;
    @Test
    public void testGetEvent(){
        assert (environment.resolvePlaceholders("${alfred.event}").matches("true"));
    }
    @Test
    public void testGetHealth(){
        assert (environment.resolvePlaceholders("${alfred.health}").matches("true"));
    }
    @Test
    public void testGetException(){
        assert (environment.resolvePlaceholders("${req.exception}").matches("true"));
    }
}
