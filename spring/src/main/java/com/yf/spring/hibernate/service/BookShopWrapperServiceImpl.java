package com.yf.spring.hibernate.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookShopWrapperServiceImpl implements BookShopWrapperService {

    @Resource
    private BookShopService bookShopService;
//        @Transactional
        @Override
        public void buyMany(String accName, List<String> list) {
            for (String bookNo : list) {
                bookShopService.buy(accName,bookNo);
            }
        }
}
