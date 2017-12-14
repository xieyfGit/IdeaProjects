package com.yf.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DaoFactory {
    private static Properties properties = new Properties();

    private static Map<String,Object> cache = new HashMap<>();

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("dao.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static <T> T getInstance(String daoName,Class daoClass){
        T obj = (T) cache.get(daoName);
        if (null == obj) {
            String className = properties.getProperty(daoName);
            if(className!=null && !className.equals("")){
                try {
                    obj = (T) daoClass.cast(Class.forName(className).newInstance());
                    cache.put(daoName, obj);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

}
