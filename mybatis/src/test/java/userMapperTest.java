import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.yf.entity.Address;
import com.yf.entity.Gender;
import com.yf.entity.User;
import com.yf.entity.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tools.ant.types.Mapper;
import org.junit.Test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class userMapperTest {

    @Test
    public void test() {

//        initialContextTest();
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader,"development");
        SqlSession session = null;

        try {
            session = factory.openSession();

//            saveXml(session);
//            saveMapper(session);
//            saveBatch(session);
            selectOneXML(session);
//            selectOneByMapper(session);
//            findAll(session);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }

    }

    //xml方式查询
    private void selectOneXML(SqlSession session) {

        User user = session.selectOne("com.yf.entity.UserMapper.findById", 47);
        System.out.println(user);
//        Address address = session.selectOne("com.yf.entity.UserMapper.findById", 47);
//        System.out.println(address);

    }

    //Mapper接口存储
    private void saveMapper(SqlSession session) {
        User user = new User();
        user.setName("hello java");
        user.setDate(new Date());
        user.setGender(Gender.女);
        session.getMapper(UserMapper.class).addOneMapper(user);

        Address address = new Address();
        address.setAddress("zhejiang");
        address.setUserId(user.getId());
        System.out.println(user);
        session.insert("com.yf.entity.Address.addOne", address);

    }

    //xml方式存储
    private void saveXml(SqlSession session) {
        User user = new User();
        user.setName("hello java");
        user.setDate(new Date());
        user.setGender(Gender.男);
        session.insert("com.yf.entity.UserMapper.addOne", user);

        Address address = new Address();
        address.setAddress("zhejiang");
        address.setUserId(user.getId());
        session.insert("com.yf.entity.Address.addOne", address);
        System.out.println(user);
    }

    //批量更新
    private void saveBatch(SqlSession session) {
        List<User> list = new ArrayList<>();
        User user;
        for (int i = 0; i <10 ; i++) {
            user = new User();
            user.setName("mrxie"+i);
            user.setDate(new Date());
            user.setGender(i%2==0?Gender.男:Gender.女);
            list.add(user);
        }
        session.insert("com.yf.entity.UserMapper.addBatch",list);
    }
    //Mapper接口方式查询所有

    private void findAll(SqlSession session){
        List<User> list = session.getMapper(UserMapper.class).findAll().subList(1,5);
        System.out.println(list);
    }
    //利用Mapper接口方式查询
    private void selectOneByMapper(SqlSession session) {
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUser(51);
        System.out.println(user);
    }

    private  void initialContextTest(){
        Hashtable environment = new Hashtable();
        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(userMapperTest.class.getResourceAsStream("c3p0Context.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        environment.putAll(p);
        try {
            InitialContext context = new InitialContext(environment);
            context.bind("context",ComboPooledDataSource.class);
            context.addToEnvironment("dataSouce",new ComboPooledDataSource());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

}
