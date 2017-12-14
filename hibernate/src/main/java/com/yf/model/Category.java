package com.yf.model;

import java.util.Set;

public class Category {
    private int id;
    private String name;
    private Category parent;
    private Set<Category> sons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getSons() {
        return sons;
    }

    public void setSons(Set<Category> sons) {
        this.sons = sons;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", sons=" + sons +
                '}';
    }
}
