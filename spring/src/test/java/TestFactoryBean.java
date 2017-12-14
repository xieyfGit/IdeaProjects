import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactoryBean {
    @Test
    public void test() {
        ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext();
        ctx.setConfigLocation("spring-factorybean.xml");
        ctx.refresh();

        System.out.println(ctx.getBean("car"));
    }
}
