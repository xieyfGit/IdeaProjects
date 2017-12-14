package com.yf.spring.ioc.annotation.entity.aspectj;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AspectJNoScanExtend extends AspectJNoScan {
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
