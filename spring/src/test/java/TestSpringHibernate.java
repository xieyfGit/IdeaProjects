import com.yf.spring.hibernate.service.BookShopWrapperService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class TestSpringHibernate {

    @Test
    public void test(){

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-hibernate.xml");
        BookShopWrapperService service = (BookShopWrapperService) ctx.getBean("bookShopWrapperServiceImpl");
        service.buyMany("mrxie", Arrays.asList("1001","1002"));
    }
}
