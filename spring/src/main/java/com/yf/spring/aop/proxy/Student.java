package com.yf.spring.aop.proxy;

public class Student implements StudentInterface {
    @Override
    public void print() {
        System.out.println("hello world->print! invoked by jdkProxy...");
        show();
    }

    @Override
    public void show() {
        System.out.println("hello world->show! invoked by jdkProxy...");
    }
}
