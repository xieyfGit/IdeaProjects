package com.yf.spring.ioc.annotation.entity.assigenable;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NoScanExtend extends NoScanImpl {
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
