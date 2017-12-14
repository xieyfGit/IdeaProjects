package com.yf.spring.tx;

public interface BookDao {

    public int findBookPrice(String bookNo);


    public void updateBookStock(String bookNo);

    public void updateAccountBalance(String accName,int price);
}
