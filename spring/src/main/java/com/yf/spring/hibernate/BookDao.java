package com.yf.spring.hibernate;

public interface BookDao {

    public int findBookPrice(String bookNo);


    public void updateBookStock(String bookNo);

    public void updateAccountBalance(String accName, int price);
}
