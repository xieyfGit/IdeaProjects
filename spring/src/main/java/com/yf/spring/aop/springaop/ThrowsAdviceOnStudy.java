package com.yf.spring.aop.springaop;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

public class ThrowsAdviceOnStudy implements ThrowsAdvice{

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex){
        System.out.println("拦截异常来自"+target.getClass().getSimpleName()+"."+method.getName());
        System.out.println(ex.getMessage());
    }

}
