package com.yf.spring.ioc.method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

//public class Boss implements BeanFactoryAware{
//
//    private String name;
//    private BeanFactory factory;
//
//    @Override
//    public String toString() {
//        return "Boss{" +
//                "name='" + name + '\'' +
//                ", car=" + getCar() +
//                '}';
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Car getCar() {
//        return (Car) factory.getBean("car");
//    }
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        this.factory=beanFactory;
//    }
//}
public class Boss{

    private String name;
    private Car car;
    private String carName;

    @Override
    public String toString() {
        return "Boss{" +
                "name='" + name + '\'' +
                ", car=" + car +
                ", carName='" + carName + '\'' +
                '}';
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
