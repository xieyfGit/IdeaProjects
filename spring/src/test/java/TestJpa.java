import com.yf.spring.jpa.entity.Person;
import com.yf.spring.jpa.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJpa {

    private PersonService personService;
    @Before
    public void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-jpa.xml");
        personService = (PersonService) context.getBean("personService");
    }

    @Test
    public void save() {
        Person person = new Person();
        person.setName("mrxie");
        Person person2 = new Person();
        person2.setName("mrChen");
        System.out.println(personService.getClass().getName());
        personService.save(person,person2);

    }
}
