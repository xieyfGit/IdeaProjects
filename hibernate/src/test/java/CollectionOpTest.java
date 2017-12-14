import com.yf.model.UserCollection;
import com.yf.utils.SessionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.*;

public class CollectionOpTest {
    @Test
    public void Test() {
        Session session = SessionUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {

//            save(session);
            query(session);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }


    }


    public void query(Session session) {
        UserCollection userCollection = session.get(UserCollection.class, (long) 1);
        System.out.println(userCollection.getName());
        for (String s : userCollection.getSet()) {
            System.out.println(s);
        }
        for (String s : userCollection.getList()) {
            System.out.println(s);
        }
        for (Map.Entry<String,String> entry : userCollection.getMap().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        for (String s : userCollection.getCollection()) {
            System.out.println(s);
        }
        userCollection.getCollection().remove("linkd1");
        userCollection.getCollection().add("linkd3");
        userCollection.getMap().remove("key1");
        userCollection.getMap().put("key3", "v3");
    }


    public void save(Session session) {
        Set set = new HashSet<String>();
        set.add("set1");
        set.add("set2");

        Map map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");

        List list = new ArrayList();
        list.add("list1");
        list.add("list2");

        Collection coll = new LinkedList();
        coll.add("linkd1");
        coll.add("linked2");

        UserCollection uc = new UserCollection();
        uc.setName("userCollection");
        uc.setSet(set);
        uc.setList(list);
        uc.setMap(map);
        uc.setCollection(coll);

        session.save(uc);
    }
}
