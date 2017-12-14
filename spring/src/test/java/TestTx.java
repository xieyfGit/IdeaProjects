import com.yf.spring.tx.service.BookShopWrapperService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class TestTx {
    @Test
    public void test(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-tx.xml");
        BookShopWrapperService bookShopService = (BookShopWrapperService) ctx.getBean("bookShopWrapperServiceImpl");
//        bookShopService.buy("mrxie","1001");
        bookShopService.buyMany("mrxie", Arrays.asList("1001","1002"));
    }
}
