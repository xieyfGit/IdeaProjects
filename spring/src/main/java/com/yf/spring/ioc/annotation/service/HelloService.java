package com.yf.spring.ioc.annotation.service;

import com.yf.spring.ioc.annotation.repository.HelloRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Spring提供了两种方式来自行管理Bean的生命周期回调
 * 1.（不推荐，耦合度过高）实现InitializingBean的afterPropertiesSet方法以便于初始化Bean完成后执行自定义操作
 * 实现DisposableBean的destroy方法，以便于在容器销毁该Bean时调用
 * 2.在XML文件的bean定义中定义init-method和destroy-method方法
 * 3.利用@PostConstruct和@PreDestroy来实现回调，且在XML中激活后处理器<context:annotation-config/>
 */
@Service("helloService")
public class HelloService {

    //等价于@AutoWired+@Qualifier
    @Resource
    private HelloRepository helloRepository;

    public HelloRepository getHelloRepository() {
        return helloRepository;
    }

    //判断setter方法是否被调用，等价于XML中bean的dependency-check属性(none|simple(原始基本类型、集合类型)|objects(复杂对象类型)|all)
    //若 使用此注解，需要激活后处理器
//    @Required
    public void setHelloRepository(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+" init...");
    }
}
