package com.yf.jpa;

import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 测试关联映射
 */
public class MappingTest {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void before() {
        factory = /*(SessionFactory)*/ Persistence.createEntityManagerFactory("Jpa");
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

    //======================================delete/update======================================================

    @Test
    public void jpqlDelAndUpdate() {
        String jpql = "update Customer set email = :email where id = :id";
        entityManager.createQuery(jpql).setParameter("id",15)
                .setParameter("email","aaa@aaa.com")
                .executeUpdate();

        jpql = "delete from Customer where id = :id";
        entityManager.createQuery(jpql).setParameter("id",16)
                .executeUpdate();
    }

    //==========================================JPQL函数========================================================
    @Test
    public void jpqlFunc() {
        String jpql = "select upper(lastName)  from Customer where id = :id";
        String lastName = (String) entityManager.createQuery(jpql).setParameter("id",15)
                .getSingleResult();
        System.out.println(lastName);
    }

    //==========================================子查询========================================================
    @Test
    public void subQuery() {
        String jpql = "from Order where customer = (from Customer where lastName = :lastName)";
//        String jpql = "from Order o  inner join fetch o.customer where o.customer.lastName = :lastName";
        List<Order> orders = entityManager.createQuery(jpql).setParameter("lastName","mrChen")
                .getResultList();
        System.out.println(orders);

    }
    //==========================================关联查询=======================================================
    //若left outer join fetch不使用fetch，则返回的List<Object[]>
    @Test
    public void leftJoinQuery() {
//        String jpql =  "from Customer c left outer join fetch c.orderSet o where c.id  = :id";
//        Customer customer = (Customer) entityManager.createQuery(jpql)
//                .setParameter("id",28).getSingleResult();
//
//        System.out.println(customer);
//        System.out.println(customer.getOrderSet().size());
        String jpql =  "from Customer c left outer join c.orderSet o where c.id  = :id";
        List<Object[]> list =  entityManager.createQuery(jpql)
                .setParameter("id",28).getResultList();

        for (Object[] objects : list) {
            System.out.println(objects[0]);
            System.out.println(objects[1]);
        }
    }



    //=======================================orderBy groupBy===========================================================
    @Test
    public void orderByAndGroupBy() {
        String jpql = "select count(c),age from Customer c where id >:id group by age having age>:age order by age asc";
        Query query = entityManager.createQuery(jpql).setParameter("id",10)
                .setParameter("age",10);
        List<Object[]> list = query.getResultList();
        for (Object[] o : list) {
            System.out.println("[count:"+o[0]+",age:"+o[1]+"]");
        }
    }
    //===============================================JPQL==============================================================
    //本地SQL(包括命名查询)：参数占位符都是从1开始
    //JPQL：参数占位符从0开始

    //查询缓存：
    //1.开启查询缓存：配置JPA产品的hibernate.cache.use_query_cache属性
    //2.ehcache.xml中配置查询缓存策略:不知道怎么用
    @Test
    public void jpqlQuryCache() {
        String jpql = "from Customer where id = :id";
        Customer customer = (Customer) entityManager.createQuery(jpql).setParameter("id",15)
                .setHint(QueryHints.HINT_CACHEABLE,true)
                .getSingleResult();
        System.out.println(customer);

        Customer customer2 = (Customer) entityManager.createQuery(jpql).setParameter("id",15)
                .setHint(QueryHints.HINT_CACHEABLE,true)
                .getSingleResult();
        System.out.println(customer2);


    }


    //命名查询:在实体类上定义@NamedQuery
    @Test
    public void namedQuery() {
        //命名查询API有问题，索引需要从1开始
        Query query = entityManager.createNamedQuery("findOneById").setParameter(1,28);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer);
        System.out.println(customer.getOrderSet());
    }

    //本地查询：使用原生SQL，索引从1开始
    @Test
    public void jqplNativeQuery() {
//        String sql = "select * from jpa_customer where id = ?";
//        Query query = entityManager.createNativeQuery(sql,Customer.class).setParameter(1,15);
//        Customer customer = (Customer) query.getSingleResult();
//        System.out.println(customer);

        String sql = "select * from jpa_customer where id > ?";

        Query query = entityManager.createNativeQuery(sql,"findAll").setParameter(1,40);
        List<Customer> list = query.getResultList();
        System.out.println(list);
    }


