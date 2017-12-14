package com.yf.spring.springdata.repository;


import com.yf.spring.springdata.dao.PersonDao;
import com.yf.spring.springdata.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 两种实现方式：
 * 1.继承Repository<Domain,ID>接口或子接口，可以通过JPQL自定义方法
 * 2.使用@RepositoryDefinition注解
 * <p>
 * 方法命名方式：
 * 1.查询方法：find/read/get开头
 * 2.查询条件要以关键字连接，涉及属性首字母大写
 * 3.支持级联属性，建议级联属性用_连接避免歧义
 */
//@RepositoryDefinition(domainClass = Person.class,idClass = Integer.class)
//public interface PersonRepository{
//    Person getByLastName(String name);
//}
    //基础的Jpa查询接口
//public interface PersonRepository extends Repository<Person, Integer> {
    //支持增删改查
//public interface PersonRepository extends CrudRepository<Person, Integer> {
    //支持无条件分页
//public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
    //JpaRepository支持merge，JpaSpecificationExecutor支持待条件分页查询
//public interface PersonRepository extends JpaRepository<Person, Integer> ,JpaSpecificationExecutor<Person> {
    //personDao为自定义接口，规定其实现类名为PersonRepositoryImpl
public interface PersonRepository extends PersonDao,
        JpaRepository<Person, Integer> ,JpaSpecificationExecutor<Person> {
    Person getByLastName(String name);

    //默认先查询自身属性，若没有才级联属性查询
    Person getByAddressIdGreaterThan(Integer addrId);

    //强制级联属性查询，加下划线
    Person getByAddress_IdGreaterThan(Integer addrId);
    //子查询
    @Query(value = "select p from Person p where p.id = (select max(p2.id) from Person p2)")
    Person getMaxIdPerson();

    //1.占位符
    @Query(value = "select p from Person p where p.id = ?1 ")
    List<Person> getAll(Integer id);   //1.占位符
    //2.命名参数方式
    @Query(value = "select p from Person p where p.id = :id ")
    List<Person> getAll2(@Param("id") Integer id);

    //like命名参数查询
    @Query("select p from Person p where p.lastName like %:name%")
    Person getByLastNameLike(@Param("name") String lastName);

    //原生SQL
    @Query(value = "select count(*) from jpa_person",nativeQuery = true)
    long getTotalCount();

    //1.通过自定义JPQL可以完成UPDATE和DELETE操作；注意：JPQL不支持INSERT操作
    //2.DML操作需要加上@Modifying注解，以通知SpringData需要事务管理
    //3.对于查询的情况，可以不加@Modifying，因为SpringData为每个方法默认添加了只读事务
    @Modifying
    @Query("update Person  set lastName = :lastName where id = :id")
    void update(@Param("lastName") String lastName,@Param("id")Integer id);

}