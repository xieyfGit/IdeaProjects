package com.yf.web.test.listener;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;

public class SpringServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("SpringServletContextListener..contextInitialized！");

        String configLocation = (String) sce.getServletContext().getInitParameter("contextConfigLocation");
//        //创建IOC容器,配置文件配置到当前web应用的初始化参数中
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
//
//        //把IOC容器放到ServletContext中
        sce.getServletContext().setAttribute("applicationCtx",context);
        System.out.println("SpringServletContextListener..contextInitialized complete...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) sce.getServletContext().getAttribute("applicationCtx");
        context.close();
    }
}
