package com.yf.model;

import java.util.Set;

public class Contact {

    private int id;
    private String name;
    private Set<People> peoples;
    private Set<PeoCon> peoConSet;

    public Set<PeoCon> getPeoConSet() {
        return peoConSet;
    }

    public void setPeoConSet(Set<PeoCon> peoConSet) {
        this.peoConSet = peoConSet;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", peoples=" + peoples +
                '}';
    }

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

    public Set<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(Set<People> peoples) {
        this.peoples = peoples;
    }
}
