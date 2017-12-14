package com.yf.entity;

import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


public interface UserMapper {
//    @Select("select user.id uid,user.* from user where id = #{id}")
//    @ResultMap("userrm")
    @Select("select id,name,date from user where id = #{id}")
    User selectUser(int id);

    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(name,gener,date,link_account) values(#{name},#{gender},#{date},#{linkAccount})")
    @Options(useGeneratedKeys = true)
    void addOneMapper(User user);

}
