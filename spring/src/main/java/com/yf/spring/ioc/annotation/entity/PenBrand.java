package com.yf.spring.ioc.annotation.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PenBrand {
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
