import com.yf.spring.ioc.complext.ComplexBean;
import com.yf.spring.ioc.complext.ExtendComplexBean;
import com.yf.spring.ioc.spel.Car;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class TestSpEL {

    @Test
    public void test(){
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//        reader.loadBeanDefinitions(new ClassPathResource("spring-spel.xml"));
//        factory.setBeanExpressionResolver(new StandardBeanExpressionResolver());

        ConfigurableApplicationContext factory = new ClassPathXmlApplicationContext("spring-spel.xml");
        System.out.println(factory.getBean("wheel"));
        System.out.println((Car)factory.getBean("car"));
        factory.close();

    }
}
