package com.yf.spring.tx.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookShopService {

    public void buy(String accName,String bookNo);

}
