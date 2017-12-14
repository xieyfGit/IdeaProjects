import com.yf.spring.ioc.complext.ComplexBean;
import com.yf.spring.ioc.complext.ExtendComplexBean;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class TestCollection {

    @Test
    public void test(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("spring-collection.xml"));

//        testComplexBean(factory);
        testExtComplexBean(factory);

    }

    //测试Bean的复杂类型属性注入
    private void testComplexBean(DefaultListableBeanFactory factory){
        ComplexBean cb= (ComplexBean) factory.getBean("complexBean");
        System.out.println(cb);
    }

    //测试Bean之间的属性继承、覆盖特性
    private void testExtComplexBean(DefaultListableBeanFactory factory) {
        ComplexBean cb = (ExtendComplexBean) factory.getBean("extComplexBean");
        System.out.println(cb);
    }
}
