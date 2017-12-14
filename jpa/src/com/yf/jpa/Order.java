package com.yf.jpa;

import javax.persistence.*;

@Table(name= "JPA_ORADER")
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="ORDER_NAME")
    private String orderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")//映射外键
    private Customer customer;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName +
                '}';
    }

//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + id +
//                ", orderName='" + orderName + '\'' +
//                '}';
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
