package com.yf.spring.aop.cglib;

import com.yf.spring.aop.ProxyDisabled;

public class Student {
    @ProxyDisabled(false)
    public void print(){
        System.out.println("hello world! invoked by cglib...");
    }
}
