package jdk8.design.adapter;

/**
 * 源角色 需要适配的接口
 */
public class Adaptee {
    public void reduce() {
        System.out.println("reduce something...in Adaptee");
    }
}
