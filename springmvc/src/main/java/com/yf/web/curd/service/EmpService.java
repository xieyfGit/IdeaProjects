package com.yf.web.curd.service;

import com.yf.web.curd.entity.Dept;
import com.yf.web.curd.entity.Emp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmpService {
    private static final Map<Integer, Emp> map = new HashMap<>();
    private static int initId = 108;
    @Resource
    private DeptService deptService;

    public EmpService() {
        System.out.println("com.yf.web.curd.service.EmpService constructor...");
    }

    static {
        map.put(100,new Emp(100,"emp1","emp1.@emp.com", 1,new Dept(1001,"技术部")));
        map.put(101,new Emp(101,"emp2","emp2.@emp.com", 1,new Dept(1001,"技术部")));
        map.put(102,new Emp(102,"emp3","emp3.@emp.com", 0,new Dept(1001,"技术部")));
        map.put(103,new Emp(103,"emp4","emp4.@emp.com", 0,new Dept(1001,"技术部")));
        map.put(104,new Emp(104,"emp5","emp5.@emp.com", 1,new Dept(1002,"人事部")));
        map.put(105,new Emp(105,"emp6","emp6.@emp.com", 0,new Dept(1002,"人事部")));
        map.put(106,new Emp(106,"emp7","emp7.@emp.com", 1,new Dept(1002,"人事部")));
        map.put(107,new Emp(107,"emp8","emp8.@emp.com", 0,new Dept(1003,"财务部")));
        map.put(108,new Emp(108,"emp9","emp9.@emp.com", 1,new Dept(1003,"财务部")));
    }

    public Collection<Emp> getAll() {
        return map.values();
    }

    public Emp getOne(Integer id) {
        return map.get(id);
    }

    public Emp delOne(Integer id) {
        return map.remove(id);
    }

    public void addOne(Emp emp) {
        if (emp.getId()==null) {
            emp.setId(++initId);
        }
        emp.setDept(deptService.getOne(emp.getDept().getDeptNo()));
        map.put(emp.getId(), emp);
    }
}
