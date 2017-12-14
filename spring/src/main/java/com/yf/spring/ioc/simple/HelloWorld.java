package com.yf.spring.ioc.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloWorld implements HelloMessage{
    private Logger logger = LogManager.getLogger(HelloWorld.class);
    @Override
    public void sayHello(String message) {
        logger.debug(message);
    }
    public void init(){
       logger.debug("HelloWorld init...");
    }
    public void destroy(){
        logger.debug("HelloWorld destroy...");
    }
}
