package com.yf.web.curd.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstInterceptor implements HandlerInterceptor {

    @Override
    //方法执行于DispatcherServlet.java:962调用applyPreHandle，处理方法调用之前
    //考虑用于做权限，事务，日志处理
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FirstInterceptor..preHandle!");
        //此处若返回false.此过滤器结束，然后倒叙遍历执行此过滤器之前执行过的过滤器，即调用其afterCompletion方法，此时页面无返回值
        return true;
    }

    //处理方法调用之后，渲染视图之前，用于对请求于域中的属性或视图做出修改
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("FirstInterceptor..postHandle!");
    }

    //渲染视图之后被调用，用于释放资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("FirstInterceptor..afterCompletion!");
    }
}
