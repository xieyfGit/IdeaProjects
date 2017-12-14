package com.yf.web.struts2.service;

import com.yf.web.struts2.entity.Person;

public class PersonServiceImpl implements PersonService {
    @Override
    public Person getPerson() {
        Person person = new Person();
        person.setName("mrXie");
        return person;
    }
}
