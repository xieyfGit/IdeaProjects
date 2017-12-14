package com.yf.spring.ioc.annotation.repository;

import com.yf.spring.ioc.annotation.entity.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //执行update、delete、insert
    public void update() {
        String sql = "update student set name = ? where id = ?";
        jdbcTemplate.update(sql, "mrxie", "genius_19_fool");
    }

    //批量update、delete、insert
    public void batchUpdate() {
        String sql = "insert into student values (?,?,?)";
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"aa", "bb", 22});
        list.add(new Object[]{"aa1", "bb", 22});
        list.add(new Object[]{"aa2", "bb", 22});
        list.add(new Object[]{"aa3", "bb", 22});
        list.add(new Object[]{"aa4", "bb", 22});
        list.add(new Object[]{"aa5", "bb", 22});
        jdbcTemplate.batchUpdate(sql, list);
    }

    //不支持级联对象查询，归根结底JdbcTemplate只是jdbc查询
    public void select() {
        //当返回列名与类字段不一致时，利用列别名来映射类中的字段
        String sql = "select id stuId,name,age from student where id = ?";
        RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        Student student = jdbcTemplate.queryForObject(sql, rowMapper, "genius_19_fool");
        System.out.println(student);

    }    //不支持级联对象查询，归根结底JdbcTemplate只是jdbc查询

    //使用paramMap方式时，较麻烦
    public void selectNamedParam() {
        //当返回列名与类字段不一致时，利用列别名来映射类中的字段
        String sql = "select id stuId,name,age from student where id = :id";
        RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id","genius_19_fool");
        Student student = namedParameterJdbcTemplate.queryForObject(sql,paramMap,rowMapper);
        System.out.println(student);

    }

    //使用BeanPropertySqlParameterSource时，要求命名参数的名字需要和类字段的名字一致
    public void selectNamedParam2() {
        //当返回列名与类字段不一致时，利用列别名来映射类中的字段
        String sql = "select id stuId,name,age from student where id = :stuId";
        Student student = new Student();
        student.setStuId("genius_19_fool");
        RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(student);
        Student result = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, rowMapper);
        System.out.println(result);

    }

    //这个地方优点奇怪，居然不是调用queryForList方法
    public void list() {
        String sql = "select id stuId,name,age from student";
        RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        List<Student> list = jdbcTemplate.query(sql, rowMapper);
        System.out.println(list);

    }

    //获取单个列的值或统计查询
    public void selectOneColumn() {
        String sql = "select count(*) from student";
        long count = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(count);

    }

}
