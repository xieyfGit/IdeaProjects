package com.yf.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 测试基本API
 */
public class StarterTest {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;


    /**
     * 总的来说i：merge方法，类似与hiberante的saveOrUpdate方法
     */
    //若传入的是一个临时对象(不设置ID)：会创建一个新的对象，将临时对象的属性复制到新的对象中，然后对新的对象进行insert操作，并返回
    //故新的对象中有ID，但之前的临时对象中没有ID
    @Test
    public void merge() {
        Customer customer = new Customer();
        customer.setLastName("mrXie");
        customer.setAge(25);
        customer.setEmail("mrxie.forever@gmail.com");
        customer.setDate(LocalDate.now());
        customer.setTime(LocalDateTime.now());

        Customer customer2 = entityManager.merge(customer);
        System.out.println("-----------------------------");
        System.out.println(customer==customer2);
        System.out.println("临时对象ID:" + customer.getId());
        System.out.println("新创建的对象ID：" + customer2.getId());
    }
    //若传入的是一个游离对象：（设置ID）
    //1.首先判断在EntityManager缓存中是否存在该对象，若存在，则将游离对象的属性值复制给缓存中的持久化对象，然后执行update操作
    //2.若EntityManager缓存中不存在该对象，则判断在数据库中是否存在该对象：若存在，则加载该持久化对象，并将游离对象的属性赋值给该对象
    //然后执行update操作
    //3，若数据库中也不存在该对象，则JPA会创建一个新的对象，然后把当前游离对象的属性复制到新对象中。然后对新对象执行insert操作
    @Test
    public void merge2() {
        Customer customer = new Customer();
        customer.setLastName("msChen");
        customer.setAge(25);
        customer.setEmail("mrxie.forever@gmail.com");
        customer.setDate(LocalDate.now());
        customer.setTime(LocalDateTime.now());
        customer.setId(2);
        //只要设置了ID，若缓存中没有，就会从数据库查询，若查到则对返回的持久化对象update，若查不到则对游离对象insert

        //将该对象加载到缓存中
//        Customer customer1 = entityManager.find(Customer.class,3);
        System.out.println("----------------------------------------------");
        //将游离对象的属性值复制给缓存中的对象，然后update缓存中的对象
        Customer customer2 = entityManager.merge(customer);
        System.out.println("持久化对象和返回的持久化对象"+ (customer == customer2));
        System.out.println("游离对象ID:" + customer.getId());
        System.out.println("持久化的对象ID：" + customer2.getId());
    }





    //类似与hiberante中的delete方法，但是只能删除持久化对象，而hibernate可以删除游离状态对象
    @Test
    public void delete() {
//        Customer customer = new Customer();
//        customer.setId(1);
        Customer customer = entityManager.find(Customer.class, 1);
        entityManager.remove(customer);
    }

    //类似与hibernate的load方法，故若提前关闭entityManager,可能产生延迟加载异常的问题
    @Test
    public void getReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println(customer.getClass());//class com.yf.jpa.Customer_$$_jvsta12_0是一个代理对象
        System.out.println("--------------------------------");
        System.out.println(customer);
    }


    //类似与hibernate的get方法
    @Test
    public void find() {
        Customer customer = entityManager.find(Customer.class, 15);
        System.out.println("---------------------------------");
        System.out.println(customer);
        System.out.println("-----------测试flush----------------------");
        entityManager.flush();//执行了SQL语句，但是并未提交
        entityManager.refresh(customer);//从数据库中同步该对象

    }


    //保存,类似与hibernate的save方法，但是当为对象设置ID后再持久化操作就会出错，hibernate不会。
    @Test
    public void persist() {
        Customer customer = new Customer();
        customer.setLastName("mrXie");
        customer.setAge(25);
        customer.setEmail("mrxie.forever@gmail.com");
        customer.setDate(LocalDate.now());
        customer.setTime(LocalDateTime.now());
//        customer.setId(100);

        entityManager.persist(customer);
    }

    @Before
    public void before() {
        factory =  Persistence.createEntityManagerFactory("Jpa");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        //Session session = factory.openSession();
        //Transaction transaction = session.beginTransaction();
        transaction.begin();
    }

    @After
    public void after() {
        //session.persist(customer);
        transaction.commit();
        //session.close();
        entityManager.close();
        factory.close();

    }

}
