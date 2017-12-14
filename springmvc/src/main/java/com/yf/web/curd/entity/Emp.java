package com.yf.web.curd.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * JSR-303:
 * @Null @NotNull @AssertTrue @AssertFalse @Min(value) @Max(value) @DecimalMin(value) @DecimalMax(value) @Size(max,min)
 * @Digits(integer,fraction) @Past @Future @Pattern(value)
 * Spring4.0拥有自己的校验框架LocalValidatorFactoryBean实现了Spring的Validator接口和JSR-303的validator接口:
 *   通过在处理方法的入参上标注@Valid注解，可让SpringMVC在完成数据绑定后执行数据校验工作，错误结果存储于BindingResult或Errors对象中
 * 但是Spring本身并没有对JSR303进行实现，所以依赖于JSR-303其它的实现，如hibernate-validator
 * Hibernate-Validator对JSR303做了进一步扩展：
 * @Email @Length @NotEmpty @Range
 */
public class Emp {
    private Integer id;
    //@NotNull无效？
    @NotEmpty
    private String name;
    @Email
    private String email;
    private Integer gender;
    private Dept dept;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    @NumberFormat(pattern = "#,###,###.#")
    private float salary;

    public Emp() {
    }

    public Emp(Integer id, String name, String email, Integer gender, Dept dept) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dept = dept;
    }

    public Emp(String name, String email, Integer gender, Dept dept) {

        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dept = dept;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

}