package com.yf.jpa;

import javax.persistence.*;

@Entity
@Table(name="JPA_DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue
    @Column(name="DEP_ID")
    private Integer id;
    private String name;
    //有外键的一方，使用@JoinColumn来映射外键，且指定unique来保证外键唯一性
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MGR_ID",unique = true)
    private Manager manager;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }

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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
