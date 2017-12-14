package com.yf.web.curd.service;

import com.yf.web.curd.entity.Dept;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeptService {

    private static final Map<Integer,Dept> map = new HashMap<>();

    static{
        map.put(1001, new Dept(1001, "技术部"));
        map.put(1002, new Dept(1002, "人事部"));
        map.put(1003, new Dept(1003, "财务部"));
    }

    public Collection<Dept> getAll() {
        return map.values();
    }

    public Dept getOne(Integer deptNo) {
        return map.get(deptNo);
    }
}
