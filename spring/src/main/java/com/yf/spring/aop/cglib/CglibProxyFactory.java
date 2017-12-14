package com.yf.spring.aop.cglib;

import com.yf.spring.aop.ProxyDisabled;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory<T> implements MethodInterceptor {

//    private T target;

    @SuppressWarnings("unchecked")
    public T crateProxyFactory(T obj) {
//        this.target = obj;
        return (T) Enhancer.create(obj.getClass(), this);
    }


    /**
     * API文档描述：使用proxy反射调用速度更快
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.getAnnotation(ProxyDisabled.class).value()) {
            System.out.println("该方法已经禁用代理...");
        } else {
//            method.invoke(target, args);
            //在代理对象上调用原始方法
            proxy.invokeSuper(obj, args);
            //在指定对象上调用原始方法
//            proxy.invoke(target,args);
        }
        return null;
    }
}
