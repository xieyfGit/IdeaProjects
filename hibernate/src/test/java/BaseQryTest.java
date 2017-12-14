import com.yf.model.Student;
import com.yf.utils.SessionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * HQL
 */
public class BaseQryTest {

    @Test
    public void baseQuery() {
        Session session = SessionUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();
        BaseQryTest baseQryTest = new BaseQryTest();
        try {
            selOne(session);
//            selOneSpe(session);
//            selOneProj(session);
//            selListPageable(session, 1, 5);
//            selWithPlaceHolder(session);
//            selNamedQry(session, 1, 2);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }


    /**
     * 本地HQL、SQL查询
     *
     * @param session
     * @param pageNo
     * @param pageSize
     */
    public void selNamedQry(Session session, int pageNo, int pageSize) {
        Query query = session.getNamedQuery("hql");
        //hql使用？占位符时，参数索引从0开始
        List<Student> list;

        list = query.setString(0, "mrxie").list();
        for (Student student : list) {
            System.out.println(student);
        }
        query = session.getNamedQuery("hql1");
        list = query.setParameter("name", "mrxie").list();
        for (Student student : list) {
            System.out.println(student);
        }

        //sql使用？占位符时，参数索引从1开始,但是这个版本的Query接口好像有问题，这个程序员没救了
        query = session.getNamedQuery("nativeSql");
        list = query.setString(0, "genius_4_fool").list();
//        list = query.setParameter(0, "genius_4_fool").list();
        for (Student student : list) {
            System.out.println(student);
        }
        query = session.getNamedQuery("nativeSql1");
        list = query.setString("id", "genius_4_fool").list();
        for (Student student : list) {
            System.out.println(student);
        }
        //分页
        query = session.getNamedQuery("nativePage");
        List<Object[]> list1 = query.setInteger("offset", (pageNo - 1) * pageSize).setInteger("endset", pageSize).list();
        for (Object[] objs : list1) {
            for (Object obj : objs) {
                System.out.println(obj);
            }
        }
//        list = query.setInteger("offset", (pageNo - 1) * pageSize).setInteger("endset", pageSize).list();
//        for (Student student : list) {
//            System.out.println(student);
//        }
    }

    /**
     * 参数占位形式查询
     *
     * @param session
     */
    public void selWithPlaceHolder(Session session) {
        Query query = session.createQuery("from Student where name like :name");
        query.setString("name", "%xie");
        List<Student> list = query.list();
        for (Student student : list) {
            System.out.println(student);
        }
    }

    /**
     * 分页查询
     *
     * @param session
     * @param pageNo
     * @param pageSize
     */
    public void selListPageable(Session session, int pageNo, int pageSize) {

        List<Student> list = session.createQuery("from Student order by id desc")
                .setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
        for (Student student : list) {
            System.out.println(student);
        }
    }

    /**
     * 投影查询
     *
     * @param session
     */
    public void selOneProj(Session session) {
        List<Student> list = session.createQuery("select new Student(s.name,s.age) from Student s").list();
        for (Student student : list) {
            System.out.println(student);
        }
    }

    /**
     * 带条件查询
     *
     * @param session
     */
    public void selOneSpe(Session session) {
        List<Student> list = (List<Student>) session.createQuery("from Student where id = 'genius_0_fool'").list();
        System.out.println(list);
    }

    //列表查询
    public void selList(Session session) {
        List<Student> list = (List<Student>) session.createQuery("from Student").list();
        for (Student stu : list) {
            System.out.println(stu);
        }
    }

    //单对象查询
    public void selOne(Session session) {
        Student student = (Student) session.createQuery("from Student").setMaxResults(1).uniqueResult();
        System.out.println(student);
    }

}