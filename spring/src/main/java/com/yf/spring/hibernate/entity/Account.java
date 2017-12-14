package com.yf.spring.hibernate.entity;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String accName;
    private int balance;

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accName='" + accName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

}
