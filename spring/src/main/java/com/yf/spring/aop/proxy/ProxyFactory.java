package com.yf.spring.aop.proxy;

import com.yf.spring.aop.ProxyDisabled;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> implements InvocationHandler {

    private T target;

    @SuppressWarnings("unchecked")
    public T createProxy(T obj) {
        this.target = obj;
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getAnnotation(ProxyDisabled.class)!=null && method.getAnnotation(ProxyDisabled.class).value()) {
            System.out.println("该方法注解了不需要代理");
        } else {
            //前置通知位置
            System.out.println("before method "+ method.getName());
            method.invoke(target, args);
            //后置通知位置
            System.out.println("after method "+ method.getName());
        }
        //返回通知位置
        return null;
    }

}
