package com.yf.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//@Order(2)
//@Aspect
//@Component
public class LoggingAspect {

    //前置通知：在方法执行之前执行，此处也可以指定接口
//    @Before("com.yf.spring.aop.aspectj.StudentAspect.pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("LoggingAspect-->beforeMethod===The method " +methodName+" begins with args "+args);
    }
}
