package com.yf.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Entity
@Table(name = "userC")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Set<String> set;
    private List<String> list;
    private Map<String,String> map;
    private Collection<String> collection;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "UserCollection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", set=" + set +
                ", list=" + list +
                ", map=" + map +
                ", collection=" + collection +
                '}';
    }
}
