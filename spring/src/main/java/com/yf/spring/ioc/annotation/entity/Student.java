package com.yf.spring.ioc.annotation.entity;

public class Student {
    private String stuId;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
