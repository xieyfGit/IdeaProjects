package com.yf.spring.aop.springaop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AroundAdviceOnStudy implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        for (Object o : invocation.getArguments()) {
            System.out.println(o);
        }
        System.out.println("AroundAdviceOnStudy before "+invocation.getClass().getSimpleName()+"."+invocation.getMethod().getName());
        invocation.proceed();
        System.out.println("AroundAdviceOnStudy after "+invocation.getClass().getSimpleName()+"."+invocation.getMethod().getName());
        return null;
    }
}
