package com.yf.spring.hibernate.entity;

import javax.persistence.*;

@Entity
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String bookNo;
    private int stock;

    @Override
    public String toString() {
        return "BookStore{" +
                "bookNo='" + bookNo + '\'' +
                ", stock=" + stock +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
