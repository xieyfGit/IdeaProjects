import com.yf.spring.ioc.annotation.entity.Pen;
import com.yf.spring.ioc.annotation.entity.Student;
import com.yf.spring.ioc.annotation.repository.HelloRepository;
import com.yf.spring.ioc.annotation.repository.StudentDao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestAnnotation {
    @Test
    public void test() throws SQLException {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.scan("com.yf.spring.ioc.annotation");
        ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext();
        ctx.setConfigLocation("spring-anno.xml");
        ctx.refresh();

        System.out.println(((DataSource)ctx.getBean("dataSource")).getConnection());
        JdbcTemplate jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)ctx.getBean("namedParameterJdbcTemplate");
        StudentDao studentDao = (StudentDao) ctx.getBean("studentDao");
//        studentDao.select();
//        studentDao.selectNamedParam();
        studentDao.selectNamedParam2();
//        studentDao.batchUpdate();
//        studentDao.list();
//        studentDao.selectOneColumn();
//        studentDao.update();
    }

}
