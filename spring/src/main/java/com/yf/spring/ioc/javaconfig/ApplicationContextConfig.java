package com.yf.spring.ioc.javaconfig;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;

@Configuration("ctxConfig")
//引入XML中的Bean定义
//@ImportResource("classpath:spring-javaconfig.xml")
@Import(DBConfig.class)
public class ApplicationContextConfig {

//    @Bean(name="user",initMethod = "init",destroyMethod = "destroy")
//    @Autowired
//    public User getUser(IDCard idCard){
//        User user = new User();
//        user.setIdCard(idCard);
//        return user;
//    }


    @Bean("idCard")
    @Scope(value = "prototype")
    @Lazy(true)
    public IDCard getIDCard(){
        return new IDCard();
    }

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
