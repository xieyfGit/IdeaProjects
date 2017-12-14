import com.yf.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

/**
 * hibernate基本操作
 */
public class BasicOpTest {

    private Logger logger = LogManager.getLogger(BasicOpTest.class);

    @Test
    public void getOrLoad() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        User u;
        User u1;
        Transaction tx = session.beginTransaction();
        session.setDefaultReadOnly(true);
        try {
//            logger.debug("get begin...");
//            u =session.get(User.class,110);
//            logger.debug(u.getName());
            logger.debug("load begin...");
            u1 = session.load(User.class, 10);
            logger.debug(u1.getName());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

    }

    @Test
    public void save() {
        User user = new User();
        user.setName("msHand");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        session.setFlushMode(FlushMode.MANUAL);
        Transaction tx = session.beginTransaction();

        try {
            session.save(user);
            logger.debug("id=" + user.getId());
            user.setName("mrHand1");
            session.flush();
            user.setName("aaa");
//            session.update(user);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

    }

    @Test
    public void upOrMerge() {

        User user;

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            //update一般用于脱管状态对象信息的更新
            //脱管状态的解释：脱离当前session管理，但是存在于数据库中，可以通过evict()方法达到目的
            user = new User();
            user.setName("MrXI1");
            user.setId(1482);  //瞬时状态Transient

//            user  = session.get(User.class,1242);
            session.update(user); //首先查找缓存中不存在，然后发现数据库中存在，直接update语句
            //不明白意义何在，反正也要提交后才可以更新到数据库的。
            //意义就在这个地方，刷新缓存后，语句会被发送到数据库。如果不刷新，在事务提交前（提交事务会先刷新缓存）
            // 此间对缓存中的对象进行的所有操作都将反映到缓存对象。
            //那么有人就会问了，即使我刷新了，但是我对对象再操作，依然会反映到数据库。当然，但是过程有了变化。
            //刷新的意思就是在数据库登记操作，但是并没有提交。此时你更改了对象，到事务提交的时候，会再次刷新缓存，所以不足为奇。
            session.flush();
            session.evict(user);//从缓存中删除对象


            //merge 类似saveOrUpdate
            user = new User();
            user.setName("merge");// 瞬时状态 Transient
            session.merge(user);//进入持久状态 Persistent，此时insert语句已经发送到数据库，等待提交
            user.setName("merge2");
            //update抛出异常，对象并没有和数据库有实际关联
//            session.update(user);
            //以下两种操作会多插入记录，且主键生成策略由数据库决定
            //此时，由于上面的merge操作并没有提交事务，所以对象虽然处于持久状态，而一个save方法就会登记一个sql操作到数据库
            //所以才会多插入记录，至于主键生成策略的问题，暂时不清楚。
//            session.save(user);
//            session.merge(user);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }


    }
}
