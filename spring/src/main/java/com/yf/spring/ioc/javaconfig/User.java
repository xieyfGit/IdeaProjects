package com.yf.spring.ioc.javaconfig;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("user")
public class User {
    private String name = "mrXie";
    private IDCard idCard;

    @Lookup
    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ",idCard="+idCard+
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init(){
        this.idCard = getIdCard();
        System.out.println("user init...");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("user destroy...");
    }
}
