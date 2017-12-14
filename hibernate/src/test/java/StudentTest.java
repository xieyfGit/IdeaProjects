import com.yf.model.Account;
import com.yf.model.Student;
import com.yf.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;


/**
 * hibernate入门
 */
public class StudentTest {
    private Logger logger = LogManager.getLogger(StudentTest.class);

    @Test
    public void createTable() {
        logger.debug("slf4j-->log4j转换测试...");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        MetadataImplementor metadata = (MetadataImplementor) new MetadataSources(serviceRegistry).buildMetadata();
        new SchemaExport(metadata).create(true, true);
    }

    @Test
    public void add() {
        logger.debug("insert student into...");
        Student student = new Student();
        student.setName("mrxie");
        student.setAge(26);

        Student student2 = new Student();
        student2.setName("mischen");
        student2.setAge(27);

        User user = new User();
        user.setName("msChen");

        //hibernate简直一坨shit，好好的api一会一个样
//        Configuration cfg = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        //每个会话每次只能存储一种对象？好像是这样
        Session session = sessionFactory.getCurrentSession();
//        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        tx.begin();

        try {
            session.save(student);
            session.save(student2);
            session.save(user);
            tx.commit();
        } catch (Exception e) {
//            logger.error(e.getCause().getMessage());
            tx.rollback();
        }
    }

    @Test
    public void save() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Session session2 = null;
        Transaction tx2 = null;
        try {
//            Account account = new Account();
//            account.setName("mrxie");
//            Address address = new Address();
//            address.setAddr("chengdu");
//            account.setAddress(address);

            Account account = session.get(Account.class, 1);
            session2 = factory.openSession();
            tx2 = session2.beginTransaction();
            Account account2 = session2.get(Account.class, 1);
            account2.setName("msLis");
            tx2.commit();
            account.setName("msLik");
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx2.rollback();
            tx.rollback();
        }


    }
}
