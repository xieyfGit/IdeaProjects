package com.yf.web.struts2.action;

import com.yf.web.struts2.service.PersonService;

public class HelloAction{

    private PersonService personService;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public String execute(){
        getPerson();
        return "success";
    }


    private void getPerson(){
        System.out.println(personService.getPerson());
    }
}
