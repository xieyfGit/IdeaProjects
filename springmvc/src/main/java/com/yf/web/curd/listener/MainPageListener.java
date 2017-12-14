package com.yf.web.curd.listener;

import com.yf.web.curd.service.EmpService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MainPageListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
       EmpService empService = (EmpService) ctx.getBean("empService");
        servletContextEvent.getServletContext().setAttribute("empService",empService.getAll());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
