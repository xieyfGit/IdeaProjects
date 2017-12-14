import com.yf.spring.aop.springaop.MonitorManager;
import com.yf.spring.aop.springaop.Monitorable;
import com.yf.spring.aop.springaop.StudyBean;
import com.yf.spring.aop.springaop.StudyBeanInterface;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringAOP {

    @Test
    public void test(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aop.xml");
//        StudyBeanInterface studyBean = (StudyBeanInterface) ctx.getBean("studyBean");
        StudyBean studyBean = (StudyBean) ctx.getBean("studyBean");

        studyBean.question();
        studyBean.lesson();
        studyBean.ofLesson();
        try {
            studyBean.doze();
        } catch (Exception e) {
        }
        System.out.println("开启性能监视......................................");
        Monitorable monitorable =(Monitorable) studyBean;
        monitorable.setMinitorable(true);
        studyBean.question();
        studyBean.lesson();
        studyBean.ofLesson();
        try {
            studyBean.doze();
        } catch (Exception e) {
        }


    }
}
