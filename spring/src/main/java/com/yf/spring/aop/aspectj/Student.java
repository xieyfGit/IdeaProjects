package com.yf.spring.aop.aspectj;

import org.springframework.stereotype.Component;

@Component
public class Student {
    public String print(String name){
        System.out.println("with-args<String>,hello world! invoked by aspectj...");
        return name;
    }

    public Integer print(int i) {
        System.out.println("with-args<int>, hello world! invoked by aspectj...");
        return 12/i;
    }

}
