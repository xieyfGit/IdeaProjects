package com.yf.model;

public class PeoCon {
    private int id;
    private People people;
    private Contact contact;

    @Override
    public String toString() {
        return "PeoCon{" +
                "id=" + id +
                ", people=" + people +
                ", contact=" + contact +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
