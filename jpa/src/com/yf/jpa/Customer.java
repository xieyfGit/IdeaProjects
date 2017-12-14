package com.yf.jpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;



@NamedQuery(name="findOneById",query = "from Customer where id = ?")
@SqlResultSetMapping(name="findAll",entities = {@EntityResult(entityClass = Customer.class)})
@Cacheable
@Table(name = "JPA_CUSTOMER")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
//    @TableGenerator(name = "ID_GENERATOR",
//            table = "ID_GENERATOR",
//            pkColumnName = "PK_NAME",
//            pkColumnValue = "CUSTOMER_ID",
//            valueColumnName = "PK_VALUE",
//            allocationSize = 20)
    private Integer id;
    @Column(name = "LAST_NAME")
    private String lastName;
    private String email;
    private int age;
    private LocalDate date;
    private LocalDateTime time;

    public Customer(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }

    public Customer() {
    }

    //设置@OneToMany(mappedBy = "customer")，将外键维护权交给多端，且先保存一端，此时不会再产生update语句
    // 此时一端不能在使用@JoinColum注解，因为没有维护权了.
//    @JoinColumn(name="CUSTOMER_ID")
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,mappedBy = "customer")
    private Set<Order> orderSet = new HashSet<>();

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    @Temporal(value = TemporalType.DATE)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", time=" + time  +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
