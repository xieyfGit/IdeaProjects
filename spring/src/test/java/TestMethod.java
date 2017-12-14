import com.yf.spring.ioc.method.Boss;
import com.yf.spring.ioc.method.Car;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class TestMethod {
    @Test
    public void test(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("spring-method.xml"));

        Boss boss = (Boss) factory.getBean("boss");
        System.out.println(boss.getCar());
        System.out.println(boss.getCar());
        Boss boss2 = (Boss) factory.getBean("boss2");
        Car car = boss2.getCar();
        System.out.println(car+car.getName());

//        Car car = (Car) factory.getBean("car");
//        System.out.println(car);
//
//        car = (Car) factory.getBean("car");
//        System.out.println(car);
    }
}
