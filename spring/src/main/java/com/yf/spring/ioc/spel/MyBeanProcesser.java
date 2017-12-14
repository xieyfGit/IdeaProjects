package com.yf.spring.ioc.spel;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanProcesser implements BeanPostProcessor,BeanFactoryPostProcessor {
    //init-method方法调用之前执行
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "bean postProcessBeforeInitialization...");
        return bean;
    }

    // init-method方法调用之后执行
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "bean postProcessAfterInitialization...");
        if (bean instanceof Wheel) {
            ((Wheel) bean).setBrand("BMW");
        }
        return bean;
    }

    //可以修改bean的配置信息
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition bd= beanFactory.getBeanDefinition("wheel");
        MutablePropertyValues pv =bd.getPropertyValues();
        if (pv.contains("price")) {
            pv.add("price",10000);
        }
    }
}
