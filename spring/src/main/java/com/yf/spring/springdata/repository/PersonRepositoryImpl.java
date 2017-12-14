package com.yf.spring.springdata.repository;

import com.yf.spring.springdata.dao.PersonDao;
import com.yf.spring.springdata.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//若类不在<jpa:repositories/>标签指定的扫描范围，则添加@Repository注解，其实加入到IOC容器中
//@Repository
public class PersonRepositoryImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void test() {
        System.out.println(entityManager.find(Person.class,1));
    }
}
