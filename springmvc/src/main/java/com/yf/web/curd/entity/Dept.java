package com.yf.web.curd.entity;

public class Dept {

    private Integer deptNo;
    private String deptName;

    @Override
    public String toString() {
        return "Dept{" +
                "deptNo=" + deptNo +
                ", deptName='" + deptName + '\'' +
                '}';
    }

    public Dept() {
    }

    public Dept(Integer deptNo, String deptName) {

        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public Integer getDeptNo() {

        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
