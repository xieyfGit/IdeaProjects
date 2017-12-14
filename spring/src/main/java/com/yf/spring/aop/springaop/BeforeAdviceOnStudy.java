package com.yf.spring.aop.springaop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeAdviceOnStudy implements MethodBeforeAdvice{
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String methodName = method.getName();
        if(methodName.equals("question")){
            System.out.println("excuse me ...teacher,just now ,i have a question...");
        }else if(methodName.equals("lesson")){
            System.out.println("good morning teacher,have a nice class...");
        }
    }
}
