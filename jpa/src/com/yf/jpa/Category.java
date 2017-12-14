package com.yf.jpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name="JPA_CATEGORY")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Integer id;
    @Column(name = "CATEGORY_NAME")
    private String name;
    @ManyToMany(mappedBy = "categorySet")
    private Set<Item> itemSet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<Item> itemSet) {
        this.itemSet = itemSet;
    }
}
