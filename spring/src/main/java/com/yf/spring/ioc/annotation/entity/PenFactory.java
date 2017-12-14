package com.yf.spring.ioc.annotation.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

@Component("factory")
public class PenFactory {

    private String name;
    //自动注入所有PenBrand类型Bean到集合中，或者将penBrands同名Bean注入，或者将元素类型相同Set类型Bean注入
    @Resource
    private Set<PenBrand> penBrands;

    public Set<PenBrand> getPenBrands() {
        return penBrands;
    }

    public void setPenBrands(Set<PenBrand> penBrands) {
        this.penBrands = penBrands;
    }

    @Override
    public String toString() {
        return "PenFactory{" +
                "name='" + name + '\'' +
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
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
