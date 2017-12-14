import com.yf.spring.springdata.entity.Address;
import com.yf.spring.springdata.entity.Person;
import com.yf.spring.springdata.repository.PersonRepository;
import com.yf.spring.springdata.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestSpringData {


    private PersonRepository repository;
    private PersonService personService;

    @Before
    public void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-data.xml");
        repository = (PersonRepository) context.getBean("personRepository");
        personService = (PersonService) context.getBean("personService");
    }

    @Test
    public void init() {

    }

    @Test
    public void findByName() {
        Person person = null;
//        String name = "mrxie";
//        person = repository.getByLastName(name);
//        System.out.println(person);
//        person = repository.getByAddressIdGreaterThan(5);
//        System.out.println(person);
//        person = repository.getByAddress_IdGreaterThan(2);
//        System.out.println(person);
        person = repository.getMaxIdPerson();
        System.out.println(person);
    }

    @Test
    public void testQueryParam() {
//        for (Person person : repository.getAll(1)) {
//            System.out.println(person);
//        }
        for (Person person : repository.getAll2(1)) {
            System.out.println(person);
        }

        Person person = repository.getByLastNameLike("r");
        System.out.println(person);

        System.out.println(repository.getTotalCount());
    }

    @Test
    public void update() {
//        repository.update("mrChen",1);
        personService.update("mrChen",1);
    }


    @Test
    public void save() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Person person = new Person();
            person.setAddress(new Address());
            person.setAddressId(i+100);
            person.setBirth(LocalDate.now());
            person.setEmail("aa@"+i+"xx.com");
            person.setLastName("person"+i);
            list.add(person);
        }
        personService.save(list);
    }

    //只能实现不带查询条件的分页
    @Test
    public void pageTest() {
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC,"id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"lastName");
        Sort sort = new Sort(order1, order2);
        //pageN从零开始o
        Pageable page = new PageRequest(1,10,sort);
        Page<Person> personPage =repository.findAll(page);

        System.out.println(personPage.getSize());
        System.out.println(personPage.getTotalPages());
        System.out.println(personPage.getNumberOfElements());
        System.out.println(personPage.getNumber());
        System.out.println(personPage.getTotalElements());
        System.out.println(personPage.getContent());
        System.out.println(personPage);
    }
    //带查询条件的分页查询
    @Test
    public void pageWithSpe() {
        int pageNo = 1;
        int size = 5;
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        Pageable pageable = new PageRequest(pageNo - 1, size, sort);

        Specification<Person> spec = new Specification<Person>() {
            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = cb.and(
                        cb.lt(root.get("id"),20),
                        cb.equal(root.get("address").get("province"),"zj")
                );
                return predicate;
            }
        };
       Page<Person> page = repository.findAll(spec,pageable);
        System.out.println(page.getContent());
    }

    @Test
    public void testCustomerRepository() {
        repository.test();
    }

    @Test
    public void jpaRepositoryTest() {
        Person person = new Person();
        person.setAddress(new Address());
        person.setAddressId(100);
        person.setBirth(LocalDate.now());
        person.setEmail("aa@xx1.com");
        person.setLastName("person100");
        person.setId(45);
        //等价与jpa中的merge方法，内部有属性的复制操作
        Person person1 =repository.saveAndFlush(person);
        System.out.println("---------person == person1 ?------------------");
        System.out.println(person==person1);
    }
}
