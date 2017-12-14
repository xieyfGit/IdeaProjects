package com.yf.spring.ioc.annotation.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Inherited;

@Component
public class PaiKe extends PenBrand {
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
