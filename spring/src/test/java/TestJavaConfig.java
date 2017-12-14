import com.yf.spring.ioc.javaconfig.ApplicationContextConfig;
import com.yf.spring.ioc.javaconfig.DBConfig;
import com.yf.spring.ioc.javaconfig.IDCard;
import com.yf.spring.ioc.javaconfig.User;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJavaConfig {

    @Test
    public void test(){
        //JavaConfig引入配置文件方式
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        //等价于在ApplicationContextConfig类上使用@Import注解引入其它注解类
//        //context.register(ApplicationContextConfig.class, DBConfig.class);
//        context.register(ApplicationContextConfig.class);
//        context.refresh();
//        User user = (User) context.getBean("user");
//        System.out.println(user);
//        System.out.println(user.getIdCard());
//
//        context.destroy();

//        配置文件引入JavaConfig配置类方式
        ClassPathXmlApplicationContext xmlCtx = new ClassPathXmlApplicationContext();
        xmlCtx.setConfigLocation("spring-javaconfig.xml");
        xmlCtx.refresh();
        User user = (User) xmlCtx.getBean("user");
        System.out.println(user);
        System.out.println(user.getIdCard());
        xmlCtx.destroy();
    }
}
