package com.yf.service;

import com.yf.dao.DaoFactory;
import com.yf.dao.StudentDao;
import com.yf.model.Student;
import com.yf.utils.SessionUtils;
import org.hibernate.Transaction;

import java.util.List;

public class StudentService {
    private StudentDao studentDao = DaoFactory.getInstance("studentDao", StudentDao.class);

    public static void main(String[] args) {

        StudentService service = new StudentService();
//        service.addStu();

        for (Student stu :  service.list()) {
            System.out.println(stu);
        }
    }

    public List<Student> list() {
        Transaction tx = SessionUtils.getCurrentSession().beginTransaction();
        List<Student> list = studentDao.findAll();
        return list;
    }

    public void addStu() {
        Transaction tx = SessionUtils.getCurrentSession().beginTransaction();
        try {
            for (int i = 0; i < 20; i++) {
                    Student stu = new Student();
                    stu.setAge(12 + i);
                    stu.setName("mrxie" + i);
                    studentDao.save(stu);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}

