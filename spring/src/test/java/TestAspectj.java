import com.yf.spring.aop.cglib.CglibProxyFactory;
import com.yf.spring.aop.proxy.ProxyFactory;
import com.yf.spring.aop.proxy.Student;
import com.yf.spring.aop.proxy.StudentInterface;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectj {
    @Test
    public void test(){

        testProxy();
//        testCglib();
//        testAspect();
    }

    private void testProxy(){
        Student sl = new Student();
        ProxyFactory<StudentInterface> factory = new ProxyFactory<>();
        StudentInterface sl2 = factory.createProxy(sl);
        sl2.print();
    }

    private void testCglib(){
        com.yf.spring.aop.cglib.Student s = new com.yf.spring.aop.cglib.Student();
        CglibProxyFactory<com.yf.spring.aop.cglib.Student> factory = new CglibProxyFactory<>();
        com.yf.spring.aop.cglib.Student sc = factory.crateProxyFactory(s);
        sc.print();
    }

    private void testAspect(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aspectj.xml");
        com.yf.spring.aop.aspectj.Student stu = (com.yf.spring.aop.aspectj.Student) ctx.getBean("student");
        stu.print("mrXia");
        System.out.println("=================================");
        stu.print(0);
    }
}
