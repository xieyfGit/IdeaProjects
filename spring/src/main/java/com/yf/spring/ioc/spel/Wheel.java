package com.yf.spring.ioc.spel;

public class Wheel {
    private int radius;
    private String brand;
    private double price;

    @Override
    public String toString() {
        return "Wheel{" +
                "radius=" + radius +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
