package com.yf.annoModel;

import com.yf.model.Address;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
@Entity
@Table(name="account_tb")
public class Account implements Serializable{
    private static final long serialVersionUID = -8646300884615737032L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="acc_name")
    private String name;
    @Basic(fetch = FetchType.EAGER,optional = true)
    private int age;
    private double score;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date birthday;

    @Transient
    private String address;

    @Lob
    private Clob content;
    @Lob
    private Blob image;

    @Version
    private  int version;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", content=" + content +
                ", image=" + image +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Clob getContent() {
        return content;
    }

    public void setContent(Clob content) {
        this.content = content;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
