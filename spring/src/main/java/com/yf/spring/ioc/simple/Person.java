package com.yf.spring.ioc.simple;

public class Person {
    private ThreadLocal<String> names = new ThreadLocal<>();

    public String getName() {
        return names.get();
    }

    public void setName(String name) {
        names.set(name);
    }

    private HelloMessage helloMessage;

    public Person(HelloMessage helloMessage) {
        System.out.println("Person constructor with args...");
        this.helloMessage = helloMessage;
    }

    public Person() {
        System.out.println("Person constructor non args...");
    }

    public HelloMessage getHelloMessage() {
        return helloMessage;
    }

    public void setHelloMessage(HelloMessage helloMessage) {
        System.out.println("Person setHelloMessage...");
        this.helloMessage = helloMessage;
    }

    public void sayHello(String s){
        helloMessage.sayHello(s);
    }
}