    //投影映射 索引从0开始
    @Test
    public void jpqlFindPartly() {
        String jpql = "select new Customer(lastName,age) from Customer where id = ?";
        List<Customer> list = entityManager.createQuery(jpql).setParameter(0,15).getResultList();
        System.out.println(list);
    }

    //
    @Test
    public void jpqlFindAll() {
        String jpql = "from Customer where age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(0,20);
        List<Customer> list = query.getResultList();
        System.out.println(list.size());
    }


    //================================================二级缓存==========================================================
    //配置步骤：
    //1.加入jar包：hibernate-ehcache.jar\ehcache-core.jar\slf4j-api.jar
    //2.加入配置：二级缓存策略<shared-cache-mode/>标签
    //3.对JPA产品开启二级缓存并配置缓存工厂类hibernate.cache.use_second_level_cache、hibernate.cache.region.factory_class属性
    //4.根据缓存策略，对实体类添加@Cacheable注解
    //5.ehcache.xml中配置二级缓存策略
    //6.开启查询缓存：配置JPA产品的hibernate.cache.use_query_cache属性
    @Test
    public void firstLevelCache() {
        //使用了一级缓存，只会发送一次查询
        Customer customer = entityManager.find(Customer.class,15);
        Customer customer2 = entityManager.find(Customer.class,15);
    }

    @Test
    public void secondLevelCache() {
        Customer customer = entityManager.find(Customer.class,15);
        transaction.commit();
        entityManager.close();

        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer2 = entityManager.find(Customer.class,15);
    }

    //==================================================双向多对多，必须有一方放弃维护权===================================
    @Test
    public void testManyToManyFind() {
        //获取维护端，对于关联对象默认使用懒加载
        Item item = entityManager.find(Item.class, 60);
        System.out.println("------------------------------------");
        System.out.println(item.getCategorySet());
        //获取不维护端，对于关联对象默认使用懒加载
        Category category = entityManager.find(Category.class, 58);
        System.out.println("--------------------------------------");
        System.out.println(category.getItemSet());
        //总结：使用任意一方获取都一样
    }

    @Test
    public void testManyToMany() {
        Item item = new Item();
        item.setName("item-" + LocalDate.now());
        Item item2 = new Item();
        item2.setName("item2-" + LocalDate.now());

        Category category = new Category();
        category.setName("category-" + LocalDate.now());
        Category category2 = new Category();
        category2.setName("category2-" + LocalDate.now());

        //对于不维护关系端，不用设置属性
        item.getCategorySet().add(category);
        item.getCategorySet().add(category2);
        item2.getCategorySet().add(category);
        item2.getCategorySet().add(category2);


        entityManager.persist(category);
        entityManager.persist(category2);
        entityManager.persist(item);
        entityManager.persist(item2);


    }

//==================================================双向一对一===========================================================
//1.在设置延迟加载的情况下：
    // 若获取维护关联关系的一端，默认会通过左外连接获取其关联对象，且发送两条语句;若获取不维护关系一方，也默认左外连接，但只发送一条语句
//2.在设置延迟加载的情况下：(双向一对一的懒加载根据业务需要慎重选择！！若加载关联对象需求几率低,推荐,否则不推荐)
    //若获取维护关联关系的一端,会先单表查询，对关联对象生成代理；然后在加载关联对象的时候，使用左外连接+单表查询，一共三条语句
    //若获取不维护关系的一端，也是一条左外连接搞定

    //总结使用建议：维护关系的一端使用@OneToOne(fetch = FetchType.LAZY)设置延迟加载；对于不维护关系的一端，懒加载意义不大
//若关联加载几率高，建议直接获取不维护关系的一端;若获取关联对象几率低，则获取维护关系的一端
    @Test
    public void testOneToOneFind() {
        Manager manager = entityManager.find(Manager.class, 48);
        System.out.println("-------------------------------------------");
        System.out.println(manager.getDepartment().getClass());
        System.out.println(manager.getDepartment());

//        Department department = entityManager.find(Department.class,47);
//        System.out.println("---------------------------------------------");
//        System.out.println(department.getManager().getClass());
//        System.out.println(department.getManager());

    }

