package com.yf.web.test.servlet;

import com.yf.web.test.entity.Person;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = -5138288456631539535L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从application中得到IOC容器
        ServletContext servletContext = getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("applicationCtx");
        //从IOC容器中得到需要的Bean
        Person person = context.getBean("person",Person.class);
        System.out.println(person);
    }
}
