package com.yf.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "hql", query = "from Student where name like ?")
        , @NamedQuery(name = "hql1", query = "from Student where name like :name")})
@NamedNativeQueries({
        @NamedNativeQuery(name = "nativeSql", query = "select * from student where id = ?", resultClass = Student.class),
        @NamedNativeQuery(name = "nativeSql1", query = "select * from student where id = :id", resultClass = Student.class),
        @NamedNativeQuery(name = "nativePage", query = "select id,name,age from student limit :offset,:endset",/*resultClass = Student.class
        ,*/resultSetMapping = "columns")
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "columns", columns = {
                @ColumnResult(name = "id", type = String.class)
                , @ColumnResult(name = "name", type = String.class)
        }),
        @SqlResultSetMapping(name = "entitys", entities = {@EntityResult(entityClass = Student.class, fields = {
                @FieldResult(name = "id", column = "id"), @FieldResult(name = "name", column = "name"),@FieldResult(name = "age", column = "age")
        })})
})
@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = -4282846112653742495L;
    @Id
    @GeneratedValue(generator = "myGen")
    @GenericGenerator(name = "myGen", strategy = "com.yf.utils.YFUUIDGenerator")
    private String id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //使用hbm.xml映射文件时需要提供所有熟悉的get,set方法
    private String getId() {
        return id;
    }

    private void setId(String id) {
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

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
