package com.yf.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;
import java.util.List;

public class XmlStudentAspect {

    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("beforeMethod===The method " + methodName + " begins with args " + args);
    }

    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("afterMethod===The method " + methodName + " end...");
    }

    public void afterMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("afterMethodReturn===The method " + methodName + " end...return " + result);
    }

    public void onThrows(JoinPoint joinPoint, ArithmeticException ex) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("onThrows===The method " + methodName + " end...with Exception " + ex.getMessage());
    }

    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        Object result = null;
        try {
            //前置通知
            System.out.println("aroundMethod===The method " + methodName + " begin...with args " + args);
            result = joinPoint.proceed();
            //后置通知
            System.out.println("aroundMethod===The method " + methodName + " end...");
        } catch (Throwable ex) {
            //异常通知
            System.out.println("aroundMethod===The method " + methodName + " end...with Exception " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        //返回通知
        System.out.println("aroundMethod===The method " + methodName + " return " + result);
        return result;
    }
}
