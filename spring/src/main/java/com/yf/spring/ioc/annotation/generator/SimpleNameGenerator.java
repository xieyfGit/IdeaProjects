package com.yf.spring.ioc.annotation.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * 简易Bean的名字生成器，非限定类名首字母小写
 */
public class SimpleNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

        String beanClassName = definition.getBeanClassName();
        String beanName = beanClassName.substring(beanClassName.lastIndexOf(".")+1);
        char firstLetter = beanName.charAt(0);
        return beanName.replace(firstLetter,(char)(firstLetter+32));
    }
}
