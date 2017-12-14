package com.yf.spring.aop.springaop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class MonitorManager extends DelegatingIntroductionInterceptor implements Monitorable {
    private static ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    @Override
    public void setMinitorable(boolean flag) {
        threadLocal.set(flag);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (threadLocal.get() != null && threadLocal.get()) {
            System.out.println("monitor on --" + mi.getClass().getSimpleName() + "." + mi.getMethod().getName() + "-- begin...");
            DealCalculate.begin();
            Object result = super.invoke(mi);
            System.out.println("monitor on --" + mi.getClass().getSimpleName() + "." + mi.getMethod().getName() + "-- end...,expends" + DealCalculate.end() + "ms");

            return result;
        }
        return super.invoke(mi);
    }
}

class DealCalculate {
    private static ThreadLocal<Long> expends = new ThreadLocal<>();

    public static void begin() {
        expends.set(System.currentTimeMillis());
    }

    public static long end() {
        return System.currentTimeMillis() - expends.get();
    }

    public static long get() {
        return expends.get();
    }
}
