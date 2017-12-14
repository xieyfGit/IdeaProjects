package com.yf.web.curd.converter;

import com.yf.web.curd.entity.Emp;
import com.yf.web.curd.service.DeptService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmpConverter implements Converter<String,Emp> {

    @Resource
    private DeptService deptService;
    @Override
    public Emp convert(String source) {
        if (source != null &&!source.trim().equals("")) {
            String[] vals = ((String)source).split("-");
            if (vals.length==4) {
                vals = ((String)source).split("-");
                String name =vals[0];
                String email =vals[1];
                int gender =Integer.valueOf(vals[2]);
                int deptNo =Integer.valueOf(vals[3]);
                return new Emp(name,email,gender,deptService.getOne(deptNo));
            }
        }
        return null;
    }

}
