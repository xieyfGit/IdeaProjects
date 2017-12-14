package com.yf.spring.hibernate.service;

import com.yf.spring.hibernate.BookDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 对于同一个类中的事务，如果由关联关系都不生效。
 * 原因：
 * 参照动态代理实现方式，method.invoke(..)关键就在这个地方，它仅仅会代理当前方法，而无法对其内部的方法进行处理，如果要处理，
 * 就已经到了JVM层面了，因为方法调用的时候就已经到了JVM方法区对应字节码，而内嵌的方法，表现形式时字节码内的符号引用。依我之见，
 * 除非JVM将方法区再细分到语句区，还可能实现。这也解释了为什么@Order在AOP中对同一个类中的方法进行切入排序的时候无效的问题。
 * 解决办法：另外产生一个代理类来处理需要嵌套的方法
 */
@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

    @Resource
    private BookDao bookDao;

    /**
     * 常用事务隔离级别：READ_COMMITTED;
     * 默认RuntimeException异常会回滚;
     * 可以通过rollbackFor\noRollbackFor指定;
     * timeout事务强制回滚之前，事务超时时间 单位:s
     */
    @Override

//    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED)
    public void buy(String accName, String bookNo) {
        int price = bookDao.findBookPrice(bookNo);
        bookDao.updateBookStock(bookNo);
        bookDao.updateAccountBalance(accName,price);
    }
}
