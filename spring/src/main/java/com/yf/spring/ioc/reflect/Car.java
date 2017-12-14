package com.yf.spring.ioc.reflect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Car {

    protected Logger logger = LogManager.getLogger("com.yf.spring.ioc.reflect");

    private String name;

    public int wheelCount;

    public Car() {
    }

    public Car(String name, int wheelCount) {
        this.name = name;
        this.wheelCount = wheelCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWheelCount() {
        return wheelCount;
    }

    public void setWheelCount(int wheelCount) {
        this.wheelCount = wheelCount;
    }

    public void drive(){
        logger.debug(this.getName()+" has "+this.wheelCount+" wheels,and can drive...");
    }


    private void music(){
        logger.debug(this.getName()+" has "+this.wheelCount+" wheels,and can listen music...");
    }
}
