import com.yf.annoModel.Account;
import com.yf.utils.SessionUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Date;


public class AnnoTest {

    @Test
    public void test(){
        Session session = SessionUtils.openSession();
        Transaction tx = session.beginTransaction();

        try {
            save(session);
//            update(session);
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

    }

    private void save(Session session){

        Account account = new Account();
        account.setAddress("chengdu");
        account.setAge(22);
        account.setBirthday(new Date());
        account.setContent(Hibernate.getLobCreator(session).createClob("你好！alibaba Clob数据测试"));
        account.setImage(null);
        account.setScore(88.9);
        account.setName("MrTest");

        session.save(account);
    }

    private void update(Session session){
        Account account = session.get(Account.class,2);
        //druid连接池的这一块实现不完整
//        account.setContent((java.sql.Clob) new MockClob("你好！alibaba Clob数据测试".getBytes()));
        account.setContent(Hibernate.getLobCreator(session).createClob("你好！alibaba Clob数据测试"));
        session.update(account);

    }

}
