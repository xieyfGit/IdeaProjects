package com.yf.spring.springdata.service;

import com.yf.spring.springdata.entity.Person;
import com.yf.spring.springdata.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonService {
    @Resource
    private PersonRepository repository;

    @Transactional
    public void update(String name,Integer id) {
        repository.update(name,id);
    }

    @Transactional
    public void save(List<Person> list) {
        repository.save(list);
    }
}
