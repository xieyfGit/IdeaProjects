package com.yf.spring.tx;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Repository
public class BookDaoImpl implements BookDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public int findBookPrice(String bookNo) {
        String sql = "select price from book where bookno = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,bookNo);
    }

    @Override
    public void updateBookStock(String bookNo) {
        int stock = jdbcTemplate.queryForObject("select stock from book_store where bookno = ?",Integer.class,bookNo);
        if (stock == 0) {
            throw new BookStockException("图书库存不够啦...");
        }
        String sql = "update book_store set stock = stock -1 where bookno = ?";
        jdbcTemplate.update(sql,bookNo);
    }

    @Override
    public void updateAccountBalance(String accName, int price) {
        int balance = jdbcTemplate.queryForObject("select balance from book_account where accName = ?",Integer.class,accName);
        if (balance < price) {
            throw new UserAccountException("用户余额不足...");
        }
        String sql = "update book_account set balance = balance-? where accname = ?";
        jdbcTemplate.update(sql,price,accName);
    }
}
