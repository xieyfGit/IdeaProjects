package com.yf.spring.ioc.reflect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyCar extends Car implements Flex {
    @Override
    public void fly() {
        logger.debug(this.getName()+" has "+this.wheelCount+" wheels,and can fly...");
    }
}
