package com.yf.spring.jpa.dao;

import com.yf.spring.jpa.entity.Person;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PersonRepository {

    //如何获取到和当前事务关联的entityManager对象
    @PersistenceContext
    private EntityManager entityManager;
    public void save(Person person) {
        entityManager.persist(person);
    }
}
