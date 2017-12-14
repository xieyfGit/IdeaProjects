package com.yf.spring.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class BookDaoImpl implements BookDao {

    @Resource
    private SessionFactory sessionFactory;

    private Query getQuery(String hql){
        return sessionFactory.getCurrentSession().createQuery(hql);
    }

    @Override
    public int findBookPrice(String bookNo) {
        String hql ="select price from Book where bookNo = :bookNo";
        Query query = getQuery(hql);
        query.setParameter("bookNo",bookNo);
        int price = (int) query.uniqueResult();
        return price;
    }

    @Override
    public void updateBookStock(String bookNo) {

        String hql0 = "select stock from BookStore where bookNo = :bookNo";
        Query query0 = getQuery(hql0).setParameter("bookNo",bookNo);

        if ((int)query0.uniqueResult()==0) {
            throw new BookStockException("图书库存不足...");
        }

        String hql = "update BookStore set stock = stock -1 where bookNo = :bookNo";
        Query query = getQuery(hql).setParameter("bookNo", bookNo);
        query.executeUpdate();
    }

    @Override
    public void updateAccountBalance(String accName, int price) {
        String hql0 = "select balance from Account where accName = :accName";
        Query query0 = getQuery(hql0).setParameter("accName",accName);

        if ((int)query0.uniqueResult() < price) {
            throw new UserAccountException("账户余额不足...");
        }
        String hql = "update Account set balance = balance - :price where accName = :accName";
        Query query = getQuery(hql).setParameter("accName",accName).setParameter("price",price);
        query.executeUpdate();
    }

}
