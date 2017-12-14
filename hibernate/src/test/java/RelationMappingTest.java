import com.yf.model.*;
import com.yf.utils.SessionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * cascade 用于维护关联对象之间的关系
 * inverse用于一对多时，一方放弃外键维护权利，这样可以减少sql语句
 */
public class RelationMappingTest {

    @Test
    public void test() {
        Session session = SessionUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {

//            saveOto(session);
//             Account account = session.get(Account.class,2);
//             session.delete(account);
//            saveOtm(session);
//            Dept dept = session.get(Dept.class,6);
//            session.delete(dept);


//            saveSelf(session);
//            Category parent =session.get(Category.class,25);
//            session.delete(parent);

            saveMtm(session);
//            People people = session.get(People.class,1);
//            Set<PeoCon> set = people.getPeoConSet();
//            for (PeoCon peoCon : set) {
//                System.out.println(peoCon.getContact());
//            }
//            session.delete(people);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    /*
    many-to-many
     */
    public void saveMtm(Session session) {
        People p1 = new People();
        People p2 = new People();
        p1.setName("p1");
        p2.setName("p2");

        Contact c1 = new Contact();
        Contact c2 = new Contact();
        c1.setName("c1");
        c2.setName("c2");
////many-to-many方式
////        Set<People> peoples = new HashSet<>();
////        peoples.add(p1);
////        peoples.add(p2);
//
//        Set<Contact> contacts = new HashSet<>();
//        contacts.add(c1);
//        contacts.add(c2);
//
//        p1.setContacts(contacts);
//        p2.setContacts(contacts);
//
////        c1.setPeoples(peoples);
////        c2.setPeoples(peoples);
//
//        session.save(p1);
//        session.save(p2);
//        session.save(c1);
//        session.save(c2);
//新建一个中间类，利用one—to-many和many-to-one替代

        PeoCon peoCon = new PeoCon();
        peoCon.setPeople(p1);
        peoCon.setContact(c1);
        session.save(peoCon);

        PeoCon peoCon1 = new PeoCon();
        peoCon1.setPeople(p1);
        peoCon1.setContact(c2);
        session.save(peoCon1);

        PeoCon peoCon2= new PeoCon();
        peoCon2.setPeople(p2);
        peoCon2.setContact(c1);
        session.save(peoCon2);

        PeoCon peoCon3 = new PeoCon();
        peoCon3.setPeople(p2);
        peoCon3.setContact(c2);
        session.save(peoCon3);

    }

    /*
    relationOnSelf自身关联
     */
    public void saveSelf(Session session) {
        Category parent = new Category();
        parent.setName("computer");

        Category son1 = new Category();
        son1.setName("java");

        Category son2 = new Category();
        son2.setName(".net");

        /*由多方负责外键维护*/
        son1.setParent(parent);
        son2.setParent(parent);
        session.save(son1);
        session.save(son2);
        /*由一方负责外键维护*/
//        Set<Category> set = new HashSet<>();
//        set.add(son1);
//        set.add(son2);
//        parent.setSons(set);
//        session.save(parent);


    }

    /*
    one-to-one
     */
    public void saveOto(Session session) {
        Account account = new Account();
        account.setName("account");

        Address address = new Address();
        address.setAddr("beijing");
        account.setAddress(address);


        session.save(account);
    }

    private void getOto(Session session) {
        Account acc = session.get(Account.class, 1);
        System.out.println(acc.getAddress());
        Address addr = session.get(Address.class, 1);
        System.out.println(addr.getAccount());
    }

    /*
    one-to-many\many-to-one
     */
    public void saveOtm(Session session) {
        Dept dept = new Dept();
        dept.setName("dept1");
        Employee emp1 = new Employee();
        emp1.setName("emp1");
        Employee emp2 = new Employee();
        emp2.setName("emp2");

        Set<Employee> set = new HashSet<>();
        set.add(emp1);
        set.add(emp2);
        dept.setEmps(set);

        session.save(dept);

//        emp1.setDept(dept);
//        emp2.setDept(dept);
//        session.save(emp1);
//        session.save(emp2);
    }

}
