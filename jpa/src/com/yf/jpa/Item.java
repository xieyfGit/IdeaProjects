package com.yf.jpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="JPA_ITEM")
public class Item {
    @Id
    @GeneratedValue
    @Column(name="ITEM_ID")
    private Integer id;
    @Column(name="ITEM_NAME")
    private String name;
    //使用@ManyToMany来进行多对多映射
    //1.joinColumns指定中间表与本表的关系，name指定中间表外键列的列名，referencedColumnName指定中间表外键对应的本表列名
    //2.inverseJoinColumns指定中间表与关联对象表的关系，name指定中间表外键列的列名，referencedColumnName指定中间表外键对应的关联对象表列名
    @JoinTable(name="ITEM_CATEGORY",joinColumns = {@JoinColumn(name="ITEM_ID",referencedColumnName = "ITEM_ID")},
    inverseJoinColumns = {@JoinColumn(name="CATEGORY_ID",referencedColumnName = "CATEGORY_ID")})
    @ManyToMany
    private Set<Category> categorySet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
