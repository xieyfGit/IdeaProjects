package com.yf.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "sq_gen")
    @TableGenerator(name = "sq_gen" ,table ="sq_gen", initialValue = 1000,allocationSize = 10)
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
