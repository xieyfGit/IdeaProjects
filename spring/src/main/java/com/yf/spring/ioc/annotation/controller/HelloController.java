package com.yf.spring.ioc.annotation.controller;

import com.yf.spring.ioc.annotation.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller("helloController")
@Scope("singleton")
public class HelloController {

    //默认为true，若找不到抛出异常
    @Autowired(required = false)
    @Qualifier("helloService")
    private HelloService helloService;

    @Autowired(required = false)
    public void setHelloService(@Qualifier("helloService") HelloService helloService) {
        this.helloService = helloService;
    }
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
