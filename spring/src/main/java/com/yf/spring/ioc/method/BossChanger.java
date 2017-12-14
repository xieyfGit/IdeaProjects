package com.yf.spring.ioc.method;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class BossChanger implements MethodReplacer{
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        Car car = new Car();
        car.setName("红旗");
        return car;
    }
}
