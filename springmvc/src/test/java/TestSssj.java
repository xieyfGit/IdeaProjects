import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

//测试spring + spring mvc + spring data + jpa
public class TestSssj {

    @Before
    public void before() {
        ApplicationContext context = new ClassPathXmlApplicationContext("sssj.xml");
        EntityManagerFactory factory = (EntityManagerFactory)context.getBean("entityManagerFactory");
        System.out.println(factory.createEntityManager());
    }

    @Test
    public void init() {

    }
}
