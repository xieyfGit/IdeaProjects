package com.yf.spring.tx.service;

import java.util.List;

public interface BookShopWrapperService {
    void buyMany(String accName, List<String> list);

}
