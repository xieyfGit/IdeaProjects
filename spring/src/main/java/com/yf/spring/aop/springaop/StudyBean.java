package com.yf.spring.aop.springaop;

public class StudyBean implements StudyBeanInterface{

    public void lesson(){
        System.out.println("go to class...");
    }

    public void ofLesson(){
        System.out.println("leave class...");
    }

    public void question(){
        System.out.println("rise a question...");
    }

    public void doze() throws Exception {
        throw new Exception("doze off...,please teacher repeat.");
    }
}
