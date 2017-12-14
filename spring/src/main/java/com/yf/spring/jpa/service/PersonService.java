package com.yf.spring.jpa.service;

import com.yf.spring.jpa.dao.PersonRepository;
import com.yf.spring.jpa.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PersonService {

    @Resource
    private PersonRepository personRepository;

    @Transactional
    public void save(Person person, Person person2) {
        personRepository.save(person);
        personRepository.save(person2);
        int i = 10/0;
    }
}
