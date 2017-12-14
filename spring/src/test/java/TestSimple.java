import com.yf.spring.ioc.simple.Person;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class TestSimple {


    @Test
    public void test() throws InterruptedException {
        //在启动容器的时候并不会初始化Bean，等到具体使用的时候再分别初始化
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//        reader.loadBeanDefinitions(new ClassPathResource("spring-simple.xml"));

        //容器启动的时候就初始化了所有Bean,此处指的是单例bean
        ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("spring-simple.xml");
        Person person = (Person) factory.getBean("person_cons");
        person.sayHello("hello world,person complext by constructor");
        person.setName("mrXie");
        System.out.println(person);

        //重置实例缓存
//        factory.destroySingletons();
        //利用ThreadLocal来解决单例的线程安全问题
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Person person2 = (Person) factory.getBean("person_cons");
                person2.sayHello("hello world,person complext by constructor");
                System.out.println(person2);
                System.out.println(person2.getName());
            }
        });
        thread.start();
        thread.join();

        Person person2 = (Person) factory.getBean("person_cons");
        person2.sayHello("hello world,person complext by constructor");
        System.out.println(person2);
        System.out.println(person2.getName());

        Person person3 = (Person) factory.getBean("person_set");
        person3.sayHello("hello world,person complext by setter");
        System.out.println(person3);

    }


}
