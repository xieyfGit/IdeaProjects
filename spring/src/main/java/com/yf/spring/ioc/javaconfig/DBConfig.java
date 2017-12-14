package com.yf.spring.ioc.javaconfig;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DBConfig {
    @PostConstruct
    public void init(){
        System.out.println("DBConfig init...");
    }
}
