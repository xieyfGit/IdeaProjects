package com.yf.jpa;

import javax.persistence.*;

@Entity
@Table(name="JPA_MANAGER")
public class Manager {
    @Id
    @GeneratedValue
    @Column(name="MGR_ID")
    private Integer id;
    private String name;
    //没有外键的一方，使用@OneToOne，且设置mappedBy来指定维护方
    @OneToOne(mappedBy = "manager")
    private Department department;

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
