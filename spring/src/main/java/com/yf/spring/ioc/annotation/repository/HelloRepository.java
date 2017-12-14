package com.yf.spring.ioc.annotation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Repository("helloRepository")
public class HelloRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
