package com.yf.spring.aop.springaop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterAdviceOnStudy implements AfterReturningAdvice{
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        String methodName = method.getName();
        if(methodName.equals("question")){
            System.out.println("thanks teacher...,i get it!");
        }else if(methodName.equals("ofLesson")){
            System.out.println("good bye teacher,see you next class...");
        }
    }
}
