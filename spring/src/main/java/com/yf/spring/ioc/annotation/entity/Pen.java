package com.yf.spring.ioc.annotation.entity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 除了 @Component,Spring还提供了下列三个基于@Component扩展的专用注解，建议专项专用，各司其职
 * @Controller 用于Controller层
 * @Repository 用于Dao层
 * @Service    用于Service层
 */
@Component("pen")
@Lazy(true)
public class Pen {
    private String name;
    private double price;
    @Resource
    private PenFactory factory;

    public PenFactory getFactory() {
        return factory;
    }

    public void setFactory(PenFactory factory) {
        this.factory = factory;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", factory=" + factory +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