    @Test
    public void testOneToOne() {
        Manager manager = new Manager();
        manager.setName("mrxie");

        Department department = new Department();
        department.setName("dept");
        //对于不维护关系的一端，可以不用设置关联对象
//        manager.setDepartment(department);
        department.setManager(manager);
        //建议先保存不维护外键关系的一方，不会多出update语句
        entityManager.persist(manager);
        entityManager.persist(department);

    }
//===================================================单(双)向一对多======================================================

    //单向1-n关联保存时，一定会多出update语句，因为n端不会同时差插入外键关系列
    //双向1-n关联时，
    @Test
    public void testOneToMany() {
        Customer customer = new Customer();
        customer.setLastName("mrXie");
        customer.setAge(25);
        customer.setEmail("mrxie.forever@gmail.com");
        customer.setDate(LocalDate.now());
        customer.setTime(LocalDateTime.now());

        Order order = new Order();
        order.setOrderName("order-" + LocalTime.now());
        Order order2 = new Order();
        order2.setOrderName("order-" + LocalTime.now());

        customer.getOrderSet().add(order);
        customer.getOrderSet().add(order2);

        order.setCustomer(customer);
        order2.setCustomer(customer);
//Case1:先保存一端，再保存多端，此时多端会再insert的时候就将外键插入，但是一端会再维护一次，多了n条update语句
//        entityManager.persist(customer);
//        entityManager.persist(order);
//        entityManager.persist(order2);
//Case2:先保存多端，再保存一端，这种情况下，多端在插入的时候并不知道外键值，故会在一端插入后再更新，而一端也会维护，多了2n条update语句
//        entityManager.persist(order);
//        entityManager.persist(order2);
//        entityManager.persist(customer);
//Case3:设置@OneToMany(mappedBy = "customer")，将外键维护权交给多端，且先保存一端，此时不会再产生update语句.
// 此时一端不能在使用@JoinColum注解，因为没有维护权了.
        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);
    }

    //默认使用懒加载，可以@OneToMany(fetch = FetchType.EAGER)设置为立即加载
    @Test
    public void testOneToManyFind() {
        Customer customer = entityManager.find(Customer.class, 27);
        System.out.println("-----------------------------------------");
        System.out.println(customer);
    }

    //默认情况下，会先把多的一方外键置空而不会删除，
    //可以通过@OneToMany(cascade = CascadeType.REMOVE),也会先置空，然后再级联删除
    @Test
    public void testOneToManyDel() {
        Customer customer = entityManager.find(Customer.class, 22);
        System.out.println("-----------------------------------------");
        entityManager.remove(customer);
    }

    //修改
    @Test
    public void testOneToManyUpdate() {
        Customer customer = entityManager.find(Customer.class, 20);
        System.out.println("-----------------------------------------");
        customer.getOrderSet().iterator().next().setOrderName("Order-Update");
    }

//================================================单向多对一================================================
    /*@Test
    public void testManyToOneUpdate() {
        Order order = entityManager.find(Order.class,14);
        order.getCustomer().setLastName("msLiu");
    }

    @Test
    public void testManyToOneDel() {
        //对于多端删除可以成功
        Order order = entityManager.find(Order.class,13);
        entityManager.remove(order);
        //不能直接删除一端，因为有外键约束
        Customer customer = entityManager.find(Customer.class,15);
        entityManager.remove(customer);
    }

    //默认情况下，使用的左外链接获取关联对象，可以设置@ManyToOne(fetch = FetchType.LAZY)
    @Test
    public void testManyToOneGet() {
        Order order = entityManager.find(Order.class,13);
        System.out.println("--------------------------------------");
        System.out.println(order);
    }

    //建议：先保存一的一端，避免多出update语句
    @Test
    public void testManyToOne() {
        Customer customer = new Customer();
        customer.setLastName("mrXie");
        customer.setAge(25);
        customer.setEmail("mrxie.forever@gmail.com");
        customer.setDate(LocalDate.now());
        customer.setTime(LocalDateTime.now());

        Order order = new Order();
        order.setOrderName("order-"+ LocalTime.now());
        Order order2 = new Order();
        order2.setOrderName("order-"+ LocalTime.now());

        order.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);
    }*/

}
