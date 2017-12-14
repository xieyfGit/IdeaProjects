package com.yf.model;

import java.util.Set;

public class People {
    private int id;
    private String name;
    private Set<Contact> contacts;
    private Set<PeoCon> peoConSet;

    public Set<PeoCon> getPeoConSet() {
        return peoConSet;
    }

    public void setPeoConSet(Set<PeoCon> peoConSet) {
        this.peoConSet = peoConSet;
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

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts=" + contacts+'}';
    }
}
