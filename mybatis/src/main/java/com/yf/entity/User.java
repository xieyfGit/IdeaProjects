package com.yf.entity;

import java.util.Date;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private Gender gender;
    private String linkAccount = "mrxie.forever@gmail.com";
    private Date date;

    private Set<Address> addr;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", linkAccount='" + linkAccount + '\'' +
                ", date=" + date +
                ", addr=" + addr +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Address> getAddr() {
        return addr;
    }

    public void setAddr(Set<Address> addr) {
        this.addr = addr;
    }

    public String getLinkAccount() {
        return linkAccount;
    }

    public void setLinkAccount(String linkAccount) {
        this.linkAccount = linkAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
}
