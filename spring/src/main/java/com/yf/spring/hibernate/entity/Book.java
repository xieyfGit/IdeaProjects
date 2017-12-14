package com.yf.spring.hibernate.entity;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String bookNo;
    private String bookName;
    private int price;

    @Override
    public String toString() {
        return "Book{" +
                "bookNo='" + bookNo + '\'' +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
