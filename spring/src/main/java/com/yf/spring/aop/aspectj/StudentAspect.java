package com.yf.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 通知的位置可以参考com.yf.spring.aop.proxy.ProxyFactory类中的注释
 */
//使用Order来指定切面的优先级，值越小优先级越高，默认为int型的最大值
@Order
@Aspect
@Component
public class StudentAspect {

    //统一定义切点表达式
    @Pointcut("execution(public * com.yf.spring.aop.aspectj.Student.print(..))")
    public void pointCut(){

    }

    //前置通知：在方法执行之前执行，此处也可以指定接口
//    @Before("execution(public * com.yf.spring.aop.aspectj.Student.print(..))")
    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("beforeMethod===The method " +methodName+" begins with args "+args);
    }

    //返回通知：无论异常是否发生,方法结束后都会执行
//    @After("execution(public * com.yf.spring.aop.aspectj.Student.print(..))")
    @After("pointCut()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("afterMethod===The method " +methodName+" end...");
    }

    //后置通知：在方法正常结束后执行的,由于方法可能抛出异常，故可能访问不到返回值
//    @AfterReturning(value="execution(* com.yf.spring.aop.aspectj.Student.*(..))",returning = "result")
    @AfterReturning(value = "pointCut()",returning = "result")
    public void afterMethodReturn(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("afterMethodReturn===The method " +methodName+" end...return "+result);
    }

    //后置通知：在方法正常结束后执行的,由于方法可能抛出异常，故可能访问不到返回值
//    @AfterThrowing(value="execution(* com.yf.spring.aop.aspectj.Student.*(..))",throwing = "ex")
    @AfterThrowing(value = "pointCut()",throwing = "ex")
    public void onThrows(JoinPoint joinPoint,ArithmeticException ex){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("onThrows===The method " +methodName+" end...with Exception "+ex.getMessage());
    }

    //环绕通知(类似于动态代理的全过程),环绕通知必须由返回值，且必须与目标方法返回值一致
//    @Around(value="execution(* com.yf.spring.aop.aspectj.Student.*(..))")
    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        Object result = null;
        try {
            //前置通知
            System.out.println("aroundMethod===The method " +methodName+" begin...with args "+args);
           result =  joinPoint.proceed();
           //后置通知
            System.out.println("aroundMethod===The method " +methodName+" end...");
        } catch (Throwable ex) {
//            ex.printStackTrace();
            //异常通知
            System.out.println("aroundMethod===The method " +methodName+" end...with Exception "+ex.getMessage());
            throw new RuntimeException(ex);
        }
        //返回通知
        System.out.println("aroundMethod===The method " +methodName+" return "+result);
        return result;
    }
}
