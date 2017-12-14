package com.yf.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionUtils {
    private static ThreadLocal<Session> sessionLocal = new ThreadLocal<>();

    private static SessionFactory sessionFactory;

    public static Session getSession(){
        Session session = sessionLocal.get();
        synchronized (sessionFactory) {
            if (session == null) {
                session = getSessionFactory().openSession();
                sessionLocal.set(session);
            }
        }
        return  session;
    }

    static {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }


    public static Session openSession(){
        return sessionFactory.openSession();
    }
}
