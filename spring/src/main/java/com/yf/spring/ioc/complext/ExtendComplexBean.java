package com.yf.spring.ioc.complext;

public class ExtendComplexBean extends ComplexBean {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExtendComplexBean{" +
                super.toString()+
                "description='" + description + '\'' +
                '}';
    }
}
