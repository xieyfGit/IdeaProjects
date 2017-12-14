import com.yf.model.ECacheEntity;
import com.yf.utils.SessionUtils;
import net.sf.ehcache.config.ConfigurationHelper;
import org.hibernate.*;
import org.junit.Test;

public class ECacheTest {

    @Test
    public void test() {

        Session session = SessionUtils.openSession();
        Transaction tx = session.beginTransaction();

        try {
//            save(session);
            secLevelQuery(session);
            queryCache(session);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        Session session2 = SessionUtils.openSession();
        Transaction tx2 = session2.beginTransaction();
        try {
            secLevelQuery(session2);
            tx2.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx2.rollback();
        }
    }


    private void save(Session session){
        ECacheEntity eCacheEntity = new ECacheEntity();
        eCacheEntity.setName("ec1");
        session.save(eCacheEntity);
    }

    /**
     * 二级缓存测试
     * @param session
     */
    private void secLevelQuery(Session session){
        ECacheEntity eCacheEntity = session.get(ECacheEntity.class,1);
        System.out.println(eCacheEntity);
    }

    /**
     * 查询缓存，依赖二级缓存
     * @param session
     */
    private void queryCache(Session session){
        Query query = session.createQuery("from ECacheEntity where id = 1");
        query.setCacheable(true);
        query.setCacheRegion("queryRegion");
        ECacheEntity eCacheEntity = (ECacheEntity) query.setMaxResults(1).uniqueResult();
        System.out.println(eCacheEntity);
    }

}
